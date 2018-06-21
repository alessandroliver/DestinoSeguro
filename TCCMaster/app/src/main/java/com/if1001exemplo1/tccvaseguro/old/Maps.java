package com.if1001exemplo1.tccvaseguro.old;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.if1001exemplo1.tccvaseguro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Maps extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        LocationListener{

    public static GoogleMap mMap;
    private double longitude;
    private double latitude;
    private String response;


    private Context mContext;
    public static boolean alertShowed = false;

    public static String addressOrigen = "";
    public static String destAdress = "";

    //pra pequenas 'alternativas'
    public static double latOri = 0;
    public static double longOri = 0;
    private double latDest =0;
    private double longiDest = 0;
    public static String mode = "";
    Location locationA = null;

    //variáveis do relatório
    public static String origem = "";
    public static String destino = "";
    public static String tempo_saida= "";
    public static String tempo_chegada= "";



    SharedPreferences sharedPref;
    SharedPreferences sharedPrefCheck;
    SharedPreferences.Editor edt;
    LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);

        //sharedPreference relacionada as preferências da Preference Activity
        sharedPref = getActivity().getSharedPreferences(Constants.PREFERENCES
                , Context.MODE_PRIVATE);

        //preference usada pra ver se há rota em curso
        sharedPrefCheck = getActivity().getSharedPreferences(Constants.CHECK, Context.MODE_PRIVATE);
        edt = sharedPrefCheck.edit();

        //contexto
        mContext = getActivity();
    }

//onMapReady é um método chamado quando o mapa está pronto
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //inicializa o mapa
        mMap = googleMap;
        //seta o o tipo do mapa
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        try {
            //habilita a localização do usuário
            mMap.setMyLocationEnabled(true);


        } catch (SecurityException e) {
        }
        //poder controlar o zoom
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //reposicionar o zoom e o setmylocation no mapa
        mMap.setPadding(0,240,0,170);

        //isso serve pra pegar os extras, por exemplo, quando o usuário muda de tela, ele perde as informações se não salvar
        //nesse caso, para não se dar ao trabalho de salvar, simplesmente passamos extras de uma tela pra outra

        Bundle extras = getActivity().getIntent().getExtras();
        //passo começa aqui
        //checa se há rota em curso- ou seja, se o cara colocou o destino
        if (sharedPrefCheck.getBoolean(Constants.CHECK_ROUTE, false)) {
            if(extras!=null) {
                //pega os extras : destino, origem e modo (que são usados na URL pra fazer o Download do Json)
                destAdress = extras.getString("destinationName");
                latOri = extras.getDouble("latOrigenExtra");
                longOri = extras.getDouble("longOrigenExtra");
                mode = extras.getString("mode");
            }

            //inicializa a sharedPreference pra pegar o modo que o usuário escolheu lá nas Preferências
            sharedPref = mContext.getSharedPreferences(Constants.PREFERENCES
                    , Context.MODE_PRIVATE);
            //pega esse modo, se não tiver, usa o valor default
            String selectedMode = sharedPref.getString("listTransportPreference", "walking");

            //checa se a rota foi escolhida - ou seja, vê se tá lá no banco salvo
            if (new LocationDatasController(getActivity()).isTypeRouteExists(addressOrigen, destAdress, selectedMode)) {
                //pega o que tava no arquivo json que foi armazenada no BD para fazer o parser
                String jsonRoute = new LocationDatasController(getActivity()).getRoute(addressOrigen, destAdress, selectedMode);
                //executa a Asysnc responsável pelo Parser do texto do json que contém a rota - fica responsável por pegar todos os pontos
                new ParserTask().execute(jsonRoute);
            } else {
                //seta a hora que começou a rota (pro relatório)
                SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
                Date hora = Calendar.getInstance().getTime();
                String dataFormatada = sd.format(hora);


                //caso não haja essa rota armazenada, faz primeiramente a conversão de nome pra coordenada
                //executando a Asynctak responsável por isso
                new Geocoding().execute(destAdress);
            }

        } else{
            //caso não haja um percurso em curso, seta a localização do usuário no mapa
            Location loc = MainActivity.loc;
            if(loc != null){
                mMap.clear();
                latOri = loc.getLatitude();
                longOri = loc.getLongitude();
                //faz a o reversegeocoding pra obter o nome do lugar da origem
                new ReverseGeocodingTask(getActivity()).execute(new LatLng(latOri,longOri));
                LatLng newLocation = new LatLng(latOri,longOri);
                //add o marcador
                addMarker(newLocation, "Você");
                //move pra onde o usuário está
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 16));
            }
        }

        //verifica se o usuário quer ver os locais perigosos marcados no mapa

        if(sharedPref.getBoolean("ativarLPPreference", false)){
            //chama o método responsável por marcar isso no mapa
            markerDangerousLocations(getActivity(), mMap);
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        try {
            //esse location serve pra controlar quando irá atualizar pra pegar a localização
            //nesse caso, foram 3 segundos
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        }catch (SecurityException e){
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        //remove atualização da localização quando sai do mapa
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(this);

    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    //pegar rota - isso aqui usa com aqueles botões do reload
    public void getRoute(String destAdress, GoogleMap map, Context context){
        this.mContext = context;
        mMap = map;
        this.destAdress = destAdress;
        new Maps.Geocoding().execute(destAdress);

    }

    //opção de nova rota quando o usuário sai da sua rota original
    public void newRoute(String destAdress, GoogleMap map, Context context){
        this.mContext = context;
        mMap = map;
        this.destAdress = destAdress;
        new Maps.ReverseGeocodingTask(mContext).execute(new LatLng(latOri,longOri));
        new Maps.Geocoding().execute(destAdress);
        alertShowed = false;
    }

    //colocando os marcadores dos lugares onde foi emitido o alarme
    public void markerDangerousLocations(Context context,GoogleMap mMap ){
        List<DangerousLocations> dangerousLocations = new LocationDatasController(context).getAllLocationAlarm(context);
        if(dangerousLocations.size() > 0) {
            MarkerOptions marker = new MarkerOptions();
            for (DangerousLocations dangerousLocation : dangerousLocations) {
                marker.position(new LatLng(dangerousLocation.getLat(), dangerousLocation.getLng()));
                marker.title("Alerta emitido aqui");
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
                mMap.addMarker(marker);

            }
        }
    }


    //removendo lugare perigosos
    public void removeDangerousLocations(Context context, GoogleMap mMap){
        sharedPrefCheck = context.getSharedPreferences(Constants.CHECK, Context.MODE_PRIVATE);
        sharedPref = context.getSharedPreferences(Constants.PREFERENCES
                , Context.MODE_PRIVATE);
        String selectedMode = sharedPref.getString("listTransportPreference", "walking");

        if(sharedPrefCheck.getBoolean(Constants.CHECK_ROUTE, false)){
            if(new LocationDatasController(context).isTypeRouteExists(addressOrigen, destAdress, selectedMode)){
                String jsonRoute = new LocationDatasController(context).getRoute(addressOrigen, destAdress, selectedMode);
                new ParserTask().execute(jsonRoute);

            } else {
                new Geocoding().execute(destAdress);
            }
        } else{
            LatLng origin = new LatLng(latOri, longOri);
            mMap.clear();
            addMarker(origin, "Você");
        }
    }

    //método pra add marcador pra o corrente e o destino
    public void addMarker(LatLng latLng, String title){
        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng);
        marker.title(title);
        mMap.addMarker(marker);
    }

    //método chamado quando o user muda a sua localização
      @Override
      public void onLocationChanged(Location location) {
          Log.d("Maps", "Posição alterada");
          latOri = location.getLatitude();
          longOri = location.getLongitude();
          locationA = location;

            //checa se há rota em curso, se n, apenas mar o current location
              if (sharedPrefCheck.getBoolean(Constants.CHECK_ROUTE, false)) {
                  Location temp = new Location(LocationManager.GPS_PROVIDER);
                  temp.setLatitude(latDest);
                  temp.setLongitude(longiDest);
                  //verifica se chegou ao destino ao destino
                  if(location.distanceTo(temp) < Constants.DISTANCE_DESTINATION){
                      //horário de chagada
                      //hora
                      SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
                      Date hora = Calendar.getInstance().getTime();
                      tempo_chegada = sd.format(hora);

                      edt.putBoolean(Constants.CHECK_ROUTE, false);
                      edt.apply();

                      //enviar relatório pra o usuário saber o que aconteceu durante a rota
                      //Relatorio relatorio = new Relatorio(origem, destino, tempo_saida, tempo_chegada,MainActivity.emitir_alerta, MainActivity.localizacao_alerta, MainActivity.horario_do_alerta, MainActivity.pessoas_receberam_alerta, MainActivity.data_do_alerta);
                      //new SendMail().sendRelatorio(getActivity(), new MainActivity(), new LatLng(Maps.latOri, Maps.longOri), relatorio);

                      //emitir alerta
                      new Alert(mContext).destinationHasBeenReachedAlert();

                  } else{
                      sharedPref = mContext.getSharedPreferences(Constants.PREFERENCES
                              , Context.MODE_PRIVATE);
                      String selectedMode = sharedPref.getString("listTransportPreference", "walking");
                      //Verifica se essa rota que o usuário está tentando fazer já exist no banco de dados
                      if(new LocationDatasController(getActivity()).isTypeRouteExists(addressOrigen, destAdress, selectedMode)){
                          String jsonRoute = new LocationDatasController(getActivity()).getRoute(addressOrigen, destAdress, selectedMode);
                          new ParserTask().execute(jsonRoute);

                      } else {
                          new Geocoding().execute(destAdress);
                      }
                  }


              }
              else{
                  //apenas marca a nova localização
                      mMap.clear();
                      latOri = location.getLatitude();
                      longOri = location.getLongitude();
                      new ReverseGeocodingTask(getActivity()).execute(new LatLng(latOri,longOri));

                      LatLng newLocation = new LatLng(latOri,longOri);
                      addMarker(newLocation, "Você");
                      //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 16));

              }

              //marcar ou não os lugares perigosos
          if(sharedPref.getBoolean("ativarLPPreference", false)){
              markerDangerousLocations(getActivity(), mMap);
          }


      }

      @Override
      public void onStatusChanged(String provider, int status, Bundle extras) {

      }

      @Override
      public void onProviderEnabled(String provider) {

      }

      @Override
      public void onProviderDisabled(String provider) {

      }


      //encontra o adress por meio das coordenadas
    //usa isso aqui pra jogar lá no text view do set Destination, informando ao user o seu endereço atual

    private class ReverseGeocodingTask extends AsyncTask<LatLng, Void, List<Address>>{
        Context mContext;
        public ReverseGeocodingTask(Context context){
            super();
            mContext = context;
        }

        @Override
        protected List<Address> doInBackground(LatLng... params) {
            //usamos Geocoding pra isso
            Geocoder geocoder = new Geocoder(mContext);
            //pega os parâmetros passados
            double latitude = params[0].latitude;
            double longitude = params[0].longitude;
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latitude, longitude,1);
               // Thread.sleep(500);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addressText) {
            String addressT="";
            //parser do resultado pra pegar a coordenada
            if(addressText != null && addressText.size() > 0 ){
                Address address = addressText.get(0);
                addressT = String.format("%s, %s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getLocality(),
                        address.getCountryName());
            }

            addressOrigen = addressT;
            origem = addressOrigen;

        }
    }


        //responsável por pegar as coordenadas de um adress
    private class Geocoding extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(mContext);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //inicia o dialog
            dialog.setMessage("Por favor, aguarde...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String...params) {
            //pega o adress vinda do Destination (ou string do BD), monta a URL e obtém a coordenada
            //esse replace all tira todos os espaços
            destino = params[0];
            response = getLatLongByURL("https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(params[0].replaceAll(" ", "+")));
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                //obtém a coordenada do json
                longitude = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lng");

                latitude = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lat");

                LatLng destinationLocation = new LatLng(latitude, longitude);

                latDest = latitude;
                longiDest = longitude;

                LatLng origin = new LatLng(latOri, longOri);
                // Pegando a Google Directions API
                String url = getUrl(origin, destinationLocation);
                FetchUrl FetchUrl = new FetchUrl();

                //uma vez que temos as coordenadas do destino, podemos chamas a Asynctask responsável por pegar a rota
                FetchUrl.execute(url);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (dialog.isShowing()) {
                //cancela o dialog
                dialog.dismiss();
            }
        }
    }
    //método que pega o arquivo que contém a coordenada do nome do lugar
    //retorna a string pra fazer o parser nele e obter a coordenada
    public String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    //une as informações pra formar a url que é usada pra obter a rota

    private String getUrl(LatLng origin, LatLng dest) {
        sharedPref = mContext.getSharedPreferences(Constants.PREFERENCES
                , Context.MODE_PRIVATE);

        // origem da rota
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // destino da rota
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor
        String sensor = "sensor=false";
        //mode
        String mode = "mode="+ sharedPref.getString("listTransportPreference", "walking");

        //região
        String region = "region=br";

        // montando os parâmetros
        String parameters = str_origin + "&" + str_dest + "&"+region+ "&" + mode + "&" + sensor;

        // formato de saída
        String output = "json";

        // url a ser retornada
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    //responsável por fazer o download
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();

            // estabelecendo conexão
            urlConnection.connect();

            // leitura de dados do url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (Exception e) {
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

        //responsável por fazer o download do arquivo com a rota
    private class FetchUrl extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(mContext);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //inicia e mostra o dialog
            dialog.setMessage("Por favor, aguarde...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                //faz o download do json que terá os pontos que liga a origem ao destino
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            sharedPref = mContext.getSharedPreferences(Constants.PREFERENCES
                    , Context.MODE_PRIVATE);
            //inserir a rota no BD para poder resgatar depois
            ParserTask parserTask = new ParserTask();
            if(!(new LocationDatasController(mContext).isTypeRouteExists(addressOrigen, destAdress, sharedPref.getString("listTransportPreference", "walking")))){
                new LocationDatasController(mContext).inserRoute(new Route(addressOrigen, destAdress, result, sharedPref.getString("listTransportPreference", "walking")));

            }

            //invoca a Thread que tratará de dar os pontos da rota
            parserTask.execute(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

    //Task para obter os polylinesOptions da rota do user
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            //monsta um objeto json
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                //chama o método parser
                DataParser parser = new DataParser();

                // Fazer parsing dos dados com o método parse
                routes = parser.parse(jObject);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                //pegar todos os pontos da polyline
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }
                if(locationA != null){
                    //trata de atualizar a rota do usuário de acordo com a sua posição
                  points = new DistanceCalc(points, locationA).getRouteUpdated();
                    if(points.size() >0) {
                        latDest = points.get(points.size() - 1).latitude;
                        longiDest = points.get(points.size() - 1).longitude;
                    }
                    //checa se a menor distância é menor que MIN_DISTANCE, ou seja, se
                    if(new DistanceCalc(points, locationA).getMinDistance() > Constants.MIN_DISTANCE){
                        if(!alertShowed) {
                            new Alert(mContext).enableAlertRoute(sharedPref.getInt("timeAlertPreference", 30));
                            alertShowed = true;
                        }
                    }
                }

                //adicionando todos os pontos e setando algumas configurações
                lineOptions.addAll(points);
                lineOptions.width(15);
                lineOptions.color(Color.parseColor("#0080ff"));
            }

            // desenha a rota no Google Map usando tds os pontos
            if(lineOptions != null) {
                mMap.clear();
                LatLng destinationLocation = new LatLng(latDest, longiDest);
                LatLng origin = new LatLng(latOri, longOri);

                mMap.addPolyline(lineOptions);
                addMarker(origin, "Você");

                addMarker(destinationLocation, "Destino");

                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latOri, longOri), 16));

            }
            else {
            }

        }
    }



  }

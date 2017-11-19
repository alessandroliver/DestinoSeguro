package com.example.alessandro.destinoseguro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//usado para compatibilidade em versões de Android mais antigas
public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private Polyline line;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onPause() {
        try {
            this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        super.onPause();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //informações referentes ao mapa em si
        mMap = googleMap;

        //registrar evento de clique do mapa
        mMap.setOnMapClickListener(this);

        //botao de zoom do mapa
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // coordenadas para a cidade de Sydney
        LatLng dest = new LatLng(-8.062976, -34.895467);

        //marcar no mapa a posiçao desejada
        MarkerOptions marker = new MarkerOptions();
        marker.position(dest);
        marker.title("Marker in Sydney");

        mMap.addMarker(marker);

        //mover a camera ate a posiçao que foi marcada


        //  How to get these values in another class
        //  Class B
        Intent i= getActivity().getIntent();
       // i.getDoubleExtra("latitude",0);
        //i.getDoubleExtra("longitude",0);


        // String urlTopass = makeURL(-8.060679, -34.870908, -8.062976, -34.895467); // lat origem, lon origem, lat destino, lon destino

        LatLng origin = new LatLng(-8.060679, -34.870908);
        LatLng destino = new LatLng(i.getDoubleExtra("latitude",0), i.getDoubleExtra("longitude",0));

        String url = getUrl(origin,destino,"walking");
        new FetchUrl().execute(url);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(dest));
    }



    @Override
    //recuperar a coordenada que cliquei no mapa
    public void onMapClick(LatLng latLng) {
        //exibir a coordenada
        Toast.makeText(getContext(), "Coordenadas: " + latLng.toString(), Toast.LENGTH_SHORT).show();

    }

    private class connectAsyncTask extends AsyncTask<Void, Void, String> {
        String url;

        connectAsyncTask(String urlPass) {
            url = urlPass;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            JSONParser jParser = new JSONParser();
            String json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                drawPath(result);
            }
        }
    }

    public void drawPath(String result) {
        if (line != null) {
            mMap.clear();
        }

        try {
            // Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);

            for (int z = 0; z < list.size() - 1; z++) {
                LatLng src = list.get(z);
                LatLng dest = list.get(z + 1);
                line = mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude, dest.longitude))
                        .width(5).color(Color.BLUE).geodesic(true));
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    //une as informações pra formar a url

    private String getUrl(LatLng origin, LatLng dest, String mode2) {
        // origem da rota
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // destino da rota
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor
        String sensor = "sensor=false";
        //mode
        String mode = "mode=" + mode2;
        //região
        String region = "region=br";
        // montando os parâmetros
        String parameters = str_origin + "&" + str_dest + "&" + region + "&" + mode + "&" + sensor;
        // formato de saída
        String output = "json";
        // url a ser retornada
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;

            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
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
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    //responsável por fazer o download do arquivo com a rota
    private class FetchUrl extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
            //inserir a rota no BD
            ParserTask parserTask = new ParserTask();


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

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Fazer parsing dos dados
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
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

                //adicionando todos os pontos e setando algumas configurações
                lineOptions.addAll(points);
                lineOptions.width(15);
                lineOptions.color(Color.parseColor("#0080ff"));

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // desenha a rota no Google Map usando tds os pontos
            if(lineOptions != null) {
                mMap.clear();
                mMap.addPolyline(lineOptions);
                //addMarker(origin, "Você");

                //addMarker(destinationLocation, "Destino");

              //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latOri, longOri), 16));

            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }

        }
    }

}

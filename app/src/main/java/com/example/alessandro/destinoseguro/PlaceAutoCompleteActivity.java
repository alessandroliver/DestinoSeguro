package com.example.alessandro.destinoseguro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;


public class PlaceAutoCompleteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_auto_complete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: obter informações sobre o local selecionado.
                Log.i("place", "Place: " + place.getName());
                String nm =  place.getName().toString();

                new DataLongOperationAsynchTask().execute(nm);

            }

            @Override
            public void onError(Status status) {
                // TODO: Solucionar o erro.
                Log.i("place", "Ocorreu um erro: " + status);
            }
        });
    }

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

    //responsável por pegar as coordenadas de um adress
    private class DataLongOperationAsynchTask extends AsyncTask<String, Void, String> {
        //ProgressDialog dialog = new ProgressDialog(getApplication());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //dialog.setMessage("Por favor, aguarde...");
            //dialog.setCanceledOnTouchOutside(false);
            //dialog.show();
        }

        @Override
        protected String doInBackground(String...params) {
            //pega o adress vinda do Destination (ou string do BD), monta a URL e obtém a coordenada
            String response = getLatLongByURL("https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(params[0].replaceAll(" ", "+"))+"&key=AIzaSyBrGJsAdEPZD9L4kUCdu2HCgOX1VMDTmkg");
            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                //obtém a coordenada do json
                double longitude = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lng");

                double latitude = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lat");

                Log.d("latitude", "" + latitude);
                Log.d("longitude", "" + longitude);

                //  How to send value using intent from one class to another class
                //  class A(which will send data)
                Intent theIntent = new Intent(getApplication(), MapsFragment.class);
                theIntent.putExtra("latitude", latitude);
                theIntent.putExtra("longitude", longitude);
                startActivity(theIntent);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}

package com.if1001exemplo1.tccvaseguro.old;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.if1001exemplo1.tccvaseguro.R;


public class PlaceAutoCompleteActivity extends AppCompatActivity {
    //gerenciar os fragmentos da aplicaçao
    private FragmentManager fragmentManager;

    public static double lat= 0.0;
    public static double lng= 0.0;
    private double latOrigen = 0;
    private double longiOrigen = 0;
    private double latDestination = 0;
    private double longiDestination = 0;
    private String adressOrigen = null;
    private String casa = "Minha casa";
    private String type;
    SharedPreferences sharedPrefCheck;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_auto_complete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //pega o fragment
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        //pega p modo que o usuário quer
        SharedPreferences sharedPrefP = getSharedPreferences(Constants.PREFERENCES
                , Context.MODE_PRIVATE);
        type = sharedPrefP.getString("listTransportPreference", "walking");

        sharedPrefCheck = getSharedPreferences(Constants.CHECK, Context.MODE_PRIVATE);
        editor = sharedPrefCheck.edit();

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: obter informações sobre o local selecionado.
                Log.i("place", "Place: " + place.getName());
                //pega o nome do lugar que o user selecionou
                String nm =  place.getAddress().toString();

                //pega os extras
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    latOrigen = extras.getDouble("latOrigenExtra", 0);
                    longiOrigen = extras.getDouble("longOrigenExtra", 0);
                    adressOrigen = extras.getString("adressOrigenExtra", "");
                }

                //diz que tá tendo rota pq o user selecionou o destino
                editor.putBoolean(Constants.CHECK_ROUTE, true);
                editor.apply();
                Intent intent = new Intent(PlaceAutoCompleteActivity.this, MainActivity.class);
                intent.putExtra("destinationName", place.getName().toString() +","+ nm );
                intent.putExtra("latOrigenExtra", latOrigen);
                intent.putExtra("longOrigenExtra", longiOrigen);
                intent.putExtra("mode", type);
                startActivity(intent);

                //new DataLongOperationAsynchTask().execute(nm);

            }

            @Override
            public void onError(Status status) {
                // TODO: Solucionar o erro.
                Log.i("place", "Ocorreu um erro: " + status);
            }
        });
    }


}

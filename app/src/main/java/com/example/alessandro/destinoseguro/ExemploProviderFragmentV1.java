package com.example.alessandro.destinoseguro;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
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

//usado para compatibilidade em versões de Android mais antigas
public class ExemploProviderFragmentV1 extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final String TAG = "ExemploProvFragmentV1";

    private GoogleMap mMap;
    //localizar o provider
    private LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
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
    try {

        //recuperar a referencia do objeto
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //realizar buscas especificas
        Criteria criteria = new Criteria();

        //tentar localizar melhor provider ativo no momento
        String provider = locationManager.getBestProvider(criteria, true);

        //exibir o nome do provider
        Toast.makeText(getActivity(), "Provider: " + provider, Toast.LENGTH_LONG).show();

        //informações referentes ao mapa em si
        mMap = googleMap;

        //registrar evento de clique do mapa
        mMap.setOnMapClickListener(this);
        //ativar uma opçao para que possamos buscar uma localizaçao
        mMap.setMyLocationEnabled(true);
        //botao de zoom do mapa
        mMap.getUiSettings().setZoomControlsEnabled(true);



    } catch (SecurityException ex)
    {
        Log.e(TAG, "Error", ex);
    }


        // coordenadas para a cidade de Sydney
        LatLng sydney = new LatLng(-33.87365, 151.20689);

        //marcar no mapa a posiçao desejada
        MarkerOptions marker = new MarkerOptions();
        marker.position(sydney);
        marker.title("Marker in Sydney");

        mMap.addMarker(marker);

        //mover a camera ate a posiçao que foi marcada
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    //recuperar a coordenada que cliquei no mapa
    public void onMapClick(LatLng latLng) {
        //exibir a coordenada
        Toast.makeText(getContext(), "Coordenadas: " + latLng.toString(), Toast.LENGTH_SHORT).show();

    }
}

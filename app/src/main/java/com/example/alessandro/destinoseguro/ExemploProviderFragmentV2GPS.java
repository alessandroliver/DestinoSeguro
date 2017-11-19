package com.example.alessandro.destinoseguro;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
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
public class ExemploProviderFragmentV2GPS extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, LocationListener {

    private static final String TAG = "ExProvFragmentV2GPS";

    private GoogleMap mMap;
    //localizar o provider
    private LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Ativa o GPS
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //especificar que quero utilizar gps
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    public void onPause(){
        super.onPause();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(this);
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

            //expecificar o provider que quero

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

    @Override
    //quando muda a localizaçao
    public void onLocationChanged(Location location) {
        Toast.makeText(getActivity(), "Posição alterada.", Toast.LENGTH_LONG).show();

        // coordenadas para nova localizaçao
        LatLng novaLocalizacao = new LatLng(location.getLatitude(), location.getLongitude());

        //marcar no mapa a posiçao desejada
        MarkerOptions marker = new MarkerOptions();
        marker.position(novaLocalizacao);
        marker.title("Nova Localizacao");
        mMap.clear();
        mMap.addMarker(marker);

        //mover a camera ate a posiçao que foi marcada
        mMap.moveCamera(CameraUpdateFactory.newLatLng(novaLocalizacao));
    }

    @Override
    //quando muda o status
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(getActivity(), "O status do Provider foi alterado.", Toast.LENGTH_LONG).show();
    }

    @Override
    //quando o provider é habilitado
    public void onProviderEnabled(String provider) {
        Toast.makeText(getActivity(), "Provider Habilitado.", Toast.LENGTH_LONG).show();
    }

    @Override
    //quando o provider é desabilitado
    public void onProviderDisabled(String provider) {
        Toast.makeText(getActivity(), "Provider Desabilitado.", Toast.LENGTH_LONG).show();
    }
}

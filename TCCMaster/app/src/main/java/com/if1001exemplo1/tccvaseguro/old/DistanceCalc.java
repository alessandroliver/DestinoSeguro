package com.if1001exemplo1.tccvaseguro.old;


import android.location.*;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;



//classe pra atualizar a rota de acordo com a posição do user e checar se está no caminho

public class DistanceCalc {

    private ArrayList<LatLng> polylineOptions;
    private android.location.Location location;
    private double distance = 0;

    public DistanceCalc(ArrayList<LatLng> polylineOptions, android.location.Location location){
        this.polylineOptions = polylineOptions;
        this.location = location;
    }

    //dará um array ordernado de distâncias até a current location
    //n pega min aqui? ou collection? api >24 :(

    public double[] distanceSort(){
        double[] result = new double[polylineOptions.size()];
        for(int i = 0; i< polylineOptions.size() ; i++){
            result[i] = getDistance(polylineOptions.get(i));
        }

        Arrays.sort(result);
        return result;
    }

    //retorna o ponto da rota mais próximo da posição atual do usuário
    public int getMinLatLng(){
        int result = 0;
        double minDistance = distanceSort()[0];
        distance = minDistance;
        for(int i = 0; i < polylineOptions.size(); i++){
            boolean find = false;
            if(getDistance(polylineOptions.get(i)) == minDistance){
                if(!find){
                    result = i;
                    find = true;
                }
            }
        }
        return result;
    }

    //apaga o caminho percorrido - ou seja, apaga as posições que tiver para atrás da menor distância entre o usuário e a rota

    public ArrayList<LatLng> getRouteUpdated(){
        for(int i = getMinLatLng()-1; i >=0 ; i--){
            polylineOptions.remove(i);
        }
        return polylineOptions;
    }

    public float getDistance(LatLng point){
        //usa o GPS
        android.location.Location temp = new android.location.Location(LocationManager.GPS_PROVIDER);
        temp.setLatitude(point.latitude);
        temp.setLongitude(point.longitude);
        //distanceTo dá a distância de um ponto a outro, nesse caso, ele dá a distância do usuário para cada ponto da rota
        return (int) (location.distanceTo(temp));
    }


    public double getMinDistance(){
        return distanceSort()[0];
    }


}

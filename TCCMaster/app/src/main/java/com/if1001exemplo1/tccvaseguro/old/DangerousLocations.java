package com.if1001exemplo1.tccvaseguro.old;


public class DangerousLocations {
    private double lat;
    private double lng;

    public DangerousLocations(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}

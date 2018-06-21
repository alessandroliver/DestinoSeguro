package com.if1001exemplo1.tccvaseguro.old;


public class Route {
    private String originAdress;
    private String destinationAdress;
    private String polylineOptions;
    private String type;

    public Route(String originAdress, String destinationAdress, String polylineOptions, String type){
        this.originAdress = originAdress;
        this.destinationAdress = destinationAdress;
        this.polylineOptions = polylineOptions;
        this.type = type;
    }

    public String getOriginAdress() {
        return originAdress;
    }

    public String getDestinationAdress() {
        return destinationAdress;
    }

    public String getRoute() {
        return this.polylineOptions;
    }

    public String getType() {
        return type;
    }

    public void setDestinationAdress(String destinationAdress) {
        this.destinationAdress = destinationAdress;
    }

    public void setOriginAdress(String originAdress) {
        this.originAdress = originAdress;
    }

    public void setRoute(String route) {
        this.polylineOptions = route;
    }

    public void setType(String type) {
        this.type = type;
    }
}

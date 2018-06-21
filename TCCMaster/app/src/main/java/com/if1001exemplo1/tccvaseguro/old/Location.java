package com.if1001exemplo1.tccvaseguro.old;



public class Location {
    private Double lat;
    private Double longi;
    private String adressName;
    private String id;

   public Location(Double lat, Double longi, String adressName, String id){
        this.lat= lat;
        this.longi = longi;
        this.adressName = adressName;
        this.id = id;
    }

    public Double getLat() {
        return this.lat;
    }

    public Double getLongi() {
        return this.longi;
    }

    public String getAdressName() {
        return this.adressName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

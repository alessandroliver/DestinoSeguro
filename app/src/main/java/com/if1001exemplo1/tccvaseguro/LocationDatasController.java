package com.if1001exemplo1.tccvaseguro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import static com.if1001exemplo1.tccvaseguro.LocationDatasOpenHelper.TABLE_COORDINATES_ALERT;
import static com.if1001exemplo1.tccvaseguro.LocationDatasOpenHelper.TABLE_ROUTES;


//classe pra manipulação do BD

public class LocationDatasController {
    private SQLiteDatabase db;
    private LocationDatasOpenHelper ldDB;

    public LocationDatasController(Context context){
        ldDB = new LocationDatasOpenHelper(context);
    }


    //apagar os locais perigosos
    public void clearAllDangerousLocations() {

        ldDB.getWritableDatabase().delete(TABLE_COORDINATES_ALERT, null, null);

    }

    public void clearAllRoutes(){
        ldDB.getWritableDatabase().delete(TABLE_ROUTES, null, null);
    }

    public String insertLocationCoordinates(Location location){
        long check =0;
        SQLiteDatabase db = ldDB.getWritableDatabase();
        ContentValues values = new ContentValues();

            values.put(LocationDatasOpenHelper.LAT, location.getLat());
            values.put(LocationDatasOpenHelper.LONG, location.getLongi());
            values.put(LocationDatasOpenHelper.STREET_ADRESS, location.getAdressName());
            values.put(LocationDatasOpenHelper.ID_ADRESS, location.getId());


        check = db.insert(LocationDatasOpenHelper.TABLE_COORDINATES, null, values);

        db.close();

        if(check == -1){
            return "Error";
        } else{
            return "DB created";
        }
    }
//insere LPs
    public String dangerousLocationsInsert(DangerousLocations dangerousLocations){
        long check =0;
        SQLiteDatabase db = ldDB.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LocationDatasOpenHelper.LAT_ALERT, dangerousLocations.getLat());
        values.put(LocationDatasOpenHelper.LONG_ALERT, dangerousLocations.getLng());

        check = db.insert(TABLE_COORDINATES_ALERT, null, values);

        db.close();

        if(check == -1){
            return "Error";
        } else{
            return "DB created";
        }
    }

    //insere a rota
    public String inserRoute(Route route){
        long check =0;
        SQLiteDatabase db = ldDB.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LocationDatasOpenHelper.ORIGIN_ADRESS, route.getOriginAdress());
        values.put(LocationDatasOpenHelper.DESTINATION_ADRESS, route.getDestinationAdress());
        values.put(LocationDatasOpenHelper.ROUTE, route.getRoute());
        values.put(LocationDatasOpenHelper.TYPE, route.getType());


        check = db.insert(TABLE_ROUTES, null, values);

        db.close();

        if(check == -1){
            return "Error";
        } else{
            return "DB created";
        }
    }

    public Cursor loadCoordinatesAlarm(Context context){
        SQLiteDatabase db;
        LocationDatasOpenHelper ldDB;
        ldDB = new LocationDatasOpenHelper(context);

        Cursor cursor;
        db = ldDB.getReadableDatabase();

        cursor = db.query(TABLE_COORDINATES_ALERT, ldDB.COLUMNS_COORDINATES_ALERT, null, null, null, null, null, null);

        if(cursor!=null && cursor.getCount() > 0){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
//pra poder marcar os locais perigosos no mapa
    public List<DangerousLocations> getAllLocationAlarm(Context context) {
        List<DangerousLocations> locations = new ArrayList<DangerousLocations>();
        Cursor cursor = loadCoordinatesAlarm(context);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DangerousLocations location = new DangerousLocations(cursor.getDouble(0),cursor.getDouble(1));

                // Adding contact to list
                locations.add(location);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return contact list
        return locations;
    }


    public boolean isCoordinatesExists(Double lat, Double longi) {
        SQLiteDatabase db = ldDB.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT 1 FROM " + ldDB.TABLE_COORDINATES
                + " WHERE lat = '" + lat + "'" + " AND long = '" + longi + "'", new String[] {});
        boolean exists = (cursor.getCount() > 0);
        db.close();
        return exists;
    }

    public boolean isDestinationAdressExists(String adress) {
        SQLiteDatabase db = ldDB.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT 1 FROM " + ldDB.TABLE_ROUTES
                + " WHERE destination_adress = '" + adress + "'", new String[] {});
        boolean exists = (cursor.getCount() > 0);
        db.close();
        return exists;
    }

    public boolean isRouteExists(String originAdress, String destinationAdress) {
        SQLiteDatabase db = ldDB.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ROUTES
                + " WHERE origin_adress = '" + originAdress + "'"  + " AND destination_adress = '" + destinationAdress + "'",
                new String[] {});
        boolean exists = (cursor.getCount() > 0);
        db.close();
        return exists;
    }

    //a rota feita nesse modo existe
    public boolean isTypeRouteExists(String originAdress, String destinationAdress, String type ) {
        SQLiteDatabase db = ldDB.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ROUTES
                        + " WHERE origin_adress = '" + originAdress + "'"  + " AND destination_adress = '" + destinationAdress + "'"
                        + " AND type = '" + type + "'",
                new String[] {});
        boolean exists = (cursor.getCount() > 0);
        db.close();
        return exists;
    }

    public LatLng getLocation(String adress) {
        SQLiteDatabase db = ldDB.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ldDB.TABLE_COORDINATES
                + " WHERE streetAdress = '" + adress + "'", new String[] {});
        db.close();
        LatLng coordinates = null;
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            coordinates = new LatLng(cursor.getDouble(0), cursor.getDouble(1));
        }
        cursor.close();

        return coordinates;
    }

    public String getAdress(Double lat, Double longi) {
        SQLiteDatabase db = ldDB.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ldDB.TABLE_COORDINATES
                + " WHERE lat = '" + lat + "'" + " AND long = '" + longi + "'", new String[] {});

        String adress ="";
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            adress = cursor.getString(2);
        }
        cursor.close();
        db.close();
        return adress;
    }
//pegar a rota(texto puro - string do json com a rota
    public String getRoute(String originAdress, String destinationAdress, String type) {
        SQLiteDatabase db = ldDB.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ROUTES
                        + " WHERE origin_adress = '" + originAdress + "'"  + " AND destination_adress = '" + destinationAdress + "'"
                        + " AND type = '" + type + "'",
                new String[] {});        String route = "";
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            route = cursor.getString(3);
        }
        cursor.close();
        db.close();
        return route;
    }
//método pro reload
    public List<String> getLastDestination() {
        SQLiteDatabase db = ldDB.getReadableDatabase();
        List<String> destinations = new ArrayList<String>();

        Cursor cursor = db.query(TABLE_ROUTES, ldDB.COLUMNS_ROUTE, null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

        }

        if (cursor.moveToFirst()) {
            do {
                destinations.add(cursor.getString(1));

            } while (cursor.moveToNext());

        }
            cursor.close();
            db.close();
            return destinations;

    }
}

package com.example.ftn.showbook.database;

import java.util.Iterator;
import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class FacilityDB {
    public static final String TABLE_NAME = "facilities";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_LATITUDE= "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_BACKID = "backId";

    private Integer id;
    private String name;
    private String type;
    private String address;
    private String location;
    private String latitude;
    private String longitude;
    private String backId;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_TYPE + " TEXT,"
                    + COLUMN_ADDRESS + " TEXT,"
                    + COLUMN_LOCATION + " TEXT,"
                    + COLUMN_LATITUDE + " TEXT,"
                    + COLUMN_LONGITUDE + " TEXT,"
                    + COLUMN_BACKID + " TEXT "
                    + ")";

    FacilityDB(){}

    public FacilityDB(Integer id, String name, String type, String address, String location, String latitude, String longitude, String backId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.backId = backId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBackId() {
        return backId;
    }

    public void setBackId(String backId) {
        this.backId = backId;
    }

    public static double haversineDistance(double lat1, double lon1, double lat2, double lon2)
    {
        double R = 6371;
        double dLat = (lat2 - lat1) * PI / 180;
        double dLon = (lon2 - lon1) * PI / 180;
        lat1 = lat1 * PI / 180;
        lat2 = lat2 * PI / 180;

        double a = sin(dLat/2) * sin(dLat/2) + sin(dLon/2) * sin(dLon/2) * cos(lat1) * cos(lat2);
        double c = 2 * atan2(sqrt(a), sqrt(1-a));
        double d = R * c;

        return d;
    }


    public static List<FacilityDB> filterByDistanceAndType(List<FacilityDB> toFilter, double latitude, double longitude, double distance, String type)
    {
        System.out.println("uslo u metodu");
        Iterator<FacilityDB> it = toFilter.iterator();
        if(!type.equals("ALL")){

            while(it.hasNext())
            {
                FacilityDB revob = it.next();
                if(haversineDistance(latitude, longitude,
                        Double.parseDouble(revob.getLatitude()), Double.parseDouble(revob.getLongitude())) > distance ||
                        !revob.getType().equals(type) ) // ako je udaljen vise od distance i u odnosu na tip facilitija
                {
                    it.remove(); // izbaci ga
                }
            }
        }else {
            while(it.hasNext()) {
                FacilityDB revob = it.next();
                if (haversineDistance(latitude, longitude,
                        Double.parseDouble(revob.getLatitude()), Double.parseDouble(revob.getLongitude())) > distance) // ako je udaljen vise od distance
                {
                    it.remove(); // izbaci ga
                }
            }

        }

        return toFilter;
    }
}

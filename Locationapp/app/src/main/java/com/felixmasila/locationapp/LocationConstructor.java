package com.felixmasila.locationapp;

/**
 * Created by felixmasila on 11/15/16.
 */

public class LocationConstructor {

    int geolocation_id;
    String location_name, longitude, latitude;

    public int getId()
    {
        return geolocation_id;
    }
    public void setId(int geolocation_id)
    {
        this.geolocation_id = geolocation_id;
    }
    public String getLongitude()
    {
        return longitude;
    }
    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }
    public String getLatitude()
    {
        return latitude;
    }
    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }
    public String getName()
    {
        return location_name;
    }
    public void setName(String Name)
    {
        this.location_name = location_name;
    }

}

package com.kandktech.gpyes.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GpsDatamodel {
    @SerializedName("user_id")
    @Expose
    private Integer user_id;

    @SerializedName("lat")
    @Expose
    private double lat;

    @SerializedName("lon")
    @Expose
    private double lon;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("date")
    @Expose
    private String date;

    public GpsDatamodel(Integer user_id, double lat, double lon, String time, String date) {
        this.user_id = user_id;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
        this.date = date;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "GpsDatamodel{" +
                "user_id=" + user_id +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

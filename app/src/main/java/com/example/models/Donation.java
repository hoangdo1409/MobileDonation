package com.example.models;

import java.util.List;

public class Donation {
    public int id;
    public int amount;
    public String method;
    public String paymenttype;
    public int upvotes;
    public double latitude;
    public double longitude;

    /*public Donation (int amount, String method)
    {
        this.amount = amount;
        this.method = method;
    }*/

    public Donation(int amount, String method, int upvotes, double lat, double lng)
    {
        this.amount = amount;
        this.paymenttype = method;
        this.upvotes = upvotes;
        this.latitude = lat;
        this.longitude = lng;
    }

    public Donation ()
    {
        this.amount = 0;
        this.method = "";
        this.upvotes = 0;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }
    public String toString()
    {
        return id + ", " + amount + ", " + paymenttype + ", " + upvotes + ", " + latitude + ", " + longitude;
    }
}

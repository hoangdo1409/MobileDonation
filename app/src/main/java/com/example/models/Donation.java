package com.example.models;

public class Donation
{
    public int id;
    public int amount;
    public String method;
    public String paymenttype;
    public int upvotes;

    /*public Donation (int amount, String method)
    {
        this.amount = amount;
        this.method = method;
    }*/

    public Donation (int amount, String method, int upvotes)
    {
        this.amount = amount;
        this.paymenttype = method;
        this.upvotes = upvotes;
    }

    public Donation ()
    {
        this.amount = 0;
        this.method = "";
        this.upvotes = 0;
    }
    public String toString()
    {
        return id + ", " + amount + ", " + method;
    }
}

package com.bazaraki.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Advertisement {

    private int id;
    private String name;
    private String location;
    private double price;
    private int fotoCount;
    private Calendar publishedDate;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getPrice() {
        return price;
    }

    public int getFotoCount() {
        return fotoCount;
    }

    public Calendar getPublishedDate() {
        return publishedDate;
    }

    public Advertisement withId(int id) {
        this.id = id;
        return this;
    }

    public Advertisement withName(String name) {
        this.name = name;
        return this;
    }

    public Advertisement withLocation(String location) {
        this.location = location;
        return this;
    }

    public Advertisement withPrice(double price) {
        this.price = price;
        return this;
    }

    public Advertisement withFotoCount(int fotoCount) {
        this.fotoCount = fotoCount;
        return this;
    }

    public Advertisement withPublishedDate(Calendar publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return "'" + getName() + "' id='" + getId() + "' foto count ='" + getFotoCount() +
                                 "' price ='" + getPrice() + "' publishedDate ='" + 
                                 formatter.format(getPublishedDate().getTime()) + "'";
    }

}

package com.example.dhagrafis.models;

public class Location {
    private final String nameLocation;
    private final String priceLocation;

    public Location(String nameLocation, String priceLocation) {
        this.nameLocation = nameLocation;
        this.priceLocation = priceLocation;
    }

    public String getNameLocation() {
        return nameLocation;
    }
    public String getPriceLocation() {
        return priceLocation;
    }
}

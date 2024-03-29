package com.example.dhagrafis.models;

public class PaketList {
    public String iconPkt;
    public String name;
    public String description;
    public int price;
    public String category;

    public PaketList() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public String getIconPkt() {
        return iconPkt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setIconPkt(String iconPkt) {
        this.iconPkt = iconPkt;
    }
}

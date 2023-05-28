package com.example.dhagrafis.controllers;

import com.example.dhagrafis.models.Order;
import com.example.dhagrafis.models.PaketList;

import java.util.ArrayList;

public class OrderController {
    ArrayList<Order> orders = new ArrayList<>();

    public ArrayList<Order> loadOrder() {
        Order placeHolder = new Order();
        placeHolder.nameOrder = "Yudha";
        placeHolder.priceOrder = "1000.000";

        orders.add(placeHolder);
        return orders;
    }

}

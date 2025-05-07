package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Table {

    private int x;
    private int y;
    private final int diameter;
    private final int number;
    private ArrayList<Subscriber> subscribers;
    private Menu menu;
    private int guestAmount;

    public Table(int x, int y, int diameter, int number, Menu menu){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.number = number;
        this.subscribers = new ArrayList<>();
        this.menu = menu;


    }

    public void subscribe(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber){
        subscribers.remove(subscriber);
    }

    public void order(int orderAmount){
        for (Subscriber s: subscribers){


            // Gives tables x and y-position, table number, menu items based on how many times the waiter has gone to this table and how many guests at the table
            s.retrieveOrder(getX(), getY(), getNumber(), bunchOrders(orderAmount)); // Why can I use int and it works with Integer?

        }
    }

    private ArrayList<String> bunchOrders(int orderAmount){
        // Random int to select which menu item to get from the current menu, gets one meal for each guest
        Random foodRandom = new Random();
        ArrayList<String> foodOrders = new ArrayList<>();
        for (int i = 0; i < guestAmount; i++) {
            int foodInt = foodRandom.nextInt(2);
            foodOrders.add(this.menu.getMenuItems().get(orderAmount).get(foodInt));
        }
        return foodOrders;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getDiameter() {
        return this.diameter;
    }

    public int getNumber(){return this.number;}

    public int getGuestAmount(){return guestAmount;}






}

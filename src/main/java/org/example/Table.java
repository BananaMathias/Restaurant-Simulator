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
    private ArrayList<String> appetizers;
    private ArrayList<String> mainCourses;
    private ArrayList<String> desserts;
    private HashMap<Integer, ArrayList<String>> menuItems;

    public Table(int x, int y, int diameter, int number){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.number = number;
        this.subscribers = new ArrayList<>();

        this.appetizers = new ArrayList<>();
        this.appetizers.add("Mushrooms");
        this.appetizers.add("Eggs");
        this.appetizers.add("Pickles");

        this.mainCourses = new ArrayList<>();
        this.mainCourses.add("Spaghetti carbonara");
        this.mainCourses.add("Meatballs");
        this.mainCourses.add("Joel");

        this.desserts = new ArrayList<>();
        this.desserts.add("Mathias");
        this.desserts.add("Chocolate");
        this.desserts.add("Ice cream");

        this.menuItems = new HashMap<>();
        this.menuItems.put(1, this.appetizers);
        this.menuItems.put(2, this.mainCourses);
        this.menuItems.put(3, this.desserts);

    }

    public void subscribe(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber){
        subscribers.remove(subscriber);
    }

    public void order(int orderAmount){
        for (Subscriber s: subscribers){

            // Random int to select which menu item to get from the current menu
            Random foodRandom = new Random();
            int foodInt = foodRandom.nextInt(2);

            // Gives tables x and y-position, table number, menu item based on how many times the waiter has gone to this table
            s.recieveOrder(getX(), getY(), getNumber(), menuItems.get(orderAmount).get(foodInt)); // Why can I use int and it works with Integer?

        }
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






}

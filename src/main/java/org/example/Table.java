package org.example;

import java.util.ArrayList;
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

    public Table(int x, int y, int diameter, int number){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.number = number;
        this.subscribers = new ArrayList<Subscriber>();
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

    }

    public void subscribe(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber){
        subscribers.remove(subscriber);
    }

    public void order(int tableNumber){
        for (Subscriber s: subscribers){
            Random appetizerRandom = new Random();
            int appetizerInt = appetizerRandom.nextInt(2);

            Random mainCourseRandom = new Random();
            int mainCourseInt = mainCourseRandom.nextInt(2);

            Random dessertRandom = new Random();
            int dessertInt = dessertRandom.nextInt(2);

            s.recieveOrder(getX(), getY(), tableNumber, appetizers.get(appetizerInt), mainCourses.get(mainCourseInt), desserts.get(dessertInt));
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

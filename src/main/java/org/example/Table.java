package org.example;

import java.util.ArrayList;

public class Table {

    private int x;
    private int y;
    private final int diameter;
    private final int number;
    private ArrayList<Subscriber> subscribers;

    public Table(int x, int y, int diameter, int number){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.number = number;
        this.subscribers = new ArrayList<Subscriber>();


    }

    public void subscribe(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber){
        subscribers.remove(subscriber);
    }

    public void order(){
        for (Subscriber s: subscribers){
            s.recieveOrder(getX(), getY());
            System.out.println(3);
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

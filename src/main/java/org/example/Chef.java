package org.example;

import java.awt.*;
import java.util.ArrayList;

public class Chef extends Walker implements Cooking{
    protected enum States {IDLE, GOING_TO_MASTER, GOING_HOME, MAKING_FOOD}
    protected States state;
    protected Color color;
    protected ArrayList<ChefListener> subscribers;

    public Chef(int x, int y){
        super(x, y);

    }

    public void startCooking(ArrayList<String> order){}

    public Color getColor(){
        return this.color;
    }

    public void subscribe(ChefListener subscriber){
        subscribers.add(subscriber);
    }

    public void unsubscribe(ChefListener subscriber){
        subscribers.remove(subscriber);
    }
    // Publisher here
}

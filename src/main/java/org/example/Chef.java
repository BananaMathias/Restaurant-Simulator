package org.example;

import java.awt.*;
import java.util.ArrayList;

public class Chef extends Walker implements Cooking{
    protected enum States {IDLE, GOING_TO_MASTER, GOING_HOME, MAKING_FOOD}
    protected States state = States.IDLE;
    protected Color color;
    protected ArrayList<ChefListener> subscribers;
    protected int ingredients = 10;

    public Chef(int x, int y){
        super(x, y);
        subscribers = new ArrayList<>();

    }

    public void startCooking(ArrayList<String> order){}

    public Color getColor(){
        return this.color;
    }

    public void update(){}

    public void notifyMasterChef(){}

    public void subscribe(ChefListener subscriber){
        subscribers.add(subscriber);
        System.out.println(subscribers.get(0));
    }

    public void unsubscribe(ChefListener subscriber){
        subscribers.remove(subscriber);
    }
    // Publisher here


}

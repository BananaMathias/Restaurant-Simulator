package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Chef extends Walker implements Cooking{
    protected enum States {IDLE, GOING_TO_MASTER, GOING_HOME, DELIVERING_FOOD, MAKING_FOOD}
    protected States state = States.IDLE;
    protected Color color;
    protected ArrayList<ChefListener> masterSubscribers;
    protected ArrayList<ChefListener> prepSubscribers;
    HashMap<Integer, ArrayList<String>> ordersToComplete;
    HashMap<Integer, ArrayList<String>> completedOrders;
    protected int ingredients = 10;

    public Chef(int x, int y){
        super(x, y);
        masterSubscribers = new ArrayList<>();
        prepSubscribers = new ArrayList<>();

    }

    public void startCooking(HashMap<Integer, ArrayList<String>> order){}

    public Color getColor(){
        return this.color;
    }

    public void update(){}

    public void notifyMasterChef(){}

    public void masterSubscribe(ChefListener masterSubscriber){
        masterSubscribers.add(masterSubscriber);
        System.out.println(masterSubscribers.get(0));
    }

    public void masterUnsubscribe(ChefListener masterSubscriber){
        masterSubscribers.remove(masterSubscriber);
    }

    public void prepSubscribe(ChefListener masterSubscriber){
        prepSubscribers.add(masterSubscriber);
    }

    public void prepUnsubscribe(ChefListener masterSubscriber){
        masterSubscribers.remove(masterSubscriber);
    }

    protected ArrayList<String> getOrderArray(){
        Object objectKey = ordersToComplete.keySet().toArray()[0];
        Integer key = (Integer) objectKey;
       
        ArrayList<String> order = ordersToComplete.get(key);
        order.add(key.toString());
        ordersToComplete.remove(key);

        return order;

    }
    // Publisher here

    //FIX SO I CAN GET RETURN KEY SO THAT I CAN USE IT IN GARDEMANGERCHEF CHECK COMMENTS THERE


}

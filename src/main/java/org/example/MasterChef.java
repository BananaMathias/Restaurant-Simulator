package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class MasterChef implements ChefListener {

    private static MasterChef masterChefInstance = null;
    private int diameter;
    private int x;
    private int y;
    private Menu menu;
    private HashMap<Integer, ArrayList<String>> ordersFromTables;
    private HashMap<Integer, ArrayList<String>> ordersToBeDelivered;
    private Cooking gardeMangerChef;
    private Cooking sousChef;
    private Cooking patissierChef;
    private enum States {IS_BUSY, IDLE}
    private States state = States.IDLE;


    private MasterChef(int x, int y, int diameter, Menu menu, Chef gardeMangerChef, Chef sousChef, Chef patissierChef){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.menu = menu;
        this.ordersFromTables = new HashMap<>();
        this.gardeMangerChef = gardeMangerChef;
        this.sousChef = sousChef;
        this.patissierChef = patissierChef;
        ordersToBeDelivered = new HashMap<>();
    }

    public static MasterChef getInstance(int x, int y, int diameter, Menu menu, Chef gardeMangerChef, Chef sousChef, Chef patissierChef) {
        if (masterChefInstance == null) {
            masterChefInstance = new MasterChef(x, y, diameter, menu, gardeMangerChef, sousChef, patissierChef);
        }
        return masterChefInstance;
    }
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getDiameter(){
        return this.diameter;
    }
    // Takes order and puts in order list
    public void takeOrderFromWaiter(HashMap<Integer, ArrayList<String>> orderFromTable){
        ordersFromTables.putAll(orderFromTable);
    }

    public void takeOrderFromChef(HashMap<Integer, ArrayList<String>> completedOrders){
        ordersToBeDelivered.putAll(completedOrders);
    }

    public HashMap<Integer, ArrayList<String>> giveOrderToWaiter(){
        return chooseCompletedOrder();
    }

    private void distributeOrder(){
        if (ordersFromTables.isEmpty()){
            return;
        }

        Cooking chef = null;

        HashMap<Integer, ArrayList<String>> order = chooseOrderFromOrders();
        ArrayList<String> orderArray = getOrderArray(order);

        if (menu.getMenuItems().get(1).contains(orderArray.get(0))){
            chef = this.gardeMangerChef;
            System.out.println("I need the garde manger chef");
        }
        else if (menu.getMenuItems().get(2).contains(orderArray.get(0))){
            chef = this.sousChef;
            System.out.println("I need the sous chef");
        }
        else if (menu.getMenuItems().get(3).contains(orderArray.get(0))){
            chef = this.patissierChef;
            System.out.println("I need the patissier chef");
        }

        state = States.IS_BUSY;
        chef.startCooking(order);

    }
    // I NEED THE ARRAY NOT HASH MAP BUT I STILL NEED THE HASHMAP TO PARSE TO THE CHEF, MIGHT BE ABLE TO USE ORDERSFROMTABLES TO FIND RIGHT ORDER BUT STILL NEED TO GET THE KEY OUT LIKE GARDERMANGERCHEF
    private HashMap<Integer, ArrayList<String>> chooseOrderFromOrders(){
        Object objectKey = ordersFromTables.keySet().toArray()[0];
        Integer key = (Integer) objectKey;
        HashMap<Integer, ArrayList<String>> order = new HashMap<>();
        order.put(key,ordersFromTables.get(key));

        ordersFromTables.remove(key);


        return order;

    }

    private HashMap<Integer, ArrayList<String>> chooseCompletedOrder(){
        Object objectKey = ordersToBeDelivered.keySet().toArray()[0];
        Integer key = (Integer) objectKey;
        HashMap<Integer, ArrayList<String>> order = new HashMap<>();
        order.put(key,ordersToBeDelivered.get(key));

        ordersToBeDelivered.remove(key);


        return order;
    }



    private ArrayList<String> getOrderArray(HashMap<Integer, ArrayList<String>> orderHashmap) {
        Object objectKey = orderHashmap.keySet().toArray()[0];
        Integer key = (Integer) objectKey;

        ArrayList<String> order = orderHashmap.get(key);
        return order;
    }

    @Override
    public void notifyListener() {
        if (isIdle()){
            distributeOrder();
        }
    }


    // Strategy pattern depending on which chef should take the order
    // Observer for each chef. If masterChef gets appetizer, he should notify correct chef to take care of it



    private boolean isIdle(){return state == States.IDLE;}
    private boolean isBusy(){return state == States.IS_BUSY;}

    public void setIdle(){state = States.IDLE;}


    public void toString(ArrayList<String> a) {
        System.out.print("[");
        for (String element : a) {
            System.out.print(element + ", ");
        }
        System.out.print("]");
    }


    public HashMap<Integer, ArrayList<String>> getOrdersToBeDelivered(){
        return ordersToBeDelivered;
    }
}


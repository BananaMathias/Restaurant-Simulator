package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

/**
 * Singleton
 * Creates the masterChef
 * Listener on the other chefs
 */
public class MasterChef implements ChefListener {

    private static MasterChef masterChefInstance = null;
    private int diameter;
    private int x;
    private int y;
    private Menu menu;
    private HashMap<Integer, ArrayList<String>> ordersFromTables;
    private HashMap<Integer, ArrayList<String>> ordersToBeDelivered;
    private Chef gardeMangerChef;
    private Chef sousChef;
    private Chef patissierChef;
    private enum States {IS_BUSY, IDLE}
    private States state = States.IDLE;

    /**
     * Constructor
     * @param x The masterChef's x-position
     * @param y The masterChef's y-position
     * @param diameter Tts diameter
     * @param menu A reference to the menu in the restaurant
     * @param gardeMangerChef A reference to the gardeMangerChef
     * @param sousChef A reference to the sousChef
     * @param patissierChef A reference to the patissierChef
     */
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

    /**
     * Creates the masterChef. Returns the already created masterChef if more than one masterChef is attemped to be created
     * @param x The masterChef's x-position
     * @param y The masterChef's y-position
     * @param diameter int masterChef's diameter
     * @param menu A reference to the menu in the restaurant
     * @param gardeMangerChef A reference to the gardeMangerChef
     * @param sousChef A reference to the sousChef
     * @param patissierChef A reference to the patissierChef
     * @return masterChef object, if one has already been created it returns that one
     */
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

    /**
     * Takes the order provided from getOrderWaiter
     * @param orderFromTable HashMap<Integer, ArrayList<String></String>> containing table number as a key and an array with food orders from the table
     */
    public void takeOrderFromWaiter(HashMap<Integer, ArrayList<String>> orderFromTable){
        ordersFromTables.putAll(orderFromTable);
    }

    /**
     * Takes the completed order from a chef
     * @param completedOrders HashMap<Integer, ArrayList<String></String>> containing the table number as a key and an array with completed food orders from a table
     */
    public void takeOrderFromChef(HashMap<Integer, ArrayList<String>> completedOrders){
        ordersToBeDelivered.putAll(completedOrders);

    }

    /**
     * Gives the completed order from a chef to the DeliverOrderWaiter
     * @return HashMap<Integer, ArrayList<String></String>> containing the table number as a key and an array with completed food orders from the table
     */
    public HashMap<Integer, ArrayList<String>> giveOrderToWaiter(){
        return chooseCompletedOrder();
    }

    /**
     * Strategy pattern
     * If the ordersFromTables is not empty, the masterChef chooses chef strategy based on which key the food to be cooked fits in the menu
     * Runs the startCooking method on the chosen chef
     */
    private void distributeOrder(){
        if (ordersFromTables.isEmpty()){
            return;
        }

        Chef chef = null;

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

    /**
     * Chooses a single order from the orders it has collected from the getOrderWaiter
     * @return HashMap<Integer, ArrayList<String></String>> with a single order and table number as a key
     */
    private HashMap<Integer, ArrayList<String>> chooseOrderFromOrders(){
        Object objectKey = ordersFromTables.keySet().toArray()[0];
        Integer key = (Integer) objectKey;
        HashMap<Integer, ArrayList<String>> order = new HashMap<>();
        order.put(key,ordersFromTables.get(key));
        ordersFromTables.remove(key);


        return order;

    }

    /**
     * Chooses a single order from the completed orders it has collected from the chefs
     * @return HashMap<Integer, ArrayList<String></String>> with a completed order and table number as a key
     */
    private HashMap<Integer, ArrayList<String>> chooseCompletedOrder(){
        Object objectKey = ordersToBeDelivered.keySet().toArray()[0];
        Integer key = (Integer) objectKey;
        HashMap<Integer, ArrayList<String>> order = new HashMap<>();
        order.put(key,ordersToBeDelivered.get(key));

        ordersToBeDelivered.remove(key);


        return order;
    }


    /**
     * Gets the ArrayList<String></String> item from a provided HashMap with an unknown key
     * @param orderHashmap HashMap<Integer, ArrayList<String></String>> with an unknown key
     * @return ArrayList<String></String> Order
     */
    private ArrayList<String> getOrderArray(HashMap<Integer, ArrayList<String>> orderHashmap) {
        Object objectKey = orderHashmap.keySet().toArray()[0];
        Integer key = (Integer) objectKey;

        ArrayList<String> order = orderHashmap.get(key);
        return order;
    }

    /**
     * Chefs notify the masterChef that they need an order
     */
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

    /**
     * Prints out a provided ArrayList<String></String>
     * @param a ArrayList<String></String>
     */
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


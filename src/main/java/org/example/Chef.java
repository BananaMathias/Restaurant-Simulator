package org.example;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Abstract
 * Chef class that the specific chefs inherits and the common methods they use
 * Extends Walker
 * Publisher for the masterChef and prepCHef
 */
public abstract class Chef extends Walker{
    protected enum States {IDLE, GOING_TO_MASTER, GOING_HOME, DELIVERING_FOOD, MAKING_FOOD}
    protected States state = States.IDLE;
    protected Color color;
    protected ArrayList<ChefListener> masterSubscribers;
    protected ArrayList<PrepChefListener> prepSubscribers;
    protected HashMap<Integer, ArrayList<String>> ordersToComplete;
    protected HashMap<Integer, ArrayList<String>> completedOrders;
    protected int ingredients = 10;
    protected int homeX;
    protected int homeY;
    protected String chef;

    /**
     * Constructor
     * @param x The chef's x-position
     * @param y The chef's y-position
     */
    public Chef(int x, int y){
        super(x, y);
        masterSubscribers = new ArrayList<>();
        prepSubscribers = new ArrayList<>();
        completedOrders = new HashMap<>();
    }


    public Color getColor(){
        return this.color;
    }


    /**
     * Adds the masterChef as a listener
     * @param masterSubscriber a masterChef listener
     */
    public void masterSubscribe(ChefListener masterSubscriber){masterSubscribers.add(masterSubscriber);}

    /**
     * Remove the masterChef as a listener
     * @param masterSubscriber a masterChef listener
     */
    public void masterUnsubscribe(ChefListener masterSubscriber){
        masterSubscribers.remove(masterSubscriber);
    }

    /**
     * Adds the prepChef as a listener
     * @param prepSubscriber a prepChef listener
     */
    public void prepSubscribe(PrepChefListener prepSubscriber){
        prepSubscribers.add(prepSubscriber);
    }

    /**
     * Removes the prepChef as a listener
     * @param prepSubscriber a prepChef listener
     */
    public void prepUnsubscribe(PrepChefListener prepSubscriber){
        prepSubscribers.remove(prepSubscriber);
    }

    /**
     * Returns the array that contains all the meals the chef have to cook
     * @return ArrayList<String></String> The order array
     */
    protected ArrayList<String> getOrderArray(){

        String objectKey = Arrays.toString(ordersToComplete.keySet().toArray()); // String with brackets included
        String key = String.valueOf(objectKey.toCharArray()[1]); // Removes brackets

        char keyChar = key.toCharArray()[0]; // Gets the number alone
        Integer keyInt = Integer.valueOf(String.valueOf(keyChar)); // Makes the number into an integer
        ArrayList<String> order = ordersToComplete.get(keyInt);
        order.add(0, keyInt.toString()); // Adds the table number to the beginning of the array
        return order;

    }

    /**
     * Starts the cooking process
     * @param order HashMap<Integer</Integer, ArrayList<String></String>> containing the number of the table who ordered and the food ordered
     */
    public void startCooking(HashMap<Integer, ArrayList<String>> order) {
        state = States.GOING_TO_MASTER;
        // Get the order from masterChef
        for (ChefListener masterChef: masterSubscribers){
            targetX = masterChef.getX();
            targetY = masterChef.getY();
        }
        ordersToComplete = order;
    }

    /**
     * Updates the chefs in main to do the different actions based on its state
     */
    public void update(){
        switch (state){
            case GOING_TO_MASTER:
                walkToMaster();
                break;

            case GOING_HOME:
                walkHome();
                break;

            case MAKING_FOOD:
                cook();
                break;

            case DELIVERING_FOOD:
                deliverFood();
                break;
                // If the chef is IDLE it should send a notification to the masterChef telling it can take an order
            case IDLE:
                notifyMasterChef();
                break;
        }
    }

    /**
     * Makes the chef walk to the masterChef
     */
    private void walkToMaster(){
        if (isGoingToMaster()){
            if (goTo()){
                state = States.GOING_HOME;
            }
        }
    }

    /**
     * Makes the chef walk to its home station. If it has an order it should start cooking instead of going idle
     */
    private void walkHome(){
        if (isGoingHome()){
            targetX = this.homeX;
            targetY = this.homeY;
            if (goTo()){
                // Sets the masterChef to idle so other chefs can go to it
                for (ChefListener masterChef: masterSubscribers){
                    masterChef.setIdle();
                }
                // If it did not take an order from masterChef it becomes idle
                if (ordersToComplete.isEmpty()){
                    this.state = States.IDLE;
                }
                else{
                    this.state = States.MAKING_FOOD;
                }

            }
        }
    }

    /**
     * Notifies the chef that it is currently idle
     */
    public void notifyMasterChef(){
        for (ChefListener masterChef: masterSubscribers){
            masterChef.notifyListener();
        }
    }

    /**
     * Checks if the chef has enough ingredients to start cooking
     * @return Boolean true if it does not have enough to cook
     */
    private boolean checkIngredients(){
        // It takes 3 ingredients per meal, need enough for every meal in an order to pass
        if ((double) ingredients/3.0 <= ordersToComplete.size()){
            for (PrepChefListener prepChef: prepSubscribers){
                prepChef.notifyListener(this.homeX, this.homeY, this.chef);
            }
            return true; // If ingredients is low
        }
        else { return false;}
    }

    /**
     * Cooks the order provided from the masterChef. Waits 6 seconds before its done cooking
     */
    private void cook() {
        if (isMakingFood()) {
            // If ingredients are low it does not start cooking
            if (checkIngredients()){
                return;
            }
            ArrayList<String> ordersToCompleteArray = getOrderArray(); // gets the order array from ordersToComplete with the table number at the end of the array
            int tableNumber = Integer.parseInt(ordersToCompleteArray.get(0)); // Get the table number from order array
            ordersToCompleteArray.remove(0); // Remove key from the Array
            ArrayList<String> completedOrdersArray = new ArrayList<>();


                startTimer(6000, "cook");
                long thisTime = System.currentTimeMillis(); // Takes new time every update in RestaurantMain
                // If it has gone the period time:
                if ((thisTime - lastTime) >= period) {
                    // Adds ordersToComplete to completedOrders
                    completedOrdersArray.addAll(ordersToCompleteArray);
                    completedOrders.put(tableNumber, completedOrdersArray);

                    ordersToCompleteArray.clear();
                    ordersToComplete.clear();
                    for (ChefListener masterChef: masterSubscribers) {
                        targetX = masterChef.getX();
                        targetY = masterChef.getY();
                    }
                    startCounting = true;
                    // Removes ingredients used
                    ingredients -= completedOrdersArray.size() * 3;
                    this.state = States.DELIVERING_FOOD;
                }
            }

        }

    /**
     * Goes to the masterChef and gives the HashMap containing cooked food and the tables number as a key
     */
    private void deliverFood(){
        if (isDeliveringFood()){
            if (goTo()){
                for (ChefListener masterChef: masterSubscribers){
                    masterChef.takeOrderFromChef(completedOrders);
                }
                completedOrders.clear(); // Clears the order so that a new order can be taken and delivered
                state = States.GOING_HOME;
            }
        }
    }

    /**
     * Method that the prepChef uses to add ingredients to the chef
     * @param addOn int The ingredients the prepChef gives
     */
    public void addIngredients(int addOn){
        ingredients += addOn;
    }

    private boolean isGoingToMaster(){return this.state == States.GOING_TO_MASTER;}
    private boolean isGoingHome(){return this.state == States.GOING_HOME;}
    private boolean isDeliveringFood(){return this.state == States.DELIVERING_FOOD;}
    private boolean isMakingFood(){return this.state == States.MAKING_FOOD;}
}

package org.example;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class Chef extends Walker implements Cooking{
    protected enum States {IDLE, GOING_TO_MASTER, GOING_HOME, DELIVERING_FOOD, MAKING_FOOD}
    protected States state = States.IDLE;
    protected Color color;
    protected ArrayList<ChefListener> masterSubscribers;
    protected ArrayList<PrepChefListener> prepSubscribers;
    HashMap<Integer, ArrayList<String>> ordersToComplete;
    HashMap<Integer, ArrayList<String>> completedOrders;
    protected int ingredients = 10;
    protected int homeX;
    protected int homeY;
    protected String chef;

    public Chef(int x, int y){
        super(x, y);
        masterSubscribers = new ArrayList<>();
        prepSubscribers = new ArrayList<>();
        completedOrders = new HashMap<>();
    }


    public Color getColor(){
        return this.color;
    }



    public void masterSubscribe(ChefListener masterSubscriber){masterSubscribers.add(masterSubscriber);}

    public void masterUnsubscribe(ChefListener masterSubscriber){
        masterSubscribers.remove(masterSubscriber);
    }

    public void prepSubscribe(PrepChefListener prepSubscriber){
        prepSubscribers.add(prepSubscriber);
    }

    public void prepUnsubscribe(PrepChefListener prepSubscriber){
        prepSubscribers.remove(prepSubscriber);
    }

    protected ArrayList<String> getOrderArray(){

        String objectKey = Arrays.toString(ordersToComplete.keySet().toArray());
        String key = String.valueOf(objectKey.toCharArray()[1]);

        char keyChar = key.toCharArray()[0];
        Integer keyInt = Integer.valueOf(String.valueOf(keyChar));
        ArrayList<String> order = ordersToComplete.get(keyInt);
        order.add(0, keyInt.toString());
        return order;

    }

    public void startCooking(HashMap<Integer, ArrayList<String>> order) {
        state = States.GOING_TO_MASTER;
        targetX = masterSubscribers.get(0).getX();
        targetY = masterSubscribers.get(0).getY();
        ordersToComplete = order;


    }

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

            case IDLE:
                notifyMasterChef();
                break;
        }
    }

    private void walkToMaster(){
        if (isGoingToMaster()){
            if (goTo()){
                state = States.GOING_HOME;
            }
        }
    }

    private void walkHome(){
        if (isGoingHome()){
            targetX = this.homeX;
            targetY = this.homeY;
            if (goTo()){
                for (ChefListener masterChef: masterSubscribers){
                    masterChef.setIdle();
                }
                if (ordersToComplete.isEmpty()){
                    this.state = States.IDLE;
                }
                else{
                    this.state = States.MAKING_FOOD;
                }

            }
        }
    }

    public void notifyMasterChef(){
        for (ChefListener masterChef: masterSubscribers){
            masterChef.notifyListener();
        }
    }

    private boolean checkIngredients(){
        if ((double) ingredients/3.0 <= ordersToComplete.size()){
            for (PrepChefListener prepChef: prepSubscribers){
                prepChef.notifyListener(this.homeX, this.homeY, this.chef);
            }
            return true; // If ingridients is low
        }
        else { return false;}
    }

    private void cook() {
        if (isMakingFood()) {
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

                    completedOrdersArray.addAll(ordersToCompleteArray);

                    completedOrders.put(tableNumber, completedOrdersArray);
                    ordersToCompleteArray.clear();
                    ordersToComplete.clear();

                    targetX = masterSubscribers.get(0).getX();
                    targetY = masterSubscribers.get(0).getY();
                    startCounting = true;
                    ingredients -= 9;
                    this.state = States.DELIVERING_FOOD;
                }


            // I NEED TO GET THE KEY WHICH REPRESENTS A TABLE SO THAT COMPLETEDORDERS IS A HASHMAP WITH THE ORDER AND KEY
            // MIGHT ME ABLE TO JUST MAKE COMPLETEDORDERS = ORDERSTOCOMPLETE

            }

        }


    private void deliverFood(){
        if (isDeliveringFood()){
            if (goTo()){
                for (ChefListener masterChef: masterSubscribers){
                    masterChef.takeOrderFromChef(completedOrders);
                }
                state = States.GOING_HOME;
            }
        }
    }
    private boolean isGoingToMaster(){return this.state == States.GOING_TO_MASTER;}
    private boolean isGoingHome(){return this.state == States.GOING_HOME;}
    private boolean isDeliveringFood(){return this.state == States.DELIVERING_FOOD;}
    private boolean isMakingFood(){return this.state == States.MAKING_FOOD;}


    private HashMap<Integer, ArrayList<String>> deepCopyOrder(HashMap<Integer, ArrayList<String>> original) {
        HashMap<Integer, ArrayList<String>> copy = new HashMap<>();
        for (Integer key : original.keySet()) {
            copy.put(key, new ArrayList<>(original.get(key)));
        }
        return copy;
    }

    public void addIngredients(int addOn){
        ingredients += addOn;
    }
}


 /*while (!ordersToCompleteArray.isEmpty()) {
                //This while loop is pausing everything
                startTimer(2000, "Is now idle");
                long thisTime = System.currentTimeMillis(); // Takes new time every update in RestaurantMain
                // If it has gone the period time:
                if ((thisTime - lastTime) >= period) {
                    completedOrdersArray.add(ordersToCompleteArray.get(0));
                    System.out.println("Completed" + ordersToCompleteArray.get(0));
                    ordersToCompleteArray.remove(0);
                    startCounting = true;
                    ingredients -= 3;
                    System.out.println(ingredients);
                }
*/
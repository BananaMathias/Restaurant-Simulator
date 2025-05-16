package org.example;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GardeMangerChef extends Chef{

    public GardeMangerChef(int x, int y){
        super(x, y);
        this.color = Color.GREEN;
    }

    @Override
    public void startCooking(HashMap<Integer, ArrayList<String>> order) {
        state = States.GOING_TO_MASTER;
        targetX = 400;
        targetY = 300;
        ordersToComplete = order;

    }
    @Override
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

            case IDLE:
                notifyMasterChef();

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
            targetX = 300;
            targetY = 75;
            if (goTo()){
                if (ordersToComplete.isEmpty()){
                    this.state = States.IDLE;
                }
                else{
                    this.state = States.MAKING_FOOD;
                }

            }
        }
    }
    @Override
    public void notifyMasterChef(){
        for (ChefListener masterChef: masterSubscribers){
            masterChef.notifyListener();
        }
    }

    private void checkIngredients(){
        if ((double) ingredients/3.0 <= ordersToComplete.size()){
            for (ChefListener prepChef: prepSubscribers){
                prepChef.notifyListener();
            }
        }
    }

    private void cook() {
        if (isMakingFood()) {
            checkIngredients();

            ArrayList<String> ordersToCompleteArray = getOrderArray(); // gets the order array from ordersToComplete with the table number at the end of the array
            int tableNumber = Integer.parseInt(ordersToCompleteArray.get(-1)); // Get the table number from order array
            ordersToCompleteArray.remove(-1); // Remove key from the Array
            ArrayList<String> completedOrdersArray = new ArrayList<>();

            while (!ordersToCompleteArray.isEmpty()) {

                startTimer(2000, "Is now idle");
                long thisTime = System.currentTimeMillis(); // Takes new time every update in RestaurantMain
                // If it has gone the period time:
                if ((thisTime - lastTime) >= period) {
                    completedOrdersArray.add(ordersToCompleteArray.get(0));
                    ordersToCompleteArray.remove(0);
                    startCounting = true;

                    ingredients -= 3;
                }

                // I NEED TO GET THE KEY WHICH REPRESENTS A TABLE SO THAT COMPLETEDORDERS IS A HASHMAP WITH THE ORDER AND KEY
                // MIGHT ME ABLE TO JUST MAKE COMPLETEDORDERS = ORDERSTOCOMPLETE
                completedOrders.put(tableNumber, completedOrdersArray);
                this.state = States.DELIVERING_FOOD;
                targetX = 400;
                targetY = 300;
            }
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
}

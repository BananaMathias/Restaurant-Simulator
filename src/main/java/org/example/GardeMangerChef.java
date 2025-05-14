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

            case IDLE:
                notifyMasterChef();

        }
    }

    private void walkToMaster(){
        if (isGoingToMaster()){
            if (goTo()){
                state = States.GOING_HOME;
                targetX = 300;
                targetY = 75;
            }
        }
    }

    private void walkHome(){
        if (isGoingHome()){
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
        masterSubscribers.get(0).notifyListener();
    }

    private void cook() {

        if ((double) ingredients/3.0 <= ordersToComplete.size()){
            for (ChefListener prepChef: prepSubscribers){
                prepChef.notifyListener();
            }
        }
        ArrayList<String> ordersToCompleteArray = getOrderArray();
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
            this.state = States.DELIVERING_FOOD;
            targetX = 300;
            targetY = 75;
        }
    }

    private void deliverFood(){
        if (isDeliveringFood()){
            if (goTo()){

            }
        }
    }
    private boolean isGoingToMaster(){return this.state == States.GOING_TO_MASTER;}
    private boolean isGoingHome(){return this.state == States.GOING_HOME;}
    private boolean isDeliveringFood(){return this.state == States.DELIVERING_FOOD;}

}

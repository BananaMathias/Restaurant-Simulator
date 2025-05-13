package org.example;

import java.awt.*;
import java.util.ArrayList;

public class GardeMangerChef extends Chef{

    ArrayList<String> ordersToComplete;
    ArrayList<String> completedOrders;
    public GardeMangerChef(int x, int y){
        super(x, y);
        this.color = Color.GREEN;
    }

    @Override
    public void startCooking(ArrayList<String> order) {
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
                state = States.MAKING_FOOD;
            }
        }
    }
    @Override
    public void notifyMasterChef(){
        subscribers.get(0).notifyListener();
    }

    private void cook(){
        while (ingredients < 3){





            ingredients -= 3;
        }
    }
    private boolean isGoingToMaster(){return state == States.GOING_TO_MASTER;}
    private boolean isGoingHome(){return state == States.GOING_HOME;}

}

package org.example;

import java.awt.*;
import java.util.ArrayList;

public class GardeMangerChef extends Chef{

    public GardeMangerChef(int x, int y){
        super(x, y);
        this.color = Color.GREEN;
    }

    @Override
    public void startCooking(ArrayList<String> order) {
        state = States.GOING_TO_MASTER;
        targetX = 400;
        targetY = 300;
    }

    public void update(){
        switch (state){
            case GOING_TO_MASTER:
                walkToMaster();
                break;

                case GOING_HOME:
                walkHome;
                break;

                case MAKING_FOOD:
                cook();
                break;
        }
    }

    private void walkToMaster(){
        if (isGoingToMaster()){

        }
    }

    private boolean isGoingToMaster(){return state == States.GOING_TO_MASTER;}

}

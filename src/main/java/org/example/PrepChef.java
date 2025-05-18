package org.example;

import javax.swing.plaf.nimbus.State;
import java.awt.*;

public class PrepChef extends Walker implements PrepChefListener{

    protected enum States {IDLE, GOING_TO_CHEF, GOING_HOME}
    States state = States.IDLE;
    Color color;
    private int homeX;
    private int homeY;

    public PrepChef(int x, int y){
        super(x, y);
        this.color = Color.ORANGE;
        this.homeX = 50;
        this.homeY = 50;
    }

    public void notifyListener(int x, int y) {
        if (isIdle()){

            this.targetX = x;
            this.targetY = y;
            this.state = States.GOING_TO_CHEF;

        }
    }

    private void walkToChef(){
        if (isGoingToChef()){
            if (goTo()){
                this.targetX = this.homeX;
                this.targetY = this.homeY;
                state = States.GOING_HOME;

            }
        }
    }

    private void walkHome(){
        if (isGoingHome()){
            if (goTo()){
                state = States.IDLE;
            }
        }
    }

    public void update(){
        switch (state){
            case GOING_TO_CHEF:
                walkToChef();
                break;

            case GOING_HOME:
                walkHome();
                break;

            default:
                break;
        }
    }

    public Color getColor() {
        return color;
    }

    public boolean isIdle(){
        return this.state == States.IDLE;
    }
    public boolean isGoingToChef(){return this.state == States.GOING_TO_CHEF;}
    public boolean isGoingHome(){return this.state == States.GOING_HOME;}
}


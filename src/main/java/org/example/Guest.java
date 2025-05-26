package org.example;

import java.awt.*;

public class Guest extends Walker {

    private int number;
    private enum States {IDLE, GOING_TO_TABLE, LEAVING, EATING}
    private States state = States.IDLE;
    private String food = null;
    private int mealsEaten = 0;
    private Color color = Color.BLUE;

    public Guest(int x, int y, int number) {
        super(x, y);
        this.number = number;
    }

    public void walkToTable() {
        // If everything has gone correctly this should always be true, just a failsafe
        if (isGoingToTable()) {
            // Runs goTo() and checks if it returns true for both x and y
            if (goTo()) {
            }
        }
    }

    public void setSeat(int x, int y){
        this.targetX = x;
        this.targetY = y;
        state = States.GOING_TO_TABLE;
    }

    public void eat(){
        if (isEating()){
            if (!(food == null)) {
                startTimer(10000, "eating");
                long thisTime = System.currentTimeMillis();
                if ((thisTime - lastTime) >= period) {
                    food = null;
                    state = States.IDLE;
                    startCounting = true;
                    mealsEaten ++;
                    System.out.println("Has eaten");
                    if (isDone()){

                        walkOut();
                    }
                }
            }
        }
    }

    private void walkOut(){}

    public void update(){
        switch (state){
            case GOING_TO_TABLE:
                walkToTable();
                break;

            case EATING:
                eat();
                break;

            case LEAVING:
                walkOut();
                break;

            default:
                break;

        }
    }

    public void receiveFood(String meal) {
        food = meal;
        state = States.EATING;
    }

    public boolean isGoingToTable() {
        return state == States.GOING_TO_TABLE;
    }

    public boolean isEating(){ return state == States.EATING;}

    public boolean isDone(){return mealsEaten == 3;}

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public int getY() {
        return super.getY();
    }

    @Override
    public int getDiameter() {
        return super.getDiameter();
    }

    public Color getColor(){
        return color;
    }

    public int getNumber(){
        return number;
    }

    public void setEating(){
        state = States.EATING;
    }

}

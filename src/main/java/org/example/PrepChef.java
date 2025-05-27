package org.example;

import javax.swing.plaf.nimbus.State;
import java.awt.*;

/**
 * Creates the prepChef that gives the ingredients to the chefs
 * Listener on the chefs
 * Extends walker
 */
public class PrepChef extends Walker implements PrepChefListener{

    protected enum States {IDLE, GOING_TO_CHEF, GOING_HOME}
    States state = States.IDLE;
    Color color;
    private int homeX;
    private int homeY;
    private Chef gardeMangerChef;
    private Chef sousChef;
    private Chef patissierChef;
    private String goToChef;

    /**
     * Constructor
     * @param x The prepChef's x-position
     * @param y The prepChef's y-position
     * @param gardeMangerChef Reference to the gardeMangerChef
     * @param sousChef Reference to the sousChef
     * @param patissierChef Reference to the patissierChef
     */
    public PrepChef(int x, int y, Chef gardeMangerChef, Chef sousChef, Chef patissierChef){
        super(x, y);
        this.color = Color.ORANGE;
        this.homeX = 50;
        this.homeY = 50;

        this.gardeMangerChef = gardeMangerChef;
        this.sousChef = sousChef;
        this.patissierChef = patissierChef;
    }

    /**
     * Gets notified by the chefs if they are low on ingredients
     * @param x the Chef's x-position
     * @param y the Chef's y-position
     * @param chef String which chef it is going to
     */
    public void notifyListener(int x, int y, String chef) {
        if (isIdle()){
            goToChef = chef;
            this.targetX = x;
            this.targetY = y;
            this.state = States.GOING_TO_CHEF;

        }
    }

    /**
     * Walks to the chef who notified the prepChef and gives the ingredients to the chef
     */
    private void walkToChef(){
        if (isGoingToChef()){
            if (goTo()){

                switch (goToChef){
                    case "gardeMangerChef":
                        gardeMangerChef.addIngredients(20);
                        break;

                    case "sousChef":
                        sousChef.addIngredients(20);
                        break;

                    case "patissierChef":
                        patissierChef.addIngredients(20);
                        break;
                }
                this.targetX = this.homeX;
                this.targetY = this.homeY;
                state = States.GOING_HOME;

            }
        }
    }

    /**
     * Walks to the home station
     */
    private void walkHome(){
        if (isGoingHome()){
            if (goTo()){
                state = States.IDLE;
            }
        }
    }

    /**
     * Updates the prepChef in main
     */
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


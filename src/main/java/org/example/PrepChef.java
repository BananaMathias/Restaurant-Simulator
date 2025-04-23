package org.example;

import java.awt.*;

public class PrepChef extends Chef{

    protected enum States {IDLE, GOING_TO_CHEF, GOING_HOME, MAKING_FOOD}

    public PrepChef(int x, int y){
        super(x, y);
        this.color = Color.ORANGE;
    }
}

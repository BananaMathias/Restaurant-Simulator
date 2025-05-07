package org.example;

import java.awt.*;

public class PrepChef extends Walker{

    protected enum States {IDLE, GOING_TO_CHEF, GOING_HOME, MAKING_FOOD}
    Color color;
    public PrepChef(int x, int y){
        super(x, y);
        this.color = Color.ORANGE;
    }
}

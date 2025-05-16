package org.example;

import javax.swing.plaf.nimbus.State;
import java.awt.*;

public class PrepChef extends Walker{

    protected enum States {IDLE, GOING_TO_CHEF, GOING_HOME}
    States state = States.IDLE;
    Color color;
    public PrepChef(int x, int y){
        super(x, y);
        this.color = Color.ORANGE;
    }

    public void notifyListener() {
        if (isIdle()){
            this.state = States.GOING_TO_CHEF;
        }
    }

    public boolean isIdle(){
        return this.state == States.IDLE;
    }
}

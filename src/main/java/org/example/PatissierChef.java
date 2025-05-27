package org.example;

import java.awt.*;

/**
 * The patissierChef that creates the desserts
 */
public class PatissierChef extends Chef{

    public PatissierChef(int x, int y){
        super(x, y);
        this.color = Color.pink;
        this.homeX = 400;
        this.homeY = 500;
        this.chef = "patissierChef";
    }
}

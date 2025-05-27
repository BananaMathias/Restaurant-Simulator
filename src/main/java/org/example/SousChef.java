package org.example;

import java.awt.*;

/**
 * The sousChef that cooks the main courses
 */
public class SousChef extends Chef{

    public SousChef(int x, int y){
        super(x, y);
        this.color = Color.BLUE;
        this.homeX = 125;
        this.homeY = 375;
        this.chef = "sousChef";
    }


}

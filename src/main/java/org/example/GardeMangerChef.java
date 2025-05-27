package org.example;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The garde manger chef that cooks the appetizers
 */
public class GardeMangerChef extends Chef{

    public GardeMangerChef(int x, int y){
        super(x, y);
        this.color = Color.GREEN;
        this.homeX = 300;
        this.homeY = 75;
        this.chef = "gardeMangerChef";
    }



}

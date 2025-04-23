package org.example;

import java.util.HashMap;

public class MasterChef implements ChefListener {

    private static MasterChef masterChefInstance = null;
    private int diameter;
    private int x;
    private int y;
    private Menu menu;
    private HashMap<Integer, String> ordersFromTables;
    private PrepChef prepChef;
    private GardeMangerChef gardeMangerChef;
    private SousChef sousChef;
    private PatissierChef patissierChef;



    private MasterChef(int x, int y, int diameter, Menu menu, PrepChef prepChef, GardeMangerChef gardeMangerChef, SousChef sousChef, PatissierChef patissierChef){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.menu = menu;
        this.ordersFromTables = new HashMap<>();
        this.prepChef = prepChef;
        this.gardeMangerChef = gardeMangerChef;
        this.sousChef = sousChef;
        this.patissierChef = patissierChef;
    }

    public static MasterChef getInstance(int x, int y, int diameter, Menu menu, PrepChef prepChef, GardeMangerChef gardeMangerChef, SousChef sousChef, PatissierChef patissierChef) {
        if (masterChefInstance == null) {
            masterChefInstance = new MasterChef(x, y, diameter, menu, prepChef, gardeMangerChef, sousChef, patissierChef);
        }
        return masterChefInstance;
    }
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getDiameter(){
        return this.diameter;
    }
    // Takes order and puts in order list
    public void takeOrder(HashMap<Integer, String> orderFromTable){
        ordersFromTables.putAll(orderFromTable);

    }

    @Override
    public void notifyListener() {

    }

    // Strategy pattern depending on which chef should take the order
    // Observer for each chef. If masterChef gets appetizer, he should notify correct chef to take care of it

}

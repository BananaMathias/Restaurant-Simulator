package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Steward implements StewardListener {

    private int x;
    private int y;
    private ArrayList<Table> tables = new ArrayList<>();
    private int diameter = 30;
    private Color color = Color.orange;
    private boolean busy;

    public Steward(int x, int y, ArrayList<Table> tables){
        this.x = x;
        this.y = y;
        this.tables = tables;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDiameter() {
        return diameter;
    }

    public Color getColor() {
        return color;
    }

    private void createGuests(int tableNumber){
        if (!tables.get(tableNumber).isBusy()){
            Random random = new Random();
            int randomInt = random.nextInt(4) + 1;

            ArrayList<Guest> guests = new ArrayList<>();
            for (int i = 0; i < randomInt; i++){
                guests.add(new Guest(900 + (i * 50),350, i));
            }

            tables.get(tableNumber).setGuests(guests);
            busy = false;
            System.out.println(busy);
        }

    }

    public void notifySteward(int tableNumber) {
        if (!isBusy()) {
            createGuests(tableNumber);
            busy = true;
        }
    }

    public boolean isBusy(){return busy;}
}

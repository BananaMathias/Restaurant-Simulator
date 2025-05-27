package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Creates the Steward that creates the guests and guides them to the correct table
 * Listener on tables
 */
public class Steward implements StewardListener {

    private int x;
    private int y;
    private ArrayList<Table> tables = new ArrayList<>();
    private int diameter = 30;
    private Color color = Color.orange;

    /**
     * Constructor
     * @param x Int The Steward's x-position
     * @param y Int The Steward's y-position
     * @param tables ArrayList<Table></Table> of the tables
     */
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

    /**
     * Creates a random amount of guests up to 4 and assigns them a table
     * @param tableNumber int The assigned table's number
     */
    private void createGuests(int tableNumber){
        if (!tables.get(tableNumber).isBusy()){
            Random random = new Random();
            int randomInt = random.nextInt(4) + 1;

            ArrayList<Guest> guests = new ArrayList<>();
            for (int i = 0; i < randomInt; i++){
                guests.add(new Guest(900 + (i * 50),350, i));
            }

            tables.get(tableNumber).setGuests(guests);
        }

    }

    /**
     * The method the tables uses to notify the Steward that they are not busy
     * Calls the method that creates guests
     * @param tableNumber int The table number of the table who notified the Steward
     */
    public void notifySteward(int tableNumber) {
        createGuests(tableNumber);
    }
}

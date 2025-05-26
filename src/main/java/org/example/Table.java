package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Table {

    private int x;
    private int y;
    private final int diameter;
    private final int number;
    private ArrayList<WaiterListener> waiterSubscribers;
    private Menu menu;
    private int guestAmount;
    ArrayList<String> foodToEat;
    private boolean busy = false;
    private ArrayList<StewardListener> stewardSubscribers = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> placesToSit = new ArrayList<>();
    private ArrayList<Guest> guests = new ArrayList<>();

    public Table(int x, int y, int diameter, int number, Menu menu){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.number = number;
        this.waiterSubscribers = new ArrayList<>();
        this.menu = menu;
        this.foodToEat = new ArrayList<>();
        // Seats to the west and east
        for (int i = -1; i < 2; i += 2){
            ArrayList<Integer> coordinates = new ArrayList<>();
            coordinates.add(this.x + i*50);
            coordinates.add(this.y);
            placesToSit.add(coordinates);
        }

        // Seats north and south
        for (int i = -1; i < 2; i += 2){
            ArrayList<Integer> coordinates = new ArrayList<>();
            coordinates.add(this.x);
            coordinates.add(this.y + i*50);
            placesToSit.add(coordinates);
        }

    }

    public void waiterSubscribe(WaiterListener subscriber){
        waiterSubscribers.add(subscriber);
    }

    public void waiterUnsubscribe(WaiterListener subscriber){
        waiterSubscribers.remove(subscriber);
    }

    public void stewardSubscribe(StewardListener subscriber){
        stewardSubscribers.add(subscriber);
    }

    public void stewardUnsubscribe(StewardListener subscriber){
        stewardSubscribers.remove(subscriber);
    }


    public void order(int orderAmount){
        for (WaiterListener s: waiterSubscribers){
            // Gives tables x and y-position, table number, menu items based on how many times the waiter has gone to this table and how many guests at the table
            s.retrieveOrder(getX(), getY(), getNumber(), bunchOrders(orderAmount)); // Why can I use int and it works with Integer?
        }
    }

    private ArrayList<String> bunchOrders(int orderAmount){
        // Random int to select which menu item to get from the current menu, gets one meal for each guest
        Random foodRandom = new Random();
        ArrayList<String> foodOrders = new ArrayList<>();
        for (int i = 0; i < guestAmount; i++) {
            int foodInt = foodRandom.nextInt(2);
            foodOrders.add(this.menu.getMenuItems().get(orderAmount).get(foodInt));
        }
        return foodOrders;
    }

    public void needGuests() {
        for (StewardListener steward : stewardSubscribers) {
            steward.notifySteward(number);
        }
    }

    public void bringGuests(){
        if (!isBusy()){
            for (Guest guest: this.guests){
                guest.setSeat(placesToSit.get(guest.getNumber()).get(0), placesToSit.get(guest.getNumber()).get(1));
                guestAmount++;
            }
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getDiameter() {
        return this.diameter;
    }

    public int getNumber(){return this.number;}

    public int getGuestAmount(){return guestAmount;}

    public boolean isBusy(){return busy;}

    public void setFoodToEat(ArrayList<String> array){
        foodToEat.addAll(array);
        giveFoodToGuests();


    }

    private void giveFoodToGuests(){
        for (Guest guest: guests){
            guest.receiveFood(foodToEat.get(guest.getNumber()));
        }
    }

    public ArrayList<ArrayList<Integer>> getPlacesToSit() {
        return placesToSit;
    }

    public void setGuests(ArrayList<Guest> guests) {
        this.guests = guests;
        bringGuests();
        busy = true;
    }

    public void updateGuests() {
        boolean clear = false;
        for (Guest guest : guests) {
            guest.update();
            if (guest.isDone()){
                clear = true;
            }
        }
        if (clear){
            guests.clear();
            busy = false;
        }
    }

    public ArrayList<Guest> getGuests() {
        return guests;
    }
}

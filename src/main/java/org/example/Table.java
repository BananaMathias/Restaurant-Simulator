package org.example;

public class Table {

    private int x;
    private int y;
    private final int diameter;
    private final int number;

    public Table(int x, int y, int diameter, int number){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.number = number;

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






}

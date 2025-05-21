package org.example;

import java.util.ArrayList;

public class DeliverOrderWaiter extends MasterWaiter {
    private ArrayList<Table> tables;

    public DeliverOrderWaiter(int x, int y, int diameter, ArrayList<Table> tables, MasterChef masterChef){
        super(x, y, masterChef);
        this.tables = tables;
    }

    protected void waiterSpecificTask(){

    }
}

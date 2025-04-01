package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RestaurantMain extends JPanel {

    static ArrayList<Waiter> waiters = new ArrayList<Waiter>();
    static ArrayList<Table> tables = new ArrayList<Table>();
    private static final long PERIOD = 111000; //one lap
    private static long lastTime = System.currentTimeMillis();

    // In here all objects that are needed for operating the restaurant should be created.
    // This is initialisation and determines the initial state of the program.
    static void setupRestaurant(){
        for (int i = 0; i < 3; i++){
           tables.add(new Table((515 + i*260), 50, 50, i));
        }
        for (int i = 3; i < 6; i++) {
            tables.add(new Table((i * 260 - 270), 500, 50, i));
        }
        //tables.add(new Table(1000,500,50,1));
        waiters.add(new Waiter(507,300,40,tables));

        for (Waiter w: waiters){
            for (Table t: tables){
                t.subscribe(w);
            }
        }


    }

    // Contains the simulation logic, should probably be broken into smaller pieces as the program expands
    static void update() {

        // what should happen with the waiter each time the simulation loops
        for (Waiter w : waiters) {
            /*if (w.isIdle()) {
                Random randomTable = new Random();
                int tableNumber = randomTable.nextInt(5);
                tables.get(tableNumber).order();
                System.out.println(2);*/



            }

        for (Table t: tables){

        }
        // ... similar updates for all other agents in the simulation.
    }
    static void updateTableOrder(){
        Random randomTable = new Random();
        int tableNumber = randomTable.nextInt(5);

        tables.get(tableNumber).order();

        /*long thisTime = System.currentTimeMillis(); //takes new time every update in RestaurantMain
        if ((thisTime - lastTime) >= PERIOD) { // if the difference between when the object was created and the current time is == PERIOD, returns -1 all the time
            return -1;
        }
        else {
        }
        return 0;*/
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(new Color(255, 245, 158, 184)); //  // Set the background color to light yellow

        g.setColor(Color.DARK_GRAY); // Set the color for the border lines
        g.drawRect(500, 0, 600, getHeight() - 5);
        //g.drawRect(800, 0, getWidth() - 5, getHeight() - 5);
        g.setColor(Color.BLACK);
        g.drawRect(500, 0, 695, getHeight() - 5);

        // Draw kitchen door
        g.setColor(Color.DARK_GRAY);
        g.fillRect(490, 270, 20, 100);
        g.fillRect(1090, 270, 20, 100);

        // Draw tables
        drawTables(g);

        // Draw the waiters
        drawWaiters(g);

        // MORE CODE HERE
    }

    static void drawTables(Graphics g) {
        for (Table table : tables) {
            g.setColor(Color.RED);
            g.fillOval(table.getX(), table.getY(), table.getDiameter(), table.getDiameter()); // Draw circle with diameter of 50 pixels
            g.setColor(Color.WHITE);
            g.fillOval(table.getX()+3, table.getY()+3, table.getDiameter()-6, table.getDiameter()-6);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(table.getNumber()), table.getX()+30,  table.getY()+35);
        }
    }

    static  void drawWaiters(Graphics g){
        for (Waiter waiter : waiters) {
            g.setColor(Color.BLACK);
            g.fillOval(waiter.getX(), waiter.getY(), waiter.getDiameter(), waiter.getDiameter()); // Draw circle with diameter of 50 pixels
            g.setColor(Color.WHITE);
            g.fillOval(waiter.getX()+7, waiter.getY()+7, waiter.getDiameter()-14, waiter.getDiameter()-14); // Draw circle with diameter of 50 pixels
        }
    }

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Restuarant Simulation");
        frame.setSize(1200, 640); // Set window size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Add the custom panel (with circles) to the frame
        RestaurantMain panel = new RestaurantMain();
        frame.add(panel);

        // Display the window
        frame.setVisible(true);

        // Setup for the restaurant
        setupRestaurant();

        while (true) {
            try {
                Thread.sleep(33); // With the goal of having about 30 fps.
            } catch (Exception threadException) {
                System.out.println("Sleep exception: " + threadException.getMessage());
            }
            update();

            panel.repaint();
        }
    }
}
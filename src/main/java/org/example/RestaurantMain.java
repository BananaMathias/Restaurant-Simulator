package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RestaurantMain extends JPanel {

    static ArrayList<GetOrderWaiter> getOrderWaiters = new ArrayList<GetOrderWaiter>();
    static ArrayList<Table> tables = new ArrayList<Table>();
    static ArrayList<Chef> chefs = new ArrayList<>();
    private static double[] visits = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private static double totalVisits = 0.0;
    private static MasterChef masterChef;
    private static PrepChef prepChef;
    private static Chef gardeMangerChef;
    private static Chef sousChef;
    private static Chef patissierChef;

    // In here all objects that are needed for operating the restaurant should be created.
    // This is initialisation and determines the initial state of the program.
    static void setupRestaurant(){

        Menu menu = Menu.getInstance();

        for (int i = 0; i < 3; i++){
           tables.add(new Table((515 + i*260), 50, 50, i, menu));
        }
        for (int i = 3; i < 6; i++) {
            tables.add(new Table((i * 260 - 270), 500, 50, i, menu));
        }
        //tables.add(new Table(1000,500,50,1));

        prepChef = new PrepChef(50, 50, gardeMangerChef, sousChef, patissierChef);
        gardeMangerChef = new GardeMangerChef(300, 75);
        sousChef = new SousChef(125, 375);
        patissierChef = new PatissierChef(400, 500);

        masterChef = MasterChef.getInstance(450, 300, 40, menu, prepChef, gardeMangerChef, sousChef, patissierChef);
        getOrderWaiters.add(new GetOrderWaiter(507,300,40,tables, masterChef));

        chefs.add(gardeMangerChef);
        chefs.add(sousChef);
        chefs.add(patissierChef);

        for (Chef c: chefs){
            c.masterSubscribe(masterChef);
            c.prepSubscribe(prepChef);

        }
        for (GetOrderWaiter w: getOrderWaiters){
            for (Table t: tables){
                t.subscribe(w);
            }
        }


    }

    // Contains the simulation logic, should probably be broken into smaller pieces as the program expands
    static void update() {

        prepChef.update();
        // what should happen with the waiter each time the simulation loops
        for (GetOrderWaiter w : getOrderWaiters) {
            // Runs update() to run the correct walk method in GetOrderWaiter
            w.update();

            // If the GetOrderWaiter is IDLE
            if (w.isIdle()) {

                // Gets random table to go to
                Random randomTable = new Random();
                int tableNumber = randomTable.nextInt(6);

                // If the GetOrderWaiter has not gone to any table
                if (totalVisits == 0){
                    randomOrder(tableNumber);
                }

                // If the table has gone to a table, it will not go to the same table until it has gone to every other table first
                else if (visits[tableNumber]/totalVisits <= (1.0/6.0)){
                    randomOrder(tableNumber);

                }


            }
        }

        for (Table t: tables){

        }
        // ... similar updates for all other agents in the simulation.

        for (Chef chef: chefs){
            chef.update();
        }


    }

    static void randomOrder(int tableNumber){
        // Adds a visit to log amount of visits Waiters has done to a specific table
        visits[tableNumber]++;
        totalVisits++;
        // Runs the correct table's order()
        tables.get(tableNumber).order((int) visits[tableNumber]);

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

        // Draw the chefs' workplaces
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 50, 150); // Prepchef
        g.fillRect(50, 0, 100, 50);
        g.fillRect(350, 50, 100, 100); // Garde Manger
        g.fillRect(450, 445, 50, 150); // Patissier
        g.fillRect(50, 300, 75, 200); // Sous chef

        // Draw tables
        drawTables(g);

        // Draw the getOrderWaiters
        drawWaiters(g);

        // Draw the chefs
        drawChefs(g);

        // Draw the prepChef
        drawPrepChef(g);

        // Draw master chef office
        g.setColor(new Color(93, 191, 73, 255));
        g.fillRect(410, 200, 60, 200);

        // Draw the master chef
        g.setColor(Color.BLUE);
        g.fillOval(masterChef.getX(), masterChef.getY(), masterChef.getDiameter(), masterChef.getDiameter());

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
        for (GetOrderWaiter getOrderWaiter : getOrderWaiters) {
            g.setColor(Color.BLACK);
            g.fillOval(getOrderWaiter.getX(), getOrderWaiter.getY(), getOrderWaiter.getDiameter(), getOrderWaiter.getDiameter()); // Draw circle with diameter of 50 pixels
            g.setColor(Color.WHITE);
            g.fillOval(getOrderWaiter.getX()+7, getOrderWaiter.getY()+7, getOrderWaiter.getDiameter()-14, getOrderWaiter.getDiameter()-14); // Draw circle with diameter of 50 pixels
        }
    }

    static void drawChefs(Graphics g){
        for (Chef chef : chefs) {
            g.setColor(Color.GRAY);
            g.fillOval(chef.getX(), chef.getY(), chef.getDiameter(), chef.getDiameter()); // Draw circle with diameter of 50 pixels
            g.setColor(chef.getColor());
            g.fillOval(chef.getX()+3, chef.getY()+3, chef.getDiameter()-6, chef.getDiameter()-6); // Draw circle with diameter of 50 pixels
        }
    }

    static void drawPrepChef(Graphics g){
        g.setColor(Color.GRAY);
        g.fillOval(prepChef.getX(), prepChef.getY(), prepChef.getDiameter(), prepChef.getDiameter()); // Draw circle with diameter of 50 pixels
        g.setColor(prepChef.getColor());
            g.fillOval(prepChef.getX()+3, prepChef.getY()+3, prepChef.getDiameter()-6, prepChef.getDiameter()-6); // Draw circle with diameter of 50 pixels
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

    public static void toString(ArrayList<String> a) {
        System.out.print("[");
        for (String element : a) {
            System.out.print(element + ", ");
        }
        System.out.print("]");
    }

}
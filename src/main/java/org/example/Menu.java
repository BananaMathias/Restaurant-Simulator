package org.example;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Singleton
 * The menu that the restaurant has
 * Consists of 3 items. The different keys refers to different food types
 * These food types corresponds to different chefs
 */
public class Menu {

    private static Menu menuInstance = null;
    private ArrayList<String> appetizers;
    private ArrayList<String> mainCourses;
    private ArrayList<String> desserts;
    private HashMap<Integer, ArrayList<String>> menuItems;

    /**
     * Constructor
     * Adds the different foods to the final HashMap
     */
    private Menu(){

        this.appetizers = new ArrayList<>();
        this.appetizers.add("Mushrooms");
        this.appetizers.add("Eggs");
        this.appetizers.add("Pickles");

        this.mainCourses = new ArrayList<>();
        this.mainCourses.add("Spaghetti carbonara");
        this.mainCourses.add("Meatballs");
        this.mainCourses.add("Joel");

        this.desserts = new ArrayList<>();
        this.desserts.add("Mathias");
        this.desserts.add("Chocolate");
        this.desserts.add("Ice cream");

        this.menuItems = new HashMap<>();
        this.menuItems.put(1, this.appetizers);
        this.menuItems.put(2, this.mainCourses);
        this.menuItems.put(3, this.desserts);
    }

    /**
     * Creates menu object
     * If a menu already exists it returns that one instead of creating a new one
     * @return new menu object or the already created one
     */
    public static Menu getInstance(){
        if (menuInstance == null){
            menuInstance = new Menu();
        }
        return menuInstance;
    }

    public HashMap<Integer, ArrayList<String>> getMenuItems() {
        return menuItems;
    }
}





package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu {

    private static Menu menuInstance = null;
    private ArrayList<String> appetizers;
    private ArrayList<String> mainCourses;
    private ArrayList<String> desserts;
    private HashMap<Integer, ArrayList<String>> menuItems;

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


    public static Menu getInstance(){
        if (menuInstance == null){
            menuInstance = new Menu();
        }
        return menuInstance;
    }

    public HashMap<Integer, ArrayList<String>> getMenuItems() {
        return menuItems;
    }

    // REMOVE SINGLETON
}





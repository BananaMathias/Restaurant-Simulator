package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    Chef gardemangerChef;

    @BeforeEach
    void setup(){
        gardemangerChef = new GardeMangerChef(0,0);
    }
    // Try if it works
    @Test
    void goToTest1(){
        while (true) {
            try {
                Thread.sleep(33); // With the goal of having about 30 fps.
            } catch (Exception threadException) {
                System.out.println("Sleep exception: " + threadException.getMessage());
            }
            gardemangerChef.targetX = 100;
            gardemangerChef.targetY = 100;

            if (gardemangerChef.goTo()){
                System.out.println("Passed 1");
                break;
            }
        }
    }
    // Test if negative values works
    @Test
    void goToTest2(){
        while (true) {
            try {
                Thread.sleep(33); // With the goal of having about 30 fps.
            } catch (Exception threadException) {
                System.out.println("Sleep exception: " + threadException.getMessage());
            }
            gardemangerChef.targetX = -100;
            gardemangerChef.targetY = -100;

            if (gardemangerChef.goTo()){
                System.out.println("Passed 2");
                break;
            }
        }
    }
    // Try if it works if you go to where you already are
    @Test
    void goToTest3(){
        while (true) {
            try {
                Thread.sleep(33);
            } catch (Exception threadException) {
                System.out.println("Sleep exception: " + threadException.getMessage());
            }
            gardemangerChef.targetX = 0;
            gardemangerChef.targetY = 0;

            if (gardemangerChef.goTo()){
                System.out.println("Passed 3");
                break;
            }
        }
    }
    // Test if it works when it stop in y position before x
    @Test
    void goToTest4(){
        while (true) {
            gardemangerChef.targetX = 200;
            gardemangerChef.targetY = 143;

            if (gardemangerChef.goTo()){
                System.out.println("Passed 4");
                break;
            }
        }
    }

    @Test
    void startCountingTest1(){
        // Tests the method used normally
        gardemangerChef.startCounting = true;
        gardemangerChef.startTimer(1000, "");

        // Tests if it works when startCounting is false
        gardemangerChef.startCounting = false;
        assertFalse(gardemangerChef.startTimer(100, "passed")); // If this prints out passed it passed
    }

    @Test
    void startCountingTest2() {
        // Tests if it works when period is negative
        while (true) {

            try{
                Thread.sleep(33);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            gardemangerChef.startCounting = false;
            gardemangerChef.startTimer(100, "passed"); // If this prints out passed it passed
            long thisTime = System.currentTimeMillis();
            if ((thisTime - gardemangerChef.lastTime) >= gardemangerChef.period) {
                assertFalse(gardemangerChef.startCounting);
                break;
                }
        }
    }
}

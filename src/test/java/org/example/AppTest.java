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

    @Test
    void testGoToNormally(){
        // Try if the function works
        while (true) {
            try {
                Thread.sleep(33); // With the goal of having about 30 fps.
            } catch (Exception threadException) {
                System.out.println("Sleep exception: " + threadException.getMessage());
            }
            gardemangerChef.targetX = 100;
            gardemangerChef.targetY = 100;

            if (gardemangerChef.goTo()){
                assertEquals(100, gardemangerChef.getX());
                assertEquals(100, gardemangerChef.getY());
                break;
            }
        }
    }

    @Test
    void testGoToNegative(){
        // Test if negative values works
        while (true) {
            try {
                Thread.sleep(33); // With the goal of having about 30 fps.
            } catch (Exception threadException) {
                System.out.println("Sleep exception: " + threadException.getMessage());
            }
            gardemangerChef.targetX = -100;
            gardemangerChef.targetY = -100;

            if (gardemangerChef.goTo()){
                assertEquals(-100, gardemangerChef.getX());
                assertEquals(-100, gardemangerChef.getY());
                break;
            }
        }
    }

    @Test
    void testGoToStandStill(){
        // Try if it works if you go to where you already are
        while (true) {
            try {
                Thread.sleep(33);
            } catch (Exception threadException) {
                System.out.println("Sleep exception: " + threadException.getMessage());
            }
            gardemangerChef.targetX = 0;
            gardemangerChef.targetY = 0;

            if (gardemangerChef.goTo()){
                assertEquals(0, gardemangerChef.getX());
                assertEquals(0, gardemangerChef.getY());
                break;
            }
        }
    }

    @Test
    void testGoToNotSameValues(){
        // Test if it works when it stop in y position before x
        while (true) {
            gardemangerChef.targetX = 200;
            gardemangerChef.targetY = 143;

            if (gardemangerChef.goTo()){
                assertEquals(200, gardemangerChef.getX());
                assertEquals(143,gardemangerChef.getY());
                break;
            }
        }
    }

    @Test
    void timerNormallyAndFalse(){
       // Test how the function is supposed to be used
        gardemangerChef.startCounting = true;
        while (true) {

            gardemangerChef.startTimer(1000, "");
            assertFalse(gardemangerChef.startCounting);
            long thisTime = System.currentTimeMillis();
            long timeGone = (thisTime - gardemangerChef.lastTime);
            if (timeGone >= gardemangerChef.period) {
                assertEquals(1000, timeGone);
                break;
            }
        }

        // Tests if it works when startCounting is false
        gardemangerChef.startCounting = false;
        assertFalse(gardemangerChef.startTimer(100, ""));
    }

    @Test
    void testNegativePeriod() {
        // Tests if it works when period is negative, It should not
        while (true) {

            try{
                Thread.sleep(33);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            gardemangerChef.startCounting = false;
            gardemangerChef.startTimer(-100, "");
            long thisTime = System.currentTimeMillis();
            long timeGone = (thisTime - gardemangerChef.lastTime);
            if (timeGone >= gardemangerChef.period) {
                assertNotEquals(-100, timeGone);
                break;
            }
        }
    }
}

package org.example;

public abstract class Walker {

    protected static long period; //one lap
    protected long lastTime;
    protected boolean startCounting = true;

    protected int x;
    protected int y;
    protected int targetX;
    protected int targetY;

    public Walker(int x, int y) {
        this.x = x;
        this.y = y;

    }

    protected boolean goTo() {
        boolean xArrived = false;
        boolean yArrived = false;

        // If Walker x-position is less than the targets x-position
        if (this.x < targetX) {
            // Adds the difference between target-x and Walker-x, with a max of adding 10
            this.x += Math.min(10, targetX - this.x);

            // If Walker x-position is greater than targets x-position
        } else if (this.x > targetX) {
            // Adds the difference between Walker-x and target-x, with a max of removing 10
            this.x -= Math.min(10, this.x - targetX);

            // If none of the above is true, the Walker is at its target's x-position
        } else {
            xArrived = true;
        }

        // If Walker y-position is less than the targets x-position
        if (this.y < targetY) {
            // Adds the difference between target-y and Walker-y, with a max of adding 5
            this.y += Math.min(5, targetY - this.y);

            // If Walker y-position is greater than targets y-position
        } else if (this.y > targetY) {
            // Adds the difference between Walker-y and target-y, with a max of removing 5
            this.y -= Math.min(5, this.y - targetY);

            // If none of the above is true, the Walker is at its target's y-position
        } else {
            yArrived = true;
        }
        // Returns true if the Walker has arrived at both x and y-coordinates
        return xArrived && yArrived;
    }

    protected void startTimer(int newPeriod, String action) {
        // If the Walker has its startCounting set as true
        if (startCounting) {
            // Sets the time that should be compared to in Walkers timer
            lastTime = System.currentTimeMillis();
            period = newPeriod;

            // Sets startCounting to false so that this method only runs once by itself
            startCounting = false;
            System.out.println(action);
        }
    }
}

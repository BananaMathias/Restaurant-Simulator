package org.example;

public abstract class Walker {

    protected int x;
    protected int y;
    protected int targetX;
    protected int targetY;

    public Walker(int x, int y){
        this.x = x;
        this.y = y;

    }
    protected boolean goTo(){
        boolean xArrived = false;
        boolean yArrived = false;

        if (this.x < targetX) {
            this.x += Math.min(10, targetX - this.x);
        } else if (this.x > targetX) {
            this.x -= Math.min(10, this.x - targetX);
        } else {
            xArrived = true;
        }


        if (this.y < targetY) {
            this.y += Math.min(5, targetY - this.y);
        } else if (this.y > targetY) {
            this.y -= Math.min(5, this.y - targetY);
        } else {
            yArrived = true;
        }

        return xArrived && yArrived;
    }
}

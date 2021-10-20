package io.jagiello;

import static io.jagiello.Map.MAP_WIDTH;

public class Player {
    private static final float STEP = 0.5f;

    private float x = 8.0f;
    private float y = 8.0f;
    private float angle = 0.0f;

    public void turnLeft(){
        angle -= (0.1f);
    }

    public void turnRight(){
        angle += (0.1f);
    }

    public void moveForward(StringBuffer map){
        x += Math.sin(angle) * STEP;
        y += Math.cos(angle) * STEP;

        if(map.charAt((int)y * MAP_WIDTH + (int)x) == '#'){
            x -= Math.sin(angle) * STEP;
            y -= Math.cos(angle) * STEP;
        }
    }

    public void moveBackward(StringBuffer map){
        x -= Math.sin(angle) * STEP;
        y -= Math.cos(angle) * STEP;

        if(map.charAt((int)y * MAP_WIDTH + (int)x) == '#'){
            x += Math.sin(angle) * STEP;
            y += Math.cos(angle) * STEP;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

}

package com.mlj.steeringdemo.unit;

import com.mlj.steeringdemo.geom.Vector;

public class Obstacle extends Entity {

    public Obstacle(int x, int y, double boundingRadius) {
        this.position = new Vector(x, y);
        this.boundingRadius = boundingRadius;
    }

}
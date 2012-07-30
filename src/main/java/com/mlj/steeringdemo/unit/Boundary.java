package com.mlj.steeringdemo.unit;

import com.mlj.steeringdemo.geom.Vector;

public class Boundary extends Entity {

    public Boundary(int x, int y, double boundingRadius) {
        this.position = new Vector(x, y);
        this.boundingRadius = boundingRadius;
    }

}
package com.mlj.steeringdemo.unit;

import com.mlj.steeringdemo.geom.Vector;

public class Wall {

    private Vector from;
    private Vector to;
    private Vector normal;

    public Wall(Vector from, Vector to) {
        this.from = from;
        this.to = to;
        calculateNormal();
    }

    public Wall(Vector from, Vector to, Vector normal) {
        this.from = from;
        this.to = to;
        this.normal = normal;
    }

    public Vector centre() {
        return from.add(to).dividedBy(2.0);
    }

    public Vector from() {
        return from;
    }

    public Vector to() {
        return to;
    }

    public Vector normal() {
        return normal;
    }

    private void calculateNormal() {
        Vector temp = to.subtract(from).normalise();
        normal = temp.normalise().perp();
    }

}

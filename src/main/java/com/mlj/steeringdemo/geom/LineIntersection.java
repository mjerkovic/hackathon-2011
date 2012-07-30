package com.mlj.steeringdemo.geom;

public class LineIntersection {

    private final boolean intersects;
    private final double distance;
    private final Vector intersectionPoint;

    public LineIntersection(boolean intersects, double distance, Vector intersectionPoint) {
        this.intersects = intersects;
        this.distance = distance;
        this.intersectionPoint = intersectionPoint;
    }

    public boolean intersects() {
        return intersects;
    }

    public double distance() {
        return distance;
    }

    public Vector intersectionPoint() {
        return intersectionPoint;
    }

}

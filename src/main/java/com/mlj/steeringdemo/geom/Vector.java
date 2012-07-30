package com.mlj.steeringdemo.geom;

import static com.mlj.steeringdemo.geom.Rotation.ANTI_CLOCKWISE;
import static com.mlj.steeringdemo.geom.Rotation.CLOCKWISE;

import javax.vecmath.Vector2d;

public class Vector {

    public static final Vector ZERO = new Vector(0, 0);

    private final Vector2d point;

    public Vector(double x, double y) {
        point = new Vector2d(x, y);
    }

    public Vector(Vector vec) {
        point = new Vector2d(vec.X(),  vec.Y());
    }

    private Vector(Vector2d vec) {
        point = new Vector2d(vec);
    }

    public Vector add(Vector vector) {
        Vector2d v = new Vector2d(point);
        v.add(vector.point);
        return new Vector(v);
    }

    public Vector scale(double multiplier) {
        Vector2d v = new Vector2d(point);
        v.scale(multiplier);
        return new Vector(v);
    }

    public double X() {
        return point.x;
    }

    public double Y() {
        return point.y;
    }

    public double length() {
        return point.length();
    }

    public double lengthSquared() {
        return point.lengthSquared();
    }

    public Vector normalise() {
        Vector2d v = new Vector2d(point);
        v.normalize();
        return new Vector(v);
    }

    public Vector dividedBy(double divisor) {
        return new Vector(point.x / divisor, point.y / divisor);
    }

    public Vector truncate(double max) {
        return (length() > max) ? normalise().scale(max): this;
    }

    public Vector perp() {
        return new Vector(-point.y, point.x);
    }

    public Vector subtract(Vector vector) {
        Vector2d v = new Vector2d(point);
        v.sub(vector.point);
        return new Vector(v);
    }

    @Override
    public String toString() {
        return String.format("[%f, %f]", point.x, point.y);
    }

    public boolean isZero() {
        return point.x == ZERO.X() && point.y == ZERO.Y();
    }

    public Vector reverse() {
        return new Vector(-point.x, -point.y);
    }

    public Rotation rotationTo(Vector vector) {
        return (point.y* vector.point.x > point.x * vector.point.y) ? ANTI_CLOCKWISE : CLOCKWISE;
    }

    public double dot(Vector vector) {
        return point.dot(vector.point);
    }

    public double angle(Vector vector) {
        return point.angle(vector.point);
    }

}

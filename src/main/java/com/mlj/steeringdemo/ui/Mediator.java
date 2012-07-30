package com.mlj.steeringdemo.ui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.world.World;

public class Mediator {

    private final World world;

    public Mediator(World world) {
        this.world = world;
    }

    public void update() {
        world.update();
    }

    public void renderUsing(Graphics2D graphics) {
        world.renderWith(graphics);
    }

    public void moveTo(Point2D position) {
        world.moveTo(new Vector(position.getX(), position.getY()));
    }

    public void fleeFrom(Point point) {
        world.fleeFrom(new Vector(point.getX(), point.getY()));
    }

    public void arriveAt(Point point) {
        world.arriveAt(new Vector(point.getX(), point.getY()));
    }
}

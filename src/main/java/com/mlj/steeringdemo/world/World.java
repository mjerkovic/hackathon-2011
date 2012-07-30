package com.mlj.steeringdemo.world;

import java.awt.Graphics2D;
import java.util.Collection;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.Obstacle;
import com.mlj.steeringdemo.unit.Vehicle;
import com.mlj.steeringdemo.unit.Wall;

public interface World {

    void update();

    void renderWith(Graphics2D graphics);

    //void moveVehicle(HandlerProcessor.Direction direction);

    void moveTo(Vector position);

    Collection<Obstacle> getObstacles();

    Collection<Wall> getWalls();

    Collection<Vehicle> getVehicles();

    void fleeFrom(Vector position);

    void arriveAt(Vector position);

}

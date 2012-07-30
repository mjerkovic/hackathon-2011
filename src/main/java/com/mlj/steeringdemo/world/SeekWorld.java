package com.mlj.steeringdemo.world;

import java.awt.Graphics2D;
import java.util.Collection;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.ui.Renderer;
import com.mlj.steeringdemo.unit.Obstacle;
import com.mlj.steeringdemo.unit.Vehicle;
import com.mlj.steeringdemo.unit.Wall;

public class SeekWorld implements World {

    private Vehicle vehicle;
    private Vector selectedPosition;

    public SeekWorld() {
        this.vehicle = new Vehicle(this, new Vector(400, 400), new Vector(0, -1), 4);
    }

    public void update() {
        vehicle.update();
    }

    public void renderWith(Graphics2D graphics) {
        Renderer renderer = new Renderer(Renderer.DisplaySize.ZOOM, graphics);
        if (selectedPosition != null) {
            renderer.render(selectedPosition);
        }
        renderer.render(vehicle);
    }

    public void moveTo(Vector position) {
        selectedPosition = position;
        vehicle.steering().seekOn(position);
    }

    public void fleeFrom(Vector position) {
        selectedPosition = position;
        vehicle.steering().fleeOn(position);
    }

    public void arriveAt(Vector position) {
        selectedPosition = position;
        vehicle.steering().arriveOn(position);
    }

    public Collection<Obstacle> getObstacles() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Wall> getWalls() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Vehicle> getVehicles() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}

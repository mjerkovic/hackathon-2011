package com.mlj.steeringdemo.world;

import static com.google.common.collect.Lists.newArrayList;

import java.awt.Graphics2D;
import java.util.Collection;
import java.util.Random;

import com.google.common.collect.Lists;
import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.ui.Renderer;
import com.mlj.steeringdemo.unit.Obstacle;
import com.mlj.steeringdemo.unit.Vehicle;
import com.mlj.steeringdemo.unit.Wall;

public class WanderWorld implements World {

    private Random rand = new Random();
    private Collection<Vehicle> vehicles = Lists.newArrayList();
    private final Collection<Wall> walls = newArrayList();
    private boolean showWallNormals;
    private boolean showFeelers;

    public WanderWorld(int numberOfVehicles) {
        addWalls();
        createVehicles(numberOfVehicles);
    }

    public void update() {
        for (Vehicle v : vehicles) {
            v.update();
        }
    }

    public void renderWith(Graphics2D graphics) {
        Renderer renderer = new Renderer(Renderer.DisplaySize.ZOOM, graphics, showWallNormals, showFeelers);
        for (Wall wall : walls) {
            renderer.render(wall);
        }
        for (Vehicle v : vehicles) {
            renderer.render(v);
        }
    }

    private void addWalls() {
        walls.add(new Wall(new Vector(0, 0), new Vector(790, 0)));
        walls.add(new Wall(new Vector(790, 0), new Vector(790, 790)));
        walls.add(new Wall(new Vector(790, 790), new Vector(0, 790)));
        walls.add(new Wall(new Vector(0, 790), new Vector(0, 0)));
    }

    private void createVehicles(int numberOfVehicles) {
        for (int i = 0; i < numberOfVehicles; i++) {
            vehicles.add(createVehicle());
        }
    }

    private Vehicle createVehicle() {
        Vector pos = new Vector(rand.nextDouble(), rand.nextDouble()).scale(800);
        Vector heading = new Vector(generateCoordinate(), generateCoordinate());
        Vehicle v = new Vehicle(this, pos, heading, 5);
        v.steering().wanderOn(4, 4, 80).wallAvoidanceOn();
        return v;
    }

    private double generateCoordinate() {
        return rand.nextDouble() - rand.nextDouble();
    }

    public void moveTo(Vector position) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Obstacle> getObstacles() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Wall> getWalls() {
        return walls;
    }

    public Collection<Vehicle> getVehicles() {
        return null;
    }

    public void fleeFrom(Vector position) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void arriveAt(Vector position) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void toggleWallNormals() {
        showWallNormals = !showWallNormals;
    }

    public void toggleFeelers() {
        showFeelers = !showFeelers;
    }
}

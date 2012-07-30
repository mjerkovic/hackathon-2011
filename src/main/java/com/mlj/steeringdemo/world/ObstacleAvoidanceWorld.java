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

public class ObstacleAvoidanceWorld implements World {

    private Random rand = new Random();
    private Collection<Vehicle> vehicles = Lists.newArrayList();
    private final Collection<Wall> walls = newArrayList();
    private final Collection<Obstacle> obstacles = newArrayList();

    public ObstacleAvoidanceWorld(int numberOfVehicles) {
        addObstacles();
        addWalls();
        createVehicles(numberOfVehicles);
    }

    public void update() {
        for (Vehicle v : vehicles) {
            v.update();
        }
    }

    public void renderWith(Graphics2D graphics) {
        Renderer renderer = new Renderer(Renderer.DisplaySize.ZOOM, graphics);
        for (Wall wall : walls) {
            renderer.render(wall);
        }
        for (Obstacle obstacle : obstacles) {
            renderer.render(obstacle);
        }
        for (Vehicle v : vehicles) {
            renderer.render(v);
        }
    }

    private void addObstacles() {
        for (int i=0; i < 8; i++) {
            double radius = 10 + (rand.nextDouble() * 40);
            int x = generateObstacleCoordinate(radius);
            int y = generateObstacleCoordinate(radius);
            obstacles.add(new Obstacle(x, y, radius));
        }
    }

    private int generateObstacleCoordinate(double radius) {
        int result = 0;
        do {
            result = rand.nextInt(800);
        } while (result - radius < 0 || result + radius > 800);
        return result;
    }

    private void addWalls() {
        walls.add(new Wall(new Vector(0, 0), new Vector(790, 0)));
        walls.add(new Wall(new Vector(790, 0), new Vector(790, 790)));
        walls.add(new Wall(new Vector(790, 790), new Vector(0, 790)));
        walls.add(new Wall(new Vector(0, 790), new Vector(0, 0)));
    }

    private void createVehicles(int numberOfVehicles) {
        for (int i = 0; i < numberOfVehicles; i++) {
            Vector pos = new Vector(rand.nextDouble(), rand.nextDouble()).scale(800);
            Vector heading = new Vector(generateCoordinate(), generateCoordinate());
            Vehicle v = new Vehicle(this, pos, heading, 4);
            v.steering().wanderOn(4, 4, 80).wallAvoidanceOn().obstacleAvoidanceOn();
            vehicles.add(v);
        }
    }

    private double generateCoordinate() {
        return rand.nextDouble() - rand.nextDouble();
    }

    public void moveTo(Vector position) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Obstacle> getObstacles() {
        return obstacles;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Wall> getWalls() {
        return walls;
    }

    public Collection<Vehicle> getVehicles() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void fleeFrom(Vector position) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void arriveAt(Vector position) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}

package com.mlj.steeringdemo.world;

import static com.google.common.collect.Lists.newArrayList;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collection;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.steering.Direction;
import com.mlj.steeringdemo.ui.Renderer;
import com.mlj.steeringdemo.unit.Obstacle;
import com.mlj.steeringdemo.unit.Vehicle;
import com.mlj.steeringdemo.unit.Wall;

public class PursuitWorld implements World {

    private Vehicle prey1;
    private Vehicle hunter;
    private final Collection<Wall> walls = newArrayList();
    private final Collection<Obstacle> obstacles = newArrayList();
    private final Collection<Vehicle> prey = newArrayList();
    private Vehicle shonkyPursuer;
    private boolean shonkyPursuit;

    public void pursuit() {
        this.prey1 = new Vehicle(this, new Vector(780, 200), new Vector(-1, 0), 4);
        prey1.steering().arriveOn(new Vector(10, 200));
        prey.add(prey1);
        this.hunter = new Vehicle(this, new Vector(500, 600), new Vector(0, -1), 6);
        hunter.steering().pursuitOn(prey1);
        this.shonkyPursuer = new Vehicle(this, new Vector(500, 600), new Vector(0, -1), 6);
    }

    public void hide() {
        addWalls();
        obstacles.add(new Obstacle(200, 200, 70));
        obstacles.add(new Obstacle(400, 400, 80));
        obstacles.add(new Obstacle(600, 600, 60));
        this.hunter = new Vehicle(this, new Vector(700, 30), new Vector(0, -1), 4);
        hunter.steering().wallAvoidanceOn().obstacleAvoidanceOn();
        this.prey1 = new Vehicle(this, new Vector(20, 400), new Vector(1, 0), 5);
        prey1.steering().wallAvoidanceOn().obstacleAvoidanceOn().hideOn(hunter);
        prey.add(prey1);

        Vehicle prey2 = new Vehicle(this, new Vector(400, 600), new Vector(1, 0), 5);
        prey2.steering().wallAvoidanceOn().obstacleAvoidanceOn().hideOn(hunter);
        prey.add(prey2);

        Vehicle prey3 = new Vehicle(this, new Vector(680, 580), new Vector(1, 0), 5);
        prey3.steering().wallAvoidanceOn().obstacleAvoidanceOn().hideOn(hunter);
        prey.add(prey3);
    }

    public void sneak() {
        addWalls();
        obstacles.add(new Obstacle(200, 200, 70));
        obstacles.add(new Obstacle(400, 400, 80));
        obstacles.add(new Obstacle(600, 600, 60));
        this.prey1 = new Vehicle(this, new Vector(100, 100), new Vector(-1, 0), 5);
        prey.add(prey1);
        this.hunter = new Vehicle(this, new Vector(700, 700), new Vector(0, -1), 5);
        hunter.steering().wallAvoidanceOn().obstacleAvoidanceOn().hideOn(prey1).pursuitOn(prey1);
    }

    private void addWalls() {
        walls.add(new Wall(new Vector(0, 0), new Vector(790, 0)));
        walls.add(new Wall(new Vector(790, 0), new Vector(790, 790)));
        walls.add(new Wall(new Vector(790, 790), new Vector(0, 790)));
        walls.add(new Wall(new Vector(0, 790), new Vector(0, 0)));
    }

    public void update() {
        for (Vehicle v : prey) {
            v.update();
        }
        if (shonkyPursuer != null) {
            shonkyPursuer.steering().seekOn(prey1.position());
            shonkyPursuer.update();
        }
        hunter.update();
    }

    public void renderWith(Graphics2D graphics) {
        Renderer renderer = new Renderer(Renderer.DisplaySize.ZOOM, graphics);
        for (Obstacle obstacle : obstacles) {
            renderer.render(obstacle);
        }
        for (Vehicle v : prey) {
            renderer.render(v, Color.BLUE);
        }
        if (shonkyPursuit) {
            renderer.render(shonkyPursuer, Color.GREEN);
        }
        renderer.render(hunter);
    }

    public void moveTo(Vector position) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void move(Direction direction) {
        hunter.steering().directionOn(direction);
    }

    public Collection<Obstacle> getObstacles() {
        return obstacles;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Wall> getWalls() {
        return walls;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Vehicle> getVehicles() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void fleeFrom(Vector position) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void arriveAt(Vector position) {
        hunter.steering().arriveOn(position);
    }

    public void toggleShonkyPursuit() {
        shonkyPursuit = !shonkyPursuit;
    }
}

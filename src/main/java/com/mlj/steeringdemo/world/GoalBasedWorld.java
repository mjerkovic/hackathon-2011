package com.mlj.steeringdemo.world;

import static com.google.common.collect.Lists.newArrayList;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.Collections;

import com.google.common.collect.Lists;
import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.goals.Guard;
import com.mlj.steeringdemo.steering.Direction;
import com.mlj.steeringdemo.ui.Renderer;
import com.mlj.steeringdemo.unit.Boundary;
import com.mlj.steeringdemo.unit.Obstacle;
import com.mlj.steeringdemo.unit.Vehicle;
import com.mlj.steeringdemo.unit.Wall;

public class GoalBasedWorld implements World {

    private Vehicle human;
    private Vehicle guard;
    private Obstacle potOfGold;
    private Boundary guardRange;
    private final Collection<Wall> walls = newArrayList();

    public GoalBasedWorld() {
        addWalls();
        potOfGold = new Obstacle(400, 400, 15);
        guardRange = new Boundary(400, 400, 200);
        this.human = new Vehicle(this, new Vector(780, 400), new Vector(-1, 0), 3);
        Collection<Vector> waypoints = Lists.newArrayList(new Vector(450, 375), new Vector(450, 425),
                new Vector(350, 425), new Vector(350, 375));
        this.guard = new Vehicle(this, new Vector(350, 375), new Vector(1, 0), new Guard(this, potOfGold.position(), waypoints));
    }

    private void addWalls() {
        walls.add(new Wall(new Vector(0, 0), new Vector(790, 0)));
        walls.add(new Wall(new Vector(790, 0), new Vector(790, 790)));
        walls.add(new Wall(new Vector(790, 790), new Vector(0, 790)));
        walls.add(new Wall(new Vector(0, 790), new Vector(0, 0)));
    }

    public void update() {
        guard.update();
        human.update();
    }

    public void renderWith(Graphics2D graphics) {
        Renderer renderer = new Renderer(Renderer.DisplaySize.ZOOM, graphics);
        renderer.renderPointOfInterest(potOfGold);
        renderer.render(guardRange);
        renderer.render(human, Color.BLUE);
        renderer.render(guard);
    }

    public void moveTo(Vector position) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void move(Direction direction) {
        human.steering().directionOn(direction);
    }

    public Collection<Obstacle> getObstacles() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Wall> getWalls() {
        return walls;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Vehicle> getVehicles() {
        return Collections.singletonList(human);
    }

    public void fleeFrom(Vector position) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void arriveAt(Vector position) {
        human.steering().arriveOn(position);
    }
}

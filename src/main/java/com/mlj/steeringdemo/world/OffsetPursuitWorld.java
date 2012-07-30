package com.mlj.steeringdemo.world;

import static com.google.common.collect.Lists.newArrayList;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collection;

import com.google.common.collect.Lists;
import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.ui.Renderer;
import com.mlj.steeringdemo.unit.Obstacle;
import com.mlj.steeringdemo.unit.Vehicle;
import com.mlj.steeringdemo.unit.Wall;

public class OffsetPursuitWorld implements World {

    private static final Vector OFFSET = new Vector(30, 20);

    private Vehicle leader;
    private Collection<Vehicle> squad = newArrayList();
    private final Collection<Wall> walls = Lists.newArrayList();

    public OffsetPursuitWorld() {
        addWalls();
        this.leader = new Vehicle(this, new Vector(50, 600), new Vector(1, -1), 4.0);
        leader.steering().wallAvoidanceOn().wanderOn(2, 4, 80);

        Vehicle first = new Vehicle(this, new Vector(20, 620), new Vector(1, 0), 8);
        first.steering().wallAvoidanceOn().offsetPursuitOn(leader, new Vector(-1, 25));
        squad.add(first);
        Vehicle second = new Vehicle(this, new Vector(30, 650), new Vector(1, 0), 8);
        second.steering().wallAvoidanceOn().offsetPursuitOn(leader, new Vector(-1, -25));
        squad.add(second);
        Vehicle third = new Vehicle(this, new Vector(30, 700), new Vector(1, 0), 8);
        third.steering().wallAvoidanceOn().offsetPursuitOn(first, new Vector(-1, 25));
        squad.add(third);
        Vehicle fourth = new Vehicle(this, new Vector(40, 710), new Vector(1, 0), 8);
        fourth.steering().wallAvoidanceOn().offsetPursuitOn(second, new Vector(-1, -25));
        squad.add(fourth);
        Vehicle fifth = new Vehicle(this, new Vector(20, 620), new Vector(1, 0), 8);
        fifth.steering().wallAvoidanceOn().offsetPursuitOn(leader, new Vector(-1, 0));
        squad.add(fifth);
        Vehicle sixth = new Vehicle(this, new Vector(20, 620), new Vector(1, 0), 8);
        sixth.steering().wallAvoidanceOn().offsetPursuitOn(fifth, new Vector(-1, -25));
        squad.add(sixth);
        Vehicle seventh = new Vehicle(this, new Vector(20, 620), new Vector(1, 0), 8);
        seventh.steering().wallAvoidanceOn().offsetPursuitOn(fifth, new Vector(-1, 25));
        squad.add(seventh);
        Vehicle eighth = new Vehicle(this, new Vector(20, 620), new Vector(1, 0), 8);
        eighth.steering().wallAvoidanceOn().offsetPursuitOn(fifth, new Vector(-1, 0));
        squad.add(eighth);
    }

    public void update() {
        leader.update();
        for (Vehicle follower : squad) {
            follower.update();
        }
    }

    public void renderWith(Graphics2D graphics) {
        Renderer renderer = new Renderer(Renderer.DisplaySize.ZOOM, graphics);
        renderer.render(leader, Color.BLUE);
        for (Vehicle follower : squad) {
            renderer.render(follower);
        }
    }

    private void addWalls() {
        walls.add(new Wall(new Vector(0, 0), new Vector(790, 0)));
        walls.add(new Wall(new Vector(790, 0), new Vector(790, 790)));
        walls.add(new Wall(new Vector(790, 790), new Vector(0, 790)));
        walls.add(new Wall(new Vector(0, 790), new Vector(0, 0)));
    }

    public void moveTo(Vector position) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Obstacle> getObstacles() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

package com.mlj.steeringdemo.unit;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.goals.Goal;
import com.mlj.steeringdemo.world.World;

public class Vehicle extends MovingEntity {

    public Vehicle(World world, Vector position, Vector heading) {
        super(world);
        this.position = position;
        this.heading = heading;
    }

    public Vehicle(World world, Vector position, Vector heading, Goal goal) {
        super(world, goal);
        this.position = position;
        this.heading = heading;
    }

    public Vehicle(World world, Vector position, Vector heading, double maxSpeed) {
        this(world, position, heading);
        this.maxSpeed = maxSpeed;
    }

    public double X() {
        return position.X();
    }

    public double Y() {
        return position.Y();
    }

}

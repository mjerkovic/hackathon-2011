package com.mlj.steeringdemo.steering;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;

public class Interpose implements SteeringBehaviour {

    private final MovingEntity entity;
    private final MovingEntity vehicleA;
    private final MovingEntity vehicleB;

    public Interpose(MovingEntity entity, MovingEntity vehicleA, MovingEntity vehicleB) {
        this.entity = entity;
        this.vehicleA = vehicleA;
        this.vehicleB = vehicleB;
    }

    public Vector calculate() {
        Vector midPoint = vehicleA.position().add(vehicleB.position()).dividedBy(2);
        double timeToReachMidPoint = midPoint.subtract(entity.position()).length() / entity.getMaxSpeed();
        Vector aPos = vehicleA.position().add(vehicleA.velocity().scale(timeToReachMidPoint));
        Vector bPos = vehicleB.position().add(vehicleB.velocity().scale(timeToReachMidPoint));
        midPoint = aPos.add(bPos).dividedBy(2);
        return new Arrive(entity, midPoint).calculate();
    }

}

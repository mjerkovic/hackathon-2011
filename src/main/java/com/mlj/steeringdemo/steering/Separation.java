package com.mlj.steeringdemo.steering;

import java.util.Collection;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;
import com.mlj.steeringdemo.unit.Vehicle;

public class Separation implements SteeringBehaviour {

    private final MovingEntity entity;
    private final Collection<Vehicle> neighbours;

    public Separation(MovingEntity entity, Collection<Vehicle> neighbours) {
        this.entity = entity;
        this.neighbours = neighbours;
    }

    public Vector calculate() {
        Vector steeringForce = Vector.ZERO;
        for (Vehicle neighbour : neighbours) {
            Vector to = entity.position().subtract(neighbour.position());
            steeringForce = steeringForce.add(to.normalise().dividedBy(to.length()));
        }
        return steeringForce;
    }

}


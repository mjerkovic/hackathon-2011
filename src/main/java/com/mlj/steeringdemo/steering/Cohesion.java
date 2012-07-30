package com.mlj.steeringdemo.steering;

import java.util.Collection;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;
import com.mlj.steeringdemo.unit.Vehicle;

public class Cohesion implements SteeringBehaviour {

    private final MovingEntity entity;
    private final Collection<Vehicle> neighbours;

    public Cohesion(MovingEntity entity, Collection<Vehicle> neighbours) {
        this.entity = entity;
        this.neighbours = neighbours;
    }

    public Vector calculate() {
        Vector steeringForce = Vector.ZERO;
        Vector centreOfMass = Vector.ZERO;
        for (Vehicle neighbour : neighbours) {
            centreOfMass = centreOfMass.add(neighbour.position());
        }
        if (neighbours.size() > 0) {
            centreOfMass = centreOfMass.dividedBy(neighbours.size());
            steeringForce = new Seek(entity, centreOfMass).calculate();
        }
        return steeringForce;
    }

}

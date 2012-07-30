package com.mlj.steeringdemo.steering;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;

public class Seek implements SteeringBehaviour {

    private final MovingEntity entity;
    private final Vector position;

    public Seek(MovingEntity entity, Vector position) {
        this.entity = entity;
        this.position = position;
    }

    public Vector calculate() {
        Vector desiredVelocity = position.subtract(entity.position()).normalise().scale(entity.getMaxSpeed());
        return desiredVelocity.subtract(entity.velocity());
    }

}

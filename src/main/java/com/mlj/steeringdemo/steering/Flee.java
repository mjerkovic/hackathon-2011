package com.mlj.steeringdemo.steering;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;

public class Flee implements SteeringBehaviour {

    private final MovingEntity entity;
    private final Vector position;

    public Flee(MovingEntity entity, Vector position) {
        this.entity = entity;
        this.position = position;
    }

    public Vector calculate() {
        Vector desiredVelocity = entity.position().subtract(position).normalise().scale(entity.getMaxSpeed());
        return desiredVelocity.subtract(entity.velocity());
    }
}

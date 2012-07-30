package com.mlj.steeringdemo.steering;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;

public class Evade implements SteeringBehaviour {

    private final MovingEntity entity;
    private final MovingEntity pursuer;

    public Evade(MovingEntity entity, MovingEntity pursuer) {
        this.entity = entity;
        this.pursuer = pursuer;
    }

    public Vector calculate() {
        Vector toPursuer = pursuer.position().subtract(entity.position());
        double lookAheadTime = toPursuer.length() / (entity.getMaxSpeed() + pursuer.velocity().length());
        return new Flee(entity, pursuer.position().add(pursuer.velocity().scale(lookAheadTime))).calculate();
    }

}

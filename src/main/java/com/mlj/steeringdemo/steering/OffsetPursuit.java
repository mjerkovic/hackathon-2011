package com.mlj.steeringdemo.steering;

import static com.mlj.steeringdemo.geom.Transformations.pointToWorldSpace;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;

public class OffsetPursuit implements SteeringBehaviour {

    private final MovingEntity entity;
    private final MovingEntity leader;
    private final Vector offset;

    public OffsetPursuit(MovingEntity entity, MovingEntity leader, Vector offset) {
        this.entity = entity;
        this.leader = leader;
        this.offset = offset;
    }

    public Vector calculate() {
        Vector worldOffsetPos = pointToWorldSpace(offset, leader.heading(), leader.side(), leader.position());
        Vector toOffset = worldOffsetPos.subtract(entity.position());
        double lookAheadTime = toOffset.length() / (entity.getMaxSpeed() + leader.velocity().length());
        return new Arrive(entity, worldOffsetPos.add(leader.velocity().scale(lookAheadTime))).calculate();
    }

}
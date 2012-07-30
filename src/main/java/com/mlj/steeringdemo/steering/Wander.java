package com.mlj.steeringdemo.steering;

import static com.mlj.steeringdemo.geom.Transformations.pointToWorldSpace;
import static java.lang.Math.random;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;

public class Wander implements SteeringBehaviour {

    private static final double WANDER_RADIUS = 2.0;
    private static final double WANDER_DISTANCE = 4.0;
    private static final double WANDER_JITTER = 80.0;

    private final MovingEntity entity;
    private final double wanderRadius;
    private final double wanderDistance;
    private final double wanderJitter;

    public Wander(MovingEntity entity, double wanderRadius, double wanderDistance, double wanderJitter) {
        this.entity = entity;
        this.wanderRadius = wanderRadius;
        this.wanderDistance = wanderDistance;
        this.wanderJitter = wanderJitter;
    }

    public Vector calculate() {
        Vector wanderTarget = new Vector(randomNumber(), randomNumber()).normalise().scale(wanderRadius);
        Vector targetLocal = wanderTarget.add(new Vector(wanderDistance, 0));
        Vector targetWorld = pointToWorldSpace(targetLocal, entity.heading(), entity.side(),
                entity.position());
        return targetWorld.subtract(entity.position());
    }

    private double randomNumber() {
        return (random() - random()) * wanderJitter;
    }

}

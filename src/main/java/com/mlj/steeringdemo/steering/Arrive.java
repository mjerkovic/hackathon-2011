package com.mlj.steeringdemo.steering;

import static java.lang.Math.min;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;

public class Arrive implements SteeringBehaviour {

    private static enum Arrival {
        FAST(1),
        NORMAL(2),
        SLOW(3);

        private double deceleration;

        private Arrival(double deceleration) {
            this.deceleration = deceleration;
        }

        public double getDeceleration() {
            return deceleration;
        }

    }

    private final MovingEntity entity;
    private final Vector position;

    public Arrive(MovingEntity entity, Vector position) {
        this.entity = entity;
        this.position = position;
    }

    public Vector calculate() {
        Vector toTarget = position.subtract(entity.position());
        double dist = toTarget.length();
        if (dist > 0) {
            double tweaker = 20;
            double speed = dist / ((Arrival.FAST.getDeceleration() * tweaker));
            speed = min(speed, entity.getMaxSpeed());
            Vector desiredVelocity = toTarget.scale(speed / dist);
            return desiredVelocity.subtract(entity.velocity());
        }
        return Vector.ZERO;
    }
}

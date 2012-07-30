package com.mlj.steeringdemo.steering;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;

public class Pursuit {

    private final MovingEntity pursuer;
    private final MovingEntity evader;

    public Pursuit(MovingEntity pursuer, MovingEntity evader) {
        this.pursuer = pursuer;
        this.evader = evader;
    }

    public Vector calculate() {
        Vector toEvader = evader.position().subtract(pursuer.position());
        double relativeHeading = pursuer.heading().dot(evader.heading());
        if ((toEvader.dot(pursuer.heading()) > 0) && (relativeHeading < -0.95)) {
            return new Seek(pursuer, evader.position()).calculate();
        }
        double lookAheadTime = toEvader.length() / (pursuer.getMaxSpeed() + evader.velocity().length());
        return new Seek(pursuer, evader.position().add(evader.velocity().scale(lookAheadTime))).calculate();
    }

}

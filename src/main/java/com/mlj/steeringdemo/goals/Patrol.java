package com.mlj.steeringdemo.goals;

import static com.mlj.steeringdemo.goals.GoalState.INACTIVE;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;

public class Patrol extends SimpleGoal<MovingEntity> {

    private static final double RADIUS = 64;

    private final Vector waypoint;

    public Patrol(Vector waypoint) {
        this.waypoint = waypoint;
    }

    @Override
    protected void doActivation(MovingEntity entity) {
        entity.steering().seekOn(waypoint);
    }

    public GoalState doProcess(MovingEntity entity) {
        activate(entity);
        Vector distance = entity.position().subtract(waypoint);
        return distance.lengthSquared() < RADIUS ? GoalState.COMPLETED : GoalState.ACTIVE;
    }

    public void terminate(MovingEntity entity) {
        entity.steering().seekOff();
        state = INACTIVE;
    }
}

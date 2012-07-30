package com.mlj.steeringdemo.goals;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;

public class Chase extends SimpleGoal<MovingEntity> {

    private final MovingEntity target;
    private final Vector origin;

    public Chase(MovingEntity target, Vector origin) {
        this.target = target;
        this.origin = origin;
    }

    @Override
    protected void doActivation(MovingEntity entity) {
        entity.steering().pursuitOn(target);
    }

    public GoalState doProcess(MovingEntity entity) {
        return origin.subtract(target.position()).lengthSquared() >= 40000 ? GoalState.COMPLETED : GoalState.ACTIVE;
    }

    public void terminate(MovingEntity entity) {
        entity.steering().pursuitOff();
    }

}

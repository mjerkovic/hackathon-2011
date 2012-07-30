package com.mlj.steeringdemo.goals;

import static com.mlj.steeringdemo.goals.GoalState.ACTIVE;
import static com.mlj.steeringdemo.goals.GoalState.INACTIVE;

public abstract class SimpleGoal<T> implements Goal<T> {

    protected GoalState state = INACTIVE;

    public void addSubGoal(Goal<T> tGoal) {
        throw new UnsupportedOperationException("A SimpleGoal cannot have sub-goals");
    }

    public final void activate(T entity) {
        if (state.equals(INACTIVE)) {
            doActivation(entity);
            state = ACTIVE;
        }
    }

    public final GoalState process(T entity) {
        activate(entity);
        return doProcess(entity);
    }

    protected abstract void doActivation(T entity);

    protected abstract GoalState doProcess(T entity);

}

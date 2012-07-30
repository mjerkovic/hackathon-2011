package com.mlj.steeringdemo.goals;

import static com.mlj.steeringdemo.goals.GoalState.ACTIVE;
import static com.mlj.steeringdemo.goals.GoalState.COMPLETED;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class CompositeGoal<T> implements Goal<T> {

    private final Deque<Goal<T>> subGoals = new ArrayDeque<Goal<T>>();
    protected GoalState state = GoalState.INACTIVE;

    public GoalState process(T entity) {
        return processSubGoals(entity);
    }

    public void addSubGoal(Goal<T> goal) {
        subGoals.push(goal);
    }

    protected void addSubGoalToEnd(Goal<T> goal) {
        subGoals.add(goal);
    }

    protected Goal<T> currentGoal() {
        return subGoals.peek();
    }

    protected GoalState processSubGoals(T entity) {
        activate(entity);
        if (subGoalsAreEmpty()) {
            return COMPLETED;
        }
        GoalState subGoalState = currentGoal().process(entity);
        if (subGoalState.equals(COMPLETED)) {
            currentGoal().terminate(entity);
            subGoals.removeFirst();
        }
        return (!subGoals.isEmpty()) ? ACTIVE : subGoalState;
    }

    protected boolean subGoalsAreEmpty() {
        return subGoals.isEmpty();
    }

}

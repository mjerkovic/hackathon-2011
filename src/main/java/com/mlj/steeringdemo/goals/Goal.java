package com.mlj.steeringdemo.goals;

public interface Goal<T> {

    GoalState process(T entity);

    void addSubGoal(Goal<T> goal);

    void terminate(T entity);

    void activate(T entity);

}

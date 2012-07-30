package com.mlj.steeringdemo.goals;

import java.util.Collection;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;
import com.mlj.steeringdemo.unit.Vehicle;
import com.mlj.steeringdemo.world.World;

public class Guard extends CompositeGoal<MovingEntity> {

    private static final double VIEW_RANGE = 40000; //22500; // 150 pixels squared
    private final World world;
    private final Vector potOfGold;
    private final Collection<Vector> waypoints;

    public Guard(World world, Vector potOfGold, Collection<Vector> waypoints) {
        this.world = world;
        this.potOfGold = potOfGold;
        this.waypoints = waypoints;
    }

    public void activate(MovingEntity entity) {
        if (subGoalsAreEmpty()) {
            for (Vector waypoint : waypoints) {
                addSubGoalToEnd(new Patrol(waypoint));
            }
        }
        MovingEntity closestTarget = findClosestTarget();
            if (closestTarget != null && !(currentGoal() instanceof Chase)) {
                currentGoal().terminate(entity);
                addSubGoal(new Chase(closestTarget, potOfGold));
            }
        }

    private MovingEntity findClosestTarget() {
        double distanceSquared = Double.MAX_VALUE;
        MovingEntity closestTarget = null;
        for (Vehicle vehicle : world.getVehicles()) {
            Vector distanceToVehicle = vehicle.position().subtract(potOfGold);
            if (distanceToVehicle.lengthSquared() < VIEW_RANGE && distanceToVehicle.lengthSquared() < distanceSquared) {
                distanceSquared = distanceToVehicle.lengthSquared();
                closestTarget = vehicle;
            }
        }
        return closestTarget;
    }

    public void terminate(MovingEntity entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

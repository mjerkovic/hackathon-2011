package com.mlj.steeringdemo.steering;

import static com.mlj.steeringdemo.geom.Transformations.pointToLocalSpace;
import static com.mlj.steeringdemo.geom.Transformations.vectorToWorldSpace;

import java.util.ArrayList;
import java.util.Collection;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.Entity;
import com.mlj.steeringdemo.unit.MovingEntity;
import com.mlj.steeringdemo.unit.Obstacle;

public class ObstacleAvoidance implements SteeringBehaviour {

    private final MovingEntity entity;
    private final Collection<Obstacle> obstacles;

    public ObstacleAvoidance(MovingEntity entity, Collection<Obstacle> obstacles) {
        this.entity = entity;
        this.obstacles = obstacles;
    }

    public Vector calculate() {
        double boxLength = 30;
        Entity closestObstacle = null;
        double distanceToClosestIntersectingPoint = Double.MAX_VALUE;
        Vector localPositionOfClosestObstacle = null;

        for (Obstacle obstacle : tagObstaclesWithinViewRange(boxLength)) {
            Vector localPos = pointToLocalSpace(obstacle.position(), entity.heading(), entity.side(), entity.position());
            if (localPos.X() <  0) {
                continue;
            }
            double expandedRadius = obstacle.boundingRadius() + entity.boundingRadius();
            if (Math.abs(localPos.Y()) < expandedRadius) {
                double cX = localPos.X();
                double cY = localPos.Y();
                double sqrtPart = Math.sqrt(expandedRadius * expandedRadius - cY*cY);
                double ip = cX - sqrtPart;
                if (ip <= 0) {
                    ip = cX + sqrtPart;
                }
                if (ip < distanceToClosestIntersectingPoint) {
                    distanceToClosestIntersectingPoint = ip;
                    closestObstacle = obstacle;
                    localPositionOfClosestObstacle = localPos;
                }
            }
        }
        Vector steeringForce = Vector.ZERO;
        if (closestObstacle != null) {
            double multiplier = 1.0 + (boxLength - localPositionOfClosestObstacle.X()) / boxLength;
            double sY = (closestObstacle.boundingRadius()-localPositionOfClosestObstacle.Y()) * multiplier;
            double brakingWeight = 0.2;
            double sX = (closestObstacle.boundingRadius() - localPositionOfClosestObstacle.X()) * brakingWeight;
            steeringForce = new Vector(sX, sY);
        }
        return vectorToWorldSpace(steeringForce, entity.heading(), entity.side());

    }

    private Collection<Obstacle> tagObstaclesWithinViewRange(double boxLength) {
        Collection<Obstacle> obsts = new ArrayList<Obstacle>(obstacles.size());
        for (Obstacle obstacle : obstacles) {
            Vector to = obstacle.position().subtract(entity.position());
            double range = boxLength + obstacle.boundingRadius();
            if (to.lengthSquared() < range * range) {
                obsts.add(obstacle);
            }
        }
        return obsts;
    }

}

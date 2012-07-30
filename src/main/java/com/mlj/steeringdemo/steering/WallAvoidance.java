package com.mlj.steeringdemo.steering;

import static com.mlj.steeringdemo.geom.Geometry.createFeelersFor;
import static com.mlj.steeringdemo.geom.Geometry.lineIntersects;

import java.util.Collection;

import com.mlj.steeringdemo.geom.LineIntersection;
import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;
import com.mlj.steeringdemo.unit.Wall;

public class WallAvoidance {

    private final MovingEntity entity;
    private final Collection<Wall> walls;
    private Vector[] feelers = new Vector[3];

    public WallAvoidance(MovingEntity entity, Collection<Wall> walls) {
        this.entity = entity;
        this.walls = walls;
        feelers = createFeelersFor(entity);
    }

    public Vector calculate() {
        double distanceToThisIP = 0.0;
        double distanceToClosestIP = Double.MAX_VALUE;
        Wall closestWall = null;

        Vector steeringForce = Vector.ZERO, closestPoint = Vector.ZERO;

        for (int flr=0; flr < feelers.length; flr++) {
            for (Wall wall : walls) {
                LineIntersection intersection = lineIntersects(entity.position(), feelers[flr], wall.from(), wall.to());
                if (intersection.intersects()) {
                    if (intersection.distance() < distanceToClosestIP) {
                        distanceToClosestIP = intersection.distance();
                        closestWall = wall;
                        closestPoint = intersection.intersectionPoint();
                    }
                }
            }
            if (closestWall != null) {
                Vector overShoot = feelers[flr].subtract(closestPoint);
                steeringForce = closestWall.normal().scale(overShoot.length());
            }
        }

        return steeringForce;
    }

}

package com.mlj.steeringdemo.steering;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.mlj.steeringdemo.geom.Rotation;
import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;
import com.mlj.steeringdemo.unit.Vehicle;
import com.mlj.steeringdemo.world.World;

public class Steering {

    private final World world;
    private final MovingEntity entity;

    private boolean seek;
    private boolean flee;
    private boolean arrive;
    private boolean pursuit;
    private boolean offsetPursuit;
    private boolean wander;
    private boolean wallAvoidance;
    private boolean obstacleAvoidance;
    private boolean hide;
    private boolean separation;
    private boolean cohesion;
    private boolean alignment;
    private boolean userControlled;
    private boolean interpose;

    private Vector seekPosition;
    private Vector fleePosition;
    private Vector arrivalPosition;
    private MovingEntity prey;
    private double wanderRadius;
    private double wanderDistance;
    private double wanderJitter;
    private MovingEntity hunter;
    private Collection<Vehicle> neighbours = Lists.newArrayList();
    private Vehicle leader;
    private Vector offset;
    private Direction direction;
    private Rotation rotation;
    private MovingEntity vehicleA;
    private MovingEntity vehicleB;

    public Steering(MovingEntity entity, World world) {
        this.entity = entity;
        this.world = world;
    }

    private Vector accumulateForce(Vector runningTotal, Vector forceToAdd) {
        double magnitudeSoFar = runningTotal.length();
        double magnitudeRemaining = entity.getMaxForce() - magnitudeSoFar;
        double magnitudeToAdd = forceToAdd.length();
        if (magnitudeToAdd < magnitudeRemaining) {
            return runningTotal.add(forceToAdd);
        } else {
            return runningTotal.add(forceToAdd.normalise().scale(magnitudeRemaining));
        }
    }

    private boolean magnitudeExceeded(Vector runningTotal, Vector forceToAdd) {
        double magnitudeSoFar = runningTotal.length();
        double magnitudeRemaining = entity.getMaxForce() - magnitudeSoFar;
        return magnitudeRemaining <= 0;
    }

    public Vector calculate() {
        Vector steeringForce = Vector.ZERO;
        if (wallAvoidance) {
            Vector force = steeringForce.add(new WallAvoidance(entity, world.getWalls()).calculate());
            if (magnitudeExceeded(steeringForce, force)) {
                return steeringForce;
            } else {
                steeringForce = accumulateForce(steeringForce, force);
            }
        }
        if (obstacleAvoidance) {
            Vector force = steeringForce.add(new ObstacleAvoidance(entity, world.getObstacles()).calculate());
            if (magnitudeExceeded(steeringForce, force)) {
                return steeringForce;
            } else {
                steeringForce = accumulateForce(steeringForce, force);
            }
        }
        if (separation || cohesion || alignment) {
            tagNeighbours();
        }
        if (separation) {
            Vector force = steeringForce.add(new Separation(entity, neighbours).calculate());
            if (magnitudeExceeded(steeringForce, force)) {
                return steeringForce;
            } else {
                steeringForce = accumulateForce(steeringForce, force.scale(6));
            }
        }
        if (alignment) {
            Vector force = steeringForce.add(new Alignment(entity, neighbours).calculate());
            if (magnitudeExceeded(steeringForce, force)) {
                return steeringForce;
            } else {
                steeringForce = accumulateForce(steeringForce, force.scale(2));
            }
        }
        if (cohesion) {
            Vector force = steeringForce.add(new Cohesion(entity, neighbours).calculate());
            if (magnitudeExceeded(steeringForce, force)) {
                return steeringForce;
            } else {
                steeringForce = accumulateForce(steeringForce, force);
            }
        }
        if (userControlled) {
            Vector force = steeringForce.add(new UserControl(entity, direction, rotation).calculate());
            if (magnitudeExceeded(steeringForce, force)) {
                return steeringForce;
            } else {
                steeringForce = accumulateForce(steeringForce, force);
            }
        }
        if (seek) {
            Vector force = steeringForce.add(new Seek(entity, seekPosition).calculate());
            if (magnitudeExceeded(steeringForce, force)) {
                return steeringForce;
            } else {
                steeringForce = accumulateForce(steeringForce, force);
            }
        }
        if (flee) {
            Vector force = steeringForce.add(new Flee(entity, fleePosition).calculate());
            if (magnitudeExceeded(steeringForce, force)) {
                return steeringForce;
            } else {
                steeringForce = accumulateForce(steeringForce, force);
            }
        }
        if (arrive) {
            Vector force = steeringForce.add(new Arrive(entity, arrivalPosition).calculate());
            if (magnitudeExceeded(steeringForce, force)) {
                return steeringForce;
            } else {
                steeringForce = accumulateForce(steeringForce, force);
            }
        }
        if (pursuit) {
            Vector force = steeringForce.add(new Pursuit(entity, prey).calculate());
            if (magnitudeExceeded(steeringForce, force)) {
                return steeringForce;
            } else {
                steeringForce = accumulateForce(steeringForce, force);
            }
        }
        if (offsetPursuit) {
            Vector force = steeringForce.add(new OffsetPursuit(entity, leader, offset).calculate());
            if (magnitudeExceeded(steeringForce, force)) {
                return steeringForce;
            } else {
                steeringForce = accumulateForce(steeringForce, force);
            }
        }
        if (wander) {
            Vector force = steeringForce.add(new Wander(entity, wanderRadius, wanderDistance, wanderJitter).calculate());
            if (magnitudeExceeded(steeringForce, force)) {
                return steeringForce;
            } else {
                steeringForce = accumulateForce(steeringForce, force);
            }
        }
        if (hide) {
            Vector force = steeringForce.add(new Hide(entity, hunter, world.getObstacles()).calculate());
            if (magnitudeExceeded(steeringForce, force)) {
                return steeringForce;
            } else {
                steeringForce = accumulateForce(steeringForce, force);
            }
        }
        if (interpose) {
            Vector force = steeringForce.add(new Interpose(entity, vehicleA, vehicleB).calculate());
            if (magnitudeExceeded(steeringForce, force)) {
                return steeringForce;
            } else {
                steeringForce = accumulateForce(steeringForce, force);
            }
        }
        return steeringForce;
    }

    public Steering seekOn(Vector seekPosition) {
        this.seekPosition = seekPosition;
        seek = true;
        return this;
    }

    public Steering seekOff() {
        seekPosition = null;
        seek = false;
        return this;
    }

    public Steering fleeOn(Vector fleePosition) {
        this.fleePosition = fleePosition;
        flee = true;
        return this;
    }

    public Steering fleeOff() {
        fleePosition = null;
        flee = false;
        return this;
    }

    public Steering arriveOn(Vector arrivalPosition) {
        this.arrivalPosition = arrivalPosition;
        arrive = true;
        return this;
    }

    public Steering arriveOff() {
        arrivalPosition = null;
        arrive = false;
        return this;
    }

    public Steering pursuitOn(MovingEntity prey) {
        this.prey = prey;
        pursuit = true;
        return this;
    }

    public Steering pursuitOff() {
        prey = null;
        pursuit = false;
        return this;
    }

    public Steering wanderOn(double wanderRadius, double wanderDistance, double wanderJitter) {
        this.wanderRadius = wanderRadius;
        this.wanderDistance = wanderDistance;
        this.wanderJitter = wanderJitter;
        wander = true;
        return this;
    }

    public Steering wanderOff() {
        wander = false;
        return this;
    }

    public Steering wallAvoidanceOn() {
        wallAvoidance = true;
        return this;
    }

    public Steering wallAvoidanceOff() {
        wallAvoidance = false;
        return this;
    }

    public Steering obstacleAvoidanceOn() {
        obstacleAvoidance = true;
        return this;
    }

    public Steering obstacleAvoidanceOff() {
        obstacleAvoidance = false;
        return this;
    }

    public Steering hideOn(Vehicle hunter) {
        this.hunter = hunter;
        hide = true;
        return this;
    }

    public Steering hideOff() {
        hunter = null;
        hide = false;
        return this;
    }

    public Steering separationOn() {
        separation = true;
        return this;
    }

    public Steering separationOff() {
        separation = false;
        return this;
    }

    public Steering cohesionOn() {
        cohesion = true;
        return this;
    }

    public Steering cohesionOff() {
        cohesion = false;
        return this;
    }

    public Steering alignmentOn() {
        alignment = true;
        return this;
    }

    public Steering alignmentOff() {
        alignment = false;
        return this;
    }

    private void tagNeighbours() {
        neighbours.clear();
        for (Vehicle vehicle : world.getVehicles()) {
            if (vehicle == entity) {
                continue;
            }
            Vector to = vehicle.position().subtract(entity.position());
            double range = 100 + vehicle.boundingRadius();
            if (to.lengthSquared() < (range * range)) {
                neighbours.add(vehicle);
            }
        }
    }

    public Steering offsetPursuitOn(Vehicle leader, Vector offset) {
        offsetPursuit = true;
        this.leader = leader;
        this.offset = offset;
        return this;
    }

    public Steering offsetPursuitOff() {
        leader = null;
        offset  = null;
        offsetPursuit = false;
        return this;
    }

    public Steering directionOn(Direction direction) {
        this.direction = direction;
        this.rotation = entity.heading().rotationTo(direction.heading());
        userControlled = true;
        return this;
    }
    public Steering directionOff() {
        this.direction = null;
        this.rotation = null;
        userControlled = false;
        return this;
    }

    public Steering interposeOn(MovingEntity vehicleA, MovingEntity vehicleB) {
        this.vehicleA = vehicleA;
        this.vehicleB = vehicleB;
        interpose = true;
        return this;
    }

    public Steering interposeOff() {
        this.vehicleA = null;
        this.vehicleB = null;
        interpose = false;
        return this;
    }
}

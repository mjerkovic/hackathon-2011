package com.mlj.steeringdemo.unit;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.goals.Goal;
import com.mlj.steeringdemo.steering.Steering;
import com.mlj.steeringdemo.world.World;

public abstract class MovingEntity extends Entity {

    protected final Steering steering;
    protected Goal goal;
    protected Vector velocity = new Vector(0, 0);
    protected Vector heading = new Vector(1, 0);
    protected Vector side = new Vector(1, 0);
    protected double mass = 1;
    protected double maxSpeed = 3.5;
    protected double maxForce = 120;
    protected double maxTurnRate = 0.2;
    private final Collection<ForceObserver> observers = Lists.newArrayList();
    private Vector smoothedHeading = Vector.ZERO;
    private final HeadingSmoother headingSmoother;

    public MovingEntity(World world) {
        this.steering = new Steering(this, world);
        this.boundingRadius = 30;
        headingSmoother = new HeadingSmoother(5);
    }

    public MovingEntity(World world, Goal goal) {
        this(world);
        this.goal = goal;
    }

    public void update() {
        if (goal != null) {
            goal.process(this);
        }
        Vector steeringForce = steering.calculate();
        reportOn(steeringForce);
        steeringForce = restrictTurnRate(steeringForce);
        Vector acceleration = steeringForce.dividedBy(mass);
        velocity = velocity.add(acceleration).truncate(maxSpeed);
        position = position.add(velocity);

        if (velocity.lengthSquared() > 0.00000001) {
            heading = velocity.normalise();
            side = heading.perp();
        }

        smoothedHeading = headingSmoother.update(heading);
    }

    private void reportOn(Vector steeringForce) {
        for (ForceObserver observer : observers) {
            observer.reportOn(steeringForce);
        }
    }

    protected final Vector restrictTurnRate(Vector steeringForce) {
        double angleChange = heading.angle(steeringForce.normalise());
        return angleChange > maxTurnRate ? steeringForce.scale(maxTurnRate) : steeringForce;
    }

    public void stop() {
        velocity = Vector.ZERO;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public Vector velocity() {
        return velocity;
    }

    public Vector heading() {
        return heading;
    }

    public Vector side() {
        return side;
    }

    public Steering steering() {
        return steering;
    }

    public void move(Vector toPosition) {
        //steeringBehaviours.move(this, toPosition);
    }

    public double getMaxTurnRate() {
        return maxTurnRate;
    }

    public void addObserver(ForceObserver observer) {
        observers.add(observer);
    }

    public double getMaxForce() {
        return maxForce;
    }

    public Vector getSmoothedHeading() {
        return smoothedHeading;
    }
}

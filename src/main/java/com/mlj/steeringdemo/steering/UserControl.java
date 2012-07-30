package com.mlj.steeringdemo.steering;

import static com.mlj.steeringdemo.geom.Transformations.rotateAroundOrigin;

import com.mlj.steeringdemo.geom.Rotation;
import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.MovingEntity;

public class UserControl implements SteeringBehaviour {

    private static final double FOURTY_FIVE_DEGREES = Math.PI / 2.0 * 0.5;

    private final MovingEntity entity;
    private final Direction direction;
    private final Rotation rotation;

    public UserControl(MovingEntity entity, Direction direction, Rotation rotation) {
        this.entity = entity;
        this.direction = direction;
        this.rotation = rotation;
    }

    public Vector calculate() {
        if (Direction.NONE.equals(direction)) {
            entity.steering().directionOff();
            entity.stop();
            return Vector.ZERO;
        }
        Vector toPosition;
        if (entity.heading().angle(direction.heading()) > 1.5) {
            toPosition = entity.position().add(restrictTurnRate(entity, rotation).scale(100));
        } else {
            toPosition = entity.position().add(direction.heading().scale(100));
        }
        return new Seek(entity, toPosition).calculate();
    }

    private Vector restrictTurnRate(MovingEntity entity, Rotation rotation) {
        return rotateAroundOrigin(entity.heading(), rotation.rotate(FOURTY_FIVE_DEGREES));
    }

}

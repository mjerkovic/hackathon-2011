package com.mlj.steeringdemo.steering;

import com.mlj.steeringdemo.geom.Vector;

public enum Direction {

    NONE(new Vector(0, 0)),
    NORTH(new Vector(0, -1).normalise()),
    NORTH_EAST(new Vector(1, -1).normalise()),
    EAST(new Vector(1, 0).normalise()),
    SOUTH_EAST(new Vector(1, 1).normalise()),
    SOUTH(new Vector(0, 1).normalise()),
    SOUTH_WEST(new Vector(-1, 1).normalise()),
    WEST(new Vector(-1, 0).normalise()),
    NORTH_WEST(new Vector(-1, -1).normalise());

    private final Vector heading;

    Direction(Vector heading) {
        this.heading = heading;
    }

    public Vector heading() {
        return heading;
    }

}

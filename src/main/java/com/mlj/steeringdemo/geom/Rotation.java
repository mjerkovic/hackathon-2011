package com.mlj.steeringdemo.geom;

public enum Rotation {

    CLOCKWISE(1),
    ANTI_CLOCKWISE(-1);

    private final double multiplier;

    private Rotation(double multiplier) {
        this.multiplier = multiplier;
    }

    public double rotate(double radians) {
        return radians * multiplier;
    }

}

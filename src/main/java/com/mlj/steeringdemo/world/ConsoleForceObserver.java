package com.mlj.steeringdemo.world;

import com.mlj.steeringdemo.geom.Vector;
import com.mlj.steeringdemo.unit.ForceObserver;

public class ConsoleForceObserver implements ForceObserver {

    public void reportOn(Vector steeringForce) {
        System.out.println(String.format("steeringForce = %s, length = %f", steeringForce, steeringForce.length()));
    }

}

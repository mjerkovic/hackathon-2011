package com.mlj.steeringdemo.unit;

import com.mlj.steeringdemo.geom.Vector;

public interface ForceObserver {

    void reportOn(Vector steeringForce);

}

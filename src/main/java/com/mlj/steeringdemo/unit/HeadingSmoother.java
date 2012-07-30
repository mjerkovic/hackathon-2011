package com.mlj.steeringdemo.unit;

import java.util.ArrayDeque;
import java.util.Deque;

import com.mlj.steeringdemo.geom.Vector;

public class HeadingSmoother {

    private Deque<Vector> values = new ArrayDeque<Vector>();
    private final int maxSamples;

    public HeadingSmoother(int numberOfSamples) {
        values = new ArrayDeque<Vector>(numberOfSamples);
        maxSamples = numberOfSamples;
    }

    public Vector update(Vector heading) {
        addToValues(heading);
        Vector averageHeading = Vector.ZERO;
        for (Vector vector : values) {
            averageHeading = averageHeading.add(vector);
        }
        return values.isEmpty() ? Vector.ZERO : averageHeading.dividedBy(values.size());
    }

    private void addToValues(Vector heading) {
        if (values.size() == maxSamples) {
            values.pop();
        }
        values.add(heading);
    }

}

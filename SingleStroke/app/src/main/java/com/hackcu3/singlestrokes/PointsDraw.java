package com.hackcu3.singlestrokes;

import java.util.List;

/**
 * Created by nivinantonalexislawrence on 4/22/17.
 */

public class PointsDraw {

    List<Byte> x;
    List<Byte> y;
    String shape;

    public PointsDraw(List<Byte> pointsX, List<Byte> pointsY, String shape) {
        this.x = pointsX;
        this.y = pointsY;
        this.shape = shape;
    }

    public List<Byte> getPointsX() {
        return x;
    }

    public void setPointsX(List<Byte> pointsX) {
        this.x = pointsX;
    }

    public List<Byte> getPointsY() {
        return y;
    }

    public void setPointsY(List<Byte> pointsY) {
        this.x = pointsY;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
}

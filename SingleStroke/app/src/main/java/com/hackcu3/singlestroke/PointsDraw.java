package com.hackcu3.singlestroke;

import java.util.List;

/**
 * Created by nivinantonalexislawrence on 4/22/17.
 */

public class PointsDraw {

    List<Float> x;
    List<Float> y;
    String shape;

    public PointsDraw(List<Float> pointsX, List<Float> pointsY, String shape) {
        this.x = pointsX;
        this.y = pointsY;
        this.shape = shape;
    }

    public List<Float> getPointsX() {
        return x;
    }

    public void setPointsX(List<Float> pointsX) {
        this.x = pointsX;
    }

    public List<Float> getPointsY() {
        return y;
    }

    public void setPointsY(List<Float> pointsY) {
        this.x = pointsY;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
}

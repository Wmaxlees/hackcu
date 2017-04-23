package com.hackcu3.singlestrokes;

import java.util.List;

/**
 * Created by nivinantonalexislawrence on 4/22/17.
 */

public class PointsDraw {

    List<Byte> x;
    List<Byte> y;
    String shape;
    float minX, minY, maxX, maxY;
    float startX, startY, endX, endY;

    public PointsDraw(List<Byte> pointsX, List<Byte> pointsY, String shape) {
        this.x = pointsX;
        this.y = pointsY;
        this.shape = shape;
    }

    public void setBoundaries (float minXVal, float minYVal, float maxXVal, float maxYVal) {
        this.minX = minXVal;
        this.minY = minYVal;
        this.maxX = maxXVal;
        this.maxY = maxYVal;
    }

    public void setEndpoints (float startX, float startY, float endX, float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public float getMinX() { return minX; }
    public float getMinY() { return minY; }
    public float getMaxX() { return maxX; }
    public float getMaxY() { return maxY; }

    public float getStartX() { return startX; }
    public float getStartY() { return startY; }
    public float getEndX() { return endX; }
    public float getEndY() { return endY; }

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

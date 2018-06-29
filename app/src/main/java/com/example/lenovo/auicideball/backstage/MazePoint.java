package com.example.lenovo.auicideball.backstage;

public class MazePoint {
    private boolean isVisited = false;
    private boolean wallUp = true;
    private boolean wallRight = true;
    private boolean wallLeft = true;
    private boolean wallDown = true;

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public boolean isWallUp() {
        return wallUp;
    }

    public void setWallUp(boolean wallUp) {
        this.wallUp = wallUp;
    }

    public boolean isWallRight() {
        return wallRight;
    }

    public void setWallRight(boolean wallRight) {
        this.wallRight = wallRight;
    }

    public boolean isWallLeft() {
        return wallLeft;
    }

    public void setWallLeft(boolean wallLeft) {
        this.wallLeft = wallLeft;
    }

    public boolean isWallDown() {
        return wallDown;
    }

    public void setWallDown(boolean wallDown) {
        this.wallDown = wallDown;
    }
}

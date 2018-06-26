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

    //    private int left = 0 ;
//    private int right = 0 ;
//    private int up = 0 ;
//    private int down = 0 ;
//    private int x;
//    private int y;
//    public boolean visted;
//    public MazePoint(int x , int y ){
//        this.x = x;
//        this.y = y;
//    }
//
//    public int getLeft() {
//        return left;
//    }
//
//    public void setLeft() {
//        this.left = 1;
//    }
//
//    public int getRight() {
//        return right;
//    }
//
//    public void setRight() {
//        this.right = 1;
//    }
//
//    public int getUp() {
//        return up;
//    }
//
//    public void setUp() {
//        this.up = 1;
//    }
//
//    public int getDown() {
//        return down;
//    }
//
//    public void setDown() {
//        this.down = 1;
//    }
//
//    public int getX() {
//        return x;
//    }
//
//    public void setX(int x) {
//        this.x = x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public void setY(int y) {
//        this.y = y;
//    }
}

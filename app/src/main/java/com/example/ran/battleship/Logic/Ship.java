package com.example.ran.battleship.Logic;
import java.util.Random;
/**
 * Created by ran on 05/12/2017.
 */

public class Ship {



    //  private Point[] points;
    private int size;
    private int hitPoints;
    public String direction;
    private boolean isSunk;
    private Point pointsOnBoard[];
    private Point headPoint;


    public Ship(int size){
        this.size = size;
        this.hitPoints = size;
        this.isSunk = false;
        this.headPoint = new Point();
        pointsOnBoard = new Point[size];


    }


    public String getDirection() {
        return direction;
    }


    public void setDirection(String direction){
        this.direction = direction;
    }

    public void hitShip() {
        this.hitPoints--;
        if (this.hitPoints == 0 )
            isSunk = true;
    }

  //  public Point[] getPoints() {
    //    return points;
   // }

    public int getSize() {
        return size;
    }

    public boolean isSunk() {
        return isSunk;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public Point[] getPointsOnBoard() {
        return pointsOnBoard;
    }


    public void setPointsOnBoard(Point[] pointsOnBoard) {
        this.pointsOnBoard = pointsOnBoard;
    }

    public void setHeadPoint(int row, int col) {
        this.headPoint.setRow(row);
        this.headPoint.setCol(col);
    }


    public Point getHeadPoint() {
        return headPoint;
    }


}

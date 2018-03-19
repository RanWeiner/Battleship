package com.example.ran.battleship;

/**
 * Created by ran on 05/01/2018.
 */

public class HighScoreRecord {
    private int id;
    private String name;
    private int score;
    private double latitude,longtitude;



    public HighScoreRecord( String name, int score, double latitude, double longtitude){

        this.name=name;
        this.score=score;
        this.latitude=latitude;
        this.longtitude=longtitude;
    }


    public HighScoreRecord(int id, String name, int score, double latitude, double longtitude){
        this.id=id;
        this.name=name;
        this.score=score;
        this.latitude=latitude;
        this.longtitude=longtitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

}

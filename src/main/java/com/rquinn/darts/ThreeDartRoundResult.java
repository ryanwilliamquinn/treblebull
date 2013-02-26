package com.rquinn.darts;

import com.google.gson.annotations.Expose;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 2/3/13
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThreeDartRoundResult {
    @Expose private int id;
    @Expose private int round;
    @Expose private String firstDart;
    @Expose private String secondDart;
    @Expose private String thirdDart;
    @Expose private int score;

    public ThreeDartRoundResult(){};

    public ThreeDartRoundResult(int round, String firstDart, String secondDart, String thirdDart) {
        this.round = round;
        this.firstDart = firstDart;
        this.secondDart = secondDart;
        this.thirdDart = thirdDart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getFirstDart() {
        return firstDart;
    }

    public void setFirstDart(String firstDart) {
        this.firstDart = firstDart;
    }

    public String getSecondDart() {
        return secondDart;
    }

    public void setSecondDart(String secondDart) {
        this.secondDart = secondDart;
    }

    public String getThirdDart() {
        return thirdDart;
    }

    public void setThirdDart(String thirdDart) {
        this.thirdDart = thirdDart;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public String toString() {
        return "RoundResult: round " + round + ", firstDart: " + firstDart + ", secondDart: " + secondDart + ", thirdDart: " + thirdDart;
    }
}

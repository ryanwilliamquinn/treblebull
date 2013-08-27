package com.rquinn.darts;

import com.rquinn.darts.model.Dart;

import java.util.List;


public class ThreeOhOneResult extends DartsResult {
  private List<Dart> darts;
  private String doubleIn;
  private String doubleOut;
  private int out;

  // might need this for mybatis
  public ThreeOhOneResult(List<Dart> darts, String type, String doubleIn, String doubleOut, int out) {
    this.darts = darts;
    this.setNumRounds(darts.get(darts.size() - 1).getRound());
    calculateScore();
    PracticeType.getPracticeTypeForString(type);
    this.doubleIn = doubleIn;
    this.doubleOut = doubleOut;
    this.out = out;
  }

  public ThreeOhOneResult(List<Dart> darts, BasePracticeType type, String doubleIn, String doubleOut, int out) {
    setType(type);
    this.darts = darts;
    calculateScore();
    this.setNumRounds(darts.get(darts.size() - 1).getRound());
    this.doubleIn = doubleIn;
    this.doubleOut = doubleOut;
    this.out = out;
  }

  public void calculateScore() {
    this.setScore(darts.size());
  }

  public List<Dart> getRoundResult() {
    return darts;
  }

  public String getDoubleIn() {
    return doubleIn;
  }

  public void setDoubleIn(String doubleIn) {
    this.doubleIn = doubleIn;
  }

  public String getDoubleOut() {
    return doubleOut;
  }

  public void setDoubleOut(String doubleOut) {
    this.doubleOut = doubleOut;
  }

  public int getOut() {
    return out;
  }

  public void setOut(int out) {
    this.out = out;
  }

  public String toString() {
    return "ThreeOhOneResult --- score: " + getScore() + ", type: " + getType() + ", num rounds: " + getNumRounds() + ", double in: " + doubleIn + ", double out: " + doubleOut + ", out: " + out;
  }
}

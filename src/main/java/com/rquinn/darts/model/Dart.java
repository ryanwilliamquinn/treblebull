package com.rquinn.darts.model;

import com.google.gson.annotations.Expose;
import com.rquinn.darts.DateTimeManagement;

/**
 * User: rquinn
 * Date: 8/24/13
 * Time: 2:46 PM
 */
public class Dart {

  @Expose
  private int id;
  @Expose private int round;
  @Expose private String target;
  @Expose private String actual;
  @Expose private Integer score;

  public Dart(){};

  public Dart(int round, String actual) {
    this.round = round;
    this.actual = actual;
  }

  public Dart(int round, String actual, int score) {
    this.round = round;
    this.actual = actual;
    this.score = score;
  }

  public Dart(int round, String target, String actual, int score) {
    this.round = round;
    this.target = target;
    this.actual = actual;
    this.score = score;
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

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }


  public String getActual() {
    return actual;
  }

  public void setActual(String dart) {
    this.actual = dart;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }


  public String toString() {
    return "Dart: round " + round + ", dart: " + actual + ", target: " + target;
  }

}

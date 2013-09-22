package com.rquinn.darts.model;

import com.google.gson.annotations.Expose;

/**
 * User: rquinn
 * Date: 9/21/13
 * Time: 10:01 PM
 * <result property="type" column="target"></result>
 <result property="actual" column="actual"></result>
 <result property="numDarts" column="numDarts"></result>
 */
public class DartsAnalyticsResult {
  @Expose
  private String type;
  @Expose
  private String actual;
  @Expose
  private int numDarts;

  public DartsAnalyticsResult() {};

  public int getNumDarts() {
    return numDarts;
  }

  public void setNumDarts(int numDarts) {
    this.numDarts = numDarts;
  }

  public String getActual() {
    return actual;
  }

  public void setActual(String actual) {
    this.actual = actual;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String toString() {
    return "Type: " + type + ", actual: " + actual + ", numDarts: " + numDarts;
  }


}

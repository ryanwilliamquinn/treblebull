package com.rquinn.darts.model;

import com.google.gson.annotations.Expose;

/**
 * User: rquinn
 * Date: 9/21/13
 * Time: 11:02 PM
 */
public class TargetAnalyticsData {
  @Expose
  String target;
  @Expose
  String actual;
  @Expose
  int total;
  @Expose
  int numTriples;
  @Expose
  int numDoubles;

  public TargetAnalyticsData() {};

  public TargetAnalyticsData(String target, String actual, int total, int numTriples, int numDoubles) {
    this.target = target;
    this.actual = actual;
    this.total = total;
    this.numTriples = numTriples;
    this.numDoubles = numDoubles;
  }

  public int getNumDoubles() {
    return numDoubles;
  }

  public void setNumDoubles(int numDoubles) {
    this.numDoubles = numDoubles;
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

  public void setActual(String actual) {
    this.actual = actual;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getNumTriples() {
    return numTriples;
  }

  public void setNumTriples(int numTriples) {
    this.numTriples = numTriples;
  }



}

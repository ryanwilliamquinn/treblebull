package com.rquinn.darts.model;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * User: rquinn
 * Date: 9/21/13
 * Time: 11:06 PM
 */
public class DartsAnalyticsResponse {
  @Expose
  private String type;
  @Expose
  private int total;
  @Expose
  private int totalMisses;
  @Expose
  private TargetAnalyticsData hits;
  @Expose
  private List<TargetAnalyticsData> misses;


  public DartsAnalyticsResponse() {};

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getTotalMisses() {
    return totalMisses;
  }

  public void setTotalMisses(int totalMisses) {
    this.totalMisses = totalMisses;
  }

  public TargetAnalyticsData getHits() {
    return hits;
  }

  public void setHits(TargetAnalyticsData hits) {
    this.hits = hits;
  }

  public List<TargetAnalyticsData> getMisses() {
    return misses;
  }

  public void setMisses(List<TargetAnalyticsData> misses) {
    this.misses = misses;
  }
}

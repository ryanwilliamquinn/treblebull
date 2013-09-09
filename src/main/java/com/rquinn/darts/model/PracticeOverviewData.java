package com.rquinn.darts.model;

import com.google.gson.annotations.Expose;
import com.rquinn.darts.DartsResult;
import com.rquinn.darts.TargetPracticeType;

/**
 * User: rquinn
 * Date: 9/8/13
 * Time: 8:38 PM
 */
public class PracticeOverviewData {

  private TargetPracticeType practiceType;
  @Expose
  private String type;
  @Expose
  private int totalNumDarts;
  @Expose
  private double averageScore;
  @Expose
  private int totalNumRounds;
  @Expose
  private int numDartsLastDay;
  @Expose
  private double avgScoreLastDay;
  @Expose
  private int numDartsLastWeek;
  @Expose
  private double avgScoreLastWeek;
  @Expose
  private int numDartsLastMonth;
  @Expose
  private double avgScoreLastMonth;
  @Expose
  private int totalScore;
  @Expose
  private DartsResult latestResult;

  public int getTotalScore() {
    return totalScore;
  }

  public void setTotalScore(int totalScore) {
    this.totalScore = totalScore;
  }

  public int getTotalNumRounds() {
    return totalNumRounds;
  }

  public void setTotalNumRounds(int totalNumRounds) {
    this.totalNumRounds = totalNumRounds;
  }

  public double getAverageScore() {
    return averageScore;
  }

  public void setAverageScore(double averageScore) {
    this.averageScore = averageScore;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public TargetPracticeType getPracticeType() {
    return practiceType;
  }

  public void setPracticeType(TargetPracticeType type) {
    this.practiceType = practiceType;
  }

  public int getTotalNumDarts() {
    return totalNumDarts;
  }

  public void setTotalNumDarts(int totalNumDarts) {
    this.totalNumDarts = totalNumDarts;
  }

  public double getAvgScoreLastWeek() {
    return avgScoreLastWeek;
  }

  public void setAvgScoreLastWeek(double avgScoreLastWeek) {
    this.avgScoreLastWeek = avgScoreLastWeek;
  }

  public int getNumDartsLastDay() {
    return numDartsLastDay;
  }

  public void setNumDartsLastDay(int numDartsLastDay) {
    this.numDartsLastDay = numDartsLastDay;
  }

  public double getAvgScoreLastDay() {
    return avgScoreLastDay;
  }

  public void setAvgScoreLastDay(double avgScoreLastDay) {
    this.avgScoreLastDay = avgScoreLastDay;
  }

  public int getNumDartsLastWeek() {
    return numDartsLastWeek;
  }

  public void setNumDartsLastWeek(int numDartsLastWeek) {
    this.numDartsLastWeek = numDartsLastWeek;
  }

  public double getAvgScoreLastMonth() {
    return avgScoreLastMonth;
  }

  public void setAvgScoreLastMonth(double avgScoreLastMonth) {
    this.avgScoreLastMonth = avgScoreLastMonth;
  }

  public int getNumDartsLastMonth() {
    return numDartsLastWeek;
  }

  public void setNumDartsLastMonth(int numDartsLastMonth) {
    this.numDartsLastMonth = numDartsLastMonth;
  }

  public DartsResult getLatestResult() {
    return latestResult;
  }

  public void setLatestResult(DartsResult latestResult) {
    this.latestResult = latestResult;
  }

  public String toString() {
    return "Type: " + getType() + ", total score: " + getTotalScore() + ", total num rounds: " + getTotalNumRounds()
        + ", latest result date: " + latestResult.getDateTimeManagement().getDisplayDateTime();
  }


}

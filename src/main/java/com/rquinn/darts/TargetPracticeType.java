package com.rquinn.darts;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 1/19/13
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public enum TargetPracticeType implements BasePracticeType {

  BULLS ("bull", true),
  TRIPLE_TWENTIES ("t20", false),
  DOUBLE_TWENTIES ("d20", false),
  TWENTIES ("20", true),
  TRIPLE_NINETEEN ("t19", false),
  DOUBLE_NINETEEN ("d19", false),
  NINETEEN ("19", true),
  TRIPLE_EIGHTEEN ("t18", false),
  DOUBLE_EIGHTEEN ("d18", false),
  EIGHTEEN ("18", true),
  TRIPLE_SEVENTEEN ("t17", false),
  DOUBLE_SEVENTEEN ("d17", false),
  SEVENTEEN ("17", true),
  TRIPLE_SIXTEEN ("t16", false),
  DOUBLE_SIXTEEN ("d16", false),
  SIXTEEN ("16", true),
  TRIPLE_FIFTEEN ("t15", false),
  DOUBLE_FIFTEEN ("d15", false),
  FIFTEEN ("15", true),
  FREE ("free", false);


  private String value;
  private boolean isAnalytics;

  private TargetPracticeType(String value, boolean isAnalytics) {
    this.value = value;
    this.isAnalytics = isAnalytics;
  }

  public String getValue() {
    return value;
  }

  public boolean isTargetPracticeType() {
    return true;
  }

  public boolean isAnalytics() {
    return isAnalytics;
  }

  public static TargetPracticeType getTargetPracticeTypeForString(String type) {
    TargetPracticeType pt = null;
    for (TargetPracticeType t : TargetPracticeType.values()) {
      if (StringUtils.equalsIgnoreCase(t.getValue(), type)) {
        pt = t;
      }
    }
    return  pt;
  }

}

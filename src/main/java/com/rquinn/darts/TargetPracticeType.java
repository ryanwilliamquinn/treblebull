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

    BULLS ("bull"),
    TRIPLE_TWENTIES ("t20"),
    DOUBLE_TWENTIES ("d20"),
    TWENTIES ("20"),
    TRIPLE_NINETEEN ("t19"),
    DOUBLE_NINETEEN ("d19"),
    NINETEEN ("19"),
    TRIPLE_EIGHTEEN ("t18"),
    DOUBLE_EIGHTEEN ("d18"),
    EIGHTEEN ("18"),
    TRIPLE_SEVENTEEN ("t17"),
    DOUBLE_SEVENTEEN ("d17"),
    SEVENTEEN ("17"),
    TRIPLE_SIXTEEN ("t16"),
    DOUBLE_SIXTEEN ("d16"),
    SIXTEEN ("16"),
    TRIPLE_FIFTEEN ("t15"),
    DOUBLE_FIFTEEN ("d15"),
    FIFTEEN ("15");


    private String value;

    private TargetPracticeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
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

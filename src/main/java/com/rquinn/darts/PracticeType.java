package com.rquinn.darts;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 12/25/12
 * Time: 7:12 PM
 * To change this template use File | Settings | File Templates.
 */
public enum PracticeType implements BasePracticeType {

    TARGET ("target", true, "target.jsp"),
    FREE_TARGET ("free", false, "freeTarget.jsp"),
    CRICKET ("cricket", false, "cricket.jsp"),
    THREE_OH_ONE ("301", false, "threeOhOne.jsp");

    private String value;
    private boolean simple;
    private String template;

    private PracticeType(String value, boolean simple, String template) {
        this.value = value;
        this.simple = simple;
        this.template = template;
    }


    public String getValue() {
        return value;
    }

    public boolean isSimple() {
        return simple;
    }

    public String getTemplate() {
        return template;
    }


    public static PracticeType getPracticeTypeForString(String type) {
        PracticeType pt = null;
        for (PracticeType t : PracticeType.values()) {
            if (StringUtils.equalsIgnoreCase(t.getValue(), type)) {
                pt = t;
            }
        }
        return  pt;
    }
}

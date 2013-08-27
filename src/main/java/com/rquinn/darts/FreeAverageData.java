package com.rquinn.darts;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 3/11/13
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class FreeAverageData {

    private String target;
    private Integer score;
    private Integer numDarts;

    public FreeAverageData() {}

    public FreeAverageData(String target, Integer score) {
        this.target = target;
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScoe(Integer score) {
        this.score = score;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getNumDarts() {
        return numDarts;
    }

    public void setNumDarts(Integer numDarts) {
        this.numDarts = numDarts;
    }

}

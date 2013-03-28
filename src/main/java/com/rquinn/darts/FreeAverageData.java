package com.rquinn.darts;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 3/11/13
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class FreeAverageData {

    private String type;
    private Integer score;
    private Integer numDarts;

    public FreeAverageData() {}

    public FreeAverageData(String type, Integer score) {
        this.type = type;
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScoe(Integer score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumDarts() {
        return numDarts;
    }

    public void setNumDarts(Integer numDarts) {
        this.numDarts = numDarts;
    }

}

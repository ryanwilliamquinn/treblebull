package com.rquinn.darts;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 12/31/12
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class DartsResultResponse {

    @Expose private List<DartsResult> dartsResults;
    @Expose private int totalNumResults;

    public List<DartsResult> getDartsResults() {
        return dartsResults;
    }

    public void setDartsResults(List<DartsResult> dartsResults) {
        this.dartsResults = dartsResults;
    }

    public int getTotalNumResults() {
        return totalNumResults;
    }

    public void setTotalNumResults(int totalNumResults) {
        this.totalNumResults = totalNumResults;
    }
}

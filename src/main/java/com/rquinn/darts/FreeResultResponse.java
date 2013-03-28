package com.rquinn.darts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 3/18/13
 * Time: 8:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class FreeResultResponse {

    List<FreeAverageData> aggregateData = new ArrayList<FreeAverageData>();
    List<FreeTargetHistory> freeTargetHistories = new ArrayList<FreeTargetHistory>();

    public void setAggregateData(List<FreeAverageData> aggregateData) {
        this.aggregateData = aggregateData;
    }

    public void setFreeTargetHistories(List<FreeTargetHistory> freeTargetHistories) {
        this.freeTargetHistories = freeTargetHistories;
    }

    public List<FreeTargetHistory> getFreeTargetHistories() {
        return freeTargetHistories;
    }



}

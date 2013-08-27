package com.rquinn.darts;

import com.rquinn.darts.model.Dart;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 3/18/13
 * Time: 8:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class FreeTargetHistory {
    List<Dart> darts;

    public void setSingleDartResults(List<Dart> darts) {
        this.darts = darts;
    }
}

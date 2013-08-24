package com.rquinn.darts;

import com.rquinn.darts.BasePracticeType;
import com.rquinn.darts.DartsResult;
import com.rquinn.darts.PracticeType;
import com.rquinn.darts.ThreeDartRoundResult;
import com.rquinn.darts.model.Dart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 8/24/13
 * Time: 3:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class DartResult extends DartsResult {

  private static final Logger slf4jLogger = LoggerFactory.getLogger(DartResult.class);

  private List<Dart> darts;

  public DartResult(List<Dart> rounds, String type) {
    this.darts = rounds;
    this.setNumRounds(rounds.size());
    calculateScore();
    PracticeType.getPracticeTypeForString(type);
  }

  public DartResult(List<Dart> darts, BasePracticeType type) {
    setType(type);
    this.darts = darts;
    calculateScore();
    // could use a better way to set the number of rounds.  for now, query the round number of the last dart
    this.setNumRounds(darts.get(darts.size() - 1).getRound());
  }

  public void calculateScore() {
    if (getType() == PracticeType.CRICKET) {
      setScore(darts.size());
    } else {
      int total = 0;
      for(Dart dart : darts) {
        if (dart != null) {
          total += dart.getScore();
        }
      }
      setScore(total);
    }

  }

  public List<Dart> getDarts() {
    return darts;
  }
}

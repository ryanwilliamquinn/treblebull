package com.rquinn.darts;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 2/3/13
 * Time: 12:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ThreeDartResult extends DartsResult {
    private List<ThreeDartRoundResult> rounds;

    public ThreeDartResult(List<ThreeDartRoundResult> rounds, String type) {
        this.rounds = rounds;
        this.setNumRounds(rounds.size());
        calculateScore();
        PracticeType.getPracticeTypeForString(type);
    }

    public ThreeDartResult(List<ThreeDartRoundResult> rounds, BasePracticeType type) {
        setType(type);
        this.rounds = rounds;
        calculateScore();
        this.setNumRounds(rounds.size());
    }

    public void calculateScore() {
        if (getType() == PracticeType.CRICKET) {
            setScore(rounds.size());
        } else {
            int total = 0;
            for(ThreeDartRoundResult round : rounds) {
                if (round != null) {
                    total += round.getScore();
                }
            }
            setScore(total);
        }

    }

    public List<ThreeDartRoundResult> getRoundResult() {
        return rounds;
    }
}

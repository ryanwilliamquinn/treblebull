package com.rquinn.darts;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 2/13/13
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class ThreeOhOneResult extends DartsResult {
    private List<ThreeDartRoundResult> rounds;

    // might need this for mybatis
    public ThreeOhOneResult(List<ThreeDartRoundResult> rounds, String type) {
        this.rounds = rounds;
        this.setNumRounds(rounds.size());
        calculateScore();
        PracticeType.getPracticeTypeForString(type);
    }

    public ThreeOhOneResult(List<ThreeDartRoundResult> rounds, BasePracticeType type) {
        setType(type);
        this.rounds = rounds;
        calculateScore();
        this.setNumRounds(rounds.size());
    }

    public void calculateScore() {
        this.setScore(rounds.size());
    }

    public List<ThreeDartRoundResult> getRoundResult() {
        return rounds;
    }
}

package com.rquinn.darts;

import java.util.List;

/**
 *
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

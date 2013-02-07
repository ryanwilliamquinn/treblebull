package com.rquinn.darts;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 2/3/13
 * Time: 12:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class CricketResult extends DartsResult {
    private List<CricketRoundResult> rounds;

    public CricketResult(List<CricketRoundResult> rounds, String type) {
        this.rounds = rounds;
        this.setNumRounds(rounds.size());
        calculateScore();
        PracticeType.getPracticeTypeForString(type);
    }

    public CricketResult(List<CricketRoundResult> rounds, BasePracticeType type) {
        setType(type);
        this.rounds = rounds;
        calculateScore();
        this.setNumRounds(rounds.size());
    }

    public void calculateScore() {
        this.setScore(rounds.size());
    }

    public List<CricketRoundResult> getRoundResult() {
        return rounds;
    }
}

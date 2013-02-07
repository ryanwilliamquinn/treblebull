package com.rquinn.darts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 12/14/12
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimplePracticeResult extends DartsResult {

    private List<RoundResult> rounds;

    public SimplePracticeResult(List<RoundResult> rounds, String type) {
        this.rounds = rounds;
        this.setNumRounds(rounds.size());
        calculateScore();
        PracticeType.getPracticeTypeForString(type);
    }

    public SimplePracticeResult(List<RoundResult> rounds, BasePracticeType type) {
        setType(type);
        this.rounds = rounds;
        calculateScore();
        this.setNumRounds(rounds.size());
    }

    public List<RoundResult> getRoundResult() {
        return rounds;
    }

    public void setRoundResult(List<RoundResult> roundScore) {
        this.rounds = roundScore;
    }

    public void setRound(int round, int score) {
        if (rounds == null) {
            rounds = new ArrayList<RoundResult>();
        }
        rounds.add(new RoundResult(round, score));
    }

    public void calculateScore() {
        int score = 0;
        for (RoundResult round : rounds) {
            if (round != null) {
                score += round.getScore();
            }
        }
        this.setScore(score);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (rounds != null) {
            for (RoundResult round : rounds) {
                sb.append("round ").append(round.getRound()).append(" score was ").append(round.getScore()).append("\n");
            }
        }
        sb.append("total score: ").append(getScore()).append("\n");
        sb.append("dateTime: ").append(getDateTime()).append("\n");
        sb.append("type: ").append(getType());
        return sb.toString();
    }

}

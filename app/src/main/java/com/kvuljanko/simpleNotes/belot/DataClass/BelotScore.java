package com.kvuljanko.simpleNotes.belot.DataClass;

import java.io.Serializable;

public class BelotScore implements Serializable {
    private int score1;
    private int score2;
    private int additional1;
    private int additional2;

    public BelotScore() {
    }

    public BelotScore(int score1, int score2, int additional1, int additional2) {
        this.score1 = score1;
        this.score2 = score2;
        this.additional1 = additional1;
        this.additional2 = additional2;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public int getAdditional1() {
        return additional1;
    }

    public void setAdditional1(int additional1) {
        this.additional1 = additional1;
    }

    public int getAdditional2() {
        return additional2;
    }

    public void setAdditional2(int additional2) {
        this.additional2 = additional2;
    }

    public int getTotalScore1() {
        return score1+additional1;
    }

    public int getTotalScore2() {
        return score2+additional2;
    }
}

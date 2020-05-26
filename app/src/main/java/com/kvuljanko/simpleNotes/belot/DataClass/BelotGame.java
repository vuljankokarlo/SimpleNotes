package com.kvuljanko.simpleNotes.belot.DataClass;

import java.io.Serializable;
import java.util.List;

public class BelotGame implements Serializable {

    private int dealer;
    private int firstDealer;
    private int total1;
    private int total2;
    private List<BelotScore> listOfBelotScores;

    public BelotGame(int total1, int total2, List<BelotScore> listOfBelotScores, int dealer, int firstDealer) {
        this.total1 = total1;
        this.total2 = total2;
        this.listOfBelotScores = listOfBelotScores;
        this.dealer = dealer;
        this.firstDealer = firstDealer;
    }

    public BelotGame() {

    }

    public int getTotal1() {
        return total1;
    }

    public void setTotal1(int total1) {
        this.total1 = total1;
    }

    public int getTotal2() {
        return total2;
    }

    public void setTotal2(int total2) {
        this.total2 = total2;
    }

    public List<BelotScore> getListOfBelotScores() {
        return listOfBelotScores;
    }

    public void setListOfBelotScores(List<BelotScore> listOfBelotScores) {
        this.listOfBelotScores = listOfBelotScores;
    }

    public int getDealer() {
        return dealer;
    }

    public void setDealer(int dealer) {
        this.dealer = dealer;
    }

    public int getFirstDealer() {
        return firstDealer;
    }

    public void setFirstDealer(int firstDealer) {
        this.firstDealer = firstDealer;
    }
}

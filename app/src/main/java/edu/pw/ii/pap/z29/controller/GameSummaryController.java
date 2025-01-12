package edu.pw.ii.pap.z29.controller;

import lombok.Getter;

public class GameSummaryController {
    MainController mainController;
    @Getter int score;
    @Getter int currentHighScore;
    String word;
    public GameSummaryController(MainController mainController, int score) {
        this.mainController = mainController;
        this.score = score;
        //TODO update table, get high score
        currentHighScore = 0;
    }
}

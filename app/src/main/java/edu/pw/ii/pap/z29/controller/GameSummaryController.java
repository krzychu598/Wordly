package edu.pw.ii.pap.z29.controller;

import lombok.Getter;

import java.sql.SQLException;

import edu.pw.ii.pap.z29.model.primitives.Score;

public class GameSummaryController {
    MainController mainController;
    @Getter int score;
    @Getter int currentHighScore;
    String word;
    public GameSummaryController(MainController mainController, int score) {
        this.mainController = mainController;
        this.score = score;
        /* TODO  get current score, level, highscore*/
        this.currentHighScore = 20;
        mainController.insertScore(score);
        /* TODO  get it again*/


    }
}

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
        this.currentHighScore = 20;
        //TODO update table, get high score, how to get user id?, how to generate scoreID?
        /* currentHighScore = mainController.getScores().getHighScore();
        int scoreID = 0;
        int userID = 0;
        Score scoreT = new Score(scoreID, userID, score);
        try{
            mainController.getScores().insert(scoreT);
        } catch (SQLException e){
            mainController.sqlLogger.log(e);
        }
            */
    }
}

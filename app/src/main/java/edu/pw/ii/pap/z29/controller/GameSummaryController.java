package edu.pw.ii.pap.z29.controller;

import lombok.Getter;

import java.sql.SQLException;

import edu.pw.ii.pap.z29.model.primitives.Score;

public class GameSummaryController {
    MainController mainController;
    @Getter int score;
    @Getter int currentBestScore;
    @Getter int level;
    @Getter int highScore;
    @Getter int lowScore;
    @Getter int totalScore;
    String word;
    public GameSummaryController(MainController mainController, int score) {
        this.mainController = mainController;
        this.score = score;
        currentBestScore = 20; //for tests only        
        level = mainController.getProfileController().getLevel().getLevelNr();
        lowScore = mainController.getProfileController().getLevel().getLowScore();
        highScore = mainController.getProfileController().getLevel().getHighScore();
        mainController.insertScore(score);
        int newLevel = mainController.getProfileController().getLevel().getLevelNr();
        if (newLevel > level){
            lowScore = mainController.getProfileController().getLevel().getLowScore();
            highScore = mainController.getProfileController().getLevel().getHighScore();
        }
    }
}

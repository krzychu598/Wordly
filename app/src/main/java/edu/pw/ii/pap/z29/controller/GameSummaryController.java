package edu.pw.ii.pap.z29.controller;

import lombok.Getter;


public class GameSummaryController {
    MainController mainController;
    @Getter int score;
    @Getter int currentBestScore;
    @Getter int level;
    @Getter int highScore;
    @Getter int lowScore;
    @Getter int totalScore;
    @Getter int newLevel;
    String word;
    public GameSummaryController(MainController mainController, int score) {
        this.mainController = mainController;
        this.score = score;
        currentBestScore = mainController.getProfileController().getMaxScore(); 
        level = mainController.getProfileController().getLevel().getLevelNr();
        mainController.insertScore(score);
        newLevel = mainController.getProfileController().getLevel().getLevelNr();
        lowScore = mainController.getProfileController().getLevel().getLowScore();
        highScore = mainController.getProfileController().getLevel().getHighScore();
        
    }
}

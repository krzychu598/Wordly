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
    String word;
    public GameSummaryController(MainController mainController, int score) {
        this.mainController = mainController;
        this.score = score;
        currentBestScore = mainController.getProfileController().maxScore(); 
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

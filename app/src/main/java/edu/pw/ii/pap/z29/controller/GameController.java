package edu.pw.ii.pap.z29.controller;

public class GameController {
    MainController mainController;
    int wordLength;
    boolean definition;

    public GameController(MainController mainController, int wordLength, boolean definition) {
        this.mainController = mainController;
        this.wordLength = wordLength;
        this.definition = definition;
    }

    public boolean validateField(){
        return false;
    }

    public int getWordLength(){
        return wordLength;
    }

}

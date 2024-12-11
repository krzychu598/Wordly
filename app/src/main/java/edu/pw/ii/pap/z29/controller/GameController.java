package edu.pw.ii.pap.z29.controller;
public class GameController {
    MainController mainController;
    int wordLength;
    boolean definition;
    String word;

    public GameController(MainController mainController, int wordLength, boolean definition) {
        this.mainController = mainController;
        this.wordLength = wordLength;
        this.definition = definition;
        this.word = ApiController.getRandomWord(wordLength);
    }

    public boolean validateField(){
        return false;
    }

    public int getWordLength(){
        return wordLength;
    }

    public boolean validateInput(String letter){
        if((letter.length() > 1) || (letter.isEmpty())){
            return false;
        } 
        char l = letter.charAt(0);
        if ((l>='a' && l <= 'z') || (l>='A' && l<='Z') ){
            return true;
        }
        return false;
    }
    public boolean check(String word){
        if(ApiController.isInDictionary(word)){
            return true;
        }
        return false;
    }
}

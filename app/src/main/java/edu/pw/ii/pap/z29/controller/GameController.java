package edu.pw.ii.pap.z29.controller;

import java.util.*;

import oracle.net.aso.f;


public class GameController {
    MainController mainController;
    int wordLength;
    String word;
    int score = 0;
    int guesssedLetters;
    boolean won = false;
    final static int MAX_IT = 3;
    public GameController(MainController mainController, int wordLength ) {
        this.mainController = mainController;
        this.wordLength = wordLength;
        this.word = ApiController.getRandomWord(wordLength);
    }

    public int getWordLength(){
        return wordLength;
    }
    public String getWord(){
        return word;
    }
    public int getMaxIt(){
        return MAX_IT;
    }
    public void updateScoreGuessed(int turn){
        score += Math.max(wordLength * 100 - (int)( 50 * turn / (MAX_IT-1)), 0);
        won = true;
    }
    public void updateScoreNotGuessed(){
        score = Math.max(score+5*guesssedLetters, 0);
    }
    private void updateScoreShownDefinition(){
        score -= 20; 
    }
    public int getScore(){
        return score;
    }
    public boolean haveWon(){
        return won;
    }
    public String validateInput(String text){
        if (text.length() >= 1) {
            text = text.substring(text.length()-1);
        } else if (text.isEmpty()){
            return null;
        }
        char l = text.charAt(0);
        if (!(l>='a' && l <= 'z') && !(l>='A' && l<='Z') ){
            return null;
        }
        return text.toUpperCase();
    }
    public ArrayList<Integer> check(String givenWord){
        guesssedLetters = 0;
        givenWord = givenWord.toLowerCase();
        var results = new ArrayList<Integer>();
        if(givenWord.length() != word.length()){
            //4 - incorrect length
            results.add(4);
            return results;
        }
        if(!ApiController.isInDictionary(givenWord)){
            return results;
        }
        for(int i = 0; i < word.length(); ++i){
            char a;
            if((a = givenWord.charAt(i)) == word.charAt(i)){
                results.add(0);
                guesssedLetters++;
            } else if (word.indexOf(a) != -1){
                results.add(1);
            } else{
                results.add(2);
            }
        }
        return results;
    }

    private String toHtml(String words){
        if (words.length() < 80){
            return words;
        }
        for (int i=65; i<words.length(); ++i){
            if (words.charAt(i) == ' '){
                words = words.substring(0, i) + "<br />" + toHtml(words.substring(i));
                break;
            }
        }
        return words;

    }
    public String getDefinition(){
        String definition = ApiController.getDefinition(word);
        updateScoreShownDefinition();
        return String.format("<html>%s</html>", toHtml(definition));
    }
}

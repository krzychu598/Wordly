package edu.pw.ii.pap.z29.controller;
import java.util.*;
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
    public ArrayList<Integer> check(String givenWord){
        System.out.println(givenWord);
        System.out.println(word);
        givenWord = givenWord.toLowerCase();
        var results = new ArrayList<Integer>();
        if(!ApiController.isInDictionary(givenWord)){
            return results;
        }
        for(int i = 0; i < word.length(); ++i){
            char a;
            if((a = givenWord.charAt(i)) == word.charAt(i)){
                results.add(0);
            } else if (word.indexOf(a) != -1){
                results.add(1);
            } else{
                results.add(2);
            }
        }
        return results;
    }
}

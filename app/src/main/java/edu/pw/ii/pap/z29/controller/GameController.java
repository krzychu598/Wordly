package edu.pw.ii.pap.z29.controller;

import java.util.*;


public class GameController {
    MainController mainController;
    int wordLength;
    String word;
    /*TODO (implement functionality)
    | implement score system
    */
    public GameController(MainController mainController, int wordLength ) {
        /*TODO (potential bug)
        code sometimes gets stuck here. Probably api issue
        */
        this.mainController = mainController;
        this.wordLength = wordLength;
        //System.out.println("before random word");
        this.word = ApiController.getRandomWord(wordLength);
    }

    public int getWordLength(){
        return wordLength;
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
            } else if (word.indexOf(a) != -1){
                results.add(1);
            } else{
                results.add(2);
            }
        }
        return results;
    }

    public String getDefinition(){
        return ApiController.getDefinition(word);
    }
}

package edu.pw.ii.pap.z29.controller;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.lang.StringIndexOutOfBoundsException;
import edu.pw.ii.pap.z29.exception.WordNotFoundException;
import edu.pw.ii.pap.z29.exception.InvalidLengthException;


public final class ApiController {
    
    static String urlRand = "https://random-word-api.herokuapp.com/word?length=%d";
    static String urlDef = "https://api.dictionaryapi.dev/api/v2/entries/en/%s";

    /**
     * randomWord returns a random word that can be found in the dictionary api 
     * using radom word api 
     * @param length    length of a random word
     * @return          random word as a String
     */
    static public String getRandomWord(int length)
    {  
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(urlRand, length)))
                .build();
        
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            responseBody = responseBody.substring(2, responseBody.length()-2);
            if(!ApiController.isInDictionary(responseBody)){
                throw new WordNotFoundException();
            }
            return responseBody;
        } catch (StringIndexOutOfBoundsException e){
            throw new InvalidLengthException(Integer.toString(length));
        } catch (IOException | InterruptedException | WordNotFoundException e){
            return ApiController.getRandomWord(length);
        }


    }
    /**
     * returns the definition of a word
     * @param word  a word of which definition is required
     * @return      definition of the word
     */
    static public String getDefinition(String word)
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(urlDef, word)))
                .build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 404){
                throw new WordNotFoundException();
            }
            String responseBody = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            String definition = root.get(0).path("meanings").get(0).path("definitions").get(0).path("definition").asText();
            return definition;
        } catch (IOException | InterruptedException e){
            return ApiController.getDefinition(word);
        }
    }
    /**
     * checks if a word is in the dictionary
     * @param word  a word to be checked
     * @return      true or false
     */
    static public boolean isInDictionary(String word)
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(urlDef, word)))
                .build();
        try{
           if(client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode() == 200){
            return true;
           }
           return false;
        } catch (IOException | InterruptedException e){
            return ApiController.isInDictionary(word);
        }
    }
}

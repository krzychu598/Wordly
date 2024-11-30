package edu.pw.ii.pap.z29.controller;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.lang.RuntimeException;
import java.lang.StringIndexOutOfBoundsException;
import edu.pw.ii.pap.z29.exception.WordNotFoundException;

public class ApiController {
    
    static String urlRand = "https://random-word-api.herokuapp.com/word?length=%d";
    static String urlDef = "https://api.dictionaryapi.dev/api/v2/entries/en/%s";

    static public String randomWord(int length) throws RuntimeException
    {   
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(urlRand, length)))
                .build();
        
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            responseBody = responseBody.substring(2, responseBody.length()-2);
            if(!ApiController.check(responseBody)){
                throw new WordNotFoundException("Word is not to be found in the dictionary");
            }
            return responseBody;
        } catch (IOException | InterruptedException | StringIndexOutOfBoundsException e){
            return "";
        } catch (WordNotFoundException e){
            return ApiController.randomWord(length);
        }


    }
    static public String definition(String word)
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(urlDef, word)))
                .build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            String definition = root.get(0).path("meanings").get(0).path("definitions").get(0).path("definition").asText();
            return definition;
        } catch (Exception e){
            return "";
        }
    }

    static public boolean check(String word)
    {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(urlDef, word)))
                .build();
        try{
           if(client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode() == 200){
            return true;
           } else{
            throw(new RuntimeException("Word not found in the dictionary"));
           }
        } catch (Exception e){
            return false;
        }
    }
}

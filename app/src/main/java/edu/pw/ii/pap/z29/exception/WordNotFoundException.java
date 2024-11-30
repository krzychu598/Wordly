package edu.pw.ii.pap.z29.exception;

public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException() {
        super("Word could not be found in the dictionary");
    }
}
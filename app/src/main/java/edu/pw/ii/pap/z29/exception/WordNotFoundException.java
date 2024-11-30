package edu.pw.ii.pap.z29.exception;
import java.lang.RuntimeException;

public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException(String message) {
        super(message);
    }
}
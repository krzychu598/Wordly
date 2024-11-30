package edu.pw.ii.pap.z29.controller;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.pw.ii.pap.z29.exception.InvalidLengthException;
import edu.pw.ii.pap.z29.exception.WordNotFoundException;

public class ApiControllerTest {
    @Test void getRandomWord(){
        assertThrows(InvalidLengthException.class, () -> ApiController.getRandomWord(0));
        assertNotNull(ApiController.getRandomWord(2));
    }
    @Test void  getRandomWordSpeed(){
        long startTime = System.nanoTime();
        ApiController.getRandomWord(6);
        assertTrue(System.nanoTime() - startTime < 2000000000); /*2s */
    }
    @Test void getDefinitionWrongWord(){
        assertThrows(WordNotFoundException.class, () -> ApiController.getDefinition("ziom"));
    }
    @Test void getDefinitionCorrectWord(){
        assertEquals("A corduroy fabric having narrow ribs.", ApiController.getDefinition("pinwale"));
    }
    @Test void isInDictionaryTrue(){
        assertTrue(ApiController.isInDictionary("kamikaze"));
    }
    @Test void isInDictionaryFalse(){
        assertFalse(ApiController.isInDictionary("ziom"));
    }
}

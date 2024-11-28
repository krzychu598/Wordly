package edu.pw.ii.pap.z29.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {
    @Test void usage() {
        var password = new Password("okon");
        assertEquals(password.getPassword(), "okon");
        password.setPassword("szczupak");
        assertEquals(password.getPassword(), "szczupak");
    }

    @Test void notnull() {
        assertThrows(NullPointerException.class, () -> new Password(null));
        var password = new Password("okon");
        assertThrows(NullPointerException.class, () -> password.setPassword(null));
    }
}

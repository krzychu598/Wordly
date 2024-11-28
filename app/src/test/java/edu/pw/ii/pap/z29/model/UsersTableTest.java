package edu.pw.ii.pap.z29.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.pw.ii.pap.z29.Database;
import edu.pw.ii.pap.z29.model.primitives.User;
import edu.pw.ii.pap.z29.model.primitives.Username;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Arrays;


public class UsersTableTest {
    static Database db = new Database();
    static UsersTable users;
    
    static final Username testUsername = new Username("UsersTableTest");

    @BeforeAll
    static void static_setup() throws SQLException {
        users = new UsersTable(db.getConnection());
    }

    @BeforeEach
    void setUp() throws SQLException {
        var user_to_delete = users.read(testUsername);
        if (user_to_delete.isPresent()) {
            users.delete(user_to_delete.get().getUserId());
        }
    }

    @AfterAll
    static void cleanUp() throws SQLException {
        var user_to_delete = users.read(testUsername);
        if (user_to_delete.isPresent()) {
            users.delete(user_to_delete.get().getUserId());
        }
        UsersTableTest.db.close();
    }
    
    User addUser(Username username) throws SQLException {
        var user = new User(username);
        var user_id = users.create(user);
        return users.read(user_id).get();
    }

    @Test void createUser() {
        try {
            var read_user = addUser(testUsername);
            assertEquals(read_user.getUsername(), testUsername);
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }

    @Test void createUserLongName() {
        var long_name_arr = new char[41];   // in database, length limit is 40
        Arrays.fill(long_name_arr, 'a');
        var long_name = new String(long_name_arr);
        var username = new Username(long_name);
        var user = new User(username);
        assertThrows(SQLException.class, () -> users.create(user));
    }

    @Test void updateUser() {
        try {
            var username = new Username("UsersTableTest2");
            var user = addUser(username);
            user.setUsername(testUsername);
            var did_update = users.update(user.getUserId(), user);
            assertTrue(did_update);
            var read_user = users.read(user.getUserId()).get();
            assertEquals(read_user, user);
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }

    @Test void updateUserNonExistent() {
        try {
            var user = new User(testUsername);
            var did_update = users.update(1, user);
            assertFalse(did_update);
            var read_user = users.read(user.getUserId());
            assertTrue(read_user.isEmpty());
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }

    @Test void deleteUser() {
        try { 
            var user = addUser(testUsername);
            var did_delete = users.delete(user.getUserId());
            assertTrue(did_delete);
            var read_user = users.read(user.getUserId());
            assertTrue(read_user.isEmpty());
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }

    @Test void deleteUserNonExistent() {
        try {
            var user = new User(testUsername);
            var did_update = users.delete(1);
            assertFalse(did_update);
            var read_user = users.read(user.getUserId());
            assertTrue(read_user.isEmpty());
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }
}

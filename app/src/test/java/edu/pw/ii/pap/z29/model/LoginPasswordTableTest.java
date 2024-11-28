package edu.pw.ii.pap.z29.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.pw.ii.pap.z29.Database;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;


public class LoginPasswordTableTest {
    static Database db = new Database();
    static UsersTable users;
    static LoginPasswordTable loginPasswordTable;
    static User user;

    static final Username testUsername = new Username("LoginPasswordTableTest");
    static final Password testPassword = new Password("LoginPasswordTableTest");

    @BeforeAll
    static void static_setup() throws SQLException {
        users = new UsersTable(db.getConnection());
        loginPasswordTable = new LoginPasswordTable(db.getConnection());
    }

    @BeforeEach
    void setUp() throws SQLException {
        var user_to_delete = users.read(testUsername);
        if (user_to_delete.isPresent()) {
            users.delete(user_to_delete.get().getUserId());
        }
        user = addUser(testUsername);
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

    LoginPassword addPassword(int user_id, Password password) throws SQLException {
        var login_password = new LoginPassword(user_id, testPassword);
        loginPasswordTable.create(login_password);
        return login_password;
    }

    @Test void createLoginPassword() {
        try {
            var login_password = addPassword(user.getUserId(), testPassword);
            var read_loginPassword = loginPasswordTable.read(user.getUserId());
            assertEquals(read_loginPassword.get(), login_password);
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }

    @Test void createLoginPasswordNull() {
        var nullPassword = new Password("");
        var login_password = new LoginPassword(user.getUserId(), nullPassword);
        assertThrows(SQLException.class, () -> loginPasswordTable.create(login_password));
    }

    @Test void createLoginPasswordNoUser() {
        var loginPassword = new LoginPassword(1, testPassword);
        assertThrows(SQLException.class, () -> loginPasswordTable.create(loginPassword));
    }

    @Test void updatePassword() {
        try {
            var password_to_update = new Password("LoginPasswordTableTest2");
            var login_password = addPassword(user.getUserId(), password_to_update);
            login_password.setPassword(testPassword);
            var did_update = loginPasswordTable.update(login_password);
            assertTrue(did_update);
            var read_login_password = loginPasswordTable.read(user.getUserId()).get();
            assertEquals(read_login_password, login_password);
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }

    @Test void updateToNull() {
        try {
            var login_password = addPassword(user.getUserId(), testPassword);
            login_password.setPassword(new Password(""));
            assertThrows(SQLException.class, () -> loginPasswordTable.update(login_password));
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }

    @Test void deletePassword() {
        try { 
            addPassword(user.getUserId(), testPassword);
            var did_delete = loginPasswordTable.delete(user.getUserId());
            assertTrue(did_delete);
            var read_login_password = loginPasswordTable.read(user.getUserId());
            assertTrue(read_login_password.isEmpty());
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }
}

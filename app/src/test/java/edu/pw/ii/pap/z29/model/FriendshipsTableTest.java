package edu.pw.ii.pap.z29.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.pw.ii.pap.z29.Database;
import edu.pw.ii.pap.z29.model.primitives.User;
import edu.pw.ii.pap.z29.model.primitives.Username;
import edu.pw.ii.pap.z29.model.primitives.Friendship;
import edu.pw.ii.pap.z29.model.FriendshipsTable;
import edu.pw.ii.pap.z29.model.UsersTable;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.Arrays;


public class FriendshipsTableTest {
    static Database db = new Database();
    static UsersTable users;
    static FriendshipsTable friendships;
    
    static final Username testUsername1 = new Username("FriendshipsTableTest1");
    static final Username testUsername2 = new Username("FriendshipsTableTest2");
    static User testUser1;
    static User testUser2;
    static Friendship testFriendship;

    @BeforeAll
    static void static_setup() throws SQLException {
        users = new UsersTable(db.getConnection());
        friendships = new FriendshipsTable(db.getConnection());
        testUser1 = addUser(testUsername1);
        testUser2 = addUser(testUsername2);
        testFriendship = new Friendship(testUser1.getUserId(), testUser2.getUserId(), true);
    }

    @BeforeEach
    void setUp() throws SQLException {
        friendships.delete(testFriendship);
    }

    @AfterAll
    static void cleanUp() throws SQLException {
        users.delete(testUsername1);
        users.delete(testUsername2);
        UsersTableTest.db.close();
    }
    
    static User addUser(Username username) throws SQLException {
        var user = new User(username);
        var user_id = users.create(user);
        return users.read(user_id).get();
    }

    @Test void createFriendship() {
        try {
            friendships.create(testFriendship);
            var friendship = friendships.read(testUser1.getUserId(), testUser2.getUserId()).get();
            assertEquals(friendship, testFriendship);
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }

    @Test void updateFriendship() {
        try {
            friendships.create(testFriendship);
            var did_update = friendships.update_pending(testFriendship);
            assertTrue(did_update);
            var friendship_accepted = testFriendship.toBuilder().build();
            friendship_accepted.setPending(false);
            var read_friendship = friendships.read(
                testUser1.getUserId(), testUser2.getUserId()).get();
            assertEquals(friendship_accepted, read_friendship);
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }

    @Test void deleteFriendship() {
        try { 
            friendships.create(testFriendship);
            var did_delete = friendships.delete(testFriendship);
            assertTrue(did_delete);
            var friendship = friendships.read(
                testUser1.getUserId(), testUser2.getUserId());
            assertTrue(friendship.isEmpty());
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }
}

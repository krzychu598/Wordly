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
    static final Username testUsername3 = new Username("FriendshipsTableTest3");
    static final Username testUsername4 = new Username("FriendshipsTableTest4");
    static User testUser1;
    static User testUser2;
    static User testUser3;
    static User testUser4;
    static Friendship testFriendship1;
    static Friendship testFriendship2;

    @BeforeAll
    static void static_setup() throws SQLException {
        users = new UsersTable(db.getConnection());
        friendships = new FriendshipsTable(db.getConnection());
        testUser1 = addUser(testUsername1);
        testUser2 = addUser(testUsername2);
        testUser3 = addUser(testUsername3);
        testUser4 = addUser(testUsername4);
        testFriendship1 = new Friendship(testUser1.getUserId(), testUser2.getUserId(), true);
        testFriendship2 = new Friendship(testUser3.getUserId(), testUser1.getUserId(), true);
    }

    @BeforeEach
    void setUp() throws SQLException {
        friendships.delete(testFriendship1);
        friendships.delete(testFriendship2);
    }

    @AfterAll
    static void cleanUp() throws SQLException {
        users.delete(testUsername1);
        users.delete(testUsername2);
        users.delete(testUsername3);
        users.delete(testUsername4);
        UsersTableTest.db.close();
    }
    
    static User addUser(Username username) throws SQLException {
        var user = new User(username);
        var user_id = users.create(user);
        return users.read(user_id).get();
    }

    @Test void createFriendship() {
        try {
            friendships.create(testFriendship1);
            var friendship = friendships.read(testUser1.getUserId(), testUser2.getUserId()).get();
            assertEquals(friendship, testFriendship1);
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }

    @Test void createDuplicate() {
        try {
            friendships.create(testFriendship1);
            var duplicate = testFriendship1.toBuilder().build();
            duplicate.setInviting(testUser2.getUserId());
            duplicate.setInvited(testUser1.getUserId());
            assertThrows(SQLException.class, () -> friendships.create(duplicate));
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }

    @Test void readFriends() {
        try {
            friendships.create(testFriendship1);
            friendships.create(testFriendship2);
            var friends = friendships.read_friends(testUser1.getUserId());
            assertTrue(friends.contains(testFriendship1));
            assertTrue(friends.contains(testFriendship2));
        } catch (SQLException ex) {
            new SQLLogger().log(ex);
            assertTrue(false);
        }
    }

    @Test void updateFriendship() {
        try {
            friendships.create(testFriendship1);
            var did_update = friendships.update_pending(testFriendship1);
            assertTrue(did_update);
            var friendship_accepted = testFriendship1.toBuilder().build();
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
            friendships.create(testFriendship1);
            var did_delete = friendships.delete(testFriendship1);
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

package edu.pw.ii.pap.z29.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import edu.pw.ii.pap.z29.model.primitives.Friendship;


public class FriendshipsTable {
    private Connection conn;
    private static final FriendshipsRecord record = new FriendshipsRecord();

    public FriendshipsTable(Connection conn) {
        this.conn = conn;
    }


    public void create(Friendship friendship) throws SQLException {
        var stmt_str = "INSERT INTO friendships VALUES (?, ?, ?)";
        try (var stmt = conn.prepareStatement(stmt_str))
        {
            record.serialize(friendship, stmt);
            stmt.executeUpdate();
        }
    }

    public Optional<Friendship> read(int user_id1, int user_id2) throws SQLException {
        var query_str = new String("SELECT inviting, invited, pending FROM friendships "
            + "WHERE inviting = ? AND invited = ? OR inviting = ? AND invited = ?");
        var friendship_opt = Optional.<Friendship>empty();
        try (var query = conn.prepareStatement(query_str))
        {
            query.setInt(1, user_id1);
            query.setInt(2, user_id2);
            query.setInt(3, user_id2);
            query.setInt(4, user_id1);
            query.execute();
            try (var rset = query.getResultSet()) {
                if (rset.next()) {
                    var friendship = record.deserialize(rset);
                    friendship_opt = Optional.of(friendship);
                }
            }
        }
        return friendship_opt;
    }

    public List<Friendship> read_friends(int user_id) throws SQLException {
        var query_str = new String("SELECT inviting, invited, pending FROM friendships "
            + "WHERE inviting = ? OR invited = ?");
        var friends = new ArrayList<Friendship>();
        try (var query = conn.prepareStatement(query_str))
        {
            query.setInt(2, user_id);
            query.setInt(1, user_id);
            query.execute();
            try (var rset = query.getResultSet()) {
                while (rset.next()) {
                    var friendship = record.deserialize(rset);
                    friends.add(friendship);
                }
            }
        }
        return friends;
    }

    public boolean update_pending(Friendship friendship) throws SQLException {
        var stmt_str = "UPDATE friendships SET pending = 0 WHERE inviting = ? AND invited = ?";
        boolean did_update = false;
        try (var stmt = conn.prepareStatement(stmt_str)) {
            stmt.setInt(1, friendship.getInviting());
            stmt.setInt(2, friendship.getInvited());
            did_update = stmt.executeUpdate() == 1;
        }
        return did_update;
    }

    public boolean delete(Friendship friendship) throws SQLException {
        var update_str = new StringBuilder("DELETE FROM friendships WHERE inviting = ? AND invited = ?");
        boolean did_delete;
        try (var update = conn.prepareStatement(update_str.toString())) {
            update.setInt(1, friendship.getInviting());
            update.setInt(2, friendship.getInvited());
            did_delete = update.executeUpdate() == 1;
        }
        return did_delete;
    }
}


class FriendshipsRecord implements Record<Friendship> {
    public void serialize(Friendship friendship, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, friendship.getInviting());
        stmt.setInt(2, friendship.getInvited());
        stmt.setInt(3, friendship.isPending() ? 1 : 0);
    }

    public Friendship deserialize(ResultSet rset) throws SQLException
    {
        var inviting = rset.getInt(1);
        var invited = rset.getInt(2);
        var pending = rset.getInt(3) == 1 ? true : false;
        return new Friendship(inviting, invited, pending);
    }
}

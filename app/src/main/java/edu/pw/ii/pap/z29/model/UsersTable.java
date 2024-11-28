package edu.pw.ii.pap.z29.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;


public class UsersTable {
    private Connection conn;
    private static final UserRecord record = new UserRecord();

    UsersTable(Connection conn) {
        this.conn = conn;
    }

    int create(User user) throws SQLException {
        var stmt_str = "INSERT INTO users VALUES (DEFAULT, ?)";
        int user_id = 0;
        try (var stmt = conn.prepareStatement(stmt_str, new String[]{"user_id"}))
        {
            record.serialize(user, stmt);
            stmt.executeUpdate();
            try (var rset = stmt.getGeneratedKeys()) {
                rset.next();
                user_id = rset.getInt(1);
            }
        }
        return user_id;
    }

    Optional<User> read(int user_id) throws SQLException {
        var query_str = new String("SELECT user_id, username FROM users WHERE user_id = ?");
        var user_opt = Optional.<User>empty();
        try (var query = conn.prepareStatement(query_str))
        {
            query.setInt(1, user_id);
            query.execute();
            try (var rset = query.getResultSet()) {
                if (rset.next()) {
                    var user = record.deserialize(rset);
                    user_opt = Optional.of(user);
                }
            }
        }
        return user_opt;
    }

    Optional<User> read(Username username) throws SQLException {
        var query_str = new String("SELECT user_id, username FROM users WHERE username = ?");
        var user_opt = Optional.<User>empty();
        try (var query = conn.prepareStatement(query_str)) {
            query.setString(1, username.getUsername());
            if (query.execute()) {
                try (var rset = query.getResultSet()) {
                    if (rset.next()) {
                        var user = record.deserialize(rset);
                        user_opt = Optional.of(user);
                    }
                }
            }
        }
        return user_opt;
    }

    boolean update(int user_id, User user) throws SQLException {
        var stmt_str = "UPDATE users SET username = ? WHERE user_id = ?";
        boolean did_update = false;
        try (var stmt = conn.prepareStatement(stmt_str)) {
            record.serialize(user, stmt);
            stmt.setInt(2, user_id);
            did_update = stmt.executeUpdate() == 1;
        }
        return did_update;
    }

    boolean delete(int user_id) throws SQLException {
        var update_str = new StringBuilder("DELETE FROM users WHERE user_id = ?");
        boolean did_delete;
        try (var update = conn.prepareStatement(update_str.toString())) {
            update.setInt(1, user_id);
            did_delete = update.executeUpdate() == 1;
        }
        return did_delete;
    }
}


class UserRecord implements Record<User> {
    public void serialize(User user, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, user.getUsername().getUsername());
    }

    public User deserialize(ResultSet rset) throws SQLException
    {
        var userId = rset.getInt(1);
        var username = rset.getString(2);
        return new User(userId, new Username(username));
    }
}

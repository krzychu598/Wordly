package edu.pw.ii.pap.z29.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

import edu.pw.ii.pap.z29.model.primitives.LoginPassword;
import edu.pw.ii.pap.z29.model.primitives.Password;


public class LoginPasswordTable {
    private Connection conn;
    private static final LoginPasswordRecord record = new LoginPasswordRecord();

    public LoginPasswordTable(Connection conn) {
        this.conn = conn;
    }


    public void create(LoginPassword login_password) throws SQLException {
        var stmt_str = "INSERT INTO login_password VALUES(?, ?)";
        try (var stmt = conn.prepareStatement(stmt_str)) {
            record.serialize(login_password, stmt);
            stmt.executeUpdate();
        }
    }

    public Optional<LoginPassword> read(int user_id) throws SQLException {
        var stmt_str = "SELECT user_id, passwd FROM login_password WHERE user_id = ?";
        var login_password_opt = Optional.<LoginPassword>empty();
        try(var stmt = conn.prepareStatement(stmt_str)) {
            stmt.setInt(1, user_id);
            if (stmt.execute()) {
                try (var rset = stmt.getResultSet()) {
                    if (rset.next()) {
                        var login_password = record.deserialize(rset);
                        login_password_opt = Optional.of(login_password);
                    }
                }
            }
        }
        return login_password_opt;
    }

    public boolean update(LoginPassword login_password) throws SQLException {
        var stmt_str = "UPDATE login_password SET passwd = ? WHERE user_id = ?";
        boolean did_update = false;
        try (var stmt = conn.prepareStatement(stmt_str)) {
            stmt.setString(1, login_password.getPassword().getPassword());
            stmt.setInt(2, login_password.getUserId());
            did_update = stmt.executeUpdate() == 1;
        }
        return did_update;
    }

    public boolean delete(int user_id) throws SQLException {
        var stmt_str = "DELETE FROM login_password WHERE user_id = ?";
        boolean did_delete;
        try (var stmt = conn.prepareStatement(stmt_str.toString())) {
            stmt.setInt(1, user_id);
            did_delete = stmt.executeUpdate() == 1;
        }
        return did_delete;
    }

    public boolean checkCredentials(int user_id, String password) throws SQLException {
        var stmt_str = "SELECT COUNT(*) FROM login_password WHERE user_id = ? AND passwd = ?";
        try (var stmt = conn.prepareStatement(stmt_str)) {
            stmt.setInt(1, user_id);
            stmt.setString(2, password);
            try (var rset = stmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean insert(int user_id, Password password) throws SQLException {
        var stmt_str = "INSERT INTO login_password (user_id, passwd) VALUES (?, ?)";
        boolean did_insert = false;
        try (PreparedStatement stmt = conn.prepareStatement(stmt_str)) {
            stmt.setInt(1, user_id);
            stmt.setString(2, password.getPassword());
            did_insert = stmt.executeUpdate() == 1;
        }
        return did_insert;
    }

}

class LoginPasswordRecord implements Record<LoginPassword> {
    public void serialize(LoginPassword login_password, PreparedStatement stmt)
    throws SQLException {
        stmt.setInt(1, login_password.getUserId());
        stmt.setString(2, login_password.getPassword().getPassword());
    }

    public LoginPassword deserialize(ResultSet rset) throws SQLException
    {
        var userId = rset.getInt("user_id");
        var password = rset.getString("passwd");
        return new LoginPassword(userId, new Password(password));
    }
}

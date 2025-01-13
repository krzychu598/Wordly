package edu.pw.ii.pap.z29.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

import edu.pw.ii.pap.z29.model.primitives.User;
import edu.pw.ii.pap.z29.model.primitives.Username;


public class SettingsTable {
    private Connection conn;

    public SettingsTable(Connection conn) {
        this.conn = conn;
    }

    public boolean read(int user_id) throws SQLException {
        var query_str = new String("SELECT user_id, private_profile FROM user_settings WHERE user_id = ?");
        boolean priv_profile = true;
        try (var query = conn.prepareStatement(query_str))
        {
            query.setInt(1, user_id);
            query.execute();
            try (var rset = query.getResultSet()) {
                if (rset.next()) {
                    priv_profile = (rset.getInt("private_profile") == 1);
                }
            }
        }
        return priv_profile;
    }


    public void update(int user_id, boolean priv_profile) throws SQLException {
        final var stmt_str = "{call update_user_settings(?, ?)}";
        try (var stmt = conn.prepareCall(stmt_str)) {
            stmt.setInt(1, user_id);
            stmt.setInt(2, priv_profile ? 1 : 0);
            stmt.execute();
        }
    }
}

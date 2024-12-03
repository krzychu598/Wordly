package edu.pw.ii.pap.z29.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.pw.ii.pap.z29.model.primitives.User;


public class ScoresTable {
    private Connection conn;

    public ScoresTable(Connection conn) {
        this.conn = conn;
    }


    public boolean insert(User user, int score) throws SQLException {
        var stmt_str = "INSERT INTO scores (user_id, score) VALUES (?, ?)";
        int id = 0;
    
        try (var stmt = conn.prepareStatement(stmt_str, new String[]{"id"})) {
            stmt.setInt(1, user.getUserId());
            stmt.setInt(2, score);
    
            stmt.executeUpdate();
    
            try (var rset = stmt.getGeneratedKeys()) {
                if (rset.next()) {
                    id = rset.getInt(1);
                }
            }
        }
    
        return id != 0;
    }
    

    public List<Integer> readAllScores(int user_id) throws SQLException {
        var query_str = "SELECT score FROM scores WHERE user_id = ?";
        List<Integer> scores = new ArrayList<>();
    
        try (var query = conn.prepareStatement(query_str)) {
            query.setInt(1, user_id);
    
            try (var rset = query.executeQuery()) {
                while (rset.next()) {
                    scores.add(rset.getInt("score"));
                }
            }
        }
        return scores;
    }
    


    public boolean delete(int user_id) throws SQLException {
        var update_str = new StringBuilder("DELETE FROM scores WHERE user_id = ?");
        boolean did_delete;
        try (var update = conn.prepareStatement(update_str.toString())) {
            update.setInt(1, user_id);
            did_delete = update.executeUpdate() > 0;
        }
        return did_delete;
    }

}

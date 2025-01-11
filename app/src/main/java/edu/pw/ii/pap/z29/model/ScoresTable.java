package edu.pw.ii.pap.z29.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.pw.ii.pap.z29.model.primitives.Score;


public class ScoresTable {
    private Connection conn;

    public ScoresTable(Connection conn) {
        this.conn = conn;
    }

    public int insert(Score score) throws SQLException {
        final var stmt_str = "INSERT INTO scores2 (user_id, score) VALUES (?, ?)";
        int score_id = 0;
        try (var stmt = conn.prepareStatement(stmt_str, new String[]{"id"})) {
            stmt.setInt(1, score.getUserId());
            stmt.setInt(2, score.getScore());
            stmt.executeUpdate();
            try (var rset = stmt.getGeneratedKeys()) {
                score_id = rset.getInt(1);
            }
        }
        return score_id;
    }

    public List<Score> readAllScores(int user_id) throws SQLException {
        final var stmt_str = "SELECT score_id, score FROM scores2 WHERE user_id = ?";
        List<Score> scores = new ArrayList<>();
        try (var stmt = conn.prepareStatement(stmt_str)) {
            stmt.setInt(1, user_id);
            try (var rset = stmt.executeQuery()) {
                while (rset.next()) {
                    var score = new Score(
                        rset.getInt("score_id"),
                        user_id,
                        rset.getInt("score")
                    );
                    scores.add(score);
                }
            }
        }
        return scores;
    }

    public boolean delete(int user_id) throws SQLException {
        var update_str = new StringBuilder("DELETE FROM scores2 WHERE user_id = ?");
        boolean did_delete;
        try (var update = conn.prepareStatement(update_str.toString())) {
            update.setInt(1, user_id);
            did_delete = update.executeUpdate() > 0;
        }
        return did_delete;
    }

}

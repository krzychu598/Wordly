package edu.pw.ii.pap.z29.model;

import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import edu.pw.ii.pap.z29.model.primitives.Score;


public class ScoresTable {
    private Connection conn;

    public ScoresTable(Connection conn) {
        this.conn = conn;
    }


    public int insert(Score score) throws SQLException {
        final var stmt_str = "INSERT INTO scores2 (user_id, score, word, game_date) VALUES (?, ?, ?, ?)";
        int score_id = 0;
        try (var stmt = conn.prepareStatement(stmt_str, new String[]{"score_id"})) {
            stmt.setInt(1, score.getUserId());
            stmt.setInt(2, score.getScore());
            stmt.setString(3, score.getWord());
            stmt.setDate(4, java.sql.Date.valueOf(score.getDate()));
            stmt.executeUpdate();
            try (var rset = stmt.getGeneratedKeys()) {
                if (rset.next()) {
                    score_id = rset.getInt(1);
                }
            }
        }
        return score_id;
    }


    public List<Score> readAllScores(int user_id) throws SQLException {
        final var stmt_str = "SELECT score_id, score, word, game_date FROM scores2 WHERE user_id = ?";
        List<Score> scores = new ArrayList<>();
        try (var stmt = conn.prepareStatement(stmt_str)) {
            stmt.setInt(1, user_id);
            try (var rset = stmt.executeQuery()) {
                while (rset.next()) {
                    var score = new Score(
                        rset.getInt("score_id"),
                        user_id,
                        rset.getInt("score"),
                        rset.getString("word"),
                        rset.getDate("game_date").toLocalDate());
                    scores.add(score);
                }
            }
        }
        return scores;
    }

    public int readTotalScore(int user_id) throws SQLException {
        final var stmt_str = "SELECT SUM(score) AS total FROM scores2 WHERE user_id = ?";
        int score = 0;
        try (var stmt = conn.prepareStatement(stmt_str)) {
            stmt.setInt(1, user_id);
            try (var rset = stmt.executeQuery()) {
                rset.next();
                score = rset.getInt("total");
            }
        }
        return score;
    }

    public boolean delete(int user_id) throws SQLException {
        final var stmt_str = "DELETE FROM scores2 WHERE user_id = ?";
        boolean did_delete;
        try (var stmt = conn.prepareStatement(stmt_str)) {
            stmt.setInt(1, user_id);
            did_delete = stmt.executeUpdate() > 0;
        }
        return did_delete;
    }
}


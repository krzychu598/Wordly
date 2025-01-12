package edu.pw.ii.pap.z29.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

import edu.pw.ii.pap.z29.model.primitives.Level;

public class LevelsTable {
    private static final LevelRecord record = new LevelRecord();
    private Connection conn;

    public LevelsTable(Connection conn) {
        this.conn = conn;
    }


    public void create(Level level) throws SQLException {
        var stmt_str = "INSERT INTO levels VALUES(?, ?, ?)";
        try (var stmt = conn.prepareStatement(stmt_str)) {
            record.serialize(level, stmt);
            stmt.executeUpdate();
        }
    }

    public Optional<Level> read(int level_nr) throws SQLException {
        var stmt_str = "SELECT level_nr, low_score, high_score"
            + " FROM levels WHERE level_nr = ?";
        var level_opt = Optional.<Level>empty();
        try(var stmt = conn.prepareStatement(stmt_str)) {
            stmt.setInt(1, level_nr);
            if (stmt.execute()) {
                try (var rset = stmt.getResultSet()) {
                    if (rset.next()) {
                        var level = record.deserialize(rset);
                        level_opt = Optional.of(level);
                    }
                }
            }
        }
        return level_opt;
    }

    public Optional<Level> readByScore(int score) throws SQLException {
        var stmt_str = "SELECT level_nr, low_score, high_score"
            + " FROM levels WHERE low_score <= ? AND ? < high_score";
        var level_opt = Optional.<Level>empty();
        try(var stmt = conn.prepareStatement(stmt_str)) {
            stmt.setInt(1, score);
            stmt.setInt(2, score);
            if (stmt.execute()) {
                try (var rset = stmt.getResultSet()) {
                    if (rset.next()) {
                        var level = record.deserialize(rset);
                        level_opt = Optional.of(level);
                    }
                }
            }
        }
        return level_opt;
    }

    public Optional<Level> readHighestLevel() throws SQLException {
        var stmt_str = "SELECT level_nr, low_score, high_score FROM levels"
            + "WHERE level_nr = (SELECT MAX(level_nr) FROM levels)";
        var level_opt = Optional.<Level>empty();
        try(var stmt = conn.createStatement()) {
            try (var rset = stmt.executeQuery(stmt_str)) {
                if (rset.next()) {
                    var level = record.deserialize(rset);
                    level_opt = Optional.of(level);
                }
            }
        }
        return level_opt;
    }
}

class LevelRecord implements Record<Level> {
    public void serialize(Level level, PreparedStatement stmt)
    throws SQLException {
        stmt.setInt(1, level.getLevelNr());
        stmt.setInt(2, level.getLowScore());
        stmt.setInt(3, level.getHighScore());
    }

    public Level deserialize(ResultSet rset) throws SQLException
    {
        var level_nr = rset.getInt("level_nr");
        var low_score = rset.getInt("low_score");
        var high_score = rset.getInt("high_score");
        return new Level(level_nr, low_score, high_score);
    }
}

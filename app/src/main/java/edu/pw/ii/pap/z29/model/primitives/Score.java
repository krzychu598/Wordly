package edu.pw.ii.pap.z29.model.primitives;

import lombok.Data;


@Data
public class Score {
    int scoreId;
    int userId;
    int score;

    public Score(int scoreId, int userId, int score) {
        this.scoreId = scoreId;
        this.userId = userId;
        this.score = score;
    }
}

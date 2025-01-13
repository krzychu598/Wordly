package edu.pw.ii.pap.z29.model.primitives;

import lombok.Data;
import java.util.List;
import java.time.LocalDate;


@Data
public class Score {
    private int scoreId;
    private int userId;
    private int score;
    private String word;
    private LocalDate date;


    public Score(int scoreId, int userId, int score, String word, LocalDate date) {
        this.scoreId = scoreId;
        this.userId = userId;
        this.score = score;
        this.word = word;
        this.date = date;
    }

    public static int total_score(List<Score> scores) {
        return scores.stream().mapToInt(Score::getScore).sum();
    }
}

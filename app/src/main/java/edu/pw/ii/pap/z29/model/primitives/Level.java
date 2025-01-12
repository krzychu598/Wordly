package edu.pw.ii.pap.z29.model.primitives;

import lombok.Data;


@Data
public class Level {
    int levelNr;
    int lowScore;
    int highScore;

    public Level(int levelNr, int lowScore, int highScore) {
        this.levelNr = levelNr;
        this.lowScore = lowScore;
        this.highScore = highScore;
    }
}

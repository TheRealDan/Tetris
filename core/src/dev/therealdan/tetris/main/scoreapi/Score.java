package dev.therealdan.tetris.main.scoreapi;

import java.text.DecimalFormat;

public class Score {

    private static DecimalFormat format = new DecimalFormat("###,###");

    public String Name;
    public double Score;

    public String getEntry() {
        return Name + ": " + format(Score);
    }

    public static String format(double score) {
        return format.format(score);
    }
}
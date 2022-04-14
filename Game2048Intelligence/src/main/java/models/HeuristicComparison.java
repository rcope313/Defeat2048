package models;

import models.game.Grid2048;

public class HeuristicComparison {
    private final int highestScore;
    private final int averageScore;
    private final Grid2048 bestGrid;

    public HeuristicComparison(int highestScore, int averageScore, Grid2048 bestGrid) {
        this.highestScore = highestScore;
        this.averageScore = averageScore;
        this.bestGrid = bestGrid;
    }

    public Grid2048 getBestGrid() {
        return bestGrid;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public int getAverageScore() {
        return averageScore;
    }
}

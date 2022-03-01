package models;

public class HeuristicComparison {
    private final int highestScore;
    private final int averageScore;

    public HeuristicComparison(int highestScore, int averageScore) {
        this.highestScore = highestScore;
        this.averageScore = averageScore;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public int getAverageScore() {
        return averageScore;
    }
}

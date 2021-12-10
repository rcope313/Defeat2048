package heuristic;

import game2048.Game2048;
import javalib.worldimages.Posn;
import models.HeuristicScore;
import models.square.*;

import java.util.Arrays;

public class SnakeHeuristic extends GameHeuristic {
    public SnakeHeuristic(Game2048 game2048) {
        super(game2048);
    }

    @Override
    public Game2048 evaluateNextGameState() {
        return null;
    }

    @Override
    public int evaluateGameStateHeuristicScore(Game2048 game) {
        HeuristicScore score = new HeuristicScore();
        score.setValue(0);

        for (Square[] row : game.getBoard2048().getGrid()) {
            Arrays.stream(row).forEach((square) -> getHeuristicScoreSquare(square, score));
        }
        return score.getValue();
    }

    static void getHeuristicScoreSquare (Square square, HeuristicScore score) {
        if (square.isTile()) {
            getHeuristicScoreTile(square, score);
        }
    }

    static void getHeuristicScoreTile (Square square, HeuristicScore score) {
        int currentScore = score.getValue();
        if (square.getPosition().equals(new Posn(0,0))) { score.setValue(1000000 + currentScore); }
        else if (square.getPosition().equals(new Posn(0,1))) { score.setValue(900000 + currentScore); }
        else if (square.getPosition().equals(new Posn(0,2))) { score.setValue(800000 + currentScore); }
        else if (square.getPosition().equals(new Posn(0,3))) { score.setValue(700000 + currentScore); }
        else if (square.getPosition().equals(new Posn(1,3))) { score.setValue(10000 + currentScore); }
        else if (square.getPosition().equals(new Posn(1,2))) { score.setValue(9000 + currentScore); }
        else if (square.getPosition().equals(new Posn(1,1))) { score.setValue(8000 + currentScore); }
        else if (square.getPosition().equals(new Posn(1,0))) { score.setValue(7000 + currentScore); }
        else if (square.getPosition().equals(new Posn(2,0))) { score.setValue(100 + currentScore); }
        else if (square.getPosition().equals(new Posn(2,1))) { score.setValue(90 + currentScore); }
        else if (square.getPosition().equals(new Posn(2,2))) { score.setValue(80 + currentScore); }
        else if (square.getPosition().equals(new Posn(2,3))) { score.setValue(70 + currentScore); }
        else if (square.getPosition().equals(new Posn(3,3))) { score.setValue(4 + currentScore); }
        else if (square.getPosition().equals(new Posn(3,2))) { score.setValue(3 + currentScore); }
        else if (square.getPosition().equals(new Posn(3,1))) { score.setValue(2 + currentScore); }
        else if (square.getPosition().equals(new Posn(3,0))) { score.setValue(1 + currentScore); }
        else {
            throw new IllegalStateException("Invalid posn of tile");
        }

    }
}

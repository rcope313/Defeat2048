package heuristic;

import models.HeuristicScore;
import models.game.Grid2048;
import models.game.KeyEventHandler;
import models.square.Square;

public class SnakeHeuristic extends GameHeuristic {

    @Override
    public String getHeuristicName() {
        return "Snake Heuristic";
    }

    @Override
    public HeuristicScore evaluateHeuristicScore(KeyEventHandler handler) {
        HeuristicScore score = new HeuristicScore(0);
        int y = 0;
        int x = 0;

        while (y < Grid2048.SQUARES_PER_AXIS) {
            while (x < Grid2048.SQUARES_PER_AXIS) {
                score = new HeuristicScore(evaluateHeuristicScore(handler.getGrid2048(), y, x, score.getValue()));
                x++;
            }
            y++;
            x = 0;
        }
        return score;
    }

    static int evaluateHeuristicScore(Grid2048 grid, int y , int x, int score) {
        Square square = grid.getSquareByCoordinates(x,y);
        int squareValue = square.getValue();
        if (square.isTile()) {
            if (x == 0 && y == 0) { return score + 10000 * squareValue; }
            else if (x == 1 && y == 0) { return score + 9000 * squareValue; }
            else if (x == 2 && y == 0) { return score + 8000 * squareValue; }
            else if (x == 3 && y == 0) { return score + 7000 * squareValue; }
            else if (x == 3 && y == 1) { return score + 1000 * squareValue; }
            else if (x == 2 && y == 1) { return score + 900 * squareValue; }
            else if (x == 1 && y == 1) { return score + 800 * squareValue; }
            else if (x == 0 && y == 1) { return score + 700 * squareValue; }
            else if (x == 0 && y == 2) { return score + 100 * squareValue; }
            else if (x == 1 && y == 2) { return score + 90 * squareValue; }
            else if (x == 2 && y == 2) { return score + 80 * squareValue; }
            else if (x == 3 && y == 2) { return score + 70 * squareValue; }
            else if (x == 3 && y == 3) { return score + 10 * squareValue; }
            else if (x == 2 && y == 3) { return score + 9 * squareValue; }
            else if (x == 1 && y == 3) { return score + 8 * squareValue; }
            else if (x == 0 && y == 3) { return score + 7 * squareValue; }
            else {
                return 0;
            }
        } else {
            return score;
        }
    }
}

package heuristic;

import models.HeuristicScore;
import models.game.Grid2048;
import models.game.KeyEvent;
import models.game.KeyEventHandler;
import models.game.Scoreboard;
import models.square.Square;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnakeHeuristic extends GameHeuristic {

    @Override
    public KeyEventHandler evaluateNextGameState(Grid2048 grid, Scoreboard scoreboard) {
        Map<Integer, KeyEventHandler> handlerMap =  new HashMap<>();
        ArrayList<Integer> scores = new ArrayList<>();

        establishKeyEventSequence(grid, scoreboard, handlerMap, scores);
        for (int idx = 0; idx < scores.size(); idx++) {
            int currentScore = scores.get(idx);
            if (handlerMap.get(currentScore).isTilesMoved()) {
                return handlerMap.get(currentScore);
            }
            if (idx == scores.size() - 1) {
                return handlerMap.get(currentScore);
            }
        }
        throw new IllegalStateException("Unable to evaluate next game state");
    }

    @Override
    public HeuristicScore evaluateHeuristicScore(Grid2048 grid) {
        HeuristicScore score = new HeuristicScore(0);
        int y = 0;
        int x = 0;

        while (y < Grid2048.SQUARES_PER_AXIS) {
            while (x < Grid2048.SQUARES_PER_AXIS) {
                score = new HeuristicScore(evaluateHeuristicScore(grid, y, x, score.getValue()));
                x++;
            }
            y++;
            x = 0;
        }
        return score;
    }

    static void establishKeyEventSequence(Grid2048 grid, Scoreboard scoreboard, Map<Integer, KeyEventHandler> handlerMap, List<Integer> scores) {
        KeyEventHandler upHandler = grid.handleKeyEventWithoutRandomTile(KeyEvent.UP, scoreboard);
        KeyEventHandler downHandler = grid.handleKeyEventWithoutRandomTile(KeyEvent.DOWN, scoreboard);
        KeyEventHandler leftHandler = grid.handleKeyEventWithoutRandomTile(KeyEvent.LEFT, scoreboard);
        KeyEventHandler rightHandler = grid.handleKeyEventWithoutRandomTile(KeyEvent.RIGHT, scoreboard);

        HeuristicScore upScore = new SnakeHeuristic().evaluateHeuristicScore(upHandler.getGrid2048());
        HeuristicScore downScore = new SnakeHeuristic().evaluateHeuristicScore(downHandler.getGrid2048());
        HeuristicScore leftScore = new SnakeHeuristic().evaluateHeuristicScore(leftHandler.getGrid2048());
        HeuristicScore rightScore = new SnakeHeuristic().evaluateHeuristicScore(rightHandler.getGrid2048());

        handlerMap.put(upScore.getValue(), upHandler);
        handlerMap.put(downScore.getValue(), downHandler);
        handlerMap.put(leftScore.getValue(), leftHandler);
        handlerMap.put(rightScore.getValue(), rightHandler);

        scores.add(upScore.getValue());
        scores.add(downScore.getValue());
        scores.add(leftScore.getValue());
        scores.add(rightScore.getValue());
        scores.sort(Comparator.reverseOrder());
    }

    static int evaluateHeuristicScore(Grid2048 grid, int y , int x, int score) {
        Square square = grid.getSquareByCoordinates(y,x);
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

package compare;

import heuristic.GameHeuristic;
import heuristic.SnakeHeuristic;
import models.HeuristicComparison;
import models.game.Grid2048;
import models.game.KeyEvent;
import models.game.KeyEventHandler;
import models.game.Scoreboard;
import models.square.Square;

public class CompareHeuristic {
    private final static int TREE_DEPTH = 4;

    public static void main(String[] args) {
        HeuristicComparison comparison = getHeuristicComparison(new SnakeHeuristic(), 50);
        System.out.print("Snake Heuristic \n");
        System.out.print("Average Score: " + comparison.getAverageScore() + "\n");
        System.out.print("Highest Score: " + comparison.getHighestScore() + "\n");
        System.out.print("Highest Score Grid: \n");

        for (int idxRow = 0; idxRow < Grid2048.SQUARES_PER_AXIS; idxRow++) {
            for (int idxCol = 0; idxCol < Grid2048.SQUARES_PER_AXIS; idxCol++) {
                Square currentSquare = comparison.getBestGrid().getSquareByCoordinates(idxRow, idxCol);
                System.out.printf("%-10s", currentSquare.getValue());
            }
            System.out.print("\n");
        }
    }

    static HeuristicComparison getHeuristicComparison (GameHeuristic heuristic, int timesToComplete) {
        int highestScore = 0;
        int averageScore = 0;
        int idx = 0;
        Grid2048 bestGrid = new Grid2048();
        while (idx < timesToComplete) {
            KeyEventHandler currentHandler = completePlayerCycle(heuristic);
            int currentScore = currentHandler.getScoreboard().getPoints();
            if (currentScore > highestScore) {
                highestScore = currentScore;
                bestGrid = currentHandler.getGrid2048();
            }
            averageScore += currentScore;
            idx++;
        }
        return new HeuristicComparison(highestScore, averageScore/timesToComplete, bestGrid);
    }

    static KeyEventHandler completePlayerCycle(GameHeuristic heuristic) {
        KeyEventHandler handler = new KeyEventHandler(false, new Grid2048(), new Scoreboard(0));
        return completePlayerCycle(heuristic, handler);
    }

    static KeyEventHandler completePlayerCycle(GameHeuristic heuristic, KeyEventHandler handler) {
        if (isGameOver(handler)){
            return handler;
        }
        KeyEventHandler newHandler = heuristic.getNextMove(TREE_DEPTH, handler);
        Grid2048.addRandomTileOnKeyEventHandler(newHandler);
        return completePlayerCycle(heuristic, newHandler);
    }

    static boolean isGameOver(KeyEventHandler handler) {
        Grid2048 grid = handler.getGrid2048();
        Scoreboard scoreboard = handler.getScoreboard();

        KeyEventHandler upHandler = grid.handleKeyEvent(KeyEvent.UP, scoreboard);
        KeyEventHandler downHandler = grid.handleKeyEvent(KeyEvent.DOWN, scoreboard);
        KeyEventHandler leftHandler = grid.handleKeyEvent(KeyEvent.RIGHT, scoreboard);
        KeyEventHandler rightHandler = grid.handleKeyEvent(KeyEvent.LEFT, scoreboard);

        return  !upHandler.isTilesMoved() &&
                !downHandler.isTilesMoved() &&
                !leftHandler.isTilesMoved() &&
                !rightHandler.isTilesMoved();
    }
}

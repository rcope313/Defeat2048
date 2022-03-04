package compare;

import heuristic.GameHeuristic;
import heuristic.SnakeHeuristic;
import models.HeuristicComparison;
import models.game.Grid2048;
import models.game.KeyEvent;
import models.game.KeyEventHandler;
import models.game.Scoreboard;

public class CompareHeuristic {

    public static void main(String[] args) {
        HeuristicComparison comparison = getHeuristicComparison(new SnakeHeuristic(), 50);
        System.out.print("Snake Heuristic \n");
        System.out.print("Highest Score: " + comparison.getHighestScore() + "\n");
        System.out.print("Average Score: " + comparison.getAverageScore() + "\n");
    }

    static HeuristicComparison getHeuristicComparison (GameHeuristic heuristic, int timesToComplete) {
        int highestScore = 0;
        int averageScore = 0;
        int idx = 0;
        while (idx < timesToComplete) {
            int currentScore = completePlayerCycle(heuristic);
            if (currentScore > highestScore) {
                highestScore = currentScore;
            }
            averageScore += currentScore;
            idx++;
        }
        return new HeuristicComparison(highestScore, averageScore/timesToComplete);
    }

    static int completePlayerCycle(GameHeuristic heuristic) {
        Grid2048 initGrid = new Grid2048();
        Scoreboard initBoard = new Scoreboard(0);
        return completePlayerCycle(heuristic, initGrid, initBoard);
    }

    static int completePlayerCycle(GameHeuristic heuristic, Grid2048 grid, Scoreboard board) {
        if (isGameOver(grid, board)) {
            return board.getPoints();
        }
        KeyEventHandler handler = heuristic.evaluateNextGameState(grid, board);
        Grid2048.addRandomTileOnKeyEventHandler(handler);
        return completePlayerCycle(heuristic, handler.getGrid2048(), handler.getScoreboard());
    }

    static boolean isGameOver(Grid2048 grid, Scoreboard scoreboard) {
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

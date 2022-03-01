package compare;

import heuristic.GameHeuristic;
import heuristic.PreferUpHeuristic;
import heuristic.SnakeHeuristic;
import models.HeuristicComparison;
import models.game.Grid2048;
import models.game.KeyEvent;
import models.game.KeyEventHandler;
import models.game.Scoreboard;

public class CompareHeuristic {

    public static void main(String[] args) {
        HeuristicComparison comparison = getHeuristicComparison(new PreferUpHeuristic(), 50);
        System.out.print("PreferUp Heuristic \n");
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
        Grid2048.createRandomTileOnKeyEventHandler(handler);
        return completePlayerCycle(heuristic, handler.getGrid2048(), handler.getScoreboard());
    }

    static boolean isGameOver(Grid2048 grid, Scoreboard scoreboard) {
        Grid2048 gridUp = grid.handleKeyEventWithRandomTile(KeyEvent.UP, scoreboard).getGrid2048();
        Grid2048 gridDown = grid.handleKeyEventWithRandomTile(KeyEvent.DOWN, scoreboard).getGrid2048();
        Grid2048 gridRight = grid.handleKeyEventWithRandomTile(KeyEvent.RIGHT, scoreboard).getGrid2048();
        Grid2048 gridLeft = grid.handleKeyEventWithRandomTile(KeyEvent.LEFT, scoreboard).getGrid2048();

        return  gridUp.equals(grid)
                && gridDown.equals(grid)
                && gridRight.equals(grid)
                && gridLeft.equals(grid);
    }
}

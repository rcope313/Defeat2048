package compare;

import heuristic.GameHeuristic;
import heuristic.PreferUpHeuristic;
import heuristic.SnakeAndWorstCaseHeuristic;
import heuristic.SnakeHeuristic;
import models.HeuristicComparison;
import models.game.Grid2048;
import models.game.KeyEvent;
import models.game.KeyEventHandler;
import models.game.Scoreboard;
import models.square.Square;

public class CompareHeuristic {
    private final int treeDepth;
    private final int timesToComplete;
    private final GameHeuristic heuristic;

    public CompareHeuristic(int treeDepth, int timesToComplete, GameHeuristic heuristic) {
        this.treeDepth = treeDepth;
        this.timesToComplete = timesToComplete;
        this.heuristic = heuristic;
    }

    public static void main(String[] args) {
        if (args.length == 3) {
            CompareHeuristic compareHeuristic = new CompareHeuristic(Integer.parseInt(args[0]), Integer.parseInt(args[1]), getHeuristicByStringName(args[2]));
            HeuristicComparison comparison = compareHeuristic.getHeuristicComparison(compareHeuristic.heuristic);
            System.out.print(compareHeuristic.heuristic.getHeuristicName() + "\n");
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
        } else {
            throw new IllegalArgumentException("Expecting 3 arguments. Please see read me for proper play game execution");
        }
    }

    private HeuristicComparison getHeuristicComparison(GameHeuristic heuristic) {
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

    private KeyEventHandler completePlayerCycle(GameHeuristic heuristic) {
        KeyEventHandler handler = new KeyEventHandler(false, new Grid2048(), new Scoreboard(0));
        return completePlayerCycle(heuristic, handler);
    }

    private KeyEventHandler completePlayerCycle(GameHeuristic heuristic, KeyEventHandler handler) {
        if (isGameOver(handler)){
            return handler;
        }
        KeyEventHandler newHandler = heuristic.getNextMove(treeDepth, handler);
        Grid2048.addRandomTileOnKeyEventHandler(newHandler);
        return completePlayerCycle(heuristic, newHandler);
    }

    private static boolean isGameOver(KeyEventHandler handler) {
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

    private static GameHeuristic getHeuristicByStringName(String str) {
        if (str.equals("Prefer Up Heuristic")) {
            return new PreferUpHeuristic();
        }
        if (str.equals("Snake Heuristic")) {
            return new SnakeHeuristic();
        }
        if (str.equals("Snake and Worst Case Heuristic")) {
            return new SnakeAndWorstCaseHeuristic();
        } else {
            throw new IllegalArgumentException("Not a valid heuristic. Check read me for all available heuristics and their respective spelling");
        }
    }
}

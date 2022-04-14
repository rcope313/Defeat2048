package heuristic;

import com.google.common.annotations.VisibleForTesting;
import models.HeuristicScore;
import models.game.Grid2048;
import models.game.KeyEvent;
import models.game.KeyEventHandler;
import models.game.Scoreboard;

public class SnakeAndWorstCaseHeuristic extends GameHeuristic {

    @Override
    public HeuristicScore evaluateHeuristicScore(KeyEventHandler handler) {
        if (checkGridForWorstCase(handler) && !isGridStackable(handler)) {
            return new HeuristicScore(0);
        } else {
            return new SnakeHeuristic().evaluateHeuristicScore(handler);
        }
    }

    @Override
    public String getHeuristicName() {
        return "Snake and Worst Case Heuristic";
    }

    @VisibleForTesting
    static boolean checkGridForWorstCase(KeyEventHandler handler) {
        int[] numTilesPerRow = getNumTilesPerRow(handler);
        boolean hasBadRow = false;
        if (numTilesPerRow[0]!= 4) {
            return false;
        }
        for (int idx = 1; idx < numTilesPerRow.length; idx++) {
            if (hasBadRow) {
                if (numTilesPerRow[idx] != 0) {
                    return false;
                }
            } else {
                if (numTilesPerRow[idx] < 3) {
                    return false;
                }
                if (numTilesPerRow[idx] == 3) {
                    hasBadRow = true;
                }
            }
        }
        return true;
    }

    static int[] getNumTilesPerRow(KeyEventHandler handler) {
        int[] numTilesPerRow = new int[Grid2048.SQUARES_PER_AXIS];
        int numTilesInRow = 0;
        Grid2048 grid = handler.getGrid2048();

        for (int idxRow = 0; idxRow < Grid2048.SQUARES_PER_AXIS; idxRow++) {
            for (int idxCol = 0; idxCol < Grid2048.SQUARES_PER_AXIS; idxCol++) {
                if (grid.getSquareByCoordinates(idxCol, idxRow).isTile()) {
                    numTilesInRow++;
                }
            }
            numTilesPerRow[idxRow] = numTilesInRow;
            numTilesInRow = 0;
        }
        return numTilesPerRow;
    }

    @VisibleForTesting
    static boolean isGridStackable(KeyEventHandler handler) {
        Grid2048 currentGrid = handler.getGrid2048();
        Scoreboard scoreboard = handler.getScoreboard();
        KeyEventHandler upHandler = currentGrid.handleKeyEvent(KeyEvent.UP, scoreboard);
        KeyEventHandler rightHandler = currentGrid.handleKeyEvent(KeyEvent.RIGHT, scoreboard);
        KeyEventHandler leftHandler = currentGrid.handleKeyEvent(KeyEvent.LEFT, scoreboard);

        int currentNumOfEmptyTiles = currentGrid.getEmptyTilePosns().size(),
                upNumOfEmptyTiles = upHandler.getGrid2048().getEmptyTilePosns().size(),
                leftNumOfEmptyTiles = leftHandler.getGrid2048().getEmptyTilePosns().size(),
                rightNumOfEmptyTiles = rightHandler.getGrid2048().getEmptyTilePosns().size();

        return currentNumOfEmptyTiles != upNumOfEmptyTiles ||
                currentNumOfEmptyTiles != leftNumOfEmptyTiles ||
                currentNumOfEmptyTiles != rightNumOfEmptyTiles;
    }
}

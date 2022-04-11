package heuristic;

import com.google.common.annotations.VisibleForTesting;
import models.HeuristicScore;
import models.WorstCase;
import models.game.Grid2048;
import models.game.KeyEventHandler;
import models.square.Square;

public class SnakeAndWorstCaseHeuristic extends GameHeuristic {

    @Override
    public HeuristicScore evaluateHeuristicScore(KeyEventHandler handler) {
        if (isGameStateForWorstCase(handler)) {
            return new HeuristicScore(0);
        } else {
            return new SnakeHeuristic().evaluateHeuristicScore(handler);
        }
    }

    @Override
    public String getHeuristicName() {
        return "Snake and Worst Case Heuristic";
    }

    public static boolean isGameStateForWorstCase(KeyEventHandler handler) {
        WorstCase worstCase = new WorstCase(0, false);
        for (int idxRow = 0; idxRow < Grid2048.SQUARES_PER_AXIS; idxRow++) {
            checkRowForWorstCase(idxRow, handler, worstCase);
        }
        return worstCase.isWorstCase();
    }

    @VisibleForTesting
    static void checkRowForWorstCase(int idxRow, KeyEventHandler handler, WorstCase worstCase) {
        int lastSeenTile = 0;
        int numEmptySquares = 0;
        Grid2048 grid = handler.getGrid2048();

        for (int idxCol = 0 ; idxCol < Grid2048.SQUARES_PER_AXIS; idxCol++) {
            Square currentSquare = grid.getSquareByCoordinates(idxCol, idxRow);
            if (currentSquare.isEmptyTile()) {
                numEmptySquares++;
            } else {
                if (lastSeenTile != currentSquare.getValue()) {
                    lastSeenTile = currentSquare.getValue();
                } else {
                    worstCase.setStackable(true);
                }
            }
        }
        if (numEmptySquares != 4) {
            worstCase.setNumEmptySquare(worstCase.getNumEmptySquare() + numEmptySquares);
        }
    }
}

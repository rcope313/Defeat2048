package heuristic;

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

    public boolean isGameStateForWorstCase(KeyEventHandler handler) {
        WorstCase worstCase = new WorstCase(0, false);
        for (int idxRow = 0; idxRow < Grid2048.SQUARES_PER_AXIS; idxRow++) {
            checkRowForWorstCase(idxRow, handler, worstCase);
        }
        return worstCase.isWorstCase();
    }

    private void checkRowForWorstCase(int idxRow, KeyEventHandler handler, WorstCase worstCase) {
        int stackValue = 0;
        for (int idxCol = 0 ; idxCol < Grid2048.SQUARES_PER_AXIS; idxCol++) {
            Grid2048 grid = handler.getGrid2048();
            Square currentSquare = grid.getSquareByCoordinates(idxRow, idxCol);
            if (currentSquare.isEmptyTile()) {
                worstCase.setNumEmptySquare(worstCase.getNumEmptySquare() + 1);
            } else {
                if (stackValue != currentSquare.getValue()) {
                    stackValue = currentSquare.getValue();
                } else {
                    worstCase.setStackable(true);
                }
            }
        }
    }
}

package models.grid2048;

import models.game2048.KeyEventHandler;
import models.game2048.Scoreboard;

public enum Grid2048Event {
    UP, DOWN, LEFT, RIGHT;

    public KeyEventHandler buildKeyEventHandlerOnKeyEvent(Grid2048 grid, Scoreboard scoreboard) {
        return switch (this) {
            case UP -> grid.handleUpEvent(scoreboard, true);
            case DOWN -> grid.handleDownEvent(scoreboard, true);
            case RIGHT -> grid.handleRightEvent(scoreboard, true);
            case LEFT -> grid.handleLeftEvent(scoreboard, true);
        };
    }

    public KeyEventHandler buildKeyEventHandlerOnKeyEventNoRandomTile(Grid2048 grid, Scoreboard scoreboard) {
        return switch (this) {
            case UP -> grid.handleUpEvent(scoreboard, false);
            case DOWN -> grid.handleDownEvent(scoreboard, false);
            case RIGHT -> grid.handleRightEvent(scoreboard, false);
            case LEFT -> grid.handleLeftEvent(scoreboard, false);
        };
    }

}

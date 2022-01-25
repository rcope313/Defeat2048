package heuristic;

import models.game2048.Scoreboard;
import models.grid2048.Grid2048;
import models.grid2048.Grid2048Event;
import models.game2048.KeyEventHandler;

public abstract class GameHeuristic {
    private final Grid2048 grid2048;
    private final Scoreboard scoreboard;
    private final KeyEventHandler keyEventUp;
    private final KeyEventHandler keyEventDown;
    private final KeyEventHandler keyEventLeft;
    private final KeyEventHandler keyEventRight;

    public GameHeuristic(Grid2048 grid2048, Scoreboard scoreboard) {
        this.grid2048 = grid2048;
        this.scoreboard = scoreboard;
        this.keyEventUp = Grid2048Event.UP.buildKeyEventHandlerOnKeyEventNoRandomTile(grid2048, scoreboard);
        this.keyEventDown = Grid2048Event.DOWN.buildKeyEventHandlerOnKeyEventNoRandomTile(grid2048, scoreboard);
        this.keyEventLeft = Grid2048Event.LEFT.buildKeyEventHandlerOnKeyEventNoRandomTile(grid2048, scoreboard);
        this.keyEventRight = Grid2048Event.RIGHT.buildKeyEventHandlerOnKeyEventNoRandomTile(grid2048, scoreboard);
    }

    public abstract KeyEventHandler evaluateNextGameState();

    public Grid2048 getGrid2048() {
        return grid2048;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public KeyEventHandler getKeyEventUp() {
        return keyEventUp;
    }

    public KeyEventHandler getKeyEventDown() {
        return keyEventDown;
    }

    public KeyEventHandler getKeyEventLeft() {
        return keyEventLeft;
    }

    public KeyEventHandler getKeyEventRight() {
        return keyEventRight;
    }

}


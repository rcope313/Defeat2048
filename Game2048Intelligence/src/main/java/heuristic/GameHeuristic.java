package heuristic;

import models.game.KeyEventHandler;
import models.game.Scoreboard;
import models.game.Grid2048;

public abstract class GameHeuristic {

    public abstract KeyEventHandler evaluateNextGameState(Grid2048 grid, Scoreboard scoreboard);
}

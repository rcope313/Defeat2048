package heuristic;

import models.GameStateTree;
import models.HeuristicScore;
import models.game.KeyEventHandler;

public abstract class GameHeuristic {

    public abstract HeuristicScore evaluateHeuristicScore(KeyEventHandler handler);

    public KeyEventHandler getNextMove(int treeDepth, KeyEventHandler handler) {
        return new GameStateTree(this, treeDepth, handler).getNextMove();
    }

    public abstract String getHeuristicName();

}

package models;

import heuristic.GameHeuristic;
import models.game.Grid2048;
import models.game.KeyEvent;
import models.game.KeyEventHandler;
import models.game.Scoreboard;
import org.assertj.core.util.VisibleForTesting;
import java.util.ArrayList;

public class GameStateTree {
    private final GameHeuristic heuristic;
    private final int treeDepth;
    private KeyEventHandler handler;
    private KeyEvent direction;
    private ArrayList<GameStateTree> children;
    private GameStateTree parent;

    public GameStateTree(GameHeuristic heuristic, int treeDepth) {
        this.heuristic = heuristic;
        this.treeDepth = treeDepth;
    }

    public GameStateTree(GameHeuristic heuristic, int treeDepth, KeyEventHandler handler) {
        this.heuristic = heuristic;
        this.treeDepth = treeDepth;
        this.handler = handler;
        this.direction = KeyEvent.NOUP;
        this.children = new ArrayList<>();
        this.parent = null;
    }

    public GameStateTree(GameHeuristic heuristic, int treeDepth, KeyEventHandler handler, KeyEvent direction, ArrayList<GameStateTree> children, GameStateTree parent) {
        this.heuristic = heuristic;
        this.treeDepth = treeDepth;
        this.handler = handler;
        this.direction = direction;
        this.children = children;
        this.parent = parent;
    }

    public KeyEventHandler getNextMove() {
        ArrayList<GameStateTree> bottomRow = buildGameStateTreeAndGetBottomRow(new ArrayList<>(), treeDepth);
        GameStateTree bestNode = getHighestScoringNodeOfBottomRow(bottomRow);
        return getNextMove(bestNode);
    }

    private static KeyEventHandler getNextMove(GameStateTree bestNode) {
        GameStateTree currentNode = bestNode;

        while (currentNode.parent.direction != KeyEvent.NOUP) {
            currentNode = currentNode.parent;
        }
        return currentNode.getHandler();
    }

    @VisibleForTesting
    GameStateTree getHighestScoringNodeOfBottomRow(ArrayList<GameStateTree> bottomRow) {
        int highestScore = 0;
        GameStateTree bestNode = new GameStateTree(heuristic, treeDepth);

        for (GameStateTree node : bottomRow) {
            int currentScore = heuristic.evaluateHeuristicScore(node.handler).getValue();
            if (currentScore > highestScore) {
                highestScore = currentScore;
                bestNode = node;
            }
        }
        return bestNode;
    }

    @VisibleForTesting
    ArrayList<GameStateTree> buildGameStateTreeAndGetBottomRow(ArrayList<GameStateTree> bottomRow, int treeDepth) {
        treeDepth--;
        if (treeDepth == 0) {
            bottomRow.add(this);
            return bottomRow;
        }
        buildChildrenOfGameStateTree();
        for (GameStateTree child : children) {
            child.buildGameStateTreeAndGetBottomRow(bottomRow, treeDepth);
        }
        return bottomRow;
    }

    private void buildChildrenOfGameStateTree() {
        Grid2048 grid = handler.getGrid2048();
        Scoreboard scoreboard = handler.getScoreboard();
        KeyEventHandler upHandler = grid.handleKeyEvent(KeyEvent.UP, scoreboard);
        KeyEventHandler leftHandler = grid.handleKeyEvent(KeyEvent.LEFT, scoreboard);
        KeyEventHandler rightHandler = grid.handleKeyEvent(KeyEvent.RIGHT, scoreboard);
        KeyEventHandler downHandler = grid.handleKeyEvent(KeyEvent.DOWN, scoreboard);

        if (upHandler.isTilesMoved() && heuristic.evaluateHeuristicScore(upHandler).getValue() != 0) {
            children.add(new GameStateTree(heuristic, treeDepth, upHandler, KeyEvent.UP, new ArrayList<>(), this));
        }
        if (leftHandler.isTilesMoved() && heuristic.evaluateHeuristicScore(leftHandler).getValue() != 0) {
            children.add(new GameStateTree(heuristic, treeDepth, leftHandler, KeyEvent.LEFT, new ArrayList<>(), this));
        }
        if (rightHandler.isTilesMoved() && heuristic.evaluateHeuristicScore(rightHandler).getValue() != 0) {
            children.add(new GameStateTree(heuristic, treeDepth, rightHandler, KeyEvent.RIGHT, new ArrayList<>(), this));
        }
        if (children.isEmpty() && downHandler.isTilesMoved()) {
            children.add(new GameStateTree(heuristic, treeDepth, downHandler, KeyEvent.DOWN, new ArrayList<>(), this));
        }
    }

    public KeyEventHandler getHandler() {
        return handler;
    }

}

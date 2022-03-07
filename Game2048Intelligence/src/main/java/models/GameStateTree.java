package models;

import heuristic.GameHeuristic;
import models.game.Grid2048;
import models.game.KeyEvent;
import models.game.KeyEventHandler;
import models.game.Scoreboard;
import org.assertj.core.util.VisibleForTesting;
import java.util.ArrayList;

public class GameStateTree {
    private KeyEventHandler handler;
    private KeyEvent direction;
    private ArrayList<GameStateTree> children;
    private GameStateTree parent;

    public GameStateTree() {
    }

    public GameStateTree(KeyEventHandler handler) {
        this.handler = handler;
        this.direction = KeyEvent.NOUP;
        this.children = new ArrayList<>();
        this.parent = null;
    }

    public GameStateTree(KeyEventHandler handler, KeyEvent direction, ArrayList<GameStateTree> children, GameStateTree parent) {
        this.handler = handler;
        this.direction = direction;
        this.children = children;
        this.parent = parent;
    }

    public static KeyEventHandler getNextMove(int treeDepth, GameHeuristic heuristic, KeyEventHandler currentHandler) {
        GameStateTree headNode = new GameStateTree(currentHandler, KeyEvent.NOUP, new ArrayList<>(), null);
        ArrayList<GameStateTree> bottomRow = buildGameStateTreeAndGetBottomRow(headNode, treeDepth, new ArrayList<>());
        GameStateTree bestNode = getHighestScoringNodeOfBottomRow(bottomRow, heuristic);
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
    static GameStateTree getHighestScoringNodeOfBottomRow(ArrayList<GameStateTree> bottomRow, GameHeuristic heuristic) {
        int highestScore = 0;
        GameStateTree bestNode = new GameStateTree();

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
    static ArrayList<GameStateTree> buildGameStateTreeAndGetBottomRow(GameStateTree currentNode, int treeDepth, ArrayList<GameStateTree> bottomRow) {
        treeDepth--;
        if (treeDepth == 0) {
            bottomRow.add(currentNode);
            return bottomRow;
        }
        buildChildrenOfGameStateTree(currentNode);
        for (GameStateTree child : currentNode.children) {
            buildGameStateTreeAndGetBottomRow(child, treeDepth, bottomRow);
        }
        return bottomRow;
    }

    private static void buildChildrenOfGameStateTree(GameStateTree currentNode) {
        Grid2048 grid = currentNode.handler.getGrid2048();
        Scoreboard scoreboard = currentNode.handler.getScoreboard();
        KeyEventHandler upHandler = grid.handleKeyEvent(KeyEvent.UP, scoreboard);
        KeyEventHandler leftHandler = grid.handleKeyEvent(KeyEvent.LEFT, scoreboard);
        KeyEventHandler rightHandler = grid.handleKeyEvent(KeyEvent.RIGHT, scoreboard);
        KeyEventHandler downHandler = grid.handleKeyEvent(KeyEvent.DOWN, scoreboard);

        if (upHandler.isTilesMoved()) {
            currentNode.children.add(new GameStateTree(upHandler, KeyEvent.UP, new ArrayList<>(), currentNode));
        }
        if (leftHandler.isTilesMoved()) {
            currentNode.children.add(new GameStateTree(leftHandler, KeyEvent.LEFT, new ArrayList<>(), currentNode));
        }
        if (rightHandler.isTilesMoved()) {
            currentNode.children.add(new GameStateTree(rightHandler, KeyEvent.RIGHT, new ArrayList<>(), currentNode));
        }
        if (currentNode.children.isEmpty() && downHandler.isTilesMoved()) {
            currentNode.children.add(new GameStateTree(downHandler, KeyEvent.DOWN, new ArrayList<>(), currentNode));
        }
    }

    public KeyEventHandler getHandler() {
        return handler;
    }

}

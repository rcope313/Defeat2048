package models;

import heuristic.GameHeuristic;
import models.game.Grid2048;
import models.game.KeyEvent;
import models.game.KeyEventHandler;
import models.game.Scoreboard;

import java.util.ArrayList;

public class GameStateTree {
    private KeyEventHandler handler;
    private ArrayList<GameStateTree> children;
    private GameStateTree parent;

    public GameStateTree() {}

    public GameStateTree(KeyEventHandler handler, ArrayList<GameStateTree> children, GameStateTree parent) {
        this.handler = handler;
        this.children = children;
        this.parent = parent;
    }

    public static ArrayList<KeyEventHandler> getKeyEventSequence(int treeDepth, GameHeuristic heuristic) {
        GameStateTree headNode = new GameStateTree();
        ArrayList<GameStateTree> bottomRow = buildGameStateTreeAndGetBottomRow(headNode, treeDepth, new ArrayList<>());
        GameStateTree bestNode = getHighestScoringNodeOfBottomRow(bottomRow, heuristic);
        return getKeyEventSequence(bestNode);
    }

    private static ArrayList<KeyEventHandler> getKeyEventSequence(GameStateTree bestNode) {
        ArrayList<KeyEventHandler> keyEventSequence = new ArrayList<>();
        GameStateTree currentNode = bestNode;

        while (currentNode.parent != null) {
            keyEventSequence.add(0, currentNode.handler);
            currentNode = currentNode.parent;
        }
        return keyEventSequence;
    }

    private static GameStateTree getHighestScoringNodeOfBottomRow(ArrayList<GameStateTree> bottomRow, GameHeuristic heuristic) {
        int highestScore = 0;
        GameStateTree bestNode = new GameStateTree();

        for (GameStateTree node : bottomRow) {
            if (heuristic.evaluateHeuristicScore(node.handler.getGrid2048()).getValue() > highestScore) {
                bestNode = node;
            }
        }
        return bestNode;
    }

    private static ArrayList<GameStateTree> buildGameStateTreeAndGetBottomRow(GameStateTree currentNode, int treeDepth, ArrayList<GameStateTree> bottomRow) {
        treeDepth--;
        if (treeDepth == 0) {
            bottomRow.add(currentNode);
            return bottomRow;
        }
        buildChildrenOfGameStateTree(currentNode);
        for (GameStateTree child : currentNode.children) {
            return buildGameStateTreeAndGetBottomRow(child, treeDepth, bottomRow);
        }
        return bottomRow;
    }

    private static void buildChildrenOfGameStateTree(GameStateTree currentNode) {
        Grid2048 grid = currentNode.handler.getGrid2048();
        Scoreboard scoreboard = currentNode.handler.getScoreboard();

        ArrayList<KeyEventHandler> handlerList = new ArrayList<>();
        handlerList.add(grid.handleKeyEventWithoutRandomTile(KeyEvent.UP, scoreboard));
        handlerList.add(grid.handleKeyEventWithoutRandomTile(KeyEvent.LEFT, scoreboard));
        handlerList.add(grid.handleKeyEventWithoutRandomTile(KeyEvent.RIGHT, scoreboard));

        for (int idx = 0; idx <= handlerList.size(); idx++) {
            if (idx == handlerList.size()) {
                currentNode.children.add(new GameStateTree(grid.handleKeyEventWithoutRandomTile(KeyEvent.DOWN, scoreboard), new ArrayList<>(), currentNode));
                break;
            }
            if (handlerList.get(idx).isTilesMoved()) {
                currentNode.children.add(new GameStateTree(grid.handleKeyEventWithoutRandomTile(KeyEvent.UP, scoreboard), new ArrayList<>(), currentNode));
                break;
            }
        }
    }
}

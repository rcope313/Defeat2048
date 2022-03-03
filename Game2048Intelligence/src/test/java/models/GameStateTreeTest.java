package models;

import heuristic.SnakeHeuristic;
import javalib.worldimages.Posn;
import models.game.Grid2048;
import models.game.KeyEvent;
import models.game.KeyEventHandler;
import models.game.Scoreboard;
import models.square.EmptySquare;
import models.square.Square;
import models.square.Tile;
import org.junit.Test;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.*;

public class GameStateTreeTest {
    Square[] rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3, row0, row1, row2, row3;
    Grid2048 g0, g1;

    void initData() {
        rowEmpty0 = new Square[]{
                new EmptySquare(new Posn(0, 0)),
                new EmptySquare(new Posn(0, 1)),
                new EmptySquare(new Posn(0, 2)),
                new EmptySquare(new Posn(0, 3))};
        rowEmpty1 = new Square[]{
                new EmptySquare(new Posn(1, 0)),
                new EmptySquare(new Posn(1, 1)),
                new EmptySquare(new Posn(1, 2)),
                new EmptySquare(new Posn(1, 3))};
        rowEmpty2 = new Square[]{
                new EmptySquare(new Posn(2, 0)),
                new EmptySquare(new Posn(2, 1)),
                new EmptySquare(new Posn(2, 2)),
                new EmptySquare(new Posn(2, 3))};
        rowEmpty3 = new Square[]{
                new EmptySquare(new Posn(3, 0)),
                new EmptySquare(new Posn(3, 1)),
                new EmptySquare(new Posn(3, 2)),
                new EmptySquare(new Posn(3, 3))};

        g0 = new Grid2048(new Square[][]{rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3});

        row0 = new Square[]{
                new Tile(4, new Posn(0, 0)),
                new EmptySquare(new Posn(0, 1)),
                new EmptySquare(new Posn(0, 2)),
                new EmptySquare(new Posn(0, 3))};
        row1 = new Square[]{
                new EmptySquare(new Posn(1, 0)),
                new Tile(4, new Posn(1, 1)),
                new Tile(8, new Posn(1, 2)),
                new EmptySquare(new Posn(1, 3))};
        row2 = new Square[]{
                new Tile(2, new Posn(2, 0)),
                new EmptySquare(new Posn(2, 1)),
                new EmptySquare(new Posn(2, 2)),
                new EmptySquare(new Posn(2, 3))};
        row3 = new Square[]{
                new EmptySquare(new Posn(3, 0)),
                new EmptySquare(new Posn(3, 1)),
                new EmptySquare(new Posn(3, 2)),
                new Tile(2, new Posn(3, 3))};

        g1 = new Grid2048(new Square[][]{row0, row1, row2, row3});
    }

    @Test
    public void itReturnsBestNodeOfBottomRowAsDeterminedByHeuristicScore() {
        this.initData();
        KeyEventHandler handler = new KeyEventHandler(true, g1, new Scoreboard(0));
        GameStateTree headNode = new GameStateTree(handler, KeyEvent.NOUP, new ArrayList<>(), null);
        ArrayList<GameStateTree> bottomRow = GameStateTree.buildGameStateTreeAndGetBottomRow(headNode,3, new ArrayList<>());

        SnakeHeuristic snakeHeuristic = new SnakeHeuristic();
        GameStateTree bestNode = GameStateTree.getHighestScoringNodeOfBottomRow(bottomRow, snakeHeuristic);

        assertThat(snakeHeuristic.evaluateHeuristicScore(bestNode.getHandler().getGrid2048()).getValue()).isEqualTo(161400);
    }

    @Test
    public void itCreatesGameStateTreeAndReturnBottomRow() {
        this.initData();
        KeyEventHandler handler = new KeyEventHandler(true, g1, new Scoreboard(0));
        GameStateTree headNode = new GameStateTree(handler, KeyEvent.NOUP, new ArrayList<>(), null);
        ArrayList<GameStateTree> bottomRow = GameStateTree.buildGameStateTreeAndGetBottomRow(headNode,3, new ArrayList<>());
        assertThat(bottomRow.size()).isEqualTo(6);
    }

    @Test
    public void itCreatesGameStateTreeAndReturnBottomRowOnEmptyBoard() {
        this.initData();
        KeyEventHandler handler = new KeyEventHandler(true, g0, new Scoreboard(0));
        GameStateTree headNode = new GameStateTree(handler, KeyEvent.NOUP, new ArrayList<>(), null);
        ArrayList<GameStateTree> bottomRow = GameStateTree.buildGameStateTreeAndGetBottomRow(headNode,3, new ArrayList<>());
        assertThat(bottomRow.size()).isEqualTo(0);
    }

}

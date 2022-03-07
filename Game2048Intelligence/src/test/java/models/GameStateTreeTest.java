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
    Square[] rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3,
            row10, row11, row12, row13,
            row20, row21, row22, row23,
            row30, row31, row32, row33,
            row40, row41, row42, row43;
    Grid2048 g0, g1, g2, g3, g4;

    void initData() {
        rowEmpty0 = new Square[]{
                new EmptySquare(new Posn(0, 0)),
                new EmptySquare(new Posn(1, 0)),
                new EmptySquare(new Posn(2, 0)),
                new EmptySquare(new Posn(3, 0))};
        rowEmpty1 = new Square[]{
                new EmptySquare(new Posn(0, 1)),
                new EmptySquare(new Posn(1, 1)),
                new EmptySquare(new Posn(2, 1)),
                new EmptySquare(new Posn(3, 1))};
        rowEmpty2 = new Square[]{
                new EmptySquare(new Posn(0, 2)),
                new EmptySquare(new Posn(1, 2)),
                new EmptySquare(new Posn(2, 2)),
                new EmptySquare(new Posn(3, 2))};
        rowEmpty3 = new Square[]{
                new EmptySquare(new Posn(0, 3)),
                new EmptySquare(new Posn(1, 3)),
                new EmptySquare(new Posn(2, 3)),
                new EmptySquare(new Posn(3, 3))};

        g0 = new Grid2048(new Square[][]{rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3});

        row10 = new Square[]{
                new Tile(4, new Posn(0, 0)),
                new EmptySquare(new Posn(1, 0)),
                new EmptySquare(new Posn(2, 0)),
                new EmptySquare(new Posn(3, 0))};
        row11 = new Square[]{
                new EmptySquare(new Posn(0, 1)),
                new Tile(4, new Posn(1, 1)),
                new Tile(8, new Posn(2, 1)),
                new EmptySquare(new Posn(3, 1))};
        row12 = new Square[]{
                new Tile(2, new Posn(0, 2)),
                new EmptySquare(new Posn(1, 2)),
                new EmptySquare(new Posn(2, 2)),
                new EmptySquare(new Posn(3, 2))};
        row13 = new Square[]{
                new EmptySquare(new Posn(0, 3)),
                new EmptySquare(new Posn(1, 3)),
                new EmptySquare(new Posn(2, 3)),
                new Tile(2, new Posn(3, 3))};

        g1 = new Grid2048(new Square[][]{row10, row11, row12, row13});

        row20 = new Square[]{
                new Tile(4, new Posn(0, 0)),
                new Tile(4, new Posn(1, 0)),
                new Tile(8, new Posn(2, 0)),
                new Tile(2, new Posn(3, 0))};
        row21 = new Square[]{
                new Tile(2, new Posn(0, 1)),
                new EmptySquare(new Posn(1, 1)),
                new EmptySquare(new Posn(2, 1)),
                new EmptySquare(new Posn(3, 1))};
        row22 = new Square[]{
                new EmptySquare(new Posn(0, 2)),
                new EmptySquare(new Posn(1, 2)),
                new EmptySquare(new Posn(2, 2)),
                new EmptySquare(new Posn(3, 2))};
        row23 = new Square[]{
                new EmptySquare(new Posn(0, 3)),
                new EmptySquare(new Posn(1, 3)),
                new EmptySquare(new Posn(2, 3)),
                new EmptySquare(new Posn(3, 3))};

        g2 = new Grid2048(new Square[][]{row20, row21, row22, row23});

        row30 = new Square[]{
                new Tile(4, new Posn(0, 0)),
                new Tile(2, new Posn(1, 0)),
                new Tile(4, new Posn(2, 0)),
                new Tile(2, new Posn(3, 0))};
        row31 = new Square[]{
                new Tile(2, new Posn(0, 1)),
                new Tile(4, new Posn(1, 1)),
                new Tile(2, new Posn(2, 1)),
                new Tile(4, new Posn(3, 1))};
        row32 = new Square[]{
                new EmptySquare(new Posn(0, 2)),
                new EmptySquare(new Posn(1, 2)),
                new EmptySquare(new Posn(2, 2)),
                new EmptySquare(new Posn(3, 2))};
        row33 = new Square[]{
                new EmptySquare(new Posn(0, 3)),
                new EmptySquare(new Posn(1, 3)),
                new EmptySquare(new Posn(2, 3)),
                new EmptySquare(new Posn(3, 3))};

        g3 = new Grid2048(new Square[][]{row30, row31, row32, row33});

        row40 = new Square[]{
                new EmptySquare(new Posn(0, 0)),
                new EmptySquare(new Posn(1, 0)),
                new EmptySquare(new Posn(2, 0)),
                new EmptySquare(new Posn(3, 0))};
        row41 = new Square[]{
                new EmptySquare(new Posn(0, 1)),
                new EmptySquare(new Posn(1, 1)),
                new EmptySquare(new Posn(2, 1)),
                new EmptySquare(new Posn(3, 1))};
        row42 = new Square[]{
                new Tile(4, new Posn(0, 2)),
                new Tile(2, new Posn(1, 2)),
                new Tile(4, new Posn(2, 2)),
                new Tile(2, new Posn(3, 2))};
        row43 = new Square[]{
                new Tile(2, new Posn(0, 3)),
                new Tile(4, new Posn(1, 3)),
                new Tile(2, new Posn(2, 3)),
                new Tile(4, new Posn(3, 3))};

        g4 = new Grid2048(new Square[][]{row40, row41, row42, row43});
    }

    @Test
    public void itDeterminesNextMove() {
        this.initData();
        KeyEventHandler currentHandler = new KeyEventHandler(true, g1, new Scoreboard(0));
        KeyEventHandler resultHandler = GameStateTree.getNextMove(3, new SnakeHeuristic(), currentHandler);
        assertThat(resultHandler).usingRecursiveComparison().isEqualTo(new KeyEventHandler(true, g2, new Scoreboard(0)));
    }

    @Test
    public void itDeterminesNextMoveDownOnly() {
        this.initData();
        KeyEventHandler currentHandler = new KeyEventHandler(true, g3, new Scoreboard(0));
        KeyEventHandler resultHandler = GameStateTree.getNextMove(3, new SnakeHeuristic(), currentHandler);
        assertThat(resultHandler).usingRecursiveComparison().isEqualTo(new KeyEventHandler(true, g3, new Scoreboard(0)));
    }

    @Test
    public void itReturnsBestNodeOfBottomRowAsDeterminedByHeuristicScore() {
        this.initData();
        KeyEventHandler handler = new KeyEventHandler(true, g1, new Scoreboard(0));
        GameStateTree headNode = new GameStateTree(handler, KeyEvent.NOUP, new ArrayList<>(), null);
        ArrayList<GameStateTree> bottomRow = GameStateTree.buildGameStateTreeAndGetBottomRow(headNode,3, new ArrayList<>());

        SnakeHeuristic snakeHeuristic = new SnakeHeuristic();
        GameStateTree bestNode = GameStateTree.getHighestScoringNodeOfBottomRow(bottomRow, snakeHeuristic);

        assertThat(snakeHeuristic.evaluateHeuristicScore(bestNode.getHandler()).getValue()).isEqualTo(169400);
    }

    @Test
    public void itCreatesGameStateTreeAndReturnBottomRow() {
        this.initData();
        KeyEventHandler handler = new KeyEventHandler(true, g1, new Scoreboard(0));
        GameStateTree headNode = new GameStateTree(handler, KeyEvent.NOUP, new ArrayList<>(), null);

        ArrayList<GameStateTree> bottomRow = GameStateTree.buildGameStateTreeAndGetBottomRow(headNode,3, new ArrayList<>());
        SnakeHeuristic snakeHeuristic = new SnakeHeuristic();

        assertThat(bottomRow.size()).isEqualTo(6);
        assertThat(snakeHeuristic.evaluateHeuristicScore(bottomRow.get(0).getHandler()).getValue()).isEqualTo(169400);
        assertThat(snakeHeuristic.evaluateHeuristicScore(bottomRow.get(1).getHandler()).getValue()).isEqualTo(152000);
        assertThat(snakeHeuristic.evaluateHeuristicScore(bottomRow.get(2).getHandler()).getValue()).isEqualTo(154800);
        assertThat(snakeHeuristic.evaluateHeuristicScore(bottomRow.get(3).getHandler()).getValue()).isEqualTo(39760);
        assertThat(snakeHeuristic.evaluateHeuristicScore(bottomRow.get(4).getHandler()).getValue()).isEqualTo(68280);
        assertThat(snakeHeuristic.evaluateHeuristicScore(bottomRow.get(5).getHandler()).getValue()).isEqualTo(49414);
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

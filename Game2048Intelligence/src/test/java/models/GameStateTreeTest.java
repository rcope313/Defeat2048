package models;

import heuristic.SnakeAndWorstCaseHeuristic;
import heuristic.SnakeHeuristic;
import models.game.Grid2048;
import models.game.KeyEventHandler;
import models.game.Scoreboard;
import models.square.EmptySquare;
import models.square.Square;
import models.square.Tile;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.*;

public class GameStateTreeTest {
    Square[] rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3,
            row10, row11, row12, row13,
            row20, row21, row22, row23,
            row30, row31, row32, row33,
            row40, row41, row42, row43,
            row50, row51, row52, row53,
            row60, row61, row62, row63,
            row70, row71, row72, row73;
    Grid2048 g0, g1, g2, g3, g4, g5, g6, g7;
    KeyEventHandler h0, h1, h2, h3, h4, h5, h6, h7;
    SnakeHeuristic snakeHeuristic = new SnakeHeuristic();
    SnakeAndWorstCaseHeuristic snakeAndWorstCaseHeuristic = new SnakeAndWorstCaseHeuristic();

    @Before
    public void initData() {
        rowEmpty0 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        rowEmpty1 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        rowEmpty2 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        rowEmpty3 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        g0 = new Grid2048(new Square[][]{rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3});
        h0 = new KeyEventHandler(false, g0, new Scoreboard(0));

        row10 = new Square[]{new Tile(4), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row11 = new Square[]{new EmptySquare(), new Tile(4), new Tile(8), new EmptySquare()};
        row12 = new Square[]{new Tile(2), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row13 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new Tile(2)};
        g1 = new Grid2048(new Square[][]{row10, row11, row12, row13});
        h1 = new KeyEventHandler(false, g1, new Scoreboard(0));

        row20 = new Square[]{new Tile(4), new Tile(4), new Tile(8), new Tile(2)};
        row21 = new Square[]{new Tile(2), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row22 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row23 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        g2 = new Grid2048(new Square[][]{row20, row21, row22, row23});
        h2 = new KeyEventHandler(true, g2, new Scoreboard(0));

        row30 = new Square[]{new Tile(4), new Tile(2), new Tile(4), new Tile(2)};
        row31 = new Square[]{new Tile(2), new Tile(4), new Tile(2), new Tile(4)};
        row32 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row33 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        g3 = new Grid2048(new Square[][]{row30, row31, row32, row33});
        h3 = new KeyEventHandler(false, g3, new Scoreboard(0));

        row40 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row41 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row42 = new Square[]{new Tile(4), new Tile(2), new Tile(4), new Tile(2)};
        row43 = new Square[]{new Tile(2), new Tile(4), new Tile(2), new Tile(4)};
        g4 = new Grid2048(new Square[][]{row40, row41, row42, row43});
        h4 = new KeyEventHandler(true, g4, new Scoreboard(0));

        row50 = new Square[]{new Tile(4), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row51 = new Square[]{new EmptySquare(), new Tile(32), new Tile(8), new EmptySquare()};
        row52 = new Square[]{new Tile(2), new Tile(16), new EmptySquare(), new Tile(4)};
        row53 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new Tile(2)};
        g5 = new Grid2048(new Square[][]{row50, row51, row52, row53});
        h5 = new KeyEventHandler(false, g5, new Scoreboard(0));

        row60 = new Square[]{new Tile(4), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row61 = new Square[]{new Tile(32), new Tile(8), new EmptySquare(), new EmptySquare()};
        row62 = new Square[]{new Tile(2), new Tile(16), new Tile(4), new EmptySquare()};
        row63 = new Square[]{new Tile(2), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        g6 = new Grid2048(new Square[][]{row60, row61, row62, row63});
        h6 = new KeyEventHandler(true, g6, new Scoreboard(0));

        row70 = new Square[]{new Tile(256), new Tile(512), new Tile(2), new Tile(8)};
        row71 = new Square[]{new Tile(32), new Tile(64), new Tile(256), new Tile(64)};
        row72 = new Square[]{new Tile(4), new Tile(16), new Tile(32), new Tile(16)};
        row73 = new Square[]{new Tile(2), new Tile(2), new Tile(8), new Tile(4)};
        g7 = new Grid2048(new Square[][]{row70, row71, row72, row73});
        h7 = new KeyEventHandler(true, g7, new Scoreboard(0));

    }

    @Test
    public void testWeirdThingHappening() {
        KeyEventHandler resultHandler = new GameStateTree(snakeAndWorstCaseHeuristic, 3, h7).getNextMove();
    }

    @Test
    public void itDeterminesNextMove() {
        KeyEventHandler resultHandler1 = new GameStateTree(snakeHeuristic, 3, h1).getNextMove();
        assertThat(resultHandler1).usingRecursiveComparison().isEqualTo(h2);
        KeyEventHandler resultHandler5 = new GameStateTree(snakeAndWorstCaseHeuristic, 2, h5).getNextMove();
        assertThat(resultHandler5).usingRecursiveComparison().isEqualTo(h6);
    }

    @Test
    public void itDeterminesNextMoveDownOnly() {
        KeyEventHandler resultHandler1 = new GameStateTree(snakeHeuristic, 3, h3).getNextMove();
        assertThat(resultHandler1).usingRecursiveComparison().isEqualTo(h4);
    }

    @Test
    public void itReturnsBestNodeOfBottomRowAsDeterminedByHeuristicScore() {
        GameStateTree headNode1 = new GameStateTree(snakeHeuristic, 3, h1);
        ArrayList<GameStateTree> bottomRow1 = headNode1.buildGameStateTreeAndGetBottomRow(new ArrayList<>(), 3);
        GameStateTree bestNode1 = headNode1.getHighestScoringNodeOfBottomRow(bottomRow1);
        assertThat(snakeHeuristic.evaluateHeuristicScore(bestNode1.getHandler()).getValue()).isEqualTo(169400);

        GameStateTree headNode5 = new GameStateTree(snakeAndWorstCaseHeuristic, 2, h5);
        ArrayList<GameStateTree> bottomRow5 = headNode5.buildGameStateTreeAndGetBottomRow(new ArrayList<>(), 2);
        GameStateTree bestNode5 = headNode5.getHighestScoringNodeOfBottomRow(bottomRow5);
        assertThat(snakeAndWorstCaseHeuristic.evaluateHeuristicScore(bestNode5.getHandler()).getValue()).isEqualTo(70774);
    }

    @Test
    public void itCreatesGameStateTreeAndReturnBottomRow() {
        GameStateTree headNode1 = new GameStateTree(snakeHeuristic, 3, h1);
        ArrayList<GameStateTree> bottomRow1 = headNode1.buildGameStateTreeAndGetBottomRow(new ArrayList<>(), 3);
        assertThat(bottomRow1.size()).isEqualTo(6);
        assertThat(snakeHeuristic.evaluateHeuristicScore(bottomRow1.get(0).getHandler()).getValue()).isEqualTo(169400);
        assertThat(snakeHeuristic.evaluateHeuristicScore(bottomRow1.get(1).getHandler()).getValue()).isEqualTo(152000);
        assertThat(snakeHeuristic.evaluateHeuristicScore(bottomRow1.get(2).getHandler()).getValue()).isEqualTo(154800);
        assertThat(snakeHeuristic.evaluateHeuristicScore(bottomRow1.get(3).getHandler()).getValue()).isEqualTo(39760);
        assertThat(snakeHeuristic.evaluateHeuristicScore(bottomRow1.get(4).getHandler()).getValue()).isEqualTo(68280);
        assertThat(snakeHeuristic.evaluateHeuristicScore(bottomRow1.get(5).getHandler()).getValue()).isEqualTo(49414);

        GameStateTree headNode5 = new GameStateTree(snakeAndWorstCaseHeuristic, 2, h5);
        ArrayList<GameStateTree> bottomRow5 = headNode5.buildGameStateTreeAndGetBottomRow(new ArrayList<>(), 2);
        assertThat(bottomRow5.size()).isEqualTo(2);
        assertThat(snakeAndWorstCaseHeuristic.evaluateHeuristicScore(bottomRow5.get(0).getHandler()).getValue()).isEqualTo(70774);
        assertThat(snakeAndWorstCaseHeuristic.evaluateHeuristicScore(bottomRow5.get(1).getHandler()).getValue()).isEqualTo(66560);
    }

    @Test
    public void itCreatesGameStateTreeAndReturnBottomRowOnEmptyBoard() {
        KeyEventHandler handler = new KeyEventHandler(true, g0, new Scoreboard(0));
        GameStateTree headNode = new GameStateTree(snakeHeuristic, 3, handler);
        ArrayList<GameStateTree> bottomRow = headNode.buildGameStateTreeAndGetBottomRow(new ArrayList<>(), 3);
        assertThat(bottomRow.size()).isEqualTo(0);
    }

}

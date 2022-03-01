package heuristic;

import javalib.worldimages.Posn;
import models.HeuristicScore;
import models.game.Grid2048;
import models.game.KeyEvent;
import models.game.KeyEventHandler;
import models.game.Scoreboard;
import models.square.Square;
import models.square.Tile;
import models.square.EmptySquare;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static heuristic.SnakeHeuristic.establishKeyEventSequence;
import static org.assertj.core.api.Assertions.assertThat;

public class SnakeHeuristicTest {

    Square[] rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3, row0, row1, row2, row3, row10, row11, row12, row13;
    Grid2048 g0, g1, g2;

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

        row10 = new Square[]{
                new Tile(2, new Posn(0, 0)),
                new Tile(4, new Posn(0, 1)),
                new Tile(2, new Posn(0, 2)),
                new Tile(4, new Posn(0, 3))};
        row11 = new Square[]{
                new Tile(4, new Posn(1, 0)),
                new Tile(2, new Posn(1, 1)),
                new Tile(4, new Posn(1, 2)),
                new Tile(2, new Posn(1, 3))};
        row12 = new Square[]{
                new EmptySquare(new Posn(2, 0)),
                new EmptySquare(new Posn(2, 1)),
                new Tile(8, new Posn(2, 2)),
                new EmptySquare(new Posn(2, 3))};
        row13 = new Square[]{
                new EmptySquare(new Posn(3, 0)),
                new EmptySquare(new Posn(3, 1)),
                new EmptySquare(new Posn(3, 2)),
                new EmptySquare(new Posn(3, 3))};

        g2 = new Grid2048(new Square[][]{row10, row11, row12, row13});
    }

    @Test
    public void itEvaluatesNextGameState() {
        this.initData();
        KeyEventHandler upHandler = g1.handleKeyEventWithoutRandomTile(KeyEvent.UP, new Scoreboard(0));
        assertThat(new SnakeHeuristic().evaluateNextGameState(g1, new Scoreboard(0)))
                .usingRecursiveComparison()
                .isEqualTo(upHandler);

        KeyEventHandler leftHandler = g2.handleKeyEventWithoutRandomTile(KeyEvent.LEFT, new Scoreboard(0));
        assertThat(new SnakeHeuristic().evaluateNextGameState(g2, new Scoreboard(0)))
                .usingRecursiveComparison()
                .isEqualTo(leftHandler);
    }

    @Test
    public void itEvaluatesNextGameStateOnEmptyBoard() {
        this.initData();
        KeyEventHandler upHandler = g0.handleKeyEventWithoutRandomTile(KeyEvent.UP, new Scoreboard(0));
        assertThat(new SnakeHeuristic().evaluateNextGameState(g0, new Scoreboard(0)))
                .usingRecursiveComparison()
                .isEqualTo(upHandler);
    }

    @Test
    public void itEstablishesKeyEventSequence() {
        this.initData();
        KeyEventHandler upHandler = g1.handleKeyEventWithoutRandomTile(KeyEvent.UP, new Scoreboard(0));
        KeyEventHandler downHandler = g1.handleKeyEventWithoutRandomTile(KeyEvent.DOWN, new Scoreboard(0));
        KeyEventHandler leftHandler = g1.handleKeyEventWithoutRandomTile(KeyEvent.LEFT, new Scoreboard(0));
        KeyEventHandler rightHandler = g1.handleKeyEventWithoutRandomTile(KeyEvent.RIGHT, new Scoreboard(0));
        Map<Integer, KeyEventHandler> handlerMap =  new HashMap<>();
        ArrayList<Integer> scores = new ArrayList<>();

        establishKeyEventSequence(g1, new Scoreboard(0), handlerMap, scores);

        assertThat(scores.get(0)).isEqualTo(3407000);
        assertThat(scores.get(1)).isEqualTo(1015101);
        assertThat(scores.get(2)).isEqualTo(719074);
        assertThat(scores.get(3)).isEqualTo(110);
    }

    @Test
    public void itEstablishesKeyEventSequenceOnEmptyBoard() {
        this.initData();
        KeyEventHandler upHandler = g0.handleKeyEventWithoutRandomTile(KeyEvent.UP, new Scoreboard(0));
        KeyEventHandler downHandler = g0.handleKeyEventWithoutRandomTile(KeyEvent.DOWN, new Scoreboard(0));
        KeyEventHandler leftHandler = g0.handleKeyEventWithoutRandomTile(KeyEvent.LEFT, new Scoreboard(0));
        KeyEventHandler rightHandler = g0.handleKeyEventWithoutRandomTile(KeyEvent.RIGHT, new Scoreboard(0));
        Map<Integer, KeyEventHandler> handlerMap =  new HashMap<>();
        ArrayList<Integer> scores = new ArrayList<>();

        establishKeyEventSequence(g0, new Scoreboard(0), handlerMap, scores);

        assertThat(scores.get(0)).isEqualTo(0);
        assertThat(scores.get(1)).isEqualTo(0);
        assertThat(scores.get(2)).isEqualTo(0);
        assertThat(scores.get(3)).isEqualTo(0);
    }
}

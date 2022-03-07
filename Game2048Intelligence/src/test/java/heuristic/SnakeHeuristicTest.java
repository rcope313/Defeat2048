package heuristic;

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
        rowEmpty0 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        rowEmpty1 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        rowEmpty2 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        rowEmpty3 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};

        g0 = new Grid2048(new Square[][]{rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3});

        row0 = new Square[]{new Tile(4), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row1 = new Square[]{new EmptySquare(), new Tile(4), new Tile(8), new EmptySquare()};
        row2 = new Square[]{new Tile(2), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row3 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new Tile(2)};

        g1 = new Grid2048(new Square[][]{row0, row1, row2, row3});

        row10 = new Square[]{new Tile(2), new Tile(4), new Tile(2), new Tile(4)};
        row11 = new Square[]{new Tile(4), new Tile(2), new Tile(4), new Tile(2)};
        row12 = new Square[]{new EmptySquare(), new EmptySquare(), new Tile(8), new EmptySquare()};
        row13 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};

        g2 = new Grid2048(new Square[][]{row10, row11, row12, row13});
    }

    @Test
    public void itEvaluatesNextGameState() {
        this.initData();
        KeyEventHandler upHandler = g1.handleKeyEvent(KeyEvent.UP, new Scoreboard(0));
        assertThat(new SnakeHeuristic().evaluateNextGameState(g1, new Scoreboard(0)))
                .usingRecursiveComparison()
                .isEqualTo(upHandler);

        KeyEventHandler leftHandler = g2.handleKeyEvent(KeyEvent.LEFT, new Scoreboard(0));
        assertThat(new SnakeHeuristic().evaluateNextGameState(g2, new Scoreboard(0)))
                .usingRecursiveComparison()
                .isEqualTo(leftHandler);
    }

    @Test
    public void itEvaluatesNextGameStateOnEmptyBoard() {
        this.initData();
        KeyEventHandler upHandler = g0.handleKeyEvent(KeyEvent.UP, new Scoreboard(0));
        assertThat(new SnakeHeuristic().evaluateNextGameState(g0, new Scoreboard(0)))
                .usingRecursiveComparison()
                .isEqualTo(upHandler);
    }

    @Test
    public void itEstablishesKeyEventSequence() {
        this.initData();
        Map<Integer, KeyEventHandler> handlerMap =  new HashMap<>();
        ArrayList<Integer> scores = new ArrayList<>();
        establishKeyEventSequence(g1, new Scoreboard(0), handlerMap, scores);

        assertThat(scores.get(0)).isEqualTo(155400);
        assertThat(scores.get(1)).isEqualTo(49414);
        assertThat(scores.get(2)).isEqualTo(39760);
        assertThat(scores.get(3)).isEqualTo(538);
    }

    @Test
    public void itEstablishesKeyEventSequenceOnEmptyBoard() {
        this.initData();
        Map<Integer, KeyEventHandler> handlerMap =  new HashMap<>();
        ArrayList<Integer> scores = new ArrayList<>();

        establishKeyEventSequence(g0, new Scoreboard(0), handlerMap, scores);

        assertThat(scores.get(0)).isEqualTo(0);
        assertThat(scores.get(1)).isEqualTo(0);
        assertThat(scores.get(2)).isEqualTo(0);
        assertThat(scores.get(3)).isEqualTo(0);
    }
}

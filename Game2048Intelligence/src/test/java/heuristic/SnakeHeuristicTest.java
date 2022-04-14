package heuristic;

import models.game.Grid2048;
import models.game.KeyEventHandler;
import models.game.Scoreboard;
import models.square.Square;
import models.square.Tile;
import models.square.EmptySquare;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SnakeHeuristicTest {

    Square[] rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3, row0, row1, row2, row3, row10, row11, row12, row13;
    Grid2048 g0, g1, g2;
    SnakeHeuristic snakeHeuristic = new SnakeHeuristic();

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
    public void itEvaluatesHeuristicScore() {
        this.initData();
        KeyEventHandler handler0 = new KeyEventHandler(true, g0, new Scoreboard(0));
        KeyEventHandler handler1 = new KeyEventHandler(true, g1, new Scoreboard(0));
        KeyEventHandler handler2 = new KeyEventHandler(true, g2, new Scoreboard(0));
        assertThat(snakeHeuristic.evaluateHeuristicScore(handler0).getValue()).isEqualTo(0);
        assertThat(snakeHeuristic.evaluateHeuristicScore(handler1).getValue()).isEqualTo(50620);
        assertThat(snakeHeuristic.evaluateHeuristicScore(handler2).getValue()).isEqualTo(110640);
    }
}

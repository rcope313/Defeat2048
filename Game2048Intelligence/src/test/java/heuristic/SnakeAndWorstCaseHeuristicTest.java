package heuristic;

import models.game.Grid2048;
import models.game.KeyEventHandler;
import models.game.Scoreboard;
import models.square.EmptySquare;
import models.square.Square;
import models.square.Tile;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class SnakeAndWorstCaseHeuristicTest {

    Square[] rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3,
            row10, row11, row12, row13,
            row20, row21, row22, row23,
            row30, row31, row32, row33;
    Grid2048 g0, g1, g2, g3;
    KeyEventHandler h0, h1, h2, h3;

    @Before
    public void initData() {
        rowEmpty0 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        rowEmpty1 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        rowEmpty2 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        rowEmpty3 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        g0 = new Grid2048(new Square[][]{rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3});
        h0 = new KeyEventHandler(false, g0, new Scoreboard(0));

        row10 = new Square[]{new Tile(4), new Tile(4), new Tile(8), new Tile(4)};
        row11 = new Square[]{new Tile(2), new Tile(16), new EmptySquare(), new Tile(2)};
        row12 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row13 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        g1 = new Grid2048(new Square[][]{row10, row11, row12, row13});
        h1 = new KeyEventHandler(false, g1, new Scoreboard(0));

        row20 = new Square[]{new Tile(4), new Tile(32), new Tile(8), new Tile(4)};
        row21 = new Square[]{new Tile(2), new Tile(16), new EmptySquare(), new Tile(2)};
        row22 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row23 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        g2 = new Grid2048(new Square[][]{row20, row21, row22, row23});
        h2 = new KeyEventHandler(false, g2, new Scoreboard(0));

        row30 = new Square[]{new Tile(4), new Tile(32), new Tile(8), new Tile(4)};
        row31 = new Square[]{new Tile(2), new Tile(16), new Tile(64), new Tile(2)};
        row32 = new Square[]{new Tile(4), new Tile(8), new EmptySquare(), new Tile(4)};
        row33 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        g3 = new Grid2048(new Square[][]{row30, row31, row32, row33});
        h3 = new KeyEventHandler(false, g3, new Scoreboard(0));
    }

    @Test
    public void itEvaluatesHeuristicScore() {
        assertThat(new SnakeAndWorstCaseHeuristic().evaluateHeuristicScore(h0).getValue()).isEqualTo(0);
        assertThat(new SnakeAndWorstCaseHeuristic().evaluateHeuristicScore(h1).getValue()).isEqualTo(184200);
        assertThat(new SnakeAndWorstCaseHeuristic().evaluateHeuristicScore(h2).getValue()).isEqualTo(0);
    }

    @Test
    public void itChecksIfGridIsStackable() {
        assertThat(SnakeAndWorstCaseHeuristic.isGridStackable(h1)).isTrue();
        assertThat(SnakeAndWorstCaseHeuristic.isGridStackable(h2)).isFalse();
        assertThat(SnakeAndWorstCaseHeuristic.isGridStackable(h3)).isFalse();
    }

    @Test
    public void itChecksGridForWorstCase() {
        assertThat(SnakeAndWorstCaseHeuristic.checkGridForWorstCase(h0)).isFalse();
        assertThat(SnakeAndWorstCaseHeuristic.checkGridForWorstCase(h1)).isTrue();
        assertThat(SnakeAndWorstCaseHeuristic.checkGridForWorstCase(h2)).isTrue();
        assertThat(SnakeAndWorstCaseHeuristic.checkGridForWorstCase(h3)).isTrue();
    }
}

package heuristic;

import models.WorstCase;
import models.game.Grid2048;
import models.game.KeyEventHandler;
import models.game.Scoreboard;
import models.square.EmptySquare;
import models.square.Square;
import models.square.Tile;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class SnakeAndWorstCaseHeuristicTest {

    Square[] rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3, row10, row11, row12, row13, row20, row21, row22, row23;
    Grid2048 g0, g1, g2;
    KeyEventHandler h0, h1, h2;

    void initData() {
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
    }

    @Test
    public void itEvaluatesHeuristicScore() {
        this.initData();
        assertThat(new SnakeAndWorstCaseHeuristic().evaluateHeuristicScore(h0).getValue()).isEqualTo(0);
        assertThat(new SnakeAndWorstCaseHeuristic().evaluateHeuristicScore(h1).getValue()).isEqualTo(184200);
        assertThat(new SnakeAndWorstCaseHeuristic().evaluateHeuristicScore(h2).getValue()).isEqualTo(0);
    }

    @Test
    public void itChecksIfGameStateIsWorstCase() {
        this.initData();
        assertThat(SnakeAndWorstCaseHeuristic.isGameStateForWorstCase(h1)).isFalse();
        assertThat(SnakeAndWorstCaseHeuristic.isGameStateForWorstCase(h2)).isTrue();
    }

    @Test
    public void itChecksRowForWorstCase() {
        this.initData();
        WorstCase worstCase1 = new WorstCase(0, false);
        SnakeAndWorstCaseHeuristic.checkRowForWorstCase(0, h1, worstCase1);
        SnakeAndWorstCaseHeuristic.checkRowForWorstCase(1, h1, worstCase1);
        SnakeAndWorstCaseHeuristic.checkRowForWorstCase(2, h1, worstCase1);
        SnakeAndWorstCaseHeuristic.checkRowForWorstCase(3, h1, worstCase1);
        assertThat(worstCase1).usingRecursiveComparison().isEqualTo(new WorstCase(1, true));

        WorstCase worstCase2 = new WorstCase(0, false);
        SnakeAndWorstCaseHeuristic.checkRowForWorstCase(0, h2, worstCase2);
        SnakeAndWorstCaseHeuristic.checkRowForWorstCase(1, h2, worstCase2);
        SnakeAndWorstCaseHeuristic.checkRowForWorstCase(2, h2, worstCase2);
        SnakeAndWorstCaseHeuristic.checkRowForWorstCase(3, h2, worstCase2);
        assertThat(worstCase2).usingRecursiveComparison().isEqualTo(new WorstCase(1, false));
    }
}

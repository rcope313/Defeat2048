package heuristic;

import models.game.Scoreboard;
import javalib.worldimages.Posn;
import models.game.Grid2048;
import models.square.EmptySquare;
import models.square.Square;
import models.square.Tile;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.*;

public class PreferUpHeuristicTest {

    Square[]
            rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3,
            row00Up, row01Up, row02Up, row03Up,
            row10Up, row11Up, row12Up, row13Up,
            row20LR, row21LR, row22LR, row23LR,
            row30LR, row31LR, row32LR, row33LR,
            row40D, row41D, row42D, row43D,
            row50D, row51D, row52D, row53D,
            row60WE, row61WE, row62WE, row63WE;
    Grid2048 gridEmpty, grid0Up, grid1Up, grid2LR, grid3LR, grid4D, grid5D, grid6WE;
    GameHeuristic preferUp;

    void initData() {
        preferUp = new PreferUpHeuristic();

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

        gridEmpty = new Grid2048(new Square[][]{rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3});

        row00Up = new Square[]{
                new EmptySquare(new Posn(0, 0)),
                new EmptySquare(new Posn(0, 1)),
                new EmptySquare(new Posn(0, 2)),
                new EmptySquare(new Posn(0, 3))};
        row01Up = new Square[]{
                new Tile(2, new Posn(1, 0)),
                new Tile(2, new Posn(1, 1)),
                new Tile(2, new Posn(1, 2)),
                new Tile(2, new Posn(1, 3))};
        row02Up = new Square[]{
                new EmptySquare(new Posn(2, 0)),
                new EmptySquare(new Posn(2, 1)),
                new EmptySquare(new Posn(2, 2)),
                new EmptySquare(new Posn(2, 3))};
        row03Up = new Square[]{
                new EmptySquare(new Posn(3, 0)),
                new EmptySquare(new Posn(3, 1)),
                new EmptySquare(new Posn(3, 2)),
                new EmptySquare(new Posn(3, 3))};

        grid0Up = new Grid2048(new Square[][]{row00Up, row01Up, row02Up, row03Up});

        row10Up = new Square[]{
                new Tile(2, new Posn(0, 0)),
                new Tile(2, new Posn(0, 1)),
                new Tile(2, new Posn(0, 2)),
                new Tile(2, new Posn(0, 3))};
        row11Up = new Square[]{
                new EmptySquare(new Posn(1, 0)),
                new EmptySquare(new Posn(1, 1)),
                new EmptySquare(new Posn(1, 2)),
                new EmptySquare(new Posn(1, 3))};
        row12Up = new Square[]{
                new EmptySquare(new Posn(2, 0)),
                new EmptySquare(new Posn(2, 1)),
                new EmptySquare(new Posn(2, 2)),
                new EmptySquare(new Posn(2, 3))};
        row13Up = new Square[]{
                new EmptySquare(new Posn(3, 0)),
                new EmptySquare(new Posn(3, 1)),
                new EmptySquare(new Posn(3, 2)),
                new EmptySquare(new Posn(3, 3))};

        grid1Up = new Grid2048(new Square[][]{row10Up, row11Up, row12Up, row13Up});

        row20LR = new Square[]{
                new Tile(4, new Posn(0, 0)),
                new Tile(4, new Posn(0, 1)),
                new EmptySquare(new Posn(0, 2)),
                new EmptySquare(new Posn(0, 3))};
        row21LR = new Square[]{
                new EmptySquare(new Posn(1, 0)),
                new EmptySquare(new Posn(1, 1)),
                new EmptySquare(new Posn(1, 2)),
                new EmptySquare(new Posn(1, 3))};
        row22LR = new Square[]{
                new EmptySquare(new Posn(2, 0)),
                new EmptySquare(new Posn(2, 1)),
                new EmptySquare(new Posn(2, 2)),
                new EmptySquare(new Posn(2, 3))};
        row23LR = new Square[]{
                new EmptySquare(new Posn(3, 0)),
                new EmptySquare(new Posn(3, 1)),
                new EmptySquare(new Posn(3, 2)),
                new EmptySquare(new Posn(3, 3))};

        grid2LR = new Grid2048(new Square[][]{row20LR, row21LR, row22LR, row23LR});

        row30LR = new Square[]{
                new EmptySquare(new Posn(0, 0)),
                new EmptySquare(new Posn(0, 1)),
                new Tile(4, new Posn(0, 2)),
                new Tile(4, new Posn(0, 3))};
        row31LR = new Square[]{
                new EmptySquare(new Posn(1, 0)),
                new EmptySquare(new Posn(1, 1)),
                new EmptySquare(new Posn(1, 2)),
                new EmptySquare(new Posn(1, 3))};
        row32LR = new Square[]{
                new EmptySquare(new Posn(2, 0)),
                new EmptySquare(new Posn(2, 1)),
                new EmptySquare(new Posn(2, 2)),
                new EmptySquare(new Posn(2, 3))};
        row33LR = new Square[]{
                new EmptySquare(new Posn(3, 0)),
                new EmptySquare(new Posn(3, 1)),
                new EmptySquare(new Posn(3, 2)),
                new EmptySquare(new Posn(3, 3))};

        grid3LR = new Grid2048(new Square[][]{row30LR, row31LR, row32LR, row33LR});

        row40D = new Square[]{
                new Tile(2, new Posn(0, 0)),
                new Tile(4, new Posn(0, 1)),
                new Tile(2, new Posn(0, 2)),
                new Tile(4, new Posn(0, 3))};
        row41D = new Square[]{
                new EmptySquare(new Posn(1, 0)),
                new EmptySquare(new Posn(1, 1)),
                new EmptySquare(new Posn(1, 2)),
                new EmptySquare(new Posn(1, 3))};
        row42D = new Square[]{
                new EmptySquare(new Posn(2, 0)),
                new EmptySquare(new Posn(2, 1)),
                new EmptySquare(new Posn(2, 2)),
                new EmptySquare(new Posn(2, 3))};
        row43D = new Square[]{
                new EmptySquare(new Posn(3, 0)),
                new EmptySquare(new Posn(3, 1)),
                new EmptySquare(new Posn(3, 2)),
                new EmptySquare(new Posn(3, 3))};

        grid4D = new Grid2048(new Square[][]{row40D, row41D, row42D, row43D});

        row50D = new Square[]{
                new EmptySquare(new Posn(0, 0)),
                new EmptySquare(new Posn(0, 1)),
                new EmptySquare(new Posn(0, 2)),
                new EmptySquare(new Posn(0, 3))};
        row51D = new Square[]{
                new EmptySquare(new Posn(1, 0)),
                new EmptySquare(new Posn(1, 1)),
                new EmptySquare(new Posn(1, 2)),
                new EmptySquare(new Posn(1, 3))};
        row52D = new Square[]{
                new EmptySquare(new Posn(2, 0)),
                new EmptySquare(new Posn(2, 1)),
                new EmptySquare(new Posn(2, 2)),
                new EmptySquare(new Posn(2, 3))};
        row53D = new Square[]{
                new Tile(2, new Posn(3, 0)),
                new Tile(4, new Posn(3, 1)),
                new Tile(2, new Posn(3, 2)),
                new Tile(4, new Posn(3, 3))};

        grid5D = new Grid2048(new Square[][]{row50D, row51D, row52D, row53D});

        row60WE = new Square[]{
                new Tile(4, new Posn(0, 0)),
                new Tile(2, new Posn(0, 1)),
                new Tile(4, new Posn(0, 2)),
                new Tile(2, new Posn(0, 3))};
        row61WE = new Square[]{
                new Tile(2, new Posn(1, 0)),
                new Tile(4, new Posn(1, 1)),
                new Tile(2, new Posn(1, 2)),
                new Tile(4, new Posn(1, 3))};
        row62WE = new Square[]{
                new Tile(4, new Posn(2, 0)),
                new Tile(2, new Posn(2, 1)),
                new Tile(4, new Posn(2, 2)),
                new Tile(2, new Posn(2, 3))};
        row63WE = new Square[]{
                new Tile(2, new Posn(3, 0)),
                new Tile(4, new Posn(3, 1)),
                new Tile(2, new Posn(3, 2)),
                new Tile(4, new Posn(3, 3))};

        grid6WE = new Grid2048(new Square[][]{row60WE, row61WE, row62WE, row63WE});
    }

    @Test
    public void testEvaluateNextGameStateEmptyBoard() {
        this.initData();
        assertThatThrownBy(() -> preferUp.evaluateNextGameState(gridEmpty, new Scoreboard(0)))
                .hasMessage("Initial board empty or world ends");
    }

    @Test
    public void testEvaluateNextGameStateUp() {
        this.initData();
        assertThat(preferUp.evaluateNextGameState(grid0Up, new Scoreboard(0)).getGrid2048()).isEqualTo(grid1Up);
    }

    @Test
    public void testEvaluateNextGameStateLeftOrRight() {
        this.initData();
        ArrayList<Grid2048> leftRightList = new ArrayList<>(Arrays.asList(grid2LR, grid3LR));
        assertThat(leftRightList).contains(preferUp.evaluateNextGameState(grid1Up, new Scoreboard(0)).getGrid2048());
    }

    @Test
    public void testEvaluateNextGameStateDown() {
        this.initData();
        assertThat(preferUp.evaluateNextGameState(grid4D, new Scoreboard(0)).getGrid2048()).isEqualTo(grid5D);
    }

    @Test
    public void testEvaluateNextGameStateWorldEnds() {
        this.initData();
        assertThatThrownBy(() -> preferUp.evaluateNextGameState(grid6WE, new Scoreboard(0)))
                .hasMessage("Initial board empty or world ends");
    }
}

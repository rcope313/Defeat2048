package heuristic;

import models.game.Scoreboard;
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

        rowEmpty0 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        rowEmpty1 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        rowEmpty2 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        rowEmpty3 = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        gridEmpty = new Grid2048(new Square[][]{rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3});

        row00Up = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row01Up = new Square[]{new Tile(2), new Tile(2), new Tile(2), new Tile(2)};
        row02Up = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row03Up = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        grid0Up = new Grid2048(new Square[][]{row00Up, row01Up, row02Up, row03Up});

        row10Up = new Square[]{new Tile(2), new Tile(2), new Tile(2), new Tile(2)};
        row11Up = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row12Up = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row13Up = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        grid1Up = new Grid2048(new Square[][]{row10Up, row11Up, row12Up, row13Up});

        row20LR = new Square[]{new Tile(4), new Tile(4), new EmptySquare(), new EmptySquare()};
        row21LR = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row22LR = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row23LR = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        grid2LR = new Grid2048(new Square[][]{row20LR, row21LR, row22LR, row23LR});

        row30LR = new Square[]{new EmptySquare(), new EmptySquare(), new Tile(4), new Tile(4)};
        row31LR = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row32LR = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row33LR = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        grid3LR = new Grid2048(new Square[][]{row30LR, row31LR, row32LR, row33LR});

        row40D = new Square[]{new Tile(2), new Tile(4), new Tile(2), new Tile(4)};
        row41D = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row42D = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row43D = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        grid4D = new Grid2048(new Square[][]{row40D, row41D, row42D, row43D});

        row50D = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row51D = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row52D = new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()};
        row53D = new Square[]{new Tile(2), new Tile(4), new Tile(2), new Tile(4)};
        grid5D = new Grid2048(new Square[][]{row50D, row51D, row52D, row53D});

        row60WE = new Square[]{new Tile(4), new Tile(2), new Tile(4), new Tile(2)};
        row61WE = new Square[]{new Tile(2), new Tile(4), new Tile(2), new Tile(4)};
        row62WE = new Square[]{new Tile(4), new Tile(2), new Tile(4), new Tile(2)};
        row63WE = new Square[]{new Tile(2), new Tile(4), new Tile(2), new Tile(4)};
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

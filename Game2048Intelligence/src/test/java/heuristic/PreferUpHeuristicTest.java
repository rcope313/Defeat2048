package heuristic;

import game2048.Game2048;
import game2048.Scoreboard;
import javalib.worldimages.Posn;
import models.board2048.Board2048;
import models.square.EmptyTile;
import models.square.Square;
import models.square.Tile;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiPredicate;

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
    Board2048 bEmpty, b0Up, b1Up, b2LR, b3LR, b4D, b5D, b6WE;
    Game2048 gEmpty, g0Up, g1Up, g2LR, g3LR, g4D, g5D, g6WE;
    GameHeuristic ghEmpty, gh0Up, gh1Up, gh2LR, gh3LR, gh4D, gh5D, gh6WE;

    void initData() {
        rowEmpty0 = new Square[]{
                new EmptyTile(new Posn(0, 0)),
                new EmptyTile(new Posn(0, 1)),
                new EmptyTile(new Posn(0, 2)),
                new EmptyTile(new Posn(0, 3))};
        rowEmpty1 = new Square[]{
                new EmptyTile(new Posn(1, 0)),
                new EmptyTile(new Posn(1, 1)),
                new EmptyTile(new Posn(1, 2)),
                new EmptyTile(new Posn(1, 3))};
        rowEmpty2 = new Square[]{
                new EmptyTile(new Posn(2, 0)),
                new EmptyTile(new Posn(2, 1)),
                new EmptyTile(new Posn(2, 2)),
                new EmptyTile(new Posn(2, 3))};
        rowEmpty3 = new Square[]{
                new EmptyTile(new Posn(3, 0)),
                new EmptyTile(new Posn(3, 1)),
                new EmptyTile(new Posn(3, 2)),
                new EmptyTile(new Posn(3, 3))};

        bEmpty = new Board2048(new Square[][]{rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3});
        gEmpty = new Game2048(bEmpty, new Scoreboard(0));
        ghEmpty = new PreferUpHeuristic(gEmpty);

        row00Up = new Square[]{
                new EmptyTile(new Posn(0, 0)),
                new EmptyTile(new Posn(0, 1)),
                new EmptyTile(new Posn(0, 2)),
                new EmptyTile(new Posn(0, 3))};
        row01Up = new Square[]{
                new Tile(2, new Posn(1, 0)),
                new Tile(2, new Posn(1, 1)),
                new Tile(2, new Posn(1, 2)),
                new Tile(2, new Posn(1, 3))};
        row02Up = new Square[]{
                new EmptyTile(new Posn(2, 0)),
                new EmptyTile(new Posn(2, 1)),
                new EmptyTile(new Posn(2, 2)),
                new EmptyTile(new Posn(2, 3))};
        row03Up = new Square[]{
                new EmptyTile(new Posn(3, 0)),
                new EmptyTile(new Posn(3, 1)),
                new EmptyTile(new Posn(3, 2)),
                new EmptyTile(new Posn(3, 3))};

        b0Up = new Board2048(new Square[][]{row00Up, row01Up, row02Up, row03Up});
        g0Up = new Game2048(b0Up, new Scoreboard(0));
        gh0Up = new PreferUpHeuristic(g0Up);

        row10Up = new Square[]{
                new Tile(2, new Posn(0, 0)),
                new Tile(2, new Posn(0, 1)),
                new Tile(2, new Posn(0, 2)),
                new Tile(2, new Posn(0, 3))};
        row11Up = new Square[]{
                new EmptyTile(new Posn(1, 0)),
                new EmptyTile(new Posn(1, 1)),
                new EmptyTile(new Posn(1, 2)),
                new EmptyTile(new Posn(1, 3))};
        row12Up = new Square[]{
                new EmptyTile(new Posn(2, 0)),
                new EmptyTile(new Posn(2, 1)),
                new EmptyTile(new Posn(2, 2)),
                new EmptyTile(new Posn(2, 3))};
        row13Up = new Square[]{
                new EmptyTile(new Posn(3, 0)),
                new EmptyTile(new Posn(3, 1)),
                new EmptyTile(new Posn(3, 2)),
                new EmptyTile(new Posn(3, 3))};

        b1Up = new Board2048(new Square[][]{row10Up, row11Up, row12Up, row13Up});
        g1Up = new Game2048(b1Up, new Scoreboard(0));
        gh1Up = new PreferUpHeuristic(g1Up);

        row20LR = new Square[]{
                new Tile(4, new Posn(0, 0)),
                new Tile(4, new Posn(0, 1)),
                new EmptyTile(new Posn(0, 2)),
                new EmptyTile(new Posn(0, 3))};
        row21LR = new Square[]{
                new EmptyTile(new Posn(1, 0)),
                new EmptyTile(new Posn(1, 1)),
                new EmptyTile(new Posn(1, 2)),
                new EmptyTile(new Posn(1, 3))};
        row22LR = new Square[]{
                new EmptyTile(new Posn(2, 0)),
                new EmptyTile(new Posn(2, 1)),
                new EmptyTile(new Posn(2, 2)),
                new EmptyTile(new Posn(2, 3))};
        row23LR = new Square[]{
                new EmptyTile(new Posn(3, 0)),
                new EmptyTile(new Posn(3, 1)),
                new EmptyTile(new Posn(3, 2)),
                new EmptyTile(new Posn(3, 3))};

        b2LR = new Board2048(new Square[][]{row20LR, row21LR, row22LR, row23LR});
        g2LR = new Game2048(b2LR, new Scoreboard(0));
        gh2LR = new PreferUpHeuristic(g2LR);

        row30LR = new Square[]{
                new EmptyTile(new Posn(0, 0)),
                new EmptyTile(new Posn(0, 1)),
                new Tile(4, new Posn(0, 2)),
                new Tile(4, new Posn(0, 3))};
        row31LR = new Square[]{
                new EmptyTile(new Posn(1, 0)),
                new EmptyTile(new Posn(1, 1)),
                new EmptyTile(new Posn(1, 2)),
                new EmptyTile(new Posn(1, 3))};
        row32LR = new Square[]{
                new EmptyTile(new Posn(2, 0)),
                new EmptyTile(new Posn(2, 1)),
                new EmptyTile(new Posn(2, 2)),
                new EmptyTile(new Posn(2, 3))};
        row33LR = new Square[]{
                new EmptyTile(new Posn(3, 0)),
                new EmptyTile(new Posn(3, 1)),
                new EmptyTile(new Posn(3, 2)),
                new EmptyTile(new Posn(3, 3))};

        b3LR = new Board2048(new Square[][]{row30LR, row31LR, row32LR, row33LR});
        g3LR = new Game2048(b3LR, new Scoreboard(0));
        gh3LR = new PreferUpHeuristic(g3LR);

        row40D = new Square[]{
                new Tile(2, new Posn(0, 0)),
                new Tile(4, new Posn(0, 1)),
                new Tile(2, new Posn(0, 2)),
                new Tile(4, new Posn(0, 3))};
        row41D = new Square[]{
                new EmptyTile(new Posn(1, 0)),
                new EmptyTile(new Posn(1, 1)),
                new EmptyTile(new Posn(1, 2)),
                new EmptyTile(new Posn(1, 3))};
        row42D = new Square[]{
                new EmptyTile(new Posn(2, 0)),
                new EmptyTile(new Posn(2, 1)),
                new EmptyTile(new Posn(2, 2)),
                new EmptyTile(new Posn(2, 3))};
        row43D = new Square[]{
                new EmptyTile(new Posn(3, 0)),
                new EmptyTile(new Posn(3, 1)),
                new EmptyTile(new Posn(3, 2)),
                new EmptyTile(new Posn(3, 3))};

        b4D = new Board2048(new Square[][]{row40D, row41D, row42D, row43D});
        g4D = new Game2048(b4D, new Scoreboard(0));
        gh4D = new PreferUpHeuristic(g4D);

        row50D = new Square[]{
                new EmptyTile(new Posn(0, 0)),
                new EmptyTile(new Posn(0, 1)),
                new EmptyTile(new Posn(0, 2)),
                new EmptyTile(new Posn(0, 3))};
        row51D = new Square[]{
                new EmptyTile(new Posn(1, 0)),
                new EmptyTile(new Posn(1, 1)),
                new EmptyTile(new Posn(1, 2)),
                new EmptyTile(new Posn(1, 3))};
        row52D = new Square[]{
                new EmptyTile(new Posn(2, 0)),
                new EmptyTile(new Posn(2, 1)),
                new EmptyTile(new Posn(2, 2)),
                new EmptyTile(new Posn(2, 3))};
        row53D = new Square[]{
                new Tile(2, new Posn(3, 0)),
                new Tile(4, new Posn(3, 1)),
                new Tile(2, new Posn(3, 2)),
                new Tile(4, new Posn(3, 3))};

        b5D = new Board2048(new Square[][]{row50D, row51D, row52D, row53D});
        g5D = new Game2048(b5D, new Scoreboard(0));
        gh5D = new PreferUpHeuristic(g5D);

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

        b6WE = new Board2048(new Square[][]{row60WE, row61WE, row62WE, row63WE});
        g6WE = new Game2048(b6WE, new Scoreboard(0));
        gh6WE = new PreferUpHeuristic(g6WE);
    }

    @Test
    public void testEvaluateNextGameState() {
        this.initData();
        ArrayList<Board2048> leftRightList = new ArrayList<>(Arrays.asList(g2LR.getBoard2048(), g3LR.getBoard2048()));

        assertThatThrownBy(() -> ghEmpty.evaluateNextGameState())
                .hasMessage("Initial board empty or world ends");

        assertThat(gh0Up.evaluateNextGameState()
                        .getUpdatedGame2048()
                        .getBoard2048())
                .isEqualTo(gh1Up.getGame2048().getBoard2048());

        assertThat(leftRightList)
                .contains(gh1Up.evaluateNextGameState().getUpdatedGame2048().getBoard2048());

        assertThat(gh4D.evaluateNextGameState()
                        .getUpdatedGame2048()
                        .getBoard2048())
                .isEqualTo(gh5D.getGame2048().getBoard2048());

        assertThatThrownBy(() -> gh6WE.evaluateNextGameState())
                .hasMessage("Initial board empty or world ends");

    }
}

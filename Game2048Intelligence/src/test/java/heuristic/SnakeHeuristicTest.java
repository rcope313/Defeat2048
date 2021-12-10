package heuristic;

import game2048.Game2048;
import game2048.Scoreboard;
import javalib.worldimages.Posn;
import models.board2048.Board2048;
import models.square.EmptyTile;
import models.square.Square;
import models.square.Tile;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SnakeHeuristicTest {

    Square[]
            rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3,
            row0, row1, row2, row3,
            row0Base, row1Base, row2Base, row3Base,
            row0Up, row1Up, row2Up, row3Up,
            row0Down, row1Down, row2Down, row3Down,
            row0Left, row1Left, row2Left, row3Left,
            row0Right, row1Right, row2Right, row3Right;
    Board2048 b0, b1, bBase, bUp, bDown, bLeft, bRight;
    Game2048 g0, g1, gBase, gUp, gDown, gLeft, gRight;
    GameHeuristic gh0, gh1, ghBase, ghUp, ghDown, ghLeft, ghRight;

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

        b0 = new Board2048(new Square[][]{rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3});
        g0 = new Game2048(b0, new Scoreboard(0));
        gh0 = new PreferUpHeuristic(g0);

        row0 = new Square[]{
                new Tile(2, new Posn(0, 0)),
                new Tile(2, new Posn(0, 1)),
                new Tile(2, new Posn(0, 2)),
                new Tile(2, new Posn(0, 3))};
        row1 = new Square[]{
                new Tile(2, new Posn(1, 0)),
                new EmptyTile(new Posn(1, 1)),
                new EmptyTile(new Posn(1, 2)),
                new Tile(2, new Posn(1, 3))};
        row2 = new Square[]{
                new Tile(2, new Posn(2, 0)),
                new EmptyTile(new Posn(2, 1)),
                new EmptyTile(new Posn(2, 2)),
                new Tile(2, new Posn(2, 3))};
        row3 = new Square[]{
                new Tile(2, new Posn(3, 0)),
                new Tile(2, new Posn(3, 1)),
                new Tile(2, new Posn(3, 2)),
                new Tile(2, new Posn(3, 3))};

        b1 = new Board2048(new Square[][]{row0, row1, row2, row3});
        g1 = new Game2048(b1, new Scoreboard(0));
        gh1 = new SnakeHeuristic(g1);

        row0Base = new Square[]{
                new Tile(2, new Posn(0, 0)),
                new Tile(2, new Posn(0, 1)),
                new Tile(2, new Posn(0, 2)),
                new Tile(2, new Posn(0, 3))};
        row1Base = new Square[]{
                new Tile(2, new Posn(1, 0)),
                new Tile(2, new Posn(1, 1)),
                new Tile(2, new Posn(1, 2)),
                new Tile(2, new Posn(1, 3))};
        row2Base = new Square[]{
                new Tile(2, new Posn(2, 0)),
                new Tile(2, new Posn(2, 1)),
                new Tile(2, new Posn(2, 2)),
                new Tile(2, new Posn(2, 3))};
        row3Base = new Square[]{
                new Tile(2, new Posn(3, 0)),
                new Tile(2, new Posn(3, 1)),
                new Tile(2, new Posn(3, 2)),
                new Tile(2, new Posn(3, 3))};

        bBase = new Board2048(new Square[][]{row0Base, row1Base, row2Base, row3Base});
        gBase = new Game2048(bBase, new Scoreboard(0));
        ghBase = new SnakeHeuristic(gBase);

        row0Up = new Square[]{
                new Tile(4, new Posn(0, 0)),
                new Tile(4, new Posn(0, 1)),
                new Tile(4, new Posn(0, 2)),
                new Tile(4, new Posn(0, 3))};
        row1Up = new Square[]{
                new Tile(4, new Posn(1, 0)),
                new Tile(4, new Posn(1, 1)),
                new Tile(4, new Posn(1, 2)),
                new Tile(4, new Posn(1, 3))};
        row2Up = new Square[]{
                new EmptyTile(new Posn(2, 0)),
                new EmptyTile(new Posn(2, 1)),
                new EmptyTile(new Posn(2, 2)),
                new EmptyTile(new Posn(2, 3))};
        row3Up = new Square[]{
                new EmptyTile(new Posn(3, 0)),
                new EmptyTile(new Posn(3, 1)),
                new EmptyTile(new Posn(3, 2)),
                new EmptyTile(new Posn(3, 3))};

        bUp = new Board2048(new Square[][]{row0Up, row1Up, row2Up, row3Up});
        gUp = new Game2048(bUp, new Scoreboard(32));
        ghUp = new SnakeHeuristic(gUp);

        row0Down = new Square[]{
                new EmptyTile(new Posn(0, 0)),
                new EmptyTile(new Posn(0, 1)),
                new EmptyTile(new Posn(0, 2)),
                new EmptyTile(new Posn(0, 3))};
        row1Down = new Square[]{
                new EmptyTile(new Posn(1, 0)),
                new EmptyTile(new Posn(1, 1)),
                new EmptyTile(new Posn(1, 2)),
                new EmptyTile(new Posn(1, 3))};
        row2Down = new Square[]{
                new Tile(4, new Posn(2, 0)),
                new Tile(4, new Posn(2, 1)),
                new Tile(4, new Posn(2, 2)),
                new Tile(4, new Posn(2, 3))};
        row3Down = new Square[]{
                new Tile(4, new Posn(3, 0)),
                new Tile(4, new Posn(3, 1)),
                new Tile(4, new Posn(3, 2)),
                new Tile(4, new Posn(3, 3))};

        bDown = new Board2048(new Square[][]{row0Down, row1Down, row2Down, row3Down});
        gDown = new Game2048(bDown, new Scoreboard(32));
        ghDown = new SnakeHeuristic(gDown);

        row0Left = new Square[]{
                new Tile(4, new Posn(0, 0)),
                new Tile(4, new Posn(0, 1)),
                new EmptyTile(new Posn(0, 2)),
                new EmptyTile(new Posn(0, 3))};
        row1Left = new Square[]{
                new Tile(4, new Posn(1, 0)),
                new Tile(4, new Posn(1, 1)),
                new EmptyTile(new Posn(1, 2)),
                new EmptyTile(new Posn(1, 3))};
        row2Left = new Square[]{
                new Tile(4, new Posn(2, 0)),
                new Tile(4, new Posn(2, 1)),
                new EmptyTile(new Posn(2, 2)),
                new EmptyTile(new Posn(2, 3))};
        row3Left = new Square[]{
                new Tile(4, new Posn(3, 0)),
                new Tile(4, new Posn(3, 1)),
                new EmptyTile(new Posn(3, 2)),
                new EmptyTile(new Posn(3, 3))};

        bLeft = new Board2048(new Square[][]{row0Left, row1Left, row2Left, row3Left});
        gLeft = new Game2048(bLeft, new Scoreboard(32));
        ghLeft = new SnakeHeuristic(gLeft);

        row0Right = new Square[]{
                new EmptyTile(new Posn(0, 0)),
                new EmptyTile(new Posn(0, 1)),
                new Tile(4, new Posn(0, 2)),
                new Tile(4, new Posn(0, 3))};
        row1Right = new Square[]{
                new EmptyTile(new Posn(1, 0)),
                new EmptyTile(new Posn(1, 1)),
                new Tile(4, new Posn(1, 2)),
                new Tile(4, new Posn(1, 3))};
        row2Right = new Square[]{
                new EmptyTile(new Posn(2, 0)),
                new EmptyTile(new Posn(2, 1)),
                new Tile(4, new Posn(2, 2)),
                new Tile(4, new Posn(2, 3))};
        row3Right = new Square[]{
                new EmptyTile(new Posn(3, 0)),
                new EmptyTile(new Posn(3, 1)),
                new Tile(4, new Posn(3, 2)),
                new Tile(4, new Posn(3, 3))};

        bRight = new Board2048(new Square[][]{row0Right, row1Right, row2Right, row3Right});
        gRight = new Game2048(bRight, new Scoreboard(32));
        ghRight = new SnakeHeuristic(gRight);
    }

    @Test
    public void testGameHeuristicClassFields() {
        this.initData();
        Board2048 emptyBoard = gh0.getGame2048().getBoard2048();
        assertThat(gh0.getKeyEventUp().getUpdatedGame2048().getBoard2048()).isEqualTo(emptyBoard);
        assertThat(gh0.getKeyEventUp().isTilesMoved()).isFalse();
        assertThat(gh0.getKeyEventDown().getUpdatedGame2048().getBoard2048()).isEqualTo(emptyBoard);
        assertThat(gh0.getKeyEventDown().isTilesMoved()).isFalse();
        assertThat(gh0.getKeyEventLeft().getUpdatedGame2048().getBoard2048()).isEqualTo(emptyBoard);
        assertThat(gh0.getKeyEventLeft().isTilesMoved()).isFalse();
        assertThat(gh0.getKeyEventRight().getUpdatedGame2048().getBoard2048()).isEqualTo(emptyBoard);
        assertThat(gh0.getKeyEventRight().isTilesMoved()).isFalse();

        assertThat(ghBase.getKeyEventUp().getUpdatedGame2048().getBoard2048()).isEqualTo(bUp);
        assertThat(ghBase.getKeyEventUp().isTilesMoved()).isTrue();
        assertThat(ghBase.getKeyEventDown().getUpdatedGame2048().getBoard2048()).isEqualTo(bDown);
        assertThat(ghBase.getKeyEventDown().isTilesMoved()).isTrue();
        assertThat(ghBase.getKeyEventLeft().getUpdatedGame2048().getBoard2048()).isEqualTo(bLeft);
        assertThat(ghBase.getKeyEventLeft().isTilesMoved()).isTrue();
        assertThat(ghBase.getKeyEventRight().getUpdatedGame2048().getBoard2048()).isEqualTo(bRight);
        assertThat(ghBase.getKeyEventRight().isTilesMoved()).isTrue();
    }

    @Test
    public void testEvaluateGameStateHeuristicScore() {
        this.initData();
        assertThat(gh0.evaluateGameStateHeuristicScore(gh0.getGame2048())).isEqualTo(0);
        assertThat(gh1.evaluateGameStateHeuristicScore(gh1.getGame2048())).isEqualTo(3417180);
        assertThat(ghBase.evaluateGameStateHeuristicScore(ghBase.getGame2048())).isEqualTo(3434350);
    }
}

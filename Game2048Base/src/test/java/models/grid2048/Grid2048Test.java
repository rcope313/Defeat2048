package models.grid2048;

import javalib.worldimages.Posn;
import models.game2048.KeyEventHandler;
import models.game2048.Scoreboard;
import models.square.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;

public class Grid2048Test {
    Grid2048 g0, gNoTilesMovedUpLeftRight, gNoTilesMovedDownLeftRight;
    Square[][] squareArray0, squareArrayNoTilesMovedUpLeftRight, getSquareArrayNoTilesMovedDownLeftRight;

    void initData() {
        squareArray0 = new Square[][] {
            new Square[]{
                new EmptySquare(new Posn(0,0)),
                new Tile(2, new Posn(0,1)),
                new EmptySquare(new Posn(0,2)),
                new EmptySquare(new Posn(0,3))},
            new Square[]{
                new EmptySquare(new Posn(1,0)),
                new Tile(2, new Posn(1,1)),
                new Tile(2, new Posn(1,2)),
                new Tile(2, new Posn(1,3))},
            new Square[]{
                new EmptySquare(new Posn(2,0)),
                new EmptySquare(new Posn(2,1)),
                new EmptySquare(new Posn(2,2)),
                new EmptySquare(new Posn(2,3))},
            new Square[]{
                new EmptySquare(new Posn(3,0)),
                new EmptySquare(new Posn(3,1)),
                new EmptySquare(new Posn(3,2)),
                new EmptySquare(new Posn(3,3))}};

        squareArrayNoTilesMovedUpLeftRight = new Square[][] {
                new Square[]{
                        new Tile(2, new Posn(0,0)),
                        new Tile(4, new Posn(0,1)),
                        new Tile(2, new Posn(0,2)),
                        new Tile(4, new Posn(0,3))},
                new Square[]{
                        new Tile(4, new Posn(1,0)),
                        new Tile(2, new Posn(1,1)),
                        new Tile(4, new Posn(1,2)),
                        new Tile(2, new Posn(1,3))},
                new Square[]{
                        new EmptySquare(new Posn(2,0)),
                        new EmptySquare(new Posn(2,1)),
                        new EmptySquare(new Posn(2,2)),
                        new EmptySquare(new Posn(2,3))},
                new Square[]{
                        new EmptySquare(new Posn(3,0)),
                        new EmptySquare(new Posn(3,1)),
                        new EmptySquare(new Posn(3,2)),
                        new EmptySquare(new Posn(3,3))}};

        getSquareArrayNoTilesMovedDownLeftRight = new Square[][] {
                new Square[]{
                        new EmptySquare(new Posn(0,0)),
                        new EmptySquare(new Posn(0,1)),
                        new EmptySquare(new Posn(0,2)),
                        new EmptySquare(new Posn(0,3))},
                new Square[]{
                        new EmptySquare(new Posn(1,0)),
                        new EmptySquare(new Posn(1,1)),
                        new EmptySquare(new Posn(1,2)),
                        new EmptySquare(new Posn(1,3))},
                new Square[]{
                        new Tile(2, new Posn(2,0)),
                        new Tile(4, new Posn(2,1)),
                        new Tile(2, new Posn(2,2)),
                        new Tile(4, new Posn(2,3))},
                new Square[]{
                        new Tile(4, new Posn(3,0)),
                        new Tile(2, new Posn(3,1)),
                        new Tile(4, new Posn(3,2)),
                        new Tile(2, new Posn(3,3))}};

        g0 = new Grid2048(squareArray0);
        gNoTilesMovedUpLeftRight = new Grid2048(squareArrayNoTilesMovedUpLeftRight);
        gNoTilesMovedDownLeftRight = new Grid2048(getSquareArrayNoTilesMovedDownLeftRight);
    }

    @Test
    public void itHandlesUpEventWithTilesMoved() {
        this.initData();
        KeyEventHandler handler = g0.handleUpEvent(new Scoreboard(0));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.getScoreboard()).usingRecursiveComparison().isEqualTo(new Scoreboard(4));
        assertThat(resultGrid[0][1]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (0,1)));
        assertThat(resultGrid[0][2]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (0,2)));
        assertThat(resultGrid[0][3]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (0,3)));

        List<Square> resultList = new ArrayList<>();
        resultList.addAll(Arrays.stream(resultGrid[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[3]).filter(Square::isTile).collect(Collectors.toList()));
        assertThat(resultList.size()).isEqualTo(4);
    }

    @Test
    public void itHandlesUpEventWithTilesNoMoved() {
        this.initData();
        KeyEventHandler handler = gNoTilesMovedUpLeftRight.handleUpEvent(new Scoreboard(100));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.getScoreboard()).usingRecursiveComparison().isEqualTo(new Scoreboard(100));
        assertThat(resultGrid[0][0]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (0,0)));
        assertThat(resultGrid[0][1]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (0,1)));
        assertThat(resultGrid[0][2]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (0,2)));
        assertThat(resultGrid[0][3]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (0,3)));
        assertThat(resultGrid[1][0]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (1,0)));
        assertThat(resultGrid[1][1]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (1,1)));
        assertThat(resultGrid[1][2]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (1,2)));
        assertThat(resultGrid[1][3]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (1,3)));

        List<Square> resultList = new ArrayList<>();
        resultList.addAll(Arrays.stream(resultGrid[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[3]).filter(Square::isTile).collect(Collectors.toList()));
        assertThat(resultList.size()).isEqualTo(8);
    }

    @Test
    public void itHandlesDownEventWithTilesMoved() {
        this.initData();
        KeyEventHandler handler = g0.handleDownEvent(new Scoreboard(0));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.getScoreboard()).usingRecursiveComparison().isEqualTo(new Scoreboard(4));
        assertThat(resultGrid[3][1]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (3,1)));
        assertThat(resultGrid[3][2]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (3,2)));
        assertThat(resultGrid[3][3]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (3,3)));

        List<Square> resultList = new ArrayList<>();
        resultList.addAll(Arrays.stream(resultGrid[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[3]).filter(Square::isTile).collect(Collectors.toList()));
        assertThat(resultList.size()).isEqualTo(4);
    }

    @Test
    public void itHandlesDownEventWithTilesNoMoved() {
        this.initData();
        KeyEventHandler handler = gNoTilesMovedDownLeftRight.handleDownEvent(new Scoreboard(100));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.getScoreboard()).usingRecursiveComparison().isEqualTo(new Scoreboard(100));
        assertThat(resultGrid[2][0]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (2,0)));
        assertThat(resultGrid[2][1]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (2,1)));
        assertThat(resultGrid[2][2]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (2,2)));
        assertThat(resultGrid[2][3]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (2,3)));
        assertThat(resultGrid[3][0]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (3,0)));
        assertThat(resultGrid[3][1]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (3,1)));
        assertThat(resultGrid[3][2]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (3,2)));
        assertThat(resultGrid[3][3]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (3,3)));

        List<Square> resultList = new ArrayList<>();
        resultList.addAll(Arrays.stream(resultGrid[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[3]).filter(Square::isTile).collect(Collectors.toList()));
        assertThat(resultList.size()).isEqualTo(8);
    }

    @Test
    public void itHandlesLeftEventWithTilesMoved() {
        this.initData();
        KeyEventHandler handler = g0.handleLeftEvent(new Scoreboard(0));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.getScoreboard()).usingRecursiveComparison().isEqualTo(new Scoreboard(4));
        assertThat(resultGrid[0][0]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (0,0)));
        assertThat(resultGrid[1][0]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (1,0)));
        assertThat(resultGrid[1][1]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (1,1)));

        List<Square> resultList = new ArrayList<>();
        resultList.addAll(Arrays.stream(resultGrid[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[3]).filter(Square::isTile).collect(Collectors.toList()));
        assertThat(resultList.size()).isEqualTo(4);
    }

    @Test
    public void itHandlesLeftEventWithTilesNoMoved() {
        this.initData();
        KeyEventHandler handler = gNoTilesMovedUpLeftRight.handleLeftEvent(new Scoreboard(100));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.getScoreboard()).usingRecursiveComparison().isEqualTo(new Scoreboard(100));
        assertThat(resultGrid[0][0]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (0,0)));
        assertThat(resultGrid[0][1]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (0,1)));
        assertThat(resultGrid[0][2]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (0,2)));
        assertThat(resultGrid[0][3]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (0,3)));
        assertThat(resultGrid[1][0]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (1,0)));
        assertThat(resultGrid[1][1]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (1,1)));
        assertThat(resultGrid[1][2]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (1,2)));
        assertThat(resultGrid[1][3]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (1,3)));

        List<Square> resultList = new ArrayList<>();
        resultList.addAll(Arrays.stream(resultGrid[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[3]).filter(Square::isTile).collect(Collectors.toList()));
        assertThat(resultList.size()).isEqualTo(8);
    }

    @Test
    public void itHandlesRightEventWithTilesMoved() {
        this.initData();
        KeyEventHandler handler = g0.handleRightEvent(new Scoreboard(0));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.getScoreboard()).usingRecursiveComparison().isEqualTo(new Scoreboard(4));
        assertThat(resultGrid[0][3]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (0,3)));
        assertThat(resultGrid[1][3]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (1,3)));
        assertThat(resultGrid[1][2]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (1,2)));

        List<Square> resultList = new ArrayList<>();
        resultList.addAll(Arrays.stream(resultGrid[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[3]).filter(Square::isTile).collect(Collectors.toList()));
        assertThat(resultList.size()).isEqualTo(4);
    }

    @Test
    public void itHandlesRightEventWithTilesNoMoved() {
        this.initData();
        KeyEventHandler handler = gNoTilesMovedUpLeftRight.handleRightEvent(new Scoreboard(100));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.getScoreboard()).usingRecursiveComparison().isEqualTo(new Scoreboard(100));
        assertThat(resultGrid[0][0]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (0,0)));
        assertThat(resultGrid[0][1]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (0,1)));
        assertThat(resultGrid[0][2]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (0,2)));
        assertThat(resultGrid[0][3]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (0,3)));
        assertThat(resultGrid[1][0]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (1,0)));
        assertThat(resultGrid[1][1]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (1,1)));
        assertThat(resultGrid[1][2]).usingRecursiveComparison().isEqualTo(new Tile(4, new Posn (1,2)));
        assertThat(resultGrid[1][3]).usingRecursiveComparison().isEqualTo(new Tile(2, new Posn (1,3)));

        List<Square> resultList = new ArrayList<>();
        resultList.addAll(Arrays.stream(resultGrid[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[3]).filter(Square::isTile).collect(Collectors.toList()));
        assertThat(resultList.size()).isEqualTo(8);
    }

    @Test
    public void itDetectsAndThrowsErrorOnTooManyTiles() {
        for (int count = 0; count < 50; count++) {
            this.initData();
            Grid2048 grid = new Grid2048();
            List<Square> resultList = new ArrayList<>();

            resultList.addAll(Arrays.stream(grid.grid[0]).filter(Square::isTile).collect(Collectors.toList()));
            resultList.addAll(Arrays.stream(grid.grid[1]).filter(Square::isTile).collect(Collectors.toList()));
            resultList.addAll(Arrays.stream(grid.grid[2]).filter(Square::isTile).collect(Collectors.toList()));
            resultList.addAll(Arrays.stream(grid.grid[3]).filter(Square::isTile).collect(Collectors.toList()));

            assertThat(resultList.size())
                    .withFailMessage("Incorrect amount of tiles (%s) produced at count %s",
                            resultList.size(), count)
                    .isEqualTo(2);
        }
    }

    @Test
    public void itInitializeStartingGrid() {
        Grid2048 aGrid = new Grid2048();
        List<Square> resultList = new ArrayList<>();
        resultList.addAll(Arrays.stream(aGrid.grid[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(aGrid.grid[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(aGrid.grid[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(aGrid.grid[3]).filter(Square::isTile).collect(Collectors.toList()));
        assertThat(resultList.size()).isEqualTo(2);
    }
}

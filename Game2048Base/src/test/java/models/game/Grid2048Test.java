package models.game;

import javalib.worldimages.Posn;
import models.square.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;

public class Grid2048Test {
    Grid2048 g0, gNoTilesMovedUpLeftRight, gNoTilesMovedDownLeftRight, g2;
    Square[][] squareArray0, squareArrayNoTilesMovedUpLeftRight, getSquareArrayNoTilesMovedDownLeftRight, squareArray2;

    void initData() {
        squareArray0 = new Square[][] {
            new Square[]{
                new EmptySquare(new Posn(0,0)),
                new Tile(2, new Posn(1,0)),
                new EmptySquare(new Posn(2,0)),
                new EmptySquare(new Posn(3,0))},
            new Square[]{
                new EmptySquare(new Posn(0,1)),
                new Tile(2, new Posn(1,1)),
                new Tile(2, new Posn(2,1)),
                new Tile(2, new Posn(3,1))},
            new Square[]{
                new EmptySquare(new Posn(0,2)),
                new EmptySquare(new Posn(1,2)),
                new EmptySquare(new Posn(2,2)),
                new EmptySquare(new Posn(3,2))},
            new Square[]{
                new EmptySquare(new Posn(0,3)),
                new EmptySquare(new Posn(1,3)),
                new EmptySquare(new Posn(2,3)),
                new EmptySquare(new Posn(3,3))}};

        squareArrayNoTilesMovedUpLeftRight = new Square[][] {
                new Square[]{
                        new Tile(2, new Posn(0,0)),
                        new Tile(4, new Posn(1,1)),
                        new Tile(2, new Posn(2,2)),
                        new Tile(4, new Posn(3,3))},
                new Square[]{
                        new Tile(4, new Posn(0,1)),
                        new Tile(2, new Posn(1,1)),
                        new Tile(4, new Posn(2,1)),
                        new Tile(2, new Posn(3,1))},
                new Square[]{
                        new EmptySquare(new Posn(0,2)),
                        new EmptySquare(new Posn(1,2)),
                        new EmptySquare(new Posn(2,2)),
                        new EmptySquare(new Posn(3,2))},
                new Square[]{
                        new EmptySquare(new Posn(0,3)),
                        new EmptySquare(new Posn(1,3)),
                        new EmptySquare(new Posn(2,3)),
                        new EmptySquare(new Posn(3,3))}};

        getSquareArrayNoTilesMovedDownLeftRight = new Square[][] {
                new Square[]{
                        new EmptySquare(new Posn(0,0)),
                        new EmptySquare(new Posn(1,1)),
                        new EmptySquare(new Posn(2,2)),
                        new EmptySquare(new Posn(3,3))},
                new Square[]{
                        new EmptySquare(new Posn(0,1)),
                        new EmptySquare(new Posn(1,1)),
                        new EmptySquare(new Posn(2,1)),
                        new EmptySquare(new Posn(3,1))},
                new Square[]{
                        new Tile(2, new Posn(0,2)),
                        new Tile(4, new Posn(1,2)),
                        new Tile(2, new Posn(2,2)),
                        new Tile(4, new Posn(3,2))},
                new Square[]{
                        new Tile(4, new Posn(0,3)),
                        new Tile(2, new Posn(1,3)),
                        new Tile(4, new Posn(2,3)),
                        new Tile(2, new Posn(3,3))}};

        squareArray2 = new Square[][] {
                new Square[]{
                        new Tile(4, new Posn(0,0)),
                        new Tile(4, new Posn(1,0)),
                        new Tile(8, new Posn(2,0)),
                        new Tile(2, new Posn(3,0))},
                new Square[]{
                        new Tile(2, new Posn(0,1)),
                        new EmptySquare(new Posn(1,1)),
                        new EmptySquare(new Posn(2,1)),
                        new EmptySquare(new Posn(3,1))},
                new Square[]{
                        new EmptySquare(new Posn(0,2)),
                        new EmptySquare(new Posn(1,2)),
                        new EmptySquare(new Posn(2,2)),
                        new EmptySquare(new Posn(3,2))},
                new Square[]{
                        new EmptySquare(new Posn(0,3)),
                        new EmptySquare(new Posn(1,3)),
                        new EmptySquare(new Posn(2,3)),
                        new EmptySquare(new Posn(3,3))}};

        g0 = new Grid2048(squareArray0);
        gNoTilesMovedUpLeftRight = new Grid2048(squareArrayNoTilesMovedUpLeftRight);
        gNoTilesMovedDownLeftRight = new Grid2048(getSquareArrayNoTilesMovedDownLeftRight);
        g2 = new Grid2048(squareArray2);
    }

    @Test
    public void itHandlesNOUPEvent() {
        this.initData();
        Scoreboard board = new Scoreboard(100);
        KeyEventHandler handler = g0.handleKeyEvent(KeyEvent.NOUP, board);
        assertThat(handler.getGrid2048()).isEqualTo(g0);
        assertThat(handler.getScoreboard()).isEqualTo(board);
    }

    @Test
    public void itHandlesBlocking() {
        this.initData();
        KeyEventHandler handler = g2.handleKeyEvent(KeyEvent.LEFT, new Scoreboard(0));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(resultGrid[0][0].getValue()).isEqualTo(8);
        assertThat(resultGrid[0][1].getValue()).isEqualTo(8);
        assertThat(resultGrid[0][2].getValue()).isEqualTo(2);
        assertThat(resultGrid[1][0].getValue()).isEqualTo(2);
    }

    @Test
    public void itHandlesUpEventWithTilesMoved() {
        this.initData();
        KeyEventHandler handler = g0.handleKeyEvent(KeyEvent.UP, new Scoreboard(0));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.isTilesMoved()).isTrue();
        assertThat(handler.getScoreboard().getPoints()).isEqualTo(4);
        assertThat(resultGrid[0][1].getValue()).isEqualTo(4);
        assertThat(resultGrid[0][2].getValue()).isEqualTo(2);
        assertThat(resultGrid[0][3].getValue()).isEqualTo(2);
    }

    @Test
    public void itHandlesUpEventWithTilesNoMoved() {
        this.initData();
        KeyEventHandler handler = gNoTilesMovedUpLeftRight.handleKeyEvent(KeyEvent.UP, new Scoreboard(100));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.isTilesMoved()).isFalse();
        assertThat(handler.getScoreboard().getPoints()).isEqualTo(100);
        assertThat(resultGrid[0][0].getValue()).isEqualTo(2);
        assertThat(resultGrid[0][1].getValue()).isEqualTo(4);
        assertThat(resultGrid[0][2].getValue()).isEqualTo(2);
        assertThat(resultGrid[0][3].getValue()).isEqualTo(4);
        assertThat(resultGrid[1][0].getValue()).isEqualTo(4);
        assertThat(resultGrid[1][1].getValue()).isEqualTo(2);
        assertThat(resultGrid[1][2].getValue()).isEqualTo(4);
        assertThat(resultGrid[1][3].getValue()).isEqualTo(2);
    }

    @Test
    public void itHandlesDownEventWithTilesMoved() {
        this.initData();
        KeyEventHandler handler = g0.handleKeyEvent(KeyEvent.DOWN, new Scoreboard(0));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.isTilesMoved()).isTrue();
        assertThat(handler.getScoreboard().getPoints()).isEqualTo(4);
        assertThat(resultGrid[3][1].getValue()).isEqualTo(4);
        assertThat(resultGrid[3][2].getValue()).isEqualTo(2);
        assertThat(resultGrid[3][3].getValue()).isEqualTo(2);
    }

    @Test
    public void itHandlesDownEventWithTilesNoMoved() {
        this.initData();
        KeyEventHandler handler = gNoTilesMovedDownLeftRight.handleKeyEvent(KeyEvent.DOWN, new Scoreboard(100));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.isTilesMoved()).isFalse();
        assertThat(handler.getScoreboard().getPoints()).isEqualTo(100);
        assertThat(resultGrid[2][0].getValue()).isEqualTo(2);
        assertThat(resultGrid[2][1].getValue()).isEqualTo(4);
        assertThat(resultGrid[2][2].getValue()).isEqualTo(2);
        assertThat(resultGrid[2][3].getValue()).isEqualTo(4);
        assertThat(resultGrid[3][0].getValue()).isEqualTo(4);
        assertThat(resultGrid[3][1].getValue()).isEqualTo(2);
        assertThat(resultGrid[3][2].getValue()).isEqualTo(4);
        assertThat(resultGrid[3][3].getValue()).isEqualTo(2);
    }

    @Test
    public void itHandlesLeftEventWithTilesMoved() {
        this.initData();
        KeyEventHandler handler = g0.handleKeyEvent(KeyEvent.LEFT, new Scoreboard(0));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.isTilesMoved()).isTrue();
        assertThat(handler.getScoreboard().getPoints()).isEqualTo(4);
        assertThat(resultGrid[0][0].getValue()).isEqualTo(2);
        assertThat(resultGrid[1][0].getValue()).isEqualTo(4);
        assertThat(resultGrid[1][1].getValue()).isEqualTo(2);
    }

    @Test
    public void itHandlesLeftEventWithTilesNoMoved() {
        this.initData();
        KeyEventHandler handler = gNoTilesMovedUpLeftRight.handleKeyEvent(KeyEvent.LEFT, new Scoreboard(100));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.isTilesMoved()).isFalse();
        assertThat(handler.getScoreboard().getPoints()).isEqualTo(100);
        assertThat(resultGrid[0][0].getValue()).isEqualTo(2);
        assertThat(resultGrid[0][1].getValue()).isEqualTo(4);
        assertThat(resultGrid[0][2].getValue()).isEqualTo(2);
        assertThat(resultGrid[0][3].getValue()).isEqualTo(4);
        assertThat(resultGrid[1][0].getValue()).isEqualTo(4);
        assertThat(resultGrid[1][1].getValue()).isEqualTo(2);
        assertThat(resultGrid[1][2].getValue()).isEqualTo(4);
        assertThat(resultGrid[1][3].getValue()).isEqualTo(2);
    }

    @Test
    public void itHandlesRightEventWithTilesMoved() {
        this.initData();
        KeyEventHandler handler = g0.handleKeyEvent(KeyEvent.RIGHT, new Scoreboard(0));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.isTilesMoved()).isTrue();
        assertThat(handler.getScoreboard().getPoints()).isEqualTo(4);
        assertThat(resultGrid[0][3].getValue()).isEqualTo(2);
        assertThat(resultGrid[1][3].getValue()).isEqualTo(4);
        assertThat(resultGrid[1][2].getValue()).isEqualTo(2);
    }

    @Test
    public void itHandlesRightEventWithTilesNoMoved() {
        this.initData();
        KeyEventHandler handler = gNoTilesMovedUpLeftRight.handleKeyEvent(KeyEvent.RIGHT, new Scoreboard(100));
        Square[][] resultGrid = handler.getGrid2048().grid;
        assertThat(handler.isTilesMoved()).isFalse();
        assertThat(handler.getScoreboard().getPoints()).isEqualTo(100);
        assertThat(resultGrid[0][0].getValue()).isEqualTo(2);
        assertThat(resultGrid[0][1].getValue()).isEqualTo(4);
        assertThat(resultGrid[0][2].getValue()).isEqualTo(2);
        assertThat(resultGrid[0][3].getValue()).isEqualTo(4);
        assertThat(resultGrid[1][0].getValue()).isEqualTo(4);
        assertThat(resultGrid[1][1].getValue()).isEqualTo(2);
        assertThat(resultGrid[1][2].getValue()).isEqualTo(4);
        assertThat(resultGrid[1][3].getValue()).isEqualTo(2);
    }

    @Test
    public void itAddsRandomTileToKeyEventHandler() {
        this.initData();
        KeyEventHandler handler = g0.handleKeyEvent(KeyEvent.UP, new Scoreboard(100));
        Grid2048.addRandomTileOnKeyEventHandler(handler);
        Square[][] resultGrid = handler.getGrid2048().grid;

        List<Square> resultList = new ArrayList<>();
        resultList.addAll(Arrays.stream(resultGrid[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(resultGrid[3]).filter(Square::isTile).collect(Collectors.toList()));
        assertThat(resultList.size()).isEqualTo(4);
    }

    @Test
    public void itCreatesACopyOfAGrid() {
        this.initData();
        Grid2048 copyGrid = g0.createGridCopy();
        assertThat(g0).usingRecursiveComparison().isEqualTo(copyGrid);
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

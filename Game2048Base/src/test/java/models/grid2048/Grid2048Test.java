package models.grid2048;

import javalib.worldimages.Posn;
import models.square.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;

public class Grid2048Test {
    EmptySquare tEmpty;
    Tile t2, t4, t8, t16;
    Grid2048 g0, g1, g2;
    Square[] rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3,
            row0, row1, row2, row3;

    void initData() {
        tEmpty = new EmptySquare();
        t2 = new Tile(2);
        t4 = new Tile(4);
        t8 = new Tile(8);
        t16 = new Tile(16);

        rowEmpty0 = new Square[]{
                new EmptySquare(new Posn(0,0)),
                new EmptySquare(new Posn(0,1)),
                new EmptySquare(new Posn(0,2)),
                new EmptySquare(new Posn(0,3))};
        rowEmpty1 = new Square[]{
                new EmptySquare(new Posn(1,0)),
                new EmptySquare(new Posn(1,1)),
                new EmptySquare(new Posn(1,2)),
                new EmptySquare(new Posn(1,3))};
        rowEmpty2 = new Square[]{
                new EmptySquare(new Posn(2,0)),
                new EmptySquare(new Posn(2,1)),
                new EmptySquare(new Posn(2,2)),
                new EmptySquare(new Posn(2,3))};
        rowEmpty3 = new Square[]{
                new EmptySquare(new Posn(3,0)),
                new EmptySquare(new Posn(3,1)),
                new EmptySquare(new Posn(3,2)),
                new EmptySquare(new Posn(3,3))};
        row0 = new Square[] {t2, tEmpty, tEmpty, tEmpty};
        row1 = new Square[] {t2, t4, tEmpty, t16};
        row2 = new Square[] {tEmpty, tEmpty, t8, t2};
        row3 = new Square[] {t4, tEmpty, tEmpty, t16};

        g0 = new Grid2048(new Square[4][4]);
        g1 = new Grid2048(new Square[][] {rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3});
        g2 = new Grid2048(new Square[][] {row0, row1, row2, row3});
    }

    @Test
    public void itDetectsAndThrowsErrorOnTooManyTiles() {
        for (int count = 0; count < 50; count++) {
            this.initData();
            Grid2048.addTwoRandomTilesForInitializedGrid(g1.getGrid());
            List<Square> resultList = new ArrayList<>();

            resultList.addAll(Arrays.stream(g1.getGrid()[0]).filter(Square::isTile).collect(Collectors.toList()));
            resultList.addAll(Arrays.stream(g1.getGrid()[1]).filter(Square::isTile).collect(Collectors.toList()));
            resultList.addAll(Arrays.stream(g1.getGrid()[2]).filter(Square::isTile).collect(Collectors.toList()));
            resultList.addAll(Arrays.stream(g1.getGrid()[3]).filter(Square::isTile).collect(Collectors.toList()));

            assertThat(resultList.size())
                    .withFailMessage("Incorrect amount of tiles (%s) produced at count %s",
                            resultList.size(), count)
                    .isEqualTo(2);
        }
    }

    @Test
    public void itCreatesEmptySquaresOntoANestedArray() {
        this.initData();
        Grid2048.createEmptySquaresOnGrid(g0.getGrid());
        assertThat(g0.isSameGrid(g1)).isEqualTo(true);
    }

    @Test
    public void itAddsTwoRandomTilesToInitializedGrid() {
        this.initData();
        Grid2048.addTwoRandomTilesForInitializedGrid(g1.getGrid());
        List<Square> resultList = new ArrayList<>();
        resultList.addAll(Arrays.stream(g1.getGrid()[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(g1.getGrid()[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(g1.getGrid()[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(g1.getGrid()[3]).filter(Square::isTile).collect(Collectors.toList()));
        assertThat(resultList.size()).isEqualTo(2);
    }

    @Test
    public void itInitializeStartingGrid() {
        Grid2048 aGrid = new Grid2048();
        List<Square> resultList = new ArrayList<>();
        resultList.addAll(Arrays.stream(aGrid.getGrid()[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(aGrid.getGrid()[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(aGrid.getGrid()[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(aGrid.getGrid()[3]).filter(Square::isTile).collect(Collectors.toList()));
        assertThat(resultList.size()).isEqualTo(2);
    }
}

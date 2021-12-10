package models.grid2048;

import javalib.worldimages.Posn;
import models.square.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;

public class Board2048Test {
    EmptyTile tEmpty;
    Tile t2, t4, t8, t16;
    Board2048 g0, g1, g2;
    Square[] rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3,
            row0, row1, row2, row3;

    void initData() {
        tEmpty = new EmptyTile();
        t2 = new Tile(2);
        t4 = new Tile(4);
        t8 = new Tile(8);
        t16 = new Tile(16);

        rowEmpty0 = new Square[]{
                new EmptyTile(new Posn(0,0)),
                new EmptyTile(new Posn(0,1)),
                new EmptyTile(new Posn(0,2)),
                new EmptyTile(new Posn(0,3))};
        rowEmpty1 = new Square[]{
                new EmptyTile(new Posn(1,0)),
                new EmptyTile(new Posn(1,1)),
                new EmptyTile(new Posn(1,2)),
                new EmptyTile(new Posn(1,3))};
        rowEmpty2 = new Square[]{
                new EmptyTile(new Posn(2,0)),
                new EmptyTile(new Posn(2,1)),
                new EmptyTile(new Posn(2,2)),
                new EmptyTile(new Posn(2,3))};
        rowEmpty3 = new Square[]{
                new EmptyTile(new Posn(3,0)),
                new EmptyTile(new Posn(3,1)),
                new EmptyTile(new Posn(3,2)),
                new EmptyTile(new Posn(3,3))};
        row0 = new Square[] {t2, tEmpty, tEmpty, tEmpty};
        row1 = new Square[] {t2, t4, tEmpty, t16};
        row2 = new Square[] {tEmpty, tEmpty, t8, t2};
        row3 = new Square[] {t4, tEmpty, tEmpty, t16};

        g0 = new Board2048();
        g1 = new Board2048(new Square[][] {rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3});
        g2 = new Board2048(new Square[][] {row0, row1, row2, row3});
    }

    @Test
    public void testRandomnessOfTilesToGrid() {

        for (int count = 0; count < 50; count++) {
            this.initData();
            g1.addRandomTilesToGrid();
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
    public void testInitializeStartingGridHelperMethods () {
        this.initData();

        g0.createEmptyTilesOnGrid();
        assertThat(g0.isSameGrid(g1)).isEqualTo(true);

        g0.addRandomTilesToGrid();
        List<Square> resultList = new ArrayList<>();
        resultList.addAll(Arrays.stream(g0.getGrid()[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(g0.getGrid()[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(g0.getGrid()[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(g0.getGrid()[3]).filter(Square::isTile).collect(Collectors.toList()));

        assertThat(resultList.size()).isEqualTo(2);
    }

    @Test
    public void testInitializeStartingGrid () {
        this.initData();

        g0.initializeStartingGrid();
        List<Square> resultList = new ArrayList<>();
        resultList.addAll(Arrays.stream(g0.getGrid()[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(g0.getGrid()[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(g0.getGrid()[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(g0.getGrid()[3]).filter(Square::isTile).collect(Collectors.toList()));

        assertThat(resultList.size()).isEqualTo(2);
    }



}
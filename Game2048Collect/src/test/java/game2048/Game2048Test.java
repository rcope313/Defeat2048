package game2048;

import javalib.worldimages.Posn;
import models.board2048.*;
import models.square.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class Game2048Test {

    Board2048 b0, b1, bUpEvent00, bUpEvent01, bUpEvent10, bUpEvent11, bNoTilesMoved;
    Square[] rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3,
            row0, row1, row2, row3,
            rowUpEvent000, rowUpEvent001, rowUpEvent002, rowUpEvent003,
            rowUpEvent010, rowUpEvent011, rowUpEvent012, rowUpEvent013,
            rowUpEvent100, rowUpEvent101, rowUpEvent102, rowUpEvent103,
            rowUpEvent110, rowUpEvent111, rowUpEvent112, rowUpEvent113,
            rowNoTilesMoved0, rowNoTilesMoved1, rowNoTilesMoved2, rowNoTilesMoved3;
    Game2048 g0, g1;

    void initData() {

        rowNoTilesMoved0 = new Square[]{
                new Tile(2, new Posn(0,0)),
                new Tile(4, new Posn(0,1)),
                new Tile(2, new Posn(0,2)),
                new Tile(4, new Posn(0,3))};
        rowNoTilesMoved1 = new Square[]{
                new Tile(4, new Posn(1,0)),
                new Tile(2, new Posn(1,1)),
                new Tile(4, new Posn(1,2)),
                new Tile(2, new Posn(1,3))};
        rowNoTilesMoved2 = new Square[]{
                new EmptyTile(new Posn(2,0)),
                new EmptyTile(new Posn(2,1)),
                new EmptyTile(new Posn(2,2)),
                new EmptyTile(new Posn(2,3))};
        rowNoTilesMoved3 = new Square[]{
                new EmptyTile(new Posn(3,0)),
                new EmptyTile(new Posn(3,1)),
                new EmptyTile(new Posn(3,2)),
                new EmptyTile(new Posn(3,3))};

        bNoTilesMoved = new Board2048(new Square[][] {rowNoTilesMoved0, rowNoTilesMoved1, rowNoTilesMoved2, rowNoTilesMoved3});

        rowUpEvent100 = new Square[]{
                new EmptyTile(new Posn(0,0)),
                new Tile(2, new Posn(0,1)),
                new EmptyTile(new Posn(0,2)),
                new EmptyTile(new Posn(0,3))};
        rowUpEvent101 = new Square[]{
                new EmptyTile(new Posn(1,0)),
                new EmptyTile(new Posn(1,1)),
                new EmptyTile(new Posn(1,2)),
                new EmptyTile(new Posn(1,3))};
        rowUpEvent102 = new Square[]{
                new EmptyTile(new Posn(2,0)),
                new EmptyTile(new Posn(2,1)),
                new EmptyTile(new Posn(2,2)),
                new EmptyTile(new Posn(2,3))};
        rowUpEvent103 = new Square[]{
                new EmptyTile(new Posn(3,0)),
                new Tile(2, new Posn(3,1)),
                new EmptyTile(new Posn(3,2)),
                new EmptyTile(new Posn(3,3))};

        bUpEvent10 = new Board2048(new Square[][] {rowUpEvent100, rowUpEvent101, rowUpEvent102, rowUpEvent103});

        rowUpEvent110 = new Square[]{
                new EmptyTile(new Posn(0,0)),
                new Tile(4, new Posn(0,1)),
                new EmptyTile(new Posn(0,2)),
                new EmptyTile(new Posn(0,3))};
        rowUpEvent111 = new Square[]{
                new EmptyTile(new Posn(1,0)),
                new EmptyTile(new Posn(1,1)),
                new EmptyTile(new Posn(1,2)),
                new EmptyTile(new Posn(1,3))};
        rowUpEvent112 = new Square[]{
                new EmptyTile(new Posn(2,0)),
                new EmptyTile(new Posn(2,1)),
                new EmptyTile(new Posn(2,2)),
                new EmptyTile(new Posn(2,3))};
        rowUpEvent113 = new Square[]{
                new EmptyTile(new Posn(3,0)),
                new EmptyTile(new Posn(3,1)),
                new EmptyTile(new Posn(3,2)),
                new EmptyTile(new Posn(3,3))};

        bUpEvent11 = new Board2048(new Square[][] {rowUpEvent110, rowUpEvent111, rowUpEvent112, rowUpEvent113});


        rowUpEvent000 = new Square[]{
                new EmptyTile(new Posn(0,0)),
                new EmptyTile(new Posn(0,1)),
                new EmptyTile(new Posn(0,2)),
                new EmptyTile(new Posn(0,3))};
        rowUpEvent001 = new Square[]{
                new EmptyTile(new Posn(1,0)),
                new Tile(2, new Posn(1,1)),
                new EmptyTile(new Posn(1,2)),
                new EmptyTile(new Posn(1,3))};
        rowUpEvent002 = new Square[]{
                new EmptyTile(new Posn(2,0)),
                new EmptyTile(new Posn(2,1)),
                new EmptyTile(new Posn(2,2)),
                new EmptyTile(new Posn(2,3))};
        rowUpEvent003 = new Square[]{
                new EmptyTile(new Posn(3,0)),
                new EmptyTile(new Posn(3,1)),
                new EmptyTile(new Posn(3,2)),
                new EmptyTile(new Posn(3,3))};

        bUpEvent00 = new Board2048(new Square[][] {rowUpEvent000, rowUpEvent001, rowUpEvent002, rowUpEvent003});

        rowUpEvent010 = new Square[]{
                new EmptyTile(new Posn(0,0)),
                new Tile(2, new Posn(0,1)),
                new EmptyTile(new Posn(0,2)),
                new EmptyTile(new Posn(0,3))};
        rowUpEvent011 = new Square[]{
                new EmptyTile(new Posn(1,0)),
                new EmptyTile(new Posn(1,1)),
                new EmptyTile(new Posn(1,2)),
                new EmptyTile(new Posn(1,3))};
        rowUpEvent012 = new Square[]{
                new EmptyTile(new Posn(2,0)),
                new EmptyTile(new Posn(2,1)),
                new EmptyTile(new Posn(2,2)),
                new EmptyTile(new Posn(2,3))};
        rowUpEvent013 = new Square[]{
                new EmptyTile(new Posn(3,0)),
                new EmptyTile(new Posn(3,1)),
                new EmptyTile(new Posn(3,2)),
                new EmptyTile(new Posn(3,3))};

        bUpEvent01 = new Board2048(new Square[][] {rowUpEvent010, rowUpEvent011, rowUpEvent012, rowUpEvent013});


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

        b0 = new Board2048(new Square[][] {rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3});
        g0 = new Game2048(b0, new Scoreboard(0));

        row0 = new Square[]{
                new Tile(2, new Posn(0,0)),
                new Tile(2, new Posn(0,1)),
                new Tile(2, new Posn(0,2)),
                new Tile(2, new Posn(0,3))};
        row1 = new Square[]{
                new Tile(2, new Posn(1,0)),
                new EmptyTile(new Posn(1,1)),
                new EmptyTile(new Posn(1,2)),
                new Tile(2, new Posn(1,3))};
        row2 = new Square[]{
                new Tile(2, new Posn(2,0)),
                new EmptyTile(new Posn(2,1)),
                new EmptyTile(new Posn(2,2)),
                new Tile(2, new Posn(2,3))};
        row3 = new Square[]{
                new Tile(2, new Posn(3,0)),
                new Tile(2, new Posn(3,1)),
                new Tile(2, new Posn(3,2)),
                new Tile(2, new Posn(3,3))};

        b1 = new Board2048(new Square[][] {row0, row1, row2, row3});
        g1 = new Game2048(b1, new Scoreboard(0));

    }

    @Test
    public void testNoTilesMovedKeyHandler () {
        this.initData();
        Game2048 uninstantiatedGame = new Game2048(bNoTilesMoved, new Scoreboard(0));

        Game2048 compareGame = new Game2048(bNoTilesMoved, new Scoreboard(0));

        KeyEventHandler compareKeyEventHandler = new KeyEventHandler();
        compareKeyEventHandler.setTilesMoved(false);
        compareKeyEventHandler.setUpdatedGame2048(compareGame);

        KeyEventHandler instantiatedKeyEventHandler = uninstantiatedGame.handleUpEvent();
        assertThat(instantiatedKeyEventHandler.isTilesMoved()).isFalse();
        assertThat(instantiatedKeyEventHandler
                .getUpdatedGame2048()
                .getBoard2048()
                .isSameBoard(compareGame.getBoard2048()))
                .isTrue();
    }

    @Test
    public void testTilesMovedKeyHandler () {
        this.initData();
        Game2048 uninstantiatedGame = new Game2048(bUpEvent10, new Scoreboard(0));

        Game2048 compareGame = new Game2048(bUpEvent11, new Scoreboard(4));

        KeyEventHandler compareKeyEventHandler = new KeyEventHandler();
        compareKeyEventHandler.setTilesMoved(false);
        compareKeyEventHandler.setUpdatedGame2048(compareGame);

        KeyEventHandler instantiatedKeyEventHandler = uninstantiatedGame.handleUpEvent();
        assertThat(instantiatedKeyEventHandler.isTilesMoved()).isTrue();

    }

    @Test
    public void handleUpEventTestTilesMovedNoScoreboardChange () {
        this.initData();
        Game2048 uninstantiatedGame = new Game2048(bUpEvent00, new Scoreboard(0));

        Game2048 compareGame = new Game2048(bUpEvent01, new Scoreboard(0));

        KeyEventHandler compareKeyEventHandler = new KeyEventHandler();
        compareKeyEventHandler.setTilesMoved(false);
        compareKeyEventHandler.setUpdatedGame2048(compareGame);

        KeyEventHandler instantiatedKeyEventHandler = uninstantiatedGame.handleUpEvent();
        Square[][] instantiatedGrid = instantiatedKeyEventHandler.getUpdatedGame2048().getBoard2048().getGrid();
        List<Square> resultList = new ArrayList<>();

        resultList.addAll(Arrays.stream(instantiatedGrid[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(instantiatedGrid[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(instantiatedGrid[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(instantiatedGrid[3]).filter(Square::isTile).collect(Collectors.toList()));

        assertThat(resultList.size()).isEqualTo(2);
        assertThat(resultList).extracting("position").contains(new Posn (0,1));
        assertThat(instantiatedKeyEventHandler.getUpdatedGame2048().getScoreboard().getPoints())
                .isEqualTo(compareGame.getScoreboard().getPoints());

    }

    @Test
    public void handleUpEventTestTilesMovedScoreboardChange () {
        this.initData();
        Game2048 uninstantiatedGame = new Game2048(bUpEvent10, new Scoreboard(0));

        Game2048 compareGame = new Game2048(bUpEvent11, new Scoreboard(4));

        KeyEventHandler compareKeyEventHandler = new KeyEventHandler();
        compareKeyEventHandler.setTilesMoved(false);
        compareKeyEventHandler.setUpdatedGame2048(compareGame);

        KeyEventHandler instantiatedKeyEventHandler = uninstantiatedGame.handleUpEvent();
        Square[][] instantiatedGrid = instantiatedKeyEventHandler.getUpdatedGame2048().getBoard2048().getGrid();
        List<Square> resultList = new ArrayList<>();

        resultList.addAll(Arrays.stream(instantiatedGrid[0]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(instantiatedGrid[1]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(instantiatedGrid[2]).filter(Square::isTile).collect(Collectors.toList()));
        resultList.addAll(Arrays.stream(instantiatedGrid[3]).filter(Square::isTile).collect(Collectors.toList()));

        assertThat(resultList.size()).isEqualTo(2);
        assertThat(resultList).extracting("position").contains(new Posn (0,1));
        assertThat(resultList).extracting("value").contains(4);
        assertThat(instantiatedKeyEventHandler.getUpdatedGame2048().getScoreboard().getPoints())
                .isEqualTo(compareGame.getScoreboard().getPoints());

    }

    @Test
    public void buildUpdatedSquareByUpEvent () {
        this.initData();
        Tile tile0 = new Tile (2, new Posn (1,2));
        Square compareSquare0 = Game2048.buildUpdatedSquareByKeyEvent(b0.getGrid(), "up", tile0, tile0.getPosition());
        Tile testTile0 = new Tile (2, new Posn (0,2));
        assertThat(testTile0.isSameSquare(compareSquare0)).isEqualTo(true);

        Tile tile1 = new Tile (2, new Posn (0,3));
        Square compareSquare1 = Game2048.buildUpdatedSquareByKeyEvent(b0.getGrid(), "up", tile1, tile1.getPosition());
        Tile testTile1 = new Tile (2, new Posn (0,3));
        assertThat(testTile1.isSameSquare(compareSquare1)).isEqualTo(true);

        Tile tile2 = new Tile (4, new Posn (2,2));
        Square compareSquare2 = Game2048.buildUpdatedSquareByKeyEvent(b1.getGrid(), "up", tile2, tile2.getPosition());
        Tile testTile2 = new Tile (4, new Posn (1,2));
        assertThat(testTile2.isSameSquare(compareSquare2)).isEqualTo(true);

        Tile tile3 = new Tile (2, new Posn (2,2));
        Square compareSquare3 = Game2048.buildUpdatedSquareByKeyEvent(b1.getGrid(), "up", tile3, tile3.getPosition());
        Tile testTile3 = new Tile (4, new Posn (0,2));
        assertThat(testTile3.isSameSquare(compareSquare3)).isEqualTo(true);

    }

    @Test
    public void buildUpdatedSquareByDownEvent () {
        this.initData();
        Tile tile0 = new Tile (2, new Posn (1,2));
        Square compareSquare0 = Game2048.buildUpdatedSquareByKeyEvent(b0.getGrid(), "down", tile0, tile0.getPosition());
        Tile testTile0 = new Tile (2, new Posn (3,2));
        assertThat(testTile0.isSameSquare(compareSquare0)).isEqualTo(true);

        Tile tile1 = new Tile (2, new Posn (3,3));
        Square compareSquare1 = Game2048.buildUpdatedSquareByKeyEvent(b0.getGrid(), "down", tile1, tile1.getPosition());
        Tile testTile1 = new Tile (2, new Posn (3,3));
        assertThat(testTile1.isSameSquare(compareSquare1)).isEqualTo(true);

        Tile tile2 = new Tile (4, new Posn (1,2));
        Square compareSquare2 = Game2048.buildUpdatedSquareByKeyEvent(b1.getGrid(), "down", tile2, tile2.getPosition());
        Tile testTile2 = new Tile (4, new Posn (2,2));
        assertThat(testTile2.isSameSquare(compareSquare2)).isEqualTo(true);

        Tile tile3 = new Tile (2, new Posn (1,2));
        Square compareSquare3 = Game2048.buildUpdatedSquareByKeyEvent(b1.getGrid(), "down", tile3, tile3.getPosition());
        Tile testTile3 = new Tile (4, new Posn (3,2));
        assertThat(testTile3.isSameSquare(compareSquare3)).isEqualTo(true);
    }

    @Test
    public void buildUpdatedSquareByLeftEvent () {
        this.initData();
        Tile tile0 = new Tile (2, new Posn (1,2));
        Square compareSquare0 = Game2048.buildUpdatedSquareByKeyEvent(b0.getGrid(), "left", tile0, tile0.getPosition());
        Tile testTile0 = new Tile (2, new Posn (1,0));
        assertThat(testTile0.isSameSquare(compareSquare0)).isEqualTo(true);

        Tile tile1 = new Tile (2, new Posn (0,0));
        Square compareSquare1 = Game2048.buildUpdatedSquareByKeyEvent(b0.getGrid(), "left", tile1, tile1.getPosition());
        Tile testTile1 = new Tile (2, new Posn (0,0));
        assertThat(testTile1.isSameSquare(compareSquare1)).isEqualTo(true);

        Tile tile2 = new Tile (4, new Posn (2,2));
        Square compareSquare2 = Game2048.buildUpdatedSquareByKeyEvent(b1.getGrid(), "left", tile2, tile2.getPosition());
        Tile testTile2 = new Tile (4, new Posn (2,1));
        assertThat(testTile2.isSameSquare(compareSquare2)).isEqualTo(true);

        Tile tile3 = new Tile (2, new Posn (2,2));
        Square compareSquare3 = Game2048.buildUpdatedSquareByKeyEvent(b1.getGrid(), "left", tile3, tile3.getPosition());
        Tile testTile3 = new Tile (4, new Posn (2,0));
        assertThat(testTile3.isSameSquare(compareSquare3)).isEqualTo(true);
    }

    @Test
    public void buildUpdatedSquareByRightEvent () {
        this.initData();
        Tile tile0 = new Tile (2, new Posn (1,2));
        Square compareSquare0 = Game2048.buildUpdatedSquareByKeyEvent(b0.getGrid(), "right", tile0, tile0.getPosition());
        Tile testTile0 = new Tile (2, new Posn (1,3));
        assertThat(testTile0.isSameSquare(compareSquare0)).isEqualTo(true);

        Tile tile1 = new Tile (2, new Posn (3,3));
        Square compareSquare1 = Game2048.buildUpdatedSquareByKeyEvent(b0.getGrid(), "right", tile1, tile1.getPosition());
        Tile testTile1 = new Tile (2, new Posn (3,3));
        assertThat(testTile1.isSameSquare(compareSquare1)).isEqualTo(true);

        Tile tile2 = new Tile (4, new Posn (1,1));
        Square compareSquare2 = Game2048.buildUpdatedSquareByKeyEvent(b1.getGrid(), "right", tile2, tile2.getPosition());
        Tile testTile2 = new Tile (4, new Posn (1,2));
        assertThat(testTile2.isSameSquare(compareSquare2)).isEqualTo(true);

        Tile tile3 = new Tile (2, new Posn (1,1));
        Square compareSquare3 = Game2048.buildUpdatedSquareByKeyEvent(b1.getGrid(), "right", tile3, tile3.getPosition());
        Tile testTile3 = new Tile (4, new Posn (1,3));
        assertThat(testTile3.isSameSquare(compareSquare3)).isEqualTo(true);

    }


}
package src.main.java;
import javalib.funworld.*;
import javalib.worldimages.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class GamePlay2048 extends World {
    Grid grid;
    Scoreboard scoreboard;


    GamePlay2048 (Grid grid, Scoreboard score) {
        this.grid = grid;
        this.scoreboard = score;

    }

    GamePlay2048 () {

        this.grid = new Grid();
        this.scoreboard = new Scoreboard(0);
    }

    @Override
    public WorldScene makeScene() {
        WorldScene s = new WorldScene(Square.SIDE_LENGTH * 6, Square.SIDE_LENGTH * 6);
        return s.placeImageXY(this.grid.drawGrid(), Square.SIDE_LENGTH * 3, Square.SIDE_LENGTH * 3)
                .placeImageXY(this.scoreboard.drawScoreboard(), Scoreboard.WIDTH/2 + 10 , Scoreboard.HEIGHT);

    }

    @Override
    public WorldScene lastScene (String msg) {
        return this.makeScene().placeImageXY(new TextImage(msg, 100, Color.BLACK), Square.SIDE_LENGTH * 3, Square.SIDE_LENGTH * 3);

    }

    @Override
    public WorldEnd worldEnds() {
        if (this.isGameOver()) {
            return new WorldEnd(true, this.lastScene("You Lose!"));
        } else {
            return new WorldEnd(false, this.makeScene());
        }
    }

    @Override
    public World onKeyEvent(String s) {

        if (s.equals("left")) {
            return this.handleLeftEvent(new HashSet<>(), true, this.scoreboard.points);
        }
        if (s.equals("right")) {
            return this.handleRightEvent(new HashSet<>(), true, this.scoreboard.points);
        }
        if (s.equals("down")) {
            return this.handleDownEvent(new HashSet<>(), true, this.scoreboard.points);
        }
        if (s.equals("up")) {
            return this.handleUpEvent(new HashSet<>(), true, this.scoreboard.points);
        } else {
            return this; }

    }

    GamePlay2048 handleUpEvent(Set<Posn> emptyTilePosns, boolean noTilesMoved, int points) {

        Square[][] resultSquareSuperList = new Square[4][4];

        for (int idxRow = 0; idxRow < 4; idxRow++ ) {

            Square[] row = this.grid.squareArrArr[idxRow];

            for (int idxColumn = 0; idxColumn < 4; idxColumn++) {

                if (row[idxColumn].isTile()) {

                    resultSquareSuperList[idxRow][idxColumn] = new Tile(this.grid.squareArrArr[idxRow][idxColumn].getValue());

                    PosnAndIsSameValue newPosnAndIsSameValue = aSquareNewPosnAndValueAfterAKeyEvent
                                    (new Grid(resultSquareSuperList), "up", new Posn(idxRow, idxColumn), new Posn(idxRow, idxColumn));

                    if (newPosnAndIsSameValue.posn.x == idxRow && newPosnAndIsSameValue.posn.y == idxColumn) {


                    }

                    else if (newPosnAndIsSameValue.isSameValue) {

                        int doubleVal = this.grid.squareArrArr[idxRow][idxColumn].getValue() * 2;

                        resultSquareSuperList[newPosnAndIsSameValue.posn.x][newPosnAndIsSameValue.posn.y] =
                                new Tile(doubleVal);
                        resultSquareSuperList[idxRow][idxColumn] = new EmptyTile();

                        points = points + doubleVal;

                        emptyTilePosns.add(new Posn(idxRow, idxColumn));
                        emptyTilePosns.remove(new Posn (newPosnAndIsSameValue.posn.x, newPosnAndIsSameValue.posn.y));
                        noTilesMoved = false;

                    }

                    else if (!newPosnAndIsSameValue.isSameValue) {

                        resultSquareSuperList[newPosnAndIsSameValue.posn.x][newPosnAndIsSameValue.posn.y] =
                                new Tile(this.grid.squareArrArr[idxRow][idxColumn].getValue());
                        resultSquareSuperList[idxRow][idxColumn] = new EmptyTile();
                        emptyTilePosns.add(new Posn(idxRow, idxColumn));
                        emptyTilePosns.remove(new Posn (newPosnAndIsSameValue.posn.x, newPosnAndIsSameValue.posn.y));
                        noTilesMoved = false;


                    }


                } else {

                    resultSquareSuperList[idxRow][idxColumn] = new EmptyTile();
                    emptyTilePosns.add(new Posn(idxRow, idxColumn));

                }

            }
        }

        return new GamePlay2048(new Grid(resultSquareSuperList), this.scoreboard)
                .handleKeyEventSecondary(emptyTilePosns, noTilesMoved, points);

    }

    GamePlay2048 handleDownEvent(Set<Posn> emptyTilePosns, boolean noTilesMoved, int points) {

        Square[][] resultSquareSuperList = new Square[4][4];

        for (int idxRow = this.grid.squareArrArr.length - 1; idxRow >= 0; idxRow-- ) {

            Square[] row = this.grid.squareArrArr[idxRow];

            for (int idxColumn = 0; idxColumn < row.length; idxColumn++) {

                if (row[idxColumn].isTile()) {

                    resultSquareSuperList[idxRow][idxColumn] = new Tile(this.grid.squareArrArr[idxRow][idxColumn].getValue());

                    PosnAndIsSameValue newPosnAndSameValue = aSquareNewPosnAndValueAfterAKeyEvent
                                    (new Grid(resultSquareSuperList), "down", new Posn(idxRow, idxColumn), new Posn(idxRow, idxColumn));

                    if (newPosnAndSameValue.posn.x == idxRow && newPosnAndSameValue.posn.y == idxColumn) {

                    }

                    else if (newPosnAndSameValue.isSameValue) {

                        int doubleVal = this.grid.squareArrArr[idxRow][idxColumn].getValue() * 2;

                        resultSquareSuperList[newPosnAndSameValue.posn.x][newPosnAndSameValue.posn.y] =
                                new Tile(doubleVal);
                        resultSquareSuperList[idxRow][idxColumn] = new EmptyTile();

                        points = points + doubleVal;

                        emptyTilePosns.add(new Posn(idxRow, idxColumn));
                        emptyTilePosns.remove(new Posn (newPosnAndSameValue.posn.x, newPosnAndSameValue.posn.y));
                        noTilesMoved = false;


                    }

                    else if (!newPosnAndSameValue.isSameValue) {

                        resultSquareSuperList[newPosnAndSameValue.posn.x][newPosnAndSameValue.posn.y] =
                                new Tile(this.grid.squareArrArr[idxRow][idxColumn].getValue());
                        resultSquareSuperList[idxRow][idxColumn] = new EmptyTile();
                        emptyTilePosns.add(new Posn(idxRow, idxColumn));
                        emptyTilePosns.remove(new Posn (newPosnAndSameValue.posn.x, newPosnAndSameValue.posn.y));
                        noTilesMoved = false;

                    }


                } else {

                    resultSquareSuperList[idxRow][idxColumn] = new EmptyTile();
                    emptyTilePosns.add(new Posn(idxRow, idxColumn));

                }

            }
        }

        return new GamePlay2048(new Grid(resultSquareSuperList), this.scoreboard)
                .handleKeyEventSecondary(emptyTilePosns, noTilesMoved, points);

    }

    GamePlay2048 handleLeftEvent(Set<Posn> emptyTilePosns, boolean noTilesMoved, int points) {

        Square[][] resultSquareSuperList = new Square[4][4];

        for (int idxRow = 0; idxRow < this.grid.squareArrArr.length; idxRow++ ) {

            Square[] row = this.grid.squareArrArr[idxRow];

            for (int idxColumn = 0; idxColumn < row.length; idxColumn++) {

                if (row[idxColumn].isTile()) {

                    resultSquareSuperList[idxRow][idxColumn] = new Tile(this.grid.squareArrArr[idxRow][idxColumn].getValue());

                    PosnAndIsSameValue newPosnAndSameValue = aSquareNewPosnAndValueAfterAKeyEvent
                                    (new Grid(resultSquareSuperList), "left", new Posn(idxRow, idxColumn), new Posn(idxRow, idxColumn));

                    if (newPosnAndSameValue.posn.x == idxRow && newPosnAndSameValue.posn.y == idxColumn) {

                    }

                    else if (newPosnAndSameValue.isSameValue) {

                        int doubleVal = this.grid.squareArrArr[idxRow][idxColumn].getValue() * 2;

                        resultSquareSuperList[newPosnAndSameValue.posn.x][newPosnAndSameValue.posn.y] =
                                new Tile(doubleVal);
                        resultSquareSuperList[idxRow][idxColumn] = new EmptyTile();

                        points = points + doubleVal;

                        emptyTilePosns.add(new Posn(idxRow, idxColumn));
                        emptyTilePosns.remove(new Posn (newPosnAndSameValue.posn.x, newPosnAndSameValue.posn.y));
                        noTilesMoved = false;


                    }

                    else if (!newPosnAndSameValue.isSameValue) {

                        resultSquareSuperList[newPosnAndSameValue.posn.x][newPosnAndSameValue.posn.y] =
                                new Tile(this.grid.squareArrArr[idxRow][idxColumn].getValue());
                        resultSquareSuperList[idxRow][idxColumn] = new EmptyTile();
                        emptyTilePosns.add(new Posn(idxRow, idxColumn));
                        emptyTilePosns.remove(new Posn (newPosnAndSameValue.posn.x, newPosnAndSameValue.posn.y));
                        noTilesMoved = false;

                    }


                } else {

                    resultSquareSuperList[idxRow][idxColumn] = new EmptyTile();
                    emptyTilePosns.add(new Posn(idxRow, idxColumn));

                }

            }
        }

        return new GamePlay2048(new Grid(resultSquareSuperList), this.scoreboard)
                .handleKeyEventSecondary(emptyTilePosns, noTilesMoved, points);

    }

    GamePlay2048 handleRightEvent(Set<Posn> emptyTilePosns, boolean noTilesMoved, int points) {

        Square[][] resultSquareSuperList = new Square[4][4];

        for (int idxRow = 0; idxRow < this.grid.squareArrArr.length; idxRow++ ) {

            Square[] row = this.grid.squareArrArr[idxRow];

            for (int idxColumn = row.length - 1; idxColumn >= 0; idxColumn--) {

                if (row[idxColumn].isTile()) {

                    resultSquareSuperList[idxRow][idxColumn] = new Tile(this.grid.squareArrArr[idxRow][idxColumn].getValue());

                    PosnAndIsSameValue newPosnAndIsSameValue = aSquareNewPosnAndValueAfterAKeyEvent
                                    (new Grid(resultSquareSuperList), "right", new Posn(idxRow, idxColumn), new Posn(idxRow, idxColumn));

                    if (newPosnAndIsSameValue.posn.x == idxRow && newPosnAndIsSameValue.posn.y == idxColumn) {

                    }

                    else if (newPosnAndIsSameValue.isSameValue) {

                        int doubleVal = this.grid.squareArrArr[idxRow][idxColumn].getValue() * 2;

                        resultSquareSuperList[newPosnAndIsSameValue.posn.x][newPosnAndIsSameValue.posn.y] =
                                new Tile(doubleVal);
                        resultSquareSuperList[idxRow][idxColumn] = new EmptyTile();

                        points = points + doubleVal;

                        emptyTilePosns.add(new Posn(idxRow, idxColumn));
                        emptyTilePosns.remove(new Posn (newPosnAndIsSameValue.posn.x, newPosnAndIsSameValue.posn.y));
                        noTilesMoved = false;


                    }

                    else if (!newPosnAndIsSameValue.isSameValue) {

                        resultSquareSuperList[newPosnAndIsSameValue.posn.x][newPosnAndIsSameValue.posn.y] =
                                new Tile(this.grid.squareArrArr[idxRow][idxColumn].getValue());
                        resultSquareSuperList[idxRow][idxColumn] = new EmptyTile();
                        emptyTilePosns.add(new Posn(idxRow, idxColumn));
                        emptyTilePosns.remove(new Posn (newPosnAndIsSameValue.posn.x, newPosnAndIsSameValue.posn.y));
                        noTilesMoved = false;

                    }


                } else {

                    resultSquareSuperList[idxRow][idxColumn] = new EmptyTile();
                    emptyTilePosns.add(new Posn(idxRow, idxColumn));

                }

            }
        }

        Grid resultGrid = new Grid(resultSquareSuperList);
        resultGrid.addRandomTileToGrid(emptyTilePosns, noTilesMoved, points);

        return new GamePlay2048 (resultGrid, this.scoreboard);



    }

    GamePlay2048 handleKeyEventSecondary (Set<Posn> emptyTilePosns, boolean noTilesMoved, int points) {

        if (!noTilesMoved) {

            Posn[] emptyTilePosnsArray = emptyTilePosns.toArray(new Posn[0]);
            int randomInt = new Random().nextInt(emptyTilePosnsArray.length);
            Posn newTilePosn = emptyTilePosnsArray[randomInt];

            this.grid.squareArrArr[newTilePosn.x][newTilePosn.y] =
                    new Tile(Tile.weightedRandomTileValue());

        }

        return new GamePlay2048 (this.grid, new Scoreboard(points));
    }

    static PosnAndIsSameValue aSquareNewPosnAndValueAfterAKeyEvent(Grid grid, String keyEvent, Posn originalTilePosn, Posn newTilePosn) {

        if (keyEvent == "up") {

            if (newTilePosn.x == 0) {
                return new PosnAndIsSameValue(newTilePosn, false);
            }

            if (grid.squareArrArr[newTilePosn.x - 1][newTilePosn.y].isTile()) {

                if (grid.squareArrArr[newTilePosn.x - 1][newTilePosn.y].sameValue(grid.squareArrArr[originalTilePosn.x][originalTilePosn.y])) {
                    return new PosnAndIsSameValue
                            (new Posn (newTilePosn.x - 1, newTilePosn.y), true);

                } else {

                    return new PosnAndIsSameValue(newTilePosn, false);
                }
            }

            else {
                return aSquareNewPosnAndValueAfterAKeyEvent
                        (grid, keyEvent, originalTilePosn, new Posn(newTilePosn.x - 1, newTilePosn.y));
            }
        }

        if (keyEvent == "down") {

            if (newTilePosn.x == 3) {
                return new PosnAndIsSameValue(newTilePosn, false);
            }

            if (grid.squareArrArr[newTilePosn.x + 1][newTilePosn.y].isTile()) {

                if (grid.squareArrArr[newTilePosn.x + 1][newTilePosn.y]
                        .sameValue(grid.squareArrArr[originalTilePosn.x][originalTilePosn.y])) {
                    return new PosnAndIsSameValue
                            (new Posn (newTilePosn.x + 1, newTilePosn.y), true);

                } else {

                    return new PosnAndIsSameValue(newTilePosn, false);
                }
            }

            else {
                return aSquareNewPosnAndValueAfterAKeyEvent
                        (grid, keyEvent, originalTilePosn, new Posn(newTilePosn.x + 1, newTilePosn.y));
            }
        }

        if (keyEvent == "right") {

            if (newTilePosn.y == 3) {
                return new PosnAndIsSameValue(newTilePosn, false);
            }

            if (grid.squareArrArr[newTilePosn.x][newTilePosn.y + 1].isTile()) {

                if (grid.squareArrArr[newTilePosn.x][newTilePosn.y + 1]
                        .sameValue(grid.squareArrArr[originalTilePosn.x][originalTilePosn.y])) {
                    return new PosnAndIsSameValue
                            (new Posn (newTilePosn.x, newTilePosn.y + 1), true);

                } else {

                    return new PosnAndIsSameValue(newTilePosn, false);
                }
            }

            else {
                return aSquareNewPosnAndValueAfterAKeyEvent
                        (grid, keyEvent, originalTilePosn, new Posn(newTilePosn.x, newTilePosn.y + 1));
            }
        }

        if (keyEvent == "left") {

            if (newTilePosn.y == 0) {
                return new PosnAndIsSameValue(newTilePosn, false);
            }

            if (grid.squareArrArr[newTilePosn.x][newTilePosn.y - 1].isTile()) {

                if (grid.squareArrArr[newTilePosn.x][newTilePosn.y - 1]
                        .sameValue(grid.squareArrArr[originalTilePosn.x][originalTilePosn.y])) {
                    return new PosnAndIsSameValue
                            (new Posn (newTilePosn.x, newTilePosn.y - 1), true);

                } else {

                    return new PosnAndIsSameValue(newTilePosn, false);
                }
            }

            else {
                return aSquareNewPosnAndValueAfterAKeyEvent
                        (grid, keyEvent, originalTilePosn, new Posn(newTilePosn.x, newTilePosn.y - 1));
            }
        }

        else {
            throw new IllegalArgumentException("Key event not found: " + keyEvent);
        }

    }




    boolean isGameOver () {

        GamePlay2048 gpUp = this.handleUpEvent(new HashSet<>(), true, this.scoreboard.points);
        GamePlay2048 gpDown = this.handleDownEvent(new HashSet<>(), true, this.scoreboard.points);
        GamePlay2048 gpRight = this.handleRightEvent(new HashSet<>(), true, this.scoreboard.points);
        GamePlay2048 gpLeft = this.handleLeftEvent(new HashSet<>(), true, this.scoreboard.points);

        return gpUp.grid.isSameGrid(this.grid) &&
                gpDown.grid.isSameGrid(this.grid) &&
                gpRight.grid.isSameGrid(this.grid) &&
                gpLeft.grid.isSameGrid(this.grid);


    }











}

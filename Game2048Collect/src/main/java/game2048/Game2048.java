package game2048;

import javalib.worldimages.Posn;
import models.board2048.Board2048;
import models.square.EmptyTile;
import models.square.Square;
import models.square.Tile;
import java.util.Arrays;
import java.util.Random;

public class Game2048 {
    public final Board2048 board2048;
    public final Scoreboard scoreboard;

    public Game2048(Board2048 board2048, Scoreboard scoreboard) {
        this.board2048 = board2048;
        this.scoreboard = scoreboard;
    }

    public KeyEventHandler handleUpEvent() {

        KeyEventHandler keyEventHandler = initializeKeyEventMethods();

        for (int idxRow = 0; idxRow < 4; idxRow++ ) {
            Arrays.stream(this.getBoard2048().getGrid()[idxRow]).forEach((square) ->
                    handleCurrentSquare(square,"up", keyEventHandler));
        }

        createRandomTile(keyEventHandler);
        return keyEventHandler;

    }

    public KeyEventHandler handleDownEvent() {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods();

        for (int idxRow = 3; idxRow >= 0; idxRow-- ) {
            Arrays.stream(this.getBoard2048().getGrid()[idxRow]).forEach((square) ->
                    handleCurrentSquare(square, "down", keyEventHandler));
        }

        createRandomTile(keyEventHandler);
        return keyEventHandler;

    }

    public KeyEventHandler handleLeftEvent() {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods();

        for (int idxRow = 0; idxRow < 4; idxRow++) {
            Arrays.stream(this.getBoard2048().getGrid()[idxRow]).forEach((square) ->
                    handleCurrentSquare(square, "left", keyEventHandler));
        }

        createRandomTile(keyEventHandler);
        return keyEventHandler;
    }

    public KeyEventHandler handleRightEvent() {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods();

        for (int idxRow = 0; idxRow < 4; idxRow++) {
            for (int idxColumn = 3; idxColumn >= 0; idxColumn--) {
                Square[][] currentGrid = this.getBoard2048().getGrid();
                handleCurrentSquare(currentGrid[idxRow][idxColumn], "right", keyEventHandler);
            }
        }

        createRandomTile(keyEventHandler);
        return keyEventHandler;
    }

    public KeyEventHandler handleUpEventNoRandomTile() {

        KeyEventHandler keyEventHandler = initializeKeyEventMethods();

        for (int idxRow = 0; idxRow < 4; idxRow++ ) {
            Arrays.stream(this.getBoard2048().getGrid()[idxRow]).forEach((square) ->
                    handleCurrentSquare(square,"up", keyEventHandler));
        }

        return keyEventHandler;

    }

    public KeyEventHandler handleDownEventNoRandomTile() {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods();

        for (int idxRow = 3; idxRow >= 0; idxRow-- ) {
            Arrays.stream(this.getBoard2048().getGrid()[idxRow]).forEach((square) ->
                    handleCurrentSquare(square, "down", keyEventHandler));
        }

        return keyEventHandler;

    }

    public KeyEventHandler handleLeftEventNoRandomTile() {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods();

        for (int idxRow = 0; idxRow < 4; idxRow++) {
            Arrays.stream(this.getBoard2048().getGrid()[idxRow]).forEach((square) ->
                    handleCurrentSquare(square, "left", keyEventHandler));
        }

        return keyEventHandler;
    }

    public KeyEventHandler handleRightEventNoRandomTile() {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods();

        for (int idxRow = 0; idxRow < 4; idxRow++) {
            for (int idxColumn = 3; idxColumn >= 0; idxColumn--) {
                Square[][] currentGrid = this.getBoard2048().getGrid();
                handleCurrentSquare(currentGrid[idxRow][idxColumn], "right", keyEventHandler);
            }
        }

        return keyEventHandler;
    }

    KeyEventHandler handleCurrentSquare (Square staticSquare, String keyEvent, KeyEventHandler keyEventHandler) {
        if (staticSquare.isTile()) {
            return handleCurrentTile(staticSquare, keyEvent, keyEventHandler);
        } else {
            return handleCurrentEmptyTile(staticSquare, keyEventHandler);
        }
    }

    KeyEventHandler handleCurrentTile (Square staticSquare, String keyEvent, KeyEventHandler keyEventHandler) {

        int value = staticSquare.getValue();
        Posn staticSquarePosn = staticSquare.getPosition();
        Square[][] resultGrid = keyEventHandler.getUpdatedGame2048().getBoard2048().getGrid();

        resultGrid[staticSquarePosn.x][staticSquarePosn.y] = new Tile(value, staticSquarePosn);
        Square updatedSquare = buildUpdatedSquareByKeyEvent(resultGrid, keyEvent, staticSquare, staticSquare.getPosition());

        if (updatedSquare.getPosition().equals(staticSquare.getPosition())) {
            return keyEventHandler;

        }
        if (updatedSquare.getValue() == staticSquare.getValue() * 2) {
            int currentPoints = this.getScoreboard().getPoints();
            keyEventHandler.getUpdatedGame2048().getScoreboard().setPoints(currentPoints + updatedSquare.getValue());

        }
        resultGrid[updatedSquare.getPosition().x][updatedSquare.getPosition().y] = updatedSquare;
        resultGrid[staticSquarePosn.x][staticSquarePosn.y] = new EmptyTile(staticSquarePosn);
        keyEventHandler.setTilesMoved(true);

        return keyEventHandler;

    }

    static KeyEventHandler handleCurrentEmptyTile (Square staticSquare, KeyEventHandler keyEventHandler) {
        Posn staticSquarePosn = staticSquare.getPosition();
        Board2048 resultBoard2048 = keyEventHandler.getUpdatedGame2048().getBoard2048();

        resultBoard2048.getGrid()[staticSquarePosn.x][staticSquarePosn.y] = new EmptyTile(staticSquarePosn);
        resultBoard2048.getEmptyTilePosns().add(new Posn(staticSquarePosn.x, staticSquarePosn.y));

        return keyEventHandler;
    }

    static Square buildUpdatedSquareByKeyEvent(Square[][] grid, String keyEvent, Square staticSquare, Posn tilePosnToUpdate) {

        if (keyEvent == "up") {

            if (tilePosnToUpdate.x == 0) {
                return new Tile (staticSquare.getValue(), tilePosnToUpdate);
            }

            Posn upPosn = new Posn (tilePosnToUpdate.x - 1, tilePosnToUpdate.y);
            Square updatedSquare = grid[upPosn.x][upPosn.y];

            if (updatedSquare.isTile()) {
                if (updatedSquare.getValue() == staticSquare.getValue()) {
                    return new Tile (staticSquare.getValue() * 2, upPosn);
                } else {
                    return new Tile(staticSquare.getValue(), tilePosnToUpdate);
                }

            } else {
                return buildUpdatedSquareByKeyEvent(grid, keyEvent, staticSquare, upPosn);
            }
        }

        if (keyEvent == "down") {

            if (tilePosnToUpdate.x == 3) {
                return new Tile (staticSquare.getValue(), tilePosnToUpdate);
            }

            Posn downPosn = new Posn (tilePosnToUpdate.x + 1, tilePosnToUpdate.y);
            Square updatedSquare = grid[downPosn.x][downPosn.y];
            if (updatedSquare.isTile()) {
                if (updatedSquare.getValue() == staticSquare.getValue()) {
                    return new Tile (staticSquare.getValue() * 2, downPosn);
                } else {
                    return new Tile (staticSquare.getValue(), tilePosnToUpdate);
                }

            } else {
                return buildUpdatedSquareByKeyEvent(grid, keyEvent, staticSquare, downPosn);
            }
        }

        if (keyEvent == "right") {

            if (tilePosnToUpdate.y == 3) {
                return new Tile (staticSquare.getValue(), tilePosnToUpdate);
            }

            Posn rightPosn = new Posn (tilePosnToUpdate.x, tilePosnToUpdate.y + 1);
            Square compareSquare = grid[rightPosn.x][rightPosn.y];
            if (compareSquare.isTile()) {
                if (compareSquare.getValue() == staticSquare.getValue()) {
                    return new Tile (staticSquare.getValue() * 2, rightPosn);
                } else {
                    return new Tile (staticSquare.getValue(), tilePosnToUpdate);
                }

            } else {
                return buildUpdatedSquareByKeyEvent(grid, keyEvent, staticSquare, rightPosn);
            }
        }

        if (keyEvent == "left") {

            if (tilePosnToUpdate.y == 0) {
                return new Tile (staticSquare.getValue(), tilePosnToUpdate);
            }

            Posn leftPosn = new Posn (tilePosnToUpdate.x, tilePosnToUpdate.y - 1);
            Square compareSquare = grid[leftPosn.x][leftPosn.y];
            if (compareSquare.isTile()) {
                if (compareSquare.getValue() == staticSquare.getValue()) {
                    return new Tile (staticSquare.getValue() * 2, leftPosn);
                } else {
                    return new Tile (staticSquare.getValue(), tilePosnToUpdate);
                }

            } else {
                return buildUpdatedSquareByKeyEvent(grid, keyEvent, staticSquare, leftPosn);
            }

        } else {
            throw new IllegalArgumentException("Key event not found: " + keyEvent);
        }

    }

    static KeyEventHandler createRandomTile (KeyEventHandler keyEventHandler) {

        Board2048 board2048 = keyEventHandler.getUpdatedGame2048().getBoard2048();

        if (keyEventHandler.isTilesMoved()) {
            Posn[] emptyTilePosnsArray = board2048.getEmptyTilePosns().toArray(new Posn[0]);
            int randomInt = new Random().nextInt(emptyTilePosnsArray.length);
            Posn randomTilePosn = emptyTilePosnsArray[randomInt];

            board2048.getGrid()[randomTilePosn.x][randomTilePosn.y] = new Tile(Tile.weightedRandomTileValue(), randomTilePosn);
        }

        return keyEventHandler;
    }

    KeyEventHandler initializeKeyEventMethods () {
        Board2048 resultGrid = new Board2048();
        resultGrid.createEmptyTilesOnGrid();

        Game2048 resultGame = new Game2048(resultGrid, new Scoreboard(this.getScoreboard().getPoints()));

        KeyEventHandler keyEventHandler = new KeyEventHandler();
        keyEventHandler.setTilesMoved(false);
        keyEventHandler.setUpdatedGame2048(resultGame);

        return keyEventHandler;
    }


    public Board2048 getBoard2048() {
        return board2048;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

}
package gameplay;

import javalib.worldimages.Posn;
import models.game2048.Game2048;
import models.game2048.KeyEventHandler;
import models.game2048.Scoreboard;
import models.grid2048.Grid2048;
import models.square.EmptySquare;
import models.square.Square;
import models.square.Tile;
import java.util.Arrays;
import java.util.Random;

public class KeyEventGamePlay {

    public static KeyEventHandler handleUpEvent(Game2048 staticGame) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(staticGame);

        for (int idxRow = 0; idxRow < 4; idxRow++ ) {
            Arrays.stream(staticGame.getGrid2048().getGrid()[idxRow]).forEach((square) ->
                    handleCurrentSquare(square, staticGame,"up", keyEventHandler));
        }
        createRandomTile(keyEventHandler);
        return keyEventHandler;
    }

    public static KeyEventHandler handleDownEvent(Game2048 staticGame) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(staticGame);

        for (int idxRow = 3; idxRow >= 0; idxRow--) {
            Arrays.stream(staticGame.getGrid2048().getGrid()[idxRow]).forEach((square) ->
                    handleCurrentSquare(square, staticGame,"down", keyEventHandler));
        }
        createRandomTile(keyEventHandler);
        return keyEventHandler;
    }

    public static KeyEventHandler handleLeftEvent(Game2048 staticGame) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(staticGame);

        for (int idxRow = 0; idxRow < 4; idxRow++) {
            Arrays.stream(staticGame.getGrid2048().getGrid()[idxRow]).forEach((square) ->
                    handleCurrentSquare(square, staticGame, "left", keyEventHandler));
        }
        createRandomTile(keyEventHandler);
        return keyEventHandler;
    }

    public static KeyEventHandler handleRightEvent(Game2048 staticGame) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(staticGame);

        for (int idxRow = 0; idxRow < 4; idxRow++) {
            for (int idxColumn = 3; idxColumn >= 0; idxColumn--) {
                Square[][] currentGrid = staticGame.getGrid2048().getGrid();
                handleCurrentSquare(currentGrid[idxRow][idxColumn], staticGame, "right", keyEventHandler);
            }
        }
        createRandomTile(keyEventHandler);
        return keyEventHandler;
    }

    static KeyEventHandler handleCurrentSquare(Square staticSquare, Game2048 staticGame, String keyEvent, KeyEventHandler keyEventHandler) {
        if (staticSquare.isTile()) {
            return handleCurrentTile(staticSquare, staticGame, keyEvent, keyEventHandler);
        } else {
            return handleCurrentEmptyTile(staticSquare, keyEventHandler);
        }
    }

    static KeyEventHandler handleCurrentTile(Square staticSquare, Game2048 staticGame, String keyEvent, KeyEventHandler keyEventHandler) {

        int value = staticSquare.getValue();
        Posn staticSquarePosn = staticSquare.getPosition();
        Square[][] resultGrid = keyEventHandler.getResultGame2048().getGrid2048().getGrid();

        resultGrid[staticSquarePosn.x][staticSquarePosn.y] = new Tile(value, staticSquarePosn);
        Square updatedSquare = buildUpdatedSquareByKeyEvent(resultGrid, keyEvent, staticSquare, staticSquare.getPosition());

        if (updatedSquare.getPosition().equals(staticSquare.getPosition())) {
            return keyEventHandler;

        }
        if (updatedSquare.getValue() == staticSquare.getValue() * 2) {
            int currentPoints = staticGame.getScoreboard().getPoints();
            keyEventHandler.getResultGame2048().getScoreboard().setPoints(currentPoints + updatedSquare.getValue());

        }
        resultGrid[updatedSquare.getPosition().x][updatedSquare.getPosition().y] = updatedSquare;
        resultGrid[staticSquarePosn.x][staticSquarePosn.y] = new EmptySquare(staticSquarePosn);
        keyEventHandler.setTilesMoved(true);

        return keyEventHandler;

    }

    static KeyEventHandler handleCurrentEmptyTile(Square staticSquare, KeyEventHandler keyEventHandler) {
        Posn staticSquarePosn = staticSquare.getPosition();
        Grid2048 resultGrid2048 = keyEventHandler.getResultGame2048().getGrid2048();

        resultGrid2048.getGrid()[staticSquarePosn.x][staticSquarePosn.y] = new EmptySquare(staticSquarePosn);
        resultGrid2048.getEmptyTilePosns().add(new Posn(staticSquarePosn.x, staticSquarePosn.y));

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

    static KeyEventHandler createRandomTile(KeyEventHandler keyEventHandler) {

        Grid2048 grid2048 = keyEventHandler.getResultGame2048().getGrid2048();

        if (keyEventHandler.isTilesMoved()) {
            Posn[] emptyTilePosnsArray = grid2048.getEmptyTilePosns().toArray(new Posn[0]);
            int randomInt = new Random().nextInt(emptyTilePosnsArray.length);
            Posn randomTilePosn = emptyTilePosnsArray[randomInt];

            grid2048.getGrid()[randomTilePosn.x][randomTilePosn.y] = new Tile(Tile.weightedRandomTileValue(), randomTilePosn);
        }

        return keyEventHandler;
    }

    static KeyEventHandler initializeKeyEventMethods(Game2048 staticGame) {
        Square[][] grid = new Square[4][4];
        Grid2048.createEmptySquaresOnGrid(grid);
        Grid2048 grid2048 = new Grid2048(grid);

        Game2048 resultGame = new Game2048();
        resultGame.setGrid2048(grid2048);
        resultGame.setScoreboard(new Scoreboard(staticGame.getScoreboard().getPoints()));

        KeyEventHandler keyEventHandler = new KeyEventHandler();
        keyEventHandler.setTilesMoved(false);
        keyEventHandler.setResultGame2048(resultGame);

        return keyEventHandler;
    }
}

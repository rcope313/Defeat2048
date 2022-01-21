package models.grid2048;

import com.google.common.annotations.VisibleForTesting;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayOffsetImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;
import models.game2048.KeyEventHandler;
import models.game2048.Scoreboard;
import models.square.EmptySquare;
import models.square.Square;
import models.square.Tile;
import utility.PosnUtility;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Grid2048 {
    @VisibleForTesting
    final Square[][] grid;
    private final static WorldImage IMAGE = new RectangleImage(Square.SIDE_LENGTH * 4, Square.SIDE_LENGTH * 4, OutlineMode.OUTLINE, Color.BLACK);

    public Grid2048() {
        this.grid = initializeStartingGrid();
    }

    public Grid2048(Square[][] grid) {
        this.grid = grid;
    }

    public KeyEventHandler handleUpEvent(Scoreboard scoreboard) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(scoreboard);
        for (int idxRow = 0; idxRow < 4; idxRow++ ) {
            Arrays.stream(grid[idxRow]).forEach((square) ->
                    handleCurrentSquareByUpdatingKeyEventHandler(square,"up", keyEventHandler));
        }
        createRandomTileOnKeyEventHandlerGrid2048(keyEventHandler);
        return keyEventHandler;
    }

    public KeyEventHandler handleDownEvent(Scoreboard scoreboard) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(scoreboard);
        for (int idxRow = 3; idxRow >= 0; idxRow--) {
            Arrays.stream(grid[idxRow]).forEach((square) ->
                    handleCurrentSquareByUpdatingKeyEventHandler(square,"down", keyEventHandler));
        }
        createRandomTileOnKeyEventHandlerGrid2048(keyEventHandler);
        return keyEventHandler;
    }

    public KeyEventHandler handleLeftEvent(Scoreboard scoreboard) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(scoreboard);
        for (int idxRow = 0; idxRow < 4; idxRow++) {
            Arrays.stream(grid[idxRow]).forEach((square) ->
                    handleCurrentSquareByUpdatingKeyEventHandler(square, "left", keyEventHandler));
        }
        createRandomTileOnKeyEventHandlerGrid2048(keyEventHandler);
        return keyEventHandler;
    }

    public KeyEventHandler handleRightEvent(Scoreboard scoreboard) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(scoreboard);
        for (int idxRow = 0; idxRow < 4; idxRow++) {
            for (int idxColumn = 3; idxColumn >= 0; idxColumn--) {
                handleCurrentSquareByUpdatingKeyEventHandler(grid[idxRow][idxColumn],"right", keyEventHandler);
            }
        }
        createRandomTileOnKeyEventHandlerGrid2048(keyEventHandler);
        return keyEventHandler;
    }

    private static void handleCurrentSquareByUpdatingKeyEventHandler(Square staticSquare, String keyEvent, KeyEventHandler keyEventHandler) {
        if (staticSquare.isTile()) {
            handleCurrentTileByUpdatingKeyEventHandler(staticSquare,keyEvent, keyEventHandler);
        } else {
            handleCurrentEmptyTileByUpdatingKeyEventHandler(staticSquare, keyEventHandler);
        }
    }

    private static void handleCurrentTileByUpdatingKeyEventHandler(Square staticSquare, String keyEvent, KeyEventHandler keyEventHandler) {
        int value = staticSquare.getValue();
        Posn staticSquarePosn = staticSquare.getPosition();
        Square[][] resultGrid = keyEventHandler.getGrid2048().grid;

        resultGrid[staticSquarePosn.x][staticSquarePosn.y] = new Tile(value, staticSquarePosn);
        Square updatedSquare = buildUpdatedSquareByKeyEvent(resultGrid, keyEvent, staticSquare, staticSquare.getPosition());

        if (!updatedSquare.getPosition().equals(staticSquare.getPosition())) {
            resultGrid[updatedSquare.getPosition().x][updatedSquare.getPosition().y] = updatedSquare;
            resultGrid[staticSquarePosn.x][staticSquarePosn.y] = new EmptySquare(staticSquarePosn);

            if (updatedSquare.getValue() == staticSquare.getValue() * 2) {
                int currentPoints = keyEventHandler.getScoreboard().getPoints();
                keyEventHandler.setScoreboard(new Scoreboard(currentPoints + updatedSquare.getValue()));
            }

            keyEventHandler.setGrid2048(new Grid2048(resultGrid));
            keyEventHandler.setTilesMoved(true);
        }
    }

    private static void handleCurrentEmptyTileByUpdatingKeyEventHandler(Square staticSquare, KeyEventHandler keyEventHandler) {
        Posn staticSquarePosn = staticSquare.getPosition();
        Square[][] resultGrid = keyEventHandler.getGrid2048().grid;
        resultGrid[staticSquarePosn.x][staticSquarePosn.y] = new EmptySquare(staticSquarePosn);
        keyEventHandler = new KeyEventHandler(keyEventHandler.isTilesMoved(), new Grid2048(resultGrid), keyEventHandler.getScoreboard());
    }

    private static Square buildUpdatedSquareByKeyEvent(Square[][] grid, String keyEvent, Square staticSquare, Posn tilePosnToUpdate) {
        if (keyEvent.equals("up")) {
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
        if (keyEvent.equals("down")) {
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
        if (keyEvent.equals("right")) {
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
        if (keyEvent.equals("left")) {
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

    private static void createRandomTileOnKeyEventHandlerGrid2048(KeyEventHandler keyEventHandler) {
        Grid2048 grid2048 = keyEventHandler.getGrid2048();
        Square[][] grid = grid2048.grid;

        if (keyEventHandler.isTilesMoved()) {
            Posn[] emptyTilePosnsArray = grid2048.getEmptyTilePosns().toArray(new Posn[0]);
            int randomInt = new Random().nextInt(emptyTilePosnsArray.length);
            Posn randomTilePosn = emptyTilePosnsArray[randomInt];
            grid[randomTilePosn.x][randomTilePosn.y] = new Tile(Tile.weightedRandomTileValue(), randomTilePosn);
        }
        keyEventHandler.setGrid2048(new Grid2048(grid));
    }

    private static KeyEventHandler initializeKeyEventMethods(Scoreboard scoreboard) {
        Square[][] grid = createEmptySquaresOnGrid();
        return new KeyEventHandler(false, new Grid2048(grid), scoreboard);
    }

    private static Square[][] initializeStartingGrid() {
        Square[][] grid = createEmptySquaresOnGrid();
        return addTwoRandomTilesForInitializedGrid(grid);
    }

    @VisibleForTesting
    static Square[][] createEmptySquaresOnGrid() {
        Square[][] grid = new Square[4][4];
        for (int idxRow = 0; idxRow < 4; idxRow ++) {
            for (int idxColumn = 0; idxColumn < 4; idxColumn ++) {
                grid[idxRow][idxColumn] = new EmptySquare(new Posn(idxRow, idxColumn));
            }
        }
        return grid;
    }

    @VisibleForTesting
    static Square[][] addTwoRandomTilesForInitializedGrid(Square[][] grid) {
        ArrayList<Posn> tilePosns = PosnUtility.createRandomPosns();
        Posn t0Posn = new Posn (tilePosns.get(0).x, tilePosns.get(0).y);
        Posn t1Posn = new Posn (tilePosns.get(1).x, tilePosns.get(1).y);
        int t0Value = Tile.weightedRandomTileValue();
        int t1Value = Tile.weightedRandomTileValue();
        grid[t0Posn.x][t0Posn.y] = new Tile(t0Value, t0Posn);
        grid[t1Posn.x][t1Posn.y] = new Tile(t1Value, t1Posn);
        return grid;
    }

    public WorldImage drawGrid() {
        WorldImage resultGrid = IMAGE;
        int dxOffset = -2;
        int dyOffset = -2;

        for (int idxRow = 0; idxRow < 4; idxRow++) {
            for (int idxColumn = 0; idxColumn < 4; idxColumn++) {
                Square s = grid[idxRow][idxColumn];
                resultGrid = drawNextSquareOnGridByPosnOffset(resultGrid, s, dxOffset, dyOffset);
                dxOffset ++;
            }
            dyOffset ++;
            dxOffset = -2;
        }
        return resultGrid;
    }

    private static WorldImage drawNextSquareOnGridByPosnOffset(WorldImage currentGrid, Square s, int dxOffset, int dyOffset) {
        return new OverlayOffsetImage(currentGrid,
                Square.SIDE_LENGTH * dxOffset + (Square.SIDE_LENGTH / 2.0),
                Square.SIDE_LENGTH * dyOffset + (Square.SIDE_LENGTH / 2.0),
                s.image);
    }

    private HashSet<Posn> getEmptyTilePosns() {
        HashSet<Posn> emptyTilePosns = new HashSet<>();
        for (int idxRow = 0; idxRow < 4; idxRow ++) {
            for (int idxColumn = 0; idxColumn < 4; idxColumn++) {
                if (grid[idxRow][idxColumn].isEmptyTile()) {
                    emptyTilePosns.add(new Posn(idxRow, idxColumn));
                }
            }
        }
        return emptyTilePosns;
    }

    public boolean isSameGrid(Grid2048 that) {

        for (int idxRow = 0; idxRow < this.grid.length; idxRow ++) {

            for (int idxColumn = 0; idxColumn < this.grid[idxRow].length; idxColumn++ ) {

                if (!this.grid[idxRow][idxColumn].isSameSquare(that.grid[idxRow][idxColumn])) {
                    return false;
                }
            }
        }

        return true;

    }
}

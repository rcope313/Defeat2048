package models.game;

import com.google.common.annotations.VisibleForTesting;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayOffsetImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;
import models.square.EmptySquare;
import models.square.Square;
import models.square.Tile;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Grid2048 {
    @VisibleForTesting
    final Square[][] grid;
    private final static WorldImage GRID_IMAGE = new RectangleImage(Square.SIDE_LENGTH * 4, Square.SIDE_LENGTH * 4, OutlineMode.OUTLINE, Color.BLACK);
    private final static int SQUARES_PER_AXIS = 4;

    public Grid2048() {
        this.grid = initializeStartingGrid();
    }

    public Grid2048(Square[][] grid) {
        this.grid = grid;
    }

    public KeyEventHandler handleKeyEventWithRandomTile(KeyEvent event, Scoreboard scoreboard) {
        return switch (event) {
            case UP -> handleUpEventWithRandomTile(scoreboard);
            case DOWN -> handleDownEventWithRandomTile(scoreboard);
            case LEFT -> handleLeftEventWithRandomTile(scoreboard);
            case RIGHT -> handleRightEventWithRandomTile(scoreboard);
            case NOUP -> new KeyEventHandler(false, this, scoreboard);
        };
    }

    public KeyEventHandler handleKeyEventWithoutRandomTile(KeyEvent event, Scoreboard scoreboard) {
        return switch (event) {
            case UP -> handleUpEventWithoutRandomTile(scoreboard);
            case DOWN -> handleDownEventWithoutRandomTile(scoreboard);
            case LEFT -> handleLeftEventWithoutRandomTile(scoreboard);
            case RIGHT -> handleRightEventWithoutRandomTile(scoreboard);
            case NOUP -> new KeyEventHandler(false, this, scoreboard);
        };
    }

    private KeyEventHandler handleUpEventWithRandomTile(Scoreboard scoreboard) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(scoreboard);
        for (int idxRow = 0; idxRow < SQUARES_PER_AXIS; idxRow++ ) {
            Arrays.stream(grid[idxRow]).forEach((square) ->
                    handleCurrentSquareByUpdatingKeyEventHandler(square,"up", keyEventHandler));
        }
        createRandomTileOnKeyEventHandler(keyEventHandler);
        return keyEventHandler;
    }

    private KeyEventHandler handleDownEventWithRandomTile(Scoreboard scoreboard) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(scoreboard);
        for (int idxRow = SQUARES_PER_AXIS - 1; idxRow >= 0; idxRow--) {
            Arrays.stream(grid[idxRow]).forEach((square) ->
                    handleCurrentSquareByUpdatingKeyEventHandler(square,"down", keyEventHandler));
        }
        createRandomTileOnKeyEventHandler(keyEventHandler);
        return keyEventHandler;
    }

    private KeyEventHandler handleLeftEventWithRandomTile(Scoreboard scoreboard) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(scoreboard);
        for (int idxRow = 0; idxRow < SQUARES_PER_AXIS; idxRow++) {
            Arrays.stream(grid[idxRow]).forEach((square) ->
                    handleCurrentSquareByUpdatingKeyEventHandler(square, "left", keyEventHandler));
        }
        createRandomTileOnKeyEventHandler(keyEventHandler);
        return keyEventHandler;
    }

    private KeyEventHandler handleRightEventWithRandomTile(Scoreboard scoreboard) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(scoreboard);
        for (int idxRow = 0; idxRow < SQUARES_PER_AXIS; idxRow++) {
            for (int idxColumn = SQUARES_PER_AXIS - 1; idxColumn >= 0; idxColumn--) {
                handleCurrentSquareByUpdatingKeyEventHandler(grid[idxRow][idxColumn],"right", keyEventHandler);
            }
        }
        createRandomTileOnKeyEventHandler(keyEventHandler);
        return keyEventHandler;
    }

    private KeyEventHandler handleUpEventWithoutRandomTile(Scoreboard scoreboard) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(scoreboard);
        for (int idxRow = 0; idxRow < SQUARES_PER_AXIS; idxRow++ ) {
            Arrays.stream(grid[idxRow]).forEach((square) ->
                    handleCurrentSquareByUpdatingKeyEventHandler(square,"up", keyEventHandler));
        }
        return keyEventHandler;
    }

    private KeyEventHandler handleDownEventWithoutRandomTile(Scoreboard scoreboard) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(scoreboard);
        for (int idxRow = SQUARES_PER_AXIS - 1; idxRow >= 0; idxRow--) {
            Arrays.stream(grid[idxRow]).forEach((square) ->
                    handleCurrentSquareByUpdatingKeyEventHandler(square,"down", keyEventHandler));
        }
        return keyEventHandler;
    }

    private KeyEventHandler handleLeftEventWithoutRandomTile(Scoreboard scoreboard) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(scoreboard);
        for (int idxRow = 0; idxRow < SQUARES_PER_AXIS; idxRow++) {
            Arrays.stream(grid[idxRow]).forEach((square) ->
                    handleCurrentSquareByUpdatingKeyEventHandler(square, "left", keyEventHandler));
        }
        return keyEventHandler;
    }

    private KeyEventHandler handleRightEventWithoutRandomTile(Scoreboard scoreboard) {
        KeyEventHandler keyEventHandler = initializeKeyEventMethods(scoreboard);
        for (int idxRow = 0; idxRow < SQUARES_PER_AXIS; idxRow++) {
            for (int idxColumn = SQUARES_PER_AXIS - 1; idxColumn >= 0; idxColumn--) {
                handleCurrentSquareByUpdatingKeyEventHandler(grid[idxRow][idxColumn],"right", keyEventHandler);
            }
        }
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
                return new Tile(staticSquare.getValue(), tilePosnToUpdate);
            }
            Posn upPosn = new Posn (tilePosnToUpdate.x - 1, tilePosnToUpdate.y);
            Square updatedSquare = grid[upPosn.x][upPosn.y];
            if (updatedSquare.isTile()) {
                if (updatedSquare.getValue() == staticSquare.getValue()) {
                    return new Tile(staticSquare.getValue() * 2, upPosn);
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
                    return new Tile(staticSquare.getValue() * 2, downPosn);
                } else {
                    return new Tile(staticSquare.getValue(), tilePosnToUpdate);
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
                    return new Tile(staticSquare.getValue() * 2, rightPosn);
                } else {
                    return new Tile(staticSquare.getValue(), tilePosnToUpdate);
                }
            } else {
                return buildUpdatedSquareByKeyEvent(grid, keyEvent, staticSquare, rightPosn);
            }
        }
        if (keyEvent.equals("left")) {
            if (tilePosnToUpdate.y == 0) {
                return new Tile(staticSquare.getValue(), tilePosnToUpdate);
            }
            Posn leftPosn = new Posn (tilePosnToUpdate.x, tilePosnToUpdate.y - 1);
            Square compareSquare = grid[leftPosn.x][leftPosn.y];
            if (compareSquare.isTile()) {
                if (compareSquare.getValue() == staticSquare.getValue()) {
                    return new Tile(staticSquare.getValue() * 2, leftPosn);
                } else {
                    return new Tile(staticSquare.getValue(), tilePosnToUpdate);
                }
            } else {
                return buildUpdatedSquareByKeyEvent(grid, keyEvent, staticSquare, leftPosn);
            }
        } else {
            throw new IllegalArgumentException("Key event not found: " + keyEvent);
        }
    }

    public static void createRandomTileOnKeyEventHandler(KeyEventHandler keyEventHandler) {
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
        Square[][] grid = new Square[SQUARES_PER_AXIS][SQUARES_PER_AXIS];
        for (int idxRow = 0; idxRow < SQUARES_PER_AXIS; idxRow ++) {
            for (int idxColumn = 0; idxColumn < SQUARES_PER_AXIS; idxColumn ++) {
                grid[idxRow][idxColumn] = new EmptySquare(new Posn(idxRow, idxColumn));
            }
        }
        return grid;
    }

    @VisibleForTesting
    static Square[][] addTwoRandomTilesForInitializedGrid(Square[][] grid) {
        ArrayList<Posn> tilePosns = createRandomPosns();
        Posn t0Posn = new Posn (tilePosns.get(0).x, tilePosns.get(0).y);
        Posn t1Posn = new Posn (tilePosns.get(1).x, tilePosns.get(1).y);
        int t0Value = Tile.weightedRandomTileValue();
        int t1Value = Tile.weightedRandomTileValue();
        grid[t0Posn.x][t0Posn.y] = new Tile(t0Value, t0Posn);
        grid[t1Posn.x][t1Posn.y] = new Tile(t1Value, t1Posn);
        return grid;
    }

    private static ArrayList<Posn> createRandomPosns() {
        ArrayList<Posn> result;
        Random r = new Random();

        Posn p1 = new Posn(r.nextInt(4), r.nextInt(4));
        Posn p2 = new Posn(r.nextInt(4), r.nextInt(4));

        if (p1.equals(p2)) {
            result = createRandomPosns();
        } else {
            result = new ArrayList<>(Arrays.asList(p1, p2));
        }
        return result;
    }

    public WorldImage drawGrid() {
        WorldImage resultGrid = GRID_IMAGE;
        int dxOffset = -SQUARES_PER_AXIS/2;
        int dyOffset = -SQUARES_PER_AXIS/2;

        for (int idxRow = 0; idxRow < SQUARES_PER_AXIS; idxRow++) {
            for (int idxColumn = 0; idxColumn < SQUARES_PER_AXIS; idxColumn++) {
                Square s = grid[idxRow][idxColumn];
                resultGrid = drawNextSquareOnGridByPosnOffset(resultGrid, s, dxOffset, dyOffset);
                dxOffset ++;
            }
            dyOffset ++;
            dxOffset = -SQUARES_PER_AXIS/2;
        }
        return resultGrid;
    }

    private static WorldImage drawNextSquareOnGridByPosnOffset(WorldImage currentGrid, Square s, int dxOffset, int dyOffset) {
        return new OverlayOffsetImage(currentGrid,
                Square.SIDE_LENGTH * dxOffset + (Square.SIDE_LENGTH / 2.0),
                Square.SIDE_LENGTH * dyOffset + (Square.SIDE_LENGTH / 2.0),
                s.getImage());
    }

    private HashSet<Posn> getEmptyTilePosns() {
        HashSet<Posn> emptyTilePosns = new HashSet<>();
        for (int idxRow = 0; idxRow < SQUARES_PER_AXIS; idxRow ++) {
            for (int idxColumn = 0; idxColumn < SQUARES_PER_AXIS; idxColumn++) {
                if (grid[idxRow][idxColumn].isEmptyTile()) {
                    emptyTilePosns.add(new Posn(idxRow, idxColumn));
                }
            }
        }
        return emptyTilePosns;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Grid2048) {
            for (int idxRow = 0; idxRow < this.grid.length; idxRow ++) {
                for (int idxColumn = 0; idxColumn < this.grid[idxRow].length; idxColumn++ ) {
                    if (!this.grid[idxRow][idxColumn].equals(((Grid2048) obj).grid[idxRow][idxColumn])) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
}

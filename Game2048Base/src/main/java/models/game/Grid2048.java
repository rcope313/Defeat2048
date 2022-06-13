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
    public final static int SQUARES_PER_AXIS = 4;

    public Grid2048() {
        this.grid = initializeStartingGrid();
    }

    public Grid2048(Square[][] grid) {
        this.grid = grid;
    }

    public KeyEventHandler handleKeyEvent(KeyEvent event, Scoreboard scoreboard) {
        return switch (event) {
            case UP -> handleUpEvent(scoreboard);
            case DOWN -> handleDownEvent(scoreboard);
            case LEFT -> handleLeftEvent(scoreboard);
            case RIGHT -> handleRightEvent(scoreboard);
            case NOUP -> new KeyEventHandler(false, this, scoreboard);
        };
    }

    public static void addRandomTileOnKeyEventHandler(KeyEventHandler keyEventHandler) {
        Grid2048 grid2048 = keyEventHandler.getGrid2048();
        Square[][] grid = grid2048.grid;

        if (keyEventHandler.isTilesMoved()) {
            Posn[] emptyTilePosnsArray = grid2048.getEmptyTilePosns().toArray(new Posn[0]);
            int randomInt = new Random().nextInt(emptyTilePosnsArray.length);
            Posn randomTilePosn = emptyTilePosnsArray[randomInt];
            grid[randomTilePosn.x][randomTilePosn.y] = new Tile(Tile.weightedRandomTileValue());
        }
        keyEventHandler.setGrid2048(new Grid2048(grid));
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

    public HashSet<Posn> getEmptyTilePosns() {
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

    public Square getSquareByCoordinates(int posnX, int posnY) {
        if (posnY >= SQUARES_PER_AXIS || posnX >= SQUARES_PER_AXIS || posnY < 0 || posnX < 0) {
            throw new IllegalStateException("Invalid coordinates");
        }
        return grid[posnY][posnX];
    }

    private KeyEventHandler handleUpEvent(Scoreboard scoreboard) {
        HashSet<Posn> tilesChanged = new HashSet<>();
        KeyEventHandler resultHandler = new KeyEventHandler(false, createGridCopy(), scoreboard);

        for (int idxRow = 0; idxRow < SQUARES_PER_AXIS; idxRow++) {
            for (int idxCol = 0; idxCol < SQUARES_PER_AXIS; idxCol++) {
                Grid2048 resultGrid = resultHandler.getGrid2048();
                if (this.grid[idxRow][idxCol] instanceof Tile) {
                    resultGrid.moveTileUp(idxRow, idxCol, tilesChanged, resultHandler);
                }
            }
        }
        return resultHandler;
    }

    private KeyEventHandler handleDownEvent(Scoreboard scoreboard) {
        HashSet<Posn> tilesChanged = new HashSet<>();
        KeyEventHandler resultHandler = new KeyEventHandler(false, createGridCopy(), scoreboard);

        for (int idxRow = SQUARES_PER_AXIS - 1; idxRow >= 0; idxRow--) {
            for (int idxCol = 0; idxCol < SQUARES_PER_AXIS; idxCol++) {
                Grid2048 resultGrid = resultHandler.getGrid2048();
                if (resultGrid.grid[idxRow][idxCol] instanceof Tile) {
                    resultGrid.moveTileDown(idxRow, idxCol, tilesChanged, resultHandler);
                }
            }
        }
        return resultHandler;
    }

    private KeyEventHandler handleLeftEvent(Scoreboard scoreboard) {
        HashSet<Posn> tilesChanged = new HashSet<>();
        KeyEventHandler resultHandler = new KeyEventHandler(false, createGridCopy(), scoreboard);

        for (int idxRow = 0; idxRow < SQUARES_PER_AXIS; idxRow++) {
            for (int idxCol = 0; idxCol < SQUARES_PER_AXIS; idxCol++) {
                Grid2048 resultGrid = resultHandler.getGrid2048();
                if (resultGrid.grid[idxRow][idxCol] instanceof Tile) {
                    resultGrid.moveTileLeft(idxRow, idxCol, tilesChanged, resultHandler);
                }
            }
        }
        return resultHandler;
    }

    private KeyEventHandler handleRightEvent(Scoreboard scoreboard) {
        HashSet<Posn> tilesChanged = new HashSet<>();
        KeyEventHandler resultHandler = new KeyEventHandler(false, createGridCopy(), scoreboard);

        for (int idxRow = 0; idxRow < SQUARES_PER_AXIS; idxRow++) {
            for (int idxCol = SQUARES_PER_AXIS - 1; idxCol >= 0; idxCol--) {
                Grid2048 resultGrid = resultHandler.getGrid2048();
                if (resultGrid.grid[idxRow][idxCol] instanceof Tile) {
                    resultGrid.moveTileRight(idxRow, idxCol, tilesChanged, resultHandler);
                }
            }
        }
        return resultHandler;
    }

    private void moveTileUp(int idxRow, int idxCol, HashSet<Posn> tilesChanged, KeyEventHandler handler) {
        Square square = this.getSquareByCoordinates(idxCol, idxRow);

        while (idxRow > 0) {
            Square squareUp = this.grid[idxRow - 1][idxCol];
            this.grid[idxRow][idxCol] = new EmptySquare();
            if (squareUp.isTile()) {
                if (squareUp.getValue() == square.getValue() && !tilesChanged.contains(new Posn(idxRow - 1, idxCol))) {
                    this.grid[idxRow - 1][idxCol] = new Tile(square.getValue() * 2);
                    tilesChanged.add(new Posn(idxRow - 1, idxCol));
                    handler.setScoreboard(new Scoreboard(handler.getScoreboard().getPoints() + square.getValue() * 2));
                    handler.setTilesMoved(true);
                } else {
                    this.grid[idxRow][idxCol] = new Tile(square.getValue());
                }
                break;
            }
            idxRow--;
            handler.setTilesMoved(true);
        }
        if (idxRow == 0) {
            this.grid[idxRow][idxCol] = new Tile(square.getValue());
        }
    }

    private void moveTileDown(int idxRow, int idxCol, HashSet<Posn> tilesChanged, KeyEventHandler handler) {
        Square square = this.getSquareByCoordinates(idxCol, idxRow);

        while (idxRow < SQUARES_PER_AXIS - 1) {
            Square squareDown = this.grid[idxRow + 1][idxCol];
            this.grid[idxRow][idxCol] = new EmptySquare();
            if (squareDown.isTile()) {
                if (squareDown.getValue() == square.getValue() && !tilesChanged.contains(new Posn(idxRow + 1, idxCol))) {
                    this.grid[idxRow + 1][idxCol] = new Tile(square.getValue() * 2);
                    tilesChanged.add(new Posn(idxRow + 1, idxCol));
                    handler.setScoreboard(new Scoreboard(handler.getScoreboard().getPoints() + square.getValue() * 2));
                    handler.setTilesMoved(true);
                } else {
                    this.grid[idxRow][idxCol] = new Tile(square.getValue());
                }
                break;
            }
            idxRow++;
            handler.setTilesMoved(true);
        }
        if (idxRow == SQUARES_PER_AXIS - 1) {
            this.grid[idxRow][idxCol] = new Tile(square.getValue());
        }
    }

    private void moveTileLeft(int idxRow, int idxCol, HashSet<Posn> tilesChanged, KeyEventHandler handler) {
        Square square = this.getSquareByCoordinates(idxCol, idxRow);

        while (idxCol > 0) {
            Square squareLeft = this.grid[idxRow][idxCol - 1];
            this.grid[idxRow][idxCol] = new EmptySquare();
            if (squareLeft.isTile()) {
                if (squareLeft.getValue() == square.getValue() && !tilesChanged.contains(new Posn(idxRow, idxCol - 1))) {
                    this.grid[idxRow][idxCol - 1] = new Tile(square.getValue() * 2);
                    tilesChanged.add(new Posn(idxRow, idxCol - 1));
                    handler.setScoreboard(new Scoreboard(handler.getScoreboard().getPoints() + square.getValue() * 2));
                    handler.setTilesMoved(true);
                } else {
                    this.grid[idxRow][idxCol] = new Tile(square.getValue());
                }
                break;
            }
            idxCol--;
            handler.setTilesMoved(true);
        }
        if (idxCol == 0) {
            this.grid[idxRow][idxCol] = new Tile(square.getValue());
        }
    }

    private void moveTileRight(int idxRow, int idxCol, HashSet<Posn> tilesChanged, KeyEventHandler handler) {
        Square square = this.getSquareByCoordinates(idxCol, idxRow);

        while (idxCol < SQUARES_PER_AXIS - 1) {
            Square squareRight = this.grid[idxRow][idxCol + 1];
            this.grid[idxRow][idxCol] = new EmptySquare();
            if (squareRight.isTile()) {
                if (squareRight.getValue() == square.getValue() && !tilesChanged.contains(new Posn(idxRow, idxCol + 1))) {
                    this.grid[idxRow][idxCol + 1] = new Tile(square.getValue() * 2);
                    tilesChanged.add(new Posn(idxRow, idxCol + 1));
                    handler.setScoreboard(new Scoreboard(handler.getScoreboard().getPoints() + square.getValue() * 2));
                    handler.setTilesMoved(true);
                } else {
                    this.grid[idxRow][idxCol] = new Tile(square.getValue());
                }
                break;
            }
            idxCol++;
            handler.setTilesMoved(true);
        }
        if (idxCol == SQUARES_PER_AXIS - 1) {
            this.grid[idxRow][idxCol] = new Tile(square.getValue());
        }
    }

    @VisibleForTesting
    Grid2048 createGridCopy() {
        Square[][] resultGrid = new Square[SQUARES_PER_AXIS][SQUARES_PER_AXIS];
        for (int idxRow = 0; idxRow < SQUARES_PER_AXIS; idxRow++) {
            for (int idxCol = 0; idxCol < SQUARES_PER_AXIS; idxCol++) {
                Square currentSquare = this.grid[idxRow][idxCol];
                if (currentSquare.isTile()) {
                    resultGrid[idxRow][idxCol] = new Tile(currentSquare.getValue());
                } else {
                    resultGrid[idxRow][idxCol] = new EmptySquare();
                }
            }
        }
        return new Grid2048(resultGrid);
    }

    private static Square[][] initializeStartingGrid() {
        Square[][] grid = createEmptySquareGrid();
        return addTwoRandomTilesForInitializedGrid(grid);
    }

    @VisibleForTesting
    static Square[][] createEmptySquareGrid() {
        Square[][] resultGrid = new Square[SQUARES_PER_AXIS][SQUARES_PER_AXIS];
        for (int idxRow = 0; idxRow < SQUARES_PER_AXIS; idxRow++) {
            for (int idxCol = 0; idxCol < SQUARES_PER_AXIS; idxCol++) {
                resultGrid[idxRow][idxCol] = new EmptySquare();
            }
        }
        return resultGrid;
    }

    @VisibleForTesting
    static Square[][] addTwoRandomTilesForInitializedGrid(Square[][] grid) {
        ArrayList<Posn> tilePosns = createRandomPosns();
        Posn t0Posn = new Posn (tilePosns.get(0).x, tilePosns.get(0).y);
        Posn t1Posn = new Posn (tilePosns.get(1).x, tilePosns.get(1).y);
        int t0Value = Tile.weightedRandomTileValue();
        int t1Value = Tile.weightedRandomTileValue();
        grid[t0Posn.x][t0Posn.y] = new Tile(t0Value);
        grid[t1Posn.x][t1Posn.y] = new Tile(t1Value);
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

    private static WorldImage drawNextSquareOnGridByPosnOffset(WorldImage currentGrid, Square s, int dxOffset, int dyOffset) {
        return new OverlayOffsetImage(currentGrid,
                Square.SIDE_LENGTH * dxOffset + (Square.SIDE_LENGTH / 2.0),
                Square.SIDE_LENGTH * dyOffset + (Square.SIDE_LENGTH / 2.0),
                s.getImage());
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

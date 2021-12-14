package models.grid2048;

import com.google.common.annotations.VisibleForTesting;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayOffsetImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;
import models.square.EmptySquare;
import models.square.Square;
import models.square.Tile;
import utility.PosnUtility;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;

public class Grid2048 {

    public Square[][] grid;
    static WorldImage IMAGE = new RectangleImage(
            Square.SIDE_LENGTH * 4,
            Square.SIDE_LENGTH * 4,
            OutlineMode.OUTLINE,
            Color.BLACK);

    public Grid2048() {
        this.grid = initializeStartingGrid();
    }

    public Grid2048(Square[][] grid) {
        this.grid = grid;
    }

    private static Square[][] initializeStartingGrid() {
        Square[][] grid = new Square[4][4];

        createEmptyTilesOnGrid(grid);
        addTwoRandomTilesForInitializedGrid(grid);

        return grid;
    }

    public static void createEmptyTilesOnGrid(Square[][] grid) {
        for (int idxRow = 0; idxRow < 4; idxRow ++) {
            for (int idxColumn = 0; idxColumn < 4; idxColumn ++) {
                grid[idxRow][idxColumn] = new EmptySquare(new Posn(idxRow, idxColumn));
            }
        }
    }

    @VisibleForTesting
    static void addTwoRandomTilesForInitializedGrid(Square[][] grid) {
        ArrayList<Posn> tilePosns = PosnUtility.createRandomPosns();
        Posn t0Posn = new Posn (tilePosns.get(0).x, tilePosns.get(0).y);
        Posn t1Posn = new Posn (tilePosns.get(1).x, tilePosns.get(1).y);
        int t0Value = Tile.weightedRandomTileValue();
        int t1Value = Tile.weightedRandomTileValue();
        grid[t0Posn.x][t0Posn.y] = new Tile(t0Value, t0Posn);
        grid[t1Posn.x][t1Posn.y] = new Tile(t1Value, t1Posn);
    }

    public WorldImage drawGrid() {
        WorldImage resultGrid = IMAGE;
        int dxOffset = -2;
        int dyOffset = -2;

        for (int idxRow = 0; idxRow < 4; idxRow++) {
            for (int idxColumn = 0; idxColumn < 4; idxColumn++) {
                Square s = this.getGrid()[idxRow][idxColumn];
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
                Square.SIDE_LENGTH * dxOffset + Square.SIDE_LENGTH /2,
                Square.SIDE_LENGTH * dyOffset + Square.SIDE_LENGTH /2,
                s.image);
    }

    public HashSet<Posn> getEmptyTilePosns() {
        HashSet<Posn> emptyTilePosns = new HashSet<>();

        for (int idxRow = 0; idxRow < 4; idxRow ++) {
            for (int idxColumn = 0; idxColumn < 4; idxColumn++) {
                if (this.getGrid()[idxRow][idxColumn].isEmptyTile()) {
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

    public Square[][] getGrid() {
        return grid;
    }

}
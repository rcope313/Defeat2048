package models.board2048;

import javalib.worldimages.*;
import models.square.*;
import utility.Utility;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Board2048 {

    public Square[][] grid;
    static WorldImage IMAGE = new RectangleImage (
            Square.SIDE_LENGTH * 4,
            Square.SIDE_LENGTH * 4,
            OutlineMode.OUTLINE,
            Color.BLACK);

    public Board2048() {
        this.grid = new Square[4][4];

    }

    @Override
    public boolean equals(Object obj) {
        Board2048 otherBoard = (Board2048) obj;

        for (int idxRow = 0; idxRow < this.grid.length; idxRow ++) {

            for (int idxColumn = 0; idxColumn < this.grid[idxRow].length; idxColumn++ ) {

                if (!this.grid[idxRow][idxColumn].isSameSquare(otherBoard.grid[idxRow][idxColumn])) {
                    return false;
                }
            }
        }

        return true;
    }

    public Board2048(Square[][] grid) {
        this.grid = grid;
    }

    public Board2048 initializeStartingGrid() {
        this.createEmptyTilesOnGrid();
        this.addRandomTilesToGrid();

        return this;
    }

    public void createEmptyTilesOnGrid() {
        for (int idxRow = 0; idxRow < 4; idxRow ++) {
            for (int idxColumn = 0; idxColumn < 4; idxColumn ++) {
                this.getGrid()[idxRow][idxColumn] = new EmptyTile(new Posn (idxRow, idxColumn));
            }
        }
    }

    void addRandomTilesToGrid () {
        ArrayList<Posn> tilePosns = Utility.createRandomPosns();
        Posn t0Posn = new Posn (tilePosns.get(0).x, tilePosns.get(0).y);
        Posn t1Posn = new Posn (tilePosns.get(1).x, tilePosns.get(1).y);
        int t0Value = Tile.weightedRandomTileValue();
        int t1Value = Tile.weightedRandomTileValue();

        this.getGrid()[t0Posn.x][t0Posn.y] = new Tile(t0Value, t0Posn);
        this.getGrid()[t1Posn.x][t1Posn.y] = new Tile(t1Value, t1Posn);

    }

    public WorldImage drawGrid() {

        WorldImage resultGrid = IMAGE;
        int dxOffset = -2;
        int dyOffset = -2;

        for (int idxRow = 0; idxRow < 4; idxRow++) {

            for (int idxColumn = 0; idxColumn < 4; idxColumn++) {

                Square s = this.getGrid()[idxRow][idxColumn];
                resultGrid = new OverlayOffsetImage(resultGrid,
                        Square.SIDE_LENGTH * dxOffset + Square.SIDE_LENGTH /2,
                        Square.SIDE_LENGTH * dyOffset + Square.SIDE_LENGTH /2,
                        s.image);

                dxOffset ++;
            }
            dyOffset ++;
            dxOffset = -2;
        }

        return resultGrid;
    }

    public HashSet<Posn> getEmptyTilePosns () {
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

    public Square[][] getGrid() {
        return grid;
    }

    public void setGrid(Square[][] grid) {
        this.grid = grid;
    }

}
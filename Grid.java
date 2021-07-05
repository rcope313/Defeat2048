package game2048;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;

import javalib.worldimages.*;

public class Grid {

    Square[][] squareArrArr;
    static WorldImage IMAGE = new RectangleImage (
            Square.SIDE_LENGTH * 4,
            Square.SIDE_LENGTH * 4,
            OutlineMode.OUTLINE,
            Color.BLACK
    );

    Grid () {
        this.squareArrArr = createStartingGrid();
    }

    Grid (Square[][] squareArrArr) {
        this.squareArrArr = squareArrArr;
    }


    static Square[][] createStartingGrid() {

        EmptyTile empT = new EmptyTile();
        Square[][] resultSquareSuperList =
                {{empT, empT, empT, empT},
                        {empT, empT, empT, empT},
                        {empT, empT, empT, empT},
                        {empT, empT, empT, empT}};

        ArrayList<Posn> tilePosns = randomPosns();
        int t1Value = Tile.weightedRandomTileValue();
        int t2Value = Tile.weightedRandomTileValue();

        resultSquareSuperList[tilePosns.get(0).x][tilePosns.get(0).y] = new Tile(t1Value);
        resultSquareSuperList[tilePosns.get(1).x][tilePosns.get(1).y] = new Tile(t2Value);

        return resultSquareSuperList;

    }

    WorldImage drawGrid () {

        WorldImage resultGrid = this.IMAGE;
        int dxOffset = -2;
        int dyOffset = -2;

        for (int idxRow = 0; idxRow < this.squareArrArr.length; idxRow++) {

            for (int idxColumn = 0; idxColumn < this.squareArrArr[idxRow].length; idxColumn++) {

                Square s = this.squareArrArr[idxRow][idxColumn];
                resultGrid = new OverlayOffsetImage(resultGrid,
                        s.SIDE_LENGTH * dxOffset + s.SIDE_LENGTH/2,
                        s.SIDE_LENGTH * dyOffset + s.SIDE_LENGTH/2,
                        s.image);

                dxOffset ++;

            }

            dyOffset ++;
            dxOffset = -2;

        }

        return resultGrid;

    }

    void addRandomTileToGrid (Set<Posn> emptyTilePosns, boolean noTilesMoved, int points) {

        if (!noTilesMoved) {

            Posn[] emptyTilePosnsArray = emptyTilePosns.toArray(new Posn[0]);
            int randomInt = new Random().nextInt(emptyTilePosnsArray.length);
            Posn newTilePosn = emptyTilePosnsArray[randomInt];

            this.squareArrArr[newTilePosn.x][newTilePosn.y] =
                    new Tile(Tile.weightedRandomTileValue());


        }

    }

    private static ArrayList<Posn> randomPosns() {

        ArrayList<Posn> result;
        Random r = new Random();

        Posn p1 = new Posn(r.nextInt(4), r.nextInt(4));
        Posn p2 = new Posn(r.nextInt(4), r.nextInt(4));

        if (p1 == p2) {
            result = randomPosns();
        } else {
            result = new ArrayList<>(Arrays.asList(p1, p2));
        }

        return result;
    }

    boolean isSameGrid (Grid that) {

        for (int idxRow = 0; idxRow < this.squareArrArr.length; idxRow ++) {

            for (int idxColumn = 0; idxColumn < this.squareArrArr[idxRow].length; idxColumn++ ) {

                if (!this.squareArrArr[idxRow][idxColumn].isSameSquare(that.squareArrArr[idxRow][idxColumn])) {
                    return false;
                }
            }
        }

        return true;

    }


}

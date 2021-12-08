package src.main.java;
import javalib.worldimages.*;

import java.awt.*;

public class EmptyTile extends Square {

    public EmptyTile() {
        this.image =
                new OverlayImage
                    (new RectangleImage(Square.SIDE_LENGTH - 5,
                        Square.SIDE_LENGTH - 5,
                        OutlineMode.SOLID,
                        Color.GRAY),
                    (new RectangleImage(Square.SIDE_LENGTH,
                        Square.SIDE_LENGTH,
                        OutlineMode.SOLID,
                        Color.DARK_GRAY)));

    }

    @Override
    public boolean isTile() { return false; }


    @Override
    public boolean isSameSquare(Square that) {
        return that.isSameEmptyTile(this);
    }
    @Override
    public boolean isSameTile(Tile that) {
        return false;
    }
    @Override
    public boolean isSameEmptyTile(EmptyTile that) {
        return true;
    }


    @Override
    public int getValue() {
        throw new IllegalArgumentException("Empty Tile has no value");
    }



    @Override
    public boolean sameValue(Square that) {
        return that.sameEmptyTileValue(this);
    }
    @Override
    public boolean sameTileValue(Tile that) {
        throw new IllegalArgumentException("Empty Tiles have no value. Don't compare an empty tile to a tile!!");
    }
    @Override
    public boolean sameEmptyTileValue(EmptyTile that) {
        throw new IllegalArgumentException("Empty Tiles have no value. Don't compare two empty tiles!!");
    }

}

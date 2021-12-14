package models.square;


import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;

import java.awt.Color;

public class EmptySquare extends Square {

    public EmptySquare() {
        this.value = 0;
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

    public EmptySquare(Posn position) {
        this.position = position;
        this.value = 0;
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
    public boolean isEmptyTile() {
        return true;
    }

    @Override
    public boolean isSameSquare(Square that) {
        return that.isSameEmptyTile(this);
    }

    @Override
    public boolean isSameTile(Tile that) {
        return false;
    }

    @Override
    public boolean isSameEmptyTile(EmptySquare that) {
        return this.getPosition().equals(that.getPosition());
    }
}


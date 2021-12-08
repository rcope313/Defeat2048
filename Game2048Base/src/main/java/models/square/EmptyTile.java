package models.square;

import javalib.worldimages.*;
import java.awt.*;

public class EmptyTile extends Square {

    public EmptyTile() {
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

    public EmptyTile(Posn position) {
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
    public boolean isSameEmptyTile(EmptyTile that) {
        if (this.getPosition().equals(that.getPosition())) {
            return true;
        } else {
            return false;
        }
    }


}

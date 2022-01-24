package models.square;

import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import java.awt.Color;

public class EmptySquare extends Square {

    public EmptySquare(Posn position) {
        super(position);
    }

    @Override
    public OverlayImage buildImage() {
        return new OverlayImage
                (new RectangleImage(Square.SIDE_LENGTH - BORDER_OFFSET,
                        Square.SIDE_LENGTH - BORDER_OFFSET,
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
}

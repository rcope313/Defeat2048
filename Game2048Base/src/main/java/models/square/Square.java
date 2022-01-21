package models.square;

import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

public abstract class Square {
    public final static int SIDE_LENGTH = 100;
    public WorldImage image;
    public Posn position;
    public int value;

    public abstract boolean isTile();

    public abstract boolean isEmptyTile();

    public abstract boolean isSameSquare(Square that);

    public abstract boolean isSameTile(Tile that);

    public abstract boolean isSameEmptyTile(EmptySquare that);

    public WorldImage getImage() {
        return image;
    }

    public void setImage(WorldImage image) {
        this.image = image;
    }

    public Posn getPosition() {
        return position;
    }

    public void setPosition(Posn position) {
        this.position = position;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

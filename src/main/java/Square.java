package src.main.java;
import javalib.worldimages.WorldImage;

public abstract class Square {
    public static int SIDE_LENGTH = 100;
    public WorldImage image;

    Square () {}

    public abstract boolean isTile();

    public abstract boolean isSameSquare(Square that);
    public abstract boolean isSameTile(Tile that);
    public abstract boolean isSameEmptyTile(EmptyTile that);

    public abstract int getValue();
    public abstract boolean sameValue(Square that);
    public abstract boolean sameTileValue(Tile that);
    public abstract boolean sameEmptyTileValue (EmptyTile that);


}

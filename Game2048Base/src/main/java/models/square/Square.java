package models.square;

import javalib.worldimages.OverlayImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

public abstract class Square {
    public final static int SIDE_LENGTH = 100;
    static final int BORDER_OFFSET = 5;
    static final double SMALL_VALUE_TEXT_SIZE_OFFSET = .4;
    static final double MEDIUM_VALUE_TEXT_SIZE_OFFSET = .35;
    static final double LARGE_VALUE_TEXT_SIZE_OFFSET = .3;
    static final double VERY_LARGE_VALUE_TEXT_SIZE_OFFSET = .3;
    private final WorldImage image;
    private final Posn position;
    final int value;

    public Square(int value, Posn position) {
        this.position = position;
        this.value = value;
        this.image = buildImage();
    }

    public Square(Posn position) {
        this.position = position;
        this.value = 0;
        this.image = buildImage();
    }

    public abstract OverlayImage buildImage();

    public abstract boolean isTile();

    public abstract boolean isEmptyTile();

    @Override
    public boolean equals(Object obj) {
        Square square = (Square) obj;
        return square.position.equals(position) && square.value == value;
    }

    public WorldImage getImage() {
        return image;
    }

    public Posn getPosition() {
        return position;
    }

    public int getValue() {
        return value;
    }

}

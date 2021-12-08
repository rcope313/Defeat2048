package src.main.java;

import javalib.worldimages.*;

import java.awt.*;
import java.util.Random;

public class Tile extends Square {
    int value;

    public Tile(int value) {
        this.image = tileImage(value);
        this.value = value;

    }


    @Override
    public boolean isTile() { return true; }


    @Override
    public boolean isSameSquare(Square that) {
        return that.isSameTile(this);
    }
    @Override
    public boolean isSameTile(Tile that) {

        if (this.value == that.value) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean isSameEmptyTile(EmptyTile that) {
        return false;
    }


    @Override
    public int getValue() {
        return this.value;
    }


    @Override
    public boolean sameValue(Square that) {
        return that.sameTileValue(this);
    }
    @Override
    public boolean sameTileValue(Tile that) {
        if (this.value == that.value) { return true; }
        else { return false; }

    }
    @Override
    public boolean sameEmptyTileValue(EmptyTile that) {
        throw new IllegalArgumentException("Empty tiles have no value. Don't compare a tile to an empty tile!!");
    }



    static int weightedRandomTileValue() {
        Random r = new Random();
        int threshold = r.nextInt(101);

        if (threshold > 10) {
            return 2;
        } else {
            return 4;
        }
    }

    static WorldImage tileImage(int value) {

        if (value == 2) {

            WorldImage nonBorder =
                    (new OverlayImage
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * .4, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - 5,
                                            SIDE_LENGTH - 5,
                                            OutlineMode.SOLID,
                                            Color.WHITE))));


            return new OverlayImage
                    (nonBorder,
                            (new RectangleImage(SIDE_LENGTH,
                                    SIDE_LENGTH,
                                    OutlineMode.SOLID,
                                    Color.DARK_GRAY)));


        }

        if (value == 4) {
            WorldImage nonBorder =
                    (new OverlayImage
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * .4, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - 5,
                                            SIDE_LENGTH - 5,
                                            OutlineMode.SOLID,
                                            Color.LIGHT_GRAY))));


            return new OverlayImage
                    (nonBorder,
                            (new RectangleImage(SIDE_LENGTH,
                                    SIDE_LENGTH,
                                    OutlineMode.SOLID,
                                    Color.DARK_GRAY)));

        }

        if (value == 8 || value == 16 || value == 32) {

            WorldImage nonBorder =
                    (new OverlayImage
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * .4, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - 5,
                                            SIDE_LENGTH - 5,
                                            OutlineMode.SOLID,
                                            Color.PINK))));


            return new OverlayImage
                    (nonBorder,
                            (new RectangleImage(SIDE_LENGTH,
                                    SIDE_LENGTH,
                                    OutlineMode.SOLID,
                                    Color.DARK_GRAY)));

        }

        if (value == 64) {

            WorldImage nonBorder =
                    (new OverlayImage
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * .4, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - 5,
                                            SIDE_LENGTH - 5,
                                            OutlineMode.SOLID,
                                            Color.RED))));


            return new OverlayImage
                    (nonBorder,
                            (new RectangleImage(SIDE_LENGTH,
                                    SIDE_LENGTH,
                                    OutlineMode.SOLID,
                                    Color.DARK_GRAY)));

        }

        if (value == 128 || value == 256) {

            WorldImage nonBorder =
                    (new OverlayImage
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * .35, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - 5,
                                            SIDE_LENGTH - 5,
                                            OutlineMode.SOLID,
                                            Color.YELLOW))));


            return new OverlayImage
                    (nonBorder,
                            (new RectangleImage(SIDE_LENGTH,
                                    SIDE_LENGTH,
                                    OutlineMode.SOLID,
                                    Color.DARK_GRAY)));

        }

        if (value == 512) {

            WorldImage nonBorder =
                    (new OverlayImage
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * .35, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - 5,
                                            SIDE_LENGTH - 5,
                                            OutlineMode.SOLID,
                                            Color.ORANGE))));


            return new OverlayImage
                    (nonBorder,
                            (new RectangleImage(SIDE_LENGTH,
                                    SIDE_LENGTH,
                                    OutlineMode.SOLID,
                                    Color.DARK_GRAY)));

        }

        if (value == 1024 || value == 2048) {

            WorldImage nonBorder =
                    (new OverlayImage
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * .3, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - 5,
                                            SIDE_LENGTH - 5,
                                            OutlineMode.SOLID,
                                            Color.ORANGE))));


            return new OverlayImage
                    (nonBorder,
                            (new RectangleImage(SIDE_LENGTH,
                                    SIDE_LENGTH,
                                    OutlineMode.SOLID,
                                    Color.DARK_GRAY)));

        }

        if (value == 4096 ||
                value == 8192) {

            WorldImage nonBorder =
                    (new OverlayImage
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * .3, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - 5,
                                            SIDE_LENGTH - 5,
                                            OutlineMode.SOLID,
                                            Color.CYAN))));


            return new OverlayImage
                    (nonBorder,
                            (new RectangleImage(SIDE_LENGTH,
                                    SIDE_LENGTH,
                                    OutlineMode.SOLID,
                                    Color.DARK_GRAY)));


        }


        if (value == 16384 ||
                value == 32768 ||
                value == 65536 ||
                value == 131072) {

            WorldImage nonBorder =
                    (new OverlayImage
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * .2, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - 5,
                                            SIDE_LENGTH - 5,
                                            OutlineMode.SOLID,
                                            Color.CYAN))));


            return new OverlayImage
                    (nonBorder,
                            (new RectangleImage(SIDE_LENGTH,
                                    SIDE_LENGTH,
                                    OutlineMode.SOLID,
                                    Color.DARK_GRAY)));


        } else {
            throw new IllegalArgumentException("Value not valid: " + Integer.toString(value));
        }


    }


}

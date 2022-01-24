package models.square;

import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;
import java.awt.*;
import java.util.Random;

public class Tile extends Square {
    private static final int TILE_SEED_LOW_VALUE = 2;
    private static final int TILE_SEED_HIGH_VALUE = 4;

    public Tile(int value, Posn position) {
        super(value, position);
    }

    public static int weightedRandomTileValue() {
        Random r = new Random();
        int threshold = r.nextInt(101);
        if (threshold > 10) {
            return TILE_SEED_LOW_VALUE;
        } else {
            return TILE_SEED_HIGH_VALUE;
        }
    }

    @Override
    public OverlayImage buildImage() {
        if (value == 2) {
            WorldImage nonBorder =
                    (new OverlayImage
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * SMALL_VALUE_TEXT_SIZE_OFFSET, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - BORDER_OFFSET,
                                            SIDE_LENGTH - BORDER_OFFSET,
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
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * SMALL_VALUE_TEXT_SIZE_OFFSET, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - BORDER_OFFSET,
                                            SIDE_LENGTH - BORDER_OFFSET,
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
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * SMALL_VALUE_TEXT_SIZE_OFFSET, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - BORDER_OFFSET,
                                            SIDE_LENGTH - BORDER_OFFSET,
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
                                    (new RectangleImage(SIDE_LENGTH - BORDER_OFFSET,
                                            SIDE_LENGTH - BORDER_OFFSET,
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
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * MEDIUM_VALUE_TEXT_SIZE_OFFSET, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - BORDER_OFFSET,
                                            SIDE_LENGTH - BORDER_OFFSET,
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
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * MEDIUM_VALUE_TEXT_SIZE_OFFSET, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - BORDER_OFFSET,
                                            SIDE_LENGTH - BORDER_OFFSET,
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
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * LARGE_VALUE_TEXT_SIZE_OFFSET, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - BORDER_OFFSET,
                                            SIDE_LENGTH - BORDER_OFFSET,
                                            OutlineMode.SOLID,
                                            Color.ORANGE))));
            return new OverlayImage
                    (nonBorder,
                            (new RectangleImage(SIDE_LENGTH,
                                    SIDE_LENGTH,
                                    OutlineMode.SOLID,
                                    Color.DARK_GRAY)));
        }
        if (value == 4096 ||value == 8192) {
            WorldImage nonBorder =
                    (new OverlayImage
                            (new TextImage(Integer.toString(value), SIDE_LENGTH * LARGE_VALUE_TEXT_SIZE_OFFSET, Color.black),
                                    (new RectangleImage(SIDE_LENGTH - BORDER_OFFSET,
                                            SIDE_LENGTH - BORDER_OFFSET,
                                            OutlineMode.SOLID,
                                            Color.CYAN))));
            return new OverlayImage
                    (nonBorder,
                            (new RectangleImage(SIDE_LENGTH,
                                    SIDE_LENGTH,
                                    OutlineMode.SOLID,
                                    Color.DARK_GRAY)));
        }
        if (value == 16384 || value == 32768 || value == 65536 || value == 131072) {
            WorldImage nonBorder =
                    (new OverlayImage
                            (new TextImage (Integer.toString(value),
                                    SIDE_LENGTH * VERY_LARGE_VALUE_TEXT_SIZE_OFFSET,
                                    Color.black),
                                    (new RectangleImage(SIDE_LENGTH - BORDER_OFFSET,
                                            SIDE_LENGTH - BORDER_OFFSET,
                                            OutlineMode.SOLID,
                                            Color.CYAN))));
            return new OverlayImage
                    (nonBorder,
                            (new RectangleImage(SIDE_LENGTH,
                                    SIDE_LENGTH,
                                    OutlineMode.SOLID,
                                    Color.DARK_GRAY)));

        } else {
            throw new IllegalArgumentException("Value not valid: " + value);
        }
    }

    @Override
    public boolean isTile() {
        return true;
    }

    @Override
    public boolean isEmptyTile() {
        return false;
    }

}

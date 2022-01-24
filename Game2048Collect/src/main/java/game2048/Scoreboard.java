package game2048;

import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayImage;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;
import java.awt.Color;

public class Scoreboard {
    private final int points;
    public final static int WIDTH = 200;
    public final static int HEIGHT = 50;
    public final static WorldImage IMAGE = new RectangleImage(WIDTH, HEIGHT, OutlineMode.SOLID, Color.BLACK);

    public Scoreboard(int points) {
        this.points = points;
    }

    public WorldImage drawScoreboard() {
        return new OverlayImage
                (new TextImage(Integer.toString(this.points), 30, Color.WHITE),
                        this.IMAGE);
    }

    public int getPoints() {
        return points;
    }
}

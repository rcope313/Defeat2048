package game2048;

import javalib.worldimages.*;

import java.awt.*;

public class Scoreboard {

    public int points;
    public static int WIDTH = 200;
    public static int HEIGHT = 50;
    public static WorldImage IMAGE = new RectangleImage(WIDTH, HEIGHT, OutlineMode.SOLID, Color.BLACK);

    public Scoreboard (int points) {
        this.points = points;
    }

    public WorldImage drawScoreboard () {
        return new OverlayImage
                (new TextImage(Integer.toString(this.points), 30, Color.WHITE),
                        this.IMAGE);

    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
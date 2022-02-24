package manual;

import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;
import models.game2048.Game2048;
import models.grid2048.KeyEvent;
import models.game2048.KeyEventHandler;
import models.game2048.Scoreboard;
import models.grid2048.Grid2048;
import models.square.Square;
import playgame.PlayGame;

import java.awt.Color;

public class PlayGameManual extends World implements PlayGame {
    private final Grid2048 grid;
    private final Scoreboard scoreboard;

    public PlayGameManual(Grid2048 grid, Scoreboard scoreboard) {
        this.grid = grid;
        this.scoreboard = scoreboard;
    }

    public static void main (String[] args) {
        PlayGameManual g = new PlayGameManual(new Grid2048(), new Scoreboard(0));
        g.bigBang(Square.SIDE_LENGTH * WINDOW_SIZE,Square.SIDE_LENGTH * WINDOW_SIZE,MANUAL_SPEED);
    }

    @Override
    public WorldScene makeScene() {
        WorldScene s = new WorldScene(Square.SIDE_LENGTH * WINDOW_SIZE, Square.SIDE_LENGTH * WINDOW_SIZE);
        return s.placeImageXY(grid.drawGrid(), Square.SIDE_LENGTH * WINDOW_SIZE/2, Square.SIDE_LENGTH * WINDOW_SIZE/2)
                .placeImageXY(scoreboard.drawScoreboard(), Scoreboard.WIDTH/2 + SCOREBOARD_POSN_OFFSET , Scoreboard.HEIGHT);
    }

    @Override
    public WorldScene lastScene (String msg) {
        return this.makeScene().placeImageXY(new TextImage(msg, WINDOW_TEXT_SIZE, Color.BLACK), Square.SIDE_LENGTH * WINDOW_SIZE/2, Square.SIDE_LENGTH * WINDOW_SIZE/2);
    }

    @Override
    public WorldEnd worldEnds() {
        if (this.isGameOver()) {
            return new WorldEnd(true, this.lastScene("You Lose!"));
        } else {
            return new WorldEnd(false, this.makeScene());
        }
    }

    @Override
    public World onKeyEvent(String s) {
        KeyEventHandler handler = grid.handleKeyEventWithRandomTile(KeyEvent.from(s), scoreboard);
        return new PlayGameManual(handler.getGrid2048(), handler.getScoreboard());
    }

    boolean isGameOver () {
        Grid2048 gridUp = grid.handleKeyEventWithRandomTile(KeyEvent.UP, scoreboard).getGrid2048();
        Grid2048 gridDown = grid.handleKeyEventWithRandomTile(KeyEvent.DOWN, scoreboard).getGrid2048();
        Grid2048 gridRight = grid.handleKeyEventWithRandomTile(KeyEvent.RIGHT, scoreboard).getGrid2048();
        Grid2048 gridLeft = grid.handleKeyEventWithRandomTile(KeyEvent.LEFT, scoreboard).getGrid2048();

        return  gridUp.equals(grid)
                && gridDown.equals(grid)
                && gridRight.equals(grid)
                && gridLeft.equals(grid);
    }
}

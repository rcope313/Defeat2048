package player;

import heuristic.GameHeuristic;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;
import models.game.KeyEvent;
import models.game.KeyEventHandler;
import models.game.Scoreboard;
import models.game.Grid2048;
import models.square.Square;

import java.awt.Color;

public class Player extends World  {
    int WINDOW_TEXT_SIZE = 100;
    private final Grid2048 grid;
    private final Scoreboard scoreboard;
    private GameHeuristic heuristic;
    private final static int WINDOW_SIZE = 6;
    private final static int MANUAL_SPEED = 1;
    private final static double HEURISTIC_SPEED = .1;
    private final static int SCOREBOARD_POSN_OFFSET = 10;

    public Player(Grid2048 grid, Scoreboard scoreboard) {
        this.grid = grid;
        this.scoreboard = scoreboard;
    }

    public Player(Grid2048 grid, Scoreboard scoreboard, GameHeuristic heuristic) {
        this.grid = grid;
        this.scoreboard = scoreboard;
        this.heuristic = heuristic;
    }

    public static void main (String[] args) {
        Player player = new Player(new Grid2048(), new Scoreboard(0));
        if (player.heuristic == null) {
            player.bigBang(Square.SIDE_LENGTH * WINDOW_SIZE, Square.SIDE_LENGTH * WINDOW_SIZE, MANUAL_SPEED);
        } else {
            player.bigBang(Square.SIDE_LENGTH * WINDOW_SIZE, Square.SIDE_LENGTH * WINDOW_SIZE, HEURISTIC_SPEED);
        }
    }

    @Override
    public WorldScene makeScene() {
        WorldScene s = new WorldScene(Square.SIDE_LENGTH * WINDOW_SIZE, Square.SIDE_LENGTH * WINDOW_SIZE);
        return s.placeImageXY(grid.drawGrid(), Square.SIDE_LENGTH * WINDOW_SIZE/2, Square.SIDE_LENGTH * WINDOW_SIZE/2)
                .placeImageXY(scoreboard.drawScoreboard(), Scoreboard.WIDTH/2 + SCOREBOARD_POSN_OFFSET , Scoreboard.HEIGHT);
    }

    @Override
    public World onTick() {
        if (heuristic == null) {
            return this;
        }
        KeyEventHandler handler = heuristic.evaluateNextGameState(grid, scoreboard);
        Grid2048.addRandomTileOnKeyEventHandler(handler);
        return new Player(handler.getGrid2048(), handler.getScoreboard(), heuristic);
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
        if (heuristic != null) {
            return this;
        }
        KeyEventHandler handler = grid.handleKeyEvent(KeyEvent.from(s), scoreboard);
        Grid2048.addRandomTileOnKeyEventHandler(handler);
        return new Player(handler.getGrid2048(), handler.getScoreboard());
    }

    boolean isGameOver () {
        KeyEventHandler upHandler = grid.handleKeyEvent(KeyEvent.UP, scoreboard);
        KeyEventHandler downHandler = grid.handleKeyEvent(KeyEvent.DOWN, scoreboard);
        KeyEventHandler leftHandler = grid.handleKeyEvent(KeyEvent.RIGHT, scoreboard);
        KeyEventHandler rightHandler = grid.handleKeyEvent(KeyEvent.LEFT, scoreboard);

        return  !upHandler.isTilesMoved() &&
                !downHandler.isTilesMoved() &&
                !leftHandler.isTilesMoved() &&
                !rightHandler.isTilesMoved();
    }
}

package player;

import heuristic.GameHeuristic;
import heuristic.PreferUpHeuristic;
import heuristic.SnakeHeuristic;
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
        Player player = new Player(new Grid2048(), new Scoreboard(0), new SnakeHeuristic());
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
        Grid2048.createRandomTileOnKeyEventHandler(handler);
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
        KeyEventHandler handler = grid.handleKeyEventWithRandomTile(KeyEvent.from(s), scoreboard);
        return new Player(handler.getGrid2048(), handler.getScoreboard());
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

package player;

import heuristic.GameHeuristic;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;
import models.game.KeyEvent;
import models.game.KeyEventHandler;
import models.game.Scoreboard;
import models.game.Grid2048;
import models.square.EmptySquare;
import models.square.Square;
import models.square.Tile;

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
        Square[] rowEmpty0 = new Square[]{
                new Tile(4, new Posn(0, 0)),
                new EmptySquare(new Posn(1, 0)),
                new EmptySquare(new Posn(2, 0)),
                new EmptySquare(new Posn(3, 0))};
        Square[] rowEmpty1 = new Square[]{
                new Tile(4, new Posn(0, 1)),
                new Tile(8, new Posn(1, 1)),
                new EmptySquare(new Posn(2, 1)),
                new EmptySquare(new Posn(3, 1))};
        Square[] rowEmpty2 = new Square[]{
                new Tile(2, new Posn(0, 2)),
                new EmptySquare(new Posn(1, 2)),
                new EmptySquare(new Posn(2, 2)),
                new EmptySquare(new Posn(3, 2))};
        Square[] rowEmpty3 = new Square[]{
                new Tile(2, new Posn(0, 3)),
                new EmptySquare(new Posn(1, 3)),
                new EmptySquare(new Posn(2, 3)),
                new EmptySquare(new Posn(3, 3))};

        Grid2048 g0 = new Grid2048(new Square[][]{rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3});

        Player player = new Player(g0, new Scoreboard(0));
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

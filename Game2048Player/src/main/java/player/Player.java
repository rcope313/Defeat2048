package player;

import heuristic.GameHeuristic;
import heuristic.PreferUpHeuristic;
import heuristic.SnakeAndWorstCaseHeuristic;
import heuristic.SnakeHeuristic;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.FontStyle;
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

public class Player extends World {
    int WINDOW_TEXT_SIZE = 100;
    private final Grid2048 grid;
    private final Scoreboard scoreboard;
    private GameHeuristic heuristic;
    private final static int WINDOW_SIZE = 6;
    private final static int MANUAL_SPEED = 1;
    private final static double HEURISTIC_SPEED = .1;
    private final static int SCOREBOARD_POSN_OFFSET = 10;
    private final static int TREE_DEPTH = 3;

    public Player(Grid2048 grid, Scoreboard scoreboard) {
        this.grid = grid;
        this.scoreboard = scoreboard;
    }

    public Player(Grid2048 grid, Scoreboard scoreboard, GameHeuristic heuristic) {
        this.grid = grid;
        this.scoreboard = scoreboard;
        this.heuristic = heuristic;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            Player player = new Player(new Grid2048(), new Scoreboard(0));
            player.bigBang(Square.SIDE_LENGTH * WINDOW_SIZE, Square.SIDE_LENGTH * WINDOW_SIZE, MANUAL_SPEED);
        }
        if (args.length == 1) {
            Player player = new Player(new Grid2048(), new Scoreboard(0), getHeuristicByStringName(args[0]));
            player.bigBang(Square.SIDE_LENGTH * WINDOW_SIZE, Square.SIDE_LENGTH * WINDOW_SIZE, HEURISTIC_SPEED);
        }
        else {
            throw new IllegalArgumentException("Expecting 0 or 1 argument. Please see read me for proper play game execution");
        }
    }

    @Override
    public WorldScene makeScene() {
        WorldScene s = new WorldScene(Square.SIDE_LENGTH * WINDOW_SIZE, Square.SIDE_LENGTH * WINDOW_SIZE);
        TextImage heuristicText = new TextImage("Heuristic: " + heuristic.getHeuristicName(), 20, FontStyle.BOLD, Color.BLACK);
        if (heuristic != null) {
            return s.placeImageXY(grid.drawGrid(), Square.SIDE_LENGTH * WINDOW_SIZE / 2, Square.SIDE_LENGTH * WINDOW_SIZE / 2)
                    .placeImageXY(scoreboard.drawScoreboard(), Scoreboard.WIDTH / 2 + SCOREBOARD_POSN_OFFSET, Scoreboard.HEIGHT)
                    .placeImageXY(heuristicText, WINDOW_TEXT_SIZE + SCOREBOARD_POSN_OFFSET * 4, Square.SIDE_LENGTH * WINDOW_SIZE - Scoreboard.HEIGHT);
        } else {
            return s.placeImageXY(grid.drawGrid(), Square.SIDE_LENGTH * WINDOW_SIZE / 2, Square.SIDE_LENGTH * WINDOW_SIZE / 2)
                    .placeImageXY(scoreboard.drawScoreboard(), WINDOW_TEXT_SIZE + SCOREBOARD_POSN_OFFSET, Scoreboard.HEIGHT);
        }
    }

    @Override
    public World onTick() {
        if (heuristic == null) {
            return this;
        }
        KeyEventHandler handler = heuristic.getNextMove(TREE_DEPTH, new KeyEventHandler(false, grid, scoreboard));
        Grid2048.addRandomTileOnKeyEventHandler(handler);
        return new Player(handler.getGrid2048(), handler.getScoreboard(), heuristic);
    }

    @Override
    public WorldScene lastScene(String msg) {
        return this.makeScene().placeImageXY(new TextImage(msg, WINDOW_TEXT_SIZE, Color.BLACK), Square.SIDE_LENGTH * WINDOW_SIZE / 2, Square.SIDE_LENGTH * WINDOW_SIZE / 2);
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

    boolean isGameOver() {
        KeyEventHandler upHandler = grid.handleKeyEvent(KeyEvent.UP, scoreboard);
        KeyEventHandler downHandler = grid.handleKeyEvent(KeyEvent.DOWN, scoreboard);
        KeyEventHandler leftHandler = grid.handleKeyEvent(KeyEvent.RIGHT, scoreboard);
        KeyEventHandler rightHandler = grid.handleKeyEvent(KeyEvent.LEFT, scoreboard);

        return !upHandler.isTilesMoved() &&
                !downHandler.isTilesMoved() &&
                !leftHandler.isTilesMoved() &&
                !rightHandler.isTilesMoved();
    }

    private static GameHeuristic getHeuristicByStringName(String str) {
        if (str.equals("Prefer Up Heuristic")) {
            return new PreferUpHeuristic();
        }
        if (str.equals("Snake Heuristic")) {
            return new SnakeHeuristic();
        }
        if (str.equals("Snake and Worst Case Heuristic")) {
            return new SnakeAndWorstCaseHeuristic();
        } else {
            throw new IllegalArgumentException("Not a valid heuristic. Check read me for all available heuristics and their respective spelling");
        }
    }
}

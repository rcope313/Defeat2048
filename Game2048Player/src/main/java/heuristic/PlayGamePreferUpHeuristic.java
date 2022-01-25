package heuristic;

import models.game2048.Game2048;
import models.game2048.KeyEventHandler;
import models.game2048.Scoreboard;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;
import models.grid2048.Grid2048;
import models.square.Square;
import playgame.PlayGame;
import java.awt.*;

public class PlayGamePreferUpHeuristic extends World implements PlayGame {
    private final Game2048 game2048;
    public final PreferUpHeuristic heuristic;

    public PlayGamePreferUpHeuristic(Game2048 game2048, PreferUpHeuristic heuristic) {
        this.game2048 = game2048;
        this.heuristic = heuristic;
    }

    public static void main (String[] args) {
        Game2048 newGame = new Game2048(new Grid2048(), new Scoreboard(0));
        PlayGamePreferUpHeuristic g = new PlayGamePreferUpHeuristic(newGame, new PreferUpHeuristic(newGame.getGrid2048(), newGame.getScoreboard()));
        g.bigBang(Square.SIDE_LENGTH * WINDOW_SIZE,Square.SIDE_LENGTH * WINDOW_SIZE, HEURISTIC_SPEED);
    }

    @Override
    public WorldScene makeScene() {
        WorldScene s = new WorldScene(Square.SIDE_LENGTH * WINDOW_SIZE, Square.SIDE_LENGTH * WINDOW_SIZE);
        return s.placeImageXY(this.getGame2048().getGrid2048().drawGrid(), Square.SIDE_LENGTH * WINDOW_SIZE/2, Square.SIDE_LENGTH * WINDOW_SIZE/2)
                .placeImageXY(this.getGame2048().getScoreboard().drawScoreboard(), Scoreboard.WIDTH/2 + SCOREBOARD_POSN_OFFSET , Scoreboard.HEIGHT);
    }

    @Override
    public World onTick() {
        KeyEventHandler nextGameState = heuristic.evaluateNextGameState();
        Grid2048.createRandomTileOnKeyEventHandlerGrid2048(nextGameState);
        Game2048 newGame2048 = new Game2048(nextGameState.getGrid2048(), nextGameState.getScoreboard());
        PreferUpHeuristic newHeuristic = new PreferUpHeuristic(nextGameState.getGrid2048(), nextGameState.getScoreboard());
        return new PlayGamePreferUpHeuristic(newGame2048, newHeuristic);
    }

    @Override
    public WorldScene lastScene(String s) {
        return this.makeScene()
                .placeImageXY(new TextImage(s, WINDOW_TEXT_SIZE, Color.BLACK), Square.SIDE_LENGTH * WINDOW_SIZE/2, Square.SIDE_LENGTH * WINDOW_SIZE/2);
    }

    @Override
    public WorldEnd worldEnds() {
        if (this.isGameOver()) {
            return new WorldEnd(true, this.lastScene("You Lose!"));
        } else {
            return new WorldEnd(false, this.makeScene());
        }
    }

    boolean isGameOver () {
        Grid2048 currentGrid = game2048.getGrid2048();
        Grid2048 gridUp = currentGrid.handleUpEvent(game2048.getScoreboard(), true).getGrid2048();
        Grid2048 gridDown = currentGrid.handleDownEvent(game2048.getScoreboard(), true).getGrid2048();
        Grid2048 gridRight = currentGrid.handleRightEvent(game2048.getScoreboard(), true).getGrid2048();
        Grid2048 gridLeft = currentGrid.handleLeftEvent(game2048.getScoreboard(), true).getGrid2048();

        return  gridUp.equals(currentGrid)
                && gridDown.equals(currentGrid)
                && gridRight.equals(currentGrid)
                && gridLeft.equals(currentGrid);
    }

    public Game2048 getGame2048() {
        return game2048;
    }
}

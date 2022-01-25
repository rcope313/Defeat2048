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
import java.awt.*;


public class PlayGameHeuristic extends World {
    private final Game2048 game2048;
    public final GameHeuristic heuristic;

    public PlayGameHeuristic(Game2048 game2048, GameHeuristic heuristic) {
        this.game2048 = game2048;
        this.heuristic = heuristic;
    }

    public static void main (String[] args) {
        Game2048 newGame = new Game2048(new Grid2048(), new Scoreboard(0));
        PlayGameHeuristic g = new PlayGameHeuristic(newGame, new PreferUpHeuristic(newGame.getGrid2048(), newGame.getScoreboard()));
        g.bigBang(Square.SIDE_LENGTH * 6,Square.SIDE_LENGTH * 6,.25);
    }

    @Override
    public WorldScene makeScene() {
        WorldScene s = new WorldScene(Square.SIDE_LENGTH * 6, Square.SIDE_LENGTH * 6);
        return s.placeImageXY(this.getGame2048().getGrid2048().drawGrid(), Square.SIDE_LENGTH * 3, Square.SIDE_LENGTH * 3)
                .placeImageXY(this.getGame2048().getScoreboard().drawScoreboard(), Scoreboard.WIDTH/2 + 10 , Scoreboard.HEIGHT);
    }

    @Override
    public World onTick() {
        KeyEventHandler nextGameState = heuristic.evaluateNextGameState();
        Grid2048.createRandomTileOnKeyEventHandlerGrid2048(nextGameState);
        Game2048 newGame2048 = new Game2048(nextGameState.getGrid2048(), nextGameState.getScoreboard());
        PreferUpHeuristic newHeuristic = new PreferUpHeuristic(nextGameState.getGrid2048(), nextGameState.getScoreboard());
        return new PlayGameHeuristic(newGame2048, newHeuristic);
    }

    @Override
    public WorldScene lastScene(String s) {
        return this.makeScene()
                .placeImageXY(new TextImage(s, 100, Color.BLACK), Square.SIDE_LENGTH * 3, Square.SIDE_LENGTH * 3);
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

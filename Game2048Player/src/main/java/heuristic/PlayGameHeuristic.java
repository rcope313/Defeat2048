package heuristic;

import game2048.Game2048;
import game2048.KeyEventHandler;
import game2048.Scoreboard;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;
import models.board2048.Board2048;
import models.square.Square;

import java.awt.*;

public class PlayGameHeuristic extends World {
    public final Game2048 game2048;
    public final GameHeuristic heuristic;

    public PlayGameHeuristic(Game2048 game2048, GameHeuristic heuristic) {
        this.game2048 = game2048;
        this.heuristic = heuristic;
    }

    public static void main (String[] args) {

        Game2048 game2048 = new Game2048(new Board2048().initializeStartingGrid(), new Scoreboard(0));
        GameHeuristic h = new PreferUpHeuristic(game2048);
        PlayGameHeuristic g = new PlayGameHeuristic(game2048, h);
        g.bigBang(Square.SIDE_LENGTH * 6,Square.SIDE_LENGTH * 6,.25);
    }

    @Override
    public WorldScene makeScene() {
        WorldScene s = new WorldScene(Square.SIDE_LENGTH * 6, Square.SIDE_LENGTH * 6);
        return s.placeImageXY(this.getGame2048().getBoard2048().drawGrid(), Square.SIDE_LENGTH * 3, Square.SIDE_LENGTH * 3)
                .placeImageXY(this.getGame2048().getScoreboard().drawScoreboard(), Scoreboard.WIDTH/2 + 10 , Scoreboard.HEIGHT);
    }

    @Override
    public World onTick() {
        KeyEventHandler nextGameState =  this.getHeuristic().evaluateNextGameState();
        Game2048.addRandomTileOnKeyEventHandler(nextGameState);

        GameHeuristic h = new PreferUpHeuristic(nextGameState.getUpdatedGame2048());
        return new PlayGameHeuristic(nextGameState.getUpdatedGame2048(), h);
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

        Board2048 gridUp = this.getGame2048().handleUpEvent().getUpdatedGame2048().getBoard2048();
        Board2048 gridDown = this.getGame2048().handleDownEvent().getUpdatedGame2048().getBoard2048();
        Board2048 gridRight = this.getGame2048().handleRightEvent().getUpdatedGame2048().getBoard2048();
        Board2048 gridLeft = this.getGame2048().handleLeftEvent().getUpdatedGame2048().getBoard2048();
        Board2048 currentBoard = this.getGame2048().getBoard2048();

        return
                gridUp.equals(currentBoard) &&
                        gridDown.equals(currentBoard) &&
                        gridRight.equals(currentBoard) &&
                        gridLeft.equals(currentBoard);
    }

    public Game2048 getGame2048() {
        return game2048;
    }

    public GameHeuristic getHeuristic() {
        return heuristic;
    }
}

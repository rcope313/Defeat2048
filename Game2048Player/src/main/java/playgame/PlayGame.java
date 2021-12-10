package playgame;

import javalib.funworld.*;
import javalib.worldimages.*;
import game2048.Game2048;
import game2048.Scoreboard;
import models.board2048.Board2048;
import models.square.Square;
import java.awt.*;

public class PlayGame extends World {
    public Game2048 game2048;

    public PlayGame() {}

    public static void main (String[] args) {
        PlayGame g = new PlayGame();
        Game2048 game2048 = new Game2048(new Board2048().initializeStartingGrid(), new Scoreboard(0));
        g.setGame2048(game2048);
        g.bigBang(Square.SIDE_LENGTH * 6,Square.SIDE_LENGTH * 6,1);
    }

    @Override
    public WorldScene makeScene() {
        WorldScene s = new WorldScene(Square.SIDE_LENGTH * 6, Square.SIDE_LENGTH * 6);
        return s.placeImageXY(this.getGame2048().getBoard2048().drawGrid(), Square.SIDE_LENGTH * 3, Square.SIDE_LENGTH * 3)
                .placeImageXY(this.getGame2048().getScoreboard().drawScoreboard(), Scoreboard.WIDTH/2 + 10 , Scoreboard.HEIGHT);
    }

    @Override
    public WorldScene lastScene (String msg) {
        return this.makeScene().placeImageXY(new TextImage(msg, 100, Color.BLACK), Square.SIDE_LENGTH * 3, Square.SIDE_LENGTH * 3);
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

        PlayGame updatedPlayGame = new PlayGame();
        Game2048 updatedGame2048;

        if (s.equals("up")) {
            updatedGame2048 = this.getGame2048().handleUpEvent().getUpdatedGame2048();
            updatedPlayGame.setGame2048(updatedGame2048);
            return updatedPlayGame;
        }
        if (s.equals("down")) {
            updatedGame2048 = this.getGame2048().handleDownEvent().getUpdatedGame2048();
            updatedPlayGame.setGame2048(updatedGame2048);
            return updatedPlayGame;
        }
        if (s.equals("left")) {
            updatedGame2048 = this.getGame2048().handleLeftEvent().getUpdatedGame2048();
            updatedPlayGame.setGame2048(updatedGame2048);
            return updatedPlayGame;
        }
        if (s.equals("right")) {
            updatedGame2048 = this.getGame2048().handleRightEvent().getUpdatedGame2048();
            updatedPlayGame.setGame2048(updatedGame2048);
            return updatedPlayGame;

        } else {
            return this; }

    }

    boolean isGameOver () {

        Board2048 gridUp = this.getGame2048().handleUpEvent().getUpdatedGame2048().getBoard2048();
        Board2048 gridDown = this.getGame2048().handleDownEvent().getUpdatedGame2048().getBoard2048();
        Board2048 gridRight = this.getGame2048().handleRightEvent().getUpdatedGame2048().getBoard2048();
        Board2048 gridLeft = this.getGame2048().handleLeftEvent().getUpdatedGame2048().getBoard2048();
        Board2048 currentGrid = this.getGame2048().getBoard2048();

        return
                gridUp.isSameBoard(currentGrid) &&
                        gridDown.isSameBoard(currentGrid) &&
                        gridRight.isSameBoard(currentGrid) &&
                        gridLeft.isSameBoard(currentGrid);
    }


    public Game2048 getGame2048() {
        return game2048;
    }

    public void setGame2048(Game2048 game2048) {
        this.game2048 = game2048;
    }
}
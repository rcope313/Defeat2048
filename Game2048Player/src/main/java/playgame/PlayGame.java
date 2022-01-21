package playgame;

import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;
import models.game2048.Game2048;
import models.game2048.KeyEventHandler;
import models.game2048.Scoreboard;
import models.grid2048.Grid2048;
import models.square.Square;
import java.awt.Color;

public class PlayGame extends World {
    public Game2048 game2048;

    public PlayGame() {}

    public PlayGame(Game2048 game2048) {
        this.game2048 = game2048;
    }

    public static void main (String[] args) {
        PlayGame g = new PlayGame();
        Game2048 game2048 = new Game2048();
        g.setGame2048(game2048);
        g.getGame2048().setGrid2048(new Grid2048());
        g.getGame2048().setScoreboard(new Scoreboard(0));
        g.bigBang(Square.SIDE_LENGTH * 6,Square.SIDE_LENGTH * 6,1);
    }

    @Override
    public WorldScene makeScene() {
        WorldScene s = new WorldScene(Square.SIDE_LENGTH * 6, Square.SIDE_LENGTH * 6);
        return s.placeImageXY(this.getGame2048().getGrid2048().drawGrid(), Square.SIDE_LENGTH * 3, Square.SIDE_LENGTH * 3)
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
        if (s.equals("left")) {
            KeyEventHandler handler = game2048.getGrid2048().handleLeftEvent(game2048.getScoreboard());
            return new PlayGame(new Game2048(handler.getGrid2048(), handler.getScoreboard()));
        }
        if (s.equals("right")) {
            KeyEventHandler handler = game2048.getGrid2048().handleRightEvent(game2048.getScoreboard());
            return new PlayGame(new Game2048(handler.getGrid2048(), handler.getScoreboard()));
        }
        if (s.equals("down")) {
            KeyEventHandler handler = game2048.getGrid2048().handleDownEvent(game2048.getScoreboard());
            return new PlayGame(new Game2048(handler.getGrid2048(), handler.getScoreboard()));
        }
        if (s.equals("up")) {
            KeyEventHandler handler = game2048.getGrid2048().handleUpEvent(game2048.getScoreboard());
            return new PlayGame(new Game2048(handler.getGrid2048(), handler.getScoreboard()));
        } else {
            return this; }
    }

    boolean isGameOver () {
        Grid2048 gridUp = game2048.getGrid2048().handleUpEvent(game2048.getScoreboard())
                .getResultGame2048().getGrid2048();
        Grid2048 gridDown = game2048.getGrid2048().handleDownEvent(game2048.getScoreboard())
                .getResultGame2048().getGrid2048();
        Grid2048 gridRight = game2048.getGrid2048().handleRightEvent(game2048.getScoreboard())
                .getResultGame2048().getGrid2048();
        Grid2048 gridLeft = game2048.getGrid2048().handleLeftEvent(game2048.getScoreboard())
                .getResultGame2048().getGrid2048();
        Grid2048 currentGrid = this.getGame2048().getGrid2048();

        return  gridUp.equals(currentGrid)
                && gridDown.equals(currentGrid)
                && gridRight.equals(currentGrid)
                && gridLeft.equals(currentGrid);
    }

    public Game2048 getGame2048() {
        return game2048;
    }

    public void setGame2048(Game2048 game2048) {
        this.game2048 = game2048;
    }
}

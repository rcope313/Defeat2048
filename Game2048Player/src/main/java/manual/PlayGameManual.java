package manual;

import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;
import models.game2048.Game2048;
import models.grid2048.Grid2048Event;
import models.game2048.KeyEventHandler;
import models.game2048.Scoreboard;
import models.grid2048.Grid2048;
import models.square.Square;
import java.awt.Color;

public class PlayGameManual extends World {
    private final Game2048 game2048;

    public PlayGameManual(Game2048 game2048) {
        this.game2048 = game2048;
    }

    public static void main (String[] args) {
        Game2048 newGame = new Game2048(new Grid2048(), new Scoreboard(0));
        PlayGameManual g = new PlayGameManual(newGame);
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
            KeyEventHandler handler = Grid2048Event.LEFT.buildKeyEventHandlerOnKeyEvent(game2048.getGrid2048(), game2048.getScoreboard());
            return new PlayGameManual(new Game2048(handler.getGrid2048(), handler.getScoreboard()));
        }
        if (s.equals("right")) {
            KeyEventHandler handler = Grid2048Event.RIGHT.buildKeyEventHandlerOnKeyEvent(game2048.getGrid2048(), game2048.getScoreboard());
            return new PlayGameManual(new Game2048(handler.getGrid2048(), handler.getScoreboard()));
        }
        if (s.equals("down")) {
            KeyEventHandler handler = Grid2048Event.DOWN.buildKeyEventHandlerOnKeyEvent(game2048.getGrid2048(), game2048.getScoreboard());
            return new PlayGameManual(new Game2048(handler.getGrid2048(), handler.getScoreboard()));
        }
        if (s.equals("up")) {
            KeyEventHandler handler = Grid2048Event.UP.buildKeyEventHandlerOnKeyEvent(game2048.getGrid2048(), game2048.getScoreboard());
            return new PlayGameManual(new Game2048(handler.getGrid2048(), handler.getScoreboard()));
        } else {
            return this;
        }
    }

    boolean isGameOver () {
        Grid2048 gridUp = Grid2048Event.UP.buildKeyEventHandlerOnKeyEvent(game2048.getGrid2048(), game2048.getScoreboard()).getGrid2048();
        Grid2048 gridDown = Grid2048Event.DOWN.buildKeyEventHandlerOnKeyEvent(game2048.getGrid2048(), game2048.getScoreboard()).getGrid2048();
        Grid2048 gridRight = Grid2048Event.RIGHT.buildKeyEventHandlerOnKeyEvent(game2048.getGrid2048(), game2048.getScoreboard()).getGrid2048();
        Grid2048 gridLeft = Grid2048Event.LEFT.buildKeyEventHandlerOnKeyEvent(game2048.getGrid2048(), game2048.getScoreboard()).getGrid2048();

        return  gridUp.equals(game2048.getGrid2048())
                && gridDown.equals(game2048.getGrid2048())
                && gridRight.equals(game2048.getGrid2048())
                && gridLeft.equals(game2048.getGrid2048());
    }

    public Game2048 getGame2048() {
        return game2048;
    }
}

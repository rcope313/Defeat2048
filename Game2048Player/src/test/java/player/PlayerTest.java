package player;

import javalib.worldimages.Posn;
import models.game.Scoreboard;
import models.game.Grid2048;
import models.square.EmptySquare;
import models.square.Square;
import models.square.Tile;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    Square[][] squareArrayGameOver, squareArrayGameContinuesWithUpOrLeft, squareArrayGameContinuesWithDownOrRight;
    Player gameOver, gameContinuesWithUpOrLeft, gameContinuesWithDownOrRight;

    void initData() {
        squareArrayGameOver = new Square[][]{
                new Square[]{
                        new Tile(4, new Posn(0, 0)),
                        new Tile(2, new Posn(0, 1)),
                        new Tile(4, new Posn(0, 2)),
                        new Tile(2, new Posn(0, 3))},
                new Square[]{
                        new Tile(2, new Posn(1, 0)),
                        new Tile(4, new Posn(1, 1)),
                        new Tile(2, new Posn(1, 2)),
                        new Tile(4, new Posn(1, 3))},
                new Square[]{
                        new Tile(4, new Posn(2, 0)),
                        new Tile(2, new Posn(2, 1)),
                        new Tile(4, new Posn(2, 2)),
                        new Tile(2, new Posn(2, 3))},
                new Square[]{
                        new Tile(2, new Posn(3, 0)),
                        new Tile(4, new Posn(3, 1)),
                        new Tile(2, new Posn(3, 2)),
                        new Tile(4, new Posn(3, 3))}
        };
        squareArrayGameContinuesWithUpOrLeft = new Square[][]{
                new Square[]{
                        new EmptySquare(new Posn(0, 0)),
                        new Tile(2, new Posn(0, 1)),
                        new Tile(4, new Posn(0, 2)),
                        new Tile(2, new Posn(0, 3))},
                new Square[]{
                        new Tile(2, new Posn(1, 0)),
                        new Tile(4, new Posn(1, 1)),
                        new Tile(2, new Posn(1, 2)),
                        new Tile(4, new Posn(1, 3))},
                new Square[]{
                        new Tile(4, new Posn(2, 0)),
                        new Tile(2, new Posn(2, 1)),
                        new Tile(4, new Posn(2, 2)),
                        new Tile(2, new Posn(2, 3))},
                new Square[]{
                        new Tile(2, new Posn(3, 0)),
                        new Tile(4, new Posn(3, 1)),
                        new Tile(2, new Posn(3, 2)),
                        new Tile(4, new Posn(3, 3))}
        };
        squareArrayGameContinuesWithDownOrRight = new Square[][]{
                new Square[]{
                        new Tile(4, new Posn(0, 0)),
                        new Tile(2, new Posn(0, 1)),
                        new Tile(4, new Posn(0, 2)),
                        new Tile(2, new Posn(0, 3))},
                new Square[]{
                        new Tile(2, new Posn(1, 0)),
                        new Tile(4, new Posn(1, 1)),
                        new Tile(2, new Posn(1, 2)),
                        new Tile(4, new Posn(1, 3))},
                new Square[]{
                        new Tile(4, new Posn(2, 0)),
                        new Tile(2, new Posn(2, 1)),
                        new Tile(4, new Posn(2, 2)),
                        new Tile(2, new Posn(2, 3))},
                new Square[]{
                        new Tile(2, new Posn(3, 0)),
                        new Tile(4, new Posn(3, 1)),
                        new Tile(2, new Posn(3, 2)),
                        new EmptySquare(new Posn(3, 3))}
        };
        gameOver = new Player(new Grid2048(squareArrayGameOver), new Scoreboard(0));
        gameContinuesWithUpOrLeft = new Player(new Grid2048(squareArrayGameContinuesWithUpOrLeft), new Scoreboard(0));
        gameContinuesWithDownOrRight = new Player(new Grid2048(squareArrayGameContinuesWithDownOrRight), new Scoreboard(0));

    }

    @Test
    public void itComparesEachAvailableKeyEventGrid2048StateForGameOverCondition() {
        this.initData();
        assertThat(gameOver.isGameOver()).isTrue();
        assertThat(gameContinuesWithUpOrLeft.isGameOver()).isFalse();
        assertThat(gameContinuesWithDownOrRight.isGameOver()).isFalse();
    }
}

package player;

import models.game.Scoreboard;
import models.game.Grid2048;
import models.square.EmptySquare;
import models.square.Square;
import models.square.Tile;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    Square[][]
            squareArrayGameOver, squareArrayGameContinuesWithUpOrLeft, squareArrayGameContinuesWithDownOrRight,
            squareArr0, squareArr1, squareArr2, squareArr3, squareArr4;
    Player gameOver, gameContinuesWithUpOrLeft, gameContinuesWithDownOrRight,
            player0, player1, player2, player3, player4;

    @Before
    public void initData() {
        squareArrayGameOver = new Square[][]{
                new Square[]{new Tile(4), new Tile(2), new Tile(4), new Tile(2)},
                new Square[]{new Tile(2), new Tile(4), new Tile(2), new Tile(4)},
                new Square[]{new Tile(4), new Tile(2), new Tile(4), new Tile(2)},
                new Square[]{new Tile(2), new Tile(4), new Tile(2), new Tile(4)}};
        gameOver = new Player(new Grid2048(squareArrayGameOver), new Scoreboard(0));

        squareArrayGameContinuesWithUpOrLeft = new Square[][]{
                new Square[]{new EmptySquare(), new Tile(2), new Tile(4), new Tile(2)},
                new Square[]{new Tile(2), new Tile(4), new Tile(2), new Tile(4)},
                new Square[]{new Tile(4), new Tile(2), new Tile(4), new Tile(2)},
                new Square[]{new Tile(2), new Tile(4), new Tile(2), new Tile(4)}};
        gameContinuesWithUpOrLeft = new Player(new Grid2048(squareArrayGameContinuesWithUpOrLeft), new Scoreboard(0));

        squareArrayGameContinuesWithDownOrRight = new Square[][]{
                new Square[]{new Tile(4), new Tile(2), new Tile(4), new Tile(2)},
                new Square[]{new Tile(2), new Tile(4), new Tile(2), new Tile(4)},
                new Square[]{new Tile(4), new Tile(2), new Tile(4), new Tile(2)},
                new Square[]{new Tile(2), new Tile(4), new Tile(2), new EmptySquare()}};
        gameContinuesWithDownOrRight = new Player(new Grid2048(squareArrayGameContinuesWithDownOrRight), new Scoreboard(0));

        squareArr0= new Square[][]{
                new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()},
                new Square[]{new EmptySquare(), new Tile(4), new Tile(4), new EmptySquare()},
                new Square[]{new EmptySquare(), new Tile(4), new Tile(4), new EmptySquare()},
                new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()}};
        player0 = new Player(new Grid2048(squareArr0), new Scoreboard(0));

        squareArr1= new Square[][]{
                new Square[]{new Tile(4), new Tile(4), new EmptySquare(), new EmptySquare()},
                new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()},
                new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()},
                new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()}};
        player1 = new Player(new Grid2048(squareArr1), new Scoreboard(0));

        squareArr2= new Square[][]{
                new Square[]{new EmptySquare(), new EmptySquare(), new Tile(4), new Tile(4)},
                new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()},
                new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()},
                new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()}};
        player2 = new Player(new Grid2048(squareArr2), new Scoreboard(0));

        squareArr3= new Square[][]{
                new Square[]{new Tile(4), new EmptySquare(), new EmptySquare(), new EmptySquare()},
                new Square[]{new Tile(4), new EmptySquare(), new EmptySquare(), new EmptySquare()},
                new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()},
                new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()}};
        player3 = new Player(new Grid2048(squareArr3), new Scoreboard(0));

        squareArr4= new Square[][]{
                new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()},
                new Square[]{new EmptySquare(), new EmptySquare(), new EmptySquare(), new EmptySquare()},
                new Square[]{new Tile(4), new EmptySquare(), new EmptySquare(), new EmptySquare()},
                new Square[]{new Tile(4), new EmptySquare(), new EmptySquare(), new EmptySquare()}};
        player4 = new Player(new Grid2048(squareArr4), new Scoreboard(0));
    }

    @Test
    public void itAddsRandomTileWhenTileStackAndWhenTilesMove() {
        Player player0Left = (Player) player0.onKeyEvent("left");
        Player player0Right = (Player) player0.onKeyEvent("right");
        Player player0Up = (Player) player0.onKeyEvent("up");
        Player player0Down = (Player) player0.onKeyEvent("down");

        assertThat(player0Left.getGrid().getEmptyTilePosns().size()).isEqualTo(13);
        assertThat(player0Right.getGrid().getEmptyTilePosns().size()).isEqualTo(13);
        assertThat(player0Up.getGrid().getEmptyTilePosns().size()).isEqualTo(13);
        assertThat(player0Down.getGrid().getEmptyTilePosns().size()).isEqualTo(13);
    }

    @Test
    public void itAddsRandomTileWhenTileStackOnly() {
        Player playerLeft= (Player) player1.onKeyEvent("left");
        Player playerRight = (Player) player2.onKeyEvent("right");
        Player playerUp = (Player) player3.onKeyEvent("up");
        Player playerDown = (Player) player4.onKeyEvent("down");

        assertThat(playerLeft.getGrid().getEmptyTilePosns().size()).isEqualTo(14);
        assertThat(playerRight.getGrid().getEmptyTilePosns().size()).isEqualTo(14);
        assertThat(playerUp.getGrid().getEmptyTilePosns().size()).isEqualTo(14);
        assertThat(playerDown.getGrid().getEmptyTilePosns().size()).isEqualTo(14);
    }

    @Test
    public void itComparesEachAvailableKeyEventGrid2048StateForGameOverCondition() {
        assertThat(gameOver.isGameOver()).isTrue();
        assertThat(gameContinuesWithUpOrLeft.isGameOver()).isFalse();
        assertThat(gameContinuesWithDownOrRight.isGameOver()).isFalse();
    }


}

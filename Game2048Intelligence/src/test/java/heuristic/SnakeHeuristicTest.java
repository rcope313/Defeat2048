package heuristic;

import game2048.Game2048;
import game2048.Scoreboard;
import javalib.worldimages.Posn;
import models.grid2048.Board2048;
import models.square.EmptyTile;
import models.square.Square;
import models.square.Tile;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SnakeHeuristicTest {

    Square[]
            rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3,
            row0, row1, row2, row3,
            row4, row5, row6, row7;
    Board2048 b0, b1, b2;
    Game2048 g0, g1, g2;
    GameHeuristic gh0, gh1, gh2;

    void initData() {
        rowEmpty0 = new Square[]{
                new EmptyTile(new Posn(0, 0)),
                new EmptyTile(new Posn(0, 1)),
                new EmptyTile(new Posn(0, 2)),
                new EmptyTile(new Posn(0, 3))};
        rowEmpty1 = new Square[]{
                new EmptyTile(new Posn(1, 0)),
                new EmptyTile(new Posn(1, 1)),
                new EmptyTile(new Posn(1, 2)),
                new EmptyTile(new Posn(1, 3))};
        rowEmpty2 = new Square[]{
                new EmptyTile(new Posn(2, 0)),
                new EmptyTile(new Posn(2, 1)),
                new EmptyTile(new Posn(2, 2)),
                new EmptyTile(new Posn(2, 3))};
        rowEmpty3 = new Square[]{
                new EmptyTile(new Posn(3, 0)),
                new EmptyTile(new Posn(3, 1)),
                new EmptyTile(new Posn(3, 2)),
                new EmptyTile(new Posn(3, 3))};

        b0 = new Board2048(new Square[][]{rowEmpty0, rowEmpty1, rowEmpty2, rowEmpty3});
        g0 = new Game2048(b0, new Scoreboard(0));
        gh0 = new PreferUpHeuristic(g0);

        row0 = new Square[]{
                new Tile(2, new Posn(0, 0)),
                new Tile(2, new Posn(0, 1)),
                new Tile(2, new Posn(0, 2)),
                new Tile(2, new Posn(0, 3))};
        row1 = new Square[]{
                new Tile(2, new Posn(1, 0)),
                new EmptyTile(new Posn(1, 1)),
                new EmptyTile(new Posn(1, 2)),
                new Tile(2, new Posn(1, 3))};
        row2 = new Square[]{
                new Tile(2, new Posn(2, 0)),
                new EmptyTile(new Posn(2, 1)),
                new EmptyTile(new Posn(2, 2)),
                new Tile(2, new Posn(2, 3))};
        row3 = new Square[]{
                new Tile(2, new Posn(3, 0)),
                new Tile(2, new Posn(3, 1)),
                new Tile(2, new Posn(3, 2)),
                new Tile(2, new Posn(3, 3))};

        b1 = new Board2048(new Square[][]{row0, row1, row2, row3});
        g1 = new Game2048(b1, new Scoreboard(0));
        gh1 = new SnakeHeuristic(g1);

        row4 = new Square[]{
                new Tile(2, new Posn(0, 0)),
                new Tile(2, new Posn(0, 1)),
                new Tile(2, new Posn(0, 2)),
                new Tile(2, new Posn(0, 3))};
        row5 = new Square[]{
                new Tile(2, new Posn(1, 0)),
                new Tile(2, new Posn(1, 1)),
                new Tile(2, new Posn(1, 2)),
                new Tile(2, new Posn(1, 3))};
        row6 = new Square[]{
                new Tile(2, new Posn(2, 0)),
                new Tile(2, new Posn(2, 1)),
                new Tile(2, new Posn(2, 2)),
                new Tile(2, new Posn(2, 3))};
        row7 = new Square[]{
                new Tile(2, new Posn(3, 0)),
                new Tile(2, new Posn(3, 1)),
                new Tile(2, new Posn(3, 2)),
                new Tile(2, new Posn(3, 3))};

        b2 = new Board2048(new Square[][]{row4, row5, row6, row7});
        g2 = new Game2048(b2, new Scoreboard(0));
        gh2 = new SnakeHeuristic(g2);
    }


    @Test
    public void testGameHeuristicClassFields() {

    }
    
    @Test
    public void testEvaluateGameStateHeuristicScore() {
        this.initData();
        assertThat(gh0.evaluateGameStateHeuristicScore(gh0.getGame2048())).isEqualTo(0);
        assertThat(gh1.evaluateGameStateHeuristicScore(gh1.getGame2048())).isEqualTo(3417180);
        assertThat(gh2.evaluateGameStateHeuristicScore(gh2.getGame2048())).isEqualTo(3434350);
    }
}

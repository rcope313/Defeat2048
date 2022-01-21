package models.game2048;

import models.grid2048.Grid2048;

public class KeyEventHandler {

    Game2048 resultGame2048;
    private boolean tilesMoved;
    private Grid2048 grid2048;
    private Scoreboard scoreboard;

    public KeyEventHandler(boolean tilesMoved, Grid2048 grid2048, Scoreboard scoreboard) {
        this.tilesMoved = tilesMoved;
        this.grid2048 = grid2048;
        this.scoreboard = scoreboard;
    }

    //    public KeyEventHandler(boolean tilesMoved) {
//        this.tilesMoved = tilesMoved;
//    }
//
//    public KeyEventHandler() {}

//    public KeyEventHandler(boolean tilesMoved, Game2048 resultGame2048) {
//        this.tilesMoved = tilesMoved;
//    }


    public Game2048 getResultGame2048() {
        return resultGame2048;
    }

    public void setResultGame2048(Game2048 resultGame2048) {
        this.resultGame2048 = resultGame2048;
    }

    public boolean isTilesMoved() {
        return tilesMoved;
    }

    public void setTilesMoved(boolean tilesMoved) {
        this.tilesMoved = tilesMoved;
    }

    public Grid2048 getGrid2048() {
        return grid2048;
    }

    public void setGrid2048(Grid2048 grid2048) {
        this.grid2048 = grid2048;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }
}

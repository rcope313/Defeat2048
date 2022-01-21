package models.game2048;

import models.grid2048.Grid2048;

public class Game2048 {
    private final Grid2048 grid2048;
    private final Scoreboard scoreboard;

    public Game2048(Grid2048 grid2048, Scoreboard scoreboard) {
        this.grid2048 = grid2048;
        this.scoreboard = scoreboard;
    }

    public Grid2048 getGrid2048() {
        return grid2048;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}

package heuristic;

import game2048.Game2048;
import game2048.Game2048Event;

public abstract class GameHeuristic {
    public final Game2048 game2048;
    public Game2048 game2048Up;
    public Game2048 game2048Down;
    public Game2048 game2048Left;
    public Game2048 game2048Right;

    public GameHeuristic(Game2048 game2048) {
        this.game2048 = game2048;
        this.game2048Up = Game2048Event.UP.buildNewGame2048StateOnKeyEvent(this.getGame2048());
        this.game2048Down = Game2048Event.DOWN.buildNewGame2048StateOnKeyEvent(this.getGame2048());
        this.game2048Left = Game2048Event.LEFT.buildNewGame2048StateOnKeyEvent(this.getGame2048());
        this.game2048Right = Game2048Event.RIGHT.buildNewGame2048StateOnKeyEvent(this.getGame2048());
    }

    public abstract Game2048 evaluateNextGameState();

    public abstract int evaluateGameStateHeuristicScore(Game2048 game);

    public Game2048 getGame2048() {
        return game2048;
    }

    public Game2048 getGame2048Up() {
        return game2048Up;
    }

    public Game2048 getGame2048Down() {
        return game2048Down;
    }

    public void setGame2048Down(Game2048 game2048Down) {
        this.game2048Down = game2048Down;
    }

    public Game2048 getGame2048Left() {
        return game2048Left;
    }

    public void setGame2048Left(Game2048 game2048Left) {
        this.game2048Left = game2048Left;
    }

    public Game2048 getGame2048Right() {
        return game2048Right;
    }

    public void setGame2048Right(Game2048 game2048Right) {
        this.game2048Right = game2048Right;
    }
}


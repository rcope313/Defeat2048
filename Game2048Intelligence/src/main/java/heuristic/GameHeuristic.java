package heuristic;

import game2048.Game2048;
import game2048.Game2048Event;
import game2048.KeyEventHandler;

public abstract class GameHeuristic {
    public final Game2048 game2048;
    public KeyEventHandler keyEventUp;
    public KeyEventHandler keyEventDown;
    public KeyEventHandler keyEventLeft;
    public KeyEventHandler keyEventRight;

    public GameHeuristic(Game2048 game2048) {
        this.game2048 = game2048;
        this.keyEventUp = Game2048Event.UP.buildNewGame2048StateOnKeyEvent(this.getGame2048());
        this.keyEventDown = Game2048Event.DOWN.buildNewGame2048StateOnKeyEvent(this.getGame2048());
        this.keyEventLeft = Game2048Event.LEFT.buildNewGame2048StateOnKeyEvent(this.getGame2048());
        this.keyEventRight = Game2048Event.RIGHT.buildNewGame2048StateOnKeyEvent(this.getGame2048());
    }

    public abstract KeyEventHandler evaluateNextGameState();

    public abstract int evaluateGameStateHeuristicScore(Game2048 game);

    public Game2048 getGame2048() {
        return game2048;
    }

    public KeyEventHandler getKeyEventUp() {
        return keyEventUp;
    }

    public void setKeyEventUp(KeyEventHandler keyEventUp) {
        this.keyEventUp = keyEventUp;
    }

    public KeyEventHandler getKeyEventDown() {
        return keyEventDown;
    }

    public void setKeyEventDown(KeyEventHandler keyEventDown) {
        this.keyEventDown = keyEventDown;
    }

    public KeyEventHandler getKeyEventLeft() {
        return keyEventLeft;
    }

    public void setKeyEventLeft(KeyEventHandler keyEventLeft) {
        this.keyEventLeft = keyEventLeft;
    }

    public KeyEventHandler getKeyEventRight() {
        return keyEventRight;
    }

    public void setKeyEventRight(KeyEventHandler keyEventRight) {
        this.keyEventRight = keyEventRight;
    }
}


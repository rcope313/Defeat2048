package models.game2048;

public class KeyEventHandler {
    boolean tilesMoved;
    Game2048 resultGame2048;

    public KeyEventHandler(boolean tilesMoved) {
        this.tilesMoved = tilesMoved;
    }

    public KeyEventHandler() {}

    public KeyEventHandler(boolean tilesMoved, Game2048 resultGame2048) {
        this.tilesMoved = tilesMoved;
    }

    public boolean isTilesMoved() {
        return tilesMoved;
    }

    public void setTilesMoved(boolean tilesMoved) {
        this.tilesMoved = tilesMoved;
    }

    public Game2048 getResultGame2048() {
        return resultGame2048;
    }

    public void setResultGame2048(Game2048 resultGame2048) {
        this.resultGame2048 = resultGame2048;
    }
}
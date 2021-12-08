package game2048;

public class KeyEventHandler {
    boolean tilesMoved;
    Game2048 updatedGame2048;

    public KeyEventHandler(boolean tilesMoved) {
        this.tilesMoved = tilesMoved;
    }

    public KeyEventHandler() {}

    public KeyEventHandler(boolean tilesMoved, Game2048 updatedGame2048) {
        this.tilesMoved = tilesMoved;
    }

    public boolean isTilesMoved() {
        return tilesMoved;
    }

    public void setTilesMoved(boolean tilesMoved) {
        this.tilesMoved = tilesMoved;
    }

    public Game2048 getUpdatedGame2048() {
        return updatedGame2048;
    }

    public void setUpdatedGame2048(Game2048 updatedGame2048) {
        this.updatedGame2048 = updatedGame2048;
    }
}
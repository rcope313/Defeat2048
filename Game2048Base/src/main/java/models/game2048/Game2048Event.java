package game2048;

public enum Game2048Event {
    UP, DOWN, LEFT, RIGHT;

    public KeyEventHandler buildNewGame2048StateOnKeyEvent(Game2048 game2048) {
        switch(this) {
            case UP:
                return game2048.handleUpEventNoRandomTile();
            case DOWN:
                return game2048.handleDownEventNoRandomTile();
            case RIGHT:
                return game2048.handleRightEventNoRandomTile();
            case LEFT:
                return game2048.handleLeftEventNoRandomTile();
            default:
                return null;
        }
    }

}

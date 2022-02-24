package models.grid2048;

public enum KeyEvent {
    UP, DOWN, LEFT, RIGHT, NOUP;

    public static KeyEvent from(String keyEvent) {
        if (keyEvent.equals("up")) {
            return KeyEvent.UP;
        }
        if (keyEvent.equals("down")) {
            return KeyEvent.DOWN;
        }
        if (keyEvent.equals("left")) {
            return KeyEvent.LEFT;
        }
        if (keyEvent.equals("right")) {
            return KeyEvent.RIGHT;
        } else {
            return KeyEvent.NOUP;
        }
    }
}

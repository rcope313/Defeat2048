package models;

public class WorstCase {
    private int numEmptySquare;
    private boolean isStackable;

    public WorstCase(int numEmptySquare, boolean isStackable) {
        this.numEmptySquare = numEmptySquare;
        this.isStackable = isStackable;
    }

    public boolean isWorstCase() {
        return !this.isStackable && this.numEmptySquare == 1;
    }

    public int getNumEmptySquare() {
        return numEmptySquare;
    }

    public void setNumEmptySquare(int numEmptySquare) {
        this.numEmptySquare = numEmptySquare;
    }

    public boolean isStackable() {
        return isStackable;
    }

    public void setStackable(boolean stackable) {
        isStackable = stackable;
    }
}

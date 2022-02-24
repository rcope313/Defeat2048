package heuristic;

import models.game2048.KeyEventHandler;
import models.game2048.Scoreboard;
import models.grid2048.Grid2048;
import java.util.HashSet;
import java.util.Random;

public class PreferUpHeuristic extends GameHeuristic {
    public PreferUpHeuristic(Grid2048 grid2048, Scoreboard scoreboard) {
        super(grid2048, scoreboard);
    }

    public static void main (String[] args) {
        HashSet<Integer[]> map = new HashSet<>();
    }

    @Override
    public KeyEventHandler evaluateNextGameState() {
        KeyEventHandler[] eventSequence = establishKeyEventSequence();
        int idx = 0;
        while (idx < 4) {
            if (eventSequence[idx].isTilesMoved()) {
                return eventSequence[idx];
            }
            idx++;
       }
        throw new IllegalStateException("Initial board empty or world ends");
    }

    KeyEventHandler[] establishKeyEventSequence() {
        KeyEventHandler[] eventSequence = new KeyEventHandler[4];
        eventSequence[0] = getKeyEventUp();
        eventSequence[3] =  getKeyEventDown();
        this.assignLeftOrRightKeyEventAtRandom(eventSequence);
        return eventSequence;
    }

    void assignLeftOrRightKeyEventAtRandom(KeyEventHandler[] eventSequence) {
        Random r = new Random();
        int randomInt = r.nextInt(2);
        if (randomInt == 0) {
            eventSequence[1] = getKeyEventLeft();
            eventSequence[2] = getKeyEventRight();
        } else {
            eventSequence[2] = getKeyEventLeft();
            eventSequence[1] = getKeyEventRight();
        }
    }

}


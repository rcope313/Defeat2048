package heuristic;

import game2048.Game2048;
import game2048.KeyEventHandler;
import java.util.Random;

public class PreferUpHeuristic extends GameHeuristic {

    public PreferUpHeuristic(Game2048 game2048) {
        super(game2048);
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

    @Override
    public int evaluateGameStateHeuristicScore(Game2048 game) {
        return 0;
    }
}


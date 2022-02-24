package heuristic;

import models.game.KeyEventHandler;
import models.game.Scoreboard;
import models.game.Grid2048;
import models.game.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class PreferUpHeuristic extends GameHeuristic {

    @Override
    public KeyEventHandler evaluateNextGameState(Grid2048 grid, Scoreboard scoreboard) {
        ArrayList<KeyEventHandler> eventSequence = establishKeyEventSequence(grid, scoreboard);
        int idx = 0;
        while (idx < 4) {
            if (eventSequence.get(idx).isTilesMoved()) {
                return eventSequence.get(idx);
            }
            idx++;
       }
        throw new IllegalStateException("Initial board empty or world ends");
    }

    static ArrayList<KeyEventHandler> establishKeyEventSequence(Grid2048 grid, Scoreboard scoreboard) {
        ArrayList<KeyEventHandler> eventSequence = new ArrayList<>();

        eventSequence.add(grid.handleKeyEventWithoutRandomTile(KeyEvent.UP, scoreboard));
        assignLeftOrRightKeyEventAtRandom(eventSequence, grid, scoreboard);
        eventSequence.add(grid.handleKeyEventWithoutRandomTile(KeyEvent.DOWN, scoreboard));
        return eventSequence;
    }

    static void assignLeftOrRightKeyEventAtRandom(ArrayList<KeyEventHandler> eventSequence, Grid2048 grid, Scoreboard scoreboard) {
        Random r = new Random();
        int randomInt = r.nextInt(2);
        if (randomInt == 0) {
            eventSequence.add(grid.handleKeyEventWithoutRandomTile(KeyEvent.LEFT, scoreboard));
            eventSequence.add(grid.handleKeyEventWithoutRandomTile(KeyEvent.RIGHT, scoreboard));
        } else {
            eventSequence.add(grid.handleKeyEventWithoutRandomTile(KeyEvent.RIGHT, scoreboard));
            eventSequence.add(grid.handleKeyEventWithoutRandomTile(KeyEvent.LEFT, scoreboard));

        }
    }

}


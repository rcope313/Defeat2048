package heuristic;

import game2048.Game2048;

public class PreferUpHeuristic extends GameHeuristic {

    public PreferUpHeuristic(Game2048 game2048) {
        super(game2048);
    }

    @Override
    public Game2048 evaluateNextGameState() {
        return null;

    }

    @Override
    public int evaluateGameStateHeuristicScore(Game2048 game) {
        return 0;
    }
}

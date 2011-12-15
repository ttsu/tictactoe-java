package game.ai.heuristic;

import game.DiscreteGameState;

public interface StateEvaluator<T extends DiscreteGameState> {
    int evaluate(T state);
}

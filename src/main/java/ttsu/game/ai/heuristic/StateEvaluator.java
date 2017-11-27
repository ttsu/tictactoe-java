package ttsu.game.ai.heuristic;

import ttsu.game.DiscreteGameState;

public interface StateEvaluator<T extends DiscreteGameState> {
    int evaluate(T state);
}

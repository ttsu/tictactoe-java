package ttsu.game.ai;

import ttsu.game.DiscreteGameState;

public interface GameIntelligenceAgent<T extends DiscreteGameState> {

    T evaluateNextState(T currentState);

    T evaluateNextState(T currentState, int depth);

}

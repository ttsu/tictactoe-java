package game.ai;

import game.DiscreteGameState;

public interface GameIntelligenceAgent<T extends DiscreteGameState> {

    T evaluateNextState(T currentState);

    T evaluateNextState(T currentState, int depth);

}
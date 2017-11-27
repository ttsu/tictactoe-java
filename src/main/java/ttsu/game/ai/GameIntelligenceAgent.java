package ttsu.game.ai;

import ttsu.game.DiscreteGameState;

public interface GameIntelligenceAgent<T extends DiscreteGameState> {

    /**
     * Returns the game state representing the game after this agent makes its move.
     */
    T evaluateNextState(T currentState);

    T evaluateNextState(T currentState, int depth);

}

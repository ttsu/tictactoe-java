package ttsu.game.ai;

import ttsu.game.DiscreteGameState;

/**
 * A game agent for discrete game states that evaluates the next game state from the current game
 * state.
 * 
 * @author Tim Tsu
 * 
 * @param <T> the type of {@link DiscreteGameState} for the game that this agent plays
 */
public interface GameIntelligenceAgent<T extends DiscreteGameState> {

  /**
   * Returns the game state representing the game after this agent makes its move.
   * 
   * @param currentState the current state of the game
   * @return the next game state; or null if there are no more states available
   */
  T evaluateNextState(T currentState);

  /**
   * Returns the game state representing the game after this agent makes its move. Limits the
   * evaluation to a specific search depth.
   * 
   * @param currentState the current state of the game
   * @param depth the limit to the number of future game states used to evaluate the next move
   * @return the next game state; or null if there are no more states available
   */
  T evaluateNextState(T currentState, int depth);

}

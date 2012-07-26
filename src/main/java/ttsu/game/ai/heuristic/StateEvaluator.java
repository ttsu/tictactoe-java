package ttsu.game.ai.heuristic;

import ttsu.game.DiscreteGameState;

/**
 * An evaluator that examines a {@link DiscreteGameState} and calculates a simple heuristic score.
 * 
 * @author Tim Tsu
 * 
 * @param <T> the type of game state that this evaluator examines
 */
public interface StateEvaluator<T extends DiscreteGameState> {

  /**
   * Computes the heuristic score for a given game state.
   * 
   * @param state the {@link DiscreteGameState} to evaluate
   * @return an integer score
   */
  int evaluate(T state);
}

package ttsu.game;

import java.util.List;

public interface DiscreteGameState {

  /**
   * Gets a list of available next states from the current game state.
   * 
   * @return a {@link List} of available {@link DiscreteGameState}s; an empty list when there are no
   *         available states. This will never be null.
   */
  List<DiscreteGameState> availableStates();

  /**
   * Gets whether this game state represents a terminal state.
   * 
   * @return <code>true</code> if the game is over; <code>false</code> otherwise.
   */
  boolean isOver();

  // TODO: consider getCurrentPlayer so it doesn't have to be passed into the
  // minimax evaluation.
}

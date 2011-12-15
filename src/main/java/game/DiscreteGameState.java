package game;

import java.util.List;

public interface DiscreteGameState {
    List<DiscreteGameState> availableStates();

    boolean isOver();
    
    //TODO: consider getCurrentPlayer so it doesn't have to be passed into the minimax evaluation.
}

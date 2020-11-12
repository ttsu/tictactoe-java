package ttsu.game.ai;

import ttsu.game.DiscreteGameState;

import java.util.List;
import java.util.Random;

public class RandomAgent<T extends DiscreteGameState> implements GameIntelligenceAgent<T> {

    public T evaluateNextState(T currentState) {
        List<DiscreteGameState> availableStates = currentState.availableStates();
        Random random = new Random();

        int size = availableStates.size();
        if (size == 0) {
            return null;
        }
        int i = size == 1 ? 0 : random.nextInt(size - 1);

        return (T) availableStates.get(i);
    }

    public T evaluateNextState(T currentState, int depth) {
        return evaluateNextState(currentState);
    }


    private void validate(T state, int depth) {
        if (state == null) {
            throw new IllegalArgumentException("initialState cannot be null");
        }
        if (depth < 0) {
            throw new IllegalArgumentException("depth cannot be less than zero. depth=" + depth);
        }
    }
}

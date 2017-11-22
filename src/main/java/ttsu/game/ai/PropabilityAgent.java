package ttsu.game.ai;

import ttsu.game.DiscreteGameState;
import ttsu.game.ai.heuristic.StateEvaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PropabilityAgent<T extends DiscreteGameState> implements GameIntelligenceAgent<T> {

    private static class Node<S extends DiscreteGameState> {
        private S state;
        private int wins;
        private int loses;
        private int value;
        private List<PropabilityAgent.Node<S>> children;

        public Node(S state) {
            this.state = state;
        }
    }

    public PropabilityAgent(StateEvaluator<T> evaluator) {
        if (evaluator == null) {
            throw new IllegalArgumentException("evaluator cannot be null");
        }
    }

    public T evaluateNextState(T currentState) {
        List<DiscreteGameState> availableStates = currentState.availableStates();

        return null;
    }

    public T evaluateNextState(T currentState, int depth) {
        if (currentState == null) {
            throw new IllegalArgumentException("initialState cannot be null");
        }
        if (depth < 0) {
            throw new IllegalArgumentException("depth cannot be less than zero. depth=" + depth);
        }
        PropabilityAgent.Node<T> root = buildTree(currentState, depth);
        for (PropabilityAgent.Node<T> child : root.children) {
            if (child.value == root.value) {
                return child.state;
            }
        }
    }

    private PropabilityAgent.Node<T> buildTree(T state, int depth) {
        PropabilityAgent.Node<T> current = new PropabilityAgent.Node<T>(state);

        if (depth == 0 || state.isOver()) {
            current.state = state;
            current.children = Collections.emptyList();
            return current;
        } else {
            ArrayList<PropabilityAgent.Node<T>> children = new ArrayList<PropabilityAgent.Node<T>>();

            for (DiscreteGameState nextState : state.availableStates()) {
//                PropabilityAgent.Node<T> child = buildTree((T) nextState, depth - 1);
//                children.add(child);
            }

            current.children = children;
        }
        return current;
    }

}

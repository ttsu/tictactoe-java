package game.ai;

import game.DiscreteGameState;
import game.ai.heuristic.StateEvaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * not thread-safe
 * 
 * @author Tim Tsu
 * 
 * @param <T>
 */
public class MinimaxEvaluation<T extends DiscreteGameState> implements GameIntelligenceAgent<T> {

    private static class Node<S extends DiscreteGameState> {
        private S state;
        private int alpha;
        private int beta;
        private int value;
        private List<Node<S>> children;

        public Node(S state, int alpha, int beta) {
            this.alpha = alpha;
            this.beta = beta;
            this.state = state;
        }
    }

    private final StateEvaluator<T> evaluator;
    private final Node<T> root;

    //TODO: consider static factory method for evaluation
    
    public MinimaxEvaluation(StateEvaluator<T> evaluator, T initialState) {
        this(evaluator, initialState, Integer.MAX_VALUE);
    }

    public MinimaxEvaluation(StateEvaluator<T> evaluator, T initialState, int depth) {
    	if (evaluator == null) {
    		throw new IllegalArgumentException("evaluator cannot be null");
    	}
    	if (initialState == null) {
    		throw new IllegalArgumentException("initialState cannot be null");
    	}
    	if (depth < 0) {
    		throw new IllegalArgumentException("depth cannot be less than zero. depth=" + depth);
    	}
        this.evaluator = evaluator;
        this.root = buildTree(initialState, depth);
    }

    @Override
	public T getNextGameState() {
        for (Node<T> child : root.children) {
            if (child.value == root.value) {
                return child.state;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Node<T> buildTree(T state, boolean max, int alpha, int beta, int depth) {
        Node<T> current = new Node<T>(state, alpha, beta);
        current.value = max ? alpha : beta;
        if (depth == 0 || state.isOver()) {
            current.value = evaluator.evaluate(state);
            current.alpha = current.value;
            current.beta = current.value;
            current.state = state;
            current.children = Collections.emptyList();
            return current;
        } else {
            ArrayList<Node<T>> children = new ArrayList<Node<T>>();
            for (DiscreteGameState nextState : state.availableStates()) {
                Node<T> child = buildTree((T) nextState, !max, current.alpha, current.beta, depth - 1);
                if (max && child.value > current.value) {
                    current.value = child.value;
                    current.alpha = child.value;
                } else if (!max && child.value < current.value) {
                    current.value = child.value;
                    current.beta = child.value;
                }
                // prune condition
                if (current.alpha >= current.beta) {
                    break;
                }
                children.add(child);
            }
            current.children = children;
        }
        return current;
    }

    private Node<T> buildTree(T state, int depth) {
        return buildTree(state, true, Integer.MIN_VALUE, Integer.MAX_VALUE, depth);
    }
}

package ttsu.game.ai;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ttsu.game.DiscreteGameState;
import ttsu.game.ai.heuristic.StateEvaluator;

/**
 * Implementation of {@link GameIntelligenceAgent} that evaluates the next optimal game state using
 * the minimax algorithm, with a-b pruning.
 * 
 * @author Tim Tsu
 * 
 * @param <T> the type of {@link DiscreteGameState} to evaluate
 */
public class MinimaxAgent<T extends DiscreteGameState> implements GameIntelligenceAgent<T> {

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

  /**
   * Creates a new instance of {@link MinimaxAgent} that uses the given {@link StateEvaluator} for
   * measuring the value of each game state.
   * 
   * @param evaluator the {@link StateEvaluator} used to measure the value of each game state;
   *        cannot be null
   */
  public MinimaxAgent(StateEvaluator<T> evaluator) {
    if (evaluator == null) {
      throw new IllegalArgumentException("evaluator cannot be null");
    }
    this.evaluator = evaluator;
  }

  @Override
  public T evaluateNextState(T currentState) {
    return evaluateNextState(currentState, Integer.MAX_VALUE);
  }

  @Override
  public T evaluateNextState(T currentState, int depth) {
    if (currentState == null) {
      throw new IllegalArgumentException("initialState cannot be null");
    }
    if (depth < 0) {
      throw new IllegalArgumentException("depth cannot be less than zero. depth=" + depth);
    }
    Node<T> root = buildTree(currentState, depth);
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

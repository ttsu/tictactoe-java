package ttsu.game.ai;

import ttsu.game.DiscreteGameState;
import ttsu.game.Main;
import ttsu.game.Watcher;
import ttsu.game.tictactoe.TicTacToeGameState;

import java.util.ArrayList;
import java.util.List;

public class PropabilityAgent<T extends DiscreteGameState> implements GameIntelligenceAgent<T> {
    private TicTacToeGameState.Player initialPlayer;
    private long startTime;

    private static class Node<S extends DiscreteGameState> {
        private S state;
        private int winningStates = 0;
        private int allStates = 0;
        private List<PropabilityAgent.Node<S>> children;
        private PropabilityAgent.Node<S> bestChild;

        public Node(S state) {
            this.state = state;
            this.bestChild = null;
        }

        public double getPropabilityOfWin() throws Exception {
            if (allStates == 0) {
                throw new Exception("Not defined propabilityOfWin");
            }
            return winningStates / allStates;
        }
    }

    public T evaluateNextState(T currentState) {
        return evaluateNextState(currentState, Main.PROP_DEPTH);
    }

    public T evaluateNextState(T currentState, int depth) {
        initialPlayer = currentState.getCurrentPlayer();
        PropabilityAgent.Node<T> root = null;

        this.startTime = System.nanoTime();

        try {
            root = buildTree(currentState, depth);
        } catch (Exception e) {
            RandomAgent<T> ra = new RandomAgent<T>();
            return ra.evaluateNextState(currentState);
        }

        if (root.bestChild == null) {
            RandomAgent<T> ra = new RandomAgent<T>();
            return ra.evaluateNextState(currentState);
        }

        return root.bestChild.state;
    }

    private PropabilityAgent.Node<T> buildTree(T state, int depth) throws Exception {
        PropabilityAgent.Node<T> current = new PropabilityAgent.Node<T>(state);
        List<DiscreteGameState> availableStates = state.availableStates();

        if (availableStates.isEmpty()) {
            this.setPropabilitiesForLastGame(current);
        } else {
            ArrayList<PropabilityAgent.Node<T>> children = new ArrayList<PropabilityAgent.Node<T>>();

            for (DiscreteGameState nextState : availableStates) {
                if (depth < 0 || (Watcher.timePassedMs(this.startTime) > Main.TIME_LIMIT && !Main.DEBUG)) {
                    throw new Exception("too long");
                }
//                 todo: coś dziwnego chyba

                PropabilityAgent.Node<T> child = buildTree((T) nextState, depth - 1);
                children.add(child);

                PropabilityAgent.Node<T> bestChild = null;
                for (PropabilityAgent.Node<T> c : children) {
                    current.allStates += c.allStates;
                    current.winningStates += c.winningStates;
                    try {
                        if (bestChild == null || (bestChild.winningStates / bestChild.allStates) < (c.winningStates / c.allStates)) {
                            bestChild = c;
                        }
                    } catch (ArithmeticException e) {
                        return current;
                    }
                }
                current.bestChild = bestChild;
            }

            current.children = children;
        }

        return current;
    }

    private void setPropabilitiesForLastGame(PropabilityAgent.Node<T> current) {
        if (current.state.hasWin(this.initialPlayer)) {
            current.winningStates = 1;
            current.allStates = 1;
            current.bestChild = current;
        } else {
            current.winningStates = 0;
            current.allStates = 1;
            current.bestChild = null;
        }
        current.children = null;
    }
}

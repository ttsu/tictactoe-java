package ttsu.game.tictactoe;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import ttsu.game.Block;
import ttsu.game.DiscreteGameState;

/**
 * A {@link DiscreteGameState} representing the current state of a TicTacToe game.
 */
public class TicTacToeGameState implements DiscreteGameState {
    public static enum Player {
        O, X;

        public static Player opponentOf(Player player) {
            return player == X ? O : X;
        }
    }

    private final GameBoard board;
    private Player currentPlayer;
    private Block lastMove;

    public TicTacToeGameState() {
        board = new GameBoard();
        currentPlayer = Player.X;
    }

    public TicTacToeGameState(GameBoard board, Player currentPlayer) {
        validate(board, currentPlayer);
        this.board = board;
        this.currentPlayer = currentPlayer;
    }

    public TicTacToeGameState(TicTacToeGameState other) {
        this.board = new GameBoard(other.board);
        this.currentPlayer = other.getCurrentPlayer();
        this.lastMove = other.lastMove;
    }

    public List<DiscreteGameState> availableStates() {
        List<Block> availableMoves = board.getOpenPositions();
        List<DiscreteGameState> availableStates = new ArrayList<DiscreteGameState>(availableMoves.size());

        for (Block move : availableMoves) {
            TicTacToeGameState newState = new TicTacToeGameState(this);
            newState.play(move);
            newState.switchPlayer();
            availableStates.add(newState);
        }

        return availableStates;
    }

    /**
     * Gets the current player whose turn it is to make the next move.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets the last position that was played on the TicTacToe board.
     */
    public Block getLastMove() {
        return lastMove;
    }

    public boolean hasWin(Player player) {
        List<Block> openPositions = board.getOpenPositions();
        if (openPositions.size() == 1) {
            return currentPlayer.equals(player);
        } else if (openPositions.size() == 2) {
            return openPositions.get(0).hasCommonPoint(openPositions.get(1));
        } else if (openPositions.size() == 0) {
            return getCurrentPlayer().equals(player);
        }

        return false;
    }

    public boolean isOver() {
        return hasWin(Player.O) || hasWin(Player.X);
    }

    /**
     * Play a move in the given points of the TicTacToe board with the current player.
     */
    private boolean play(Point a, Point b) {
        Block block = new Block(a, b);
        if (board.mark(block, currentPlayer)) {
            lastMove = block;
            return true;
        }
        return false;

    }

    private void validate(GameBoard board, Player currentPlayer) {
        if (board == null) {
            throw new IllegalArgumentException("board cannot be null");
        }
        if (currentPlayer == null) {
            throw new IllegalArgumentException("currentPlayer cannot be null");
        }
    }

    public boolean play(Block b) {
        return play(b.a, b.b);
    }

    public GameBoard getGameBoard() {
        return board;
    }

    public void switchPlayer() {
        currentPlayer = Player.opponentOf(currentPlayer);
    }
}

package ttsu.game.tictactoe;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import ttsu.game.Block;
import ttsu.game.DiscreteGameState;
import ttsu.game.Main;

public class TicTacToeGameState implements DiscreteGameState {
    public static enum Player {
        O, X, N;

        public static Player opponentOf(Player player) {
            return player == X ? O : X;
        }
    }

    private final GameBoard board;
    private Player currentPlayer;
    private Block lastMove;
    List<Block> availableMoves;

    public TicTacToeGameState() {
        board = new GameBoard(Main.DEFAULT_SIZE);
        availableMoves = board.getOpenPositions();
        currentPlayer = Player.X;
    }

    public TicTacToeGameState(int size, ArrayList<Point> excluded) {
        board = new GameBoard(size, excluded);
        availableMoves = board.getOpenPositions();
        currentPlayer = Player.X;
    }

    public TicTacToeGameState(GameBoard board, Player currentPlayer) {
        validate(board, currentPlayer);
        this.board = board;
        availableMoves = board.getOpenPositions();
        this.currentPlayer = currentPlayer;
    }

    public TicTacToeGameState(TicTacToeGameState other) {
        this.board = new GameBoard(other.board);
        this.currentPlayer = other.getCurrentPlayer();
        availableMoves = board.getOpenPositions();
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

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

package game;


import java.util.ArrayList;
import java.util.List;


public class TicTacToe implements DiscreteGameState {
    public static enum Player {
        O, X;
        public static Player opponentOf(Player player) {
            return player == X ? O : X;
        }
    }
    private final GameBoard board;
    private Player currentPlayer;
    private Position lastMove;

    public TicTacToe() {
        board = new GameBoard();
        currentPlayer = Player.X;
    }

    public TicTacToe(TicTacToe other) {
        this.board = new GameBoard(other.board);
        this.currentPlayer = other.getCurrentPlayer();
    }

    @Override
    public List<DiscreteGameState> availableStates() {
        List<Position> availableMoves = board.getOpenPositions();
        List<DiscreteGameState> availableStates = new ArrayList<DiscreteGameState>(availableMoves.size());
        for (Position move : availableMoves) {
            TicTacToe newState = new TicTacToe(this);
            newState.play(move.getRow(), move.getCol());
            newState.switchPlayer();
            availableStates.add(newState);
        }
        return availableStates;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Position getLastMove() {
        return lastMove;
    }

    public boolean hasWin(Player player) {
        for (int i = 0; i < 3; i++) {
            if (completesRow(player, i) || completesColumn(player, i)) {
                return true;
            }
        }
        return completesDiagonal(player);
    }

    @Override
    public boolean isOver() {
        return hasWin(Player.O) || hasWin(Player.X) || board.getOpenPositions().isEmpty();
    }

    public boolean play(int row, int col) {
        if (board.mark(row, col, currentPlayer)) {
            lastMove = new Position(row, col);
            return true;
        }
        return false;

    }

    public void print() {
        System.out.println(board);
    }

    public void switchPlayer() {
        currentPlayer = Player.opponentOf(currentPlayer);
    }

    private boolean completesColumn(Player player, int col) {
        Player col0 = board.getMark(0, col);
        Player col1 = board.getMark(1, col);
        Player col2 = board.getMark(2, col);
        return player == col0 && col0 == col1 && col1 == col2;
    }

    private boolean completesDiagonal(Player player) {
        Player center = board.getMark(1, 1);
        if (player != center) {
            return false;
        }
        return (board.getMark(0, 0) == center && center == board.getMark(2, 2))
                || (board.getMark(0, 2) == center && center == board.getMark(2, 0));
    }

    private boolean completesRow(Player player, int row) {
        Player row0 = board.getMark(row, 0);
        Player row1 = board.getMark(row, 1);
        Player row2 = board.getMark(row, 2);
        return player == row0 && row0 == row1 && row1 == row2;
    }
}

package game;

import game.TicTacToe.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a TicTacToe game board.
 * 
 * @author Tim Tsu
 * 
 */
public class GameBoard {

    private static final int COLS = 3;
    private static final int ROWS = 3;

    private final Player[][] board;

    /**
     * Creates a new blank TicTacToe board.
     */
    public GameBoard() {
        board = new Player[ROWS][COLS];
    }

    /**
     * Create a game board from an array.
     * 
     * @param board
     *            the {@link Player} array to use as a game board
     */
    public GameBoard(Player[][] board) {
        if (board == null) {
            throw new IllegalArgumentException("board cannot be null");
        }
        this.board = board;
    }

    /**
     * Create a deep copy of another game board.
     * 
     * @param board
     *            the board to copy
     */
    public GameBoard(GameBoard other) {
        board = new Player[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = other.board[row][col];
            }
        }
    }

    /**
     * Marks a position on the game board for a given player.
     * 
     * @param row
     *            the row of the position on the board to mark
     * @param col
     *            the column of the position on the board to mark
     * @param player
     *            the {@link Player} marking the board
     * @return <code>true</code> if the position was open to mark;
     *         <code>false</code> if the position was already marked
     * @throws IllegalArgumentException
     *             if the given position is off the board or the player is
     *             <code>null</code>
     */
    public boolean mark(int row, int col, Player player) {
        validatePosition(row, col);
        if (player == null) {
            throw new IllegalArgumentException("cannot mark null player");
        }
        if (board[row][col] != null) {
            return false;
        } else {
            board[row][col] = player;
            return true;
        }
    }

    /**
     * Gets the mark at the given board position.
     * 
     * @param row
     *            the row of the position to inspect
     * @param col
     *            the column of the position to inspect
     * @return the {@link Player} that marked the given position, or
     *         <code>null</code> if position is open
     */
    public Player getMark(int row, int col) {
        validatePosition(row, col);
        return board[row][col];
    }

    /**
     * Gets the list of open positions on the game board.
     * 
     * @return a {@link List} of {@link Position}s; will never be null
     */
    public List<Position> getOpenPositions() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == null) {
                    positions.add(new Position(row, col));
                }
            }
        }
        return positions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Player p = board[row][col];
                if (p != null) {
                    sb.append(p);
                } else {
                    sb.append(' ');
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private static void validatePosition(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            throw new IllegalArgumentException("(" + row + "," + col + ") is off the board");
        }
    }
}

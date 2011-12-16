package game;

import game.TicTacToe.Player;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    private static final int COLS = 3;
    private static final int ROWS = 3;

    private final Player[][] board;

    public GameBoard() {
        board = new Player[ROWS][COLS];
    }

    public GameBoard(Player[][] board) {
        if (board == null) {
            throw new IllegalArgumentException("board cannot be null");
        }
        this.board = board;
    }

    public GameBoard(GameBoard other) {
        board = new Player[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = other.board[row][col];
            }
        }
    }

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

    public Player getMark(int row, int col) {
        validatePosition(row, col);
        return board[row][col];
    }

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

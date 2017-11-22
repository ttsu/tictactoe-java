package ttsu.game.tictactoe;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ttsu.game.Main;
import ttsu.game.Block;
import ttsu.game.tictactoe.TicTacToeGameState.Player;


public class GameBoard {

    private static final int COLS = Main.X;
    private static final int ROWS = Main.Y;

    private final Player[][] board;

    public GameBoard() {
        board = new Player[ROWS][COLS];
    }

    public int GetRows() {
        return ROWS;
    }

    public int GetCols() {
        return COLS;
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

    public boolean mark(Block b, Player player) {
        validateBlock(b);
        return mark(b.a, b.b, player);
    }

    private boolean mark(Point a, Point b, Player player) {
        validateBlock(new Block(a, b));

        if (player == null) {
            throw new IllegalArgumentException("cannot mark null player");
        }
        if (board[a.x][a.y] != null && board[b.x][b.y] != null) {
            return false;
        } else {
            board[a.x][a.y] = player;
            board[b.x][b.y] = player;

            return true;
        }
    }

    /**
     * Gets the mark at the given board position.
     *
     * @param a the row of the position to inspect
     * @return the {@link Player} that marked the given position, or <code>null</code> if position is
     * open
     */
    public Player getMark(Point a) {
        validateSinglePosition(a);
        return board[a.x][a.y];
    }

    /**
     * Gets the list of open positions on the game board.
     *
     * @return a {@link List} of {@link Block}s; will never be null
     */
    public List<Block> getOpenPositions() {
        ArrayList<Block> blocks = new ArrayList<Block>();
        Point p1, p2;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                p1 = new Point(row, col);
                p2 = new Point(row, col + 1);

                try {
                    if (validateBlock(new Block(p1, p2)) && getMark(p1) == null && getMark(p2) == null) {
                        blocks.add(new Block(p1, p2));
                    }
                } catch (Exception e) {

                }

                try {
                    p2 = new Point(row + 1, col);
                    if (validateBlock(new Block(p1, p2)) && getMark(p1) == null && getMark(p2) == null) {
                        blocks.add(new Block(p1, p2));
                    }
                } catch (Exception e) {

                }
            }
        }
        return blocks;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GameBoard)) {
            return false;
        }
        GameBoard other = (GameBoard) obj;
        for (int row = 0; row < ROWS; row++) {
            if (!Arrays.equals(board[row], other.board[row])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        for (int row = 0; row < ROWS; row++) {
            result = prime * result + Arrays.hashCode(board[row]);
        }
        return result;
    }

    private static void validateSinglePosition(Point a) {
        if (a.x < 0 || a.x >= ROWS || a.y < 0 || a.y >= COLS) {
            throw new IllegalArgumentException("(" + a.x + "," + a.y + ") is off the board");
        }
    }

    private static boolean validateBlock(Block p) {
        return p.isConsistent() && p.isInsideBoard();
    }
}

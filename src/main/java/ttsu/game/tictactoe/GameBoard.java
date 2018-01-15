package ttsu.game.tictactoe;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ttsu.game.Main;
import ttsu.game.Block;
import ttsu.game.Watcher;
import ttsu.game.tictactoe.TicTacToeGameState.Player;


public class GameBoard {
    private final Player[][] board;
    private final int size;

    public GameBoard(int size) {
        board = new Player[size][size];
        this.size = size;
    }

    public GameBoard(int size, ArrayList<Point> excluded) {
        board = new Player[size][size];
        this.size = size;

        for (Point point : excluded) {
            board[point.x][point.y] = Player.N;
        }
    }

    public int getSize() {
        return this.size;
    }

    public GameBoard(GameBoard other) {
        this.size = other.getSize();
        board = new Player[this.size][this.size];
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                board[row] = other.board[row].clone();
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

    public Player getMark(Point a) {
        validateSinglePosition(a);
        return board[a.x][a.y];
    }

    public List<Block> getOpenPositions() {
        ArrayList<Block> blocks = new ArrayList<Block>();
        Point p1, p2;

//        if (this.openPositions != null) {
//            return this.openPositions;
//        }

        long startTime = System.nanoTime();

        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                if(Watcher.timePassedMs(startTime) > Main.TIME_LIMIT && blocks.size() != 0) {
                    return blocks;
                }

                if(this.board[row][col] != Player.O && this.board[row][col] != Player.X && this.board[row][col] != Player.N) {
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
        }

        if (Main.DEBUG) {
//            System.out.println("getOpenPositions GameBoard.java: " + Watcher.timePassedMs(startTime));
        }

//        this.openPositions = blocks;
        return blocks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
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
        for (int row = 0; row < this.size; row++) {
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
        for (int row = 0; row < this.size; row++) {
            result = prime * result + Arrays.hashCode(board[row]);
        }
        return result;
    }

    private void validateSinglePosition(Point a) {
        if (a.x < 0 || a.x >= this.size || a.y < 0 || a.y >= this.size) {
            throw new IllegalArgumentException("(" + a.x + "," + a.y + ") is off the board");
        }
    }

    private boolean validateBlock(Block p) {
        return p.isConsistent() && p.a.x >= 0 && p.a.x < this.size && p.b.x >= 0 && p.b.y < this.size;
    }

    private void validate(Player[][] board) {
        if (board == null) {
            throw new IllegalArgumentException("board cannot be null");
        }
    }
}

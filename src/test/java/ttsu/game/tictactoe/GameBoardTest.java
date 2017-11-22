package ttsu.game.tictactoe;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ttsu.game.Block;
import ttsu.game.tictactoe.GameBoard;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

import java.awt.*;


public class GameBoardTest {

    private GameBoard board;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        board = new GameBoard();
    }

    // -- constructor

    @Test
    public void copyConstructor() {
        Point p1, p2, p3, p4;
        p1 = new Point(0, 0);
        p2 = new Point(0, 1);
        p3 = new Point(1, 0);
        p4 = new Point(1, 1);

        board.mark(new Block(p1, p2), Player.X);
        GameBoard newBoard = new GameBoard(board);
        assertThat(newBoard.getMark(p1)).isEqualTo(Player.X);
        assertThat(newBoard.getMark(p2)).isEqualTo(Player.X);

        newBoard.mark(new Block(p3, p4), Player.O);
        assertThat(board.getMark(p3)).isNotEqualTo(Player.X);
        assertThat(board.getMark(p4)).isNotEqualTo(Player.X);
    }

    // -- getMark
    @Test
    public void getMarkUnmarked() {
        assertThat(board.getMark(new Point(0, 0))).isNull();
    }

    @Test
    public void getMarkOffBoard() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("(3,3) is off the board");
        board.getMark(new Point(3, 3));
    }

    @Test
    public void getMarkOffBoardNegative() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("(-1,0) is off the board");
        board.getMark(new Point(-1, 0));
    }

    // -- mark

    @Test
    public void markOnBoard() {
        Point p1, p2;
        p1 = new Point(0, 0);
        p2 = new Point(0, 1);
        boolean success = board.mark(new Block(p1, p2), Player.O);

        assertThat(success).isTrue();
        assertThat(board.getMark(p1)).isEqualTo(Player.O);
        assertThat(board.getMark(p2)).isEqualTo(Player.O);
    }

    @Test
    public void markTwice() {
        Point p1, p2;
        p1 = new Point(0, 0);
        p2 = new Point(0, 1);
        board.mark(new Block(p1, p2), Player.O);
        boolean success = board.mark(new Block(p1, p2), Player.X);

        assertThat(success).isFalse();
        assertThat(board.getMark(p1)).isEqualTo(Player.O);
        assertThat(board.getMark(p2)).isEqualTo(Player.O);
    }

    @Test
    public void markNull() {
        Point p1, p2;
        p1 = new Point(0, 0);
        p2 = new Point(0, 1);
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("cannot mark null player");
        board.mark(new Block(p1, p2), null);
    }

    //    @Test
//    public void markOffBoard() {
//        thrown.expect(IllegalArgumentException.class);
//        thrown.expectMessage("(3,0) is off the board");
//        board.mark(3, 0, null);
//    }
//
//    // -- getOpenPositions
//
    @Test
    public void getOpenPositionsAll() {
        Point p1, p2, p3, p4, p5, p6, p7, p8, p9;
        p1 = new Point(0, 0);
        p2 = new Point(1, 0);
        p3 = new Point(2, 0);
        p4 = new Point(0, 1);
        p5 = new Point(1, 1);
        p6 = new Point(2, 1);
        p7 = new Point(0, 2);
        p8 = new Point(1, 2);
        p9 = new Point(2, 2);

        assertThat(board.getOpenPositions()).containsOnly(
                new Block(p1, p2),
                new Block(p2, p3),
                new Block(p4, p5),
                new Block(p5, p6),
                new Block(p7, p8),
                new Block(p8, p9),
                new Block(p1, p4),
                new Block(p2, p5),
                new Block(p3, p6),
                new Block(p4, p7),
                new Block(p5, p8),
                new Block(p6, p9)
        );
    }
//
//    @Test
//    public void getOpenPositions() {
//        board.mark(0, 0, Player.X);
//        assertThat(board.getOpenPositions()).containsOnly(new Block(0, 1), new Block(0, 2),
//                new Block(1, 0), new Block(1, 1), new Block(1, 2), new Block(2, 0),
//                new Block(2, 1), new Block(2, 2));
//    }
}

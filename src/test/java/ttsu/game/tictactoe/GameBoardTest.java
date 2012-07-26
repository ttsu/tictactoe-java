package ttsu.game.tictactoe;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ttsu.game.Position;
import ttsu.game.tictactoe.GameBoard;
import ttsu.game.tictactoe.TicTacToeGameState.Player;


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
    board.mark(0, 0, Player.X);
    GameBoard newBoard = new GameBoard(board);
    assertThat(newBoard.getMark(0, 0)).isEqualTo(Player.X);

    newBoard.mark(1, 1, Player.O);
    assertThat(board.getMark(1, 1)).isNotEqualTo(Player.X);
  }

  // -- getMark
  @Test
  public void getMarkUnmarked() {
    assertThat(board.getMark(0, 0)).isNull();
  }

  @Test
  public void getMarkOffBoard() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("(3,0) is off the board");
    board.getMark(3, 0);
  }

  @Test
  public void getMarkOffBoardNegative() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("(-1,0) is off the board");
    board.getMark(-1, 0);
  }

  // -- mark

  @Test
  public void markOnBoard() {
    boolean success = board.mark(0, 0, Player.O);

    assertThat(success).isTrue();
    assertThat(board.getMark(0, 0)).isEqualTo(Player.O);
  }

  @Test
  public void markTwice() {
    board.mark(0, 0, Player.O);
    boolean success = board.mark(0, 0, Player.X);

    assertThat(success).isFalse();
    assertThat(board.getMark(0, 0)).isEqualTo(Player.O);
  }

  @Test
  public void markNull() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("cannot mark null player");
    board.mark(0, 0, null);
  }

  @Test
  public void markOffBoard() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("(3,0) is off the board");
    board.mark(3, 0, null);
  }

  // -- getOpenPositions

  @Test
  public void getOpenPositionsAll() {
    assertThat(board.getOpenPositions()).containsOnly(new Position(0, 0), new Position(0, 1),
        new Position(0, 2), new Position(1, 0), new Position(1, 1), new Position(1, 2),
        new Position(2, 0), new Position(2, 1), new Position(2, 2));
  }

  @Test
  public void getOpenPositions() {
    board.mark(0, 0, Player.X);
    assertThat(board.getOpenPositions()).containsOnly(new Position(0, 1), new Position(0, 2),
        new Position(1, 0), new Position(1, 1), new Position(1, 2), new Position(2, 0),
        new Position(2, 1), new Position(2, 2));
  }
}

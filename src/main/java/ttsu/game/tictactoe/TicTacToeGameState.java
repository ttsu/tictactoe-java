package ttsu.game.tictactoe;

import java.util.ArrayList;
import java.util.List;

import ttsu.game.DiscreteGameState;
import ttsu.game.Position;

/**
 * A {@link DiscreteGameState} representing the current state of a TicTacToe game.
 * 
 * @author Tim Tsu
 * 
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
  private Position lastMove;

  /**
   * Creates the initial state of a new TicTacToe game.
   */
  public TicTacToeGameState() {
    board = new GameBoard();
    currentPlayer = Player.X;
  }

  /**
   * Creates a new instance of a TicTacToe game state with a given board layout and current player.
   * 
   * @param board the current board state
   * @param currentPlayer the current player whose turn it is to make the next move
   */
  public TicTacToeGameState(GameBoard board, Player currentPlayer) {
    if (board == null) {
      throw new IllegalArgumentException("board cannot be null");
    }
    if (currentPlayer == null) {
      throw new IllegalArgumentException("currentPlayer cannot be null");
    }
    this.board = board;
    this.currentPlayer = currentPlayer;
  }

  /**
   * Creates a deep copy of the given TicTacToe game state.
   * 
   * @param other the TicTacToe game state to copy
   */
  public TicTacToeGameState(TicTacToeGameState other) {
    this.board = new GameBoard(other.board);
    this.currentPlayer = other.getCurrentPlayer();
    this.lastMove = other.lastMove;
  }

  @Override
  public List<DiscreteGameState> availableStates() {
    List<Position> availableMoves = board.getOpenPositions();
    List<DiscreteGameState> availableStates =
        new ArrayList<DiscreteGameState>(availableMoves.size());
    for (Position move : availableMoves) {
      TicTacToeGameState newState = new TicTacToeGameState(this);
      newState.play(move.getRow(), move.getCol());
      newState.switchPlayer();
      availableStates.add(newState);
    }
    return availableStates;
  }

  /**
   * Gets the current player whose turn it is to make the next move.
   * 
   * @return the {@link Player} who gets to make the next move
   */
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Gets the last position that was played on the TicTacToe board.
   * 
   * @return a {@link Position} on the TicTacToe board, or null if no moves were taken yet.
   */
  public Position getLastMove() {
    return lastMove;
  }

  /**
   * Returns whether the given player has a winning position on the TicTacToe board.
   * 
   * @param player the player to check for a win
   * @return <code>true</code> if player has won; <code>false</code> otherwise
   */
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

  /**
   * Play a move in the given row and column of the TicTacToe board with the current player.
   * 
   * @param row the row to mark
   * @param col the column to mark
   * @return <code>true</code> if this position was playable; <code>false</code> otherwise
   */
  public boolean play(int row, int col) {
    if (board.mark(row, col, currentPlayer)) {
      lastMove = new Position(row, col);
      return true;
    }
    return false;

  }

  /**
   * Gets the game board.
   * 
   * @return {@link GameBoard} for the current TicTacToe game; cannot be null
   */
  public GameBoard getGameBoard() {
    return board;
  }

  /**
   * Switches the current player.
   */
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

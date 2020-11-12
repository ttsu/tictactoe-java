package ttsu.game;

import ttsu.game.tictactoe.GameBoard;
import ttsu.game.tictactoe.TicTacToeGameState;

import java.util.List;

public interface DiscreteGameState {
  List<DiscreteGameState> availableStates();
  TicTacToeGameState.Player getCurrentPlayer();
  public GameBoard getGameBoard();
  boolean hasWin(TicTacToeGameState.Player p);
  boolean isOver();
}

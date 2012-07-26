package ttsu.game.tictactoe;

import java.io.PrintStream;

import ttsu.game.tictactoe.TicTacToeGameState.Player;

/**
 * Prints a TicTacToe game board to the console.
 * 
 * @author Tim Tsu
 * 
 */
public class TicTacToeBoardPrinter {

  private PrintStream printStream;

  public TicTacToeBoardPrinter(PrintStream printStream) {
    this.printStream = printStream;
  }

  /**
   * Prints the TicTacToe game board.
   * 
   * @param board the {@link GameBoard} to print; cannot be null
   */
  public void printGameBoard(GameBoard board) {
    printRow(0, board);
    printStream.println("-+-+-");
    printRow(1, board);
    printStream.println("-+-+-");
    printRow(2, board);
  }

  private void printRow(int row, GameBoard board) {
    printStream.printf("%s|%s|%s\n", markToString(board.getMark(row, 0)),
        markToString(board.getMark(row, 1)), markToString(board.getMark(row, 2)));
  }

  private static String markToString(Player player) {
    return player == null ? " " : player.toString();
  }

}

package ttsu.game.tictactoe;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import ttsu.game.tictactoe.Pair;

import ttsu.game.Position;
import ttsu.game.ai.GameIntelligenceAgent;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

/**
 * A class for configuring and running a TicTacToe game.
 * 
 * @author Tim Tsu
 * 
 */
public class TicTacToeGameRunner {

  /**
   * The instructions for valid human input.
   */
  static final String INSTRUCTION_TEXT =
      "Enter '<row>,<col>' to play a position. For example, '0,2'.";

  private TicTacToeGameState game;

  private TicTacToeBoardPrinter boardPrinter;
  private GameIntelligenceAgent<TicTacToeGameState> agent;
  private Scanner scanner;
  private PrintStream printStream;

  private List<Pair> availSpaces;
  /**
   * Creates a new game runner.
   * 
   * @param agent a {@link GameIntelligenceAgent} for choosing the computer opponent's moves
   * @param scanner a {@link Scanner} for collecting user input
   * @param printStream the {@link PrintStream} for displaying text to the user
   */
  public TicTacToeGameRunner(GameIntelligenceAgent<TicTacToeGameState> agent, Scanner scanner,
      PrintStream printStream) {
    this.game = new TicTacToeGameState();
    this.boardPrinter = new TicTacToeBoardPrinter(printStream);
    this.agent = agent;
    this.scanner = scanner;
    this.printStream = printStream;

    this.availSpaces = new ArrayList<Pair>();
    for (int i = 0 ; i < 3; i++)
    {
    	for (int j = 0; j < 3; j++)
    	{
		this.availSpaces.add(new Pair(i,j));
    	}
    }
  }

  /**
   * Runs the TicTacToe game, alternating between human and computer moves until the game is over.
   */
  public void run() {
    //printInstructions();
    while (!game.isOver()) {
      moveHuman();
      moveComputer();
      //boardPrinter.printGameBoard(game.getGameBoard());
    }
    printGameOver();
  }

  /**
   * Gets the current game state.
   * 
   * @return the game
   */
  TicTacToeGameState getGame() {
    return game;
  }

  void moveComputer() {
    TicTacToeGameState nextState = agent.evaluateNextState(game);
    if (nextState == null) {
      return;
    }
    Position nextMove = nextState.getLastMove();
    game.play(nextMove.getRow(), nextMove.getCol());
    game.switchPlayer();
  }

  void moveHuman() {
    Position userPosition;
    Random rng = new Random();
    int idx; 
    while (true) {
      do {
  //      printStream.print("Player X [row,col]: ");
  //      original code used scanner input for the players next move.
  //      Here, we replace with 2 random ints between [0,2], with a blank println
  //      to adjust for the formatting difference
        
  //      String input = scanner.nextLine();
  	idx = rng.nextInt(this.availSpaces.size());
	
	// get avail coord
	Pair pair = this.availSpaces.get(idx);
	// remove it from list so it is not tried again
	this.availSpaces.remove(idx);
        String input = String.format("%s,%s", pair.getKey(), pair.getValue()) ;
	userPosition = parseUserInput(input);
       
      } while (userPosition == null);

      try {
        if (game.play(userPosition.getRow(), userPosition.getCol())) {
          game.switchPlayer();
          return;
        } else {
  //        printStream.printf("(%d,%d) has already been taken. ", userPosition.getRow(),
  //        userPosition.getCol());
  //        printInstructions();
        }
      } catch (IllegalArgumentException e) {
  //        printStream.printf("(%d,%d) is not on the board. ", userPosition.getRow(),
  //        userPosition.getCol());
  //        printInstructions();
      }
    }
  }

  private void printGameOver() {
    if (game.hasWin(Player.X)) {
      ((PrintStream) printStream).println("Player X won.");
    } else if (game.hasWin(Player.O)) {
      printStream.println("Player O won.");
    } else {
      printStream.println("Game ended in a draw.");
    }
  }

  private void printInstructions() {
    printStream.println(INSTRUCTION_TEXT);
  }

  private Position parseUserInput(String input) {
    String[] posInput = input.split(",");
    if (posInput.length != 2) {
      printInstructions();
      return null;
    }
    int row, col;
    try {
      row = Integer.parseInt(posInput[0]);
      col = Integer.parseInt(posInput[1]);
    } catch (NumberFormatException e) {
      printInstructions();
      return null;
    }
    return new Position(row, col);
  }

}

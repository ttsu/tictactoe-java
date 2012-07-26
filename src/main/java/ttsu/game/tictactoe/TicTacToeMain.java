package ttsu.game.tictactoe;

import java.util.Scanner;

import ttsu.game.ai.GameIntelligenceAgent;
import ttsu.game.ai.MinimaxAgent;
import ttsu.game.ai.heuristic.tictactoe.TicTacToeEvaluator;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

public class TicTacToeMain {

  /**
   * @param args
   */
  public static void main(String[] args) {
    TicTacToeEvaluator evaluator = new TicTacToeEvaluator(Player.O);
    GameIntelligenceAgent<TicTacToeGameState> agent =
        new MinimaxAgent<TicTacToeGameState>(evaluator);
    Scanner scanner = new Scanner(System.in);
    TicTacToeGameRunner game = new TicTacToeGameRunner(agent, scanner, System.out);

    game.run();
  }

}

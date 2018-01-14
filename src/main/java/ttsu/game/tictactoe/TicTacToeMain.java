package ttsu.game.tictactoe;

import java.util.Scanner;

import ttsu.game.ai.GameIntelligenceAgent;
import ttsu.game.ai.MinimaxAgent;
import ttsu.game.ai.PropabilityAgent;
import ttsu.game.ai.heuristic.tictactoe.TicTacToeEvaluator;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

public class TicTacToeMain {
    public static void main(String[] args) {

        GameIntelligenceAgent<TicTacToeGameState> propabilityAgent = new PropabilityAgent<TicTacToeGameState>();

        TicTacToeGameRunner game = new TicTacToeGameRunner(propabilityAgent, System.out);

        game.run();
    }

}

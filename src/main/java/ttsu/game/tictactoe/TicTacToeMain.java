package ttsu.game.tictactoe;

import java.util.Scanner;

import ttsu.game.Position;
import ttsu.game.ai.GameIntelligenceAgent;
import ttsu.game.ai.MinimaxAgent;
import ttsu.game.ai.heuristic.tictactoe.TicTacToeEvaluator;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

public class TicTacToeMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        TicTacToeGameState game = new TicTacToeGameState();
        TicTacToeEvaluator eval = new TicTacToeEvaluator(Player.O);
        GameIntelligenceAgent<TicTacToeGameState> agent = new MinimaxAgent<TicTacToeGameState>(eval);
        Scanner scanner = new Scanner(System.in);
        while (!game.isOver()) {
            System.out.println("Player " + game.getCurrentPlayer() + ":");
            int row, col;
            do {
                String input = scanner.nextLine();
                String[] posInput = input.split(",");
                row = Integer.parseInt(posInput[0]);
                col = Integer.parseInt(posInput[1]);
            } while (!game.play(row, col));
            game.switchPlayer();

            if (!game.isOver()) {
                TicTacToeGameState nextState = agent.evaluateNextState(game);
                // check for not null
                Position lastMove = nextState.getLastMove();
                game.play(lastMove.getRow(), lastMove.getCol());
                game.print();
                game.switchPlayer();
            }
        }
        System.out.println("Game over.");
    }
}

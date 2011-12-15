package game;

import game.TicTacToe.Player;
import game.ai.GameIntelligenceAgent;
import game.ai.MinimaxEvaluation;
import game.ai.heuristic.TicTacToeEvaluator;

import java.util.Scanner;


public class TicTacToeMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        TicTacToeEvaluator eval = new TicTacToeEvaluator(Player.O);
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
                GameIntelligenceAgent<TicTacToe> ai = new MinimaxEvaluation<TicTacToe>(eval, game);
                TicTacToe nextState = ai.getNextGameState();
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

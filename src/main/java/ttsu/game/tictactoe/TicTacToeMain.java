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

        printInstructions();

        TicTacToeGameState game = new TicTacToeGameState();
        TicTacToeEvaluator eval = new TicTacToeEvaluator(Player.O);
        GameIntelligenceAgent<TicTacToeGameState> agent = new MinimaxAgent<TicTacToeGameState>(eval);
        Scanner scanner = new Scanner(System.in);

        while (!game.isOver()) {
            game.print();
            userPlay(scanner, game);
            game.switchPlayer();
            computerPlay(agent, game);
            game.switchPlayer();
        }
        game.print();
        printGameOver(game);
    }

    private static void computerPlay(GameIntelligenceAgent<TicTacToeGameState> agent, TicTacToeGameState game) {
        TicTacToeGameState nextState = agent.evaluateNextState(game);
        if (nextState == null) {
            return;
        }
        Position lastMove = nextState.getLastMove();
        game.play(lastMove.getRow(), lastMove.getCol());
    }

    private static Position parseUserInput(String input) {
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

    private static void userPlay(Scanner scanner, TicTacToeGameState game) {
        Position userPosition;
        while (true) {
            do {
                String input = scanner.nextLine();
                userPosition = parseUserInput(input);
            } while (userPosition == null);

            try {
                if (game.play(userPosition.getRow(), userPosition.getCol())) {
                    return;
                } else {
                    System.out.printf("(%d,%d) has already been taken. ", userPosition.getRow(), userPosition.getCol());
                    printInstructions();
                }
            } catch (IllegalArgumentException e) {
                System.out.printf("(%d,%d) is not on the board. ", userPosition.getRow(), userPosition.getCol());
                printInstructions();
            }
        }
    }

    private static void printGameOver(TicTacToeGameState gamestate) {
        if (gamestate.hasWin(Player.X)) {
            System.out.println("Player X won.");
        } else if (gamestate.hasWin(Player.O)) {
            System.out.println("Player O won.");
        } else {
            System.out.println("Game ended in a draw.");
        }
    }

    private static void printInstructions() {
        System.out.println("Enter '<row>,<col>' to play a position:");
    }

}

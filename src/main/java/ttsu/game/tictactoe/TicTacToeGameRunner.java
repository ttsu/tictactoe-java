package ttsu.game.tictactoe;

import java.io.PrintStream;

import ttsu.game.Block;
import ttsu.game.ai.GameIntelligenceAgent;
import ttsu.game.ai.RandomAgent;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

public class TicTacToeGameRunner {
    private TicTacToeGameState game;
    private TicTacToeBoardPrinter boardPrinter;
    private GameIntelligenceAgent<TicTacToeGameState> propabilityAgent;
    private PrintStream printStream;

    public TicTacToeGameRunner(GameIntelligenceAgent<TicTacToeGameState> propabilityAgent,
                               PrintStream printStream) {
        this.game = new TicTacToeGameState();
        this.boardPrinter = new TicTacToeBoardPrinter(printStream);
        this.printStream = printStream;
        this.propabilityAgent = propabilityAgent;
    }

    TicTacToeGameState getGame() {
        return game;
    }

    public void run() {
        printStream.println("Game is starting - randomly by " + game.getCurrentPlayer());

        while (!game.isOver()) {
            moveRandomlyComputer();
            game.switchPlayer();
//            boardPrinter.printGameBoard(game.getGameBoard());

            moveRandomlyComputer();
            game.switchPlayer();
//            boardPrinter.printGameBoard(game.getGameBoard());
        }
        printGameOver();
    }

    void movePropabilityComputer() {
        moveAny(propabilityAgent.evaluateNextState(game));
    }

    void moveRandomlyComputer() {
        RandomAgent<TicTacToeGameState> randomAgent = new RandomAgent<TicTacToeGameState>();
        moveAny(randomAgent.evaluateNextState(game));
    }

    private void moveAny(TicTacToeGameState state) {
        if (state == null) {
            return;
        }
        Block nextMove = state.getLastMove();
        game.play(nextMove);
    }

    private void printGameOver() {
        if (game.hasWin(Player.X)) {
            printStream.println("Player X won.");
        } else if (game.hasWin(Player.O)) {
            printStream.println("Player O won.");
        }
    }
}

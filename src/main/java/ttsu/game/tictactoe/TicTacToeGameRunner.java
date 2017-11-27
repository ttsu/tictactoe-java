package ttsu.game.tictactoe;

import java.io.PrintStream;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import ttsu.game.Block;
import ttsu.game.Main;
import ttsu.game.ai.GameIntelligenceAgent;
import ttsu.game.ai.RandomAgent;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

public class TicTacToeGameRunner {
    private TicTacToeGameState game;
    private TicTacToeBoardPrinter boardPrinter;
    private GameIntelligenceAgent<TicTacToeGameState> agent;
    private GameIntelligenceAgent<TicTacToeGameState> propabilityAgent;
    private Scanner scanner;
    private PrintStream printStream;

    public TicTacToeGameRunner(GameIntelligenceAgent<TicTacToeGameState> agent,
                               GameIntelligenceAgent<TicTacToeGameState> propabilityAgent,
                               Scanner scanner,
                               PrintStream printStream) {
        this.game = new TicTacToeGameState();
        this.boardPrinter = new TicTacToeBoardPrinter(printStream);
        this.agent = agent;
        this.scanner = scanner;
        this.printStream = printStream;
        this.propabilityAgent = propabilityAgent;
    }

    TicTacToeGameState getGame() {
        return game;
    }

    public void run() {
        printStream.println("Game has started");

        while (!game.isOver()) {
            moveRandomlyComputer();
//            moveComputer();
            game.switchPlayer();
            movePropabilityComputer();
            boardPrinter.printGameBoard(game.getGameBoard());
        }
        printGameOver();
    }

    void movePropabilityComputer() {
        moveAny(propabilityAgent.evaluateNextState(game));
    }

    void moveComputer() {
        moveAny(agent.evaluateNextState(game));
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

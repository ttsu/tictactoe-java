package ttsu.game.tictactoe;

import java.io.PrintStream;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import ttsu.game.Block;
import ttsu.game.Main;
import ttsu.game.ai.GameIntelligenceAgent;
import ttsu.game.tictactoe.TicTacToeGameState.Player;


public class TicTacToeGameRunner {
    static final String INSTRUCTION_TEXT = "Enter '<row>,<col>' to play a position. For example, '0,2'.";
    private TicTacToeGameState game;
    private TicTacToeBoardPrinter boardPrinter;
    private GameIntelligenceAgent<TicTacToeGameState> agent;
    private Scanner scanner;
    private PrintStream printStream;

    /**
     * Creates a new game runner.
     *
     * @param agent       a {@link GameIntelligenceAgent} for choosing the computer opponent's moves
     * @param scanner     a {@link Scanner} for collecting user input
     * @param printStream the {@link PrintStream} for displaying text to the user
     */
    public TicTacToeGameRunner(GameIntelligenceAgent<TicTacToeGameState> agent, Scanner scanner,
                               PrintStream printStream) {
        this.game = new TicTacToeGameState();
        this.boardPrinter = new TicTacToeBoardPrinter(printStream);
        this.agent = agent;
        this.scanner = scanner;
        this.printStream = printStream;
    }

    public void run() {
        printStream.println("Game has started");

        while (!game.isOver()) {
//            moveRandomlyComputer();
            moveRandomlyComputer();
//            moveComputer();
            moveComputer();
            boardPrinter.printGameBoard(game.getGameBoard());
        }
        printGameOver();
    }

    TicTacToeGameState getGame() {
        return game;
    }

    void moveComputer() {
        TicTacToeGameState nextState = agent.evaluateNextState(game);
        if (nextState == null) {
            return;
        }
        Block nextMove = nextState.getLastMove();
        game.play(nextMove);
        game.switchPlayer();
    }

    void moveRandomlyComputer() {
        Block userBlock;
        while (true) {

            if (this.getRandomBlock() != null) {
                userBlock = this.getRandomBlock();
            } else {
                return;
            }

            try {
                if (game.play(userBlock)) {
                    game.switchPlayer();
                    return;
                } else {
                    if (game.getGameBoard().getOpenPositions().isEmpty()) {
                        return;
                    }

                    printStream.printf("(%d,%d,%d,%d) has already been taken. ", userBlock.a.x,
                            userBlock.a.y, userBlock.b.x, userBlock.b.y);

                    printInstructions();
                    boardPrinter.printGameBoard(game.getGameBoard());
                }
            } catch (IllegalArgumentException e) {
                printStream.printf("(%d,%d,%d,%d) is not on the board. ", userBlock.a.x,
                        userBlock.a.y, userBlock.b.x, userBlock.b.y);
                printInstructions();
            }

        }
    }

    private void printGameOver() {
        if (game.hasWin(Player.X)) {
            printStream.println("Player X won.");
        } else if (game.hasWin(Player.O)) {
            printStream.println("Player O won.");
        } else {
            printStream.println("Game ended in a draw.");
        }
    }

    private void printInstructions() {
        printStream.println(INSTRUCTION_TEXT);
    }

    private Block getRandomBlock() {
        Random random = new Random();
        List<Block> openPositions = game.getGameBoard().getOpenPositions();
        int size = openPositions.size();

        if (size == 0) {
            return null;
        }

        if (size == 1) {
            return openPositions.get(0);
        }

        return openPositions.get(random.nextInt(size - 1));
    }

}

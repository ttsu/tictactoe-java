package ttsu.game;

import ttsu.game.ai.GameIntelligenceAgent;
import ttsu.game.ai.MinimaxAgent;
import ttsu.game.ai.PropabilityAgent;
import ttsu.game.ai.RandomAgent;
import ttsu.game.ai.heuristic.tictactoe.TicTacToeEvaluator;
import ttsu.game.tictactoe.TicTacToeBoardPrinter;
import ttsu.game.tictactoe.TicTacToeGameState;
import ttsu.mrozu.Algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.*;
import java.util.ArrayList;

public class Communication {
    public static void Communication() throws IOException, InterruptedException {
        TicTacToeGameState gameState = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        TicTacToeBoardPrinter boardPrinter = new TicTacToeBoardPrinter(System.out);
        GameIntelligenceAgent<TicTacToeGameState> propabilityAgent = new PropabilityAgent<TicTacToeGameState>();
        
        String input;
        int boardSize;
        ArrayList<Point> excludedPoints;

        input = br.readLine();

        long startTime = System.nanoTime();

        boardSize = Parser.parseBoardSize(input);
        excludedPoints = Parser.parseExcludedPoints(input);
        gameState = new TicTacToeGameState(boardSize, excludedPoints);

        if(Main.DEBUG) {
            System.out.println(Watcher.timePassedMs(startTime));
        }

        System.out.println("OK");

        while (true) {
            input = br.readLine();

            startTime = System.nanoTime();

            if (input.equals("stop") || input.equals("STOP"))
                break;
            else {
                if (input.equals("start") || input.equals("START")) {

                    gameState = propabilityAgent.evaluateNextState(gameState);
                    System.out.println(gameState.getLastMove().toString());

                } else {
                    Block opponentBlock = Parser.parseOpponentMove(input);

                    gameState.play(opponentBlock);
                    gameState.switchPlayer();

                    gameState = propabilityAgent.evaluateNextState(gameState);
                    System.out.println(gameState.getLastMove().toString());
                }
            }

            if (Main.DEBUG) {
                boardPrinter.printGameBoard(gameState.getGameBoard());
                System.out.println("Time: " + Watcher.timePassedMs(startTime));
            }
        }
    }

}

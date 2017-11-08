package ttsu.game.tictactoe;

import java.awt.*;
import java.io.PrintStream;
import java.util.ArrayList;

import ttsu.game.tictactoe.TicTacToeGameState.Player;

public class TicTacToeBoardPrinter {

    private PrintStream printStream;

    public TicTacToeBoardPrinter(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void printGameBoard(GameBoard board) {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < board.GetCols(); i++) {
            str.append("-");
            if (i != board.GetCols() - 1) {
                str.append("+");
            }
        }

        for (int i = 0; i < board.GetCols(); i++) {
            printRow(i, board);
            printStream.println(str.toString());
        }
    }

    private void printRow(int row, GameBoard board) {
        StringBuilder str = new StringBuilder();
        ArrayList<String> output = new ArrayList<String>();

        for (int i = 0; i < board.GetRows(); i++) {
            output.add(
                    markToString(board.getMark(new Point(row, i))));
            str.append("%s");

            if (i == board.GetRows() - 1) {
                str.append("\r\n");
            } else {
                str.append("|");
            }
        }

        printStream.printf(str.toString(), output.toArray());
    }

    private static String markToString(Player player) {
        return player == null ? " " : player.toString();
    }

}

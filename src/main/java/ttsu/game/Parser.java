package ttsu.game;

import java.awt.*;
import java.util.ArrayList;

public class Parser {
    static int parseBoardSize(String input) {
        return Integer.parseInt(input.split("_")[0]);
    }

    static Block parseOpponentMove(String input) {
        String[] splitted = input.split("_");

        Point p1,p2;
        Block block;

        p1 = new Point(
                Integer.parseInt(splitted[0].split("x")[0]) - (Main.COUNT_FROM_ONE ? 1 : 0),
                Integer.parseInt(splitted[0].split("x")[1]) - (Main.COUNT_FROM_ONE ? 1 : 0)
        );
        p2 = new Point(
                Integer.parseInt(splitted[1].split("x")[0]) - (Main.COUNT_FROM_ONE ? 1 : 0),
                Integer.parseInt(splitted[1].split("x")[1]) - (Main.COUNT_FROM_ONE ? 1 : 0)
        );

        return new Block(p1,p2);
    }

    static ArrayList<Point> parseExcludedPoints(String input) {
        String[] values = input.split("_");
        ArrayList<Point> output = new ArrayList<Point>();

        for (int i = 1; i < values.length; i++) {
            String[] numbers = values[i].split("x");
            output.add(
                    new Point(
                            Integer.parseInt(numbers[0]) - (Main.COUNT_FROM_ONE ? 1 : 0),
                            Integer.parseInt(numbers[1]) - (Main.COUNT_FROM_ONE ? 1 : 0)
                    )
            );
        }

        return output;
    }
}

package ttsu.mrozu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Algorithm {
    private int gridSize;
    private ArrayList<Point> gridArray;

    public Algorithm(int gS) {
        gridSize = gS;
        gridArray = new ArrayList<Point>();
    }

    public void add(int a, int b, int c, int d) {
        gridArray.add(new Point(a, b));
        gridArray.add(new Point(c, d));

        ArrayList<Block> validMoves = validMovesLeft(gridArray);
        int min = 257542;
        int x = 0;

        ArrayList<Point> temp = new ArrayList<Point>();

        if (validMoves.size() == 1) {
            gridArray.add(validMoves.get(0).a);
            gridArray.add(validMoves.get(0).b);
            System.out.println(validMoves.get(0).a.x + " " + validMoves.get(0).a.y + " " + validMoves.get(0).b.x + " " +validMoves.get(0).b.y);
        } else {
            for (int i = 0; i < validMoves.size(); i++) {
                temp.addAll(gridArray);
                temp.add(validMoves.get(i).a);
                temp.add(validMoves.get(i).b);

                if (validMovesLeft(temp).size() % 2 == 0 /*&& validMovesLeft(temp).size() < min*/) {
                    x = i;
                    //min = validMovesLeft(temp).size();
                }
            }

            /*if (x == -1)
                for (int i = 0; i < validMoves.size(); i++) {
                    temp.addAll(gridArray);
                    temp.add(validMoves.get(i).a);
                    temp.add(validMoves.get(i).b);

                    if (validMovesLeft(temp).size() % 2 == 1) {
                        x = i;
                        min = validMovesLeft(temp).size();
                    }
                }*/

            gridArray.add(validMoves.get(x).a);
            gridArray.add(validMoves.get(x).b);
            System.out.println(validMoves.get(x).a.x + " " + validMoves.get(x).a.y + " " + validMoves.get(x).b.x + " " + validMoves.get(x).b.y);
        }
    }

    private ArrayList<Block> validMovesLeft(ArrayList<Point> list) {
        ArrayList<Block> validMovesList = new ArrayList<Block>();
        Point middle, top, bottom, left, right;

        for (int i = 1; i < gridSize; i++) {
            for (int j = 1; j < gridSize; j++) {
                if (isValid(middle = new Point(i, j), list)) {
                    if (isValid(top = new Point(middle.x, middle.y + 1), list))
                        validMovesList.add(new Block(middle, top));
                    if (isValid(bottom = new Point(middle.x, middle.y - 1), list))
                        validMovesList.add(new Block(middle, bottom));
                    if (isValid(left = new Point(middle.x - 1, middle.y), list))
                        validMovesList.add(new Block(middle, left));
                    if (isValid(right = new Point(middle.x + 1, middle.y), list))
                        validMovesList.add(new Block(middle, right));
                }
            }
        }

        Set<Block> hs = new HashSet<Block>();
        hs.addAll(validMovesList);
        validMovesList.clear();
        validMovesList.addAll(hs);

        return validMovesList;
    }

    private boolean isValid(Point point, ArrayList<Point> list){
        if(point.x > gridSize || point.y > gridSize || point.x < 1 || point.y < 1)
            return false;

        for(Point temp : gridArray)
            if (temp.equals(point))
                return false;

        return true;
    }

    private class Block {
        private Point a;
        private Point b;

        private Block(Point a, Point b){
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Block block = (Block) o;

            if ((a.equals(block.a) || a.equals(block.b)) && (b.equals(block.a) || b.equals(block.b))) return true;
            return false;

        }

        @Override
        public String toString() {
            return "Block{" +
                    "a=" + a +
                    ", b=" + b +
                    '}' + '\n';
        }
    }

    private class Point {
        private int x;
        private int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            return y == point.y;
        }
    }
}
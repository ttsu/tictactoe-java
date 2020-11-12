package ttsu.game;

import java.awt.*;

public final class Block {
    public final Point a;
    public final Point b;

    public Block(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public boolean hasCommonPoint(Block block) {
        return (block.a == a && block.b == b) || (block.a == b && block.b == a);
    }

    public boolean isConsistent() {
        return a.distance(b) == 1.0;
    }

//    public boolean isInsideBoard() {
//        return a.x >= 0 && a.x < Main.X && b.x >= 0 && b.y < Main.Y;
//    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Block)) {
            return false;
        }
        Block other = (Block) obj;
        return a.equals(other.a) && b.equals(other.b);
    }

    @Override
    public int hashCode() {
        return a.hashCode() + b.hashCode();
    }

    @Override
    public String toString() {
        return (this.a.x + (Main.COUNT_FROM_ONE ? 1 : 0)) + "x" +
                (this.a.y + (Main.COUNT_FROM_ONE ? 1 : 0)) + "_" +
                (this.b.x + (Main.COUNT_FROM_ONE ? 1 : 0)) + "x" +
                (this.b.y + (Main.COUNT_FROM_ONE ? 1 : 0));
    }
}

package ttsu.game;

public class Watcher {
    public static long timePassedMs(long startTime) {
        return (System.nanoTime() - startTime) / 1000000;
    }
}

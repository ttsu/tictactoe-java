package ttsu.game;

public class Main {
    public static int DEFAULT_SIZE = 4;
    public static int PROP_DEPTH = 100;
    public static boolean COUNT_FROM_ONE = false;
    public static boolean DEBUG = false;
    public static long TIME_LIMIT = 280;

    public static void main(String[] args) {

        try {
            Communication.Communication();
        } catch (Exception e) {
            System.err.println("IO Exception");
        }

    }
}

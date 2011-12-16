package game.ai.heuristic;

import static game.TicTacToe.Player.opponentOf;
import game.TicTacToe;
import game.TicTacToe.Player;

public class TicTacToeEvaluator implements StateEvaluator<TicTacToe> {

    private final Player player;

    public TicTacToeEvaluator(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }
        this.player = player;
    }

    @Override
    public int evaluate(TicTacToe game) {
        if (game == null) {
            throw new IllegalArgumentException("cannot evaluate null game");
        }
        if (game.hasWin(player)) {
            return game.availableStates().size() + 1;
        } else if (game.hasWin(opponentOf(player))) {
            return -1;
        } else {
            return 0;
        }
    }

}

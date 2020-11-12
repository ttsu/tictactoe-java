package ttsu.game.ai.heuristic.tictactoe;

import static ttsu.game.tictactoe.TicTacToeGameState.Player.opponentOf;

import ttsu.game.ai.heuristic.StateEvaluator;
import ttsu.game.tictactoe.TicTacToeGameState;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

public class TicTacToeEvaluator implements StateEvaluator<TicTacToeGameState> {

    private final Player player;

    public TicTacToeEvaluator(Player player) {
        validate(player);
        this.player = player;
    }

    public int evaluate(TicTacToeGameState game) {
        validate(game);

        if (game.hasWin(player)) {
            return game.availableStates().size() + 1;
        } else if (game.hasWin(opponentOf(player))) {
            return -1;
        } else {
            return 0;
        }
    }

    private void validate(TicTacToeGameState game) {
        if (game == null) {
            throw new IllegalArgumentException("cannot evaluate null game");
        }
    }

    private void validate(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }
    }
}

package game.ai.heuristic;

import static game.TicTacToe.Player.opponentOf;
import game.TicTacToe;
import game.TicTacToe.Player;

/**
 * A {@link StateEvaluator} for a {@link TicTacToe} game state, relative to a
 * particular player.
 * <p>
 * The score is calculated such that a winning state always has a higher score
 * than a drawn state and a drawn state always has a higher score than a losing
 * state. A winning state in less moves has a higher score than a winning state
 * in more moves.
 * </p>
 * 
 * @author Tim Tsu
 * 
 */
public class TicTacToeEvaluator implements StateEvaluator<TicTacToe> {

    private final Player player;

    /**
     * Crates a new {@link TicTacToeEvaluator} that scores winning states
     * relative to a given player.
     * 
     * @param player
     *            a TicTacToe {@link Player}; cannot be null
     */
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

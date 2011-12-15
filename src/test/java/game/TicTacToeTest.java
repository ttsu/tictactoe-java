package game;

import static org.fest.assertions.Assertions.assertThat;

import game.Position;
import game.TicTacToe;
import game.TicTacToe.Player;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TicTacToeTest {

    private TicTacToe game;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        game = new TicTacToe();
    }

    // -- constructor

    @Test
    public void startingPlayerIsX() {
        assertThat(new TicTacToe().getCurrentPlayer()).isEqualTo(Player.X);
    }

    // TODO copy c'tor

    // -- availableStates
    
    @Test
    public void getAvaliableStatesEmptyBoard() {
    	TicTacToe game = new TicTacToe();
    	List<DiscreteGameState> states = game.availableStates();
        assertThat(states).hasSize(9);
    }

    @Test
    public void getAvailableStatesLastOne() {
        TicTacToe game = new TicTacToe();
        game.play(0, 0);
        game.play(0, 1);
        game.play(0, 2);
        game.play(1, 0);
        game.play(1, 1);
        game.play(1, 2);
        game.play(2, 0);
        game.play(2, 1);

        List<DiscreteGameState> states = game.availableStates();
        assertThat(states).hasSize(1);
        TicTacToe availableState = (TicTacToe) states.get(0);
        assertThat(availableState.getCurrentPlayer()).isEqualTo(Player.opponentOf(game.getCurrentPlayer()));
        assertThat(availableState.getLastMove()).isEqualTo(new Position(2, 2));
    }

    @Test
    public void getAvailableStatesCompleteBoard() {
        TicTacToe game = new TicTacToe();
        game.play(0, 0);
        game.play(0, 1);
        game.play(0, 2);
        game.play(1, 0);
        game.play(1, 1);
        game.play(1, 2);
        game.play(2, 0);
        game.play(2, 1);
        game.play(2, 2);

        assertThat(game.availableStates()).isEmpty();
    }

    // -- hasWin

    @Test
    public void hasWinRow() {
        game.play(0, 0);
        game.play(0, 1);
        game.play(0, 2);
        assertThat(game.hasWin(Player.X));
    }

    @Test
    public void hasWinCol() {
        game.play(0, 0);
        game.play(1, 0);
        game.play(2, 0);
        assertThat(game.hasWin(Player.X));
    }

    @Test
    public void hasWinDiagonal() {
        game.play(0, 0);
        game.play(1, 1);
        game.play(2, 2);
        assertThat(game.hasWin(Player.X));
    }

    // -- isOver
    
    @Test
    public void isOverWin() {
        game.play(0, 0);
        game.play(0, 1);
        game.play(0, 2);
        assertThat(game.isOver()).isTrue();
    }

    @Test
    public void isOverDraw() {
        // XOX
        // OXX
        // OXO
        game.play(0, 0);
        game.play(0, 2);
        game.play(1, 1);
        game.play(1, 2);
        game.play(2, 1);
        game.switchPlayer();
        game.play(0, 1);
        game.play(1, 0);
        game.play(2, 0);
        game.play(2, 2);

        assertThat(game.isOver()).isTrue();
    }

    // -- play
    
    @Test
    public void playOnBoard() {
        assertThat(game.play(0, 0)).isTrue();
        assertThat(game.getLastMove()).isEqualTo(new Position(0, 0));
    }
    
    @Test
    public void playOffBoard() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("(-1,0) is off the board");
        game.play(-1, 0);
    }

    @Test
    public void playSameLocation() {
        assertThat(game.play(0, 0)).isTrue();
        assertThat(game.play(0, 1)).isTrue();
        // should not affect the last move
        assertThat(game.play(0, 0)).isFalse();
        assertThat(game.getLastMove()).isEqualTo(new Position(0, 1));
    }

    // -- switchPlayer

    @Test
    public void switchPlayer() {
        assertThat(game.getCurrentPlayer()).isEqualTo(Player.X);
        game.switchPlayer();
        assertThat(game.getCurrentPlayer()).isEqualTo(Player.O);
        game.switchPlayer();
        assertThat(game.getCurrentPlayer()).isEqualTo(Player.X);
    }
}

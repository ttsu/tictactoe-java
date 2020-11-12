package ttsu.game.tictactoe;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ttsu.game.Block;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

import java.awt.*;

import static org.fest.assertions.Assertions.assertThat;


public class ParserTest {

    private GameBoard board;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        board = new GameBoard();
    }

//    @Test
//    public void getOpenPositions() {
//        board.mark(0, 0, Player.X);
//        assertThat(board.getOpenPositions()).containsOnly(new Block(0, 1), new Block(0, 2),
//                new Block(1, 0), new Block(1, 1), new Block(1, 2), new Block(2, 0),
//                new Block(2, 1), new Block(2, 2));
//    }
}

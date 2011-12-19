package game.ai;

import static game.TicTacToe.Player.O;
import static game.TicTacToe.Player.X;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import game.DiscreteGameState;
import game.GameBoard;
import game.TicTacToe;
import game.TicTacToe.Player;
import game.ai.heuristic.TicTacToeEvaluator;

import org.fest.util.Collections;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TicTacToeMinimaxAgentTest {

    private MinimaxAgent<TicTacToe> agent;

    @Mock
    private TicTacToeEvaluator evaluator;

    @Mock
    private TicTacToe gameState;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        agent = new MinimaxAgent<TicTacToe>(evaluator);
    }

    @Test
    public void evaluateLeaf() {
        MinimaxAgent<TicTacToe> minimaxEval = new MinimaxAgent<TicTacToe>(evaluator);
        assertThat(minimaxEval.evaluateNextState(gameState, 0)).isNull();
    }

    @Test
    public void evaluateNegativeDepth() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("depth cannot be less than zero. depth=-1");
        agent.evaluateNextState(gameState, -1);
    }

    // choose winning move
    @Test
    public void preferWinningState() {
        TicTacToe winState = mock(TicTacToe.class);
        TicTacToe drawState = mock(TicTacToe.class);
        when(winState.isOver()).thenReturn(true);
        when(drawState.isOver()).thenReturn(true);
        when(evaluator.evaluate(winState)).thenReturn(100);
        when(evaluator.evaluate(drawState)).thenReturn(0);
        when(gameState.availableStates()).thenReturn(Collections.<DiscreteGameState> list(winState, drawState));

        TicTacToe actualState = agent.evaluateNextState(gameState);
        assertThat(actualState).isSameAs(winState);
    }

    // prevent loss. this is essentially the same as above...
    @Test
    public void preventLosingMove() {
        TicTacToe loseState = mock(TicTacToe.class);
        TicTacToe drawState = mock(TicTacToe.class);
        when(loseState.isOver()).thenReturn(true);
        when(drawState.isOver()).thenReturn(true);
        when(evaluator.evaluate(loseState)).thenReturn(-1);
        when(evaluator.evaluate(drawState)).thenReturn(0);
        when(gameState.availableStates()).thenReturn(Collections.<DiscreteGameState> list(loseState, drawState));

        TicTacToe actualState = agent.evaluateNextState(gameState);
        assertThat(actualState).isSameAs(drawState);
    }

    @Test
    public void preferEarlyWin() {
        TicTacToe gameState = new TicTacToe(new GameBoard(new Player[][] { 
                { O, null, null }, 
                { O, X, X },
                { null, null, X } }), O);
        MinimaxAgent<TicTacToe> agent = new MinimaxAgent<TicTacToe>(new TicTacToeEvaluator(O));
        TicTacToe actualState = agent.evaluateNextState(gameState);
        assertThat(actualState.hasWin(O)).isTrue();
    }

    // /game already over
    @Test
    public void evaluateGameAlreadyOver() {
        when(gameState.isOver()).thenReturn(true);
        assertThat(agent.evaluateNextState(gameState)).isNull();
    }

}

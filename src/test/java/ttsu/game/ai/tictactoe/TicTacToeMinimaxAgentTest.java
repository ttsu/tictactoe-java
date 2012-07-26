package ttsu.game.ai.tictactoe;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ttsu.game.tictactoe.TicTacToeGameState.Player.O;
import static ttsu.game.tictactoe.TicTacToeGameState.Player.X;

import org.fest.util.Collections;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ttsu.game.DiscreteGameState;
import ttsu.game.ai.MinimaxAgent;
import ttsu.game.ai.heuristic.tictactoe.TicTacToeEvaluator;
import ttsu.game.tictactoe.GameBoard;
import ttsu.game.tictactoe.TicTacToeGameState;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

@RunWith(MockitoJUnitRunner.class)
public class TicTacToeMinimaxAgentTest {

  private MinimaxAgent<TicTacToeGameState> agent;

  @Mock
  private TicTacToeEvaluator evaluator;

  @Mock
  private TicTacToeGameState gameState;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() {
    agent = new MinimaxAgent<TicTacToeGameState>(evaluator);
  }

  @Test
  public void evaluateLeaf() {
    MinimaxAgent<TicTacToeGameState> minimaxEval = new MinimaxAgent<TicTacToeGameState>(evaluator);
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
    TicTacToeGameState winState = mock(TicTacToeGameState.class);
    TicTacToeGameState drawState = mock(TicTacToeGameState.class);
    when(winState.isOver()).thenReturn(true);
    when(drawState.isOver()).thenReturn(true);
    when(evaluator.evaluate(winState)).thenReturn(100);
    when(evaluator.evaluate(drawState)).thenReturn(0);
    when(gameState.availableStates()).thenReturn(
        Collections.<DiscreteGameState>list(winState, drawState));

    TicTacToeGameState actualState = agent.evaluateNextState(gameState);
    assertThat(actualState).isSameAs(winState);
  }

  // prevent loss. this is essentially the same as above...
  @Test
  public void preventLosingMove() {
    TicTacToeGameState loseState = mock(TicTacToeGameState.class);
    TicTacToeGameState drawState = mock(TicTacToeGameState.class);
    when(loseState.isOver()).thenReturn(true);
    when(drawState.isOver()).thenReturn(true);
    when(evaluator.evaluate(loseState)).thenReturn(-1);
    when(evaluator.evaluate(drawState)).thenReturn(0);
    when(gameState.availableStates()).thenReturn(
        Collections.<DiscreteGameState>list(loseState, drawState));

    TicTacToeGameState actualState = agent.evaluateNextState(gameState);
    assertThat(actualState).isSameAs(drawState);
  }

  @Test
  public void preferEarlyWin() {
    TicTacToeGameState gameState =
        new TicTacToeGameState(new GameBoard(new Player[][] { {O, null, null}, {O, X, X},
            {null, null, X}}), O);
    MinimaxAgent<TicTacToeGameState> agent =
        new MinimaxAgent<TicTacToeGameState>(new TicTacToeEvaluator(O));
    TicTacToeGameState actualState = agent.evaluateNextState(gameState);
    assertThat(actualState.hasWin(O)).isTrue();
  }

  // /game already over
  @Test
  public void evaluateGameAlreadyOver() {
    when(gameState.isOver()).thenReturn(true);
    assertThat(agent.evaluateNextState(gameState)).isNull();
  }

}

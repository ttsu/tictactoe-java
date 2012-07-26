package ttsu.game.ai.heuristic.tictactoe;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ttsu.game.DiscreteGameState;
import ttsu.game.tictactoe.TicTacToeGameState;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

@RunWith(MockitoJUnitRunner.class)
public class TicTacToeEvaluatorTest {

  private TicTacToeEvaluator evaluator;
  @Mock
  private TicTacToeGameState game;
  @Mock
  private List<DiscreteGameState> availableStates;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setup() {
    evaluator = new TicTacToeEvaluator(Player.X);
  }

  // -- constructor

  @Test
  public void constructorNullPlayer() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("player cannot be null");
    new TicTacToeEvaluator(null);
  }

  // -- evaluate

  @Test
  public void evaluateWin() {
    when(game.hasWin(Player.X)).thenReturn(true);
    assertThat(evaluator.evaluate(game)).isEqualTo(1);
  }

  @Test
  public void evaluateWinConsidersAvailableMoves() {
    when(game.hasWin(Player.X)).thenReturn(true);
    when(game.availableStates()).thenReturn(availableStates);
    when(availableStates.size()).thenReturn(5);
    assertThat(evaluator.evaluate(game)).isEqualTo(6);
  }

  @Test
  public void evaluateLoss() {
    when(game.hasWin(Player.O)).thenReturn(true);
    assertThat(evaluator.evaluate(game)).isEqualTo(-1);
  }

  @Test
  public void evaluateDraw() {
    assertThat(evaluator.evaluate(game)).isEqualTo(0);
  }

  @Test
  public void evaluateNull() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("cannot evaluate null game");
    evaluator.evaluate(null);
  }
}

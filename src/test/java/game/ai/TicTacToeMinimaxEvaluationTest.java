package game.ai;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import game.DiscreteGameState;
import game.TicTacToe;
import game.ai.heuristic.TicTacToeEvaluator;

import org.fest.util.Collections;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TicTacToeMinimaxEvaluationTest {

	@Mock
	private TicTacToeEvaluator evaluator;

	@Mock
	private TicTacToe gameState;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void evaluateLeaf() {
		MinimaxEvaluation<TicTacToe> evaluation = new MinimaxEvaluation<TicTacToe>(
				evaluator, gameState, 0);
		assertThat(evaluation.getNextGameState()).isNull();
	}

	@Test
	public void evaluateNegativeDepth() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("depth cannot be less than zero. depth=-1");
		new MinimaxEvaluation<TicTacToe>(evaluator, gameState, -1);
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
		when(gameState.availableStates()).thenReturn(
				Collections.<DiscreteGameState> list(winState, drawState));

		TicTacToe actualState = new MinimaxEvaluation<TicTacToe>(evaluator,
				gameState).getNextGameState();
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
		when(gameState.availableStates()).thenReturn(
				Collections.<DiscreteGameState> list(loseState, drawState));

		TicTacToe actualState = new MinimaxEvaluation<TicTacToe>(evaluator,
				gameState).getNextGameState();
		assertThat(actualState).isSameAs(drawState);
	}

	// /game already over
	@Test
	public void evaluateGameAlreadyOver() {
		when(gameState.isOver()).thenReturn(true);
		MinimaxEvaluation<TicTacToe> evaluation = new MinimaxEvaluation<TicTacToe>(
				evaluator, gameState);
		assertThat(evaluation.getNextGameState()).isNull();
	}

}

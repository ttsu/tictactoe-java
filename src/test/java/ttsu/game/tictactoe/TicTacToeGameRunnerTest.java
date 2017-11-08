package ttsu.game.tictactoe;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Strings.join;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ttsu.game.ai.GameIntelligenceAgent;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

@RunWith(MockitoJUnitRunner.class)
public class TicTacToeGameRunnerTest {

  String line = System.getProperty("line.separator");

  @Mock
  private GameIntelligenceAgent<TicTacToeGameState> agent;
  @Mock
  private PrintStream printStream;

  @Test
  public void moveRandomlyComputerContinuesToAcceptInputUntilValid() {
    Scanner scanner = scannerWithInputs("", " 1, 1", "invalid", "-1,1", "3,1", "1,2,3", "0,0");
    TicTacToeGameRunner runner = new TicTacToeGameRunner(agent, scanner, printStream);

    runner.moveRandomlyComputer();

    verify(printStream, times(6)).println(TicTacToeGameRunner.INSTRUCTION_TEXT);
  }

  @Test
  public void moveRandomlyComputerErrorWhenOffBoard() {
    Scanner scanner = scannerWithInputs("-1,0", "3,3", "0,0");
    TicTacToeGameRunner runner = new TicTacToeGameRunner(agent, scanner, printStream);

    runner.moveRandomlyComputer();

    verify(printStream).printf("(%d,%d) is not on the board. ", -1, 0);
    verify(printStream).printf("(%d,%d) is not on the board. ", 3, 3);
    verify(printStream, times(2)).println(TicTacToeGameRunner.INSTRUCTION_TEXT);
  }

  @Test
  public void moveRandomlyComputerErrorWhenRepeatMove() {
    Scanner scanner = scannerWithInputs("1,1", "1,1", "0,0");
    TicTacToeGameRunner runner = new TicTacToeGameRunner(agent, scanner, printStream);

    runner.moveRandomlyComputer();
    runner.moveRandomlyComputer();

    verify(printStream).printf("(%d,%d) has already been taken. ", 1, 1);
    verify(printStream).println(TicTacToeGameRunner.INSTRUCTION_TEXT);
  }

  @Test
  public void moveRandomlyComputerSwitchesPlayers() {
    Scanner scanner = scannerWithInputs("1,1", "0,0");
    TicTacToeGameRunner runner = new TicTacToeGameRunner(agent, scanner, printStream);

    assertThat(runner.getGame().getCurrentPlayer()).isEqualTo(Player.X);
    runner.moveRandomlyComputer();
    assertThat(runner.getGame().getCurrentPlayer()).isEqualTo(Player.O);
  }

//  @Test
//  public void moveComputerSwitchesPlayers() {
//    TicTacToeGameRunner runner = new TicTacToeGameRunner(agent, new Scanner(""), printStream);
//    TicTacToeGameState nextState = mock(TicTacToeGameState.class);
//    when(nextState.getLastMove()).thenReturn(new Block(0, 0));
//    when(agent.evaluateNextState(Mockito.any(TicTacToeGameState.class))).thenReturn(nextState);
//
//    assertThat(runner.getGame().getCurrentPlayer()).isEqualTo(Player.X);
//    runner.moveComputer();
//    assertThat(runner.getGame().getCurrentPlayer()).isEqualTo(Player.O);
//  }

  // -- helper --

  private Scanner scannerWithInputs(String... inputs) {
    String joinedInputs = join(inputs).with("\n");
    Scanner scanner = new Scanner(joinedInputs);
    return scanner;
  }
}

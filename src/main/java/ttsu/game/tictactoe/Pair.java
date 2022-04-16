package ttsu.game.tictactoe;

/**
 * @author Digen Gill
 * 
 * Hold row and column pair for tic-tac-toe.
 */
public class Pair {

	int p1, p2;

	public Pair (int p1, int p2)
	{
		this.p1 = p1;
		this.p2 = p2;
	}

	public int getKey() {return this.p1;}
	public int getValue() {return this.p2;}



}

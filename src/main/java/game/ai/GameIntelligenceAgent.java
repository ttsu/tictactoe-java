package game.ai;

import game.DiscreteGameState;

public interface GameIntelligenceAgent<T extends DiscreteGameState> {

	T getNextGameState();

}
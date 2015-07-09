package com.evolutionandgames.agentbased.compact;


/**
 * This interface defines how the payoff should be specified. Given a population,
 * the fitness values should be set inside after the method has concluded. 
 * @author garcia
 *
 */
public interface CompactPopulationPayoffCalculator {
	
	void calculatePayoffs(CompactPopulation population);
}

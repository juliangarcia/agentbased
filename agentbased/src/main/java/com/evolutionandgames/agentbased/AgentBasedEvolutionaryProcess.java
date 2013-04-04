package com.evolutionandgames.agentbased;


/**
 * Interface for agent based evolutionary processes. 
 * @author garcia
 *
 */
public interface AgentBasedEvolutionaryProcess {
	
	/**
	 * one step, changes the population accordingly.
	 */
	public void step();
	
	
	/**
	 * Step without innovation, the same agents remain only changing the frequencies. 
	 */
	public void stepWithoutMutation();
	
	
	/**
	 * Returns the population on which this process operates. 
	 * @return ExtensivePopulation
	 */
	public AgentBasedPopulation getPopulation();
	
	
	/**
	 * Restart the population of the process, and set the timeStep to 0. 
	 * @param startingPopulation
	 */
	public void reset(AgentBasedPopulation startingPopulation);
	
	
	/**
	 * Total population payoff.
	 * @return the sum of individual payoffs in the population
	 */
	public double getTotalPopulationPayoff();
	
	
	/**
	 * Get the current timestep. every step increaseas this number.
	 * @return the current timestep
	 */
	public int getTimeStep();
	
	/**
	 * Provides a population for fixation computations
	 * @param size
	 * @param mutant
	 * @param incumbent
	 * @return
	 */
	public AgentBasedPopulation oneMutantPopulation(int size, Agent mutant, Agent incumbent);
	
}

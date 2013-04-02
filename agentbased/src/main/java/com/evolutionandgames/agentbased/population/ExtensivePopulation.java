package com.evolutionandgames.agentbased.population;

import com.evolutionandgames.agentbased.Agent;


/**
 * Interface for a population in an agent-based process. 
 * @author garcia
 *
 */
public interface ExtensivePopulation extends AgentBasedPopulation {
	
	/**
	 * Adds a new individual. Since the population size is fixed, an index has to be given of the indidividual to be replaced.
	 * Index runs from 0 to population size minus one. 
	 * @param agent individual to add
	 * @param position index of the individual to replace
	 */
	public void addOneIndividual(Agent agent, int position);
	
	
	/**
	 * Get the agent in a given position.
	 * @param index
	 * @return Agent
	 */
	public Agent getAgent(int index);
	
	
	/**
	 * Get the payoff of a certain agent.
	 * @param index
	 * @return double
	 */
	public double getPayoffOfAgent(int index);
	
	
	/**
	 * Set the payoff of an agent in a certain position. 
	 * @param index
	 * @param payoff
	 */
	public void setPayoffOfAgent(int index, double payoff);
	
	
	
	
	/**
	 * Returns the array of agents composing the population
	 * @return an array of Agent
	 */
	public Agent[] getAsArrayOfAgents();
	

	
	
}

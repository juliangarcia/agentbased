package com.evolutionandgames.agentbased;

import java.util.HashMap;
import java.util.Set;


public interface AgentBasedPopulation {
	
	/**
	 * How many copies of a given agent. Couting relies on the equals() method of the agent implementation.
	 * @param agent
	 * @return int
	 */
	public int getNumberOfCopies(Agent agent);
	
	
	/**
	 * Population size
	 * @return int
	 */
	public int getSize();
	
	
	/**
	 * A set containing Agents, no repetitions.  If the set size is one the population has fixated.
	 * @return A Set of Agent.
	 */
	public Set<Agent> getSetOfAgents();
	
	
	/**
	 * Gives a String representation of the population
	 * @return String
	 */
	public String toString();
	
	
	/**
	 * Additional method to return any extra info that may be required.
	 * @return
	 */
	public Object getExtraInfo();
	
	
	/**
	 * Returns a view of the population as a dictionary. 
	 * @return
	 */
	public HashMap<Agent, Integer> getDictionaryOfCopies();
	

}

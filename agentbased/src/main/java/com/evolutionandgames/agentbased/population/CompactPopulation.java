package com.evolutionandgames.agentbased.population;

import com.evolutionandgames.agentbased.Agent;

public interface CompactPopulation extends AgentBasedPopulation {
	
	public double getPayoffOfAgent(Agent agent);
	public void setPayoffOfAgent(Agent agent, double payoff);
	public int getNumberOfCopies(Agent agent);
	public void setNumberOfCopies(Agent agent, int copies);
	public void incrementNumberofCopies(Agent agent);
	public void decrementNumberofCopies(Agent agent);

}

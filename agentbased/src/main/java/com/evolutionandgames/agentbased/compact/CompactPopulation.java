package com.evolutionandgames.agentbased.compact;

import com.evolutionandgames.agentbased.Agent;
import com.evolutionandgames.agentbased.AgentBasedPopulation;

public interface CompactPopulation extends AgentBasedPopulation {
	
	public double getPayoffOfAgent(Agent agent);
	public void setPayoffOfAgent(Agent agent, double payoff);
	public int getNumberOfCopies(Agent agent);
	public void setNumberOfCopies(Agent agent, int copies);
	public void incrementNumberofCopies(Agent agent);
	public void decrementNumberofCopies(Agent agent);

}

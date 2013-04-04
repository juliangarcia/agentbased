package com.evolutionandgames.agentbased.compact;

import java.util.HashMap;
import java.util.Set;

import com.evolutionandgames.agentbased.Agent;

public class CompactPopulationImpl implements CompactPopulation {
	
	private HashMap<Agent, Integer> populationComposition;
	private HashMap<Agent, Double> payoffs;
	
	//Holder for other info in the run that may be needed
	private Object extraInfo;
	
	

	public int getSize() {
		Integer sum= 0; 
	     for (Integer i:populationComposition.values())
	         sum = sum + i;
	     return sum;
	}

	public Set<Agent> getSetOfAgents() {
		return populationComposition.keySet();
	}

	public Object getExtraInfo() {
		return extraInfo;
	}

	public double getPayoffOfAgent(Agent agent) {
		return payoffs.get(agent);
	}

	public void setPayoffOfAgent(Agent agent, double payoff) {
		this.payoffs.put(agent, payoff);
	}

	public int getNumberOfCopies(Agent agent) {
		return this.populationComposition.get(agent);
	}

	public void setNumberOfCopies(Agent agent, int copies) {
		this.populationComposition.put(agent, copies);

	}

	public void incrementNumberofCopies(Agent agent) {
		this.populationComposition.put(agent, this.populationComposition.get(agent)+1);

	}

	public void decrementNumberofCopies(Agent agent) {
		this.populationComposition.put(agent, this.populationComposition.get(agent)-1);
	}

	public CompactPopulationImpl(HashMap<Agent, Integer> populationComposition) {
		super();
		this.populationComposition = populationComposition;
		this.payoffs = new HashMap<Agent, Double>();
		
	}

	public HashMap<Agent, Integer> getDictionaryOfCopies() {
		return this.populationComposition;
	}

}

package com.evolutionandgames.agentbased.compact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.evolutionandgames.agentbased.Agent;
import com.google.common.base.Joiner;

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
		Integer numberOfCopies = this.populationComposition.get(agent);
		if (numberOfCopies == null){
			this.populationComposition.put(agent, 1);
		}else if (numberOfCopies >=1 ) {
			this.populationComposition.put(agent, numberOfCopies+1);
		} else{
			throw new IllegalArgumentException("Type with 0 copies");
		}
	}
		
	public void decrementNumberofCopies(Agent agent) {
		Integer numberOfCopies = this.populationComposition.get(agent);
		if (numberOfCopies > 1){
			this.populationComposition.put(agent, numberOfCopies-1);
		}else if (numberOfCopies ==1 ) {
			this.populationComposition.remove(agent);
		} else{
			throw new IllegalArgumentException("Decrement a non-existing type");
		}
	}

	public CompactPopulationImpl(HashMap<Agent, Integer> populationComposition) {
		super();
		this.populationComposition = populationComposition;
		this.payoffs = new HashMap<Agent, Double>();
		
	}

	public HashMap<Agent, Integer> getDictionaryOfCopies() {
		return this.populationComposition;
	}

	@Override
	public String toString() {
		//prints as a python dictionary
		return pythonDictString(populationComposition);
	}
	
	private String pythonDictString(HashMap<Agent, Integer> composition){
		ArrayList<String> listOfStrings = new ArrayList<String>();
		for (Map.Entry<Agent, Integer> entry : composition.entrySet()) {
		    Agent key = entry.getKey();
		    Integer value = entry.getValue();
		    listOfStrings.add(key.toString()+":"+value.toString());
		}
		Joiner joiner = Joiner.on( "," ).skipNulls();
		return "{" + joiner.join(listOfStrings) + "}";
	}

}

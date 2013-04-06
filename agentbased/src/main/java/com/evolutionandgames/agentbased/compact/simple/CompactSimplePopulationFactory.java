package com.evolutionandgames.agentbased.compact.simple;

import java.util.HashMap;

import com.evolutionandgames.agentbased.Agent;
import com.evolutionandgames.agentbased.AgentBasedPopulation;
import com.evolutionandgames.agentbased.AgentBasedPopulationFactory;
import com.evolutionandgames.agentbased.compact.CompactPopulationImpl;

public class CompactSimplePopulationFactory implements
		AgentBasedPopulationFactory {
	
	private int populationSize;
	private Agent sampleAgent;


	public AgentBasedPopulation createPopulation() {
		HashMap<Agent, Integer> map = new HashMap<Agent, Integer>();
		map.put(sampleAgent, populationSize);
		return new CompactPopulationImpl(map);
	}


	public CompactSimplePopulationFactory(int populationSize, Agent sampleAgent) {
		super();
		this.populationSize = populationSize;
		this.sampleAgent = sampleAgent;
	}

}

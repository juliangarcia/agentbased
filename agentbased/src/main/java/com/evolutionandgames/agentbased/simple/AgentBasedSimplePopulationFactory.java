package com.evolutionandgames.agentbased.simple;

import com.evolutionandgames.agentbased.Agent;
import com.evolutionandgames.agentbased.ExtensivePopulation;
import com.evolutionandgames.agentbased.AgentBasedPopulationFactory;
import com.evolutionandgames.agentbased.impl.AgentBasedPopulationImpl;



/***
 * ExtensivePopulation factory, creates a simple population with given copies of the same Agent
 * @author garcia
 *
 */
public class AgentBasedSimplePopulationFactory implements
		AgentBasedPopulationFactory {
	
	private int populationSize;
	private Agent sampleAgent;
	
	

	public ExtensivePopulation createPopulation() {
		Agent[] agentArray = new AgentSimple[this.populationSize];
		for (int i = 0; i < agentArray.length; i++) {
			agentArray[i] = this.sampleAgent;
		}
		return new AgentBasedPopulationImpl(agentArray);
	}


	/***
	 * Initializes this factory
	 * @param populationSize 
	 * @param sampleAgent
	 */
	public AgentBasedSimplePopulationFactory(int populationSize,
			Agent sampleAgent) {
		super();
		this.populationSize = populationSize;
		this.sampleAgent = sampleAgent;
	}

}

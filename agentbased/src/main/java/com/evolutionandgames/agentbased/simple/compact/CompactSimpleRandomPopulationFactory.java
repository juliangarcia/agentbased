package com.evolutionandgames.agentbased.simple.compact;

import java.util.HashMap;

import com.evolutionandgames.agentbased.Agent;
import com.evolutionandgames.agentbased.AgentBasedPopulationFactory;
import com.evolutionandgames.agentbased.compact.CompactPopulation;
import com.evolutionandgames.agentbased.compact.CompactPopulationImpl;
import com.evolutionandgames.agentbased.simple.AgentSimple;
import com.evolutionandgames.jevodyn.utils.Random;

public class CompactSimpleRandomPopulationFactory implements
		AgentBasedPopulationFactory {

	private boolean edge = false;
	private int numberOfTypes;
	private int populationSize;

	public CompactSimpleRandomPopulationFactory(int numberOfTypes,
			int populationSize) {
		this.edge = false;
		this.numberOfTypes = numberOfTypes;
		this.populationSize = populationSize;
	}
	
	public CompactSimpleRandomPopulationFactory(int numberOfTypes,
			int populationSize, boolean edge) {
		this.edge = edge;
		this.numberOfTypes = numberOfTypes;
		this.populationSize = populationSize;
	}

	public CompactPopulation createPopulation() {
		if (edge)
			return createEdgePopulation();
		return createRandomPopulation();
	}

	private CompactPopulation createRandomPopulation() {
		HashMap<Agent, Integer> map = new HashMap<Agent, Integer>();
		map.put(new AgentSimple(Random.nextInt(this.numberOfTypes)), 1);
		CompactPopulationImpl ans = new CompactPopulationImpl(map);
		for (int i = 1; i < populationSize; i++) {
			int strategy = Random.nextInt(this.numberOfTypes);
			ans.incrementNumberofCopies(new AgentSimple(strategy));
		}
		return ans;
	}

	private CompactPopulation createEdgePopulation() {
		HashMap<Agent, Integer> map = new HashMap<Agent, Integer>();
		AgentSimple type1 = new AgentSimple(Random.nextInt(this.numberOfTypes));
		AgentSimple type2 = type1;
		while (type2.equals(type1)) {
			type2 = new AgentSimple(Random.nextInt(this.numberOfTypes));
		}
		if (Random.nextBoolean()) {
			map.put(type1, 1);
		} else {
			map.put(type2, 1);
		}
		CompactPopulationImpl ans = new CompactPopulationImpl(map);
		for (int i = 1; i < populationSize; i++) {
			if (Random.nextBoolean()) {
				ans.incrementNumberofCopies(type1);
			} else {
				ans.incrementNumberofCopies(type2);
			}
		}
		return ans;
	}

}

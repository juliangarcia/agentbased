package com.evolutionandgames.agentbased.compact;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.evolutionandgames.agentbased.Agent;
import com.evolutionandgames.agentbased.AgentBasedPayoffCalculator;
import com.evolutionandgames.agentbased.AgentBasedPopulation;
import com.evolutionandgames.agentbased.AgentMutator;
import com.evolutionandgames.agentbased.compact.AgentBasedMoranProcess;
import com.evolutionandgames.agentbased.compact.CompactPopulation;
import com.evolutionandgames.agentbased.compact.CompactPopulationImpl;
import com.evolutionandgames.agentbased.simple.AgentMutatorSimpleKernel;
import com.evolutionandgames.agentbased.simple.AgentSimple;
import com.evolutionandgames.jevodyn.utils.ArrayUtils;
import com.evolutionandgames.jevodyn.utils.PayoffToFitnessMapping;
import com.evolutionandgames.jevodyn.utils.Random;


public class AgentBasedMoranProcessTest {
	
	private class EveryBodyGetsOnePayoffCalculator implements AgentBasedPayoffCalculator {
		public void calculatePayoffs(AgentBasedPopulation population) {
			for (Iterator<Agent> iterator = population.getSetOfAgents().iterator(); iterator.hasNext();) {
				Agent agent = (Agent) iterator.next();
				((CompactPopulationImpl)population).setPayoffOfAgent(agent, 1.0);
			}
			
		}

	}

	private static final double DELTA = 0.001;

	@Before
	public void setUp() throws Exception {
	}

	
	@Test
	public void testStepIsInvariableInPopulationSize() {
		Random.seed();
		for (int j = 0; j < 5; j++) {
			int numberOfTypes = 2;
			int maximumNumberOfCopiesPerType = 10;
			CompactPopulation population  = randomPopulation(numberOfTypes, maximumNumberOfCopiesPerType);
			int populationSize = population.getSize();
			AgentBasedPayoffCalculator payoffCalculator = new EveryBodyGetsOnePayoffCalculator();
			AgentMutator mutator = new AgentMutatorSimpleKernel(ArrayUtils.uniformMutationKernel(0.1, 2));
			AgentBasedMoranProcess mp = new AgentBasedMoranProcess(population, payoffCalculator, PayoffToFitnessMapping.LINEAR, 1.0, mutator);
			for (int i = 0; i < 1000; i++) {
				mp.step();
				assertEquals(mp.getPopulation().getSize(), populationSize);
			}
		}
	}
	
	
	@Test
	public void testTotalPayoff() {
		Random.seed();
		for (int j = 0; j < 5; j++) {
			int numberOfTypes = 3;
			int maximumNumberOfCopiesPerType = 10;
			CompactPopulation population  = randomPopulation(numberOfTypes, maximumNumberOfCopiesPerType);
			int populationSize = population.getSize();
			AgentBasedPayoffCalculator payoffCalculator = new EveryBodyGetsOnePayoffCalculator();
			AgentMutator mutator = new AgentMutatorSimpleKernel(ArrayUtils.uniformMutationKernel(0.1, 3));
			AgentBasedMoranProcess mp = new AgentBasedMoranProcess(population, payoffCalculator, PayoffToFitnessMapping.LINEAR, 1.0, mutator);
			for (int i = 0; i < 1000; i++) {
				mp.step();
				assertEquals((double)populationSize, mp.getTotalPopulationPayoff(), DELTA);
			}
		}
	}

	private CompactPopulation randomPopulation(int numberOfTypes,int maximumNumberOfCopiesPerType) {
		HashMap<Agent, Integer> composition = new HashMap<Agent, Integer>();
		for (int i = 0; i < numberOfTypes; i++) {
			composition.put(new AgentSimple(i), Random.nextInt(maximumNumberOfCopiesPerType-1) +1);
		}
		return new CompactPopulationImpl(composition);
	}

}

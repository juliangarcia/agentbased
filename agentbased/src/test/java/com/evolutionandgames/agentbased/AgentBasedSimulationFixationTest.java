package com.evolutionandgames.agentbased;

import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Assert;
import org.junit.Test;

import com.evolutionandgames.agentbased.Agent;
import com.evolutionandgames.agentbased.AgentBasedEvolutionaryProcess;
import com.evolutionandgames.agentbased.AgentBasedPayoffCalculator;
import com.evolutionandgames.agentbased.AgentBasedPopulation;
import com.evolutionandgames.agentbased.AgentBasedPopulationFactory;
import com.evolutionandgames.agentbased.AgentBasedSimulation;
import com.evolutionandgames.agentbased.AgentMutator;
import com.evolutionandgames.agentbased.impl.AgentBasedPopulationImpl;
import com.evolutionandgames.agentbased.impl.AgentBasedWrightFisherProcessWithAssortment;
import com.evolutionandgames.agentbased.simple.AgentBasedSimpleRandomPopulationFactory;
import com.evolutionandgames.agentbased.simple.AgentMatrixBasedPayoffCalculator;
import com.evolutionandgames.agentbased.simple.AgentMutatorSimpleKernel;
import com.evolutionandgames.agentbased.simple.AgentSimple;
import com.evolutionandgames.jevodyn.utils.ArrayUtils;
import com.evolutionandgames.jevodyn.utils.Games;
import com.evolutionandgames.jevodyn.utils.PayoffToFitnessMapping;
import com.evolutionandgames.jevodyn.utils.Random;


public class AgentBasedSimulationFixationTest {
	
	private static final double DELTA = 0.01;

	@Test
	public void testEstimateFixationProbabilityNeutral() {
		Long seed = System.currentTimeMillis();
		Random.seed();
		double intensityOfSelection = 0.0;
		double mutationProbability = 0.01;
		int numberOfTypes = 2;
		double r= 0.0;
		RealMatrix gameMatrix = Games.prionersDilemma();
		int populationSize = 10;
		AgentMutator mutator = new AgentMutatorSimpleKernel(ArrayUtils.uniformMutationKernel(mutationProbability, numberOfTypes));
		AgentBasedPayoffCalculator payoffCalculator = new AgentMatrixBasedPayoffCalculator(gameMatrix);
		AgentBasedPopulationFactory factory = new AgentBasedSimpleRandomPopulationFactory(numberOfTypes, populationSize);
		Agent[] agentArray = ((AgentBasedPopulationImpl)factory.createPopulation()).getAsArrayOfAgents();
		AgentBasedPopulation population = new  AgentBasedPopulationImpl(agentArray);
		AgentBasedEvolutionaryProcess wf = new AgentBasedWrightFisherProcessWithAssortment(population, payoffCalculator, 
				PayoffToFitnessMapping.EXPONENTIAL, intensityOfSelection, mutator, r);
		AgentBasedSimulation simulation = new AgentBasedSimulation(wf);
		int numberOfSamples = 500000;
		double fixation = simulation.estimateFixationProbability(new AgentSimple(1), new AgentSimple(0), numberOfSamples, seed);
		Assert.assertEquals(1.0/populationSize, fixation, DELTA);
	}

}
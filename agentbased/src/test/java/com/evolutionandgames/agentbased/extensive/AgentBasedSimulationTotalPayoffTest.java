package com.evolutionandgames.agentbased.extensive;

import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Assert;
import org.junit.Test;

import com.evolutionandgames.agentbased.Agent;
import com.evolutionandgames.agentbased.AgentBasedEvolutionaryProcess;
import com.evolutionandgames.agentbased.AgentBasedPayoffCalculator;
import com.evolutionandgames.agentbased.AgentBasedPopulationFactory;
import com.evolutionandgames.agentbased.AgentBasedSimulation;
import com.evolutionandgames.agentbased.AgentMutator;
import com.evolutionandgames.agentbased.extensive.AgentBasedWrightFisherProcessWithAssortment;
import com.evolutionandgames.agentbased.extensive.ExtensivePopulation;
import com.evolutionandgames.agentbased.extensive.ExtensivePopulationImpl;
import com.evolutionandgames.agentbased.simple.AgentMutatorSimpleKernel;
import com.evolutionandgames.agentbased.simple.extensive.ExtensivePopulationMatrixBasedPayoffCalculator;
import com.evolutionandgames.agentbased.simple.extensive.ExtensiveSimpleRandomPopulationFactory;
import com.evolutionandgames.jevodyn.utils.ArrayUtils;
import com.evolutionandgames.jevodyn.utils.Games;
import com.evolutionandgames.jevodyn.utils.PayoffToFitnessMapping;
import com.evolutionandgames.jevodyn.utils.Random;


public class AgentBasedSimulationTotalPayoffTest {
	
	private static final double DELTA = 1.0;

	@Test
	public void testEstimateFixationProbabilityNeutral() {
		//for neutral selection should spend half time in each state
		Long seed = System.currentTimeMillis();
		Random.seed();
		double intensityOfSelection = 0.0;
		double mutationProbability = 0.01;
		int numberOfTypes = 2;
		double r= 0.0;
		RealMatrix gameMatrix = Games.prionersDilemma();
		int populationSize = 10;
		AgentMutator mutator = new AgentMutatorSimpleKernel(ArrayUtils.uniformMutationKernel(mutationProbability, numberOfTypes));
		AgentBasedPayoffCalculator payoffCalculator = new ExtensivePopulationMatrixBasedPayoffCalculator(gameMatrix);
		AgentBasedPopulationFactory factory = new ExtensiveSimpleRandomPopulationFactory(numberOfTypes, populationSize);
		Agent[] agentArray = ((ExtensivePopulationImpl)factory.createPopulation()).getAsArrayOfAgents();
		ExtensivePopulation population = new  ExtensivePopulationImpl(agentArray);
		AgentBasedEvolutionaryProcess wf = new AgentBasedWrightFisherProcessWithAssortment(population, payoffCalculator, 
				PayoffToFitnessMapping.EXPONENTIAL, intensityOfSelection, mutator, r);
		AgentBasedSimulation simulation = new AgentBasedSimulation(wf);
		int burningTimePerEstimate = 100;
		int samplesPerEstimate = 500;
		int numberOfEstimates = 10;
		int reportEveryTimeSteps = 10;
		double totalPayoff = simulation.estimateTotalPayoff(burningTimePerEstimate, samplesPerEstimate, numberOfEstimates, reportEveryTimeSteps, seed, factory); 
		double expectedPayoff = (gameMatrix.getEntry(0, 0)*0.5 + gameMatrix.getEntry(1, 1)*0.5)*populationSize; 
		//System.out.println(totalPayoff);
		//System.out.println(expectedPayoff);
		Assert.assertEquals(expectedPayoff, totalPayoff, DELTA);
	}

}

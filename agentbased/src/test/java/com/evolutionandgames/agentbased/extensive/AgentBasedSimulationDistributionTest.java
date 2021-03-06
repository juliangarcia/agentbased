package com.evolutionandgames.agentbased.extensive;

import java.util.Map;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
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
import com.evolutionandgames.agentbased.simple.AgentSimple;
import com.evolutionandgames.agentbased.simple.extensive.ExtensivePopulationMatrixBasedPayoffCalculator;
import com.evolutionandgames.agentbased.simple.extensive.ExtensiveSimpleRandomPopulationFactory;
import com.evolutionandgames.jevodyn.utils.ArrayUtils;
import com.evolutionandgames.jevodyn.utils.Games;
import com.evolutionandgames.jevodyn.utils.PayoffToFitnessMapping;
import com.evolutionandgames.jevodyn.utils.Random;


public class AgentBasedSimulationDistributionTest {
	
	private static final double DELTA = 0.01;

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
		int samplesPerEstimate = 500000;
		int numberOfEstimates = 10;
		int reportEveryTimeSteps = 2;
		Map<Agent, Double> map = simulation.estimateStationaryDistribution(burningTimePerEstimate, samplesPerEstimate, numberOfEstimates, reportEveryTimeSteps ,seed, factory);
		Assert.assertEquals(2, map.size());
		System.out.println(map);
		for (Map.Entry<Agent, Double> entry : map.entrySet()){
			System.out.println(entry.getKey().getClass());
		}
		Assert.assertEquals(0.5, map.get(new AgentSimple(0)), DELTA);
		Assert.assertEquals(0.5, map.get(new AgentSimple(1)), DELTA);
	}
	
	@Test
	public void testEstimateFixationProbabilityNeutral3() {
		//for neutral selection should spend half time in each state
		Long seed = System.currentTimeMillis();
		Random.seed();
		double intensityOfSelection = 0.0;
		double mutationProbability = 0.01;
		int numberOfTypes = 3;
		double r= 0.0;
		RealMatrix gameMatrix = Games.allcTftAlld();
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
		int samplesPerEstimate = 500000;
		int numberOfEstimates = 10;
		int reportEveryTimeSteps = 2;
		Map<Agent, Double> map = simulation.estimateStationaryDistribution(burningTimePerEstimate, samplesPerEstimate, numberOfEstimates, reportEveryTimeSteps, seed, factory);
		Assert.assertEquals(3, map.size());
		//System.out.println(map);
		System.out.println(map.get(new AgentSimple(0)));
		Assert.assertEquals(1.0/3.0, map.get(new AgentSimple(0)), DELTA);
		Assert.assertEquals(1.0/3.0, map.get(new AgentSimple(1)), DELTA);
		Assert.assertEquals(1.0/3.0, map.get(new AgentSimple(2)), DELTA);
	}
	
	@Test
	public void testEstimateFixationProbabilityNeutralN() {
		//for neutral selection should spend half time in each state
		Long seed = System.currentTimeMillis();
		Random.seed();
		double intensityOfSelection = 0.0;
		double mutationProbability = 0.01;
		int numberOfTypes = 8;
		double r= 0.0;
		double d[][] = new double[8][8];  
		RealMatrix gameMatrix = new Array2DRowRealMatrix(d);
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
		int samplesPerEstimate = 500000;
		int numberOfEstimates = 10;
		int reportEveryTimeSteps = 2;
		Map<Agent, Double> map = simulation.estimateStationaryDistribution(burningTimePerEstimate, samplesPerEstimate, numberOfEstimates,  reportEveryTimeSteps, seed, factory);
		Assert.assertEquals(8, map.size());
		System.out.println(map);
		Assert.assertEquals(1.0/8.0, map.get(new AgentSimple(0)), DELTA);
		Assert.assertEquals(1.0/8.0, map.get(new AgentSimple(1)), DELTA);
		Assert.assertEquals(1.0/8.0, map.get(new AgentSimple(2)), DELTA);
		Assert.assertEquals(1.0/8.0, map.get(new AgentSimple(3)), DELTA);
		Assert.assertEquals(1.0/8.0, map.get(new AgentSimple(4)), DELTA);
		Assert.assertEquals(1.0/8.0, map.get(new AgentSimple(5)), DELTA);
		Assert.assertEquals(1.0/8.0, map.get(new AgentSimple(6)), DELTA);
		Assert.assertEquals(1.0/8.0, map.get(new AgentSimple(7)), DELTA);
	}



}

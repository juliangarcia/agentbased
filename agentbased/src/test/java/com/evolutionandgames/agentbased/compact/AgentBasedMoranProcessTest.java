package com.evolutionandgames.agentbased.compact;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Before;
import org.junit.Test;

import com.evolutionandgames.agentbased.Agent;
import com.evolutionandgames.agentbased.AgentBasedPayoffCalculator;
import com.evolutionandgames.agentbased.AgentBasedPopulation;
import com.evolutionandgames.agentbased.AgentBasedSimulation;
import com.evolutionandgames.agentbased.AgentMutator;
import com.evolutionandgames.agentbased.simple.AgentMutatorSimpleKernel;
import com.evolutionandgames.agentbased.simple.AgentSimple;
import com.evolutionandgames.agentbased.simple.compact.CompactPopulationMatrixPayoffCalculator;
import com.evolutionandgames.agentbased.simple.compact.CompactSimpleRandomPopulationFactory;
import com.evolutionandgames.jevodyn.utils.ArrayUtils;
import com.evolutionandgames.jevodyn.utils.Games;
import com.evolutionandgames.jevodyn.utils.PayoffToFitnessMapping;
import com.evolutionandgames.jevodyn.utils.Random;

public class AgentBasedMoranProcessTest {

	private class EveryBodyGetsOnePayoffCalculator implements
			 CompactPopulationPayoffCalculator {
		public void calculatePayoffs(CompactPopulation population) {
			for (Iterator<Agent> iterator = population.getSetOfAgents()
					.iterator(); iterator.hasNext();) {
				Agent agent = (Agent) iterator.next();
				((CompactPopulationImpl) population).setPayoffOfAgent(agent,
						1.0);
			}

		}

	}

	private static final double DELTA = 0.025;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testStepIsInvariableInPopulationSize() {
		Random.seed();
		for (int j = 0; j < 5; j++) {
			int numberOfTypes = 2;
			int maximumNumberOfCopiesPerType = 10;
			CompactPopulation population = randomPopulation(numberOfTypes,
					maximumNumberOfCopiesPerType);
			int populationSize = population.getSize();
			CompactPopulationPayoffCalculator payoffCalculator = new EveryBodyGetsOnePayoffCalculator();
			AgentMutator mutator = new AgentMutatorSimpleKernel(
					ArrayUtils.uniformMutationKernel(0.1, 2));
			AgentBasedMoranProcess mp = new AgentBasedMoranProcess(population,
					payoffCalculator, PayoffToFitnessMapping.LINEAR, 1.0,
					mutator);
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
			CompactPopulation population = randomPopulation(numberOfTypes,
					maximumNumberOfCopiesPerType);
			int populationSize = population.getSize();
			CompactPopulationPayoffCalculator payoffCalculator = new EveryBodyGetsOnePayoffCalculator();
			AgentMutator mutator = new AgentMutatorSimpleKernel(
					ArrayUtils.uniformMutationKernel(0.1, 3));
			AgentBasedMoranProcess mp = new AgentBasedMoranProcess(population,
					payoffCalculator, PayoffToFitnessMapping.LINEAR, 1.0,
					mutator);
			for (int i = 0; i < 1000; i++) {
				mp.step();
				assertEquals((double) populationSize,
						mp.getTotalPopulationPayoff(), DELTA);
			}
		}
	}

	private CompactPopulation randomPopulation(int numberOfTypes,
			int maximumNumberOfCopiesPerType) {
		HashMap<Agent, Integer> composition = new HashMap<Agent, Integer>();
		for (int i = 0; i < numberOfTypes; i++) {
			composition.put(new AgentSimple(i),
					Random.nextInt(maximumNumberOfCopiesPerType - 1) + 1);
		}
		return new CompactPopulationImpl(composition);
	}

	@Test
	public void testNeutralEvolution() {
		Random.seed();
		RealMatrix game = Games.hawkDoveGame();
		CompactPopulationMatrixPayoffCalculator payoffCalculator = new CompactPopulationMatrixPayoffCalculator(
				game);
		HashMap<Agent, Integer> mapa = new HashMap<Agent, Integer>();
		mapa.put(new AgentSimple(0), 5);
		mapa.put(new AgentSimple(1), 5);
		CompactPopulationImpl population = new CompactPopulationImpl(mapa);
		AgentMutator mutator = new AgentMutatorSimpleKernel(
				ArrayUtils.uniformMutationKernel(0.1, 2));
		AgentBasedMoranProcess mp = new AgentBasedMoranProcess(population,
				payoffCalculator, PayoffToFitnessMapping.LINEAR, 0.0, mutator);
		AgentBasedSimulation sim = new AgentBasedSimulation(mp);
		double estimate = sim.estimateFixationProbability(new AgentSimple(0),
				new AgentSimple(1), 100000,
				new Long(System.currentTimeMillis()));
		System.out.println(estimate);
		assertEquals(1.0 / 10, estimate, DELTA);
	}

	@Test
	public void testNonTrivialFixation() {
		Random.seed();
		RealMatrix game = Games.prionersDilemma();
		CompactPopulationMatrixPayoffCalculator payoffCalculator = new CompactPopulationMatrixPayoffCalculator(
				game);
		HashMap<Agent, Integer> mapa = new HashMap<Agent, Integer>();
		mapa.put(new AgentSimple(0), 5);
		mapa.put(new AgentSimple(1), 5);
		CompactPopulationImpl population = new CompactPopulationImpl(mapa);
		AgentMutator mutator = new AgentMutatorSimpleKernel(
				ArrayUtils.uniformMutationKernel(0.1, 2));
		AgentBasedMoranProcess mp = new AgentBasedMoranProcess(population,
				payoffCalculator, PayoffToFitnessMapping.EXPONENTIAL, 1.0,
				mutator);
		AgentBasedSimulation sim = new AgentBasedSimulation(mp);
		double estimate = sim.estimateFixationProbability(new AgentSimple(1),
				new AgentSimple(0), 1000000,
				new Long(System.currentTimeMillis()));
		System.out.println(estimate);
		assertEquals(0.7364040545619178, estimate, DELTA);

	}

	@Test
	public void testSimulateStationaryDistributionNeutral() {

		Random.seed();
		RealMatrix game = Games.allcTftAlld();
		CompactPopulationMatrixPayoffCalculator payoffCalculator = new CompactPopulationMatrixPayoffCalculator(
				game);
		HashMap<Agent, Integer> mapa = new HashMap<Agent, Integer>();
		mapa.put(new AgentSimple(0), 2);
		mapa.put(new AgentSimple(1), 2);
		CompactPopulationImpl population = new CompactPopulationImpl(mapa);
		AgentMutator mutator = new AgentMutatorSimpleKernel(
				ArrayUtils.uniformMutationKernel(0.01, 2));
		AgentBasedMoranProcess mp = new AgentBasedMoranProcess(population,
				payoffCalculator, PayoffToFitnessMapping.EXPONENTIAL, 0.0,
				mutator);
		AgentBasedSimulation sim = new AgentBasedSimulation(mp);
		int burningTimePerEstimate = 100;
		int samplesPerEstimate = 1000000;
		int numberOfEstimates = 10;
		int reportEveryTimeSteps = 5;
		int numberOfTypes = 2;
		int populationSize = 20;
		CompactSimpleRandomPopulationFactory factory = new CompactSimpleRandomPopulationFactory(
				numberOfTypes, populationSize);
		Map<Agent, Double> result = sim.estimateStationaryDistribution(
				burningTimePerEstimate, samplesPerEstimate, numberOfEstimates,
				reportEveryTimeSteps, new Long(System.currentTimeMillis()),
				factory);
		for (Iterator<Agent> iterator = result.keySet().iterator(); iterator
				.hasNext();) {
			Agent type = (Agent) iterator.next();
			assertEquals(1.0 / numberOfTypes, result.get(type), DELTA);
		}
		
	}
	
	@Test
	public void testSimulateStationaryDistribution() {
		////--------------
		
		Random.seed();
		RealMatrix game = Games.allcTftAlld();
		CompactPopulationMatrixPayoffCalculator payoffCalculator = new CompactPopulationMatrixPayoffCalculator(
				game);
		HashMap<Agent, Integer> mapa = new HashMap<Agent, Integer>();
		mapa.put(new AgentSimple(0), 2);
		mapa.put(new AgentSimple(1), 2);
		mapa.put(new AgentSimple(2), 6);
		CompactPopulationImpl population = new CompactPopulationImpl(mapa);
		AgentMutator mutator = new AgentMutatorSimpleKernel(
				ArrayUtils.uniformMutationKernel(0.001, 3));
		AgentBasedMoranProcess mp = new AgentBasedMoranProcess(population,
				payoffCalculator, PayoffToFitnessMapping.EXPONENTIAL, 1.0,
				mutator);
		AgentBasedSimulation sim = new AgentBasedSimulation(mp);
		int burningTimePerEstimate = 100;
		int samplesPerEstimate = 5000000;
		int numberOfEstimates = 10;
		int reportEveryTimeSteps = 1;
		int numberOfTypes = 3;
		int populationSize = 10;
		CompactSimpleRandomPopulationFactory factory = new CompactSimpleRandomPopulationFactory(
				numberOfTypes, populationSize);
		Map<Agent, Double> result = sim.estimateStationaryDistribution(
				burningTimePerEstimate, samplesPerEstimate, numberOfEstimates,
				reportEveryTimeSteps, new Long(System.currentTimeMillis()),
				factory);
		assertEquals(0.0799139, result.get(new AgentSimple(0)), DELTA);
		assertEquals(0.66839172, result.get(new AgentSimple(1)), DELTA);
		assertEquals(0.25169438, result.get(new AgentSimple(2)), DELTA);
		
		
		
		
		
	}

}

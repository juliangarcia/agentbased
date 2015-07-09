package com.evolutionandgames.agentbased.compact;

import java.util.HashMap;
import java.util.Iterator;

import com.evolutionandgames.agentbased.Agent;
import com.evolutionandgames.agentbased.AgentBasedEvolutionaryProcess;
import com.evolutionandgames.agentbased.AgentBasedPayoffCalculator;
import com.evolutionandgames.agentbased.AgentBasedPopulation;
import com.evolutionandgames.agentbased.AgentMutator;
import com.evolutionandgames.jevodyn.utils.ArrayUtils;
import com.evolutionandgames.jevodyn.utils.PayoffToFitnessMapping;
import com.evolutionandgames.jevodyn.utils.Random;





public class AgentBasedMoranProcess implements AgentBasedEvolutionaryProcess {
	
	private int timeStep = 0;
	private CompactPopulation population;
	private double totalPopulationPayoff;
	private CompactPopulationPayoffCalculator payoffCalculator;
	private PayoffToFitnessMapping mapping;
	private double intensityOfSelection;
	private AgentMutator mutator;

	public void step() {
		this.totalPopulationPayoff = 0.0;
		payoffCalculator.calculatePayoffs(population);
		HashMap<Integer, Agent> enumeration = this.enumerate();
		//compute array of frequencies and fitness
		int numberOfTypes = enumeration.size();
		double[] frequencies = new double[numberOfTypes];
		double[] payoffVector = new double[numberOfTypes];
		int populationSize = this.population.getSize();
		for (int i = 0; i < frequencies.length; i++) {
			frequencies[i]=  ((float) this.population.getDictionaryOfCopies().get(enumeration.get(i))) / populationSize;
			payoffVector[i] = ((float) this.population.getPayoffOfAgent(enumeration.get(i)));
			this.totalPopulationPayoff = this.totalPopulationPayoff + populationSize*frequencies[i]*payoffVector[i]; 
		}
		double[] fitness= computeFitness(frequencies, payoffVector);
		Agent chosenOne = enumeration.get(Random.simulateDiscreteDistribution(fitness));
		//mutation
		chosenOne = mutator.mutate(chosenOne);
		Agent dies = enumeration.get(Random.simulateDiscreteDistribution(frequencies));
		this.population.decrementNumberofCopies(dies);
		this.population.incrementNumberofCopies(chosenOne);
		timeStep = timeStep + 1;
	}
	
	
	public void stepWithoutMutation() {
		this.totalPopulationPayoff = 0.0;
		payoffCalculator.calculatePayoffs(population);
		HashMap<Integer, Agent> enumeration = this.enumerate();
		//compute array of frequencies and fitness
		int numberOfTypes = enumeration.size();
		double[] frequencies = new double[numberOfTypes];
		double[] payoffVector = new double[numberOfTypes];
		int populationSize = this.population.getSize();
		for (int i = 0; i < frequencies.length; i++) {
			Agent agentI = enumeration.get(i);
			frequencies[i]=  ((float) this.population.getDictionaryOfCopies().get(agentI)) / populationSize;
			payoffVector[i] = ((float) this.population.getPayoffOfAgent(agentI));
			this.totalPopulationPayoff = this.totalPopulationPayoff + payoffVector[i];
		}
		double[] fitness= computeFitness(frequencies, payoffVector);
		Agent chosenOne = enumeration.get(Random.simulateDiscreteDistribution(fitness));
		Agent dies = enumeration.get(Random.simulateDiscreteDistribution(frequencies));
		this.population.decrementNumberofCopies(dies);
		this.population.incrementNumberofCopies(chosenOne);
		timeStep = timeStep + 1;

	}
	

	private double[] computeFitness(double[] frequencies, double[] payoffVector) {
		double[] fitness = new double[frequencies.length];
		// Map fitness
		switch (mapping) {
		case LINEAR:
			for (int i = 0; i < fitness.length; i++) {
				fitness[i] = (frequencies[i])*(1.0 - intensityOfSelection + intensityOfSelection
						* payoffVector[i]);
			}
			break;
		case EXPONENTIAL:
			for (int i = 0; i < fitness.length; i++) {
				fitness[i] = (frequencies[i])*(Math.exp(intensityOfSelection * payoffVector[i]));
			}
			break;
		default:
			throw new IllegalArgumentException("Fitness mapping" + mapping.toString() + "is not implemented ");	
		}
		return ArrayUtils.normalize(fitness);
	}

	private HashMap<Integer, Agent> enumerate() {
		int index = 0;
		HashMap<Integer, Agent> ans = new HashMap<Integer, Agent>();
		for (Iterator<Agent> iterator = this.population.getDictionaryOfCopies().keySet().iterator(); iterator.hasNext();) {
			Agent agent= (Agent) iterator.next();
			ans.put(index, agent);
			index++;
		}
		return ans;
	}

	

	public AgentBasedPopulation getPopulation() {
		return this.population;
	}

	public void reset(AgentBasedPopulation startingPopulation) {
		this.timeStep = 0;
		this.population = (CompactPopulation) startingPopulation;

	}

	public double getTotalPopulationPayoff() {
		return this.totalPopulationPayoff;
	}

	public int getTimeStep() {
		return this.timeStep;
	}

	public AgentBasedPopulation oneMutantPopulation(int size, Agent mutant,
			Agent incumbent) {
		HashMap<Agent, Integer> composition = new HashMap<Agent, Integer>();
		composition.put(mutant, 1);
		composition.put(incumbent, size-1);
		return new CompactPopulationImpl(composition );
	}


	public AgentBasedMoranProcess(CompactPopulation population,
			AgentBasedPayoffCalculator payoffCalculator,
			PayoffToFitnessMapping mapping, double intensityOfSelection,
			AgentMutator mutator) {
		super();
		this.population = population;
		this.payoffCalculator = payoffCalculator;
		this.mapping = mapping;
		this.intensityOfSelection = intensityOfSelection;
		this.mutator = mutator;
	}

}

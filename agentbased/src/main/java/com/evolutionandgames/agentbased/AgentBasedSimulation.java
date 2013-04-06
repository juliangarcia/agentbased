package com.evolutionandgames.agentbased;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import com.evolutionandgames.jevodyn.utils.Random;
import com.google.common.util.concurrent.AtomicLongMap;

/**
 * Provides methods for simulating a given AgentBased process.
 * 
 * @author garcia
 * 
 */
public class AgentBasedSimulation {

	/**
	 * Process to be simulated
	 */
	private AgentBasedEvolutionaryProcess process;

	/***
	 * Default constructor takes a process.
	 * 
	 * @param process
	 */
	public AgentBasedSimulation(AgentBasedEvolutionaryProcess process) {
		super();
		this.process = process;
	}

	/***
	 * Estimate the fixation probability of mutant in a population of incumbent.
	 * 
	 * @param mutant
	 *            Agent
	 * @param incumbent
	 *            Agent
	 * @param numberOfSamples
	 * @param seed
	 * @return double
	 */
	public double estimateFixationProbability(Agent mutant, Agent incumbent,
			int numberOfSamples, Long seed) {
		Random.seed(seed);
		int populationSize = this.process.getPopulation().getSize();
		int positives = 0;
		for (int i = 0; i < numberOfSamples; i++) {
			this.process.reset(this.process.oneMutantPopulation(populationSize, mutant, incumbent));
			boolean fixated = false;
			// step until fixation is reached
			while (!fixated) {
				this.process.stepWithoutMutation();
				fixated = this.process.getPopulation().getSetOfAgents().size() == 1;
			}
			// increase positives if it fixated to the mutant type
			if (this.process.getPopulation().getSetOfAgents().iterator().next().equals(mutant))
				positives++;
		}
		return ((double) positives) / numberOfSamples;
	}
	
	

	

	private AtomicLongMap<Agent> sampleAndCount(int reportEveryTimeSteps,
			int numberOfEstimates, int burningTimePerEstimate,
			int samplesPerEstimate, AgentBasedPopulationFactory factory) {
		// estimate final size of the bag, to see if it will break
		if (numberOfEstimates * samplesPerEstimate
				* this.process.getPopulation().getSize() >= Long.MAX_VALUE) {
			throw new IllegalArgumentException(
					"The bag will break, it  will contain more items than Long can hold");
		}
		AtomicLongMap<Agent> map = AtomicLongMap.create();
		long bagSize = 0;
		for (int estimate = 0; estimate < numberOfEstimates; estimate++) {
			process.reset(factory.createPopulation());
			for (int burningStep = 0; burningStep < burningTimePerEstimate; burningStep++) {
				process.step();
			}
			long sample = 0;
			while (sample < samplesPerEstimate) {
				process.step();
				if (process.getTimeStep() % reportEveryTimeSteps == 0) {
					HashMap<Agent, Integer> composition = this.process.getPopulation().getDictionaryOfCopies();
					for (Iterator<Agent> iterator = composition.keySet().iterator(); iterator.hasNext();) {
						Agent type = iterator.next();
						int numberOfCopies = composition.get(type);
						map.addAndGet(type, numberOfCopies);
						bagSize = bagSize + numberOfCopies;
					}
					sample++;
				}
			}
		}
		if (bagSize >= Long.MAX_VALUE) {
			throw new IllegalArgumentException(
					"The bag is broken, it contains more items than a Long can hold");
		}
		return map;

	}

	/**
	 * Estimate stationary distribution
	 * 
	 * @param burningTimePerEstimate
	 *            for every estimate the chain is left running without taking
	 *            samples
	 * @param timeStepsPerEstimate
	 *            once burningTime is over, we start taking this number of
	 *            samples
	 * @param numberOfEstimates
	 *            the process is repeatedas many times as number of estimate
	 *            requires
	 * @param reportEveryTimeSteps
	 *            takes one sample every this number of steps.
	 * 
	 * @param seed
	 *            for reproduciblity.
	 * @param factory
	 *            a class that generates a new starting population for every
	 *            estimate.
	 * @return A Map of Agent to a double frequency.
	 */
	public Map<Agent, Double> estimateStationaryDistribution(
			int burningTimePerEstimate, int timeStepsPerEstimate,
			int numberOfEstimates, int reportEveryTimeSteps, Long seed,
			AgentBasedPopulationFactory factory) {
		Random.seed(seed);
		AtomicLongMap<Agent> multiset = sampleAndCount(reportEveryTimeSteps,
				numberOfEstimates, burningTimePerEstimate,
				timeStepsPerEstimate, factory);
		// build the answer
		long size = multiset.sum();
		Map<Agent, Long> multisetAsMap = multiset.asMap();
		// before we cared for order, now we do not
		// Map<Agent, Double> ans = new TreeMap<Agent, Double>(new
		// ValueComparator(multisetAsMap));
		Map<Agent, Double> ans = new HashMap<Agent, Double>();
		for (Map.Entry<Agent, Long> entry : multisetAsMap.entrySet()) {
			ans.put(entry.getKey(), (double) entry.getValue() / size);
		}
		return ans;
	}

	/**
	 * Simulates evolution writing the ouput to a file.
	 * 
	 * @param numberOfTimeSteps
	 * @param reportEveryTimeSteps
	 * @param seed
	 * @param fileName
	 * @param extraColumnProcessor
	 * @throws IOException
	 */
	public void simulateTimeSeries(int numberOfTimeSteps,
			int reportEveryTimeSteps, Long seed, String fileName,
			ExtraColumnsProcessor extraColumnProcessor) throws IOException {
		Random.seed(seed);
		ICsvListWriter listWriter = null;
		String[] header = this.buildHeader(extraColumnProcessor);
		CellProcessor[] processors = this.getProcessors(extraColumnProcessor);
		try {
			listWriter = new CsvListWriter(new FileWriter(fileName),
					CsvPreference.STANDARD_PREFERENCE);
			// write the header
			listWriter.writeHeader(header);
			// write the initial zero step content
			listWriter.write(
					this.currentStateRow(process, extraColumnProcessor),
					processors);
			// repeat for as many steps as requested
			for (int i = 0; i < numberOfTimeSteps; i++) {
				// step
				process.step();
				// if time to repor, report
				if (process.getTimeStep() % reportEveryTimeSteps == 0) {
					listWriter
							.write(this.currentStateRow(process,
									extraColumnProcessor), processors);
				}
			}
		} finally {
			// close files no matter what
			if (listWriter != null) {
				listWriter.close();
			}
		}

	}

	/**
	 * 
	 * @param numberOfTimeSteps
	 * @param reportEveryTimeSteps
	 * @param seed
	 * @param fileName
	 * @throws IOException
	 */
	public void simulateTimeSeries(int numberOfTimeSteps,
			int reportEveryTimeSteps, Long seed, String fileName)
			throws IOException {
		simulateTimeSeries(numberOfTimeSteps, reportEveryTimeSteps, seed,
				fileName, null);
	}

	/**
	 * Helper method to write the csv file
	 * 
	 * @return
	 */
	private CellProcessor[] getProcessors(
			ExtraColumnsProcessor extraColumnProcessor) {
		// TODO: does the next thing really need the final keyword
		CellProcessor[] processors;
		// now possible extra things
		if (extraColumnProcessor == null) {
			processors = new CellProcessor[3];
		} else {
			processors = new CellProcessor[3 + extraColumnProcessor
					.getNumberOfExtraColumns()];
		}
		for (int i = 0; i < processors.length; i++) {
			processors[i] = new NotNull();
		}
		return processors;
	}

	/**
	 * Helper method to write the csv file
	 * 
	 * @return
	 */
	private String[] buildHeader(ExtraColumnsProcessor extraColumnProcessor) {
		// build the header
		if (extraColumnProcessor == null) {
			final String[] header = { "timeStep", "totalPayoff", "population" };
			return header;
		} else {
			String header[] = new String[3 + extraColumnProcessor
					.getNumberOfExtraColumns()];
			String extras[] = extraColumnProcessor.getColumnHeaders();

			header[0] = "timeStep";
			header[1] = "totalPayoff";
			header[2] = "population";
			for (int i = 3; i < header.length; i++) {
				header[i] = extras[i - 3];
			}
			return header;
		}

	}

	/**
	 * Turns the current population into a list to be written in the csv file
	 * 
	 * @param process
	 * @param extraColumnProcessor
	 * @return
	 */
	private List<Object> currentStateRow(AgentBasedEvolutionaryProcess process,
			ExtraColumnsProcessor extraColumnProcessor) {
		ArrayList<Object> ans = new ArrayList<Object>();
		ans.add(process.getTimeStep());
		ans.add(process.getTotalPopulationPayoff());
		ans.add(process.getPopulation().toString());
		if (extraColumnProcessor != null) {
			Object[] extras = extraColumnProcessor.compute(process
					.getPopulation());
			for (int i = 0; i < extras.length; i++) {
				ans.add(extras[i]);
			}
		}
		return ans;
	}

	/**
	 * Estimate the total payoff, paramters are similar to those of estimate
	 * distribution. Instead of counting the agents, all we care about here is
	 * the population payoff
	 * 
	 * @param burningTimePerEstimate
	 * @param samplesPerEstimate
	 * @param numberOfEstimates
	 * @param reportEveryTimeSteps
	 * @param seed
	 * @param factory
	 * @return double
	 */
	public double estimateTotalPayoff(int burningTimePerEstimate,
			int samplesPerEstimate, int numberOfEstimates,
			int reportEveryTimeSteps, Long seed,
			AgentBasedPopulationFactory factory) {
		Random.seed(seed);
		double payoffSum = 0.0;
		int totalNumberOfSamples = 0;
		for (int estimate = 0; estimate < numberOfEstimates; estimate++) {
			process.reset(factory.createPopulation());
			for (int burningStep = 0; burningStep < burningTimePerEstimate; burningStep++) {
				process.step();
			}
			long sample = 0;
			while (sample < samplesPerEstimate) {
				// sample every time?
				process.step();
				if (process.getTimeStep() % reportEveryTimeSteps == 0) {
					payoffSum = payoffSum + process.getTotalPopulationPayoff();
					totalNumberOfSamples++;
					sample++;
				}
			}

		}
		return payoffSum / totalNumberOfSamples;
	}
}

package com.evolutionandgames.agentbased.simple.compact;

import java.util.Iterator;

import org.apache.commons.math3.linear.RealMatrix;

import com.evolutionandgames.agentbased.Agent;
import com.evolutionandgames.agentbased.compact.CompactPopulation;
import com.evolutionandgames.agentbased.compact.CompactPopulationImpl;
import com.evolutionandgames.agentbased.compact.CompactPopulationPayoffCalculator;
import com.evolutionandgames.agentbased.simple.AgentSimple;

public class CompactPopulationMatrixPayoffCalculator implements
		CompactPopulationPayoffCalculator {

	private RealMatrix gameMatrix;

	public CompactPopulationMatrixPayoffCalculator(RealMatrix gameMatrix) {
		super();
		this.gameMatrix = gameMatrix;
	}

	public void calculatePayoffs(CompactPopulation population) {
		double factorNumberOfInteractions = 1.0 / (population.getSize() - 1.0);
		for (Iterator<Agent> iterator = ((CompactPopulationImpl) population)
				.getDictionaryOfCopies().keySet().iterator(); iterator
				.hasNext();) {
			AgentSimple focal = (AgentSimple) iterator.next();
			double focalStrategyPayoff = 0.0;
			for (Iterator<Agent> innerIterator = ((CompactPopulationImpl) population)
					.getDictionaryOfCopies().keySet().iterator(); innerIterator
					.hasNext();) {
				AgentSimple oponent = (AgentSimple) innerIterator.next();
				if (!focal.equals(oponent)) {
					focalStrategyPayoff = focalStrategyPayoff
							+ ((CompactPopulationImpl) population)
									.getNumberOfCopies(oponent)
							* gameMatrix.getEntry(focal.getStrategy(),
									oponent.getStrategy());
				} else {
					focalStrategyPayoff = focalStrategyPayoff
							+ (((CompactPopulationImpl) population)
									.getNumberOfCopies(oponent) - 1)
							* gameMatrix.getEntry(focal.getStrategy(),
									oponent.getStrategy());
				}
			}
			((CompactPopulationImpl) population).setPayoffOfAgent(focal,
					factorNumberOfInteractions*focalStrategyPayoff);
		}

	}

}

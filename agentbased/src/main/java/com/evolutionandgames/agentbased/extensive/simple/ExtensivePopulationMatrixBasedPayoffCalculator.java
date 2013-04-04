package com.evolutionandgames.agentbased.extensive.simple;

import org.apache.commons.math3.linear.RealMatrix;

import com.evolutionandgames.agentbased.AgentBasedPayoffCalculator;
import com.evolutionandgames.agentbased.AgentBasedPopulation;
import com.evolutionandgames.agentbased.extensive.ExtensivePopulation;
import com.evolutionandgames.agentbased.simple.AgentSimple;


public class ExtensivePopulationMatrixBasedPayoffCalculator implements
		AgentBasedPayoffCalculator {

	private RealMatrix gameMatrix;
	
	
	public ExtensivePopulationMatrixBasedPayoffCalculator(RealMatrix gameMatrix) {
		super();
		this.gameMatrix = gameMatrix;
	}


	public void calculatePayoffs(AgentBasedPopulation population) {
		if (population.getSize()%2 !=0) throw new IllegalArgumentException("This class asumes that the population size is even");
		for (int i = 0; i < population.getSize()-1; i=i+2) {
			//i is focal, we set the fitness of i and i+1
			int focal = ((AgentSimple)((ExtensivePopulation) population).getAgent(i)).getStrategy();
			int other = ((AgentSimple)((ExtensivePopulation) population).getAgent(i+1)).getStrategy();
			((ExtensivePopulation) population).setPayoffOfAgent(i, gameMatrix.getEntry(focal, other));
			((ExtensivePopulation) population).setPayoffOfAgent(i+1, gameMatrix.getEntry(other, focal));
		}
	}

}

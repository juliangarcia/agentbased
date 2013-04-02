package com.evolutionandgames.agentbased;

import com.evolutionandgames.agentbased.population.AgentBasedPopulation;

/**
 * This interface allows customization of the time series data. By default the
 * time series includes generationTime, totalPayoff and population (as a
 * string), this would allow the file to contain extra columns
 * 
 * @author garcia
 * 
 */
public interface ExtraColumnsProcessor {
 
	public int getNumberOfExtraColumns();

	public String[] getColumnHeaders();

	public Object[] compute(AgentBasedPopulation population);

}

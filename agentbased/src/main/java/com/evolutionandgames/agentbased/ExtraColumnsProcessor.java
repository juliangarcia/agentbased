package com.evolutionandgames.agentbased;


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

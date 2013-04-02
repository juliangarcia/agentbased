package com.evolutionandgames.agentbased.population;

/**
 * This class defines a method to create populations. It is used in simulations, where different estimates typically depart from
 * a different (often random generated) population. 
 * @author garcia
 *
 */
public interface AgentBasedPopulationFactory {
	
	ExtensivePopulation createPopulation();

}

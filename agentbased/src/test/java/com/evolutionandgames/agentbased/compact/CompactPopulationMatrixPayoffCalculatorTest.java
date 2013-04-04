package com.evolutionandgames.agentbased.compact;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Test;

import com.evolutionandgames.agentbased.Agent;
import com.evolutionandgames.agentbased.simple.AgentSimple;
import com.evolutionandgames.jevodyn.utils.Games;

public class CompactPopulationMatrixPayoffCalculatorTest {

	private static final double DELTA = 0.001;

	@Test
	public void testGetPayoff() {
		RealMatrix game = Games.allcTftAlld();
		CompactPopulationMatrixPayoffCalculator payoffCalculator = new CompactPopulationMatrixPayoffCalculator(game);
		HashMap<Agent, Integer> mapa = new HashMap<Agent, Integer>();
		mapa.put(new AgentSimple(1), 7);
		mapa.put(new AgentSimple(2), 3);
		CompactPopulationImpl population = new CompactPopulationImpl(mapa );
		payoffCalculator.calculatePayoffs(population);
		//Harcoded values
		assertEquals(2.3166666666, population.getPayoffOfAgent(new AgentSimple(1)), DELTA);
		assertEquals(1.1166666, population.getPayoffOfAgent(new AgentSimple(2)), DELTA);
	}

}

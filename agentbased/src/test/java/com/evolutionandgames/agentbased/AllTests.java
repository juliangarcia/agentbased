package com.evolutionandgames.agentbased;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.evolutionandgames.agentbased.impl.AgentBasedPopulationImplTest;
import com.evolutionandgames.agentbased.impl.AgentBasedWrightFisherProcessWithAssortmentTest;
import com.evolutionandgames.agentbased.impl.AgentMutatorSimpleKernelTest;
import com.evolutionandgames.agentbased.simple.AgentBasedSimplePopulationFactoryTest;
import com.evolutionandgames.agentbased.simple.AgentBasedSimpleRandomPopulationFactoryTest;


@RunWith(Suite.class)
@SuiteClasses({AgentBasedPopulationImplTest.class,
			   AgentBasedWrightFisherProcessWithAssortmentTest.class,
			   AgentBasedSimulationTimeSeriesTest.class,
			   AgentBasedSimulationFixationTest.class,
			   AgentBasedSimulationDistributionTest.class,
			   AgentBasedSimulationTotalPayoffTest.class,
			   AgentBasedSimplePopulationFactoryTest.class,
			   AgentBasedSimpleRandomPopulationFactoryTest.class,
			   AgentMutatorSimpleKernelTest.class})

public class AllTests {

}

package com.evolutionandgames.agentbased;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.evolutionandgames.agentbased.extensive.AgentBasedPopulationImplTest;
import com.evolutionandgames.agentbased.extensive.AgentBasedSimplePopulationFactoryTest;
import com.evolutionandgames.agentbased.extensive.AgentBasedSimpleRandomPopulationFactoryTest;
import com.evolutionandgames.agentbased.extensive.AgentBasedSimulationDistributionTest;
import com.evolutionandgames.agentbased.extensive.AgentBasedSimulationFixationTest;
import com.evolutionandgames.agentbased.extensive.AgentBasedSimulationTimeSeriesTest;
import com.evolutionandgames.agentbased.extensive.AgentBasedSimulationTotalPayoffTest;
import com.evolutionandgames.agentbased.extensive.AgentBasedWrightFisherProcessWithAssortmentTest;


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

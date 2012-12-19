#agentbased#

##Basics##

This package provides a framework for **agent-based evolutionary dynamics with Java**. It provides a set of interfaces and a few implementations. So far the only process implemented is Wright-Fisher with assortment (as laid out in [this paper](http://www.pnas.org/content/early/2012/06/01/1206694109.abstract)), but the framework is extensible (pull requests welcome), and you could as well implement any other process you need.

Agent-based essentially means that fitness is a random variable, and that your individuals can be complex objects, with a state and methods of their own. In a typicall application you have to provide the following implementations.

* Agent.

* AgentBasedPayoffCalculator.



##Extending it##

Extend to other processes by implementing: 

AgentBasedEvolutionaryProcess


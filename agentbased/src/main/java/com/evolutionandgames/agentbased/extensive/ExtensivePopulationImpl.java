package com.evolutionandgames.agentbased.extensive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.evolutionandgames.agentbased.Agent;
import com.google.common.base.Joiner;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

public class ExtensivePopulationImpl implements ExtensivePopulation {

	public static final boolean EXTENSIVE_TO_STRING = false;
	private Agent[] agentArray;

	public static String FORMAT = "Strategy : %s, Count : %d";

	private double[] payoffsArray;
	private int size;

	// Holder for other info in the run that may be needed
	private Object extraInfo;

	public ExtensivePopulationImpl(Agent[] agentArray) {
		super();
		this.size = agentArray.length;
		this.agentArray = agentArray.clone();
		payoffsArray = new double[this.size];
	}

	public Agent[] getAsArrayOfAgents() {
		return agentArray;
	}

	public void addOneIndividual(Agent agent, int position) {
		agentArray[position] = agent;
	}

	public int getNumberOfCopies(Agent agent) {
		int ans = 0;
		for (int i = 0; i < agentArray.length; i++) {
			if (agentArray[i].equals(agent)) {
				ans++;
			}
		}
		return ans;
	}

	public int getSize() {
		return this.size;
	}

	public Set<Agent> getSetOfAgents() {
		return new HashSet<Agent>(Arrays.asList(agentArray));
	}

	public Agent getAgent(int index) {
		return agentArray[index];
	}

	public double getPayoffOfAgent(int index) {
		return payoffsArray[index];
	}

	public void setPayoffOfAgent(int index, double payoff) {
		this.payoffsArray[index] = payoff;
	}

	private String frequenciesToString() {
		// first get an ordered multiset from the
		Multiset<Agent> multiset = Multisets.copyHighestCountFirst(HashMultiset
				.create(Arrays.asList(this.agentArray)));
		List<String> stringView = new ArrayList<String>();
		for (Iterator<Agent> iterator = multiset.elementSet().iterator(); iterator
				.hasNext();) {
			Agent agent = (Agent) iterator.next();
			// stringView.add("Strategy : " + agent.toString() + ", Count : "+
			// multiset.count(agent));
			stringView.add(String.format(FORMAT, agent.toString(),
					multiset.count(agent)));
		}
		Joiner joiner = Joiner.on("; ").skipNulls();
		return joiner.join(stringView);
	}

	@Override
	public String toString() {
		if (EXTENSIVE_TO_STRING)
			return "Population [agents=" + Arrays.toString(agentArray)
					+ ", payoffs=" + Arrays.toString(payoffsArray) + "]";
		return frequenciesToString();
	}

	public Object getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(Object extraInfo) {
		this.extraInfo = extraInfo;
	}

	public HashMap<Agent, Integer> getDictionaryOfCopies() {
		HashMap<Agent, Integer> ans = new HashMap<Agent, Integer>();
		for (int i = 0; i < this.agentArray.length; i++) {
			if (!ans.containsKey(this.agentArray[i])) {
				ans.put(agentArray[i], 1);
			} else {
				ans.put(agentArray[i], ans.get(agentArray[i]) + 1);
			}
		}
		return ans;
	}

}

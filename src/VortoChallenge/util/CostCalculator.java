package VortoChallenge.util;

import java.util.ArrayList;

import VortoChallenge.models.Cluster;

public class CostCalculator {

	int numClusters;
    
	/*
	 * calculate the cost of traversal for all of the points in the cluster
	 */
	public static Float clusterCost(ArrayList<String> clusterStringList) {

		Float totalCost = (float) 0.0;
		for (String clusterString : clusterStringList) {
			totalCost += Cluster.calculateCost(clusterString);
		}
		return totalCost;
	}
}

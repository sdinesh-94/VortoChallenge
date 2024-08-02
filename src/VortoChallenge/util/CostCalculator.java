package VortoChallenge.util;

import java.util.ArrayList;

import VortoChallenge.models.Cluster;

public class CostCalculator {
	
	int numClusters;

	public static Float clusterCost(ArrayList<String> clusterStringList) {
		
		Float totalCost = (float) 0.0;
		for(String clusterString: clusterStringList) {
			totalCost += Cluster.calculateCost(clusterString);
		}
		return totalCost;
	}
	
}

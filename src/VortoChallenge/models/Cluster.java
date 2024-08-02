package VortoChallenge.models;

import java.util.ArrayList;
import java.util.HashMap;

//represent the trips made by the driver in a given problem as clustering of trips

public class Cluster {
	
	HashMap<String, Float> clusterCostMap; //contains the cost of travel for a particular set of trips (represented as bit integer)
	DistanceMatrix dm;
	
	Cluster(DistanceMatrix dm) {
		clusterCostMap = new HashMap<>();
		this.dm = dm;
	}
	
	Float calculateCost(String clusterNumStr) {
		if(clusterCostMap.containsKey(clusterNumStr)) {
			return clusterCostMap.get(clusterNumStr);
		}
		else {
			/*
			 * we iteratively first find mid point of the trip closest to the previous point(start at (0,0)) and add the sum
			 */
			int prevPos = 0;
			StringBuilder clusterStr = new StringBuilder(clusterNumStr);
			Float cost = (float)0.0;
				
				while(clusterStr.toString().contains("1")) {
					Float minCostItr = Float.MAX_VALUE;
					int minIndexItr = -1;
					for(int i=1; i<=clusterStr.length(); i++) {
						if(i != prevPos && clusterStr.toString().charAt(i) != '0') {
							Float numCost = dm.distmtx[prevPos][i];
							if(numCost < minCostItr) {
								minCostItr = numCost;
								minIndexItr = i;
							}
						}
					}
					cost += minCostItr;
					clusterStr.setCharAt(minIndexItr,'0');
					prevPos = minIndexItr;
				}
				clusterCostMap.put(clusterNumStr, cost);
				return cost;
		}
	}
	/*
	 * 
	 */
	String generateKClusters(int k) {
		
		return null;
		
	}

	public static ArrayList<ArrayList<Integer>> getClusterRepresentation(ArrayList<Integer> tripPlacementClusterIdx) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

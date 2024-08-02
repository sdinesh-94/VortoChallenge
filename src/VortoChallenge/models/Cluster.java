package VortoChallenge.models;

import java.util.ArrayList;
import java.util.HashMap;

//represent the trips made by the driver in a given problem as clustering of trips

public class Cluster {

	static HashMap<String, Float> clusterCostMap; // contains the cost of travel for a particular set of trips													
	static DistanceMatrix dm;

	public static void setup(DistanceMatrix d) {
		clusterCostMap = new HashMap<>();
		dm = d;
	}
	
    /*
     * calculates the minimal cost for cluster as string representation of 1's and 0's
     * example 1101 means trip1 is present, trip2 is present, trip3 is absent, trip4 is present in that cluster
     * 
     * input clusterNumStr - string representation of the cluster
     * output cost - minimum cost of the cluster
     */
	public static Float calculateCost(String clusterNumStr) {
		if (clusterCostMap.containsKey(clusterNumStr)) {
			return clusterCostMap.get(clusterNumStr);
		} else {
			/*
			 * we iteratively first find mid point of the trip closest to the previous
			 * point(start at (0,0)) and add the sum
			 */
			int prevPos = 0;
			StringBuilder clusterStr = new StringBuilder(clusterNumStr);
			Float cost = (float) 0.0;

			while (clusterStr.toString().contains("1")) {
				Float minCostItr = Float.MAX_VALUE;
				int minIndexItr = -1;
				for (int i = 1; i <= clusterStr.length(); i++) {
					if (i != prevPos && clusterStr.toString().charAt(i - 1) != '0') {
						Float numCost = getDm().distmtx[prevPos][i];
						if (numCost < minCostItr) {
							minCostItr = numCost;
							minIndexItr = i;
						}
					}
				}
				cost += minCostItr;
				clusterStr.setCharAt(minIndexItr - 1, '0');
				prevPos = minIndexItr;
			}
			clusterCostMap.put(clusterNumStr, cost);
			return cost;
		}
	}

	/*
	 * we get a string representation of k bit strings where k is the number of the
	 * cluster. Each cluster string has the value set to 1 at the position of the
	 * trip character. Example if given 12211 and cluster size of 2 we would output [{1,0,0,1,1}, {0,1,1,0}]
	 * 
	 * input tripPlacementClusterIdx - arraylist of cluster number for each trip
	 * input clusterSize - size of the cluster
	 * output clusterCfgStringList - string value of if cluster has a particular trip or not
	 * 
	 */
	public static ArrayList<String> getClusterStringRepresentation(ArrayList<Integer> tripPlacementClusterIdx,
			int clusterSize) {

		ArrayList<String> clusterStringList = new ArrayList<>();
		for (int index = 1; index <= clusterSize; index++) {
			StringBuilder str = new StringBuilder();
			for (int itr = 0; itr < tripPlacementClusterIdx.size(); itr++) {
				if (tripPlacementClusterIdx.get(itr) == index) {
					str.append("1");
				} else {
					str.append("0");
				}
			}
			clusterStringList.add(str.toString());
		}
		return clusterStringList;
	}

	/*
     *Provides the trips assigned across clusters. Example if given 12211 and cluster size of 2 we would output [{1,4,5}, {2,3}]
	 * 
	 * input tripPlacementClusterIdx - arraylist of cluster number for each trip
	 * input clusterSize - size of the cluster
	 * output clusterCfgList - arraylist of trips assigned in a cluster
	 * 
	 */
	public static ArrayList<ArrayList<Integer>> getClusterCfgRepresentation(ArrayList<Integer> tripPlacementClusterIdx,
			int clusterSize) {

		ArrayList<ArrayList<Integer>> clusterCfgList = new ArrayList<>();
		for (int i = 0; i < clusterSize; i++) {
			clusterCfgList.add(new ArrayList<Integer>());
		}
		for (int tripIndex = 0; tripIndex < tripPlacementClusterIdx.size(); tripIndex++) {
			Integer tripClusterValueatTripIndex = tripPlacementClusterIdx.get(tripIndex);
			clusterCfgList.get(tripClusterValueatTripIndex - 1).add(tripIndex + 1);
		}
		return clusterCfgList;

	}

	public static DistanceMatrix getDm() {
		return dm;
	}

	public static void setDm(DistanceMatrix dm) {
		Cluster.dm = dm;
	}

}

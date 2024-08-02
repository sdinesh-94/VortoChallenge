package VortoChallenge.models;

import java.util.ArrayList;
import java.util.HashMap;

//represent the trips made by the driver in a given problem as clustering of trips

public class Cluster {
	
	static HashMap<String, Float> clusterCostMap; //contains the cost of travel for a particular set of trips (represented as bit integer)
	static DistanceMatrix dm;
	
	Cluster(DistanceMatrix dm) {
		clusterCostMap = new HashMap<>();
		this.dm = dm;
	}
	
	public static Float calculateCost(String clusterNumStr) {
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
    /*
     * we get a string representation of k bit strings where k is the number of the cluster. Each cluster string has the 
     * value set to 1 at the position of the trip character
     * 
     */
	public static ArrayList<String> getClusterStringRepresentation(ArrayList<Integer> tripPlacementClusterIdx, int clusterSize) {
		
		ArrayList<String> clusterStringList = new ArrayList<>();
		for(int index=1; index<=clusterSize; index++) {
			StringBuilder str = new StringBuilder();
			for(int itr=0; itr<tripPlacementClusterIdx.size(); itr++) {
				if(tripPlacementClusterIdx.get(itr) == index) {
					str.append("1");
				}
				else {
					str.append("0");
				}
			}
			clusterStringList.add(str.toString());
		}
		return clusterStringList;
	}
	
	public static ArrayList<ArrayList<Integer>> getClusterCfgRepresentation(ArrayList<Integer> tripPlacementClusterIdx, int clusterSize){
		
		ArrayList<ArrayList<Integer>> clusterCfgRepresentationList = new ArrayList<>();
		for(int i=0; i<clusterSize; i++) {
			clusterCfgRepresentationList.add(new ArrayList<Integer>());
		}
		for(int tripIndex=1; tripIndex<=tripPlacementClusterIdx.size(); tripIndex++) {
			clusterCfgRepresentationList.get(tripPlacementClusterIdx.get(tripIndex)).add(tripIndex);
		}
		return clusterCfgRepresentationList;
		
	}
	
}

package VortoChallenge.controllers;

import java.util.ArrayList;

import VortoChallenge.models.Cluster;
import VortoChallenge.models.ClusterConfigCost;
import VortoChallenge.models.TripLoad;
import VortoChallenge.util.CostCalculator;

public class OptimalKClusterConfigCostCalculator extends ClusterConfigCostCalculator {
	
	ArrayList<String> clusterPlacementPerm = new ArrayList<String>();
	

	
	
	public static ClusterConfigCost calculateMultipleClusterCostCfg(ArrayList<TripLoad> trips, int tripSize, int clusterSize){	
		
		ArrayList<ArrayList<Integer>> alltripPlacementClusterIdx = new ArrayList<>();
		alltripPlacementClusterIdx = generatePlacementListRecFun(tripSize, 1, clusterSize);
		
		/*
		 *  given cluster assignment for each trip in trips list, we convert to binary string representation
		 *  for that cluster and calculate the associated cost
		 */
		
		Float minClusterCost = Float.MAX_VALUE;
		ArrayList<ArrayList<Integer>> minClusterCfg = new ArrayList<>();
		for(ArrayList<Integer> tripPlacementClusterIdx: alltripPlacementClusterIdx) {
			Float currentClusterCost = CostCalculator.clusterCost(tripPlacementClusterIdx);
			if(currentClusterCost < minClusterCost) {
				minClusterCost = currentClusterCost;
				minClusterCfg = Cluster.getClusterRepresentation(tripPlacementClusterIdx);
			}
		}
		ClusterConfigCost minClusterfork = new ClusterConfigCost();
		minClusterfork.setClusterConfig(minClusterCfg);
		minClusterfork.setClusterCost(minClusterCost);
		return minClusterfork;
	}
	
	static ArrayList<ArrayList<Integer>> generatePlacementListRecFun(int tripSize, int tripPos, int clusterSize){
		
		if(tripPos == tripSize) {
			ArrayList<Integer> tripPlacementLastIdx = new ArrayList<Integer>();
			for(int indexToAppend=1; indexToAppend<=clusterSize; indexToAppend++) {
				tripPlacementLastIdx.add(indexToAppend);
			}
			ArrayList<ArrayList<Integer>> alltripPlacementLastIdx = new ArrayList<>();
			alltripPlacementLastIdx.add(tripPlacementLastIdx);
			return alltripPlacementLastIdx;
		}
				
		ArrayList<ArrayList<Integer>> alltripPlacementClusterPrevIdx = generatePlacementListRecFun(tripSize, tripPos+1, clusterSize);
		ArrayList<ArrayList<Integer>> alltripPlacementClusterCurrIdx = new ArrayList<>();
		
		for(int indexToAppend=1; indexToAppend<=clusterSize; indexToAppend++) {
			for(ArrayList<Integer> alltripPlacementClusterPrevIdxEle : alltripPlacementClusterPrevIdx) {
				alltripPlacementClusterPrevIdxEle.add(indexToAppend);
				alltripPlacementClusterCurrIdx.add(alltripPlacementClusterPrevIdxEle);
			}			
		}

		return 	alltripPlacementClusterCurrIdx;	
	}

	@Override
	public Float calculateSingleClusterCost() {
		// TODO Auto-generated method stub
		return null;
	}



}

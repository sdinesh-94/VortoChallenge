package VortoChallenge.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import VortoChallenge.models.Cluster;
import VortoChallenge.models.ClusterConfigCost;
import VortoChallenge.models.DistanceMatrix;
import VortoChallenge.models.TripLoad;
import VortoChallenge.util.CostCalculator;

public class OptimalKClusterConfigCostCalculator extends ClusterConfigCostCalculator {
	
	ArrayList<String> clusterPlacementPerm = new ArrayList<String>();
	

	
	
	public ClusterConfigCost calculateMultipleClusterCostCfg(ArrayList<TripLoad> trips, int tripSize, int clusterSize){	
				
		ArrayList<ArrayList<Integer>> alltripPlacementClusterIdx = new ArrayList<>();
		alltripPlacementClusterIdx = generatePlacementListRecFun(tripSize, 1, clusterSize);
		
		/*
		 *  given cluster assignment for each trip in trips list, we convert to binary string representation
		 *  for that cluster and calculate the associated cost
		 */
		
		Float minClusterCost = Float.MAX_VALUE;
		ArrayList<ArrayList<Integer>> minClusterCfg = new ArrayList<>();
		for(ArrayList<Integer> tripPlacementClusterIdx: alltripPlacementClusterIdx) {
			
			ArrayList<String> clusterStringList = Cluster.getClusterStringRepresentation(tripPlacementClusterIdx, clusterSize);
			
			Float currentClusterCost = CostCalculator.clusterCost(clusterStringList);
			if(currentClusterCost != 0.0 && currentClusterCost < minClusterCost) {
				minClusterCost = currentClusterCost;
				minClusterCfg = Cluster.getClusterCfgRepresentation(tripPlacementClusterIdx, clusterSize);
			}
		}
		ClusterConfigCost minClusterfork = new ClusterConfigCost();
		minClusterfork.setClusterConfig(minClusterCfg);
		minClusterfork.setClusterCost(minClusterCost);
		return minClusterfork;
	}
	
	static ArrayList<ArrayList<Integer>> generatePlacementListRecFun(int tripSize, int tripPos, int clusterSize){
				
		if(tripPos == tripSize) {
			ArrayList<ArrayList<Integer>> alltripPlacementLastIdx = new ArrayList<>();
			for(int indexToAppend=1; indexToAppend<=clusterSize; indexToAppend++) {
				ArrayList<Integer> tripPlacementLastIdx = new ArrayList<Integer>();
				tripPlacementLastIdx.add(indexToAppend);
				alltripPlacementLastIdx.add(tripPlacementLastIdx);
			}		
			return alltripPlacementLastIdx;
		}
				
		ArrayList<ArrayList<Integer>> alltripPlacementClusterPrevIdx = generatePlacementListRecFun(tripSize, tripPos+1, clusterSize);
		ArrayList<ArrayList<Integer>> alltripPlacementClusterCurrIdx = new ArrayList<>();
		
		for(ArrayList<Integer> alltripPlacementClusterPrevIdxEle : alltripPlacementClusterPrevIdx) {
			for(int indexToAppend=1; indexToAppend<=clusterSize; indexToAppend++) {
				ArrayList<Integer> alltripPlacementClusterPrevIdxEleCopy = new ArrayList<>(alltripPlacementClusterPrevIdxEle);
				alltripPlacementClusterPrevIdxEleCopy.add(indexToAppend);
				alltripPlacementClusterCurrIdx.add(alltripPlacementClusterPrevIdxEleCopy);
			}			
		}

		return 	alltripPlacementClusterCurrIdx;	
	}

	@Override
	public ClusterConfigCost calculateSingleClusterCostCfg(ArrayList<TripLoad> trips, int tripSize) {
		
		ClusterConfigCost singleClusterConfigCost = new ClusterConfigCost();
		ArrayList<ArrayList<Integer>> singleClusterCfg = new ArrayList<>();
		//classic TSP problem. To avoid exponential time complexity, we just go with input cluster configuration 
		ArrayList<Integer> singleCluster = (ArrayList<Integer>) trips.stream().map(x -> x.getLoadNumber()).collect(Collectors.toList());
		Float singleClusterCost = calculateSingleClusterCost(singleCluster);
		singleClusterCfg.add(singleCluster);
		singleClusterConfigCost.setClusterCost(singleClusterCost);
		singleClusterConfigCost.setClusterConfig(singleClusterCfg);
		return singleClusterConfigCost;
	}
	
	Float calculateSingleClusterCost(ArrayList<Integer> tripIdx) {
		
		Float cost = (float) 0.0;
		for(int i=1; i<=tripIdx.size(); i++) {
			cost += distmtx[i-1][i];
		}
		cost += distmtx[tripIdx.size()][0];
		return cost;
	}

	@Override
	public void setup(int tripSize, DistanceMatrix dm) {
		Cluster.setup(dm);
		this.tripSize = tripSize;
		this.distmtx = dm.distmtx;	
	}


}

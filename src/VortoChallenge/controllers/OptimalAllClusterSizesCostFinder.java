package VortoChallenge.controllers;

import java.util.ArrayList;

import VortoChallenge.models.ClusterConfigCost;
import VortoChallenge.models.DistanceMatrix;
import VortoChallenge.models.TripLoad;
import VortoChallenge.util.TimeChecker;

public class OptimalAllClusterSizesCostFinder{
	
	ArrayList<ArrayList<Integer>> bestAllClusterCfg; //optimal cluster setup lowest cost config
	Float minCostAllClusters = Float.MAX_VALUE;  // minimum cost across all clusters
	
	ArrayList<ArrayList<Integer>> bestkminus1ClusterCfg; //k-1 cluster setup lowest cost config
	Float minCostkminus1Cluster;  // minimum cost across k-1 cluster
	
	ArrayList<ArrayList<Integer>> bestkClusterCfg; //k cluster setup lowest cost config
	Float minCostkCluster;  // minimum cost across k cluster
	
	ArrayList<ArrayList<Integer>> bestkplus1ClusterCfg; //k+1 cluster setup lowest cost config
	Float minCostkplus1Cluster;  // minimum cost across k+1 cluster
	
	
	ClusterConfigCostCalculator clusterConfigCostCalcualtor; // algorithm used to calculate individual cluster config costs
	DistanceMatrix dm;
	ArrayList<TripLoad> trips;
	int tripSize = 0;
	
	
	public void setupCostFinder(ClusterConfigCostCalculator clusterConfigCostCalculator, DistanceMatrix dm, ArrayList<TripLoad> trips) {
		this.clusterConfigCostCalcualtor = clusterConfigCostCalculator;
		this.dm = dm;
		this.trips = trips;
		this.tripSize = trips.size();
		clusterConfigCostCalcualtor.setup(tripSize, dm);
	}
	
	ClusterConfigCost calculateSingleClusterCostCfg() {
		ClusterConfigCost singleClusterCfgCost = new ClusterConfigCost();
		singleClusterCfgCost = clusterConfigCostCalcualtor.calculateSingleClusterCostCfg(trips, tripSize);
		return singleClusterCfgCost;
	}
	
	ClusterConfigCost calculateMultipleClustersLowestCostCfg(int clusterSize) {
		ClusterConfigCost multipleClusterCfgCost = new ClusterConfigCost();
		multipleClusterCfgCost = clusterConfigCostCalcualtor.calculateMultipleClusterCostCfg(trips, tripSize, clusterSize);
		return multipleClusterCfgCost;
	}
	
	
	public ArrayList<ArrayList<Integer>> calculateOptimalCost() {
		
		bestAllClusterCfg = new ArrayList<>();
		ClusterConfigCost singleClusterCostCfg = calculateSingleClusterCostCfg();
		
		if(tripSize == 1) {
			if(!TimeChecker.checkTimeExceedCondition(singleClusterCostCfg.getClusterCost())) {
				bestAllClusterCfg = singleClusterCostCfg.getClusterConfig();
				return bestAllClusterCfg;
			}
		}
		
				bestkminus1ClusterCfg = singleClusterCostCfg.getClusterConfig();
				minCostkminus1Cluster = singleClusterCostCfg.getClusterCost();
				int clusterSize = 2;
				
				ClusterConfigCost kClusterCostCfg = calculateMultipleClustersLowestCostCfg(clusterSize);
				bestkClusterCfg = kClusterCostCfg.getClusterConfig();
				minCostkCluster = kClusterCostCfg.getClusterCost();
				
				if(tripSize == 2) {
					minCostAllClusters = Math.min(minCostkCluster, minCostkminus1Cluster);
					if(!TimeChecker.checkTimeExceedCondition(minCostAllClusters)) {
						bestAllClusterCfg = minCostkCluster < minCostkminus1Cluster ? bestkClusterCfg : bestkminus1ClusterCfg;
						return bestAllClusterCfg;
					}
				}
				
				while(clusterSize < tripSize) {
					ClusterConfigCost kplus1ClusterCostCfg = calculateMultipleClustersLowestCostCfg(clusterSize+1);
					bestkplus1ClusterCfg = kplus1ClusterCostCfg.getClusterConfig();
					minCostkplus1Cluster = kplus1ClusterCostCfg.getClusterCost();
					
					if(minCostkCluster < minCostkminus1Cluster && minCostkCluster < minCostkplus1Cluster) { //k is global minima
						minCostAllClusters = minCostkCluster;
						if(!TimeChecker.checkTimeExceedCondition(minCostAllClusters)) {
							bestAllClusterCfg =  bestkClusterCfg;
							return bestAllClusterCfg;
						}
					}
					else {
						bestkminus1ClusterCfg = bestkClusterCfg;
						minCostkminus1Cluster = minCostkCluster;
						bestkClusterCfg = bestkplus1ClusterCfg;
						minCostkCluster = minCostkplus1Cluster;
						clusterSize+=1;
					}
					
				}
				
				minCostAllClusters = minCostkplus1Cluster;
				if(!TimeChecker.checkTimeExceedCondition(minCostAllClusters)) {
					bestAllClusterCfg =  bestkplus1ClusterCfg;
					return bestAllClusterCfg;
				}
				return bestAllClusterCfg;
	
	}
	
	
	}


package VortoChallenge.controllers;

import java.util.ArrayList;

import VortoChallenge.models.ClusterConfigCost;
import VortoChallenge.models.DistanceMatrix;
import VortoChallenge.models.TripLoad;
import VortoChallenge.util.TimeChecker;

public class OptimalAllClusterSizesCostFinder{
	
	ArrayList<ArrayList<TripLoad>> bestAllClusterCfg; //optimal cluster setup lowest cost config
	Float minCostAllClusters = Float.MAX_VALUE;  // minimum cost across all clusters
	
	ArrayList<ArrayList<TripLoad>> bestkminus1ClusterCfg; //k-1 cluster setup lowest cost config
	Float minCostkminus1Cluster;  // minimum cost across k-1 cluster
	
	ArrayList<ArrayList<TripLoad>> bestkClusterCfg; //k cluster setup lowest cost config
	Float minCostkCluster;  // minimum cost across k cluster
	
	ArrayList<ArrayList<TripLoad>> bestkplus1ClusterCfg; //k+1 cluster setup lowest cost config
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
	}
	
	ClusterConfigCost calculateSingleClusterCostCfg() {
		ClusterConfigCost singleClusterCfgCost = new ClusterConfigCost();
		Float clusterCost = clusterConfigCostCalcualtor.calculateSingleClusterCost();
		ArrayList<ArrayList<TripLoad>> clusterCfg = new ArrayList<>();
		clusterCfg.add(trips);
		singleClusterCfgCost.setClusterConfig(clusterCfg);
		singleClusterCfgCost.setClusterCost(clusterCost);
		return singleClusterCfgCost;
	}
	
	ClusterConfigCost calculateMultipleClustersLowestCostCfg(int clusterSize) {
		ClusterConfigCost multipleClusterCfgCost = new ClusterConfigCost();
		Float clusterCost = clusterConfigCostCalcualtor.calculateMultipleClusterCostCfg(trips, tripSize, clusterSize);
	}
	
	

	
	public void calculateOptimalCost() {
		
		bestAllClusterCfg = new ArrayList<>();
		ClusterConfigCost singleClusterCostCfg = calculateSingleClusterCostCfg();
		
		if(tripSize == 1) {
			if(!TimeChecker.checkTimeExceedCondition(singleClusterCostCfg.getClusterCost())) {
				bestAllClusterCfg = singleClusterCostCfg.getClusterConfig();
				return;
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
						return;
					}
				}
				
				while(clusterSize <= tripSize) {
					ClusterConfigCost kplus1ClusterCostCfg = calculateMultipleClustersLowestCostCfg(clusterSize);
					bestkplus1ClusterCfg = kplus1ClusterCostCfg.getClusterConfig();
					minCostkplus1Cluster = kplus1ClusterCostCfg.getClusterCost();
					
					if(minCostkCluster < minCostkminus1Cluster && minCostkCluster < minCostkplus1Cluster) { //k is global minima
						minCostAllClusters = minCostkCluster;
						if(!TimeChecker.checkTimeExceedCondition(minCostAllClusters)) {
							bestAllClusterCfg =  bestkClusterCfg;
							return;
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
					return;
				}
	
	}
	
	
	}


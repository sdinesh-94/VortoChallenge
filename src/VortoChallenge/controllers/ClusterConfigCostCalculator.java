package VortoChallenge.controllers;

import VortoChallenge.models.ClusterConfigCost;

public abstract class ClusterConfigCostCalculator {
	
	int clusterSize;
	int tripSize;
	
	

	public abstract Float calculateSingleClusterCost();
	
	public abstract ClusterConfigCost calculateMultipleClusterCostCfg();
	


}

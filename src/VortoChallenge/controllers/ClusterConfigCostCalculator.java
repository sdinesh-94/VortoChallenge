package VortoChallenge.controllers;

import java.util.ArrayList;

import VortoChallenge.models.ClusterConfigCost;
import VortoChallenge.models.TripLoad;

public abstract class ClusterConfigCostCalculator {
	
	int clusterSize;
	int tripSize;
	
	

	public abstract Float calculateSingleClusterCost();
	
	public abstract ClusterConfigCost calculateMultipleClusterCostCfg(ArrayList<TripLoad> trips, int tripSize, int clusterSize);
	


}

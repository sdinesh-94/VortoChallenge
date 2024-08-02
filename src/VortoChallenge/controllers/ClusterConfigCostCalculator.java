package VortoChallenge.controllers;

import java.util.ArrayList;

import VortoChallenge.models.ClusterConfigCost;
import VortoChallenge.models.DistanceMatrix;
import VortoChallenge.models.TripLoad;

public abstract class ClusterConfigCostCalculator {
	
	int tripSize;
	Float[][] distmtx;
	
	public abstract void setup(int tripSize, DistanceMatrix dm);
		
	public abstract ClusterConfigCost calculateMultipleClusterCostCfg(ArrayList<TripLoad> trips, int tripSize, int clusterSize);

	public abstract ClusterConfigCost calculateSingleClusterCostCfg(ArrayList<TripLoad> trips, int tripSize); 

	


}

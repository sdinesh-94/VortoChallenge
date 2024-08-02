package VortoChallenge.models;

import java.util.ArrayList;

public class ClusterConfigCost {
	
	ArrayList<ArrayList<TripLoad>> clusterConfig;
	Float clusterCost;

	public ArrayList<ArrayList<TripLoad>> getClusterConfig() {
		return clusterConfig;
	}
	public void setClusterConfig(ArrayList<ArrayList<TripLoad>> clusterConfig) {
		this.clusterConfig = clusterConfig;
	}
	public Float getClusterCost() {
		return clusterCost;
	}
	public void setClusterCost(Float clusterCost) {
		this.clusterCost = clusterCost;
	}

}

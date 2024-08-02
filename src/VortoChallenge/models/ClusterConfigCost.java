package VortoChallenge.models;

import java.util.ArrayList;

public class ClusterConfigCost {

	ArrayList<ArrayList<Integer>> clusterConfig;
	Float clusterCost;

	public ArrayList<ArrayList<Integer>> getClusterConfig() {
		return clusterConfig;
	}

	public void setClusterConfig(ArrayList<ArrayList<Integer>> clusterConfig) {
		this.clusterConfig = clusterConfig;
	}

	public Float getClusterCost() {
		return clusterCost;
	}

	public void setClusterCost(Float clusterCost) {
		this.clusterCost = clusterCost;
	}

}

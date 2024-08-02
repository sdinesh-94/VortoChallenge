package VortoChallenge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import VortoChallenge.models.DistanceMatrix;
import VortoChallenge.models.TripLoad;

import VortoChallenge.controllers.ClusterConfigCostCalculator;
import VortoChallenge.controllers.OptimalAllClusterSizesCostFinder;
import VortoChallenge.controllers.OptimalKClusterConfigCostCalculator;


public class VehicleRoutingProblem {
	
	static ArrayList<TripLoad> trips;
	static DistanceMatrix dm = new DistanceMatrix();
	static int tripSize = 0;
	
	
	void populateDistanceMatrix(){
		dm.setNumTrips(tripSize);
		dm.createDistanceMatrix(trips);
	}

	
	public static void main(String args[]) throws IOException {
		
		String pathToFile = args[0];
		if(pathToFile == null) {
			System.out.println("Please provide problem set file");
		}
		
		BufferedReader bf = new BufferedReader(new FileReader(pathToFile));
		String headerLine = bf.readLine();
		if(headerLine == null || !headerLine.contains("loadNumber pickup dropoff")) {
			System.out.println("file input not in correct format");
		}
		
		trips = new ArrayList<TripLoad>();
		VehicleRoutingProblem vrp = new VehicleRoutingProblem();
		
		String inputLine = bf.readLine();
		while(inputLine != null) {
			String[] input = inputLine.split(" ");
			if(input.length !=3) {
				System.out.println("input: "+input+" not in right format. Skipping");
				continue;
			}
			TripLoad trip = new TripLoad();
			trip.setLoadNumber(input[0]);
			trip.setPoint1(input[1]);
			trip.setPoint2(input[2]);
			trip.setup();
			trips.add(trip);
						
		inputLine = bf.readLine();
		
	}
		tripSize = trips.size();
		vrp.populateDistanceMatrix();
		/*
		 * Intutively we see that the solutions space contains a global minima. This could be represented as an inverted
		 * bell curve with the global minima being the lowest cost configuration. Therefore we start with 1 cluster (driver)
		 * to cover N points and we increase it till k clusters setup such that optimal configuration of the kth cluster is lower 
		 * than optimal configuration of k-1 clusters setup and k+1 cluster setup
		 */
				
		OptimalAllClusterSizesCostFinder optclstr = new OptimalAllClusterSizesCostFinder();
		

		ClusterConfigCostCalculator kclusterCfgCstCltr= new OptimalKClusterConfigCostCalculator();
		//at runtime we pass in the cost calculator algorithm
		optclstr.setupCostFinder(kclusterCfgCstCltr, dm, trips);
		ArrayList<ArrayList<Integer>> ClusterConfig = optclstr.calculateOptimalCost();		
		
		//output the config info. Print only non empty lists
		for(ArrayList<Integer> cluster: ClusterConfig) {
			if(!cluster.isEmpty())
				System.out.println(cluster.toString());
		}
}
}

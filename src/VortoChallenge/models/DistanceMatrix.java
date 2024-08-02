package VortoChallenge.models;

import java.util.ArrayList;

public class DistanceMatrix {
	int numTrips;
	Float[][] distmtx;
	
	public void setNumTrips(int numTrips) {
		this.numTrips = numTrips;
	}

	public void createDistanceMatrix(ArrayList<TripLoad> trips) {
		distmtx = new Float[numTrips+1][numTrips+1]; // cointains eucledian distance between midpoints in trips

		for(int i=0; i<numTrips; i++) {
			for(int j=0; j<numTrips; j++) {
				if((i==0 && j==0) || (i == j) ) {
					distmtx[i][j] = (float) 0.0;
					continue;
				}
				else if(i==0 && j!=0) {
					distmtx[0][j+1] = (float) Math.sqrt(Math.pow(trips.get(j).p.x,2) + Math.pow(trips.get(j).p.y,2));
				}
				else if(i!=0 && j==0) {
					distmtx[i+1][0] = (float) Math.sqrt(Math.pow(trips.get(i).p.x,2) + Math.pow(trips.get(i).p.y,2));
				}
				else {
					distmtx[i+1][j+1] = (float) Math.sqrt(Math.pow(trips.get(j).p.x - trips.get(i).p.x,2) + Math.pow(trips.get(j).p.y - trips.get(i).p.y,2));
				}
			}
		}
	}	
}

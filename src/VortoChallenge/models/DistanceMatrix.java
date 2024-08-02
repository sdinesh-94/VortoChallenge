package VortoChallenge.models;

import java.util.ArrayList;

public class DistanceMatrix {
	public int numTrips;
	public Float[][] distmtx;

	public void setNumTrips(int numTrips) {
		this.numTrips = numTrips;
	}
    /*
     * Computes the distance of one point to every other point and stores it in a 2d array
     */
	public void createDistanceMatrix(ArrayList<TripLoad> trips) {
		distmtx = new Float[numTrips + 1][numTrips + 1]; // cointains eucledian distance between midpoints in trips

		for (int i = 0; i <= numTrips; i++) {
			for (int j = 0; j <= numTrips; j++) {
				if ((i == 0 && j == 0) || (i == j)) {
					distmtx[i][j] = (float) 0.0;
					continue;
				} else if (i == 0 && j != 0) {
					distmtx[0][j] = (float) Math
							.sqrt(Math.pow(trips.get(j - 1).p.x, 2) + Math.pow(trips.get(j - 1).p.y, 2));
				} else if (i != 0 && j == 0) {
					distmtx[i][0] = (float) Math
							.sqrt(Math.pow(trips.get(i - 1).p.x, 2) + Math.pow(trips.get(i - 1).p.y, 2));
				} else {
					distmtx[i][j] = (float) Math.sqrt(Math.pow(trips.get(j - 1).p.x - trips.get(i - 1).p.x, 2)
							+ Math.pow(trips.get(j - 1).p.y - trips.get(i - 1).p.y, 2));
				}
			}
		}
	}
}

package VortoChallenge.models;

public class TripLoad {
	Integer loadNumber;
	Point p1, p2;
	Float tripCost;
	Point p; // to simplify the problem, we consider the midpoints of p1 and p2. The
				// traversal across trips
				// is between midpoints

	public TripLoad() {
		loadNumber = 0;
		p1 = new Point();
		p2 = new Point();
		p = new Point();
	}

	public void setLoadNumber(String loadNumber) {
		this.loadNumber = Integer.valueOf(loadNumber);
	}

	public Integer getLoadNumber() {
		return this.loadNumber;
	}

	void setPoint(Point pt, String pointInput) {
		if (pointInput != null) {
			pointInput = pointInput.replace("(", "").replace(")", "");
			String[] str = pointInput.split(",");
			pt.x = Float.valueOf(str[0]);
			pt.y = Float.valueOf(str[1]);
		}
	}

	public void setPoint1(String pointInput) {
		this.setPoint(p1, pointInput);
	}

	public void setPoint2(String pointInput) {
		this.setPoint(p2, pointInput);
	}

	// simple check to see if the trip has been populated
	public void setup() {
		if (loadNumber != 0) {
			calculateTripCost();
			calculateMidPoint();
		}
	}

	void calculateTripCost() {
		tripCost = (float) Math.sqrt(((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y)));
	}

	void calculateMidPoint() {
		p.x = calculateMidPoint(p1.x, p2.x);
		p.y = calculateMidPoint(p1.y, p2.y);
	}

	Float calculateMidPoint(Float x, Float y) {
		Float val = Math.abs(x) + Math.abs(y);
		if (x < 0 && y < 0) {
			return -1 * val / 2;
		} else if (x > 0 && y > 0) {
			return val / 2;
		} else {
			val = x > y ? y + val / 2 : x + val / 2;
			return val;
		}
	}

}

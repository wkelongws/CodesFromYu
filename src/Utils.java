
public class Utils {
	
	/**
	 * Calculate distance between two geo-spatial points
	 * @param curlon1
	 * @param ilon
	 * @param curlat1
	 * @param ilat
	 * @return
	 */
	public static double distCalc(double long1, double long2, double lat1,
			double lat2) {
		double theta = long1 - long2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = rad2deg(Math.acos(dist));
		return (dist * 60 * 1.1515);
	}

	/**
	 * degree to radian
	 * @param deg
	 * @return
	 */
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/**
	 * radian to degree
	 * @param rad
	 * @return
	 */
	private static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	public static void waitToNextRound(long startTime) throws InterruptedException {
		long runTime = System.currentTimeMillis() - startTime;
		if (runTime < 20000) {
			Thread.sleep(20000-runTime);
		}
	}

}

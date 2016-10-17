package bean;


public class Segment {
	
	private String start;
	private String end;
	private double startLat;
	private double startLong;
	private double endLat;
	private double endLong;
	
	private String bearing;
	private String group;
	private double distance;
	private double distToMid;
	private String wzloc;
	
	private double startDetectorSpeed;
	private double startDetectorVolume;
	private double endDetectorSpeed;
	private double endDetectorVolume;
	
	private double segmentSpeed;
	private double segmentVolume;
	
	private double timeRangeTotalSpeed;
	private double timeRangeTotalVolume;
	private int counter;
	
	public void incrementTotalSpeedVolume(double speed, double volume) {
		if (speed!=0) {
			timeRangeTotalSpeed += speed;
			timeRangeTotalVolume += volume;
			counter++;
		}
	}
	public double getAvgTimeRangeSpeed() {
		return timeRangeTotalSpeed/counter;
	}
	public double getAvgTimeRangeVolume() {
		return timeRangeTotalVolume/counter;
	}
	public void resetTimeRange() {
		counter = 0;
		timeRangeTotalSpeed = 0;
		timeRangeTotalVolume = 0;
	}
	
	private double runTime;
	private double defaultRunTime;
	private double delay;
	public double totalDelay;
	
	
	private String date;
	
	
//	// for daily statistics
//	private int counterOfReported;
//	private int counterOfTotal;
//	
//	public void incrementCounterOfReported() {
//		counterOfReported++;
//	}
//	public void incrementCounterOfTotal() {
//		counterOfTotal++;
//	}
//	public double countPercentTimeReported() {
//		double p = counterOfReported / (double)counterOfTotal;
//		return p;  // Math.round(p * 100) / 100;	
//	}
//	public void clearCounters() {
//		counterOfReported = 0;
//		counterOfTotal = 0;
//	}

	// Getters and Setters
	public String getWzloc() {
		return wzloc;
	}
	public void setWzloc(String wzloc) {
		this.wzloc = wzloc;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public double getStartLat() {
		return startLat;
	}
	public void setStartLat(double startLat) {
		this.startLat = startLat;
	}
	public double getStartLong() {
		return startLong;
	}
	public void setStartLong(double startLong) {
		this.startLong = startLong;
	}
	public double getEndLat() {
		return endLat;
	}
	public void setEndLat(double endLat) {
		this.endLat = endLat;
	}
	public double getEndLong() {
		return endLong;
	}
	public void setEndLong(double endLong) {
		this.endLong = endLong;
	}
	public String getBearing() {
		return bearing;
	}
	public void setBearing(String bearing) {
		this.bearing = bearing;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getDistToMid() {
		return distToMid;
	}
	public void setDistToMid(double distToMid) {
		this.distToMid = distToMid;
	}
	
	public double getStartDetectorSpeed() {
		return startDetectorSpeed;
	}
	public void setStartDetectorSpeed(double startDetectorSpeed) {
		this.startDetectorSpeed = startDetectorSpeed;
	}
	public double getStartDetectorVolume() {
		return startDetectorVolume;
	}
	public void setStartDetectorVolume(double startDetectorVolume) {
		this.startDetectorVolume = startDetectorVolume;
	}
	public double getEndDetectorSpeed() {
		return endDetectorSpeed;
	}
	public void setEndDetectorSpeed(double endDetectorSpeed) {
		this.endDetectorSpeed = endDetectorSpeed;
	}
	public double getEndDetectorVolume() {
		return endDetectorVolume;
	}
	public void setEndDetectorVolume(double endDetectorVolume) {
		this.endDetectorVolume = endDetectorVolume;
	}
	public double getSegmentSpeed() {
		return segmentSpeed;
	}
	public void setSegmentSpeed(double segmentSpeed) {
		this.segmentSpeed = segmentSpeed;
	}
	public double getSegmentVolume() {
		return segmentVolume;
	}
	public void setSegmentVolume(double segmentVolume) {
		this.segmentVolume = segmentVolume;
	}
	
	public double getRunTime() {
		return runTime;
	}
	public void setRunTime(double runTime) {
		this.runTime = runTime;
	}
	public double getDefaultRunTime() {
		return defaultRunTime;
	}
	public void setDefaultRunTime(double defaultRunTime) {
		this.defaultRunTime = defaultRunTime;
	}
	public double getDelay() {
		return delay;
	}
	public void setDelay(double delay) {
		this.delay = delay;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String toString() {
		return group + "," + date + "," + start + "," + end + ","
				+ startLat + "," + startLong + "," + endLat + "," + endLong
				+ "," + distance + "," + distToMid + "," + bearing + "," + startDetectorSpeed + ","
				+ endDetectorSpeed + "," + segmentSpeed + "," + runTime + ","
				+ defaultRunTime + "," + delay + "," + segmentVolume + ","
				+ wzloc + "\n";
	}
}

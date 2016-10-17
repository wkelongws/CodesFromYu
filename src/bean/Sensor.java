package bean;

public class Sensor {

	private String name;
	private double speed;
	private int volume;
	private double mileMarker;
	private String direction;
	private String IWZ;
	private String route;
	private String latitude;
	private String longitude;
	private String arrowDirection;
	
	
	private double intervalSpeed;
	private double intervalVolume;
	private int counter;
	
	public void incrementIntervalSpeedVolume(double speed, double volume) {
		if (speed!=0) {
			intervalSpeed += speed;
			intervalVolume += volume;
			counter++;
		}
	}
	public double getAvgIntervalSpeed() {
		return intervalSpeed/counter;
	}
	public double getAvgIntervalVolume() {
		return intervalVolume/counter;
	}
	public void resetIntervalValues() {
		counter = 0;
		intervalSpeed = 0;
		intervalVolume = 0;
	}
	
	// getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public double getMileMarker() {
		return mileMarker;
	}

	public void setMileMarker(double mileMarker) {
		this.mileMarker = mileMarker;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getIWZ() {
		return IWZ;
	}

	public void setIWZ(String iWZ) {
		IWZ = iWZ;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getArrowDirection() {
		return arrowDirection;
	}

	public void setArrowDirection(String arrowDirection) {
		this.arrowDirection = arrowDirection;
	}

	@Override
	public String toString() {
		return name + "," + direction + "," + IWZ + "," + route + ","
				+ mileMarker + "," + latitude + "," + longitude + ","
				+ arrowDirection + "," + speed + "," + volume;
	}

}

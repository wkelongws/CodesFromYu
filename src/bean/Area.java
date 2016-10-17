package bean;

import java.util.ArrayList;
import java.util.List;

public class Area {
	
	
	private List<List<Integer>> speedList = new ArrayList<>();   	// this is for speed flag
	private List<List<Integer>> realSpeedList = new ArrayList<>();  // this is for real speed, but not interpolated
	
	
	public List<List<Integer>> getRealSpeedList() {
		return realSpeedList;
	}

	public void resetRealSpeedList() {
		realSpeedList = new ArrayList<>();
	}

	public List<List<Integer>> getSpeedList() {
		return speedList;
	}

	public void resetSpeedList() {
		speedList = new ArrayList<>();
	}

	
	
	private String name;
	private String projectID;
	private String route;
	private String groupID;
	private String direction;
	private String midLatitude;
	private String midLongitude;
	private double startMileMarker;
	private double endMileMarker;
	
	private List<Sensor> sensorList;
	private List<Sensor> borderList;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getMidLatitude() {
		return midLatitude;
	}

	public void setMidLatitude(String midLatitude) {
		this.midLatitude = midLatitude;
	}

	public String getMidLongitude() {
		return midLongitude;
	}

	public void setMidLongitude(String midLongitude) {
		this.midLongitude = midLongitude;
	}

	public double getStartMileMarker() {
		return startMileMarker;
	}

	public void setStartMileMarker(double startMileMarker) {
		this.startMileMarker = startMileMarker;
	}

	public double getEndMileMarker() {
		return endMileMarker;
	}

	public void setEndMileMarker(double endMileMarker) {
		this.endMileMarker = endMileMarker;
	}

	public List<Sensor> getSensorList() {
		return sensorList;
	}

	public void setSensorList(List<Sensor> sensorList) {
		this.sensorList = sensorList;
	}

	public List<Sensor> getBorderList() {
		return borderList;
	}

	public void setBorderList(List<Sensor> borderList) {
		this.borderList = borderList;
	}

	

	// counters for statistical result
	public int events1;
	public int events2;
	public int events3;
	public int totalEvents;
	public double totalDelay;
	public int totalVolume;
	
}

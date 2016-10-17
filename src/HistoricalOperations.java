import java.util.ArrayList;
import java.util.List;

import bean.Area;
import bean.DistSpeed;
import bean.Sensor;

public class HistoricalOperations {

	/**
	 * Update the historical result
	 * 
	 * @param area
	 * @return
	 */
	public static void updateHistory(Area area) {

//		List<DistSpeed> dsList1 = new ArrayList<DistSpeed>();
//		List<DistSpeed> dsList2 = new ArrayList<DistSpeed>();
//		List<DistSpeed> borderList = new ArrayList<DistSpeed>();
//		
//		// step 1. split the whole table into 3 lists
//		// dsList1: direction 1, dsList2: direction 2, borderList: borders
//		for (Sensor s : area.getSensorList()) {
//			DistSpeed ds = new DistSpeed();
//			ds.setDistance(s.getMileMarker());
//			ds.setSpeed(s.getAvgIntervalSpeed());
//			if (s.getDirection().equals("1")) {
//				dsList1.add(ds);
//			}
//			else if (s.getDirection().equals("2")) {
//				dsList2.add(ds);
//			}
//			else {
//				borderList.add(ds);
//			}
//		}
		
		// List<Integer> realSpeedList = new ArrayList<Integer>();
		List<Integer> categorizedList = new ArrayList<Integer>();
		
		// step 1. generate dsList (mile marker round up to 0.1)
		List<DistSpeed> dsList = new ArrayList<DistSpeed>();
		
		for (Sensor s : area.getSensorList()) {
			DistSpeed ds = new DistSpeed();
			ds.setDistance(Math.round(s.getMileMarker()*10)/10.0);
			ds.setSpeed(s.getAvgIntervalSpeed());
			dsList.add(ds);	
		}
		
		// step 2. interpolate the speed by 0.1 mile
		List<DistSpeed> interpolatedList = interpolate(dsList);
		
		// step 3. categorize the speed list
		categorize(interpolatedList);
		
		// step 4. set borders
		setBorders(interpolatedList, area.getBorderList());
		
		// step 5. return the speed list
		for (DistSpeed ds : interpolatedList) {
			categorizedList.add((int)ds.getSpeed());
		}
		area.getSpeedList().add(categorizedList);
		
		// computeHistoricalDelay(area);
		
//		// count the events
//		double min = 45;
//		for (double speed : speedList) { // find minimum value in the list
//			if (speed ) {
//				sum += speed;
//			}
//		}
//		double avg = sum / (speedList.size() - 2);
//		if (avg <= 45 && avg > 0) {
//			area.totalEvents++;
//			if (avg <= 25) {
//				area.events1++;
//			} else if (avg <= 35) {
//				area.events2++;
//			} else {
//				area.events3++;
//			}
//		}
		
	}
	
	public static void clearHistoryData(Area area) {
		for (Sensor s : area.getSensorList()) {
			s.resetIntervalValues();
		}
	}
	
//	/**
//	 * Calculate delay time with 5-minute average speed (in minute)
//	 * Accumulate the delay (volume) to the area's total delay time
//	 * @param area
//	 */
//	private void computeHistoricalDelay(Area area) {
//		
//		final double DEFAULTSPEED = 55.0;
//		for (Segment seg : area.getSegments()) {
//			double speed = seg.getAvgTimeRangeSpeed();
//			
//			double runTime = seg.getDistance() / speed;
//			double defaultRunTime = seg.getDistance() / DEFAULTSPEED;
//			double delayTime = (runTime - defaultRunTime) * 60; // 60 minutes
//			if (delayTime > 0) {
//				area.totalDelay += (delayTime * seg.getAvgTimeRangeVolume());
//			}
//			area.totalVolume += seg.getAvgTimeRangeVolume();
//			seg.resetTimeRange(); // reset the 2-minute speed
//		}
//	}

	
	/**
	 * Interpolate the list by 0.1 mile
	 * 
	 * @return
	 */
	private static List<DistSpeed> interpolate(List<DistSpeed> dsList) {
		
		List<DistSpeed> list = new ArrayList<DistSpeed>();
		
		for (int i = 1; i < dsList.size(); i++) {
			list.addAll(interpolateTwoSensors(dsList.get(i - 1),
					dsList.get(i)));
		}

		// Don't forget to add the last element of the dsList
		list.add(dsList.get(dsList.size()-1));

		return list;
	}
	
	
	/**
	 * Interpolate [t1, t2)
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	private static List<DistSpeed> interpolateTwoSensors(DistSpeed t1, DistSpeed t2) {

		final double step = 0.1; // fixed value
		
		List<DistSpeed> result = new ArrayList<DistSpeed>();
		
		double dist1 = t1.getDistance();
		double dist2 = t2.getDistance();

		double val1 = t1.getSpeed();
		double val2 = t2.getSpeed();

		result.add(t1);   // add the first element
		
		if (val1==0 || val2==0) {
			for (double i=(dist1+step); i<dist2; i=i+step) {
				DistSpeed ds = new DistSpeed();
				ds.setDistance(i);
				ds.setSpeed(0);
				result.add(ds);
			}
		}
		else {
			for (double i=(dist1+step); i<dist2; i=i+step) {
				double val = val1 + ((val2 - val1) / (dist2 - dist1) * (i - dist1));
				DistSpeed ds = new DistSpeed();
				ds.setDistance(i);
				ds.setSpeed(val);
				result.add(ds);
			}
		}

		return result;
	}
	
	
	/**
	 * Categorize each speed 
	 * @param list
	 */
	private static void categorize(List<DistSpeed> list) {

		for (DistSpeed e : list) {
			double speed = e.getSpeed();
			
			// if speed is 0, keep it as 0
			if (speed > 45) {
				e.setSpeed(40);
			} else if (speed <= 25) {
				e.setSpeed(10);
			} else if (speed > 25 && speed <= 35) {
				e.setSpeed(20);
			} else if (speed > 35 && speed <= 45) {
				e.setSpeed(30);
			}
		}
	}
	
	/**
	 * Set borders
	 * @param list
	 * @param borderList
	 */
	private static void setBorders(List<DistSpeed> list, List<Sensor> borderList) {
		
		if (borderList.size() == 0) {
			return;
		}
		
		for (int i=0; i<list.size()-1; i++) {
			double dist1 = list.get(i).getDistance();
			double dist2 = list.get(i+1).getDistance();
			
			for (Sensor s : borderList) {
				double dist = s.getMileMarker();
				if (dist>=dist1 && dist<=dist2) {
					if ((dist-dist1) < (dist2-dist)) {
						list.get(i).setSpeed(50);
					}
					else {
						list.get(i+1).setSpeed(50);
					}
				}
			}
		}
		
	}

}

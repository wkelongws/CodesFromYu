import java.util.HashMap;
import java.util.List;

import bean.Area;
import bean.Detector;
import bean.Sensor;

public class RealTimeOperations {
	
	/**
	 * Join the detectors data with each area (file)
	 * @param area
	 * @param map
	 */
	public static void join(Area area, HashMap<String,Detector> map) {
		List<Sensor> sensorList = area.getSensorList();
		for (Sensor s : sensorList) {
			String name = s.getName();
			if (map.containsKey(name)) {
				Detector d = map.get(name);
				s.setSpeed(d.getSpeed());
				s.setVolume(d.getVolume());
			}
		}
	}

	
//	/**
//	 * Set the real-time slow traffic flag
//	 * True, if there is a segment in an area is slow (speed<45)
//	 * @param area
//	 */
//	public static void setRealTimeSlowFlag (Area area) {
//		for (Sensor s : area.getSensorList()) {
//			double speed = s.getSpeed();
//			if (speed <= 45 && speed > 0) {
//				area.setSlowFlag(true);
//				return;
//			}
//		}
//		area.setSlowFlag(false);
//	}

	
//	/**
//	 * Calculate the real-time delay of each segment
//	 * @param area
//	 * @param date
//	 */
//	public static void computeRealTimeDelay(Area area) {
//		
//		final double DEFAULTSPEED = 55;
//		final double SECONDS_PER_HOUR = 3600;
//		
//		List<Segment> segmentList = area.getSegments();
//		
//		for (int i=0; i<segmentList.size(); i++) {
//			Segment seg = segmentList.get(i);
//			
//			double segSpeed = seg.getSegmentSpeed();
//			
//			// calculate delay time in seconds (if no delay set it as 0)
//			double runTime = seg.getDistance()/segSpeed*SECONDS_PER_HOUR;
//			double defaultRunTime = seg.getDistance()/DEFAULTSPEED*SECONDS_PER_HOUR;
//			seg.setRunTime(runTime);
//			seg.setDefaultRunTime(defaultRunTime);
//			double delayTime = Math.round((runTime - defaultRunTime)*100)/100.0;
//			if (delayTime > 0) {
//				seg.setDelay(delayTime);
//			}
//			else {
//				seg.setDelay(0);
//			}
//		}
//	}

}




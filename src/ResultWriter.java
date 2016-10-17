import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;

import bean.Area;


public class ResultWriter {
	
//	/*
//	 * write the historical result to a json file
//	 */
//	
//	public static void writeToJson(FileWriter dailyWriter, FileWriter dailyWriter1,
//			List<Zone> workzones) {
//		JSONArray myArray = new JSONArray();
//		JSONArray myArray1 = new JSONArray();
//		
//		for (Zone zone : workzones) {
//			for (Area area : zone.getAreas()) {
//				Map areaJson = new LinkedHashMap();
//				areaJson.put("workid", zone.getId());
//				areaJson.put("direction", area.getBearing());
//				areaJson.put("latitude", area.getMidLat());
//				areaJson.put("longitude", area.getMidLong());
//				areaJson.put("totaldist", area.getTotaldist());
//				areaJson.put("zonedist", area.getZonedist());
//				
//				areaJson.put("totalevents", area.totalEvents);
//				areaJson.put("events1", area.events1);
//				areaJson.put("events2", area.events2);
//				areaJson.put("events3", area.events3);
//				if (area.totalVolume!=0) {
//					areaJson.put("delay", (area.totalDelay/area.totalVolume));
//				}
//				else {
//					areaJson.put("delay", 0);
//				}
//				
//				JSONArray allDaySpeed = new JSONArray();
//				for (List<Integer> list : area.getSpeedList()) {
//					JSONArray speedArray = new JSONArray();
//					for (int i : list) {
//						speedArray.add(i);
//					}
//					allDaySpeed.add(speedArray);
//				}
//				areaJson.put("chart",allDaySpeed);
//				myArray.add(areaJson);
//				
//				
//				
//				// write the real speed json file
//				Map areaJson1 =new LinkedHashMap();
//				areaJson1.put("workid", zone.getId());
//				areaJson1.put("direction", area.getBearing());
//				JSONArray realSpeed = new JSONArray();
//				for (List<Integer> list : area.getRealSpeedList()) {
//					JSONArray speedArray = new JSONArray();
//					for (int i : list) {
//						speedArray.add(i);
//					}
//					realSpeed.add(speedArray);
//				}
//				areaJson1.put("chart", realSpeed);
//				myArray1.add(areaJson1);
//
//			}
//		}
//		
//		try {
//			dailyWriter.write(myArray.toString());
//			dailyWriter.flush();
//			
//			dailyWriter1.write(myArray1.toString());
//			dailyWriter1.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void writeToJson(FileWriter dailyWriter, List<Area> areaList) {

		JSONArray myArray = new JSONArray();

		for (Area area : areaList) {

			Map areaJson = new LinkedHashMap();
			areaJson.put("name", area.getName());
			areaJson.put("projectID", area.getProjectID());
			areaJson.put("route", area.getRoute());
			areaJson.put("groupID", area.getGroupID());
			areaJson.put("latitude", area.getMidLatitude());
			areaJson.put("longitude", area.getMidLongitude());
			areaJson.put("startMileMarker", area.getStartMileMarker());
			areaJson.put("endMileMarker", area.getEndMileMarker());
			

			JSONArray allDaySpeed = new JSONArray();
			for (List<Integer> list : area.getSpeedList()) {
				JSONArray speedArray = new JSONArray();
				for (int i : list) {
					speedArray.add(i);
				}
				allDaySpeed.add(speedArray);
			}
			areaJson.put("chart", allDaySpeed);
			myArray.add(areaJson);
		}

		try {
			dailyWriter.write(myArray.toString());
			dailyWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
//	/*
//	 * write 'percent time reported' of each segment sensor to file
//	 */
//	public static void writeTimeReported(FileWriter writer,
//			HashMap<String, Group> groupMap) {
//		
//		try {
//			writer.write("group,start,end,pecent time reported\n");
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		
//		for (Group group : groupMap.values()) {
//			for (Area area : group.getAreas()) {
//				for (Segment seg : area.getSegments()) {
//					try {
//						writer.write(seg.getGroup()
//								+ ","
//								+ seg.getBearing()
//								+ ","
//								+ seg.getStart()
//								+ ","
//								+ seg.getEnd()
//								+ ","
//								+ Double.toString(seg
//										.countPercentTimeReported()) + '\n');
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					seg.clearCounters();
//				}
//			}
//		}
//	}
	
	
//	/*
//	 * Write percent time the work zone is active
//	 */
//	public static void writeTimeActive(FileWriter writer,
//			HashMap<String, Group> groupMap) {
//		try {
//			writer.write("group,bearing,active%,criteria 1,criteria 2,criteria 3\n");
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		for (Group group : groupMap.values()) {
//			for (Area area : group.getAreas()) {
//				double p1 = area.criteria1/(double)area.totalCriteria;
//				double p2 = area.criteria2/(double)area.totalCriteria;
//				double p3 = area.criteria3/(double)area.totalCriteria;
//				String str = group.getGid() + "," + area.getBearing() + ","
//						+ area.countPercentTimeActive() + "," + p1 + "," + p2
//						+ "," + p3 + "\n";
//				try {
//					writer.write(str);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				area.clearCounters();
//			}
//		}
//	}

}

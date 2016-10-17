import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import bean.Area;
import bean.Detector;
import bean.Sensor;


public class DataLoader {
	
	private String date = "";
	
	public String getDate() {
		return date;
	}

	public HashMap<String,Detector> downloadDetectorData() throws Exception{
		
		final double KMH_TO_MPH = 0.621371;
		
		// URL of the "Traffic Detector Data" source
		String link = "http://205.221.97.102/Iowa.Sims.AllSites.C2C.Geofenced/"
				+ "IADOT_SIMS_AllSites_C2C.asmx/OP_ShareTrafficDetectorData?"
				+ "MSG_TrafficDetectorDataRequest=stringHTTP/1.1";

		HashMap<String, Detector> detectorMap = new HashMap<String, Detector>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(link);
		document.getDocumentElement().normalize();

		// get the local date
		String date = document.getElementsByTagName("local-date").item(0)
				.getTextContent()
				+ " "
				+ document.getElementsByTagName("local-time").item(0)
						.getTextContent();
		this.date = date;

		// get detectors
		NodeList detectorList = document
				.getElementsByTagName("detector-report");
		for (int i = 0; i < detectorList.getLength(); i++) {
			Element detector = (Element) detectorList.item(i);
			String id = detector.getElementsByTagName("detector-id").item(0)
					.getTextContent();
			id = id.trim().replaceAll("\\s+", ""); // remove all the inner spaces
			NodeList laneList = detector.getElementsByTagName("lane");
			double totalSpeed = 0;
			int volume = 0;
			double avgSpeed = 0;
			for (int j = 0; j < laneList.getLength(); j++) {
				Element lane = (Element) laneList.item(j);
				NodeList countList = lane.getElementsByTagName("count");
				NodeList speedList = lane.getElementsByTagName("speed");
				if (countList.getLength() > 0 && speedList.getLength() > 0) {
					int count = Integer.parseInt(countList.item(0)
							.getTextContent());
					int speed = Integer.parseInt(speedList.item(0)
							.getTextContent());
					volume += count;
					totalSpeed += speed * count;
				}
			}
			
			if (volume!=0) {
				avgSpeed = totalSpeed / volume * KMH_TO_MPH;
				avgSpeed = Math.round(avgSpeed * 100) / 100.0;
			}
			
			Detector d = new Detector(id, avgSpeed, volume);
			detectorMap.put(id, d);
		}

		return detectorMap;
	}
	
	
	/**
	 * Load the work zone data from the files
	 * @param inputPath
	 * @return
	 */
	public List<Area> loadWorkZoneData(String inputPath) {
		
		File inputFolder = new File(inputPath);
		File[] files = inputFolder.listFiles();
		
		List<Area> areaList = new ArrayList<Area>();
		for (File file : files) {
			Area area = loadArea(file);		
			areaList.add(area);
		}
		
		return areaList;
	}
	
	
	/**
	 * Given a file, load the data as an @Area object
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private Area loadArea(File file) {
		
		Area area = new Area();
		
		String areaName = file.getName();
		area.setName(areaName);
		
		String[] tokens = areaName.split("-");
		area.setProjectID(tokens[0]);
		area.setRoute(tokens[1]);
		area.setGroupID(tokens[2]);
		area.setDirection(tokens[3]);
		
		List<Sensor> sensorList = new ArrayList<Sensor>();
		List<Sensor> borderList = new ArrayList<Sensor>();
		
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader buffReader = new BufferedReader(fileReader);
			String line = null;

			while ((line = buffReader.readLine()) != null) {
				String[] split = line.split(",");
				Sensor s = new Sensor();
				
				if (split[1].equals("0")) {
					s.setDirection(split[0]);
					s.setIWZ(split[1]);
					s.setName(split[2].trim().replaceAll("\\s+", ""));
					s.setRoute(split[3]);
					s.setMileMarker(Double.parseDouble(split[4]));
					s.setLatitude(split[5]);
					s.setLongitude(split[6]);
					s.setArrowDirection(split[7]);
					
					sensorList.add(s);
				}
				else {
					s.setDirection(split[0]);
					s.setIWZ(split[1]);
					s.setName(split[2]);
					s.setMileMarker(Double.parseDouble(split[4]));
					
					borderList.add(s);
				}
			}
			buffReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// set the mid point latitude and longitude of the area
		int size = sensorList.size();
		int midIndex = size/2;
		Sensor midSensor = sensorList.get(midIndex);
		area.setMidLatitude(midSensor.getLatitude());
		area.setMidLongitude(midSensor.getLongitude());
		
		// set start and end mile marker
		Sensor startSensor = sensorList.get(0);
		Sensor endSensor = sensorList.get(size-1);
		double startMileMarker = Math.round(startSensor.getMileMarker()*10)/10.0;
		double endMileMarker = Math.round(endSensor.getMileMarker()*10)/10.0;
		area.setStartMileMarker(startMileMarker);
		area.setEndMileMarker(endMileMarker);
		
		// set the sensor list and border list
		area.setSensorList(sensorList);
		area.setBorderList(borderList);
		
		return area;
	}
	

	/**
	 * Test
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			List<Area> areaList = new DataLoader().loadWorkZoneData("output");
			for (Area a : areaList) {
				List<Sensor> list = a.getBorderList();
				for (Sensor s : list) {
					System.out.println(s.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

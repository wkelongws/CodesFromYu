import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import bean.Area;
import bean.Detector;
import bean.Sensor;


public class Main {

	public static void main(String[] args) throws Exception {

		String staticFilesPath = "./input/";
		String realOut = "./output/realtime.txt";
		String dailyOut = "./daily/MergedData.json";
		// String realSpeedOut = "./daily/RealSpeed.json";
		
		final long ROUNDTIME = 5*60*1000;  // 5 minutes

		DataLoader loader = new DataLoader();
		 
		// load the static work zone data from files
		List<Area> areaList = loader.loadWorkZoneData(staticFilesPath);

		// Get today's date
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(new Date());
		long historyClock = System.currentTimeMillis();
		
		// begin
		while (true) {
			long startTime = System.currentTimeMillis();
			
			HashMap<String, Detector> detectorMap = new HashMap<String, Detector>();
			try{
				detectorMap = loader.downloadDetectorData();
			} catch (Exception e) {
				System.err.println("Exceptions in download detector data: "
								+ new SimpleDateFormat("yyyy-MM-dd HH-mm-ss")
								.format(new Date()));
				e.printStackTrace();
				Utils.waitToNextRound(startTime);
				continue;
			}
			
			FileWriter realWriter = new FileWriter(realOut, false);
			String title = "Name,Direction,IWZ,Route,Mile Marker,Latitude,Longitude,"
							+ "Arrow Direction,Speed,Volume\r\n";
			realWriter.write(title);

			for (Area area : areaList) {

				RealTimeOperations.join(area, detectorMap);

				// RealTimeOperations.setRealTimeSlowFlag(area);

				// loader.getDate();

				for (Sensor s : area.getSensorList()) {
					realWriter.write(s.toString() + "\r\n");

					// update the 5-minute speed,volume and counter
					s.incrementIntervalSpeedVolume(s.getSpeed(), s.getVolume());

					// clear the detector speed and volume values
					s.setSpeed(0);
					s.setVolume(0);
				}
			}
			realWriter.close();
			
			
			// for every 5 minutes, update history
			if ((System.currentTimeMillis()-historyClock) >= ROUNDTIME) {
				historyClock = System.currentTimeMillis();
				for (Area area : areaList) {
					HistoricalOperations.updateHistory(area);
					// reset the 5 minutes interval values
					HistoricalOperations.clearHistoryData(area);
				}
				
			}
			
			// at end of the day, invoke writing statistical result and historical json
			String date = dateFormat.format(new Date());
			if (!today.equals(date)) {
				// write the historical result
				FileWriter dailyWriter = new FileWriter(dailyOut, false);
				// FileWriter dailyWriter1 = new FileWriter(realSpeedOut, false);
				/// ResultWriter.writeToJson(dailyWriter, dailyWriter1, areaList);
				ResultWriter.writeToJson(dailyWriter, areaList);
				dailyWriter.close();
				// dailyWriter1.close();
				
				// reset time
				today = date; 
				historyClock = System.currentTimeMillis();
				
				// reset the data
				// reload the static work zone data from files
				areaList = loader.loadWorkZoneData(staticFilesPath);
			}
			Utils.waitToNextRound(startTime);
		}
	}
	
	
	

}

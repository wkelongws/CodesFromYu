import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class OutputValidation {

	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("MergedData.json");
		
		Object obj = JSONValue.parse(fr);
		JSONArray array = (JSONArray)obj;
		for (int i=0; i<array.size(); i++) {
			JSONObject areaObj = (JSONObject)array.get(i);
			String areaName = (String)areaObj.get("Workid");
			String fileName = areaName + (String)areaObj.get("Direction");
			FileWriter fw = new FileWriter("output/" + fileName + ".csv");
			FileWriter fw1 = new FileWriter("output1/" + fileName + ".csv");
			
			JSONArray chart = (JSONArray)areaObj.get("chart");
			for (int j=0; j<chart.size(); j++) {
				String listOneSlot = chart.get(j).toString();
				// remove the first and last brace character
				listOneSlot = listOneSlot.substring(1, listOneSlot.length()-1);
				fw.write(listOneSlot);
				fw.write("\n");
			}
			fw.close();
			
			
			
			for (int j=0; j<chart.size(); j++) {
				JSONArray arrayOneSlot = (JSONArray)chart.get(j);
				String str = "";
				for (int k=0; k<arrayOneSlot.size(); k++) {
					long speed = (long)arrayOneSlot.get(k);
					if (speed > 45 || speed == 0) {
						str += "40,";
						
						if (speed==0) {
							System.out.println("Note: " + fileName);
						}
					} else if (speed <= 25 && speed>1) {
						str += "10,";
					} else if (speed > 25 && speed <= 35) {
						str += "20,";
					} else if (speed > 35 && speed <= 45) {
						str += "30,";
					}
					else if(speed == -1L) {
						str += "100,";
					}
				}
				fw1.write(str.substring(0, str.length()-1));
				fw1.write("\n");
			}
			fw1.close();
		}
	}

}

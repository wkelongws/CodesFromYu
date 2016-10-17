import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RunWorkzone {

	public static void main(String[] args) throws IOException {

		String staticFile ="C:/Users/mislam/Desktop/WorkzoneBackend/input/";
		String DownloadPath = "C:/Users/mislam/Desktop/WorkzoneBackend/xml/";
		String output = "C:/Users/mislam/Desktop/WorkzoneBackend/bOutput/";
		String rOutput = "C:/Users/mislam/Desktop/WorkzoneBackend/rOutput/";
		String jsonOutput = "C:/Users/mislam/Desktop/WorkzoneBackend/json/";
		String outNoInt ="C:/Users/mislam/Desktop/WorkzoneBackend/oni/";
		String jsononi = "C:/Users/mislam/Desktop/WorkzoneBackend/jsononi/";
		
		int counter = 0; 
		
		List<Integer> bins = new ArrayList<Integer>();
		bins.add(5);bins.add(10);bins.add(15);bins.add(20);bins.add(25);bins.add(30);bins.add(35);bins.add(40);bins.add(45);bins.add(50);
		bins.add(55);bins.add(60);bins.add(65);bins.add(70);bins.add(75);bins.add(80);bins.add(85);bins.add(90);bins.add(95);bins.add(100);
		bins.add(9999);
		
		List<Integer> bset = new ArrayList<Integer>();
		bset.add(1);bset.add(2);bset.add(3);bset.add(4);bset.add(5);bset.add(6);bset.add(7);bset.add(8);bset.add(9);bset.add(10);
		bset.add(11);bset.add(12);bset.add(13);bset.add(14);bset.add(15);bset.add(16);bset.add(17);bset.add(18);bset.add(19);bset.add(20);
		bset.add(0);
		
		
		do{
			DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
			Date today = Calendar.getInstance().getTime();        
			String todays = dateFormat.format(today);
			
			String Final_Output = output + todays +"/";
			File filex=new File(Final_Output);
			filex.mkdir();
			String input = Final_Output;
			
			String J_Output = jsonOutput + todays +"/";
			File filej=new File(J_Output);
			filej.mkdir();
			
			String fxml = DownloadPath + todays +"/";
			File filexml=new File(fxml);
			filexml.mkdir();
			
			
			long timeBefore = System.currentTimeMillis();
		    URL link = new URL ("http://205.221.97.102//Iowa.Sims.AllSites.C2C.Geofenced/IADOT_SIMS_AllSites_C2C.asmx/OP_ShareTrafficDetectorData?MSG_TrafficDetectorDataRequest=stringHTTP/1.1");
			
		    
		    try{
				/*DateFormat dateFormat2 = new SimpleDateFormat("HH-mm-ss");
				Date today2 = Calendar.getInstance().getTime();        
				String reportDate2 = dateFormat2.format(today2);*/
				InputStream in = new BufferedInputStream(link.openStream());
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int n=0;
			
				while(-1!=(n=in.read(buf)))
				{
					out.write(buf,0,n);
				}
				out.close();
				in.close();
				byte[] response = out.toByteArray();
			
				FileOutputStream fos = new FileOutputStream(fxml + "data" + ".xml/");
				fos.write(response);
				fos.close();
				}catch(IOException exception)
				{
				}
		    
			
			File folder = new File(fxml);
			
			File [] listOfFiles = folder.listFiles(new FilenameFilter(){
				
				@Override
				public boolean accept(File folder, String name){
					return name.endsWith(".xml");
				}
				
			});
			String inputfile = listOfFiles[listOfFiles.length-1].getName();

			
			File folderx = new File(staticFile);
			File [] listOfFilesy = folderx.listFiles(new FilenameFilter(){
				
				@Override
				public boolean accept(File folderx, String namex){
					return namex.endsWith(".txt");				
				}
			});
			List<String> GroupIDs = new ArrayList<String>();
			List<String> GroupDets = new ArrayList<String>();
			List<String> GroupLats = new ArrayList<String>();
			List<String> GroupLongs = new ArrayList<String>();
			List<String> GroupActive = new ArrayList<String>();
			List<String> GroupSpeed = new ArrayList<String>();
			List<String> GroupDxn = new ArrayList<String>();
			List<String> GroupNames = new ArrayList<String>();
			
			for (int xy=0; xy<listOfFilesy.length; xy++){
				
				//for (int xy=0; xy<2; xy++){
				List<String> Name = new ArrayList<String>();List<String> LR = new ArrayList<String>();List<String> lons= new ArrayList<String>();
				List<String> arrs = new ArrayList<String>();List<String> lats = new ArrayList<String>();
			String filesx = listOfFilesy[xy].getName();
			FileReader frx = new FileReader(staticFile + filesx);
			BufferedReader textReaderx = new BufferedReader(frx);
			
			String aLinex; String [] lineSplitx; 
			while((aLinex = textReaderx.readLine()) != null){
				
				lineSplitx = aLinex.split("\t");
				if (lineSplitx[2].equals("0")){
				Name.add(lineSplitx[5].replaceAll("\\s+",""));LR.add(lineSplitx[10]);arrs.add(lineSplitx[13]);
				lats.add(lineSplitx[11]);lons.add(lineSplitx[12]);
				}
			}
			textReaderx.close();
			//Name.remove(0); LR.remove(0);
			//System.out.println(Name);
			List<Integer> speeds = new ArrayList<Integer>();
			List<String> uDetectors = new ArrayList<String>();
			try{
				DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
				DocumentBuilder builder =factory.newDocumentBuilder();	
				Document document = builder.parse(new File(fxml+inputfile));
				document.getDocumentElement().normalize();
				NodeList timestamps = document.getElementsByTagName("detection-time-stamp");
				Element timestamp = (Element) timestamps.item(0);
				String date = timestamp.getElementsByTagName("local-date").item(0).getTextContent();
				//System.out.println();
				
				NodeList starttime = document.getElementsByTagName("start-time");
				Node stNode = starttime.item(0);
				String starttimer = stNode.getTextContent();
				
				NodeList endtime = document.getElementsByTagName("end-time");
				Node enNode = endtime.item(0);
				String endtimer = enNode.getTextContent();
				
				NodeList detectorReports = document.getElementsByTagName("detector-report");
				for (int ixa=0; ixa<detectorReports.getLength(); ++ixa){
					Element detectorReport = (Element) detectorReports.item(ixa);
					String detectorID = detectorReport.getElementsByTagName("detector-id").item(0).getTextContent();
					
					if (Name.contains(detectorID.replaceAll("\\s+",""))){
						uDetectors.add(detectorID.replaceAll("\\s+",""));
					NodeList Lanes = detectorReport.getElementsByTagName("lanes");
					Element Ln = (Element) Lanes.item(0);
					int tl = Ln.getElementsByTagName("lane").getLength();
					//System.out.print(detectorID +",");
					NodeList curLanes = Ln.getElementsByTagName("lane");
					String speed = "0";
					int speedT=0; 
					for (int ixb=0; ixb<tl; ++ixb){

						Element Lns = (Element) curLanes.item(ixb);					
						int cspeed =Lns.getElementsByTagName("speed").getLength();
						if (cspeed>0){ speed = Lns.getElementsByTagName("speed").item(0).getTextContent();}else{ speed = "0";}
						speedT+=Integer.parseInt(speed);
					}
					if (tl>0){
					//System.out.print(speedT/tl+",");
					speeds.add((speedT/tl));
					}else{
						speeds.add(speedT);
						//System.out.print(speedT+",");
					}
					//System.out.println();
					}
				}
				
			}catch (Exception e) {
				e.printStackTrace();
		    }
			

			// Remove duplicates
			List<String> LR2 = new ArrayList<String>();List<Integer> indx = new ArrayList<Integer>();
			for (int c=0; c<uDetectors.size(); c++){
				LR2.addAll(uDetectors); LR2.remove(c);
				int xyx= LR2.indexOf(uDetectors.get(c));
				if (xyx>-1){indx.add(c);}
				LR2 = new ArrayList<String>();
			}

			if (indx.size()>0){
			indx.remove(0);
			for (int r=0; r<indx.size(); r++){
				int vx=indx.get(r);
				uDetectors.remove(vx);
				speeds.remove(vx);
			}
			}  // Duplicate removal completed
			
			// Save Realtime information
			String groups = filesx.split(" ")[0].split("Group")[1];
			String mph = "null"; int active=0;String arrow="";
			String latitude="";String longitude="";
			
			for (int qc=0; qc<uDetectors.size(); qc++){
				double curSpeed = Math.round(speeds.get(qc)*0.62);			
				if (curSpeed!=0){
				 mph = String.valueOf((int) curSpeed);
				}else{
					mph = "null";
				}
				if (curSpeed<45&&!mph.equals("null")){
					 active = 1;
				}else{
					active =0;
				}
				
				int vx = Name.indexOf(uDetectors.get(qc));
				if (vx>=0){
					arrow = arrs.get(vx);
					latitude=lats.get(vx);
					longitude=lons.get(vx);
				}
				GroupIDs.add(groups);GroupDets.add(uDetectors.get(qc));GroupLats.add(latitude);GroupLongs.add(longitude);
				GroupActive.add(Integer.toString(active));GroupSpeed.add(mph);GroupDxn.add(arrow);
				GroupNames.add(filesx.split(".txt")[0]);
				//System.out.println(groups +","+uDetectors.get(qc) +","+latitude+","+longitude+"," + active +","+mph +"," + arrow);
				
			}
			PrintStream outxx = new PrintStream(new FileOutputStream(rOutput+  "realtimet" + ".csv", false));
			System.setOut(outxx);
			/*System.out.print("GroupName" +"\t");System.out.print("GroupID" +"\t");System.out.print("Detector"+"\t");System.out.print("Lat"+"\t");
			System.out.print("Lon"+"\t");System.out.print("Active"+"\t");System.out.print("Speed"+"\t");System.out.println("Direction"+"\t");
			for (int out=0; out<GroupIDs.size(); out++){
				System.out.println(GroupNames.get(out)+"\t"+GroupIDs.get(out)+"\t"+GroupDets.get(out)+"\t"+GroupLats.get(out)+"\t"+GroupLongs.get(out)+"\t"
						+GroupActive.get(out)+"\t"+GroupSpeed.get(out)+"\t"+GroupDxn.get(out)+"\t");
			}*/
			System.out.print("GroupName" +",");System.out.print("GroupID" +",");System.out.print("Detector"+",");System.out.print("Lat"+",");
			System.out.print("Lon"+",");System.out.print("Active"+",");System.out.print("Speed"+",");System.out.println("Direction"+",");
			for (int out=0; out<GroupIDs.size(); out++){
				System.out.println(GroupNames.get(out)+","+GroupIDs.get(out)+","+GroupDets.get(out)+","+GroupLats.get(out)+","+GroupLongs.get(out)+","
						+GroupActive.get(out)+","+GroupSpeed.get(out)+","+GroupDxn.get(out)+",");
			}
			outxx.close();
			
			// Write raw data without interpolation
		
			PrintStream outoni = new PrintStream(new FileOutputStream(outNoInt+  filesx + ".txt", true));
			System.setOut(outoni);
			for (int abc=0; abc<Name.size(); abc++){
				String curCod = Name.get(abc);
				int sindex = GroupDets.indexOf(curCod);
				if (sindex>=0){
				System.out.print(GroupSpeed.get(sindex) +",");
				}else{
					System.out.print("null" +",");
				}
					
			}
			System.out.println("");
			outoni.close();
			
			File rfolderxx = new File(outNoInt);
			File [] rlistOfFilesyy = rfolderxx.listFiles(new FilenameFilter(){
				
				@Override
				public boolean accept(File rfolderx, String namex){
					return namex.endsWith(".txt");				
				}
			});
			
			List<String> rdirections = new ArrayList<String>();
			rdirections.add("east"); rdirections.add("west"); rdirections.add("north"); rdirections.add("south");
			String rfiledxn = "null";
			JSONArray rfinalx = new JSONArray();JSONArray rfinalxr = new JSONArray();
			
			for (int rx=0; rx<rlistOfFilesyy.length; rx++){
				JSONObject robj = new JSONObject();JSONObject robjx = new JSONObject();
				String rfilesx = rlistOfFilesyy[rx].getName();
				
				String[] arayx = rfilesx.split("[\\s\\.]+");
				for (int xya=0; xya<arayx.length; xya++){
					if (rdirections.contains(arayx[xya])){
						int dxn = rdirections.indexOf(arayx[xya]);
						rfiledxn = rdirections.get(dxn);
					}
				}
				FileReader frxy = new FileReader(staticFile + rfilesx.split(".txt")[0] +".txt");
				BufferedReader textR = new BufferedReader(frxy);
				List<String> LRf = new ArrayList<String>();List<String> zone = new ArrayList<String>();
				List<String> latsx = new ArrayList<String>();List<String> longx = new ArrayList<String>();
				
				String aLinexy; String [] lineSplitxy; int ctt=0; int total = 0;
				while((aLinexy = textR.readLine()) != null){
					ctt++;
					lineSplitxy = aLinexy.split("\t");
					LRf.add(lineSplitxy[10]);
					if (lineSplitxy[2].matches("^-?\\d+$")&&Integer.parseInt(lineSplitxy[2])>0){
						zone.add(Integer.toString(ctt-1));
						latsx.add(lineSplitxy[3]);
						longx.add(lineSplitxy[4]);
					}else if (lineSplitxy[2].matches("^-?\\d+$")&&Integer.parseInt(lineSplitxy[2])==0){
						latsx.add(lineSplitxy[3]);
						longx.add(lineSplitxy[4]);
					}else if (lineSplitxy[2].matches("^-?\\d+$")){
						latsx.add(lineSplitxy[3]);
						longx.add(lineSplitxy[4]);
					}
					total=ctt;
				}
	/*			PrintStream outxx3 = new PrintStream(new FileOutputStream(J_Output + "test3/"+ "NearHist"+".txt", false));
				System.setOut(outxx3);
				System.out.println(filesx);
				System.out.println(latsx.get(0));*/
				textR.close();frxy.close();
				LRf.remove(0);
				Double tDist = Double.parseDouble(LRf.get(LRf.size()-1)) - Double.parseDouble(LRf.get(0));
				
				if (rfiledxn.equals("east")){
					rfiledxn = "E";
				}else if (rfiledxn.equals("west")){
					rfiledxn = "W";
				}else if (rfiledxn.equals("south")){
					rfiledxn = "S";	
				}else if (rfiledxn.equals("north")){
					rfiledxn = "N";
				}

				robj.put("aName", rfilesx.split(".txt")[0]);robj.put("direction", rfiledxn);robj.put("totaldist", tDist);
				robj.put("latitude", latsx.get(0));
				robj.put("longitude", longx.get(0));robj.put("workid", rfilesx.split("-")[1]);
				
				robjx.put("aName", rfilesx.split(".txt")[0]);robjx.put("direction", rfiledxn);robjx.put("totaldist", tDist);robjx.put("latitude", latsx.get(0));
				robjx.put("longitude", longx.get(0));robjx.put("workid", rfilesx.split("-")[1]);
		        JSONArray rcompany = new JSONArray();
		        JSONArray rcompanyx = new JSONArray();
		        
		        
		        
			//raw data to json
			FileReader rfrx = new FileReader(outNoInt + rfilesx);
			BufferedReader rtextReaderx = new BufferedReader(rfrx);
			String [] routx = rtextReaderx.readLine().split(",");
			rtextReaderx.close();
			
			String rline; 
			String [] rlinsplit; int rcnt=0;
			FileReader frx2 = new FileReader(outNoInt + rfilesx);
			BufferedReader rtextReadery = new BufferedReader(frx2);
			
			List<Integer> averagingr = new ArrayList<Integer>(Collections.nCopies(routx.length,0));
			 List<Integer> rraveraging = new ArrayList<Integer>(Collections.nCopies(routx.length,0));
			 List<Integer> rcounters = new ArrayList<Integer>(Collections.nCopies(routx.length,0));

			while((rline = rtextReadery.readLine()) != null){
				rcnt=rcnt+1;
				//JSONArray rcompany2 = new JSONArray();
				JSONArray rcompany2x = new JSONArray();
				rlinsplit = rline.split(",");
				for (int ab=0; ab<routx.length; ab++){
					if (rlinsplit.length>1&&!rlinsplit[ab].equals("null")&&(rlinsplit[ab] !=null&&!rlinsplit[ab].isEmpty()) &&rlinsplit.length==routx.length){
						averagingr.set(ab, (int) ((int) (averagingr.get(ab)+ (Double.parseDouble(rlinsplit[ab]))*0.62)));
						rcounters.set(ab, rcounters.get(ab)+1);
					}else{
						averagingr.set(ab, (int) ((int) (averagingr.get(ab)+ 0)));
						rcounters.set(ab, rcounters.get(ab)+0);
					}

				}
				
				if (rcnt==15){
					//System.out.println("xxx"+averaging);
					//System.out.println("yyy"+counters);
					
					for (int xy1=0; xy1<averagingr.size(); xy1++){
						if (rcounters.get(xy1)>0){
							averagingr.set(xy1, averagingr.get(xy1)/rcounters.get(xy1));
						}else{
							averagingr.set(xy1, averagingr.get(xy1));
						}
						
					}
					//System.out.println("ggg"+averaging);
					
					rraveraging =averagingr;
					
	/*				if (zone.size()>0){
						for (int vx=0; vx<zone.size(); vx++){
							rraveraging.set(Integer.parseInt(zone.get(vx)), 9999);
						}
					}*/
					
					rcompany2x.addAll(rraveraging);
					rcompanyx.add(rcompany2x);
					
					/*for (int xy2=0;xy2<averagingr.size(); xy2++){
						int curPoint = averagingr.get(xy2);
						for (int bn=1; bn<bins.size(); bn++){
							if (curPoint>0 && curPoint<= (double) bins.get(0)){
								averagingr.set(xy2,  bset.get(0));
							}else if (curPoint<= (double) bins.get(bn)&&curPoint> (double) bins.get(bn-1)){
								averagingr.set(xy2, bset.get(bn));
							}
						}
					}
					
					if (zone.size()>0){
						for (int vx=0; vx<zone.size(); vx++){
							averagingr.set(Integer.parseInt(zone.get(vx))-1, 9999);
						}
					}*/
					
					//rcompany2.addAll(averagingr);
					//rcompany.add(rcompany2);
					
					//robj.put("chart", rcompany);
					robjx.put("chart", rcompanyx);
				
					rcounters = new ArrayList<Integer>(Collections.nCopies(routx.length,0));
					averagingr = new ArrayList<Integer>(Collections.nCopies(routx.length,0));
					rcnt=0;
				}
			}
			//rfinalx.add(robj);
			rfinalxr.add(robjx);
			rtextReadery.close();
		}
			PrintStream routjson = new PrintStream(new FileOutputStream(jsononi + todays +".txt", false));
			System.setOut(routjson);
			System.out.println(rfinalxr);
			routjson.close();
			
			
			// Start Processing for Story Board
			List<String> LRNew = new ArrayList<String>();
			List<String> dNew = new ArrayList<String>();
			List<Integer> speedNew = new ArrayList<Integer>();
			if (speeds.size()>1){
				for (int xxy=0; xxy<Name.size(); xxy++){
					int valx = uDetectors.indexOf(Name.get(xxy));
					if (valx>=0){
					dNew.add(uDetectors.get(valx));
					LRNew.add(LR.get(xxy));
					speedNew.add(speeds.get(valx));
					}
				}
			}
			
			List<Double> InterpPoints = new ArrayList<Double>();
			if (speedNew.size()>1){
				for (int ab=1; ab<LRNew.size(); ab++){
					int cur = speedNew.get(ab-1); int post=speedNew.get(ab);
					if (cur!=0&&post!=0){
						InterpPoints.addAll(interpolationx(Double.parseDouble(LRNew.get(ab-1)), Double.parseDouble(LRNew.get(ab)), 
								cur, post));	
					}else{
						cur = 9999; post = 9999;
						InterpPoints.addAll(interpolationx(Double.parseDouble(LRNew.get(ab-1)), Double.parseDouble(LRNew.get(ab)), 
								cur, post));
					}
				}
			}
			
			PrintStream outxx2 = new PrintStream(new FileOutputStream(Final_Output+  filesx + ".txt", true));
			System.setOut(outxx2);
			for (int abc=0; abc<InterpPoints.size(); abc++){
				System.out.print(InterpPoints.get(abc) +",");
			}
			System.out.println("");
			outxx2.close();
				

			}
			
			/*int prex = Integer.parseInt(tomorrow);
			int posx = Integer.parseInt("1025");*/
			//if (prex>1024&&prex<=posx){
				
				// categorize into bins
				
			File folderxx = new File(input);
			File [] listOfFilesyy = folderxx.listFiles(new FilenameFilter(){
				
				@Override
				public boolean accept(File folderx, String namex){
					return namex.endsWith(".txt");				
				}
			});
			
			List<String> directions = new ArrayList<String>();
			directions.add("east"); directions.add("west"); directions.add("north"); directions.add("south");
			String filedxn = "null";
			JSONArray finalx = new JSONArray();JSONArray finalxr = new JSONArray();
			
			for (int xy=0; xy<listOfFilesyy.length; xy++){
			//for (int xy=0; xy<1; xy++){
				JSONObject obj = new JSONObject();JSONObject objx = new JSONObject();
				String filesx = listOfFilesyy[xy].getName();
				
				String[] arayx = filesx.split("[\\s\\.]+");
				for (int xya=0; xya<arayx.length; xya++){
					if (directions.contains(arayx[xya])){
						int dxn = directions.indexOf(arayx[xya]);
						filedxn = directions.get(dxn);
					}
				}
				FileReader frxy = new FileReader(staticFile + filesx.split(".txt")[0] +".txt");
				BufferedReader textR = new BufferedReader(frxy);
				List<String> LRf = new ArrayList<String>();List<String> zone = new ArrayList<String>();
				List<String> latsx = new ArrayList<String>();List<String> longx = new ArrayList<String>();
				
				String aLinexy; String [] lineSplitxy; int ctt=0; int total = 0;
				while((aLinexy = textR.readLine()) != null){
					ctt++;
					lineSplitxy = aLinexy.split("\t");
					LRf.add(lineSplitxy[10]);
					if (lineSplitxy[2].matches("^-?\\d+$")&&Integer.parseInt(lineSplitxy[2])>0){
						zone.add(Integer.toString(ctt-1));
						latsx.add(lineSplitxy[3]);
						longx.add(lineSplitxy[4]);
					}else if (lineSplitxy[2].matches("^-?\\d+$")&&Integer.parseInt(lineSplitxy[2])==0){
						latsx.add(lineSplitxy[3]);
						longx.add(lineSplitxy[4]);
					}else if (lineSplitxy[2].matches("^-?\\d+$")){
						latsx.add(lineSplitxy[3]);
						longx.add(lineSplitxy[4]);
					}
					total=ctt;
				}
	/*			PrintStream outxx3 = new PrintStream(new FileOutputStream(J_Output + "test3/"+ "NearHist"+".txt", false));
				System.setOut(outxx3);
				System.out.println(filesx);
				System.out.println(latsx.get(0));*/
				textR.close();frxy.close();
				LRf.remove(0);
				Double tDist = Double.parseDouble(LRf.get(LRf.size()-1)) - Double.parseDouble(LRf.get(0));
				
				if (filedxn.equals("east")){
					filedxn = "E";
				}else if (filedxn.equals("west")){
					filedxn = "W";
				}else if (filedxn.equals("south")){
					filedxn = "S";	
				}else if (filedxn.equals("north")){
					filedxn = "N";
				}

				obj.put("aName", filesx.split(".txt")[0]);obj.put("direction", filedxn);obj.put("totaldist", tDist);
				obj.put("latitude", latsx.get(0));
				obj.put("longitude", longx.get(0));obj.put("workid", filesx.split("-")[1]);
				
				objx.put("aName", filesx.split(".txt")[0]);objx.put("direction", filedxn);objx.put("totaldist", tDist);objx.put("latitude", latsx.get(0));
				objx.put("longitude", longx.get(0));objx.put("workid", filesx.split("-")[1]);
		        JSONArray company = new JSONArray();
		        JSONArray companyx = new JSONArray();
		        
		       
				
				
			
			FileReader frx = new FileReader(input + filesx);
			BufferedReader textReaderx = new BufferedReader(frx);
			String [] outx = textReaderx.readLine().split(",");
			textReaderx.close();
			//System.out.println(outx.length);
			
			if (zone.size()>0){
				for (int ixy=0; ixy<zone.size(); ixy++){
					int a = Integer.parseInt(zone.get(ixy)); int b =(outx.length); 
					zone.set(ixy, Integer.toString((a*b)/total));
				}
			}
			
			 List<Integer> averaging = new ArrayList<Integer>(Collections.nCopies(outx.length,0));
			 List<Integer> raveraging = new ArrayList<Integer>(Collections.nCopies(outx.length,0));
			 List<Integer> counters = new ArrayList<Integer>(Collections.nCopies(outx.length,0));
			String aLinex; 
			String [] lineSplitx; int cnt=0;
			FileReader frx2 = new FileReader(input + filesx);
			BufferedReader textReadery = new BufferedReader(frx2);
			
	/*		PrintStream outxx7 = new PrintStream(new FileOutputStream(J_Output + "NearHistxx"+".txt", false));
			System.setOut(outxx7);
			System.out.println(filesx);
			outxx7.close();*/
			
			while((aLinex = textReadery.readLine()) != null){
				cnt=cnt+1;
				JSONArray company2 = new JSONArray();
				JSONArray company2x = new JSONArray();
				lineSplitx = aLinex.split(",");
				for (int ab=0; ab<outx.length; ab++){
					if (lineSplitx.length>1&&(lineSplitx[ab] !=null&&!lineSplitx[ab].isEmpty()) &&lineSplitx.length==outx.length&&Double.parseDouble(lineSplitx[ab])!=9999){
						averaging.set(ab, (int) ((int) (averaging.get(ab)+ (Double.parseDouble(lineSplitx[ab]))*0.62)));
						counters.set(ab, counters.get(ab)+1);
					}else{
						averaging.set(ab, (int) ((int) (averaging.get(ab)+ 0)));
						counters.set(ab, counters.get(ab)+0);
					}

				}
				if (cnt==15){
					//System.out.println("xxx"+averaging);
					//System.out.println("yyy"+counters);
					
					for (int xy1=0; xy1<averaging.size(); xy1++){
						if (counters.get(xy1)>0){
						averaging.set(xy1, averaging.get(xy1)/counters.get(xy1));
						}else{
							averaging.set(xy1, averaging.get(xy1));
						}
						
					}
					//System.out.println("ggg"+averaging);
					
					raveraging =averaging;
					
					if (zone.size()>0){
						for (int vx=0; vx<zone.size(); vx++){
							int n=Integer.parseInt(zone.get(vx));
							System.out.print("N is: "+n);
							raveraging.set(n, 9999);
						}
					}
					
					company2x.addAll(raveraging);
					companyx.add(company2x);
					
					for (int xy2=0;xy2<averaging.size(); xy2++){
						int curPoint = averaging.get(xy2);
						for (int bn=1; bn<bins.size(); bn++){
							if (curPoint>0 && curPoint<= (double) bins.get(0)){
								averaging.set(xy2,  bset.get(0));
							}else if (curPoint<= (double) bins.get(bn)&&curPoint> (double) bins.get(bn-1)){
								averaging.set(xy2, bset.get(bn));
							}
						}
					}
					
					if (zone.size()>0){
						for (int vx=0; vx<zone.size(); vx++){
							int n=Integer.parseInt(zone.get(vx));
							System.out.print("N is: "+n);
							averaging.set(n, 9999);
						}
					}
					
					company2.addAll(averaging);
					company.add(company2);
					
					obj.put("chart", company);
					objx.put("chart", companyx);
				
					counters = new ArrayList<Integer>(Collections.nCopies(outx.length,0));
					averaging = new ArrayList<Integer>(Collections.nCopies(outx.length,0));
					cnt=0;
				}
			}
			
			finalx.add(obj);finalxr.add(objx);
			
			
			textReadery.close();
			}	
			
			PrintStream outxx3 = new PrintStream(new FileOutputStream(J_Output + "NearHist"+".txt", false));
			System.setOut(outxx3);
			System.out.println(finalx);
			outxx3.close();
			
			PrintStream outxx4 = new PrintStream(new FileOutputStream(J_Output + "NearHist_raw"+".txt", false));
			System.setOut(outxx4);
			System.out.println(finalxr);
			outxx4.close();
			
			long timeAfter = System.currentTimeMillis();
			long elapsedtime = timeAfter - timeBefore;
			if (elapsedtime<20000){
			try {
				Thread.sleep(20000-elapsedtime);
			} catch (InterruptedException e) {
			}
			}
		    
		}while(counter<2);


	}
		
		private static List<Double> interpolationx(double dist1, double dist2, double val1, double val2){
			List<Double> result = new ArrayList<Double>(); double incr = 0.1; 
			for (double ix=dist1; ix<dist2; ix=ix+incr){
				double intercept = val1; double slope = (val2-val1); double num = (ix-dist1);double den = (dist2-dist1);
				double out = intercept + (slope*(num/den));
				result.add((double) Math.round((out*100))/100);
			}
			return result;
			
		}

}

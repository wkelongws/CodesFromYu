import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class InputValidation {

	public static void main(String[] args) throws Exception {
		
		String input = "input";
		File inputFolder = new File(input);
		File[] inputFiles = inputFolder.listFiles();
		
		
		for (File file : inputFiles) {
			String fileName = file.getName();
			
			String name1 = fileName + "-1";
			String name2 = fileName + "-2";
			FileWriter fw1 = new FileWriter("output/" + name1);
			FileWriter fw2 = new FileWriter("output/" + name2);
			
			Scanner scan = new Scanner(file);
			
			int count = 0;
			
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				count++;
				String[] split = line.trim().split(",");
				if ((split.length != 8) && split[1].equals("0")) {
					System.out.println(file.getName()
							+ "line: " + count + "linenumber: " + split.length);
				}

//				String start = split[0].trim().replaceAll("\\s+", "");
//
//				if (start.equals("workzonebegins")
//						|| start.equals("workzonebegin")
//						|| start.equals("workzonestarts")
//						|| start.equals("workzonestart")) {
//					split[0] = "work zone begin";
//					split[1] = "work zone begin";
//					split[8] = "1";
//					flag++;
//				} else if (start.equals("workzoneends")
//						|| start.equals("workzoneend")) {
//					split[0] = "work zone end";
//					split[1] = "work zone end";
//					split[8] = "3";
//					flag++;
//				} else if (start.equals("workzone-middle")
//						|| start.equals("workzonemiddle")
//						|| split[8].equals("2")) {
//					split[0] = "work zone middle";
//					split[1] = "work zone middle";
//					split[8] = "2";
//					flag++;
//				}
				
				int flag = Integer.parseInt(split[0]);
				if (flag == 1) {
					fw1.write(line + "\r\n");
				}
				else if (flag == 2) {
					fw2.write(line + "\r\n");
				}
				else {
					fw1.write(line + "\r\n");
					fw2.write(line + "\r\n");
				}
			}
			
			scan.close();
			fw1.close();
			fw2.close();
		}

	}

}

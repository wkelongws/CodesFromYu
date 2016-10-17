import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;


public class TestProperties {

	public static void main(String[] args) {
		ResourceBundle resb1 = ResourceBundle.getBundle("myres"); 
        System.out.println(resb1.getString("aaa"));
		
		Properties prop = new Properties();  
        
        try {  
            prop.load(new FileInputStream("myres.properties"));  
              
            System.out.println(prop.getProperty("aaa"));  
            prop.setProperty("aaa","nihao");
            prop.store(new FileOutputStream("myres.properties"),"Yu comment");
            System.out.println(prop.getProperty("aaa")); 
        } catch(IOException e) {  
            e.printStackTrace();  
        }  
	}

}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;


public class PrepareMenifest {

	public static final int INDEX_MENIFEST = 0;
	public static final int INDEX_PROPERTY = 1;
	public static final int INDEX_OUTPUT = 2;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("args"+args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}
		PrepareMenifest main = new PrepareMenifest();
		main.readFile(args);
		//main.loadPropertyFile();
		
	}

	
	private  void readFile(String args[]) {
//		args[0] = "./AndroidManifest.xml";
//		args[1] = "./keys.properties";
//		args[2] = "./Temp.xml";
		BufferedReader br = null;
		 
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader(args[0]));
			StringBuffer buffer = new StringBuffer();
 
			while ((sCurrentLine = br.readLine()) != null) {
				buffer.append(sCurrentLine).append("\n");
				//System.out.println(sCurrentLine);
			}
			
			System.out.println(buffer);
			String content = buffer.toString();
			Properties prop = new Properties();
	    	try {
	               //load a properties file
	    		prop.load(new FileInputStream(args[1]));
	    		Enumeration<Object> keys = prop.keys();
	    		while(keys.hasMoreElements()){
	    			 String key = (String) keys.nextElement();
	    			 String value = prop.getProperty(key);
	    		      System.out.println(key + " -- " + value);
	    		      content = content.replace("$"+key+"$", value);
	    		}
	            System.out.println("Content = "+content);  
	    	} catch (IOException ex) {
	    		ex.printStackTrace();
	        }
	    	
	    	// write to a file
	    	File file = new File(args[2]);
	    	if (!file.exists()) {
				file.createNewFile();
			}
	    	FileWriter writer = new FileWriter(file);
	    	writer.append(content);
	    	writer.flush();
	    	writer.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}


	public void loadPropertyFile(){
		Properties prop = new Properties();
    	try {
               //load a properties file
    		prop.load(new FileInputStream("./keys.properties"));
    		Enumeration<Object> keys = prop.keys();
    		while(keys.hasMoreElements()){
    			 String key = (String) keys.nextElement();
    		      System.out.println(key + " -- " + prop.getProperty(key));
    		}
              
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
	}
}

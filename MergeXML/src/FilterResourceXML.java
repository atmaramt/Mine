import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;


public class FilterResourceXML {

	public static final int INDEX_MENIFEST = 0;
	public static final int INDEX_PROPERTY = 1;
	public static final int INDEX_OUTPUT = 2;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("hnghj");
			args = new String[3];
		System.out.println("args"+args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}
		FilterResourceXML main = new FilterResourceXML();
		main.readFile(args);
		//main.loadPropertyFile();
		
	}

	public Hashtable<String, String> loadPropertyFile(String fileName){
		
		Hashtable<String, String> properties = new Hashtable<String, String>();
		Properties prop = new Properties();
    	try {
               //load a properties file
    		prop.load(new FileInputStream(fileName));
    		Enumeration<Object> keys = prop.keys();
    		while(keys.hasMoreElements()){
    			 String key = (String) keys.nextElement();
    			 String value = prop.getProperty(key);
    			 properties.put(key, value);
    		}
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
    	return properties;
	}
	
	private  void readFile(String args[]) {
		args[0] = "./about.xml";
		args[1] = "./keys1.properties";
		args[2] = "./about1.xml";
		BufferedReader br = null;
		 
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader(args[0]));
			StringBuffer buffer = new StringBuffer();
			boolean toBeIgnored = false;
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				if(sCurrentLine.trim().startsWith("<!--")){
					KeyValue keyVal = getKeyValue(sCurrentLine);
					// if key matches
					toBeIgnored = compareKeys(keyVal, args[1]);
					continue;
				}
				else if(sCurrentLine.trim().endsWith("-->")){
					if(sCurrentLine.contains("endif")){
						toBeIgnored = false;
					continue;
					}
				}
				if(!toBeIgnored)
				buffer.append(sCurrentLine).append("\n");
				//System.out.println(sCurrentLine);
			}
			
			System.out.println(buffer);
			String content = buffer.toString();
			
	    	
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


	private boolean compareKeys(KeyValue keyVal, String fileName) {
		Hashtable<String, String> properties = loadPropertyFile(fileName);
		String val = properties.get(keyVal.key);
		if(val!= null && val.equals(keyVal.value)){
			System.out.println("key value matching....");
			return false;
		}
		return true;
	}


	class KeyValue {
		String key;
		String value;
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Key :"+key+" Val:"+value;
		}
	}
	private KeyValue getKeyValue(String sCurrentLine) {
		KeyValue keyVal = new KeyValue();
		String s = sCurrentLine.substring(sCurrentLine.indexOf("<!--")+3);
		// remove if condition
		if(s.indexOf("$if") != -1){
			s = s.substring(s.indexOf("$if")+3).trim();
			System.out.println(s);
		}else{
			return null;
		}
		if(s.indexOf("==") != -1){
			String key = s.substring(0, s.indexOf("==")).trim();
			System.out.println(key);
			String val = s.substring(s.indexOf("==")+2).trim();
			System.out.println(val);
			if(val.indexOf("-->") != -1){
				val = val.substring(0, val.indexOf("-->"));
			}
			keyVal.key = key;
			keyVal.value = val;
		
		}
		System.out.println(keyVal);
		return keyVal;
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

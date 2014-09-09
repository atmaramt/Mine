import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.imageio.ImageIO;

/**
 * 
 */

/**
 * @author Thakur
 *
 */

public class Convert {

	public static final int INDEX_INPUT = 0;
	public static final int INDEX_OUTPUT = 1;
	String inputDir ;
	String outputDir;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		args = new String[]{"", ""};
		System.out.println("args"+args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}
		Convert main = new Convert();
		main.readFile(args);
		//main.loadPropertyFile();
		
	}

	
	private  void readFile(String args[]) {
		args[0] = "./res";
		args[1] = "./res1";
//		args[2] = "./Temp.xml";
		inputDir = args[INDEX_INPUT];
		outputDir = args[INDEX_OUTPUT];
		// load a folder
		File inputFolder = new File(inputDir);
		if(inputFolder.isDirectory()){
			// list all the files 
			String[] fileList = inputFolder.list();
			for (String fileName : fileList) {
				System.out.println("filename "+fileName);
				//fileName = inputDir+"/"+fileName;
				readWriteImage(fileName);
			}
		}
		
		
	}


	/**
	 * 
	 */
	private void readWriteImage(String fileName) {
		try {
			String filePath = inputDir+"/"+fileName;
			File inputfile = new File(filePath);
			BufferedImage imm = ImageIO.read(inputfile);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(imm, "png", baos );
			System.out.println("image read from "+inputfile.getAbsolutePath());
			//write to file
			File outputFolder = new File(outputDir);
			outputFolder.mkdir();
			if(outputFolder.exists() && outputFolder.isDirectory()){
				System.out.println("Output file "+outputDir+"/");
				File outputFile = new File(outputDir+"/"+fileName.substring(0, fileName.indexOf('.')));
				if(!outputFile.exists()) outputFile.createNewFile();
				FileOutputStream str = new FileOutputStream(outputFile);
				str.write(baos.toByteArray());
				str.flush();
				str.close();
				System.out.println("image write to  "+outputFile.getAbsolutePath());
			}else{
			}
			baos.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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


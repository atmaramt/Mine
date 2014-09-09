import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 */

/**
 * @author Thakur
 *
 */
public class MergeXML {
	public static final int INDEX_SOURCE1 = 0;
	public static final int INDEX_SOURCE2 = 1;
	public static final int INDEX_DESTINATION = 2;
	
	public static Hashtable<String, String> allXMLNodes = new Hashtable<String, String>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		args = new String[]{
				"strings1.xml", // source 1
				"strings2.xml", // source 2 - to override
				"destination.xml"// merged file
				};
		if(args.length < 3){
			System.out.println("dffhdjfh");
		}else{
			loadXML(args[INDEX_SOURCE1]);
			loadXML(args[INDEX_SOURCE2]);
			writeXML(args[INDEX_DESTINATION]);
		}
	}

	/**
	 * @param string
	 */
	private static void writeXML(String fileName) {
		StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n <resources>");
		Enumeration<String> list = allXMLNodes.keys();
		while(list.hasMoreElements()){
			String key = list.nextElement();
			String value = allXMLNodes.get(key);
			buffer.append("\n<string name=\""+key+"\">"+value+"</string>");
		}
		buffer.append("\n\n</resources>");
		System.out.println(buffer);
		
		// write to a file
    	File file = new File(fileName);
    	try {
	    	if (!file.exists()) {
					file.createNewFile();
			}
	    	FileWriter writer = new FileWriter(file);
	    	writer.append(buffer);
	    	writer.flush();
	    	writer.close();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	}

	
	/**
	 * @param string
	 */
	private static Hashtable<String, String> loadXML(String fileName) {
//			Hashtable<String, String> xmlNodes = new Hashtable<String, String>();
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			try {
				docBuilder = docBuilderFactory.newDocumentBuilder();
				Document doc = docBuilder.parse (new File(fileName));
				doc.getDocumentElement ().normalize ();
				System.out.println ("Root element of the doc is " + doc.getDocumentElement().getNodeName());
				
				NodeList listOfstrings = doc.getElementsByTagName("string");
				int length = listOfstrings.getLength();
				System.out.println("Length = "+length);
				for(int s=0; s<length ; s++){
					Node stringNode = listOfstrings.item(s);
					if(stringNode.getNodeType() == Node.ELEMENT_NODE){
						NamedNodeMap maps = stringNode.getAttributes();
						String key = maps.item(0).getNodeValue();
						NodeList valueList = stringNode.getChildNodes();
						String value = ((Node)valueList.item(0)).getNodeValue().trim();
						System.out.println(key+ " : " + value);
						allXMLNodes.put(key, value);
					}
				}
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// normalize text representation
			return allXMLNodes;
		}
		
}

package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.services;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This abstract class acts as the parent of all other service classes. 
 * 
 * @author Dustin Noah Young
 *
 */
public abstract class EntityService {
	/**
	 * Extracts information from an XML entity.
	 * 
	 * @param tagNames List of attribute names.
	 * @param startingIndexForTag The index to start in the attributes.
	 * @param valueCount Number of values to be extracted.
	 * @param element The root XML element.
	 * 
	 * @return The values in a String array.
	 */
	public String[] extractInformationFromXMLEntity(String[] tagNames, int startingIndexForTag, int valueCount, Element element) {
		String[] values = new String[valueCount];
		
		// We begin at two because we don't need "Customer" and "CustomerId"
		for(int i = startingIndexForTag; i < tagNames.length; i++) {
			String value = getInformationFromXMLElement(tagNames[i], element);
			
			if(value.equals("")) {
				throw new NullPointerException("Value " + tagNames[i] + " is empty!");
			}else {
				values[i-startingIndexForTag] = value;
			}
		}
		
		return values;
	}
	
	/**
	 * Extracts the information from a node.
	 * 
	 * @param tagName Name of the XML node.
	 * @param element XML node.
	 * 
	 * @return Value of the node.
	 */
	public String getInformationFromXMLElement(String tagName, Element element) {
		String value = "";
		
        NodeList elementList = element.getElementsByTagName(tagName);
        
        // If node list is not empty we may save the value
        if (elementList != null && elementList.getLength() > 0) {
        	value = elementList.item(0).getNodeValue();
        }

        return value;
    }
}

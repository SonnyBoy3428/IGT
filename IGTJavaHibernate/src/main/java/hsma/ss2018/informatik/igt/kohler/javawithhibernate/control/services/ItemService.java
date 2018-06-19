package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.ItemRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;

/**
 * This class acts as the API for calls to the item repository.
 * 
 * @author Dustin Noah Young, 
 *
 */
@Path("/itemService")
public class ItemService extends EntityService{
	/**
	 * The tag names belonging to a item XML object.
	 */
	static final String[] TAG_NAMES = {"Item", "ItemId", "ItemName", "Price"};
	
	/**
	 * Receives a POST request to create a item. The item information is located in the request body.
	 * 
	 * @param itemInformation Request body containing item information.
	 * 
	 * @return The response containing the item.
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@POST
	@Path("/createItem")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createItem(String itemInformation) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(new InputSource(new StringReader(itemInformation)));
		Element rootElement = document.getDocumentElement();
		
		String[] itemValues = extractInformationFromXMLEntity(TAG_NAMES, 2, 2, rootElement);
		
		Item createdItem = ItemRepository.createItem(itemValues[0], Double.parseDouble(itemValues[1]));
		
		return Response.status(200).entity(ItemRepository.itemToXML(createdItem)).build();
	}
	
	/**
	 * Receives a GET request to get a item. The item id is located within the URL.
	 * 
	 * @param itemId The item Id located in the URL.
	 * 
	 * @return The response containing the item.
	 */
	@GET
	@Path("/getItemById={param}")
	public Response getItemById(@PathParam("param") long itemId) {
		Item item = ItemRepository.getItem(itemId);
		
		String itemXML = ItemRepository.itemToXML(item);
		
		return Response.status(200).entity(itemXML).build();
	}
	
	/**
	 * Receives a GET request to get all items.
	 * 
	 * @return The response containing the items.
	 */
	@GET
	@Path("/getAllItems")
	public Response getAllItems() {
		Set<Item> items = ItemRepository.getAllItems();
		
		String itemsXML = ItemRepository.itemsToXML(items);
		
		return Response.status(200).entity(itemsXML).build();
	}
	
	/**
	 * Receives a DELETE request to delete a item. The item id is located within the URL.
	 * 
	 * @param itemId The item id located inside the URL.
	 * 
	 * @return The response.
	 */
	@DELETE
	@Path("/deleteItemByItemId={param}")
	public Response deleteItem(@PathParam("param") long itemId) {
		ItemRepository.deleteItem(itemId);
		
		String response = "Deletion of item with id: " + itemId + " was successful!";
		
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Receives a PUT request to update a item. The item information is located within the request body.
	 * 
	 * @param itemInformation The item information inside the request body.
	 * 
	 * @return The response containing the updated item.
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@PUT
	@Path("/updateItem")
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateItem(String itemInformation) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(new InputSource(new StringReader(itemInformation)));
		Element rootElement = document.getDocumentElement();
		
		String[] itemValues = extractInformationFromXMLEntity(TAG_NAMES, 1, 3, rootElement);
		
		Item updatedItem = ItemRepository.updateItem(Long.parseLong(itemValues[0]), itemValues[1], Double.parseDouble(itemValues[2]));
		
		return Response.status(200).entity(ItemRepository.itemToXML(updatedItem)).build();
	}
}

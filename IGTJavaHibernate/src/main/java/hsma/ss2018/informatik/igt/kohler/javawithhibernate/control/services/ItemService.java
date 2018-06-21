package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.services;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.ItemRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;

/**
 * This class acts as the API for calls to the item repository.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 *
 */
@Path("/itemService")
public class ItemService extends EntityService{	
	/**
	 * Receives a POST request to create a item. The item information is located in the request body.
	 * 
	 * @param itemInformation Request body containing item information.
	 * 
	 * @return The response containing the item.
	 */
	@POST
	@Path("/createItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response createItem(String itemInformation) {
		JSONObject newItem = new JSONObject(itemInformation);
		
		Item createdItem = ItemRepository.createItem(newItem.getString("ItemName"), newItem.getDouble("Price"));
		
		JSONObject response = new JSONObject();
		
		if(createdItem != null) {
			response.put("Item", ItemRepository.itemToJSON(createdItem));
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Creation of item failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
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
	@Produces("application/json")
	public Response getItemById(@PathParam("param") int itemId) {
		Item item = ItemRepository.getItem(itemId);
		
		JSONObject response = new JSONObject();
		
		if(item != null) {
			response.put("Item", ItemRepository.itemToJSON(item));
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Fetching of item with id " + itemId + " failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to get all items.
	 * 
	 * @return The response containing the items.
	 */
	@GET
	@Path("/getAllItems")
	@Produces("application/json")
	public Response getAllItems() {
		Set<Item> items = ItemRepository.getAllItems();
		
		JSONObject response = new JSONObject();
		
		if(items != null) {
			response = new JSONObject().put("Items", ItemRepository.itemsToJSON(items));
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Fetching of items failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
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
	@Produces("application/json")
	public Response deleteItem(@PathParam("param") int itemId) {
		boolean itemDeleted = ItemRepository.deleteItem(itemId);
		
		JSONObject response = new JSONObject();
		
		if(itemDeleted) {
			response.put("Message", "Deletion of item with id: " + itemId + " successful!");
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Deletion of item with id: " + itemId + " failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a PUT request to update a item. The item information is located within the request body.
	 * 
	 * @param itemInformation The item information inside the request body.
	 * 
	 * @return The response containing the updated item.
	 */
	@PUT
	@Path("/updateItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response updateItem(String itemInformation){
		JSONObject item = new JSONObject(itemInformation);
		
		Item updatedItem = ItemRepository.updateItem(item.getInt("ItemId"), item.getString("ItemName"), item.getDouble("Price"));
		
		JSONObject response = new JSONObject();
		
		if(updatedItem != null) {
			response.put("Item", ItemRepository.itemToJSON(updatedItem));
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Update of item with id: " + item.getInt("ItemId") + " failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
}

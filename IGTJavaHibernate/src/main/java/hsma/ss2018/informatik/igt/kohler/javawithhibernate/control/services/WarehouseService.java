package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.services;

import java.util.HashMap;
import java.util.Map;
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

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.StockRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.WarehouseRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Warehouse;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.District;

/**
 * This class acts as the API for calls to the warehouse repository.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 *
 */
@Path("/warehouseService")
public class WarehouseService extends EntityService{	
	/**
	 * Receives a POST request to create a warehouse. The warehouse information is located in the request body.
	 * 
	 * @param warehouseInformation Request body containing warehouse information.
	 * 
	 * @return The response containing the warehouse.
	 */
	@POST
	@Path("/createWarehouse")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response createWarehouse(String warehouseInformation){
		JSONObject newWarehouse = new JSONObject(warehouseInformation);
		
		Warehouse createdWarehouse = WarehouseRepository.createWarehouse(newWarehouse.getString("Location"), newWarehouse.getString("WarehouseOwner"));
		
		JSONObject response = new JSONObject();
		
		if(createdWarehouse != null) {
			response.put("Warehouse", WarehouseRepository.warehouseToJSON(createdWarehouse));
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Creation of warehouse failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to get a warehouse. The warehouse id is located within the URL.
	 * 
	 * @param warehouseId The warehouse Id located in the URL.
	 * 
	 * @return The response containing the warehouse.
	 */
	@GET
	@Path("/getWarehouseById={param}")
	@Produces("application/json")
	public Response getWarehouseById(@PathParam("param") int warehouseId) {
		Warehouse warehouse = WarehouseRepository.getWarehouse(warehouseId);
		
		JSONObject response = new JSONObject();
		
		if(warehouse != null) {
			response.put("Warehouse", WarehouseRepository.warehouseToJSON(warehouse));
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Fetching of warehouse with id " + warehouseId + " failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to get all warehouses.
	 * 
	 * @return The response containing the warehouses.
	 */
	@GET
	@Path("/getAllWarehouses")
	@Produces("application/json")
	public Response getAllWarehouses() {
		Set<Warehouse> warehouses = WarehouseRepository.getAllWarehouses();
		
		JSONObject response = new JSONObject();
		
		if(warehouses != null) {
			response = new JSONObject().put("Warehouses", WarehouseRepository.warehousesToJSON(warehouses));
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Fetching of warehouses failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to get all districts beinting to a warehouse. The warehouse id is located within the URL.
	 * 
	 * @param warehouseId The warehouse id located in the URL.
	 * 
	 * @return The response containing the warehouse and all his districts.
	 */
	@GET
	@Path("/getWarehouseDistrictsByWarehouseId={param}")
	@Produces("application/json")
	public Response getAllWarehouseDistricts(@PathParam("param") int warehouseId) {
		Warehouse warehouse = WarehouseRepository.getWarehouse(warehouseId);
		
		JSONObject response = new JSONObject();
		
		if(warehouse != null) {
			Set<District> districts = WarehouseRepository.getWarehouseDistricts(warehouseId);
			
			if(districts != null) {
				response = WarehouseRepository.warehouseAndDistrictsToJSON(warehouse, districts);
				
				return Response.status(200).entity(response.toString()).build();
			}else {
				response.put("Message", "Warehouse with id " + warehouseId + " does not have any districts!");
				
				return Response.status(200).entity(response.toString()).build();
			}
		}else {
			response.put("Message", "Fetching of warehouse with id " + warehouseId + " failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a DELETE request to delete a warehouse. The warehouse id is located within the URL.
	 * 
	 * @param warehouseId The warehouse id located inside the URL.
	 * 
	 * @return The response.
	 */
	@DELETE
	@Path("/deleteWarehouseByWarehouseId={param}")
	@Produces("application/json")
	public Response deleteWarehouse(@PathParam("param") int warehouseId) {
		boolean districtDeleted = WarehouseRepository.deleteWarehouse(warehouseId);
		
		JSONObject response = new JSONObject();
		
		if(districtDeleted) {
			response.put("Message", "Deletion of warehouse with id " + warehouseId + " successful!");
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Deletion of warehouse with id " + warehouseId + " failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a PUT request to update a warehouse. The warehouse information is located within the request body.
	 * 
	 * @param warehouseInformation The warehouse information inside the request body.
	 * 
	 * @return The response containing the updated warehouse.
	 */
	@PUT
	@Path("/updateWarehouse")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response updateWarehouse(String warehouseInformation){
		JSONObject warehouse = new JSONObject(warehouseInformation);
		
		Warehouse updatedWarehouse = WarehouseRepository.updateWarehouse(warehouse.getInt("WarehouseId"), warehouse.getString("Location"), warehouse.getString("Owner"));
		
		JSONObject response = new JSONObject();
		
		if(updatedWarehouse != null) {
			response.put("Warehouse", WarehouseRepository.warehouseToJSON(updatedWarehouse));
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Update of warehouse with id " + warehouse.getInt("WarehouseId") + " failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	@POST
	@Path("/createStockForWarehouseId={warehouseId}/itemId={itemId}/quantity={quantity}")
	@Produces("application/json")
	public Response createStockForWarehouse(@PathParam("warehouseId") int warehouseId, @PathParam("itemId") int itemId, @PathParam("quantity") int quantity) {
		Warehouse warehouse = StockRepository.createStock(warehouseId, itemId, quantity);
		Map<Integer, Integer> itemsAndQuantities = WarehouseRepository.getAllItemsOfWarehouse(warehouseId);
		
		JSONObject response = new JSONObject();
		
		if(warehouse != null) {
			
			if(itemsAndQuantities.size() > 0) {
				response = WarehouseRepository.completeWarehouseToJSON(warehouse, itemsAndQuantities);
				
				return Response.status(200).entity(response.toString()).build();	
			}else {
				response.put("Message", "Warehouse with id " + warehouseId + " does not have any items!");
				
				return Response.status(500).entity(response.toString()).build();
			}
		}else {
			response.put("Message", "Creation of stock failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to get the warehouse and all its stocks. The warehouse id is located within the URL.
	 * 
	 * @param warehouseId The warehouse id located in the URL.
	 * 
	 * @return The response containing the warehouse and all its stocks.
	 */
	@GET
	@Path("/getWarehouseStockByWarehouseId={param}")
	@Produces("application/json")
	public Response getWarehouseStock(@PathParam("param") int warehouseId) {
		Warehouse warehouse = WarehouseRepository.getWarehouse(warehouseId);
		
		JSONObject response = new JSONObject();
		
		if(warehouse != null) {
			Map<Integer, Integer> itemsAndQuantities = WarehouseRepository.getAllItemsOfWarehouse(warehouseId);
			
			if(itemsAndQuantities.size() > 0) {
				response = WarehouseRepository.completeWarehouseToJSON(warehouse, itemsAndQuantities);
				
				return Response.status(200).entity(response.toString()).build();
			}else {
				response.put("Message", "Warehosue with the id " + warehouseId + " does not have any items!");
				
				return Response.status(500).entity(response.toString()).build();
			}
		}else {
			response.put("Message", "Fetching of warehouse with id " + warehouseId + " failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to get all warehouses and all their stocks. The warehouse id is located within the URL.
	 * 
	 * @param warehouseId The warehouse id located in the URL.
	 * 
	 * @return The response containing the warehouse and all its stocks.
	 */
	@GET
	@Path("/getAllWarehousesAndTheirStocks")
	@Produces("application/json")
	public Response getWarehousesStock() {
		Set<Warehouse> warehouses = WarehouseRepository.getAllWarehouses();
		
		Map<Warehouse, Map<Integer, Integer>> completeWarehouses = new HashMap<Warehouse, Map<Integer, Integer>>();
		
		JSONObject response = new JSONObject();
		
		if(warehouses != null) {			
			for(Warehouse warehouse : warehouses) {
				Map<Integer, Integer> itemsAndQuantities = WarehouseRepository.getAllItemsOfWarehouse(warehouse.getWarehouseId());
				
				if(itemsAndQuantities.size() > 0) {
					completeWarehouses.put(warehouse, itemsAndQuantities);
				}else {
					response.put("Message", "Warehouse with the id " + warehouse.getWarehouseId() + " does not have any items!");
					
					return Response.status(500).entity(response.toString()).build();
				}
			}
		}else {
			response.put("Message", "Fetching of warehouses failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
		
		return Response.status(200).entity(WarehouseRepository.completeWarehousesToJSON(completeWarehouses)).build();
	}
	
	/**
	 * Receives a DELETE request to delete a stock. The warehouse id is located within the URL.
	 * 
	 * @param warehouseId The order id located inside the URL.
	 * 
	 * @return The response.
	 */
	@DELETE
	@Path("/deleteStockByWarehouseId={param}")
	@Produces("application/json")
	public Response deleteStock(@PathParam("param") int warehouseId) {
		boolean stockDeleted = StockRepository.deleteStock(warehouseId);
		
		JSONObject response = new JSONObject();
		
		if(stockDeleted) {
			response.put("Message", "Deletion of stock of warehouse with id " + warehouseId + " successful!");
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Deletion of stock of warehouse with id " + warehouseId + " failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a PUT request to update a warehouse stock. The warehouse information is located within the URL.
	 * 
	 * @param warehouseInformation The order information inside the request body.
	 * 
	 * @return The response containing the updated stock
	 */
	@PUT
	@Path("/updateStockByWarehouseId={warehouse}/itemId={item}/updateType={type}/quantity={quantity}")
	@Produces("application/json")
	public Response updateWarehouseStock(@PathParam("warehouse") int warehouseId, @PathParam("item") int itemId, @PathParam("type") String updateType, @PathParam("quantity") int quantity){		
		Warehouse warehouse = StockRepository.updateStock(warehouseId, itemId, updateType, quantity);
		
		JSONObject response = new JSONObject();
		
		if(warehouse != null) {
			Map<Integer, Integer> itemsAndQuantities = new HashMap<Integer, Integer>();
			itemsAndQuantities = WarehouseRepository.getAllItemsOfWarehouse(warehouseId);
			
			if(itemsAndQuantities.size() > 0) {
				response = WarehouseRepository.completeWarehouseToJSON(warehouse, itemsAndQuantities);
				
				return Response.status(200).entity(response.toString()).build();
			}else {
				response.put("Message", "Warehouse id " + warehouseId + " does not have any items!");
				
				return Response.status(500).entity(response.toString()).build();
			}
		}else {
			response.put("Message", "Update of warehouse with id: " + warehouseId + " failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
}

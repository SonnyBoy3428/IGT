package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.services;

import java.io.IOException;
import java.io.StringReader;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.OrderRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.OrderlineRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.StockRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.WarehouseRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Warehouse;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.District;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Stock;

/**
 * This class acts as the API for calls to the warehouse repository.
 * 
 * @author Dustin Noah Young, 
 *
 */
@Path("/warehouseService")
public class WarehouseService extends EntityService{
	/**
	 * The tag names beinting to a warehouse XML object.
	 */
	static final String[] TAG_NAMES = {"Warehouse", "WarehouseId", "Location", "Owner"};
	
	/**
	 * Receives a POST request to create a warehouse. The warehouse information is located in the request body.
	 * 
	 * @param warehouseInformation Request body containing warehouse information.
	 * 
	 * @return The response containing the warehouse.
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@POST
	@Path("/createWarehouse")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createWarehouse(String warehouseInformation) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(new InputSource(new StringReader(warehouseInformation)));
		Element rootElement = document.getDocumentElement();
		
		String[] warehouseValues = extractInformationFromXMLEntity(TAG_NAMES, 2, 2, rootElement);
		
		Warehouse createdWarehouse = WarehouseRepository.createWarehouse(warehouseValues[0], warehouseValues[1]);
		
		return Response.status(200).entity(WarehouseRepository.warehouseToXML(createdWarehouse)).build();
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
	public Response getWarehouseById(@PathParam("param") int warehouseId) {
		Warehouse warehouse = WarehouseRepository.getWarehouse(warehouseId);
		
		String warehouseXML = WarehouseRepository.warehouseToXML(warehouse);
		
		return Response.status(200).entity(warehouseXML).build();
	}
	
	/**
	 * Receives a GET request to get all warehouses.
	 * 
	 * @return The response containing the warehouses.
	 */
	@GET
	@Path("/getAllWarehouses")
	public Response getAllWarehouses() {
		Set<Warehouse> warehouses = WarehouseRepository.getAllWarehouses();
		
		String warehousesXML = WarehouseRepository.warehousesToXML(warehouses);
		
		return Response.status(200).entity(warehousesXML).build();
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
	public Response getAllWarehouseDistricts(@PathParam("param") int warehouseId) {
		Warehouse warehouse = WarehouseRepository.getWarehouse(warehouseId);
		Set<District> districts = WarehouseRepository.getWarehouseDistricts(warehouseId);
		
		String warehouseAndDistrictsXML = WarehouseRepository.warehouseAndDistrictsToXML(warehouse, districts);
		
		return Response.status(200).entity(warehouseAndDistrictsXML).build();
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
	public Response deleteWarehouse(@PathParam("param") int warehouseId) {
		WarehouseRepository.deleteWarehouse(warehouseId);
		
		String response = "Deletion of warehouse with id: " + warehouseId + " was successful!";
		
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Receives a PUT request to update a warehouse. The warehouse information is located within the request body.
	 * 
	 * @param warehouseInformation The warehouse information inside the request body.
	 * 
	 * @return The response containing the updated warehouse.
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@PUT
	@Path("/updateWarehouse")
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateWarehouse(String warehouseInformation) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(new InputSource(new StringReader(warehouseInformation)));
		Element rootElement = document.getDocumentElement();
		
		String[] warehouseValues = extractInformationFromXMLEntity(TAG_NAMES, 1, 3, rootElement);
		
		Warehouse updatedWarehouse = WarehouseRepository.updateWarehouse(Integer.parseInt(warehouseValues[0]), warehouseValues[1], warehouseValues[2]);
		
		return Response.status(200).entity(WarehouseRepository.warehouseToXML(updatedWarehouse)).build();
	}
	
	@POST
	@Path("/createStockForWarehouseId={warehouseId}/itemId={itemId}/quantity={quantity}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createStockForWarehouse(@PathParam("warehouseId") int warehouseId, @PathParam("itemId") int itemId, @PathParam("quantity") int quantity) {
		StockRepository.createStock(warehouseId, itemId, quantity);
		
		String response = "Stock successfully added to warehouse with the id:" + warehouseId + "!";
		
		return Response.status(200).entity(response).build();
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
	public Response getWarehouseStock(@PathParam("param") int warehouseId) {
		Warehouse warehouse = WarehouseRepository.getWarehouse(warehouseId);
		Map<Integer, Integer> itemIdsAndQuantity = WarehouseRepository.getAllItemsOfWarehouse(warehouseId);
	
		return Response.status(200).entity(WarehouseRepository.completeWarehouseToXML(warehouse, itemIdsAndQuantity)).build();
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
	public Response getWarehouseStock() {
		Set<Warehouse> warehouses = WarehouseRepository.getAllWarehouses();
		Map<Warehouse, Map<Integer, Integer>> completeWarehouses = new HashMap<Warehouse, Map<Integer, Integer>>();
		
		for(Warehouse warehouse : warehouses) {
			Map<Integer, Integer> itemIdsAndQuantity = WarehouseRepository.getAllItemsOfWarehouse(warehouse.getWarehouseId());
			completeWarehouses.put(warehouse, itemIdsAndQuantity);
		}
	
		return Response.status(200).entity(WarehouseRepository.completeWarehousesToXML(completeWarehouses)).build();
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
	public Response deleteOrder(@PathParam("param") int warehouseId) {
		StockRepository.deleteStock(warehouseId);
		
		String response = "Deletion of stock beinting to warehouse with id: " + warehouseId + " was successful!";
		
		return Response.status(200).entity(response).build();
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
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateWarehouseStock(@PathParam("warehouse") int warehouseId, @PathParam("item") int itemId, @PathParam("type") String updateType, @PathParam("quantity") int quantity){		
		StockRepository.updateStock(warehouseId, itemId, updateType, quantity);
		
		Warehouse warehouse = WarehouseRepository.getWarehouse(warehouseId);
		Map<Integer, Integer> itemsAndQuantities = WarehouseRepository.getAllItemsOfWarehouse(warehouseId);
		
		return Response.status(200).entity(WarehouseRepository.completeWarehouseToXML(warehouse, itemsAndQuantities)).build();
	}
}

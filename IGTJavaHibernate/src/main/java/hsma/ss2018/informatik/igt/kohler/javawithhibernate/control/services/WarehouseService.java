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

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.WarehouseRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Warehouse;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.District;

/**
 * This class acts as the API for calls to the warehouse repository.
 * 
 * @author Dustin Noah Young, 
 *
 */
@Path("/warehouseService")
public class WarehouseService extends EntityService{
	/**
	 * The tag names belonging to a warehouse XML object.
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
	public Response getWarehouseById(@PathParam("param") long warehouseId) {
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
	 * Receives a GET request to get all districts belonging to a warehouse. The warehouse id is located within the URL.
	 * 
	 * @param warehouseId The warehouse id located in the URL.
	 * 
	 * @return The response containing the warehouse and all his districts.
	 */
	@GET
	@Path("/getWarehouseDistrictsByWarehouseId={param}")
	public Response getAllWarehouseDistricts(@PathParam("param") long warehouseId) {
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
	public Response deleteWarehouse(@PathParam("param") long warehouseId) {
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
		
		Warehouse updatedWarehouse = WarehouseRepository.updateWarehouse(Long.parseLong(warehouseValues[0]), warehouseValues[1], warehouseValues[2]);
		
		return Response.status(200).entity(WarehouseRepository.warehouseToXML(updatedWarehouse)).build();
	}
}

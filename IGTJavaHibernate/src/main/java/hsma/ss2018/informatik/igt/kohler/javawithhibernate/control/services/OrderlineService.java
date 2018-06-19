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

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.OrderlineRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Orderline;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;

/**
 * This class acts as the API for calls to the orderline repository.
 * 
 * @author Dustin Noah Young, 
 *
 */
@Path("/orderlineService")
public class OrderlineService extends EntityService{
	/**
	 * The tag names belonging to a orderline XML object.
	 */
	static final String[] TAG_NAMES = {"Orderline", "OrderlineId", "FirstName", "LastName", "Address", "Telephone", "CreditCardNr", "DistrictId"};
	
	/**
	 * Receives a POST request to create a orderline. The orderline information is located in the request body.
	 * 
	 * @param orderlineInformation Request body containing orderline information.
	 * 
	 * @return The response containing the orderline.
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@POST
	@Path("/createOrderlineForCustomerId={param}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createOrderline(@PathParam("param") long customerId, String orderlineInformation) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(new InputSource(new StringReader(orderlineInformation)));
		Element rootElement = document.getDocumentElement();
		
		String[] orderlineValues = extractInformationFromXMLEntity(TAG_NAMES, 2, 6, rootElement);
		
		Orderline createdOrderline = OrderlineRepository.createOrderline(customerId);
		
		return Response.status(200).entity(OrderlineRepository.orderlineToXML(createdOrderline)).build();
	}
	
	/**
	 * Receives a GET request to get a orderline. The orderline id is located within the URL.
	 * 
	 * @param orderlineId The orderline Id located in the URL.
	 * 
	 * @return The response containing the orderline.
	 */
	@GET
	@Path("/getOrderlineById={param}")
	public Response getOrderlineById(@PathParam("param") long orderlineId) {
		Orderline orderline = OrderlineRepository.getOrderline(orderlineId);
		
		String orderlineXML = OrderlineRepository.orderlineToXML(orderline);
		
		return Response.status(200).entity(orderlineXML).build();
	}
	
	/**
	 * Receives a GET request to get all orderlines.
	 * 
	 * @return The response containing the orderlines.
	 */
	@GET
	@Path("/getAllOrderlines")
	public Response getAllOrderlines() {
		Set<Orderline> orderlines = OrderlineRepository.getAllOrderlines();
		
		String orderlinesXML = OrderlineRepository.orderlinesToXML(orderlines);
		
		return Response.status(200).entity(orderlinesXML).build();
	}
	
	/**
	 * Receives a GET request to get all orders belonging to a orderline. The orderline id is located within the URL.
	 * 
	 * @param orderlineId The orderline id located in the URL.
	 * 
	 * @return The response containing the orderline and all his orders.
	 */
	@GET
	@Path("/getAllOrderlineOrdersByOrderlineId={param}")
	public Response getAllOrderlineOrders(@PathParam("param") long orderlineId) {
		Orderline orderline = OrderlineRepository.getOrderline(orderlineId);
		Set<Order> orders = OrderlineRepository.getOrderlineAllOrders(orderlineId);
		
		String orderlineAndOrdersXML = OrderlineRepository.orderlineAndOrdersToXML(orderline, orders);
		
		return Response.status(200).entity(orderlineAndOrdersXML).build();
	}
	
	/**
	 * Receives a GET request to get all new orders belonging to a orderline. The orderline id is located within the URL.
	 * 
	 * @param orderlineId The orderline id located in the URL.
	 * 
	 * @return The response containing the orderline and all his new orders.
	 */
	@GET
	@Path("/getAllNewOrderlineOrdersByOrderlineId={param}")
	public Response getNewOrderlineOrders(@PathParam("param") long orderlineId) {
		Orderline orderline = OrderlineRepository.getOrderline(orderlineId);
		Set<Order> orders = OrderlineRepository.getOrderlineNewOrders(orderlineId);
		
		String orderlineAndOrdersXML = OrderlineRepository.orderlineAndOrdersToXML(orderline, orders);
		
		return Response.status(200).entity(orderlineAndOrdersXML).build();
	}
	
	/**
	 * Receives a GET request to the order history belonging to a orderline. The orderline id is located within the URL.
	 * 
	 * @param orderlineId The orderline id located in the URL.
	 * 
	 * @return The response containing the orderline and his order history.
	 */
	@GET
	@Path("/getOrderlineOrderHistoryByOrderlineId={param}")
	public Response getOrderlineOrderHistory(@PathParam("param") long orderlineId) {
		Orderline orderline = OrderlineRepository.getOrderline(orderlineId);
		Set<Order> orders = OrderlineRepository.getOrderlineOrderHistory(orderlineId);
		
		String orderlineAndOrdersXML = OrderlineRepository.orderlineAndOrdersToXML(orderline, orders);
		
		return Response.status(200).entity(orderlineAndOrdersXML).build();
	}
	
	/**
	 * Receives a DELETE request to delete a orderline. The orderline id is located within the URL.
	 * 
	 * @param orderlineId The orderline id located inside the URL.
	 * 
	 * @return The response.
	 */
	@DELETE
	@Path("/deleteOrderlineByOrderlineId={param}")
	public Response deleteOrderline(@PathParam("param") long orderlineId) {
		OrderlineRepository.deleteOrderline(orderlineId);
		
		String response = "Deletion of orderline with id: " + orderlineId + " was successful!";
		
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Receives a PUT request to update a orderline. The orderline information is located within the request body.
	 * 
	 * @param orderlineInformation The orderline information inside the request body.
	 * 
	 * @return The response containing the updated orderline.
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@PUT
	@Path("/updateOrderline")
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateOrderline(String orderlineInformation) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(new InputSource(new StringReader(orderlineInformation)));
		Element rootElement = document.getDocumentElement();
		
		String[] orderlineValues = extractInformationFromXMLEntity(TAG_NAMES, 1, 6, rootElement);
		
		Orderline updatedOrderline = OrderlineRepository.updateOrderline(Long.parseLong(orderlineValues[0]), orderlineValues[1], orderlineValues[2], orderlineValues[3], orderlineValues[4], orderlineValues[5]);
		
		return Response.status(200).entity(OrderlineRepository.orderlineToXML(updatedOrderline)).build();
	}
}

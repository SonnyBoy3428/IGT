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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.OrderRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.OrderlineRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Orderline;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;

/**
 * This class acts as the API for calls to the order and orderline repositories.
 * 
 * @author Dustin Noah Young, 
 *
 */
@Path("/orderAndOrderlineService")
public class OrderlineService extends EntityService{
	/**
	 * The tag names belonging to a orderline XML object.
	 */
	static final String[] TAG_NAMES = {"Orderline", "OrderlineId", "FirstName", "LastName", "Address", "Telephone", "CreditCardNr", "DistrictId"};
	
	/**
	 * Receives a POST request to create an order. The order information is located in the request body.
	 * Even though we are dealing with orderlines a user would expect an order.
	 * 
	 * @param orderInformation Request body containing order information.
	 * 
	 * @return The response containing the order.
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@POST
	@Path("/createCompleteOrderForCustomerId={param}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createCompleteOrder(@PathParam("param") long customerId, String orderlineInformation) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(new InputSource(new StringReader(orderlineInformation)));
		Element rootElement = document.getDocumentElement();
		
		Map<Long, Long> itemsAndQuantities = extractMultipleItemsFromXML(rootElement);
		
		OrderlineRepository.createOrderline(customerId, itemsAndQuantities);
		
		String response = "Order and order successfully created!";
		
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Converts Items and their quantities from XML into a Map.
	 * 
	 * @param element XML element.
	 * 
	 * @return Returns a Map of item ids and quantities.
	 */
	private Map<Long, Long> extractMultipleItemsFromXML(Element element){
		Map<Long, Long> itemsAndQuantities = new HashMap<Long, Long>();
		
		NodeList elementList = element.getElementsByTagName("Items");
		NodeList itemList = elementList.item(0).getChildNodes();
		
		for(int i = 0; i < itemList.getLength(); i++) {
			Node item = itemList.item(i);
			
			long itemId = Long.parseLong(item.getFirstChild().getNodeValue());
			long itemQuantity = Long.parseLong(item.getLastChild().getNodeValue());
			
			itemsAndQuantities.put(itemId, itemQuantity);
		}
		
		return itemsAndQuantities;
	}
	
	/**
	 * Receives a GET request to get an order. The order id is located within the URL.
	 * We are getting an order and all its items.
	 * 
	 * @param orderlineId The order Id located in the URL.
	 * 
	 * @return The response containing the order.
	 */
	@GET
	@Path("/getCompleteOrderById={param}")
	public Response getCompleteOrderById(@PathParam("param") long orderId) {
		Order order = OrderRepository.getOrder(orderId);
		Map<Long, Long> itemsAndQuantities = OrderRepository.getAllItemsOfOrder(orderId);
		
		return Response.status(200).entity(OrderRepository.completeOrderToXML(order, itemsAndQuantities)).build();
	}
	
	/**
	 * Receives a GET request to get all orders.
	 * 
	 * @return The response containing the orders.
	 */
	@GET
	@Path("/getAllOrdersOfCustomer={param}")
	public Response getAllOrdersOfCustomer(@PathParam("param") long customerId) {
		Set<Order> orders = OrderRepository.getAllOrdersOfCustomer(customerId);
		Map<Order, Map<Long, Long>> completeOrders = new HashMap<Order, Map<Long, Long>>();
		
		for(Order order : orders) {
			Map<Long, Long> itemsAndQuantities = OrderRepository.getAllItemsOfOrder(order.getOrderId());
			
			completeOrders.put(order, itemsAndQuantities);
		}
		
		return Response.status(200).entity(OrderRepository.completeOrdersToXML(completeOrders)).build();
	}
	
	/**
	 * Receives a DELETE request to delete an order. The order id is located within the URL.
	 * 
	 * @param orderId The order id located inside the URL.
	 * 
	 * @return The response.
	 */
	@DELETE
	@Path("/deleteOrderById={param}")
	public Response deleteOrder(@PathParam("param") long orderId) {
		OrderlineRepository.deleteOrderline(orderId);
		
		String response = "Deletion of order with id: " + orderId + " was successful!";
		
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Receives a PUT request to update an order. The order information is located within the URL.
	 * 
	 * @param orderInformation The order information inside the request body.
	 * 
	 * @return The response containing the updated order.
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@PUT
	@Path("/updateOrderById={order}/{item}/{type}/{quantity}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateOrder(@PathParam("order") long orderId, @PathParam("item") long itemId, @PathParam("type") String updateType, @PathParam("quantity") long quantity){		
		OrderlineRepository.updateOrderline(orderId, itemId, updateType, quantity);
		
		Order order = OrderRepository.getOrder(orderId);
		Map<Long, Long> itemsAndQuantities = OrderRepository.getAllItemsOfOrder(orderId);
		
		return Response.status(200).entity(OrderRepository.completeOrderToXML(order, itemsAndQuantities)).build();
	}
}

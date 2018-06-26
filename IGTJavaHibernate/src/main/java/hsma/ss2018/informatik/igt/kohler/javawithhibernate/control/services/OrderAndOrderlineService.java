package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.services;

import java.io.IOException;
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
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.EntityRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.OrderRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.OrderlineRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;

/**
 * This class acts as the API for calls to the order and orderline repositories.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 *
 */
@Path("/orderAndOrderlineService")
public class OrderAndOrderlineService{
	/**
	 * Receives a POST request to create an order. The order information is located in the request body.
	 * Even though we are dealing with orderlines a user would expect an order.
	 * 
	 * @param orderInformation Request body containing order information.
	 * 
	 * @return The response containing the order.
	 */
	@POST
	@Path("/createCompleteOrderForCustomerId={customerId}/date={date}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response createCompleteOrder(@PathParam("customerId") int customerId, @PathParam("date") String date, String orderlineInformation){
		EntityRepository.setUp();
		
		JSONObject newOrderline = new JSONObject(orderlineInformation);
		JSONArray items = newOrderline.getJSONArray("ItemsAndQuantities");
		
		Map<Integer, Integer> itemsAndQuantities = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < items.length(); i++) {
			JSONObject item = items.getJSONObject(i);
			
			int itemId = item.getInt("ItemId");
			int itemQuantity = item.getInt("ItemQuantity");
			
			itemsAndQuantities.put(itemId, itemQuantity);
		}
				
		Order createdOrder = OrderlineRepository.createOrderline(customerId, itemsAndQuantities, date);
		
		JSONObject response = new JSONObject();
		
		if(createdOrder != null) {
			response = OrderRepository.completeOrderToJSON(createdOrder, itemsAndQuantities);
			
			EntityRepository.exit();
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Creation of order failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
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
	@Produces("application/json")
	public Response getCompleteOrderById(@PathParam("param") int orderId) {
		EntityRepository.setUp();
		
		Order order = OrderRepository.getOrder(orderId);
		
		JSONObject response = new JSONObject();
		
		if(order != null) {
			Map<Integer, Integer> itemsAndQuantities = OrderRepository.getAllItemsOfOrder(orderId);
			
			if(itemsAndQuantities.size() > 0) {
				response = OrderRepository.completeOrderToJSON(order, itemsAndQuantities);
				
				EntityRepository.exit();
				
				return Response.status(200).entity(response.toString()).build();
			}else {
				response.put("Message", "Order with the id " + orderId + " does not have any items!");
				
				EntityRepository.exit();
				
				return Response.status(500).entity(response.toString()).build();
			}
		}else {
			response.put("Message", "Fetching of order with id " + orderId + " failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to get all orders.
	 * 
	 * @return The response containing the orders.
	 */
	@GET
	@Path("/getAllOrdersOfCustomer={param}")
	@Produces("application/json")
	public Response getAllOrdersOfCustomer(@PathParam("param") int customerId) {
		EntityRepository.setUp();
		
		Set<Order> orders = OrderRepository.getAllOrdersOfCustomer(customerId);
		
		Map<Order, Map<Integer, Integer>> completeOrders = new HashMap<Order, Map<Integer, Integer>>();
		
		JSONObject response = new JSONObject();
		
		if(orders != null) {			
			for(Order order : orders) {
				Map<Integer, Integer> itemsAndQuantities = OrderRepository.getAllItemsOfOrder(order.getOrderId());
				
				if(itemsAndQuantities.size() > 0) {
					completeOrders.put(order, itemsAndQuantities);
				}else {
					response.put("Message", "The order with the id " + order.getOrderId() + " does not have any items!");
					
					EntityRepository.exit();
					
					return Response.status(500).entity(response.toString()).build();
				}
			}
		}else {
			response.put("Message", "The customer with the id " + customerId + " does not have any orders!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
		
		EntityRepository.exit();
		
		return Response.status(200).entity(OrderRepository.completeOrdersToJSON(completeOrders)).build();
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
	@Produces("application/json")
	public Response deleteOrder(@PathParam("param") int orderId) {
		EntityRepository.setUp();
		
		boolean orderlineDeleted = OrderlineRepository.deleteOrderline(orderId);
		
		JSONObject response = new JSONObject();
		
		if(orderlineDeleted) {
			response.put("Message", "Deletion of order with id " + orderId + " successful!");
			
			EntityRepository.exit();
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Deletion of order with id " + orderId + " failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
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
	@Path("/updateOrderById={order}/item={item}/updateType={type}/quantity={quantity}")
	@Produces("application/json")
	public Response updateOrder(@PathParam("order") int orderId, @PathParam("item") int itemId, @PathParam("type") String updateType, @PathParam("quantity") int quantity){		
		EntityRepository.setUp();
		
		Order order = OrderlineRepository.updateOrderline(orderId, itemId, updateType, quantity);
		
		JSONObject response = new JSONObject();
		
		if(order != null) {
			Map<Integer, Integer> itemsAndQuantities = OrderRepository.getAllItemsOfOrder(orderId);
			
			if(itemsAndQuantities.size() > 0) {
				response = OrderRepository.completeOrderToJSON(order, itemsAndQuantities);
				
				EntityRepository.exit();
				
				return Response.status(200).entity(response.toString()).build();
			}else {
				response.put("Message", "Order with id " + orderId + " does not have any items!");
				
				EntityRepository.exit();
				
				return Response.status(500).entity(response.toString()).build();
			}	
		}else {
			response.put("Message", "Update of order with id " + orderId + " failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
}

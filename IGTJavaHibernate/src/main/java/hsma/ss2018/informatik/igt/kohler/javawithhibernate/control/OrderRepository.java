package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.LocalDate;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Orderline;

/**
 * This class functions as the API with which one can deal with orders.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 *
 */
public class OrderRepository extends EntityRepository{
	/**
	 * Creates an order.
	 * 
	 * @return The newly created order.
	 */
	public static Order createOrder(int customerId) {
		Order order = new Order();
		
		Customer customer = CustomerRepository.getCustomer(customerId);
		
		String orderDate = LocalDate.now().toString();
		
		order.setOrderDate(orderDate);
		order.setOrderCarriedOut(false);
		order.setCustomer(customer);
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			session.save(order);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
			
			order = null;
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return order;
	}
	
	/**
	 * Gets an order based on the passed order Id.
	 * 
	 * @param orderId Id of the order which is to be fetched.
	 * 
	 * @return The fetched order.
	 */
	public static Order getOrder(int orderId) {
		Order order = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			order = session.get(Order.class,  orderId);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return order;
	}
	
	/**
	 * Gets all the orders.
	 * 
	 * @return All existing orders.
	 */
	@SuppressWarnings("unchecked")
	public static Set<Order> getAllOrders() {
		Set<Order> orders = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
		
			List<Order> ordersList = session.createQuery("from CUSTOMER_ORDER").getResultList();
			
			session.getTransaction().commit();
			
			orders = new HashSet<Order>(ordersList);
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return orders;
	}
	
	/**
	 * Gets all the orders belonging to a customer.
	 * 
	 * @param customerId Id of customer.
	 * 
	 * @return All existing orders.
	 */
	public static Set<Order> getAllOrdersOfCustomer(int customerId) {
		Customer customer = CustomerRepository.getCustomer(customerId);
		Set<Order> orders = customer.getOrders();
		
		return orders;
	}
	
	/**
	 * Deletes an order based on its Id.
	 * 
	 * @param orderId Id of the order that is to be deleted.
	 * 
	 * @return Returns true if deleted.
	 */
	public static boolean deleteOrder(int orderId) {
		Session session = null;
		
		boolean orderDeleted = true;
		
		try {
			Order order = getOrder(orderId);
			
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			session.delete(order);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
			
			orderDeleted = false;
		}finally {
			session.close();
		}
		
		return orderDeleted;
	}
	
	/**
	 * Gets all the items belonging to the order.
	 * 
	 * @param orderId Id of the order from which the items should be fetched.
	 * 
	 * @return Set with all the items ids and quantities.
	 */
	public static Map<Integer, Integer> getAllItemsOfOrder(int orderId) {
		Order order = getOrder(orderId);
		Set<Orderline> orderline = order.getOrderline();
		Map<Integer, Integer> itemIdsAndQuantity = new HashMap<Integer, Integer>();
		
		for(Orderline orderlineElement : orderline) {
			itemIdsAndQuantity.put(orderlineElement.getItem().getItemId(), orderlineElement.getQuantity());
		}
		
		return itemIdsAndQuantity;
	}
	
	/**
	 * Converts an order object into JSON-format.
	 * 
	 * @param order Order that is to be converted.
	 * 
	 * @return Order in JSON format.
	 */
	public static JSONObject orderToJSON(Order order) {
		JSONObject jsonOrder = new JSONObject().put("OrderId", new Integer(order.getOrderId()));
		jsonOrder.put("CustomerId", new Integer(order.getCustomer().getCustomerId()));
		jsonOrder.put("OrderDate", order.getOrderDate());
		jsonOrder.put("TotalCost", new Double(order.getTotalCost()));
		jsonOrder.put("OrderCarriedOut", new Boolean(order.getOrderCarriedOut()));
		
		return jsonOrder;
	}
	
	/**
	 * Converts a set of orders into JSON-format.
	 * 
	 * @param orders Set of orders that are to be converted.
	 * 
	 * @return orders in JSON format.
	 */
	public static JSONArray ordersToJSON(Set<Order> orders) {
		JSONArray jsonOrders = new JSONArray();
		
		if(orders != null && orders.size() > 0) {
			for(Order order : orders) {
				JSONObject jsonOrder = orderToJSON(order);

				jsonOrders.put(jsonOrder); 
			}
		}
				
		return jsonOrders;
	}
	
	/**
	 * Converts an order and its items into JSON-format.
	 * 
	 * @param order Order that is to be converted.
	 * @param itemIdsAndQuantity All the items and their quantity belonging to the order.
	 * 
	 * @return Complete order in JSON format.
	 */
	public static JSONObject completeOrderToJSON(Order order, Map<Integer, Integer> itemIdsAndQuantity) {
		JSONObject jsonOrderAndItems = new JSONObject();
		jsonOrderAndItems.put("Order", orderToJSON(order));
		
		JSONArray jsonItems = new JSONArray();
		
		for(int itemId : itemIdsAndQuantity.keySet()) {
			Item item = ItemRepository.getItem(itemId);
			JSONObject jsonItem = ItemRepository.itemToJSON(item);
			jsonItem.put("ItemQuantity", new Integer(itemIdsAndQuantity.get(itemId)));
			
			jsonItems.put(jsonItem);
		}
		
		jsonOrderAndItems.put("Items", jsonItems);
		
		return jsonOrderAndItems;
	}
	
	/**
	 * Converts a set of complete orders into JSON-format.
	 * 
	 * @param completeOrders Map of complete orders that are to be converted.
	 * 
	 * @return Complete orders in JSON format.
	 */
	public static JSONObject completeOrdersToJSON(Map<Order, Map<Integer, Integer>> completeOrders) {
		JSONArray orderAndItemsArray = new JSONArray();
				
		for(Order order : completeOrders.keySet()) {
			JSONObject orderAndItems = completeOrderToJSON(order, completeOrders.get(order)); 
			orderAndItemsArray.put(orderAndItems);
		}
				
		JSONObject jsonCompleteOrders = new JSONObject().put("CompleteOrders", orderAndItemsArray);
		
		return jsonCompleteOrders;
	}
}

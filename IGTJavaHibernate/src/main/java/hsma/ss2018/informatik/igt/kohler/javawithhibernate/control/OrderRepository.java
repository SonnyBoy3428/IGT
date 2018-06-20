package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.LocalDate;
import org.hibernate.Session;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Orderline;

/**
 * This class functions as the API with which one can deal with orders.
 * 
 * @author Dustin Noah Young,
 *
 */
public class OrderRepository extends EntityRepository{
	/**
	 * Creates an order.
	 * 
	 * @return The newly created order.
	 */
	protected static Order createOrder() {
		Order order = new Order();
		
		String orderDate = LocalDate.now().toString();
		
		order.setOrderDate(orderDate);
		order.setOrderCarriedOut(false);
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			session.save(order);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			//TODO
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
			
			order = session.get(Order.class,  orderId);
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
	protected static Set<Order> getAllOrders() {
		List<Order> ordersList = null;
		Set<Order> orders = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
		
			ordersList = session.createQuery("from CUSTOMER_ORDER").getResultList();
			
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
	 * Gets all the orders beinting to a customer.
	 * 
	 * @param customerId Id of customer.
	 * 
	 * @return All existing orders.
	 */
	@SuppressWarnings("unchecked")
	public static Set<Order> getAllOrdersOfCustomer(int customerId) {
		Customer customer = CustomerRepository.getCustomer(customerId);
		Set<Order> orders = customer.getOrders();
		
		return orders;
	}
	
	/**
	 * Deletes an order based on its Id.
	 * 
	 * @param orderId Id of the order that is to be deleted.
	 */
	protected static void deleteOrder(int orderId) {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Order order = session.get(Order.class,  orderId);		
			
			session.beginTransaction();
			
			session.delete(order);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
		}finally {
			session.close();
		}
	}
	
	/**
	 * Gets all the items beinting to the order.
	 * 
	 * @param orderId Id of the order from which the items should be fetched.
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
	 * Converts an order object into XML-format.
	 * 
	 * @param order Order that is to be converted.
	 * 
	 * @return Order in XML format.
	 */
	public static String orderToXML(Order order) {
		String xmlOrder;
		
		xmlOrder = "<Order>"
				+ "<CustomerId" + order.getCustomer().getCustomerId() + "</CustomerId>"
				+ "<OrderId>" + order.getOrderId() + "</ItemId>"
				+ "<OrderDate>" + order.getOrderDate() + "</OrderDate>"
				+ "<TotalCost>" + order.getTotalCost() + "</TotalCost>"
				+ "<OrderCarriedOut>" + order.getOrderCarriedOut() + "</OrderCarriedOut>"
				+ "</Order>";
		
		return xmlOrder;
	}
	
	/**
	 * Converts a set of orders into XML-format.
	 * 
	 * @param orders Set of orders that are to be converted.
	 * 
	 * @return orders in XML format.
	 */
	public static String ordersToXML(Set<Order> orders) {
		String xmlOrders;
		
		xmlOrders = "<Orders>";
		
		if(orders != null && orders.size() > 0) {
			for(Order order : orders) {
				xmlOrders += orderToXML(order); 
			}
		}
				
		xmlOrders += "</Orders>";
		
		return xmlOrders;
	}
	
	/**
	 * Converts an order and its items into XML-format.
	 * 
	 * @param order Order that is to be converted.
	 * @param itemIdsAndQuantity All the items and their quantity beinting to the order.
	 * 
	 * @return Complete order in XML format.
	 */
	public static String completeOrderToXML(Order order, Map<Integer, Integer> itemIdsAndQuantity) {
		String xmlCompleteOrder;
		
		xmlCompleteOrder = "<CompleteOrder>" + orderToXML(order);
		
		for(int itemId : itemIdsAndQuantity.keySet()) {
			Item item = ItemRepository.getItem(itemId);
			xmlCompleteOrder += ItemRepository.itemToXML(item);
			xmlCompleteOrder += "<Quantity>" + itemIdsAndQuantity.get(itemId) + "</Quantity>";
		}
				
		xmlCompleteOrder += "</CompleteOrder>";
		
		return xmlCompleteOrder;
	}
	
	/**
	 * Converts a set of complete orders into XML-format.
	 * 
	 * @param completeOrders Map of complete orders that are to be converted.
	 * 
	 * @return Complete orders in XML format.
	 */
	public static String completeOrdersToXML(Map<Order, Map<Integer, Integer>> completeOrders) {
		String xmlCompleteOrders;
		
		xmlCompleteOrders = "<CompleteOrders>";
		
		for(Order order : completeOrders.keySet()) {
			xmlCompleteOrders += completeOrderToXML(order, completeOrders.get(order)); 
		}
				
		xmlCompleteOrders += "</CompleteOrders>";
		
		return xmlCompleteOrders;
	}
}

package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Orderline;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Stock;

/**
 * This class functions as the API with which one can deal with orderlines.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 *
 */
public class OrderlineRepository extends EntityRepository {
	/**
	 * Creates an orderline and builds up the relations between all affected objects.
	 * 
	 * @param customerId Customer for whom the order should be created.
	 * @param itemsAndQuantity The ordered items and their quantity.
	 * 
	 * @return Returns the newly created order.
	 */
	public static Order createOrderline(int customerId, Map<Integer, Integer> itemsAndQuantity, String date) {
		Customer customer = CustomerRepository.getCustomer(customerId);
		Order newOrder = null;
		Set<Item> items = new HashSet<Item>();
		
		boolean errorOccured = false;
		
		// If the customer does not exist we don't need to continue.
		if(customer != null) {
			newOrder = OrderRepository.createOrder(customerId, date);
			
			// If the order could not be created we don't need to continue.
			if(newOrder != null) {
				// Get each item and add it to the item list
				for(int itemId : itemsAndQuantity.keySet()) {
					Item item = ItemRepository.getItem(itemId);
					
					// If the item does not exist we don't need to continue.
					if(item != null) {
						items.add(item);
					}else {
						// TODO
						
						errorOccured = true;
						break;
					}
				}
				
				if(!errorOccured) {
					double totalCost = 0.0d;
					
					// Here we set the relationships.
					for(Item item : items) {
						totalCost += (item.getPrice() * itemsAndQuantity.get(item.getItemId()));
						
						// The orderline needs a link to the order and the item 
						Orderline orderline = new Orderline();
						orderline.setOrder(newOrder);
						orderline.setItem(item);
						orderline.setQuantity(itemsAndQuantity.get(item.getItemId()));
					
						Session session = null;
						
						try {
							session = sessionFactory.openSession();
							
							session.beginTransaction();
							session.save(orderline);
							session.getTransaction().commit();
						}catch(Exception ex) {
							// TODO
							
							errorOccured = true;
							newOrder = null;
							break;
						}finally {
							session.close();
						}
					}
					
					if(!errorOccured) {
						Session session = null;
						newOrder.setTotalCost(totalCost);
						
						try {
							session = sessionFactory.openSession();
							
							session.beginTransaction();
							session.update(newOrder);
							session.getTransaction().commit();
						}catch(Exception ex) {
							// TODO
							
							newOrder = null;
						}finally {
							if(session != null) {
								session.close();
							}
						}	
					}
				}
			}else {
				// TODO
			}
		}else {
			// TODO
		}
		
		return newOrder;
	}
	
	/**
	 * Gets the orderline belonging to the order and the item.
	 * 
	 * @param orderId Id of the order associated with the orderline.
	 * @param itemId Id of the item associated with the orderline.
	 * 
	 * @return The orderline of the order.
	 */
	public static Orderline getOrderline(int orderId, int itemId){
		Orderline orderline = null;
		
		Set<Orderline> orderlines = getOrderlines(orderId);
		
		for(Orderline fetchedOrderline : orderlines) {
			if(fetchedOrderline.getItem().getItemId() == itemId) {
				orderline = fetchedOrderline;
			}
		}
		
		return orderline;
	}
	
	/**
	 * Gets the orderline belonging to the order Id.
	 * 
	 * @param orderId Id of the order associated with the orderline.
	 * 
	 * @return The orderline of the order.
	 */
	@SuppressWarnings("unchecked")
	public static Set<Orderline> getOrderlines(int orderId){
		Set<Orderline> orderlines = new HashSet<Orderline>();
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			List<Orderline> orderlineList = session.createQuery("FROM hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Orderline").getResultList();
			
			for(Orderline fetchedOrderline : orderlineList) {
				if(fetchedOrderline.getOrder().getOrderId() == orderId) {
					orderlines.add(fetchedOrderline);
				}
			}
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();	
			}
		}
		
		if(orderlines.size() <= 0) {
			orderlines = null;
		}
		
		return orderlines;
	}
	
	/**
	 * Updates an orderline.
	 * 
	 * @param orderId Order associated with the orderline.
	 * @param itemId Item associated with the orderline.
	 * @param updateType Update type can be an insert, an update or a delete function.
	 * @param quantity Quantity of the new item or of the item that is to be updated.
	 * 
	 * @return The updated order.
	 */
	public static Order updateOrderline(int orderId, int itemId, String updateType, int quantity) {
		Session session = null;
		
		boolean updateSuccessful = true;
				
		try {			
			Order order = OrderRepository.getOrder(orderId);
			Item item = ItemRepository.getItem(itemId);
			
			Set<Orderline> orderline = getOrderlines(orderId);
			
			double totalCost = 0.0f;
			int oldQuantity = 0;
			
			session = sessionFactory.openSession();

			boolean itemExists = false;
			
			for(Orderline orderlineElement : orderline) {
				Item orderlineItem = orderlineElement.getItem();
			
				// Item found
				if(orderlineItem.getItemId() == itemId) {
					itemExists = true;
					switch(updateType) {
						case "Update":
							oldQuantity = orderlineElement.getQuantity();
							double oldPrice = item.getPrice() * oldQuantity;
							orderlineElement.setQuantity(quantity);
								
							totalCost = order.getTotalCost();
							totalCost -= oldPrice;
							totalCost += (item.getPrice() * quantity);
							order.setTotalCost(totalCost);
						
							session.beginTransaction();
							session.update(orderlineElement);
							session.getTransaction().commit();
						
							session.beginTransaction();
							session.update(order);
							session.getTransaction().commit();
								
							break;
						case "Delete":	
							oldQuantity = orderlineElement.getQuantity();
								
							totalCost = order.getTotalCost();
							totalCost -= (item.getPrice() * oldQuantity);
							order.setTotalCost(totalCost);
								
							session.beginTransaction();
							session.delete(orderlineElement);
							session.getTransaction().commit();
							
							session.beginTransaction();
							session.update(order);
							session.getTransaction().commit();
								
							break;
					}
				}
			}
				
			if(!itemExists && updateType.equals("Insert")) {
				// The orderline needs a link to the order and the item 
				Orderline newOrderline = new Orderline();
				newOrderline.setOrder(order);
				newOrderline.setItem(item);
				newOrderline.setQuantity(quantity);
					
				// The order needs a link to the orderline.
				totalCost = order.getTotalCost();
				totalCost += (item.getPrice() * quantity);
				order.setTotalCost(totalCost);
					
				session.beginTransaction();
				session.save(newOrderline);
				session.getTransaction().commit();
					
				session.beginTransaction();
				session.update(order);
				session.getTransaction().commit();
			}
		}catch(Exception ex) {
			// TODO
			
			updateSuccessful = false;
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		Order updatedOrder = OrderRepository.getOrder(orderId);
		
		if(!updateSuccessful) {
			updatedOrder = null;
		}
		
		return updatedOrder;
	}
	
	/**
	 * Deletes an orderline and it's relationships.
	 * 
	 * @param orderId Order Id from order on which deletion is based.
	 * 
	 * @return Returns true if deletion successful.
	 */
	public static boolean deleteOrderline(int orderId) {
		Session session = null;
		
		boolean orderlineDeleted = true;
		
		try {
			Order order = OrderRepository.getOrder(orderId);
			Set<Orderline> orderline = getOrderlines(orderId);
			
			session = sessionFactory.openSession();	
			
			for(Orderline orderlineElement : orderline) {				
				session.beginTransaction();
				session.delete(orderlineElement);
				session.getTransaction().commit();
			}
			
			OrderRepository.deleteOrder(orderId);
		}catch(Exception ex) {
			// TODO
			
			orderlineDeleted = false;
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return orderlineDeleted;
	}
}

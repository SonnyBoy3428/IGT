package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Orderline;

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
	public static Order createOrderline(int customerId, Map<Integer, Integer> itemsAndQuantity) {
		Customer customer = CustomerRepository.getCustomer(customerId);
		Order newOrder = null;
		Set<Item> items = new HashSet<Item>();
		
		boolean errorOccured = false;
		
		// If the customer does not exist we don't need to continue.
		if(customer != null) {
			newOrder = OrderRepository.createOrder(customerId);
			
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
						
						// The order needs a link to the orderline and the customer
						newOrder.getOrderline().add(orderline);
						
						// The item needs a link to the orderline.
						item.getOrderline().add(orderline);
					
						Session session = null;
						
						try {
							session = sessionFactory.openSession();
							
							session.beginTransaction();
							session.save(orderline);
							session.getTransaction().commit();
							
							session.beginTransaction();
							session.update(item);
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
	 * Gets the orderline belonging to the order Id.
	 * 
	 * @param orderId Id of the order associated with the orderline.
	 * 
	 * @return The orderline of the order.
	 */
	protected static Set<Orderline> getOrderlines(int orderId){
		Set<Orderline> orderline = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			Order order = session.get(Order.class, orderId);
			
			session.getTransaction().commit();
			
			orderline = order.getOrderline();
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return orderline;
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
				
		try {			
			Order order = OrderRepository.getOrder(orderId);
			Item item = ItemRepository.getItem(itemId);
			
			Set<Orderline> orderline = order.getOrderline();
			
			double totalCost = 0.0f;
			int oldQuantity = 0;
			
			session = sessionFactory.openSession();

			if(!updateType.equals("Insert")) {
				for(Orderline orderlineElement : orderline) {
					Item orderlineItem = orderlineElement.getItem();
				
					// Item found
					if(orderlineItem.getItemId() == itemId) {	
						switch(updateType) {
							case "Update":
								oldQuantity = orderlineElement.getQuantity();
								double oldPrice = item.getPrice() * oldQuantity;
								orderlineElement.setQuantity(quantity);
								
								order.setOrderline(orderline);
								totalCost = order.getTotalCost();
								totalCost -= oldPrice;
								totalCost += (item.getPrice() * quantity);
								order.setTotalCost(totalCost);
								
								item.setOrderline(orderline);
						
								session.beginTransaction();
								session.update(orderlineElement);
								session.getTransaction().commit();
						
								session.beginTransaction();
								session.update(order);
								session.getTransaction().commit();
								
								session.beginTransaction();
								session.update(item);
								session.getTransaction().commit();
								
								break;
							case "Delete":	
								oldQuantity = orderlineElement.getQuantity();
								orderline.remove(orderlineElement);
								
								order.setOrderline(orderline);
								totalCost = order.getTotalCost();
								totalCost -= (item.getPrice() * oldQuantity);
								order.setTotalCost(totalCost);
								
								item.setOrderline(orderline);
								
								session.beginTransaction();
								session.delete(orderlineElement);
								session.getTransaction().commit();
								
								session.beginTransaction();
								session.update(order);
								session.getTransaction().commit();
								
								session.beginTransaction();
								session.update(item);
								session.getTransaction().commit();
								
								break;
						}
					}
				}
			}else {
				// The orderline needs a link to the order and the item 
				Orderline newOrderline = new Orderline();
				newOrderline.setOrder(order);
				newOrderline.setItem(item);
				newOrderline.setQuantity(quantity);
				
				// The order needs a link to the orderline.
				order.getOrderline().add(newOrderline);
				totalCost = order.getTotalCost();
				totalCost += (item.getPrice() * quantity);
				order.setTotalCost(totalCost);
				
				// The item needs a link to the orderline.
				item.getOrderline().add(newOrderline);
					
				session.beginTransaction();
				session.save(newOrderline);
				session.getTransaction().commit();
				
				session.beginTransaction();
				session.update(order);
				session.getTransaction().commit();
					
				session.beginTransaction();
				session.update(item);
				session.getTransaction().commit();
			}
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		Order updatedOrder = OrderRepository.getOrder(orderId);
		
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
			Set<Orderline> orderline = order.getOrderline();
			
			order.setCustomer(null);
			order.setOrderline(null);
			
			session = sessionFactory.openSession();	
			
			session.beginTransaction();
			session.update(order);
			session.getTransaction().commit();
			
			for(Orderline orderlineElement : orderline) {
				Item item = orderlineElement.getItem();
				Set<Orderline> itemOrderline = item.getOrderline();
				
				itemOrderline.remove(orderlineElement);
				item.setOrderline(itemOrderline);
				
				session.beginTransaction();
				session.update(item);
				session.getTransaction().commit();
				
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

package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Orderline;

/**
 * This class functions as the API with which one can deal with orderlines.
 * 
 * @author Dustin Noah Young,
 *
 */
public class OrderlineManager extends EntityManager {
	/**
	 * Creates an orderline and builds up the relations between all affected objects.
	 * 
	 * @param customerId Customer for whom the order should be created.
	 * @param itemsAndQuantity The ordered items and their quantity.
	 */
	public void createOrderline(long customerId, Map<Long, Long> itemsAndQuantity) {
		Customer customer = CustomerManager.getCustomer(customerId);
		Order newOrder = null;
		Set<Item> items = new HashSet<Item>();
		
		// If the customer does not exist we don't need to continue.
		if(customer != null) {
			newOrder = OrderManager.createOrder();
			
			// If the order could not be created we don't need to continue.
			if(newOrder != null) {
				// Get each item and add it to the item list
				for(long itemId : itemsAndQuantity.keySet()) {
					Item item = ItemManager.getItem(itemId);
					
					// If the item does not exist we don't need to continue.
					if(item != null) {
						items.add(item);
					}else {
						throw new NullPointerException("No item with the Id: " + itemId + " exists.");
					}
				}
			}else {
				throw new NullPointerException("No new order could be created.");
			}
		}else {
			throw new NullPointerException("No customer with the Id: " + customerId + " exists.");
		}
		
		// Here we set the relationships.
		for(Item item : items) {
			// The orderline needs a link to the order and the item 
			Orderline orderline = new Orderline();
			orderline.setOrder(newOrder);
			orderline.setItem(item);
			orderline.setQuantity(itemsAndQuantity.get(item.getItemId()));
			
			// The order needs a link to the orderline and the customer
			newOrder.getOrderline().add(orderline);
			newOrder.setCustomer(customer);
			
			// The item needs a link to the orderline.
			item.getOrderline().add(orderline);
		
			Session session = null;
			
			try {
				session = sessionFactory.openSession();
				
				session.beginTransaction();
				session.save(orderline);
				session.getTransaction().commit();
				
				session.beginTransaction();
				session.update(newOrder);
				session.getTransaction().commit();
				
				session.beginTransaction();
				session.update(item);
				session.getTransaction().commit();
			}catch(Exception ex) {
				// TODO
			}finally {
				session.close();
			}
		}
	}
}

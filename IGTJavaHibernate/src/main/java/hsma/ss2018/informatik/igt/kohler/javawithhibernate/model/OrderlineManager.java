package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;

public class OrderlineManager extends EntityManager {
	public void createOrderline(long customerId, Map<Long, Long> itemQuantity) {
		Customer customer = CustomerManager.getCustomer(customerId);
		
		Set<Item> items = new HashSet<Item>();
		
		Order newOrder = null;
		
		if(customer != null) {
			newOrder = OrderManager.createOrder();
			
			if(newOrder != null) {
				for(long itemId : itemQuantity.keySet()) {
					Item item = ItemManager.getItem(itemId);
					
					if(item != null) {
						items.add(item);
					}else {
						// TODO
					}
				}
			}else {
				//TODO
			}
		}else {
			// TODO
		}
		
		for(Item item : items) {
			Orderline orderline = new Orderline();
			
			orderline.setOrder(newOrder);
			orderline.setItem(item);
			orderline.setQuantity(itemQuantity.get(item.getItemId()));
			
			newOrder.getOrderline().add(orderline);
			item.getOrderline().add(orderline);
		
			Session session = null;
			
			try {
				session = sessionFactory.openSession();
				
				session.save(orderline);
				session.update(newOrder);
				session.update(item);
			}catch(Exception ex) {
				// TODO
			}finally {
				session.close();
			}
		}
	}
}

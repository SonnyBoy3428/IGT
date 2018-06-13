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
			newOrder.setCustomer(customer);
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
	
	public List<Orderline> getOrderline(long orderId) {	
		Order order = null;
		List<Orderline> orderline = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Query<Orderline> query = session.createQuery("from Orderline where order.Id =" + orderId);
			orderline = query.getResultList();
		}catch(Exception ex) {
			
		}finally {
			session.close();
		}
		
		return orderline;
	}
	
	public getOrderlineAsXML() {
		
	}
}

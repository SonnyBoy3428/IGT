package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

public class OrderManager extends EntityManager{
	public static Order createOrder() {
		Order order = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			session.save(order);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			//TODO
		}finally {
			session.close();
		}
		
		return order;
	}
	
	public static Order getOrder(long orderId) {
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
	
	@SuppressWarnings("unchecked")
	public static String getAllOrders() {
		Session session = sessionFactory.openSession();
		
		List<Order> ordersList = session.createQuery("from CUSTOMER_ORDER").list();
		
		session.close();
		
		Set<Order> orders = new HashSet<Order>(ordersList);
		
		return ordersToXML(orders);
	}
	
	public static void deleteOrder(long orderId) {
		Session session = sessionFactory.openSession();
		
		Order order = session.get(Order.class,  orderId);
		
		session.beginTransaction();
		
		session.delete(order);
		
		session.getTransaction().commit();
		session.close();
	}
	
	protected static String orderToXML(Order order) {
		String xmlOrder;
		
		xmlOrder = "<Order>"
				+ "<OrderId>" + order.getOrderId() + "</ItemId>"
				+ "</Order>";
		
		return xmlOrder;
	}
	
	protected static String ordersToXML(Set<Order> orders) {
		String xmlOrders;
		
		xmlOrders = "<Orders>";
		
		if(orders != null && orders.size() > 0) {
			for(Order order : orders) {
				xmlOrders += OrderManager.orderToXML(order); 
			}
		}
				
		xmlOrders += "</Orders>";
		
		return xmlOrders;
	}
}

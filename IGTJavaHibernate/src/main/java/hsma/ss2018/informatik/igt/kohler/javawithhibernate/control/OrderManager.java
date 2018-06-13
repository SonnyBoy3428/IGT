package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import org.hibernate.Session;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;

public class OrderManager extends EntityManager{
	protected static Order createOrder() {
		Order order = null;
		
		String date = LocalDate.now().toString();
		
		order.setDate(date);
		order.setOrderCarriedOut((byte) 0);
		
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
	
	protected static Order getOrder(long orderId) {
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
	protected static Set<Order> getAllOrders() {
		Session session = sessionFactory.openSession();
		
		List<Order> ordersList = session.createQuery("from CUSTOMER_ORDER").list();
		
		session.close();
		
		Set<Order> orders = new HashSet<Order>(ordersList);
		
		return orders;
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
				+ "<date>" + order.getDate() + "</date>"
				+ "<orderCarriedOut>" + order.getOrderCarriedOut() + "</orderCarriedOut>"
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

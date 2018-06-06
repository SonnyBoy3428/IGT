package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import org.hibernate.Session;

public class OrderManager extends EntityManager{
	public void createOrder(Item ... items) {
		Session session = sessionFactory.openSession();
		
		for(Item item : items) {
			session.beginTransaction();
			
			session.save(item);
			
			session.getTransaction().commit();
		}
	
		session.close();
	}
	
	public String getOrder(long orderId) {
		Session session = sessionFactory.openSession();
		
		Order order = session.get(Order.class,  orderId);
		
		session.close();
		
		return orderToXML(order);
	}
	
	public void deleteOrder(long orderId) {
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
}

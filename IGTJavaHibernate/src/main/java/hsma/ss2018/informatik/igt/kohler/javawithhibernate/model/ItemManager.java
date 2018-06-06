package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import org.hibernate.Session;

public class ItemManager extends EntityManager{
	public void createItem(String itemName) {
		Item item = new Item();
		item.setItemName(itemName);
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(item);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public String getItem(long itemId) {
		Session session = sessionFactory.openSession();
		
		Item item = session.get(Item.class,  itemId);
		
		session.close();
		
		return itemToXML(item);
	}
	
	public void deleteItem(long itemId) {
		Session session = sessionFactory.openSession();
		
		Item item = session.get(Item.class,  itemId);
		
		session.beginTransaction();
		
		session.delete(item);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public void updateItem(long itemId, String itemName) {
		Session session = sessionFactory.openSession();
		
		Item item = session.get(Item.class,  itemId);
		item.setItemName(itemName);
		
		session.beginTransaction();
		
		session.update(item);
		
		session.getTransaction().commit();
		session.close();
	}
	
	protected String itemToXML(Item item) {
		String xmlItem;
		
		xmlItem = "<Item>"
				+ "<ItemId>" + item.getItemId() + "</ItemId>"
				+ "<ItemName>" + item.getItemName() + "</ItemName>"
				+ "</Item>";
		
		return xmlItem;
	}
}

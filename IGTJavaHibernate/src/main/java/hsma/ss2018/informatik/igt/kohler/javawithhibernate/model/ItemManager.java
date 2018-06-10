package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

public class ItemManager extends EntityManager{
	public static void createItem(String itemName) {
		Item item = new Item();
		item.setItemName(itemName);
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(item);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public static Item getItem(long itemId) {
		Item item = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			item = session.get(Item.class,  itemId);
		}catch(Exception ex) {
			// TODO
		}finally {
			session.close();
		}
		
		return item;
	}
	
	@SuppressWarnings("unchecked")
	public static String getAllItems() {
		Session session = sessionFactory.openSession();
		
		List<Item> itemsList = session.createQuery("from ITEM").list();
		
		session.close();
		
		Set<Item> items = new HashSet<Item>(itemsList);
		
		return itemsToXML(items);
	}
	
	public static void deleteItem(long itemId) {
		Session session = sessionFactory.openSession();
		
		Item item = session.get(Item.class,  itemId);
		
		session.beginTransaction();
		
		session.delete(item);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public static void updateItem(long itemId, String itemName) {
		Session session = sessionFactory.openSession();
		
		Item item = session.get(Item.class,  itemId);
		item.setItemName(itemName);
		
		session.beginTransaction();
		
		session.update(item);
		
		session.getTransaction().commit();
		session.close();
	}
	
	protected static String itemToXML(Item item) {
		String xmlItem;
		
		xmlItem = "<Item>"
				+ "<ItemId>" + item.getItemId() + "</ItemId>"
				+ "<ItemName>" + item.getItemName() + "</ItemName>"
				+ "</Item>";
		
		return xmlItem;
	}
	
	protected static String itemsToXML(Set<Item> items) {
		String xmlItems;
		
		xmlItems = "<Items>";
		
		if(items != null && items.size() > 0) {
			for(Item item : items) {
				xmlItems += itemToXML(item); 
			}
		}
				
		xmlItems += "</Items>";
		
		return xmlItems;
	}
}

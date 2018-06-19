package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;

/**
 * This class functions as the API with which one can deal with items.
 * 
 * @author Dustin Noah Young,
 *
 */
public class ItemRepository extends EntityRepository{
	/**
	 * Created a new item from the given values.
	 * 
	 * @param itemName Name of the item.
	 * @param price The price of the item.
	 * 
	 * @return The newly created item.
	 */
	public static Item createItem(String itemName, double price) {
		Item item = new Item();
		item.setItemName(itemName);
		item.setPrice(price);
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			session.save(item);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return item;
	}
	
	/**
	 * Gets a item based on the passed item Id.
	 * 
	 * @param itemId Id of the item that is to be fetched.
	 * 
	 * @return The fetched item.
	 */
	public static Item getItem(long itemId) {
		Item item = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
		
			item = session.get(Item.class,  itemId);
		
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return item;
	}
	
	/**
	 * Gets all the items.
	 * 
	 * @return All existing items.
	 */
	@SuppressWarnings("unchecked")
	public static Set<Item> getAllItems() {
		List<Item> itemsList = null;
		Set<Item> items = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			itemsList = session.createQuery("from ITEM").getResultList();
			
			items = new HashSet<Item>(itemsList);
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();	
			}
		}
		
		return items;
	}
	
	/**
	 * Deletes a item.
	 * 
	 * @param itemId Id of the item that is to be deleted.
	 */
	public static void deleteItem(long itemId) {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
		}catch(Exception ex) {
			Item item = session.get(Item.class,  itemId);
			
			session.beginTransaction();
			
			session.delete(item);
			
			session.getTransaction().commit();
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Updated a item by it's id.
	 * 
	 * @param itemId Id of the item that is to be updated.
	 * @param itemName Name of the item.
	 * @param price Price of the item in euro.
	 * 
	 * @return The newly updated item.
	 */
	public static Item updateItem(long itemId, String itemName, double price) {
		Session session = null;
		Item item = null;
		
		try {
			session = sessionFactory.openSession();
		}catch(Exception ex) {
			item = session.get(Item.class,  itemId);
			item.setItemName(itemName);
			item.setPrice(price);
			
			session.beginTransaction();
			
			session.update(item);
			
			session.getTransaction().commit();
		}finally{
			if(session != null) {
				session.close();
			}
		}
		
		return item;
	}
	
	/**
	 * Turns a item into XML.
	 * 
	 * @param item Item that is to be turned into XML.
	 * 
	 * @return XML version of the item.
	 */
	public static String itemToXML(Item item) {
		String xmlItem;
		
		xmlItem = "<Item>"
				+ "<ItemId>" + item.getItemId() + "</ItemId>"
				+ "<ItemName>" + item.getItemName() + "</ItemName>"
				+ "<Price>" + item.getPrice() +  "</Price>"
				+ "</Item>";
		
		return xmlItem;
	}
	
	/**
	 * Turns items into XML.
	 * 
	 * @param items Items that are to be turned into XML.
	 * 
	 * @return XML version of the items.
	 */
	public static String itemsToXML(Set<Item> items) {
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

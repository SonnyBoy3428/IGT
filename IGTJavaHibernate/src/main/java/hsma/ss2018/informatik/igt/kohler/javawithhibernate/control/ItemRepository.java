package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;

/**
 * This class functions as the API with which one can deal with items.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
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
	public static Item getItem(int itemId) {
		Item item = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
		
			item = session.get(Item.class,  itemId);
		
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
	 * Gets all the items.
	 * 
	 * @return All existing items.
	 */
	@SuppressWarnings("unchecked")
	public static Set<Item> getAllItems() {
		Set<Item> items = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();

			List<Item> itemsList = session.createQuery("from ITEM").getResultList();
			
			session.getTransaction().commit();
			
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
	 * 
	 * @return Returns true if deletion successful.
	 */
	public static boolean deleteItem(int itemId) {
		Session session = null;
		
		boolean itemDeleted = true;
		
		try {			
			Item item = getItem(itemId);
			
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			session.delete(item);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
			
			itemDeleted = false;
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return itemDeleted;
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
	public static Item updateItem(int itemId, String itemName, double price) {
		Session session = null;
		Item item = null;
		
		try {
			item = getItem(itemId);
			item.setItemName(itemName);
			item.setPrice(price);
			
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			session.update(item);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
		}finally{
			if(session != null) {
				session.close();
			}
		}
		
		Item updatedItem = getItem(itemId);
		
		return updatedItem;
	}
	
	/**
	 * Turns a item into JSON.
	 * 
	 * @param item Item that is to be turned into JSON.
	 * 
	 * @return JSON version of the item.
	 */
	public static JSONObject itemToJSON(Item item) {
		JSONObject jsonItem = new JSONObject().put("ItemId", new Integer(item.getItemId()));
		jsonItem.put("ItemName", item.getItemName());
		jsonItem.put("Price", new Double(item.getPrice()));
		
		return jsonItem;
	}
	
	/**
	 * Turns items into JSON.
	 * 
	 * @param items Items that are to be turned into JSON.
	 * 
	 * @return JSON version of the items.
	 */
	public static JSONArray itemsToJSON(Set<Item> items) {
		JSONArray jsonItems = new JSONArray();
		
		if(items != null && items.size() > 0) {
			for(Item item : items) {
				JSONObject jsonItem = new JSONObject().put("Item", itemToJSON(item));

				jsonItems.put(jsonItem); 
			}
		}
		
		//JSONObject jsonAllItems = new JSONObject().put("Items", jsonItems)
		
		return jsonItems;
	}
}

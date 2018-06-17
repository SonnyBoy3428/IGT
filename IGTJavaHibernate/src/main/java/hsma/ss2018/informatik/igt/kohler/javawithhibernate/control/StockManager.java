package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Warehouse;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Stock;

/**
 * This class functions as the API with which one can deal with stocks.
 * 
 * @author Dustin Noah Young,
 *
 */
public class StockManager extends EntityManager{
	/**
	 * Creates a stock and builds up the relations between all affected objects.
	 * 
	 * @param warehouseId Id of warehouse
	 * @param item Item that is to be stocked
	 * @param quantity
	 */
	public void createStock(long warehouseId, long itemId, long quantity) {
		Warehouse warehouse = WarehouseManager.getWarehouse(warehouseId);
		Item item = ItemManager.getItem(itemId);
		Set<Item> items = new HashSet<Item>();
		
		// If the warehouse does not exist we don't need to continue.
		if(warehouse != null) {
			// If the item could not be created we don't need to continue.
			if(item == null) {
				throw new NullPointerException("No item with the Id: " + itemId + " exists.");
			}
		}else {
			throw new NullPointerException("No warehouse with the Id: " + warehouseId + " exists.");
		}
		
		// The stock needs a link to the warehouse and the item 
		Stock stock = new Stock();
		stock.setWarehouse(warehouse);
		stock.setItem(item);
		
		// The order needs a link to the stock
		warehouse.getStock().add(stock);
		
		// The item needs a link to the stock.
		item.getStock().add(stock);
	
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			session.save(stock);
			session.getTransaction().commit();
			
			session.beginTransaction();
			session.update(warehouse);
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
	
	/**
	 * Gets the stocks belonging to the warehouse Id.
	 * 
	 * @param warehouseId Id of the order associated with the warehouse.
	 * 
	 * @return The stocks of the warehouse.
	 */
	public Set<Stock> getOrderlines(long warehouseId){
		Set<Stock> stock = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Warehouse warehouse = session.get(Warehouse.class, warehouseId);
			
			stock = warehouse.getStock();
		}catch(Exception ex) {
			// TODO
		}finally {
			session.close();
		}
		
		return stock;
	}
	
	/**
	 * Updates an stock.
	 * 
	 * @param warehouseId Warehouse associated with the stock.
	 * @param itemId Item associated with the stock.
	 * @param updateType Update type can be an insert, an update or a delete function.
	 * @param quantity Quantity of the new item or of the item that is to be updated.
	 */
	public void updateOrderline(long warehouseId, long itemId, String updateType, long quantity) {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Warehouse warehouse = session.get(Warehouse.class, warehouseId);
			Item item = session.get(Item.class, itemId);
			
			Set<Stock> stock = warehouse.getStock();
			
			if(!updateType.equals("Insert")) {
				for(Stock stockElement : stock) {
					Item stockItem = stockElement.getItem();
				
					// Item found
					if(stockItem.getItemId() == itemId) {
						switch(updateType) {
							case "Update":
								stockElement.setQuantity(quantity);
								warehouse.setStock(stock);
								item.setStock(stock);
						
								session.beginTransaction();
								session.update(stockElement);
								session.getTransaction().commit();
						
								session.beginTransaction();
								session.update(warehouse);
								session.getTransaction().commit();
								
								session.beginTransaction();
								session.update(item);
								session.getTransaction().commit();
								
								break;
							case "Delete":	
								stock.remove(stockElement);
								warehouse.setStock(stock);
								item.setStock(stock);
								
								session.beginTransaction();
								session.delete(stockElement);
								session.getTransaction().commit();
								
								session.beginTransaction();
								session.update(warehouse);
								session.getTransaction().commit();
								
								session.beginTransaction();
								session.update(item);
								session.getTransaction().commit();
								
								break;
						}
					}
				}
			}else {
				// The stock needs a link to the warehouse and the item 
				Stock newStock = new Stock();
				newStock.setWarehouse(warehouse);
				newStock.setItem(item);
				newStock.setQuantity(quantity);
				
				// The warehouse needs a link to the stock.
				warehouse.getStock().add(newStock);
				
				// The item needs a link to the stock.
				item.getStock().add(newStock);
					
				session.beginTransaction();
				session.save(newStock);
				session.getTransaction().commit();
				
				session.beginTransaction();
				session.update(warehouse);
				session.getTransaction().commit();
					
				session.beginTransaction();
				session.update(item);
				session.getTransaction().commit();
			}
		}catch(Exception ex) {
			// TODO
		}finally {
			session.close();
		}
	}
	
	/**
	 * Deletes a stock and it's relationships.
	 * 
	 * @param warehouseId Warehouse Id from warehouse on which deletion is based.
	 */
	public void deleteOrderline(long warehouseId) {
		Set<Stock> stock = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Warehouse warehouse = session.get(Warehouse.class, warehouseId);
			
			stock = warehouse.getStock();
			
			warehouse.setStock(null);
			
			for(Stock stockElement : stock) {
				Item item = stockElement.getItem();
				Set<Stock> itemStock = item.getStock();
				
				itemStock.remove(stockElement);
				item.setStock(itemStock);
				
				session.beginTransaction();
				session.update(item);
				session.getTransaction().commit();
				
				session.beginTransaction();
				session.delete(stockElement);
				session.getTransaction().commit();
			}
		}catch(Exception ex) {
			// TODO
		}finally {
			session.close();
		}
	}
}

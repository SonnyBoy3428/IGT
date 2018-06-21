package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.Set;

import org.hibernate.Session;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Warehouse;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Stock;

/**
 * This class functions as the API with which one can deal with stocks.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 *
 */
public class StockRepository extends EntityRepository{
	/**
	 * Creates a stock and builds up the relations between all affected objects.
	 * 
	 * @param warehouseId Id of warehouse
	 * @param item Item that is to be stocked
	 * @param quantity
	 */
	public static Warehouse createStock(int warehouseId, int itemId, int quantity) {
		Warehouse warehouse = WarehouseRepository.getWarehouse(warehouseId);
		Item item = ItemRepository.getItem(itemId);
		
		boolean warehouseCreated = true;
		
		// If the warehouse does not exist we don't need to continue.
		if(warehouse != null) {
			// If the item could not be created we don't need to continue.
			if(item != null) {
				// The stock needs a link to the warehouse and the item 
				Stock stock = new Stock();
				stock.setWarehouse(warehouse);
				stock.setItem(item);
				stock.setQuantity(quantity);
				
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
					
					warehouseCreated = false;
				}finally {
					if(session != null) {
						session.close();
					}
				}
			}else {
				// TODO
				
				warehouseCreated = false;
			}
		}else {
			// TODO
			
			warehouseCreated = false;
		}
		
		if(!warehouseCreated) {
			warehouse = null;
		}
		
		return warehouse;
	}
	
	/**
	 * Gets the stocks belonging to the warehouse Id.
	 * 
	 * @param warehouseId Id of the order associated with the warehouse.
	 * 
	 * @return The stocks of the warehouse.
	 */
	public static Set<Stock> getStocks(int warehouseId){
		Set<Stock> stock = null;
		Session session = null;
		
		try {
			Warehouse warehouse = WarehouseRepository.getWarehouse(warehouseId);
			
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			stock = warehouse.getStock();
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();
			}
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
	public static Warehouse updateStock(int warehouseId, int itemId, String updateType, int quantity) {
		Session session = null;
		Warehouse warehouse = WarehouseRepository.getWarehouse(warehouseId);
		
		try {
			session = sessionFactory.openSession();
		
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
			if(session != null) {
				session.close();
			}
		}
		
		warehouse = WarehouseRepository.getWarehouse(warehouseId);
		
		return warehouse;
	}
	
	/**
	 * Deletes a stock and it's relationships.
	 * 
	 * @param warehouseId Warehouse Id from warehouse on which deletion is based.
	 */
	public static boolean deleteStock(int warehouseId) {
		Set<Stock> stock = null;
		Session session = null;
		
		boolean stockDeleted = true;
		
		try {
			Warehouse warehouse = WarehouseRepository.getWarehouse(warehouseId);
			
			session = sessionFactory.openSession();
			
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
			
			stockDeleted = false;
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return stockDeleted;
	}
}

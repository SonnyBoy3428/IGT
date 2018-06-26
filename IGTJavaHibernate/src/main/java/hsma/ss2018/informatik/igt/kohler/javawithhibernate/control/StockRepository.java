package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashSet;
import java.util.List;
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
			
				Session session = null;
				
				try {
					session = sessionFactory.openSession();
					
					session.beginTransaction();
					session.save(stock);
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
		
		warehouse = WarehouseRepository.getWarehouse(warehouseId);
		
		if(!warehouseCreated) {
			warehouse = null;
		}
		
		return warehouse;
	}
	
	/**
	 * Gets the stock belonging to the warehouse and the item.
	 * 
	 * @param warehouseId Id of the warehouse associated with the stock.
	 * @param itemId Id of the item associated with the stock.
	 * 
	 * @return The stock of the warehouse.
	 */
	public static Stock getStock(int warehouseId, int itemId){
		Stock stock = null;
		
		Set<Stock> stocks = getStocks(warehouseId);
		
		for(Stock fetchedStock : stocks) {
			if(fetchedStock.getItem().getItemId() == itemId) {
				stock = fetchedStock;
			}
		}
		
		return stock;
	}
	
	/**
	 * Gets the stocks belonging to the warehouse Id.
	 * 
	 * @param warehouseId Id of the order associated with the warehouse.
	 * 
	 * @return The stocks of the warehouse.
	 */
	@SuppressWarnings("unchecked")
	public static Set<Stock> getStocks(int warehouseId){
		Set<Stock> stocks = new HashSet<Stock>();
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			List<Stock> stockList = session.createQuery("FROM hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Stock").getResultList();
			
			for(Stock stock : stockList) {
				if(stock.getWarehouse().getWarehouseId() == warehouseId) {
					stocks.add(stock);
				}
			}
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();	
			}
		}
		
		if(stocks.size() <= 0) {
			stocks = null;
		}
		
		return stocks;
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
		
		boolean updateSuccessful = true;
		
		try {
			Warehouse warehouse = WarehouseRepository.getWarehouse(warehouseId);
			
			session = sessionFactory.openSession();
		
			Item item = ItemRepository.getItem(itemId);
			
			Set<Stock> stock = getStocks(warehouseId);
			
			boolean itemExists = false;
				
			for(Stock stockElement : stock) {
				Item stockItem = stockElement.getItem();
				
				// Item found
				if(stockItem.getItemId() == itemId) {
					itemExists = true;
					switch(updateType) {
						case "Update":
							stockElement.setQuantity(quantity);
					
							session.beginTransaction();
							session.update(stockElement);
							session.getTransaction().commit();
								
							break;
						case "Delete":	
							session.beginTransaction();
							session.delete(stockElement);
							session.getTransaction().commit();
								
							break;
					}
				}
			}
				
			if(!itemExists && updateType.equals("Insert")) {
				// The stock needs a link to the warehouse and the item 
				Stock newStock = new Stock();
				newStock.setWarehouse(warehouse);
				newStock.setItem(item);
				newStock.setQuantity(quantity);
						
				session.beginTransaction();
				session.save(newStock);
				session.getTransaction().commit();
			}
		}catch(Exception ex) {
			// TODO
			
			updateSuccessful = false;
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		Warehouse updatedWarehouse = WarehouseRepository.getWarehouse(warehouseId);
		
		if(!updateSuccessful){
			updatedWarehouse = null;
		}
		
		return updatedWarehouse;
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
			session = sessionFactory.openSession();
			
			stock = getStocks(warehouseId);
			
			for(Stock stockElement : stock) {				
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

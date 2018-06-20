package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.District;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Stock;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Warehouse;

/**
 * This class functions as the API with which one can deal with warehouses.
 * 
 * @author Dustin Noah Young,
 *
 */
public class WarehouseRepository extends EntityRepository{
	/**
	 * Created a new warehouse from the given values.
	 * 
	 * @param location Location of the warehouse.
	 * 
	 * @return The newly created warehouse.
	 */
	public static Warehouse createWarehouse(String location, String owner) {
		Warehouse warehouse = new Warehouse();
		warehouse.setLocation(location);
		warehouse.setOwner(owner);
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			session.save(warehouse);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return warehouse;
	}
	
	/**
	 * Gets a warehouse based on the passed warehouse Id.
	 * 
	 * @param warehouseId Id of the warehouse that is to be fetched.
	 * 
	 * @return The fetched warehouse.
	 */
	public static Warehouse getWarehouse(int warehouseId) {
		Warehouse warehouse = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
		
			warehouse = session.get(Warehouse.class,  warehouseId);
		
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return warehouse;
	}
	
	/**
	 * Gets all the warehouses.
	 * 
	 * @return All existing warehouses.
	 */
	@SuppressWarnings("unchecked")
	public static Set<Warehouse> getAllWarehouses() {
		List<Warehouse> warehousesList = null;
		Set<Warehouse> warehouses = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			warehousesList = session.createQuery("from WAREHOUSE").getResultList();
			
			warehouses = new HashSet<Warehouse>(warehousesList);
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();	
			}
		}
		
		return warehouses;
	}
	
	/**
	 * Gets all the districts beinting to the warehouse.
	 * 
	 * @param warehouseId Id of the warehouse.
	 * 
	 * @return All districts beinting to the warehouse.
	 */
	public static Set<District> getWarehouseDistricts(int warehouseId) {
		Warehouse warehouse = null;
		Set<District> districts = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			warehouse = session.get(Warehouse.class,  warehouseId);
			
			districts = warehouse.getDistricts();
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();	
			}
		}
						
		return districts;
	}
	
	/**
	 * Deletes a warehouse.
	 * 
	 * @param warehouseId Id of the warehouse that is to be deleted.
	 */
	public static void deleteWarehouse(int warehouseId) {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			Warehouse warehouse = session.get(Warehouse.class,  warehouseId);
			
			session.beginTransaction();
			
			session.delete(warehouse);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Updated a warehouse by it's id.
	 * 
	 * @param warehouseId Id of the warehouse that is to be updated.
	 * @param location Location of the warehouse.
	 * @param owner Owner of the warehouse.
	 */
	public static Warehouse updateWarehouse(int warehouseId, String location, String owner) {
		Session session = null;
		Warehouse warehouse = null;
		
		try {
			session = sessionFactory.openSession();
			
			warehouse = session.get(Warehouse.class,  warehouseId);
			warehouse.setLocation(location);
			warehouse.setOwner(owner);
			
			session.beginTransaction();
			
			session.update(warehouse);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
		}finally{
			if(session != null) {
				session.close();
			}
		}
		
		return warehouse;
	}
	
	/**
	 * Gets all the items beinting to the warehouse.
	 * 
	 * @param warehouseId Id of the warehouse from which the items should be fetched.
	 * @return Set with all the items ids and quantities.
	 */
	public static Map<Integer, Integer> getAllItemsOfWarehouse(int warehouseId) {
		Warehouse warehouse = getWarehouse(warehouseId);
		Set<Stock> stock = warehouse.getStock();
		Map<Integer, Integer> itemIdsAndQuantity = new HashMap<Integer, Integer>();
		
		for(Stock stockElement : stock) {
			itemIdsAndQuantity.put(stockElement.getItem().getItemId(), stockElement.getQuantity());
		}
		
		return itemIdsAndQuantity;
	}
	
	/**
	 * Turns a warehouse into XML.
	 * 
	 * @param warehouse Warehouse that is to be turned into XML.
	 * 
	 * @return XML version of the warehouse.
	 */
	public static String warehouseToXML(Warehouse warehouse) {
		String xmlWarehouse;
		
		xmlWarehouse = "<Warehouse>"
				+ "<WarehouseId>" + warehouse.getWarehouseId() + "</WarehouseId>"
				+ "<Location>" + warehouse.getLocation() + "</Location>"
				+ "<Owner>" + warehouse.getOwner() + "</Owner>"
				+ "</Warehouse>";
		
		return xmlWarehouse;
	}
	
	/**
	 * Turns warehouses into XML.
	 * 
	 * @param warehouses Warehouses that are to be turned into XML.
	 * 
	 * @return XML version of the warehouses.
	 */
	public static String warehousesToXML(Set<Warehouse> warehouses) {
		String xmlWarehouses;
		
		xmlWarehouses = "<Warehouses>";
		
		if(warehouses != null && warehouses.size() > 0) {
			for(Warehouse warehouse : warehouses) {
				xmlWarehouses += warehouseToXML(warehouse); 
			}
		}
				
		xmlWarehouses += "</Warehouses>";
		
		return xmlWarehouses;
	}
	
	/**
	 * Converts a warehouse and its items into XML-format.
	 * 
	 * @param warehouse Warehouse that is to be converted.
	 * @param itemIdsAndQuantity All the items and their quantity beinting to the warehouse.
	 * 
	 * @return Complete warehouse in XML format.
	 */
	public static String completeWarehouseToXML(Warehouse warehouse, Map<Integer, Integer> itemIdsAndQuantity) {
		String xmlCompleteWarehouse;
		
		xmlCompleteWarehouse = "<CompleteWarehouse>" + warehouseToXML(warehouse);
		
		for(int itemId : itemIdsAndQuantity.keySet()) {
			Item item = ItemRepository.getItem(itemId);
			xmlCompleteWarehouse += ItemRepository.itemToXML(item);
			xmlCompleteWarehouse += "<Quantity>" + itemIdsAndQuantity.get(itemId) + "</Quantity>";
		}
				
		xmlCompleteWarehouse += "</CompleteWarehouse>";
		
		return xmlCompleteWarehouse;
	}
	
	/**
	 * Converts a set of complete warehouses into XML-format.
	 * 
	 * @param completeWarehouses Map of complete warehouses that are to be converted.
	 * 
	 * @return Complete warehouses in XML format.
	 */
	public static String completeWarehousesToXML(Map<Warehouse, Map<Integer, Integer>> completeWarehouses) {
		String xmlCompleteWarehouses;
		
		xmlCompleteWarehouses = "<CompleteWarehouses>";
		
		for(Warehouse warehouse : completeWarehouses.keySet()) {
			xmlCompleteWarehouses += completeWarehouseToXML(warehouse, completeWarehouses.get(warehouse)); 
		}
				
		xmlCompleteWarehouses += "</CompleteWarehouses>";
		
		return xmlCompleteWarehouses;
	}
	
	/**
	 * Converts a warehouse and its districts into XML-format.
	 * 
	 * @param customer The warehouse.
	 * @param orders The districts of the warehouse.
	 * 
	 * @return Warehouse and its districts in XML-format.
	 */
	public static String warehouseAndDistrictsToXML(Warehouse warehouse, Set<District> districts) {
		String xmlWarehouseAndDistricts = "<WarehouseAndDistricts>";
		
		xmlWarehouseAndDistricts += warehouseToXML(warehouse);
		xmlWarehouseAndDistricts += DistrictRepository.districtsToXML(districts);
		
		xmlWarehouseAndDistricts += "</WarehouseAndDistricts>";
		
		return xmlWarehouseAndDistricts;
	}
}

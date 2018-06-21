package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.District;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Stock;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Warehouse;

/**
 * This class functions as the API with which one can deal with warehouses.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
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
		
			session.beginTransaction();
			
			warehouse = session.get(Warehouse.class,  warehouseId);
		
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
	 * Gets all the warehouses.
	 * 
	 * @return All existing warehouses.
	 */
	@SuppressWarnings("unchecked")
	public static Set<Warehouse> getAllWarehouses() {
		Set<Warehouse> warehouses = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			List<Warehouse> warehousesList = session.createQuery("from WAREHOUSE").getResultList();
			
			session.getTransaction().commit();
			
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
	 * Gets all the districts belonging to the warehouse.
	 * 
	 * @param warehouseId Id of the warehouse.
	 * 
	 * @return All districts belonging to the warehouse.
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
	 * 
	 * @return Returns true if deletion was successful.
	 */
	public static boolean deleteWarehouse(int warehouseId) {
		Session session = null;
		
		boolean warehouseDeleted = true;
		
		try {
			session = sessionFactory.openSession();
			
			Warehouse warehouse = getWarehouse(warehouseId);
			
			session.beginTransaction();
			
			session.delete(warehouse);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
			
			warehouseDeleted = false;
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return warehouseDeleted;
	}
	
	/**
	 * Updated a warehouse by it's id.
	 * 
	 * @param warehouseId Id of the warehouse that is to be updated.
	 * @param location Location of the warehouse.
	 * @param owner Owner of the warehouse.
	 * 
	 * @return The updated warehouse.
	 */
	public static Warehouse updateWarehouse(int warehouseId, String location, String owner) {
		Session session = null;
		Warehouse warehouse = null;
		
		try {			
			warehouse = getWarehouse(warehouseId);
			warehouse.setLocation(location);
			warehouse.setOwner(owner);
			
			session = sessionFactory.openSession();
			
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
	 * Gets all the items belonging to the warehouse.
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
	 * Turns a warehouse into JSON.
	 * 
	 * @param warehouse Warehouse that is to be turned into JSON.
	 * 
	 * @return JSON version of the warehouse.
	 */
	public static JSONObject warehouseToJSON(Warehouse warehouse) {
		JSONObject jsonWarehouse = new JSONObject().put("WarehouseId", new Integer(warehouse.getWarehouseId()));
		jsonWarehouse.put("Location", warehouse.getLocation());
		jsonWarehouse.put("Owner", warehouse.getOwner());

		return jsonWarehouse;
	}
	
	/**
	 * Turns warehouses into JSON.
	 * 
	 * @param warehouses Warehouses that are to be turned into JSON.
	 * 
	 * @return JSON version of the warehouses.
	 */
	public static JSONArray warehousesToJSON(Set<Warehouse> warehouses) {
		JSONArray jsonWarehouses = new JSONArray();
		
		if(warehouses != null && warehouses.size() > 0) {
			for(Warehouse warehouse : warehouses) {
				JSONObject jsonWarehouse = warehouseToJSON(warehouse);

				jsonWarehouses.put(jsonWarehouse); 
			}
		}
		
		return jsonWarehouses;
	}
	
	/**
	 * Converts a warehouse and its items into JSON-format.
	 * 
	 * @param warehouse Warehouse that is to be converted.
	 * @param itemIdsAndQuantity All the items and their quantity belonging to the warehouse.
	 * 
	 * @return Complete warehouse in JSON format.
	 */
	public static JSONObject completeWarehouseToJSON(Warehouse warehouse, Map<Integer, Integer> itemIdsAndQuantity) {
		JSONObject jsonWarehouseAndItems = new JSONObject();
		jsonWarehouseAndItems.put("Warehouse", warehouseToJSON(warehouse));
		
		JSONArray jsonItems = new JSONArray();
		
		for(int itemId : itemIdsAndQuantity.keySet()) {
			Item item = ItemRepository.getItem(itemId);
			JSONObject jsonItem = ItemRepository.itemToJSON(item);
			jsonItem.put("Quantity", new Integer(itemIdsAndQuantity.get(itemId)));
			
			jsonItems.put(jsonItem);
		}
		
		jsonWarehouseAndItems.put("Items", jsonItems);
		
		return jsonWarehouseAndItems;
	}
	
	/**
	 * Converts a set of complete warehouses into JSON-format.
	 * 
	 * @param completeWarehouses Map of complete warehouses that are to be converted.
	 * 
	 * @return Complete warehouses in JSON format.
	 */
	public static JSONObject completeWarehousesToJSON(Map<Warehouse, Map<Integer, Integer>> completeWarehouses) {
		JSONArray warehosueAndItemsArray = new JSONArray();
		
		for(Warehouse warehouse : completeWarehouses.keySet()) {
			JSONObject warehouserAndItems = completeWarehouseToJSON(warehouse, completeWarehouses.get(warehouse)); 
			warehosueAndItemsArray.put(warehouserAndItems);
		}
				
		JSONObject jsonCompleteWarehouses = new JSONObject().put("CompleteWarehouses", warehosueAndItemsArray);
		
		return jsonCompleteWarehouses;
	}
	
	/**
	 * Converts a warehouse and its districts into JSON-format.
	 * 
	 * @param customer The warehouse.
	 * @param orders The districts of the warehouse.
	 * 
	 * @return Warehouse and its districts in JSON-format.
	 */
	public static JSONObject warehouseAndDistrictsToJSON(Warehouse warehouse, Set<District> districts) {
		JSONObject jsonWarehouseAndDistricts = new JSONObject();
		jsonWarehouseAndDistricts.put("Warehouse", warehouseToJSON(warehouse));
		jsonWarehouseAndDistricts.put("Districts", DistrictRepository.districtsToJSON(districts));
		
		JSONObject jsonCompleteWarehouseAndDistricts = new JSONObject().put("WarehouseAndDistricts", jsonWarehouseAndDistricts);
		
		return jsonCompleteWarehouseAndDistricts;
	}
}

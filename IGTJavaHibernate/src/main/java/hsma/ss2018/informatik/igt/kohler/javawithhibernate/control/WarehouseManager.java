package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.District;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Warehouse;

/**
 * This class functions as the API with which one can deal with warehouses.
 * 
 * @author Dustin Noah Young,
 *
 */
public class WarehouseManager extends EntityManager{
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
	public static Warehouse getWarehouse(long warehouseId) {
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
	public Set<Warehouse> getAllWarehouses() {
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
	 * Gets all the districts belonging to the warehouse.
	 * 
	 * @param warehouseId Id of the warehouse.
	 * 
	 * @return All districts belonging to the warehouse.
	 */
	public Set<District> getWarehouseDistricts(long warehouseId) {
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
	public void deleteWarehouse(long warehouseId) {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
		}catch(Exception ex) {
			Warehouse warehouse = session.get(Warehouse.class,  warehouseId);
			
			session.beginTransaction();
			
			session.delete(warehouse);
			
			session.getTransaction().commit();
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
	public void updateWarehouse(long warehouseId, String location, String owner) {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
		}catch(Exception ex) {
			Warehouse warehouse = session.get(Warehouse.class,  warehouseId);
			warehouse.setLocation(location);
			warehouse.setOwner(owner);
			
			session.beginTransaction();
			
			session.update(warehouse);
			
			session.getTransaction().commit();
		}finally{
			if(session != null) {
				session.close();
			}
		}
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
}
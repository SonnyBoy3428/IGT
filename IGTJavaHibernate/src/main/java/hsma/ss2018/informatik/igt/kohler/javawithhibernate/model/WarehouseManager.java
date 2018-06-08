package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

public class WarehouseManager extends EntityManager{
	public static void createWarehouse(String location, String owner) {
		Warehouse warehouse = new Warehouse();
		warehouse.setLocation(location);
		warehouse.setOwner(owner);
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(owner);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public static String getWarehouse(long warehouseId) {
		Session session = sessionFactory.openSession();
		
		Warehouse warehouse = session.get(Warehouse.class,  warehouseId);
		
		session.close();
		
		return warehouseToXML(warehouse);
	}
	
	@SuppressWarnings("unchecked")
	public static String getAllWarehouses() {
		Session session = sessionFactory.openSession();
		
		List<Warehouse> warehousesList = session.createQuery("from WAREHOUSE").list();
		
		session.close();
		
		Set<Warehouse> warehouses = new HashSet<Warehouse>(warehousesList);
		
		return warehousesToXML(warehouses);
	}
	
	public static String getWarehouseDistricts(long warehouseId) {
		Session session = sessionFactory.openSession();
		
		Warehouse warehouse = session.get(Warehouse.class,  warehouseId);
		
		session.close();
		
		Set<District> districts = warehouse.getDistricts();
		
		return DistrictManager.districtsToXML(districts);
	}
	
	public static void deleteWarehouse(long warehouseId) {
		Session session = sessionFactory.openSession();
		
		Warehouse warehouse = session.get(Warehouse.class,  warehouseId);
		
		session.beginTransaction();
		
		session.delete(warehouse);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public static void updateWarehouse(long warehouseId, String location, String owner) {
		Session session = sessionFactory.openSession();
		
		Warehouse warehouse = session.get(Warehouse.class,  warehouseId);
		warehouse.setLocation(location);
		warehouse.setOwner(owner);
		
		session.beginTransaction();
		
		session.update(warehouse);
		
		session.getTransaction().commit();
		session.close();
	}
	
	protected static String warehouseToXML(Warehouse warehouse) {
		String xmlWarehouse;
		
		xmlWarehouse = "<Warehouse>"
				+ "<WarehouseId>" + warehouse.getWarehouseId() + "</WarehouseId>"
				+ "<Location>" + warehouse.getLocation() + "</Location>"
				+ "<Owner>" + warehouse.getOwner() + "</Owner>"
				+ "</Warehouse>";
		
		return xmlWarehouse;
	}
	
	protected static String warehousesToXML(Set<Warehouse> warehouses) {
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

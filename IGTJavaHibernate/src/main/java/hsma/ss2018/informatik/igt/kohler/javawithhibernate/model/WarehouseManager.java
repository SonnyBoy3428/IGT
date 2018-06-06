package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.util.Set;

import org.hibernate.Session;

public class WarehouseManager extends EntityManager{
	public void createWarehouse(String location, String owner) {
		Warehouse warehouse = new Warehouse();
		warehouse.setLocation(location);
		warehouse.setOwner(owner);
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(owner);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public String getWarehouse(long warehouseId) {
		Session session = sessionFactory.openSession();
		
		Warehouse warehouse = session.get(Warehouse.class,  warehouseId);
		
		session.close();
		
		Set<District> districts = warehouse.getDistricts();
		
		return districtsToXML(districts);
	}
	
	public String getWarehouseDistricts(long warehouseId) {
		Session session = sessionFactory.openSession();
		
		Warehouse warehouse = session.get(Warehouse.class,  warehouseId);
		
		session.close();
		
		return warehouseToXML(warehouse);
	}
	
	public void deleteWarehouse(long warehouseId) {
		Session session = sessionFactory.openSession();
		
		Warehouse warehouse = session.get(Warehouse.class,  warehouseId);
		
		session.beginTransaction();
		
		session.delete(warehouse);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public void updateWarehouse(long warehouseId, String location, String owner) {
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
	
	protected String districtsToXML(Set<District> districts) {
		String xmlDistricts;
		
		xmlDistricts = "<Districts>";
		
		if(districts != null && districts.size() > 0) {
			for(District district : districts) {
				xmlDistricts += DistrictManager.districtToXML(district); 
			}
		}
		
		xmlDistricts += "</Districts>";
		
		return xmlDistricts;
	}
}

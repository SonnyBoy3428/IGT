package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.util.Set;

import org.hibernate.Session;

public class DistrictManager extends EntityManager{
	public void createDistrict(String location) {
		District district = new District();
		district.setLocation(location);
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(district);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public String getDistrict(long districtId) {
		Session session = sessionFactory.openSession();
		
		District district = session.get(District.class,  districtId);
		
		session.close();
		
		return districtToXML(district);
	}
	
	public String getDistrictCustomers(long districtId) {
		Session session = sessionFactory.openSession();
		
		District district = session.get(District.class,  districtId);
		
		session.close();
		
		Set<Customer> customers = district.getCustomers();
		
		return customersToXML(customers);
	}
	
	public void deleteDistrict(long districtId) {
		Session session = sessionFactory.openSession();
		
		District district = session.get(District.class,  districtId);
		
		session.beginTransaction();
		
		session.delete(district);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public void updateDistrict(long districtId, String location) {
		Session session = sessionFactory.openSession();
		
		District district = session.get(District.class,  districtId);
		district.setLocation(location);
		
		session.beginTransaction();
		
		session.update(district);
		
		session.getTransaction().commit();
		session.close();
	}
	
	protected static String districtToXML(District district) {
		String xmlDistrict;
		
		xmlDistrict = "<District>"
				+ "<DistrictId>" + district.getDistrictId() + "</DistrictId>"
				+ "<Location>" + district.getLocation() + "</Location>"
				+ "<WarehouseId" + district.getWarehouse().getWarehouseId() + "</WarehouseId>"
				+ "<WarehouseLocation>" + district.getWarehouse().getLocation() + "</WarehouseLocation>"
				+ "<WarehouseOwner>" + district.getWarehouse().getOwner() + "</WarehouseOwner>"
				+ "</District>";
		
		return xmlDistrict;
	}
	
	protected String customersToXML(Set<Customer> customers) {
		String xmlCustomers;
		
		xmlCustomers = "<Customers>";
		
		if(customers != null && customers.size() > 0) {
			for(Customer customer : customers) {
				xmlCustomers += CustomerManager.customerToXML(customer); 
			}
		}
		
		xmlCustomers += "</Customers>";
		
		return xmlCustomers;
	}
}

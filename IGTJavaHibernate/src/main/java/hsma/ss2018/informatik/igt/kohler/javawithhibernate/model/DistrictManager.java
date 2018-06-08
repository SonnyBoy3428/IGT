package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.util.HashSet;
import java.util.List;
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
	
	@SuppressWarnings("unchecked")
	public String getAllDistricts() {
		Session session = sessionFactory.openSession();
		
		List<District> districtsList = session.createQuery("from DISTRICT").list();
		
		session.close();
		
		Set<District> districts = new HashSet<District>(districtsList);
		
		return districtsToXML(districts);
	}
	
	public String getDistrictCustomers(long districtId) {
		Session session = sessionFactory.openSession();
		
		District district = session.get(District.class,  districtId);
		
		session.close();
		
		Set<Customer> customers = district.getCustomers();
		
		return CustomerManager.customersToXML(customers);
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
	
	protected static String districtsToXML(Set<District> districts) {
		String xmlDistricts;
		
		xmlDistricts = "<Districts>";
		
		if(districts != null && districts.size() > 0) {
			for(District district : districts) {
				xmlDistricts += districtToXML(district); 
			}
		}
				
		xmlDistricts += "</Districts>";
		
		return xmlDistricts;
	}
}

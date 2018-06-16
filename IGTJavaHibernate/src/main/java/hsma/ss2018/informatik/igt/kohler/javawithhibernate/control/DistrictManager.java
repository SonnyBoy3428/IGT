package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.District;

/**
 * This class functions as the API with which one can deal with districts.
 * 
 * @author Dustin Noah Young,
 *
 */
public class DistrictManager extends EntityManager{
	/**
	 * Created a new district from the given values.
	 * 
	 * @param districtSize Size of the district.
	 * @param districtName Name of the district.
	 * 
	 * @return The newly created district.
	 */
	public static District createDistrict(String districtName, double districtSize) {
		District district = new District();
		district.setDistrictName(districtName);
		district.setDistrictSize(districtSize);
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			session.save(district);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return district;
	}
	
	/**
	 * Gets a district based on the passed district Id.
	 * 
	 * @param districtId Id of the district that is to be fetched.
	 * 
	 * @return The fetched district.
	 */
	public static District getDistrict(long districtId) {
		District district = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
		
			district = session.get(District.class,  districtId);
		
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return district;
	}
	
	/**
	 * Gets all the districts.
	 * 
	 * @return All existing districts.
	 */
	@SuppressWarnings("unchecked")
	public Set<District> getAllDistricts() {
		List<District> districtsList = null;
		Set<District> districts = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			districtsList = session.createQuery("from DISTRICT").getResultList();
			
			districts = new HashSet<District>(districtsList);
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
	 * Gets all the customers belonging to the district.
	 * 
	 * @param districtId Id of the district.
	 * 
	 * @return All customers belonging to the districts.
	 */
	public Set<Customer> getDistrictCustomers(long districtId) {
		District district = null;
		Set<Customer> customers = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			district = session.get(District.class,  districtId);
			
			customers = district.getCustomers();
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();	
			}
		}
						
		return customers;
	}
	
	/**
	 * Deletes a district.
	 * 
	 * @param districtId Id of the district that is to be deleted.
	 */
	public void deleteDistrict(long districtId) {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			District district = session.get(District.class,  districtId);
			
			session.beginTransaction();
			
			session.delete(district);
			
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
	 * Updated a district by it's id.
	 * 
	 * @param districtId Id of the district that is to be updated.
	 * @param districtName Name of the district.
	 * @param districtSize Size of the district in km^2.
	 */
	public void updateDistrict(long districtId, String districtName, double districtSize) {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			District district = session.get(District.class,  districtId);
			district.setDistrictName(districtName);
			district.setDistrictSize(districtSize);
			
			session.beginTransaction();
			
			session.update(district);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
		}finally{
			if(session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Turns a district into XML.
	 * 
	 * @param district District that is to be turned into XML.
	 * 
	 * @return XML version of the district.
	 */
	public static String districtToXML(District district) {
		String xmlDistrict;
		
		xmlDistrict = "<District>"
				+ "<DistrictId>" + district.getDistrictId() + "</DistrictId>"
				+ "<DistrictName>" + district.getDistrictName() + "</DistrictName>"
				+ "<WarehouseId>" + district.getWarehouse().getWarehouseId() + "</WarehouseId>"
				+ "<WarehouseLocation>" + district.getWarehouse().getLocation() + "</WarehouseLocation>"
				+ "<WarehouseOwner>" + district.getWarehouse().getOwner() + "</WarehouseOwner>"
				+ "</District>";
		
		return xmlDistrict;
	}
	
	/**
	 * Turns districts into XML.
	 * 
	 * @param districts Districts that are to be turned into XML.
	 * 
	 * @return XML version of the districts.
	 */
	public static String districtsToXML(Set<District> districts) {
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

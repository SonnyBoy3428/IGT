package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.District;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Warehouse;

/**
 * This class functions as the API with which one can deal with districts.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 *
 */
public class DistrictRepository extends EntityRepository{
	/**
	 * Created a new district from the given values.
	 * 
	 * @param districtSize Size of the district.
	 * @param districtName Name of the district.
	 * 
	 * @return The newly created district.
	 */
	public static District createDistrict(String districtName, double districtSize, int warehouseId) {
		District district = new District();
		district.setDistrictName(districtName);
		district.setDistrictSize(districtSize);
		
		Warehouse warehouse = WarehouseRepository.getWarehouse(warehouseId);
		
		district.setWarehouse(warehouse);
		
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
	public static District getDistrict(int districtId) {
		District district = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
		
			district = session.get(District.class,  districtId);
		
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
	 * Gets all the districts.
	 * 
	 * @return All existing districts.
	 */
	@SuppressWarnings("unchecked")
	public static Set<District> getAllDistricts() {
		Set<District> districts = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			List<District> districtsList = session.createQuery("from DISTRICT").getResultList();
			
			session.getTransaction().commit();
			
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
	public static Set<Customer> getDistrictCustomers(int districtId) {
		District district = getDistrict(districtId);	
		Set<Customer> customers = district.getCustomers();
						
		return customers;
	}
	
	/**
	 * Deletes a district.
	 * 
	 * @param districtId Id of the district that is to be deleted.
	 */
	public static boolean deleteDistrict(int districtId) {
		Session session = null;
		
		boolean districtDeleted = true;
		
		try {
			session = sessionFactory.openSession();
			
			District district = getDistrict(districtId);
			
			session.beginTransaction();
			
			session.delete(district);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
			
			districtDeleted = false;
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return districtDeleted;
	}
	
	/**
	 * Updated a district by it's id.
	 * 
	 * @param districtId Id of the district that is to be updated.
	 * @param districtName Name of the district.
	 * @param districtSize Size of the district in km^2.
	 * 
	 * @return The updated district.
	 */
	public static District updateDistrict(int districtId, String districtName, double districtSize, int warehouseId) {
		Session session = null;
		
		try {
			District district = getDistrict(districtId);
			Warehouse warehouse = WarehouseRepository.getWarehouse(warehouseId);
			
			district.setDistrictName(districtName);
			district.setDistrictSize(districtSize);
			district.setWarehouse(warehouse);
			
			session = sessionFactory.openSession();
			
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
		
		District updatedDistrict = getDistrict(districtId);
		
		return updatedDistrict;
	}
	
	/**
	 * Turns a district into JSON.
	 * 
	 * @param district District that is to be turned into JSON.
	 * 
	 * @return JSON version of the district.
	 */
	public static JSONObject districtToJSON(District district) {
		JSONObject jsonDistrict = new JSONObject().put("DistrictId", new Integer(district.getDistrictId()));
		jsonDistrict.put("DistrictName", district.getDistrictName());
		jsonDistrict.put("WarehouseId", district.getWarehouse().getWarehouseId());
		jsonDistrict.put("WarehouseLocation", district.getWarehouse().getLocation());
		jsonDistrict.put("WarehouseOwner", district.getWarehouse().getOwner());
		
		return jsonDistrict;
	}
	
	/**
	 * Turns districts into JSON.
	 * 
	 * @param districts Districts that are to be turned into JSON.
	 * 
	 * @return JSON version of the districts.
	 */
	public static JSONArray districtsToJSON(Set<District> districts) {
		JSONArray jsonDistricts = new JSONArray();
		
		if(districts != null && districts.size() > 0) {
			for(District district : districts) {
				JSONObject jsonDistrict = new JSONObject().put("District", districtToJSON(district));

				jsonDistricts.put(jsonDistrict); 
			}
		}
				
		return jsonDistricts;
	}
	
	/**
	 * Converts a district and its customers into JSON-format.
	 * 
	 * @param district The district.
	 * @param customers The customers of the district.
	 * 
	 * @return District and its customers in JSON-format.
	 */
	public static JSONObject districtAndCustomersToJSON(District district, Set<Customer> customers) {
		JSONObject jsonDistrictsAndCustomers = new JSONObject();
		jsonDistrictsAndCustomers.put("District", districtToJSON(district));
		jsonDistrictsAndCustomers.put("Customers", CustomerRepository.customersToJSON(customers));
		
		JSONObject jsonCompleteDistrictsAndCustomers = new JSONObject().put("DistrcitAndCustomers", jsonDistrictsAndCustomers);
		
		return jsonCompleteDistrictsAndCustomers;
	}
}

package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.services;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.DistrictRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.EntityRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.District;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;

/**
 * This class acts as the API for calls to the district repository.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 *
 */
@Path("/districtService")
public class DistrictService{
	/**
	 * Receives a POST request to create a district. The district information is located in the request body.
	 * 
	 * @param districtInformation Request body containing district information.
	 * 
	 * @return The response containing the district.
	 */
	@POST
	@Path("/createDistrict")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response createDistrict(String districtInformation){
		EntityRepository.setUp();
		
		JSONObject newDistrict = new JSONObject(districtInformation);
		
		District createdDistrict = DistrictRepository.createDistrict(newDistrict.getString("DistrictName"),newDistrict.getDouble("DistrictSize"), newDistrict.getInt("WarehouseId"));
		
		JSONObject response = new JSONObject();
		
		if(createdDistrict != null) {
			response.put("District", DistrictRepository.districtToJSON(createdDistrict));
			
			EntityRepository.exit();
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Creation of district failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to get a district. The district id is located within the URL.
	 * 
	 * @param districtId The district Id located in the URL.
	 * 
	 * @return The response containing the district.
	 */
	@GET
	@Path("/getDistrictById={param}")
	@Produces("application/json")
	public Response getDistrictById(@PathParam("param") int districtId) {
		EntityRepository.setUp();
		
		District district = DistrictRepository.getDistrict(districtId);
		
		JSONObject response = new JSONObject();
		
		if(district != null) {
			response.put("District", DistrictRepository.districtToJSON(district));
			
			EntityRepository.exit();
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Fetching of district with id " + districtId + " failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to get all districts.
	 * 
	 * @return The response containing the districts.
	 */
	@GET
	@Path("/getAllDistricts")
	@Produces("application/json")
	public Response getAllDistricts() {
		EntityRepository.setUp();
		
		Set<District> districts = DistrictRepository.getAllDistricts();
		
		JSONObject response = new JSONObject();
		
		if(districts != null) {
			response.put("Districts", DistrictRepository.districtsToJSON(districts));
			
			EntityRepository.exit();
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Fetching of districts failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to get all customers belonging to a district. The district id is located within the URL.
	 * 
	 * @param districtId The district id located in the URL.
	 * 
	 * @return The response containing the district and all his customers.
	 */
	@GET
	@Path("/getAllDistrictCustomersByDistrictId={param}")
	@Produces("application/json")
	public Response getAllDistrictCustomers(@PathParam("param") int districtId) {
		EntityRepository.setUp();
		
		District district = DistrictRepository.getDistrict(districtId);
		
		JSONObject response = new JSONObject();
		
		if(district != null) {
			Set<Customer> customers = DistrictRepository.getDistrictCustomers(districtId);
			
			if(customers != null) {
				response = DistrictRepository.districtAndCustomersToJSON(district, customers);
				
				EntityRepository.exit();
				
				return Response.status(200).entity(response.toString()).build();
			}else {
				response.put("Message", "District with id " + districtId + " does not have any customers!");
				
				EntityRepository.exit();
				
				return Response.status(200).entity(response.toString()).build();
			}
		}else {
			response.put("Message", "Fetching of district with id " + districtId + " failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a DELETE request to delete a district. The district id is located within the URL.
	 * 
	 * @param districtId The district id located inside the URL.
	 * 
	 * @return The response.
	 */
	@DELETE
	@Path("/deleteDistrictById={param}")
	@Produces("application/json")
	public Response deleteDistrict(@PathParam("param") int districtId) {
		EntityRepository.setUp();
		
		boolean districtDeleted = DistrictRepository.deleteDistrict(districtId);
		
		JSONObject response = new JSONObject();
		
		if(districtDeleted) {
			response.put("Message", "Deletion of district with id " + districtId + " successful!");
			
			EntityRepository.exit();
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Deletion of district with id " + districtId + " failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a PUT request to update a district. The district information is located within the request body.
	 * 
	 * @param districtInformation The district information inside the request body.
	 * 
	 * @return The response containing the updated district.
	 */
	@PUT
	@Path("/updateDistrict")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response updateDistrict(String districtInformation){
		EntityRepository.setUp();
		
		JSONObject district = new JSONObject(districtInformation);
		
		District updatedDistrict = DistrictRepository.updateDistrict(district.getInt("DistrictId"), district.getString("DistrictName"), district.getDouble("DistrictSize"), district.getInt("WarehouseId"));
		
		JSONObject response = new JSONObject();
		
		if(updatedDistrict != null) {
			response.put("District", DistrictRepository.districtToJSON(updatedDistrict));
			
			EntityRepository.exit();
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Update of district failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
}

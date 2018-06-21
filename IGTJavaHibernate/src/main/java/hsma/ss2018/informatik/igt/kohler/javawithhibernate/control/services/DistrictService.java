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
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.District;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;

/**
 * This class acts as the API for calls to the district repository.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 *
 */
@Path("/districtService")
public class DistrictService extends EntityService{
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
		JSONObject newDistrict = new JSONObject(districtInformation);
		
		District createdDistrict = DistrictRepository.createDistrict(newDistrict.getString("DistrictName"), Double.parseDouble(newDistrict.getString("DistrictSize")), Integer.parseInt(newDistrict.getString("WarehouseId")));
		
		JSONObject response = new JSONObject();
		
		if(createdDistrict != null) {
			response.put("Customer", DistrictRepository.districtToJSON(createdDistrict));
			
			return Response.status(201).entity(response.toString()).build();
		}else {
			response.put("Message", "Creation of district failed!");
			
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
		District district = DistrictRepository.getDistrict(districtId);
		
		JSONObject response = new JSONObject();
		
		if(district != null) {
			response.put("Customer", DistrictRepository.districtToJSON(district));
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Fetching of district with id " + districtId + " failed!");
			
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
		Set<District> districts = DistrictRepository.getAllDistricts();
		
		JSONObject response = new JSONObject();
		
		if(districts != null) {
			response.put("Districts", DistrictRepository.districtsToJSON(districts));
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Fetching of districts failed!");
			
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
		District district = DistrictRepository.getDistrict(districtId);
		
		JSONObject response = new JSONObject();
		
		if(district != null) {
			Set<Customer> customers = DistrictRepository.getDistrictCustomers(districtId);
			
			if(customers != null) {
				response = DistrictRepository.districtAndCustomersToJSON(district, customers);
				
				return Response.status(200).entity(response.toString()).build();
			}else {
				response.put("Message", "District with id " + districtId + " does not have any customers!");
				
				return Response.status(204).entity(response.toString()).build();
			}
		}else {
			response.put("Message", "Fetching of district with id " + districtId + " failed!");
			
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
		boolean districtDeleted = DistrictRepository.deleteDistrict(districtId);
		
		JSONObject response = new JSONObject();
		
		if(districtDeleted) {
			response.put("Message", "Deletion of district with id: " + districtId + " successful!");
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Deletion of district with id: " + districtId + " failed!");
			
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
		JSONObject district = new JSONObject(districtInformation);
		
		District updatedDistrict = DistrictRepository.updateDistrict(Integer.parseInt(district.getString("DistrictId")), district.getString("DistrictName"), Double.parseDouble(district.getString("DistrictSize")), Integer.parseInt(district.getString("WarehouseId")));
		
		JSONObject response = new JSONObject();
		
		if(updatedDistrict != null) {
			response.put("District", DistrictRepository.districtToJSON(updatedDistrict));
			
			return Response.status(202).entity(response.toString()).build();
		}else {
			response.put("Message", "Creation of district failed!");
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
}

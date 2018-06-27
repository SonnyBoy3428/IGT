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

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.CustomerRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.EntityRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;

/**
 * This class acts as the API for calls to the customer repository.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 *
 */
@Path("/customerService")
public class CustomerService extends EntityRepository{
	/**
	 * Receives a POST request to create a customer. The customer information is located in the request body.
	 * 
	 * @param customerInformation Request body containing customer information.
	 * 
	 * @return The response containing the customer.
	 */
	@POST
	@Path("/createCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response createCustomer(String customerInformation) {
		EntityRepository.setUp();
		
		JSONObject newCustomer = new JSONObject(customerInformation);
		
		Customer createdCustomer = CustomerRepository.createCustomer(newCustomer.getString("FirstName"), newCustomer.getString("LastName"), newCustomer.getString("Address"), newCustomer.getString("Telephone"), newCustomer.getString("CreditCardNr"), newCustomer.getInt("DistrictId"));
		
		JSONObject response = new JSONObject();
		
		if(createdCustomer != null) {
			response.put("Customer", CustomerRepository.customerToJSON(createdCustomer));
			
			EntityRepository.exit();
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Creation of customer failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to get a customer. The customer id is located within the URL.
	 * 
	 * @param customerId The customer Id located in the URL.
	 * 
	 * @return The response containing the customer.
	 */
	@GET
	@Path("/getCustomerById={param}")
	@Produces("application/json")
	public Response getCustomerById(@PathParam("param") int customerId) {
		EntityRepository.setUp();
		
		Customer customer = CustomerRepository.getCustomer(customerId);
		
		JSONObject response = new JSONObject();
		
		if(customer != null) {
			response.put("Customer", CustomerRepository.customerToJSON(customer));
			
			EntityRepository.exit();
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Fetching of customer with id " + customerId + " failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to get all customers.
	 * 
	 * @return The response containing the customers.
	 */
	@GET
	@Path("/getAllCustomers")
	@Produces("application/json")
	public Response getAllCustomers() {
		EntityRepository.setUp();
		
		Set<Customer> customers = CustomerRepository.getAllCustomers();
		
		JSONObject response = new JSONObject();
		
		if(customers != null) {
			response.put("Customers", CustomerRepository.customersToJSON(customers));
			
			EntityRepository.exit();
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Fetching of customers failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to get all orders belonging to a customer. The customer id is located within the URL.
	 * 
	 * @param customerId The customer id located in the URL.
	 * 
	 * @return The response containing the customer and all his orders.
	 */
	@GET
	@Path("/getAllCustomerOrdersByCustomerId={param}")
	@Produces("application/json")
	public Response getAllCustomerOrders(@PathParam("param") int customerId) {
		EntityRepository.setUp();
		
		Customer customer = CustomerRepository.getCustomer(customerId);
		
		JSONObject response = new JSONObject();
		
		if(customer != null) {
			Set<Order> orders = CustomerRepository.getCustomerAllOrders(customerId);
			
			if(orders != null) {
				response = CustomerRepository.customerAndOrdersToJSON(customer, orders);
				
				EntityRepository.exit();
				
				return Response.status(200).entity(response.toString()).build();
			}else {
				response.put("Message", "Customer with id " + customerId + " does not have any orders!");
				
				EntityRepository.exit();
				
				return Response.status(200).entity(response.toString()).build();
			}
		}else {
			response.put("Message", "Fetching of customer with id " + customerId + " failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to get all new orders belonging to a customer. The customer id is located within the URL.
	 * 
	 * @param customerId The customer id located in the URL.
	 * 
	 * @return The response containing the customer and all his new orders.
	 */
	@GET
	@Path("/getAllNewCustomerOrdersByCustomerId={param}")
	@Produces("application/json")
	public Response getNewCustomerOrders(@PathParam("param") int customerId) {
		EntityRepository.setUp();
		
		Customer customer = CustomerRepository.getCustomer(customerId);
		
		JSONObject response = new JSONObject();
		
		if(customer != null) {
			Set<Order> orders = CustomerRepository.getCustomerNewOrders(customerId);
			
			if(orders != null) {
				response = CustomerRepository.customerAndOrdersToJSON(customer, orders);
				
				EntityRepository.exit();
				
				return Response.status(200).entity(response.toString()).build();
			}else {
				response.put("Message", "Customer with id " + customerId + " does not have any new orders!");
				
				EntityRepository.exit();
				
				return Response.status(200).entity(response.toString()).build();
			}
		}else {
			response.put("Message", "Fetching of customer with id " + customerId + " failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a GET request to the order history belonging to a customer. The customer id is located within the URL.
	 * 
	 * @param customerId The customer id located in the URL.
	 * 
	 * @return The response containing the customer and his order history.
	 */
	@GET
	@Path("/getCustomerOrderHistoryByCustomerId={param}")
	@Produces("application/json")
	public Response getCustomerOrderHistory(@PathParam("param") int customerId) {
		EntityRepository.setUp();
		
		Customer customer = CustomerRepository.getCustomer(customerId);
		
		JSONObject response = new JSONObject();
		
		if(customer != null) {
			Set<Order> orders = CustomerRepository.getCustomerOrderHistory(customerId);
			
			if(orders != null) {
				response = CustomerRepository.customerAndOrdersToJSON(customer, orders);
				
				EntityRepository.exit();
				
				return Response.status(200).entity(response.toString()).build();
			}else {
				response.put("Message", "Customer with id " + customerId + " does not have an order history!");
				
				EntityRepository.exit();
				
				return Response.status(200).entity(response.toString()).build();
			}
		}else {
			response.put("Message", "Fetching of customer with id " + customerId + " failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a DELETE request to delete a customer. The customer id is located within the URL.
	 * 
	 * @param customerId The customer id located inside the URL.
	 * 
	 * @return The response.
	 */
	@DELETE
	@Path("/deleteCustomerByCustomerId={param}")
	@Produces("application/json")
	public Response deleteCustomer(@PathParam("param") int customerId) {
		EntityRepository.setUp();
		
		boolean customerDeleted = CustomerRepository.deleteCustomer(customerId);
		
		JSONObject response = new JSONObject();
		
		if(customerDeleted) {
			response.put("Message", "Deletion of customer with id " + customerId + " successful!");
			
			EntityRepository.exit();
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Deletion of customer with id " + customerId + " failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
	
	/**
	 * Receives a PUT request to update a customer. The customer information is located within the request body.
	 * 
	 * @param customerInformation The customer information inside the request body.
	 * 
	 * @return The response containing the updated customer.
	 */
	@PUT
	@Path("/updateCustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response updateCustomer(String customerInformation){
		EntityRepository.setUp();
		
		JSONObject customer = new JSONObject(customerInformation);
		
		Customer updatedCustomer = CustomerRepository.updateCustomer(customer.getInt("CustomerId"), customer.getString("FirstName"), customer.getString("LastName"), customer.getString("Address"), customer.getString("Telephone"), customer.getString("CreditCardNr"), customer.getInt("DistrictId"));
		
		JSONObject response = new JSONObject();
		
		if(updatedCustomer != null) {
			response.put("Customer", CustomerRepository.customerToJSON(updatedCustomer));
			
			EntityRepository.exit();
			
			return Response.status(200).entity(response.toString()).build();
		}else {
			response.put("Message", "Update of customer with id " + customer.getInt("CustomerId") + " failed!");
			
			EntityRepository.exit();
			
			return Response.status(500).entity(response.toString()).build();
		}
	}
}

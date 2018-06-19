package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.CustomerRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;

/**
 * This class acts as the API for calls to the customer repository.
 * 
 * @author Dustin Noah Young, 
 *
 */
@Path("/customerService")
public class CustomerService extends EntityService{
	/**
	 * The tag names belonging to a customer XML object.
	 */
	static final String[] TAG_NAMES = {"Customer", "CustomerId", "FirstName", "LastName", "Address", "Telephone", "CreditCardNr", "DistrictId"};
	
	/**
	 * Receives a POST request to create a customer. The customer information is located in the request body.
	 * 
	 * @param customerInformation Request body containing customer information.
	 * 
	 * @return The response containing the customer.
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@POST
	@Path("/createCustomer")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createCustomer(String customerInformation) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(new InputSource(new StringReader(customerInformation)));
		Element rootElement = document.getDocumentElement();
		
		String[] customerValues = extractInformationFromXMLEntity(TAG_NAMES, 2, 6, rootElement);
		
		Customer createdCustomer = CustomerRepository.createCustomer(customerValues[0], customerValues[1], customerValues[2], customerValues[3], customerValues[4], Long.parseLong(customerValues[5]));
		
		return Response.status(200).entity(CustomerRepository.customerToXML(createdCustomer)).build();
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
	public Response getCustomerById(@PathParam("param") long customerId) {
		Customer customer = CustomerRepository.getCustomer(customerId);
		
		String customerXML = CustomerRepository.customerToXML(customer);
		
		return Response.status(200).entity(customerXML).build();
	}
	
	/**
	 * Receives a GET request to get all customers.
	 * 
	 * @return The response containing the customers.
	 */
	@GET
	@Path("/getAllCustomers")
	public Response getAllCustomers() {
		Set<Customer> customers = CustomerRepository.getAllCustomers();
		
		String customersXML = CustomerRepository.customersToXML(customers);
		
		return Response.status(200).entity(customersXML).build();
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
	public Response getAllCustomerOrders(@PathParam("param") long customerId) {
		Customer customer = CustomerRepository.getCustomer(customerId);
		Set<Order> orders = CustomerRepository.getCustomerAllOrders(customerId);
		
		String customerAndOrdersXML = CustomerRepository.customerAndOrdersToXML(customer, orders);
		
		return Response.status(200).entity(customerAndOrdersXML).build();
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
	public Response getNewCustomerOrders(@PathParam("param") long customerId) {
		Customer customer = CustomerRepository.getCustomer(customerId);
		Set<Order> orders = CustomerRepository.getCustomerNewOrders(customerId);
		
		String customerAndOrdersXML = CustomerRepository.customerAndOrdersToXML(customer, orders);
		
		return Response.status(200).entity(customerAndOrdersXML).build();
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
	public Response getCustomerOrderHistory(@PathParam("param") long customerId) {
		Customer customer = CustomerRepository.getCustomer(customerId);
		Set<Order> orders = CustomerRepository.getCustomerOrderHistory(customerId);
		
		String customerAndOrdersXML = CustomerRepository.customerAndOrdersToXML(customer, orders);
		
		return Response.status(200).entity(customerAndOrdersXML).build();
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
	public Response deleteCustomer(@PathParam("param") long customerId) {
		CustomerRepository.deleteCustomer(customerId);
		
		String response = "Deletion of customer with id: " + customerId + " was successful!";
		
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Receives a PUT request to update a customer. The customer information is located within the request body.
	 * 
	 * @param customerInformation The customer information inside the request body.
	 * 
	 * @return The response containing the updated customer.
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@PUT
	@Path("/updateCustomer")
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateCustomer(String customerInformation) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(new InputSource(new StringReader(customerInformation)));
		Element rootElement = document.getDocumentElement();
		
		String[] customerValues = extractInformationFromXMLEntity(TAG_NAMES, 1, 6, rootElement);
		
		Customer updatedCustomer = CustomerRepository.updateCustomer(Long.parseLong(customerValues[0]), customerValues[1], customerValues[2], customerValues[3], customerValues[4], customerValues[5]);
		
		return Response.status(200).entity(CustomerRepository.customerToXML(updatedCustomer)).build();
	}
}

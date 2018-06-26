package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.District;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;

/**
 * This class functions as the API with which one can deal with customers.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 *
 */
public class CustomerRepository extends EntityRepository{	
	/**
	 * Creates a new customer from the given values.
	 * 
	 * @param firstName First name of the customer.
	 * @param lastName Last name of the customer.
	 * @param address Address of the customer.
	 * @param telephone Telephone number of the customer.
	 * @param creditCardNr Credit card number of the customer.
	 * 
	 * @return The newly created customer.
	 */
	public static Customer createCustomer(String firstName, String lastName, String address, String telephone, String creditCardNr, int districtId) {
		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setAddress(address);
		customer.setTelephone(telephone);
		customer.setCreditCardNr(creditCardNr);
		
		District district = DistrictRepository.getDistrict(districtId);
		
		customer.setDistrict(district);
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
		
			session.save(customer);
		
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
			
			customer = null;
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return customer;
	}
	
	/**
	 * Gets a customer based on the passed customer Id.
	 * 
	 * @param customerId Id of the customer who is to be fetched.
	 * 
	 * @return The fetched customer.
	 */
	public static Customer getCustomer(int customerId) {
		Customer customer = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
		
			customer = session.get(Customer.class,  customerId);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();
			}
		}
		
		return customer;
	}
	
	/**
	 * Gets all the customers.
	 * 
	 * @return All existing customers.
	 */
	@SuppressWarnings("unchecked")
	public static Set<Customer> getAllCustomers() {
		Set<Customer> customers = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
		
			session.beginTransaction();
			
			List<Customer>  customersList = session.createQuery("FROM hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer").getResultList();
			
			session.getTransaction().commit();
			
			customers = new HashSet<Customer>(customersList);
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
	 * Gets all the orders belonging to the customer.
	 * 
	 * @param customerId Id of the customer.
	 * 
	 * @return All orders belonging to the customer.
	 */
	public static Set<Order> getCustomerAllOrders(int customerId) {		
		Customer customer = getCustomer(customerId);
		Set<Order> orders = null;
		
		if(customer != null) {
			orders = customer.getOrders();
		}
				
		return orders;
	}
	
	/**
	 * Gets all the new orders belonging to the customer.
	 * A new order is an order which is placed in the current month.
	 * 
	 * @param customerId Id of the customer.
	 * 
	 * @return All new orders belonging to the customer.
	 */
	public static Set<Order> getCustomerNewOrders(int customerId) {
		Set<Order> orders = getCustomerAllOrders(customerId);
		Set<Order> newOrders = new HashSet<Order>();
		
		// We need to get the current year and month and convert them to a string
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		int year = localDate.getYear();
		
		String monthAsString = Integer.toString(month);
		String yearAsString = Integer.toString(year);
		
		// This makes sure the month value has two digits -> January would be 01 instead of just 1
		if(monthAsString.length() < 2) {
			monthAsString = "0" + monthAsString;
		}
		
		// This represents the current year and month in the form of the date attribute -> 2018-06
		String currentYearAndMonth = yearAsString + "-" + monthAsString;
		
		// Go through every order and see if they are new
		for(Order order : orders) {
			if(order.getOrderDate().contains(currentYearAndMonth)) {
				newOrders.add(order);
			}
		}
		
		return newOrders;
	}
	
	/**
	 * Gets the order history belonging to the customer.
	 * An order history contains all the orders of the current year.
	 * 
	 * @param customerId Id of the customer.
	 * 
	 * @return Order history belonging to the customer.
	 */
	public static Set<Order> getCustomerOrderHistory(int customerId) {
		Set<Order> orders = getCustomerAllOrders(customerId);
		Set<Order> orderHistory = new HashSet<Order>();
		
		// We need to get the current year and convert it to a string
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		
		String yearAsString = Integer.toString(year);
		
		// Go through every order and see if they are from this year
		for(Order order : orders) {
			if(order.getOrderDate().contains(yearAsString)) {
				orderHistory.add(order);
			}
		}
		
		return orderHistory;
	}
	
	/**
	 * Deletes a customer based on the Id.
	 * 
	 * @param customerId Id which is used to delete the customer.
	 * 
	 * @return Returns true if deleted.
	 */
	public static boolean deleteCustomer(int customerId) {
		Session session = null;
		
		boolean customerDeleted = true;
		
		try {
			session = sessionFactory.openSession();
			
			Customer customer = getCustomer(customerId);
			
			session.beginTransaction();
			
			session.delete(customer);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
			
			customerDeleted = false;
		}finally {
			session.close();
		}
		
		return customerDeleted;
	}
	
	/**
	 * Updates a customer with the given values.
	 * 
	 * @param customerId Id of the customer who is to be updated
	 * @param firstName First name of the customer
	 * @param lastName Last name of the customer
	 * @param address Address of the customer
	 * @param telephone Telephone number of the customer
	 * @param creditCardNr Credit card number of the customer
	 * 
	 * @return The newly updated customer.
	 */
	public static Customer updateCustomer(int customerId, String firstName, String lastName, String address, String telephone, String creditCardNr, int districtId) {
		Session session = null;
		
		boolean customerUpdated = true;
		
		try {			
			Customer customer = getCustomer(customerId);
			District district = DistrictRepository.getDistrict(districtId);
			
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			customer.setAddress(address);
			customer.setTelephone(telephone);
			customer.setCreditCardNr(creditCardNr);
			customer.setDistrict(district);
			
			session = sessionFactory.openSession();
			
			session.beginTransaction();
			
			session.update(customer);
			
			session.getTransaction().commit();
		}catch(Exception ex) {
			// TODO
			
			customerUpdated = false;
		}finally {
			session.close();
		}
		
		Customer updatedCustomer = getCustomer(customerId);
		
		if(!customerUpdated) {
			updatedCustomer = null;
		}
		
		return updatedCustomer;
	}
	
	/**
	 * Converts a Customer object into JSON-format.
	 * 
	 * @param customer Customer that is to be converted.
	 * 
	 * @return Customer in JSON format.
	 */
	public static JSONObject customerToJSON(Customer customer) {
		JSONObject jsonCustomer = new JSONObject().put("CustomerId", new Integer(customer.getCustomerId()));
		jsonCustomer.put("FirstName", customer.getFirstName());
		jsonCustomer.put("LastName", customer.getLastName());
		jsonCustomer.put("Address", customer.getAddress());
		jsonCustomer.put("Telephone", customer.getTelephone());
		jsonCustomer.put("CreditCardNr", customer.getCreditCardNr());
		jsonCustomer.put("DistrictId", new Integer(customer.getDistrict().getDistrictId()));
		jsonCustomer.put("DistrictName", customer.getDistrict().getDistrictName());
		
		return jsonCustomer;
	}
	
	/**
	 * Converts a set of customers into JSON-format.
	 * 
	 * @param customers Set of customers that are to be converted.
	 * 
	 * @return Customers in JSON format.
	 */
	public static JSONArray customersToJSON(Set<Customer> customers) {		
		JSONArray jsonCustomers = new JSONArray();
		
		if(customers != null && customers.size() > 0) {
			for(Customer customer : customers) {
				JSONObject jsonCustomer = customerToJSON(customer);

				jsonCustomers.put(jsonCustomer); 
			}
		}
		
		return jsonCustomers;
	}
	
	/**
	 * Converts a customer and his orders into JSON-format.
	 * 
	 * @param customer The customer.
	 * @param orders The orders of the customer.
	 * 
	 * @return Customer and his orders in JSON-format.
	 */
	public static JSONObject customerAndOrdersToJSON(Customer customer, Set<Order> orders) {
		JSONObject jsonCustomerAndOrders = new JSONObject();
		jsonCustomerAndOrders.put("Customer", customerToJSON(customer));
		jsonCustomerAndOrders.put("Orders", OrderRepository.ordersToJSON(orders));
		
		return jsonCustomerAndOrders;
	}
}

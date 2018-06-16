package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Order;

/**
 * This class functions as the API with which one can deal with customers.
 * 
 * @author Dustin Noah Young,
 *
 */
public class CustomerManager extends EntityManager{	
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
	public static Customer createCustomer(String firstName, String lastName, String address, String telephone, String creditCardNr) {
		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setAddress(address);
		customer.setTelephone(telephone);
		customer.setCreditCardNr(creditCardNr);
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			
			session.beginTransaction();
		
			session.save(customer);
		
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
	 * Gets a customer based on the passed customer Id.
	 * 
	 * @param customerId Id of the customer who is to be fetched.
	 * 
	 * @return The fetched customer.
	 */
	public static Customer getCustomer(long customerId) {
		Customer customer = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
		
			customer = session.get(Customer.class,  customerId);
		
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
		List<Customer> customersList = null;
		Set<Customer> customers = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
		
			customersList = session.createQuery("from CUSTOMER").getResultList();
			
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
	public static Set<Order> getCustomerAllOrders(long customerId) {
		Customer customer = null;
		Set<Order> orders = null;
		
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
		
			customer = session.get(Customer.class,  customerId);
		
			orders = customer.getOrders();
		}catch(Exception ex) {
			// TODO
		}finally {
			if(session != null) {
				session.close();
			}
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
	public static Set<Order> getCustomerNewOrders(long customerId) {
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
		
		for(Order order : orders) {
			if(order.getDate().contains(currentYearAndMonth)) {
				newOrders.add(order);
			}
		}
		
		return newOrders;
	}
	
	/**
	 * Gets the order history belonging to the customer.
	 * An order history contains all the orders from the current year.
	 * 
	 * @param customerId Id of the customer.
	 * 
	 * @return Order history belonging to the customer.
	 */
	public static Set<Order> getCustomerOrderHistory(long customerId) {
		Set<Order> orders = getCustomerAllOrders(customerId);
		Set<Order> orderHistory = new HashSet<Order>();
		
		// We need to get the current year and convert it to a string
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		
		String yearAsString = Integer.toString(year);
		
		for(Order order : orders) {
			if(order.getDate().contains(yearAsString)) {
				orderHistory.add(order);
			}
		}
		
		return orderHistory;
	}
	
	/**
	 * Deletes a customer based on the Id.
	 * 
	 * @param customerId Id which is used to delete the customer.
	 */
	public static void deleteCustomer(long customerId) {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
		}catch(Exception ex) {
			Customer customer = session.get(Customer.class,  customerId);		
			
			session.beginTransaction();
			
			session.delete(customer);
			
			session.getTransaction().commit();
		}finally {
			session.close();
		}
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
	 * @return The newly created customer.
	 */
	public static void updateCustomer(long customerId, String firstName, String lastName, String address, String telephone, String creditCardNr) {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
		}catch(Exception ex) {
			Customer customer = session.get(Customer.class,  customerId);		
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			customer.setAddress(address);
			customer.setTelephone(telephone);
			customer.setCreditCardNr(creditCardNr);
			
			session.beginTransaction();
			
			session.update(customer);
			
			session.getTransaction().commit();
		}finally {
			session.close();
		}
	}
	
	/**
	 * Converts a Customer object into XML-format.
	 * 
	 * @param customer That is to be converted.
	 * 
	 * @return Customer in XML format.
	 */
	public static String customerToXML(Customer customer) {
		String xmlCustomer;
		
		xmlCustomer = "<Customer>"
				+ "<CustomerId>" + customer.getCustomerId() + "</CustomerId>"
				+ "<FirstName>" + customer.getFirstName() + "</FirstName>"
				+ "<LastName>" + customer.getLastName() + "</LastName>"
				+ "<Address>" + customer.getAddress() + "</Address>"
				+ "<Telephone>" + customer.getTelephone() + "</Telephone>"
				+ "<CreditCardNr>" + customer.getCreditCardNr() + "</CreditCardNr>"
				+ "<DistrictId" + customer.getDistrict().getDistrictId() + "</DistrictId>"
				+ "<DistrictName>" + customer.getDistrict().getDistrictName() + "</DistrictName>"
				+ "</Customer>";
		
		return xmlCustomer;
	}
	
	/**
	 * Converts a set of customers into XML-format.
	 * 
	 * @param customers Set of customers that are to be converted.
	 * 
	 * @return Customers in XML format.
	 */
	public static String customersToXML(Set<Customer> customers) {
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

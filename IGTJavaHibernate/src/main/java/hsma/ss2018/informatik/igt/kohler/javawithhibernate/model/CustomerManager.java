package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

public class CustomerManager extends EntityManager{	
	public static void createCustomer(String firstName, String lastName, String address, String telephone, String creditCardNr) {
		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setAddress(address);
		customer.setTelephone(telephone);
		customer.setCreditCardNr(creditCardNr);
		
		try {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(customer);
		
		session.getTransaction().commit();
		session.close();
		}catch(Exception ex) {
			
		}finally {
			
		}
	}
	
	public static String getCustomer(long customerId) {
		Session session = sessionFactory.openSession();
		
		Customer customer = session.get(Customer.class,  customerId);
		
		session.close();
		
		return customerToXML(customer);
	}
	
	@SuppressWarnings("unchecked")
	public static String getAllCustomers() {
		Session session = sessionFactory.openSession();
		
		List<Customer> customersList = session.createQuery("from CUSTOMER").list();
		
		session.close();
		
		Set<Customer> customers = new HashSet<Customer>(customersList);
		
		return customersToXML(customers);
	}
	
	public static String getCustomerOrderHistory(long customerId) {
		Session session = sessionFactory.openSession();
		
		Customer customer = session.get(Customer.class,  customerId);
		
		session.close();
		
		Set<Order> orders = customer.getOrders();
		
		return OrderManager.ordersToXML(orders);
	}
	
	public static void deleteCustomer(long customerId) {
		Session session = sessionFactory.openSession();
		
		Customer customer = session.get(Customer.class,  customerId);		
		
		session.beginTransaction();
		
		session.delete(customer);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public static void updateCustomer(long customerId, String firstName, String lastName, String address, String telephone, String creditCardNr) {
		Session session = sessionFactory.openSession();
		
		Customer customer = session.get(Customer.class,  customerId);		
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setAddress(address);
		customer.setTelephone(telephone);
		customer.setCreditCardNr(creditCardNr);
		
		session.beginTransaction();
		
		session.update(customer);
		
		session.getTransaction().commit();
		session.close();
	}
	
	protected static String customerToXML(Customer customer) {
		String xmlCustomer;
		
		xmlCustomer = "<Customer>"
				+ "<CustomerId>" + customer.getCustomerId() + "</CustomerId>"
				+ "<FirstName>" + customer.getFirstName() + "</FirstName>"
				+ "<LastName>" + customer.getLastName() + "</LastName>"
				+ "<Address>" + customer.getAddress() + "</Address>"
				+ "<Telephone>" + customer.getTelephone() + "</Telephone>"
				+ "<CreditCardNr>" + customer.getCreditCardNr() + "</CreditCardNr>"
				+ "<DistrictId" + customer.getDistrict().getDistrictId() + "</DistrictId>"
				+ "<DistrictLocation" + customer.getDistrict().getLocation() + "</DistrictLocation>"
				+ "</Customer>";
		
		return xmlCustomer;
	}
	
	protected static String customersToXML(Set<Customer> customers) {
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

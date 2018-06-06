package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.util.Set;

import org.hibernate.Session;

public class CustomerManager extends EntityManager{	
	public void createCustomer(String firstName, String lastName, String address, String telephone, String creditCardNr) {
		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setAddress(address);
		customer.setTelephone(telephone);
		customer.setCreditCardNr(creditCardNr);
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(customer);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public String getCustomer(long customerId) {
		Session session = sessionFactory.openSession();
		
		Customer customer = session.get(Customer.class,  customerId);
		
		session.close();
		
		return customerToXML(customer);
	}
	
	public String getAllCustomers() {
		Session session = sessionFactory.openSession();
		
		//Customer customer = session
		
		session.close();
		
		return customerToXML(customer);
	}
	
	public String getCustomerOrderHistory(long customerId) {
		Session session = sessionFactory.openSession();
		
		Customer customer = session.get(Customer.class,  customerId);
		
		session.close();
		
		Set<Order> orders = customer.getOrders();
		
		return ordersToXML(orders);
	}
	
	public void deleteCustomer(long customerId) {
		Session session = sessionFactory.openSession();
		
		Customer customer = session.get(Customer.class,  customerId);		
		
		session.beginTransaction();
		
		session.delete(customer);
		
		session.getTransaction().commit();
		session.close();
	}
	
	public void updateCustomer(long customerId, String firstName, String lastName, String address, String telephone, String creditCardNr) {
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
	
	protected String ordersToXML(Set<Order> orders) {
		String xmlOrders;
		
		xmlOrders = "<Orders>";
		
		if(orders != null && orders.size() > 0) {
			for(Order order : orders) {
				xmlOrders += OrderManager.orderToXML(order); 
			}
		}
				
		xmlOrders += "</Orders>";
		
		return xmlOrders;
	}
}

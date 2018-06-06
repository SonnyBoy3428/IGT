package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

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
	
	protected String customerToXML(Customer customer) {
		String xmlCustomer;
		
		xmlCustomer = "<Customer>"
				+ "<CustomerId>" + customer.getCustomerId() + "</CustomerId>"
				+ "<FirstName>" + customer.getFirstName() + "</FirstName>"
				+ "<LastName>" + customer.getLastName() + "</LastName>"
				+ "<Address>" + customer.getAddress() + "</Address>"
				+ "<Telephone>" + customer.getTelephone() + "</Telephone>"
				+ "<CreditCardNr>" + customer.getCreditCardNr() + "</CreditCardNr>"
				+ "</Customer>";
		
		return xmlCustomer;
	}
}

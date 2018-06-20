/**
 * This class functions as a map to the CUSTOMER in the database.
 * Every Attribute is represented in the form of a member variable.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 */
package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER")
public class Customer implements Serializable{
	@Id
	@Column(name = "CustomerId", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;
	
	@Column(name = "FirstName", length = 30, nullable = false)
	private String firstName;
	
	@Column(name = "LastName", length = 30, nullable = false)
	private String lastName;
	
	@Column(name = "Address", length = 50)
	private String address;
	
	@Column(name = "Telephone", length = 15)
	private String telephone;
	
	@Column(name = "CreditCardNr", length = 12, nullable = false)
	private String creditCardNr;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "DistrictId")
	private District district;
	
	
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "OrderId")
	private Set<Order> orders;
	
	public Customer() {
	}
	
	public int getCustomerId() {
		return customerId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getCreditCardNr() {
		return creditCardNr;
	}
	
	public void setCreditCardNr(String creditCardNr) {
		this.creditCardNr = creditCardNr;
	}
	
	public District getDistrict() {
		return district;
	}
	
	public void setDistrict(District district) {
		this.district = district;
	}
	
	public Set<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
}

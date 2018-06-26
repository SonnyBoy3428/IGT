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
	private int customerId;
	private String firstName;
	private String lastName;
	private String address;
	private String telephone;
	private String creditCardNr;
	private District district;
	private Set<Order> orders;
	
	public Customer() {
	}
	
	@Id
	@Column(name = "CustomerId", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	@Column(name = "FirstName", length = 30)
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "LastName", length = 30)
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "Address", length = 50)
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "Telephone", length = 15)
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Column(name = "CreditCardNr", length = 12)
	public String getCreditCardNr() {
		return creditCardNr;
	}
	
	public void setCreditCardNr(String creditCardNr) {
		this.creditCardNr = creditCardNr;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DistrictId")
	public District getDistrict() {
		return district;
	}
	
	public void setDistrict(District district) {
		this.district = district;
	}
	
	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Set<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
}

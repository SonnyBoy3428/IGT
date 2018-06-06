package hsma.ss2018.informatik.igt.kohler.javawithhibernate;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER")
public class Customer {
	private long customerId;
	private String firstName;
	private String lastName;
	private String address;
	private String telephone;
	private String creditCardNr;
	
	private District district;
	
	public Customer() {
	}
	
	@Id
	@Column(name = "CustomerId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	@Column(name = "FirstName")
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "LastName")
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "Address")
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "Telephone")
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Column(name = "CreditCardNr")
	public String getCreditCardNr() {
		return creditCardNr;
	}
	
	public void setCreditCardNr(String creditCardNr) {
		this.creditCardNr = creditCardNr;
	}
	
	@ManyToOne(cascade = CascadeType.ALL)
	public District getDistrict() {
		return district;
	}
	
	public void setDistrict(District district) {
		this.district = district;
	}
}

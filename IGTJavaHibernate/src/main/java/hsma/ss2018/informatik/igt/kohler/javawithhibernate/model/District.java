/**
 * This class functions as a map to the DISTRICT in the database.
 * Every Attribute is represented in the form of a member variable.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 */
package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "DISTRICT")
public class District implements Serializable{
	private int districtId;
	private String districtName;
	private double districtSize;
	private Warehouse warehouse;
	private Set<Customer> customers;
	
	public District() {
	}
	
	@Id
	@Column(name = "DistrictId", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getDistrictId() {
		return districtId;
	}
	
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	
	@Column(name = "DistrictName", length = 50)
	public String getDistrictName() {
		return districtName;
	}
	
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	@Column(name = "DistrictSize")
	public double getDistrictSize() {
		return districtSize;
	}
	
	public void setDistrictSize(double districtSize) {
		this.districtSize = districtSize;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "WarehouseId")
	public Warehouse getWarehouse() {
		return warehouse;
	}
	
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	@OneToMany(mappedBy = "district", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Set<Customer> getCustomers(){
		return customers;
	}
	
	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}
}

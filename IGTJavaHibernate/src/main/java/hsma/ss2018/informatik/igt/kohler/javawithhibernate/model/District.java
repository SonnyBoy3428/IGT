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
	@Id
	@Column(name = "DistrictId", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int districtId;
	
	@Column(name = "DistrictName", length = 50, nullable = false)
	private String districtName;
	
	@Column(name = "DistrictSize")
	private double districtSize;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "WarehouseId")
	private Warehouse warehouse;
	
	@OneToMany(mappedBy = "district", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "CustomerId")
	private Set<Customer> customers;
	
	public District() {
	}
	
	public int getDistrictId() {
		return districtId;
	}
	
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	
	public String getDistrictName() {
		return districtName;
	}
	
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	public double getDistrictSize() {
		return districtSize;
	}
	
	public void setDistrictSize(double districtSize) {
		this.districtSize = districtSize;
	}
	
	public Warehouse getWarehouse() {
		return warehouse;
	}
	
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	public Set<Customer> getCustomers(){
		return customers;
	}
	
	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}
}

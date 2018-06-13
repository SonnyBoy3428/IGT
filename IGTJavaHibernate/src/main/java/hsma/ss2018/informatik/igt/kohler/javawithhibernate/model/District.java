package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "DISTRICT")
public class District implements Serializable{
	@Id
	@Column(name = "DistrictId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long districtId;
	
	@Column(name = "Location")
	private String location;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "WarehouseId")
	private Warehouse warehouse;
	
	@OneToMany(mappedBy = "district", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "CustomerId")
	private Set<Customer> customers;
	
	public District() {
	}
	
	public long getDistrictId() {
		return districtId;
	}
	
	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
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

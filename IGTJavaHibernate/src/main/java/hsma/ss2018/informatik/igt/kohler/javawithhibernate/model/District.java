package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "DISTRICT")
public class District {
	private long districtId;
	private String location;
	
	private Warehouse warehouse;
	
	private Set<Customer> customers;
	
	public District() {
	}
	
	@Id
	@Column(name = "DistrictId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getDistrictId() {
		return districtId;
	}
	
	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}
	
	@Column(name = "Location")
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	@ManyToOne
	@JoinColumn(name = "WarehouseId")
	public Warehouse getWarehouse() {
		return warehouse;
	}
	
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	@OneToMany(mappedBy = "district")
	public Set<Customer> getCustomers(){
		return customers;
	}
	
	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}
}

package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "WAREHOUSE")
public class Warehouse {
	private long warehouseId;
	private String location;
	private String owner;
	
	private Set<District> district;
	
	public Warehouse() {
	}
	
	@Id
	@Column(name = "WarehouseId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getWarehouseId() {
		return warehouseId;
	}
	
	public void setWarehouseId(long warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	@Column(name = "Location")
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Column(name = "Owner")
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	@OneToMany(mappedBy = "warehouse")
	public Set<District> getDistricts(){
		return district;
	}
	
	public void setDistricts(Set<District> district) {
		this.district = district;
	}
}

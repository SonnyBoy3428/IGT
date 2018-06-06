package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "WAREHOUSE")
public class Warehouse {
	private long warehouseId;
	private String location;
	private String owner;
	
	private Set<Stock> stock;
	
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
	public Set<Stock> getStock(){
		return stock;
	}
	
	public void setStock(Set<Stock> stock) {
		this.stock = stock;
	}
}

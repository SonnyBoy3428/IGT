package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "WAREHOUSE")
public class Warehouse implements Serializable{
	@Id
	@Column(name = "WarehouseId", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long warehouseId;
	
	@Column(name = "Location", nullable = false)
	private String location;
	
	@Column(name = "Owner", nullable = false)
	private String owner;
	
	@OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "DistrictId")
	private Set<District> districts;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stockId.warehouse", cascade = CascadeType.ALL)
	private Set<Stock> stocks;
	
	public Warehouse() {
	}
	
	public long getWarehouseId() {
		return warehouseId;
	}
	
	public void setWarehouseId(long warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public Set<District> getDistricts(){
		return districts;
	}
	
	public void setDistricts(Set<District> districts) {
		this.districts = districts;
	}
	
	public Set<Stock> getStocks(){
		return stocks;
	}
	
	public void setStocks(Set<Stock> stocks) {
		this.stocks = stocks;
	}
}

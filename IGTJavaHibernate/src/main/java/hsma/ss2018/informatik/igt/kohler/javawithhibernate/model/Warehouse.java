/**
 * This class functions as a map to the WAREHOUSE in the database.
 * Every Attribute is represented in the form of a member variable.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 */
package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "WAREHOUSE")
public class Warehouse implements Serializable{
	private int warehouseId;
	private String location;
	private String owner;
	private Set<District> districts;
	private Set<Stock> stock;
	
	public Warehouse() {
	}
	
	@Id
	@Column(name = "WarehouseId", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getWarehouseId() {
		return warehouseId;
	}
	
	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	@Column(name = "Location", length = 50)
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Column(name = "Owner", length = 60)
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	@OneToMany(mappedBy = "warehouse", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Set<District> getDistricts(){
		return districts;
	}
	
	public void setDistricts(Set<District> districts) {
		this.districts = districts;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stockId.warehouse", cascade = CascadeType.ALL)
	public Set<Stock> getStock(){
		return stock;
	}
	
	public void setStock(Set<Stock> stock) {
		this.stock = stock;
	}
}
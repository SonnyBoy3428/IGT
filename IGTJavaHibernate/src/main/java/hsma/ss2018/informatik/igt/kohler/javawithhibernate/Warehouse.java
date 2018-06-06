package hsma.ss2018.informatik.igt.kohler.javawithhibernate;

import javax.persistence.*;

@Entity
@Table(name = "WAREHOUSE")
public class Warehouse {
	private long warehouseId;
	private String location;
	private String owner;
	
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
	
	@Column(name = )
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Column(name = )
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
}

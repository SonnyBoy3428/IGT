package hsma.ss2018.informatik.igt.kohler.javawithhibernate;

import javax.persistence.*;

@Entity
@Table(name = "DISTRICT")
public class District {
	private long districtId;
	private String location;
	
	private long warehouseId;
	
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
	
	@ManyToOne(cascade = CascadeType.ALL)
	public long getWarehouseId() {
		return warehouseId;
	}
	
	public void setWarehouseId(long warehouseId) {
		this.warehouseId = warehouseId;
	}
}

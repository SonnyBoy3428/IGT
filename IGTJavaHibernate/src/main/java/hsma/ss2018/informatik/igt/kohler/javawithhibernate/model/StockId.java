package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class StockId implements Serializable{
	@ManyToOne
	private Warehouse warehouse;
	
	@ManyToOne
	private Item item;
	
	public StockId() {
		
	}
	
	protected Warehouse getWarehouse() {
		return warehouse;
	}
	
	protected void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	protected Item getItem() {
		return item;
	}
	
	protected void setItem(Item item) {
		this.item = item;
	}
}

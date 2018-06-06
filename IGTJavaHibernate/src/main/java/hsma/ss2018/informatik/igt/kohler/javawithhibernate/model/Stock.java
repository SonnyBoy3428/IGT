package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import javax.persistence.*;

@Entity
@Table(name = "STOCK")
public class Stock {
	private Warehouse warehouse;
	private Item item;
	private long quantity;
	
	@ManyToOne
	@JoinColumn(name = "WarehouseId")
	public Warehouse getWarehouse() {
		return warehouse;
	}
	
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	@ManyToOne
	@JoinColumn(name = "ItemId")
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	@Column(name = "Quantity")
	public long getQuantity() {
		return quantity;
	}
	
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
}

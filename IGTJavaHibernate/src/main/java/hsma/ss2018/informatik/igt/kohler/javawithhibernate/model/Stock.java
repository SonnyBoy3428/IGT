/**
 * This class functions as a map to the STOCK in the database.
 * Every Attribute is represented in the form of a member variable.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 */
package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "STOCK")
@AssociationOverrides({
	@AssociationOverride(name = "stockId.warehouse", joinColumns = @JoinColumn(name = "WarehouseId")),
	@AssociationOverride(name = "stockId.item", joinColumns = @JoinColumn(name = "ItemId")),
})
public class Stock implements Serializable{
	@EmbeddedId
	private StockId stockId;

	@Column(name = "Quantity")
	private int quantity;
	
	public Stock() {
		
	}
	
	public StockId getStockId() {
		return stockId;
	}
	
	public void setStockId(StockId stockId) {
		this.stockId = stockId;
	}
	
	@Transient
	public Warehouse getWarehouse() {
		return getStockId().getWarehouse();
	}
	
	public void setWarehouse(Warehouse warehouse) {
		getStockId().setWarehouse(warehouse);
	}
	
	@Transient
	public Item getItem() {
		return getStockId().getItem();
	}
	
	public void setItem(Item item) {
		getStockId().setItem(item);
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

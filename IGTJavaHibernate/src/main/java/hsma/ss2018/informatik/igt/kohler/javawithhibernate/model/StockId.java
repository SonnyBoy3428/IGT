/**
 * This class functions as the id for stocks.
 * Every Attribute is represented in the form of a member variable.
 * 
 * @author Dustin Noah Young
 */
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
	
	public Warehouse getWarehouse() {
		return warehouse;
	}
	
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
}

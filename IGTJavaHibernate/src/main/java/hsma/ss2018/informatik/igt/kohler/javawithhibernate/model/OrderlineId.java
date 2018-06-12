package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class OrderlineId {
	private Order order;
	private Item item;
	
	@ManyToOne
	@JoinColumn(name = "OrderId")
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@ManyToOne
	@JoinColumn(name = "ItemId")
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
}

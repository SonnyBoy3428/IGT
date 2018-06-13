package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class OrderlineId {
	private Order order;
	private Item item;
	
	@ManyToOne
	@JoinColumn(name = "OrderId")
	protected Order getOrder() {
		return order;
	}
	
	protected void setOrder(Order order) {
		this.order = order;
	}
	
	@ManyToOne
	@JoinColumn(name = "ItemId")
	protected Item getItem() {
		return item;
	}
	
	protected void setItem(Item item) {
		this.item = item;
	}
}

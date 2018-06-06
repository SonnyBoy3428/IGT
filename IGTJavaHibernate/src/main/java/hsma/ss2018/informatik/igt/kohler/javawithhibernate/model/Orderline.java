package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import javax.persistence.*;

@Entity
@Table(name = "ORDERLINE")
public class Orderline {
	private Order order;
	private Item item;
	private long quantity;
	
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
	@Column(name = "Quantity")
	public long getQuantity() {
		return quantity;
	}
	
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
}

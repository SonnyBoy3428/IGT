package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import javax.persistence.*;

@Entity
@Table(name = "ORDERLINE")
public class Orderline {
	private OrderlineId orderlineId;
	private long quantity;
	
	@EmbeddedId
	public OrderlineId getId() {
		return orderlineId;
	}
	
	@Transient
	public Order getOrder() {
		return orderlineId.getOrder();
	}
	
	public void setOrder(Order order) {
		this.orderlineId.setOrder(order);
	}
	
	@Transient
	public Item getItem() {
		return orderlineId.getItem();
	}
	
	public void setItem(Item item) {
		this.orderlineId.setItem(item);
	}
	
	@Column(name = "Quantity")
	public long getQuantity() {
		return quantity;
	}
	
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
}

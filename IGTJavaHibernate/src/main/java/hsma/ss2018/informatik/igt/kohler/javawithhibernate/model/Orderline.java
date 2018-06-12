package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import javax.persistence.*;

@Entity
@Table(name = "ORDERLINE")
public class Orderline {
	private OrderlineId id;
	private Order order;
	private Item item;
	private long quantity;
	
	@EmbeddedId
	public OrderlineId getId() {
		return id;
	}
	
	@Transient
	public Order getOrder() {
		return order;
	}
	
	@Transient
	public Item getItem() {
		return item;
	}
	
	@Column(name = "Quantity")
	public long getQuantity() {
		return quantity;
	}
	
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
}

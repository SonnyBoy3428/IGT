package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderlineId implements Serializable{
	@ManyToOne
	private Order order;
	
	@ManyToOne
	private Item item;
	
	public OrderlineId() {
		
	}
	
	protected Order getOrder() {
		return order;
	}
	
	protected void setOrder(Order order) {
		this.order = order;
	}
	
	protected Item getItem() {
		return item;
	}
	
	protected void setItem(Item item) {
		this.item = item;
	}
}

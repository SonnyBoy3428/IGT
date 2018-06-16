/**
 * This class functions as the id for orderlines.
 * Every Attribute is represented in the form of a member variable.
 * 
 * @author Dustin Noah Young
 */
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
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
}

/**
 * This class functions as a map to the ORDERLINE in the database.
 * Every Attribute is represented in the form of a member variable.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 */
package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "ORDERLINE")
@AssociationOverrides({
	@AssociationOverride(name = "orderlineId.order", joinColumns = @JoinColumn(name = "OrderId")),
	@AssociationOverride(name = "orderlineId.item", joinColumns = @JoinColumn(name = "ItemId")),
})
public class Orderline implements Serializable{
	private OrderlineId orderlineId = new OrderlineId();
	private int quantity;
	
	public Orderline() {
		
	}
	
	@EmbeddedId
	public OrderlineId getOrderlineId() {
		return orderlineId;
	}
	
	public void setOrderlineId(OrderlineId orderlineId) {
		this.orderlineId = orderlineId;
	}
	
	@Transient
	public Order getOrder() {
		return getOrderlineId().getOrder();
	}
	
	public void setOrder(Order order) {
		getOrderlineId().setOrder(order);
	}
	
	@Transient
	public Item getItem() {
		return getOrderlineId().getItem();
	}
	
	public void setItem(Item item) {
		getOrderlineId().setItem(item);
	}
	
	@Column(name = "Quantity")
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

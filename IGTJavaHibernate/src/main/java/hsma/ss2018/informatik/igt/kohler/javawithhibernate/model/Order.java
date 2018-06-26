/**
 * This class functions as a map to the CUSTOMER_ORDER in the database.
 * Every Attribute is represented in the form of a member variable.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 */
package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_ORDER")
public class Order implements Serializable{
	private int orderId;
	private String orderDate;
	private boolean orderCarriedOut;
	private double totalCost;
	private Customer customer;
	private Set<Orderline> orderline;
	
	public Order() {
	}
	
	@Id
	@Column(name = "OrderId", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "OrderDate", length = 10)
	public String getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	@Column(name = "OrderCarriedOut")
	public boolean getOrderCarriedOut() {
		return orderCarriedOut;
	}
	
	public void setOrderCarriedOut(boolean orderCarriedOut) {
		this.orderCarriedOut = orderCarriedOut;
	}
	
	@Column(name = "TotalCost")
	public double getTotalCost() {
		return totalCost;
	}
	
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CustomerId")
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderlineId.order", cascade = CascadeType.ALL)
	public Set<Orderline> getOrderline(){
		return orderline;
	}
	
	public void setOrderline(Set<Orderline> orderline) {
		this.orderline = orderline;
	}
}

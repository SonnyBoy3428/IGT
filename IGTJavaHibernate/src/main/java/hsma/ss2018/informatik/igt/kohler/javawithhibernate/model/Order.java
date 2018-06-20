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
	@Id
	@Column(name = "OrderId", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	
	@Column(name = "OrderDate", length = 10, nullable = false)
	private String orderDate;
	
	@Column(name = "OrderCarriedOut")
	private boolean orderCarriedOut;
	
	@Column(name = "TotalCost")
	private double totalCost;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "CustomerId")
	private Customer customer;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderlineId.order", cascade = CascadeType.ALL)
	private Set<Orderline> orderline;
	
	public Order() {
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public String getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	public boolean getOrderCarriedOut() {
		return orderCarriedOut;
	}
	
	public void setOrderCarriedOut(boolean orderCarriedOut) {
		this.orderCarriedOut = orderCarriedOut;
	}
	
	public double getTotalCost() {
		return totalCost;
	}
	
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Set<Orderline> getOrderline(){
		return orderline;
	}
	
	public void setOrderline(Set<Orderline> orderline) {
		this.orderline = orderline;
	}
}

/**
 * This class functions as a map to the CUSTOMER_ORDER in the database.
 * Every Attribute is represented in the form of a member variable.
 * 
 * @author Dustin Noah Young
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
	private long orderId;
	
	@Column(name = "Date", nullable = false)
	private String date;
	
	@Column(name = "OrderCarriedOut", nullable = false)
	private byte orderCarriedOut;
	
	@Column(name = "TotalCost", nullable = false)
	private double totalCost;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "OrderId")
	private Customer customer;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderlineId.order", cascade = CascadeType.ALL)
	private Set<Orderline> orderline;
	
	public Order() {
	}
	
	public long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public byte getOrderCarriedOut() {
		return orderCarriedOut;
	}
	
	public void setOrderCarriedOut(byte orderCarriedOut) {
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

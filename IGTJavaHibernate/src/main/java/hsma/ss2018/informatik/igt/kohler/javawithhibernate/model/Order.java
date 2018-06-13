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
	
	protected Order() {
	}
	
	protected long getOrderId() {
		return orderId;
	}
	
	protected void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	protected String getDate() {
		return date;
	}
	
	protected void setDate(String date) {
		this.date = date;
	}
	
	protected byte getOrderCarriedOut() {
		return orderCarriedOut;
	}
	
	protected void setOrderCarriedOut(byte orderCarriedOut) {
		this.orderCarriedOut = orderCarriedOut;
	}
	
	protected double getTotalCost() {
		return totalCost;
	}
	
	protected void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	protected Customer getCustomer() {
		return customer;
	}
	
	protected void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	protected Set<Orderline> getOrderline(){
		return orderline;
	}
	
	protected void setOrderline(Set<Orderline> orderline) {
		this.orderline = orderline;
	}
}

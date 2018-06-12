package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_ORDER")
public class Order {
	private long orderId;
	private String date;
	private byte orderCarriedOut;
	private double totalCost;
	
	private Customer customer;
	
	private Set<Orderline> orderline;
	
	protected Order() {
	}
	
	@Id
	@Column(name = "OrderId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long getOrderId() {
		return orderId;
	}
	
	protected void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "Date")
	protected String getDate() {
		return date;
	}
	
	protected void setDate(String date) {
		this.date = date;
	}
	
	@Column(name = "OrderCarriedOut")
	protected byte getOrderCarriedOut() {
		return orderCarriedOut;
	}
	
	protected void setOrderCarriedOut(byte orderCarriedOut) {
		this.orderCarriedOut = orderCarriedOut;
	}
	
	@Column(name = "TotalCost")
	protected double getTotalCost() {
		return totalCost;
	}
	
	protected void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	@ManyToOne(cascade = CascadeType.ALL)
	protected Customer getCustomer() {
		return customer;
	}
	
	protected void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@OneToMany(mappedBy = "id.order")
	protected Set<Orderline> getOrderline(){
		return orderline;
	}
	
	protected void setOrderline(Set<Orderline> orderline) {
		this.orderline = orderline;
	}
}

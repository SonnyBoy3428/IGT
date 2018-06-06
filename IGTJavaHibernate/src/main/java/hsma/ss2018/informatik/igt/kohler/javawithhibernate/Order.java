package hsma.ss2018.informatik.igt.kohler.javawithhibernate;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_ORDER")
public class Order {
	private long orderId;
	private long customerId;
	
	public Order() {
	}
	
	@Id
	@Column(name = "OrderId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = )
	public float getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
}

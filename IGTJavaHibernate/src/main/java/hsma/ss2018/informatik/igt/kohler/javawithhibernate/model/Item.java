/**
 * This class functions as a map to the ITEM in the database.
 * Every Attribute is represented in the form of a member variable.
 * 
 * @author Dustin Noah Young
 */
package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "ITEM")
public class Item implements Serializable{
	@Id
	@Column(name = "ItemId", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long itemId;
	
	@Column(name = "ItemName", nullable = false)
	private String itemName;
	
	@Column(name = "Price", nullable = false)
	private double price;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderlineId.item", cascade = CascadeType.ALL)
	private Set<Orderline> orderline;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stockId.item", cascade = CascadeType.ALL)
	private Set<Stock> stock;
	
	public Item() {
	}
	
	public long getItemId() {
		return itemId;
	}
	
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public Set<Orderline> getOrderline(){
		return orderline;
	}
	
	public void setOrderline(Set<Orderline> orderline) {
		this.orderline = orderline;
	}
	
	public Set<Stock> getStock(){
		return stock;
	}
	
	public void setStock(Set<Stock> stock) {
		this.stock = stock;
	}
}

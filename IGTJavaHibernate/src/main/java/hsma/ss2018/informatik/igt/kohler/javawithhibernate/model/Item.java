/**
 * This class functions as a map to the ITEM in the database.
 * Every Attribute is represented in the form of a member variable.
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 */
package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "ITEM")
public class Item implements Serializable{
	private int itemId;
	private String itemName;
	private double price;
	private Set<Orderline> orderline;
	private Set<Stock> stock;
	
	public Item() {
	}
	
	@Id
	@Column(name = "ItemId", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getItemId() {
		return itemId;
	}
	
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	@Column(name = "ItemName", length = 50)
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@Column(name = "Price")
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderlineId.item", cascade = CascadeType.ALL)
	public Set<Orderline> getOrderline(){
		return orderline;
	}
	
	public void setOrderline(Set<Orderline> orderline) {
		this.orderline = orderline;
	}
	
	@OneToMany(mappedBy = "stockId.item", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<Stock> getStock(){
		return stock;
	}
	
	public void setStock(Set<Stock> stock) {
		this.stock = stock;
	}
}
package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "ITEM")
public class Item {
	private long itemId;
	private String itemName;
	
	private Set<Orderline> orderline;
	
	private Set<Stock> stock;
	
	public Item() {
	}
	
	@Id
	@Column(name = "ItemId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getItemId() {
		return itemId;
	}
	
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	
	@Column(name = "ItemName")
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@OneToMany(mappedBy = "id.item")
	public Set<Orderline> getOrderline(){
		return orderline;
	}
	
	public void setOrderline(Set<Orderline> orderline) {
		this.orderline = orderline;
	}
	
	@OneToMany(mappedBy = "item")
	public Set<Stock> getStock(){
		return stock;
	}
	
	public void setStock(Set<Stock> stock) {
		this.stock = stock;
	}
}

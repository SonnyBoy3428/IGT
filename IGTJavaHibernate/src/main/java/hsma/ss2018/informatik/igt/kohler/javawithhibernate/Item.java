package hsma.ss2018.informatik.igt.kohler.javawithhibernate;

import javax.persistence.*;

@Entity
@Table(name = "ITEM")
public class Item {
	private long itemId;
	private String itemName;
	
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
	
	@Column(name = )
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}

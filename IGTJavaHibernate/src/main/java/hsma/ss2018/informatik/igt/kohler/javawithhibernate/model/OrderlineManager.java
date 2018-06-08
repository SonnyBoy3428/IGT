package hsma.ss2018.informatik.igt.kohler.javawithhibernate.model;

import java.util.List;

public class OrderlineManager {
	public void createOrderline(long customerId, List<Long> itemIds) {
		String customer = CustomerManager.getCustomer(customerId);
	}
}

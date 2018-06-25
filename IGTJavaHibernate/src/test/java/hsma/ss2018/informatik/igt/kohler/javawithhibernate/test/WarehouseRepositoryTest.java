package hsma.ss2018.informatik.igt.kohler.javawithhibernate.test;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.EntityRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.WarehouseRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Warehouse;

public class WarehouseRepositoryTest {

	@Test
	public void testCreateWarehouse() {
		EntityRepository.setUp();
		
		Warehouse warehouse1 = new Warehouse();
		warehouse1.setWarehouseId(1);
		warehouse1.setLocation("Mannheim");
		warehouse1.setOwner("Joe Mama");
		
		Warehouse warehouse2 = new Warehouse();
		warehouse2.setWarehouseId(2);
		warehouse2.setLocation("Hockenheim");
		warehouse2.setOwner("Martin Müller");
		
		Warehouse warehouse3 = new Warehouse();
		warehouse3.setWarehouseId(3);
		warehouse3.setLocation("Seckenheim");
		warehouse3.setOwner("Tony Stark");
		
		Warehouse createdWarehouse1 = WarehouseRepository.createWarehouse(warehouse1.getLocation(), warehouse1.getOwner());
		Warehouse createdWarehouse2 = WarehouseRepository.createWarehouse(warehouse2.getLocation(), warehouse2.getOwner());
		Warehouse createdWarehouse3 = WarehouseRepository.createWarehouse(warehouse3.getLocation(), warehouse3.getOwner());
		
		Warehouse gottenWarehouse1 = WarehouseRepository.getWarehouse(createdWarehouse1.getWarehouseId());
		Warehouse gottenWarehouse2 = WarehouseRepository.getWarehouse(createdWarehouse2.getWarehouseId());
		Warehouse gottenWarehouse3 = WarehouseRepository.getWarehouse(createdWarehouse3.getWarehouseId());
		
		Set<Warehouse> allWarehouses = WarehouseRepository.getAllWarehouses();
		
		assertEquals("These warehouses are not equal!", warehouse1.getLocation(), createdWarehouse1.getLocation());
		assertEquals("These warehouses are not equal!", warehouse2.getLocation(), createdWarehouse2.getLocation());
		assertEquals("These warehouses are not equal!", warehouse3.getLocation(), createdWarehouse3.getLocation());
		
		assertEquals("These warehouses are not equal!", warehouse1.getLocation(), gottenWarehouse1.getLocation());
		assertEquals("These warehouses are not equal!", warehouse2.getLocation(), gottenWarehouse2.getLocation());
		assertEquals("These warehouses are not equal!", warehouse3.getLocation(), gottenWarehouse3.getLocation());
		
		assertEquals("The warehouse set does not have the correct size", 3, allWarehouses.size());
		
		boolean warehouse3Deleted = WarehouseRepository.deleteWarehouse(gottenWarehouse3.getWarehouseId());
		
		assertEquals("The warehouse was not deleted!", true, warehouse3Deleted);
		
		EntityRepository.exit();
	}
}

package hsma.ss2018.informatik.igt.kohler.javawithhibernate.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.CustomerRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.DistrictRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.EntityRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.ItemRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.OrderlineRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.StockRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.WarehouseRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.District;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Item;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Stock;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Warehouse;

public class GeneralRepositoryTest {

	@Test
	public void testCreateWarehouse() {
		EntityRepository.setUp();
		
		// WAREHOUSES
		Warehouse warehouse1 = new Warehouse();
		warehouse1.setLocation("Bergwald");
		warehouse1.setOwner("Fritz Falter");
		
		Warehouse warehouse2 = new Warehouse();
		warehouse2.setLocation("Echtwald");
		warehouse2.setOwner("Helga Hehl");
		
		Warehouse warehouse3 = new Warehouse();
		warehouse3.setLocation("Buntstadt");
		warehouse3.setOwner("Martin Max");
		
		Warehouse warehouse4 = new Warehouse();
		warehouse4.setLocation("Bildheim");
		warehouse4.setOwner("Hildegat Bier");
		
		// CREATE WAREHOUSE
        Warehouse createdWarehouse1 = WarehouseRepository.createWarehouse(warehouse1.getLocation(), warehouse1.getOwner());
        Warehouse createdWarehouse2 = WarehouseRepository.createWarehouse(warehouse2.getLocation(), warehouse2.getOwner());
        Warehouse createdWarehouse3 = WarehouseRepository.createWarehouse(warehouse3.getLocation(), warehouse3.getOwner());
        Warehouse createdWarehouse4 = WarehouseRepository.createWarehouse(warehouse4.getLocation(), warehouse4.getOwner());
        
        assertEquals("The warehouses are not equal!", warehouse1.getLocation(), createdWarehouse1.getLocation());
        assertEquals("The warehouses are not equal!", warehouse2.getLocation(), createdWarehouse2.getLocation());
        assertEquals("The warehouses are not equal!", warehouse3.getLocation(), createdWarehouse3.getLocation());
        assertEquals("The warehouses are not equal!", warehouse4.getLocation(), createdWarehouse4.getLocation());
        
        // GET WAREHOUSE
        Warehouse gottenWarehouse1 = WarehouseRepository.getWarehouse(createdWarehouse1.getWarehouseId());
        Warehouse gottenWarehouse2 = WarehouseRepository.getWarehouse(createdWarehouse2.getWarehouseId());
        Warehouse gottenWarehouse3 = WarehouseRepository.getWarehouse(createdWarehouse3.getWarehouseId());
        Warehouse gottenWarehouse4 = WarehouseRepository.getWarehouse(createdWarehouse4.getWarehouseId());
        
        assertEquals("The warehouses are not equal!", createdWarehouse1.getLocation(), gottenWarehouse1.getLocation());
        assertEquals("The warehouses are not equal!", createdWarehouse2.getLocation(), gottenWarehouse2.getLocation());
        assertEquals("The warehouses are not equal!", createdWarehouse3.getLocation(), gottenWarehouse3.getLocation());
        assertEquals("The warehouses are not equal!", createdWarehouse4.getLocation(), gottenWarehouse4.getLocation());
        
        // GET ALL WAREHOUSES
        Set<Warehouse> warehouses = WarehouseRepository.getAllWarehouses();
        
        assertEquals("Not all warehouses could be fetched!", 4, warehouses.size());
        
        // DELETE WARHOUSE
        int warehouseId = gottenWarehouse3.getWarehouseId();
        boolean warehouseDeleted = WarehouseRepository.deleteWarehouse(gottenWarehouse3.getWarehouseId());
        
        assertEquals("Warehouse could not be deleted!", true, warehouseDeleted);
        
        Warehouse gottenWarehouseAfterDeletion3 = WarehouseRepository.getWarehouse(warehouseId);
        
        assertEquals("Warehouse could not be deleted!", null, gottenWarehouseAfterDeletion3);
        
        // UPDATE WAREHOUSE
        Warehouse updatedWarehouse = WarehouseRepository.updateWarehouse(gottenWarehouse4.getWarehouseId(), "Wasserstadt", "Willy Wassermann");
        
        assertNotEquals("The warehouse was not updated!", warehouse4.getLocation(), updatedWarehouse.getLocation());
        assertEquals("The warehouse was not updated!", "Wasserstadt", updatedWarehouse.getLocation());
        
        Warehouse gottenWarehouseAfterUpdate4 = WarehouseRepository.getWarehouse(createdWarehouse4.getWarehouseId());
        
        assertEquals("The warehouse was not updated!", warehouse4.getLocation(), gottenWarehouseAfterUpdate4.getLocation());
        assertEquals("The warehouse was not updated!", "Wasserstadt", gottenWarehouseAfterUpdate4.getLocation());

        // DISTRICTS
        District district1 = new District();
        district1.setDistrictName("Tellerheim");
        district1.setDistrictSize(10.0);
        district1.setWarehouse(gottenWarehouse1);
        
        District district2 = new District();
        district2.setDistrictName("Gabelheim");
        district2.setDistrictSize(20.0);
        district2.setWarehouse(gottenWarehouse1);
        
        District district3 = new District();
        district3.setDistrictName("Messerheim");
        district3.setDistrictSize(30.0);
        district3.setWarehouse(gottenWarehouse1);
        
        District district4 = new District();
        district4.setDistrictName("Lichtheim");
        district4.setDistrictSize(40.0);
        district4.setWarehouse(gottenWarehouse1);
        
        District district5 = new District();
        district5.setDistrictName("Loeffelheim");
        district5.setDistrictSize(50.0);
        district5.setWarehouse(gottenWarehouse1);
        
        District district6 = new District();
        district6.setDistrictName("Tischheim");
        district6.setDistrictSize(60.0);
        district6.setWarehouse(gottenWarehouse2);
        
        District district7 = new District();
        district7.setDistrictName("Sesselheim");
        district7.setDistrictSize(70.0);
        district7.setWarehouse(gottenWarehouse2);
        
        District district8 = new District();
        district8.setDistrictName("Couchheim");
        district8.setDistrictSize(80.0);
        district8.setWarehouse(gottenWarehouse2);
        
        District district9 = new District();
        district9.setDistrictName("Kastenheim");
        district9.setDistrictSize(90.0);
        district9.setWarehouse(gottenWarehouse2);
        
        District district10 = new District();
        district10.setDistrictName("Papierheim");
        district10.setDistrictSize(100.0);
        district10.setWarehouse(gottenWarehouse2);
        
        District district11 = new District();
        district11.setDistrictName("Glockenheim");
        district11.setDistrictSize(110.0);
        district11.setWarehouse(gottenWarehouse3);
        
        District district12 = new District();
        district12.setDistrictName("Druckstadt");
        district12.setDistrictSize(120.0);
        district12.setWarehouse(gottenWarehouse3);
        
        // CREATE DISTRICT
        District createdDistrict1 = DistrictRepository.createDistrict(district1.getDistrictName(), district1.getDistrictSize(), gottenWarehouse1.getWarehouseId());
        District createdDistrict2 = DistrictRepository.createDistrict(district2.getDistrictName(), district2.getDistrictSize(), gottenWarehouse1.getWarehouseId());
        District createdDistrict3 = DistrictRepository.createDistrict(district3.getDistrictName(), district3.getDistrictSize(), gottenWarehouse1.getWarehouseId());
        District createdDistrict4 = DistrictRepository.createDistrict(district4.getDistrictName(), district4.getDistrictSize(), gottenWarehouse1.getWarehouseId());
        District createdDistrict5 = DistrictRepository.createDistrict(district5.getDistrictName(), district5.getDistrictSize(), gottenWarehouse1.getWarehouseId());
        District createdDistrict6 = DistrictRepository.createDistrict(district6.getDistrictName(), district6.getDistrictSize(), gottenWarehouse2.getWarehouseId());
        District createdDistrict7 = DistrictRepository.createDistrict(district7.getDistrictName(), district7.getDistrictSize(), gottenWarehouse2.getWarehouseId());
        District createdDistrict8 = DistrictRepository.createDistrict(district8.getDistrictName(), district8.getDistrictSize(), gottenWarehouse2.getWarehouseId());
        District createdDistrict9 = DistrictRepository.createDistrict(district9.getDistrictName(), district9.getDistrictSize(), gottenWarehouse2.getWarehouseId());
        District createdDistrict10 = DistrictRepository.createDistrict(district10.getDistrictName(), district10.getDistrictSize(), gottenWarehouse2.getWarehouseId());
        District createdDistrict11 = DistrictRepository.createDistrict(district11.getDistrictName(), district11.getDistrictSize(), gottenWarehouse3.getWarehouseId());
        District createdDistrict12 = DistrictRepository.createDistrict(district12.getDistrictName(), district12.getDistrictSize(), gottenWarehouse3.getWarehouseId());
        
        assertEquals("The districts are not equal!", district1.getDistrictName(), createdDistrict1.getDistrictName());
        assertEquals("The districts are not equal!", district2.getDistrictName(), createdDistrict2.getDistrictName());
        assertEquals("The districts are not equal!", district3.getDistrictName(), createdDistrict3.getDistrictName());
        assertEquals("The districts are not equal!", district4.getDistrictName(), createdDistrict4.getDistrictName());
        assertEquals("The districts are not equal!", district5.getDistrictName(), createdDistrict5.getDistrictName());
        assertEquals("The districts are not equal!", district6.getDistrictName(), createdDistrict6.getDistrictName());
        assertEquals("The districts are not equal!", district7.getDistrictName(), createdDistrict7.getDistrictName());
        assertEquals("The districts are not equal!", district8.getDistrictName(), createdDistrict8.getDistrictName());
        assertEquals("The districts are not equal!", district9.getDistrictName(), createdDistrict9.getDistrictName());
        assertEquals("The districts are not equal!", district10.getDistrictName(), createdDistrict10.getDistrictName());
        assertEquals("The districts are not equal!", district11.getDistrictName(), createdDistrict11.getDistrictName());
        assertEquals("The districts are not equal!", district12.getDistrictName(), createdDistrict12.getDistrictName());
        
        // GET DISTRICT
        District gottenDistrict1 = DistrictRepository.getDistrict(createdDistrict1.getDistrictId());
        District gottenDistrict2 = DistrictRepository.getDistrict(createdDistrict2.getDistrictId());
        District gottenDistrict3 = DistrictRepository.getDistrict(createdDistrict3.getDistrictId());
        District gottenDistrict4 = DistrictRepository.getDistrict(createdDistrict4.getDistrictId());
        District gottenDistrict5 = DistrictRepository.getDistrict(createdDistrict5.getDistrictId());
        District gottenDistrict6 = DistrictRepository.getDistrict(createdDistrict6.getDistrictId());
        District gottenDistrict7 = DistrictRepository.getDistrict(createdDistrict7.getDistrictId());
        District gottenDistrict8 = DistrictRepository.getDistrict(createdDistrict8.getDistrictId());
        District gottenDistrict9 = DistrictRepository.getDistrict(createdDistrict9.getDistrictId());
        District gottenDistrict10 = DistrictRepository.getDistrict(createdDistrict10.getDistrictId());
        District gottenDistrict11 = DistrictRepository.getDistrict(createdDistrict11.getDistrictId());
        District gottenDistrict12 = DistrictRepository.getDistrict(createdDistrict12.getDistrictId());
        
        assertEquals("The warehouses are not equal!", createdDistrict1.getDistrictName(), gottenDistrict1.getDistrictName());
        assertEquals("The warehouses are not equal!", createdDistrict2.getDistrictName(), gottenDistrict2.getDistrictName());
        assertEquals("The warehouses are not equal!", createdDistrict3.getDistrictName(), gottenDistrict3.getDistrictName());
        assertEquals("The warehouses are not equal!", createdDistrict4.getDistrictName(), gottenDistrict4.getDistrictName());
        assertEquals("The warehouses are not equal!", createdDistrict5.getDistrictName(), gottenDistrict5.getDistrictName());
        assertEquals("The warehouses are not equal!", createdDistrict6.getDistrictName(), gottenDistrict6.getDistrictName());
        assertEquals("The warehouses are not equal!", createdDistrict7.getDistrictName(), gottenDistrict7.getDistrictName());
        assertEquals("The warehouses are not equal!", createdDistrict8.getDistrictName(), gottenDistrict8.getDistrictName());
        assertEquals("The warehouses are not equal!", createdDistrict9.getDistrictName(), gottenDistrict9.getDistrictName());
        assertEquals("The warehouses are not equal!", createdDistrict10.getDistrictName(), gottenDistrict10.getDistrictName());
        assertEquals("The warehouses are not equal!", createdDistrict11.getDistrictName(), gottenDistrict11.getDistrictName());
        assertEquals("The warehouses are not equal!", createdDistrict12.getDistrictName(), gottenDistrict12.getDistrictName());
        
        // GET ALL DISTRICTS
        Set<District> districts = DistrictRepository.getAllDistricts();
        
        assertEquals("Not all districts could be fetched!", 12, districts.size());
        
        // DELETE DISTRICT
        int districtId = gottenDistrict11.getDistrictId();
        boolean districtDeleted = DistrictRepository.deleteDistrict(gottenDistrict11.getDistrictId());
        
        assertEquals("District could not be deleted!", true, districtDeleted);
        
        District gottenDistrictAfterDeletion11 = DistrictRepository.getDistrict(districtId);
        
        assertEquals("District could not be deleted!", null, gottenDistrictAfterDeletion11);
        
        // UPDATE DISTRICT
        District updatedDistrict = DistrictRepository.updateDistrict(gottenDistrict12.getDistrictId(), "Tintenstadt", 130.0, 1);
        
        assertNotEquals("The district was not updated!", district12.getDistrictName(), updatedDistrict.getDistrictName());
        assertEquals("The district was not updated!", "", updatedDistrict.getDistrictName());
        
        District gottenDistrictAfterUpdate12 = DistrictRepository.getDistrict(createdDistrict12.getDistrictId());
        
        assertEquals("The district was not updated!", district12.getDistrictName(), gottenDistrictAfterUpdate12.getDistrictName());
        assertEquals("The district was not updated!", "Tintenstadt", gottenDistrictAfterUpdate12.getDistrictName());
        
        // GET ALL DISTRICTS FOR WAREHOUSES
        Set<District> gottenDistrictsWarehouse1 = WarehouseRepository.getWarehouseDistricts(gottenWarehouse1.getWarehouseId());
        Set<District> gottenDistrictsWarehouse2 = WarehouseRepository.getWarehouseDistricts(gottenWarehouse2.getWarehouseId());
        Set<District> gottenDistrictsWarehouse3 = WarehouseRepository.getWarehouseDistricts(gottenWarehouse3.getWarehouseId());
        
        assertEquals("Not all disrticts are in the set!", 5, gottenDistrictsWarehouse1.size());
        assertEquals("District not in the set!", true, gottenDistrictsWarehouse1.contains(gottenDistrict1));
        assertEquals("District not in the set!", true, gottenDistrictsWarehouse1.contains(gottenDistrict2));
        assertEquals("District not in the set!", true, gottenDistrictsWarehouse1.contains(gottenDistrict3));
        assertEquals("District not in the set!", true, gottenDistrictsWarehouse1.contains(gottenDistrict4));
        assertEquals("District not in the set!", true, gottenDistrictsWarehouse1.contains(gottenDistrict5));
        assertEquals("Wrong district in the set!", false, gottenDistrictsWarehouse1.contains(gottenDistrict6));
        assertEquals("Not all disrticts are in the set!", 6, gottenDistrictsWarehouse2.size());
        assertEquals("District not in the set!", true, gottenDistrictsWarehouse1.contains(gottenDistrict6));
        assertEquals("District not in the set!", true, gottenDistrictsWarehouse1.contains(gottenDistrict7));
        assertEquals("District not in the set!", true, gottenDistrictsWarehouse1.contains(gottenDistrict8));
        assertEquals("District not in the set!", true, gottenDistrictsWarehouse1.contains(gottenDistrict9));
        assertEquals("District not in the set!", true, gottenDistrictsWarehouse1.contains(gottenDistrict10));
        assertEquals("Wrong district in the set!", false, gottenDistrictsWarehouse1.contains(gottenDistrict1));
        assertEquals("Not all disrticts are in the set!", 1, gottenDistrictsWarehouse3.size());
        assertEquals("District not in the set!", true, gottenDistrictsWarehouse3.contains(gottenDistrict12));
        assertEquals("District not in the set!", false, gottenDistrictsWarehouse3.contains(gottenDistrict7));
        
        // ITEMS 
        Item item1 = new Item();
        item1.setItemName("Monitor");
        item1.setPrice(10.0);
        
        Item item2 = new Item();
        item2.setItemName("Mouse");
        item2.setPrice(20.0);
        
        Item item3 = new Item();
        item3.setItemName("Keyboard");
        item3.setPrice(30.0);
        
        Item item4 = new Item();
        item4.setItemName("Speaker");
        item4.setPrice(40.0);
        
        Item item5 = new Item();
        item5.setItemName("Headset");
        item5.setPrice(50.0);
        
        // CREATE ITEM 
        Item createdItem1 = ItemRepository.createItem(item1.getItemName(), item1.getPrice());
        Item createdItem2 = ItemRepository.createItem(item2.getItemName(), item2.getPrice());
        Item createdItem3 = ItemRepository.createItem(item3.getItemName(), item3.getPrice());
        Item createdItem4 = ItemRepository.createItem(item4.getItemName(), item4.getPrice());
        Item createdItem5 = ItemRepository.createItem(item5.getItemName(), item5.getPrice());
        
        assertEquals("The items are not equal!", item1.getItemName(), createdItem1.getItemName());
        assertEquals("The items are not equal!", item2.getItemName(), createdItem2.getItemName());
        assertEquals("The items are not equal!", item3.getItemName(), createdItem3.getItemName());
        assertEquals("The items are not equal!", item4.getItemName(), createdItem4.getItemName());
        assertEquals("The items are not equal!", item5.getItemName(), createdItem5.getItemName());
        
        // GET ITEM
        Item gottenItem1 = ItemRepository.getItem(createdItem1.getItemId());
        Item gottenItem2 = ItemRepository.getItem(createdItem2.getItemId());
        Item gottenItem3 = ItemRepository.getItem(createdItem3.getItemId());
        Item gottenItem4 = ItemRepository.getItem(createdItem4.getItemId());
        Item gottenItem5 = ItemRepository.getItem(createdItem5.getItemId());
        
        assertEquals("The items are not equal!", createdItem1.getItemName(), gottenItem1.getItemName());
        assertEquals("The items are not equal!", createdItem2.getItemName(), gottenItem2.getItemName());
        assertEquals("The items are not equal!", createdItem3.getItemName(), gottenItem3.getItemName());
        assertEquals("The items are not equal!", createdItem4.getItemName(), gottenItem4.getItemName());
        assertEquals("The items are not equal!", createdItem5.getItemName(), gottenItem5.getItemName());
        
        // GET ALL ITEMS
        Set<Item> items = ItemRepository.getAllItems();
        
        assertEquals("Not all items could be fetched", 5, items.size());
        
        // DELETE ITEM
        int itemId = gottenItem4.getItemId();
        boolean itemDeleted = ItemRepository.deleteItem(gottenItem4.getItemId());
        
        assertEquals("Item could not be deleted!", true, itemDeleted);
        
        Item gottenItemAfterDeletion4 = ItemRepository.getItem(itemId);
        
        assertEquals("Item could not be deleted!", null, gottenItemAfterDeletion4);
        
        // UPDATE ITEM
        Item updatedItem = ItemRepository.updateItem(gottenItem5.getItemId(), "Headset", 60.0);
        
        assertNotEquals("The item was not updated!", item5.getItemName(), updatedItem.getItemName());
        assertEquals("The item was not updated!", "Headset", updatedItem.getItemName());
        
        Item gottenItemAfterUpdate5 = ItemRepository.getItem(createdItem5.getItemId());
        
        assertEquals("The item was not updated!", item5.getItemName(), gottenItemAfterUpdate5.getItemName());
        assertEquals("The item was not updated!", "Headset", gottenItemAfterUpdate5.getItemName());
        
        // STOCK
        Stock stock1 = new Stock();
        stock1.setWarehouse(gottenWarehouse1);
        stock1.setItem(gottenItem1);
        stock1.setQuantity(0);
        
        Stock stock2 = new Stock();
        stock2.setWarehouse(gottenWarehouse1);
        stock2.setItem(gottenItem2);
        stock2.setQuantity(5);
        
        Stock stock3 = new Stock();
        stock3.setWarehouse(gottenWarehouse1);
        stock3.setItem(gottenItem3);
        stock3.setQuantity(10);
        
        Stock stock4 = new Stock();
        stock4.setWarehouse(gottenWarehouse1);
        stock4.setItem(gottenItem5);
        stock4.setQuantity(15);
        
        Stock stock5 = new Stock();
        stock5.setWarehouse(gottenWarehouse2);
        stock5.setItem(gottenItem1);
        stock5.setQuantity(0);
        
        Stock stock6 = new Stock();
        stock6.setWarehouse(gottenWarehouse2);
        stock6.setItem(gottenItem2);
        stock6.setQuantity(5);
        
        Stock stock7 = new Stock();
        stock7.setWarehouse(gottenWarehouse2);
        stock7.setItem(gottenItem3);
        stock7.setQuantity(10);
        
        Stock stock8 = new Stock();
        stock8.setWarehouse(gottenWarehouse2);
        stock8.setItem(gottenItem5);
        stock8.setQuantity(15);
        
        Stock stock9 = new Stock();
        stock9.setWarehouse(gottenWarehouse3);
        stock9.setItem(gottenItem1);
        stock9.setQuantity(0);
        
        Stock stock10 = new Stock();
        stock10.setWarehouse(gottenWarehouse3);
        stock10.setItem(gottenItem2);
        stock10.setQuantity(5);
        
        Stock stock11 = new Stock();
        stock11.setWarehouse(gottenWarehouse3);
        stock11.setItem(gottenItem3);
        stock11.setQuantity(10);
        
        Stock stock12 = new Stock();
        stock12.setWarehouse(gottenWarehouse3);
        stock12.setItem(gottenItem5);
        stock12.setQuantity(15);
        
        // CREATE STOCK
        StockRepository.createStock(gottenWarehouse1.getWarehouseId(), gottenItem1.getItemId(), stock1.getQuantity());
        StockRepository.createStock(gottenWarehouse1.getWarehouseId(), gottenItem2.getItemId(), stock2.getQuantity());
        StockRepository.createStock(gottenWarehouse1.getWarehouseId(), gottenItem3.getItemId(), stock3.getQuantity());
        Warehouse createdWarehouseStock1 = StockRepository.createStock(gottenWarehouse1.getWarehouseId(), gottenItem5.getItemId(), stock4.getQuantity());
        StockRepository.createStock(gottenWarehouse2.getWarehouseId(), gottenItem1.getItemId(), stock5.getQuantity());
        StockRepository.createStock(gottenWarehouse2.getWarehouseId(), gottenItem2.getItemId(), stock6.getQuantity());
        StockRepository.createStock(gottenWarehouse2.getWarehouseId(), gottenItem3.getItemId(), stock7.getQuantity());
        Warehouse createdWarehouseStock2 = StockRepository.createStock(gottenWarehouse2.getWarehouseId(), gottenItem5.getItemId(), stock8.getQuantity());
        StockRepository.createStock(gottenWarehouse3.getWarehouseId(), gottenItem1.getItemId(), stock9.getQuantity());
        StockRepository.createStock(gottenWarehouse3.getWarehouseId(), gottenItem2.getItemId(), stock10.getQuantity());
        StockRepository.createStock(gottenWarehouse3.getWarehouseId(), gottenItem3.getItemId(), stock11.getQuantity());
        Warehouse createdWarehouseStock3 = StockRepository.createStock(gottenWarehouse3.getWarehouseId(), gottenItem5.getItemId(), stock12.getQuantity());
        
        assertEquals("The stocks are not equal!", 4, createdWarehouseStock1.getStock().size());
        assertEquals("The stocks are not equal!", 4, createdWarehouseStock2.getStock().size());
        assertEquals("The stocks are not equal!", 4, createdWarehouseStock3.getStock().size());
        
        // GET STOCKS
        Set<Stock> stocks1 = StockRepository.getStocks(gottenWarehouse1.getWarehouseId());
        Set<Stock> stocks2 = StockRepository.getStocks(gottenWarehouse2.getWarehouseId());
        Set<Stock> stocks3 = StockRepository.getStocks(gottenWarehouse3.getWarehouseId());
        
        assertEquals("The stocks are not equal!", 4, stocks1.size());
        assertEquals("The stocks are not equal!", 4, stocks2.size());
        assertEquals("The stocks are not equal!", 4, stocks3.size());
        
        //UPDATE STOCKS
        Warehouse updatedWarehouseStockAfterDeletion = StockRepository.updateStock(gottenWarehouse3.getWarehouseId(), gottenItem1.getItemId(), "Delete", 0);
        assertEquals("The stocks are not updated!", 3, updatedWarehouseStockAfterDeletion.getStock().size());
        
        Warehouse updatedWarehouseStockAfterInsertion = StockRepository.updateStock(gottenWarehouse3.getWarehouseId(), gottenItem1.getItemId(), "Insert", 0);
        assertEquals("The stocks are not updated!", 4, updatedWarehouseStockAfterInsertion.getStock().size());
        
        Warehouse updatedWarehouseStockAfterUpdate = StockRepository.updateStock(gottenWarehouse3.getWarehouseId(), gottenItem1.getItemId(), "Update", 20);
        assertEquals("The stocks are not updated!", true, updatedWarehouseStockAfterUpdate.getStock().contains(gottenItem1));
        
        // DELETE STOCKS
        boolean deletedStockOfWarehouse3 = StockRepository.deleteStock(gottenWarehouse3.getWarehouseId());
        
        assertEquals("Stock of warehouse was not deleted!", true, deletedStockOfWarehouse3);

        // CUSTOMERS
        Customer customer1 = new Customer();
        customer1.setFirstName("Emil");
        customer1.setLastName("Estermann");
        customer1.setAddress("Lessingstrasse 1");
        customer1.setTelephone("0621 34562635");
        customer1.setCreditCardNr("123456789123");
        customer1.setDistrict(gottenDistrict1);
        
        Customer customer2 = new Customer();
        customer2.setFirstName("Tina");
        customer2.setLastName("Tinker");
        customer2.setAddress("Bullenstrasse 2");
        customer2.setTelephone("0621 34562636");
        customer2.setCreditCardNr("123456789124");
        customer2.setDistrict(gottenDistrict1);
        
        Customer customer3 = new Customer();
        customer3.setFirstName("Franz");
        customer3.setLastName("Trulla");
        customer3.setAddress("Reisstrasse 5");
        customer3.setTelephone("0621 34562637");
        customer3.setCreditCardNr("123456789125");
        customer3.setDistrict(gottenDistrict1);
        
        Customer customer4 = new Customer();
        customer4.setFirstName("Jessica");
        customer4.setLastName("Blau");
        customer4.setAddress("Bildstrasse 5");
        customer4.setTelephone("0621 34562638");
        customer4.setCreditCardNr("123456789126");
        customer4.setDistrict(gottenDistrict1);
        
        Customer customer5 = new Customer();
        customer5.setFirstName("Tim");
        customer5.setLastName("Rot");
        customer5.setAddress("Kamerastrasse 5");
        customer5.setTelephone("0621 34562639");
        customer5.setCreditCardNr("123456789127");
        customer5.setDistrict(gottenDistrict1);
        
        Customer customer6 = new Customer();
        customer6.setFirstName("Eva");
        customer6.setLastName("Gelb");
        customer6.setAddress("Lichtstrasse 5");
        customer6.setTelephone("0621 34562640");
        customer6.setCreditCardNr("123456789128");
        customer6.setDistrict(gottenDistrict2);
        
        Customer customer7 = new Customer();
        customer7.setFirstName("Alex");
        customer7.setLastName("Orange");
        customer7.setAddress("Schalterstrasse 5");
        customer7.setTelephone("0621 34562641");
        customer7.setCreditCardNr("123456789129");
        customer7.setDistrict(gottenDistrict2);
        
        Customer customer8 = new Customer();
        customer8.setFirstName("Lara");
        customer8.setLastName("Lila");
        customer8.setAddress("Meterstrasse 5");
        customer8.setTelephone("0621 34562642");
        customer8.setCreditCardNr("123456789130");
        customer8.setDistrict(gottenDistrict2);
        
        Customer customer9 = new Customer();
        customer9.setFirstName("Felix");
        customer9.setLastName("Magenta");
        customer9.setAddress("Kaltstrasse 5");
        customer9.setTelephone("0621 34562643");
        customer9.setCreditCardNr("123456789131");
        customer9.setDistrict(gottenDistrict2);
        
        Customer customer10 = new Customer();
        customer10.setFirstName("Katrin");
        customer10.setLastName("Cyan");
        customer10.setAddress("Warmstrasse 5");
        customer10.setTelephone("0621 34562644");
        customer10.setCreditCardNr("123456789132");
        customer10.setDistrict(gottenDistrict2);
        
        Customer customer11 = new Customer();
        customer10.setFirstName("Helena");
        customer10.setLastName("Rechteck");
        customer10.setAddress("Boxweg 5");
        customer10.setTelephone("0621 124556");
        customer10.setCreditCardNr("876543219101");
        customer10.setDistrict(gottenDistrict3);
        
        Customer customer12 = new Customer();
        customer10.setFirstName("Berta");
        customer10.setLastName("Kreis");
        customer10.setAddress("Tuckweg 5");
        customer10.setTelephone("0621 112314");
        customer10.setCreditCardNr("987654321101");
        customer10.setDistrict(gottenDistrict3);
        
        // CREATE CUSTOMER
        Customer createdCustomer1 = CustomerRepository.createCustomer(customer1.getFirstName(), customer1.getLastName(), customer1.getAddress(), customer1.getTelephone(), customer1.getCreditCardNr(), customer1.getDistrict().getDistrictId());
        Customer createdCustomer2 = CustomerRepository.createCustomer(customer2.getFirstName(), customer2.getLastName(), customer2.getAddress(), customer2.getTelephone(), customer2.getCreditCardNr(), customer2.getDistrict().getDistrictId());
        Customer createdCustomer3 = CustomerRepository.createCustomer(customer3.getFirstName(), customer3.getLastName(), customer3.getAddress(), customer3.getTelephone(), customer3.getCreditCardNr(), customer3.getDistrict().getDistrictId());
        Customer createdCustomer4 = CustomerRepository.createCustomer(customer4.getFirstName(), customer4.getLastName(), customer4.getAddress(), customer4.getTelephone(), customer4.getCreditCardNr(), customer4.getDistrict().getDistrictId());
        Customer createdCustomer5 = CustomerRepository.createCustomer(customer5.getFirstName(), customer5.getLastName(), customer5.getAddress(), customer5.getTelephone(), customer5.getCreditCardNr(), customer5.getDistrict().getDistrictId());
        Customer createdCustomer6 = CustomerRepository.createCustomer(customer6.getFirstName(), customer6.getLastName(), customer6.getAddress(), customer6.getTelephone(), customer6.getCreditCardNr(), customer6.getDistrict().getDistrictId());
        Customer createdCustomer7 = CustomerRepository.createCustomer(customer7.getFirstName(), customer7.getLastName(), customer7.getAddress(), customer7.getTelephone(), customer7.getCreditCardNr(), customer7.getDistrict().getDistrictId());
        Customer createdCustomer8 = CustomerRepository.createCustomer(customer8.getFirstName(), customer8.getLastName(), customer8.getAddress(), customer8.getTelephone(), customer8.getCreditCardNr(), customer8.getDistrict().getDistrictId());
        Customer createdCustomer9 = CustomerRepository.createCustomer(customer9.getFirstName(), customer9.getLastName(), customer9.getAddress(), customer9.getTelephone(), customer9.getCreditCardNr(), customer9.getDistrict().getDistrictId());
        Customer createdCustomer10 = CustomerRepository.createCustomer(customer10.getFirstName(), customer10.getLastName(), customer10.getAddress(), customer10.getTelephone(), customer10.getCreditCardNr(), customer10.getDistrict().getDistrictId());
        Customer createdCustomer11 = CustomerRepository.createCustomer(customer11.getFirstName(), customer11.getLastName(), customer11.getAddress(), customer11.getTelephone(), customer11.getCreditCardNr(), customer11.getDistrict().getDistrictId());
        Customer createdCustomer12 = CustomerRepository.createCustomer(customer12.getFirstName(), customer12.getLastName(), customer12.getAddress(), customer12.getTelephone(), customer12.getCreditCardNr(), customer12.getDistrict().getDistrictId());
        
        assertEquals("The customers are not equal!", customer1.getFirstName(), createdCustomer1.getFirstName());
        assertEquals("The customers are not equal!", customer2.getFirstName(), createdCustomer2.getFirstName());
        assertEquals("The customers are not equal!", customer3.getFirstName(), createdCustomer3.getFirstName());
        assertEquals("The customers are not equal!", customer4.getFirstName(), createdCustomer4.getFirstName());
        assertEquals("The customers are not equal!", customer5.getFirstName(), createdCustomer5.getFirstName());
        assertEquals("The customers are not equal!", customer6.getFirstName(), createdCustomer6.getFirstName());
        assertEquals("The customers are not equal!", customer7.getFirstName(), createdCustomer7.getFirstName());
        assertEquals("The customers are not equal!", customer8.getFirstName(), createdCustomer8.getFirstName());
        assertEquals("The customers are not equal!", customer9.getFirstName(), createdCustomer9.getFirstName());
        assertEquals("The customers are not equal!", customer10.getFirstName(), createdCustomer10.getFirstName());
        assertEquals("The customers are not equal!", customer11.getFirstName(), createdCustomer11.getFirstName());
        assertEquals("The customers are not equal!", customer12.getFirstName(), createdCustomer12.getFirstName());
        
        //GET CUSTOMER
        Customer gottenCustomer1 = CustomerRepository.getCustomer(createdCustomer1.getCustomerId());
        Customer gottenCustomer2 = CustomerRepository.getCustomer(createdCustomer2.getCustomerId());
        Customer gottenCustomer3 = CustomerRepository.getCustomer(createdCustomer3.getCustomerId());
        Customer gottenCustomer4 = CustomerRepository.getCustomer(createdCustomer4.getCustomerId());
        Customer gottenCustomer5 = CustomerRepository.getCustomer(createdCustomer5.getCustomerId());
        Customer gottenCustomer6 = CustomerRepository.getCustomer(createdCustomer6.getCustomerId());
        Customer gottenCustomer7 = CustomerRepository.getCustomer(createdCustomer7.getCustomerId());
        Customer gottenCustomer8 = CustomerRepository.getCustomer(createdCustomer8.getCustomerId());
        Customer gottenCustomer9 = CustomerRepository.getCustomer(createdCustomer9.getCustomerId());
        Customer gottenCustomer10 = CustomerRepository.getCustomer(createdCustomer10.getCustomerId());
        Customer gottenCustomer11 = CustomerRepository.getCustomer(createdCustomer11.getCustomerId());
        Customer gottenCustomer12 = CustomerRepository.getCustomer(createdCustomer12.getCustomerId());
        
        assertEquals("The customers are not equal!", createdCustomer1.getFirstName(), gottenCustomer1.getFirstName());
        assertEquals("The customers are not equal!", createdCustomer2.getFirstName(), gottenCustomer2.getFirstName());
        assertEquals("The customers are not equal!", createdCustomer3.getFirstName(), gottenCustomer3.getFirstName());
        assertEquals("The customers are not equal!", createdCustomer4.getFirstName(), gottenCustomer4.getFirstName());
        assertEquals("The customers are not equal!", createdCustomer5.getFirstName(), gottenCustomer5.getFirstName());
        assertEquals("The customers are not equal!", createdCustomer6.getFirstName(), gottenCustomer6.getFirstName());
        assertEquals("The customers are not equal!", createdCustomer7.getFirstName(), gottenCustomer7.getFirstName());
        assertEquals("The customers are not equal!", createdCustomer8.getFirstName(), gottenCustomer8.getFirstName());
        assertEquals("The customers are not equal!", createdCustomer9.getFirstName(), gottenCustomer9.getFirstName());
        assertEquals("The customers are not equal!", createdCustomer10.getFirstName(), gottenCustomer10.getFirstName());
        assertEquals("The customers are not equal!", createdCustomer11.getFirstName(), gottenCustomer11.getFirstName());
        assertEquals("The customers are not equal!", createdCustomer12.getFirstName(), gottenCustomer12.getFirstName());
        
        // GET ALL CUSTOMERS
        Set<Customer> customers = CustomerRepository.getAllCustomers();
        
        assertEquals("Not all customers could be fetched", 12, customers.size());
        
        // DELETE CUSTOMER
        int customerId = gottenCustomer11.getCustomerId();
        boolean customerDeleted = CustomerRepository.deleteCustomer(gottenCustomer11.getCustomerId());
        
        assertEquals("Customer could not be deleted!", true, customerDeleted);
        
        Customer gottenCustomerAfterDeletion11 = CustomerRepository.getCustomer(customerId);
        
        assertEquals("Customer could not be deleted!", null, gottenCustomerAfterDeletion11);
        
        // UPDATE C
        Customer updatedCustomer = CustomerRepository.updateCustomer(gottenCustomer12.getCustomerId(), "Bert";
        
        assertNotEquals("The item was not updated!", item5.getItemName(), updatedItem.getItemName());
        assertEquals("The item was not updated!", "Headset", updatedItem.getItemName());
        
        Customer gottenCustomerAfterUpdate5 = ItemRepository.getItem(createdItem5.getItemId());
        
        assertEquals("The item was not updated!", item5.getItemName(), gottenItemAfterUpdate5.getItemName());
        assertEquals("The item was not updated!", "Headset", gottenItemAfterUpdate5.getItemName());
        
        Map<Integer, Integer> CustomerItemMap1 = new HashMap<Integer, Integer>(gottenItem1.getItemId(), 3);
        Map<Integer, Integer> CustomerItemMap2 = new HashMap<Integer, Integer>(gottenItem2.getItemId(), 1);
        Map<Integer, Integer> CustomerItemMap3 = new HashMap<Integer, Integer>(gottenItem3.getItemId(), 2);
        
        // CREATE ORDER LINES
        OrderlineRepository.createOrderline(1, map1);
        OrderlineRepository.createOrderline(1, map2);
        OrderlineRepository.createOrderline(1, map3);
        OrderlineRepository.createOrderline(2, map1);
        OrderlineRepository.createOrderline(2, map2);
        OrderlineRepository.createOrderline(2, map3);
        OrderlineRepository.createOrderline(3, map1);
        OrderlineRepository.createOrderline(3, map2);
        OrderlineRepository.createOrderline(3, map3);
        OrderlineRepository.createOrderline(4, map1);
        OrderlineRepository.createOrderline(4, map2);
        OrderlineRepository.createOrderline(4, map3);
        OrderlineRepository.createOrderline(5, map1);
        OrderlineRepository.createOrderline(5, map2);
        OrderlineRepository.createOrderline(5, map3);
        OrderlineRepository.createOrderline(6, map1);
        OrderlineRepository.createOrderline(6, map2);
        OrderlineRepository.createOrderline(6, map3);
        OrderlineRepository.createOrderline(7, map1);
        OrderlineRepository.createOrderline(7, map2);
        OrderlineRepository.createOrderline(7, map3);
        OrderlineRepository.createOrderline(8, map1);
        OrderlineRepository.createOrderline(8, map2);
        OrderlineRepository.createOrderline(8, map3);
        OrderlineRepository.createOrderline(9, map1);
        OrderlineRepository.createOrderline(9, map2);
        OrderlineRepository.createOrderline(9, map3);
        OrderlineRepository.createOrderline(10, map1);
        OrderlineRepository.createOrderline(10, map2);
        OrderlineRepository.createOrderline(10, map3);
        OrderlineRepository.createOrderline(11, map1);
        OrderlineRepository.createOrderline(11, map2);
        OrderlineRepository.createOrderline(11, map3);
        OrderlineRepository.createOrderline(12, map1);
        OrderlineRepository.createOrderline(12, map2);
        OrderlineRepository.createOrderline(12, map3);
        OrderlineRepository.createOrderline(13, map1);
        OrderlineRepository.createOrderline(13, map2);
        OrderlineRepository.createOrderline(13, map3);
        OrderlineRepository.createOrderline(14, map1);
        OrderlineRepository.createOrderline(14, map2);
        OrderlineRepository.createOrderline(14, map3);
        OrderlineRepository.createOrderline(15, map1);
        OrderlineRepository.createOrderline(15, map2);
        OrderlineRepository.createOrderline(15, map3);
        OrderlineRepository.createOrderline(16, map1);
        OrderlineRepository.createOrderline(16, map2);
        OrderlineRepository.createOrderline(16, map3);
        OrderlineRepository.createOrderline(17, map1);
        OrderlineRepository.createOrderline(17, map2);
        OrderlineRepository.createOrderline(17, map3);
        OrderlineRepository.createOrderline(18, map1);
        OrderlineRepository.createOrderline(18, map2);
        OrderlineRepository.createOrderline(18, map3);
        OrderlineRepository.createOrderline(19, map1);
        OrderlineRepository.createOrderline(19, map2);
        OrderlineRepository.createOrderline(19, map3);
        OrderlineRepository.createOrderline(20, map1);
        OrderlineRepository.createOrderline(20, map2);
        OrderlineRepository.createOrderline(20, map3);
        OrderlineRepository.createOrderline(21, map1);
        OrderlineRepository.createOrderline(21, map2);
        OrderlineRepository.createOrderline(21, map3);
        OrderlineRepository.createOrderline(22, map1);
        OrderlineRepository.createOrderline(22, map2);
        OrderlineRepository.createOrderline(22, map3);
        OrderlineRepository.createOrderline(23, map1);
        OrderlineRepository.createOrderline(23, map2);
        OrderlineRepository.createOrderline(23, map3);
        OrderlineRepository.createOrderline(24, map1);
        OrderlineRepository.createOrderline(24, map2);
        OrderlineRepository.createOrderline(24, map3);
        OrderlineRepository.createOrderline(25, map1);
        OrderlineRepository.createOrderline(25, map2);
        OrderlineRepository.createOrderline(25, map3);
        OrderlineRepository.createOrderline(26, map1);
        OrderlineRepository.createOrderline(26, map2);
        OrderlineRepository.createOrderline(26, map3);
        OrderlineRepository.createOrderline(27, map1);
        OrderlineRepository.createOrderline(27, map2);
        OrderlineRepository.createOrderline(27, map3);
        OrderlineRepository.createOrderline(28, map1);
        OrderlineRepository.createOrderline(28, map2);
        OrderlineRepository.createOrderline(28, map3);
        OrderlineRepository.createOrderline(29, map1);
        OrderlineRepository.createOrderline(29, map2);
        OrderlineRepository.createOrderline(29, map3);
        OrderlineRepository.createOrderline(30, map1);
        OrderlineRepository.createOrderline(30, map2);
        OrderlineRepository.createOrderline(30, map3);
        OrderlineRepository.createOrderline(31, map1);
        OrderlineRepository.createOrderline(31, map2);
        OrderlineRepository.createOrderline(31, map3);
        OrderlineRepository.createOrderline(32, map1);
        OrderlineRepository.createOrderline(32, map2);
        OrderlineRepository.createOrderline(32, map3);
        OrderlineRepository.createOrderline(33, map1);
        OrderlineRepository.createOrderline(33, map2);
        OrderlineRepository.createOrderline(33, map3);
        OrderlineRepository.createOrderline(34, map1);
        OrderlineRepository.createOrderline(34, map2);
        OrderlineRepository.createOrderline(34, map3);
        OrderlineRepository.createOrderline(35, map1);
        OrderlineRepository.createOrderline(35, map2);
        OrderlineRepository.createOrderline(35, map3);
        OrderlineRepository.createOrderline(36, map1);
        OrderlineRepository.createOrderline(36, map2);
        OrderlineRepository.createOrderline(36, map3);
        OrderlineRepository.createOrderline(37, map1);
        OrderlineRepository.createOrderline(37, map2);
        OrderlineRepository.createOrderline(37, map3);
        OrderlineRepository.createOrderline(38, map1);
        OrderlineRepository.createOrderline(38, map2);
        OrderlineRepository.createOrderline(38, map3);
        OrderlineRepository.createOrderline(39, map1);
        OrderlineRepository.createOrderline(39, map2);
        OrderlineRepository.createOrderline(39, map3);
        OrderlineRepository.createOrderline(40, map1);
        OrderlineRepository.createOrderline(40, map2);
        OrderlineRepository.createOrderline(40, map3);
        OrderlineRepository.createOrderline(41, map1);
        OrderlineRepository.createOrderline(41, map2);
        OrderlineRepository.createOrderline(41, map3);
        OrderlineRepository.createOrderline(42, map1);
        OrderlineRepository.createOrderline(42, map2);
        OrderlineRepository.createOrderline(42, map3);
        OrderlineRepository.createOrderline(43, map1);
        OrderlineRepository.createOrderline(43, map2);
        OrderlineRepository.createOrderline(43, map3);
        OrderlineRepository.createOrderline(44, map1);
        OrderlineRepository.createOrderline(44, map2);
        OrderlineRepository.createOrderline(44, map3);
        OrderlineRepository.createOrderline(45, map1);
        OrderlineRepository.createOrderline(45, map2);
        OrderlineRepository.createOrderline(45, map3);
        OrderlineRepository.createOrderline(46, map1);
        OrderlineRepository.createOrderline(46, map2);
        OrderlineRepository.createOrderline(46, map3);
        OrderlineRepository.createOrderline(47, map1);
        OrderlineRepository.createOrderline(47, map2);
        OrderlineRepository.createOrderline(47, map3);
        OrderlineRepository.createOrderline(48, map1);
        OrderlineRepository.createOrderline(48, map2);
        OrderlineRepository.createOrderline(48, map3);
        OrderlineRepository.createOrderline(49, map1);
        OrderlineRepository.createOrderline(49, map2);
        OrderlineRepository.createOrderline(49, map3);
        OrderlineRepository.createOrderline(50, map1);
        OrderlineRepository.createOrderline(50, map2);
        OrderlineRepository.createOrderline(50, map3);
        OrderlineRepository.createOrderline(51, map1);
        OrderlineRepository.createOrderline(51, map2);
        OrderlineRepository.createOrderline(51, map3);
        OrderlineRepository.createOrderline(52, map1);
        OrderlineRepository.createOrderline(52, map2);
        OrderlineRepository.createOrderline(52, map3);
        OrderlineRepository.createOrderline(53, map1);
        OrderlineRepository.createOrderline(53, map2);
        OrderlineRepository.createOrderline(53, map3);
        OrderlineRepository.createOrderline(54, map1);
        OrderlineRepository.createOrderline(54, map2);
        OrderlineRepository.createOrderline(54, map3);
        OrderlineRepository.createOrderline(55, map1);
        OrderlineRepository.createOrderline(55, map2);
        OrderlineRepository.createOrderline(55, map3);
        OrderlineRepository.createOrderline(56, map1);
        OrderlineRepository.createOrderline(56, map2);
        OrderlineRepository.createOrderline(56, map3);
        OrderlineRepository.createOrderline(57, map1);
        OrderlineRepository.createOrderline(57, map2);
        OrderlineRepository.createOrderline(57, map3);
        OrderlineRepository.createOrderline(58, map1);
        OrderlineRepository.createOrderline(58, map2);
        OrderlineRepository.createOrderline(58, map3);
        OrderlineRepository.createOrderline(59, map1);
        OrderlineRepository.createOrderline(59, map2);
        OrderlineRepository.createOrderline(59, map3);
        OrderlineRepository.createOrderline(60, map1);
        OrderlineRepository.createOrderline(60, map2);
        OrderlineRepository.createOrderline(60, map3);
        OrderlineRepository.createOrderline(61, map1);
        OrderlineRepository.createOrderline(61, map2);
        OrderlineRepository.createOrderline(61, map3);
        OrderlineRepository.createOrderline(62, map1);
        OrderlineRepository.createOrderline(62, map2);
        OrderlineRepository.createOrderline(62, map3);
        OrderlineRepository.createOrderline(63, map1);
        OrderlineRepository.createOrderline(63, map2);
        OrderlineRepository.createOrderline(63, map3);
        OrderlineRepository.createOrderline(64, map1);
        OrderlineRepository.createOrderline(64, map2);
        OrderlineRepository.createOrderline(64, map3);
        OrderlineRepository.createOrderline(65, map1);
        OrderlineRepository.createOrderline(65, map2);
        OrderlineRepository.createOrderline(65, map3);
        OrderlineRepository.createOrderline(66, map1);
        OrderlineRepository.createOrderline(66, map2);
        OrderlineRepository.createOrderline(66, map3);
        OrderlineRepository.createOrderline(67, map1);
        OrderlineRepository.createOrderline(67, map2);
        OrderlineRepository.createOrderline(67, map3);
        OrderlineRepository.createOrderline(68, map1);
        OrderlineRepository.createOrderline(68, map2);
        OrderlineRepository.createOrderline(68, map3);
        OrderlineRepository.createOrderline(69, map1);
        OrderlineRepository.createOrderline(69, map2);
        OrderlineRepository.createOrderline(69, map3);
        OrderlineRepository.createOrderline(70, map1);
        OrderlineRepository.createOrderline(70, map2);
        OrderlineRepository.createOrderline(70, map3);
        OrderlineRepository.createOrderline(71, map1);
        OrderlineRepository.createOrderline(71, map2);
        OrderlineRepository.createOrderline(71, map3);
        OrderlineRepository.createOrderline(72, map1);
        OrderlineRepository.createOrderline(72, map2);
        OrderlineRepository.createOrderline(72, map3);
        OrderlineRepository.createOrderline(73, map1);
        OrderlineRepository.createOrderline(73, map2);
        OrderlineRepository.createOrderline(73, map3);
        OrderlineRepository.createOrderline(74, map1);
        OrderlineRepository.createOrderline(74, map2);
        OrderlineRepository.createOrderline(74, map3);
        OrderlineRepository.createOrderline(75, map1);
        OrderlineRepository.createOrderline(75, map2);
        OrderlineRepository.createOrderline(75, map3);
        OrderlineRepository.createOrderline(76, map1);
        OrderlineRepository.createOrderline(76, map2);
        OrderlineRepository.createOrderline(76, map3);
        OrderlineRepository.createOrderline(77, map1);
        OrderlineRepository.createOrderline(77, map2);
        OrderlineRepository.createOrderline(77, map3);
        OrderlineRepository.createOrderline(78, map1);
        OrderlineRepository.createOrderline(78, map2);
        OrderlineRepository.createOrderline(78, map3);
        OrderlineRepository.createOrderline(79, map1);
        OrderlineRepository.createOrderline(79, map2);
        OrderlineRepository.createOrderline(79, map3);
        OrderlineRepository.createOrderline(80, map1);
        OrderlineRepository.createOrderline(80, map2);
        OrderlineRepository.createOrderline(80, map3);
        OrderlineRepository.createOrderline(81, map1);
        OrderlineRepository.createOrderline(81, map2);
        OrderlineRepository.createOrderline(81, map3);
        OrderlineRepository.createOrderline(82, map1);
        OrderlineRepository.createOrderline(82, map2);
        OrderlineRepository.createOrderline(82, map3);
        OrderlineRepository.createOrderline(83, map1);
        OrderlineRepository.createOrderline(83, map2);
        OrderlineRepository.createOrderline(83, map3);
        OrderlineRepository.createOrderline(84, map1);
        OrderlineRepository.createOrderline(84, map2);
        OrderlineRepository.createOrderline(84, map3);
        OrderlineRepository.createOrderline(85, map1);
        OrderlineRepository.createOrderline(85, map2);
        OrderlineRepository.createOrderline(85, map3);
        OrderlineRepository.createOrderline(86, map1);
        OrderlineRepository.createOrderline(86, map2);
        OrderlineRepository.createOrderline(86, map3);
        OrderlineRepository.createOrderline(87, map1);
        OrderlineRepository.createOrderline(87, map2);
        OrderlineRepository.createOrderline(87, map3);
        OrderlineRepository.createOrderline(88, map1);
        OrderlineRepository.createOrderline(88, map2);
        OrderlineRepository.createOrderline(88, map3);
        OrderlineRepository.createOrderline(89, map1);
        OrderlineRepository.createOrderline(89, map2);
        OrderlineRepository.createOrderline(89, map3);
        OrderlineRepository.createOrderline(90, map1);
        OrderlineRepository.createOrderline(90, map2);
        OrderlineRepository.createOrderline(90, map3);
		
		EntityRepository.exit();
	}
}

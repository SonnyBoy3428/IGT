package hsma.ss2018.informatik.igt.kohler.javawithhibernate.test;

import static org.junit.Assert.*;

import org.junit.Test;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.DistrictRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.District;

/**
 * This class tests the customer Repository, to see if it all works well
 * 
 * @author Dustin Noah Young (1412293), Erica Paradis Boudjio Dongmeza (1424532) Patrick Wolf (1429439)
 *
 */
public class CustomerRepositoryTest {

	@Test
	public void testCreateCustomer() {
		District district = DistrictRepository.getDistrict(1);
		
		Customer customer = new Customer();
		customer.setFirstName("Franz");
		customer.setLastName("Bock");
		customer.setAddress("Musterstrasse 4");
		customer.setTelephone("0621 32352536");
		customer.setCreditCardNr("123456789012");
		customer.setDistrict(district);
	}

}

package hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import hsma.ss2018.informatik.igt.kohler.javawithhibernate.control.DistrictRepository;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.District;
import hsma.ss2018.informatik.igt.kohler.javawithhibernate.model.Customer;

/**
 * This class acts as the API for calls to the district repository.
 * 
 * @author Dustin Noah Young, 
 *
 */
@Path("/districtService")
public class DistrictService extends EntityService{
	/**
	 * The tag names belonging to a district XML object.
	 */
	static final String[] TAG_NAMES = {"District", "DistrictId", "DistrictName", "DistrictSize", "WarehouseId"};
	
	/**
	 * Receives a POST request to create a district. The district information is located in the request body.
	 * 
	 * @param districtInformation Request body containing district information.
	 * 
	 * @return The response containing the district.
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@POST
	@Path("/createDistrict")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createDistrict(String districtInformation) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(new InputSource(new StringReader(districtInformation)));
		Element rootElement = document.getDocumentElement();
		
		String[] districtValues = extractInformationFromXMLEntity(TAG_NAMES, 2, 3, rootElement);
		
		District createdDistrict = DistrictRepository.createDistrict(districtValues[0], Double.parseDouble(districtValues[1]), Long.parseLong(districtValues[2]));
		
		return Response.status(200).entity(DistrictRepository.districtToXML(createdDistrict)).build();
	}
	
	/**
	 * Receives a GET request to get a district. The district id is located within the URL.
	 * 
	 * @param districtId The district Id located in the URL.
	 * 
	 * @return The response containing the district.
	 */
	@GET
	@Path("/getDistrictById={param}")
	public Response getDistrictById(@PathParam("param") long districtId) {
		District district = DistrictRepository.getDistrict(districtId);
		
		String districtXML = DistrictRepository.districtToXML(district);
		
		return Response.status(200).entity(districtXML).build();
	}
	
	/**
	 * Receives a GET request to get all districts.
	 * 
	 * @return The response containing the districts.
	 */
	@GET
	@Path("/getAllDistricts")
	public Response getAllDistricts() {
		Set<District> districts = DistrictRepository.getAllDistricts();
		
		String districtsXML = DistrictRepository.districtsToXML(districts);
		
		return Response.status(200).entity(districtsXML).build();
	}
	
	/**
	 * Receives a GET request to get all customers belonging to a district. The district id is located within the URL.
	 * 
	 * @param districtId The district id located in the URL.
	 * 
	 * @return The response containing the district and all his customers.
	 */
	@GET
	@Path("/getAllDistrictCustomersByDistrictId={param}")
	public Response getAllDistrictCustomers(@PathParam("param") long districtId) {
		District district = DistrictRepository.getDistrict(districtId);
		Set<Customer> customers = DistrictRepository.getDistrictCustomers(districtId);
		
		String districtAndCustomersXML = DistrictRepository.districtAndCustomersToXML(district, customers);
		
		return Response.status(200).entity(districtAndCustomersXML).build();
	}
	
	/**
	 * Receives a DELETE request to delete a district. The district id is located within the URL.
	 * 
	 * @param districtId The district id located inside the URL.
	 * 
	 * @return The response.
	 */
	@DELETE
	@Path("/deleteDistrictByDistrictId={param}")
	public Response deleteDistrict(@PathParam("param") long districtId) {
		DistrictRepository.deleteDistrict(districtId);
		
		String response = "Deletion of district with id: " + districtId + " was successful!";
		
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Receives a PUT request to update a district. The district information is located within the request body.
	 * 
	 * @param districtInformation The district information inside the request body.
	 * 
	 * @return The response containing the updated district.
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	@PUT
	@Path("/updateDistrict")
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateDistrict(String districtInformation) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(new InputSource(new StringReader(districtInformation)));
		Element rootElement = document.getDocumentElement();
		
		String[] districtValues = extractInformationFromXMLEntity(TAG_NAMES, 1, 3, rootElement);
		
		District updatedDistrict = DistrictRepository.updateDistrict(Long.parseLong(districtValues[0]), districtValues[1], Double.parseDouble(districtValues[2]));
		
		return Response.status(200).entity(DistrictRepository.districtToXML(updatedDistrict)).build();
	}
}

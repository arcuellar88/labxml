package ufrt.it4bi.dvdshop;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Renter {

	private String lastName; //Last name of renter person
	private String firstName; //First name of renter person
	private String address; //Address of renter person
	private String date; // the return date
	
	
	public Renter(String lastName, String firstName,
			String address, String date) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.date = date;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public void saveRent(Node parent, Document document)
	{
		Element rent = document.createElement("rent");

		Attr attr = document.createAttribute("date");
		attr.setValue(date);
		rent.setAttributeNode(attr);
		
		Element person = document.createElement("person");
		
		Element nLastName = document.createElement("lastName");
		nLastName.appendChild(document.createTextNode(lastName));

		
		Element nFirstName = document.createElement("firstName");
		nFirstName.appendChild(document.createTextNode(firstName));

		Element nAddress = document.createElement("address");
		nAddress.appendChild(document.createTextNode(address));
		
		rent.appendChild(person);
		person.appendChild(nLastName);
		person.appendChild(nFirstName);
		person.appendChild(nAddress);
		
		parent.appendChild(rent);
		
		System.out.println("Item saved");
		

	}
	
	
}

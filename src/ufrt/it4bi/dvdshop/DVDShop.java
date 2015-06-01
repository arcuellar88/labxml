package ufrt.it4bi.dvdshop;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DVDShop {

	/**
	 * Root element of the DB
	 */
	private Element root;
	
	private Document document;

	// --------------------------------------------------------
	// 1- DOM parsing
	// --------------------------------------------------------

	public DVDShop()
	{
		//Load current database
		try {
			initializeDB(new File("./data/dvd.xml"));
			System.out.println("Database initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read the XML database of rented movies
	 * @throws Exception 
	 */
	private void initializeDB(File f) throws Exception 
	{
		try
		{
		// TODO Auto-generated method stub
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Load and Parse the XML document
		//document contains the complete XML as a Tree.
	    document = builder.parse(f);
		root =  document.getDocumentElement(); 
		
		}
		catch(SAXException e)
		{
			throw new Exception("Data cannot initialize:"+e.getMessage());
		}
		catch(IOException e)
		{
			throw new Exception("Data cannot initialize: File"+f+"was not found");
		}
		catch(ParserConfigurationException e)
		{
			throw new Exception("Data cannot initialize, document cannot be parsed"+e.getMessage());
		}
	}
	
	// --------------------------------------------------------
	// 2- DOM contents
	// --------------------------------------------------------
		
	
	/**
	 * Method to print the list of actors of the films
	* b) Following a recursive parsing of DOM nodes until getting <actor> nodes
	 */
	public void getAuthorsDirect()
	{
		
		NodeList actor = root.getElementsByTagName("actor");
		int nr=0;
		for (int i=0;i<actor.getLength();i++)
		{
			Node act = actor.item(i);
			if(act.getNodeType() == Node.ELEMENT_NODE)
			{
				nr++;
				Element element = (Element) act;
				System.out.println(nr+" "+element.getElementsByTagName("firstName").item(0).getFirstChild().getNodeValue()
					+ " " + element.getElementsByTagName("lastName").item(0).getFirstChild().getNodeValue() );
			}
		}
	}
	
	/**
	 * Method to print the list of actors of the films
	 * b) Direct access to <actor> tags
	 */
	public void getAuthorsRecursive(Node node)
	{

		NodeList children = node.getChildNodes();

		for (int i=0;i<children.getLength();i++)
		{
			Node n = children.item(i);
			
			if(n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("actor"))
			{
				Element element = (Element) n;
				System.out.println(element.getElementsByTagName("firstName").item(0).getFirstChild().getNodeValue()
					+ " " + element.getElementsByTagName("lastName").item(0).getFirstChild().getNodeValue() );
			}
			else
			{
				getAuthorsRecursive(n);
			}
		}
	}
	/**
	 * Method to get the dates of all DVDs
	 * 2- Add to the previous program a method, which outputs all the return dates ((attribute “date” of tag <rent>)).
	 */
	public void getReturnDates()
	{
		NodeList rents = root.getElementsByTagName("rent");
		for (int i=0;i<rents.getLength();i++)
		{
			Node r = rents.item(i);
			NamedNodeMap nnm=r.getAttributes();
			Node date=nnm.getNamedItem("date");
			System.out.println(date.getNodeValue());
		}
	}
	
	// --------------------------------------------------------
	// 	3- Modifying a DOM
	// --------------------------------------------------------

	public void rent(String title,Renter r) throws Exception
	{
		NodeList titles = root.getElementsByTagName("title");
		for (int i=0;i<titles.getLength();i++)
		{
			Node t = titles.item(i);
			
			if(t.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element) t;
				
				if(element.getFirstChild().getNodeValue().equals(title))
				{
					NodeList rents=((Element) element.getParentNode().getParentNode()).getElementsByTagName("rent");
					if(rents.getLength()==0)
					   {
							r.saveRent(t.getParentNode().getParentNode(), document);
					   }
					else
					{
						//throw new Exception("The film "+title+" is already rented");
						System.out.println("The film "+title+" is already rented");
					}
				}
				
			}
		}
	}
	
	// --------------------------------------------------------
	// 4- Transforming XML document with XSLT and DOM
	// --------------------------------------------------------

	/**
	 * Save XML file
	 * 1-Save the DOM obtained in the previous step into an XML file.
	 *  Be sure that your method works correctly and does not produce more
	 *	than one rent element for the same film.
	 * @throws TransformerException
	 */
	public void saveXML() throws TransformerException
	{
		// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(document);
				StreamResult result = new StreamResult(new File("./data/dvd.xml"));
		 
				// Output to console for testing
				// StreamResult result = new StreamResult(System.out);
		 
				transformer.transform(source, result);
		 
				System.out.println("XML file saved");
		 
	}
	
	/**
	 * Generate Html file out of the XML
	 * Add to the previous program a method, which creates a transformer based 
	 * on the given Stylesheet and applies transformation on the dvd.xml file
	 * @throws TransformerException
	 */
	public void saveHTML() throws TransformerException
	{
		TransformerFactory factory = TransformerFactory.newInstance();
	    Source stylesheetSource = new StreamSource(new File("./data/dvd_film_table.xsl").getAbsoluteFile());
	    Transformer transformer = factory.newTransformer(stylesheetSource);
		
	    DOMSource source = new DOMSource(document);
		
	    StreamResult result = new StreamResult(new File("./data/dvdhtml.html"));
	    transformer.transform(source, result);
	    
		System.out.println("HTML file saved");

	}
	// --------------------------------------------------------
	// 4- Querying XML document with XPath and DOM
	// --------------------------------------------------------
	
	/**
	 * Using Xpath get:
	 * 	a- The list of films (title)
	 */
	public void getListOfFilmTitles()
	{
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodes;
		try 
		{
			nodes = (NodeList)xPath.evaluate("DVD/film/title", root, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); ++i) {
			    Element e = (Element) nodes.item(i);
			    System.out.println((i+1)+" "+e.getFirstChild().getNodeValue());
			}
		} 
		catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * Using Xpath get:
	 * b- The list of already rented films	 
	 * */
	public void getListOfRentedFilmTitles()
	{
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodes;
		try 
		{
			nodes = (NodeList)xPath.evaluate("DVD[rent]/film/title", root, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); ++i) {
			    Element e = (Element) nodes.item(i);
			    System.out.println((i+1)+" "+e.getFirstChild().getNodeValue());
			}
		} 
		catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	// --------------------------------------------------------
	// Getters & Setters
	// --------------------------------------------------------

	public Element getRoot() {
		return root;
	}

	public void setRoot(Element root) {
		this.root = root;
	}

	
	// --------------------------------------------------------
	// MAIN
	// --------------------------------------------------------

	public static void main(String[] args) throws Exception
	{
		DVDShop dvds= new DVDShop();
		System.out.println("------------------------------");
		System.out.println("2.1.a getAuthorsDirect()");
		System.out.println("------------------------------");
		dvds.getAuthorsDirect();
		
		System.out.println("------------------------------");
		System.out.println("2.1.b getAuthorsRecursive(Node root)");
		System.out.println("------------------------------");
		dvds.getAuthorsRecursive(dvds.getRoot());
	
		System.out.println("------------------------------");
		System.out.println("2.2 getReturnDates()");
		System.out.println("------------------------------");
		dvds.getReturnDates();
		
		System.out.println("------------------------------");
		System.out.println("3. rent()");
		System.out.println("------------------------------");
		Renter r = new Renter("Rodriguez Cuellar","Alejandro","8 Rue","24/07/2003");
		dvds.rent("The Godfather", r);
		
		System.out.println("------------------------------");
		System.out.println("4.1 saveXML()");
		System.out.println("------------------------------");
		dvds.saveXML();
		
		System.out.println("------------------------------");
		System.out.println("4.2 saveHTML()");
		System.out.println("------------------------------");
		dvds.saveHTML();
		
		System.out.println("------------------------------");
		System.out.println("4.a getListOfFilmTitles()");
		System.out.println("------------------------------");
		dvds.getListOfFilmTitles();
		
		System.out.println("------------------------------");
		System.out.println("4.b getListOfRentedFilmTitles()");
		System.out.println("------------------------------");
		dvds.getListOfRentedFilmTitles();
		
	}
}

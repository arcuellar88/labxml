package domjava;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.*;

import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParser {
	
	
	public static void xpathparser()
	{
		
		System.out.println("------------------------------");

		System.out.println("XPATH 4a)");

		try
		{
		// SAX InputSource
		//XPath - SAX
		org.xml.sax.InputSource source = new InputSource(
		      new FileInputStream("./data/dvd.xml"));
		//Xpath factory
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		//evaluate Xpath query
		String xpathString = "//DVD/film/title"; XPathExpression exp = xpath.compile (xpathString); QName format = XPathConstants.NODESET;
		NodeList results = (NodeList)exp.evaluate(source,format);
		for (int i = 0; i < results.getLength(); i++) {
			System.out.println(results.item(i).getFirstChild().getNodeValue());

		}
		}
		catch(Exception e)
		{
		e.printStackTrace();	
		}
		System.out.println("------------------------------");

		System.out.println("XPATH 4b)");

		try
		{
		// SAX InputSource
		//XPath - SAX
		org.xml.sax.InputSource source = new InputSource(
		      new FileInputStream("./data/dvd.xml"));
		//Xpath factory
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		//evaluate Xpath query
		String xpathString = "//DVD[rent]/film/title"; XPathExpression exp = xpath.compile (xpathString); QName format = XPathConstants.NODESET;
		NodeList results = (NodeList)exp.evaluate(source,format);
		for (int i = 0; i < results.getLength(); i++) {
			System.out.println(results.item(i).getFirstChild().getNodeValue());

		}		
		}
		catch(Exception e)
		{
		e.printStackTrace();	
		}
		System.out.println("------------------------------");

	}
	
	
	public static void main(String[] args) throws Exception {
	    SAXParserFactory parserFactor = SAXParserFactory.newInstance();
	    SAXParser parser = parserFactor.newSAXParser();
	    SAXDomHandler handler = new SAXDomHandler();
	    
	    //Using XML File for Parsing
	    parser.parse(new FileInputStream("./data/dvd.xml"),
	                 handler);
	     
	    
	    //Printing the list of movie titles obtained from XML
	    System.out.println("\n------------------------------");
	    System.out.println("The  list of Film Titles in dvd.xml:");
	    System.out.println("------------------------------");
	    for ( DVDLibrary dvd : handler.dvdList){
	      System.out.println(dvd.showtitle());
	    }
		xpathparser();
	  }
	
		
	}


class SAXDomHandler extends DefaultHandler {
	 
	  List<DVDLibrary> dvdList = new ArrayList<>();
	  DVDLibrary dvd = null;
	  String content = null;
	  
	  @Override
	  //Triggered whenever the start of tag is found.
	  public void startElement(String uri, String localName,
	                           String qName, Attributes attributes)
	                           throws SAXException {
	       
	    switch(qName){
	      // Print whenever the start tags are found.
	      case "DVD":
			  	//Create a new DVD object when the start tag is found
		        dvd = new DVDLibrary();
		        System.out.println("Start Element: DVD");
		        break;
	      case "film":
		        System.out.println("Start Element: Film");
		        break;
	      case "actor":
		        System.out.println("Start Element: actor");
		        break;
	      case "rent":
		        System.out.println("Start Element: rent");
		        break;
	      case "title":
		        System.out.println("Start Element: title");
		        break;
	      case "director":
		        System.out.println("Start Element: director");
		        break;
	      case "year":
		        System.out.println("Start Element: year");
		        break;
	      case "type":
		        System.out.println("Start Element: type");
		        break;
	      case "lastName":
		        System.out.println("Start Element: lastName");
		        break;
	      case "firstName":
		        System.out.println("Start Element: firstName");
		        break;
	    }
	  }
	 
	  @Override
	  // Triggered whenever End of tag is found
	  public void endElement(String uri, String localName,
	                         String qName) throws SAXException {
	   switch(qName){
	      // Print whenever the start tags are found.
	     case "DVD":
		   System.out.println("End Element: DVD");
		   //Add the dvd to list once end tag is found
	       dvdList.add(dvd);      
	     case "film":
		   System.out.println("End Element: Film");
	       break;
	     case "title":
	    	 System.out.println("End Element: title");
	    	 dvd.title = content;
	       break;
	     case "director":
	    	 System.out.println("End Element: director");
	    	 dvd.director = content;
	       break;
	     case "type":
	    	 System.out.println("End Element: type");
	    	 dvd.type = content;
	       break;
	     case "year":
	    	 System.out.println("End Element: year");
	    	 dvd.year = content;
	       break;
	     case "actor":
	    	 System.out.println("End Element: actor");
	       break;
	     case "firstName":
	    	 System.out.println("End Element: firstName");
	       break;
	     case "lastName":
	    	 System.out.println("End Element: lastName");
	       break;
	   }
	  }
	 
	  @Override
	  public void characters(char[] ch, int start, int length)
	          throws SAXException {
	    content = String.copyValueOf(ch, start, length).trim();
	  }
	     
	}
	 
	class DVDLibrary {
	 
	  String title;
	  String director;
	  String year;
	  String type;
	 
	  public String showtitle() {
		return title;
	
	  }
	}

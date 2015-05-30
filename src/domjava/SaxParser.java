package domjava;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.*;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParser {
	public static void main(String[] args) throws Exception {
	    SAXParserFactory parserFactor = SAXParserFactory.newInstance();
	    SAXParser parser = parserFactor.newSAXParser();
	    SAXDomHandler handler = new SAXDomHandler();
	    
	    //Using XML File for Parsing
	    parser.parse(new FileInputStream("D:\\xml\\dvd.xml"),
	                 handler);
	     
	    //Printing the list of movie titles obtained from XML
	    System.out.println("\nThe  list of Movie Titles are as follows:");
	    for ( DVDLibrary dvd : handler.dvdList){
	      System.out.println(dvd.showtitle());
	    }
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
		        System.out.println("Start Element DVD");
		        break;
	      case "film":
		        System.out.println("Start Element Film");
		        break;
	      case "actor":
		        System.out.println("Start Element actor");
		        break;
	      case "rent":
		        System.out.println("Start Element rent");
		        break;
	      case "title":
		        System.out.println("Start Element rent");
		        break;
	      case "director":
		        System.out.println("Start Element director");
		        break;
	      case "year":
		        System.out.println("Start Element year");
		        break;
	      case "type":
		        System.out.println("Start Element type");
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
		   System.out.println("End Element DVD");
		   //Add the dvd to list once end tag is found
	       dvdList.add(dvd);      
	     case "film":
		   System.out.println("End Element Film");
	       break;
	     case "title":
	    	 System.out.println("End Element title");
	    	 dvd.title = content;
	       break;
	     case "director":
	    	 System.out.println("End Element director");
	    	 dvd.director = content;
	       break;
	     case "type":
	    	 System.out.println("End Element type");
	    	 dvd.type = content;
	       break;
	     case "year":
	    	 System.out.println("End Element year");
	    	 dvd.year = content;
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

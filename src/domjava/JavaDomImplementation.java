package domjava;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class JavaDomImplementation {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		// TODO Auto-generated method stub
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		//Load and Parse the XML document
		//document contains the complete XML as a Tree.
		Document document = builder.parse(new File("D:\\Class Lectures\\Masters_UFRT_2nd_Sem\\XML & Web Technologies\\XML API(SAX, DOM)\\Lab\\dvd.xml"));
		Element root =  document.getDocumentElement(); 
		//NodeList nodeList = document.getDocumentElement().getChildNodes();
		System.out.println(root.getChildNodes().getLength());
		NodeList actor = root.getElementsByTagName("actor");
		for (int i=0;i<actor.getLength();i++)
		{
			Node n = actor.item(i);
			if(actor.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element) n;
				System.out.println(element.getElementsByTagName("firstName").item(0).getFirstChild().getNodeValue()
					+ " " + element.getElementsByTagName("lastName").item(0).getFirstChild().getNodeValue() );
			}
		}
		/*for (int i=0;i<nodeList.getLength();i++)
		{
			NodeList childnodes = nodeList.item(i).getChildNodes();
			for (int j=0;j<childnodes.getLength();j++)
			{
				NodeList childnodes1 = childnodes.item(i).getChildNodes();
				for (int k=0;k<childnodes1.getLength();k++)
				{
					System.out.println(childnodes1.item(k).getAttributes().getNamedItem("title").getNodeValue());
				}
			}
		}*/




	}

}

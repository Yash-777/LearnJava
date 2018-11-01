package com.github.files;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.ws.commons.util.NamespaceContextImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * https://stackoverflow.com/a/33516157/5081877
 * https://stackoverflow.com/a/47280397/5081877
 * 
 * @author yashwanth.m
 *
 */
public class XML_XPath {
	
	public static void main(String[] args) {
		String namespaceXML = "<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>"
				+ "<soap:Body><m:NumberToDollarsResponse xmlns:m=\"http://www.dataaccess.com/webservicesserver/\">"
				+ "<m:NumberToDollarsResult>nine hundred and ninety nine dollars</m:NumberToDollarsResult>"
				+ "</m:NumberToDollarsResponse></soap:Body></soap:Envelope>";
		String jsonString = "{'soap':'http://schemas.xmlsoap.org/soap/envelope/',"
				+ "'m':'http://www.dataaccess.com/webservicesserver/'}";
		String expression = "//m:NumberToDollarsResponse/m:NumberToDollarsResult/text()";
		
		
		String xml = "<soapenv:Body xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'>"
				+ "<Yash:Data xmlns:Yash='http://Yash.stackoverflow.com/Services/Yash'>"
				+ "<Yash:Tags>Java</Yash:Tags><Yash:Tags>Javascript</Yash:Tags><Yash:Tags>Selenium</Yash:Tags>"
				+ "<Yash:Top>javascript</Yash:Top><Yash:User>Yash-777</Yash:User>"
				+ "</Yash:Data></soapenv:Body>";
		String jsonNameSpaces = "{'soapenv':'http://schemas.xmlsoap.org/soap/envelope/',"
				+ "'Yash':'http://Yash.stackoverflow.com/Services/Yash'}";
		String xpathExpression = "//Yash:Data";
		
		
		Document doc = getDocument(false, "fileName", namespaceXML);
		getNodesFromXpath(doc, expression, jsonString);
		System.out.println("\n===== ***** =====");
		Document doc1 = getDocument(false, "fileName", xml);
		getNodesFromXpath(doc1, xpathExpression, jsonNameSpaces);
		System.out.println("\n===== ***** =====");
		Document doc2 = getDocument(true, "./books.xml", xml);
		getNodesFromXpath(doc2, "//person", "{}");
	}
	static Document getDocument( boolean isFileName, String fileName, String xml ) {
		Document doc = null;
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			if( isFileName ) {
				File file = new File( fileName );
				FileInputStream stream = new FileInputStream( file );
				doc = builder.parse( stream );
			} else {
				doc = builder.parse( string2Source( xml ) );
			}
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * ELEMENT_NODE[1],ATTRIBUTE_NODE[2],TEXT_NODE[3],CDATA_SECTION_NODE[4],
	 * ENTITY_REFERENCE_NODE[5],ENTITY_NODE[6],PROCESSING_INSTRUCTION_NODE[7],
	 * COMMENT_NODE[8],DOCUMENT_NODE[9],DOCUMENT_TYPE_NODE[10],DOCUMENT_FRAGMENT_NODE[11],NOTATION_NODE[12]
	 */
	public static void getNodesFromXpath( Document doc, String xpathExpression, String jsonNameSpaces ) {
		try {
			XPathFactory xpf = XPathFactory.newInstance();
			XPath xpath = xpf.newXPath();
			
			JSONObject namespaces = getJSONObjectNameSpaces(jsonNameSpaces);
			if ( namespaces.size() > 0 ) {
				NamespaceContextImpl nsContext = new NamespaceContextImpl();
			
				Iterator<?> key = namespaces.keySet().iterator();
				while (key.hasNext()) { // Apache WebServices Common Utilities
					String pPrefix = key.next().toString();
					String pURI = namespaces.get(pPrefix).toString();
					nsContext.startPrefixMapping(pPrefix, pURI);
				}
				xpath.setNamespaceContext(nsContext );
			}
			
			XPathExpression compile = xpath.compile(xpathExpression);
			NodeList nodeList =	(NodeList) compile.evaluate(doc, XPathConstants.NODESET);
			displayNodeList(nodeList);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	static void displayNodeList( NodeList nodeList ) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			String NodeName = node.getNodeName();
			
			NodeList childNodes = node.getChildNodes();
			if ( childNodes.getLength() > 1 ) {
				for (int j = 0; j < childNodes.getLength(); j++) {
					
					Node child = childNodes.item(j);
					short nodeType = child.getNodeType();
					if ( nodeType == 1 ) {
						System.out.format( "\n\t Node Name:[%s], Text[%s] ", child.getNodeName(), child.getTextContent() );
					}
				}
			} else {
				System.out.format( "\n Node Name:[%s], Text[%s] ", NodeName, node.getTextContent() );
			}
			
		}
	}
	static String document2String(Node node) {
		// A character stream that collects its output in a string buffer, which can then be used to construct a string. 
		StringWriter sw = new StringWriter();
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer trnsform = factory.newTransformer(); 
			//An instance of this abstract class can transform a source tree into a result tree. 
			//"{http://xyz.foo.com/yada/baz.html}foo" --> The namespace URI enclosed in curly braces ({}), foo --> local name
			trnsform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trnsform.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {
			System.out.println("nodeToString Transformer Exception");
		}
		return sw.toString();
	}
	static InputStream string2Stream( String str ) {
		ByteArrayInputStream byteArrayInputStream = null;
		try {
			byteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return byteArrayInputStream;
	}
	static InputSource string2Source( String str ) {
		InputSource inputSource = new InputSource( new StringReader( str ) );
		return inputSource;
	}
	static JSONObject getJSONObjectNameSpaces( String jsonNameSpaces ) {
		if(jsonNameSpaces.indexOf("'") > -1)    jsonNameSpaces = jsonNameSpaces.replace("'", "\"");
		JSONParser parser = new JSONParser();
		JSONObject namespaces = null;
		try {
			namespaces = (JSONObject) parser.parse(jsonNameSpaces);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return namespaces;
	}
	static void addNameSpaces(JSONObject namespaces, XPath xpath) {
		if (namespaces.size() > 0) {
			final JSONObject declaredPrefix = namespaces; // To access in Inner-class.
			NamespaceContext nameSpace = new NamespaceContext() {
				// To get all prefixes bound to a Namespace URI in the current scope, XPath 1.0 specification
				// --> "no prefix means no namespace"
				public String getNamespaceURI(String prefix) {
					Iterator<?> key = declaredPrefix.keySet().iterator();
					System.out.println("Keys : "+key.toString());
					while (key.hasNext()) {
						String name = key.next().toString();
						if (prefix.equals(name)) {
							System.out.println(declaredPrefix.get(name));
							return declaredPrefix.get(name).toString();
						}
					}
					return "";
				}
				public Iterator<?> getPrefixes(String val) {
					return null;
				}
				public String getPrefix(String uri) {
					return null;
				}
			};// Inner class.
			
			xpath.setNamespaceContext( nameSpace );
		}
	}
}
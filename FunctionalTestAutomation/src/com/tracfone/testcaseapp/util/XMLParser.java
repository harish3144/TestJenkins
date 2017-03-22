package com.tracfone.testcaseapp.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tracfone.testcaseapp.bean.TestCaseBean;

public class XMLParser {

	public String parseSOAPXml(String input) throws ParserConfigurationException, SAXException, IOException {
		Map<String, String> nameSpaceMap = getNameSpaceMap(input);
		String subString = input.substring(input.indexOf("<soapenv:Body>") + 14, input.indexOf("</soapenv:Body>"));
		subString = populateNameSpace(subString, nameSpaceMap);
		return subString;
	}

	private Map<String, String> getNameSpaceMap(String input)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(input));
		Document doc = builder.parse(is);
		NodeList element = doc.getElementsByTagName("soapenv:Envelope");
		Map<String, String> nameSpaceMap = new HashMap<String, String>();
		for (int temp = 0; temp < element.getLength(); temp++) {
			Node node = element.item(temp);
			if (node.hasAttributes()) {
				NamedNodeMap map = node.getAttributes();
				for (int i = 0; i < map.getLength(); i++) {
					Node item = map.item(i);
					String nodeName = item.getNodeName();
					nameSpaceMap.put(nodeName.substring(nodeName.indexOf("xmlns:") + 6),
							item.getNodeName() + "=\"" + item.getNodeValue() + "\"");
				}
			}
		}
		return nameSpaceMap;
	}

	private String populateNameSpace(String test, Map<String, String> testMap) {
		Set<String> keys = testMap.keySet();
		Iterator<String> itr = keys.iterator();
		while (itr.hasNext()) {
			String key = itr.next();
			test = populateNameSpace(test, testMap, key);
		}
		return test;
	}

	private String populateNameSpace(String test, Map<String, String> testMap, String key) {
		if (test.contains("<" + key + ":")) {
			String initial = test.substring(0, test.indexOf("<" + key + ":"));
			String intermittent = test.substring(test.indexOf("<" + key + ":"));
			String actual = "";
			if (!intermittent.substring(0, intermittent.indexOf(">")).contains("xmlns")) {
				actual = intermittent.substring(0, intermittent.indexOf(">")) + " " + testMap.get(key);
			}
			if (actual != "") {
				test = initial + actual
				// + intermittent.substring(intermittent.indexOf(">"));
						+ populateNameSpace(intermittent.substring(intermittent.indexOf(">")), testMap, key);
			}
		}
		return test;
	}

	public String populateData(String input, TestCaseBean bean, String esn, String sim, String pin)
			throws ParserConfigurationException, SAXException, IOException, JDOMException {
		return populateDataSOA_WFM(input, bean, esn, sim, pin);
	}

	// private String populateDataSF(String input, TestCaseBean bean, String
	// esn, String sim, String pin) {
	// String sourceSystem = bean.getChannel();
	// String brand = bean.getBrand();
	// return null;
	// }

	public String populateDataSOA_WFM(String input, TestCaseBean bean, String esn, String sim, String pin)
			throws ParserConfigurationException, SAXException, IOException, JDOMException {
		String sourceSystem = bean.getChannel();
		String brand = bean.getBrand();
		SAXBuilder jdomBuilder = new SAXBuilder();
		org.jdom2.Document jdomDocument = jdomBuilder.build(new InputSource(new StringReader(input)));

		// use the default implementation
		XPathFactory xFactory = XPathFactory.instance();
		// System.out.println(xFactory.getClass());

		// select all links
		XPathExpression<Element> expr = xFactory.compile("//a:ConsumerInfo", Filters.element(), null,
				Namespace.getNamespace("a", "http://www.tracfone.com/rest/model/v1/common"));
		List<Element> consumerInfo = expr.evaluate(jdomDocument);
		for (Element linkElement : consumerInfo) {
			List<Element> children = linkElement.getChildren();
			String key = "";
			for (Element child : children) {
				if (child.getName().equalsIgnoreCase("name")) {
					key = child.getValue();
				}
				if (key.startsWith("brand") && child.getName().equalsIgnoreCase("value")) {
					child.setText(brand);
				}
				if (key.equals("sourceSystem") && child.getName().equalsIgnoreCase("value")) {
					child.setText(sourceSystem);
				}
			}
		}
		XPathExpression<Element> exp2 = xFactory.compile("//ns1:characteristicSpecification", Filters.element(), null,
				Namespace.getNamespace("ns1", "http://www.tracfone.com/rest/model/v1/common"));
		List<Element> characterSpec = exp2.evaluate(jdomDocument);
		String placeHolder = "";
		for (Element charElement : characterSpec) {
			List<Element> children = charElement.getChildren();
			String key = "";
			for (Element child : children) {
				if (child.getName().equalsIgnoreCase("name")) {
					key = child.getValue();
				}
				if (key.equals("productCategory") && child.getName().equalsIgnoreCase("value")) {
					placeHolder = child.getValue();
				}
			}
		}
		System.out.println(placeHolder);
		for (Element charElement : characterSpec) {
			List<Element> children = charElement.getChildren();
			String key = "";
			for (Element child : children) {
				if (child.getName().equalsIgnoreCase("name")) {
					key = child.getValue();
				}
				if (key.equalsIgnoreCase("productSerialNumber") && child.getName().equalsIgnoreCase("value")) {
					if (placeHolder.equalsIgnoreCase("HANDSET")) {
						child.setText(esn);
					}
				}
			}
		}

		XPathExpression<Element> expr3 = xFactory.compile("//ns1:SupportingResources", Filters.element(), null,
				Namespace.getNamespace("ns1", "http://www.tracfone.com/rest/model/v1/common"));
		List<Element> supportingResource = expr3.evaluate(jdomDocument);
		for (Element linkElement : supportingResource) {
			List<Element> children = linkElement.getChildren();
			String key = "";
			for (Element child : children) {
				if (child.getName().equalsIgnoreCase("resourceType")) {
					key = child.getValue();
				}
				if (key.equals("AIRTIME_CARD") && child.getName().equalsIgnoreCase("serialNumber")) {
					child.setText(pin);
				}
				if (key.equals("SIM_CARD") && child.getName().equalsIgnoreCase("serialNumber")) {
					child.setText(sim);
				}
			}
		}
		input = new XMLOutputter().outputString(jdomDocument);

		// Map<String, String> nameSpaceMap = getNameSpaceMap(input);
		// Set<String> keys = nameSpaceMap.keySet();
		// Iterator<String> itr = keys.iterator();
		// String esnKey = "";
		// while (itr.hasNext()) {
		// String key = itr.next();
		// if
		// (nameSpaceMap.get(key).contains("http://www.tracfone.com/rest/model/v1/common"))
		// {
		// esnKey = key;
		// }
		// }
		// String esnTagString = esnKey + ":productSerialNumber>";
		// String esnInInput = "";
		// String simInInput = "";
		// String pinInInput = "";
		// if (input.contains("<" + esnTagString)) {
		// esnInInput = input.substring(input.indexOf("<" + esnTagString) +
		// esnTagString.length() + 1,
		// input.indexOf("</" + esnTagString));
		// }
		// Map<String, String> map = getSimAndPin(input, esnKey);
		// String brandInInput = getBrand(input, esnKey);
		// String ssInInput = getSourceSystem(input, esnKey);
		// simInInput = map.get("sim");
		// pinInInput = map.get("pin");
		// if (!esnInInput.trim().equals("")) {
		// input = input.replace(esnInInput, esn);
		// }
		// if (!simInInput.trim().equals("")) {
		// input = input.replace(simInInput, sim);
		// }
		// if (!pinInInput.trim().equals("")) {
		// input = input.replace(pinInInput, pin);
		// }
		// if (!brandInInput.trim().equals("")) {
		// input = input.replace(brandInInput, brand);
		// }
		// if (!ssInInput.trim().equals("")) {
		// input = input.replace(ssInInput, sourceSystem);
		// }
		return input;
	}

	// private String getSourceSystem(String input, String esnKey) {
	// String headerTagStr = esnKey + ":Header>";
	// String ssInInput = "";
	// if (input.contains(headerTagStr)) {
	// String headerSubStr = input.substring(input.indexOf("<" + headerTagStr) +
	// headerTagStr.length() + 1,
	// input.indexOf("</" + headerTagStr));
	// ssInInput = getConsumerInfoVal(headerSubStr, esnKey);
	// }
	// return ssInInput;
	// }

	// private String getConsumerInfoVal(String headerSubStr, String esnKey) {
	// String consumerInfoTagStr = esnKey + ":ConsumerInfo>";
	// String nameTagStr = esnKey + ":name>";
	// String valTagStr = esnKey + ":Value>";
	// String ssInInput = "";
	// if (headerSubStr.contains(consumerInfoTagStr)) {
	// String consumerInfoSubStr = headerSubStr.substring(
	// headerSubStr.indexOf("<" + consumerInfoTagStr) +
	// consumerInfoTagStr.length() + 1,
	// headerSubStr.indexOf("</" + consumerInfoTagStr));
	// if (consumerInfoSubStr.contains(nameTagStr)) {
	// String name = consumerInfoSubStr.substring(
	// consumerInfoSubStr.indexOf("<" + nameTagStr) + nameTagStr.length() + 1,
	// consumerInfoSubStr.indexOf("</" + nameTagStr));
	// if (name.equals("sourceSystem")) {
	// ssInInput = consumerInfoSubStr.substring(
	// consumerInfoSubStr.indexOf("<" + valTagStr) + valTagStr.length() + 1,
	// consumerInfoSubStr.indexOf("</" + valTagStr));
	// } else {
	// String subStr = headerSubStr.substring(
	// headerSubStr.indexOf("</" + consumerInfoTagStr) +
	// consumerInfoTagStr.length() + 2);
	// ssInInput = getConsumerInfoVal(subStr, esnKey);
	// }
	// }
	// }
	// return ssInInput;
	// }

	// private String getBrand(String input, String esnKey) {
	// String prdSpecTagStr = esnKey + ":ProductSpecification>";
	// String brandTagStr = esnKey + ":brand>";
	// String brand = "";
	// if (input.contains(prdSpecTagStr)) {
	// String prdSpecStr = input.substring(input.indexOf("<" + prdSpecTagStr) +
	// prdSpecTagStr.length() + 1,
	// input.indexOf("</" + prdSpecTagStr));
	// if (prdSpecStr.contains(brandTagStr)) {
	// brand = prdSpecStr.substring(prdSpecStr.indexOf("<" + brandTagStr) +
	// brandTagStr.length() + 1,
	// prdSpecStr.indexOf("</" + brandTagStr));
	// }
	// }
	// return brand;
	// }

	// private Map<String, String> getSimAndPin(String input, String esnKey) {
	// Map<String, String> map = new HashMap<String, String>();
	// String simInInput = "";
	// String pinInInput = "";
	// String supportingResourceTagStr = esnKey + ":SupportingResources>";
	// String resourceTypeTagStr = esnKey + ":resourceType>";
	// String serialNumTagStr = esnKey + ":serialNumber>";
	// if (input.contains("<" + supportingResourceTagStr)) {
	// String supportingResourceStr = input.substring(
	// input.indexOf("<" + supportingResourceTagStr) +
	// supportingResourceTagStr.length() + 1,
	// input.indexOf("</" + supportingResourceTagStr));
	// if (supportingResourceStr.contains("<" + resourceTypeTagStr)) {
	// String resouceType = supportingResourceStr.substring(
	// supportingResourceStr.indexOf("<" + resourceTypeTagStr) +
	// resourceTypeTagStr.length() + 1,
	// supportingResourceStr.indexOf("</" + resourceTypeTagStr));
	// if (resouceType.equals("AIRTIME_CARD")) {
	// pinInInput = supportingResourceStr.substring(
	// supportingResourceStr.indexOf("<" + serialNumTagStr) +
	// serialNumTagStr.length() + 1,
	// supportingResourceStr.indexOf("</" + serialNumTagStr));
	// } else if (resouceType.equals("SIM_CARD")) {
	// simInInput = supportingResourceStr.substring(
	// supportingResourceStr.indexOf("<" + serialNumTagStr) +
	// serialNumTagStr.length() + 1,
	// supportingResourceStr.indexOf("</" + serialNumTagStr));
	// }
	// }
	// String subStr = input
	// .substring(input.indexOf("</" + supportingResourceTagStr) +
	// supportingResourceTagStr.length() + 2);
	// if ((pinInInput.equals("") || simInInput.equals("")) &&
	// subStr.contains(supportingResourceTagStr)) {
	// map = getSimAndPin(subStr, esnKey);
	// }
	// if (!map.containsKey("sim") || map.get("sim").trim().equals("")) {
	// map.put("sim", simInInput);
	// }
	// if (!map.containsKey("pin") || map.get("pin").trim().equals("")) {
	// map.put("pin", pinInInput);
	// }
	// }
	// return map;
	// }

	public String parseOutput(String output) throws ParserConfigurationException, SAXException, IOException {
		String executionStatus = "false";
		if (output.contains("http://www.tracfone.com/CommonTypes")) {
			String subString = output.substring(0, output.indexOf("http://www.tracfone.com/CommonTypes"));
			String nameSpace = subString.substring(subString.lastIndexOf("xmlns") + 6, subString.length() - 2);
			if (output.contains("<" + nameSpace + ":code>")) {
				String val = output.substring(output.indexOf("<" + nameSpace + ":code>") + 13,
						output.indexOf("</" + nameSpace + ":code>"));
				if (val != null && "0".equals(val)) {
					executionStatus = "true";
				}
			} else {
				executionStatus = "true";
			}
		}
		// subString = populateNameSpace(subString, nameSpaceMap);
		return executionStatus;
	}

}

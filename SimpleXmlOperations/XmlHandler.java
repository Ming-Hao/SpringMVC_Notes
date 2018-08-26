package com.ku.TestSystem.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ku.TestSystem.DataBeans.ImageFileInfo;

//https://www.cnblogs.com/wangchenyang/archive/2011/08/23/2150530.html
public class XmlHandler {

	static public String rootName = "Images";
	static public String nodeName = "img";

	public List<ImageFileInfo> ReadXml(String filename, String omit_file) {
		if (omit_file.isEmpty())
			return ReadXml(filename);

		List<ImageFileInfo> infos = new ArrayList<ImageFileInfo>();
		System.out.println(filename);

		Element root = null;

		File f = new File(filename);

		// documentBuilder為抽象不能直接實例化(將XML文檔轉換為DOM文檔)
		DocumentBuilder db = null;
		DocumentBuilderFactory dbf = null;
		try {

			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();

			Document dt = (Document) db.parse(f);
			// root
			root = (Element) dt.getDocumentElement();

			System.out.println("ROOT： " + root.getNodeName());

			NodeList childNodes = ((Node) root).getChildNodes();

			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node1 = (Node) childNodes.item(i);
				if ("img".equals(node1.getNodeName()) && !omit_file.equals(node1.getTextContent())) {
					NamedNodeMap atts = node1.getAttributes();

					ImageFileInfo info = new ImageFileInfo();
					info.setPath(node1.getTextContent());
					info.setType(atts.getNamedItem("Type").toString());

					infos.add(info);
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		DeleteNode(filename, omit_file);
		return infos;
	}

	public List<ImageFileInfo> ReadXml(String filename) {
		List<ImageFileInfo> infos = new ArrayList<ImageFileInfo>();
		System.out.println(filename);

		Element root = null;

		File f = new File(filename);

		// documentBuilder為抽象不能直接實例化(將XML文檔轉換為DOM文檔)
		DocumentBuilder db = null;
		DocumentBuilderFactory dbf = null;
		try {

			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();

			Document dt = (Document) db.parse(f);
			// root
			root = (Element) dt.getDocumentElement();

			System.out.println("ROOT： " + root.getNodeName());

			NodeList childNodes = ((Node) root).getChildNodes();

			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node1 = (Node) childNodes.item(i);
				if ("img".equals(node1.getNodeName())) {
					NamedNodeMap atts = node1.getAttributes();

					ImageFileInfo info = new ImageFileInfo();
					info.setPath(node1.getTextContent());

					info.setType(atts.getNamedItem("Type").getNodeValue());
					info.setSize(Long.parseLong(atts.getNamedItem("Size").getNodeValue()));
					info.setFilename(atts.getNamedItem("FileName").getNodeValue());
					info.setName(atts.getNamedItem("Name").getNodeValue());

					infos.add(info);
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return infos;
	}

	public void SaveXml(org.w3c.dom.Document doc, String filepath) {
		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(doc);
			transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			PrintWriter pw = new PrintWriter(new FileOutputStream(filepath));
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);
			System.out.println("generate xml file !");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//https://stackoverflow.com/questions/14255064/removechild-how-to-remove-indent-too
	public void DeleteNode(String filepath, String queryNodeName) {

		File f = new File(filepath);

		DocumentBuilderFactory dbf = null;
		DocumentBuilder db = null;
		Document dt = null;
		Element root = null;

		try {

			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			if (f.exists()) {
				dt = (Document) db.parse(f);
				root = (Element) dt.getDocumentElement();
			} else {
				dt = db.newDocument();
				root = dt.createElement(XmlHandler.rootName);
				dt.appendChild(root);
			}
			NodeList childNodes = ((Node) root).getChildNodes();
			Element readyDeleteNode = null;
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node1 = (Node) childNodes.item(i);

				NamedNodeMap atts = node1.getAttributes();
				if ("img".equals(node1.getNodeName())) {

					if (atts.getNamedItem("Name").getNodeValue().equals(queryNodeName)) {

						readyDeleteNode = (Element) node1;
						break;
					}
				}
			}
			if (readyDeleteNode != null) {
				Node prev = readyDeleteNode.getPreviousSibling();
				if (prev != null && 
					prev.getNodeType() == Node.TEXT_NODE &&
					prev.getNodeValue().trim().length() == 0) {
					root.removeChild(prev);
					}
				root.removeChild(readyDeleteNode);
			} else {
				System.out.println("remove fails");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (dt != null) {
			SaveXml(dt, filepath);
		} else {
			System.out.println("dt is null, please check!");
		}

	}

	public void AppendXml(String filepath, List<ImageFileInfo> imgInfos) {

		File f = new File(filepath);

		DocumentBuilderFactory dbf = null;
		DocumentBuilder db = null;
		Document dt = null;
		Element root = null;

		try {

			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			if (f.exists()) {
				dt = (Document) db.parse(f);
				root = (Element) dt.getDocumentElement();
			} else {
				dt = db.newDocument();
				root = dt.createElement(XmlHandler.rootName);
				dt.appendChild(root);
			}

			for (int i = 0; i < imgInfos.size(); i++) {
				ImageFileInfo info = imgInfos.get(i);
				Element currentImg = dt.createElement(XmlHandler.nodeName);
				currentImg.setAttribute("Type", info.getType());
				currentImg.setAttribute("Size", String.valueOf(info.getSize()));
				currentImg.setAttribute("Name", info.getName());
				currentImg.setAttribute("FileName", info.getFilename());
				currentImg.setAttribute("Path", info.getPath());
				currentImg.setTextContent(info.getPath());
				root.appendChild(currentImg);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		if (dt != null) {
			SaveXml(dt, filepath);
		} else {
			System.out.println("dt is null, please check!");
		}
	}


}

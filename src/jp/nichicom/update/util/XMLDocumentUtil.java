package jp.nichicom.update.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jp.nichicom.update.task.DataUpdateTask;
import jp.nichicom.update.task.ModuleCopyTask;
import jp.nichicom.update.task.SchemaChangeTask;

// java 5�ł͎g�p�ł��Ȃ�
//import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML�h�L�������g����N���X
 * @version 1.01 2005/10/13
 * @author shin fujihara
 * 1.01 java1.5 �ɑΉ�
 */
public class XMLDocumentUtil {
    
	/**
	 * ��荞��XML�t�@�C����
	 */
	private String xmlFileName = "";
	/**
	 * �h�L�������g�̃��[�g�v�f
	 */
	private Element root;
	
	public XMLDocumentUtil(String documentPath) throws Exception{
		try{
			// �h�L�������g�r���_�[�t�@�N�g���𐶐�
		    DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
	        // �h�L�������g�r���_�[�𐶐�
	        DocumentBuilder builder = dbfactory.newDocumentBuilder();
	        // �p�[�X�����s����Document�I�u�W�F�N�g���擾
	        Document doc = builder.parse(documentPath);
	        // ���[�g�v�f���擾
	        root = doc.getDocumentElement();
		} catch (Exception e){
			Log.warning("XMLDocument create Error : " + e.getLocalizedMessage());
			throw e;
		}
		xmlFileName = documentPath;
	}
	
	/**
	 * �A�b�v�f�[�g���s�^�X�N���p�[�X����B<br>
	 * �p�[�X���ʂ̃^�X�N��ArrayList�ɐݒ肵�A�ԋp����B
	 * @return �p�[�X��̃^�X�N
	 * @throws Exception ���s���G���[
	 */
	public ArrayList parseTask() throws Exception{
		ArrayList taskArray = new ArrayList();
		try {
			NodeList list = root.getElementsByTagName("system_version");
			
			for(int i = 0; i < list.getLength(); i++){
				ModuleCopyTask copyTask = new ModuleCopyTask();
				
				Element el = (Element)list.item(i);
				
				copyTask.setVersionNo(el.getAttribute("no"));
				copyTask.setWindowsName(getNodeValue(el,"version_name/windows"));
				copyTask.setMacName(getNodeValue(el,"version_name/mac"));
				NodeList nodelist = el.getElementsByTagName("task");
				Node n;
				
				for(int j = 0; j < nodelist.getLength(); j++){
					n = nodelist.item(j);
					copyTask.putTask(getNodeValue(n,"module_url"),getNodeValue(n,"copy_dir"));
				}
				
				taskArray.add(copyTask);
			}
			
			list = root.getElementsByTagName("schema_version");
			
			for(int i = 0; i < list.getLength(); i++){
				SchemaChangeTask schemaTask = new SchemaChangeTask();
					
				Element el = (Element)list.item(i);
				
				schemaTask.setVersionNo(el.getAttribute("no"));
				
				NodeList nodelist = el.getElementsByTagName("task");
				Node n;
				for(int j = 0; j < nodelist.getLength(); j++){
					n = nodelist.item(j);
					schemaTask.putTask(getNodeValue(n,"query"));
				}
				taskArray.add(schemaTask);
			}
			
			list = root.getElementsByTagName("data_version");
			
			for(int i = 0; i < list.getLength(); i++){
				DataUpdateTask dataTask = new DataUpdateTask();
				
				Element el = (Element)list.item(i);
				
				dataTask.setVersionNo(el.getAttribute("no"));
				
				NodeList nodelist = el.getElementsByTagName("task");
				Node n;
				for(int j = 0; j < nodelist.getLength(); j++){
					n = nodelist.item(j);
					dataTask.putTask(getNodeValue(n,"pre_query"),getNodeValue(n,"param_query"),getNodeValue(n,"data_url"));
				}
				
				taskArray.add(dataTask);
			}
		
		} catch (Exception e) {
			Log.warning("XML Parse Error : " + "update task�t�@�C���̃p�[�X�Ɏ��s���܂����B");
			throw e;
		}
		return taskArray;
	}
	
	/**
	 * ��荞�񂾃h�L�������g�ɑ΂��AXPath�𔭍s����B
	 * @param xpath XPath
	 * @return XPath�œ���ꂽ�l
	 * @throws Exception ���s���G���[
	 */
	public String getNodeValue(String xpath) throws Exception{
		return getNodeValue(root,xpath);
	}
	/**
	 * �w�肵���m�[�h�̒l���擾����B
	 * @param n XPath�����s����m�[�h
	 * @param xpath ���s����XPathr
	 * @return XPath�œ���ꂽ�l
	 * @throws Exception ���s���G���[
	 */
	private String getNodeValue(Node n,String xpath) throws Exception {
		String result = "";
		
		int index = xpath.indexOf("/");
		
		if(index != -1){
			NodeList nodelist = ((Element)n).getElementsByTagName(xpath.substring(0,index));
			return getNodeValue(nodelist.item(0),xpath.substring(index + 1));
		}
		
		NodeList nodelist = ((Element)n).getElementsByTagName(xpath);
		Node node;
		
		for(int i = 0; i < nodelist.getLength(); i++){
			node = nodelist.item(i);
			if(node.getFirstChild() != null){
				result = node.getFirstChild().getNodeValue();
			}
		}
		
		return result;
	}
	
	/**
	 * �ݒ�t�@�C������o�[�W���������擾����B
	 * @param properities
	 * @param properity
	 * @return
	 * @throws Exception
	 */
	public String getNodeValue(String properities,String properity) throws Exception {
		String result = "";
		Node node = getNode(properities,properity);
		
		if(node != null){
			result = node.getNodeValue();
		}
		
		return result;
	}
	
	
	/**
	 * �ݒ�t�@�C������o�[�W���������擾����B
	 * @param properities
	 * @param properity
	 * @return
	 * @throws Exception
	 */
	public void setNodeValue(String properities,String properity,String value) throws Exception {
		Node node = getNode(properities,properity);
		
		if(node != null){
			node.setNodeValue(value);
		}
	}
	
	/**
	 * �ݒ�t�@�C������w�肳�ꂽ�m�[�h���擾����B
	 * @param properities
	 * @param properity
	 * @return �w��m�[�h��������Ȃ��ꍇ�́Anull�����^�[��
	 */
	private Node getNode(String properities,String properity){
		NodeList list = root.getElementsByTagName("properties");
		Node n;
		for(int i = 0; i < list.getLength(); i++){
			n = list.item(i);
			NamedNodeMap map = n.getAttributes();
			if(properities.equals(map.getNamedItem("id").getNodeValue())){
				NodeList list2 = ((Element)n).getElementsByTagName("property");
				for(int j = 0; j < list2.getLength(); j++){
					Node node = list2.item(j);
					NamedNodeMap map2 = node.getAttributes();
					if(properity.equals(map2.getNamedItem("id").getNodeValue())){
						return node.getFirstChild();
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * �h�L�������g��XML�t�@�C���ŕۑ�����B
	 * @throws Exception ���s���G���[
	 */
	public void store() throws Exception{
		store(xmlFileName);
	}
	/**
	 * �w�肵���p�X�ցA�h�L�������g��XML�t�@�C���Ƀp�[�X���ۑ�����B
	 * @param fileName xml�t�@�C����
	 * @throws Exception ���s���G���[
	 */
	public void store(String fileName) throws Exception{
        File newXML = new File(fileName);
        Transformer tm;
		try {
			tm = TransformerFactory.newInstance().newTransformer();
			tm.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
	        tm.transform(new DOMSource(root), new StreamResult(new FileOutputStream(newXML)));
		} catch (Exception e) {
			Log.warning("store Error : " + fileName);
			throw e;
		}
        newXML = null;
	}
	
}

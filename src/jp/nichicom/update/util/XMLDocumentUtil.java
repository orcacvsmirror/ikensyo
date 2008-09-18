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

// java 5では使用できない
//import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XMLドキュメント操作クラス
 * @version 1.01 2005/10/13
 * @author shin fujihara
 * 1.01 java1.5 に対応
 */
public class XMLDocumentUtil {
    
	/**
	 * 取り込んだXMLファイル名
	 */
	private String xmlFileName = "";
	/**
	 * ドキュメントのルート要素
	 */
	private Element root;
	
	public XMLDocumentUtil(String documentPath) throws Exception{
		try{
			// ドキュメントビルダーファクトリを生成
		    DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
	        // ドキュメントビルダーを生成
	        DocumentBuilder builder = dbfactory.newDocumentBuilder();
	        // パースを実行してDocumentオブジェクトを取得
	        Document doc = builder.parse(documentPath);
	        // ルート要素を取得
	        root = doc.getDocumentElement();
		} catch (Exception e){
			Log.warning("XMLDocument create Error : " + e.getLocalizedMessage());
			throw e;
		}
		xmlFileName = documentPath;
	}
	
	/**
	 * アップデート実行タスクをパースする。<br>
	 * パース結果のタスクをArrayListに設定し、返却する。
	 * @return パース後のタスク
	 * @throws Exception 実行時エラー
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
			Log.warning("XML Parse Error : " + "update taskファイルのパースに失敗しました。");
			throw e;
		}
		return taskArray;
	}
	
	/**
	 * 取り込んだドキュメントに対し、XPathを発行する。
	 * @param xpath XPath
	 * @return XPathで得られた値
	 * @throws Exception 実行時エラー
	 */
	public String getNodeValue(String xpath) throws Exception{
		return getNodeValue(root,xpath);
	}
	/**
	 * 指定したノードの値を取得する。
	 * @param n XPathを実行するノード
	 * @param xpath 発行するXPathr
	 * @return XPathで得られた値
	 * @throws Exception 実行時エラー
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
	 * 設定ファイルからバージョン情報を取得する。
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
	 * 設定ファイルからバージョン情報を取得する。
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
	 * 設定ファイルから指定されたノードを取得する。
	 * @param properities
	 * @param properity
	 * @return 指定ノードが見つからない場合は、nullをリターン
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
	 * ドキュメントをXMLファイルで保存する。
	 * @throws Exception 実行時エラー
	 */
	public void store() throws Exception{
		store(xmlFileName);
	}
	/**
	 * 指定したパスへ、ドキュメントをXMLファイルにパースし保存する。
	 * @param fileName xmlファイル名
	 * @throws Exception 実行時エラー
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

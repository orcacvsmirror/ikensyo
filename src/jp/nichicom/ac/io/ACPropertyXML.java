package jp.nichicom.ac.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.VRLinkedHashMap;
import jp.nichicom.vr.util.VRMap;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XML形式のプロパティファイルアクセスライブラリです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */

public class ACPropertyXML extends VRLinkedHashMap {
    private String path;
    private String idAttribute = "id";
    private String propertiesNode = "properities";
    private String propertyNode = "properity";

    /**
     * ノードの属性値を返します。
     * 
     * @param node 処理対象のノード
     * @param attributeName 属性名
     * @return ノードの属性値
     */
    protected String getAttributeValue(Node node, String attributeName) {
        if (!node.hasAttributes()) {
            return null;
        }
        Node attributeNode = node.getAttributes().getNamedItem(attributeName);
        // nullチェック
        if (attributeNode == null) {
            return null;
        }
        return attributeNode.getNodeValue();
    }

    /**
     * ノードが持つ最初のテキスト要素を文字列として返します。
     * 
     * @param node 処理対象のノード
     * @return テキスト要素値
     */
    protected String getNodeTextValue(Node node) {
        // nullチェック
        if (node == null) {
            return null;
        }
        Node firstNode = node.getFirstChild();
        // nullチェック
        if (firstNode == null) {
            return null;
        }
        return firstNode.getNodeValue();
    }

    /**
     * ノードリストを解析します。
     * 
     * @param container プロパティデータの格納先
     * @param nodes 解析対象のノードリスト
     */
    protected void readNodes(VRMap container, NodeList nodes) {
        if ((nodes == null) || (container == null)) {
            return;
        }
        Node node;
        // ルートノードの子要素を全走査
        for (int i = 0; (node = nodes.item(i)) != null; i++) {
            String key = getAttributeValue(node, getIdAttribute());
            if ((key != null) && (!"".equals(key))) {
                String nodeName = node.getNodeName();
                // ＜properity＞ノード
                if (getPropertyNode().equals(nodeName)
                        ||"property".equals(nodeName)  //旧版誤字対策
                        ) {
                    String value = getNodeTextValue(node);
                    if (value != null) {
                        container.setData(key, value);
                    }
                } else if (node.hasChildNodes()) {
                    VRMap childMap = new VRLinkedHashMap();
                    readNodes(childMap, node.getChildNodes());
                    container.setData(key, childMap);
                }
            }
        }

    }

    /**
     * コンストラクタです。
     * 
     * @param path ファイルパス
     */
    public ACPropertyXML(String path) {
        super();
        setPath(path);
    }

    /**
     * ファイルパスを返します。
     * 
     * @return ファイルパス
     */
    public String getPath() {
        return path;
    }

    /**
     * バインドパスに対応するプロパティ値が定義されているかを返します。
     * 
     * @param bindPath バインドパス
     * @return バインドパスに対応するプロパティ値が定義されているか
     * @throws ParseException バインドパスの解析に失敗
     */
    public boolean hasValueAt(String bindPath) throws ParseException {
        return VRBindPathParser.has(bindPath, this);
    }

    /**
     * バインドパスに対応するプロパティ値を文字列で返します。
     * 
     * @param bindPath バインドパス
     * @return プロパティ値
     * @throws ParseException バインドパスの解析に失敗
     */
    public String getValueAt(String bindPath) throws ParseException {
        Object obj = VRBindPathParser.get(bindPath, this);
        if (obj instanceof String) {
            return (String) obj;
        }
        throw new ArrayIndexOutOfBoundsException(bindPath);
    }
    
    /**
     * バインドパスに対応するプロパティ集合をMapとして返します。
     * 
     * @param bindPath バインドパス
     * @return プロパティ集合
     * @throws ParseException バインドパスの解析に失敗
     */
    public Map getValuesAt(String bindPath) throws ParseException {
        Object obj = VRBindPathParser.get(bindPath, this);
        if (obj instanceof Map) {
            return (Map) obj;
        }
        throw new ArrayIndexOutOfBoundsException(bindPath);
    }

    
    /**
     * バインドパスに対応するプロパティ値を文字列で返します。
     * 
     * @param bindPath バインドパス
     * @param defaultValue デフォルト値
     * @return プロパティ値
     * @throws ParseException バインドパスの解析に失敗
     */
    public String getValueAt(String bindPath, String defaultValue)  {
        try{
            return getValueAt(bindPath);
        }catch(Exception ex){
            return defaultValue;
        }
    }

    /**
     * バインドパスに対応するプロパティ値を文字列で設定します。
     * 
     * @param bindPath バインドパス
     * @param value プロパティ値
     * @throws ParseException バインドパスの解析に失敗
     */
    public void setValueAt(String bindPath, String value) throws ParseException {
        if (!VRBindPathParser.set(bindPath, this, value)) {
            throw new ParseException(bindPath, 0);
        }
    }

    /**
     * バインドパスに対応するプロパティ値を文字列で設定します。 <br />
     * 指定したパスが解析構造を持つ場合、標準のソース形式で必要階層を作成します。
     * 
     * @param bindPath バインドパス
     * @param value プロパティ値
     * @throws ParseException パス解析例外
     */
    public void setForceValueAt(String bindPath, String value)
            throws ParseException {
        ArrayList elements = VRBindPathParser.parsePath(bindPath);
        // 解析完了
        if (elements.isEmpty()) {
            return;
        }

        VRBindSource currentSource = this;
        int end = elements.size();
        int lastElement = end - 1;
        for (int i = 0; i < end; i++) {
            Object element = elements.get(i);

            if (element == VRBindPathParser.CURRENT_ELEMENT) {
                continue;
            }

            if (element instanceof Integer) {
                // インデックスは認めない
                throw new ParseException(getPath(), i);
            } else {
                if (i == lastElement) {
                    currentSource.setData(element, value);
                    return;
                }

                Object childObj = currentSource.getData(element);
                if (childObj == null) {
                    // 自動生成
                    VRMap child = new VRLinkedHashMap();
                    currentSource.setData(element, child);
                    childObj = child;
                }
                currentSource = (VRMap) childObj;
            }
        }
        throw new ParseException(bindPath, 0);

    }

    /**
     * XMLファイルを読み込みます。
     * 
     * @throws FactoryConfigurationError XMLドキュメントファクトリ例外
     * @throws ParserConfigurationException XMLパーサ例外
     * @throws IOException 入出力例外
     * @throws SAXException SAX解析例外
     */
    public void read() throws SAXException, IOException,
            ParserConfigurationException, FactoryConfigurationError {
        // ドキュメントビルダーファクトリを生成
        // ドキュメントビルダーを生成
        // パースを実行してDocumentオブジェクトを取得
        // ルート要素の取得
        // 子要素を含んだノードを列挙
        NodeList rootNodes = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(getPath()).getDocumentElement()
                .getChildNodes();

        this.clearData();
        // ルートノードの子要素を全走査
        readNodes(this, rootNodes);
    }

    /**
     * XMLファイルを書き出します。
     * 
     * @throws FactoryConfigurationError XMLドキュメントファクトリ例外
     * @throws ParserConfigurationException XMLパーサ例外
     * @throws DOMException DOM解析例外
     * @throws TransformerFactoryConfigurationError XML変換ファクトリ例外
     * @throws TransformerException XML変換例外
     * @throws FileNotFoundException ファイル例外
     * @throws TransformerConfigurationException XML変換設定例外
     */
    public void write() throws DOMException, ParserConfigurationException,
            FactoryConfigurationError, TransformerConfigurationException,
            FileNotFoundException, TransformerException,
            TransformerFactoryConfigurationError {

        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().getDOMImplementation().createDocument("",
                        "doc", null);

        writeNodes(document, this, document.getDocumentElement());

        File newXML = new File(getPath());
        Transformer tm = TransformerFactory.newInstance().newTransformer();
        tm.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
        tm.transform(new DOMSource(document), new StreamResult(
                new FileOutputStream(newXML)));

    }

    /**
     * ファイルパスを設定します。
     * 
     * @param path ファイルパス
     */
    protected void setPath(String path) {
        this.path = path;
    }

    /**
     * ノード集合を出力します。
     * 
     * @param document ドキュメント
     * @param container 親マップ
     * @param parent 親要素
     */
    protected void writeNodes(Document document, VRMap container,
            Element parent) {

        Iterator it = container.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();

            String val;
            Object valObj = entry.getValue();
            if (valObj instanceof VRMap) {
                Element childElem = document.createElement(getPropertiesNode());
                childElem.setAttribute(getIdAttribute(), String.valueOf(entry
                        .getKey()));
                writeNodes(document, (VRMap) valObj, childElem);
                parent.appendChild(childElem);
                continue;
            } else if (valObj instanceof String) {
                val = (String) valObj;
            } else {
                continue;
            }

            Element properity = document.createElement(getPropertyNode());
            properity.setAttribute(getIdAttribute(), String.valueOf(entry.getKey()));
            properity.appendChild(document.createTextNode(val));
            parent.appendChild(properity);
        }

    }

    /**
     * プロパティ集合ノード名を返します。
     * 
     * @return プロパティ集合ノード名
     */
    public String getPropertiesNode() {
        return propertiesNode;
    }

    /**
     * プロパティノード名を返します。
     * 
     * @return プロパティノード名
     */
    public String getPropertyNode() {
        return propertyNode;
    }

    /**
     * プロパティ集合ノード名を設定します。
     * 
     * @param propertyNode プロパティノード名
     */
    public void setPropertyNode(String propertyNode) {
        this.propertyNode = propertyNode;
    }

    /**
     * プロパティ集合ノード名を設定します。
     * 
     * @param properitiesNode プロパティ集合ノード名
     */
    public void setPropertiesNode(String properitiesNode) {
        this.propertiesNode = properitiesNode;
    }

    /**
     * ID属性名を返します。
     * 
     * @return ID属性名
     */
    public String getIdAttribute() {
        return idAttribute;
    }

    /**
     * ID属性名を設定します。
     * 
     * @param idAttribute ID属性名
     */
    public void setIdAttribute(String idAttribute) {
        this.idAttribute = idAttribute;
    }

    /**
     * XMLファイルを書き出し可能かを返します。
     * 
     * @return XMLファイルを書き出し可能か
     */
    public boolean writeWithCheck() {
        File f = new File(getPath());
        if (f.exists() &&(!f.canWrite())) {
            ACMessageBox.show(f.getAbsolutePath() + ACConstants.LINE_SEPARATOR
                    + "に書き込めません。ファイルに書き込み権限を与えてください。");
            return false;
        }
        try{
            write();
        }catch(Exception ex){
            ACMessageBox.show(f.getAbsolutePath() + ACConstants.LINE_SEPARATOR
                    + "への書き込みに失敗しました。書き込み権限や有効なパスであるか確認してください。");
            return false;
        }
        
        return true;
    }

    /**
     * 指定のパスにファイルが存在するかを返します。
     * 
     * @return 指定のパスにファイルが存在するか
     */
    public boolean isFile() {
        return new File(getPath()).isFile();
    }

    /**
     * 読み込み可能であるかを返します。
     * 
     * @return 読み込み可能であるか
     */
    public boolean canRead() {
        return new File(getPath()).canRead();
    }

    /**
     * 書き込み可能であるかを返します。
     * 
     * @return 書き込み可能であるか
     */
    public boolean canWrite() {
        return new File(getPath()).canWrite();
    }

}

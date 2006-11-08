/*
 * Project code name "ORCA"
 * 給付管理台帳ソフト QKANCHO（JMA care benefit management software）
 * Copyright(C) 2002 JMA (Japan Medical Association)
 *
 * This program is part of "QKANCHO (JMA care benefit management software)".
 *
 * This program is distributed in the hope that it will be useful
 * for further advancement in medical care, according to JMA Open
 * Source License, but WITHOUT ANY WARRANTY.
 * Everyone is granted permission to use, copy, modify and
 * redistribute this program, but only under the conditions described
 * in the JMA Open Source License. You should have received a copy of
 * this license along with this program. If not, stop using this
 * program and contact JMA, 2-28-16 Honkomagome, Bunkyo-ku, Tokyo,
 * 113-8621, Japan.
 *****************************************************************
 * アプリ: QKANCHO
 * 開発者: 田中　統蔵
 * 作成日: 2006/04/25  日本コンピューター株式会社 田中　統蔵 新規作成
 * 更新日: ----/--/--
 * システム 主治医意見書 (I)
 * サブシステム 帳票 (P)
 * プロセス 簡易帳票カスタマイズツール (001)
 * プログラム 簡易帳票カスタマイズツール (IP001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.pdf.ip.ip001;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.im.InputSubset;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.table.ACTableCellViewer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.io.ACFileUtilities;
import jp.nichicom.ac.io.ACFileUtilitiesCreateResult;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.component.AbstractVRTextField;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.affair.IkenshoFrameEventProcesser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 簡易帳票カスタマイズツール(IP001)
 */
public class IP001 extends IP001Event {
    /**
     * コンストラクタです。
     */
    public IP001() {
    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        super.initAffair(affair);
        initAction(affair);
    }

    /**
     * 初期化処理を行ないます。
     * 
     * @param affair 業務情報
     * @throws Exception 処理例外
     */
    protected void initAction(ACAffairInfo affair) throws Exception {
        // 初期化処理を実行する。
        // ステータスバーに「編集する帳票を開いてください。」と表示する。
        setStatusText("編集する帳票を開いてください。");
        setTitle("医見書　簡易帳票カスタマイズツール");
        getFormatID().setFormat(IP001FormatIDFormat.getInstance());

        Component cmp;
        cmp= getFormatText().getCellEditorComponent();
        if (cmp instanceof ACTextField) {
            ((ACTextField) cmp).setIMEMode(InputSubset.KANJI);
            ((ACTextField) cmp).setMaxLength(9999);
        }
        cmp= getFormatX().getCellEditorComponent();
        if (cmp instanceof AbstractVRTextField) {
            ((ACTextField) cmp).setCharType(VRCharType.ONLY_DIGIT);
            ((ACTextField) cmp).setMaxLength(4);
        }
        cmp= getFormatY().getCellEditorComponent();
        if (cmp instanceof AbstractVRTextField) {
            ((ACTextField) cmp).setCharType(VRCharType.ONLY_DIGIT);
            ((ACTextField) cmp).setMaxLength(4);
        }
        cmp= getFormatWidth().getCellEditorComponent();
        if (cmp instanceof AbstractVRTextField) {
            ((ACTextField) cmp).setCharType(VRCharType.ONLY_DIGIT);
            ((ACTextField) cmp).setMaxLength(4);
        }
        cmp= getFormatHeight().getCellEditorComponent();
        if (cmp instanceof AbstractVRTextField) {
            ((ACTextField) cmp).setCharType(VRCharType.ONLY_DIGIT);
            ((ACTextField) cmp).setMaxLength(4);
        }
        cmp= getFormatSize().getCellEditorComponent();
        if (cmp instanceof AbstractVRTextField) {
            ((ACTextField) cmp).setCharType(VRCharType.ONLY_DIGIT);
            ((ACTextField) cmp).setMaxLength(4);
        }
        cmp= getFormatBorderWidth().getCellEditorComponent();
        if (cmp instanceof AbstractVRTextField) {
            ((ACTextField) cmp).setCharType(new VRCharType(
                    "CHAR_TYPE_ONE_DECIMAL_DOUBLE",
                    "^(\\d{0,2})|((\\d{1,2})(\\.\\d{0,1}))$"));
            ((ACTextField) cmp).setMaxLength(4);
        }
        getFormats().setColumnSort(false);
        
    }

    // コンポーネントイベント

    /**
     * 「帳票を開く」イベントです。
     * 
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void openActionPerformed(ActionEvent e) throws Exception {
        if(getModified()){
            if (ACMessageBox.show("編集されています。保存せずに別の帳票を開いてもよろしいですか？",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                // OK以外を選択した場合
                // 処理を中断します。
                return;
            }
        }

        
        // 編集する帳票を開く。
        // 帳票選択ダイアログを表示する。
        File f = new IP001001().showModal(getOpendFile());
        if (f != null) {
            // 帳票が選択された場合
            // 開いた帳票のファイルオブジェクト(opendFile)、ドキュメントオブジェクト(opendDocument)をそれぞれ設定する。
            setOpendFile(f);
            IP001FormatIDFormat.getInstance().setTargetFile(f);
            
            // 帳票内容を解析し、帳票定義体集合(formatElements)に格納する。
            // 定義体IDをマッピングハッシュ(idReplaceMap)にしたがって変換し、定義体項目一覧(formats)に展開する。
            String fileName = f.getName().toLowerCase();
            if (fileName.endsWith("newikensho1.xml")) {
                fileName = "主治医意見書1ページ目";
            } else if (fileName.endsWith("newikensho2.xml")) {
                fileName = "主治医意見書2ページ目";
            } else if (fileName.endsWith("shijisho.xml")) {
                fileName = "訪問看護指示書（医療機関）";
            } else if (fileName.endsWith("shijishob.xml")) {
                fileName = "訪問看護指示書（介護老人保健施設）";
            } else if (fileName.endsWith("seikyuichiran.xml")) {
                fileName = "請求書一覧";
            } else if (fileName.endsWith("seikyuichirantotal.xml")) {
                fileName = "請求書合計";
            } else if (fileName.endsWith("soukatusho.xml")) {
                fileName = "主治医意見書作成料・検査料請求(総括)書";
            } else if (fileName.endsWith("ikenshomeisai.xml")) {
                fileName = "主治医意見書作成料請求(明細)書";
            } else if (fileName.endsWith("csvfileoutputpatientlist.xml")) {
                fileName = "ＣＳＶファイル提出患者一覧";
            } else if (fileName.endsWith("patientlist.xml")) {
                fileName = "登録患者一覧";
            } else if (fileName.endsWith("seikyuikenshoichiran.xml")) {
                fileName = "請求対象意見書一覧";
            } else if (fileName.endsWith("ikenshoshien1.xml")) {
                fileName = "医師意見書1ページ目";
            } else if (fileName.endsWith("ikenshoshien2.xml")) {
                fileName = "医師意見書2ページ目";

            }
            // 変更の有無(modified)をfalseとする。
            setModified(false);
            // 帳票保存と印刷ボタンを有効にする。
            setState_FILE_OPENED();
            // ステータスバーに、開いた帳票の名称を表示する。
            setStatusText("「" + fileName + "」を編集中");

            readFoamrt();
        }
    }

    /**
     * 「印刷プレビュー」イベントです。
     * 
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void printActionPerformed(ActionEvent e) throws Exception {
        // 編集中の帳票定義体内容でPDFを作成する。
        if (getFormatElements().isEmpty()) {
            ACMessageBox.show("定義ファイルが読み込まれていません。");
            return;
        }

        // 出力用一時ファイルを作成
        String fileName;
        try {
            fileName = VRDateParser.format(Calendar.getInstance().getTime(),
                    "yyyyMMddHHmmss")
                    + "-PDF-Custom.xml";
        } catch (Exception ex) {
            // 代用ファイル名を使用する
            fileName = "PDF-Custom.xml";
        }
        final String dirTail = ACConstants.FILE_SEPARATOR + "pdf"
                + ACConstants.FILE_SEPARATOR;
        ACFileUtilitiesCreateResult result = ACFileUtilities
                .getCreatableFilePath(fileName, System
                        .getProperty("java.io.tmpdir")
                        + dirTail, System.getProperty("user.home") + dirTail);
        if (result != null) {
            if (result.isSuccess()) {
                // ファイル作成可能な場合
                File f = result.getFile();

                saveFormat(f);

                ACChotarouXMLWriter pd = new ACChotarouXMLWriter();

                pd.beginPrintEdit();
                pd.addFormat("page1", f.getAbsolutePath());
                pd.beginPageEdit("page1");
                pd.endPageEdit();
                pd.endPrintEdit();

                ACChotarouXMLUtilities.openPDF(pd);
                f.delete();
            }
        }

    }

    /**
     * 「帳票を保存」イベントです。
     * 
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void saveActionPerformed(ActionEvent e) throws Exception {
        // 編集した帳票を保存する。
        // 編集元の帳票ファイルをタイムスタンプ付きのバックアップファイル(元のファイル名_yyyyMMddHHmmss.xls)として変名する。
        File f = getOpendFile();
        File backupFile = new File(f.getAbsolutePath()
                + VRDateParser.format(new Date(), "yyyyMMddHHmmss") + ".xml");
        if (backupFile.exists()) {
            backupFile.delete();
        }
        if (f.exists()) {
            f.renameTo(backupFile);
        }
        // 編集した帳票をXML出力する。
        saveFormat(getOpendFile());

        setModified(false);

        // 保存完了のメッセージを表示する。
        ACMessageBox.show("保存しました。");

    }

    /**
     * 「終了」イベントです。
     * 
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void closeActionPerformed(ActionEvent e) throws Exception {
        // システムを終了します。
        if (canClose()) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        // デフォルトデバッグ起動
        ACFrame.getInstance().setFrameEventProcesser(
                new IkenshoFrameEventProcesser());
        VRMap param = new VRHashMap();
        // paramに渡りパラメタを詰めて実行することで、簡易デバッグが可能です。
        ACFrame.debugStart(new ACAffairInfo(IP001.class.getName(), param));
    }

    /**
     * 「フォーマット保存」に関する処理を行ないます。
     *
     * @param outputFile File
     * @throws Exception 処理例外
     *
     */
    public void saveFormat(File outputFile) throws Exception {
        Iterator it = getFormatElements().iterator();
        while (it.hasNext()) {
            Map row = (Map) it.next();

            Object obj = getFormatNodes().get(
                    ACCastUtilities.toString(row.get("Id")));
            if (obj instanceof Node) {
                Node item = (Node) obj;
                NamedNodeMap attr = item.getAttributes();
                if ("セル".equals(ACCastUtilities.toString(row.get("Type")))) {
                    // セル
                } else {
                    attr.getNamedItem("X").setNodeValue(
                            ACCastUtilities.toString(row.get("X")));
                    attr.getNamedItem("Y").setNodeValue(
                            ACCastUtilities.toString(row.get("Y")));
                    attr.getNamedItem("Width").setNodeValue(
                            ACCastUtilities.toString(row.get("Width")));
                    attr.getNamedItem("Height").setNodeValue(
                            ACCastUtilities.toString(row.get("Height")));
                    
                    Object bW = row.get("BorderWidth");
                    if(!ACTextUtilities.isNullText(bW)){
                        Node bWNode = attr.getNamedItem("BorderWidth");
                        if(bWNode!=null){
                            bWNode.setNodeValue(ACCastUtilities.toString(bW));
                        }
                    }
                }

                String fontSize = ACCastUtilities.toString(row.get("FontSize"));
                boolean hasFontSize = !ACTextUtilities.isNullText(fontSize);
                Node fontNode = getChildNode(item, "Font");
                if (fontNode != null) {
                    NamedNodeMap children = fontNode.getAttributes();
                    Node node = children.getNamedItem("Size");
                    if (node != null) {
                        if (hasFontSize) {
                            node.setNodeValue(fontSize);
                        } else {
                            children.removeNamedItem("Size");
                        }
                    } else if (hasFontSize) {
                        node = getOpendDocument().createAttribute("Size");
                        node.setNodeValue(fontSize);
                        children.setNamedItem(node);
                    }
                }else if(hasFontSize) {
                  fontNode = getOpendDocument().createElement("Font");
                  NamedNodeMap children = fontNode.getAttributes();
                  Node node;
                  
                  node = getOpendDocument().createAttribute("Name");
                  node.setNodeValue("ＭＳ ゴシック");
                  children.setNamedItem(node);
                  
                  node = getOpendDocument().createAttribute("ForeColor");
                  node.setNodeValue("#000000");
                  item.getAttributes().setNamedItem(node);

                  node = getOpendDocument().createAttribute("Size");
                  node.setNodeValue(fontSize);
                  children.setNamedItem(node);
                  
                  item.appendChild(fontNode);
                }
                
                String text = ACCastUtilities.toString(row.get("Text"));
                boolean hasText = !ACTextUtilities.isNullText(text);
                Node textNode = getChildNode(item, "Text");
                if (textNode != null) {
                    if (hasText) {
                        NodeList children = textNode.getChildNodes();
                        boolean seted = false;
                        int cEnd = children.getLength();
                        for (int j = 0; j < cEnd; j++) {
                            Node node = children.item(j);
                            if (node.getNodeType() == Node.TEXT_NODE) {
                                seted = true;
                                node.setNodeValue(text);
                                break;
                            }
                        }
                        if (!seted) {
                            textNode.appendChild(getOpendDocument()
                                    .createTextNode(text));
                        }
                    } else {
                        item.removeChild(textNode);
                    }
                } else if (hasText) {
                    //テキストを記入しかつフォントタグを持っているラベルの場合
                    textNode = getOpendDocument().createElement("Text");
                    item.appendChild(textNode);
                    textNode.appendChild(getOpendDocument()
                            .createTextNode(text));
                    
                    if (fontNode == null){
                        fontNode = getOpendDocument().createElement("Font");
                        NamedNodeMap children = fontNode.getAttributes();
                        Node node;
                        
                        node = getOpendDocument().createAttribute("Name");
                        node.setNodeValue("ＭＳ ゴシック");
                        children.setNamedItem(node);
                        
                        node = getOpendDocument().createAttribute("ForeColor");
                        node.setNodeValue("#000000");
                        item.getAttributes().setNamedItem(node);
                        
                        item.appendChild(fontNode);
                    }
                }

            }
        }

        // 編集した帳票をXML出力する。
        FileOutputStream out =new FileOutputStream(outputFile); 
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.transform(new DOMSource(getOpendDocument()), new StreamResult(
                out));
        out.close();
    }

    /**
     * 「フォントサイズノード取得」に関する処理を行ないます。
     *
     * @param item Node
     * @throws Exception 処理例外
     * @return String
     */
    public String getNodeFontSize(Node item) throws Exception{
        String fontSize = "";
        Node fontNode = getChildNode(item, "Font");
        if (fontNode != null) {
            NamedNodeMap fontAttrs = fontNode.getAttributes();
            if (fontAttrs != null) {
                Node fontSizeNode = fontAttrs.getNamedItem("Size");
                if (fontSizeNode != null) {
                    fontSize = fontSizeNode.getNodeValue();
                }
            }
        }
        return fontSize;
    }

    /**
     * 「帳票定義体読み込み」に関する処理を行ないます。
     * 
     * @throws Exception 処理例外
     */
    public void readFoamrt() throws Exception {
        File f = getOpendFile();
        if (!f.exists()) {
            ACMessageBox.show("帳票太郎形式のXMLファイルを指定してください。");
            return;
        }

        // ドキュメントビルダーファクトリを生成
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        // ドキュメントビルダーを生成
        DocumentBuilder builder = dbfactory.newDocumentBuilder();
        // パースを実行してDocumentオブジェクトを取得
        Document doc = builder.parse(f);
        // ルート要素を取得（タグ名：message）
        Element root = doc.getDocumentElement();

        getFormatNodes().clear();
        getFormatElements().clear();
        NodeList labelList = root.getElementsByTagName("Label");
        NodeList tableList = root.getElementsByTagName("Table");

        // ラベルを解析
        int end = labelList.getLength();
        for (int i = 0; i < end; i++) {
            Node item = labelList.item(i);
            NamedNodeMap attrs = item.getAttributes();
            Node id = attrs.getNamedItem("Id");
            Node shape = attrs.getNamedItem("Shape");
            Node x = attrs.getNamedItem("X");
            Node y = attrs.getNamedItem("Y");
            Node w = attrs.getNamedItem("Width");
            Node h = attrs.getNamedItem("Height");
            Node bW = attrs.getNamedItem("BorderWidth");
            String text = getChildNodeText(item, "Text");
            String fontSize = getNodeFontSize(item);
            String borderWidth = "";
            if(bW!=null){
                borderWidth = bW.getNodeValue();
            }
            String type;
            if("".equals(fontSize)){
                type = "図形";
            }else{
                type = "文字";
            }

            VRMap row = new VRHashMap();
            VRBindPathParser.set("Type", row, type);
            VRBindPathParser.set("Id", row, id.getNodeValue());
            VRBindPathParser.set("Text", row, text);
            VRBindPathParser.set("FontSize", row, fontSize);
            VRBindPathParser.set("BorderWidth", row, borderWidth);
            VRBindPathParser.set("Shape", row, shape.getNodeValue());
            VRBindPathParser.set("X", row, x.getNodeValue());
            VRBindPathParser.set("Y", row, y.getNodeValue());
            VRBindPathParser.set("Width", row, w.getNodeValue());
            VRBindPathParser.set("Height", row, h.getNodeValue());
            getFormatElements().addData(row);
            getFormatNodes().put(id.getNodeValue(), item);
        }
        end = tableList.getLength();
        for (int i = 0; i < end; i++) {
            Node item = tableList.item(i);
            NamedNodeMap attrs = item.getAttributes();
            Node idNode = attrs.getNamedItem("Id");
            Node shape = attrs.getNamedItem("Shape");
            Node x = attrs.getNamedItem("X");
            Node y = attrs.getNamedItem("Y");
            Node w = attrs.getNamedItem("Width");
            Node h = attrs.getNamedItem("Height");
            String id = idNode.getNodeValue();
            String fontSize = getNodeFontSize(item);

            VRHashMap row = new VRHashMap();
            VRBindPathParser.set("Type", row, "表");
            VRBindPathParser.set("Id", row, id);
            VRBindPathParser.set("Text", row, "");
            VRBindPathParser.set("FontSize", row, fontSize);
            VRBindPathParser.set("BorderWidth", row, "");
            VRBindPathParser.set("Shape", row, shape.getNodeValue());
            VRBindPathParser.set("X", row, x.getNodeValue());
            VRBindPathParser.set("Y", row, y.getNodeValue());
            VRBindPathParser.set("Width", row, w.getNodeValue());
            VRBindPathParser.set("Height", row, h.getNodeValue());
            getFormatElements().addData(row);
            getFormatNodes().put(id, item);

            // カラム名を取っておく
            Node columns = getChildNode(item, "Columns");
            NodeList childrens = columns.getChildNodes();
            int columnCount = 0;
            int jEnd = childrens.getLength();
            ArrayList columnsArray = new ArrayList();
            ArrayList columnsWs = new ArrayList();
            for (int j = 0; j < jEnd; j++) {
                if ("Col".equals(childrens.item(j).getNodeName())) {
                    NamedNodeMap colAttr = childrens.item(j).getAttributes();
                    columnsArray.add(colAttr.getNamedItem("Id").getNodeValue());
                    columnsWs.add(colAttr.getNamedItem("Width").getNodeValue());
                    columnCount++;
                }
            }
            String[] columnNames = new String[columnCount];
            String[] columnsWidths = new String[columnCount];
            System.arraycopy(columnsArray.toArray(), 0, columnNames, 0,
                    columnsArray.size());
            System.arraycopy(columnsWs.toArray(), 0, columnsWidths, 0,
                    columnsWs.size());

            childrens = item.getChildNodes();
            int trCount = 0;
            jEnd = childrens.getLength();
            for (int j = 0; j < jEnd; j++) {
                Node trNode = childrens.item(j);
                if ("Tr".equals(trNode.getNodeName())) {
                    NamedNodeMap trAttr = trNode.getAttributes();
//                    String trH = trAttr.getNamedItem("Height").getNodeValue();
                    String trId = trAttr.getNamedItem("Id").getNodeValue();

                    NodeList tds = trNode.getChildNodes();
                    int tdCount = 0;
                    int kEnd = tds.getLength();
                    for (int k = 0; k < kEnd; k++) {
                        Node tdNode = tds.item(k);
                        if ("Td".equals(tdNode.getNodeName())) {
                            fontSize = getNodeFontSize(tdNode);
                            String idName = id + "." + trId + "."
                                    + columnNames[tdCount];
                            row = new VRHashMap();
                            VRBindPathParser.set("Type", row, "セル");
                            VRBindPathParser.set("Id", row, idName);
                            VRBindPathParser.set("Text", row, getChildNodeText(
                                    tdNode, "Text"));
                            VRBindPathParser.set("FontSize", row, fontSize);
                            VRBindPathParser.set("BorderWidth", row, "");
                            VRBindPathParser.set("Shape", row, "");
                            getFormatElements().addData(row);
                            getFormatNodes().put(idName, tdNode);
                            tdCount++;
                        }
                    }
                    trCount++;
                }
            }

        }

        ACTableModelAdapter adapt = new ACTableModelAdapter(
                getFormatElements(), new String[] { "Type", "Id", "X", "Y",
                        "Width", "Height", "FontSize", "BorderWidth", "Text", "Shape" });
        getFormats().setModel(adapt);

        setOpendDocument(doc);

//        // デバッグ
//        System.out.println("-begin---------------------------------------");
//        Iterator it = getFormatElements().iterator();
//        while (it.hasNext()) {
//            Map row = (Map) it.next();
//            System.out.println(row.get("Id") + "\t" + row.get("Text"));
//        }
//        System.out.println("-end-----------------------------------------");
    }

    /**
     * 「子ノード取得」に関する処理を行ないます。
     * 
     * @param node Node
     * @param childName String
     * @throws Exception 処理例外
     * @return Node
     */
    public Node getChildNode(Node node, String childName) throws Exception {
        NodeList children = node.getChildNodes();
        int cEnd = children.getLength();
        for (int j = 0; j < cEnd; j++) {
            Node child = children.item(j);
            if (childName.equals(child.getNodeName())) {
                return child;
            }
        }
        return null;
    }

    /**
     * 「子ノードのテキスト要素取得」に関する処理を行ないます。
     * 
     * @param node Node
     * @param childName String
     * @throws Exception 処理例外
     * @return String
     */
    public String getChildNodeText(Node node, String childName)
            throws Exception {
        return getNodeText(getChildNode(node, childName));
    }

    /**
     * 「ノードのテキスト要素取得」に関する処理を行ないます。
     * 
     * @param node Node
     * @throws Exception 処理例外
     * @return String
     */
    public String getNodeText(Node node) throws Exception {
        if (node != null) {
            NodeList children = node.getChildNodes();
            int cEnd = children.getLength();
            for (int j = 0; j < cEnd; j++) {
                node = children.item(j);
                if (node.getNodeType() == Node.TEXT_NODE) {
                    return node.getNodeValue();
                }
            }
        }
        return "";
    }

    public boolean canClose() throws Exception {
        // 終了確認のメッセージ(OK/キャンセル)を表示します。
        if(getModified()){
            if (ACMessageBox.show("編集されています。保存せずに終了してもよろしいですか？",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                // OKを選択した場合
                // システムを終了します。
                return false;
            }
        }
        return true;
    }

    /**
     * 「セル変更」イベントです。
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void formatXCellEditing(ChangeEvent e) throws Exception {
        cellChanged();
    }

    /**
     * 「セル変更」イベントです。
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void formatYCellEditing(ChangeEvent e) throws Exception {
        cellChanged();
    }

    /**
     * 「セル変更」イベントです。
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void formatWidthCellEditing(ChangeEvent e) throws Exception {
        cellChanged();
    }

    /**
     * 「セル変更」イベントです。
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void formatHeightCellEditing(ChangeEvent e) throws Exception {
        cellChanged();
    }

    /**
     * 「セル変更」イベントです。
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void formatSizeCellEditing(ChangeEvent e) throws Exception {
        cellChanged();
    }

    /**
     * 「セル変更」イベントです。
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void formatTextCellEditing(ChangeEvent e) throws Exception {
        cellChanged();
    }

    /**
     * 「セル変更」に関する処理を行ないます。
     *
     * @throws Exception 処理例外
     *
     */
    public void cellChanged() throws Exception {
        setModified(true);
    }

    /**
     * 「セル変更」イベントです。
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void formatBorderWidthCellEditing(ChangeEvent e) throws Exception {
        cellChanged();
    }

}

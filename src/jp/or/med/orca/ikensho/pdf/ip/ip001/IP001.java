/*
 * Project code name "ORCA"
 * ���t�Ǘ��䒠�\�t�g QKANCHO�iJMA care benefit management software�j
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
 * �A�v��: QKANCHO
 * �J����: �c���@����
 * �쐬��: 2006/04/25  ���{�R���s���[�^�[������� �c���@���� �V�K�쐬
 * �X�V��: ----/--/--
 * �V�X�e�� �厡��ӌ��� (I)
 * �T�u�V�X�e�� ���[ (P)
 * �v���Z�X �ȈՒ��[�J�X�^�}�C�Y�c�[�� (001)
 * �v���O���� �ȈՒ��[�J�X�^�}�C�Y�c�[�� (IP001)
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
 * �ȈՒ��[�J�X�^�}�C�Y�c�[��(IP001)
 */
public class IP001 extends IP001Event {
    /**
     * �R���X�g���N�^�ł��B
     */
    public IP001() {
    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        super.initAffair(affair);
        initAction(affair);
    }

    /**
     * �������������s�Ȃ��܂��B
     * 
     * @param affair �Ɩ����
     * @throws Exception ������O
     */
    protected void initAction(ACAffairInfo affair) throws Exception {
        // ���������������s����B
        // �X�e�[�^�X�o�[�Ɂu�ҏW���钠�[���J���Ă��������B�v�ƕ\������B
        setStatusText("�ҏW���钠�[���J���Ă��������B");
        setTitle("�㌩���@�ȈՒ��[�J�X�^�}�C�Y�c�[��");
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

    // �R���|�[�l���g�C�x���g

    /**
     * �u���[���J���v�C�x���g�ł��B
     * 
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void openActionPerformed(ActionEvent e) throws Exception {
        if(getModified()){
            if (ACMessageBox.show("�ҏW����Ă��܂��B�ۑ������ɕʂ̒��[���J���Ă���낵���ł����H",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                // OK�ȊO��I�������ꍇ
                // �����𒆒f���܂��B
                return;
            }
        }

        
        // �ҏW���钠�[���J���B
        // ���[�I���_�C�A���O��\������B
        File f = new IP001001().showModal(getOpendFile());
        if (f != null) {
            // ���[���I�����ꂽ�ꍇ
            // �J�������[�̃t�@�C���I�u�W�F�N�g(opendFile)�A�h�L�������g�I�u�W�F�N�g(opendDocument)�����ꂼ��ݒ肷��B
            setOpendFile(f);
            IP001FormatIDFormat.getInstance().setTargetFile(f);
            
            // ���[���e����͂��A���[��`�̏W��(formatElements)�Ɋi�[����B
            // ��`��ID���}�b�s���O�n�b�V��(idReplaceMap)�ɂ��������ĕϊ����A��`�̍��ڈꗗ(formats)�ɓW�J����B
            String fileName = f.getName().toLowerCase();
            if (fileName.endsWith("newikensho1.xml")) {
                fileName = "�厡��ӌ���1�y�[�W��";
            } else if (fileName.endsWith("newikensho2.xml")) {
                fileName = "�厡��ӌ���2�y�[�W��";
            } else if (fileName.endsWith("shijisho.xml")) {
                fileName = "�K��Ō�w�����i��Ë@�ցj";
            } else if (fileName.endsWith("shijishob.xml")) {
                fileName = "�K��Ō�w�����i���V�l�ی��{�݁j";
            } else if (fileName.endsWith("seikyuichiran.xml")) {
                fileName = "�������ꗗ";
            } else if (fileName.endsWith("seikyuichirantotal.xml")) {
                fileName = "���������v";
            } else if (fileName.endsWith("soukatusho.xml")) {
                fileName = "�厡��ӌ����쐬���E����������(����)��";
            } else if (fileName.endsWith("ikenshomeisai.xml")) {
                fileName = "�厡��ӌ����쐬������(����)��";
            } else if (fileName.endsWith("csvfileoutputpatientlist.xml")) {
                fileName = "�b�r�u�t�@�C����o���҈ꗗ";
            } else if (fileName.endsWith("patientlist.xml")) {
                fileName = "�o�^���҈ꗗ";
            } else if (fileName.endsWith("seikyuikenshoichiran.xml")) {
                fileName = "�����Ώۈӌ����ꗗ";
            } else if (fileName.endsWith("ikenshoshien1.xml")) {
                fileName = "��t�ӌ���1�y�[�W��";
            } else if (fileName.endsWith("ikenshoshien2.xml")) {
                fileName = "��t�ӌ���2�y�[�W��";

            }
            // �ύX�̗L��(modified)��false�Ƃ���B
            setModified(false);
            // ���[�ۑ��ƈ���{�^����L���ɂ���B
            setState_FILE_OPENED();
            // �X�e�[�^�X�o�[�ɁA�J�������[�̖��̂�\������B
            setStatusText("�u" + fileName + "�v��ҏW��");

            readFoamrt();
        }
    }

    /**
     * �u����v���r���[�v�C�x���g�ł��B
     * 
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void printActionPerformed(ActionEvent e) throws Exception {
        // �ҏW���̒��[��`�̓��e��PDF���쐬����B
        if (getFormatElements().isEmpty()) {
            ACMessageBox.show("��`�t�@�C�����ǂݍ��܂�Ă��܂���B");
            return;
        }

        // �o�͗p�ꎞ�t�@�C�����쐬
        String fileName;
        try {
            fileName = VRDateParser.format(Calendar.getInstance().getTime(),
                    "yyyyMMddHHmmss")
                    + "-PDF-Custom.xml";
        } catch (Exception ex) {
            // ��p�t�@�C�������g�p����
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
                // �t�@�C���쐬�\�ȏꍇ
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
     * �u���[��ۑ��v�C�x���g�ł��B
     * 
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void saveActionPerformed(ActionEvent e) throws Exception {
        // �ҏW�������[��ۑ�����B
        // �ҏW���̒��[�t�@�C�����^�C���X�^���v�t���̃o�b�N�A�b�v�t�@�C��(���̃t�@�C����_yyyyMMddHHmmss.xls)�Ƃ��ĕϖ�����B
        File f = getOpendFile();
        File backupFile = new File(f.getAbsolutePath()
                + VRDateParser.format(new Date(), "yyyyMMddHHmmss") + ".xml");
        if (backupFile.exists()) {
            backupFile.delete();
        }
        if (f.exists()) {
            f.renameTo(backupFile);
        }
        // �ҏW�������[��XML�o�͂���B
        saveFormat(getOpendFile());

        setModified(false);

        // �ۑ������̃��b�Z�[�W��\������B
        ACMessageBox.show("�ۑ����܂����B");

    }

    /**
     * �u�I���v�C�x���g�ł��B
     * 
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void closeActionPerformed(ActionEvent e) throws Exception {
        // �V�X�e�����I�����܂��B
        if (canClose()) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        // �f�t�H���g�f�o�b�O�N��
        ACFrame.getInstance().setFrameEventProcesser(
                new IkenshoFrameEventProcesser());
        VRMap param = new VRHashMap();
        // param�ɓn��p�����^���l�߂Ď��s���邱�ƂŁA�ȈՃf�o�b�O���\�ł��B
        ACFrame.debugStart(new ACAffairInfo(IP001.class.getName(), param));
    }

    /**
     * �u�t�H�[�}�b�g�ۑ��v�Ɋւ��鏈�����s�Ȃ��܂��B
     *
     * @param outputFile File
     * @throws Exception ������O
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
                if ("�Z��".equals(ACCastUtilities.toString(row.get("Type")))) {
                    // �Z��
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
                  node.setNodeValue("�l�r �S�V�b�N");
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
                    //�e�L�X�g���L�������t�H���g�^�O�������Ă��郉�x���̏ꍇ
                    textNode = getOpendDocument().createElement("Text");
                    item.appendChild(textNode);
                    textNode.appendChild(getOpendDocument()
                            .createTextNode(text));
                    
                    if (fontNode == null){
                        fontNode = getOpendDocument().createElement("Font");
                        NamedNodeMap children = fontNode.getAttributes();
                        Node node;
                        
                        node = getOpendDocument().createAttribute("Name");
                        node.setNodeValue("�l�r �S�V�b�N");
                        children.setNamedItem(node);
                        
                        node = getOpendDocument().createAttribute("ForeColor");
                        node.setNodeValue("#000000");
                        item.getAttributes().setNamedItem(node);
                        
                        item.appendChild(fontNode);
                    }
                }

            }
        }

        // �ҏW�������[��XML�o�͂���B
        FileOutputStream out =new FileOutputStream(outputFile); 
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.transform(new DOMSource(getOpendDocument()), new StreamResult(
                out));
        out.close();
    }

    /**
     * �u�t�H���g�T�C�Y�m�[�h�擾�v�Ɋւ��鏈�����s�Ȃ��܂��B
     *
     * @param item Node
     * @throws Exception ������O
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
     * �u���[��`�̓ǂݍ��݁v�Ɋւ��鏈�����s�Ȃ��܂��B
     * 
     * @throws Exception ������O
     */
    public void readFoamrt() throws Exception {
        File f = getOpendFile();
        if (!f.exists()) {
            ACMessageBox.show("���[���Y�`����XML�t�@�C�����w�肵�Ă��������B");
            return;
        }

        // �h�L�������g�r���_�[�t�@�N�g���𐶐�
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        // �h�L�������g�r���_�[�𐶐�
        DocumentBuilder builder = dbfactory.newDocumentBuilder();
        // �p�[�X�����s����Document�I�u�W�F�N�g���擾
        Document doc = builder.parse(f);
        // ���[�g�v�f���擾�i�^�O���Fmessage�j
        Element root = doc.getDocumentElement();

        getFormatNodes().clear();
        getFormatElements().clear();
        NodeList labelList = root.getElementsByTagName("Label");
        NodeList tableList = root.getElementsByTagName("Table");

        // ���x�������
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
                type = "�}�`";
            }else{
                type = "����";
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
            VRBindPathParser.set("Type", row, "�\");
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

            // �J������������Ă���
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
                            VRBindPathParser.set("Type", row, "�Z��");
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

//        // �f�o�b�O
//        System.out.println("-begin---------------------------------------");
//        Iterator it = getFormatElements().iterator();
//        while (it.hasNext()) {
//            Map row = (Map) it.next();
//            System.out.println(row.get("Id") + "\t" + row.get("Text"));
//        }
//        System.out.println("-end-----------------------------------------");
    }

    /**
     * �u�q�m�[�h�擾�v�Ɋւ��鏈�����s�Ȃ��܂��B
     * 
     * @param node Node
     * @param childName String
     * @throws Exception ������O
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
     * �u�q�m�[�h�̃e�L�X�g�v�f�擾�v�Ɋւ��鏈�����s�Ȃ��܂��B
     * 
     * @param node Node
     * @param childName String
     * @throws Exception ������O
     * @return String
     */
    public String getChildNodeText(Node node, String childName)
            throws Exception {
        return getNodeText(getChildNode(node, childName));
    }

    /**
     * �u�m�[�h�̃e�L�X�g�v�f�擾�v�Ɋւ��鏈�����s�Ȃ��܂��B
     * 
     * @param node Node
     * @throws Exception ������O
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
        // �I���m�F�̃��b�Z�[�W(OK/�L�����Z��)��\�����܂��B
        if(getModified()){
            if (ACMessageBox.show("�ҏW����Ă��܂��B�ۑ������ɏI�����Ă���낵���ł����H",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                // OK��I�������ꍇ
                // �V�X�e�����I�����܂��B
                return false;
            }
        }
        return true;
    }

    /**
     * �u�Z���ύX�v�C�x���g�ł��B
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void formatXCellEditing(ChangeEvent e) throws Exception {
        cellChanged();
    }

    /**
     * �u�Z���ύX�v�C�x���g�ł��B
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void formatYCellEditing(ChangeEvent e) throws Exception {
        cellChanged();
    }

    /**
     * �u�Z���ύX�v�C�x���g�ł��B
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void formatWidthCellEditing(ChangeEvent e) throws Exception {
        cellChanged();
    }

    /**
     * �u�Z���ύX�v�C�x���g�ł��B
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void formatHeightCellEditing(ChangeEvent e) throws Exception {
        cellChanged();
    }

    /**
     * �u�Z���ύX�v�C�x���g�ł��B
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void formatSizeCellEditing(ChangeEvent e) throws Exception {
        cellChanged();
    }

    /**
     * �u�Z���ύX�v�C�x���g�ł��B
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void formatTextCellEditing(ChangeEvent e) throws Exception {
        cellChanged();
    }

    /**
     * �u�Z���ύX�v�Ɋւ��鏈�����s�Ȃ��܂��B
     *
     * @throws Exception ������O
     *
     */
    public void cellChanged() throws Exception {
        setModified(true);
    }

    /**
     * �u�Z���ύX�v�C�x���g�ł��B
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void formatBorderWidthCellEditing(ChangeEvent e) throws Exception {
        cellChanged();
    }

}

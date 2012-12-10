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
 * XML�`���̃v���p�e�B�t�@�C���A�N�Z�X���C�u�����ł��B
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
     * �m�[�h�̑����l��Ԃ��܂��B
     * 
     * @param node �����Ώۂ̃m�[�h
     * @param attributeName ������
     * @return �m�[�h�̑����l
     */
    protected String getAttributeValue(Node node, String attributeName) {
        if (!node.hasAttributes()) {
            return null;
        }
        Node attributeNode = node.getAttributes().getNamedItem(attributeName);
        // null�`�F�b�N
        if (attributeNode == null) {
            return null;
        }
        return attributeNode.getNodeValue();
    }

    /**
     * �m�[�h�����ŏ��̃e�L�X�g�v�f�𕶎���Ƃ��ĕԂ��܂��B
     * 
     * @param node �����Ώۂ̃m�[�h
     * @return �e�L�X�g�v�f�l
     */
    protected String getNodeTextValue(Node node) {
        // null�`�F�b�N
        if (node == null) {
            return null;
        }
        Node firstNode = node.getFirstChild();
        // null�`�F�b�N
        if (firstNode == null) {
            return null;
        }
        return firstNode.getNodeValue();
    }

    /**
     * �m�[�h���X�g����͂��܂��B
     * 
     * @param container �v���p�e�B�f�[�^�̊i�[��
     * @param nodes ��͑Ώۂ̃m�[�h���X�g
     */
    protected void readNodes(VRMap container, NodeList nodes) {
        if ((nodes == null) || (container == null)) {
            return;
        }
        Node node;
        // ���[�g�m�[�h�̎q�v�f��S����
        for (int i = 0; (node = nodes.item(i)) != null; i++) {
            String key = getAttributeValue(node, getIdAttribute());
            if ((key != null) && (!"".equals(key))) {
                String nodeName = node.getNodeName();
                // ��properity���m�[�h
                if (getPropertyNode().equals(nodeName)
                        ||"property".equals(nodeName)  //���Ō뎚�΍�
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
     * �R���X�g���N�^�ł��B
     * 
     * @param path �t�@�C���p�X
     */
    public ACPropertyXML(String path) {
        super();
        setPath(path);
    }

    /**
     * �t�@�C���p�X��Ԃ��܂��B
     * 
     * @return �t�@�C���p�X
     */
    public String getPath() {
        return path;
    }

    /**
     * �o�C���h�p�X�ɑΉ�����v���p�e�B�l����`����Ă��邩��Ԃ��܂��B
     * 
     * @param bindPath �o�C���h�p�X
     * @return �o�C���h�p�X�ɑΉ�����v���p�e�B�l����`����Ă��邩
     * @throws ParseException �o�C���h�p�X�̉�͂Ɏ��s
     */
    public boolean hasValueAt(String bindPath) throws ParseException {
        return VRBindPathParser.has(bindPath, this);
    }

    /**
     * �o�C���h�p�X�ɑΉ�����v���p�e�B�l�𕶎���ŕԂ��܂��B
     * 
     * @param bindPath �o�C���h�p�X
     * @return �v���p�e�B�l
     * @throws ParseException �o�C���h�p�X�̉�͂Ɏ��s
     */
    public String getValueAt(String bindPath) throws ParseException {
        Object obj = VRBindPathParser.get(bindPath, this);
        if (obj instanceof String) {
            return (String) obj;
        }
        throw new ArrayIndexOutOfBoundsException(bindPath);
    }
    
    /**
     * �o�C���h�p�X�ɑΉ�����v���p�e�B�W����Map�Ƃ��ĕԂ��܂��B
     * 
     * @param bindPath �o�C���h�p�X
     * @return �v���p�e�B�W��
     * @throws ParseException �o�C���h�p�X�̉�͂Ɏ��s
     */
    public Map getValuesAt(String bindPath) throws ParseException {
        Object obj = VRBindPathParser.get(bindPath, this);
        if (obj instanceof Map) {
            return (Map) obj;
        }
        throw new ArrayIndexOutOfBoundsException(bindPath);
    }

    
    /**
     * �o�C���h�p�X�ɑΉ�����v���p�e�B�l�𕶎���ŕԂ��܂��B
     * 
     * @param bindPath �o�C���h�p�X
     * @param defaultValue �f�t�H���g�l
     * @return �v���p�e�B�l
     * @throws ParseException �o�C���h�p�X�̉�͂Ɏ��s
     */
    public String getValueAt(String bindPath, String defaultValue)  {
        try{
            return getValueAt(bindPath);
        }catch(Exception ex){
            return defaultValue;
        }
    }

    /**
     * �o�C���h�p�X�ɑΉ�����v���p�e�B�l�𕶎���Őݒ肵�܂��B
     * 
     * @param bindPath �o�C���h�p�X
     * @param value �v���p�e�B�l
     * @throws ParseException �o�C���h�p�X�̉�͂Ɏ��s
     */
    public void setValueAt(String bindPath, String value) throws ParseException {
        if (!VRBindPathParser.set(bindPath, this, value)) {
            throw new ParseException(bindPath, 0);
        }
    }

    /**
     * �o�C���h�p�X�ɑΉ�����v���p�e�B�l�𕶎���Őݒ肵�܂��B <br />
     * �w�肵���p�X����͍\�������ꍇ�A�W���̃\�[�X�`���ŕK�v�K�w���쐬���܂��B
     * 
     * @param bindPath �o�C���h�p�X
     * @param value �v���p�e�B�l
     * @throws ParseException �p�X��͗�O
     */
    public void setForceValueAt(String bindPath, String value)
            throws ParseException {
        ArrayList elements = VRBindPathParser.parsePath(bindPath);
        // ��͊���
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
                // �C���f�b�N�X�͔F�߂Ȃ�
                throw new ParseException(getPath(), i);
            } else {
                if (i == lastElement) {
                    currentSource.setData(element, value);
                    return;
                }

                Object childObj = currentSource.getData(element);
                if (childObj == null) {
                    // ��������
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
     * XML�t�@�C����ǂݍ��݂܂��B
     * 
     * @throws FactoryConfigurationError XML�h�L�������g�t�@�N�g����O
     * @throws ParserConfigurationException XML�p�[�T��O
     * @throws IOException ���o�͗�O
     * @throws SAXException SAX��͗�O
     */
    public void read() throws SAXException, IOException,
            ParserConfigurationException, FactoryConfigurationError {
        // �h�L�������g�r���_�[�t�@�N�g���𐶐�
        // �h�L�������g�r���_�[�𐶐�
        // �p�[�X�����s����Document�I�u�W�F�N�g���擾
        // ���[�g�v�f�̎擾
        // �q�v�f���܂񂾃m�[�h���
        NodeList rootNodes = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(getPath()).getDocumentElement()
                .getChildNodes();

        this.clearData();
        // ���[�g�m�[�h�̎q�v�f��S����
        readNodes(this, rootNodes);
    }

    /**
     * XML�t�@�C���������o���܂��B
     * 
     * @throws FactoryConfigurationError XML�h�L�������g�t�@�N�g����O
     * @throws ParserConfigurationException XML�p�[�T��O
     * @throws DOMException DOM��͗�O
     * @throws TransformerFactoryConfigurationError XML�ϊ��t�@�N�g����O
     * @throws TransformerException XML�ϊ���O
     * @throws FileNotFoundException �t�@�C����O
     * @throws TransformerConfigurationException XML�ϊ��ݒ��O
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
     * �t�@�C���p�X��ݒ肵�܂��B
     * 
     * @param path �t�@�C���p�X
     */
    protected void setPath(String path) {
        this.path = path;
    }

    /**
     * �m�[�h�W�����o�͂��܂��B
     * 
     * @param document �h�L�������g
     * @param container �e�}�b�v
     * @param parent �e�v�f
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
     * �v���p�e�B�W���m�[�h����Ԃ��܂��B
     * 
     * @return �v���p�e�B�W���m�[�h��
     */
    public String getPropertiesNode() {
        return propertiesNode;
    }

    /**
     * �v���p�e�B�m�[�h����Ԃ��܂��B
     * 
     * @return �v���p�e�B�m�[�h��
     */
    public String getPropertyNode() {
        return propertyNode;
    }

    /**
     * �v���p�e�B�W���m�[�h����ݒ肵�܂��B
     * 
     * @param propertyNode �v���p�e�B�m�[�h��
     */
    public void setPropertyNode(String propertyNode) {
        this.propertyNode = propertyNode;
    }

    /**
     * �v���p�e�B�W���m�[�h����ݒ肵�܂��B
     * 
     * @param properitiesNode �v���p�e�B�W���m�[�h��
     */
    public void setPropertiesNode(String properitiesNode) {
        this.propertiesNode = properitiesNode;
    }

    /**
     * ID��������Ԃ��܂��B
     * 
     * @return ID������
     */
    public String getIdAttribute() {
        return idAttribute;
    }

    /**
     * ID��������ݒ肵�܂��B
     * 
     * @param idAttribute ID������
     */
    public void setIdAttribute(String idAttribute) {
        this.idAttribute = idAttribute;
    }

    /**
     * XML�t�@�C���������o���\����Ԃ��܂��B
     * 
     * @return XML�t�@�C���������o���\��
     */
    public boolean writeWithCheck() {
        File f = new File(getPath());
        if (f.exists() &&(!f.canWrite())) {
            ACMessageBox.show(f.getAbsolutePath() + ACConstants.LINE_SEPARATOR
                    + "�ɏ������߂܂���B�t�@�C���ɏ������݌�����^���Ă��������B");
            return false;
        }
        try{
            write();
        }catch(Exception ex){
            ACMessageBox.show(f.getAbsolutePath() + ACConstants.LINE_SEPARATOR
                    + "�ւ̏������݂Ɏ��s���܂����B�������݌�����L���ȃp�X�ł��邩�m�F���Ă��������B");
            return false;
        }
        
        return true;
    }

    /**
     * �w��̃p�X�Ƀt�@�C�������݂��邩��Ԃ��܂��B
     * 
     * @return �w��̃p�X�Ƀt�@�C�������݂��邩
     */
    public boolean isFile() {
        return new File(getPath()).isFile();
    }

    /**
     * �ǂݍ��݉\�ł��邩��Ԃ��܂��B
     * 
     * @return �ǂݍ��݉\�ł��邩
     */
    public boolean canRead() {
        return new File(getPath()).canRead();
    }

    /**
     * �������݉\�ł��邩��Ԃ��܂��B
     * 
     * @return �������݉\�ł��邩
     */
    public boolean canWrite() {
        return new File(getPath()).canWrite();
    }

}

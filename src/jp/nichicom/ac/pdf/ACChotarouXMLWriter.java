package jp.nichicom.ac.pdf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.io.ACFileUtilities;
import jp.nichicom.ac.io.ACFileUtilitiesCreateResult;
import jp.nichicom.vr.text.parsers.VRDateParser;

/**
 * ���[���Y��XML�t�H�[�}�b�g�����PDF���o�͂��钇��N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Mizuki Tsutsumi
 * @version 1.0 2005/12/01
 */
public class ACChotarouXMLWriter {
    // �f�[�^��Attribute�ݒ�@�\��ǉ�
    private HashMap dataAttributeMap = new HashMap();

    // �ݒ�f�[�^�̃^�O���G�X�P�[�v���Ȃ����[�h��ǉ�
    private HashMap dataDirectMap = new HashMap();
    private ArrayList dataKeyArray = new ArrayList();

    private ArrayList dataValueArray = new ArrayList();
    private ArrayList formatFilePathArray = new ArrayList();

    private ArrayList formatNameArray = new ArrayList();

    private String pageFormatName;
    private String printPrinter;

    private String printTitle;
    
    private File dataFile;
    private Writer writer;
    
    /**
     * �R���X�g���N�^�ł��B
     */
    public ACChotarouXMLWriter() {
        formatNameArray = new ArrayList();
        formatFilePathArray = new ArrayList();
    }
    
    /**
     * �t�H�[�}�b�g�L���b�V�������������܂��B
     */
    public void clearFormat(){
        formatNameArray.clear();
        formatFilePathArray.clear();
    }

    /**
     * �A�g���r���[�g��ݒ肵�܂��B
     * 
     * @param objectName String �^�O����
     * @param key String �A�g���r���[�g��
     * @param value String �l
     */
    public void addAttribute(String objectName, String key, String value) {
        String data = "";
        String addKey = " " + key + "=\"";
        // �w�肳�ꂽ�I�u�W�F�N�g�����ɓo�^����Ă��邩�`�F�b�N
        if (dataAttributeMap.containsKey(objectName)) {
            // �o�^����Ă���΁A�f�[�^��ޔ�
            data = dataAttributeMap.get(objectName).toString();
            int idx =data.indexOf(addKey);
            if(idx>=0){
                int tokenIdx = data.indexOf("\"", idx+addKey.length());
                if(tokenIdx>idx){
                    //�����̃L�[�����o�̏ꍇ�͒l���������ւ���
                    dataAttributeMap.put(objectName, data.substring(0, idx)
                            +addKey+ value + data.substring(tokenIdx));
                    return;
                }
            }
        }
        dataAttributeMap.put(objectName, data + addKey + value
                + "\"");
    }
    /**
     * ���ږ��ƒl�̑g��ǉ����܂��B
     * 
     * @param key ���ږ�
     * @param value �l
     */
    public void addData(String key, String value) {
        dataKeyArray.add(key);
        dataValueArray.add(value);
    }

    /**
     * �w�肵���f�[�^���^�O�̃G�X�P�[�v�����ɒǉ����܂��B
     * 
     * @param key String ���ږ�
     * @param value String �l
     */
    public void addDataDirect(String key, String value) {
        dataDirectMap.put(key, value);
    }

    /**
     * �t�H�[�}�b�g���ƃt�@�C���p�X�̐ݒ���w�����܂��B
     * 
     * @param name �t�H�[�}�b�g��
     * @param filePath �t�@�C���p�X(full path)
     */
    public void addFormat(String name, String filePath) {
        if(formatNameArray.indexOf(name)<0){
            //�V�o�Ȃ�ǉ�
            formatNameArray.add(name);
            formatFilePathArray.add(filePath);
        }
    }

    /**
     * �y�[�W�ݒ�̊J�n���w�����܂��B
     * 
     * @param formatName �y�[�W�Ŏg�p����t�H�[�}�b�g��
     */
    public void beginPageEdit(String formatName) {
        pageFormatName = formatName;
        dataKeyArray.clear();// = new ArrayList();
        dataValueArray.clear();// = new ArrayList();
        // add
        dataAttributeMap.clear();// = new HashMap();
        dataDirectMap.clear();// = new HashMap();

        setPageEmpty(false);
    }

    /**
     * �v�����g�ݒ�̊J�n���w�����܂��B
     */
    public void beginPrintEdit() {
        clear();
        
        //�o�͗p�ꎞ�t�@�C�����쐬
        String fileName;
        try {
            fileName = VRDateParser.format(Calendar.getInstance().getTime(),
                    "yyyyMMddHHmmss")
                    + ".xml";
        } catch (Exception e) {
            //��p�t�@�C�������g�p����
            fileName = "pdf.xml";
        }
        final String dirTail = ACConstants.FILE_SEPARATOR + "pdf"
                + ACConstants.FILE_SEPARATOR;
        ACFileUtilitiesCreateResult result = ACFileUtilities
                .getCreatableFilePath(fileName, System
                        .getProperty("java.io.tmpdir")
                        + dirTail, System.getProperty("user.home") + dirTail);
        if (result != null) {
            if(result.isSuccess()){
                //�t�@�C���쐬�\�ȏꍇ
                File f = result.getFile();
                try {
                    writer = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
                    dataFile = f;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        setPageEmpty(true);
    }
    /**
     * �o�͌��ʂ��t�@�C���������ĕԂ��܂��B
     * @return �o�̓t�@�C�� 
     */
    public File createDataFile(){
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            writer = null;
        }
        File outputFile = new File(dataFile.getAbsolutePath() + ".xml");
        try {
            writer = new OutputStreamWriter(
                    (new FileOutputStream(outputFile)), "UTF-8");
            // �w�b�_�o��
            writeHead(writer);

            // �f�[�^�t�@�C����]�L
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile), "UTF-8"));
                // �ǂݍ��ރf�[�^���Ȃ��Ȃ�܂œǂݍ���
                String contents;
                while ((contents = br.readLine()) != null) {
                    // �������ރf�[�^���Ȃ��Ȃ�܂ŏ�������
                    writer.write(contents);
                    writer.write(CRLF);
                    writer.flush();
                }
            } finally {
                if (br != null) {
                    br.close();
                }
            }
            // �t�b�^�o��
            writeFoot(writer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                writer = null;
            }
            if (dataFile != null) {
                dataFile.delete();
                dataFile = null;
            }
        }
        return outputFile;
    }
    /**
     * �w�b�_�����o�͂��܂��B
     * @param out �o�͐�
     * @throws IOException ������O
     */
    protected void writeHead(Writer out) throws IOException{
        StringBuffer sb = new StringBuffer();
        
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append(CRLF);
        sb.append("<PrintData>");
        sb.append(CRLF);

        // format�̒�`
        for (int i = 0; i < formatNameArray.size(); i++) {
            sb.append("<Format Id=\"");
            sb.append(String.valueOf(formatNameArray.get(i)));
            sb.append("\" Src=\"file:");
            sb.append(String.valueOf(formatFilePathArray.get(i)));
            sb.append("\" />");
            sb.append(CRLF);
        }
        
        // �^�C�g���ݒ�
        sb.append("<Print");
        if (!printTitle.equals("")) {
            sb.append(" Title=\"");
            sb.append(printTitle);
            sb.append("\"");
        }
        if (!printPrinter.equals("")) {
            sb.append(" Printer=\"");
            sb.append(printPrinter);
            sb.append("\"");
        }
        sb.append(">");
        sb.append(CRLF);

        out.write(sb.toString());
    }
    /**
     * �t�b�^�����o�͂��܂��B
     * @param out �o�͐�
     * @throws IOException ������O
     */
    protected void writeFoot(Writer out) throws IOException{
        StringBuffer sb = new StringBuffer();

        sb.append("</Print>");
        sb.append(CRLF);

        sb.append("</PrintData>");
        out.write(sb.toString());
    }
    /**
     * �o�͌��ʂ��N���A���܂��B
     */
    public void clear() {
        printTitle = "";
        printPrinter = "";
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            writer = null;
        }
        if (dataFile != null) {
            dataFile.delete();
            dataFile = null;
        }
        //�����t�H�[�}�b�g�̃N���A
        clearFormat();
    }

    /**
     * �v�����g�ݒ�̊J�n���w�����܂��B
     * 
     * @param title �^�C�g��
     * @param printer �v�����^
     */
    public void beginPrintEdit(String title, String printer) {
        printTitle = title;
        printPrinter = printer;
    }

    /**
     * �y�[�W�ݒ�̏I�����w�����܂��B
     */
    public void endPageEdit() {
        try{
            if(writer!=null){
                //1�y�[�W���̏o��
                writePage(writer, printTitle, printPrinter, pageFormatName,
                        dataKeyArray, dataValueArray, dataAttributeMap,
                        dataDirectMap);
                writer.flush();
                //���
                dataKeyArray.clear();
                dataValueArray.clear();
                dataAttributeMap.clear();
                dataDirectMap.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * �v�����g�ݒ�̏I�����w�����܂��B
     */
    public void endPrintEdit() {
    }
    protected final String CRLF = "\n";
    protected final String TAB = "\t";
    /**
     * �y�[�W�f�[�^���o�͂��܂��B
     * 
     * @param out �o�͐�
     * @param printTitle �^�C�g��
     * @param printPrinter �v�����^
     * @param pageFormatName �t�H�[�}�b�g��
     * @param dk �f�[�^�L�[�W��
     * @param dv �f�[�^�o�����[�W��
     * @param attributeMap �����}�b�v
     * @param directMap ���ڏo�͍��ڃ}�b�v
     * @throws IOException ������O
     */
    protected void writePage(Writer out, String printTitle,
            String printPrinter, String pageFormatName, ArrayList dk,
            ArrayList dv, HashMap attributeMap, HashMap directMap)
            throws IOException {
        StringBuffer sb = new StringBuffer();

        sb.append(TAB);
        sb.append("<Page FormatId=\"");
        sb.append(pageFormatName);
        sb.append("\">");
        sb.append(CRLF);

        sb.append(TAB);
        sb.append(TAB);
        sb.append("<Data>");
        sb.append(CRLF);

        for (int k = 0; k < dk.size(); k++) {
            sb.append(TAB);
            sb.append(TAB);
            sb.append(TAB);
            sb.append("<");
            sb.append(dk.get(k));
            sb.append(getAttribute(String.valueOf(dk.get(k)), attributeMap));
            sb.append(">");
            sb.append(getTabeEcape(String.valueOf(dv.get(k))));
            sb.append("</");
            sb.append(dk.get(k));
            sb.append(">");
            sb.append(CRLF);
        }
        sb.append(getAttributeRemainder(attributeMap));

        Iterator iter = directMap.keySet().iterator();
        Object key = null;
        while (iter.hasNext()) {
            key = iter.next();
            sb.append(TAB);
            sb.append(TAB);
            sb.append(TAB);
            sb.append("<");
            sb.append(key);
            sb.append(getAttribute(String.valueOf(key), attributeMap));
            sb.append(">");
            sb.append(directMap.get(key));
            sb.append("</");
            sb.append(key);
            sb.append(">");
            sb.append(CRLF);
        }

        sb.append(TAB);
        sb.append(TAB);
        sb.append("</Data>");
        sb.append(CRLF);

        sb.append(TAB);
        sb.append("</Page>");
        sb.append(CRLF);

        out.write(sb.toString());
    }
    
//    /**
//     * XML�`���̒��[���Y�p�f�[�^���擾���܂��B
//     * 
//     * @return XML�f�[�^
//     */
//    public String getDataXml() {
//        StringBuffer sb = new StringBuffer();
//
//        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//        sb.append(CRLF);
//        sb.append("<PrintData>");
//        sb.append(CRLF);
//
//        // format�̒�`
//        for (int i = 0; i < formatNameArray.size(); i++) {
//            sb.append("<Format Id=\"");
//            sb.append(formatNameArray.get(i));
//            sb.append("\" Src=\"file:");
//            sb.append(formatFilePathArray.get(i));
//            sb.append("\" />");
//            sb.append(CRLF);
//        }
//
//        // �f�[�^(�y�[�W�������[�v)
//        for (int i = 0; i < printTitleArray.size(); i++) {
//            // �^�C�g���ݒ�
//            String printTitle = printTitleArray.get(i).toString();
//            String printPrinter = printPrinterArray.get(i).toString();
//            sb.append("<Print");
//            if (!printTitle.equals("")) {
//                sb.append(" Title=\"");
//                sb.append(printTitle);
//                sb.append("\"");
//            }
//            if (!printPrinter.equals("")) {
//                sb.append(" Printer=\"");
//                sb.append(printPrinter);
//                sb.append("\"");
//            }
//            sb.append(">");
//            sb.append(CRLF);
//
//            //
//            ArrayList pfn = (ArrayList) printPageFormatNameArray.get(i);
//            ArrayList pdk = (ArrayList) printPageDataKeyArray.get(i);
//            ArrayList pdv = (ArrayList) printPageDataValueArray.get(i);
//            for (int j = 0; j < pfn.size(); j++) {
//                sb.append(TAB);
//                sb.append("<Page FormatId=\"");
//                // BUG
//                // sb.append(pfn.get(i));
//                sb.append(pfn.get(j));
//                sb.append("\">");
//                sb.append(CRLF);
//
//                sb.append(TAB);
//                sb.append(TAB);
//                sb.append("<Data>");
//                sb.append(CRLF);
//
//                ArrayList dk = (ArrayList) pdk.get(j);
//                ArrayList dv = (ArrayList) pdv.get(j);
//                // add
//                HashMap attributeMap = (HashMap) printPageAttributeArray.get(j);
//                for (int k = 0; k < dk.size(); k++) {
//                    sb.append(TAB);
//                    sb.append(TAB);
//                    sb.append(TAB);
//                    sb.append("<");
//                    sb.append(dk.get(k));
//                    // add
//                    sb.append(getAttribute(dk.get(k).toString(), attributeMap));
//                    sb.append(">");
//                    // tagesc getTabeEcape
//                    // sb.append(dv.get(k));
//                    sb.append(getTabeEcape(dv.get(k).toString()));
//                    sb.append("</");
//                    sb.append(dk.get(k));
//                    sb.append(">");
//                    sb.append(CRLF);
//                }
//                sb.append(getAttributeRemainder(attributeMap));
//                // add
//                HashMap directMap = (HashMap) printPageDirectArray.get(j);
//                Iterator iter = directMap.keySet().iterator();
//                Object key = null;
//                while (iter.hasNext()) {
//                    key = iter.next();
//                    sb.append(TAB);
//                    sb.append(TAB);
//                    sb.append(TAB);
//                    sb.append("<");
//                    sb.append(key);
//                    // add
//                    sb.append(getAttribute(key.toString(), attributeMap));
//                    sb.append(">");
//                    sb.append(directMap.get(key).toString());
//                    sb.append("</");
//                    sb.append(key);
//                    sb.append(">");
//                    sb.append(CRLF);
//                }
//
//                sb.append(TAB);
//                sb.append(TAB);
//                sb.append("</Data>");
//                sb.append(CRLF);
//
//                sb.append(TAB);
//                sb.append("</Page>");
//                sb.append(CRLF);
//            }
//
//            sb.append("</Print>");
//            sb.append(CRLF);
//        }
//
//        sb.append("</PrintData>");
//        return sb.toString();
//    }

    /**
     * �������Ɏw�肳�ꂽ�l�Ɠ����v�f�����݂���΁A�A�g���r���[�g��ݒ肵�܂��B
     * 
     * @param objectName String �^�O����
     * @param map HashMap �A�g���r���[�g�l��ێ����Ă���HashMap
     * @return String �쐬�����^�O
     */
    private String getAttribute(String objectName, HashMap map) {
        String data = "";
        if (map.containsKey(objectName)) {
            data = map.get(objectName).toString();
            map.remove(objectName);
        }
        return data;
    }

    /**
     * �����Ɏw�肳�ꂽHashMap�ɕێ����Ă���^�O��S�ďo�͂��� �������ԋp���܂��B
     * 
     * @param map HashMap �A�g���r���[�g��ێ�����HashMap
     * @return String �쐬����������
     */
    private String getAttributeRemainder(HashMap map) {
        StringBuffer data = new StringBuffer();
        Object key = null;
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            key = it.next();
            // �C���e���h
            data.append("\t\t\t");
            data.append("<");
            data.append(key);
            data.append(map.get(key));
            data.append("/>\n");
        }
        return data.toString();
    }

    /**
     * �^�O�����̃G�X�P�[�v���s���܂��B
     * 
     * @param data String �ϊ��Ώۃf�[�^
     * @return String �ϊ���̒l
     */
    private String getTabeEcape(String data) {
        String escData = data;
        escData = escData.replaceAll("&", "&amp;");
        escData = escData.replaceAll("<", "&lt;");
        escData = escData.replaceAll(">", "&gt;");
        escData = escData.replaceAll("\n", "<Br />");
        escData = escData.replaceAll("\r", "");
        // escData = escData.replaceAll(" ","&nbsp;");
        return escData;
    }


    private boolean pageEmpty = true;
    private ArrayList pushedPDF = new ArrayList();
    /**
     * ����PDF�t�@�C�����W����Ԃ��܂��B
     * @return ����PDF�t�@�C�����W��
     */
    public ArrayList getPushedPDF(){
        return pushedPDF;
    }

    /**
     * �f�[�^������XML�t�@�C�� ��Ԃ��܂��B
     * @return �f�[�^������XML�t�@�C��
     */
    public File getDataFile() {
        return dataFile;
    }

    /**
     * �y�[�W�����݂��邩 ��Ԃ��܂��B
     * @return �y�[�W�����݂��邩
     */
    public boolean isPageEmpty() {
        return pageEmpty;
    }

    /**
     * �y�[�W�����݂��邩 ��ݒ肵�܂��B
     * @param pageEmpty �y�[�W�����݂��邩
     */
    public void setPageEmpty(boolean pageEmpty) {
        this.pageEmpty = pageEmpty;
    }
}

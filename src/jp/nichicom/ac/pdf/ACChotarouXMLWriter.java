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
 * 帳票太郎のXMLフォーマットを基にPDFを出力する仲介クラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Mizuki Tsutsumi
 * @version 1.0 2005/12/01
 */
public class ACChotarouXMLWriter {
    // データのAttribute設定機能を追加
    private HashMap dataAttributeMap = new HashMap();

    // 設定データのタグをエスケープしないモードを追加
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
     * コンストラクタです。
     */
    public ACChotarouXMLWriter() {
        formatNameArray = new ArrayList();
        formatFilePathArray = new ArrayList();
    }
    
    /**
     * フォーマットキャッシュを初期化します。
     */
    public void clearFormat(){
        formatNameArray.clear();
        formatFilePathArray.clear();
    }

    /**
     * アトリビュートを設定します。
     * 
     * @param objectName String タグ名称
     * @param key String アトリビュート名
     * @param value String 値
     */
    public void addAttribute(String objectName, String key, String value) {
        String data = "";
        String addKey = " " + key + "=\"";
        // 指定されたオブジェクトが既に登録されているかチェック
        if (dataAttributeMap.containsKey(objectName)) {
            // 登録されていれば、データを退避
            data = dataAttributeMap.get(objectName).toString();
            int idx =data.indexOf(addKey);
            if(idx>=0){
                int tokenIdx = data.indexOf("\"", idx+addKey.length());
                if(tokenIdx>idx){
                    //同名のキーが既出の場合は値だけ差し替える
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
     * 項目名と値の組を追加します。
     * 
     * @param key 項目名
     * @param value 値
     */
    public void addData(String key, String value) {
        dataKeyArray.add(key);
        dataValueArray.add(value);
    }

    /**
     * 指定したデータをタグのエスケープ無しに追加します。
     * 
     * @param key String 項目名
     * @param value String 値
     */
    public void addDataDirect(String key, String value) {
        dataDirectMap.put(key, value);
    }

    /**
     * フォーマット名とファイルパスの設定を指示します。
     * 
     * @param name フォーマット名
     * @param filePath ファイルパス(full path)
     */
    public void addFormat(String name, String filePath) {
        if(formatNameArray.indexOf(name)<0){
            //新出なら追加
            formatNameArray.add(name);
            formatFilePathArray.add(filePath);
        }
    }

    /**
     * ページ設定の開始を指示します。
     * 
     * @param formatName ページで使用するフォーマット名
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
     * プリント設定の開始を指示します。
     */
    public void beginPrintEdit() {
        clear();
        
        //出力用一時ファイルを作成
        String fileName;
        try {
            fileName = VRDateParser.format(Calendar.getInstance().getTime(),
                    "yyyyMMddHHmmss")
                    + ".xml";
        } catch (Exception e) {
            //代用ファイル名を使用する
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
                //ファイル作成可能な場合
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
     * 出力結果をファイル生成して返します。
     * @return 出力ファイル 
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
            // ヘッダ出力
            writeHead(writer);

            // データファイルを転記
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile), "UTF-8"));
                // 読み込むデータがなくなるまで読み込み
                String contents;
                while ((contents = br.readLine()) != null) {
                    // 書き込むデータがなくなるまで書き込み
                    writer.write(contents);
                    writer.write(CRLF);
                    writer.flush();
                }
            } finally {
                if (br != null) {
                    br.close();
                }
            }
            // フッタ出力
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
     * ヘッダ情報を出力します。
     * @param out 出力先
     * @throws IOException 処理例外
     */
    protected void writeHead(Writer out) throws IOException{
        StringBuffer sb = new StringBuffer();
        
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append(CRLF);
        sb.append("<PrintData>");
        sb.append(CRLF);

        // formatの定義
        for (int i = 0; i < formatNameArray.size(); i++) {
            sb.append("<Format Id=\"");
            sb.append(String.valueOf(formatNameArray.get(i)));
            sb.append("\" Src=\"file:");
            sb.append(String.valueOf(formatFilePathArray.get(i)));
            sb.append("\" />");
            sb.append(CRLF);
        }
        
        // タイトル設定
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
     * フッタ情報を出力します。
     * @param out 出力先
     * @throws IOException 処理例外
     */
    protected void writeFoot(Writer out) throws IOException{
        StringBuffer sb = new StringBuffer();

        sb.append("</Print>");
        sb.append(CRLF);

        sb.append("</PrintData>");
        out.write(sb.toString());
    }
    /**
     * 出力結果をクリアします。
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
        //既存フォーマットのクリア
        clearFormat();
    }

    /**
     * プリント設定の開始を指示します。
     * 
     * @param title タイトル
     * @param printer プリンタ
     */
    public void beginPrintEdit(String title, String printer) {
        printTitle = title;
        printPrinter = printer;
    }

    /**
     * ページ設定の終了を指示します。
     */
    public void endPageEdit() {
        try{
            if(writer!=null){
                //1ページ分の出力
                writePage(writer, printTitle, printPrinter, pageFormatName,
                        dataKeyArray, dataValueArray, dataAttributeMap,
                        dataDirectMap);
                writer.flush();
                //解放
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
     * プリント設定の終了を指示します。
     */
    public void endPrintEdit() {
    }
    protected final String CRLF = "\n";
    protected final String TAB = "\t";
    /**
     * ページデータを出力します。
     * 
     * @param out 出力先
     * @param printTitle タイトル
     * @param printPrinter プリンタ
     * @param pageFormatName フォーマット名
     * @param dk データキー集合
     * @param dv データバリュー集合
     * @param attributeMap 属性マップ
     * @param directMap 直接出力項目マップ
     * @throws IOException 処理例外
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
//     * XML形式の帳票太郎用データを取得します。
//     * 
//     * @return XMLデータ
//     */
//    public String getDataXml() {
//        StringBuffer sb = new StringBuffer();
//
//        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//        sb.append(CRLF);
//        sb.append("<PrintData>");
//        sb.append(CRLF);
//
//        // formatの定義
//        for (int i = 0; i < formatNameArray.size(); i++) {
//            sb.append("<Format Id=\"");
//            sb.append(formatNameArray.get(i));
//            sb.append("\" Src=\"file:");
//            sb.append(formatFilePathArray.get(i));
//            sb.append("\" />");
//            sb.append(CRLF);
//        }
//
//        // データ(ページ数分ループ)
//        for (int i = 0; i < printTitleArray.size(); i++) {
//            // タイトル設定
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
     * 第一引数に指定された値と同じ要素が存在すれば、アトリビュートを設定します。
     * 
     * @param objectName String タグ名称
     * @param map HashMap アトリビュート値を保持しているHashMap
     * @return String 作成したタグ
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
     * 引数に指定されたHashMapに保持しているタグを全て出力した 文字列を返却します。
     * 
     * @param map HashMap アトリビュートを保持したHashMap
     * @return String 作成した文字列
     */
    private String getAttributeRemainder(HashMap map) {
        StringBuffer data = new StringBuffer();
        Object key = null;
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            key = it.next();
            // インテンド
            data.append("\t\t\t");
            data.append("<");
            data.append(key);
            data.append(map.get(key));
            data.append("/>\n");
        }
        return data.toString();
    }

    /**
     * タグ文字のエスケープを行います。
     * 
     * @param data String 変換対象データ
     * @return String 変換後の値
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
     * 分割PDFファイル名集合を返します。
     * @return 分割PDFファイル名集合
     */
    public ArrayList getPushedPDF(){
        return pushedPDF;
    }

    /**
     * データ部分のXMLファイル を返します。
     * @return データ部分のXMLファイル
     */
    public File getDataFile() {
        return dataFile;
    }

    /**
     * ページが存在するか を返します。
     * @return ページが存在するか
     */
    public boolean isPageEmpty() {
        return pageEmpty;
    }

    /**
     * ページが存在するか を設定します。
     * @param pageEmpty ページが存在するか
     */
    public void setPageEmpty(boolean pageEmpty) {
        this.pageEmpty = pageEmpty;
    }
}

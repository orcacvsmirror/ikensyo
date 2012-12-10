package jp.nichicom.ac.pdf;

import java.awt.Color;

import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;
import jp.nichicom.ac.core.ACPDFCreatable;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.logging.VRLogger;

/**
 * 帳票太郎XML関連の汎用メソッドを集めたクラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see jp.nichicom.ac.pdf.ACChotarouXMLWriter
 */
public class ACChotarouXMLUtilities {
    private static ACChotarouXMLUtilities singleton;

    /**
     * インスタンスを取得します。
     * 
     * @deprecated 直接staticメソッドを呼んでください。
     * @return インスタンス
     */
    public static ACChotarouXMLUtilities getInstance() {
        if (singleton == null) {
            singleton = new ACChotarouXMLUtilities();
        }
        return singleton;
    }

    /**
     * コンストラクタです。
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected ACChotarouXMLUtilities() {
    }

    /**
     * チェック項目をXML出力します。
     * 
     * @param pd 印刷管理クラス
     * @param data データソース
     * @param key データキー
     * @param target タグ名
     * @param checkValue チェックとみなす値
     * @throws Exception 処理例外
     * @return 出力したか
     */
    public static boolean addCheck(ACChotarouXMLWriter pd, VRMap data,
            String key, String target, int checkValue) throws Exception {
        Object obj = VRBindPathParser.get(key, data);
        if (obj instanceof Integer) {
            if (((Integer) obj).intValue() != checkValue) {
                setInvisible(pd, target);
                return false;
            }
        }
        return true;
    }

    /**
     * バイト単位で改行を整形した文字をXML出力します。
     * 
     * @param text 変換対象
     * @param columns 折り返し文字数
     * @throws Exception 処理例外
     * @return 整形した文字列
     */
    public static void setTextOfLineWrapOnByte(ACChotarouXMLWriter pd,
            VRMap data, String key, String target, int columns)
            throws Exception {
        Object obj = VRBindPathParser.get(key, data);
        if (obj != null) {
            setValue(pd, target, ACTextUtilities.concatLineWrapOnByte(
                    ACCastUtilities.toString(obj), columns));
        }
    }

    /**
     * 文字単位で改行を整形した文字をXML出力します。
     * 
     * @param text 変換対象
     * @param columns 折り返し文字数
     * @throws Exception 処理例外
     * @return 整形した文字列
     */
    public static void setTextOfLineWrapOnChar(ACChotarouXMLWriter pd,
            VRMap data, String key, String target, int columns)
            throws Exception {
        Object obj = VRBindPathParser.get(key, data);
        if (obj != null) {
            setValue(pd, target, ACTextUtilities.concatLineWrapOnChar(
                    ACCastUtilities.toString(obj), columns));
        }
    }

    /**
     * 選択型項目をXML出力します。
     * 
     * @param pd 印刷管理クラス
     * @param data データソース
     * @param key データキー
     * @param shapes Visible制御対象群
     * @param offset 値と配列添え字とのオフセット
     * @throws Exception 処理例外
     * @return 出力したか
     */
    public static boolean setSelection(ACChotarouXMLWriter pd, VRMap data,
            String key, String[] shapes, int offset) throws Exception {
        Object obj = VRBindPathParser.get(key, data);
        if (!(obj instanceof Integer)) {
            return false;
        }

        int end = shapes.length;
        int pos = ((Integer) obj).intValue() + offset;
        if ((pos < 0) || (pos >= end)) {
            // すべてfalse
            for (int i = 0; i < end; i++) {
                setInvisible(pd, shapes[i]);
            }
        } else {
            for (int i = 0; i < pos; i++) {
                setInvisible(pd, shapes[i]);
            }
            for (int i = pos + 1; i < end; i++) {
                setInvisible(pd, shapes[i]);
            }
        }
        return true;
    }

    /**
     * 帳票定義体項目を非表示にします。
     * 
     * @param pd 印刷管理クラス
     * @param shape Visible制御対象
     * @throws Exception 処理例外
     */
    public static void setInvisible(ACChotarouXMLWriter pd, String shape)
            throws Exception {
        setVisible(pd, shape, false);
    }

    /**
     * 帳票定義体項目の表示可否を設定します。
     * 
     * @param pd 印刷管理クラス
     * @param shape Visible制御対象
     * @param visible 表示可否
     * @throws Exception 処理例外
     */
    public static void setVisible(ACChotarouXMLWriter pd, String shape,
            boolean visible) throws Exception {
        pd.addAttribute(shape, "Visible", visible ? "TRUE" : "FALSE");
    }

    /**
     * 選択型項目をXML出力します。
     * 
     * @param pd 印刷管理クラス
     * @param data データソース
     * @param key データキー
     * @param shapes Visible制御対象群
     * @param offset 値と配列添え字とのオフセット
     * @param optionKey 連動して出力する文字列キー
     * @param optionTarget 連動して出力する文字列出力位置
     * @param useOptionIndex 連動して出力する文字列の出力条件となる選択番号
     * @throws Exception 処理例外
     * @return 出力したか
     */
    public static boolean setSelection(ACChotarouXMLWriter pd, VRMap data,
            String key, String[] shapes, int offset, String optionKey,
            String optionTarget, int useOptionIndex) throws Exception {
        Object obj = VRBindPathParser.get(key, data);
        if (!(obj instanceof Integer)) {
            return false;
        }

        int end = shapes.length;
        int index = ((Integer) obj).intValue();
        int pos = index + offset;
        if ((pos < 0) || (pos >= end)) {
            // すべてfalse
            for (int i = 0; i < end; i++) {
                setInvisible(pd, shapes[i]);
            }
        } else {
            for (int i = 0; i < pos; i++) {
                setInvisible(pd, shapes[i]);
            }
            for (int i = pos + 1; i < end; i++) {
                setInvisible(pd, shapes[i]);
            }
        }

        if (index == useOptionIndex) {
            setValue(pd, data, optionKey, optionTarget);
        }

        return true;
    }

    /**
     * 文字項目をXML出力します。
     * 
     * @param pd 印刷管理クラス
     * @param target タグ名
     * @param value 出力値
     * @throws Exception 処理例外
     * @return 出力したか
     */
    public static boolean setValue(ACChotarouXMLWriter pd, String target,
            Object value) throws Exception {
        String val;
        if (value == null) {
            val = "";
        } else {
            val = String.valueOf(value);
        }
        if (ACFrame.getInstance().getFrameEventProcesser() instanceof ACPDFCreatable) {
            val = ((ACPDFCreatable) ACFrame.getInstance()
                    .getFrameEventProcesser()).toPrintable(val);
        }
        pd.addData(target, val);
        return true;
    }

    /**
     * 文字項目をXML出力します。
     * 
     * @param pd 印刷管理クラス
     * @param data データソース
     * @param key データキー
     * @param target タグ名
     * @throws Exception 処理例外
     * @return 出力したか
     */
    public static boolean setValue(ACChotarouXMLWriter pd, VRMap data,
            String key, String target) throws Exception {
        Object obj = VRBindPathParser.get(key, data);
        return setValue(pd, target, obj);
    }

    /**
     * 文字項目をXML出力します。
     * 
     * @param pd 印刷管理クラス
     * @param data データソース
     * @param key データキー
     * @param target タグ名
     * @param head 文字項目値の前に連結して出力する文字列
     * @throws Exception 処理例外
     * @return 出力したか
     */
    public static boolean setValue(ACChotarouXMLWriter pd, VRMap data,
            String key, String target, String head) throws Exception {
        Object obj = VRBindPathParser.get(key, data);
        if (obj != null) {
            String text = head + String.valueOf(obj);
            return setValue(pd, target, text);
        }
        return false;
    }

    /**
     * PDFファイルを生成し、生成したPDFファイルを開きます。
     * 
     * @param pd 印刷管理クラス
     * @throws Exception 処理例外
     */
    public static void openPDF(ACChotarouXMLWriter pd) throws Exception {
        if (ACFrame.getInstance().getFrameEventProcesser() instanceof ACPDFCreatable) {
            ACPDFCreatable processer = (ACPDFCreatable) ACFrame.getInstance()
                    .getFrameEventProcesser();
            // PDFファイル生成
            String pdfPath = processer.writePDF(pd);

            // 生成したPDFを開く
            if ((pdfPath != null) && (!"".equals(pdfPath))) {
                processer.openPDF(pdfPath);
            }
        }
    }
    
    /**
     * PDFファイルを生成します。
     * 
     * @param pd 印刷管理クラス
     * @return 生成ファイルパス
     * @throws Exception 処理例外
     */
    public static String writePDF(ACChotarouXMLWriter pd) throws Exception {
        //スレッド同期

        if(pd.isPageEmpty()||(pd.getDataFile()==null)){
            //ファイルが無いならnullを返却
            return null;
        }
        
        //ファイルシステムがファイルサイズを正しく返すまでに遅延があるため、試行する。
        boolean zeroSize = true;
        for(int i=0; i<30; i++){
            //30秒までは試行する
            if(pd.getDataFile().length()>0){
                //ファイルサイズが計上されたら抜ける。
                zeroSize = false;
                break;
            }
            //ファイルサイズ0なら1秒待機する。
            Thread.sleep(1000);
        }
        if (zeroSize) {
            // 再度サイズ0ならnullを返却
            VRLogger.info("PDFファイル生成が行われていません。");
            return null;
        }

        if (ACFrame.getInstance().getFrameEventProcesser() instanceof ACPDFCreatable) {
            ACPDFCreatable processer = (ACPDFCreatable) ACFrame.getInstance()
                    .getFrameEventProcesser();
            // PDFファイル生成
            return processer.writePDF(pd);
        }
        return null;
    }
    
//    /**
//     * 分割PDFを一時出力します。
//     * @param pd 印刷管理クラス
//     * @throws Exception 処理例外
//     */
//    public static void pushConcatPDF(ACChotarouXMLWriter pd) throws Exception {
//        pd.endPrintEdit();
//        
//        String pdfPath = writePDF(pd);
//        if (ACFrame.getInstance().getFrameEventProcesser() != null) {
//            ACFrame.getInstance().getFrameEventProcesser().createSplash("印刷結果加工処理");
//        }
//        if ((pdfPath != null) && (!"".equals(pdfPath))) {
//            File renamed = new File(pdfPath + pd.getPushedPDF().size()
//                    + "-.pdf");
//            if (new File(pdfPath).renameTo(renamed)) {
//                pd.getPushedPDF().add(renamed.getAbsolutePath());
//            }
//        }
//        pd.beginPrintEdit();
//    }

//    /**
//     * 分割PDFを結合して開きします。
//     * 
//     * @param pd 印刷管理クラス
//     * @throws Exception 処理例外
//     */
//    public static void openConcatPDF(ACChotarouXMLWriter pd) throws Exception {
//        if (pd.getPushedPDF().isEmpty()) {
//            //分割不要なら直接開く
//            openPDF(pd);
//            return;
//        } else {
//            if (ACFrame.getInstance().getFrameEventProcesser() instanceof ACDefaultFrameEventProcesser) {
//                ACDefaultFrameEventProcesser processer = (ACDefaultFrameEventProcesser) ACFrame
//                        .getInstance().getFrameEventProcesser();
//                //最後の分割対象を出力
//                pushConcatPDF(pd);
//                pd.clear();
//                processer.openConcatPDF(pd);
//                pd.getPushedPDF().clear();
//            }
//        }
//    }
    
    /**
     * 使用する帳票定義体を登録します。
     * 
     * @param pd 印刷管理クラス
     * @param formatID 帳票定義体ID
     * @param formatFileName 帳票定義体のファイル名
     * @throws Exception 処理例外
     */
    public static void addFormat(ACChotarouXMLWriter pd, String formatID,
            String formatFileName) throws Exception {
        ACFrameEventProcesser processer = ACFrame.getInstance()
                .getFrameEventProcesser();
        if (processer instanceof ACPDFCreatable) {
            ACPDFCreatable pdfCreatable = (ACPDFCreatable) processer;
            String path = pdfCreatable.getPrintFormatDirectory()
                    + formatFileName;
            // フォーマットを追加
            pd.addFormat(formatID, path);

        } else {
            throw new IllegalArgumentException(
                    "指定のFrameEventProcesserは、PDF生成可能なクラス(ACPDFCreatable)ではありません。");
        }
    }

    /**
     * 帳票定義体項目の背景を指定色で塗りつぶします。
     * 
     * @param pd 印刷管理クラス
     * @param target 着色対象
     * @param color 色
     * @throws Exception 処理例外
     */
    public static void setFillColor(ACChotarouXMLWriter pd, String target,
            String color) throws Exception {
        pd.addAttribute(target, "BackStyle", "Solid");
        pd.addAttribute(target, "BackColor", color);
    }

    /**
     * 帳票定義体項目の背景を指定色で塗りつぶします。
     * 
     * @param pd 印刷管理クラス
     * @param target 着色対象
     * @param color 色
     * @throws Exception 処理例外
     */
    public static void setFillColor(ACChotarouXMLWriter pd, String target,
            Color color) throws Exception {
        if (color != null) {
            final char[] table = new char[] { '0', '1', '2', '3', '4', '5',
                    '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
            StringBuffer sb = new StringBuffer("#");
            int[] array = new int[] { color.getRed(), color.getGreen(),
                    color.getBlue() };
            int end = array.length;
            for (int i = 0; i < end; i++) {
                sb.append(table[array[i] / 0x10]);
                sb.append(table[array[i] % 0x10]);
            }
            setFillColor(pd, target, sb.toString());
        }
    }

}

/** TODO <HEAD_IKENSYO> */
package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.ACOSInfo;
import jp.nichicom.ac.core.ACDefaultFrameEventProcesser;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACPDFCreatable;
import jp.nichicom.ac.io.ACFileUtilities;
import jp.nichicom.ac.io.ACFileUtilitiesCreateResult;
import jp.nichicom.ac.io.ACPropertyXML;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.splash.ACSplash;
import jp.nichicom.ac.util.splash.ACSplashable;
import jp.nichicom.bridge.pdf.BridgeSimplePDF;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

public class IkenshoFrameEventProcesser extends ACDefaultFrameEventProcesser
        implements ACPDFCreatable {
    protected HashMap pdfReplaceHash;
    protected String printPDFDirectory;
    protected String printFormatDirectory;
    protected boolean pdfReplacerReaded;
    protected ACSplash splash;
    protected boolean properityReaded;
    protected ACPropertyXML properityXML;
    private BridgeSimplePDF pdfManager;

    public String getPrintPDFDirectory() {
        if (printPDFDirectory == null) {
            printPDFDirectory = ACFrame.getInstance().getExeFolderPath()
                    + ACConstants.FILE_SEPARATOR + "pdf"
                    + ACConstants.FILE_SEPARATOR;
        }
        return printPDFDirectory;
    }

    public String getPrintFormatDirectory() {
        if (printFormatDirectory == null) {
            printFormatDirectory = ACFrame.getInstance().getExeFolderPath()
                    + ACConstants.FILE_SEPARATOR + "format"
                    + ACConstants.FILE_SEPARATOR;
        }
        return printFormatDirectory;
    }

    public String toPrintable(String text) {
        String path = getPrintableReplaceXMLPath();
        if (path != null) {
            if ((pdfReplaceHash == null) && (!pdfReplacerReaded)) {
                pdfReplacerReaded = true;
                try {
                    ACPropertyXML pdfReplacer = new ACPropertyXML(path);
                    pdfReplacer.read();
                    pdfReplaceHash = (HashMap) pdfReplacer.getData("replace");
                } catch (Exception ex) {
                    pdfReplaceHash = null;
                }
            }
            if (pdfReplaceHash != null) {
                Iterator it = pdfReplaceHash.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry ent = (Map.Entry) it.next();
                    text = text.replaceAll(String.valueOf(ent.getKey()), String
                            .valueOf(ent.getValue()));
                }
                return text;
            }
        }
        return text;
    }

    /**
     * PDF出力時の置換文字定義ファイルパスを返します。
     * 
     * @return 置換文字定義ファイルパス
     */
    protected String getPrintableReplaceXMLPath() {
        return "pdf_def.xml";
    }

    public void openPDF(String pdfPath) throws Exception {
        String acrobatPath = "";
        if (ACFrame.getInstance().hasProperty("Acrobat/Path")) {
            acrobatPath = ACFrame.getInstance().getProperty("Acrobat/Path");
        }

        String[] arg = { "", pdfPath };

        // Macなら関連付けで開く
        if (ACOSInfo.isMac()) {
            arg[0] = "open";
        } else {
            arg[0] = acrobatPath;
            if (!new File(acrobatPath).exists()) {
                // アクロバットが存在しない
                ACMessageBox.showExclamation("PDFの設定が正しくありません。"
                        + ACConstants.LINE_SEPARATOR
                        + "[メインメニュー]-[設定(S)]-[PDF設定(P)]よりAcrobatの設定を行ってください。");
                return;
            }
        }
        Runtime.getRuntime().exec(arg);
    }

    public ACSplashable createSplash(String title) throws Exception {
        if (splash == null) {
            splash = new ACSplash();
            splash.setIconPathes(getSplashFilePathes());
            splash.refreshSize(getSplashWindowSize());
        }

        if (!splash.isVisible()) {
            splash.showModaless(title);
        }else{
            if ((title == null) || "".equals(title)) {
                splash.setMessage("次画面展開中...");
            }else{
                splash.setMessage("「" + title + "」展開中...");
            }
        }
        return splash;
    }

    /**
     * スプラッシュ画面サイズを返します。
     * 
     * @return スプラッシュ画面サイズ
     */
    public Dimension getSplashWindowSize() {
        return new Dimension(400, 120);
    }

    public void showExceptionMessage(Throwable ex) {
        IkenshoCommon.showExceptionMessage(ex);
    }

    public ACPropertyXML getPropertyXML() throws Exception {
        if (!properityReaded) {
            properityReaded = true;
            if (properityXML == null) {
                String path = getProperityXMLPath();
                properityXML = new ACPropertyXML(path);
                if (new File(path).exists()) {
                    properityXML.read();
                }
            }
        }
        return properityXML;
    }
    /**
     * 印刷時に表示するスプラッシュを返します。
     * 
     * @return スプラッシュ
     * @throws Exception 処理例外
     */
    protected ACSplashable getPrintingSplash() throws Exception {
        return createSplash("印刷中");
    }
    public String writePDF(ACChotarouXMLWriter data) throws Exception {
        String pdfFilePath = createPDFPath();
        if ((pdfFilePath == null) || "".equals(pdfFilePath)) {
            // PDFを作成できない場合
            return "";
        }

        ACSplashable splash = getPrintingSplash();

        File f = data.createDataFile();

        // PDFのデータをログ出力
        if (f != null) {
            // ストリームからの逐次読み出しで出力
            createPDFManager().write(f, pdfFilePath);

            // 一時ファイルを削除
            f.delete();
        }

        if (splash != null) {
            splash.close();
            splash = null;
        }

        return pdfFilePath;
    }

    /**
     * 保存するPDFファイル名を返します。
     * 
     * @return PDFファイル名
     * @throws Exception
     */
    protected String createPDFFileName() throws Exception {
        Calendar cal = Calendar.getInstance();
        return VRDateParser.format(cal.getTime(), "yyyyMMddHHmmss") + ".pdf";
    }

    /**
     * PDF生成管理オブジェクトを返します。
     * @return PDF生成管理オブジェクト
     */
    protected BridgeSimplePDF createPDFManager(){
        if(pdfManager==null){
            pdfManager = new BridgeSimplePDF(); 
        }
        return pdfManager;
    }

    /**
     * PDFファイルの作成先ファイルパスを返します。
     * <p>
     * 作成に失敗した場合、nullを返します。
     * </p>
     * 
     * @param dir フォルダ
     * @param fileName ファイル名
     * @return 作成先ファイルパス
     * @throws Exception 処理例外
     */
    protected String createPDFPath() throws Exception {
        ACFileUtilitiesCreateResult result = ACFileUtilities
                .getCreatableFilePath(createPDFFileName(),
                        getPrintPDFDirectory(), System
                                .getProperty("java.io.tmpdir")
                                + ACConstants.FILE_SEPARATOR
                                + "pdf"
                                + ACConstants.FILE_SEPARATOR);
        if (result != null) {
            switch (result.getResult()) {
            case ACFileUtilitiesCreateResult.ERROR_OF_DIRECTORY_CREATE:
                // 一時ディレクトリの作成に失敗
                processErrorOnPDFDirectoryCreate();
                return null;
            case ACFileUtilitiesCreateResult.ERROR_OF_FILE_CREATE:
                // ファイル作成失敗
                processErrorOnPDFFileCreate();
                return null;
            }
            if (result.getFile() != null) {
                return result.getFile().getAbsolutePath();
            }
        }
        return null;
    }

    /**
     * PDF生成管理オブジェクトを破棄します。
     */
    protected void disposePDFManager(){
        pdfManager = null;
    }

    /**
     * PDFファイルの生成先フォルダの作成に失敗した場合の処理です。
     * 
     * @throws Exception 処理例外
     */
    protected void processErrorOnPDFDirectoryCreate() throws Exception {
        ACMessageBox.showExclamation("印刷に失敗しました。" + ACConstants.LINE_SEPARATOR
                + "PDFファイルの作成先フォルダを生成できません。");
    }

    /**
     * PDFファイルの作成を拒否された場合の処理です。
     * 
     * @throws Exception 処理例外
     */
    protected void processErrorOnPDFFileCreate() throws Exception {
        ACMessageBox.showExclamation("印刷に失敗しました。" + ACConstants.LINE_SEPARATOR
                + "PDFファイルを作成する権限がありません。");
    }
    /**
     * PDFビューワを発見できなかった場合の処理です。
     * 
     * @throws Exception 処理例外
     */
    protected void processErrorOnPDFViewerNotFound() throws Exception {
        ACMessageBox.showExclamation("PDFビューワの設定を取得できません。");
    }
    
    /**
     * コンストラクタです。
     */
    public IkenshoFrameEventProcesser() {
    }

    protected String getProperityXMLPath() {
        return "IkensyoProperityXML.xml";
    }

    public String[] getSplashFilePathes() {
        return new String[] {
                "jp/or/med/orca/ikensho/images/splash/splash_01.png",
                "jp/or/med/orca/ikensho/images/splash/splash_02.png",
                "jp/or/med/orca/ikensho/images/splash/splash_03.png",
                "jp/or/med/orca/ikensho/images/splash/splash_04.png",
                "jp/or/med/orca/ikensho/images/splash/splash_05.png",
                "jp/or/med/orca/ikensho/images/splash/splash_06.png",
                "jp/or/med/orca/ikensho/images/splash/splash_07.png",
                "jp/or/med/orca/ikensho/images/splash/splash_08.png",
                "jp/or/med/orca/ikensho/images/splash/splash_09.png",
                "jp/or/med/orca/ikensho/images/splash/splash_10.png",
                "jp/or/med/orca/ikensho/images/splash/splash_11.png",
                "jp/or/med/orca/ikensho/images/splash/splash_12.png",
                "jp/or/med/orca/ikensho/images/splash/splash_13.png",
                "jp/or/med/orca/ikensho/images/splash/splash_14.png",
                "jp/or/med/orca/ikensho/images/splash/splash_15.png",
                "jp/or/med/orca/ikensho/images/splash/splash_16.png",
                "jp/or/med/orca/ikensho/images/splash/splash_17.png",
                "jp/or/med/orca/ikensho/images/splash/splash_18.png",
                "jp/or/med/orca/ikensho/images/splash/splash_19.png",
                "jp/or/med/orca/ikensho/images/splash/splash_20.png", };
    }

    public String getDefaultMessageBoxTitle() {
        return "医見書";
    }

    public String getFrameIconPath() {
        return "jp/or/med/orca/ikensho/images/icon/flameicon.gif";
    }
}

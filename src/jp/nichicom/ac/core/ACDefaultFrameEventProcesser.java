package jp.nichicom.ac.core;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.ACOSInfo;
import jp.nichicom.ac.core.debugger.ACFrameEventDebugger;
import jp.nichicom.ac.core.debugger.ACStaticDebugger;
import jp.nichicom.ac.io.ACFileUtilities;
import jp.nichicom.ac.io.ACFileUtilitiesCreateResult;
import jp.nichicom.ac.io.ACPropertyXML;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.ACMessageBoxExceptionPanel;
import jp.nichicom.ac.util.splash.ACSplash;
import jp.nichicom.ac.util.splash.ACSplashChaine;
import jp.nichicom.ac.util.splash.ACSplashable;
import jp.nichicom.bridge.pdf.BridgeSimplePDF;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.logging.VRLogger;

/**
 * 標準的な基本システムイベントを実装したイベント処理クラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see AbstractACFrameEventProcesser
 */

public class ACDefaultFrameEventProcesser extends AbstractACFrameEventProcesser
        implements ACPDFCreatable, ACFrameEventDebugger {
    private Level affairLogLevel = Level.ALL;
    private Level dbLogLevel = Level.ALL;
    private int dbLogRange = ACFrameEventDebugger.LOG_RANGE_DB_BASIC;
    private BridgeSimplePDF pdfManager;
    private Map pdfReplaceHash;
    private String printFormatDirectory;
    private Level printLogLevel = Level.ALL;
    private String printPDFDirectory;
    private boolean properityReaded;
    private ACPropertyXML properityXML;

    private ACSplash splash;

    /**
     * コンストラクタです。
     */
    public ACDefaultFrameEventProcesser() {
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

    public void finalize() {
        if (splash != null) {
            ACSplashChaine.getInstance().remove(splash);
        }
    }

    public Level getAffairLogLevel() {
        return affairLogLevel;
    }

    public Level getDBLogLevel() {
        return dbLogLevel;
    }

    public int getDBLogRange() {
        return dbLogRange;
    }

    public String getPrintFormatDirectory() {
        if (printFormatDirectory == null) {
            printFormatDirectory = ACFrame.getInstance().getExeFolderPath()
                    + ACConstants.FILE_SEPARATOR + "format"
                    + ACConstants.FILE_SEPARATOR;
        }
        return printFormatDirectory;
    }

    public Level getPrintLogLevel() {
        return printLogLevel;
    }

    public String getPrintPDFDirectory() {
        if (printPDFDirectory == null) {
            printPDFDirectory = ACFrame.getInstance().getExeFolderPath()
                    + ACConstants.FILE_SEPARATOR + "pdf"
                    + ACConstants.FILE_SEPARATOR;
        }
        return printPDFDirectory;
    }

    public ACPropertyXML getPropertyXML() throws Exception {
        if (!properityReaded) {
            properityReaded = true;
            if (properityXML == null) {
                String path = getPropertyXMLPath();
                properityXML = new ACPropertyXML(path);
                if (new File(path).exists()) {
                    try {
                        properityXML.read();
                    } catch (Exception ex) {
                        // ファイル読み込み例外
                        if (setDefaultProperityXML(properityXML)) {
                            // 例外を投げる場合
                            throw ex;
                        }
                    }
                } else {
                    // ファイルがなければデフォルト設定とする
                    setDefaultProperityXML(properityXML);
                }
            }
        }
        return properityXML;
    }

    /**
     * スプラッシュ用の画像ファイルパス配列を返します。
     * 
     * @throws Exception 処理例外
     * @return スプラッシュ用の画像ファイルパス配列
     */
    public String[] getSplashFilePathes() {
        return null;
    }

    /**
     * スプラッシュ画面サイズを返します。
     * 
     * @return スプラッシュ画面サイズ
     */
    public Dimension getSplashWindowSize() {
        return new Dimension(400, 120);
    }

    public void openPDF(String pdfPath) throws Exception {
        disposePDFManager();
        
        String[] arg = { "", pdfPath };
        
        if (ACOSInfo.isMac()) {
            // Macならば関連付けで開く
            arg[0] = "open";
        } else {
            // WinならばAcrobatのExeをキックする。
            String acrobatPath = getPDFViewerPath();

            arg[0] = acrobatPath;
            if (!new File(acrobatPath).exists()) {
                // アクロバットが存在しない
                processErrorOnPDFViewerNotFound();
                return;
            }
        }
        Runtime.getRuntime().exec(arg);
    }

    public void refleshSetting() {
        String path = getDebuggerXMLPath();
        ACPropertyXML properityXML = new ACPropertyXML(path);
        if (new File(path).exists()) {
            try {
                properityXML.read();
                // ログマネージャ
                if (properityXML.hasValueAt("Log/Level/Logger")) {
                    VRLogger.setLevel(Level.parse(properityXML
                            .getValueAt("Log/Level/Logger")));
                } else {
                    VRLogger.setLevel(Level.INFO);
                }
                // 印刷ログ
                if (properityXML.hasValueAt("Log/Level/Print")) {
                    printLogLevel = Level.parse(properityXML
                            .getValueAt("Log/Level/Print"));
                } else {
                    printLogLevel = Level.ALL;
                }
                // DBログ
                if (properityXML.hasValueAt("Log/Level/DB")) {
                    dbLogLevel = Level.parse(properityXML
                            .getValueAt("Log/Level/DB"));
                } else {
                    dbLogLevel = Level.ALL;
                }
                // DBログ範囲
                if (properityXML.hasValueAt("Log/Range/DB/QueryResult")) {
                    if (Boolean
                            .valueOf(
                                    properityXML
                                            .getValueAt("Log/Range/DB/QueryResult"))
                            .booleanValue()) {
                        dbLogRange |= ACFrameEventDebugger.LOG_RANGE_DB_QUERY_RESULT;
                    } else {
                        dbLogRange &= ~ACFrameEventDebugger.LOG_RANGE_DB_QUERY_RESULT;
                    }
                } else {
                    dbLogRange &= ~ACFrameEventDebugger.LOG_RANGE_DB_QUERY_RESULT;
                }

                // 業務ログ
                if (properityXML.hasValueAt("Log/Level/Affair")) {
                    affairLogLevel = Level.parse(properityXML
                            .getValueAt("Log/Level/Affair"));
                } else {
                    affairLogLevel = Level.ALL;
                }
                // デバッガの表示可否
                if (properityXML.hasValueAt("Debugger/Visible")) {
                    if (Boolean.valueOf(
                            String.valueOf(properityXML
                                    .getValueAt("Debugger/Visible")))
                            .booleanValue()) {
                        // デバッガを表示
                        ACStaticDebugger.getInstance().setVisible(true);
                    } else {
                        // デバッガを非表示
                        ACStaticDebugger.getInstance().setVisible(false);
                        ACStaticDebugger.getInstance().clear();
                    }
                }
                // PDF出力置換
                pdfReplaceHash = null;
                if (properityXML.hasValueAt("PDF/Replace")) {
                    Object obj = properityXML.getData("PDF/Replace");
                    if (obj instanceof Map) {
                        pdfReplaceHash = (Map) obj;
                    }
                }

                // 自動クエリ
                if (properityXML.hasValueAt("Auto/DB")) {
                    // 完了済みチェック
                    boolean finished = false;
                    if (properityXML.hasValueAt("Auto/DB/Executed")) {
                        if (Boolean.valueOf(
                                String.valueOf(properityXML
                                        .getValueAt("Auto/DB/Executed")))
                                .booleanValue()) {
                            finished = true;
                        }
                    }
                    if (!finished) {
                        // 完了済みにする
                        properityXML
                                .setForceValueAt("Auto/DB/Executed", "true");
                        try {
                            properityXML.write();
                            executeAutoQuery(properityXML);
                        } catch (Throwable ex) {
                            VRLogger.info(ex);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }

    }

    public void showExceptionMessage(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String msg = sw.toString();
        try {
            sw.close();
        } catch (Exception ex2) {
        }
        VRLogger.warning(msg);

        StringBuffer sb = new StringBuffer();
        // 例外の日時
        sb.append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                .format(new Date()));
        sb.append(ACConstants.LINE_SEPARATOR);

        // 画面のキャプチャ
        File captureFile = null;
        try {
            captureFile = new File(VRLogger.getPath()
                    + ACConstants.FILE_SEPARATOR
                    + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                    + ".spt");
            FileOutputStream fos = new FileOutputStream(captureFile);
            /*
            JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(fos);
            if (enc != null) {
                enc.encode(new Robot().createScreenCapture(new Rectangle(
                        Toolkit.getDefaultToolkit().getScreenSize())));
            }
            */
            ImageIO.write(new Robot().createScreenCapture(new Rectangle(
                    Toolkit.getDefaultToolkit().getScreenSize())), "jpeg", fos);
            fos.close();
        } catch (Exception ex2) {
            captureFile = null;
        }
        if (captureFile != null) {
            sb.append("Capture File: ");
            sb.append(captureFile.getAbsolutePath());
            sb.append(ACConstants.LINE_SEPARATOR);
        }
        
        appendException(sb, ex);

        // StackTrace
        sb.append(msg);

        // スプラッシュは閉じる
        ACSplashChaine.getInstance().closeAll();

        ACMessageBoxExceptionPanel pnl = new ACMessageBoxExceptionPanel();
        pnl.setInfomation(sb.toString());
        pnl.checkEnvironment();
        ACMessageBox.show(getExceptionMessage(), pnl, true);
        if (pnl.getParent() != null) {
            pnl.getParent().remove(pnl);
        }
        pnl = null;
    }
    
    public String toPrintable(String text) {
        if (pdfReplaceHash != null) {
            Iterator it = pdfReplaceHash.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry ent = (Map.Entry) it.next();
                text = text.replaceAll(String.valueOf(ent.getKey()), String
                        .valueOf(ent.getValue()));
            }
            return text;
        }
        return text;
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
            logOfPrint("writePDF(" + pdfFilePath + "): " + f.getAbsolutePath());

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
     * 例外にコメントを追加します。
     * @param sb コメント追記先
     * @param ex 例外
     */
    protected void appendException(StringBuffer sb, Throwable ex) {
        sb.append("OS:");
        sb.append(System.getProperty("os.name", "unknown"));
        String patch = System.getProperty("sun.os.patch.level", "");
        if (!"unknown".equalsIgnoreCase(patch)) {
            // パッチがunknownなら何も表示しない
            sb.append(" ");
            sb.append(patch);
        }
        sb.append("(");
        sb.append(System.getProperty("os.version", "-"));
        sb.append(")");
        sb.append(", VM:");
        sb.append(System.getProperty("java.vendor", "unknown"));
        sb.append("(");
        sb.append(System.getProperty("java.version", "-"));
        sb.append(")");

        sb.append(ACConstants.LINE_SEPARATOR);
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
     * 自動クエリを処理します。
     * 
     * @param properityXML プロパティファイル
     * @throws Throwable 処理例外
     */
    protected void executeAutoQuery(ACPropertyXML properityXML)
            throws Throwable {
        Object obj;
        obj = properityXML.getValuesAt("Auto/DB/Query");
        if (obj instanceof Map) {
            Map querys = (Map) obj;
            // DBマネージャクラスを動的生成
            obj = Class.forName(
                    String.valueOf(properityXML.getValueAt("Auto/DB/DBM")))
                    .newInstance();
            if (obj instanceof ACDBManager) {
                ACDBManager dbm = (ACDBManager) obj;
                dbm.beginTransaction();
                try {
                    // 登録されたクエリを順に実行
                    Iterator it = querys.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry ent = (Map.Entry) it.next();
                        String key = String.valueOf(ent.getKey());
                        String val = String.valueOf(ent.getValue());
                        if (key.indexOf("Update") >= 0) {
                            // キーにUpdateを含む場合は更新系
                            VRLogger.info("Auto-Update(" + key + "): " + val);
                            VRLogger.info("Auto-Update-Processed: "
                                    + dbm.executeUpdate(val));
                        } else if (key.indexOf("Query") >= 0) {
                            // キーにQueryを含む場合は検索系
                            VRLogger.info("Auto-Query(" + key + "): " + val);
                            VRLogger.info("Auto-Query-Processed: "
                                    + dbm.executeQuery(String.valueOf(ent
                                            .getValue())));
                        } else {
                            VRLogger.info("Auto-SQL-Unknown-Key(" + key + ")");
                        }
                    }
                    dbm.commitTransaction();
                } catch (Exception ex) {
                    dbm.rollbackTransaction();
                    throw ex;
                }
            }
        }
    }

    /**
     * ファイルを作成可能か検証し、作成可能であればそのファイルパスを返します。
     * <p>
     * 作成を拒否された場合、nullを返します。
     * </p>
     * 
     * @param f ファイル
     * @return ファイルパス
     */
    protected String getCreatableFilePath(File f) {
        if (f.exists()) {
            // ファイルは既存
            if (f.canWrite()) {
                // 上書き可能ならパスを返す
                return f.getAbsolutePath();
            }
        } else {
            // ファイル作成を試行
            try {
                if (f.createNewFile()) {
                    // ファイル作成に成功
                    return f.getAbsolutePath();
                }
            } catch (SecurityException ex) {
                // セキュリティによる作成拒否
            } catch (IOException ex) {
                // 入出力例外
            }
        }
        // ファイル作成不能
        return null;
    }

    /**
     * デバッガ設定ファイルパスを返します。
     * 
     * @return デバッガ設定ファイルパス
     */
    protected String getDebuggerXMLPath() {
        return "debugger.xml";
    }

    /**
     * 例外発生時のエラーメッセージを返します。
     * 
     * @return 例外発生時のエラーメッセージ
     */
    protected String getExceptionMessage() {
        return "予期せぬエラーが発生しました。" + ACConstants.LINE_SEPARATOR
                + "詳細情報を添えてサポートセンターにご連絡ください。";
    }

    /**
     * PDFビューワのパスを返します。
     * 
     * @return PDFビューワのパス
     * @throws Exception 処理例外
     */
    protected String getPDFViewerPath() throws Exception {
        String acrobatPath = "";
        if (ACFrame.getInstance().hasProperty("Acrobat/Path")) {
            acrobatPath = ACFrame.getInstance().getProperty("Acrobat/Path");
        }
        return acrobatPath;
    }

    /**
     * 印刷時に表示するスプラッシュを返します。
     * 
     * @return スプラッシュ
     * @throws Exception 処理例外
     */
    protected ACSplashable getPrintingSplash() throws Exception {
        return createSplash("印刷結果");
    }

    /**
     * 環境設定ファイルパスを返します。
     * 
     * @return 環境設定ファイルパス
     */
    protected String getPropertyXMLPath() {
        return "property.xml";
    }

    /**
     * 印刷関連のログを出力します。
     * 
     * @param message ログ
     */
    protected void logOfPrint(Object message) {
        ACFrameEventProcesser eventProcesser = ACFrame.getInstance()
                .getFrameEventProcesser();
        if (eventProcesser instanceof ACFrameEventDebugger) {
            VRLogger.log(((ACFrameEventDebugger) eventProcesser)
                    .getPrintLogLevel(), message);
        }
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
     * 設定ファイル読み込み失敗時にデフォルト設定を追加します。
     * 
     * @param properityMap 設定追加先
     * @return 読み込み例外の場合、例外を投げるか
     */
    protected boolean setDefaultProperityXML(ACPropertyXML properityMap) {
        return true;
    }

}
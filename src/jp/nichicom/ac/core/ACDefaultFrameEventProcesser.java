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
 * �W���I�Ȋ�{�V�X�e���C�x���g�����������C�x���g�����N���X�ł��B
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
     * �R���X�g���N�^�ł��B
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
                splash.setMessage("����ʓW�J��...");
            }else{
                splash.setMessage("�u" + title + "�v�W�J��...");
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
                        // �t�@�C���ǂݍ��ݗ�O
                        if (setDefaultProperityXML(properityXML)) {
                            // ��O�𓊂���ꍇ
                            throw ex;
                        }
                    }
                } else {
                    // �t�@�C�����Ȃ���΃f�t�H���g�ݒ�Ƃ���
                    setDefaultProperityXML(properityXML);
                }
            }
        }
        return properityXML;
    }

    /**
     * �X�v���b�V���p�̉摜�t�@�C���p�X�z���Ԃ��܂��B
     * 
     * @throws Exception ������O
     * @return �X�v���b�V���p�̉摜�t�@�C���p�X�z��
     */
    public String[] getSplashFilePathes() {
        return null;
    }

    /**
     * �X�v���b�V����ʃT�C�Y��Ԃ��܂��B
     * 
     * @return �X�v���b�V����ʃT�C�Y
     */
    public Dimension getSplashWindowSize() {
        return new Dimension(400, 120);
    }

    public void openPDF(String pdfPath) throws Exception {
        disposePDFManager();
        
        String[] arg = { "", pdfPath };
        
        if (ACOSInfo.isMac()) {
            // Mac�Ȃ�Ί֘A�t���ŊJ��
            arg[0] = "open";
        } else {
            // Win�Ȃ��Acrobat��Exe���L�b�N����B
            String acrobatPath = getPDFViewerPath();

            arg[0] = acrobatPath;
            if (!new File(acrobatPath).exists()) {
                // �A�N���o�b�g�����݂��Ȃ�
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
                // ���O�}�l�[�W��
                if (properityXML.hasValueAt("Log/Level/Logger")) {
                    VRLogger.setLevel(Level.parse(properityXML
                            .getValueAt("Log/Level/Logger")));
                } else {
                    VRLogger.setLevel(Level.INFO);
                }
                // ������O
                if (properityXML.hasValueAt("Log/Level/Print")) {
                    printLogLevel = Level.parse(properityXML
                            .getValueAt("Log/Level/Print"));
                } else {
                    printLogLevel = Level.ALL;
                }
                // DB���O
                if (properityXML.hasValueAt("Log/Level/DB")) {
                    dbLogLevel = Level.parse(properityXML
                            .getValueAt("Log/Level/DB"));
                } else {
                    dbLogLevel = Level.ALL;
                }
                // DB���O�͈�
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

                // �Ɩ����O
                if (properityXML.hasValueAt("Log/Level/Affair")) {
                    affairLogLevel = Level.parse(properityXML
                            .getValueAt("Log/Level/Affair"));
                } else {
                    affairLogLevel = Level.ALL;
                }
                // �f�o�b�K�̕\����
                if (properityXML.hasValueAt("Debugger/Visible")) {
                    if (Boolean.valueOf(
                            String.valueOf(properityXML
                                    .getValueAt("Debugger/Visible")))
                            .booleanValue()) {
                        // �f�o�b�K��\��
                        ACStaticDebugger.getInstance().setVisible(true);
                    } else {
                        // �f�o�b�K���\��
                        ACStaticDebugger.getInstance().setVisible(false);
                        ACStaticDebugger.getInstance().clear();
                    }
                }
                // PDF�o�͒u��
                pdfReplaceHash = null;
                if (properityXML.hasValueAt("PDF/Replace")) {
                    Object obj = properityXML.getData("PDF/Replace");
                    if (obj instanceof Map) {
                        pdfReplaceHash = (Map) obj;
                    }
                }

                // �����N�G��
                if (properityXML.hasValueAt("Auto/DB")) {
                    // �����ς݃`�F�b�N
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
                        // �����ς݂ɂ���
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
        // ��O�̓���
        sb.append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                .format(new Date()));
        sb.append(ACConstants.LINE_SEPARATOR);

        // ��ʂ̃L���v�`��
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

        // �X�v���b�V���͕���
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
            // PDF���쐬�ł��Ȃ��ꍇ
            return "";
        }

        ACSplashable splash = getPrintingSplash();

        File f = data.createDataFile();

        // PDF�̃f�[�^�����O�o��
        if (f != null) {
            logOfPrint("writePDF(" + pdfFilePath + "): " + f.getAbsolutePath());

            // �X�g���[������̒����ǂݏo���ŏo��
            createPDFManager().write(f, pdfFilePath);

            // �ꎞ�t�@�C�����폜
            f.delete();
        }

        if (splash != null) {
            splash.close();
            splash = null;
        }

        return pdfFilePath;
    }

    /**
     * ��O�ɃR�����g��ǉ����܂��B
     * @param sb �R�����g�ǋL��
     * @param ex ��O
     */
    protected void appendException(StringBuffer sb, Throwable ex) {
        sb.append("OS:");
        sb.append(System.getProperty("os.name", "unknown"));
        String patch = System.getProperty("sun.os.patch.level", "");
        if (!"unknown".equalsIgnoreCase(patch)) {
            // �p�b�`��unknown�Ȃ牽���\�����Ȃ�
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
     * �ۑ�����PDF�t�@�C������Ԃ��܂��B
     * 
     * @return PDF�t�@�C����
     * @throws Exception
     */
    protected String createPDFFileName() throws Exception {
        Calendar cal = Calendar.getInstance();
        return VRDateParser.format(cal.getTime(), "yyyyMMddHHmmss") + ".pdf";
    }

    /**
     * PDF�����Ǘ��I�u�W�F�N�g��Ԃ��܂��B
     * @return PDF�����Ǘ��I�u�W�F�N�g
     */
    protected BridgeSimplePDF createPDFManager(){
        if(pdfManager==null){
            pdfManager = new BridgeSimplePDF(); 
        }
        return pdfManager;
    }

    /**
     * PDF�t�@�C���̍쐬��t�@�C���p�X��Ԃ��܂��B
     * <p>
     * �쐬�Ɏ��s�����ꍇ�Anull��Ԃ��܂��B
     * </p>
     * 
     * @param dir �t�H���_
     * @param fileName �t�@�C����
     * @return �쐬��t�@�C���p�X
     * @throws Exception ������O
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
                // �ꎞ�f�B���N�g���̍쐬�Ɏ��s
                processErrorOnPDFDirectoryCreate();
                return null;
            case ACFileUtilitiesCreateResult.ERROR_OF_FILE_CREATE:
                // �t�@�C���쐬���s
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
     * PDF�����Ǘ��I�u�W�F�N�g��j�����܂��B
     */
    protected void disposePDFManager(){
        pdfManager = null;
    }

    /**
     * �����N�G�����������܂��B
     * 
     * @param properityXML �v���p�e�B�t�@�C��
     * @throws Throwable ������O
     */
    protected void executeAutoQuery(ACPropertyXML properityXML)
            throws Throwable {
        Object obj;
        obj = properityXML.getValuesAt("Auto/DB/Query");
        if (obj instanceof Map) {
            Map querys = (Map) obj;
            // DB�}�l�[�W���N���X�𓮓I����
            obj = Class.forName(
                    String.valueOf(properityXML.getValueAt("Auto/DB/DBM")))
                    .newInstance();
            if (obj instanceof ACDBManager) {
                ACDBManager dbm = (ACDBManager) obj;
                dbm.beginTransaction();
                try {
                    // �o�^���ꂽ�N�G�������Ɏ��s
                    Iterator it = querys.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry ent = (Map.Entry) it.next();
                        String key = String.valueOf(ent.getKey());
                        String val = String.valueOf(ent.getValue());
                        if (key.indexOf("Update") >= 0) {
                            // �L�[��Update���܂ޏꍇ�͍X�V�n
                            VRLogger.info("Auto-Update(" + key + "): " + val);
                            VRLogger.info("Auto-Update-Processed: "
                                    + dbm.executeUpdate(val));
                        } else if (key.indexOf("Query") >= 0) {
                            // �L�[��Query���܂ޏꍇ�͌����n
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
     * �t�@�C�����쐬�\�����؂��A�쐬�\�ł���΂��̃t�@�C���p�X��Ԃ��܂��B
     * <p>
     * �쐬�����ۂ��ꂽ�ꍇ�Anull��Ԃ��܂��B
     * </p>
     * 
     * @param f �t�@�C��
     * @return �t�@�C���p�X
     */
    protected String getCreatableFilePath(File f) {
        if (f.exists()) {
            // �t�@�C���͊���
            if (f.canWrite()) {
                // �㏑���\�Ȃ�p�X��Ԃ�
                return f.getAbsolutePath();
            }
        } else {
            // �t�@�C���쐬�����s
            try {
                if (f.createNewFile()) {
                    // �t�@�C���쐬�ɐ���
                    return f.getAbsolutePath();
                }
            } catch (SecurityException ex) {
                // �Z�L�����e�B�ɂ��쐬����
            } catch (IOException ex) {
                // ���o�͗�O
            }
        }
        // �t�@�C���쐬�s�\
        return null;
    }

    /**
     * �f�o�b�K�ݒ�t�@�C���p�X��Ԃ��܂��B
     * 
     * @return �f�o�b�K�ݒ�t�@�C���p�X
     */
    protected String getDebuggerXMLPath() {
        return "debugger.xml";
    }

    /**
     * ��O�������̃G���[���b�Z�[�W��Ԃ��܂��B
     * 
     * @return ��O�������̃G���[���b�Z�[�W
     */
    protected String getExceptionMessage() {
        return "�\�����ʃG���[���������܂����B" + ACConstants.LINE_SEPARATOR
                + "�ڍ׏���Y���ăT�|�[�g�Z���^�[�ɂ��A�����������B";
    }

    /**
     * PDF�r���[���̃p�X��Ԃ��܂��B
     * 
     * @return PDF�r���[���̃p�X
     * @throws Exception ������O
     */
    protected String getPDFViewerPath() throws Exception {
        String acrobatPath = "";
        if (ACFrame.getInstance().hasProperty("Acrobat/Path")) {
            acrobatPath = ACFrame.getInstance().getProperty("Acrobat/Path");
        }
        return acrobatPath;
    }

    /**
     * ������ɕ\������X�v���b�V����Ԃ��܂��B
     * 
     * @return �X�v���b�V��
     * @throws Exception ������O
     */
    protected ACSplashable getPrintingSplash() throws Exception {
        return createSplash("�������");
    }

    /**
     * ���ݒ�t�@�C���p�X��Ԃ��܂��B
     * 
     * @return ���ݒ�t�@�C���p�X
     */
    protected String getPropertyXMLPath() {
        return "property.xml";
    }

    /**
     * ����֘A�̃��O���o�͂��܂��B
     * 
     * @param message ���O
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
     * PDF�t�@�C���̐�����t�H���_�̍쐬�Ɏ��s�����ꍇ�̏����ł��B
     * 
     * @throws Exception ������O
     */
    protected void processErrorOnPDFDirectoryCreate() throws Exception {
        ACMessageBox.showExclamation("����Ɏ��s���܂����B" + ACConstants.LINE_SEPARATOR
                + "PDF�t�@�C���̍쐬��t�H���_�𐶐��ł��܂���B");
    }

    /**
     * PDF�t�@�C���̍쐬�����ۂ��ꂽ�ꍇ�̏����ł��B
     * 
     * @throws Exception ������O
     */
    protected void processErrorOnPDFFileCreate() throws Exception {
        ACMessageBox.showExclamation("����Ɏ��s���܂����B" + ACConstants.LINE_SEPARATOR
                + "PDF�t�@�C�����쐬���錠��������܂���B");
    }
    /**
     * PDF�r���[���𔭌��ł��Ȃ������ꍇ�̏����ł��B
     * 
     * @throws Exception ������O
     */
    protected void processErrorOnPDFViewerNotFound() throws Exception {
        ACMessageBox.showExclamation("PDF�r���[���̐ݒ���擾�ł��܂���B");
    }
    /**
     * �ݒ�t�@�C���ǂݍ��ݎ��s���Ƀf�t�H���g�ݒ��ǉ����܂��B
     * 
     * @param properityMap �ݒ�ǉ���
     * @return �ǂݍ��ݗ�O�̏ꍇ�A��O�𓊂��邩
     */
    protected boolean setDefaultProperityXML(ACPropertyXML properityMap) {
        return true;
    }

}
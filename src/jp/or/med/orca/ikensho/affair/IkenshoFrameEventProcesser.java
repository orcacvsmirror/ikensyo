/** TODO <HEAD_IKENSYO> */
package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACPDFCreatable;
import jp.nichicom.ac.core.AbstractACFrameEventProcesser;
import jp.nichicom.ac.io.ACFileUtilities;
import jp.nichicom.ac.io.ACFileUtilitiesCreateResult;
import jp.nichicom.ac.io.ACPropertyXML;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.ACSplashable;
import jp.nichicom.ac.util.splash.ACSplash;
import jp.nichicom.bridge.pdf.BridgeSimplePDF;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

public class IkenshoFrameEventProcesser extends AbstractACFrameEventProcesser
        implements ACPDFCreatable {
    protected HashMap pdfReplaceHash;
    protected String printPDFDirectory;
    protected String printFormatDirectory;
    protected boolean pdfReplacerReaded;
    protected ACSplash splash;
    protected boolean properityReaded;
    protected ACPropertyXML properityXML;
//  2006/04/24[Tozo Tanaka] : add begin
    private BridgeSimplePDF pdfManager;
//  2006/04/24[Tozo Tanaka] : add end

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
     * PDF�o�͎��̒u��������`�t�@�C���p�X��Ԃ��܂��B
     * 
     * @return �u��������`�t�@�C���p�X
     */
    protected String getPrintableReplaceXMLPath() {
        return "pdf_def.xml";
    }

    public void openPDF(String pdfPath) throws Exception {
        String acrobatPath = "";
        if (ACFrame.getInstance().hasProperity("Acrobat/Path")) {
            acrobatPath = ACFrame.getInstance().getProperity("Acrobat/Path");
        }

        String[] arg = { "", pdfPath };
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Mac")) {
            arg[0] = "open";
        } else {
            arg[0] = acrobatPath;
            if (!new File(acrobatPath).exists()) {
                // �A�N���o�b�g�����݂��Ȃ�
                ACMessageBox.showExclamation("PDF�̐ݒ肪����������܂���B"
                        + ACConstants.LINE_SEPARATOR
                        + "[���C�����j���[]-[�ݒ�(S)]-[PDF�ݒ�(P)]���Acrobat�̐ݒ���s���Ă��������B");
                return;
            }
        }
        Runtime.getRuntime().exec(arg);
    }

    public ACSplashable createSplash(ACAffairInfo newAffair) throws Exception {
        if (splash == null) {
            splash = new ACSplash();
            splash.setIconPathes(getSplashFilePathes());
            splash.refreshSize(getSplashWindowSize());
        }
        if (!splash.isVisible()) {
            splash.showModaless(newAffair.getTitle());
        }
        return splash;
    }

    /**
     * �X�v���b�V����ʃT�C�Y��Ԃ��܂��B
     * 
     * @return �X�v���b�V����ʃT�C�Y
     */
    public Dimension getSplashWindowSize() {
        return new Dimension(400, 120);
    }

    public void showExceptionMessage(Throwable ex) {
        //2006/12/03[Tozo Tanaka] : replace begin 
//        StringWriter sw = new StringWriter();
//        ex.printStackTrace(new PrintWriter(sw));
//        
//        ACMessageBox.show("�d��ȃG���[���������܂����B" + ACConstants.LINE_SEPARATOR
//                + "�T�|�[�g�Z���^�[�ɓd�b���Ă��������B");
//        VRLogger.warning(sw.toString());
        IkenshoCommon.showExceptionMessage(ex);
        //2006/12/03[Tozo Tanaka] : replace end
    }

    public ACPropertyXML getProperityXML() throws Exception {
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
     * ������ɕ\������X�v���b�V����Ԃ��܂��B
     * 
     * @return �X�v���b�V��
     * @throws Exception ������O
     */
    protected ACSplashable getPrintingSplash() throws Exception {
//        return null;
        return createSplash(new ACAffairInfo("", null, "�����"));
    }


//  2006/04/24[Tozo Tanaka] : remove begin
//    public String writePDF(ACChotarouXMLWriter data) throws Exception {
//        String dir = getPrintPDFDirectory();
//        File pdfDirectory = new File(dir);
//        if (!pdfDirectory.exists()) {
//            // �f�B���N�g���쐬
//            if (!pdfDirectory.mkdir()) {
//                ACMessageBox.showExclamation("����Ɏ��s���܂����B");
//                return "";
//            }
//        }
//        //2006/02/12[Tozo Tanaka] : delete begin
////        ACSplashable splash = getPrintingSplash();
//        //2006/02/12[Tozo Tanaka] : delete end
//
//        Calendar cal = Calendar.getInstance();
//        String pdfFilePath = dir
//                + VRDateParser.format(cal.getTime(), "yyyyMMddHHmmss") + ".pdf";
//
//        //2006/02/12[Tozo Tanaka] : add begin
//        ACSplashable splash = getPrintingSplash();
//        //2006/02/12[Tozo Tanaka] : add end
//        
//        new BridgeSimplePDF().write(data.getDataXml(), pdfFilePath);
// 
//        if (splash != null) {
//            splash.close();
//            splash = null;
//        }
//
//        return pdfFilePath;
//    }
//  2006/04/24[Tozo Tanaka] : remove end

//  2006/04/24[Tozo Tanaka] : add begin
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
//  2006/04/24[Tozo Tanaka] : add end
    
    /**
     * �R���X�g���N�^�ł��B
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
        return "�㌩��";
    }

    public String getFrameIconPath() {
        return "jp/or/med/orca/ikensho/images/icon/flameicon.gif";
    }
}

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
 * ���[���YXML�֘A�̔ėp���\�b�h���W�߂��N���X�ł��B
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
     * �C���X�^���X���擾���܂��B
     * 
     * @deprecated ����static���\�b�h���Ă�ł��������B
     * @return �C���X�^���X
     */
    public static ACChotarouXMLUtilities getInstance() {
        if (singleton == null) {
            singleton = new ACChotarouXMLUtilities();
        }
        return singleton;
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected ACChotarouXMLUtilities() {
    }

    /**
     * �`�F�b�N���ڂ�XML�o�͂��܂��B
     * 
     * @param pd ����Ǘ��N���X
     * @param data �f�[�^�\�[�X
     * @param key �f�[�^�L�[
     * @param target �^�O��
     * @param checkValue �`�F�b�N�Ƃ݂Ȃ��l
     * @throws Exception ������O
     * @return �o�͂�����
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
     * �o�C�g�P�ʂŉ��s�𐮌`����������XML�o�͂��܂��B
     * 
     * @param text �ϊ��Ώ�
     * @param columns �܂�Ԃ�������
     * @throws Exception ������O
     * @return ���`����������
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
     * �����P�ʂŉ��s�𐮌`����������XML�o�͂��܂��B
     * 
     * @param text �ϊ��Ώ�
     * @param columns �܂�Ԃ�������
     * @throws Exception ������O
     * @return ���`����������
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
     * �I���^���ڂ�XML�o�͂��܂��B
     * 
     * @param pd ����Ǘ��N���X
     * @param data �f�[�^�\�[�X
     * @param key �f�[�^�L�[
     * @param shapes Visible����ΏیQ
     * @param offset �l�Ɣz��Y�����Ƃ̃I�t�Z�b�g
     * @throws Exception ������O
     * @return �o�͂�����
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
            // ���ׂ�false
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
     * ���[��`�̍��ڂ��\���ɂ��܂��B
     * 
     * @param pd ����Ǘ��N���X
     * @param shape Visible����Ώ�
     * @throws Exception ������O
     */
    public static void setInvisible(ACChotarouXMLWriter pd, String shape)
            throws Exception {
        setVisible(pd, shape, false);
    }

    /**
     * ���[��`�̍��ڂ̕\���ۂ�ݒ肵�܂��B
     * 
     * @param pd ����Ǘ��N���X
     * @param shape Visible����Ώ�
     * @param visible �\����
     * @throws Exception ������O
     */
    public static void setVisible(ACChotarouXMLWriter pd, String shape,
            boolean visible) throws Exception {
        pd.addAttribute(shape, "Visible", visible ? "TRUE" : "FALSE");
    }

    /**
     * �I���^���ڂ�XML�o�͂��܂��B
     * 
     * @param pd ����Ǘ��N���X
     * @param data �f�[�^�\�[�X
     * @param key �f�[�^�L�[
     * @param shapes Visible����ΏیQ
     * @param offset �l�Ɣz��Y�����Ƃ̃I�t�Z�b�g
     * @param optionKey �A�����ďo�͂��镶����L�[
     * @param optionTarget �A�����ďo�͂��镶����o�͈ʒu
     * @param useOptionIndex �A�����ďo�͂��镶����̏o�͏����ƂȂ�I��ԍ�
     * @throws Exception ������O
     * @return �o�͂�����
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
            // ���ׂ�false
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
     * �������ڂ�XML�o�͂��܂��B
     * 
     * @param pd ����Ǘ��N���X
     * @param target �^�O��
     * @param value �o�͒l
     * @throws Exception ������O
     * @return �o�͂�����
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
     * �������ڂ�XML�o�͂��܂��B
     * 
     * @param pd ����Ǘ��N���X
     * @param data �f�[�^�\�[�X
     * @param key �f�[�^�L�[
     * @param target �^�O��
     * @throws Exception ������O
     * @return �o�͂�����
     */
    public static boolean setValue(ACChotarouXMLWriter pd, VRMap data,
            String key, String target) throws Exception {
        Object obj = VRBindPathParser.get(key, data);
        return setValue(pd, target, obj);
    }

    /**
     * �������ڂ�XML�o�͂��܂��B
     * 
     * @param pd ����Ǘ��N���X
     * @param data �f�[�^�\�[�X
     * @param key �f�[�^�L�[
     * @param target �^�O��
     * @param head �������ڒl�̑O�ɘA�����ďo�͂��镶����
     * @throws Exception ������O
     * @return �o�͂�����
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
     * PDF�t�@�C���𐶐����A��������PDF�t�@�C�����J���܂��B
     * 
     * @param pd ����Ǘ��N���X
     * @throws Exception ������O
     */
    public static void openPDF(ACChotarouXMLWriter pd) throws Exception {
        if (ACFrame.getInstance().getFrameEventProcesser() instanceof ACPDFCreatable) {
            ACPDFCreatable processer = (ACPDFCreatable) ACFrame.getInstance()
                    .getFrameEventProcesser();
            // PDF�t�@�C������
            String pdfPath = processer.writePDF(pd);

            // ��������PDF���J��
            if ((pdfPath != null) && (!"".equals(pdfPath))) {
                processer.openPDF(pdfPath);
            }
        }
    }
    
    /**
     * PDF�t�@�C���𐶐����܂��B
     * 
     * @param pd ����Ǘ��N���X
     * @return �����t�@�C���p�X
     * @throws Exception ������O
     */
    public static String writePDF(ACChotarouXMLWriter pd) throws Exception {
        //�X���b�h����

        if(pd.isPageEmpty()||(pd.getDataFile()==null)){
            //�t�@�C���������Ȃ�null��ԋp
            return null;
        }
        
        //�t�@�C���V�X�e�����t�@�C���T�C�Y�𐳂����Ԃ��܂łɒx�������邽�߁A���s����B
        boolean zeroSize = true;
        for(int i=0; i<30; i++){
            //30�b�܂ł͎��s����
            if(pd.getDataFile().length()>0){
                //�t�@�C���T�C�Y���v�コ�ꂽ�甲����B
                zeroSize = false;
                break;
            }
            //�t�@�C���T�C�Y0�Ȃ�1�b�ҋ@����B
            Thread.sleep(1000);
        }
        if (zeroSize) {
            // �ēx�T�C�Y0�Ȃ�null��ԋp
            VRLogger.info("PDF�t�@�C���������s���Ă��܂���B");
            return null;
        }

        if (ACFrame.getInstance().getFrameEventProcesser() instanceof ACPDFCreatable) {
            ACPDFCreatable processer = (ACPDFCreatable) ACFrame.getInstance()
                    .getFrameEventProcesser();
            // PDF�t�@�C������
            return processer.writePDF(pd);
        }
        return null;
    }
    
//    /**
//     * ����PDF���ꎞ�o�͂��܂��B
//     * @param pd ����Ǘ��N���X
//     * @throws Exception ������O
//     */
//    public static void pushConcatPDF(ACChotarouXMLWriter pd) throws Exception {
//        pd.endPrintEdit();
//        
//        String pdfPath = writePDF(pd);
//        if (ACFrame.getInstance().getFrameEventProcesser() != null) {
//            ACFrame.getInstance().getFrameEventProcesser().createSplash("������ʉ��H����");
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
//     * ����PDF���������ĊJ�����܂��B
//     * 
//     * @param pd ����Ǘ��N���X
//     * @throws Exception ������O
//     */
//    public static void openConcatPDF(ACChotarouXMLWriter pd) throws Exception {
//        if (pd.getPushedPDF().isEmpty()) {
//            //�����s�v�Ȃ璼�ڊJ��
//            openPDF(pd);
//            return;
//        } else {
//            if (ACFrame.getInstance().getFrameEventProcesser() instanceof ACDefaultFrameEventProcesser) {
//                ACDefaultFrameEventProcesser processer = (ACDefaultFrameEventProcesser) ACFrame
//                        .getInstance().getFrameEventProcesser();
//                //�Ō�̕����Ώۂ��o��
//                pushConcatPDF(pd);
//                pd.clear();
//                processer.openConcatPDF(pd);
//                pd.getPushedPDF().clear();
//            }
//        }
//    }
    
    /**
     * �g�p���钠�[��`�̂�o�^���܂��B
     * 
     * @param pd ����Ǘ��N���X
     * @param formatID ���[��`��ID
     * @param formatFileName ���[��`�̂̃t�@�C����
     * @throws Exception ������O
     */
    public static void addFormat(ACChotarouXMLWriter pd, String formatID,
            String formatFileName) throws Exception {
        ACFrameEventProcesser processer = ACFrame.getInstance()
                .getFrameEventProcesser();
        if (processer instanceof ACPDFCreatable) {
            ACPDFCreatable pdfCreatable = (ACPDFCreatable) processer;
            String path = pdfCreatable.getPrintFormatDirectory()
                    + formatFileName;
            // �t�H�[�}�b�g��ǉ�
            pd.addFormat(formatID, path);

        } else {
            throw new IllegalArgumentException(
                    "�w���FrameEventProcesser�́APDF�����\�ȃN���X(ACPDFCreatable)�ł͂���܂���B");
        }
    }

    /**
     * ���[��`�̍��ڂ̔w�i���w��F�œh��Ԃ��܂��B
     * 
     * @param pd ����Ǘ��N���X
     * @param target ���F�Ώ�
     * @param color �F
     * @throws Exception ������O
     */
    public static void setFillColor(ACChotarouXMLWriter pd, String target,
            String color) throws Exception {
        pd.addAttribute(target, "BackStyle", "Solid");
        pd.addAttribute(target, "BackColor", color);
    }

    /**
     * ���[��`�̍��ڂ̔w�i���w��F�œh��Ԃ��܂��B
     * 
     * @param pd ����Ǘ��N���X
     * @param target ���F�Ώ�
     * @param color �F
     * @throws Exception ������O
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

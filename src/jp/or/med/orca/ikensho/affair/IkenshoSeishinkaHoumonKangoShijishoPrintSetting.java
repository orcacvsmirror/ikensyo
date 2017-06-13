package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JPanel;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
public class IkenshoSeishinkaHoumonKangoShijishoPrintSetting extends IkenshoDialog {
    
    private VRPanel contents;
    private ACGroupBox styleGroup;
    private ACClearableRadioButtonGroup style;
    private VRPanel buttons;
    private ACButton ok;
    private ACButton cancel;
    protected VRMap source;
    private boolean printed = false;
    private boolean isPageBreak = false;
    
    /**
     * ��Ë@�֑�
     */
    public static final int DOCUMENT_TYPE_ORGAN = 1;
    
    /**
     * �V�l�ی��{��
     */
    public static final int DOCUMENT_TYPE_HOKEN_SHISETSU = 2;
    
    /**
     * �R���X�g���N�^
     * @param data ����f�[�^
     */
    public IkenshoSeishinkaHoumonKangoShijishoPrintSetting(VRMap data) throws HeadlessException {
        super(ACFrame.getInstance(), "�u���_�ȖK��Ō�w�����v����ݒ�", true);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            init();
            this.source = (VRMap) contents.createSource();
            this.source.putAll(data);
            contents.setSource(this.source);
            contents.bindSource();
            // ��������A���ꂩ��󎚂���f�[�^�����y�[�W���K�v���𔻒肷��
            isPageBreak = ACCastUtilities.toBoolean(data.get("IS_PAGE_BREAK"), false);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                print();
                printed = true;
                dispose();
            }
        });
    }
    
    /**
     * ��������
     */
    private void jbInit() throws Exception {
        
        ((JPanel) this.getContentPane()).add(getContents());
        
        getContents().setLayout(new VRLayout());
        getContents().add(getStyleGroup(), VRLayout.NORTH);
        getContents().add(getButtons(), VRLayout.NORTH);
        // ���[�l��
        getStyleGroup().setText("���[�l���i�V�l�ی��{�݈ȊO�̎{�݂́u��Ë@�֑��v��I�����Ă��������j");
        getStyleGroup().setLayout(new BorderLayout());
        getStyleGroup().add(getStyle(), BorderLayout.CENTER);
        VRLayout styleLayout = new VRLayout();
        styleLayout.setAutoWrap(false);
        getStyle().setLayout(styleLayout);
        getStyle().setUseClearButton(false);
        getStyle().setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "��Ë@�֑�", "�V�l�ی��{��" }))));
        getStyle().setSelectedIndex(1);
        // �����{�^��
        getButtons().setLayout(new VRLayout());
        getButtons().add(getCancel(), VRLayout.EAST);
        getButtons().add(getOk(), VRLayout.EAST);
        getOk().setText("���(O)");
        getOk().setMnemonic('O');
        getCancel().setText("�L�����Z��(C)");
        getCancel().setMnemonic('C');
    }
    
    /**
     * �T�C�Y�A�ʒu�̏����ݒ菈��
     */
    private void init() {

        ACFrame frame = ACFrame.getInstance();
        if (frame.isSmall()){
            setSize(new Dimension(600,110));
        }
        else if (frame.isMiddle()){
            setSize(new Dimension(650,130));
        }
        else if (frame.isLarge()){
            setSize(new Dimension(850,155));
        }
        else {
            setSize(new Dimension(1150,164));
        }
        // �E�B���h�E�𒆉��ɔz�u
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }
    
    /**
     * ���[�_���\�����A������ꂽ����Ԃ��܂�
     * @return ������ꂽ��
     */
    public boolean showModal() {
        setVisible(true);
        return printed;
    }
    
    /**
     * �������
     */
    protected void print() {
        try {
            contents.applySource();
            ACChotarouXMLWriter pd = new ACChotarouXMLWriter();
            
            // ����J�n
            pd.beginPrintEdit();
            
            int printStyle = style.getSelectedIndex();
            
            switch (printStyle) {
            // ��Ë@��
            case DOCUMENT_TYPE_ORGAN:
                //���y�[�W�t���O���Q�Ƃ��A��`�̂�ݒ肷��
                if (isPageBreak) {
                    ACChotarouXMLUtilities.addFormat(pd, "page1", "Seishinsho_M1.xml");
                    ACChotarouXMLUtilities.addFormat(pd, "page2", "Seishinsho_M2.xml");
                } else {
                    ACChotarouXMLUtilities.addFormat(pd, "page1", "Seishinsho.xml");
                }
                break;
            // �V�l�ی��{��
            case DOCUMENT_TYPE_HOKEN_SHISETSU:
                if (isPageBreak) {
                    ACChotarouXMLUtilities.addFormat(pd, "page1", "SeishinshoB_M1.xml");
                    ACChotarouXMLUtilities.addFormat(pd, "page2", "SeishinshoB_M2.xml");
                } else {
                    ACChotarouXMLUtilities.addFormat(pd, "page1", "SeishinshoB.xml");
                }
                break;
            default:
                throw new RuntimeException("�z�肵�Ȃ�������[�h���w�肳��܂����B");
            }
            // �������
            printShijisho(pd, source, printStyle, isPageBreak);
            // ����I��
            pd.endPrintEdit();
            // PDF�I�[�v��
            IkenshoCommon.openPDF(pd);
            
        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
    }
    
    /**
     * ���_�ȖK��Ō�w�����������
     * @param pd XML�����N���X
     * @param data �f�[�^�\�[�X
     * @param printStyle ����^�C�v(1: ��Ë@�ցA 2:�V�l�ی��{��)
     * @param isPageBreak ���ŗL��
     */
    public static void printShijisho(ACChotarouXMLWriter pd, VRMap data, int printStyle, boolean isPageBreak) throws Exception {
        
        pd.beginPageEdit("page1");
        
        // ����
        IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid2.h1.w2");
        
        // ���N����
        Date date = (Date) VRBindPathParser.get("BIRTHDAY", data);
        String era = VRDateParser.format(date, "gg");
        if ("��".equals(era)) {
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } 
        else if ("��".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } 
        else if ("��".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } 
        else if ("��".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
        } 
        else {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        }
        // �N
        IkenshoCommon.addString(pd, "Grid2.h1.w5", VRDateParser.format(date, "ee"));
        // ��
        IkenshoCommon.addString(pd, "Grid2.h1.w7", VRDateParser.format(date, "MM"));
        // ��
        IkenshoCommon.addString(pd, "Grid2.h1.w9", VRDateParser.format(date, "dd"));
        
        // �N��
        IkenshoCommon.addString(pd, data, "AGE", "Grid2.h1.w11");
        
        // �X�֔ԍ�
        IkenshoCommon.addString(pd, data, "POST_CD", "Grid3.h1.w4");
        // �Z��
        IkenshoCommon.addString(pd, data, "ADDRESS", "Grid3.h2.w4");
        // �d�b�ԍ�
        IkenshoCommon.addTel(pd, data, "TEL1", "TEL2", "Grid3.h3.w2");
        
        // �w������ �J�n
        String text = "";
        text = String.valueOf(VRBindPathParser.get("SIJI_KIKAN_FROM", data));
        if (text.indexOf("0000�N") < 0) {
            IkenshoCommon.addString(pd, "Grid1.h1.w1", "�i"
                    + text.substring(0, 2));
            IkenshoCommon.addString(pd, "Grid1.h1.w2", text.substring(2, 4));
            if (text.indexOf("00��") < 0) {
                IkenshoCommon
                        .addString(pd, "Grid1.h1.w4", text.substring(5, 7));
                if (text.indexOf("00��") < 0) {
                    IkenshoCommon.addString(pd, "Grid1.h1.w6", text.substring(
                            8, 10));
                }
            }
        } else {
            IkenshoCommon.addString(pd, "Grid1.h1.w1", "�i");
        }
        // �w������ �I��
        text = String.valueOf(VRBindPathParser.get("SIJI_KIKAN_TO", data));
        if (text.indexOf("0000�N") < 0) {
            IkenshoCommon.addString(pd, "Grid1.h1.w15", text.substring(0, 2));
            IkenshoCommon.addString(pd, "Grid1.h1.w8", text.substring(2, 4));
            if (text.indexOf("00��") < 0) {
                IkenshoCommon.addString(pd, "Grid1.h1.w10", text
                        .substring(5, 7));
                if (text.indexOf("00��") < 0) {
                    IkenshoCommon.addString(pd, "Grid1.h1.w12", text.substring(
                            8, 10));
                }
            }
        }
        
        // �{�ݖ�
        IkenshoCommon.addString(pd, data, "SISETU_NM", "Grid15.h1.w2");
        
        // �傽�鏝�a��
        StringBuffer sb = new StringBuffer();
        VRArrayList array = new VRArrayList() ;
        
        Object shindanName1 = VRBindPathParser.get("SINDAN_NM1", data);
        if (!IkenshoCommon.isNullText(shindanName1)){
            array.add(String.valueOf(shindanName1));
        }
        Object shindanName2 = VRBindPathParser.get("SINDAN_NM2", data);
        if (!IkenshoCommon.isNullText(shindanName2)){
            array.add(String.valueOf(shindanName2));
        }
        Object shindanName3 = VRBindPathParser.get("SINDAN_NM3", data);
        if (!IkenshoCommon.isNullText(shindanName3)){
            array.add(String.valueOf(shindanName3));
        }
        
        // array�̒��Ƀf�[�^�������Ă��邩�ǂ����m�F
        if (!array.isEmpty()){
            // �傽�鏝�a�������P�����L�ڂ���Ă��Ȃ��ꍇ�A�ԍ��͕t���Ȃ��B
            if (array.size() == 1){
                sb.append(array.get(0));
            }
            // �傽�鏝�a�����P�ȏ�L�ڂ���Ă���ꍇ�A"�i�P�j"����ԍ���t����B
            else{
                for (int i=0; i<array.size(); i++){
                    switch (i){
                    case 0 :
                        sb.append("(�P)");
                        sb.append(array.get(i));
                        break;
                    case 1 :
                        sb.append(" ");
                        sb.append("(�Q)");
                        sb.append(array.get(i));
                        break;
                    case 2 :
                        sb.append(" ");
                        sb.append("(�R)");
                        sb.append(array.get(i));
                        break;
                    }
                    
                }
            }
        }
        IkenshoCommon.addString(pd, "Grid4.h1.w2", sb.toString());
        
        // �Ǐ�E���Ï�ԁi���ł���ƂȂ��ŕʏ����j
        if (!isPageBreak) {
            String[] lines = ACTextUtilities.separateLineWrapOnByte(ACCastUtilities.toString(data.get("MT_STS")), 100);
            int linesCount = Math.min(5, lines.length);
            int totalByteCount = 0;
            StringBuffer sbSickProgress = new StringBuffer();
            for (int i = 0; i < linesCount; i++) {
                if (totalByteCount > 0) {
                    sbSickProgress.append(IkenshoConstants.LINE_SEPARATOR);
                }
                int lineByteLen = lines[i].getBytes().length;
                if (totalByteCount + lineByteLen > 500) {
                    sbSickProgress.append(lines[i].substring(0, Math.max(0,501 - (totalByteCount + lineByteLen))));
                    break;
                }
                sbSickProgress.append(lines[i]);
                totalByteCount += lineByteLen;
            }
            ACChotarouXMLUtilities.setValue(pd, "Grid6.h1.w2", sbSickProgress.toString());
        }
        else {
            IkenshoCommon.addString(pd, "lblJyotai", getLineBreakedString(data.get("MT_STS")));
        }
        
        // ���^���̖�܂̗p�@�E�p�ʁi���ł���ƂȂ��ŕʏ����j
        if (!isPageBreak) {
            if (!(IkenshoCommon.isNullText(VRBindPathParser.get("MEDICINE7", data)) && 
                    IkenshoCommon.isNullText(VRBindPathParser.get("MEDICINE8", data)) &&
                    IkenshoCommon.isNullText(VRBindPathParser.get("DOSAGE7", data)) &&
                    IkenshoCommon.isNullText(VRBindPathParser.get("DOSAGE8", data)) &&
                    IkenshoCommon.isNullText(VRBindPathParser.get("UNIT7", data)) &&
                    IkenshoCommon.isNullText(VRBindPathParser.get("UNIT8", data)) &&
                    IkenshoCommon.isNullText(VRBindPathParser.get("USAGE7", data)) &&
                    IkenshoCommon.isNullText(VRBindPathParser.get("USAGE8", data)))) {
                //���7�����8�����͂���Ă���ꍇ
                addMedicine(pd, data, "1", "sickMedicines8.h1.w4");
                addMedicine(pd, data, "2", "sickMedicines8.h1.w2");
                addMedicine(pd, data, "3", "sickMedicines8.h2.w4");
                addMedicine(pd, data, "4", "sickMedicines8.h2.w2");
                addMedicine(pd, data, "5", "sickMedicines8.h3.w4");
                addMedicine(pd, data, "6", "sickMedicines8.h3.w2");
                addMedicine(pd, data, "7", "sickMedicines8.h4.w4");
                addMedicine(pd, data, "8", "sickMedicines8.h4.w2");
                pd.addAttribute("Grid7", "Visible", "FALSE");
            }else{
                addMedicine(pd, data, "1", "Grid7.h1.w4");
                addMedicine(pd, data, "2", "Grid7.h1.w2");
                addMedicine(pd, data, "3", "Grid7.h2.w4");
                addMedicine(pd, data, "4", "Grid7.h2.w2");
                addMedicine(pd, data, "5", "Grid7.h3.w4");
                addMedicine(pd, data, "6", "Grid7.h3.w2");
                pd.addAttribute("sickMedicines8", "Visible", "FALSE");
            }
        }
        else {
            addMedicine(pd, data, "1", "sickMedicines8.h1.w4");
            addMedicine(pd, data, "2", "sickMedicines8.h1.w2");
            addMedicine(pd, data, "3", "sickMedicines8.h2.w4");
            addMedicine(pd, data, "4", "sickMedicines8.h2.w2");
            addMedicine(pd, data, "5", "sickMedicines8.h3.w4");
            addMedicine(pd, data, "6", "sickMedicines8.h3.w2");
            addMedicine(pd, data, "7", "sickMedicines8.h4.w4");
            addMedicine(pd, data, "8", "sickMedicines8.h4.w2");
        }
        
        // �a�����m
        IkenshoCommon.addSelection(pd, data, "BYOUMEI_KOKUTI", new String[] { "Label14", "Label15" }, -1);
        // ���Â̎󂯓���
        IkenshoCommon.addString(pd, data, "TIRYOU_UKEIRE", "Grid8.h2.w2");
        // �������K��̕K�v��
        IkenshoCommon.addSelection(pd, data, "FUKUSU_HOUMON", new String[] { "Label16", "Label17" }, -1);
        // �Z���ԖK��̕K�v��
        IkenshoCommon.addSelection(pd, data, "TANJIKAN_HOUMON", new String[] { "Label18", "Label19" }, -1);
        // ���퐶�������x�|�F�m�ǂ̏�
        IkenshoCommon.addSelection(pd, data, "CHH_STS", new String[] {
                "Shape28", "Shape17", "Shape18", "Shape13", "Shape11",
                "Shape12", "Shape16", "Shape14" }, -1);
        
        int intValue;
        // �������Y���̊m���L��
        intValue = ((Integer) VRBindPathParser.get("SEIKATU_RIZUMU_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("Label20", "Visible", "FALSE");
        }
        if (intValue == 1) {
            // �������Y���̊m���i���ł���ƂȂ��ŕʏ����j
            if (!isPageBreak) {
                IkenshoCommon.addString(pd, "Grid10.h3.w3", getLineBreakedString(data.get("SEIKATU_RIZUMU")));
            }
            else {
                IkenshoCommon.addString(pd, "lblSeikatu", getLineBreakedString(data.get("SEIKATU_RIZUMU")));
            }
        }
        
        // ���ł���̏ꍇ�͂����ŉ���
        if (isPageBreak) {
            pd.endPageEdit();
            pd.beginPageEdit("page2");
            // ���Ҏ����ƔN����Čf
            IkenshoCommon.addString(pd, data, "PATIENT_NM", "patientData.h1.w1");
            IkenshoCommon.addString(pd, data, "AGE", "patientData.h1.w3");
        }
        
        // �Ǝ��\�́A�Љ�Z�\�̊l���L��
        intValue = ((Integer) VRBindPathParser.get("KAJI_NOURYOKU_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("Label21", "Visible", "FALSE");
        }
        if (intValue == 1) {
            // �Ǝ��\�́A�Љ�Z�\�̊l���i���ł���ƂȂ��ŕʏ����j
            if (!isPageBreak) {
                IkenshoCommon.addString(pd, "Grid10.h5.w3", getLineBreakedString(data.get("KAJI_NOURYOKU")));
            }
            else {
                IkenshoCommon.addString(pd, "lblKaji", getLineBreakedString(data.get("KAJI_NOURYOKU")));
            }
        }
        
        // �ΐl�֌W�̉��P�i�Ƒ��܂ށj�L��
        intValue = ((Integer) VRBindPathParser.get("TAIJIN_KANKEI_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("Label22", "Visible", "FALSE");
        }
        if (intValue == 1) {
            // �ΐl�֌W�̉��P�i�Ƒ��܂ށj�i���ł���ƂȂ��ŕʏ����j
            if (!isPageBreak) {
                IkenshoCommon.addString(pd, "Grid10.h7.w3", getLineBreakedString(data.get("TAIJIN_KANKEI")));
            }
            else {
                IkenshoCommon.addString(pd, "lblTaijin", getLineBreakedString(data.get("TAIJIN_KANKEI")));
            }
        }
        
        // �Љ�����p�̎x���L��
        intValue = ((Integer) VRBindPathParser.get("SYAKAI_SHIGEN_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("Label23", "Visible", "FALSE");
        }
        if (intValue == 1) {
            // �Љ�����p�̎x���i���ł���ƂȂ��ŕʏ����j
            if (!isPageBreak) {
                IkenshoCommon.addString(pd, "Grid10.h9.w3", getLineBreakedString(data.get("SYAKAI_SHIGEN")));
            }
            else {
                IkenshoCommon.addString(pd, "lblSyakai", getLineBreakedString(data.get("SYAKAI_SHIGEN")));
            }
        }
        
        // �򕨗Ö@�ւ̉����L��
        intValue = ((Integer) VRBindPathParser.get("YAKUBUTU_RYOUHOU_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("Label24", "Visible", "FALSE");
        }
        if (intValue == 1) {
            // �򕨗Ö@�ւ̉����i���ł���ƂȂ��ŕʏ����j
            if (!isPageBreak) {
                IkenshoCommon.addString(pd, "Grid10.h11.w3", getLineBreakedString(data.get("YAKUBUTU_RYOUHOU")));
            }
            else {
                IkenshoCommon.addString(pd, "lblYakubutu", getLineBreakedString(data.get("YAKUBUTU_RYOUHOU")));
            }
        }
        
        // �g�̍����ǂ̔��ǁE�����̖h�~�L��
        intValue = ((Integer) VRBindPathParser.get("SHINTAI_GAPPEISYO_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("Label25", "Visible", "FALSE");
        }
        if (intValue == 1) {
            // �g�̍����ǂ̔��ǁE�����̖h�~�i���ł���ƂȂ��ŕʏ����j
            if (!isPageBreak) {
                IkenshoCommon.addString(pd, "Grid10.h13.w3", getLineBreakedString(data.get("SHINTAI_GAPPEISYO")));
            }
            else {
                IkenshoCommon.addString(pd, "lblShintai", getLineBreakedString(data.get("SHINTAI_GAPPEISYO")));
            }
        }
        
        // ���̑��L��
        intValue = ((Integer) VRBindPathParser.get("SEISHIN_OTHER_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("Label26", "Visible", "FALSE");
        }
        if (intValue == 1) {
            // ���̑��i���ł���ƂȂ��ŕʏ����j
            if (!isPageBreak) {
                IkenshoCommon.addString(pd, "Grid10.h15.w3", getLineBreakedString(data.get("SEISHIN_OTHER")));
            }
            else {
                IkenshoCommon.addString(pd, "lblEtc", getLineBreakedString(data.get("SEISHIN_OTHER")));
            }
        }
        
        // �ً}���̘A����
        IkenshoCommon.addString(pd, data, "KINKYU_RENRAKU", "Grid11.h1.w2");
        // �s�ݎ��̑Ή��@
        IkenshoCommon.addString(pd, data, "FUZAIJI_TAIOU", "Grid11.h2.w2");
        // �厡��Ƃ̏������̎�i
        IkenshoCommon.addString(pd, data, "JYOUHOU_SYUDAN", "Grid9.h1.w2");
        // ���L���ׂ����ӎ����i���ł���ƂȂ��ŕʏ����j
        if (!isPageBreak) {
            IkenshoCommon.addString(pd, "Grid12.h2.w1", getLineBreakedString(data.get("SIJI_TOKKI")));
        }
        else {
            IkenshoCommon.addString(pd, "lblTokki", getLineBreakedString(data.get("SIJI_TOKKI")));
        }
        
        // �L����
        if (VRBindPathParser.has("KINYU_DT", data)) {
            Object obj = VRBindPathParser.get("KINYU_DT", data);
            if (obj instanceof Date) {
                IkenshoCommon.addString(pd, "Label2", VRDateParser.format((Date) obj, "gggee�NMM��dd��"));
            }
        }
        
        // ��Ë@�֖�
        IkenshoCommon.addString(pd, data, "MI_NM", "Grid13.h1.w2");
        // ��Ë@�֏Z��
        IkenshoCommon.addString(pd, data, "MI_ADDRESS", "Grid13.h2.w2");
        // ��Ë@�֓d�b�ԍ�
        IkenshoCommon.addTel(pd, data, "MI_TEL1", "MI_TEL2", "Grid13.h3.w2");
        // ��Ë@��FAX�ԍ�
        IkenshoCommon.addTel(pd, data, "MI_FAX1", "MI_FAX2", "Grid13.h4.w2");
        // ��t����
        IkenshoCommon.addString(pd, data, "DR_NM", "Grid13.h5.w2");
        
        // �K��Ō�X�e�[�V����
        IkenshoCommon.addString(pd, "Label6", String.valueOf(VRBindPathParser.get("STATION_NM", data)) + " �a");
        
        pd.endPageEdit();
    }
    
    /**
     * ��܏��擾����
     * @param pd XML�����N���X
     * @param data �f�[�^�\�[�X
     * @param index �C���f�b�N�X
     * @param target �o�͐�
     */
    protected static void addMedicine(ACChotarouXMLWriter pd, VRMap data, String index, String target) throws Exception {
        StringBuffer sb = new StringBuffer();
        Object obj;
        obj = VRBindPathParser.get("MEDICINE" + index, data);
        if (obj != null) {
            sb.append(String.valueOf(obj));
        }
        sb.append("�@");
        obj = VRBindPathParser.get("DOSAGE" + index, data);
        if (obj != null) {
            sb.append(String.valueOf(obj));
        }
        obj = VRBindPathParser.get("UNIT" + index, data);
        if (obj != null) {
            sb.append(String.valueOf(obj));
        }
        obj = VRBindPathParser.get("USAGE" + index, data);
        if (obj != null) {
            // �p�@�E�p�ʂ̑O�ɋ󔒂�}�����A�ӌ����̕\���ƍ��킹��
            sb.append("�@");
            sb.append(String.valueOf(obj));
        }
        IkenshoCommon.addString(pd, target, sb.toString());
    }
    
    /**
     * ���s�ǉ�����
     * @param obj �󎚃f�[�^
     */
    private static String getLineBreakedString(Object obj) throws Exception {
        
        if (obj == null) {
            return "";
        }
        String value = ACCastUtilities.toString(obj);
        StringBuffer result = new StringBuffer();
        String[] lines = ACTextUtilities.separateLineWrapOnByte(value, 100);
        
        for (int i = 0; i < lines.length; i++) {
            if (i != 0) {
                result.append(IkenshoConstants.LINE_SEPARATOR);
            }
            result.append(lines[i]);
        }
        return result.toString();
    }
    
    /**
     * �E�B���h�E�N���[�Y������
     */
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            closeWindow();
        }
    }
    
    /**
     * �I������
     */
    protected void closeWindow() {
        // ���g��j������
        this.dispose();
    }
    
    /**
     * contents ��Ԃ��܂��B
     * @return contents
     */
    protected VRPanel getContents() {
        if(contents==null){
            contents = new VRPanel();
        }
        return contents;
    }
    
    /**
     * styleGroup ��Ԃ��܂��B
     * @return styleGroup
     */
    protected ACGroupBox getStyleGroup() {
        if(styleGroup==null){
            styleGroup = new ACGroupBox();
        }
        return styleGroup;
    }
    
    /**
     * buttons ��Ԃ��܂��B
     * @return buttons
     */
    protected VRPanel getButtons() {
        if(buttons==null){
            buttons = new VRPanel();
        }
        return buttons;
    }
    
    /**
     * style ��Ԃ��܂��B
     * @return style
     */
    protected ACClearableRadioButtonGroup getStyle() {
        if(style==null){
            style = new ACClearableRadioButtonGroup();
        }
        return style;
    }
    
    /**
     * cancel ��Ԃ��܂��B
     * @return cancel
     */
    protected ACButton getCancel() {
        if(cancel==null){
            cancel = new ACButton();
        }
        return cancel;
    }
    
    /**
     * ok ��Ԃ��܂��B
     * @return ok
     */
    protected ACButton getOk() {
        if(ok==null){
            ok = new ACButton();
        }
        return ok;
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
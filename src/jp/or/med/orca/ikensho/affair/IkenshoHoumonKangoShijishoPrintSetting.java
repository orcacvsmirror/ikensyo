package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JPanel;

import sun.security.action.GetBooleanAction;

import jp.nichicom.ac.ACConstants;
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
import jp.or.med.orca.ikensho.component.IkenshoVirticalRadioButtonGroup;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

/** <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoPrintSetting extends IkenshoDialog {
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//    private JPanel contentPane = new JPanel();
//    private VRPanel contents = new VRPanel();
//    private ACGroupBox styleGroup = new ACGroupBox();
//    private ACClearableRadioButtonGroup style = new ACClearableRadioButtonGroup();
//    private ACGroupBox sendToGroup = new ACGroupBox();
//    private IkenshoVirticalRadioButtonGroup sendTo = new IkenshoVirticalRadioButtonGroup();
//    private VRPanel buttons = new VRPanel();
//    private ACButton ok = new ACButton();
//    private ACButton cancel = new ACButton();
    private VRPanel contents;
    private ACGroupBox styleGroup;
    private ACClearableRadioButtonGroup style;
    private ACGroupBox sendToGroup;
    private IkenshoVirticalRadioButtonGroup sendTo;
    private VRPanel buttons;
    private ACButton ok;
    private ACButton cancel;
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

    protected boolean printed = false;
    protected VRMap source;
    
    //[ID:0000635][Shin Fujihara] 2011/02/25 add begin �y2010�N�x�v�]�Ή��z
    //���y�[�W���������Ă��邩�̔���t���O
    private boolean isPageBreak = false;
    //[ID:0000635][Shin Fujihara] 2011/02/25 add end �y2010�N�x�v�]�Ή��z

    /**
     * ��Ë@�֑�
     */
    public static final int DOCUMENT_TYPE_ORGAN = 1;
    /**
     * �V�l�ی��{��
     */
    public static final int DOCUMENT_TYPE_HOKEN_SHISETSU = 2;

    /**
     * ���[�_���\�����A������ꂽ����Ԃ��܂��B
     * 
     * @return ������ꂽ��
     */
    public boolean showModal() {

        try {
            boolean useOtherStation = ((Integer) VRBindPathParser.get(
                    "OTHER_STATION_SIJI", source)).intValue() == 2;

            if (useOtherStation) {
                String station1 = String.valueOf(VRBindPathParser.get(
                        "STATION_NM", source));
                String station2 = String.valueOf(VRBindPathParser.get(
                        "OTHER_STATION_NM", source));
                if (("".equals(station1)) || ("".equals(station2))) {
                    useOtherStation = false;
                } else {
                    sendTo.setModel(new VRListModelAdapter(new VRArrayList(
                            Arrays.asList(new String[] { "�o��������Ƃ��A�Q�y�[�W���",
                                    station1, station2 }))));
                    sendTo.setSelectedIndex(1);
                }
            }

            if (!useOtherStation) {
                sendToGroup.setEnabled(false);
                sendTo.setEnabled(false);
                sendTo.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                        .asList(new String[] { "", "", "" }))));
            }

        } catch (ParseException ex) {
            IkenshoCommon.showExceptionMessage(ex);
            return false;
        }

        setVisible(true);
        // show();
        return printed;
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param data ����f�[�^
     * @throws HeadlessException ��������O
     */
    public IkenshoHoumonKangoShijishoPrintSetting(VRMap data)
            throws HeadlessException {
        super(ACFrame.getInstance(), "�u�K��Ō�w�����v����ݒ�", true);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            init();

            this.source = (VRMap) contents.createSource();
            this.source.putAll(data);
            contents.setSource(this.source);
            contents.bindSource();
            //[ID:0000635][Shin Fujihara] 2011/02/25 add begin �y2010�N�x�v�]�Ή��z
            //��������A���ꂩ��󎚂���f�[�^�����y�[�W���K�v���𔻒肷��
            isPageBreak = ACCastUtilities.toBoolean(data.get("IS_PAGE_BREAK"), false);
            //[ID:0000635][Shin Fujihara] 2011/02/25 add end �y2010�N�x�v�]�Ή��z
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
     * ����������s���܂��B
     */
    protected void print() {
        try {
            contents.applySource();
            ACChotarouXMLWriter pd = new ACChotarouXMLWriter();

            pd.beginPrintEdit();
            int printStyle = style.getSelectedIndex();
            
            //[ID:0000635][Shin Fujihara] 2011/02/25 edit begin �y2010�N�x�v�]�Ή��z
            switch (printStyle) {
            case DOCUMENT_TYPE_ORGAN: // ��Ë@��
            	//���y�[�W�t���O���Q�Ƃ��A��`�̂�ݒ肷��
            	if (isPageBreak) {
            		ACChotarouXMLUtilities.addFormat(pd, "page1", "Shijisho_M1.xml");
            		ACChotarouXMLUtilities.addFormat(pd, "page2", "Shijisho_M2.xml");
            	} else {
            		ACChotarouXMLUtilities.addFormat(pd, "page1", "Shijisho.xml");
            	}
                break;
            case DOCUMENT_TYPE_HOKEN_SHISETSU: // �V�l�ی��{��
            	if (isPageBreak) {
            		ACChotarouXMLUtilities.addFormat(pd, "page1", "ShijishoB_M1.xml");
            		ACChotarouXMLUtilities.addFormat(pd, "page2", "ShijishoB_M2.xml");
            	} else {
            		ACChotarouXMLUtilities.addFormat(pd, "page1", "ShijishoB.xml");
            	}
                break;
            default:
                throw new RuntimeException("�z�肵�Ȃ�������[�h���w�肳��܂����B");
            }
            //[ID:0000635][Shin Fujihara] 2011/02/25 edit end �y2010�N�x�v�]�Ή��z


            String station1 = String.valueOf(VRBindPathParser.get("STATION_NM", source));
            String station2 = String.valueOf(VRBindPathParser.get("OTHER_STATION_NM", source));
            
            //[ID:0000635][Shin Fujihara] 2011/02/25 edit begin �y2010�N�x�v�]�Ή��z
            if (sendTo.isEnabled()) {
                switch (sendTo.getSelectedIndex()) {
                case 1: // 2��
                	printShijishoBranch(pd, "page1", source, station1, station2, printStyle);
                	printShijishoBranch(pd, "page1", source, station2, station1, printStyle);
                    break;
                case 2: // �O�҂̂�
                	printShijishoBranch(pd, "page1", source, station1, station2, printStyle);
                    break;
                case 3: // ��҂̂�
                	printShijishoBranch(pd, "page1", source, station2, station1, printStyle);
                    break;
                }
            } else {
                // �P��̃X�e�[�V�����̂�
            	printShijishoBranch(pd, "page1", source, station1, station2, printStyle);
            }
            //[ID:0000635][Shin Fujihara] 2011/02/25 edit end �y2010�N�x�v�]�Ή��z

            pd.endPrintEdit();

            IkenshoCommon.openPDF(pd);

        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }

    }

    //[ID:0000635][Shin Fujihara] 2011/02/25 add begin �y2010�N�x�v�]�Ή��z
    public void printShijishoBranch(ACChotarouXMLWriter pd, String formatName,
            VRMap data, String stationName1, String stationName2, int printStyle)
            throws Exception {
    	
    	//���y�[�W���������Ă��邩�ǂ����ŁA�󎚂Ɏg�p���郁�\�b�h��؂�ւ���
    	//���y�[�W�A��
    	if (isPageBreak) {
    		printShijishoMulti(pd, data, stationName1, stationName2, printStyle);
    		
    	//���y�[�W�Ȃ��i�]���ǂ���̃��\�b�h���g�p�j
    	} else {
    		printShijisho(pd, formatName, data, stationName1, stationName2, printStyle);
    	}
    	
    }
    //[ID:0000635][Shin Fujihara] 2011/02/25 add end �y2010�N�x�v�]�Ή��z
    
    
    /**
     * �w������������܂��B
     * 
     * @param pd XML�����N���X
     * @param formatName �t�H�[�}�b�gID
     * @param data �f�[�^�\�[�X
     * @param stationName1 ����X�e�[�V������
     * @param stationName2 ���̑��̈���X�e�[�V������
     * @param printStyle ����^�C�v(1: ��Ë@�ցA 2:�V�l�ی��{��)
     * @throws Exception ������O
     */
    public static void printShijisho(ACChotarouXMLWriter pd, String formatName,
            VRMap data, String stationName1, String stationName2, int printStyle)
            throws Exception {
    	
        pd.beginPageEdit(formatName);

        String text;
        StringBuffer sb;

        // ����
        IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid2.h1.w2");

        // ���N����
        Date date = (Date) VRBindPathParser.get("BIRTHDAY", data);
        String era = VRDateParser.format(date, "gg");
        if ("��".equals(era)) {
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } else if ("��".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } else if ("��".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } else if ("��".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
        } else {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        }
        // �N
        IkenshoCommon.addString(pd, "Grid2.h1.w5", VRDateParser.format(date,
                "ee"));
        // ��
        IkenshoCommon.addString(pd, "Grid2.h1.w7", VRDateParser.format(date,
                "MM"));
        // ��
        IkenshoCommon.addString(pd, "Grid2.h1.w9", VRDateParser.format(date,
                "dd"));

        // �N��
        IkenshoCommon.addString(pd, data, "AGE", "Grid2.h1.w11");

        // �X�֔ԍ�
        IkenshoCommon.addString(pd, data, "POST_CD", "Grid3.h1.w4");
        // �Z��
        IkenshoCommon.addString(pd, data, "ADDRESS", "Grid3.h2.w4");
        // �d�b�ԍ�
        IkenshoCommon.addTel(pd, data, "TEL1", "TEL2", "Grid3.h3.w2");

        // �K��Ō�w������ �J�n
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

        // �K��Ō�w������ �I��
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

        // �_�H���ˎw������ �J�n
        text = String.valueOf(VRBindPathParser.get("TENTEKI_FROM", data));
        if (text.indexOf("0000�N") < 0) {
            IkenshoCommon.addString(pd, "Grid1.h2.w1", "�i"
                    + text.substring(0, 2));
            IkenshoCommon.addString(pd, "Grid1.h2.w2", text.substring(2, 4));
            if (text.indexOf("00��") < 0) {
                IkenshoCommon
                        .addString(pd, "Grid1.h2.w4", text.substring(5, 7));
                if (text.indexOf("00��") < 0) {
                    IkenshoCommon.addString(pd, "Grid1.h2.w6", text.substring(
                            8, 10));
                }
            }
        } else {
            IkenshoCommon.addString(pd, "Grid1.h2.w1", "�i");
        }

        // �_�H���ˎw������ �I��
        text = String.valueOf(VRBindPathParser.get("TENTEKI_TO", data));
        if (text.indexOf("0000�N") < 0) {
            IkenshoCommon.addString(pd, "Grid1.h2.w15", text.substring(0, 2));
            IkenshoCommon.addString(pd, "Grid1.h2.w8", text.substring(2, 4));
            if (text.indexOf("00��") < 0) {
                IkenshoCommon.addString(pd, "Grid1.h2.w10", text
                        .substring(5, 7));
                if (text.indexOf("00��") < 0) {
                    IkenshoCommon.addString(pd, "Grid1.h2.w12", text.substring(
                            8, 10));
                }
            }
        }

        //2008/3/6 H.Tanaka Add Sta �K��Ō�w�����󎚕ύX�Ή�
        // �傽�鏝�a��
        sb = new StringBuffer();
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
        
        //array�̒��Ƀf�[�^�������Ă��邩�ǂ����m�F
        if (!array.isEmpty()){
        	
         	//�傽�鏝�a�������P�����L�ڂ���Ă��Ȃ��̏ꍇ�A�ԍ��͕t���Ȃ��B
	        if (array.size() == 1){
	        	sb.append(array.get(0));
	        }
	        //�傽�鏝�a�����P�ȏ�L�ڂ���Ă���ꍇ�A"�i�P�j"����ԍ���t����B
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
        //2008/3/6 H.Tanaka Add End
        //2008/3/6 H.Tanaka Del Sta �K��Ō�w�����󎚕ύX�Ή�
        //text = String.valueOf(VRBindPathParser.get("SINDAN_NM1", data));
        //if (!IkenshoCommon.isNullText(text)) {
        //    sb.append(text);
        //}
        //text = String.valueOf(VRBindPathParser.get("SINDAN_NM2", data));
        //if (!IkenshoCommon.isNullText(text)) {
        //    if (sb.length() > 0) {
        //        sb.append("�^");
        //    }
        //    sb.append(text);
        //}
        //text = String.valueOf(VRBindPathParser.get("SINDAN_NM3", data));
        //if (!IkenshoCommon.isNullText(text)) {
        //    if (sb.length() > 0) {
        //        sb.append("�^");
        //    }
        //    sb.append(text);
        //}
        //2008/3/6 H.Tanaka Del end
        IkenshoCommon.addString(pd, "Grid4.h1.w2", sb.toString());


        //2009/01/09 [Tozo Tanaka] Replace - begin
//        // ���a���Ï��
//        IkenshoCommon.addString(pd, data, "MT_STS", "Grid6.h1.w2");
//        // ���^���̖�܂̗p�@�E�p��
//        addMedicine(pd, data, "1", "Grid7.h1.w4");
//        addMedicine(pd, data, "2", "Grid7.h1.w2");
//        addMedicine(pd, data, "3", "Grid7.h2.w4");
//        addMedicine(pd, data, "4", "Grid7.h2.w2");
//        addMedicine(pd, data, "5", "Grid7.h3.w4");
//        addMedicine(pd, data, "6", "Grid7.h3.w2");

        // ���a���Ï��
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
//        ACChotarouXMLUtilities.setValue(pd, "Grid6.h1.w2",
//                getInsertionLineSeparatorToStringOnByte(ACCastUtilities.toString(data
//                        .get("MT_STS")), 100));
        String[] lines = ACTextUtilities.separateLineWrapOnByte(ACCastUtilities
                .toString(data.get("MT_STS")), 100);
        int linesCount = Math.min(5, lines.length);
        int totalByteCount = 0;
        StringBuffer sbSickProgress = new StringBuffer();
        for (int i = 0; i < linesCount; i++) {
            if (totalByteCount > 0) {
                sbSickProgress.append(IkenshoConstants.LINE_SEPARATOR);
            }
            int lineByteLen = lines[i].getBytes().length;
            if (totalByteCount + lineByteLen > 500) {
                sbSickProgress.append(lines[i].substring(0,
                        Math.max(0,501 - (totalByteCount + lineByteLen))));
                break;
            }
            sbSickProgress.append(lines[i]);
            totalByteCount += lineByteLen;
        }
        ACChotarouXMLUtilities.setValue(pd, "Grid6.h1.w2", sbSickProgress
                .toString());
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
        
        // ���^���̖�܂̗p�@�E�p��
        if (!(
                IkenshoCommon.isNullText(VRBindPathParser.get("MEDICINE7", data)) && 
                IkenshoCommon.isNullText(VRBindPathParser.get("MEDICINE8", data)) &&
                IkenshoCommon.isNullText(VRBindPathParser.get("DOSAGE7", data)) && 
                IkenshoCommon.isNullText(VRBindPathParser.get("DOSAGE8", data)) &&
                IkenshoCommon.isNullText(VRBindPathParser.get("UNIT7", data)) &&
                IkenshoCommon.isNullText(VRBindPathParser.get("UNIT8", data)) &&
                IkenshoCommon.isNullText(VRBindPathParser.get("USAGE7", data)) && 
                IkenshoCommon.isNullText(VRBindPathParser.get("USAGE8", data)) 
                ) &&
                (getMedicineViewCount()>6)) {
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
        //2009/01/09 [Tozo Tanaka] Replace - end

        // �Q������x
        IkenshoCommon.addSelection(pd, data, "NETAKIRI", new String[] {
                "Shape27", "Shape25", "Shape26", "Shape22", "Shape20",
                "Shape21", "Shape24", "Shape23", "Shape15" }, -1);
        // �s���̏��
        IkenshoCommon.addSelection(pd, data, "CHH_STS", new String[] {
                "Shape28", "Shape17", "Shape18", "Shape13", "Shape11",
                "Shape12", "Shape16", "Shape14" }, -1);

        // �v���F��̏�
        switch (((Integer) VRBindPathParser.get("YOUKAIGO_JOUKYOU", data))
                .intValue()) {
        case 1: // ����
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            break;
        case 11:// �v�x��
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 21:// �v���1
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 22:// �v���2
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 23:// �v���3
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 24:// �v���4
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 25:// �v���5
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        default:
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        }
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add begin ����21�N4���@�����Ή�
        //��ጂ̐[���FNPUAP����
        IkenshoCommon.addSelection(pd, data, "JOKUSOU_NPUAP", new String[] {
                "Shape55", "Shape56" }, -1);
        //��ጂ̐[���FDESIGN����
        IkenshoCommon.addSelection(pd, data, "JOKUSOU_DESIGN", new String[] {
                "Shape57", "Shape58", "Shape59" }, -1);
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add end

        // �����E�g�p��Ë@�퓙
        // ���������󗬑��u
        if (((Integer) VRBindPathParser.get("JD_FUKU", data)).intValue() != 1) {
            pd.addAttribute("Shape36", "Visible", "FALSE");
        }
        // ���͉t�������u
        if (((Integer) VRBindPathParser.get("TOU_KYOUKYU", data)).intValue() != 1) {
            pd.addAttribute("Shape30", "Visible", "FALSE");
        }
        // �_�f�Ö@
        if (((Integer) VRBindPathParser.get("OX_RYO", data)).intValue() != 1) {
            pd.addAttribute("Shape50", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "OX_RYO_RYO", "Grid9.h1.w20");
        }
        // �z���@
        if (((Integer) VRBindPathParser.get("KYUINKI", data)).intValue() != 1) {
            pd.addAttribute("Shape33", "Visible", "FALSE");
        }
        // ���S�Ö��h�{
        if (((Integer) VRBindPathParser.get("CHU_JOU_EIYOU", data)).intValue() != 1) {
            pd.addAttribute("Shape32", "Visible", "FALSE");
        }
        // �A�t�|���v
        if (((Integer) VRBindPathParser.get("YUEKI_PUMP", data)).intValue() != 1) {
            pd.addAttribute("Shape31", "Visible", "FALSE");
        }
        // �o�ǉh�{
        if (((Integer) VRBindPathParser.get("KEKN_EIYOU", data)).intValue() != 1) {
            pd.addAttribute("Shape34", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "KEKN_EIYOU_METHOD",
                    "Grid9.h3.w23");
            IkenshoCommon
                    .addString(pd, data, "KEKN_EIYOU_SIZE", "Grid9.h3.w14");
            IkenshoCommon.addString(pd, data, "KEKN_EIYOU_CHG", "Grid9.h3.w18");
        }
        // ���u�J�e�[�e��
        if (((Integer) VRBindPathParser.get("RYU_CATHETER", data)).intValue() != 1) {
            pd.addAttribute("Shape42", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "RYU_CAT_SIZE", "Grid9.h4.w7");
            IkenshoCommon.addString(pd, data, "RYU_CAT_CHG", "Grid9.h4.w18");
        }
        // �l�H�ċz��
        if (((Integer) VRBindPathParser.get("JINKOU_KOKYU", data)).intValue() != 1) {
            pd.addAttribute("Shape41", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "JINKOU_KKY_HOUSIKI",
                    "Grid9.h5.w23");
            IkenshoCommon.addString(pd, data, "JINKOU_KKY_SET", "Grid9.h5.w13");
        }
        // �C�ǃJ�j���[��
        if (((Integer) VRBindPathParser.get("CANNULA", data)).intValue() != 1) {
            pd.addAttribute("Shape40", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "CANNULA_SIZE", "Grid9.h6.w7");
        }
        // �h���[��
        if (((Integer) VRBindPathParser.get("DOREN", data)).intValue() != 1) {
            pd.addAttribute("Shape37", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "DOREN_BUI", "Grid9.h6.w17");
        }
        // �l�H���
        if (((Integer) VRBindPathParser.get("JINKOU_KOUMON", data)).intValue() != 1) {
            pd.addAttribute("Shape43", "Visible", "FALSE");
        }
        // �l�H�N��
        if (((Integer) VRBindPathParser.get("JINKOU_BOUKOU", data)).intValue() != 1) {
            pd.addAttribute("Shape38", "Visible", "FALSE");
        }
        // ���̑�
        if (((Integer) VRBindPathParser.get("SOUCHAKU_OTHER_FLAG", data))
                .intValue() == 1) {
            Object obj = VRBindPathParser.get("SOUCHAKU_OTHER", data);
            if (IkenshoCommon.isNullText(obj)) {
                pd.addAttribute("Shape35", "Visible", "FALSE");
            } else {
                IkenshoCommon.addString(pd, "Grid9.h7.w6", String.valueOf(obj));
            }
        } else {
            pd.addAttribute("Shape35", "Visible", "FALSE");
        }

        //[ID:0000635][Shin Fujihara] 2011/03/01 edit begin �y2010�N�x�v�]�Ή��z
        // �×{�����w����̗��ӎ���
        //IkenshoCommon.addString(pd, data, "RSS_RYUIJIKOU", "Grid10.h2.w2");
        IkenshoCommon.addString(pd, "Grid10.h2.w2", getLineBreakedString(data.get("RSS_RYUIJIKOU")));
        
        // ���n�r���e�[�V����
        if (((Integer) VRBindPathParser.get("REHA_SIJI_UMU", data)).intValue() != 1) {
            pd.addAttribute("Shape46", "Visible", "FALSE");
        } else {
            //IkenshoCommon.addString(pd, data, "REHA_SIJI", "Grid10.h4.w2");
            IkenshoCommon.addString(pd, "Grid10.h4.w2", getLineBreakedString(data.get("REHA_SIJI")));
        }
        // ���
        if (((Integer) VRBindPathParser.get("JOKUSOU_SIJI_UMU", data))
                .intValue() != 1) {
            pd.addAttribute("Shape45", "Visible", "FALSE");
        } else {
            //IkenshoCommon.addString(pd, data, "JOKUSOU_SIJI", "Grid10.h6.w2");
            IkenshoCommon.addString(pd, "Grid10.h6.w2", getLineBreakedString(data.get("JOKUSOU_SIJI")));
        }
        // �@�퓙�̑��쉇��
        if (((Integer) VRBindPathParser.get("SOUCHAKU_SIJI_UMU", data))
                .intValue() != 1) {
            pd.addAttribute("Shape44", "Visible", "FALSE");
        } else {
            //IkenshoCommon.addString(pd, data, "SOUCHAKU_SIJI", "Grid10.h8.w2");
            IkenshoCommon.addString(pd, "Grid10.h8.w2", getLineBreakedString(data.get("SOUCHAKU_SIJI")));
        }
        // ���̑�
        if (((Integer) VRBindPathParser.get("RYUI_SIJI_UMU", data)).intValue() != 1) {
            pd.addAttribute("Shape47", "Visible", "FALSE");
        } else {
            //IkenshoCommon.addString(pd, data, "RYUI_SIJI", "Grid10.h12.w2");
        	IkenshoCommon.addString(pd, "Grid10.h12.w2", getLineBreakedString(data.get("RYUI_SIJI")));
        }

        // �_�H���ˎw��
        //IkenshoCommon.addString(pd, data, "TENTEKI_SIJI", "Grid10.h10.w1");
        IkenshoCommon.addString(pd, "Grid10.h10.w1", getLineBreakedString(data.get("TENTEKI_SIJI")));

        // �ً}���̘A����
        IkenshoCommon.addString(pd, data, "KINKYU_RENRAKU", "Grid11.h1.w2");

        // �s�ݎ��̑Ή��@
        IkenshoCommon.addString(pd, data, "FUZAIJI_TAIOU", "Grid11.h2.w2");

        // ���L���ׂ����ӎ���
        //IkenshoCommon.addString(pd, data, "SIJI_TOKKI", "Grid12.h2.w1");
        IkenshoCommon.addString(pd, "Grid12.h2.w1", getLineBreakedString(data.get("SIJI_TOKKI")));
        //[ID:0000635][Shin Fujihara] 2011/03/01 edit begin �y2010�N�x�v�]�Ή��z

        // ���̖K��Ō�X�e�[�V�����ւ̎w��
        if (((Integer) VRBindPathParser.get("OTHER_STATION_SIJI", data))
                .intValue() != 2) {
            pd.addAttribute("Shape39", "Visible", "FALSE");
            IkenshoCommon.addString(pd, "Grid12.h4.w8", " �a");
        } else {
            pd.addAttribute("Shape48", "Visible", "FALSE");
            IkenshoCommon.addString(pd, "Grid12.h4.w8", stationName2 + " �a");
        }

        // �L����
        if (VRBindPathParser.has("KINYU_DT", data)) {
            Object obj = VRBindPathParser.get("KINYU_DT", data);
            if (obj instanceof Date) {
                IkenshoCommon.addString(pd, "Label2", VRDateParser.format(
                        (Date) obj, "gggee�NMM��dd��"));
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

        // �Ώۂ̖K��Ō�X�e�[�V����
        IkenshoCommon.addString(pd, "Label6", stationName1 + " �a");

        if (((Integer) VRBindPathParser.get("HOUMON_SIJISYO", data)).intValue() == -1) {
            // �K��Ō�w����
            if (((Integer) VRBindPathParser.get("TENTEKI_SIJISYO", data))
                    .intValue() == -1) {
                // �K��_�H���ˎw����
                IkenshoCommon.addString(pd, "title", "�K��Ō�w�����E�ݑ�ҖK��_�H���ˎw����");

            } else {
                IkenshoCommon.addString(pd, "title", "�K��Ō�w����");
            }
        } else {
            // �K��_�H���ˎw����
            IkenshoCommon.addString(pd, "title", "�ݑ�ҖK��_�H���ˎw����");
        }

        pd.endPageEdit();

    }
    
    
    //[ID:0000635][Shin Fujihara] 2011/02/25 add begin �y2010�N�x�v�]�Ή��z
    //�w���������y�[�W���Ĉ󎚂���
    public static void printShijishoMulti(
    		ACChotarouXMLWriter pd,  VRMap data, String stationName1, String stationName2, int printStyle)
            throws Exception {
    	
        pd.beginPageEdit("page1");

        String text;
        StringBuffer sb;
        
        if (((Integer) VRBindPathParser.get("HOUMON_SIJISYO", data)).intValue() == -1) {
            // �K��Ō�w����
            if (((Integer) VRBindPathParser.get("TENTEKI_SIJISYO", data)).intValue() == -1) {
                // �K��_�H���ˎw����
                IkenshoCommon.addString(pd, "title", "�K��Ō�w�����E�ݑ�ҖK��_�H���ˎw����");

            } else {
                IkenshoCommon.addString(pd, "title", "�K��Ō�w����");
            }
        } else {
            // �K��_�H���ˎw����
            IkenshoCommon.addString(pd, "title", "�ݑ�ҖK��_�H���ˎw����");
        }

        // ����
        IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid2.h1.w2");

        // ���N����
        Date date = (Date) VRBindPathParser.get("BIRTHDAY", data);
        String era = VRDateParser.format(date, "gg");
        if ("��".equals(era)) {
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } else if ("��".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } else if ("��".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } else if ("��".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
        } else {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        }
        // �N
        IkenshoCommon.addString(pd, "Grid2.h1.w5", VRDateParser.format(date,"ee"));
        // ��
        IkenshoCommon.addString(pd, "Grid2.h1.w7", VRDateParser.format(date,"MM"));
        // ��
        IkenshoCommon.addString(pd, "Grid2.h1.w9", VRDateParser.format(date,"dd"));

        // �N��
        IkenshoCommon.addString(pd, data, "AGE", "Grid2.h1.w11");

        // �X�֔ԍ�
        IkenshoCommon.addString(pd, data, "POST_CD", "Grid3.h1.w4");
        // �Z��
        IkenshoCommon.addString(pd, data, "ADDRESS", "Grid3.h2.w4");
        // �d�b�ԍ�
        IkenshoCommon.addTel(pd, data, "TEL1", "TEL2", "Grid3.h3.w2");

        // �K��Ō�w������ �J�n
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

        // �K��Ō�w������ �I��
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

        // �_�H���ˎw������ �J�n
        text = String.valueOf(VRBindPathParser.get("TENTEKI_FROM", data));
        if (text.indexOf("0000�N") < 0) {
            IkenshoCommon.addString(pd, "Grid1.h2.w1", "�i"
                    + text.substring(0, 2));
            IkenshoCommon.addString(pd, "Grid1.h2.w2", text.substring(2, 4));
            if (text.indexOf("00��") < 0) {
                IkenshoCommon
                        .addString(pd, "Grid1.h2.w4", text.substring(5, 7));
                if (text.indexOf("00��") < 0) {
                    IkenshoCommon.addString(pd, "Grid1.h2.w6", text.substring(
                            8, 10));
                }
            }
        } else {
            IkenshoCommon.addString(pd, "Grid1.h2.w1", "�i");
        }

        // �_�H���ˎw������ �I��
        text = String.valueOf(VRBindPathParser.get("TENTEKI_TO", data));
        if (text.indexOf("0000�N") < 0) {
            IkenshoCommon.addString(pd, "Grid1.h2.w15", text.substring(0, 2));
            IkenshoCommon.addString(pd, "Grid1.h2.w8", text.substring(2, 4));
            if (text.indexOf("00��") < 0) {
                IkenshoCommon.addString(pd, "Grid1.h2.w10", text
                        .substring(5, 7));
                if (text.indexOf("00��") < 0) {
                    IkenshoCommon.addString(pd, "Grid1.h2.w12", text.substring(
                            8, 10));
                }
            }
        }

        // �傽�鏝�a��
        sb = new StringBuffer();
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
        
        //array�̒��Ƀf�[�^�������Ă��邩�ǂ����m�F
        if (!array.isEmpty()){
        	
         	//�傽�鏝�a�������P�����L�ڂ���Ă��Ȃ��̏ꍇ�A�ԍ��͕t���Ȃ��B
	        if (array.size() == 1){
	        	sb.append(array.get(0));
	        }
	        //�傽�鏝�a�����P�ȏ�L�ڂ���Ă���ꍇ�A"�i�P�j"����ԍ���t����B
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
        
        //�Ǐ󥎡�Ï��
        IkenshoCommon.addString(pd, "lblJyotai", getLineBreakedString(data.get("MT_STS")));
        
        // ���^���̖�܂̗p�@�E�p��
        addMedicine(pd, data, "1", "sickMedicines8.h1.w4");
        addMedicine(pd, data, "2", "sickMedicines8.h1.w2");
        addMedicine(pd, data, "3", "sickMedicines8.h2.w4");
        addMedicine(pd, data, "4", "sickMedicines8.h2.w2");
        addMedicine(pd, data, "5", "sickMedicines8.h3.w4");
        addMedicine(pd, data, "6", "sickMedicines8.h3.w2");
        addMedicine(pd, data, "7", "sickMedicines8.h4.w4");
        addMedicine(pd, data, "8", "sickMedicines8.h4.w2");
        
        // �Q������x
        IkenshoCommon.addSelection(pd, data, "NETAKIRI", new String[] {
                "Shape27", "Shape25", "Shape26", "Shape22", "Shape20",
                "Shape21", "Shape24", "Shape23", "Shape15" }, -1);
        // �s���̏��
        IkenshoCommon.addSelection(pd, data, "CHH_STS", new String[] {
                "Shape28", "Shape17", "Shape18", "Shape13", "Shape11",
                "Shape12", "Shape16", "Shape14" }, -1);

        // �v���F��̏�
        switch (((Integer) VRBindPathParser.get("YOUKAIGO_JOUKYOU", data))
                .intValue()) {
        case 1: // ����
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            break;
        case 11:// �v�x��
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 21:// �v���1
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 22:// �v���2
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 23:// �v���3
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 24:// �v���4
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 25:// �v���5
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        default:
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        }
        //��ጂ̐[���FNPUAP����
        IkenshoCommon.addSelection(pd, data, "JOKUSOU_NPUAP", new String[] {
                "Shape55", "Shape56" }, -1);
        //��ጂ̐[���FDESIGN����
        IkenshoCommon.addSelection(pd, data, "JOKUSOU_DESIGN", new String[] {
                "Shape57", "Shape58", "Shape59" }, -1);

        // �����E�g�p��Ë@�퓙
        // ���������󗬑��u
        if (((Integer) VRBindPathParser.get("JD_FUKU", data)).intValue() != 1) {
            pd.addAttribute("Shape36", "Visible", "FALSE");
        }
        // ���͉t�������u
        if (((Integer) VRBindPathParser.get("TOU_KYOUKYU", data)).intValue() != 1) {
            pd.addAttribute("Shape30", "Visible", "FALSE");
        }
        // �_�f�Ö@
        if (((Integer) VRBindPathParser.get("OX_RYO", data)).intValue() != 1) {
            pd.addAttribute("Shape50", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "OX_RYO_RYO", "Grid9.h1.w20");
        }
        // �z���@
        if (((Integer) VRBindPathParser.get("KYUINKI", data)).intValue() != 1) {
            pd.addAttribute("Shape33", "Visible", "FALSE");
        }
        // ���S�Ö��h�{
        if (((Integer) VRBindPathParser.get("CHU_JOU_EIYOU", data)).intValue() != 1) {
            pd.addAttribute("Shape32", "Visible", "FALSE");
        }
        // �A�t�|���v
        if (((Integer) VRBindPathParser.get("YUEKI_PUMP", data)).intValue() != 1) {
            pd.addAttribute("Shape31", "Visible", "FALSE");
        }
        // �o�ǉh�{
        if (((Integer) VRBindPathParser.get("KEKN_EIYOU", data)).intValue() != 1) {
            pd.addAttribute("Shape34", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "KEKN_EIYOU_METHOD",
                    "Grid9.h3.w23");
            IkenshoCommon
                    .addString(pd, data, "KEKN_EIYOU_SIZE", "Grid9.h3.w14");
            IkenshoCommon.addString(pd, data, "KEKN_EIYOU_CHG", "Grid9.h3.w18");
        }
        // ���u�J�e�[�e��
        if (((Integer) VRBindPathParser.get("RYU_CATHETER", data)).intValue() != 1) {
            pd.addAttribute("Shape42", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "RYU_CAT_SIZE", "Grid9.h4.w7");
            IkenshoCommon.addString(pd, data, "RYU_CAT_CHG", "Grid9.h4.w18");
        }
        // �l�H�ċz��
        if (((Integer) VRBindPathParser.get("JINKOU_KOKYU", data)).intValue() != 1) {
            pd.addAttribute("Shape41", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "JINKOU_KKY_HOUSIKI",
                    "Grid9.h5.w23");
            IkenshoCommon.addString(pd, data, "JINKOU_KKY_SET", "Grid9.h5.w13");
        }
        // �C�ǃJ�j���[��
        if (((Integer) VRBindPathParser.get("CANNULA", data)).intValue() != 1) {
            pd.addAttribute("Shape40", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "CANNULA_SIZE", "Grid9.h6.w7");
        }
        // �h���[��
        if (((Integer) VRBindPathParser.get("DOREN", data)).intValue() != 1) {
            pd.addAttribute("Shape37", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "DOREN_BUI", "Grid9.h6.w17");
        }
        // �l�H���
        if (((Integer) VRBindPathParser.get("JINKOU_KOUMON", data)).intValue() != 1) {
            pd.addAttribute("Shape43", "Visible", "FALSE");
        }
        // �l�H�N��
        if (((Integer) VRBindPathParser.get("JINKOU_BOUKOU", data)).intValue() != 1) {
            pd.addAttribute("Shape38", "Visible", "FALSE");
        }
        // ���̑�
        if (((Integer) VRBindPathParser.get("SOUCHAKU_OTHER_FLAG", data))
                .intValue() == 1) {
            Object obj = VRBindPathParser.get("SOUCHAKU_OTHER", data);
            if (IkenshoCommon.isNullText(obj)) {
                pd.addAttribute("Shape35", "Visible", "FALSE");
            } else {
                IkenshoCommon.addString(pd, "Grid9.h7.w6", String.valueOf(obj));
            }
        } else {
            pd.addAttribute("Shape35", "Visible", "FALSE");
        }

        
        //�����ŉ��y�[�W============================
        pd.endPageEdit();
        pd.beginPageEdit("page2");
        
        //���Ҏ����ƔN����Čf
        IkenshoCommon.addString(pd, data, "PATIENT_NM", "patientData.h1.w1");
        IkenshoCommon.addString(pd, data, "AGE", "patientData.h1.w3");
        
        
        // �×{�����w����̗��ӎ���
        IkenshoCommon.addString(pd, "lblRyoyo", getLineBreakedString(data.get("RSS_RYUIJIKOU")));
        
        // ���n�r���e�[�V����
        if (((Integer) VRBindPathParser.get("REHA_SIJI_UMU", data)).intValue() != 1) {
            pd.addAttribute("Shape46", "Visible", "FALSE");
        } else {
        	IkenshoCommon.addString(pd, "lblRiha", getLineBreakedString(data.get("REHA_SIJI")));
        }
        // ���
        if (((Integer) VRBindPathParser.get("JOKUSOU_SIJI_UMU", data))
                .intValue() != 1) {
            pd.addAttribute("Shape45", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, "lblJyokusyo", getLineBreakedString(data.get("JOKUSOU_SIJI")));
        }
        // �@�퓙�̑��쉇��
        if (((Integer) VRBindPathParser.get("SOUCHAKU_SIJI_UMU", data))
                .intValue() != 1) {
            pd.addAttribute("Shape44", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, "lblSochaku", getLineBreakedString(data.get("SOUCHAKU_SIJI")));
        }
        // ���̑�
        if (((Integer) VRBindPathParser.get("RYUI_SIJI_UMU", data)).intValue() != 1) {
            pd.addAttribute("Shape47", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, "lblEtc", getLineBreakedString(data.get("RYUI_SIJI")));
        }

        // �_�H���ˎw��
        IkenshoCommon.addString(pd, "lblZaitaku", getLineBreakedString(data.get("TENTEKI_SIJI")));

        // �ً}���̘A����
        IkenshoCommon.addString(pd, data, "KINKYU_RENRAKU", "Grid11.h1.w2");

        // �s�ݎ��̑Ή��@
        IkenshoCommon.addString(pd, data, "FUZAIJI_TAIOU", "Grid11.h2.w2");

        // ���L���ׂ����ӎ���
        IkenshoCommon.addString(pd, "lblTokki", getLineBreakedString(data.get("SIJI_TOKKI")));

        // ���̖K��Ō�X�e�[�V�����ւ̎w��
        if (((Integer) VRBindPathParser.get("OTHER_STATION_SIJI", data))
                .intValue() != 2) {
            pd.addAttribute("Shape39", "Visible", "FALSE");
            IkenshoCommon.addString(pd, "Grid12.h4.w8", " �a");
        } else {
            pd.addAttribute("Shape48", "Visible", "FALSE");
            IkenshoCommon.addString(pd, "Grid12.h4.w8", stationName2 + " �a");
        }

        // �L����
        if (VRBindPathParser.has("KINYU_DT", data)) {
            Object obj = VRBindPathParser.get("KINYU_DT", data);
            if (obj instanceof Date) {
                IkenshoCommon.addString(pd, "Label2", VRDateParser.format(
                        (Date) obj, "gggee�NMM��dd��"));
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

        // �Ώۂ̖K��Ō�X�e�[�V����
        IkenshoCommon.addString(pd, "Label6", stationName1 + " �a");

        pd.endPageEdit();

    }
    
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
    
    
    //[ID:0000635][Shin Fujihara] 2011/02/25 add end �y2010�N�x�v�]�Ή��z
    

    /**
     * ��܏����o�͂��܂��B
     * 
     * @param pd XML�����N���X
     * @param data �f�[�^�\�[�X
     * @param index �C���f�b�N�X
     * @param target �o�͐�
     * @throws Exception ��͗�O
     */
    protected static void addMedicine(ACChotarouXMLWriter pd, VRMap data,
            String index, String target) throws Exception {
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
            sb.append(String.valueOf(obj));
        }
        IkenshoCommon.addString(pd, target, sb.toString());
    }

    private void jbInit() throws Exception {
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//        contentPane = (JPanel) this.getContentPane();
//        contentPane.add(contents);
//
//        contents.setLayout(new VRLayout());
//        contents.add(styleGroup, VRLayout.NORTH);
//        contents.add(sendToGroup, VRLayout.NORTH);
//        contents.add(buttons, VRLayout.NORTH);
//
//        // ���[�l��
//        styleGroup.setText("���[�l���i�V�l�ی��{�݈ȊO�̎{�݂́u��Ë@�֑��v��I�����Ă��������j");
//        styleGroup.setLayout(new BorderLayout());
//        styleGroup.add(style, BorderLayout.CENTER);
//        VRLayout styleLayout = new VRLayout();
//        styleLayout.setAutoWrap(false);
//        style.setLayout(styleLayout);
//        style.setUseClearButton(false);
//        style.setModel(new VRListModelAdapter(new VRArrayList(Arrays
//                .asList(new String[] { "��Ë@�֑�", "�V�l�ی��{��" }))));
//        style.setSelectedIndex(1);
//
//        // ����ƂȂ�K��Ō�X�e�[�V����
//        sendToGroup.setText("����ƂȂ�K��Ō�X�e�[�V������I�����Ă��������B�i�����I�����̂ݑI���\�j");
//        sendToGroup.setLayout(new VRLayout());
//        sendToGroup.add(sendTo, VRLayout.CLIENT);
//        sendTo.setUseClearButton(false);
//
//        // �����{�^��
//        buttons.setLayout(new VRLayout());
//        buttons.add(cancel, VRLayout.EAST);
//        buttons.add(ok, VRLayout.EAST);
//        ok.setText("���(O)");
//        ok.setMnemonic('O');
//        cancel.setText("�L�����Z��(C)");
//        cancel.setMnemonic('C');
        
        ((JPanel) this.getContentPane()).add(getContents());

        getContents().setLayout(new VRLayout());
        getContents().add(getStyleGroup(), VRLayout.NORTH);
        getContents().add(getSendToGroup(), VRLayout.NORTH);
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

        // ����ƂȂ�K��Ō�X�e�[�V����
        getSendToGroup().setText("����ƂȂ�K��Ō�X�e�[�V������I�����Ă��������B�i�����I�����̂ݑI���\�j");
        getSendToGroup().setLayout(new VRLayout());
        getSendToGroup().add(getSendTo(), VRLayout.CLIENT);
        getSendTo().setUseClearButton(false);

        // �����{�^��
        getButtons().setLayout(new VRLayout());
        getButtons().add(getCancel(), VRLayout.EAST);
        getButtons().add(getOk(), VRLayout.EAST);
        getOk().setText("���(O)");
        getOk().setMnemonic('O');
        getCancel().setText("�L�����Z��(C)");
        getCancel().setMnemonic('C');
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
    }

    /**
     * �ʒu�����������܂��B
     */
    private void init() {
        // �E�B���h�E�̃T�C�Y
        setSize(new Dimension(600, 200));
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

    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            closeWindow();
        }
    }

    protected void closeWindow() {
        // ���g��j������
        this.dispose();
    }

    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//    private class IkenshoVirticalRadioButtonGroup extends
//            ACClearableRadioButtonGroup {
//        public IkenshoVirticalRadioButtonGroup() {
//            super();
//            setLayout(new VRLayout());
//        }
//
//        protected void addRadioButton(JRadioButton item) {
//            this.add(item, VRLayout.FLOW_RETURN);
//        }
//    }

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
     * ok ��Ԃ��܂��B
     * @return ok
     */
    protected ACButton getOk() {
        if(ok==null){
            ok = new ACButton();
        }
        return ok;
    }

    /**
     * sendTo ��Ԃ��܂��B
     * @return sendTo
     */
    protected IkenshoVirticalRadioButtonGroup getSendTo() {
        if(sendTo==null){
            sendTo = new IkenshoVirticalRadioButtonGroup();
        }
        return sendTo;
    }

    /**
     * sendToGroup ��Ԃ��܂��B
     * @return sendToGroup
     */
    protected ACGroupBox getSendToGroup() {
        if(sendToGroup==null){
            sendToGroup = new ACGroupBox();
        }
        return sendToGroup;
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
     * styleGroup ��Ԃ��܂��B
     * @return styleGroup
     */
    protected ACGroupBox getStyleGroup() {
        if(styleGroup==null){
            styleGroup = new ACGroupBox();
        }
        return styleGroup;
    }
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
    
    //2009/01/21 [Tozo Tanaka] Add - begin
    protected static int getMedicineViewCount() {
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
//        int ikenshoCount = 6;
//        int shijishoCount = ikenshoCount;
//        try {
//            if (ACFrame.getInstance().hasProperty(
//                    "DocumentSetting/MedicineViewCount")
//                    && ACCastUtilities.toInt(ACFrame.getInstance().getProperty(
//                            "DocumentSetting/MedicineViewCount"), 6) == 8) {
//                ikenshoCount = 8;
//            }
//
//            shijishoCount = ikenshoCount;
//            if (ACFrame
//                    .getInstance()
//                    .hasProperty(
//                            "DocumentSetting/MedicineViewCountOfHoumonKangoShijishoFixed6")
//                    && ACCastUtilities
//                            .toBoolean(
//                                    ACFrame
//                                            .getInstance()
//                                            .getProperty(
//                                                    "DocumentSetting/MedicineViewCountOfHoumonKangoShijishoFixed6"),
//                                    false)) {
//                // �w�����ݒ��D��
//                shijishoCount = 6;
//            }
//
//        } catch (Exception e) {
//        }
//        return shijishoCount;
        return 8;
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
    }
    /**
     * �w�肳�ꂽ�������ŉ��s�����������Ԃ��܂��B
     * @param chr ���s�ΏۂƂȂ镶����
     * @param byteIndex ���s��o�C�g��
     * @return ���s������}������������
     */
    public static String getInsertionLineSeparatorToStringOnByte(String chr, int byteIndex){
        String[] slCharacter = ACTextUtilities.separateLineWrapOnByte(chr,byteIndex);
        
        StringBuffer sbCharacter = new StringBuffer();

        for (int i = 0; i < slCharacter.length; i++) {
            sbCharacter.append(slCharacter[i]);
            //�ŏI�s�ł���ꍇ�͉��s�R�[�h��ǉ����Ȃ�
            if (i != slCharacter.length - 1) {
                //���s�R�[�h��ǉ�����
                sbCharacter.append(ACConstants.LINE_SEPARATOR);
            }
        }

        String insertionLineSeparatorString = sbCharacter.toString();
        
        return insertionLineSeparatorString;
    }
    //2009/01/21 [Tozo Tanaka] Add - end}

}

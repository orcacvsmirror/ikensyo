package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

//[ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
public class IkenshoTokubetsuHoumonKangoShijishoPrintSetting extends
        IkenshoHoumonKangoShijishoPrintSetting {

    public IkenshoTokubetsuHoumonKangoShijishoPrintSetting(VRMap data)
            throws HeadlessException {
        super(data);
        setTitle("�u���ʖK��Ō�w�����v����ݒ�");
        getStyleGroup().setVisible(false);

        
        // �E�B���h�E�̃T�C�Y
        setSize(new Dimension(600, 150));
        
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

    protected void print() {
        try {
            getContents().applySource();
            ACChotarouXMLWriter pd = new ACChotarouXMLWriter();

            pd.beginPrintEdit();
            int printStyle = getStyle().getSelectedIndex();

            ACChotarouXMLUtilities.addFormat(pd, "page1", "SpecialShijisho.xml");

            
            String station1 = String.valueOf(VRBindPathParser.get("STATION_NM",
                    source));
            String station2 = String.valueOf(VRBindPathParser.get(
                    "OTHER_STATION_NM", source));
            if (getSendTo().isEnabled()) {
                switch (getSendTo().getSelectedIndex()) {
                case 1: // 2��
                    printShijisho(pd, "page1", source, station1, station2,
                            printStyle);
                    printShijisho(pd, "page1", source, station2, station1,
                            printStyle);
                    break;
                case 2: // �O�҂̂�
                    printShijisho(pd, "page1", source, station1, station2,
                            printStyle);
                    break;
                case 3: // ��҂̂�
                    printShijisho(pd, "page1", source, station2, station1,
                            printStyle);
                    break;
                }
            } else {
                // �P��̃X�e�[�V�����̂�
                printShijisho(pd, "page1", source, station1, station2,
                        printStyle);
            }

            pd.endPrintEdit();

            IkenshoCommon.openPDF(pd);

        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }

    }
    public static void printShijisho(ACChotarouXMLWriter pd, String formatName,
            VRMap data, String stationName1, String stationName2, int printStyle)
            throws Exception {

        pd.beginPageEdit(formatName);

        String text;
        StringBuffer sb;

        // ����
        IkenshoCommon.addString(pd, data, "PATIENT_NM", "KanjyaGrid.h1.w2");

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
        IkenshoCommon.addString(pd, "KanjyaGrid.h1.w5", VRDateParser.format(date,
                "ee"));
        // ��
        IkenshoCommon.addString(pd, "KanjyaGrid.h1.w7", VRDateParser.format(date,
                "MM"));
        // ��
        IkenshoCommon.addString(pd, "KanjyaGrid.h1.w9", VRDateParser.format(date,
                "dd"));

        // �N��
        IkenshoCommon.addString(pd, data, "AGE", "KanjyaGrid.h1.w11");

        // ���ʖK��Ō�w������ �J�n
        text = String.valueOf(VRBindPathParser.get("SIJI_KIKAN_FROM", data));
        if (text.indexOf("0000�N") < 0) {
            IkenshoCommon.addString(pd, "SijiDateGrid.h1.w1", "�i"
                    + text.substring(0, 2));
            IkenshoCommon.addString(pd, "SijiDateGrid.h1.w2", text.substring(2, 4));
            if (text.indexOf("00��") < 0) {
                IkenshoCommon
                        .addString(pd, "SijiDateGrid.h1.w4", text.substring(5, 7));
                if (text.indexOf("00��") < 0) {
                    IkenshoCommon.addString(pd, "SijiDateGrid.h1.w6", text.substring(
                            8, 10));
                }
            }
        } else {
            IkenshoCommon.addString(pd, "SijiDateGrid.h1.w1", "�i");
        }

        // ���ʖK��Ō�w������ �I��
        text = String.valueOf(VRBindPathParser.get("SIJI_KIKAN_TO", data));
        if (text.indexOf("0000�N") < 0) {
            IkenshoCommon.addString(pd, "SijiDateGrid.h1.w15", text.substring(0, 2));
            IkenshoCommon.addString(pd, "SijiDateGrid.h1.w8", text.substring(2, 4));
            if (text.indexOf("00��") < 0) {
                IkenshoCommon.addString(pd, "SijiDateGrid.h1.w10", text
                        .substring(5, 7));
                if (text.indexOf("00��") < 0) {
                    IkenshoCommon.addString(pd, "SijiDateGrid.h1.w12", text.substring(
                            8, 10));
                }
            }
        }

        // �_�H���ˎw������ �J�n
        text = String.valueOf(VRBindPathParser.get("TENTEKI_FROM", data));
        if (text.indexOf("0000�N") < 0) {
            IkenshoCommon.addString(pd, "SijiDateGrid.h2.w1", "�i"
                    + text.substring(0, 2));
            IkenshoCommon.addString(pd, "SijiDateGrid.h2.w2", text.substring(2, 4));
            if (text.indexOf("00��") < 0) {
                IkenshoCommon
                        .addString(pd, "SijiDateGrid.h2.w4", text.substring(5, 7));
                if (text.indexOf("00��") < 0) {
                    IkenshoCommon.addString(pd, "SijiDateGrid.h2.w6", text.substring(
                            8, 10));
                }
            }
        } else {
            IkenshoCommon.addString(pd, "SijiDateGrid.h2.w1", "�i");
        }

        // �_�H���ˎw������ �I��
        text = String.valueOf(VRBindPathParser.get("TENTEKI_TO", data));
        if (text.indexOf("0000�N") < 0) {
            IkenshoCommon.addString(pd, "SijiDateGrid.h2.w15", text.substring(0, 2));
            IkenshoCommon.addString(pd, "SijiDateGrid.h2.w8", text.substring(2, 4));
            if (text.indexOf("00��") < 0) {
                IkenshoCommon.addString(pd, "SijiDateGrid.h2.w10", text
                        .substring(5, 7));
                if (text.indexOf("00��") < 0) {
                    IkenshoCommon.addString(pd, "SijiDateGrid.h2.w12", text.substring(
                            8, 10));
                }
            }
        }


        // �Ǐ�E��i
        IkenshoCommon.addString(pd, "SYOJYO", ACTextUtilities
                .concatLineWrap(ACTextUtilities
                        .separateLineWrapOnByte(ACCastUtilities.toString(data
                                .get("TOKUBETSU_SHOJO_SHUSO")), 100)));

        // ���ӎ����y�юw������
        IkenshoCommon.addString(pd, "RYUI", ACTextUtilities
                .concatLineWrap(ACTextUtilities
                        .separateLineWrapOnByte(ACCastUtilities.toString(data
                                .get("TOKUBETSU_RYUI")), 100)));

        // �_�H���ˎw��
        IkenshoCommon.addString(pd, "TENTEKI", ACTextUtilities
                .concatLineWrap(ACTextUtilities
                        .separateLineWrapOnByte(ACCastUtilities.toString(data
                                .get("TOKUBETSU_CHUSHA_SHIJI")), 100)));

        // �ً}���̘A����
        sb = new StringBuffer();
        sb.append(ACCastUtilities.toString(VRBindPathParser.get("KINKYU_RENRAKU", data)));
        if(sb.length() > 0){
            sb.append(ACConstants.LINE_SEPARATOR);
        }
        sb.append(ACTextUtilities.concatLineWrap(ACTextUtilities
                .separateLineWrapOnByte(ACCastUtilities.toString(data
                        .get("TOKUBETSU_KINKYU_RENRAKU")), 100)));
        if(!ACTextUtilities.isNullText(VRBindPathParser.get("FUZAIJI_TAIOU", data))){
            if(sb.length() > 0){
                sb.append(ACConstants.LINE_SEPARATOR);
            }
            sb.append("�s�ݎ��̑Ή��@�@"+VRBindPathParser.get("FUZAIJI_TAIOU", data));
        }
        IkenshoCommon.addString(pd, "KINKYU", sb.toString());


        // �L����
        if (VRBindPathParser.has("KINYU_DT", data)) {
            Object obj = VRBindPathParser.get("KINYU_DT", data);
            if (obj instanceof Date) {
                IkenshoCommon.addString(pd, "KinyuDateLabell", VRDateParser.format(
                        (Date) obj, "gggee�NMM��dd��"));
            }
        }

        // ��Ë@�֖�
        IkenshoCommon.addString(pd, data, "MI_NM", "IryoKikanGrid.h1.w2");

        // ��Ë@�֓d�b�ԍ�
        IkenshoCommon.addTel(pd, data, "MI_TEL1", "MI_TEL2", "IryoKikanGrid.h3.w2");

        // ��Ë@��FAX�ԍ�
        IkenshoCommon.addTel(pd, data, "MI_FAX1", "MI_FAX2", "IryoKikanGrid.h4.w2");

        // ��t����
        IkenshoCommon.addString(pd, data, "DR_NM", "IryoKikanGrid.h5.w2");

        // �Ώۂ̖K��Ō�X�e�[�V����
        IkenshoCommon.addString(pd, "StationNameLabel", stationName1 + " �a");

        if (((Integer) VRBindPathParser.get("HOUMON_SIJISYO", data)).intValue() == -1) {
            // ���ʖK��Ō�w�����E�ݑ�ҖK��_�H���ˎw����
            ACChotarouXMLUtilities.setVisible(pd, "TITLE_TENTEKI", false);
            if (((Integer) VRBindPathParser.get("TENTEKI_SIJISYO", data))
                    .intValue() != -1) {
                //���ʖK��Ō�w����
                ACChotarouXMLUtilities.setVisible(pd, "TITLE_TOKUBETU_TENTEKI", false);
            }else{
                ACChotarouXMLUtilities.setVisible(pd, "TITLE_TOKUBETU", false);
            }
        } else {
            // �ݑ�ҖK��_�H���ˎw����
            ACChotarouXMLUtilities.setVisible(pd, "TITLE_TOKUBETU", false);
            ACChotarouXMLUtilities.setVisible(pd, "TITLE_TOKUBETU_TENTEKI", false);
        }
        
        
        pd.endPageEdit();

    }
    public boolean showModal() {
//        try {
//            boolean useOtherStation = ((Integer) VRBindPathParser.get(
//                    "OTHER_STATION_SIJI", source)).intValue() == 2;
//
//            if (useOtherStation) {
//                String station1 = String.valueOf(VRBindPathParser.get(
//                        "STATION_NM", source));
//                String station2 = String.valueOf(VRBindPathParser.get(
//                        "OTHER_STATION_NM", source));
//                if (("".equals(station1)) || ("".equals(station2))) {
//                    useOtherStation = false;
//                }
//            }
//
//            if (!useOtherStation) {
                getSendToGroup().setEnabled(false);
                getSendTo().setEnabled(false);
                getSendTo().setModel(new VRListModelAdapter(new VRArrayList(Arrays
                        .asList(new String[] { "", "", "" }))));
                
                print();
                dispose();
                return true;
//            }
//        } catch (ParseException ex) {
//            IkenshoCommon.showExceptionMessage(ex);
//            return false;
//        }
//        
//        setVisible(true);
//        return printed;

    }

}
//[ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

package jp.or.med.orca.ikensho.affair;

import java.awt.HeadlessException;
import java.util.Date;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
public class IkenshoSeishinkaTokubetsuHoumonKangoShijishoPrintSetting extends IkenshoSeishinkaHoumonKangoShijishoPrintSetting {
    
    /**
     * �R���X�g���N�^
     * @param data ����f�[�^
     */
    public IkenshoSeishinkaTokubetsuHoumonKangoShijishoPrintSetting(VRMap data) throws HeadlessException {
        super(data);
    }
    
    /**
     * ���[�_���\�����A������ꂽ����Ԃ��܂�
     * @return ������ꂽ��
     */
    public boolean showModal() {
        // �������
        print();
        // �I������
        dispose();
        return true;
    }
    
    /**
     * �������
     */
    protected void print() {
        try {
            getContents().applySource();
            ACChotarouXMLWriter pd = new ACChotarouXMLWriter();
            // ����J�n
            pd.beginPrintEdit();
            // ��`�̐ݒ�
            ACChotarouXMLUtilities.addFormat(pd, "page1", "SpecialSeishinsho.xml");
            // �������
            printShijisho(pd, source);
            // ����I��
            pd.endPrintEdit();
            // PDF�I�[�v��
            IkenshoCommon.openPDF(pd);
            
        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
    }
    
    /**
     * ���_�ȓ��ʖK��Ō�w�����������
     * @param pd XML�����N���X
     * @param data �f�[�^�\�[�X
     */
    public static void printShijisho(ACChotarouXMLWriter pd, VRMap data) throws Exception {
        
        pd.beginPageEdit("page1");
        
        // �^�C�g��
        if (((Integer) VRBindPathParser.get("HOUMON_SIJISYO", data)).intValue() == -1) {
            // ���_�ȓ��ʖK��Ō�w�����E�ݑ�ҖK��_�H���ˎw����
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
        
        // ���ʊŌ�w������ �J�n
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
        // ���ʊŌ�w������ �I��
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
        
        // �Ǐ�E��i
        IkenshoCommon.addString(pd, "SYOJYO", ACTextUtilities
                .concatLineWrap(ACTextUtilities
                        .separateLineWrapOnByte(ACCastUtilities.toString(data
                                .get("TOKUBETSU_SHOJO_SHUSO")), 100)));
        // �������K��̕K�v��
        IkenshoCommon.addSelection(pd, data, "FUKUSU_HOUMON", new String[] { "Label16", "Label17" }, -1);
        // �������K��̕K�v���F���R
        IkenshoCommon.addString(pd, "lblFkRiyu", String.valueOf(VRBindPathParser.get("FUKUSU_HOUMON_RIYU", data)));
        // �Z���ԖK��̕K�v��
        IkenshoCommon.addSelection(pd, data, "TANJIKAN_HOUMON", new String[] { "Label18", "Label19" }, -1);
        // �Z���ԖK��̕K�v���F���R
        IkenshoCommon.addString(pd, "lblTanRiyu", String.valueOf(VRBindPathParser.get("TANJIKAN_HOUMON_RIYU", data)));
        // ���ӎ����y�юw������
        IkenshoCommon.addString(pd, "RYUI", ACTextUtilities
                .concatLineWrap(ACTextUtilities
                        .separateLineWrapOnByte(ACCastUtilities.toString(data
                                .get("TOKUBETSU_RYUI")), 100)));
        
        int intValue = 0;
        
        // ����m�F
        intValue = ((Integer) VRBindPathParser.get("FUKUYAKU_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("lblFukuyakuUmu", "Visible", "FALSE");
        }
        // �����y�ѐH���ێ�̏�
        intValue = ((Integer) VRBindPathParser.get("SUIBUN_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("lblSuibunUmu", "Visible", "FALSE");
        }
        // ���_�Ǐ�
        intValue = ((Integer) VRBindPathParser.get("SEISHIN_SYOUJYOU_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("lblSeishinUmu", "Visible", "FALSE");
        }
        // ���_�Ǐ�i�ώ@���K�v�ȍ��ځj
        if (intValue == 1) {
            IkenshoCommon.addString(pd, "lblSeishinKansatu", String.valueOf(VRBindPathParser.get("SEISHIN_SYOUJYOU", data)));
        }
        // �g�̏Ǐ�
        intValue = ((Integer) VRBindPathParser.get("SHINTAI_SYOUJYOU_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("lblShintaiUmu", "Visible", "FALSE");
        }
        // �g�̏Ǐ�i�ώ@���K�v�ȍ��ځj
        if (intValue == 1) {
            IkenshoCommon.addString(pd, "lblShintaiKansatu", String.valueOf(VRBindPathParser.get("SHINTAI_SYOUJYOU", data)));
        }
        // ���̑�
        intValue = ((Integer) VRBindPathParser.get("KANSATU_OTHER_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("lblEtcUmu", "Visible", "FALSE");
        }
        // ���̑����e
        if (intValue == 1) {
            IkenshoCommon.addString(pd, "lblEtcNaiyo", String.valueOf(VRBindPathParser.get("KANSATU_OTHER", data)));
        }
        
        // �_�H���ˎw��
        IkenshoCommon.addString(pd, "TENTEKI", ACTextUtilities
                .concatLineWrap(ACTextUtilities
                        .separateLineWrapOnByte(ACCastUtilities.toString(data
                                .get("TOKUBETSU_CHUSHA_SHIJI")), 100)));
        // �ً}���̘A����
        StringBuffer sb = new StringBuffer();
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
                IkenshoCommon.addString(pd, "KinyuDateLabell", VRDateParser.format((Date) obj, "gggee�NMM��dd��"));
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
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
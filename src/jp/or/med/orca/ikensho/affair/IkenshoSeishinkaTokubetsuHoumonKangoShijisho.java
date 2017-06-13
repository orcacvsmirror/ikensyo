package jp.or.med.orca.ikensho.affair;

import java.awt.Color;
import java.text.ParseException;

import jp.or.med.orca.ikensho.IkenshoConstants;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
public class IkenshoSeishinkaTokubetsuHoumonKangoShijisho extends IkenshoTokubetsuHoumonKangoShijisho {
    
    /**
     * �R���X�g���N�^
     */    
    public IkenshoSeishinkaTokubetsuHoumonKangoShijisho() {
        super();
        setStatusText("���_�ȓ��ʖK��Ō�w����");
        buttons.setText("���_�ȓ��ʖK��Ō�w����");
        // �F�̕ύX
        Color c = IkenshoConstants.COLOR_DISTINCTION;
        buttons.getBackButton().setBackground(c);
        update.setBackground(c);
        print.setBackground(c);
        buttons.setBackground(c);
    }
    
    /**
     * �ǉ����������_�ȓ��ʖK��Ō�w�����ŗL��񍀖ڐݒ菈��
     * @param sb �ǉ�SQL
     */
    protected void appendInsertShijishoKeys(StringBuffer sb) {
        super.appendInsertShijishoKeys(sb);
        sb.append(",FUKUSU_HOUMON");
        sb.append(",FUKUSU_HOUMON_RIYU");
        sb.append(",TANJIKAN_HOUMON");
        sb.append(",TANJIKAN_HOUMON_RIYU");
        sb.append(",FUKUYAKU_UMU");
        sb.append(",SUIBUN_UMU");
        sb.append(",SEISHIN_SYOUJYOU_UMU");
        sb.append(",SEISHIN_SYOUJYOU");
        sb.append(",SHINTAI_SYOUJYOU_UMU");
        sb.append(",SHINTAI_SYOUJYOU");
        sb.append(",KANSATU_OTHER_UMU");
        sb.append(",KANSATU_OTHER");
    }
    
    /**
     * �ǉ����������_�ȓ��ʖK��Ō�w�����ŗL���l�ݒ菈��
     * @param sb �ǉ�SQL
     */
    protected void appendInsertShijishoValues(StringBuffer sb) throws ParseException {
        super.appendInsertShijishoValues(sb);
        sb.append("," + getDBSafeString("FUKUSU_HOUMON", originalData));
        sb.append("," + getDBSafeString("FUKUSU_HOUMON_RIYU", originalData));
        sb.append("," + getDBSafeString("TANJIKAN_HOUMON", originalData));
        sb.append("," + getDBSafeString("TANJIKAN_HOUMON_RIYU", originalData));
        sb.append("," + getDBSafeString("FUKUYAKU_UMU", originalData));
        sb.append("," + getDBSafeString("SUIBUN_UMU", originalData));
        sb.append("," + getDBSafeString("SEISHIN_SYOUJYOU_UMU", originalData));
        sb.append("," + getDBSafeString("SEISHIN_SYOUJYOU", originalData));
        sb.append("," + getDBSafeString("SHINTAI_SYOUJYOU_UMU", originalData));
        sb.append("," + getDBSafeString("SHINTAI_SYOUJYOU", originalData));
        sb.append("," + getDBSafeString("KANSATU_OTHER_UMU", originalData));
        sb.append("," + getDBSafeString("KANSATU_OTHER", originalData));
    }
    
    /**
     * �X�V���������_�ȓ��ʖK��Ō�w�����ŗL���ݒ菈��
     * @param sb �X�VSQL
     */
    protected void appendUpdateShijishoStetement(StringBuffer sb) throws ParseException {
        super.appendUpdateShijishoStetement(sb);
        sb.append(",FUKUSU_HOUMON = ");
        sb.append(getDBSafeString("FUKUSU_HOUMON", originalData));
        sb.append(",FUKUSU_HOUMON_RIYU = ");
        sb.append(getDBSafeString("FUKUSU_HOUMON_RIYU", originalData));
        sb.append(",TANJIKAN_HOUMON = ");
        sb.append(getDBSafeString("TANJIKAN_HOUMON", originalData));
        sb.append(",TANJIKAN_HOUMON_RIYU = ");
        sb.append(getDBSafeString("TANJIKAN_HOUMON_RIYU", originalData));
        sb.append(",FUKUYAKU_UMU = ");
        sb.append(getDBSafeString("FUKUYAKU_UMU", originalData));
        sb.append(",SUIBUN_UMU = ");
        sb.append(getDBSafeString("SUIBUN_UMU", originalData));
        sb.append(",SEISHIN_SYOUJYOU_UMU = ");
        sb.append(getDBSafeString("SEISHIN_SYOUJYOU_UMU", originalData));
        sb.append(",SEISHIN_SYOUJYOU = ");
        sb.append(getDBSafeString("SEISHIN_SYOUJYOU", originalData));
        sb.append(",SHINTAI_SYOUJYOU_UMU = ");
        sb.append(getDBSafeString("SHINTAI_SYOUJYOU_UMU", originalData));
        sb.append(",SHINTAI_SYOUJYOU = ");
        sb.append(getDBSafeString("SHINTAI_SYOUJYOU", originalData));
        sb.append(",KANSATU_OTHER_UMU = ");
        sb.append(getDBSafeString("KANSATU_OTHER_UMU", originalData));
        sb.append(",KANSATU_OTHER = ");
        sb.append(getDBSafeString("KANSATU_OTHER", originalData));
    }
    
    /**
     * ���[�_�C�A���O�\������
     * @return ���[�_�C�A���O
     */
    protected boolean showPrintDialogCustom() throws Exception {
        // ���[�����y�[�W���邩�̃t���O��ݒ肵�āA�����ʂɓn��
        // ���y�[�W�̌x���Ȃ��Ȃ�FALSE
        if (getWarningMessage().length() == 0) {
            originalData.put("IS_PAGE_BREAK", "FALSE");
        } else {
            originalData.put("IS_PAGE_BREAK", "TRUE");
        }
        return new IkenshoSeishinkaTokubetsuHoumonKangoShijishoPrintSetting(originalData).showModal();
    }
    
    /**
     * ���_�ȓ��ʖK��Ō�w�����̃t�H�[�}�b�g�敪��Ԃ��܂��B
     * @return ���_�ȓ��ʖK��Ō�w�����̃t�H�[�}�b�g�敪 
     */
    protected String getFormatKubun() {
        // ���_�ȓ��ʖK��Ō�w����
        return "3";
    }
    
    /**
     * ��������� ��Ԃ��܂��B
     * @return ���������
     */  
    protected String getCustomDocumentType() {
        return IkenshoConstants.DOC_KBN_SEISHIN;
    }
    
    /**
     * ��������� ��Ԃ��܂��B
     * @return ���������
     */ 
    protected String getAnotherDocumentType() {
        return IkenshoConstants.DOC_KBN_IKENSHO;
    }
    
    /**
     * ���҃^�u ��Ԃ��܂��B
     * @return ���҃^�u
     */
    public IkenshoHoumonKangoShijishoApplicant getApplicant() {
        if(applicant==null){
            applicant = new IkenshoSeishinkaTokubetsuHoumonKangoShijishoApplicant();
        }
        return applicant;
    }

    /**
     * �Ǐ�E��i�^�u ��Ԃ��܂��B
     * @return �Ǐ�E��i�^�u
     */
    public IkenshoTokubetsuHoumonKangoShijishoShojoShuso getShojoShuso() {
        if(shojoShuso==null){
            shojoShuso = new IkenshoSeishinkaTokubetsuHoumonKangoShijishoShojoShuso();
        }
        return shojoShuso;
    }

    /**
     * ���ӎ����E�w�������^�u ��Ԃ��܂��B
     * @return ���ӎ����E�w�������^�u
     */
    public IkenshoHoumonKangoShijishoRyuiShiji getRyuiShiji() {
        if(ryuiShiji==null){
            ryuiShiji = new IkenshoSeishinkaTokubetsuHoumonKangoShijishoRyuiShiji();
        }
        return ryuiShiji;
    }

    
    /**
     * �_�H���ˎw���^�u ��Ԃ��܂��B
     * @return �_�H���ˎw���^�u
     */
    public IkenshoHoumonKangoShijishoTenteki getTenteki() {
        if(tenteki==null){
            tenteki = new IkenshoSeishinkaTokubetsuHoumonKangoShijishoTenteki();
        }
        return tenteki;
    }

    /**
     * ��Ë@�փ^�u ��Ԃ��܂��B
     * @return ��Ë@�փ^�u
     */
    public IkenshoHoumonKangoShijishoIryoukikan getIryoukikan() {
        if(iryoukikan==null){
            iryoukikan = new IkenshoSeishinkaTokubetsuHoumonKangoShijishoIryoukikan();
        }
        return iryoukikan;
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
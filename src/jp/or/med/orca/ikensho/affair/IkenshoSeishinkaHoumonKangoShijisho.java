package jp.or.med.orca.ikensho.affair;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import jp.nichicom.ac.util.ACMessageBox;
import jp.or.med.orca.ikensho.IkenshoConstants;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
public class IkenshoSeishinkaHoumonKangoShijisho extends IkenshoHoumonKangoShijisho {

    private IkenshoHoumonKangoShijishoRyuiShiji ryuiShiji2;

    /**
     * �R���X�g���N�^
     */
    public IkenshoSeishinkaHoumonKangoShijisho() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * ��������
     */
    private void jbInit() throws Exception {
        addTabs();
        setStatusText("���_�ȖK��Ō�w����");
        buttons.setTitle("���_�ȖK��Ō�w����");
        // �F�̕ύX
        Color c = IkenshoConstants.COLOR_DISTINCTION;
        buttons.getBackButton().setBackground(c);
        read.setBackground(c);
        update.setBackground(c);
        print.setBackground(c);
        buttons.setBackground(c);
    }

    /**
     * �^�u�ǉ�����
     */
    protected void addTabs() {
        tabs.addTab("����", getApplicant());
        tabs.addTab("���a", getShoubyou());
        tabs.addTab("���݂̏󋵂P", getJyoukyou());
        tabs.addTab("���݂̏󋵂Q", getJyoukyou2());
        tabs.addTab("���ӎ����E�w�������P", getRyuiShiji());
        tabs.addTab("���ӎ����E�w�������Q", getRyuiShiji2());
        tabs.addTab("���L", getTokki());
        tabs.addTab("��Ë@��", getIryoukikan());

        tabArray = new ArrayList();
        tabArray.add(getApplicant());
        tabArray.add(getShoubyou());
        tabArray.add(getShoubyou2());
        tabArray.add(getJiritudo());
        tabArray.add(getRyuiShiji());
        tabArray.add(getRyuiShiji2());
        tabArray.add(getTokki());
        tabArray.add(getIryoukikan());
    }

    /**
     * �ǉ����������_�ȖK��Ō�w�����ŗL��񍀖ڐݒ菈��
     * @param sb �ǉ�SQL
     */
    protected void appendInsertShijishoKeys(StringBuffer sb) {
        super.appendInsertShijishoKeys(sb);
        sb.append(",TOKUBETSU_KINKYU_RENRAKU");
        sb.append(",SISETU_NM");
        sb.append(",BYOUMEI_KOKUTI");
        sb.append(",TIRYOU_UKEIRE");
        sb.append(",FUKUSU_HOUMON");
        sb.append(",TANJIKAN_HOUMON");
        sb.append(",SEIKATU_RIZUMU_UMU");
        sb.append(",SEIKATU_RIZUMU");
        sb.append(",KAJI_NOURYOKU_UMU");
        sb.append(",KAJI_NOURYOKU");
        sb.append(",TAIJIN_KANKEI_UMU");
        sb.append(",TAIJIN_KANKEI");
        sb.append(",SYAKAI_SHIGEN_UMU");
        sb.append(",SYAKAI_SHIGEN");
        sb.append(",YAKUBUTU_RYOUHOU_UMU");
        sb.append(",YAKUBUTU_RYOUHOU");
        sb.append(",SHINTAI_GAPPEISYO_UMU");
        sb.append(",SHINTAI_GAPPEISYO");
        sb.append(",SEISHIN_OTHER_UMU");
        sb.append(",SEISHIN_OTHER");
        sb.append(",JYOUHOU_SYUDAN");
    }

    /**
     * �ǉ����������_�ȖK��Ō�w�����ŗL���l�ݒ菈��
     * @param sb �ǉ�SQL
     */
    protected void appendInsertShijishoValues(StringBuffer sb) throws ParseException {
        super.appendInsertShijishoValues(sb);
        sb.append("," + getDBSafeString("TOKUBETSU_KINKYU_RENRAKU", originalData));
        sb.append("," + getDBSafeString("SISETU_NM", originalData));
        sb.append("," + getDBSafeString("BYOUMEI_KOKUTI", originalData));
        sb.append("," + getDBSafeString("TIRYOU_UKEIRE", originalData));
        sb.append("," + getDBSafeString("FUKUSU_HOUMON", originalData));
        sb.append("," + getDBSafeString("TANJIKAN_HOUMON", originalData));
        sb.append("," + getDBSafeString("SEIKATU_RIZUMU_UMU", originalData));
        sb.append("," + getDBSafeString("SEIKATU_RIZUMU", originalData));
        sb.append("," + getDBSafeString("KAJI_NOURYOKU_UMU", originalData));
        sb.append("," + getDBSafeString("KAJI_NOURYOKU", originalData));
        sb.append("," + getDBSafeString("TAIJIN_KANKEI_UMU", originalData));
        sb.append("," + getDBSafeString("TAIJIN_KANKEI", originalData));
        sb.append("," + getDBSafeString("SYAKAI_SHIGEN_UMU", originalData));
        sb.append("," + getDBSafeString("SYAKAI_SHIGEN", originalData));
        sb.append("," + getDBSafeString("YAKUBUTU_RYOUHOU_UMU", originalData));
        sb.append("," + getDBSafeString("YAKUBUTU_RYOUHOU", originalData));
        sb.append("," + getDBSafeString("SHINTAI_GAPPEISYO_UMU", originalData));
        sb.append("," + getDBSafeString("SHINTAI_GAPPEISYO", originalData));
        sb.append("," + getDBSafeString("SEISHIN_OTHER_UMU", originalData));
        sb.append("," + getDBSafeString("SEISHIN_OTHER", originalData));
        sb.append("," + getDBSafeString("JYOUHOU_SYUDAN", originalData));
    }

    /**
     * �X�V���������_�ȖK��Ō�w�����ŗL���ݒ菈��
     * @param sb �X�VSQL
     */
    protected void appendUpdateShijishoStetement(StringBuffer sb) throws ParseException {
        super.appendUpdateShijishoStetement(sb);
        sb.append(",TOKUBETSU_KINKYU_RENRAKU = ");
        sb.append(getDBSafeString("TOKUBETSU_KINKYU_RENRAKU", originalData));
        sb.append(",SISETU_NM = ");
        sb.append(getDBSafeString("SISETU_NM", originalData));
        sb.append(",BYOUMEI_KOKUTI = ");
        sb.append(getDBSafeString("BYOUMEI_KOKUTI", originalData));
        sb.append(",TIRYOU_UKEIRE = ");
        sb.append(getDBSafeString("TIRYOU_UKEIRE", originalData));
        sb.append(",FUKUSU_HOUMON = ");
        sb.append(getDBSafeString("FUKUSU_HOUMON", originalData));
        sb.append(",TANJIKAN_HOUMON = ");
        sb.append(getDBSafeString("TANJIKAN_HOUMON", originalData));
        sb.append(",SEIKATU_RIZUMU_UMU = ");
        sb.append(getDBSafeString("SEIKATU_RIZUMU_UMU", originalData));
        sb.append(",SEIKATU_RIZUMU = ");
        sb.append(getDBSafeString("SEIKATU_RIZUMU", originalData));
        sb.append(",KAJI_NOURYOKU_UMU = ");
        sb.append(getDBSafeString("KAJI_NOURYOKU_UMU", originalData));
        sb.append(",KAJI_NOURYOKU = ");
        sb.append(getDBSafeString("KAJI_NOURYOKU", originalData));
        sb.append(",TAIJIN_KANKEI_UMU = ");
        sb.append(getDBSafeString("TAIJIN_KANKEI_UMU", originalData));
        sb.append(",TAIJIN_KANKEI = ");
        sb.append(getDBSafeString("TAIJIN_KANKEI", originalData));
        sb.append(",SYAKAI_SHIGEN_UMU = ");
        sb.append(getDBSafeString("SYAKAI_SHIGEN_UMU", originalData));
        sb.append(",SYAKAI_SHIGEN = ");
        sb.append(getDBSafeString("SYAKAI_SHIGEN", originalData));
        sb.append(",YAKUBUTU_RYOUHOU_UMU = ");
        sb.append(getDBSafeString("YAKUBUTU_RYOUHOU_UMU", originalData));
        sb.append(",YAKUBUTU_RYOUHOU = ");
        sb.append(getDBSafeString("YAKUBUTU_RYOUHOU", originalData));
        sb.append(",SHINTAI_GAPPEISYO_UMU = ");
        sb.append(getDBSafeString("SHINTAI_GAPPEISYO_UMU", originalData));
        sb.append(",SHINTAI_GAPPEISYO = ");
        sb.append(getDBSafeString("SHINTAI_GAPPEISYO", originalData));
        sb.append(",SEISHIN_OTHER_UMU = ");
        sb.append(getDBSafeString("SEISHIN_OTHER_UMU", originalData));
        sb.append(",SEISHIN_OTHER = ");
        sb.append(getDBSafeString("SEISHIN_OTHER", originalData));
        sb.append(",JYOUHOU_SYUDAN = ");
        sb.append(getDBSafeString("JYOUHOU_SYUDAN", originalData));
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
        return new IkenshoSeishinkaHoumonKangoShijishoPrintSetting(originalData).showModal();
    }

    /**
     * ���_�ȖK��Ō�w�����̃t�H�[�}�b�g�敪��Ԃ��܂��B
     * @return ���_�ȖK��Ō�w�����̃t�H�[�}�b�g�敪
     */
    protected String getFormatKubun() {
        // ���_�ȖK��Ō�w����
        return "2";
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
     * ������ ��Ԃ��܂��B
     * @return ������
     */
    protected String getDocumentName() {
        return "���_�ȖK��Ō�w����";
    }

    /**
     * ���҃^�u ��Ԃ��܂��B
     * @return ���҃^�u
     */
    public IkenshoHoumonKangoShijishoApplicant getApplicant() {
        if(applicant == null){
            applicant = new IkenshoSeishinkaHoumonKangoShijishoApplicant();
        }
        return applicant;
    }

    /**
     * ���a�P�^�u ��Ԃ��܂��B
     * @return ���a�P�^�u
     */
    public IkenshoHoumonKangoShijishoSick getShoubyou() {
        if(shoubyou==null){
            shoubyou = new IkenshoSeishinkaHoumonKangoShijishoSick();
        }
        return shoubyou;
    }

    /**
     * ���݂̏󋵂P�^�u ��Ԃ��܂��B
     * @return ���݂̏󋵂P�^�u
     */
    public IkenshoHoumonKangoShijishoSick2 getJyoukyou() {
        if(shoubyou2==null){
            shoubyou2 = new IkenshoSeishinkaHoumonKangoShijishoJyoukyou1();
        }
        return shoubyou2;
    }

    /**
     * ���݂̏󋵂Q�^�u ��Ԃ��܂��B
     * @return ���݂̏󋵂Q�^�u
     */
    public IkenshoHoumonKangoShijishoMindBody1 getJyoukyou2() {
        if(jiritudo==null){
            jiritudo = new IkenshoSeishinkaHoumonKangoShijishoJyoukyou2();
        }
        return jiritudo;
    }

    /**
     * ���ӎ����E�w�������^�u�P ��Ԃ��܂��B
     * @return ���ӎ����E�w�������^�u�P
     */
    public IkenshoHoumonKangoShijishoRyuiShiji getRyuiShiji() {
        if(ryuiShiji==null){
            ryuiShiji = new IkenshoSeishinkaHoumonKangoShijishoRyuiShiji();
        }
        return ryuiShiji;
    }
    /**
     * ���ӎ����E�w�������^�u�Q ��Ԃ��܂��B
     * @return ���ӎ����E�w�������^�u�Q
     */
    public IkenshoHoumonKangoShijishoRyuiShiji getRyuiShiji2() {
        if(ryuiShiji2==null){
            ryuiShiji2 = new IkenshoSeishinkaHoumonKangoShijishoRyuiShiji2();
        }
        return ryuiShiji2;
    }

    /**
     * ���L�����^�u ��Ԃ��܂��B
     * @return ���L�����^�u
     */
    public IkenshoHoumonKangoShijishoTenteki getTokki() {
        if(tenteki==null){
            tenteki = new IkenshoSeishinkaHoumonKangoShijishoTokki();
        }
        return tenteki;
    }

    /**
     * ��Ë@�փ^�u ��Ԃ��܂��B
     * @return ��Ë@�փ^�u
     */
    public IkenshoHoumonKangoShijishoIryoukikan getIryoukikan() {
        if(iryoukikan==null){
        	iryoukikan = new IkenshoSeishinkaHoumonKangoShijishoIryoukikan();
        }
        return iryoukikan;
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
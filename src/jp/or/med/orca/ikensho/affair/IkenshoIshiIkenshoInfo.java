package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JComponent;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

public class IkenshoIshiIkenshoInfo extends IkenshoIkenshoInfoH18 {

    protected IkenshoIshiIkenshoInfoMindBody1 ishiMindBody1;
    protected IkenshoIshiIkenshoInfoMindBody3 ishiMindBody3;
    protected IkenshoIshiIkenshoInfoSpecialMention1 ishiSpesicalMention1;
    protected IkenshoIshiIkenshoInfoSick2 ishiSick2;
    protected ACAffairButton showHelp;
    
    /**
     * help ��Ԃ��܂��B
     * @return help
     */
    protected ACAffairButton getShowHelp() {
        if(showHelp==null){
            showHelp = new ACAffairButton("�L�ڗ�(H)");
            showHelp.setIconPath(ACConstants.ICON_PATH_QUESTION_24);
            showHelp.setMnemonic('H');
            showHelp.setToolTipText("�L�ڗ��\�����܂��B");
            showHelp.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    showHelpWindow();
                }});
        }
        return showHelp;
    }

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoIshiIkenshoInfo() {
        super();
        // �X�e�[�^�X�o�[
        setStatusText("��t�ӌ���");
        buttons.setText("��t�ӌ���");
        // �q���g�{�^���Ή�
        if(mention instanceof IkenshoIshiIkenshoInfoSpecialMention2){
            ((IkenshoIshiIkenshoInfoSpecialMention2) mention)
                    .setFollowDisabledComponents(new JComponent[] { tabs,
                            update,
                print, buttons.getBackButton() });
        }
        
        buttons.add(getShowHelp(), VRLayout.EAST);
    }
    /**
     * �L�ڗ�ւ̃A�N�Z�X��ʂ�\�����܂��B
     */
    protected void showHelpWindow(){
        new IkenshoIshiShowHelp().setVisible(true);
    }
    
    protected void addTabs(){
        // �V�K
        applicant = new IkenshoIshiIkenshoInfoApplicant();
        // �V�K
        sick = new IkenshoIshiIkenshoInfoSick1();
        // �V�K
        ishiSick2 = new IkenshoIshiIkenshoInfoSick2();
        // �V�K
        special = new IkenshoIshiIkenshoInfoSpecial();
        // �V�K
        care1 = new IkenshoIshiIkenshoInfoCare1();
        // �V�K
        care2 = new IkenshoIshiIkenshoInfoCare2();
        // �V�K
        ishiMindBody1 = new IkenshoIshiIkenshoInfoMindBody1();
        // �V�K
        mindBody2 = new IkenshoIshiIkenshoInfoMindBody2();
        // �V�K
        ishiMindBody3 = new IkenshoIshiIkenshoInfoMindBody3();
        // �V�K
        ishiSpesicalMention1 = new IkenshoIshiIkenshoInfoSpecialMention1();
        // �V�K
        mention = new IkenshoIshiIkenshoInfoSpecialMention2();
        organ = new IkenshoIkenshoInfoOrgan();
        bill = new IkenshoIkenshoInfoBill();

        // Add
        tabs.addTab("�\����", applicant);
        tabs.addTab("���a�P", sick);
        tabs.addTab("���a�Q", ishiSick2);
        tabs.addTab("���ʂȈ��", special);
        tabs.addTab("�S�g�̏�ԂP", ishiMindBody1);
        tabs.addTab("�S�g�̏�ԂQ", mindBody2);
        tabs.addTab("�S�g�̏�ԂR",ishiMindBody3);
        tabs.addTab("�����@�\�P", care1);
        tabs.addTab("�����@�\�Q", care2);
        tabs.addTab("���L�����P",ishiSpesicalMention1);
        tabs.addTab("���L�����Q�E����", mention);
        tabs.addTab("��Ë@��", organ);
        tabPanel.add(bill, BorderLayout.SOUTH);

        tabArray.clear();
        tabArray.add(applicant);
        tabArray.add(sick);
        tabArray.add(ishiSick2);
        tabArray.add(special);
        tabArray.add(ishiMindBody1);
        tabArray.add(mindBody2);
        tabArray.add(ishiMindBody3);
        tabArray.add(care1);
        tabArray.add(care2);
        tabArray.add(ishiSpesicalMention1);
        tabArray.add(mention);
        tabArray.add(organ);
        tabArray.add(bill);

        
    }
    
    /**
     * ��Ë@�փ^�u��Ԃ��܂��B
     * 
     * @return ��Ë@�փ^�u
     */
    public IkenshoIkenshoInfoOrgan getOrgan() {
        return organ;
    }

    /**
     * ���L�����^�u��Ԃ��܂��B
     * 
     * @return ���L�����^�u
     */
    public IkenshoIkenshoInfoMention getMention() {
        return mention;
    }

    /**
     * �������^�u��Ԃ��܂��B
     * 
     * @return �������^�u
     */
    public IkenshoIkenshoInfoBill getBill() {
        return bill;
    }

    /**
     * �\���҃^�u��Ԃ��܂��B
     * 
     * @return �\���҃^�u
     */
    public IkenshoIkenshoInfoApplicant getApplicant() {
        return applicant;
    }

    /**
     * CSV�t�@�C���̏o�͑Ώۋ敪��Ԃ��܂��B
     * 
     * @return CSV�t�@�C���̏o�͑Ώۋ敪
     */
    public Integer getBillFDOutputKubun() {
        return billFDOutputKubun;
    }

    /**
     * CSV�t�@�C���̏o�͑Ώۋ敪��ݒ肵�܂��B
     * 
     * @param billFDOutputKubun CSV�t�@�C���̏o�͑Ώۋ敪
     */
    public void setBillFDOutputKubun(Integer billFDOutputKubun) {
        this.billFDOutputKubun = billFDOutputKubun;
    }

    /**
     * CSV�t�@�C���̏o�͓�����ݒ肵�܂��B
     * 
     * @param billFDOutputTime CSV�t�@�C���̏o�͓���
     */
    public void setBillFDOutputTime(Date billFDOutputTime) {
        this.billFDOutputTime = billFDOutputTime;
    }

    
    
    /**
     * ���
     */
    protected boolean showPrintSetting(IkenshoIkenshoInfoPrintParameter param) {
        IkenshoIkenshoPrintSettingIshi i = new IkenshoIkenshoPrintSettingIshi(
                originalData, mindBody2.getPicture());
        return i.showModal(param);
    }
    
    /**
     * ��t�ӌ�������ǉ����܂��B
     * 
     * @param dbm DBManager
     * @throws ParseException ��͗�O
     * @throws SQLException SQL��O
     */
    protected void doInsertIkensho(IkenshoFirebirdDBManager dbm)
            throws ParseException, SQLException {
        
        // ��t�ӌ���
        StringBuffer sb = new StringBuffer();
        
        sb.append("INSERT INTO");
        sb.append(" IKN_ORIGIN");
        sb.append(" (");
        // --------------------------------------
        // �\���҃^�u�̏��
        // --------------------------------------
        sb.append(" PATIENT_NO");
        sb.append(" ,EDA_NO");
        sb.append(" ,FORMAT_KBN");
        sb.append(" ,KINYU_DT");
        sb.append(" ,DR_CONSENT");
        sb.append(" ,IKN_CREATE_CNT");
        sb.append(" ,LASTDAY");
        sb.append(" ,TAKA");
        sb.append(" ,TAKA_OTHER");
        // --------------------------------------
        // ���a�P�^�u
        // --------------------------------------
        sb.append(" ,SHUSSEI1");
        sb.append(" ,SHUSSEI2");
        sb.append(" ,SHUSSEI3");
        sb.append(" ,NYUIN_DT_STA1");
        sb.append(" ,NYUIN_DT_END1");
        sb.append(" ,NYUIN_NM1");
        sb.append(" ,NYUIN_DT_STA2");
        sb.append(" ,NYUIN_DT_END2");
        sb.append(" ,NYUIN_NM2");
        sb.append(" ,INSECURE_CONDITION");
        // --------------------------------------
        // ���ʂȈ�Ã^�u
        // --------------------------------------       
        sb.append(" ,KYUIN_SHOCHI");
        sb.append(" ,KYUIN_SHOCHI_CNT");
        sb.append(" ,KYUIN_SHOCHI_JIKI");
        // --------------------------------------
        // �S�g�̏��
        // --------------------------------------       
        sb.append(" ,KS_CHUYA");
        sb.append(" ,KS_BOUGEN");
        sb.append(" ,KS_BOUKOU");
        sb.append(" ,KS_TEIKOU");
        sb.append(" ,KS_HAIKAI");
        sb.append(" ,KS_FUSIMATU");
        sb.append(" ,KS_FUKETU");
        sb.append(" ,KS_ISHOKU");
        sb.append(" ,KS_SEITEKI_MONDAI");
        sb.append(" ,KS_OTHER");
        sb.append(" ,KS_OTHER_NM");
        sb.append(" ,SEISIN");
        sb.append(" ,SEISIN_NM");
        sb.append(" ,SS_SENMO");
        sb.append(" ,SS_KEMIN_KEIKO");
        sb.append(" ,SS_GNS_GNC");
        sb.append(" ,SS_MOUSOU");
        sb.append(" ,SS_SHIKKEN_TOSHIKI");
        sb.append(" ,SS_SHITUNIN");
        sb.append(" ,SS_SHIKKO");
        sb.append(" ,SS_NINCHI_SHOGAI");
        sb.append(" ,SS_KIOKU_SHOGAI");
        sb.append(" ,SS_CHUI_SHOGAI");
        sb.append(" ,SS_SUIKOU_KINO_SHOGAI");
        sb.append(" ,SS_SHAKAITEKI_KODO_SHOGAI");
        sb.append(" ,SS_OTHER");
        sb.append(" ,SS_KIOKU_SHOGAI_TANKI");
        sb.append(" ,SS_KIOKU_SHOGAI_CHOUKI");
        sb.append(" ,SS_OTHER_NM");
        sb.append(" ,SENMONI");
        sb.append(" ,SENMONI_NM");
        sb.append(" ,TENKAN");
        sb.append(" ,TENKAN_HINDO");
        sb.append(" ,KIKIUDE");
        sb.append(" ,HEIGHT");
        sb.append(" ,WEIGHT");
        sb.append(" ,WEIGHT_CHANGE");
        sb.append(" ,SISIKESSON");
        sb.append(" ,SISIKESSON_BUI");
        sb.append(" ,SISIKESSON_TEIDO");
        // sb.append(" ,MAHI");
        sb.append(" ,MAHI_LEFTARM");
        sb.append(" ,MAHI_RIGHTARM");
        sb.append(" ,MAHI_LOWERLEFTLIMB");
        sb.append(" ,MAHI_LOWERRIGHTLIMB");
        sb.append(" ,MAHI_ETC");
        sb.append(" ,MAHI_LEFTARM_TEIDO");
        sb.append(" ,MAHI_LOWERLEFTLIMB_TEIDO");
        sb.append(" ,MAHI_RIGHTARM_TEIDO");
        sb.append(" ,MAHI_LOWERRIGHTLIMB_TEIDO");
        sb.append(" ,MAHI_ETC_BUI");
        sb.append(" ,MAHI_ETC_TEIDO");
        sb.append(" ,KINRYOKU_TEIKA");
        sb.append(" ,KINRYOKU_TEIKA_BUI");
        sb.append(" ,KINRYOKU_TEIKA_TEIDO");
        sb.append(" ,KOUSHU");
        sb.append(" ,KATA_KOUSHU");
        sb.append(" ,MATA_KOUSHU");
        sb.append(" ,HIJI_KOUSHU");
        sb.append(" ,HIZA_KOUSHU");
        sb.append(" ,KATA_KOUSHU_MIGI");
        sb.append(" ,KATA_KOUSHU_HIDARI");
        sb.append(" ,MATA_KOUSHU_MIGI");
        sb.append(" ,MATA_KOUSHU_HIDARI");
        sb.append(" ,HIJI_KOUSHU_MIGI");
        sb.append(" ,HIJI_KOUSHU_HIDARI");
        sb.append(" ,HIZA_KOUSHU_MIGI");
        sb.append(" ,HIZA_KOUSHU_HIDARI");
        sb.append(" ,KATA_KOUSHU_MIGI_TEIDO");
        sb.append(" ,KATA_KOUSHU_HIDARI_TEIDO");
        sb.append(" ,MATA_KOUSHU_MIGI_TEIDO");
        sb.append(" ,MATA_KOUSHU_HIDARI_TEIDO");
        sb.append(" ,HIJI_KOUSHU_MIGI_TEIDO");
        sb.append(" ,HIJI_KOUSHU_HIDARI_TEIDO");
        sb.append(" ,HIZA_KOUSHU_MIGI_TEIDO");
        sb.append(" ,HIZA_KOUSHU_HIDARI_TEIDO");
        sb.append(" ,KOUSHU_ETC");
        sb.append(" ,KOUSHU_ETC_BUI");
        sb.append(" ,KANSETU_ITAMI");
        sb.append(" ,KANSETU_ITAMI_BUI");
        sb.append(" ,KANSETU_ITAMI_TEIDO");
        sb.append(" ,JOUSI_SICCHOU_MIGI");
        sb.append(" ,JOUSI_SICCHOU_HIDARI");
        sb.append(" ,TAIKAN_SICCHOU_MIGI");
        sb.append(" ,TAIKAN_SICCHOU_HIDARI");
        sb.append(" ,KASI_SICCHOU_MIGI");
        sb.append(" ,KASI_SICCHOU_HIDARI");
        sb.append(" ,JOUSI_SICCHOU_MIGI_TEIDO");
        sb.append(" ,JOUSI_SICCHOU_HIDARI_TEIDO");
        sb.append(" ,TAIKAN_SICCHOU_MIGI_TEIDO");
        sb.append(" ,TAIKAN_SICCHOU_HIDARI_TEIDO");
        sb.append(" ,KASI_SICCHOU_MIGI_TEIDO");
        sb.append(" ,KASI_SICCHOU_HIDARI_TEIDO");
        sb.append(" ,JOKUSOU");
        sb.append(" ,JOKUSOU_BUI");
        sb.append(" ,JOKUSOU_TEIDO");
        sb.append(" ,HIFUSIKKAN");
        sb.append(" ,HIFUSIKKAN_BUI");
        sb.append(" ,HIFUSIKKAN_TEIDO");
        // --------------------------------------
        // �����@�\
        // -------------------------------------- 
        sb.append(" ,NYOUSIKKIN");
        sb.append(" ,NYOUSIKKIN_TAISHO_HOUSIN");
        sb.append(" ,TENTOU_KOSSETU");
        sb.append(" ,TENTOU_KOSSETU_TAISHO_HOUSIN");
        sb.append(" ,HAIKAI_KANOUSEI");
        sb.append(" ,HAIKAI_KANOUSEI_TAISHO_HOUSIN");
        sb.append(" ,JOKUSOU_KANOUSEI");
        sb.append(" ,JOKUSOU_KANOUSEI_TAISHO_HOUSIN");
        sb.append(" ,ENGESEIHAIEN");
        sb.append(" ,ENGESEIHAIEN_TAISHO_HOUSIN");
        sb.append(" ,CHOUHEISOKU");
        sb.append(" ,CHOUHEISOKU_TAISHO_HOUSIN");
        sb.append(" ,EKIKANKANSEN");
        sb.append(" ,EKIKANKANSEN_TAISHO_HOUSIN");
        sb.append(" ,SINPAIKINOUTEIKA");
        sb.append(" ,SINPAIKINOUTEIKA_TAISHO_HOUSIN");
        sb.append(" ,ITAMI");
        sb.append(" ,ITAMI_TAISHO_HOUSIN");
        sb.append(" ,DASSUI");
        sb.append(" ,DASSUI_TAISHO_HOUSIN");
        sb.append(" ,BYOUTAITA");
        sb.append(" ,BYOUTAITA_NM");
        sb.append(" ,BYOUTAITA_TAISHO_HOUSIN");
        sb.append(" ,KETUATU");
        sb.append(" ,KETUATU_RYUIJIKOU");
        sb.append(" ,SESHOKU");
        sb.append(" ,SESHOKU_RYUIJIKOU");
        sb.append(" ,ENGE");
        sb.append(" ,ENGE_RYUIJIKOU");
        sb.append(" ,IDOU");
        sb.append(" ,IDOU_RYUIJIKOU");
        sb.append(" ,KAIGO_OTHER");
        sb.append(" ,KANSENSHOU");
        sb.append(" ,KANSENSHOU_NM");
        // --------------------------------------
        // ���̑����L���ׂ�����
        // --------------------------------------
        sb.append(" ,IKN_TOKKI");
        sb.append(" ,SK_NIJIKU_SEISHIN");
        sb.append(" ,SK_NIJIKU_NORYOKU");
        sb.append(" ,SK_NIJIKU_DT");
        sb.append(" ,SK_SEIKATSU_SHOKUJI");
        sb.append(" ,SK_SEIKATSU_RHYTHM");
        sb.append(" ,SK_SEIKATSU_HOSEI");
        sb.append(" ,SK_SEIKATSU_KINSEN_KANRI");
        sb.append(" ,SK_SEIKATSU_HUKUYAKU_KANRI");
        sb.append(" ,SK_SEIKATSU_TAIJIN_KANKEI");
        sb.append(" ,SK_SEIKATSU_SHAKAI_TEKIOU");
        sb.append(" ,SK_SEIKATSU_DT");
        // --------------------------------------
        
        sb.append(" ,INSURED_NO");
        sb.append(" ,REQ_DT");
        sb.append(" ,REQ_NO");
        sb.append(" ,SEND_DT");
        sb.append(" ,KIND");
        sb.append(" ,INSURER_NO");
        sb.append(" ,INSURER_NM");
        sb.append(" ,INSURER_TYPE");
        sb.append(" ,CREATE_DT");
        sb.append(" ,KOUSIN_DT");
        sb.append(" ,LAST_TIME");

        
        appendInsertIkenshoKeys(sb);
        
        sb.append(" )");
        sb.append(" VALUES");
        // =============================================================
        // �ǉ�����l
        // =============================================================
        sb.append(" (");
        sb.append(getPatientNo());
        sb.append(",");
        sb.append(getEdaNo());
        sb.append(",");
        sb.append(getFormatKubun());
        sb.append(",");
        sb.append(getDBSafeDate("KINYU_DT", originalData));
        sb.append(",");
        sb.append(getDBSafeString("DR_CONSENT",originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("IKN_CREATE_CNT", originalData));
        sb.append(",");
        sb.append(getDBSafeDate("LASTDAY", originalData));
        if (((Integer) VRBindPathParser.get("TAKA_FLAG", originalData))
                .intValue() == 1) {
            sb.append(",");
            sb.append(getDBSafeNumber("TAKA", originalData));
            IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                    "TAKA_OTHER_FLAG", new String[] { "TAKA_OTHER" }, false);
            sb.append(",");
        } else {
            // �ꗥ�����l
            sb.append(",0");
            sb.append(",''");
            sb.append(",");
        }
        sb.append(getDBSafeNumber("SHUSSEI1",originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SHUSSEI2",originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SHUSSEI3",originalData));
        sb.append(",");
        sb.append(getDBSafeString("NYUIN_DT_STA1",originalData));
        sb.append(",");
        sb.append(getDBSafeString("NYUIN_DT_END1",originalData));
        sb.append(",");
        sb.append(getDBSafeString("NYUIN_NM1",originalData));
        sb.append(",");
        sb.append(getDBSafeString("NYUIN_DT_STA2",originalData));
        sb.append(",");
        sb.append(getDBSafeString("NYUIN_DT_END2",originalData));
        sb.append(",");
        sb.append(getDBSafeString("NYUIN_NM2",originalData));
        sb.append(",");
        sb.append(getDBSafeString("INSECURE_CONDITION",originalData));
        // �z�����u
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "KYUIN_SHOCHI", new String[] { "KYUIN_SHOCHI_CNT" },
                new String[] { "KYUIN_SHOCHI_JIKI" }, true, true);
        
        IkenshoCommon.addFollowCheckNumberInsert(sb, originalData,
                "MONDAI_FLAG", new String[] { "KS_CHUYA", "KS_BOUGEN", "KS_BOUKOU",
                        "KS_TEIKOU", "KS_HAIKAI", "KS_FUSIMATU", "KS_FUKETU", "KS_ISHOKU",
                        "KS_SEITEKI_MONDAI"}, false);
        
        if (((Integer) VRBindPathParser.get("MONDAI_FLAG", originalData))
                .intValue() == 1) {
            IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                    "KS_OTHER", new String[] { "KS_OTHER_NM" }, true);
        } else {
            // �ꗥ�����l
            sb.append(",0");
            sb.append(",''");
        }
        // ���_��Q
        // ���_ - ���̑��̂݌ʏo��
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "SEISIN",
                new String[] { "SEISIN_NM" }, true);
        
        IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "SEISIN",
                new String[] { "SS_SENMO", "SS_KEMIN_KEIKO",
                        "SS_GNS_GNC", "SS_MOUSOU", "SS_SHIKKEN_TOSHIKI",
                        "SS_SHITUNIN", "SS_SHIKKO", "SS_NINCHI_SHOGAI",
                        "SS_KIOKU_SHOGAI", "SS_CHUI_SHOGAI",
                        "SS_SUIKOU_KINO_SHOGAI", "SS_SHAKAITEKI_KODO_SHOGAI",
                        "SS_OTHER" }, false);
        // �L����Q
        if (new Integer(1).equals(originalData.getData("SEISIN"))
                && new Integer(1).equals(originalData
                        .getData("SS_KIOKU_SHOGAI"))) {
            sb.append(" ,");
            sb.append(getDBSafeNumberNullToZero("SS_KIOKU_SHOGAI_TANKI",
                    originalData));
            sb.append(" ,");
            sb.append(getDBSafeNumberNullToZero("SS_KIOKU_SHOGAI_CHOUKI",
                    originalData));
        } else {
            sb.append(",0");
            sb.append(",0");
        }
        // ���̑�
        if (new Integer(1).equals(originalData.getData("SEISIN"))
                && new Integer(1).equals(originalData.getData("SS_OTHER"))) {
            sb.append(",");
            sb.append(getDBSafeString("SS_OTHER_NM",originalData));
        }else{
            sb.append(",''");
        }
        // �����f�̗L��
        // ���_�E�_�o�Ǐ�L�肾������INSERT����
        if (new Integer(1).equals(originalData.getData("SEISIN"))){
            IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "SENMONI",
                    new String[] { "SENMONI_NM" }, true);
        }else{
            sb.append(",2");
            sb.append(",''");
        }
        // �Ă񂩂�p�x
        IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "TENKAN",
                new String[] { "TENKAN_HINDO" }, true);
        sb.append(",");
        sb.append(getDBSafeNumber("KIKIUDE", originalData));
        sb.append(",");
        sb.append(getDBSafeString("HEIGHT", originalData));
        sb.append(",");
        sb.append(getDBSafeString("WEIGHT", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("WEIGHT_CHANGE",originalData));
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "SISIKESSON",
                new String[] { "SISIKESSON_BUI" },
                new String[] { "SISIKESSON_TEIDO" }, true, true);
        // ��Ⴣ`�F�b�N
        IkenshoCommon
                .addFollowCheckNumberInsert(sb, originalData, "MAHI_FLAG",
                        new String[] { "MAHI_LEFTARM", "MAHI_RIGHTARM",
                                "MAHI_LOWERLEFTLIMB", "MAHI_LOWERRIGHTLIMB",
                                "MAHI_ETC" }, false);
        // �e���x�ɂ��Ă��`�F�b�N����
        IkenshoCommon
        .addFollowCheckNumberInsert(sb, originalData, "MAHI_LEFTARM",
                new String[] { "MAHI_LEFTARM_TEIDO" }, false);
        IkenshoCommon
        .addFollowCheckNumberInsert(sb, originalData, "MAHI_LOWERLEFTLIMB",
                new String[] { "MAHI_LOWERLEFTLIMB_TEIDO" }, false);
        IkenshoCommon
        .addFollowCheckNumberInsert(sb, originalData, "MAHI_RIGHTARM",
                new String[] { "MAHI_RIGHTARM_TEIDO" }, false);
        IkenshoCommon
        .addFollowCheckNumberInsert(sb, originalData, "MAHI_LOWERRIGHTLIMB",
                new String[] { "MAHI_LOWERRIGHTLIMB_TEIDO" }, false);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "MAHI_ETC", new String[] { "MAHI_ETC_BUI" },
                new String[] { "MAHI_ETC_TEIDO" }, true, false);
        // �ؗ͒ቺ
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "KINRYOKU_TEIKA", new String[] { "KINRYOKU_TEIKA_BUI" },
                new String[] { "KINRYOKU_TEIKA_TEIDO" }, true, true);
        
        // �֐߂̍S�k
        IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "KOUSHU",
                new String[] { "KATA_KOUSHU", "MATA_KOUSHU", "HIJI_KOUSHU",
                        "HIZA_KOUSHU" }, true);
        
        // �֐߂̍��E�ɂ��Čʏ���
        if(new Integer(1).equals(originalData.getData("KOUSHU"))){
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "KATA_KOUSHU",
                    new String[] { "KATA_KOUSHU_MIGI", "KATA_KOUSHU_HIDARI" }, false);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "MATA_KOUSHU",
                    new String[] { "MATA_KOUSHU_MIGI", "MATA_KOUSHU_HIDARI" }, false);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "HIJI_KOUSHU",
                    new String[] { "HIJI_KOUSHU_MIGI", "HIJI_KOUSHU_HIDARI" }, false);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "HIZA_KOUSHU",
                    new String[] { "HIZA_KOUSHU_MIGI", "HIZA_KOUSHU_HIDARI" }, false);
        }else{
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
        }
        // �֐ߍ��E�����ɂ��Ē��x�o��
        // ��
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("KATA_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData,
                    "KATA_KOUSHU_MIGI",
                    new String[] { "KATA_KOUSHU_MIGI_TEIDO" }, false);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData,
                    "KATA_KOUSHU_HIDARI",
                    new String[] { "KATA_KOUSHU_HIDARI_TEIDO", }, false);
        }else{
            sb.append(",0");
            sb.append(",0");
        }
        // ��
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("MATA_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "MATA_KOUSHU_MIGI",
                    new String[] { "MATA_KOUSHU_MIGI_TEIDO"}, false);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "MATA_KOUSHU_HIDARI",
                    new String[] { "MATA_KOUSHU_HIDARI_TEIDO"}, false);
        }else{
            sb.append(",0");
            sb.append(",0");
        }
        // �I
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("HIJI_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "HIJI_KOUSHU_MIGI",
                    new String[] { "HIJI_KOUSHU_MIGI_TEIDO"}, false);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "HIJI_KOUSHU_HIDARI",
                    new String[] { "HIJI_KOUSHU_HIDARI_TEIDO"}, false);
        }else{
            sb.append(",0");
            sb.append(",0");
        }
        // �G
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("HIZA_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "HIZA_KOUSHU_MIGI",
                    new String[] { "HIZA_KOUSHU_MIGI_TEIDO"}, false);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "HIZA_KOUSHU_HIDARI",
                    new String[] { "HIZA_KOUSHU_HIDARI_TEIDO"}, false);
        }else{
            sb.append(",0");
            sb.append(",0");
        }
        // ���̑�����
        if (new Integer(1).equals(originalData.getData("KOUSHU"))){
            IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                    "KOUSHU_ETC", new String[] { "KOUSHU_ETC_BUI" },true);
        }else{
            sb.append(",0");
            sb.append(",''");
        }
        
        // �֐߂̒ɂ�
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "KANSETU_ITAMI",
                new String[] { "KANSETU_ITAMI_BUI" },
                new String[] { "KANSETU_ITAMI_TEIDO" }, true, true);
        
        // �����E�s���Ӊ^��
        if (((Integer) VRBindPathParser.get("SICCHOU_FLAG", originalData))
                .intValue() == 1) {
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData,
                    "SICCHOU_FLAG", new String[] { "JOUSI_SICCHOU_MIGI",
                            "JOUSI_SICCHOU_HIDARI", "TAIKAN_SICCHOU_MIGI",
                            "TAIKAN_SICCHOU_HIDARI", "KASI_SICCHOU_MIGI",
                            "KASI_SICCHOU_HIDARI" }, false);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "JOUSI_SICCHOU_MIGI",
                    new String[] { "JOUSI_SICCHOU_MIGI_TEIDO"}, false);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "JOUSI_SICCHOU_HIDARI",
                    new String[] { "JOUSI_SICCHOU_HIDARI_TEIDO",}, false);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "TAIKAN_SICCHOU_MIGI",
                    new String[] { "TAIKAN_SICCHOU_MIGI_TEIDO"}, false);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "TAIKAN_SICCHOU_HIDARI",
                    new String[] { "TAIKAN_SICCHOU_HIDARI_TEIDO"}, false);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "KASI_SICCHOU_MIGI",
                    new String[] { "KASI_SICCHOU_MIGI_TEIDO"}, false);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "KASI_SICCHOU_HIDARI",
                    new String[] { "KASI_SICCHOU_HIDARI_TEIDO"}, false);
        }else{
            // �����Ɋ֌W���鍀�ڂ͑S��0�Ƃ݂Ȃ�
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
        }
        
        // ���
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "JOKUSOU",
                new String[] { "JOKUSOU_BUI" },
                new String[] { "JOKUSOU_TEIDO" }, true, true);
        
        // ���̑��̔畆����
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "HIFUSIKKAN",
                new String[] { "HIFUSIKKAN_BUI" },
                new String[] { "HIFUSIKKAN_TEIDO" }, true, true);
        
        // �A����
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "NYOUSIKKIN",
                new String[] { "NYOUSIKKIN_TAISHO_HOUSIN" }, true);
        // �]�|�E����
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "TENTOU_KOSSETU",
                new String[] { "TENTOU_KOSSETU_TAISHO_HOUSIN" }, true);
        // �p�j
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "HAIKAI_KANOUSEI",
                new String[] { "HAIKAI_KANOUSEI_TAISHO_HOUSIN" }, true);
        // ���
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "JOKUSOU_KANOUSEI",
                new String[] { "JOKUSOU_KANOUSEI_TAISHO_HOUSIN" }, true);
        // �������x��
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "ENGESEIHAIEN", new String[] { "ENGESEIHAIEN_TAISHO_HOUSIN" },
                true);
        // ����
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "CHOUHEISOKU",
                new String[] { "CHOUHEISOKU_TAISHO_HOUSIN" }, true);
        // �Պ�����
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "EKIKANKANSEN", new String[] { "EKIKANKANSEN_TAISHO_HOUSIN" },
                true);
        // �S�z�@�\�ቺ
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "SINPAIKINOUTEIKA",
                new String[] { "SINPAIKINOUTEIKA_TAISHO_HOUSIN" }, true);
        // �ɂݑΏ����j
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "ITAMI",
                new String[] { "ITAMI_TAISHO_HOUSIN" }, true);
        // �E���Ώ����j
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "DASSUI",
                new String[] { "DASSUI_TAISHO_HOUSIN" }, true);
        // �a�ԑ�
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "BYOUTAITA",
                new String[] { "BYOUTAITA_NM", "BYOUTAITA_TAISHO_HOUSIN" },
                true);
        // ����
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "KETUATU",
                new String[] { "KETUATU_RYUIJIKOU" }, 2, true);
        
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "SESHOKU",
                new String[] { "SESHOKU_RYUIJIKOU" }, 2, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "ENGE",
                new String[] { "ENGE_RYUIJIKOU" }, 2, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "IDOU",
                new String[] { "IDOU_RYUIJIKOU" }, 2, true);
        sb.append(",");
        sb.append(getDBSafeString("KAIGO_OTHER", originalData));
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "KANSENSHOU",
                new String[] { "KANSENSHOU_NM" }, true);
        sb.append(",");
        sb.append(getDBSafeString("IKN_TOKKI",originalData));
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("SK_NIJIKU_SEISHIN",originalData));
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("SK_NIJIKU_NORYOKU",originalData));
        sb.append(",");
        sb.append(getDBSafeString("SK_NIJIKU_DT",originalData));
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("SK_SEIKATSU_SHOKUJI",originalData));
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("SK_SEIKATSU_RHYTHM",originalData));
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("SK_SEIKATSU_HOSEI",originalData));
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("SK_SEIKATSU_KINSEN_KANRI",originalData));
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("SK_SEIKATSU_HUKUYAKU_KANRI",originalData));
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("SK_SEIKATSU_TAIJIN_KANKEI",originalData));
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("SK_SEIKATSU_SHAKAI_TEKIOU",originalData));
        sb.append(",");
        sb.append(getDBSafeString("SK_SEIKATSU_DT",originalData));
        
        sb.append(",");
        sb.append(getDBSafeString("INSURED_NO", originalData));
        sb.append(",");
        sb.append(getDBSafeDate("REQ_DT", originalData));
        sb.append(",");
        sb.append(getDBSafeString("REQ_NO", originalData));
        sb.append(",");
        sb.append(getDBSafeDate("SEND_DT", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("KIND", originalData));
        sb.append(",");
        sb.append(getDBSafeString("INSURER_NO", originalData));
        sb.append(",");
        sb.append(getDBSafeString("INSURER_NM", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("INSURER_TYPE", originalData));
        sb.append(",CURRENT_TIMESTAMP");
        sb.append(",CURRENT_TIMESTAMP");
        sb.append(",CURRENT_TIMESTAMP");
        
        appendInsertIkenshoValues(sb);

        sb.append(")");
        
        dbm.executeUpdate(sb.toString());

        doUpdateHumanPicture(dbm);

        hasOriginalDocument = true;
        
    }

    /**
     * override���Ĉӌ����}������SQL�o�����[���ǉ����܂��B
     * 
     * @throws ParseException ��͗�O
     * @param sb �ǉ���
     */
    protected void appendInsertIkenshoValues(StringBuffer sb)
    throws ParseException {
        
    }
    
    
    /**
     * override���Ĉӌ����}������SQL�L�[���ǉ����܂��B
     * 
     * @param sb �ǉ���
     */
    protected void appendInsertIkenshoKeys(StringBuffer sb) {
        
    }
    
    
    /**
     * override���Ĉӌ����X�V����SQL�L�[���ǉ����܂��B
     * 
     * @param sb �ǉ���
     * @throws ParseException ��͗�O
     */
    protected void appendUpdateIkenshoStetement(StringBuffer sb)
            throws ParseException {
        super.appendUpdateIkenshoStetement(sb);
        
        sb.append(",NYUIN_DT_STA1 = ");
        sb.append(getDBSafeString("NYUIN_DT_STA1",originalData));
        sb.append(",NYUIN_DT_END1 = ");
        sb.append(getDBSafeString("NYUIN_DT_END1",originalData));
        sb.append(",NYUIN_NM1 = ");
        sb.append(getDBSafeString("NYUIN_NM1",originalData));
        sb.append(",NYUIN_DT_STA2 = ");
        sb.append(getDBSafeString("NYUIN_DT_STA2",originalData));
        sb.append(",NYUIN_DT_END2 = ");
        sb.append(getDBSafeString("NYUIN_DT_END2",originalData));
        sb.append(",NYUIN_NM2 = ");
        sb.append(getDBSafeString("NYUIN_NM2",originalData));   
        sb.append(",KYUIN_SHOCHI = ");
        sb.append(getDBSafeNumber("KYUIN_SHOCHI",originalData));
        sb.append(",KYUIN_SHOCHI_CNT = ");
        sb.append(getDBSafeString("KYUIN_SHOCHI_CNT",originalData));
        sb.append(",KYUIN_SHOCHI_JIKI = ");
        sb.append(getDBSafeNumber("KYUIN_SHOCHI_JIKI",originalData));
        // �s����̏�Q
        IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                "MONDAI_FLAG", new String[] { "KS_CHUYA", "KS_BOUGEN",
                        "KS_BOUKOU", "KS_TEIKOU", "KS_HAIKAI", "KS_FUSIMATU",
                        "KS_FUKETU", "KS_ISHOKU", "KS_SEITEKI_MONDAI","KS_OTHER" }, false);
        // �s����̏�Q�E���̑�
        if (new Integer(1).equals(originalData.getData("MONDAI_FLAG"))
                && new Integer(1).equals(originalData.getData("KS_OTHER"))) {
            sb.append(" ,KS_OTHER_NM = ");
            sb.append(getDBSafeString("KS_OTHER_NM",originalData));
        }else{
            sb.append(" ,KS_OTHER_NM = ''");
        }
        // ���_�`�F�b�N�{�b�N�X�S��A������
        IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData, "SEISIN",
                new String[] { "SS_SENMO", "SS_KEMIN_KEIKO", "SS_GNS_GNC",
                        "SS_MOUSOU", "SS_SHIKKEN_TOSHIKI", "SS_SHITUNIN",
                        "SS_SHIKKO", "SS_NINCHI_SHOGAI", "SS_KIOKU_SHOGAI",
                        "SS_CHUI_SHOGAI", "SS_SUIKOU_KINO_SHOGAI",
                        "SS_SHAKAITEKI_KODO_SHOGAI", "SS_OTHER" }, false);
        // �L����Q�`�F�b�N
        if (new Integer(1).equals(originalData.getData("SEISIN"))
                && new Integer(1).equals(originalData.getData("SS_KIOKU_SHOGAI"))) {
            sb.append(" ,SS_KIOKU_SHOGAI_TANKI = ");
            sb.append(getDBSafeNumberNullToZero("SS_KIOKU_SHOGAI_TANKI",originalData));
            sb.append(" ,SS_KIOKU_SHOGAI_CHOUKI = ");
            sb.append(getDBSafeNumberNullToZero("SS_KIOKU_SHOGAI_CHOUKI",originalData));
        }else{
            sb.append(" ,SS_KIOKU_SHOGAI_TANKI = 0");
            sb.append(" ,SS_KIOKU_SHOGAI_CHOUKI = 0");            
        }
        // ���̑��͌ʏ���
        if (new Integer(1).equals(originalData.getData("SEISIN"))
                && new Integer(1).equals(originalData.getData("SS_OTHER"))) {
            sb.append(" ,SS_OTHER_NM = ");
            sb.append(getDBSafeString("SS_OTHER_NM",originalData));
        }else{
            sb.append(" ,SS_OTHER_NM = ''");
        }
        // �Ă񂩂������
        IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData, "TENKAN",
                new String[] { "TENKAN_HINDO" }, true);
        // �֐ߊ֌W�͍��ڂ̊֌W���ς���Ă���̂�override�������\�b�h�ŕύX����
        try{
            appendDifferenceUpdateItem(sb);
        }catch(Exception e){}
        
        // �����E�s����
        if(new Integer(1).equals(originalData.getData("SICCHOU_FLAG"))){
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "JOUSI_SICCHOU_MIGI",
                    new String[] { "JOUSI_SICCHOU_MIGI_TEIDO" }, false);
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "JOUSI_SICCHOU_HIDARI",
                    new String[] { "JOUSI_SICCHOU_HIDARI_TEIDO" }, false);
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "KASI_SICCHOU_MIGI",
                    new String[] { "KASI_SICCHOU_MIGI_TEIDO" }, false);
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "KASI_SICCHOU_HIDARI",
                    new String[] { "KASI_SICCHOU_HIDARI_TEIDO" }, false);
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "TAIKAN_SICCHOU_MIGI",
                    new String[] { "TAIKAN_SICCHOU_MIGI_TEIDO" }, false);
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "TAIKAN_SICCHOU_HIDARI",
                    new String[] { "TAIKAN_SICCHOU_HIDARI_TEIDO" }, false);
        }else{
            sb.append(" ,JOUSI_SICCHOU_MIGI_TEIDO = 0");
            sb.append(" ,JOUSI_SICCHOU_HIDARI_TEIDO = 0");
            sb.append(" ,KASI_SICCHOU_MIGI_TEIDO = 0");
            sb.append(" ,KASI_SICCHOU_HIDARI_TEIDO = 0");
            sb.append(" ,TAIKAN_SICCHOU_MIGI_TEIDO = 0");
            sb.append(" ,TAIKAN_SICCHOU_HIDARI_TEIDO = 0");
        }
        sb.append(" ,SK_NIJIKU_SEISHIN = ");
        sb.append(getDBSafeNumberNullToZero("SK_NIJIKU_SEISHIN",originalData));
        
        sb.append(" ,SK_NIJIKU_NORYOKU = ");
        sb.append(getDBSafeNumberNullToZero("SK_NIJIKU_NORYOKU",originalData));
        
        sb.append(" ,SK_NIJIKU_DT = ");
        sb.append(getDBSafeString("SK_NIJIKU_DT",originalData));
        
        sb.append(" ,SK_SEIKATSU_SHOKUJI = ");
        sb.append(getDBSafeNumberNullToZero("SK_SEIKATSU_SHOKUJI",originalData));
        
        sb.append(" ,SK_SEIKATSU_RHYTHM = ");
        sb.append(getDBSafeNumberNullToZero("SK_SEIKATSU_RHYTHM",originalData));
        
        sb.append(" ,SK_SEIKATSU_HOSEI = ");
        sb.append(getDBSafeNumberNullToZero("SK_SEIKATSU_HOSEI",originalData));
        
        sb.append(" ,SK_SEIKATSU_KINSEN_KANRI = ");
        sb.append(getDBSafeNumberNullToZero("SK_SEIKATSU_KINSEN_KANRI",originalData));
        
        sb.append(" ,SK_SEIKATSU_HUKUYAKU_KANRI = ");
        sb.append(getDBSafeNumberNullToZero("SK_SEIKATSU_HUKUYAKU_KANRI",originalData));
        
        sb.append(" ,SK_SEIKATSU_TAIJIN_KANKEI = ");
        sb.append(getDBSafeNumberNullToZero("SK_SEIKATSU_TAIJIN_KANKEI",originalData));
        
        sb.append(" ,SK_SEIKATSU_SHAKAI_TEKIOU = ");
        sb.append(getDBSafeNumberNullToZero("SK_SEIKATSU_SHAKAI_TEKIOU",originalData));
        
        sb.append(" ,SK_SEIKATSU_DT = ");
        sb.append(getDBSafeString("SK_SEIKATSU_DT",originalData));
        // �o�����`�F�b�N�{�b�N�X
        sb.append(",SHUSSEI1 = ");
        sb.append(getDBSafeString("SHUSSEI1",originalData));
        sb.append(",SHUSSEI2 = ");
        sb.append(getDBSafeString("SHUSSEI2",originalData));
        sb.append(",SHUSSEI3 = ");
        sb.append(getDBSafeString("SHUSSEI3",originalData));
        // �����f�̂ݕύX
        try{
            doUpdateDifferenceItemSenmoni(sb);
        }catch (Exception e) { }
    }
    
    protected void doUpdateDifferenceItemSenmoni(StringBuffer sb)throws Exception{
        if(new Integer(1).equals(originalData.getData("SEISIN"))){
            IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "SENMONI",
                    new String[] { "SENMONI_NM" }, true);
        }else{
            sb.append(",SENMONI = 2");
            sb.append(",SENMONI_NM = ''");
        }
    }
    
    /**
     * ��t�ӌ����Ƃ̕ύX�_���`���܂��B 
     */
    protected void appendDifferenceUpdateItem(StringBuffer sb) throws Exception{
        IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                "KOUSHU", new String[] { "KATA_KOUSHU", "MATA_KOUSHU",
                        "HIJI_KOUSHU", "HIZA_KOUSHU","KOUSHU_ETC" }, true);
        // �e�֐߂̕��ʂɊւ��鏈��
        // ��
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("KATA_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "KATA_KOUSHU", new String[] { "KATA_KOUSHU_MIGI",
                            "KATA_KOUSHU_HIDARI" }, false);
        }else{
            sb.append(" ,KATA_KOUSHU_MIGI = 0");
            sb.append(" ,KATA_KOUSHU_HIDARI = 0");
        }
        // ��
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("MATA_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "MATA_KOUSHU", new String[] { "MATA_KOUSHU_MIGI",
                            "MATA_KOUSHU_HIDARI" }, false);
        }else{
            sb.append(" ,MATA_KOUSHU_MIGI = 0");
            sb.append(" ,MATA_KOUSHU_HIDARI = 0");
        }
        // �I
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("HIJI_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "HIJI_KOUSHU", new String[] { "HIJI_KOUSHU_MIGI",
                            "HIJI_KOUSHU_HIDARI" }, false);
        }else{
            sb.append(" ,HIJI_KOUSHU_MIGI = 0");
            sb.append(" ,HIJI_KOUSHU_HIDARI = 0");
        }
        // �G
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("HIZA_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "HIZA_KOUSHU", new String[] { "HIZA_KOUSHU_MIGI",
                            "HIZA_KOUSHU_HIDARI" }, false);
        }else{
            sb.append(" ,HIZA_KOUSHU_MIGI = 0");
            sb.append(" ,HIZA_KOUSHU_HIDARI = 0");
        }
        // ���̑�
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("KOUSHU_ETC"))) {
            IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                    "KOUSHU_ETC", new String[] { "KOUSHU_ETC_BUI"}, false);
        }else{
            sb.append(" ,KOUSHU_ETC_BUI = ''");
        }
        
        // ���x�̂ݕʓr�o�^
        // ��
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("KATA_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "KATA_KOUSHU_MIGI", new String[] { "KATA_KOUSHU_MIGI_TEIDO" },
                    false);
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "KATA_KOUSHU_HIDARI", new String[] { "KATA_KOUSHU_HIDARI_TEIDO" },
                    false);
        }else{
            sb.append(" ,KATA_KOUSHU_MIGI_TEIDO = 0");
            sb.append(" ,KATA_KOUSHU_HIDARI_TEIDO = 0");
        }
        // ��
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("MATA_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "MATA_KOUSHU_MIGI", new String[] { "MATA_KOUSHU_MIGI_TEIDO" },
                    false);
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "MATA_KOUSHU_HIDARI", new String[] { "MATA_KOUSHU_HIDARI_TEIDO" },
                    false);
        }else{
            sb.append(" ,MATA_KOUSHU_MIGI_TEIDO = 0");
            sb.append(" ,MATA_KOUSHU_HIDARI_TEIDO = 0");
        }
        // �I
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("HIJI_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "HIJI_KOUSHU_MIGI", new String[] { "HIJI_KOUSHU_MIGI_TEIDO" },
                    false);
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "HIJI_KOUSHU_HIDARI",
                    new String[] { "HIJI_KOUSHU_HIDARI_TEIDO" }, false);
        }else{
            sb.append(" ,HIJI_KOUSHU_MIGI_TEIDO = 0");
            sb.append(" ,HIJI_KOUSHU_HIDARI_TEIDO = 0");
        }
        // �G
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("HIZA_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "HIZA_KOUSHU_MIGI", new String[] { "HIZA_KOUSHU_MIGI_TEIDO" },
                    false);
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "HIZA_KOUSHU_HIDARI",
                    new String[] { "HIZA_KOUSHU_HIDARI_TEIDO" }, false);
        }else{
            sb.append(" ,HIZA_KOUSHU_MIGI_TEIDO = 0");
            sb.append(" ,HIZA_KOUSHU_HIDARI_TEIDO = 0");
        }
        
    }

    
    /**
     * ��t�ӌ�����\���敪
     */
    protected String getFormatKubun() {
        return "2";
    }
    
    public static void main(String[] args){

        //�f�t�H���g�f�o�b�O�N��
        try {
          ACFrame.getInstance().setFrameEventProcesser(new IkenshoFrameEventProcesser());

          VRMap param = new VRHashMap();
          
          param.setData("PATIENT_NO", new Integer(1));
          
          ACFrame.debugStart(new ACAffairInfo(IkenshoIshiIkenshoInfo.class.getName(), param));

//          IkenshoIkenshoInfo.goIkensho(IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO, param);

        } catch (Exception e) {
          e.printStackTrace();
        }

        
        

    }
}

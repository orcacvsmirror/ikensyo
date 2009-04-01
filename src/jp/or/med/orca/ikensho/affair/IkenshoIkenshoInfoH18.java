package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.text.ParseException;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoH18 extends IkenshoIkenshoInfo {
    protected IkenshoIkenshoSeikatsuService1 seikatsuService1;

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoIkenshoInfoH18() {
    	// 2009/01/05 [Mizuki Tsutsumi] : add begin
    	buttons.setBackToMainMenuVisible(true); //���C�����j���[�֖߂�{�^����\������
    	// 2009/01/05 [Mizuki Tsutsumi] : end begin
    }

    protected void doReplaceGraphicsCommand(IkenshoFirebirdDBManager dbm)
            throws Exception {
        // H18�ł͐l�̐}�ɂ��ĉ������Ă��Ȃ�
    }

    protected void addTabs() {
        applicant = new IkenshoIkenshoInfoApplicantH18();
        sick = new IkenshoIkenshoInfoSickH18();
        special = new IkenshoIkenshoInfoSpecial();
        seikatsuService1 = new IkenshoIkenshoSeikatsuService1();
        care1 = new IkenshoIkenshoInfoCare1H18();
        care2 = new IkenshoIkenshoInfoCare2H18();
        mindBody1 = new IkenshoIkenshoInfoMindBody1H18();
        mindBody2 = new IkenshoIkenshoInfoMindBody2H18();
        mention = new IkenshoIkenshoInfoMentionH18();
        organ = new IkenshoIkenshoInfoOrgan();
        bill = new IkenshoIkenshoInfoBill();

        // Add
        tabs.addTab("�\����", applicant);
        tabs.addTab("���a", sick);
        tabs.addTab("���ʂȈ��", special);
        tabs.addTab("�S�g�̏�ԂP", mindBody1);
        tabs.addTab("�S�g�̏�ԂQ", mindBody2);
        tabs.addTab("�����@�\�P", seikatsuService1);
        tabs.addTab("�����@�\�Q", care1);
        tabs.addTab("�����@�\�R", care2);
        tabs.addTab("���L�����E����", mention);
        tabs.addTab("��Ë@��", organ);
        tabPanel.add(bill, BorderLayout.SOUTH);

        tabArray.clear();
        tabArray.add(applicant);
        tabArray.add(sick);
        tabArray.add(special);
        tabArray.add(mindBody1);
        tabArray.add(mindBody2);
        tabArray.add(seikatsuService1);
        tabArray.add(care1);
        tabArray.add(care2);
        tabArray.add(mention);
        tabArray.add(organ);
        tabArray.add(bill);

    }

    protected void appendInsertCommonDocumentKeys(StringBuffer sb) {
        // super.appendInsertCommonDocumentKeys(sb);
        // sb.append(",IMPRO_SERVICE");
        // �@������͉��������Ȃ�
    }

    protected void appendInsertCommonDocumentValues(StringBuffer sb)
            throws ParseException {
        // super.appendInsertCommonDocumentValues(sb);

        // sb.append(",");
        // sb.append(getDBSafeString("IMPRO_SERVICE", originalData));
        // �@������͉��������Ȃ�
    }

    protected void appendUpdateCommonDocumentStetement(StringBuffer sb)
            throws ParseException {
        // super.appendUpdateCommonDocumentStetement(sb);

        // sb.append(",IMPRO_SERVICE = ");
        // sb.append(getDBSafeString("IMPRO_SERVICE", originalData));
        // �@������͉��������Ȃ�
    }

    protected String getFormatKubun() {
        return "1";
    }

    protected boolean showPrintSetting(IkenshoIkenshoInfoPrintParameter param) {
        //static���\�b�h��L����N���X���p�������ꍇ�A�A�b�v�L���X�g���đ�����邱��
        IkenshoIkenshoPrintSetting f = new IkenshoIkenshoPrintSettingH18(
                originalData, mindBody2.getPicture());
        return f.showModal(param);
    }

    protected void appendInsertIkenshoKeys(StringBuffer sb) {
        super.appendInsertIkenshoKeys(sb);

        sb.append(",WEIGHT_CHANGE");
        sb.append(",MAHI_LEFTARM");
        sb.append(",MAHI_LEFTARM_TEIDO");
        sb.append(",MAHI_RIGHTARM");
        sb.append(",MAHI_RIGHTARM_TEIDO");
        sb.append(",MAHI_LOWERLEFTLIMB");
        sb.append(",MAHI_LOWERLEFTLIMB_TEIDO");
        sb.append(",MAHI_LOWERRIGHTLIMB");
        sb.append(",MAHI_LOWERRIGHTLIMB_TEIDO");
        sb.append(",MAHI_ETC");
        sb.append(",MAHI_ETC_BUI");
        sb.append(",MAHI_ETC_TEIDO");
        sb.append(",KOUSHU");
        sb.append(",KOUSHU_BUI");
        sb.append(",KOUSHU_TEIDO");
        sb.append(",KANSETU_ITAMI");
        sb.append(",KANSETU_ITAMI_BUI");
        sb.append(",KANSETU_ITAMI_TEIDO");
        sb.append(",OUTDOOR");
        sb.append(",WHEELCHAIR");
        sb.append(",ASSISTANCE_TOOL");
        sb.append(",MEAL");
        // sb.append(",MEAL_INTAKE");
        // sb.append(",MEAL_INTAKE_RYUIJIKOU");
        // sb.append(",APPETITE");
        // sb.append(",APPETITE_RYUIJIKOU");
        sb.append(",NOURISHMENT");
        // sb.append(",NOURISHMENT_RYUIJIKOU");
        sb.append(",EATING_RYUIJIKOU");
        sb.append(",IDOUTEIKA");
        sb.append(",IDOUTEIKA_TAISHO_HOUSIN");
        sb.append(",TOJIKOMORI");
        sb.append(",TOJIKOMORI_TAISHO_HOUSIN");
        sb.append(",IYOKUTEIKA");
        sb.append(",IYOKUTEIKA_TAISHO_HOUSIN");
        sb.append(",TEIEIYOU");
        sb.append(",TEIEIYOU_TAISHO_HOUSIN");
        sb.append(",SESSYOKUENGE");
        sb.append(",SESSYOKUENGE_TAISHO_HOUSIN");
        sb.append(",GAN_TOUTU");
        sb.append(",GAN_TOUTU_TAISHO_HOUSIN");
        sb.append(",UNDOU");
        sb.append(",UNDOU_RYUIJIKOU");
        sb.append(",VITAL_FUNCTIONS_OUTLOOK");
        sb.append(",INSECURE_CONDITION");

    }

    protected void appendInsertIkenshoValues(StringBuffer sb)
            throws ParseException {
        super.appendInsertIkenshoValues(sb);

        sb.append(",");
        sb.append(getDBSafeNumber("WEIGHT_CHANGE", originalData));
        if (((Integer) VRBindPathParser.get("MAHI_FLAG", originalData))
                .intValue() == 1) {
            IkenshoCommon
                    .addFollowCheckNumberInsert(sb, originalData,
                            "MAHI_LEFTARM",
                            new String[] { "MAHI_LEFTARM_TEIDO" }, true);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData,
                    "MAHI_RIGHTARM", new String[] { "MAHI_RIGHTARM_TEIDO" },
                    true);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData,
                    "MAHI_LOWERLEFTLIMB",
                    new String[] { "MAHI_LOWERLEFTLIMB_TEIDO" }, true);
            IkenshoCommon.addFollowCheckNumberInsert(sb, originalData,
                    "MAHI_LOWERRIGHTLIMB",
                    new String[] { "MAHI_LOWERRIGHTLIMB_TEIDO" }, true);
            IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                    "MAHI_ETC", new String[] { "MAHI_ETC_BUI" },
                    new String[] { "MAHI_ETC_TEIDO" }, true, true);
        } else {
            // ��Ⴢ͂��ׂĖ����͂Ƃ��Ĉ���
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",0");
            sb.append(",''");
            sb.append(",0");
        }
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "KOUSHU",
                new String[] { "KOUSHU_BUI" }, new String[] { "KOUSHU_TEIDO" },
                true, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "KANSETU_ITAMI", new String[] { "KANSETU_ITAMI_BUI" },
                new String[] { "KANSETU_ITAMI_TEIDO" }, true, true);
        sb.append(",");
        sb.append(getDBSafeNumber("OUTDOOR", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("WHEELCHAIR", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("ASSISTANCE_TOOL", originalData));
        // �H���s��
        sb.append(",");
        sb.append(getDBSafeNumber("MEAL", originalData));
        // IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
        // "MEAL_INTAKE",
        // new String[] {
        // "MEAL_INTAKE_RYUIJIKOU"}
        // , 2, true);
        // IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "APPETITE",
        // new String[] {
        // "APPETITE_RYUIJIKOU"}
        // , 2, true);
        // ���݂̉h�{���
        sb.append(",");
        sb.append(getDBSafeNumber("NOURISHMENT", originalData));
        // �h�{�E�H������̗��ӓ_
        sb.append(",");
        sb.append(getDBSafeString("EATING_RYUIJIKOU", originalData));
        // IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
        // "NOURISHMENT",
        // new String[] {
        // "NOURISHMENT_RYUIJIKOU"}
        // , 2, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "IDOUTEIKA",
                new String[] { "IDOUTEIKA_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "TOJIKOMORI",
                new String[] { "TOJIKOMORI_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "IYOKUTEIKA",
                new String[] { "IYOKUTEIKA_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "TEIEIYOU",
                new String[] { "TEIEIYOU_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "SESSYOKUENGE", new String[] { "SESSYOKUENGE_TAISHO_HOUSIN" },
                true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "GAN_TOUTU",
                new String[] { "GAN_TOUTU_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "UNDOU",
                new String[] { "UNDOU_RYUIJIKOU" }, 2, true);

        // �T�[�r�X���p�ɂ�鐶���@�\�̈ێ��E���P�̌��ʂ�
        sb.append(",");
        sb.append(getDBSafeNumber("VITAL_FUNCTIONS_OUTLOOK", originalData));

        // �Ǐ�Ƃ��Ă̈��萫���u�s����v�Ƃ����ꍇ�A��̓I�ȏ�
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "SHJ_ANT",
                new String[] { "INSECURE_CONDITION" }, 2, false);

    }

    protected void appendUpdateIkenshoStetement(StringBuffer sb)
            throws ParseException {
        super.appendUpdateIkenshoStetement(sb);

        sb.append(",WEIGHT_CHANGE = ");
        sb.append(getDBSafeNumber("WEIGHT_CHANGE", originalData));
        if (((Integer) VRBindPathParser.get("MAHI_FLAG", originalData))
                .intValue() == 1) {
            IkenshoCommon
                    .addFollowCheckNumberUpdate(sb, originalData,
                            "MAHI_LEFTARM",
                            new String[] { "MAHI_LEFTARM_TEIDO" }, true);
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "MAHI_RIGHTARM", new String[] { "MAHI_RIGHTARM_TEIDO" },
                    true);
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "MAHI_LOWERLEFTLIMB",
                    new String[] { "MAHI_LOWERLEFTLIMB_TEIDO" }, true);
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "MAHI_LOWERRIGHTLIMB",
                    new String[] { "MAHI_LOWERRIGHTLIMB_TEIDO" }, true);
            IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                    "MAHI_ETC", new String[] { "MAHI_ETC_BUI" },
                    new String[] { "MAHI_ETC_TEIDO" }, true, true);
        } else {
            // ��Ⴢ͂��ׂĖ����͂Ƃ��Ĉ���
            sb.append(",MAHI_LEFTARM = 0");
            sb.append(",MAHI_LEFTARM_TEIDO = 0");
            sb.append(",MAHI_RIGHTARM = 0");
            sb.append(",MAHI_RIGHTARM_TEIDO = 0");
            sb.append(",MAHI_LOWERLEFTLIMB = 0");
            sb.append(",MAHI_LOWERLEFTLIMB_TEIDO = 0");
            sb.append(",MAHI_LOWERRIGHTLIMB = 0");
            sb.append(",MAHI_LOWERRIGHTLIMB_TEIDO = 0");
            sb.append(",MAHI_ETC = 0");
            sb.append(",MAHI_ETC_BUI = ''");
            sb.append(",MAHI_ETC_TEIDO = 0");
        }
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "KOUSHU",
                new String[] { "KOUSHU_BUI" }, new String[] { "KOUSHU_TEIDO" },
                true, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                "KANSETU_ITAMI", new String[] { "KANSETU_ITAMI_BUI" },
                new String[] { "KANSETU_ITAMI_TEIDO" }, true, true);
        sb.append(",OUTDOOR = ");
        sb.append(getDBSafeNumber("OUTDOOR", originalData));
        sb.append(",WHEELCHAIR = ");
        sb.append(getDBSafeNumber("WHEELCHAIR", originalData));
        sb.append(",ASSISTANCE_TOOL = ");
        sb.append(getDBSafeNumber("ASSISTANCE_TOOL", originalData));
        sb.append(",MEAL = ");
        sb.append(getDBSafeNumber("MEAL", originalData));
        // IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
        // "MEAL_INTAKE",
        // new String[] {
        // "MEAL_INTAKE_RYUIJIKOU"}
        // , 2, true);
        // IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "APPETITE",
        // new String[] {
        // "APPETITE_RYUIJIKOU"}
        // , 2, true);
        sb.append(",NOURISHMENT = ");
        sb.append(getDBSafeNumber("NOURISHMENT", originalData));
        sb.append(",EATING_RYUIJIKOU = ");
        sb.append(getDBSafeString("EATING_RYUIJIKOU", originalData));
        // IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
        // "NOURISHMENT",
        // new String[] {
        // "NOURISHMENT_RYUIJIKOU"}
        // , 2, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "IDOUTEIKA",
                new String[] { "IDOUTEIKA_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "TOJIKOMORI",
                new String[] { "TOJIKOMORI_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "IYOKUTEIKA",
                new String[] { "IYOKUTEIKA_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "TEIEIYOU",
                new String[] { "TEIEIYOU_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                "SESSYOKUENGE", new String[] { "SESSYOKUENGE_TAISHO_HOUSIN" },
                true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "GAN_TOUTU",
                new String[] { "GAN_TOUTU_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "UNDOU",
                new String[] { "UNDOU_RYUIJIKOU" }, 2, true);

        // �T�[�r�X���p�ɂ�鐶���@�\�̈ێ��E���P�̌��ʂ�
        sb.append(",VITAL_FUNCTIONS_OUTLOOK = ");
        sb.append(getDBSafeNumber("VITAL_FUNCTIONS_OUTLOOK", originalData));

        // �Ǐ�Ƃ��Ă̈��萫���u�s����v�Ƃ����ꍇ�A��̓I�ȏ�
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "SHJ_ANT",
                new String[] { "INSECURE_CONDITION" }, 2, false);
    }

    protected void doSelectBeforeCustomDocument(IkenshoFirebirdDBManager dbm)
            throws Exception {
        super.doSelectBeforeCustomDocument(dbm);

        if (VRBindPathParser.has("UNDOU", originalData)) {
            Object obj = VRBindPathParser.get("UNDOU", originalData);
            boolean set = false;
            if (obj == null) {
                set = true;
            } else if (obj instanceof Integer) {
                if (((Integer) obj).intValue() == 0) {
                    set = true;
                }
            }
            if (set) {
                // �@�����O�̃f�[�^��������p�����ꍇ�A�f�t�H���g��0��NULL�ɂȂ��Ă��邽��1�ɕύX����
                VRBindPathParser.set("UNDOU", originalData, new Integer(1));
            }
        }

    }

    // 2006/12/09[Tozo Tanaka] : delete begin
//    protected void doSelectBeforeCommonDocument(IkenshoFirebirdDBManager dbm)
//            throws Exception {
//        super.doSelectBeforeCommonDocument(dbm);
//
//        for (int i = 1; i <= 3; i++) {
//            if (VRBindPathParser.has("HASHOU_DT" + i, originalData)) {
//                String text = String.valueOf(VRBindPathParser.get("HASHOU_DT"
//                        + i, originalData));
//                if (text.length() > 2) {
//                    String era = text.substring(0, 2);
//                    if (!("00".equals(era) || "����".equals(era) || "���a"
//                            .equals(era))) {
//                        // �@������̃f�[�^�Ƃ��āA�����E���a�ȊO�͔F�߂Ȃ�
//                        VRBindPathParser.set("HASHOU_DT" + i, originalData,
//                                "0000�N00��00��");
//                    }
//                }
//            }
//        }
//
//    }
    // 2006/12/09[Tozo Tanaka] : delete end
    
}

package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.text.ParseException;

import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoH18 extends IkenshoIkenshoInfo {
    protected IkenshoIkenshoSeikatsuService1 seikatsuService1;
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能
    protected IkenshoIkenshoInfoMentionSpecial mentionSpecial;
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

    /**
     * コンストラクタです。
     */
    public IkenshoIkenshoInfoH18() {
    	// 2009/01/05 [Mizuki Tsutsumi] : add begin
    	buttons.setBackToMainMenuVisible(true); //メインメニューへ戻るボタンを表示する
    	// 2009/01/05 [Mizuki Tsutsumi] : end begin
    }

    protected void doReplaceGraphicsCommand(IkenshoFirebirdDBManager dbm)
            throws Exception {
        // H18版は人体図について何もしていない
    }

    protected void addTabs() {
        applicant = new IkenshoIkenshoInfoApplicantH18();
        sick = new IkenshoIkenshoInfoSickH18();
        // [ID:0000509][Masahiko Higuchi] 2009/06 add begin 画面調整に伴い調整
        sick2 = new IkenshoIkenshoInfoSickH18_2();
        // [ID:0000509][Masahiko Higuchi] 2009/06 add end
        special = new IkenshoIkenshoInfoSpecial();
        seikatsuService1 = new IkenshoIkenshoSeikatsuService1();
        care1 = new IkenshoIkenshoInfoCare1H18();
        care2 = new IkenshoIkenshoInfoCare2H18();
        mindBody1 = new IkenshoIkenshoInfoMindBody1H18();
        mindBody2 = new IkenshoIkenshoInfoMindBody2H18();
//      [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//        mention = new IkenshoIkenshoInfoMentionH18();
        mention = new IkenshoIkenshoInfoMentionClaim();
        mentionSpecial =  new IkenshoIkenshoInfoMentionSpecial(); 
//      [ID:0000514][Tozo TANAKA] 2009/09/09 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        organ = new IkenshoIkenshoInfoOrgan();
        bill = new IkenshoIkenshoInfoBill();

        // Add
        tabs.addTab("申請者", applicant);
                // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴い調整
        tabs.addTab("傷病１", sick);
        tabs.addTab("傷病２", sick2);
        // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
        tabs.addTab("特別な医療", special);
// [ID:0000789][Satoshi Tokusari] 2014/10 edit-Start 主治医意見書のタブ1行化対応
//        tabs.addTab("心身の状態１", mindBody1);
//        tabs.addTab("心身の状態２", mindBody2);
//        tabs.addTab("生活機能１", seikatsuService1);
//        tabs.addTab("生活機能２", care1);
//        tabs.addTab("生活機能３", care2);
        tabs.addTab("心身１", mindBody1);
        tabs.addTab("心身２", mindBody2);
        tabs.addTab("生活１", seikatsuService1);
        tabs.addTab("生活２", care1);
        tabs.addTab("生活３", care2);
// [ID:0000789][Satoshi Tokusari] 2014/10 edit-End
//      [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//        tabs.addTab("特記事項・請求", mention);
        tabs.addTab("特記事項", mentionSpecial);
        tabs.addTab("請求", mention);
//      [ID:0000514][Tozo TANAKA] 2009/09/09 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        tabs.addTab("医療機関", organ);
        tabPanel.add(bill, BorderLayout.SOUTH);

        tabArray.clear();
        tabArray.add(applicant);
        tabArray.add(sick);
        // [ID:0000509][Masahiko Higuchi] 2009/06 add begin 画面調整に伴い調整
        tabArray.add(sick2);
        // [ID:0000509][Masahiko Higuchi] 2009/06 add end
        tabArray.add(special);
        tabArray.add(mindBody1);
        tabArray.add(mindBody2);
        tabArray.add(seikatsuService1);
        tabArray.add(care1);
        tabArray.add(care2);
//      [ID:0000514][Tozo TANAKA] 2009/09/09 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        tabArray.add(mentionSpecial);
//      [ID:0000514][Tozo TANAKA] 2009/09/09 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        tabArray.add(mention);
        tabArray.add(organ);
        tabArray.add(bill);

    }

    protected void appendInsertCommonDocumentKeys(StringBuffer sb) {
        // super.appendInsertCommonDocumentKeys(sb);
        // sb.append(",IMPRO_SERVICE");
        // 法改正後は何もさせない
    }

    protected void appendInsertCommonDocumentValues(StringBuffer sb)
            throws ParseException {
        // super.appendInsertCommonDocumentValues(sb);

        // sb.append(",");
        // sb.append(getDBSafeString("IMPRO_SERVICE", originalData));
        // 法改正後は何もさせない
    }

    protected void appendUpdateCommonDocumentStetement(StringBuffer sb)
            throws ParseException {
        // super.appendUpdateCommonDocumentStetement(sb);

        // sb.append(",IMPRO_SERVICE = ");
        // sb.append(getDBSafeString("IMPRO_SERVICE", originalData));
        // 法改正後は何もさせない
    }

    protected String getFormatKubun() {
        return "1";
    }

    protected boolean showPrintSetting(IkenshoIkenshoInfoPrintParameter param) {
        //staticメソッドを有するクラスを継承した場合、アップキャストして代入すること
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

        //[ID:0000514][Tozo TANAKA] 2009/09/10 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        sb.append(",KAIGO_HITSUYODO_HENKA");
        sb.append(",NINTEI_JOHO_KIBO");
        //[ID:0000514][Tozo TANAKA] 2009/09/10 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        
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
            // 麻痺はすべて未入力として扱う
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
        // 食事行為
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
        // 現在の栄養状態
        sb.append(",");
        sb.append(getDBSafeNumber("NOURISHMENT", originalData));
        // 栄養・食生活上の留意点
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

        // サービス利用による生活機能の維持・改善の見通し
        sb.append(",");
        sb.append(getDBSafeNumber("VITAL_FUNCTIONS_OUTLOOK", originalData));

        // 症状としての安定性を「不安定」とした場合、具体的な状況
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "SHJ_ANT",
                new String[] { "INSECURE_CONDITION" }, 2, false);

        
        //[ID:0000514][Tozo TANAKA] 2009/09/10 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        sb.append(",");
        sb.append(getDBSafeNumber("KAIGO_HITSUYODO_HENKA", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("NINTEI_JOHO_KIBO", originalData));
        //[ID:0000514][Tozo TANAKA] 2009/09/10 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        
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
            // 麻痺はすべて未入力として扱う
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

        // サービス利用による生活機能の維持・改善の見通し
        sb.append(",VITAL_FUNCTIONS_OUTLOOK = ");
        sb.append(getDBSafeNumber("VITAL_FUNCTIONS_OUTLOOK", originalData));

        // 症状としての安定性を「不安定」とした場合、具体的な状況
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "SHJ_ANT",
                new String[] { "INSECURE_CONDITION" }, 2, false);
        
        //[ID:0000514][Tozo TANAKA] 2009/09/10 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        sb.append(",KAIGO_HITSUYODO_HENKA = ");
        sb.append(getDBSafeNumber("KAIGO_HITSUYODO_HENKA", originalData));
        sb.append(",NINTEI_JOHO_KIBO = ");
        sb.append(getDBSafeNumber("NINTEI_JOHO_KIBO", originalData));
        //[ID:0000514][Tozo TANAKA] 2009/09/10 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
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
                // 法改正前のデータから引き継いだ場合、デフォルトが0かNULLになっているため1に変更する
                VRBindPathParser.set("UNDOU", originalData, new Integer(1));
            }
        }
        
        // [ID:0000555][Tozo TANAKA] 2009/09/14 add begin 【2009年度対応：追加案件】医師意見書の受給者番号対応
        if (!getFormatKubun().equals(
                ACCastUtilities.toString(VRBindPathParser.get("FORMAT_KBN",
                        originalData)))) {
            //異なる文書区分間(主治医意見書⇔医師意見書)の引き継ぎにおいてはINSURED_NOを初期化する。
            VRBindPathParser.set("INSURED_NO", originalData, "");
        }
        
        
        String patientNo = getPatientNo();
        if (patientNo != null) {
            //同種の文書から引き継ぐ
            StringBuffer sb;
            sb = new StringBuffer();
            sb.append("SELECT");
            sb.append(" INSURED_NO");
            sb.append(" FROM");
            sb.append(" IKN_ORIGIN");
            sb.append(" WHERE");
            sb.append(" (PATIENT_NO = ");
            sb.append(patientNo);
            sb.append(")");
            sb.append("AND(FORMAT_KBN = ");
            sb.append(getFormatKubun());
            sb.append(")");
            sb.append(" ORDER BY");
            sb.append(" EDA_NO DESC");
            VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
            if (array.size() > 0) {
                VRMap data = (VRMap) array.getData();
                originalData.putAll(data);
            }
        }
        //[ID:0000555][Tozo TANAKA] 2009/09/14 add end 【2009年度対応：追加案件】医師意見書の受給者番号対応

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
//                    if (!("00".equals(era) || "平成".equals(era) || "昭和"
//                            .equals(era))) {
//                        // 法改正後のデータとして、平成・昭和以外は認めない
//                        VRBindPathParser.set("HASHOU_DT" + i, originalData,
//                                "0000年00月00日");
//                    }
//                }
//            }
//        }
//
//    }
    // 2006/12/09[Tozo Tanaka] : delete end
    
}

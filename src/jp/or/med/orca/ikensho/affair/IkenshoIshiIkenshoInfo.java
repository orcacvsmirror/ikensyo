package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
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
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

public class IkenshoIshiIkenshoInfo extends IkenshoIkenshoInfoH18 {

	//心身の状態１
    protected IkenshoIshiIkenshoInfoMindBody1 ishiMindBody1;
    //心身の状態２
    protected IkenshoIshiIkenshoInfoMindBody2 ishiMindBody2;
    
    //行動及び精神１
    protected IkenshoIshiIkenshoInfoActionAndMind1 ishiAction1;
    //行動及び精神２
    protected IkenshoIshiIkenshoInfoActionAndMind2 ishiAction2;
    
    //サービス利用に関する意見
    protected IkenshoIshiIkenshoInfoCare1 care1;
    
    protected IkenshoIshiIkenshoInfoSpecialMention1 ishiSpesicalMention1;
    protected IkenshoIshiIkenshoInfoSick2 ishiSick2;
    protected ACAffairButton showHelp;
    
    /**
     * help を返します。
     * @return help
     */
    protected ACAffairButton getShowHelp() {
        if(showHelp==null){
        	// 2009/01/06 [Mizuki Tsutsumi] : edit begin
        	//showHelp = new ACAffairButton("記載例(H)");
        	showHelp = new ACAffairButton("　例(H)　");
            // 2009/01/06 [Mizuki Tsutsumi] : edit end
        	//showHelp.setPreferredSize(new Dimension(67, 44));
            showHelp.setIconPath(ACConstants.ICON_PATH_QUESTION_24);
            showHelp.setMnemonic('H');
            showHelp.setToolTipText("記載例を表示します。");
            showHelp.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    showHelpWindow();
                }});
        }
        return showHelp;
    }

    /**
     * コンストラクタです。
     */
    public IkenshoIshiIkenshoInfo() {
        super();
        // ステータスバー
        setStatusText("医師意見書");
        buttons.setText("医師意見書");
        buttons.add(getShowHelp(), VRLayout.EAST);
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
        // 色の変更
        Color c = IkenshoConstants.COLOR_DISTINCTION;
        buttons.getBackButton().setBackground(c);
        buttons.getBackToMainMenuButton().setBackground(c);
        showHelp.setBackground(c);
        update.setBackground(c);
        print.setBackground(c);
        buttons.setBackground(c);
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End 
    }
    /**
     * 記載例へのアクセス画面を表示します。
     */
    protected void showHelpWindow(){
        new IkenshoIshiShowHelp().setVisible(true);
    }
    
    protected void addTabs(){
        // 申請者タブ
        applicant = new IkenshoIshiIkenshoInfoApplicant();
        // --- 1.傷病に関する意見
        // 傷病名１タブ
        sick = new IkenshoIshiIkenshoInfoSick1();
        // 傷病名２タブ
        ishiSick2 = new IkenshoIshiIkenshoInfoSick2();
        
        // --- 2.身体の状態に関する意見
        // 心身の状態１タブ
        ishiMindBody1 = new IkenshoIshiIkenshoInfoMindBody1();
        // 心身の状態２タブ
        ishiMindBody2 = new IkenshoIshiIkenshoInfoMindBody2();
        
        //--- 3.行動及び精神等の状態に関する意見
        // 行動及び精神１
        ishiAction1 = new IkenshoIshiIkenshoInfoActionAndMind1();
        ishiAction1.setFollowDisabledComponents(new JComponent[] { tabs, update, print, buttons.getBackButton() });
        
        // 行動及び精神２
        ishiAction2 = new IkenshoIshiIkenshoInfoActionAndMind2();
        
        //--- 4.特別な医療
        // 特別な医療タブ
        special = new IkenshoIshiIkenshoInfoSpecial();
        
        //--- 5.サービス利用に関する意見
        care1 = new IkenshoIshiIkenshoInfoCare1();
        
        //--- 6.特記事項
        mention = new IkenshoIshiIkenshoInfoSpecialMention1();
        
        //--- 医療機関
        organ = new IkenshoIkenshoInfoOrgan();
        
        // 診療点数のダイアログ
        bill = new IkenshoIkenshoInfoBill();

        // Add
        tabs.addTab("申請者", applicant);
        tabs.addTab("傷病１", sick);
        tabs.addTab("傷病２", ishiSick2);
        
        tabs.addTab("身体の状態１", ishiMindBody1);
        tabs.addTab("身体の状態２", ishiMindBody2);
        
        tabs.addTab("行動及び精神１", ishiAction1);
        tabs.addTab("行動及び精神２", ishiAction2);
        
        tabs.addTab("特別な医療", special);
        tabs.addTab("生活機能", care1);
        
        tabs.addTab("特記事項・請求", mention);
        tabs.addTab("医療機関", organ);
        tabPanel.add(bill, BorderLayout.SOUTH);
        
        
        tabArray.clear();
        tabArray.add(applicant);
        tabArray.add(sick);
        tabArray.add(ishiSick2);
        tabArray.add(ishiMindBody1);
        tabArray.add(ishiMindBody2);
        tabArray.add(ishiAction1);
        tabArray.add(ishiAction2);
        tabArray.add(special);
        tabArray.add(care1);
        tabArray.add(mention);
        tabArray.add(organ);
        tabArray.add(bill);
        
    }
    
    /**
     * 医療機関タブを返します。
     * 
     * @return 医療機関タブ
     */
    public IkenshoIkenshoInfoOrgan getOrgan() {
        return organ;
    }

    /**
     * 特記事項タブを返します。
     * 
     * @return 特記事項タブ
     */
    public IkenshoIkenshoInfoMention getMention() {
        return mention;
    }

    /**
     * 請求書タブを返します。
     * 
     * @return 請求書タブ
     */
    public IkenshoIkenshoInfoBill getBill() {
        return bill;
    }

    /**
     * 申請者タブを返します。
     * 
     * @return 申請者タブ
     */
    public IkenshoIkenshoInfoApplicant getApplicant() {
        return applicant;
    }

    /**
     * CSVファイルの出力対象区分を返します。
     * 
     * @return CSVファイルの出力対象区分
     */
    public Integer getBillFDOutputKubun() {
        return billFDOutputKubun;
    }

    /**
     * CSVファイルの出力対象区分を設定します。
     * 
     * @param billFDOutputKubun CSVファイルの出力対象区分
     */
    public void setBillFDOutputKubun(Integer billFDOutputKubun) {
        this.billFDOutputKubun = billFDOutputKubun;
    }

    /**
     * CSVファイルの出力日時を設定します。
     * 
     * @param billFDOutputTime CSVファイルの出力日時
     */
    public void setBillFDOutputTime(Date billFDOutputTime) {
        this.billFDOutputTime = billFDOutputTime;
    }

    
    
    /**
     * 印刷
     */
    protected boolean showPrintSetting(IkenshoIkenshoInfoPrintParameter param) {
//        IkenshoIkenshoPrintSettingIshi i = new IkenshoIkenshoPrintSettingIshi(
//                originalData, mindBody2.getPicture());
        IkenshoIkenshoPrintSettingIshi i = new IkenshoIkenshoPrintSettingIshi(
                originalData, null);
        return i.showModal(param);
    }
    
    /**
     * 医師意見書情報を追加します。
     * 
     * @param dbm DBManager
     * @throws ParseException 解析例外
     * @throws SQLException SQL例外
     */
    protected void doInsertIkensho(IkenshoFirebirdDBManager dbm)
            throws ParseException, SQLException {
        
        // 医師意見書
        StringBuffer sb = new StringBuffer();
        
        sb.append("INSERT INTO");
        sb.append(" IKN_ORIGIN");
        sb.append(" (");
        // --------------------------------------
        // 申請者タブの情報
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
        // 傷病１タブ
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
        // 特別な医療タブ
        // --------------------------------------       
        sb.append(" ,KYUIN_SHOCHI");
        sb.append(" ,KYUIN_SHOCHI_CNT");
        sb.append(" ,KYUIN_SHOCHI_JIKI");
        // --------------------------------------
        // 心身の状態
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
        // 生活機能
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
        // その他特記すべき事項
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
        
        
        // --------------------------------------
        // 平成26年度　医師意見書追加項目
        // --------------------------------------
        sb.append(" ,KINRYOKU_TEIKA_CHANGE"); //筋力低下
        sb.append(" ,KOUSHU_ETC_BUI_TEIDO"); //関節の拘縮その他程度
        sb.append(" ,KANSETU_ITAMI_CHANGE"); //関節の痛み
        sb.append(" ,KS_JISYOU"); //自傷
        sb.append(" ,SS_ISHIKI_SHOGAI"); //意識障害
        sb.append(" ,SS_KIBUN_SHOGAI"); //気分障害
        sb.append(" ,SS_SUIMIN_SHOGAI"); //睡眠障害
        sb.append(" ,KOUDO_SHOGAI"); //行動障害
        sb.append(" ,SEISIN_ZOAKU"); //精神状態の増悪
        sb.append(" ,KEIREN_HOSSA"); //けいれん発作
        sb.append(" ,TAISHO_HOUSIN"); //対処方針
        sb.append(" ,SFS_KOUDO"); //行動障害について
        sb.append(" ,SFS_KOUDO_RYUIJIKOU"); //行動障害について留意事項
        sb.append(" ,SFS_SEISIN"); //精神障害について
        sb.append(" ,SFS_SEISIN_RYUIJIKOU"); //精神障害について留意事項

        
        sb.append(" )");
        sb.append(" VALUES");
        // =============================================================
        // 追加する値
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
            // 一律初期値
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
        // 吸引処置
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
            // 一律初期値
            sb.append(",0");
            sb.append(",''");
        }
        // 精神障害
        // 精神 - その他のみ個別出力
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "SEISIN",
                new String[] { "SEISIN_NM" }, true);
        
        IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "SEISIN",
                new String[] { "SS_SENMO", "SS_KEMIN_KEIKO",
                        "SS_GNS_GNC", "SS_MOUSOU", "SS_SHIKKEN_TOSHIKI",
                        "SS_SHITUNIN", "SS_SHIKKO", "SS_NINCHI_SHOGAI",
                        "SS_KIOKU_SHOGAI", "SS_CHUI_SHOGAI",
                        "SS_SUIKOU_KINO_SHOGAI", "SS_SHAKAITEKI_KODO_SHOGAI",
                        "SS_OTHER" }, false);
        // 記憶障害
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
        // その他
        if (new Integer(1).equals(originalData.getData("SEISIN"))
                && new Integer(1).equals(originalData.getData("SS_OTHER"))) {
            sb.append(",");
            sb.append(getDBSafeString("SS_OTHER_NM",originalData));
        }else{
            sb.append(",''");
        }
        // 専門医受診の有無
        // 精神・神経症状有りだったらINSERTする
        if (new Integer(1).equals(originalData.getData("SEISIN"))){
            IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "SENMONI",
                    new String[] { "SENMONI_NM" }, true);
        }else{
            sb.append(",2");
            sb.append(",''");
        }
        // てんかん頻度
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
        // 麻痺チェック
        IkenshoCommon
                .addFollowCheckNumberInsert(sb, originalData, "MAHI_FLAG",
                        new String[] { "MAHI_LEFTARM", "MAHI_RIGHTARM",
                                "MAHI_LOWERLEFTLIMB", "MAHI_LOWERRIGHTLIMB",
                                "MAHI_ETC" }, false);
        // 各程度についてもチェックする
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
        // 筋力低下
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "KINRYOKU_TEIKA", new String[] { "KINRYOKU_TEIKA_BUI" },
                new String[] { "KINRYOKU_TEIKA_TEIDO" }, true, true);
        
        // 関節の拘縮
        IkenshoCommon.addFollowCheckNumberInsert(sb, originalData, "KOUSHU",
                new String[] { "KATA_KOUSHU", "MATA_KOUSHU", "HIJI_KOUSHU",
                        "HIZA_KOUSHU" }, true);
        
        // 関節の左右について個別処理
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
        // 関節左右を元にして程度出力
        // 肩
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
        // 股
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
        // 肘
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
        // 膝
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
        // その他処理
        if (new Integer(1).equals(originalData.getData("KOUSHU"))){
            IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                    "KOUSHU_ETC", new String[] { "KOUSHU_ETC_BUI" },true);
        }else{
            sb.append(",0");
            sb.append(",''");
        }
        
        // 関節の痛み
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "KANSETU_ITAMI",
                new String[] { "KANSETU_ITAMI_BUI" },
                new String[] { "KANSETU_ITAMI_TEIDO" }, true, true);
        
        // 失調・不随意運動
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
            // 失調に関係する項目は全て0とみなす
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
        
        // 褥瘡
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "JOKUSOU",
                new String[] { "JOKUSOU_BUI" },
                new String[] { "JOKUSOU_TEIDO" }, true, true);
        
        // その他の皮膚疾患
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "HIFUSIKKAN",
                new String[] { "HIFUSIKKAN_BUI" },
                new String[] { "HIFUSIKKAN_TEIDO" }, true, true);
        
        // 尿失禁
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "NYOUSIKKIN",
                new String[] { "NYOUSIKKIN_TAISHO_HOUSIN" }, true);
        // 転倒・骨折
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "TENTOU_KOSSETU",
                new String[] { "TENTOU_KOSSETU_TAISHO_HOUSIN" }, true);
        // 徘徊
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "HAIKAI_KANOUSEI",
                new String[] { "HAIKAI_KANOUSEI_TAISHO_HOUSIN" }, true);
        // 褥瘡
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "JOKUSOU_KANOUSEI",
                new String[] { "JOKUSOU_KANOUSEI_TAISHO_HOUSIN" }, true);
        // 嚥下性肺炎
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "ENGESEIHAIEN", new String[] { "ENGESEIHAIEN_TAISHO_HOUSIN" },
                true);
        // 腸閉塞
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "CHOUHEISOKU",
                new String[] { "CHOUHEISOKU_TAISHO_HOUSIN" }, true);
        // 易感染症
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "EKIKANKANSEN", new String[] { "EKIKANKANSEN_TAISHO_HOUSIN" },
                true);
        // 心配機能低下
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "SINPAIKINOUTEIKA",
                new String[] { "SINPAIKINOUTEIKA_TAISHO_HOUSIN" }, true);
        // 痛み対処方針
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "ITAMI",
                new String[] { "ITAMI_TAISHO_HOUSIN" }, true);
        // 脱水対処方針
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "DASSUI",
                new String[] { "DASSUI_TAISHO_HOUSIN" }, true);
        // 病態他
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "BYOUTAITA",
                new String[] { "BYOUTAITA_NM", "BYOUTAITA_TAISHO_HOUSIN" },
                true);
        // 血圧
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
        
        
        // --------------------------------------
        // 平成26年度　医師意見書追加項目
        // --------------------------------------
        //筋力低下
        if (isChecked(originalData, "KINRYOKU_TEIKA")) {
        	sb.append(",");
        	sb.append(getDBSafeNumber("KINRYOKU_TEIKA_CHANGE", originalData));
        	
        } else {
        	sb.append(",0");
        }
        
        //関節の拘縮その他程度
        if (isChecked(originalData, "KOUSHU")) {
        	sb.append(",");
        	sb.append(getDBSafeNumber("KOUSHU_ETC_BUI_TEIDO", originalData));
        } else {
        	sb.append(",0");
        }
        
        //関節の痛み
        if (isChecked(originalData, "KANSETU_ITAMI")) {
        	sb.append(",");
        	sb.append(getDBSafeNumber("KANSETU_ITAMI_CHANGE", originalData));
        } else {
        	sb.append(",0");
        }
        
        //自傷
        if (isChecked(originalData, "MONDAI_FLAG")) {
        	sb.append(",");
        	sb.append(getDBSafeNumber("KS_JISYOU", originalData));
        } else {
        	sb.append(",0");
        }
        
        
        if (isChecked(originalData, "SEISIN")) {
	        //意識障害
	        sb.append(",");
	        sb.append(getDBSafeNumber("SS_ISHIKI_SHOGAI", originalData));
	        //気分障害
	        sb.append(",");
	        sb.append(getDBSafeNumber("SS_KIBUN_SHOGAI", originalData));
	        //睡眠障害
	        sb.append(",");
	        sb.append(getDBSafeNumber("SS_SUIMIN_SHOGAI", originalData));
        } else {
        	sb.append(",0,0,0");
        }
        
        
        //行動障害
        sb.append(",");
        sb.append(getDBSafeNumber("KOUDO_SHOGAI", originalData));
        //精神状態の増悪
        sb.append(",");
        sb.append(getDBSafeNumber("SEISIN_ZOAKU", originalData));
        //けいれん発作
        sb.append(",");
        sb.append(getDBSafeNumber("KEIREN_HOSSA", originalData));
        //対処方針
        sb.append(",");
        if (isCheckdByotai(originalData)) {
        	sb.append(getDBSafeString("TAISHO_HOUSIN", originalData));
        } else {
        	sb.append("''");
        }
        
        //行動障害について
        sb.append(",");
        sb.append(getDBSafeNumber("SFS_KOUDO", originalData));
        //行動障害について留意事項
        if (isChecked(originalData, "SFS_KOUDO", 2)) {
            sb.append(",");
            sb.append(getDBSafeString("SFS_KOUDO_RYUIJIKOU", originalData));
        } else {
        	sb.append(",''");
        }
        
        //精神障害について
        sb.append(",");
        sb.append(getDBSafeNumber("SFS_SEISIN", originalData));
        //精神障害について留意事項
        if (isChecked(originalData, "SFS_SEISIN", 2)) {
	        sb.append(",");
	        sb.append(getDBSafeString("SFS_SEISIN_RYUIJIKOU", originalData));
        } else {
        	sb.append(",''");
        }
        
        sb.append(")");
        
        dbm.executeUpdate(sb.toString());

        doUpdateHumanPicture(dbm);

        hasOriginalDocument = true;
        
    }
    
    
    private boolean isChecked(VRMap map, String key) {
    	return isChecked(map, key, 1);
    }
    
    private boolean isChecked(VRMap map, String key, int tureValue) {
    	
    	try {
        	int value = ACCastUtilities.toInt(VRBindPathParser.get(key, map), 0);
        	return value == tureValue;
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return false;
    }
    
    // ５．サービス利用に関する意見
    // (1)現在、発生の可能性が高い病態とその対処方針にチェックが設定されているか
    private boolean isCheckdByotai(VRMap map) {
    	//尿失禁
    	if (isChecked(map, "NYOUSIKKIN")) {
    		return true;
    	}
    	//転倒・骨折
    	if (isChecked(map, "TENTOU_KOSSETU")) {
    		return true;
    	}
    	//徘徊
		if (isChecked(map, "HAIKAI_KANOUSEI")) {
			return true;
		}
		//褥瘡
		if (isChecked(map, "JOKUSOU_KANOUSEI")) {
			return true;
		}
		//嚥下性肺炎
		if (isChecked(map, "ENGESEIHAIEN")) {
			return true;
		}
		//腸閉塞
		if (isChecked(map, "CHOUHEISOKU")) {
			return true;
		}
		//易感染性
		if (isChecked(map, "EKIKANKANSEN")) {
			return true;
		}
		//心肺機能の低下
		if (isChecked(map, "SINPAIKINOUTEIKA")) {
			return true;
		}
		//疼痛
		if (isChecked(map, "ITAMI")) {
			return true;
		}
		//脱水
		if (isChecked(map, "DASSUI")) {
			return true;
		}
		//行動障害
		if (isChecked(map, "KOUDO_SHOGAI")) {
			return true;
		}
		//精神状態の増悪
		if (isChecked(map, "SEISIN_ZOAKU")) {
			return true;
		}
		//けいれん発作
		if (isChecked(map, "KEIREN_HOSSA")) {
			return true;
		}
		//その他
		if (isChecked(map, "BYOUTAITA")) {
			return true;
		}
		
		return false;
    }
    
    
    
    
    /**
     * overrideして意見書更新時のSQLキー句を追加します。
     * 
     * @param sb 追加先
     * @throws ParseException 解析例外
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
        
        // 親のチェックがついてないときは空白で更新
        sb.append(",KYUIN_SHOCHI_CNT = ");
        if (isChecked(originalData, "KYUIN_SHOCHI")) {
        	sb.append(getDBSafeString("KYUIN_SHOCHI_CNT",originalData));
        } else {
        	sb.append("''");
        }
        sb.append(",KYUIN_SHOCHI_JIKI = ");
        if (isChecked(originalData, "KYUIN_SHOCHI")) {
        	sb.append(getDBSafeNumber("KYUIN_SHOCHI_JIKI",originalData));
        } else {
        	sb.append("0");
        }
        
        // 行動上の障害
        IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                "MONDAI_FLAG", new String[] { "KS_CHUYA", "KS_BOUGEN",
                        "KS_BOUKOU", "KS_TEIKOU", "KS_HAIKAI", "KS_FUSIMATU",
                        "KS_FUKETU", "KS_ISHOKU", "KS_SEITEKI_MONDAI","KS_OTHER" }, false);
        // 行動上の障害・その他
        if (new Integer(1).equals(originalData.getData("MONDAI_FLAG"))
                && new Integer(1).equals(originalData.getData("KS_OTHER"))) {
            sb.append(" ,KS_OTHER_NM = ");
            sb.append(getDBSafeString("KS_OTHER_NM",originalData));
        }else{
            sb.append(" ,KS_OTHER_NM = ''");
        }
        // 精神チェックボックス郡を連動処理
        IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData, "SEISIN",
                new String[] { "SS_SENMO", "SS_KEMIN_KEIKO", "SS_GNS_GNC",
                        "SS_MOUSOU", "SS_SHIKKEN_TOSHIKI", "SS_SHITUNIN",
                        "SS_SHIKKO", "SS_NINCHI_SHOGAI", "SS_KIOKU_SHOGAI",
                        "SS_CHUI_SHOGAI", "SS_SUIKOU_KINO_SHOGAI",
                        "SS_SHAKAITEKI_KODO_SHOGAI", "SS_OTHER" }, false);
        // 記憶障害チェック
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
        // その他は個別処理
        if (new Integer(1).equals(originalData.getData("SEISIN"))
                && new Integer(1).equals(originalData.getData("SS_OTHER"))) {
            sb.append(" ,SS_OTHER_NM = ");
            sb.append(getDBSafeString("SS_OTHER_NM",originalData));
        }else{
            sb.append(" ,SS_OTHER_NM = ''");
        }
        // てんかんを処理
        IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData, "TENKAN",
                new String[] { "TENKAN_HINDO" }, true);

        // 2007/07/02 - Firebird2.0
        // 重複コールのため削除(IkenshoIkenshoInfo.doUpdateIkensho())
        // Delete - begin [kamitsukasa.kazuyoshi]
        // 関節関係は項目の関係が変わっているのでoverrideしたメソッドで変更する
//        try{
//            appendDifferenceUpdateItem(sb);
//        }catch(Exception e){}
        // Delete - end [kamitsukasa.kazuyoshi]
        
        // 失調・不随意
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
        // 出生時チェックボックス
        sb.append(",SHUSSEI1 = ");
        sb.append(getDBSafeString("SHUSSEI1",originalData));
        sb.append(",SHUSSEI2 = ");
        sb.append(getDBSafeString("SHUSSEI2",originalData));
        sb.append(",SHUSSEI3 = ");
        sb.append(getDBSafeString("SHUSSEI3",originalData));

        // 2007/07/02 - Firebird2.0
        // 重複コールのため削除(IkenshoIkenshoInfo.doUpdateIkensho())
        // Delete - begin [kamitsukasa.kazuyoshi]
        // 専門医受診のみ変更
//        try{
//            doUpdateDifferenceItemSenmoni(sb);
//        }catch (Exception e) { }
        // Delete - end [kamitsukasa.kazuyoshi]
        
        
        
        // --------------------------------------
        // 平成26年度　医師意見書追加項目
        // --------------------------------------
        
        //筋力低下
        sb.append(",KINRYOKU_TEIKA_CHANGE = ");
        if (isChecked(originalData, "KINRYOKU_TEIKA")) {
        	sb.append(getDBSafeNumber("KINRYOKU_TEIKA_CHANGE", originalData));
        } else {
        	sb.append("0");
        }
        
        //関節の拘縮その他程度
        sb.append(",KOUSHU_ETC_BUI_TEIDO = ");
        if (isChecked(originalData, "KOUSHU")) {
        	sb.append(getDBSafeNumber("KOUSHU_ETC_BUI_TEIDO", originalData));
        } else {
        	sb.append("0");
        }
        //関節の痛み
        sb.append(",KANSETU_ITAMI_CHANGE = ");
        if (isChecked(originalData, "KANSETU_ITAMI")) {
        	sb.append(getDBSafeNumber("KANSETU_ITAMI_CHANGE", originalData));
        } else {
        	sb.append("0");
        }
        
        //自傷
        sb.append(",KS_JISYOU = ");
        if (isChecked(originalData, "MONDAI_FLAG")) {
        	sb.append(getDBSafeNumber("KS_JISYOU", originalData));
        } else {
        	sb.append("0");
        }
        
        boolean isCheckedMind = isChecked(originalData, "SEISIN");
        //意識障害
        sb.append(",SS_ISHIKI_SHOGAI = ");
        if (isCheckedMind) {
        	sb.append(getDBSafeNumber("SS_ISHIKI_SHOGAI", originalData));
        } else {
        	sb.append("0");
        }
        //気分障害
        sb.append(",SS_KIBUN_SHOGAI = ");
        if (isCheckedMind) {
        	sb.append(getDBSafeNumber("SS_KIBUN_SHOGAI", originalData));
        } else {
        	sb.append("0");
        }
        //睡眠障害
        sb.append(",SS_SUIMIN_SHOGAI = ");
        if (isCheckedMind) {
        	sb.append(getDBSafeNumber("SS_SUIMIN_SHOGAI", originalData));
        } else {
        	sb.append("0");
        }
        
        
        //行動障害
        sb.append(",KOUDO_SHOGAI = ");
        sb.append(getDBSafeNumber("KOUDO_SHOGAI", originalData));
        //精神状態の増悪
        sb.append(",SEISIN_ZOAKU = ");
        sb.append(getDBSafeNumber("SEISIN_ZOAKU", originalData));
        //けいれん発作
        sb.append(",KEIREN_HOSSA = ");
        sb.append(getDBSafeNumber("KEIREN_HOSSA", originalData));
        //対処方針
        sb.append(",TAISHO_HOUSIN = ");
        if (isCheckdByotai(originalData)) {
        	sb.append(getDBSafeString("TAISHO_HOUSIN", originalData));
        } else {
        	sb.append("''");
        }
        
        //行動障害について
        sb.append(",SFS_KOUDO = ");
        sb.append(getDBSafeNumber("SFS_KOUDO", originalData));
        //行動障害について留意事項
        sb.append(",SFS_KOUDO_RYUIJIKOU = ");
        if (isChecked(originalData, "SFS_KOUDO", 2)) {
        	sb.append(getDBSafeString("SFS_KOUDO_RYUIJIKOU", originalData));
        } else {
        	sb.append("''");
        }
        
        //精神障害について
        sb.append(",SFS_SEISIN = ");
        sb.append(getDBSafeNumber("SFS_SEISIN", originalData));
        //精神障害について留意事項
        sb.append(",SFS_SEISIN_RYUIJIKOU = ");
        if (isChecked(originalData, "SFS_SEISIN", 2)) {
        	sb.append(getDBSafeString("SFS_SEISIN_RYUIJIKOU", originalData));
        } else {
        	sb.append("''");
        }
        
        
    }
    
// [ID:0000790][Satoshi Tokusari] 2014/10 del-Start 医師意見書の専門科受診の有無の仕様変更対応
//    protected void doUpdateDifferenceItemSenmoni(StringBuffer sb)throws Exception{
//        if(new Integer(1).equals(originalData.getData("SEISIN"))){
//            IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "SENMONI",
//                    new String[] { "SENMONI_NM" }, true);
//        }else{
//            sb.append(",SENMONI = 2");
//            sb.append(",SENMONI_NM = ''");
//        }
//    }
//[ID:0000790][Satoshi Tokusari] 2014/10 del-End
    
    /**
     * 医師意見書との変更点を定義します。 
     */
    protected void appendDifferenceUpdateItem(StringBuffer sb) throws Exception{
        IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                "KOUSHU", new String[] { "KATA_KOUSHU", "MATA_KOUSHU",
                        "HIJI_KOUSHU", "HIZA_KOUSHU","KOUSHU_ETC" }, false);
        // 各関節の部位に関する処理
        // 肩
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("KATA_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "KATA_KOUSHU", new String[] { "KATA_KOUSHU_MIGI",
                            "KATA_KOUSHU_HIDARI" }, false);
        }else{
            sb.append(" ,KATA_KOUSHU_MIGI = 0");
            sb.append(" ,KATA_KOUSHU_HIDARI = 0");
        }
        // 股
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("MATA_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "MATA_KOUSHU", new String[] { "MATA_KOUSHU_MIGI",
                            "MATA_KOUSHU_HIDARI" }, false);
        }else{
            sb.append(" ,MATA_KOUSHU_MIGI = 0");
            sb.append(" ,MATA_KOUSHU_HIDARI = 0");
        }
        // 肘
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("HIJI_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "HIJI_KOUSHU", new String[] { "HIJI_KOUSHU_MIGI",
                            "HIJI_KOUSHU_HIDARI" }, false);
        }else{
            sb.append(" ,HIJI_KOUSHU_MIGI = 0");
            sb.append(" ,HIJI_KOUSHU_HIDARI = 0");
        }
        // 膝
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("HIZA_KOUSHU"))) {
            IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                    "HIZA_KOUSHU", new String[] { "HIZA_KOUSHU_MIGI",
                            "HIZA_KOUSHU_HIDARI" }, false);
        }else{
            sb.append(" ,HIZA_KOUSHU_MIGI = 0");
            sb.append(" ,HIZA_KOUSHU_HIDARI = 0");
        }
        // その他
        if (new Integer(1).equals(originalData.getData("KOUSHU"))
                && new Integer(1).equals(originalData.getData("KOUSHU_ETC"))) {
            IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                    "KOUSHU_ETC", new String[] { "KOUSHU_ETC_BUI"}, false);
        }else{
            sb.append(" ,KOUSHU_ETC_BUI = ''");
        }
        
        // 程度のみ別途登録
        // 肩
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
        // 股
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
        // 肘
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
        // 膝
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
    
    
    // 意見書、指示書共通項目の更新処理
    // 医師意見書のみ間歇的導尿の項目を追加
    /**
     * overrideして共通文書挿入時のSQLキー句を追加します。
     * @param sb 追加先
     */
    protected void appendInsertCommonDocumentKeys(StringBuffer sb){
    	sb.append(" ,KANKETSUTEKI_DOUNYOU");
    }
    /**
     * overrideして共通文書挿入時のSQLバリュー句を追加します。
     * @throws ParseException 解析例外
     * @param sb 追加先
     */
    protected void appendInsertCommonDocumentValues(StringBuffer sb)throws
        ParseException{
        sb.append(",");
        sb.append(getDBSafeNumber("KANKETSUTEKI_DOUNYOU", originalData));
    }
    /**
     * overrideして共通文書更新時のSQLキー句を追加します。
     * @param sb 追加先
     * @throws ParseException 解析例外
     */
    protected void appendUpdateCommonDocumentStetement(StringBuffer sb)throws
        ParseException{
        //間歇的導尿
        sb.append(",KANKETSUTEKI_DOUNYOU = ");
        sb.append(getDBSafeString("KANKETSUTEKI_DOUNYOU", originalData));
    }
    

    
    /**
     * 医師意見書を表す区分
     */
    protected String getFormatKubun() {
        return "2";
    }
    
    public static void main(String[] args){

        //デフォルトデバッグ起動
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

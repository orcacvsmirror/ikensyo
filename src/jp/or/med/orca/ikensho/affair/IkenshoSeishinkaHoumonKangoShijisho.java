package jp.or.med.orca.ikensho.affair;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import jp.nichicom.ac.util.ACMessageBox;
import jp.or.med.orca.ikensho.IkenshoConstants;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
public class IkenshoSeishinkaHoumonKangoShijisho extends IkenshoHoumonKangoShijisho {

    private IkenshoHoumonKangoShijishoRyuiShiji ryuiShiji2;

    /**
     * コンストラクタ
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
     * 初期処理
     */
    private void jbInit() throws Exception {
        addTabs();
        setStatusText("精神科訪問看護指示書");
        buttons.setTitle("精神科訪問看護指示書");
        // 色の変更
        Color c = IkenshoConstants.COLOR_DISTINCTION;
        buttons.getBackButton().setBackground(c);
        read.setBackground(c);
        update.setBackground(c);
        print.setBackground(c);
        buttons.setBackground(c);
    }

    /**
     * タブ追加処理
     */
    protected void addTabs() {
        tabs.addTab("患者", getApplicant());
        tabs.addTab("傷病", getShoubyou());
        tabs.addTab("現在の状況１", getJyoukyou());
        tabs.addTab("現在の状況２", getJyoukyou2());
        tabs.addTab("留意事項・指示事項１", getRyuiShiji());
        tabs.addTab("留意事項・指示事項２", getRyuiShiji2());
        tabs.addTab("特記", getTokki());
        tabs.addTab("医療機関", getIryoukikan());

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
     * 追加処理時精神科訪問看護指示書固有情報項目設定処理
     * @param sb 追加SQL
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
     * 追加処理時精神科訪問看護指示書固有情報値設定処理
     * @param sb 追加SQL
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
     * 更新処理時精神科訪問看護指示書固有情報設定処理
     * @param sb 更新SQL
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
     * 帳票ダイアログ表示処理
     * @return 帳票ダイアログ
     */
    protected boolean showPrintDialogCustom() throws Exception {
        // 帳票を改ページするかのフラグを設定して、印刷画面に渡す
        // 改ページの警告なしならFALSE
        if (getWarningMessage().length() == 0) {
            originalData.put("IS_PAGE_BREAK", "FALSE");
        } else {
            originalData.put("IS_PAGE_BREAK", "TRUE");
        }
        return new IkenshoSeishinkaHoumonKangoShijishoPrintSetting(originalData).showModal();
    }

    /**
     * 精神科訪問看護指示書のフォーマット区分を返します。
     * @return 精神科訪問看護指示書のフォーマット区分
     */
    protected String getFormatKubun() {
        // 精神科訪問看護指示書
        return "2";
    }

    /**
     * 当文書種類 を返します。
     * @return 当文書種類
     */
    protected String getCustomDocumentType() {
        return IkenshoConstants.DOC_KBN_SEISHIN;
    }

    /**
     * 他文書種類 を返します。
     * @return 他文書種類
     */
    protected String getAnotherDocumentType() {
        return IkenshoConstants.DOC_KBN_IKENSHO;
    }

    /**
     * 文書名 を返します。
     * @return 文書名
     */
    protected String getDocumentName() {
        return "精神科訪問看護指示書";
    }

    /**
     * 患者タブ を返します。
     * @return 患者タブ
     */
    public IkenshoHoumonKangoShijishoApplicant getApplicant() {
        if(applicant == null){
            applicant = new IkenshoSeishinkaHoumonKangoShijishoApplicant();
        }
        return applicant;
    }

    /**
     * 傷病１タブ を返します。
     * @return 傷病１タブ
     */
    public IkenshoHoumonKangoShijishoSick getShoubyou() {
        if(shoubyou==null){
            shoubyou = new IkenshoSeishinkaHoumonKangoShijishoSick();
        }
        return shoubyou;
    }

    /**
     * 現在の状況１タブ を返します。
     * @return 現在の状況１タブ
     */
    public IkenshoHoumonKangoShijishoSick2 getJyoukyou() {
        if(shoubyou2==null){
            shoubyou2 = new IkenshoSeishinkaHoumonKangoShijishoJyoukyou1();
        }
        return shoubyou2;
    }

    /**
     * 現在の状況２タブ を返します。
     * @return 現在の状況２タブ
     */
    public IkenshoHoumonKangoShijishoMindBody1 getJyoukyou2() {
        if(jiritudo==null){
            jiritudo = new IkenshoSeishinkaHoumonKangoShijishoJyoukyou2();
        }
        return jiritudo;
    }

    /**
     * 留意事項・指示事項タブ１ を返します。
     * @return 留意事項・指示事項タブ１
     */
    public IkenshoHoumonKangoShijishoRyuiShiji getRyuiShiji() {
        if(ryuiShiji==null){
            ryuiShiji = new IkenshoSeishinkaHoumonKangoShijishoRyuiShiji();
        }
        return ryuiShiji;
    }
    /**
     * 留意事項・指示事項タブ２ を返します。
     * @return 留意事項・指示事項タブ２
     */
    public IkenshoHoumonKangoShijishoRyuiShiji getRyuiShiji2() {
        if(ryuiShiji2==null){
            ryuiShiji2 = new IkenshoSeishinkaHoumonKangoShijishoRyuiShiji2();
        }
        return ryuiShiji2;
    }

    /**
     * 特記事項タブ を返します。
     * @return 特記事項タブ
     */
    public IkenshoHoumonKangoShijishoTenteki getTokki() {
        if(tenteki==null){
            tenteki = new IkenshoSeishinkaHoumonKangoShijishoTokki();
        }
        return tenteki;
    }

    /**
     * 医療機関タブ を返します。
     * @return 医療機関タブ
     */
    public IkenshoHoumonKangoShijishoIryoukikan getIryoukikan() {
        if(iryoukikan==null){
        	iryoukikan = new IkenshoSeishinkaHoumonKangoShijishoIryoukikan();
        }
        return iryoukikan;
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
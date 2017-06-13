package jp.or.med.orca.ikensho.affair;

import java.awt.Color;
import java.text.ParseException;

import jp.or.med.orca.ikensho.IkenshoConstants;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
public class IkenshoSeishinkaTokubetsuHoumonKangoShijisho extends IkenshoTokubetsuHoumonKangoShijisho {
    
    /**
     * コンストラクタ
     */    
    public IkenshoSeishinkaTokubetsuHoumonKangoShijisho() {
        super();
        setStatusText("精神科特別訪問看護指示書");
        buttons.setText("精神科特別訪問看護指示書");
        // 色の変更
        Color c = IkenshoConstants.COLOR_DISTINCTION;
        buttons.getBackButton().setBackground(c);
        update.setBackground(c);
        print.setBackground(c);
        buttons.setBackground(c);
    }
    
    /**
     * 追加処理時精神科特別訪問看護指示書固有情報項目設定処理
     * @param sb 追加SQL
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
     * 追加処理時精神科特別訪問看護指示書固有情報値設定処理
     * @param sb 追加SQL
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
     * 更新処理時精神科特別訪問看護指示書固有情報設定処理
     * @param sb 更新SQL
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
        return new IkenshoSeishinkaTokubetsuHoumonKangoShijishoPrintSetting(originalData).showModal();
    }
    
    /**
     * 精神科特別訪問看護指示書のフォーマット区分を返します。
     * @return 精神科特別訪問看護指示書のフォーマット区分 
     */
    protected String getFormatKubun() {
        // 精神科特別訪問看護指示書
        return "3";
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
     * 患者タブ を返します。
     * @return 患者タブ
     */
    public IkenshoHoumonKangoShijishoApplicant getApplicant() {
        if(applicant==null){
            applicant = new IkenshoSeishinkaTokubetsuHoumonKangoShijishoApplicant();
        }
        return applicant;
    }

    /**
     * 症状・主訴タブ を返します。
     * @return 症状・主訴タブ
     */
    public IkenshoTokubetsuHoumonKangoShijishoShojoShuso getShojoShuso() {
        if(shojoShuso==null){
            shojoShuso = new IkenshoSeishinkaTokubetsuHoumonKangoShijishoShojoShuso();
        }
        return shojoShuso;
    }

    /**
     * 留意事項・指示事項タブ を返します。
     * @return 留意事項・指示事項タブ
     */
    public IkenshoHoumonKangoShijishoRyuiShiji getRyuiShiji() {
        if(ryuiShiji==null){
            ryuiShiji = new IkenshoSeishinkaTokubetsuHoumonKangoShijishoRyuiShiji();
        }
        return ryuiShiji;
    }

    
    /**
     * 点滴注射指示タブ を返します。
     * @return 点滴注射指示タブ
     */
    public IkenshoHoumonKangoShijishoTenteki getTenteki() {
        if(tenteki==null){
            tenteki = new IkenshoSeishinkaTokubetsuHoumonKangoShijishoTenteki();
        }
        return tenteki;
    }

    /**
     * 医療機関タブ を返します。
     * @return 医療機関タブ
     */
    public IkenshoHoumonKangoShijishoIryoukikan getIryoukikan() {
        if(iryoukikan==null){
            iryoukikan = new IkenshoSeishinkaTokubetsuHoumonKangoShijishoIryoukikan();
        }
        return iryoukikan;
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
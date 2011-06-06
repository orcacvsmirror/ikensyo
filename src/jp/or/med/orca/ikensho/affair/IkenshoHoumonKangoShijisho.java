/** TODO <HEAD_IKENSYO> */
package jp.or.med.orca.ikensho.affair;

import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JTextField;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.event.IkenshoDocumentAffairApplicantNameChageListener;
import jp.or.med.orca.ikensho.event.IkenshoWriteDateChangeListener;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

public class IkenshoHoumonKangoShijisho extends IkenshoTabbableAffairContainer {

    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//    private static final ACPassiveKey PASSIVE_CHECK_KEY_SIJISYO = new ACPassiveKey(
//            "SIS_ORIGIN", new String[] { "PATIENT_NO", "EDA_NO" },
//            new Format[] { null, null }, "SIJISYO_LAST_TIME", "LAST_TIME");
//    private IkenshoHoumonKangoShijishoApplicant applicant = new IkenshoHoumonKangoShijishoApplicant();
//    private IkenshoHoumonKangoShijishoSick shoubyou = new IkenshoHoumonKangoShijishoSick();
//    // [ID:0000509][Masahiko Higuchi] 2009/06 add begin 画面調整に伴い調整
//    private IkenshoHoumonKangoShijishoSick2 shoubyou2 = new IkenshoHoumonKangoShijishoSick2();
//    // [ID:0000509][Masahiko Higuchi] 2009/06 add end
//    private IkenshoHoumonKangoShijishoMindBody1 jiritudo = new IkenshoHoumonKangoShijishoMindBody1();
//    private IkenshoHoumonKangoShijishoSpecial tokubetu = new IkenshoHoumonKangoShijishoSpecial();
//    private IkenshoHoumonKangoShijishoRyuiShiji ryuiShiji = new IkenshoHoumonKangoShijishoRyuiShiji();
//    private IkenshoHoumonKangoShijishoTenteki tenteki = new IkenshoHoumonKangoShijishoTenteki();
//    private IkenshoHoumonKangoShijishoIryoukikan iryoukikan = new IkenshoHoumonKangoShijishoIryoukikan();
    protected static final ACPassiveKey PASSIVE_CHECK_KEY_SIJISYO = new ACPassiveKey(
            "SIS_ORIGIN", new String[] { "PATIENT_NO", "EDA_NO", "FORMAT_KBN" },
            new Format[] { null, null, null }, "SIJISYO_LAST_TIME", "LAST_TIME");
    protected IkenshoHoumonKangoShijishoApplicant applicant;
    protected IkenshoHoumonKangoShijishoSick shoubyou;
    protected IkenshoHoumonKangoShijishoSick2 shoubyou2;
    protected IkenshoHoumonKangoShijishoMindBody1 jiritudo;
    protected IkenshoHoumonKangoShijishoSpecial tokubetu;
    protected IkenshoHoumonKangoShijishoRyuiShiji ryuiShiji;
    protected IkenshoHoumonKangoShijishoTenteki tenteki;
    protected IkenshoHoumonKangoShijishoIryoukikan iryoukikan;
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

    protected void reselectForPassiveCustom(IkenshoFirebirdDBManager dbm,
            VRMap data) throws Exception {
        StringBuffer sb;
        VRArrayList array;

        // 固有文書
        sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" LAST_TIME AS SIJISYO_LAST_TIME");
        sb.append(",CREATE_DT");
        sb.append(" FROM");
        sb.append(" SIS_ORIGIN");
        sb.append(" WHERE");
        sb.append(" (SIS_ORIGIN.PATIENT_NO=");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append(" AND (SIS_ORIGIN.EDA_NO=");
        sb.append(getEdaNo());
        sb.append(")");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begijn 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        sb.append(" AND ( ");
        sb.append(getCustomDocumentTableName());
        sb.append(".FORMAT_KBN =");
        sb.append(getFormatKubun());
        sb.append(")");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        array = (VRArrayList) dbm.executeQuery(sb.toString());
        hasOriginalDocument = array.getDataSize() > 0;
        if (hasOriginalDocument) {
            data.putAll((VRMap) array.getData());
        }

    }

    protected void doSelectCustomDocument(IkenshoFirebirdDBManager dbm)
            throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" *");
        sb.append(" FROM");
        sb.append(" SIS_ORIGIN");
        sb.append(" WHERE");
        sb.append(" (SIS_ORIGIN.PATIENT_NO=");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append(" AND (SIS_ORIGIN.EDA_NO=");
        sb.append(getEdaNo());
        sb.append(")");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begijn 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        sb.append(" AND ( ");
        sb.append(getCustomDocumentTableName());
        sb.append(".FORMAT_KBN =");
        sb.append(getFormatKubun());
        sb.append(")");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
        hasOriginalDocument = array.getDataSize() > 0;
        if (hasOriginalDocument) {
            VRMap data = (VRMap) array.getData();
            data.setData("SIJISYO_LAST_TIME", VRBindPathParser.get("LAST_TIME",
                    data));

            originalData.putAll(data);
        }

    }

    protected void doSelectBeforeCustomDocument(IkenshoFirebirdDBManager dbm)
            throws Exception {
        String patientNo = getPatientNo();
        if (patientNo != null) {

            StringBuffer sb = new StringBuffer();
            sb = new StringBuffer();
            sb.append("SELECT");
            sb.append(" *");
            sb.append(" FROM");
            sb.append(" SIS_ORIGIN");
            sb.append(" WHERE");
            sb.append(" (PATIENT_NO = ");
            sb.append(patientNo);
            sb.append(")");
            sb.append(" ORDER BY");
            sb.append(" EDA_NO DESC");
            VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
            if (array.size() > 0) {
//              [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能
//                VRMap data = (VRMap) array.getData();
//                originalData.putAll(data);
//                String fromSpanValue = "";
//                Object obj = VRBindPathParser
//                .get("SIJI_KIKAN_TO", originalData);
                for(int i=0, iEnd=array.size(); i<iEnd; i++){
                    VRMap data = (VRMap) array.getData(i);
                    if (getFormatKubun().equals(
                            ACCastUtilities.toString(VRBindPathParser.get(
                                    "FORMAT_KBN", data)))) {
                        //同区分文書から引き継ぎ
                        originalData.putAll(data);
                        break;
                    }
                }
                
                VRMap recentData = (VRMap) array.getData(0);
                String fromSpanValue = "";
                //指示期間開始日は区分に関係なく直近の文書から参照する
                Object obj = VRBindPathParser.get("SIJI_KIKAN_TO", recentData);
//              [ID:0000514][Tozo TANAKA] 2009/09/09 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

                if (obj instanceof String) {
                    String textVal = (String) obj;
                    if (!(IkenshoCommon.isNullText(textVal) || "0000年00月00日"
                            .equals(textVal))) {
                        try {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(VRDateParser.parse(textVal));
                            if (cal != null) {
                                // 前回の訪問看護指示期間終了の翌日とする
                                cal.add(Calendar.DATE, 1);
                                fromSpanValue = IkenshoConstants.FORMAT_ERA_YMD
                                        .format(cal.getTime());
                            }
                        } catch (Exception ex) {
                        }
                    }
                }

                VRBindPathParser.set("HOUMON_SIJISYO", originalData,
                        new Integer(0));
                VRBindPathParser.set("TENTEKI_SIJISYO", originalData,
                        new Integer(0));
                VRBindPathParser.set("SIJI_KIKAN_FROM", originalData,
                        fromSpanValue);
                VRBindPathParser.set("SIJI_KIKAN_TO", originalData, "");
                VRBindPathParser.set("TENTEKI_FROM", originalData, "");
                VRBindPathParser.set("TENTEKI_TO", originalData, "");
                // [ID:0000517][Tozo TANAKA] 2009/09/04 delete begin 【2009年度対応：訪問看護指示書】指示項目の引継ぎ
                //VRBindPathParser.set("TENTEKI_SIJI", originalData, "");
                // [ID:0000517][Tozo TANAKA] 2009/09/04 delete end 【2009年度対応：訪問看護指示書】指示項目の引継ぎ
                VRBindPathParser.set("OTHER_STATION_SIJI", originalData,
                        new Integer(1));
                VRBindPathParser.set("OTHER_STATION_NM", originalData, "");

                // 記入日は本日
                VRBindPathParser.set("KINYU_DT", originalData, new Date());

            }
              
        }
    }

    protected void doReservedPassiveCustom() throws ParseException {
        reservedPassive(PASSIVE_CHECK_KEY_SIJISYO, originalArray);
    }

    protected boolean showPrintDialogCustom() throws Exception {
    	
    	//[ID:0000635][Shin Fujihara] 2011/02/25 add begin 【2010年度要望対応】
    	//帳票を改ページするかのフラグを設定して、印刷画面に渡す
    	//改ページの警告なしならFALSE
    	if (warningMessage.length() == 0) {
    		originalData.put("IS_PAGE_BREAK", "FALSE");
    	} else {
    		originalData.put("IS_PAGE_BREAK", "TRUE");
    	}
        //[ID:0000635][Shin Fujihara] 2011/02/25 add end 【2010年度要望対応】
    	
        return new IkenshoHoumonKangoShijishoPrintSetting(originalData)
                .showModal();
    }

    protected String getCustomDocumentTableName() {
        return "SIS_ORIGIN";
    }

    protected String getAnotherDocumentTableName() {
        return "IKN_ORIGIN";
    }

    protected void doSelectDefaultCustomDocument(IkenshoFirebirdDBManager dbm) {

    }

    protected void addPassiveInsertTaskCustom() {
        addPassiveInsertTask(PASSIVE_CHECK_KEY_SIJISYO, 0);
    }

    protected void addPassiveUpdateTaskCustom() {
        addPassiveUpdateTask(PASSIVE_CHECK_KEY_SIJISYO, 0);
    }

    protected void doInsertCustomDocument(IkenshoFirebirdDBManager dbm)
            throws Exception {
        doInsertSijisho(dbm);
    }

    protected void doUpdateCustomDocument(IkenshoFirebirdDBManager dbm)
            throws Exception {
        doUpdateSijisho(dbm);
    }

    protected String getCustomDocumentType() {
        return IkenshoConstants.DOC_KBN_SIJISHO;
    }

    protected String getAnotherDocumentType() {
        return IkenshoConstants.DOC_KBN_IKENSHO;
    }

    /**
     * 指示書情報を追加します。
     * 
     * @param dbm DBManager
     * @throws ParseException 解析例外
     * @throws SQLException SQL例外
     */
    protected void doInsertSijisho(IkenshoFirebirdDBManager dbm)
            throws ParseException, SQLException {
        // 意見書
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO");
        sb.append(" SIS_ORIGIN");
        sb.append(" (");
        sb.append(" PATIENT_NO");

        sb.append(",EDA_NO");
        sb.append(",KINYU_DT");
        sb.append(",SIJI_CREATE_CNT");
        sb.append(",SIJI_YUKOU_KIKAN");
        sb.append(",SIJI_KANGO_KBN");
        sb.append(",STATION_NM");
        sb.append(",KINKYU_RENRAKU");
        sb.append(",FUZAIJI_TAIOU");
        sb.append(",RSS_RYUIJIKOU");
        sb.append(",REHA_SIJI_UMU");
        sb.append(",REHA_SIJI");
        sb.append(",JOKUSOU_SIJI_UMU");
        sb.append(",JOKUSOU_SIJI");
        sb.append(",SOUCHAKU_SIJI_UMU");
        sb.append(",SOUCHAKU_SIJI");
        sb.append(",RYUI_SIJI_UMU");
        sb.append(",RYUI_SIJI");
        sb.append(",SIJI_TOKKI");
        sb.append(",CREATE_DT");
        sb.append(",KOUSIN_DT");
        sb.append(",SIJI_KIKAN_FROM");
        sb.append(",SIJI_KIKAN_TO");
        sb.append(",YOUKAIGO_JOUKYOU");
        sb.append(",HOUMON_SIJISYO");
        sb.append(",TENTEKI_SIJISYO");
        sb.append(",TENTEKI_FROM");
        sb.append(",TENTEKI_TO");
        sb.append(",TENTEKI_SIJI");
        sb.append(",OTHER_STATION_SIJI");
        sb.append(",OTHER_STATION_NM");
        sb.append(",LAST_TIME");
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add begin 平成21年4月法改正対応
        sb.append(",JOKUSOU_NPUAP");
        sb.append(",JOKUSOU_DESIGN");
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add end
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begijn 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        sb.append(",FORMAT_KBN");
        
        appendInsertShijishoKeys(sb);
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

        sb.append(" )");
        sb.append(" VALUES");
        sb.append(" (");

        sb.append(getPatientNo());
        sb.append(",");
        sb.append(getEdaNo());
        sb.append(",");
        sb.append(getDBSafeDate("KINYU_DT", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SIJI_CREATE_CNT", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SIJI_YUKOU_KIKAN", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SIJI_KANGO_KBN", originalData));
        sb.append(",");
        sb.append(getDBSafeString("STATION_NM", originalData));
        sb.append(",");
        sb.append(getDBSafeString("KINKYU_RENRAKU", originalData));
        sb.append(",");
        sb.append(getDBSafeString("FUZAIJI_TAIOU", originalData));
        sb.append(",");
        sb.append(getDBSafeString("RSS_RYUIJIKOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("REHA_SIJI_UMU", originalData));
        sb.append(",");
        sb.append(getDBSafeString("REHA_SIJI", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("JOKUSOU_SIJI_UMU", originalData));
        sb.append(",");
        sb.append(getDBSafeString("JOKUSOU_SIJI", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SOUCHAKU_SIJI_UMU", originalData));
        sb.append(",");
        sb.append(getDBSafeString("SOUCHAKU_SIJI", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("RYUI_SIJI_UMU", originalData));
        sb.append(",");
        sb.append(getDBSafeString("RYUI_SIJI", originalData));
        sb.append(",");
        sb.append(getDBSafeString("SIJI_TOKKI", originalData));
        sb.append(",CURRENT_TIMESTAMP");
        sb.append(",CURRENT_TIMESTAMP");
        sb.append(",");
        sb.append(getDBSafeString("SIJI_KIKAN_FROM", originalData));
        sb.append(",");
        sb.append(getDBSafeString("SIJI_KIKAN_TO", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("YOUKAIGO_JOUKYOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMON_SIJISYO", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("TENTEKI_SIJISYO", originalData));
        sb.append(",");
        sb.append(getDBSafeString("TENTEKI_FROM", originalData));
        sb.append(",");
        sb.append(getDBSafeString("TENTEKI_TO", originalData));
        sb.append(",");
        sb.append(getDBSafeString("TENTEKI_SIJI", originalData));
        // sb.append(",");
        // sb.append(getDBSafeNumber("OTHER_STATION_SIJI", originalData));
        // sb.append(",");
        // sb.append(getDBSafeString("OTHER_STATION_NM", originalData));
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "OTHER_STATION_SIJI", new String[] { "OTHER_STATION_NM" }, 2,
                true);
        sb.append(",CURRENT_TIMESTAMP");
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add begin 平成21年4月法改正対応
        sb.append(",");
        sb.append(getDBSafeString("JOKUSOU_NPUAP", originalData));
        sb.append(",");
        sb.append(getDBSafeString("JOKUSOU_DESIGN", originalData));
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add end
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begijn 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        sb.append(",");
        sb.append(getFormatKubun());

        appendInsertShijishoValues(sb);
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

        sb.append(")");

        dbm.executeUpdate(sb.toString());

        hasOriginalDocument = true;
    }

    /**
     * 指示書情報を更新します。
     * 
     * @param dbm DBManager
     * @throws Exception 処理例外
     */
    protected void doUpdateSijisho(IkenshoFirebirdDBManager dbm)
            throws Exception {

        // 意見書
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE");
        sb.append(" SIS_ORIGIN");
        sb.append(" SET");

        sb.append(" KINYU_DT = ");
        sb.append(getDBSafeDate("KINYU_DT", originalData));

        sb.append(",SIJI_CREATE_CNT = ");
        sb.append(getDBSafeNumber("SIJI_CREATE_CNT", originalData));
        sb.append(",SIJI_YUKOU_KIKAN = ");
        sb.append(getDBSafeNumber("SIJI_YUKOU_KIKAN", originalData));
        sb.append(",SIJI_KANGO_KBN = ");
        sb.append(getDBSafeNumber("SIJI_KANGO_KBN", originalData));
        sb.append(",STATION_NM = ");
        sb.append(getDBSafeString("STATION_NM", originalData));
        sb.append(",KINKYU_RENRAKU = ");
        sb.append(getDBSafeString("KINKYU_RENRAKU", originalData));
        sb.append(",FUZAIJI_TAIOU = ");
        sb.append(getDBSafeString("FUZAIJI_TAIOU", originalData));
        sb.append(",RSS_RYUIJIKOU = ");
        sb.append(getDBSafeString("RSS_RYUIJIKOU", originalData));
        sb.append(",REHA_SIJI_UMU = ");
        sb.append(getDBSafeNumber("REHA_SIJI_UMU", originalData));
        sb.append(",REHA_SIJI = ");
        sb.append(getDBSafeString("REHA_SIJI", originalData));
        sb.append(",JOKUSOU_SIJI_UMU = ");
        sb.append(getDBSafeNumber("JOKUSOU_SIJI_UMU", originalData));
        sb.append(",JOKUSOU_SIJI = ");
        sb.append(getDBSafeString("JOKUSOU_SIJI", originalData));
        sb.append(",SOUCHAKU_SIJI_UMU = ");
        sb.append(getDBSafeNumber("SOUCHAKU_SIJI_UMU", originalData));
        sb.append(",SOUCHAKU_SIJI = ");
        sb.append(getDBSafeString("SOUCHAKU_SIJI", originalData));
        sb.append(",RYUI_SIJI_UMU = ");
        sb.append(getDBSafeNumber("RYUI_SIJI_UMU", originalData));
        sb.append(",RYUI_SIJI = ");
        sb.append(getDBSafeString("RYUI_SIJI", originalData));
        sb.append(",SIJI_TOKKI = ");
        sb.append(getDBSafeString("SIJI_TOKKI", originalData));
        // sb.append(",CREATE_DT = ");
        // sb.append(getDBSafeTime("CREATE_DT", originalData));
        sb.append(",KOUSIN_DT = CURRENT_TIMESTAMP");
        sb.append(",SIJI_KIKAN_FROM = ");
        sb.append(getDBSafeString("SIJI_KIKAN_FROM", originalData));
        sb.append(",SIJI_KIKAN_TO = ");
        sb.append(getDBSafeString("SIJI_KIKAN_TO", originalData));
        sb.append(",YOUKAIGO_JOUKYOU = ");
        sb.append(getDBSafeNumber("YOUKAIGO_JOUKYOU", originalData));
        sb.append(",HOUMON_SIJISYO = ");
        sb.append(getDBSafeNumber("HOUMON_SIJISYO", originalData));
        sb.append(",TENTEKI_SIJISYO = ");
        sb.append(getDBSafeNumber("TENTEKI_SIJISYO", originalData));
        sb.append(",TENTEKI_FROM = ");
        sb.append(getDBSafeString("TENTEKI_FROM", originalData));
        sb.append(",TENTEKI_TO = ");
        sb.append(getDBSafeString("TENTEKI_TO", originalData));
        sb.append(",TENTEKI_SIJI = ");
        sb.append(getDBSafeString("TENTEKI_SIJI", originalData));
        // sb.append(",OTHER_STATION_SIJI = ");
        // sb.append(getDBSafeNumber("OTHER_STATION_SIJI", originalData));
        // sb.append(",OTHER_STATION_NM = ");
        // sb.append(getDBSafeString("OTHER_STATION_NM", originalData));
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                "OTHER_STATION_SIJI", new String[] { "OTHER_STATION_NM" }, 2,
                true);
        sb.append(",LAST_TIME = CURRENT_TIMESTAMP");
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add begin 平成21年4月法改正対応
        sb.append(",JOKUSOU_NPUAP = ");
        sb.append(getDBSafeString("JOKUSOU_NPUAP", originalData));
        sb.append(",JOKUSOU_DESIGN = ");
        sb.append(getDBSafeString("JOKUSOU_DESIGN", originalData));
        
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begijn 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        appendUpdateShijishoStetement(sb);
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add end
        sb.append(" WHERE");
        sb.append(" (SIS_ORIGIN.PATIENT_NO = ");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append("AND(SIS_ORIGIN.EDA_NO = ");
        sb.append(getEdaNo());
        sb.append(")");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begijn 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        sb.append(" AND ( ");
        sb.append(getCustomDocumentTableName());
        sb.append(".FORMAT_KBN =");
        sb.append(getFormatKubun());
        sb.append(")");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

        dbm.executeUpdate(sb.toString());

    }

    /**
     * コンストラクタです。
     */
    public IkenshoHoumonKangoShijisho() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        setStatusText("訪問看護指示書");
        buttons.setTitle("訪問看護指示書");

        // [ID:0000514][Tozo TANAKA] 2009/09/07 remove begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//        tabArray.add(applicant);
//        tabArray.add(shoubyou);
//        // [ID:0000509][Masahiko Higuchi] 2009/06 add begin 画面調整に伴い調整
//        tabArray.add(shoubyou2);
//        // [ID:0000509][Masahiko Higuchi] 2009/06 add end
//        tabArray.add(jiritudo);
//        tabArray.add(tokubetu);
//        tabArray.add(ryuiShiji);
//        tabArray.add(tenteki);
//        tabArray.add(iryoukikan);
        // [ID:0000514][Tozo TANAKA] 2009/09/07 remove end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

        // [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//        jiritudo.setFollowDisabledComponents(new JComponent[] { tabs, update,
//                print, buttons.getBackButton() });
//
//        applicant
//                .addWriteDateChangeListener(new IkenshoWriteDateChangeListener() {
//                    public void writeDataChanged(EventObject e) {
//                        try {
//                            editorDates.setText(IkenshoConstants.FORMAT_ERA_YMD
//                                    .format(((IkenshoEraDateTextField) e
//                                            .getSource()).getDate()));
//                        } catch (Exception ex) {
//                        }
//                    }
//                });
//        applicant
//                .addApplicantNameChageListener(new IkenshoDocumentAffairApplicantNameChageListener() {
//                    public void nameChanged(EventObject e) {
//                        editorName.setText(((JTextField) e.getSource())
//                                .getText());
//                    }
//                });
        getJiritudo().setFollowDisabledComponents(new JComponent[] { tabs, update,
                print, buttons.getBackButton() });

        getApplicant()
                .addWriteDateChangeListener(new IkenshoWriteDateChangeListener() {
                    public void writeDataChanged(EventObject e) {
                        try {
                            editorDates.setText(IkenshoConstants.FORMAT_ERA_YMD
                                    .format(((IkenshoEraDateTextField) e
                                            .getSource()).getDate()));
                        } catch (Exception ex) {
                        }
                    }
                });
        getApplicant()
                .addApplicantNameChageListener(new IkenshoDocumentAffairApplicantNameChageListener() {
                    public void nameChanged(EventObject e) {
                        editorName.setText(((JTextField) e.getSource())
                                .getText());
                    }
                });
        // [ID:0000514][Tozo TANAKA] 2009/09/09 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

    }

    private void jbInit() throws Exception {
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//        // tab
//        tabs.addTab("患者", applicant);
//        // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴い調整
//        tabs.addTab("傷病１", shoubyou);
//        tabs.addTab("傷病２", shoubyou2);
//        // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
//        // [ID:0000463][Tozo TANAKA] 2009/03/20 replace begin 平成21年4月法改正対応
//        //tabs.addTab("日常生活自立度", jiritudo);
//        tabs.addTab("日常生活自立度・褥瘡の深さ", jiritudo);
//        // [ID:0000463][Tozo TANAKA] 2009/03/20 replace end
//        tabs.addTab("特別な医療", tokubetu);
//        tabs.addTab("留意事項・指示事項", ryuiShiji);
//        tabs.addTab("特記・訪問点滴注射", tenteki);
//        tabs.addTab("医療機関", iryoukikan);
        addTabs();
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

    }
    
    // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
    protected void addTabs() {

        tabs.addTab("患者", getApplicant());
        tabs.addTab("傷病１", getShoubyou());
        tabs.addTab("傷病２", getShoubyou2());
        tabs.addTab("日常生活自立度・褥瘡の深さ", getJiritudo());
        tabs.addTab("特別な医療", getTokubetu());
        tabs.addTab("留意事項・指示事項", getRyuiShiji());
        tabs.addTab("特記・訪問点滴注射", getTenteki());
        tabs.addTab("医療機関", getIryoukikan());

        
        tabArray.add(getApplicant());
        tabArray.add(getShoubyou());
        tabArray.add(getShoubyou2());
        tabArray.add(getJiritudo());
        tabArray.add(getTokubetu());
        tabArray.add(getRyuiShiji());
        tabArray.add(getTenteki());
        tabArray.add(getIryoukikan());

    }
    
    /**
     * 患者タブ を返します。
     * @return 患者タブ
     */
    public IkenshoHoumonKangoShijishoApplicant getApplicant() {
        if(applicant==null){
            applicant = new IkenshoHoumonKangoShijishoApplicant();
        }
        return applicant;
    }

    /**
     * 医療機関タブ を返します。
     * @return 医療機関タブ
     */
    public IkenshoHoumonKangoShijishoIryoukikan getIryoukikan() {
        if(iryoukikan==null){
            iryoukikan = new IkenshoHoumonKangoShijishoIryoukikan();
        }
        return iryoukikan;
    }

    /**
     * 日常生活自立度・褥瘡の深さタブ を返します。
     * @return 日常生活自立度・褥瘡の深さタブ
     */
    public IkenshoHoumonKangoShijishoMindBody1 getJiritudo() {
        if(jiritudo==null){
            jiritudo = new IkenshoHoumonKangoShijishoMindBody1();
        }
        return jiritudo;
    }
    /**
     * 留意事項・指示事項タブ を返します。
     * @return 留意事項・指示事項タブ
     */
    public IkenshoHoumonKangoShijishoRyuiShiji getRyuiShiji() {
        if(ryuiShiji==null){
            ryuiShiji = new IkenshoHoumonKangoShijishoRyuiShiji();
        }
        return ryuiShiji;
    }

    /**
     * 傷病１タブ を返します。
     * @return 傷病１タブ
     */
    public IkenshoHoumonKangoShijishoSick getShoubyou() {
        if(shoubyou==null){
            shoubyou = new IkenshoHoumonKangoShijishoSick();
        }
        return shoubyou;
    }

    /**
     * 傷病２タブ を返します。
     * @return 傷病２タブ
     */
    public IkenshoHoumonKangoShijishoSick2 getShoubyou2() {
        if(shoubyou2==null){
            shoubyou2 = new IkenshoHoumonKangoShijishoSick2();
        }
        return shoubyou2;
    }

    /**
     * 特記・訪問点滴注射タブ を返します。
     * @return 特記・訪問点滴注射タブ
     */
    public IkenshoHoumonKangoShijishoTenteki getTenteki() {
        if(tenteki==null){
            tenteki = new IkenshoHoumonKangoShijishoTenteki();
        }
        return tenteki;
    }

    /**
     * 特別な医療タブ を返します。
     * @return 特別な医療タブ
     */
    public IkenshoHoumonKangoShijishoSpecial getTokubetu() {
        if(tokubetu==null){
            tokubetu = new IkenshoHoumonKangoShijishoSpecial();
        }
        return tokubetu;
    }

    /**
     * 訪問看護指示書のフォーマット区分を返します。
     * @return 訪問看護指示書のフォーマット区分 
     */
    protected String getFormatKubun() {
        //訪問看護指示書
        return "0";
    }

    /**
     * overrideして指示書挿入時のSQLキー句を追加します。
     * 
     * @param sb 追加先
     */
    protected void appendInsertShijishoKeys(StringBuffer sb) {

    }

    /**
     * overrideして指示書挿入時のSQLバリュー句を追加します。
     * 
     * @throws ParseException 解析例外
     * @param sb 追加先
     */
    protected void appendInsertShijishoValues(StringBuffer sb)
            throws ParseException {

    }

    /**
     * overrideして指示書更新時のSQLキー句を追加します。
     * 
     * @param sb 追加先
     * @throws ParseException 解析例外
     */
    protected void appendUpdateShijishoStetement(StringBuffer sb)
            throws ParseException {

    }
    // [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
    


    //2009/01/16 [Tozo Tanaka] Add - begin
    protected int getMedicineViewCount() {
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//        int ikenshoCount = super.getMedicineViewCount();
//        int shijishoCount = ikenshoCount;
//        try {
//            if (
//                    ACFrame
//                    .getInstance()
//                    .hasProperty(
//                            "DocumentSetting/MedicineViewCountOfHoumonKangoShijishoFixed6")
//                    && ACCastUtilities
//                            .toBoolean(
//                                    ACFrame
//                                            .getInstance()
//                                            .getProperty(
//                                                    "DocumentSetting/MedicineViewCountOfHoumonKangoShijishoFixed6"),
//                                    false)
//            ) {
//                //指示書設定を優先
//                shijishoCount = 6;
//            }
//        } catch (Exception e) {
//        }
//        return shijishoCount;
        return 8;
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end 【主治医医見書・医師医見書】薬剤名テキストの追加
  }
    //2009/01/16 [Tozo Tanaka] Add - end

//  [ID:0000514][Tozo TANAKA] 2009/09/09 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
    public void doSelect()throws Exception {
        if(originalData!=null){
            originalData.put("FORMAT_KBN", getFormatKubun());
        }
        super.doSelect();
    }
//  [ID:0000514][Tozo TANAKA] 2009/09/09 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能
    
    
    //[ID:0000635][Shin Fujihara] 2011/02/25 add begin 【2010年度要望対応】
    private StringBuffer warningMessage = null;
    public void setWarningMessage(String msg) {
    	warningMessage.append("・" + msg + IkenshoConstants.LINE_SEPARATOR);
    }
    // --- Override ---
	protected boolean canControlUpdate() throws Exception {
		// エラーチェック
		Iterator it = tabArray.iterator();
		while (it.hasNext()) {
			IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer)it.next();
			if (!tab.noControlError()) {
				tabs.setSelectedComponent(tab);
				return false;
			}
		}
		// 警告チェック
		warningMessage = new StringBuffer();
		it = tabArray.iterator();
		while (it.hasNext()) {
			IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer)it.next();
			if (!tab.noControlWarning()) {
				tabs.setSelectedComponent(tab);
				return false;
			}
		}
		
		if (warningMessage.length() != 0) {
			 
			ACMessageBox.show("以下の項目が1枚印刷の文字数制限を超えているため2枚で出力します。"
							  + IkenshoConstants.LINE_SEPARATOR
							  + warningMessage.toString()
							  + "1枚印刷に収めたい場合は、キャンセルして、入力しなおしてください。");
		}

		return true;
	}
    //[ID:0000635][Shin Fujihara] 2011/02/25 add end 【2010年度要望対応】
}

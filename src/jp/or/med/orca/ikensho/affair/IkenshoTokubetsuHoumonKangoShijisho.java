package jp.or.med.orca.ikensho.affair;

import java.text.ParseException;

//[ID:0000514][Tozo TANAKA] 2009/09/07 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
public class IkenshoTokubetsuHoumonKangoShijisho extends
        IkenshoHoumonKangoShijisho {
    
    protected IkenshoTokubetsuHoumonKangoShijishoShojoShuso shojoShuso;
    
    public IkenshoTokubetsuHoumonKangoShijisho() {
        super();
        
        // ステータスバー
        setStatusText("特別訪問看護指示書");
        buttons.setText("特別訪問看護指示書");
    }
    

    protected void addTabs(){
        tabs.addTab("患者", getApplicant());
        //tabs.addTab("傷病１", getShoubyou());
        //tabs.addTab("傷病２", getShoubyou2());
        //tabs.addTab("日常生活自立度・褥瘡の深さ", getJiritudo());
        //tabs.addTab("特別な医療", getTokubetu());
        tabs.addTab("症状・主訴", getShojoShuso());
        tabs.addTab("留意事項・指示事項", getRyuiShiji());
        tabs.addTab("点滴注射指示", getTenteki());
        tabs.addTab("医療機関", getIryoukikan());

        tabArray.clear();
        tabArray.add(getApplicant());
//        tabArray.add(getShoubyou());
//        tabArray.add(getShoubyou2());
//        tabArray.add(getJiritudo());
//        tabArray.add(getTokubetu());
        tabArray.add(getShojoShuso());
        tabArray.add(getRyuiShiji());
        tabArray.add(getTenteki());
        tabArray.add(getIryoukikan());
        
    }
    
    protected String getFormatKubun() {
        //特別訪問看護指示書
        return "1";
    }

    protected boolean showPrintDialogCustom() throws Exception {
        return new IkenshoTokubetsuHoumonKangoShijishoPrintSetting(originalData)
                .showModal();
    }
    
    public IkenshoHoumonKangoShijishoApplicant getApplicant() {
        if(applicant==null){
            applicant = new IkenshoTokubetsuHoumonKangoShijishoApplicant();
        }
        return applicant;
    }
    public IkenshoHoumonKangoShijishoSick2 getShoubyou2() {
        if(shoubyou2==null){
            shoubyou2 = new IkenshoTokubetsuHoumonKangoShijishoSick2();
        }
        return shoubyou2;
    }
    public IkenshoHoumonKangoShijishoRyuiShiji getRyuiShiji() {
        if(ryuiShiji==null){
            ryuiShiji = new IkenshoTokubetsuHoumonKangoShijishoRyuiShiji();
        }
        return ryuiShiji;
    }
    public IkenshoTokubetsuHoumonKangoShijishoShojoShuso getShojoShuso() {
        if(shojoShuso==null){
            shojoShuso = new IkenshoTokubetsuHoumonKangoShijishoShojoShuso();
        }
        return shojoShuso;
    }
    
    public IkenshoHoumonKangoShijishoTenteki getTenteki() {
        if(tenteki==null){
            tenteki = new IkenshoTokubetsuHoumonKangoShijishoTenteki();
        }
        return tenteki;
    }
    public IkenshoHoumonKangoShijishoIryoukikan getIryoukikan() {
        if(iryoukikan==null){
            iryoukikan = new IkenshoTokubetsuHoumonKangoShijishoIryoukikan();
        }
        return iryoukikan;
    }
    

    protected void appendInsertShijishoKeys(StringBuffer sb) {
        super.appendInsertShijishoKeys(sb);

        sb.append(",TOKUBETSU_SHOJO_SHUSO");
        sb.append(",TOKUBETSU_RYUI");
        sb.append(",TOKUBETSU_CHUSHA_SHIJI");
        sb.append(",TOKUBETSU_KINKYU_RENRAKU");
    }

    protected void appendInsertShijishoValues(StringBuffer sb)
            throws ParseException {
        super.appendInsertShijishoValues(sb);

        sb.append(",");
        sb.append(getDBSafeString("TOKUBETSU_SHOJO_SHUSO", originalData));
        sb.append(",");
        sb.append(getDBSafeString("TOKUBETSU_RYUI", originalData));
        sb.append(",");
        sb.append(getDBSafeString("TOKUBETSU_CHUSHA_SHIJI", originalData));
        sb.append(",");
        sb.append(getDBSafeString("TOKUBETSU_KINKYU_RENRAKU", originalData));
    }

    protected void appendUpdateShijishoStetement(StringBuffer sb)
            throws ParseException {
        super.appendUpdateShijishoStetement(sb);

        sb.append(",TOKUBETSU_SHOJO_SHUSO = ");
        sb.append(getDBSafeString("TOKUBETSU_SHOJO_SHUSO", originalData));
        sb.append(",TOKUBETSU_RYUI = ");
        sb.append(getDBSafeString("TOKUBETSU_RYUI", originalData));
        sb.append(",TOKUBETSU_CHUSHA_SHIJI = ");
        sb.append(getDBSafeString("TOKUBETSU_CHUSHA_SHIJI", originalData));
        sb.append(",TOKUBETSU_KINKYU_RENRAKU = ");
        sb.append(getDBSafeString("TOKUBETSU_KINKYU_RENRAKU", originalData));
    }
    
    
}
//[ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

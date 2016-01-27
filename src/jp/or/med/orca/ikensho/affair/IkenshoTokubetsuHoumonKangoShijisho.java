package jp.or.med.orca.ikensho.affair;

import java.text.ParseException;

//[ID:0000514][Tozo TANAKA] 2009/09/07 add begin y2009NxÎFKâĆìwŠzÁÊwŠÌÇ@\  
public class IkenshoTokubetsuHoumonKangoShijisho extends
        IkenshoHoumonKangoShijisho {
    
    protected IkenshoTokubetsuHoumonKangoShijishoShojoShuso shojoShuso;
    
    public IkenshoTokubetsuHoumonKangoShijisho() {
        super();
        
        // Xe[^Xo[
        setStatusText("ÁÊKâĆìwŠ");
        buttons.setText("ÁÊKâĆìwŠ");
// [ID:0000786][Satoshi Tokusari] 2014/10 add-Start KâĆìwŠìŹÌű«pŹłîńÌIđÎ
        read.setVisible(false);
// [ID:0000786][Satoshi Tokusari] 2014/10 add-End
    }
    

    protected void addTabs(){
        tabs.addTab("łÒ", getApplicant());
        //tabs.addTab("aP", getShoubyou());
        //tabs.addTab("aQ", getShoubyou2());
        //tabs.addTab("úí¶©§xEćńáÌ[ł", getJiritudo());
        //tabs.addTab("ÁÊÈăĂ", getTokubetu());
        tabs.addTab("ÇóEći", getShojoShuso());
        tabs.addTab("ŻÓEwŠ", getRyuiShiji());
        tabs.addTab("_HËwŠ", getTenteki());
        tabs.addTab("ăĂ@Ö", getIryoukikan());

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
        //ÁÊKâĆìwŠ
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
//[ID:0000514][Tozo TANAKA] 2009/09/07 add end y2009NxÎFKâĆìwŠzÁÊwŠÌÇ@\  

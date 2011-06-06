package jp.or.med.orca.ikensho.affair;

import java.text.ParseException;

//[ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
public class IkenshoTokubetsuHoumonKangoShijisho extends
        IkenshoHoumonKangoShijisho {
    
    protected IkenshoTokubetsuHoumonKangoShijishoShojoShuso shojoShuso;
    
    public IkenshoTokubetsuHoumonKangoShijisho() {
        super();
        
        // �X�e�[�^�X�o�[
        setStatusText("���ʖK��Ō�w����");
        buttons.setText("���ʖK��Ō�w����");
    }
    

    protected void addTabs(){
        tabs.addTab("����", getApplicant());
        //tabs.addTab("���a�P", getShoubyou());
        //tabs.addTab("���a�Q", getShoubyou2());
        //tabs.addTab("���퐶�������x�E��ጂ̐[��", getJiritudo());
        //tabs.addTab("���ʂȈ��", getTokubetu());
        tabs.addTab("�Ǐ�E��i", getShojoShuso());
        tabs.addTab("���ӎ����E�w������", getRyuiShiji());
        tabs.addTab("�_�H���ˎw��", getTenteki());
        tabs.addTab("��Ë@��", getIryoukikan());

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
        //���ʖK��Ō�w����
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
//[ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

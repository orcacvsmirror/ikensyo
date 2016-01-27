package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

// [ID:0000514][Tozo TANAKA] 2009/09/07 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
public class IkenshoTokubetsuHoumonKangoShijishoTenteki extends
        IkenshoHoumonKangoShijishoTenteki {
    protected ACGroupBox tokubetsuGroup;
    protected IkenshoHoumonKangoShijishoInstructContainer tokubetsuTenteki;

    public IkenshoTokubetsuHoumonKangoShijishoTenteki() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void jbInit()throws Exception{
        
        getTitle().setText("点滴注射指示内容（投与薬剤・投与量・投与方法等）");
        getGroup().setVisible(false);
        
        
        getTokubetsuTenteki().setColumns(100);
        getTokubetsuTenteki().setRows(14);
        getTokubetsuTenteki().setMaxRows(14);
        getTokubetsuTenteki().setMaxLength(700);
        getTokubetsuTenteki().setShowSelectVisible(false);
        getTokubetsuTenteki().setCheckVisible(false);
        getTokubetsuTenteki().setTextBindPath("TOKUBETSU_CHUSHA_SHIJI");
        getTokubetsuTenteki().setCaption("点滴注射指示内容");
        getTokubetsuTenteki().setTitle("点滴注射指示内容（投与薬剤・投与量・投与方法等）（700文字/14行以内）");
        getTokubetsuTenteki().fitTextArea();
        getTokubetsuGroup().add(getTokubetsuTenteki(), VRLayout.NORTH);
        this.add(getTokubetsuGroup(), VRLayout.CLIENT);
    }

    /**
     * tokubetsuGroup を返します。
     * @return tokubetsuGroup
     */
    protected ACGroupBox getTokubetsuGroup() {
        if(tokubetsuGroup==null){
            tokubetsuGroup = new ACGroupBox();
        }
        return tokubetsuGroup;
    }

    /**
     * tokubetsuTenteki を返します。
     * @return tokubetsuTenteki
     */
    protected IkenshoHoumonKangoShijishoInstructContainer getTokubetsuTenteki() {
        if(tokubetsuTenteki==null){
            tokubetsuTenteki = new IkenshoHoumonKangoShijishoInstructContainer();
        }
        return tokubetsuTenteki;
    }
    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        getTokubetsuTenteki().setLoadRecentSetting(
                dbm,
                getMasterAffair().getPatientNo(),
                "TENTEKI_SIJI",
                "0",
                "読込(L)",
                'L',
                "最新の訪問看護指示書に登録した「在宅患者訪問点滴注射に関する指示」を読み込みます。" + ACConstants.LINE_SEPARATOR
                        + "よろしいですか？", true);
        
    }
}
// [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

//[ID:0000514][Tozo TANAKA] 2009/09/07 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
public class IkenshoTokubetsuHoumonKangoShijishoRyuiShiji extends
        IkenshoHoumonKangoShijishoRyuiShiji {
    protected ACGroupBox tokubetsuGroup;
    protected IkenshoHoumonKangoShijishoInstructContainer tokubetsuRyuijiko;

    public IkenshoTokubetsuHoumonKangoShijishoRyuiShiji() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit()throws Exception{
        
        getRyuuiShijiTitle().setText("留意事項及び指示事項");
        getRyuiShijiGrp().setVisible(false);
        
        
        getTokubetsuRyuijiko().setColumns(100);
        getTokubetsuRyuijiko().setRows(14);
        getTokubetsuRyuijiko().setMaxRows(14);
        getTokubetsuRyuijiko().setMaxLength(700);
        getTokubetsuRyuijiko().fitTextArea();
        getTokubetsuRyuijiko().setShowSelectVisible(false);
        getTokubetsuRyuijiko().setCheckVisible(false);
        getTokubetsuRyuijiko().setTextBindPath("TOKUBETSU_RYUI");
        getTokubetsuRyuijiko().setCaption("留意事項及び指示事項");
//      [ID:0000558][Tozo TANAKA] 2009/10/13 replace begin 【障害】特別指示書の帳票印字文言に誤字  
//        getTokubetsuRyuijiko().setTitle(
//                "留意事項及び指示事項（700文字/14行以内）" + IkenshoConstants.LINE_SEPARATOR
//                        + "（注：点滴注射薬の相互作用・福作用についての留意点があれば記載してください。）");
        getTokubetsuRyuijiko().setTitle(
                "留意事項及び指示事項（700文字/14行以内）" + IkenshoConstants.LINE_SEPARATOR
                        + "（注：点滴注射薬の相互作用・副作用についての留意点があれば記載してください。）");
//      [ID:0000558][Tozo TANAKA] 2009/10/13 replace end 【障害】特別指示書の帳票印字文言に誤字  
        
        getTokubetsuGroup().add(getTokubetsuRyuijiko(), VRLayout.NORTH);
        this.add(getTokubetsuGroup(), VRLayout.CLIENT);
    }

    /**
     * tokubetsuShusoGroup を返します。
     * @return tokubetsuShusoGroup
     */
    protected ACGroupBox getTokubetsuGroup() {
        if(tokubetsuGroup==null){
            tokubetsuGroup = new ACGroupBox();
        }
        return tokubetsuGroup;
    }

    /**
     * tokubetsuRyuijiko を返します。
     * @return tokubetsuRyuijiko
     */
    protected IkenshoHoumonKangoShijishoInstructContainer getTokubetsuRyuijiko() {
        if(tokubetsuRyuijiko==null){
            tokubetsuRyuijiko = new IkenshoHoumonKangoShijishoInstructContainer();
        }
        return tokubetsuRyuijiko;
    }
    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        getTokubetsuRyuijiko().setLoadRecentSetting(
                dbm,
                getMasterAffair().getPatientNo(),
                "SIJI_TOKKI",
                "0",
                "読込(L)",
                'L',
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
//                "最新の訪問看護指示書に登録した「特記すべき留意事項」を読み込みます。" + ACConstants.LINE_SEPARATOR
                "最新の（精神科）訪問看護指示書に登録した「特記すべき留意事項」を読み込みます。" + ACConstants.LINE_SEPARATOR
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start
                        + "よろしいですか？", true);
        
    }
    
}
//[ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

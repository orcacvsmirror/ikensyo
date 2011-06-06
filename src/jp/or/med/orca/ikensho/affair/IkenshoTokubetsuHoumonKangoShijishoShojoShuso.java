package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

//[ID:0000514][Masahiko Higuchi] 2009/09/08 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能
public class IkenshoTokubetsuHoumonKangoShijishoShojoShuso extends
        IkenshoTabbableChildAffairContainer {

    protected IkenshoHoumonKangoShijishoInstructContainer tokubetsuShuso;
    protected ACGroupBox tokubetsuShusoGroup;
    protected IkenshoDocumentTabTitleLabel shojoShusoTitle;
    
    public IkenshoTokubetsuHoumonKangoShijishoShojoShuso() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * 画面構築処理
     * 
     * @throws Exception
     */
    private void jbInit()throws Exception{
        this.setLayout(new VRLayout());
        getShojoShusoTitle().setText("症状・主訴");
        
        
        getTokubetsuShuso().setColumns(100);
        getTokubetsuShuso().setRows(15);
        getTokubetsuShuso().setMaxRows(14);
        getTokubetsuShuso().setMaxLength(700);
        getTokubetsuShuso().setShowSelectVisible(false);
        getTokubetsuShuso().setCheckVisible(false);
        getTokubetsuShuso().setTextBindPath("TOKUBETSU_SHOJO_SHUSO");
        getTokubetsuShuso().setCaption("症状・主訴");
        getTokubetsuShuso().setTitle("症状・主訴（700文字/14行以内）");
        
        getTokubetsuShusoGroup().add(getTokubetsuShuso(), VRLayout.NORTH);
        this.add(getShojoShusoTitle(), VRLayout.NORTH);
        this.add(getTokubetsuShusoGroup(), VRLayout.CLIENT);
    }
    
    /**
     * tokubetsuShuso を返します。
     * @return tokubetsuShuso
     */
    protected IkenshoHoumonKangoShijishoInstructContainer getTokubetsuShuso() {
        if(tokubetsuShuso==null){
            tokubetsuShuso = new IkenshoHoumonKangoShijishoInstructContainer();
        }
        return tokubetsuShuso;
    }
    
    /**
     * tokubetsuShusoGroup を返します。
     * @return tokubetsuShusoGroup
     */
    protected ACGroupBox getTokubetsuShusoGroup() {
        if(tokubetsuShusoGroup==null){
            tokubetsuShusoGroup = new ACGroupBox();
        }
        return tokubetsuShusoGroup;
    }
    
    /**
     * ryuuiShijiTitle を返します。
     * 
     * @return ryuuiShijiTitle
     */
    protected IkenshoDocumentTabTitleLabel getShojoShusoTitle() {
        if (shojoShusoTitle == null) {
            shojoShusoTitle = new IkenshoDocumentTabTitleLabel();
        }
        return shojoShusoTitle;
    }
    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        getTokubetsuShuso().setLoadRecentSetting(
                dbm,
                getMasterAffair().getPatientNo(),
                "MT_STS",
                "0",
                "読込(L)",
                'L',
                "最新の訪問看護指示書に登録した「症状・治療状態」を読み込みます。" + ACConstants.LINE_SEPARATOR
                        + "よろしいですか？", true);
        
    }

}
//[ID:0000514][Masahiko Higuchi] 2009/09/08 add end
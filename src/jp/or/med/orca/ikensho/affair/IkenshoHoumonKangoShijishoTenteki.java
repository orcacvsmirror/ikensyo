package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoTenteki
    extends IkenshoTabbableChildAffairContainer {
  // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
  //private IkenshoDocumentTabTitleLabel title = new IkenshoDocumentTabTitleLabel();
  //private ACGroupBox group = new ACGroupBox();
  protected IkenshoDocumentTabTitleLabel title;
  protected ACGroupBox group;
  // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
  private IkenshoHoumonKangoShijishoInstructContainer tenteki = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer tokki = new
      IkenshoHoumonKangoShijishoInstructContainer();

  public IkenshoHoumonKangoShijishoTenteki() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(new VRLayout());
    tenteki.setShowSelectMnemonic('I');
    tenteki.setShowSelectText("選択(I)");
    tenteki.setCaption("在宅患者訪問点滴注射に関する指示");
    tenteki.setTextBindPath("TENTEKI_SIJI");
    tenteki.setTitle("在宅患者訪問点滴注射に関する指示（全項目200文字/4行以内）");
    tenteki.setCheckVisible(false);
    tenteki.setCode(IkenshoCommon.TEIKEI_HOUMON_TENTEKI_CHUSHA);
    tenteki.setMaxLength(200);
//    tenteki.setUseMaxRows(true);
    tenteki.setRows(4);
    tenteki.setMaxRows(tenteki.getRows());
    tokki.setCode(IkenshoCommon.TEIKEI_HOUMON_TOKKI);
    tokki.setMaxLength(200);
//    tokki.setUseMaxRows(true);
    tokki.setRows(4);
    tokki.setMaxRows(tokki.getRows());
    tokki.setShowSelectMnemonic('J');
    tokki.setShowSelectText("選択(J)");
    tokki.setCaption("特記すべき留意事項");
    tokki.setTextBindPath("SIJI_TOKKI");
    tokki.setTitle("特記すべき留意事項（注：薬の相互作用・副作用についての留意点、薬物アレルギーの既往等あれば記載してください。）");
    tokki.setCheckVisible(false);
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//    title.setText("訪問点滴注射・特記すべき留意事項");
//    group.setLayout(new VRLayout());
//    group.add(tenteki, VRLayout.NORTH);
//    group.add(tokki, VRLayout.NORTH);
//    this.add(title, VRLayout.NORTH);
//    this.add(group, VRLayout.CLIENT);
    getTitle().setText("訪問点滴注射・特記すべき留意事項");
    getGroup().setLayout(new VRLayout());
    getGroup().add(tenteki, VRLayout.NORTH);
    getGroup().add(tokki, VRLayout.NORTH);
    this.add(getTitle(), VRLayout.NORTH);
    this.add(getGroup(), VRLayout.CLIENT);
    
    tenteki.setRows(5);
    tokki.setRows(5);
    tenteki.setColumns(100);
    tokki.setColumns(100);
    
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
  }

  // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
    /**
     * group を返します。
     * 
     * @return group
     */
    protected ACGroupBox getGroup() {
        if (group == null) {
            group = new ACGroupBox();
        }
        return group;
    }

    /**
     * title を返します。
     * 
     * @return title
     */
    protected IkenshoDocumentTabTitleLabel getTitle() {
        if (title == null) {
            title = new IkenshoDocumentTabTitleLabel();
        }
        return title;
    }

    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        tenteki.setLoadRecentSetting(
                dbm,
                getMasterAffair().getPatientNo(),
                "TOKUBETSU_CHUSHA_SHIJI",
                "1",
                "読込(L)",
                'L',
                "最新の特別訪問看護指示書に登録した「点滴注射指示内容」を読み込みます。" + ACConstants.LINE_SEPARATOR
                        + "よろしいですか？", true);
        
        tokki.setLoadRecentSetting(
                dbm,
                getMasterAffair().getPatientNo(),
                "TOKUBETSU_RYUI",
                "1",
                "読込(M)",
                'M',
                "最新の特別訪問看護指示書に登録した「留意事項及び指示事項」を読み込みます。" + ACConstants.LINE_SEPARATOR
                        + "よろしいですか？", true);
    }
    
    
// [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  


}

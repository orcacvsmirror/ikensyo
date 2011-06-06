package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoRyuiShiji
    extends IkenshoTabbableChildAffairContainer {
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//  private IkenshoDocumentTabTitleLabel ryuuiShijiTitle = new
//      IkenshoDocumentTabTitleLabel();
//  private ACGroupBox ryuiShijiGrp = new ACGroupBox();
    protected IkenshoDocumentTabTitleLabel ryuuiShijiTitle;
    protected ACGroupBox ryuiShijiGrp;
  // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

  private IkenshoHoumonKangoShijishoInstructContainer seikatsuShidouRyuijikou = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer rehabilitation = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer jyokusou = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer kikiSousaShien = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer other = new
      IkenshoHoumonKangoShijishoInstructContainer();
//  private IkenshoHoumonKangoShijishoInstructContainer tokki = new
//      IkenshoHoumonKangoShijishoInstructContainer();

  public IkenshoHoumonKangoShijishoRyuiShiji() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    //留意事項および指示事項
    this.setLayout(new VRLayout());
    seikatsuShidouRyuijikou.setCode(IkenshoCommon.
                                    TEIKEI_HOUMON_RYOUYOU_SHIDOU_RYUIJIKOU);
    seikatsuShidouRyuijikou.setShowSelectMnemonic('C');
    seikatsuShidouRyuijikou.setShowSelectText("選択(C)");
    seikatsuShidouRyuijikou.setCaption("療養生活指導上の留意事項");
    seikatsuShidouRyuijikou.setTextBindPath("RSS_RYUIJIKOU");
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin 【2011年度対応：訪問看護指示書】帳票印字文字数の拡大
    seikatsuShidouRyuijikou.setTitle(
        " I  療養生活指導上の留意事項(全項目{0}文字以上／{1}行以上の入力では、帳票は2枚で印刷されます)(現在 {2}文字 {3}行)");
    seikatsuShidouRyuijikou.setPageBreakLimitProperty(151, 4);
    seikatsuShidouRyuijikou.setMaxLength(250);
    seikatsuShidouRyuijikou.setMaxRows(5);
    seikatsuShidouRyuijikou.setColumns(100);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace end
    seikatsuShidouRyuijikou.setCheckVisible(false);
    rehabilitation.setCheckBindPath("REHA_SIJI_UMU");
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin 【2011年度対応：訪問看護指示書】帳票印字文字数の拡大
    rehabilitation.setCheckText("１．リハビリテーション(現在 {2}文字 {3}行)");
    rehabilitation.setPageBreakLimitProperty(151, 4);
    rehabilitation.setMaxLength(250);
    rehabilitation.setMaxRows(5);
    rehabilitation.setColumns(100);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace end
    rehabilitation.setCode(IkenshoCommon.TEIKEI_HOUMON_REHABILITATION);
    rehabilitation.setShowSelectMnemonic('D');
    rehabilitation.setShowSelectText("選択(D)");
    rehabilitation.setCaption("リハビリテーション");
    rehabilitation.setTextBindPath("REHA_SIJI");
    rehabilitation.setTitle("II  ");
    jyokusou.setCheckBindPath("JOKUSOU_SIJI_UMU");
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin 【2011年度対応：訪問看護指示書】帳票印字文字数の拡大
    jyokusou.setCheckText("２．褥瘡の処置等(現在 {2}文字 {3}行)");
    jyokusou.setPageBreakLimitProperty(151, 4);
    jyokusou.setMaxLength(250);
    jyokusou.setMaxRows(5);
    jyokusou.setColumns(100);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace end
    jyokusou.setCode(IkenshoCommon.TEIKEI_HOUMON_JYOKUSOU);
    jyokusou.setShowSelectMnemonic('E');
    jyokusou.setShowSelectText("選択(E)");
    jyokusou.setCaption("褥瘡の処置等");
    jyokusou.setTextBindPath("JOKUSOU_SIJI");
    jyokusou.setTitle("　");
    kikiSousaShien.setCheckBindPath("SOUCHAKU_SIJI_UMU");
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin 【2011年度対応：訪問看護指示書】帳票印字文字数の拡大
    kikiSousaShien.setCheckText("３．装着・使用医療機器等の操作援助・管理(現在 {2}文字 {3}行)");
    kikiSousaShien.setPageBreakLimitProperty(151, 4);
    kikiSousaShien.setMaxLength(250);
    kikiSousaShien.setMaxRows(5);
    kikiSousaShien.setColumns(100);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace end
    kikiSousaShien.setCode(IkenshoCommon.TEIKEI_HOUMON_KIKI_SOUSA_ENJYO);
    kikiSousaShien.setShowSelectMnemonic('G');
    kikiSousaShien.setShowSelectText("選択(G)");
    kikiSousaShien.setCaption("装着・使用医療機器等の操作援助・管理");
    kikiSousaShien.setText("");
    kikiSousaShien.setTextBindPath("SOUCHAKU_SIJI");
    kikiSousaShien.setTitle("　");
    other.setCheckBindPath("RYUI_SIJI_UMU");
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin 【2011年度対応：訪問看護指示書】帳票印字文字数の拡大
    other.setCheckText("４．その他(現在 {2}文字 {3}行)");
    other.setPageBreakLimitProperty(151, 4);
    other.setMaxLength(250);
    other.setMaxRows(5);
    other.setColumns(100);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace end
    other.setCode(IkenshoCommon.TEIKEI_HOUMON_SONOTA);
    other.setShowSelectMnemonic('H');
    other.setShowSelectText("選択(H)");
    other.setCaption("その他");
    other.setTextBindPath("RYUI_SIJI");
    other.setTitle("　");
//    tokki.setCode(IkenshoCommon.TEIKEI_HOUMON_TOKKI);
//    tokki.setMaxLength(200);
//    tokki.setMaxRows(4);
//    tokki.setRows(5);
//    tokki.setShowSelectMnemonic('I');
//    tokki.setShowSelectText("選択(I)");
//    tokki.setCaption("特記すべき留意事項");
//    tokki.setTextBindPath("SIJI_TOKKI");
//    tokki.setTitle("特記すべき留意事項（注：薬の相互作用・副作用についての留意点、薬物アレルギーの既往等あれば記載してください。）");
//    tokki.setCheckVisible(false);
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//    ryuuiShijiTitle.setText("留意事項及び指示事項");
//    ryuiShijiGrp.setLayout(new VRLayout());
//    ryuiShijiGrp.add(seikatsuShidouRyuijikou, VRLayout.NORTH);
//    ryuiShijiGrp.add(rehabilitation, VRLayout.NORTH);
//    ryuiShijiGrp.add(jyokusou, VRLayout.NORTH);
//    ryuiShijiGrp.add(kikiSousaShien, VRLayout.NORTH);
//    ryuiShijiGrp.add(other, VRLayout.NORTH);
////    ryuiShijiGrp.add(tokki, VRLayout.NORTH);
//    this.add(ryuuiShijiTitle, VRLayout.NORTH);
//    this.add(ryuiShijiGrp, VRLayout.CLIENT);
    getRyuuiShijiTitle().setText("留意事項及び指示事項");
    getRyuiShijiGrp().setLayout(new VRLayout());
    getRyuiShijiGrp().add(seikatsuShidouRyuijikou, VRLayout.NORTH);
    getRyuiShijiGrp().add(rehabilitation, VRLayout.NORTH);
    getRyuiShijiGrp().add(jyokusou, VRLayout.NORTH);
    getRyuiShijiGrp().add(kikiSousaShien, VRLayout.NORTH);
    getRyuiShijiGrp().add(other, VRLayout.NORTH);
//    ryuiShijiGrp.add(tokki, VRLayout.NORTH);
    this.add(getRyuuiShijiTitle(), VRLayout.NORTH);
    this.add(getRyuiShijiGrp(), VRLayout.CLIENT);
    
    seikatsuShidouRyuijikou.setRows(4);
    rehabilitation.setRows(4);
    jyokusou.setRows(4);
    kikiSousaShien.setRows(4);
    other.setRows(4);
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
  }

  // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能
    /**
     * ryuiShijiGrp を返します。
     * 
     * @return ryuiShijiGrp
     */
    protected ACGroupBox getRyuiShijiGrp() {
        if (ryuiShijiGrp == null) {
            ryuiShijiGrp = new ACGroupBox();
        }
        return ryuiShijiGrp;
    }

    /**
     * ryuuiShijiTitle を返します。
     * 
     * @return ryuuiShijiTitle
     */
    protected IkenshoDocumentTabTitleLabel getRyuuiShijiTitle() {
        if (ryuuiShijiTitle == null) {
            ryuuiShijiTitle = new IkenshoDocumentTabTitleLabel();
        }
        return ryuuiShijiTitle;
    }
    
    // [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能
  

    //[ID:0000635][Shin Fujihara] 2011/02/28 add begin 【2010年度要望対応】
    // --- Override ---
    public boolean noControlWarning() throws Exception {
    	if (getMasterAffair() == null) {
    		return true;
    	}
    	
    	if (getMasterAffair().getCanUpdateCheckStatus() != IkenshoTabbableAffairContainer.CAN_UPDATE_CHECK_STATUS_PRINT) {
    		return true;
    	}
    	
    	if (!(getMasterAffair() instanceof IkenshoHoumonKangoShijisho)) {
    		return true;
    	}
    	
    	IkenshoHoumonKangoShijisho owner = (IkenshoHoumonKangoShijisho)getMasterAffair();
    	
  		//療養生活指導上の留意事項
    	setWarningMessageText(owner, seikatsuShidouRyuijikou, "療養生活指導上の留意事項");
  		//リハビリテーション
    	setWarningMessageText(owner, rehabilitation, "リハビリテーション");
  		//褥瘡の処置等
    	setWarningMessageText(owner, jyokusou, "褥瘡の処置等");
  		//装着・使用医療機器等の操作援助・管理
    	setWarningMessageText(owner, kikiSousaShien, "装着・使用医療機器等の操作援助・管理");
    	//その他
    	setWarningMessageText(owner, other, "その他");
  		
  		return true;
    }
    
    private void setWarningMessageText(
    		IkenshoHoumonKangoShijisho owner,
    		IkenshoHoumonKangoShijishoInstructContainer target,
    		String contentName) {
    	
  		if (target.isPageBreak()) {
  			owner.setWarningMessage(contentName);
  		}
    }
    //[ID:0000635][Shin Fujihara] 2011/02/28 add end 【2010年度要望対応】
    
}

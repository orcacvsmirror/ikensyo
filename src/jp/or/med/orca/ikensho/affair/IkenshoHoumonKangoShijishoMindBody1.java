package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.util.Arrays;

import javax.swing.JComponent;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACValueArrayRadioButtonGroup;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.component.IkenshoHintButton;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoHashableComboFormat;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;





/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoMindBody1 extends IkenshoDocumentAffairMindBody1 {
    // [ID:0000463][Tozo TANAKA] 2009/03/20 add begin 平成21年4月法改正対応
    private ACGroupBox jokusoDepthGroup;
    private ACLabelContainer jokusoDepthNPUAPContainer;
    private ACLabelContainer jokusoDepthDESIGNContainer;
    private ACValueArrayRadioButtonGroup jokusoDepthNPUAP;
    private ACValueArrayRadioButtonGroup jokusoDepthDESIGN;
    private IkenshoHintButton jokusoDepthNPUAPHelp;
    private IkenshoHintButton jokusoDepthDESIGNHelp;
    // [ID:0000463][Tozo TANAKA] 2009/03/20 add end
  /**
   * コンストラクタです。
   */
  public IkenshoHoumonKangoShijishoMindBody1() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    getRikaiKiokuGroup().setVisible(false);
    getJiritsuGroup().setVisible(true);
    getJiritsuGroup().setText("日常生活自立度");
    // [ID:0000463][Tozo TANAKA] 2009/03/20 remove begin 平成21年4月法改正対応
    //getTitle().setText("日常生活自立度");
    // [ID:0000463][Tozo TANAKA] 2009/03/20 remove end  
    getTitle().setVisible(true);
    getMondaiGroup().setVisible(false);
    getShinkeiGroup().setVisible(false);
    
    
    // [ID:0000463][Tozo TANAKA] 2009/03/20 add begin 平成21年4月法改正対応
    getSyougaiJiritsuHelp().setFollowPressedButtons(new IkenshoHintButton[] {
            getChihouJiritsuHelp(), getJokusoDepthNPUAPHelp(), getJokusoDepthDESIGNHelp()});
    getChihouJiritsuHelp().setFollowPressedButtons(new IkenshoHintButton[] {
            getSyougaiJiritsuHelp(), getJokusoDepthNPUAPHelp(), getJokusoDepthDESIGNHelp()});
    getSyougaiJiritsuHelp().setFollowHideComponents(new JComponent[] { getJokusoDepthGroup(), });
    getChihouJiritsuHelp().setFollowHideComponents(new JComponent[] { getJokusoDepthGroup(), });

    getTitle().setText("日常生活自立度・褥瘡の深さ");
    this.add(getJokusoDepthGroup(), VRLayout.NORTH);
    // [ID:0000463][Tozo TANAKA] 2009/03/20 add end  
  }
  
  // [ID:0000463][Tozo TANAKA] 2009/03/20 add begin 平成21年4月法改正対応
  protected ACGroupBox getJokusoDepthGroup(){
      if(jokusoDepthGroup==null){
          jokusoDepthGroup = new ACGroupBox();
          jokusoDepthGroup.setText("褥瘡の深さ");
          addJokusoDepthGroup();
      }
      return jokusoDepthGroup; 
  }  
  protected void addJokusoDepthGroup(){
      //[ID:0000688][Shin Fujihara] 2012/03/12 replace begin レイアウト変更対応
//      getJokusoDepthGroup().add(getJokusoDepthNPUAPContainer(), VRLayout.FLOW);
//      getJokusoDepthGroup().add(getJokusoDepthDESIGNContainer(), VRLayout.FLOW);
      //DESIGN分類、NPUAP分類の表示順を左右逆に変更
      getJokusoDepthGroup().add(getJokusoDepthDESIGNContainer(), VRLayout.FLOW);
      getJokusoDepthGroup().add(getJokusoDepthNPUAPContainer(), VRLayout.FLOW);
      //[ID:0000688][Shin Fujihara] 2012/03/12 replace end
  }
  protected ACLabelContainer getJokusoDepthNPUAPContainer(){
      if(jokusoDepthNPUAPContainer==null){
          jokusoDepthNPUAPContainer = new ACLabelContainer();
          jokusoDepthNPUAPContainer.setText("NPUAP分類");
          addJokusoDepthNPUAPContainer();
      }
      return jokusoDepthNPUAPContainer; 
  }  
  protected void addJokusoDepthNPUAPContainer(){
      getJokusoDepthNPUAPContainer().add(getJokusoDepthNPUAP(), VRLayout.FLOW);
      getJokusoDepthNPUAPContainer().add(getJokusoDepthNPUAPHelp(), VRLayout.FLOW);
  }  
  protected ACLabelContainer getJokusoDepthDESIGNContainer(){
      if(jokusoDepthDESIGNContainer==null){
          jokusoDepthDESIGNContainer = new ACLabelContainer();
          jokusoDepthDESIGNContainer.setText("DESIGN分類");
          addJokusoDepthDESIGNContainer();
      }
      return jokusoDepthDESIGNContainer; 
  }  
  protected void addJokusoDepthDESIGNContainer(){
      getJokusoDepthDESIGNContainer().add(getJokusoDepthDESIGN(), VRLayout.FLOW);
      getJokusoDepthDESIGNContainer().add(getJokusoDepthDESIGNHelp(), VRLayout.FLOW);
  }  
  protected ACValueArrayRadioButtonGroup getJokusoDepthNPUAP(){
      if(jokusoDepthNPUAP==null){
          jokusoDepthNPUAP = new ACValueArrayRadioButtonGroup();
          jokusoDepthNPUAP.setBindPath("JOKUSOU_NPUAP");
          jokusoDepthNPUAP.setValues(new int[] {1, 2});
          jokusoDepthNPUAP.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                  .asList(new String[] { "III度", "IV度" }))));
      }
      return jokusoDepthNPUAP; 
  }  
  protected ACValueArrayRadioButtonGroup getJokusoDepthDESIGN(){
      if(jokusoDepthDESIGN==null){
          jokusoDepthDESIGN = new ACValueArrayRadioButtonGroup();
          jokusoDepthDESIGN.setBindPath("JOKUSOU_DESIGN");
          jokusoDepthDESIGN.setValues(new int[] {1, 2,3});
          jokusoDepthDESIGN.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                  .asList(new String[] { "D3", "D4", "D5" }))));
      }
      return jokusoDepthDESIGN; 
  }
  protected IkenshoHintButton getJokusoDepthNPUAPHelp(){
      if(jokusoDepthNPUAPHelp==null){
          jokusoDepthNPUAPHelp = new IkenshoHintButton();
          jokusoDepthNPUAPHelp.setToolTipText("詳細説明を表示します。");
          jokusoDepthNPUAPHelp.setHintArea(getHintArea());
          jokusoDepthNPUAPHelp.setHintContainer(getHintContainer());
          jokusoDepthNPUAPHelp.setFollowPressedButtons(new IkenshoHintButton[] {
                  getSyougaiJiritsuHelp(), getChihouJiritsuHelp(), getJokusoDepthDESIGNHelp()});
          jokusoDepthNPUAPHelp
          .setFollowHideComponents(new JComponent[] { getJiritsuGroup(), });
      }
      return jokusoDepthNPUAPHelp; 
  }
  protected IkenshoHintButton getJokusoDepthDESIGNHelp(){
      if(jokusoDepthDESIGNHelp==null){
          jokusoDepthDESIGNHelp = new IkenshoHintButton();
          jokusoDepthDESIGNHelp.setToolTipText("詳細説明を表示します。");
          jokusoDepthDESIGNHelp.setHintArea(getHintArea());
          jokusoDepthDESIGNHelp.setHintContainer(getHintContainer());
          jokusoDepthDESIGNHelp.setFollowPressedButtons(new IkenshoHintButton[] {
                  getSyougaiJiritsuHelp(), getChihouJiritsuHelp(), getJokusoDepthNPUAPHelp()});
          jokusoDepthDESIGNHelp
                    .setFollowHideComponents(new JComponent[] { getJiritsuGroup(), });
      }
      return jokusoDepthDESIGNHelp; 
  }

  public void setFollowDisabledComponents(JComponent[] components) {
      super.setFollowDisabledComponents(components);
      getJokusoDepthNPUAPHelp().setFollowDisabledComponents(components);
      getJokusoDepthDESIGNHelp().setFollowDisabledComponents(components);
  }

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
      super.initDBCopmponent(dbm);
      
    IkenshoCommon
                .setHintButtons(dbm, new String[] { "7", "8", },
                        getFomratKubun(), new IkenshoHintButton[] {
                                getJokusoDepthNPUAPHelp(),
                                getJokusoDepthDESIGNHelp() });
  }
  
  
  // [ID:0000463][Tozo TANAKA] 2009/03/20 add end  
}

package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JLabel;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.event.ACFollowDisableSelectionListener;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoIryoukikan
    extends IkenshoDocumentAffairOrgan {
//[ID:0000514][Tozo TANAKA] 2009/09/09 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//  private JLabel iryoukikanHeader = new JLabel();
//  private ACLabelContainer kinkyuRenrakuContainer = new ACLabelContainer();
//  private ACTextField kinkyuRenraku = new ACTextField();
//  private ACLabelContainer fuzaijiTaiouContainer = new ACLabelContainer();
//  private ACTextField fuzaijiTaiou = new ACTextField();
    protected JLabel iryoukikanHeader;
  protected ACLabelContainer kinkyuRenrakuContainer;
  protected ACTextField kinkyuRenraku = new ACTextField();
  protected ACLabelContainer fuzaijiTaiouContainer;
  protected ACTextField fuzaijiTaiou = new ACTextField();
//[ID:0000514][Tozo TANAKA] 2009/09/09 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
  private VRPanel otherStationPnl = new VRPanel();
  private VRPanel otherStationSubPnl = new VRPanel();
  private ACLabelContainer otherStationNmContainer = new ACLabelContainer();
  private ACComboBox stationName = new ACComboBox();
  private JLabel otherStationNmCaption = new JLabel();
//[ID:0000514][Tozo TANAKA] 2009/09/09 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//  private ACLabelContainer otherStationSijiUmuContainer = new ACLabelContainer();
  protected ACLabelContainer otherStationSijiUmuContainer = new ACLabelContainer();
//[ID:0000514][Tozo TANAKA] 2009/09/09 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
  private ACClearableRadioButtonGroup otherStationSiji = new
      ACClearableRadioButtonGroup();
//[ID:0000514][Tozo TANAKA] 2009/09/09 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//  private ACLabelContainer otherStationSijiContainer = new ACLabelContainer();
  protected ACLabelContainer otherStationSijiContainer = new ACLabelContainer();
//[ID:0000514][Tozo TANAKA] 2009/09/09 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
  private ACComboBox otherStationName = new ACComboBox();
  private JLabel otherStationSijiCaption = new JLabel();
  private VRPanel stationContainer = new VRPanel();

//[ID:0000731][Shin Fujihara] 2012/04/20 add begin 【2012年度対応：訪問看護指示書】たん吸引指示追加
  protected ACLabelContainer kyuinStationSijiUmuContainer = new ACLabelContainer();
  private ACClearableRadioButtonGroup kyuinStationSiji = new ACClearableRadioButtonGroup();
  protected ACLabelContainer kyuinStationSijiContainer = new ACLabelContainer();
  private ACComboBox kyuinStationName = new ACComboBox();
  private JLabel kyuinStationSijiCaption = new JLabel();
//[ID:0000731][Shin Fujihara] 2012/04/20 add end 【2009年度対応：訪問看護指示書】たん吸引指示追加

public VRMap createSourceInnerBindComponent() {
    VRMap map = super.createSourceInnerBindComponent();
    map.setData("OTHER_STATION_SIJI", new Integer(1));
    // [ID:0000731][Shin Fujihara] 2012/04/20 add begin 【2012年度対応：訪問看護指示書】たん吸引指示追加
    map.setData("KYUIN_STATION_SIJI", new Integer(1));
    // [ID:0000731][Shin Fujihara] 2012/04/20 add end 【2009年度対応：訪問看護指示書】たん吸引指示追加
    return map;
  }

  public boolean noControlWarning() {


    if(otherStationSiji.getSelectedIndex()==2){
      //他ステーションへの指示

      String mainStationName = String.valueOf(stationName.getEditor().getItem());
      if(!"".equals(mainStationName) ){
        String subStationName = String.valueOf(otherStationName.getEditor().getItem());
        if(mainStationName.equals(subStationName) ){
          if (ACMessageBox.show(
              "訪問看護ステーション名および他ステーションへの指示の訪問看護ステーション名が\n同じです。\nよろしいですか？",
                                     ACMessageBox.BUTTON_OKCANCEL,
                                     ACMessageBox.ICON_QUESTION,
                                     ACMessageBox.FOCUS_CANCEL) !=
              ACMessageBox.RESULT_OK) {
            stationName.requestFocus();
            return false;
          }

        }
      }

    }

    return true;
  }

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
    super.initDBCopmponent(dbm);

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT ");
    sb.append(" MI_NM");
    sb.append(" FROM ");
    sb.append(" STATION");
    sb.append(" ORDER BY ");
    sb.append(" STATION_CD");

    VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
    stationName.setModel(IkenshoCommon.createComboAdapter(new
        VRHashMapArrayToConstKeyArrayAdapter(array, "MI_NM")));
    otherStationName.setModel(IkenshoCommon.createComboAdapter(new
        VRHashMapArrayToConstKeyArrayAdapter(array, "MI_NM")));
    
    //[ID:0000731][Shin Fujihara] 2012/04/20 add begin 【2012年度対応：訪問看護指示書】たん吸引指示追加
    kyuinStationName.setModel(IkenshoCommon.createComboAdapter(new
            VRHashMapArrayToConstKeyArrayAdapter(array, "MI_NM")));
    //[ID:0000731][Shin Fujihara] 2012/04/20 add end 【2009年度対応：訪問看護指示書】たん吸引指示追加

  }

  public IkenshoHoumonKangoShijishoIryoukikan() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    otherStationSiji.addListSelectionListener(new
        ACFollowDisableSelectionListener(new JComponent[] {
                                              otherStationName}
                                              , 1));
    
    //[ID:0000731][Shin Fujihara] 2012/04/20 add begin 【2012年度対応：訪問看護指示書】たん吸引指示追加
    kyuinStationSiji.addListSelectionListener(new ACFollowDisableSelectionListener(new JComponent[] { kyuinStationName }, 1));
    //[ID:0000731][Shin Fujihara] 2012/04/20 add end 【2009年度対応：訪問看護指示書】たん吸引指示追加

  }
  protected boolean checkSelectedDoctor() {
    Object selectItem = getDoctorName().getSelectedItem();
    if (selectItem instanceof String) {
      return false;
    }

    if ( (doctors != null) && (selectItem instanceof VRMap) &&
        (selectItem != defaultDoctor)) {
      doctor = (VRMap) getDoctorName().getSelectedItem();
    }
    else {
      doctor = defaultDoctor;
    }
    return true;
  }

  protected boolean canCallRegistDoctor() {
    return true;
  }


  private void jbInit() throws Exception {
    //医療機関
    otherStationSiji.setBindPath("OTHER_STATION_SIJI");

    getContentsGroup().add(stationContainer, VRLayout.NORTH);
    stationContainer.setLayout(new VRLayout());

//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//    getFollowDoctorContainer().add(iryoukikanHeader, VRLayout.NORTH);
//    getFollowDoctorContainer().add(kinkyuRenrakuContainer, VRLayout.FLOW_INSETLINE_RETURN);
//    getFollowDoctorContainer().add(fuzaijiTaiouContainer, VRLayout.FLOW_INSETLINE_RETURN);
    addFollowDoctorContainer();
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
    stationContainer.add(otherStationPnl, VRLayout.FLOW_INSETLINE_RETURN);


    VRLayout otherStationPnlLayout = new VRLayout();
    otherStationPnlLayout.setHgap(100);
    otherStationPnl.setLayout(otherStationPnlLayout);
    otherStationPnl.add(otherStationSubPnl, VRLayout.FLOW_RETURN);
    otherStationSubPnl.setLayout(new VRLayout());
    otherStationSubPnl.add(otherStationNmContainer,
                           VRLayout.FLOW_INSETLINE_RETURN);
    otherStationSubPnl.add(otherStationSijiUmuContainer,
                           VRLayout.FLOW_INSETLINE_RETURN);
    otherStationSubPnl.add(otherStationSijiContainer,
                           VRLayout.FLOW_INSETLINE_RETURN);
    //医療機関 / Grp / header
    //緊急時の連絡先
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//    iryoukikanHeader.setText("以上のとおり、指定訪問看護の実施を指示いたします。");
//    kinkyuRenrakuContainer.setText("緊急時の連絡先");
//    kinkyuRenrakuContainer.add(kinkyuRenraku, null);
    getIryoukikanHeader().setText("以上のとおり、指定訪問看護の実施を指示いたします。");
    getKinkyuRenrakuContainer().setText("緊急時の連絡先");
    getKinkyuRenrakuContainer().add(kinkyuRenraku, null);
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
    kinkyuRenraku.setColumns(40);
    kinkyuRenraku.setEditable(false);
    kinkyuRenraku.setBindPath("KINKYU_RENRAKU");
    //不在時の対応法
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//    fuzaijiTaiouContainer.setText("不在時の対応法");
//    fuzaijiTaiouContainer.add(fuzaijiTaiou, null);
    getFuzaijiTaiouContainer().setText("不在時の対応法");
    getFuzaijiTaiouContainer().add(fuzaijiTaiou, null);
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
    fuzaijiTaiou.setColumns(40);
    fuzaijiTaiou.setEditable(false);
    fuzaijiTaiou.setBindPath("FUZAIJI_TAIOU");
    //他看護ステーション名
    otherStationNmContainer.setText("訪問看護ステーション");
    otherStationNmContainer.add(stationName, null);
    otherStationNmContainer.add(otherStationNmCaption, null);
    stationName.setPreferredSize(new Dimension(400, 20));
    stationName.setMaxLength(30);
    stationName.setColumns(30);
    stationName.setIMEMode(InputSubset.KANJI);
    stationName.setBindPath("STATION_NM");
    otherStationNmCaption.setText("殿");
    //他ステーションへの指示の有無
    otherStationSijiUmuContainer.setText("他ステーションへの指示");
    otherStationSijiUmuContainer.add(otherStationSiji, null);
    otherStationSiji.setUseClearButton(false);
    otherStationSiji.setModel(new VRListModelAdapter(
        new VRArrayList(Arrays.asList(new String[] {
                                      "無",
                                      "有"}))));
    //他ステーション指示
    otherStationSijiContainer.setText("訪問看護ステーション");
    otherStationSijiContainer.add(otherStationName, null);
    otherStationSijiContainer.add(otherStationSijiCaption, null);
    otherStationName.setPreferredSize(new Dimension(400, 20));
    otherStationName.setMaxLength(30);
    otherStationName.setColumns(30);
    otherStationName.setIMEMode(InputSubset.KANJI);
    otherStationName.setBindPath("OTHER_STATION_NM");
    otherStationSijiCaption.setText("殿");
    
    //[ID:0000731][Shin Fujihara] 2012/04/20 add begin 【2012年度対応：訪問看護指示書】たん吸引指示追加
    //たん吸引指示の有無
    kyuinStationSijiUmuContainer.setText("たんの吸引等実施のための訪問介護事業所への指示");
    kyuinStationSijiUmuContainer.add(kyuinStationSiji, null);
    kyuinStationSiji.setUseClearButton(false);
    kyuinStationSiji.setBindPath("KYUIN_STATION_SIJI");
    kyuinStationSiji.setModel(new VRListModelAdapter(
        new VRArrayList(Arrays.asList(new String[] {
                                      "無",
                                      "有"}))));
    //たん吸引訪問介護事業所
    kyuinStationSijiContainer.setText("訪問介護事業所");
    kyuinStationSijiContainer.add(kyuinStationName, null);
    kyuinStationSijiContainer.add(kyuinStationSijiCaption, null);
    kyuinStationName.setPreferredSize(new Dimension(400, 20));
    kyuinStationName.setMaxLength(30);
    kyuinStationName.setColumns(30);
    kyuinStationName.setIMEMode(InputSubset.KANJI);
    kyuinStationName.setBindPath("KYUIN_STATION_NM");
    kyuinStationSijiCaption.setText("殿");
    
    otherStationSubPnl.add(kyuinStationSijiUmuContainer,
            VRLayout.FLOW_INSETLINE_RETURN);
    otherStationSubPnl.add(kyuinStationSijiContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
    //[ID:0000731][Shin Fujihara] 2012/04/20 add end 【2009年度対応：訪問看護指示書】たん吸引指示追加
  }
  
//[ID:0000514][Tozo TANAKA] 2009/09/09 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
  /**
   * iryoukikanHeader を返します。
   * @return iryoukikanHeader
   */
  protected JLabel getIryoukikanHeader() {
      if(iryoukikanHeader==null){
          iryoukikanHeader = new JLabel();
      }
      return iryoukikanHeader;
  }

/**
 * fuzaijiTaiouContainer を返します。
 * @return fuzaijiTaiouContainer
 */
protected ACLabelContainer getFuzaijiTaiouContainer() {
    if(fuzaijiTaiouContainer==null){
        fuzaijiTaiouContainer = new ACLabelContainer();
    }
    return fuzaijiTaiouContainer;
}

/**
 * kinkyuRenrakuContainer を返します。
 * @return kinkyuRenrakuContainer
 */
protected ACLabelContainer getKinkyuRenrakuContainer() {
    if(kinkyuRenrakuContainer==null){
        kinkyuRenrakuContainer = new ACLabelContainer();
    }
    return kinkyuRenrakuContainer;
}

/**
 * followDoctorContainerへの追加を定義します。
 */
protected void addFollowDoctorContainer(){
    getFollowDoctorContainer().add(getIryoukikanHeader(), VRLayout.NORTH);
    getFollowDoctorContainer().add(getKinkyuRenrakuContainer(), VRLayout.FLOW_INSETLINE_RETURN);
    getFollowDoctorContainer().add(getFuzaijiTaiouContainer(), VRLayout.FLOW_INSETLINE_RETURN);
}

//[ID:0000514][Tozo TANAKA] 2009/09/09 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

}

package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoBodyStatusContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoMindBody2H18 extends IkenshoIkenshoInfoMindBody2 {
  private IkenshoBodyStatusContainer connectPain;
  private IkenshoBodyStatusContainer connectKousyuku;
  private ACParentHesesPanelContainer weightChanges = new ACParentHesesPanelContainer();
  private ACClearableRadioButtonGroup weightChange = new ACClearableRadioButtonGroup();
  private ACLabelContainer mahiContainer;
  private ACIntegerCheckBox mahi;
  private IkenshoBodyStatusContainer mahiLegLeftUp;
  private IkenshoBodyStatusContainer mahiOther;
  private IkenshoBodyStatusContainer mahiLegRightDown;
  private IkenshoBodyStatusContainer mahiLegLeftDown;
  private IkenshoBodyStatusContainer mahiLegRightUp;
  private ACLabelContainer mahis;

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
    super.initDBCopmponent(dbm);

    applyPoolTeikeibun(mahiOther.getComboBox(), IkenshoCommon.TEIKEI_MAHI_POSITION_OTHER_NAME);
    applyPoolTeikeibun(connectKousyuku.getComboBox(), IkenshoCommon.TEIKEI_CENNECT_KOSHUKU_NAME);
    applyPoolTeikeibun(connectPain.getComboBox(), IkenshoCommon.TEIKEI_CONNECT_PAIN_NAME);
  }

  protected void bindSourceInnerBindComponent() throws Exception {
    super.bindSourceInnerBindComponent();

    mahi.setSelected(mahiLegLeftUp.isChecked() || mahiLegRightUp.isChecked() ||
                     mahiLegLeftDown.isChecked() || mahiLegRightDown.isChecked() ||
                     mahiOther.isChecked());

  }

  /**
   * �֐߂̒ɂ݃R���e�i��Ԃ��܂��B
   * @return �֐߂̒ɂ݃R���e�i
   */
  protected ACLabelContainer getMahiContainer(){
    if(mahiContainer==null){
      mahiContainer = new ACLabelContainer();
      mahis = new ACLabelContainer();
      mahi = new ACIntegerCheckBox();
      mahiLegLeftUp = new IkenshoBodyStatusContainer();
      mahiOther = new IkenshoBodyStatusContainer();
      mahiLegRightDown = new IkenshoBodyStatusContainer();
      mahiLegLeftDown = new IkenshoBodyStatusContainer();
      mahiLegRightUp = new IkenshoBodyStatusContainer();
    }
    return mahiContainer;
  }

  /**
   * �֐߂̒ɂ݃R���e�i��Ԃ��܂��B
   * @return �֐߂̒ɂ݃R���e�i
   */
  protected IkenshoBodyStatusContainer getConnectPain(){
    if(connectPain==null){
      connectPain = new IkenshoBodyStatusContainer();
    }
    return connectPain;
  }
  /**
   * �֐߂̍S�k�R���e�i��Ԃ��܂��B
   * @return �֐߂̍S�k�R���e�i
   */
  protected IkenshoBodyStatusContainer getConnectKousyuku(){
    if(connectKousyuku==null){
      connectKousyuku = new IkenshoBodyStatusContainer();
    }
    return connectKousyuku;
  }

  /**
   * �̏d�̕ω��R���e�i��Ԃ��܂��B
   * @return �̏d�̕ω��R���e�i
   */
  protected ACParentHesesPanelContainer getWeightChanges(){
    if(weightChanges==null){
      weightChanges = new ACParentHesesPanelContainer();
    }
    return weightChanges;
  }
  /**
   * �̏d�̕ω���Ԃ��܂��B
   * @return �̏d�̕ω�
   */
  protected ACClearableRadioButtonGroup getWeightChange(){
    if(weightChange==null){
      weightChange = new ACClearableRadioButtonGroup();
    }
    return weightChange;
  }




  public IkenshoIkenshoInfoMindBody2H18() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    mahi.addItemListener(new ItemListener(){
      public void itemStateChanged(ItemEvent e) {
        boolean select;
        switch(e.getStateChange()){
        case ItemEvent.SELECTED:
          select = true;
          break;
        case ItemEvent.DESELECTED:
          select = false;
          break;
        default:
          return;
        }
        mahiLegLeftUp.followParentEnabled(select);
        mahiLegRightUp.followParentEnabled(select);
        mahiLegLeftDown.followParentEnabled(select);
        mahiLegRightDown.followParentEnabled(select);
        mahiOther.followParentEnabled(select);
      }

    });

  }
  private void jbInit() throws Exception {
    getHumanPicture().setVisible(false);
    getMahi().setVisible(false);
    getShishiKesson().setRankVisible(false);
    getConnectKoushukus().setVisible(false);

    getMahiContainer().setContentAreaFilled(true);
    getMahiContainer().setBackground(IkenshoConstants.COLOR_DOUBLE_BACK_PANEL_BACKGROUND);
    getMahiContainer().setFocusBackground(IkenshoConstants.COLOR_DOUBLE_BACK_PANEL_BACKGROUND);


    getMahiContainer().setContentAreaFilled(true);
    getMahiContainer().setLayout(new VRLayout(VRLayout.LEFT, 0, 0));
    weightChange.setBindPath("WEIGHT_CHANGE");
    mahiOther.setCheckBindPath("MAHI_ETC");
    mahiOther.setPosBindPath("MAHI_ETC_BUI");
    mahiOther.setRankBindPath("MAHI_ETC_TEIDO");
    mahiLegLeftUp.setCheckBindPath("MAHI_LEFTARM");
    mahiLegLeftUp.setRankBindPath("MAHI_LEFTARM_TEIDO");
    mahiLegRightUp.setCheckBindPath("MAHI_RIGHTARM");
    mahiLegRightUp.setRankBindPath("MAHI_RIGHTARM_TEIDO");
    mahiLegLeftDown.setCheckBindPath("MAHI_LOWERLEFTLIMB");
    mahiLegLeftDown.setRankBindPath("MAHI_LOWERLEFTLIMB_TEIDO");
    mahiLegRightDown.setCheckBindPath("MAHI_LOWERRIGHTLIMB");
    mahiLegRightDown.setRankBindPath("MAHI_LOWERRIGHTLIMB_TEIDO");
    mahi.setSelected(true);
    mahi.setBindPath("MAHI_FLAG");
    getMahiContainer().add(mahi, VRLayout.FLOW_RETURN);
    getMahiContainer().add(mahis, VRLayout.FLOW_RETURN);

    VRLayout lay = (VRLayout) getDownFuzuiis().getLayout();
    lay.setHgap(0);
    lay.setVgap(0);
    lay.setAutoWrap(false);

    lay = new VRLayout(VRLayout.LEFT, 0, 0);
        lay.setAutoWrap(false);
    mahis.setLayout(lay);


    String osName = System.getProperty("os.name");
    if ( (osName != null) && (osName.indexOf("Mac") >= 0)) {
      //Mac�͉E�ɕ��ׂ�
      mahiLegRightUp.getCheckBox().setPreferredSize(null);
      mahiLegLeftUp.getCheckBox().setPreferredSize(null);
      mahiLegRightDown.getCheckBox().setPreferredSize(null);
      mahiLegLeftDown.getCheckBox().setPreferredSize(null);
      mahis.add(mahiLegRightUp, VRLayout.FLOW);
      mahis.add(mahiLegLeftUp, VRLayout.FLOW_RETURN);
      mahis.add(mahiLegRightDown, VRLayout.FLOW);
      mahis.add(mahiLegLeftDown, VRLayout.FLOW_RETURN);
    }else{
      mahis.add(mahiLegRightUp, VRLayout.FLOW_RETURN);
      mahis.add(mahiLegLeftUp, VRLayout.FLOW_RETURN);
      mahis.add(mahiLegRightDown, VRLayout.FLOW_RETURN);
      mahis.add(mahiLegLeftDown, VRLayout.FLOW_RETURN);
    }

    mahis.add(mahiOther, VRLayout.FLOW_RETURN);
    mahiLegLeftUp.setCheckText("���㎈");
    mahiLegRightUp.setCheckText("�E�㎈");
    mahiLegLeftDown.setCheckText("������");
    mahiLegRightDown.setCheckText("�E����");
    mahiOther.setCheckText("���̑�");
    mahis.setFocusBackground(IkenshoConstants.COLOR_DOUBLE_BACK_PANEL_BACKGROUND);
    getBodyWeightContainer().add(getWeightChanges(), null);
    mahi.setText("���");
    mahiLegLeftUp.setPosVisible(false);
    mahiLegRightDown.setPosVisible(false);
    mahiLegLeftDown.setPosVisible(false);
    mahiLegRightUp.setPosVisible(false);

    getWeightChange().setModel(new VRListModelAdapter(new
                                                 VRArrayList(Arrays.asList(new
        String[] {"����", "�ێ�", "����"}))));
    getWeightChanges().add(getWeightChange(), null);

    getConnectPain().setRankBindPath("KANSETU_ITAMI_TEIDO");
    getConnectPain().setPosBindPath("KANSETU_ITAMI_BUI");
    getConnectPain().setCheckText("�֐߂̒ɂ�");
    getConnectPain().setCheckBindPath("KANSETU_ITAMI");

    getConnectKousyuku().setCheckBindPath("KOUSHU");
    getConnectKousyuku().setCheckText("�֐߂̍S�k");
    getConnectKousyuku().setPosBindPath("KOUSHU_BUI");
    getConnectKousyuku().setRankBindPath("KOUSHU_TEIDO");
    getWeightChanges().setBeginText("�i�ߋ�6�����̑̏d�̕ω�");
  }

  protected void addContaints(){

//    getContaintsGroup().add(getMindBodyClientContainer(), BorderLayout.CENTER);
    getContaintsGroup().add(getMindBodyStatusContainer(), BorderLayout.CENTER);

    getMindBodyStatusContainer().add(getDominantHand(), VRLayout.FLOW_RETURN);
    getMindBodyStatusContainer().add(getBodyHeightContainer(), VRLayout.FLOW);
    getMindBodyStatusContainer().add(getBodyWeightContainer(), VRLayout.FLOW_RETURN);

    getMindBodyStatusContainer().add(getShishiKesson(), VRLayout.FLOW_RETURN);
    getMindBodyStatusContainer().add(getMahiContainer(), VRLayout.FLOW_RETURN);
    getMindBodyStatusContainer().add(getMahi(), VRLayout.FLOW_RETURN);
    getMindBodyStatusContainer().add(getMastleDown(), VRLayout.FLOW_RETURN);
    getMindBodyStatusContainer().add(getConnectKousyuku(), VRLayout.FLOW_RETURN);
    getMindBodyStatusContainer().add(getConnectPain(), VRLayout.FLOW_RETURN);
    getMindBodyStatusContainer().add(getDownFuzuiis(), VRLayout.FLOW_RETURN);
    getMindBodyStatusContainer().add(getJyokusou(), VRLayout.FLOW_RETURN);
    getMindBodyStatusContainer().add(getMindBodyStatusOthers(), VRLayout.FLOW_RETURN);

    getMindBodyConnectContainer().add(getConnectKoushukus(), null);
    getMindBodyClientContainer().add(getHumanPicture(), BorderLayout.CENTER);
    getMindBodyClientContainer().add(getMindBodyConnectContainer(), BorderLayout.WEST);
  }
  protected void addDownFuzuii(){
    getDownFuzuiis().add(getDownFuzuii(), VRLayout.FLOW);
    getDownFuzuiis().add(getDownFuzuiiJyoushi(), VRLayout.FLOW);
    getDownFuzuiis().add(getDownFuzuiiKashi(), VRLayout.FLOW);
    getDownFuzuiis().add(getDownFuzuiiTaikan(), VRLayout.FLOW);
  }
}

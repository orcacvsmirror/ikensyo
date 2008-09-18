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
    
    // 2007/10/18 [Masahiko Higuchi] Addition - begin �Ɩ��J�ڃR���{�Ή� ACComboBox��IkenshoOptionComboBox
    mahiOther.getComboBox().setOptionComboBoxParameters("���(���̑�)�E����",IkenshoCommon.TEIKEI_MAHI_POSITION_OTHER_NAME,10);
    connectKousyuku.getComboBox().setOptionComboBoxParameters("�֐߂̍S�k�E����",IkenshoCommon.TEIKEI_CENNECT_KOSHUKU_NAME,10);
    connectPain.getComboBox().setOptionComboBoxParameters("�֐߂̒ɂ݁E����",IkenshoCommon.TEIKEI_CONNECT_PAIN_NAME,10);
    getMindBody2Pos1().getComboBox().setOptionComboBoxParameters("�l�������E����",IkenshoCommon.TEIKEI_BODY_STATUS_HAND_FOOT_NAME,10);
    getMindBody2Pos2().getComboBox().setOptionComboBoxParameters("��ჁE����",IkenshoCommon.TEIKEI_BODY_STATUS_PARALYSIS_NAME,10);
    getMindBody2Pos3().getComboBox().setOptionComboBoxParameters("�ؗ͂̒ቺ�E����",IkenshoCommon.TEIKEI_BODY_STATUS_MUSCULAR_DOWN_NAME,10);
    getMindBody2Pos4().getComboBox().setOptionComboBoxParameters("��ጁE����",IkenshoCommon.TEIKEI_BODY_STATUS_JYOKUSOU_NAME,10);
    getMindBody2Pos5().getComboBox().setOptionComboBoxParameters("���̑��̔畆�����E����",IkenshoCommon.TEIKEI_BODY_STATUS_SKIN_NAME,10);
    // 2007/10/18 [Masahiko Higuchi] Addition - end    
    
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

    // 2006/07/10
    // ��t�ӌ����Ή� - ��ჃR���e�i���я��ύX�̂���
    // Replece - begin [Masahiko Higuchi]
    addMahiContainar();
            //    String osName = System.getProperty("os.name");
            //    if ( (osName != null) && (osName.indexOf("Mac") >= 0)) {
            //      //Mac�͉E�ɕ��ׂ�
            //      mahiLegRightUp.getCheckBox().setPreferredSize(null);
            //      mahiLegLeftUp.getCheckBox().setPreferredSize(null);
            //      mahiLegRightDown.getCheckBox().setPreferredSize(null);
            //      mahiLegLeftDown.getCheckBox().setPreferredSize(null);
            //      mahis.add(mahiLegRightUp, VRLayout.FLOW);
            //      mahis.add(mahiLegLeftUp, VRLayout.FLOW_RETURN);
            //      mahis.add(mahiLegRightDown, VRLayout.FLOW);
            //      mahis.add(mahiLegLeftDown, VRLayout.FLOW_RETURN);
            //    }else{
            //      mahis.add(mahiLegRightUp, VRLayout.FLOW_RETURN);
            //      mahis.add(mahiLegLeftUp, VRLayout.FLOW_RETURN);
            //      mahis.add(mahiLegRightDown, VRLayout.FLOW_RETURN);
            //      mahis.add(mahiLegLeftDown, VRLayout.FLOW_RETURN);
            //    }
    
            //mahis.add(mahiOther, VRLayout.FLOW_RETURN);
    // Replace - end
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
  
  /**
     * override���Ė�ჃR���e�i�̕��я���ύX���܂��B
     */
    protected void addMahiContainar() {
        // 2006/07/10
        // ��t�ӌ����Ή� - ��ჃR���e�i���я��ύX�̂���
        // Addition - begin [Masahiko Higuchi]
        String osName = System.getProperty("os.name");
        if ((osName != null) && (osName.indexOf("Mac") >= 0)) {
            // Mac�͉E�ɕ��ׂ�
            getMahiLegRightUp().getCheckBox().setPreferredSize(null);
            getMahiLegLeftUp().getCheckBox().setPreferredSize(null);
            getMahiLegRightDown().getCheckBox().setPreferredSize(null);
            getMahiLegLeftDown().getCheckBox().setPreferredSize(null);
            getMahis().add(getMahiLegRightUp(), VRLayout.FLOW);
            getMahis().add(getMahiLegLeftUp(), VRLayout.FLOW_RETURN);
            getMahis().add(getMahiLegRightDown(), VRLayout.FLOW);
            getMahis().add(getMahiLegLeftDown(), VRLayout.FLOW_RETURN);
        } else {
            getMahis().add(getMahiLegRightUp(), VRLayout.FLOW_RETURN);
            getMahis().add(getMahiLegLeftUp(), VRLayout.FLOW_RETURN);
            getMahis().add(getMahiLegRightDown(), VRLayout.FLOW_RETURN);
            getMahis().add(getMahiLegLeftDown(), VRLayout.FLOW_RETURN);
        }
        getMahis().add(getMahiOther(), VRLayout.FLOW_RETURN);

        // Addition - end
    }

    /**
     * ��ჃR���e�i��������Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getMahiLegLeftDown() {
        if(mahiLegLeftDown == null){
            mahiLegLeftDown = new IkenshoBodyStatusContainer();
        }
        return mahiLegLeftDown;
    }

    /**
     * ��ჃR���e�i���㎈��Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getMahiLegLeftUp() {
        if(mahiLegLeftUp == null){
            mahiLegLeftUp = new IkenshoBodyStatusContainer();
        }
        return mahiLegLeftUp;
    }

    /**
     * ��ჃR���e�i�E������Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getMahiLegRightDown() {
        if(mahiLegRightDown == null){
            mahiLegRightDown = new IkenshoBodyStatusContainer();
        }
        return mahiLegRightDown;
    }

    /**
     * ��ჃR���e�i�E�㎈��Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getMahiLegRightUp() {
        if(mahiLegRightUp == null){
            mahiLegRightUp = new IkenshoBodyStatusContainer();
        }
        return mahiLegRightUp;
    }

    /**
     * ��ჃR���e�i���̑���Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getMahiOther() {
        if(mahiOther == null){
            mahiOther = new IkenshoBodyStatusContainer();
        }
        return mahiOther;
    }

    /**
     * ��ჃR���e�i���x����Ԃ��܂��B
     * @return
     */
    protected ACLabelContainer getMahis() {
        if (mahis == null){
            mahis = new ACLabelContainer();
        }
        return mahis;
    }
}

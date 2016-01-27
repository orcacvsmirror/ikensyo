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
//[ID:0000514][Tozo TANAKA] 2009/09/09 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
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
//[ID:0000514][Tozo TANAKA] 2009/09/09 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
  private VRPanel otherStationPnl = new VRPanel();
  private VRPanel otherStationSubPnl = new VRPanel();
  private ACLabelContainer otherStationNmContainer = new ACLabelContainer();
  private ACComboBox stationName = new ACComboBox();
  private JLabel otherStationNmCaption = new JLabel();
//[ID:0000514][Tozo TANAKA] 2009/09/09 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//  private ACLabelContainer otherStationSijiUmuContainer = new ACLabelContainer();
  protected ACLabelContainer otherStationSijiUmuContainer = new ACLabelContainer();
//[ID:0000514][Tozo TANAKA] 2009/09/09 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
  private ACClearableRadioButtonGroup otherStationSiji = new
      ACClearableRadioButtonGroup();
//[ID:0000514][Tozo TANAKA] 2009/09/09 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//  private ACLabelContainer otherStationSijiContainer = new ACLabelContainer();
  protected ACLabelContainer otherStationSijiContainer = new ACLabelContainer();
//[ID:0000514][Tozo TANAKA] 2009/09/09 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
  private ACComboBox otherStationName = new ACComboBox();
  private JLabel otherStationSijiCaption = new JLabel();
  private VRPanel stationContainer = new VRPanel();

//[ID:0000731][Shin Fujihara] 2012/04/20 add begin �y2012�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
  protected ACLabelContainer kyuinStationSijiUmuContainer = new ACLabelContainer();
  private ACClearableRadioButtonGroup kyuinStationSiji = new ACClearableRadioButtonGroup();
  protected ACLabelContainer kyuinStationSijiContainer = new ACLabelContainer();
  private ACComboBox kyuinStationName = new ACComboBox();
  private JLabel kyuinStationSijiCaption = new JLabel();
//[ID:0000731][Shin Fujihara] 2012/04/20 add end �y2009�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�

public VRMap createSourceInnerBindComponent() {
    VRMap map = super.createSourceInnerBindComponent();
    map.setData("OTHER_STATION_SIJI", new Integer(1));
    // [ID:0000731][Shin Fujihara] 2012/04/20 add begin �y2012�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
    map.setData("KYUIN_STATION_SIJI", new Integer(1));
    // [ID:0000731][Shin Fujihara] 2012/04/20 add end �y2009�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
    return map;
  }

  public boolean noControlWarning() {


    if(otherStationSiji.getSelectedIndex()==2){
      //���X�e�[�V�����ւ̎w��

      String mainStationName = String.valueOf(stationName.getEditor().getItem());
      if(!"".equals(mainStationName) ){
        String subStationName = String.valueOf(otherStationName.getEditor().getItem());
        if(mainStationName.equals(subStationName) ){
          if (ACMessageBox.show(
              "�K��Ō�X�e�[�V����������ё��X�e�[�V�����ւ̎w���̖K��Ō�X�e�[�V��������\n�����ł��B\n��낵���ł����H",
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
    
    //[ID:0000731][Shin Fujihara] 2012/04/20 add begin �y2012�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
    kyuinStationName.setModel(IkenshoCommon.createComboAdapter(new
            VRHashMapArrayToConstKeyArrayAdapter(array, "MI_NM")));
    //[ID:0000731][Shin Fujihara] 2012/04/20 add end �y2009�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�

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
    
    //[ID:0000731][Shin Fujihara] 2012/04/20 add begin �y2012�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
    kyuinStationSiji.addListSelectionListener(new ACFollowDisableSelectionListener(new JComponent[] { kyuinStationName }, 1));
    //[ID:0000731][Shin Fujihara] 2012/04/20 add end �y2009�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�

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
    //��Ë@��
    otherStationSiji.setBindPath("OTHER_STATION_SIJI");

    getContentsGroup().add(stationContainer, VRLayout.NORTH);
    stationContainer.setLayout(new VRLayout());

//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//    getFollowDoctorContainer().add(iryoukikanHeader, VRLayout.NORTH);
//    getFollowDoctorContainer().add(kinkyuRenrakuContainer, VRLayout.FLOW_INSETLINE_RETURN);
//    getFollowDoctorContainer().add(fuzaijiTaiouContainer, VRLayout.FLOW_INSETLINE_RETURN);
    addFollowDoctorContainer();
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
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
    //��Ë@�� / Grp / header
    //�ً}���̘A����
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//    iryoukikanHeader.setText("�ȏ�̂Ƃ���A�w��K��Ō�̎��{���w���������܂��B");
//    kinkyuRenrakuContainer.setText("�ً}���̘A����");
//    kinkyuRenrakuContainer.add(kinkyuRenraku, null);
    getIryoukikanHeader().setText("�ȏ�̂Ƃ���A�w��K��Ō�̎��{���w���������܂��B");
    getKinkyuRenrakuContainer().setText("�ً}���̘A����");
    getKinkyuRenrakuContainer().add(kinkyuRenraku, null);
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
    kinkyuRenraku.setColumns(40);
    kinkyuRenraku.setEditable(false);
    kinkyuRenraku.setBindPath("KINKYU_RENRAKU");
    //�s�ݎ��̑Ή��@
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//    fuzaijiTaiouContainer.setText("�s�ݎ��̑Ή��@");
//    fuzaijiTaiouContainer.add(fuzaijiTaiou, null);
    getFuzaijiTaiouContainer().setText("�s�ݎ��̑Ή��@");
    getFuzaijiTaiouContainer().add(fuzaijiTaiou, null);
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
    fuzaijiTaiou.setColumns(40);
    fuzaijiTaiou.setEditable(false);
    fuzaijiTaiou.setBindPath("FUZAIJI_TAIOU");
    //���Ō�X�e�[�V������
    otherStationNmContainer.setText("�K��Ō�X�e�[�V����");
    otherStationNmContainer.add(stationName, null);
    otherStationNmContainer.add(otherStationNmCaption, null);
    stationName.setPreferredSize(new Dimension(400, 20));
    stationName.setMaxLength(30);
    stationName.setColumns(30);
    stationName.setIMEMode(InputSubset.KANJI);
    stationName.setBindPath("STATION_NM");
    otherStationNmCaption.setText("�a");
    //���X�e�[�V�����ւ̎w���̗L��
    otherStationSijiUmuContainer.setText("���X�e�[�V�����ւ̎w��");
    otherStationSijiUmuContainer.add(otherStationSiji, null);
    otherStationSiji.setUseClearButton(false);
    otherStationSiji.setModel(new VRListModelAdapter(
        new VRArrayList(Arrays.asList(new String[] {
                                      "��",
                                      "�L"}))));
    //���X�e�[�V�����w��
    otherStationSijiContainer.setText("�K��Ō�X�e�[�V����");
    otherStationSijiContainer.add(otherStationName, null);
    otherStationSijiContainer.add(otherStationSijiCaption, null);
    otherStationName.setPreferredSize(new Dimension(400, 20));
    otherStationName.setMaxLength(30);
    otherStationName.setColumns(30);
    otherStationName.setIMEMode(InputSubset.KANJI);
    otherStationName.setBindPath("OTHER_STATION_NM");
    otherStationSijiCaption.setText("�a");
    
    //[ID:0000731][Shin Fujihara] 2012/04/20 add begin �y2012�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
    //����z���w���̗L��
    kyuinStationSijiUmuContainer.setText("����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w��");
    kyuinStationSijiUmuContainer.add(kyuinStationSiji, null);
    kyuinStationSiji.setUseClearButton(false);
    kyuinStationSiji.setBindPath("KYUIN_STATION_SIJI");
    kyuinStationSiji.setModel(new VRListModelAdapter(
        new VRArrayList(Arrays.asList(new String[] {
                                      "��",
                                      "�L"}))));
    //����z���K���쎖�Ə�
    kyuinStationSijiContainer.setText("�K���쎖�Ə�");
    kyuinStationSijiContainer.add(kyuinStationName, null);
    kyuinStationSijiContainer.add(kyuinStationSijiCaption, null);
    kyuinStationName.setPreferredSize(new Dimension(400, 20));
    kyuinStationName.setMaxLength(30);
    kyuinStationName.setColumns(30);
    kyuinStationName.setIMEMode(InputSubset.KANJI);
    kyuinStationName.setBindPath("KYUIN_STATION_NM");
    kyuinStationSijiCaption.setText("�a");
    
    otherStationSubPnl.add(kyuinStationSijiUmuContainer,
            VRLayout.FLOW_INSETLINE_RETURN);
    otherStationSubPnl.add(kyuinStationSijiContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
    //[ID:0000731][Shin Fujihara] 2012/04/20 add end �y2009�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
  }
  
//[ID:0000514][Tozo TANAKA] 2009/09/09 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
  /**
   * iryoukikanHeader ��Ԃ��܂��B
   * @return iryoukikanHeader
   */
  protected JLabel getIryoukikanHeader() {
      if(iryoukikanHeader==null){
          iryoukikanHeader = new JLabel();
      }
      return iryoukikanHeader;
  }

/**
 * fuzaijiTaiouContainer ��Ԃ��܂��B
 * @return fuzaijiTaiouContainer
 */
protected ACLabelContainer getFuzaijiTaiouContainer() {
    if(fuzaijiTaiouContainer==null){
        fuzaijiTaiouContainer = new ACLabelContainer();
    }
    return fuzaijiTaiouContainer;
}

/**
 * kinkyuRenrakuContainer ��Ԃ��܂��B
 * @return kinkyuRenrakuContainer
 */
protected ACLabelContainer getKinkyuRenrakuContainer() {
    if(kinkyuRenrakuContainer==null){
        kinkyuRenrakuContainer = new ACLabelContainer();
    }
    return kinkyuRenrakuContainer;
}

/**
 * followDoctorContainer�ւ̒ǉ����`���܂��B
 */
protected void addFollowDoctorContainer(){
    getFollowDoctorContainer().add(getIryoukikanHeader(), VRLayout.NORTH);
    getFollowDoctorContainer().add(getKinkyuRenrakuContainer(), VRLayout.FLOW_INSETLINE_RETURN);
    getFollowDoctorContainer().add(getFuzaijiTaiouContainer(), VRLayout.FLOW_INSETLINE_RETURN);
}

//[ID:0000514][Tozo TANAKA] 2009/09/09 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

}

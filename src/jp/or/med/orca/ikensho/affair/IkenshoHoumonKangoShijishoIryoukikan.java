package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JLabel;

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
  private JLabel iryoukikanHeader = new JLabel();
  private ACLabelContainer kinkyuRenrakuContainer = new ACLabelContainer();
  private ACTextField kinkyuRenraku = new ACTextField();
  private ACLabelContainer fuzaijiTaiouContainer = new ACLabelContainer();
  private ACTextField fuzaijiTaiou = new ACTextField();
  private VRPanel otherStationPnl = new VRPanel();
  private VRPanel otherStationSubPnl = new VRPanel();
  private ACLabelContainer otherStationNmContainer = new ACLabelContainer();
  private ACComboBox stationName = new ACComboBox();
  private JLabel otherStationNmCaption = new JLabel();
  private ACLabelContainer otherStationSijiUmuContainer = new ACLabelContainer();
  private ACClearableRadioButtonGroup otherStationSiji = new
      ACClearableRadioButtonGroup();
  private ACLabelContainer otherStationSijiContainer = new ACLabelContainer();
  private ACComboBox otherStationName = new ACComboBox();
  private JLabel otherStationSijiCaption = new JLabel();
  private VRPanel stationContainer = new VRPanel();



  public VRMap createSourceInnerBindComponent() {
    VRMap map = super.createSourceInnerBindComponent();
    map.setData("OTHER_STATION_SIJI", new Integer(1));

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

    getFollowDoctorContainer().add(iryoukikanHeader, VRLayout.NORTH);
    getFollowDoctorContainer().add(kinkyuRenrakuContainer, VRLayout.FLOW_INSETLINE_RETURN);
    getFollowDoctorContainer().add(fuzaijiTaiouContainer, VRLayout.FLOW_INSETLINE_RETURN);
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
    iryoukikanHeader.setText("�ȏ�̂Ƃ���A�w��K��Ō�̎��{���w���������܂��B");
    //�ً}���̘A����
    kinkyuRenrakuContainer.setText("�ً}���̘A����");
    kinkyuRenrakuContainer.add(kinkyuRenraku, null);
    kinkyuRenraku.setColumns(40);
    kinkyuRenraku.setEditable(false);
    kinkyuRenraku.setBindPath("KINKYU_RENRAKU");
    //�s�ݎ��̑Ή��@
    fuzaijiTaiouContainer.setText("�s�ݎ��̑Ή��@");
    fuzaijiTaiouContainer.add(fuzaijiTaiou, null);
    fuzaijiTaiou.setColumns(40);
    fuzaijiTaiou.setEditable(false);
    fuzaijiTaiou.setBindPath("FUZAIJI_TAIOU");
    //���Ō�X�e�[�V������
    otherStationNmContainer.setText("�K��Ō�X�e�[�V����");
    otherStationNmContainer.add(stationName, null);
    otherStationNmContainer.add(otherStationNmCaption, null);
    stationName.setPreferredSize(new Dimension(400, 20));
    stationName.setMaxLength(30);
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
    otherStationName.setIMEMode(InputSubset.KANJI);
    otherStationName.setBindPath("OTHER_STATION_NM");
    otherStationSijiCaption.setText("�a");
  }
}

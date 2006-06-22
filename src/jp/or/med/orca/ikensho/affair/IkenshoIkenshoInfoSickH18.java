package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.im.InputSubset;

import javax.swing.JComponent;

import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.event.ACFollowDisableSelectionListener;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;


/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoSickH18 extends IkenshoIkenshoInfoSick {
  private ACLabelContainer notStableStateContainer;
  private ACComboBox noteStableState;

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
    super.initDBCopmponent(dbm);
    applyPoolTeikeibun(noteStableState, IkenshoCommon.TEIKEI_INSECURE_CONDITION_NAME);
  }

  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoIkenshoInfoSickH18() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * �s���莞�̋�̓I�ȏ󋵃R���e�i��Ԃ��܂��B
   * @return �s���莞�̋�̓I�ȏ󋵃R���e�i
   */
  protected ACLabelContainer getNotStableStateContainer() {
    if(notStableStateContainer==null){
      notStableStateContainer = new ACLabelContainer();
    }
    return notStableStateContainer;
  }

  /**
   * �s���莞�̋�̓I�ȏ󋵂�Ԃ��܂��B
   * @return �s���莞�̋�̓I�ȏ�
   */
  protected ACComboBox getNotStableState() {
    if(noteStableState==null){
      noteStableState = new ACComboBox();
    }
    return noteStableState;
  }

  protected void addSickStableGroup(){
    super.addSickStableGroup();
    getSickStableGroup().add(getNotStableStateContainer(), VRLayout.FLOW);
  }

  protected void addSickStableAndOutlook(){
    getStableAndOutlook().add(getSickStableGroup(), VRLayout.CLIENT);
  }

  protected void bindSourceInnerBindComponent() throws Exception {
    super.bindSourceInnerBindComponent();

    if(getSickStable().getSelectedIndex()!=2){
      getNotStableState().setText("");
    }
  }

  private void jbInit() throws Exception {
//    getOutlookGroup().setVisible(false);
//    getOutlook().setBindPath("");
    getSickStable().addListSelectionListener(new
        ACFollowDisableSelectionListener(new JComponent[] {
                                         getNotStableState()}
                                         , 1));

    getNotStableStateContainer().add(getNotStableState());
    getNotStableStateContainer().setText("��̓I�ȏ�");
    //�s����ɂ������̓I�ȏ󋵂̃o�C���h�p�X
    getNotStableState().setBindPath("INSECURE_CONDITION");

    getSickProgresss().setText("���a�܂���" + IkenshoConstants.LINE_SEPARATOR +
                               "���莾�a�̌o��" + IkenshoConstants.LINE_SEPARATOR +
                               "�i250����" + IkenshoConstants.LINE_SEPARATOR +
                               "�܂���5�s�ȓ��j");

    getNotStableState().setPreferredSize(new Dimension(400,20));
    getNotStableState().setMaxLength(30);
    getNotStableState().setIMEMode(InputSubset.KANJI);
    getSickNameGroup().setText("�f�f���i���莾�a�܂��͐����@�\�ቺ�̒��ڂ̌����ƂȂ��Ă��鏝�a���ɂ��Ă͂P�D�ɋL���j�y�є��ǔN����");
    getProgressGroup().setText("�����@�\�ቺ�̒��ڂ̌����ƂȂ��Ă��鏝�a�܂��͓��莾�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e");
//    getSickDate1().setEraRange(2);
//    getSickDate2().setEraRange(2);
//    getSickDate3().setEraRange(2);
//    getSickDate1().setAllowedUnknown(false);
//    getSickDate2().setAllowedUnknown(false);
//    getSickDate3().setAllowedUnknown(false);
  }

}

package jp.or.med.orca.ikensho.affair;

import java.awt.FlowLayout;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.component.ACBitCheckBoxGroup;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.event.ACFollowDisableSelectionListener;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoApplicant
    extends IkenshoDocumentAffairApplicant {

  private ACGroupBox applicantAgreeGroup = new ACGroupBox();
  private VRPanel applicantSub = new VRPanel();
  private ACGroupBox createCountGroup = new ACGroupBox();
  private ACGroupBox otherDepartmentGroup = new ACGroupBox();

  private ACComboBox otherDepartmentOther = new ACComboBox();
  private ACLabelContainer otherDepartmentUseCaption = new ACLabelContainer();
  private VRLabel otherDepartmentOtherTailMessage = new VRLabel();
  private ACLabelContainer createCountCaption = new ACLabelContainer();
  private ACClearableRadioButtonGroup otherDepartment = new ACClearableRadioButtonGroup();
  private VRLayout otherDepartmentLayout = new VRLayout();
  private ACClearableRadioButtonGroup applicantAgree = new ACClearableRadioButtonGroup();
  private IkenshoOtherDepartmentUseCheckBoxGroup otherDepartmentUse = new
      IkenshoOtherDepartmentUseCheckBoxGroup();
  private ACLabelContainer lastExaminationDateCaption = new ACLabelContainer();
  private ACGroupBox writeDateGroup = new ACGroupBox();
  private ACIntegerCheckBox otherDepartmentOtherUse = new
      ACIntegerCheckBox();
  private VRPanel otherDepartmentOtherPanel = new VRPanel();
  private ACLabelContainer otherDepartmentCaption = new ACLabelContainer();
  private ACGroupBox lastExaminationDateGroup = new ACGroupBox();
  private ACClearableRadioButtonGroup createCount = new ACClearableRadioButtonGroup();
  private ACParentHesesPanelContainer otherDepartmentOthers = new
      ACParentHesesPanelContainer();
  private IkenshoEraDateTextField lastExaminationDate = new IkenshoEraDateTextField();
  private VRLayout applicantSubLayout = new VRLayout();
  private FlowLayout otherDepartmentOtherPanelLayout = new FlowLayout();
  private VRLayout otherDepartmentUseCaptionLayout = new VRLayout();

  /**
   * ��t�̓��ӃO���[�v��Ԃ��܂��B
   * @return ��t�̓��ӃO���[�v
   */
  protected ACGroupBox getAgreeGroup(){
    return applicantAgreeGroup;
  }

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
    IkenshoCommon.setMSinryouka(dbm, otherDepartmentOther,
                                IkenshoCommon.DEPARTMENT_OTHER);

  }


  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoIkenshoInfoApplicant() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    otherDepartmentOtherUse.addItemListener(new
                                            ACFollowDisabledItemListener(new
        JComponent[] {otherDepartmentOther, otherDepartmentOthers, }));

    otherDepartment.addListSelectionListener(new
        ACFollowDisableSelectionListener(new JComponent[] {
                                              otherDepartmentUseCaption,
                                              otherDepartmentOthers,
                                              otherDepartmentOther,
                                              otherDepartmentOtherUse, }));
    otherDepartment.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
          return;
        }
        boolean used = e.getFirstIndex() == 0;
        if (used) {
          boolean selected = otherDepartmentOtherUse.isSelected();
          otherDepartmentOther.setEnabled(selected);
          otherDepartmentOthers.setEnabled(selected);
        }
        int end = otherDepartmentUse.getCheckBoxCount();
        for (int i = 0; i < end; i++) {
          otherDepartmentUse.getCheckBox(i).setEnabled(used);
        }
      }
    });

    int end = otherDepartmentUse.getCheckBoxCount();
    for (int i = 0; i < end; i++) {
      otherDepartmentUse.getCheckBox(i).setEnabled(false);
    }

  }

  private void jbInit() throws Exception {
    //Model
    otherDepartmentUse.setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new String[] {"����", "���_��", "�O��", "���`�O��",
                                  "�]�_�o�O��", "�畆��", "��A���", "�w�l��", "���", "���@��A��",
                                  "���n�r���e�[�V������", "����", }))));
    applicantAgreeGroup.setText("�{�ӌ��������T�[�r�X�v��쐬�ɗ��p����邱�Ƃ�");
    otherDepartment.setBindPath("TAKA_FLAG");
    otherDepartmentOtherUse.setBindPath("TAKA_OTHER_FLAG");
    applicantEasts.add(applicantAgreeGroup, null);
    applicantAgreeGroup.add(applicantAgree, null);

    applicantSub.setLayout(applicantSubLayout);
    applicantSub.add(lastExaminationDateGroup, VRLayout.CLIENT);
    applicantSub.add(writeDateGroup, VRLayout.CLIENT);
    createCountGroup.setLayout(new VRLayout());
    createCountGroup.add(createCountCaption, VRLayout.FLOW);
    createCountCaption.add(createCount, null);

    otherDepartmentGroup.setLayout(otherDepartmentLayout);
    otherDepartmentGroup.add(otherDepartmentCaption,
                             VRLayout.NORTH);
    otherDepartmentGroup.add(otherDepartmentUseCaption,
                             VRLayout.CLIENT);
//    otherDepartmentGroup.add(otherDepartmentUseCaption,
//                             VRLayout.NORTH);
//    otherDepartmentGroup.add(otherDepartmentCaption,
//                             VRLayout.FLOW_INSETLINE_RETURN);
//    otherDepartmentGroup.add(otherDepartmentUseCaption,
//                             VRLayout.FLOW_INSETLINE_RETURN);
//    otherDepartmentGroup.add(otherDepartmentCaption,
//                             BorderLayout.NORTH);
//    otherDepartmentGroup.add(otherDepartmentUseCaption,
//                             BorderLayout.CENTER);

    this.add(applicantSub, VRLayout.NORTH);
    this.add(createCountGroup, VRLayout.NORTH);
//    this.add(otherDepartmentGroup, VRLayout.NORTH);
    this.add(otherDepartmentGroup, VRLayout.CLIENT);


    createCount.setModel(new VRListModelAdapter(new
                                                VRArrayList(Arrays.asList(new
        String[] {"����", "�Q��ڈȏ�"}))));
    applicantAgree.setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new
                                  String[] {"���ӂ���", "���ӂ��Ȃ�"}))));
    otherDepartment.setModel(new VRListModelAdapter(new
      VRArrayList(Arrays.asList(new String[] {"�L", "��"}))));

    otherDepartmentOtherPanelLayout.setHgap(0);
    otherDepartmentOtherPanel.setLayout(otherDepartmentOtherPanelLayout);
    applicantSubLayout.setAutoWrap(false);
    applicantSubLayout.setFitHLast(true);
    otherDepartmentUseCaption.setEnabled(false);
    otherDepartmentUseCaption.setText("�i�L�̏ꍇ�j��");
    otherDepartmentUseCaption.setLayout(otherDepartmentUseCaptionLayout);
    otherDepartmentOther.setMaxLength(6);
    otherDepartmentOther.setBindPath("TAKA_OTHER");
    otherDepartmentOther.setEnabled(false);
    otherDepartmentOther.setIMEMode(InputSubset.KANJI);
    otherDepartmentOtherTailMessage.setText("�i6�����ȓ��j");
    otherDepartmentOtherTailMessage.setForeground(IkenshoConstants.
                                                  COLOR_MESSAGE_TEXT_FOREGROUND);
    createCountCaption.setText("�쐬��");
    otherDepartment.setUseClearButton(false);
    otherDepartment.setSelectedIndex(2);
    writeDate.setBindPath("KINYU_DT");
    writeDate.setAgeVisible(false);
    writeDateCaption.setText("�L�����F");
//    otherDepartmentLayout.setFitHLast(true);
    otherDepartmentLayout.setHgap(0);
    applicantTitle.setText("�\����");
    applicantAgree.setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new
                                  String[] {"���ӂ���", "���ӂ��Ȃ�"}))));
    applicantAgree.setBindPath("DR_CONSENT");
    applicantAgree.setUseClearButton(false);
    lastExaminationDateCaption.setText("�ŏI�f�@���F");
    writeDateGroup.setLayout(new VRLayout());
    otherDepartmentOtherUse.setEnabled(false);
    otherDepartmentOtherUse.setText("���̑�");
    otherDepartmentCaption.setText("���Ȏ�f�̗L��");
    lastExaminationDateGroup.setLayout(new VRLayout());
    createCount.setModel(new VRListModelAdapter(new
                                                VRArrayList(Arrays.asList(new
        String[] {"����", "�Q��ڈȏ�"}))));
    createCount.setUseClearButton(false);
    createCount.setBindPath("IKN_CREATE_CNT");
    createCount.setSelectedIndex(1);
    lastExaminationDate.setBindPath("LASTDAY");
    lastExaminationDate.setAgeVisible(false);
    otherDepartmentUse.setBindPath("TAKA");
    otherDepartmentOthers.setEnabled(false);
    lastExaminationDateGroup.add(lastExaminationDateCaption,
                                 VRLayout.FLOW_INSETLINE);
    lastExaminationDateCaption.add(lastExaminationDate, null);
    writeDateGroup.add(writeDateCaption, VRLayout.FLOW_INSETLINE);
    writeDateCaption.add(writeDate, null);
    otherDepartmentCaption.add(otherDepartment, null);
    otherDepartmentUseCaption.add(otherDepartmentUse, VRLayout.NORTH);
    otherDepartmentOtherPanel.add(otherDepartmentOtherUse, null);
    otherDepartmentOtherPanel.add(otherDepartmentOthers, null);
    otherDepartmentOtherPanel.add(otherDepartmentOtherTailMessage, null);
    otherDepartmentOthers.add(otherDepartmentOther, null);
    otherDepartmentUseCaption.add(otherDepartmentOtherPanel,
                                  VRLayout.FLOW_INSETLINE_RETURN);
  }

  public boolean noControlError() throws Exception {

    //�G���[�`�F�b�N
    if(!super.noControlError()){
      return false;
    }

    switch (writeDate.getInputStatus()) {
      case IkenshoEraDateTextField.STATE_VALID:
        break;
      case IkenshoEraDateTextField.STATE_FUTURE:
        ACMessageBox.showExclamation("�����̓��t�ł��B");
        writeDate.requestChildFocus();
        return false;
      default:
        ACMessageBox.showExclamation("���t�Ɍ�肪����܂��B");
        writeDate.requestChildFocus();
        return false;
    }


    switch (lastExaminationDate.getInputStatus()) {
      case IkenshoEraDateTextField.STATE_EMPTY:
        break;
      case IkenshoEraDateTextField.STATE_VALID:
        //�L���Ȃ�΋L�����Ƃ̑O��`�F�b�N
        if (lastExaminationDate.getDate().compareTo(writeDate.getDate())>0) {
          //�L�����̂ق����Â�
          ACMessageBox.show("�ŏI�f�@���͋L�����ȑO�łȂ���΂Ȃ�܂���B");
          lastExaminationDate.requestChildFocus();
          return false;
        }
        break;
      case IkenshoEraDateTextField.STATE_FUTURE:
        ACMessageBox.showExclamation("�����̓��t�ł��B");
        lastExaminationDate.requestChildFocus();
        return false;
      default:
        ACMessageBox.show("���t�Ɍ�肪����܂��B");
        lastExaminationDate.requestChildFocus();
        return false;
    }

    if (otherDepartment.getSelectedIndex() == 1) {
      //���Ȏ�f����
      if (otherDepartmentOtherUse.isSelected()) {
        if (IkenshoCommon.isNullText(otherDepartmentOther.getEditor().getItem())) {
          ACMessageBox.showExclamation("�u���Ȏ�f�i���̑��j�v�Ŗ��L��������܂��B");
          otherDepartmentOtherUse.requestFocus();
          return false;
        }
      }else{
        if (otherDepartmentUse.getSelectedBits() == 0) {
          ACMessageBox.showExclamation("�u���Ȏ�f�v�Ŗ��L��������܂��B");
          otherDepartment.requestChildFocus();
          return false;
        }
      }

    }

    return true;
  }

  protected void bindSourceInnerBindComponent() throws Exception {
    super.bindSourceInnerBindComponent();

    otherDepartmentOtherUse.setSelected(!IkenshoCommon.isNullText(
        otherDepartmentOther.getEditor().getItem()));

    if ( (otherDepartmentUse.getSelectedBits() > 0) ||
        (otherDepartmentOtherUse.isSelected())) {
      otherDepartment.setSelectedIndex(1);
    }else{
      otherDepartment.setSelectedIndex(2);
    }
  }


  /** TODO <HEAD_IKENSYO> */
  private class IkenshoOtherDepartmentUseCheckBoxGroup
      extends ACBitCheckBoxGroup {
    public IkenshoOtherDepartmentUseCheckBoxGroup() {
      super();
      setLayout(new VRLayout());
    }

    protected void addItem(JCheckBox item) {
      if (this.getComponentCount() + 1 == getModel().getSize()) {
        this.add(item, VRLayout.FLOW_RETURN);
      }
      else {
        this.add(item, VRLayout.FLOW);
      }
    }
  }

  /**
   * ���f��I�����Ă��邩��Ԃ��܂��B
   * @return ���f��I�����Ă��邩
   */
  public boolean isFirstCreate(){
    return createCount.getSelectedIndex()==1;
  }
}

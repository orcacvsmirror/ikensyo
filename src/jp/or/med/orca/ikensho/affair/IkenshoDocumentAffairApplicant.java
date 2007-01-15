package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.im.InputSubset;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACKanaSendTextField;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.IkenshoTelTextField;
import jp.or.med.orca.ikensho.component.IkenshoZipTextField;
import jp.or.med.orca.ikensho.event.IkenshoDocumentAffairApplicantNameChageListener;
import jp.or.med.orca.ikensho.event.IkenshoEraDateTextFieldEvent;
import jp.or.med.orca.ikensho.event.IkenshoEraDateTextFieldListener;
import jp.or.med.orca.ikensho.event.IkenshoWriteDateChangeListener;

/** TODO <HEAD_IKENSYO> */
public class IkenshoDocumentAffairApplicant extends IkenshoTabbableChildAffairContainer {
  private static final int CARE_AGE_START = 40;
  private transient Vector writeDateChangeListeners;

  protected IkenshoDocumentTabTitleLabel applicantTitle = new IkenshoDocumentTabTitleLabel();
  protected IkenshoEraDateTextField writeDate = new IkenshoEraDateTextField();
  protected ACLabelContainer writeDateCaption = new ACLabelContainer();
  protected VRPanel applicantEasts = new VRPanel();

  private VRPanel applicantNamePanel = new VRPanel();
  private VRPanel applicantNameKana = new VRPanel();
  private ACLabelContainer applicantTels = new ACLabelContainer();
  private IkenshoTelTextField applicantTel = new IkenshoTelTextField();
  private ACGroupBox applicantInfoGroup = new ACGroupBox();
  private ACTextField applicantKana = new ACTextField();
  private ACLabelContainer applicantNames = new ACLabelContainer();
  private VRLabel applicantZipSpacer = new VRLabel();
  private ACLabelContainer applicantAddresss = new ACLabelContainer();
  private ACLabelContainer applicantZips = new ACLabelContainer();
  private ACClearableRadioButtonGroup applicantSex = new ACClearableRadioButtonGroup();
  private VRLabel applicantBirthSpacer = new VRLabel();
  private ACKanaSendTextField applicantName = new ACKanaSendTextField();
  private ACLabelContainer applicantBirths = new ACLabelContainer();
  private ACTextField applicantAddress = new ACTextField();
  private ACLabelContainer applicantSexs = new ACLabelContainer();
  private VRLayout applicantLayout = new VRLayout();
  private VRLabel applicantTelSpacer = new VRLabel();
  private ACLabelContainer applicantKanas = new ACLabelContainer();
  private VRLayout applicantInfoGroupLayout = new VRLayout();
  private VRLabel applicantSexSpacer = new VRLabel();
  private IkenshoEraDateTextField applicantBirth = new IkenshoEraDateTextField();
  private VRLabel applicantBirthTail = new VRLabel();
  private IkenshoZipTextField applicantZip = new IkenshoZipTextField();
  // replace begin 2006/12/20 kamitsukasa
  protected VRLabel outOfCareRange = new VRLabel();
  // replace end 2006/12/20 kamitsukasa
  private transient Vector applicantNameChageListeners;

  public VRMap createSourceInnerBindComponent() {
    VRMap map = super.createSourceInnerBindComponent();
    //�L�����͍����ŏ�����
    map.setData("KINYU_DT", new Date());
    return map;
  }

  /**
   * �L������Ԃ��܂��B
   * @return �L����
   */
  public Date getWriteDate(){
    return writeDate.getDate();
  }

  /**
   * ���ی��K�p�͈͓��̔N��ł��邩���`�F�b�N���܂��B
   */
  protected void checkWriteDateAge() {
    if ( (applicantBirth.getInputStatus() == IkenshoEraDateTextField.STATE_VALID) &&
        (writeDate.getInputStatus() == IkenshoEraDateTextField.STATE_VALID)) {
      //�L���ȏꍇ�����A���ی��K�p�͈͓����`�F�b�N����
      Calendar birthCal = Calendar.getInstance();
      Calendar writeCal = Calendar.getInstance();
      birthCal.setTime(applicantBirth.getDate());
      writeCal.setTime(writeDate.getDate());
      if (birthCal.after(writeCal)) {
        //�L�����̂ق����a�������ȑO�Ȃ�ꗥ��
        applicantBirth.setAge("");
      }
      else {
        //Calendar��0�N�͕\���ł��Ȃ����߁A��悹����
        writeCal.add(Calendar.YEAR, -birthCal.get(Calendar.YEAR) + 2);
        writeCal.add(Calendar.DAY_OF_YEAR,
                     -birthCal.get(Calendar.DAY_OF_YEAR) + 1);
        int age = writeCal.get(Calendar.YEAR) - 2;
        applicantBirth.setAge(String.valueOf(age));
        if (age >= CARE_AGE_START) {
          outOfCareRange.setVisible(false);
        }
        else {
          outOfCareRange.setVisible(true);
        }
      }
    }
  }

  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoDocumentAffairApplicant() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    applicantBirth.addDatePanelListener(new IkenshoEraDateTextFieldListener() {
      public void valueChanged(IkenshoEraDateTextFieldEvent e) {
        checkWriteDateAge();
      }
    }
    );
    writeDate.addDatePanelListener(new IkenshoEraDateTextFieldListener() {
      public void valueChanged(IkenshoEraDateTextFieldEvent e) {
        checkWriteDateAge();
        fireWriteDataChanged(new java.awt.event.ComponentEvent(writeDate, 0));
      }
    }
    );
    applicantName.getDocument().addDocumentListener(new DocumentListener(){
      public void changedUpdate(DocumentEvent e) {
        fireApplicantNameChanged(new EventObject(applicantName));
      }

      public void insertUpdate(DocumentEvent e) {
        fireApplicantNameChanged(new EventObject(applicantName));
      }

      public void removeUpdate(DocumentEvent e) {
        fireApplicantNameChanged(new EventObject(applicantName));
      }
    });

    outOfCareRange.setVisible(false);
  }

  private void jbInit() throws Exception {
    applicantSex.setModel(new VRListModelAdapter(new VRArrayList(Arrays.asList(new
        String[] {"�j", "��"}))));

    applicantNameKana.setLayout(new VRLayout());
    setLayout(applicantLayout);
    applicantEasts.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    applicantTels.setText("�d�b�ԍ�");
    applicantTel.setBindPathArea("TEL1");
    applicantTel.setBindPathNumber("TEL2");
    applicantInfoGroup.setLayout(applicantInfoGroupLayout);
    writeDate.setBindPath("KINYU_DT");
    writeDate.setAllowedBlank(false);
    writeDate.setAgeVisible(false);
    writeDateCaption.setText("�L�����F");
    applicantTitle.setText("�\����");
    applicantKana.setIMEMode(InputSubset.KANJI);
    applicantKana.setColumns(30);
    applicantKana.setBindPath("PATIENT_KN");
    applicantKana.setMaxLength(30);
    applicantKana.setIMEMode(InputSubset.KANJI);
    applicantNames.setText("����");
    applicantAddresss.setText("�Z��");
    applicantZips.setText("�X�֔ԍ�");
    applicantSex.setBindPath("SEX");
    applicantSex.setUseClearButton(false);
    applicantName.setColumns(30);
    applicantName.setIMEMode(InputSubset.KANJI);
    applicantName.setBindPath("PATIENT_NM");
    applicantName.setMaxLength(15);
    applicantName.setKanaField(applicantKana);
    applicantName.setIMEMode(InputSubset.KANJI);
    applicantBirths.setText("���N����");
    applicantAddress.setIMEMode(InputSubset.KANJI);
    applicantAddress.setColumns(45);
    applicantAddress.setBindPath("ADDRESS");
    applicantAddress.setIMEMode(InputSubset.KANJI);
    applicantAddress.setMaxLength(45);
    applicantSexs.setText("����");
    applicantLayout.setFitVLast(true);
    applicantLayout.setFitHLast(true);
    applicantNamePanel.setLayout(new BorderLayout());
    applicantKanas.setText("�ӂ肪��");
    applicantInfoGroupLayout.setFitHLast(true);
    applicantBirth.setBindPath("BIRTHDAY");
    applicantBirthTail.setText("�i�L�������_�j");
    applicantBirthTail.setForeground(IkenshoConstants.
                                     COLOR_MESSAGE_TEXT_FOREGROUND);
    applicantZip.setAddressTextField(applicantAddress);
    applicantZip.setBindPath("POST_CD");
    outOfCareRange.setForeground(IkenshoConstants.
                                 COLOR_MESSAGE_ALART_TEXT_FOREGROUND);
    outOfCareRange.setDisplayedMnemonic('0');
    outOfCareRange.setText("���ی��K�p�͈͊O�ł��B");
    applicantNamePanel.add(applicantNameKana, BorderLayout.CENTER);
    applicantNameKana.add(applicantNames, VRLayout.FLOW_INSETLINE_RETURN);
    applicantNames.add(applicantName, null);
    applicantNameKana.add(applicantKanas, VRLayout.FLOW_INSETLINE_RETURN);
    applicantKanas.add(applicantKana, null);
    applicantNamePanel.add(applicantEasts,  BorderLayout.EAST);
    applicantSexs.add(applicantSex, null);
    applicantBirths.add(applicantBirth, null);
    applicantBirths.add(applicantBirthTail, null);
    applicantBirths.add(outOfCareRange, null);
    applicantInfoGroup.add(applicantNamePanel, VRLayout.FLOW_INSETLINE_RETURN);
    applicantInfoGroup.add(applicantSexs, VRLayout.FLOW_INSETLINE);
    applicantInfoGroup.add(applicantSexSpacer, VRLayout.FLOW_INSETLINE_RETURN);
    applicantInfoGroup.add(applicantBirths, VRLayout.FLOW_INSETLINE);
    applicantInfoGroup.add(applicantBirthSpacer, VRLayout.FLOW_INSETLINE_RETURN);
    applicantInfoGroup.add(applicantZips, VRLayout.FLOW_INSETLINE);
    applicantInfoGroup.add(applicantZipSpacer, VRLayout.FLOW_INSETLINE_RETURN);
    applicantInfoGroup.add(applicantAddresss, VRLayout.FLOW_INSETLINE_RETURN);
    applicantInfoGroup.add(applicantTels, VRLayout.FLOW_INSETLINE);
    applicantInfoGroup.add(applicantTelSpacer, VRLayout.FLOW_INSETLINE_RETURN);
    applicantZips.add(applicantZip, null);
    applicantAddresss.add(applicantAddress, null);
    applicantTels.add(applicantTel, null);
    writeDateCaption.add(writeDate, null);
//
    this.add(applicantTitle, VRLayout.NORTH);
    this.add(applicantInfoGroup, VRLayout.NORTH);
  }

  public boolean noControlError() throws Exception {

    //�G���[�`�F�b�N
    if (isNullText(applicantName.getText())) {
      ACMessageBox.show("��������͂��Ă��������B");
      applicantName.requestFocus();
      return false;
    }

    if (applicantSex.getSelectedIndex() == applicantSex.getNoSelectIndex()) {
      ACMessageBox.show("���ʂ�I�����Ă��������B");
      applicantSex.requestChildFocus();
      return false;
    }

    switch (applicantBirth.getInputStatus()) {
      case IkenshoEraDateTextField.STATE_VALID:
        break;
      case IkenshoEraDateTextField.STATE_FUTURE:
        ACMessageBox.showExclamation("�����̓��t�ł��B");
        applicantBirth.requestChildFocus();
        return false;
      default:
        ACMessageBox.showExclamation("���t�Ɍ�肪����܂��B");
        applicantBirth.requestChildFocus();
        applicantBirth.setParentColor(false);
        return false;
    }

    return true;
  }

  public Component getFirstFocusComponent() {
    return applicantName;
  }

  protected void applySourceInnerBindComponent() throws Exception {
    super.applySourceInnerBindComponent();

    VRBindSource source = getMasterSource();
    if ( source != null){
      //�ӌ����L�������_�̓��t��ݒ�
      source.setData("AGE", applicantBirth.getAge());
    }

  }

  /**
   * �L�����̕ύX�C�x���g���X�i�����O���܂��B
   * @param l �L�����̕ύX�C�x���g���X�i
   */
  public synchronized void removeWriteDateChangeListener(
      IkenshoWriteDateChangeListener l) {
    if (writeDateChangeListeners != null && writeDateChangeListeners.contains(l)) {
      Vector v = (Vector) writeDateChangeListeners.clone();
      v.removeElement(l);
      writeDateChangeListeners = v;
    }
  }

  /**
   * �L�����̕ύX�C�x���g���X�i��ǉ����܂��B
   * @param l �L�����̕ύX�C�x���g���X�i
   */
  public synchronized void addWriteDateChangeListener(
      IkenshoWriteDateChangeListener l) {
    Vector v = writeDateChangeListeners == null ? new Vector(2) :
        (Vector) writeDateChangeListeners.clone();
    if (!v.contains(l)) {
      v.addElement(l);
      writeDateChangeListeners = v;
    }
  }

  /**
   * �L�����̕ύX�����X�i�ɒʒm���܂��B
   * @param e �C�x���g���
   */
  protected void fireWriteDataChanged(EventObject e) {
    if (writeDateChangeListeners != null) {
      Vector listeners = writeDateChangeListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++) {
        ( (IkenshoWriteDateChangeListener) listeners.elementAt(i)).
            writeDataChanged(e);
      }
    }
  }
  /**
   * �\���Җ��̕ύX�����X�i�ɒʒm���܂��B
   * @param e �C�x���g���
   */
  protected void fireApplicantNameChanged(EventObject e) {
    if (applicantNameChageListeners != null) {
      Vector listeners = applicantNameChageListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++) {
        ((IkenshoDocumentAffairApplicantNameChageListener) listeners.elementAt(i)).nameChanged(e);
      }
    }
  }

  /**
   * �\���Җ��̕ύX�C�x���g���X�i�����O���܂��B
   * @param l �\���Җ��̕ύX�C�x���g���X�i
   */
  public synchronized void removeApplicantNameChageListener(IkenshoDocumentAffairApplicantNameChageListener l) {
    if (applicantNameChageListeners != null && applicantNameChageListeners.contains(l)) {
      Vector v = (Vector) applicantNameChageListeners.clone();
      v.removeElement(l);
      applicantNameChageListeners = v;
    }
  }
  /**
   * �\���Җ��̕ύX�C�x���g���X�i��ǉ����܂��B
   * @param l �\���Җ��̕ύX�C�x���g���X�i
   */
  public synchronized void addApplicantNameChageListener(IkenshoDocumentAffairApplicantNameChageListener l) {
    Vector v = applicantNameChageListeners == null ? new Vector(2) : (Vector) applicantNameChageListeners.clone();
    if (!v.contains(l)) {
      v.addElement(l);
      applicantNameChageListeners = v;
    }
  }

  // add begin 2006/12/20 kamitsukasa
	/**
	 * @return outOfCareRange ��߂��܂��B
	 */
	public VRLabel getOutOfCareRange() {
		return outOfCareRange;
	}

	/**
	 * @param outOfCareRange
	 *            outOfCareRange ��ݒ�B
	 */
	public void setOutOfCareRange(VRLabel outOfCareRange) {
		this.outOfCareRange = outOfCareRange;
	}
  // add end 2006/12/20 kamitsukasa
  
}

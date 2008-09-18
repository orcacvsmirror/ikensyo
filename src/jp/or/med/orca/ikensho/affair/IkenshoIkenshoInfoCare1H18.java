package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.component.IkenshoCareStatusContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoCare1H18
    extends IkenshoIkenshoInfoCare1 {
  private IkenshoCareStatusContainer moveDown;
  private IkenshoCareStatusContainer tojikomori;
  private IkenshoCareStatusContainer iyokuDown;
  private IkenshoCareStatusContainer lowEnergy;
  private IkenshoCareStatusContainer engeDown;
  private IkenshoCareStatusContainer toutsu;

  /**
   * �ړ��\�͂̒ቺ�R���e�i��Ԃ��܂��B
   * @return �ړ��\�͂̒ቺ�R���e�i
   */
  protected IkenshoCareStatusContainer getMoveDown() {
    if (moveDown == null) {
      moveDown = new IkenshoCareStatusContainer();
    }
    return moveDown;
  }

  /**
   * ��������R���e�i��Ԃ��܂��B
   * @return ��������R���e�i
   */
  protected IkenshoCareStatusContainer getTojikomori() {
    if (tojikomori == null) {
      tojikomori = new IkenshoCareStatusContainer();
    }
    return tojikomori;
  }

  /**
   * �ӗ~�ቺ�R���e�i��Ԃ��܂��B
   * @return �ӗ~�ቺ�R���e�i
   */
  protected IkenshoCareStatusContainer getIyokuDown() {
    if (iyokuDown == null) {
      iyokuDown = new IkenshoCareStatusContainer();
    }
    return iyokuDown;
  }

  /**
   * ��h�{�R���e�i��Ԃ��܂��B
   * @return ��h�{�R���e�i
   */
  protected IkenshoCareStatusContainer getLowEnergy() {
    if (lowEnergy == null) {
      lowEnergy = new IkenshoCareStatusContainer();
    }
    return lowEnergy;
  }

  /**
   * �ېH�E�����@�\�ቺ�R���e�i��Ԃ��܂��B
   * @return �ېH�E�����@�\�ቺ�R���e�i
   */
  protected IkenshoCareStatusContainer getEngeDown() {
    if (engeDown == null) {
      engeDown = new IkenshoCareStatusContainer();
    }
    return engeDown;
  }

  /**
   * ���񓙂ɂ���u�ɃR���e�i��Ԃ��܂��B
   * @return ���񓙂ɂ���u�ɃR���e�i
   */
  protected IkenshoCareStatusContainer getToutsu() {
    if (toutsu == null) {
      toutsu = new IkenshoCareStatusContainer();
    }
    return toutsu;
  }
  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
    super.initDBCopmponent(dbm);

    applyPoolTeikeibun(getMoveDown().getComboBox(), IkenshoCommon.TEIKEI_MOVILITY_DOWN_NAME);
    applyPoolTeikeibun(getTojikomori().getComboBox(), IkenshoCommon.TEIKEI_TOJIKOMORI_NAME);
    applyPoolTeikeibun(getIyokuDown().getComboBox(), IkenshoCommon.TEIKEI_IYOKU_DOWN_NAME);
    applyPoolTeikeibun(getLowEnergy().getComboBox(), IkenshoCommon.TEIKEI_LOW_ENERGY_NAME);
    applyPoolTeikeibun(getEngeDown().getComboBox(), IkenshoCommon.TEIKEI_SESSHOKU_ENGE_DOWN_NAME);
    applyPoolTeikeibun(getToutsu().getComboBox(), IkenshoCommon.TEIKEI_GAN_TOTSU_NAME);
        

        // 2007/10/18 [Masahiko Higuchi] Addition - begin �Ɩ��J�ڃR���{�Ή�
        // ACComboBox��IkenshoOptionComboBox
        getCare1Shikkin().getComboBox().setOptionComboBoxParameters("�A����",
                IkenshoCommon.TEIKEI_SICK_COPE_URINE_NAME, 30);
        getCare1Tentou().getComboBox().setOptionComboBoxParameters("�]�|�E����",
                IkenshoCommon.TEIKEI_SICK_COPE_FACTURE_NAME, 30);
        getCare1Haikai().getComboBox().setOptionComboBoxParameters("�p�j",
                IkenshoCommon.TEIKEI_SICK_COPE_PROWL_NAME, 30);
        getCare1Jyokusou().getComboBox().setOptionComboBoxParameters("���",
                IkenshoCommon.TEIKEI_SICK_COPE_JYOKUSOU_NAME, 30);
        getCare1Haien().getComboBox().setOptionComboBoxParameters("�������x��",
                IkenshoCommon.TEIKEI_SICK_COPE_PNEUMONIA_NAME, 30);
        getCare1Chouheisoku().getComboBox().setOptionComboBoxParameters("����",
                IkenshoCommon.TEIKEI_SICK_COPE_INTESTINES_NAME, 30);
        getCare1Ekikan().getComboBox().setOptionComboBoxParameters("�Պ�����",
                IkenshoCommon.TEIKEI_SICK_COPE_INFECTION_NAME, 30);
        getCare1ShinpaiDown().getComboBox().setOptionComboBoxParameters(
                "�S�x�@�\�̒ቺ", IkenshoCommon.TEIKEI_SICK_COPE_HEART_LUNG_NAME, 30);
        getCare1Pain().getComboBox().setOptionComboBoxParameters("�ɂ�",
                IkenshoCommon.TEIKEI_SICK_COPE_PAIN_NAME, 30);
        getCare1Dassui().getComboBox().setOptionComboBoxParameters("�E��",
                IkenshoCommon.TEIKEI_SICK_COPE_DEHYDRATION_NAME, 30);
        getCare1OtherTaisyo().setOptionComboBoxParameters("���̑��E�ڍ�",
                IkenshoCommon.TEIKEI_SICK_COPE_OTHER_NAME, 30);
        getCare1OtherName().setOptionComboBoxParameters("���̑��E���ږ�",
                IkenshoCommon.TEIKEI_SICK_TYPE_OTHER_NAME, 15);
        getMoveDown().getComboBox().setOptionComboBoxParameters("�ړ��\�͂̒ቺ",
                IkenshoCommon.TEIKEI_MOVILITY_DOWN_NAME, 30);
        getTojikomori().getComboBox().setOptionComboBoxParameters("��������",
                IkenshoCommon.TEIKEI_TOJIKOMORI_NAME, 30);
        getIyokuDown().getComboBox().setOptionComboBoxParameters("�ӗ~�ቺ",
                IkenshoCommon.TEIKEI_IYOKU_DOWN_NAME, 30);
        getLowEnergy().getComboBox().setOptionComboBoxParameters("��h�{",
                IkenshoCommon.TEIKEI_LOW_ENERGY_NAME, 30);
        getEngeDown().getComboBox().setOptionComboBoxParameters("�ېH�E�����@�\�ቺ",
                IkenshoCommon.TEIKEI_SESSHOKU_ENGE_DOWN_NAME, 30);
        getToutsu().getComboBox().setOptionComboBoxParameters("���񓙂ɂ���u��",
                IkenshoCommon.TEIKEI_GAN_TOTSU_NAME, 30);
        //  2007/10/18 [Masahiko Higuchi] Addition - end

  }

  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoIkenshoInfoCare1H18() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    getMoveDown().getComboBox().getEditor().getEditorComponent().addFocusListener(
        getTotalLengthCheckListener());
    getTojikomori().getComboBox().getEditor().getEditorComponent().addFocusListener(
        getTotalLengthCheckListener());
    getIyokuDown().getComboBox().getEditor().getEditorComponent().addFocusListener(
        getTotalLengthCheckListener());
    getLowEnergy().getComboBox().getEditor().getEditorComponent().addFocusListener(
        getTotalLengthCheckListener());
    getEngeDown().getComboBox().getEditor().getEditorComponent().addFocusListener(
        getTotalLengthCheckListener());
    getToutsu().getComboBox().getEditor().getEditorComponent().addFocusListener(
        getTotalLengthCheckListener());
  }

  private void jbInit() throws Exception {
    getTitle().setText("�S�D�����@�\�ƃT�[�r�X�Ɋւ���ӌ��i�����P�j");
    getAbstractionTail().setText("�g�[�^���ł�45���������������܂���B");
    getStateAndTaisyoGroup().setText("���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j");
    // 2006/07/10
    // ��t�ӌ����Ή�
    // Replace - begin [Masahiko Higuchi]
    getMoveDown().setValueBindPath("IDOUTEIKA_TAISHO_HOUSIN");
    getMoveDown().setCheckBindPath("IDOUTEIKA");
    getMoveDown().setCheckText("�ړ��\�͂̒ቺ");
    getTojikomori().setCheckBindPath("TOJIKOMORI");
    getTojikomori().setCheckText("��������");
    getTojikomori().setValueBindPath("TOJIKOMORI_TAISHO_HOUSIN");
    getIyokuDown().setCheckBindPath("IYOKUTEIKA");
    getIyokuDown().setCheckText("�ӗ~�ቺ");
    getIyokuDown().setValueBindPath("IYOKUTEIKA_TAISHO_HOUSIN");
    getLowEnergy().setCheckBindPath("TEIEIYOU");
    getLowEnergy().setCheckText("��h�{");
    getLowEnergy().setValueBindPath("TEIEIYOU_TAISHO_HOUSIN");
    getEngeDown().setCheckBindPath("SESSYOKUENGE");
    getEngeDown().setCheckText("�ېH�E�����@�\�ቺ");
    getEngeDown().setValueBindPath("SESSYOKUENGE_TAISHO_HOUSIN");
    getToutsu().setCheckBindPath("GAN_TOUTU");
    getToutsu().setCheckText("���񓙂ɂ���u��");
    getToutsu().setValueBindPath("GAN_TOUTU_TAISHO_HOUSIN");
    // Replace - end
    getHaien().setVisible(false);
    getChouheisoku().setVisible(false);
    getPain().setVisible(false);

  }

  protected int getAllowedMaxLength() {
    return 45;
  }

  protected void showMaxLengthError() {
    ACMessageBox.show("�Ώ����j�̓g�[�^����45�����܂ł����������܂���B");
  }

  protected int getInputedLength() {
    int total = 0;
    if (getShikkin().isChecked()) {
      total += getShikkin().getInputedLength();
    }
    if (getTentou().isChecked()) {
      total += getTentou().getInputedLength();
    }
    if (getMoveDown().isChecked()) {
      total += getMoveDown().getInputedLength();
    }
    if (getJyokusou().isChecked()) {
      total += getJyokusou().getInputedLength();
    }
    if (getShinpaiDown().isChecked()) {
      total += getShinpaiDown().getInputedLength();
    }
    if (getTojikomori().isChecked()) {
      total += getTojikomori().getInputedLength();
    }
    if (getIyokuDown().isChecked()) {
      total += getIyokuDown().getInputedLength();
    }
    if (getHaikai().isChecked()) {
      total += getHaikai().getInputedLength();
    }
    if (getLowEnergy().isChecked()) {
      total += getLowEnergy().getInputedLength();
    }
    if (getEngeDown().isChecked()) {
      total += getEngeDown().getInputedLength();
    }
    if (getDassui().isChecked()) {
      total += getDassui().getInputedLength();
    }
    if (getEkikan().isChecked()) {
      total += getEkikan().getInputedLength();
    }
    if (getToutsu().isChecked()) {
      total += getToutsu().getInputedLength();
    }
    if (getOther().isSelected()) {
      total += String.valueOf(getOtherTaisyo().getEditor().getItem()).length();
    }
    return total;
  }

  protected void addStateTaisyos() {
    getStateAndTaisyoGroup().add(getShikkin(), VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getTentou(), VRLayout.FLOW_INSETLINE_RETURN);

    getStateAndTaisyoGroup().add(getMoveDown(), VRLayout.FLOW_INSETLINE_RETURN);

    getStateAndTaisyoGroup().add(getJyokusou(), VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getShinpaiDown(),
                                 VRLayout.FLOW_INSETLINE_RETURN);

    getStateAndTaisyoGroup().add(getTojikomori(),
                                 VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getIyokuDown(), VRLayout.FLOW_INSETLINE_RETURN);

    getStateAndTaisyoGroup().add(getHaikai(), VRLayout.FLOW_INSETLINE_RETURN);

    getStateAndTaisyoGroup().add(getLowEnergy(), VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getEngeDown(), VRLayout.FLOW_INSETLINE_RETURN);

    getStateAndTaisyoGroup().add(getDassui(), VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getEkikan(), VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getToutsu(), VRLayout.FLOW_INSETLINE_RETURN);

    getStateAndTaisyoGroup().add(getOthers(), VRLayout.FLOW_INSETLINE_RETURN);

    //�ڍs
    getStateAndTaisyoGroup().add(getHaien(), VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getChouheisoku(),
                                 VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getPain(), VRLayout.FLOW_INSETLINE_RETURN);

  }

}

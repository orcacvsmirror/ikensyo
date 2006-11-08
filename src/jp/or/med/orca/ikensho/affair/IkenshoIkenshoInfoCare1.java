package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.im.InputSubset;

import jp.nichicom.ac.component.ACCheckBox;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoCareStatusContainer;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoUnderlineFormatableLabel;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoCare1
    extends IkenshoTabbableChildAffairContainer {
  private FocusListener totalLengthCheckListener;
  private VRLayout care1GroupLayout = new VRLayout();
  private VRPanel care1Abstractions = new VRPanel();
  private ACLabelContainer care1OtherNames = new ACLabelContainer();
  private VRLabel care1AbstractionHead = new VRLabel();
  private ACGroupBox care1Group = new ACGroupBox();
  private VRLayout care1Layout = new VRLayout();
  private ACIntegerCheckBox care1Other = new ACIntegerCheckBox();
  private IkenshoDocumentTabTitleLabel care1Title = new
      IkenshoDocumentTabTitleLabel();
  private VRLabel care1AbstractionMiddleMessage = new VRLabel();
  private VRLabel care1AbstractionMiddle = new VRLabel();
  private ACLabelContainer care1Others = new ACLabelContainer();
  private IkenshoUnderlineFormatableLabel care1AbstractionTail = new
      IkenshoUnderlineFormatableLabel();
  private ACLabelContainer care1OtherTaisyos = new ACLabelContainer();
  private VRLayout care1OthersLayout = new VRLayout();
  private VRLayout care1AbstractionsLayout = new VRLayout();
  private ACComboBox care1OtherName = new ACComboBox();
  private ACComboBox care1OtherTaisyo = new ACComboBox();
  private IkenshoCareStatusContainer care1Shikkin = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Jyokusou = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Haikai = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Tentou = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Haien = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Chouheisoku = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Ekikan = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1ShinpaiDown = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Pain = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Dassui = new IkenshoCareStatusContainer();
  private ACParentHesesPanelContainer care1OtherNameHeses = new
      ACParentHesesPanelContainer();
  private ACParentHesesPanelContainer care1OtherTaisyoHeses = new
      ACParentHesesPanelContainer();

  /**
   * ���������`�F�b�N���X�i��Ԃ��܂��B
   * @return ���������`�F�b�N���X�i
   */
  protected FocusListener getTotalLengthCheckListener(){
    return totalLengthCheckListener;
  }
  /**
   * �^�C�g���\�����x����Ԃ��܂��B
   * @return �^�C�g���\�����x��
   */
  protected VRLabel getTitle() {
    return care1Title;
  }

  /**
   * �ő啶�����\�����x����Ԃ��܂��B
   * @return �ő啶�����\�����x��
   */
  protected VRLabel getAbstractionTail() {
    return care1AbstractionTail;
  }

  /**
   * ��ԂƑΏ����j�O���[�v��Ԃ��܂��B
   * @return ��ԂƑΏ����j�O���[�v
   */
  protected ACGroupBox getStateAndTaisyoGroup() {
    return care1Group;
  }

  /**
   * �A���փR���e�i��Ԃ��܂��B
   * @return �A���փR���e�i
   */
  protected IkenshoCareStatusContainer getShikkin() {
    return care1Shikkin;
  }

  /**
   * �]�|���܃R���e�i��Ԃ��܂��B
   * @return �]�|���܃R���e�i
   */
  protected IkenshoCareStatusContainer getTentou() {
    return care1Tentou;
  }

  /**
   * �p�j�R���e�i��Ԃ��܂��B
   * @return �p�j�R���e�i
   */
  protected IkenshoCareStatusContainer getHaikai() {
    return care1Haikai;
  }

  /**
   * ��ጃR���e�i��Ԃ��܂��B
   * @return ��ጃR���e�i
   */
  protected IkenshoCareStatusContainer getJyokusou() {
    return care1Jyokusou;
  }

  /**
   * �������x���R���e�i��Ԃ��܂��B
   * @return �������x���R���e�i
   */
  protected IkenshoCareStatusContainer getHaien() {
    return care1Haien;
  }

  /**
   * ���ǃR���e�i��Ԃ��܂��B
   * @return ���ǃR���e�i
   */
  protected IkenshoCareStatusContainer getChouheisoku() {
    return care1Chouheisoku;
  }

  /**
   * �Պ������R���e�i��Ԃ��܂��B
   * @return �Պ������R���e�i
   */
  protected IkenshoCareStatusContainer getEkikan() {
    return care1Ekikan;
  }

  /**
   * �S�x�@�\�̒ቺ�R���e�i��Ԃ��܂��B
   * @return �S�x�@�\�̒ቺ�R���e�i
   */
  protected IkenshoCareStatusContainer getShinpaiDown() {
    return care1ShinpaiDown;
  }

  /**
   * �ɂ݃R���e�i��Ԃ��܂��B
   * @return �ɂ݃R���e�i
   */
  protected IkenshoCareStatusContainer getPain() {
    return care1Pain;
  }

  /**
   * �E���R���e�i��Ԃ��܂��B
   * @return �E���R���e�i
   */
  protected IkenshoCareStatusContainer getDassui() {
    return care1Dassui;
  }


  /**
   * ���̑��R���e�i��Ԃ��܂��B
   * @return ���̑��R���e�i
   */
  protected ACLabelContainer getOthers() {
    return care1Others;
  }

  /**
   * ���̑���Ԃ��܂��B
   * @return ���̑�
   */
  protected ACCheckBox getOther(){
    return care1Other;
  }

  /**
   * ���̑�-�Ώ��@��Ԃ��܂��B
   * @return ���̑�-�Ώ��@
   */
  protected ACComboBox getOtherTaisyo(){
    return care1OtherTaisyo;
  }

  /**
   * override���ď�ԂƑΏ����j�̒ǉ��������`���܂��B
   */
  protected void addStateTaisyos() {
    care1Group.add(care1Shikkin, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Tentou, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Haikai, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Jyokusou, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Haien, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Chouheisoku, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Ekikan, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1ShinpaiDown, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Pain, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Dassui, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Others, VRLayout.FLOW_INSETLINE_RETURN);

  }

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
    applyPoolTeikeibun(care1Shikkin.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_URINE_NAME);
    applyPoolTeikeibun(care1Tentou.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_FACTURE_NAME);
    applyPoolTeikeibun(care1Haikai.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_PROWL_NAME);
    applyPoolTeikeibun(care1Jyokusou.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_JYOKUSOU_NAME);
    applyPoolTeikeibun(care1Haien.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_PNEUMONIA_NAME);
    applyPoolTeikeibun(care1Chouheisoku.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_INTESTINES_NAME);
    applyPoolTeikeibun(care1Ekikan.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_INFECTION_NAME);
    applyPoolTeikeibun(care1ShinpaiDown.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_HEART_LUNG_NAME);
    applyPoolTeikeibun(care1Pain.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_PAIN_NAME);
    applyPoolTeikeibun(care1Dassui.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_DEHYDRATION_NAME);
    applyPoolTeikeibun(care1OtherTaisyo, IkenshoCommon.TEIKEI_SICK_COPE_OTHER_NAME);
    applyPoolTeikeibun(care1OtherName, IkenshoCommon.TEIKEI_SICK_TYPE_OTHER_NAME);


  }

  public IkenshoIkenshoInfoCare1() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    care1Other.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        boolean select;
        if (e.getStateChange() == ItemEvent.SELECTED) {
          select = true;
        }
        else if (e.getStateChange() == ItemEvent.DESELECTED) {
          select = false;
        }
        else {
          return;
        }
        care1OtherName.setEnabled(select);
        care1OtherTaisyo.setEnabled(select);
        care1OtherNameHeses.setEnabled(select);
        care1OtherTaisyoHeses.setEnabled(select);
      }
    });

    totalLengthCheckListener = new FocusAdapter() {
      private boolean lockFlag = false;
      public void focusLost(FocusEvent e) {
        if (lockFlag) {
          return;
        }
        int total = getInputedLength();
        if (total > getAllowedMaxLength()) {
          //�t�H�[�J�X�ړ���ł����̃��X�i���g�p���Ă����ꍇ�A���b�Z�[�W�{�b�N�X�ւ�
          //�t�H�[�J�X�J�ڂōēx�t�H�[�J�X���X�g���������Ă��܂����߁A�t���O�Ŕr����������B
          lockFlag = true;
          showMaxLengthError();
          lockFlag = false;
        }
      }
    };
    care1Shikkin.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1Tentou.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1Haikai.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1Jyokusou.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1Haien.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1Chouheisoku.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1Ekikan.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1ShinpaiDown.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1Pain.getValueCombo().getEditor().getEditorComponent().addFocusListener(
        totalLengthCheckListener);
    care1Dassui.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1OtherTaisyo.getEditor().getEditorComponent().addFocusListener(
        totalLengthCheckListener);

  }

  /**
   * ���e���鍀�ڑ���������Ԃ��܂��B
   * @return ���e���鍀�ڕ�������
   */
  protected int getAllowedMaxLength() {
    return 129;
  }

  /**
   * �Ώ����j�̓��͍ő咷�x�����b�Z�[�W��\�����܂��B
   */
  protected void showMaxLengthError() {
    ACMessageBox.show("�Ώ����j�̓g�[�^����140�����܂ł����������܂���B");
  }

  /**
   * ���͍ς݂̍��ڑ���������Ԃ��܂��B
   * @return ���͍ς݂̍��ڕ�����
   */
  protected int getInputedLength() {
    int total = 0;
    total += getShikkin().getInputedLength();
    total += getTentou().getInputedLength();
    total += getHaikai().getInputedLength();
    total += getJyokusou().getInputedLength();
    total += getHaien().getInputedLength();
    total += getChouheisoku().getInputedLength();
    total += getEkikan().getInputedLength();
    total += getShinpaiDown().getInputedLength();
    total += getPain().getInputedLength();
    total += getDassui().getInputedLength();
    total += String.valueOf(getOtherTaisyo().getEditor().getItem()).length();
    return total;
  }

  private void jbInit() throws Exception {
    care1Layout.setFitHLast(true);
    care1Layout.setFitVLast(true);
    setLayout(care1Layout);
    care1OtherTaisyos.setLayout(new VRLayout());
    care1OtherNames.setLayout(new VRLayout());
    care1GroupLayout.setFitHLast(true);
    care1GroupLayout.setLabelMargin(0);
    care1Abstractions.setLayout(care1AbstractionsLayout);
    care1AbstractionHead.setText("�a��");
    care1Group.setLayout(care1GroupLayout);
    care1Group.setText("���݁A�����̉\���������a�ԂƂ��̑Ώ����j");
    care1Other.setBindPath("BYOUTAITA");
    care1Other.setActionCommand("���̑�");
    care1Other.setText("���̑�");
    care1Title.setText("�S�D���Ɋւ���ӌ�");
    care1AbstractionMiddleMessage.setText("����̓I�ȕa�Ԃ���ёΏ����j");
    care1AbstractionMiddle.setForeground(IkenshoConstants.
                                         COLOR_MESSAGE_TEXT_FOREGROUND);
    care1AbstractionMiddle.setText("�i�e30�����ȓ��j");
    care1Others.setLayout(care1OthersLayout);
    care1AbstractionTail.setText("�g�[�^���ł�140���������������܂���B");
    care1AbstractionTail.setForeground(IkenshoConstants.
                                       COLOR_MESSAGE_WARNING_TEXT_FOREGROUND);
    care1OthersLayout.setFitHLast(true);
    care1OthersLayout.setAutoWrap(false);
    care1OthersLayout.setLabelMargin(0);
    care1AbstractionsLayout.setAutoWrap(false);
    care1OtherName.setEnabled(false);
    care1OtherName.setPreferredSize(new Dimension(210, 19));
    care1OtherName.setIMEMode(InputSubset.KANJI);
    care1OtherName.setMaxLength(15);
    care1OtherName.setBindPath("BYOUTAITA_NM");
    care1OtherTaisyo.setEnabled(false);
    care1OtherTaisyo.setIMEMode(InputSubset.KANJI);
    care1OtherTaisyo.setMaxLength(30);
    care1OtherTaisyo.setBindPath("BYOUTAITA_TAISHO_HOUSIN");
    care1Others.setContentAreaFilled(true);
    care1Others.setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
    care1Shikkin.setCheckBindPath("NYOUSIKKIN");
    care1Shikkin.setCheckText("�A����");
    care1Shikkin.setValueBindPath("NYOUSIKKIN_TAISHO_HOUSIN");
    care1Jyokusou.setCheckBindPath("JOKUSOU_KANOUSEI");
    care1Jyokusou.setCheckText("���");
    care1Jyokusou.setValueBindPath("JOKUSOU_KANOUSEI_TAISHO_HOUSIN");
    care1Haikai.setCheckBindPath("HAIKAI_KANOUSEI");
    care1Haikai.setCheckText("�p�j");
    care1Haikai.setValueBindPath("HAIKAI_KANOUSEI_TAISHO_HOUSIN");
    care1Tentou.setCheckBindPath("TENTOU_KOSSETU");
    care1Tentou.setCheckText("�]�|�E����");
    care1Tentou.setValueBindPath("TENTOU_KOSSETU_TAISHO_HOUSIN");
    care1Haien.setCheckBindPath("ENGESEIHAIEN");
    care1Haien.setCheckText("�������x��");
    care1Haien.setValueBindPath("ENGESEIHAIEN_TAISHO_HOUSIN");
    care1Ekikan.setCheckBindPath("EKIKANKANSEN");
    care1Ekikan.setCheckText("�Պ�����");
    care1Ekikan.setValueBindPath("EKIKANKANSEN_TAISHO_HOUSIN");
    care1Chouheisoku.setCheckBindPath("CHOUHEISOKU");
    care1Chouheisoku.setCheckText("����");
    care1Chouheisoku.setValueBindPath("CHOUHEISOKU_TAISHO_HOUSIN");
    care1ShinpaiDown.setValueBindPath("SINPAIKINOUTEIKA_TAISHO_HOUSIN");
    care1Dassui.setCheckBindPath("DASSUI");
    care1Dassui.setCheckText("�E��");
    care1Dassui.setValueBindPath("DASSUI_TAISHO_HOUSIN");
    care1OtherNameHeses.setOpaque(false);
    care1OtherNameHeses.setEnabled(false);
    care1OtherTaisyoHeses.setOpaque(false);
    care1OtherTaisyoHeses.setEnabled(false);
    care1Abstractions.add(care1AbstractionHead, VRLayout.WEST);
    care1Abstractions.add(care1AbstractionTail, VRLayout.EAST);
    care1Abstractions.add(care1AbstractionMiddle, VRLayout.EAST);
    care1Abstractions.add(care1AbstractionMiddleMessage, VRLayout.EAST);
    care1Others.add(care1Other, VRLayout.WEST);
    care1Others.add(care1OtherNameHeses, VRLayout.WEST);
    care1OtherNameHeses.add(care1OtherNames, VRLayout.CLIENT);
    care1OtherNames.add(care1OtherName, VRLayout.CLIENT);
    care1Others.add(care1OtherTaisyoHeses, VRLayout.CLIENT);
    care1OtherTaisyoHeses.add(care1OtherTaisyos, VRLayout.CLIENT);
    care1OtherTaisyos.add(care1OtherTaisyo, VRLayout.CLIENT);

    care1Group.add(care1Abstractions, VRLayout.FLOW_INSETLINE_RETURN);
    addStateTaisyos();
    this.add(care1Title, VRLayout.NORTH);
    this.add(care1Group, VRLayout.CLIENT);
    care1ShinpaiDown.setCheckText("�S�x�@�\�̒ቺ");
    care1ShinpaiDown.setCheckBindPath("SINPAIKINOUTEIKA");
    care1Pain.setCheckText("�ɂ�");
    care1Pain.setCheckBindPath("ITAMI");
    care1Pain.setValueBindPath("ITAMI_TAISHO_HOUSIN");
  }

  /**
   * �A���ււ̑Ώ����j��Ԃ��܂��B
   * @return �A���ււ̑Ώ����j
   */
  protected IkenshoCareStatusContainer getCare1Shikkin(){
      return care1Shikkin;
  }
  /**
   * �]�|���܂ւ̑Ώ����j��Ԃ��܂��B
   * @return �]�|���܂ւ̑Ώ����j
   */
  protected IkenshoCareStatusContainer getCare1Tentou(){
      return care1Tentou;
  }
  /**
   * �p�j�ւ̑Ώ����j��Ԃ��܂��B
   * @return �p�j�ւ̑Ώ����j
   */
  protected IkenshoCareStatusContainer getCare1Haikai(){
      return care1Haikai;
  }
  /**
   * ��ጂւ̑Ώ����j��Ԃ��܂��B
   * @return ��ጂւ̑Ώ����j
   */
  protected IkenshoCareStatusContainer getCare1Jyokusou(){
      return care1Jyokusou;
  }
  /**
   * �������x���ւ̑Ώ����j��Ԃ��܂��B
   * @return �������x���ւ̑Ώ����j
   */
  protected IkenshoCareStatusContainer getCare1Haien(){
      return care1Haien;
  }
  /**
   * ���ǂւ̑Ώ����j��Ԃ��܂��B
   * @return ���ǂւ̑Ώ����j
   */
  protected IkenshoCareStatusContainer getCare1Chouheisoku(){
      return care1Chouheisoku;
  }
  /**
   * �Պ������ւ̑Ώ����j��Ԃ��܂��B
   * @return �Պ������ւ̑Ώ����j
   */
  protected IkenshoCareStatusContainer getCare1Ekikan(){
      return care1Ekikan;
  }
  /**
   * �S�x�@�\�̒ቺ�ւ̑Ώ����j��Ԃ��܂��B
   * @return �S�x�@�\�̒ቺ�ւ̑Ώ����j
   */
  protected IkenshoCareStatusContainer getCare1ShinpaiDown(){
      return care1ShinpaiDown;
  }
  /**
   * �ɂ݂ւ̑Ώ����j��Ԃ��܂��B
   * @return �ɂ݂ւ̑Ώ����j
   */
  protected IkenshoCareStatusContainer getCare1Pain(){
      return care1Pain;
  }
  /**
   * �E���ւ̑Ώ����j��Ԃ��܂��B
   * @return �E���ւ̑Ώ����j
   */
  protected IkenshoCareStatusContainer getCare1Dassui(){
      return care1Dassui;
  }
  /**
   * ���̑��̑Ώ����j��Ԃ��܂��B
   * @return ���̑��̑Ώ����j
   */
  protected ACComboBox getCare1OtherTaisyo(){
      return care1OtherTaisyo;
  }

  /**
   * ���̑��̖��̂�Ԃ��܂��B
   * @return ���̑��̖���
   */
  protected ACComboBox getCare1OtherName(){
      return care1OtherName;
  }
}

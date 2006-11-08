package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.im.InputSubset;
import java.text.ParseException;
import java.util.Arrays;

import javax.swing.ComboBoxModel;
import javax.swing.SwingConstants;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACTextArea;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.IkenshoSpecialSickButton;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoDocumentAffairSick extends IkenshoTabbableChildAffairContainer {

  private VRLabel sickTitle = new VRLabel();
  private ACLabelContainer sickNames1 = new ACLabelContainer();
  private ACLabelContainer sickNames2 = new ACLabelContainer();
  private ACLabelContainer sickNames3 = new ACLabelContainer();
  private VRPanel sickStableAndOutlook;
  private ACGroupBox sickProgressGroup = new ACGroupBox();
  private ACGroupBox sickNameGroup = new ACGroupBox();
  private VRLayout sickNameGroupLayout = new VRLayout();
  private VRLabel sickMedicineDosageHead5 = new VRLabel();
  private ACComboBox sickMedicineDosageUnit5 = new ACComboBox();
//  private JScrollPane sickProgressSroll = new JScrollPane();
  private VRLabel sickMedicineUsageHead5 = new VRLabel();
  private VRLayout sickNames1Layout = new VRLayout();
  private VRLayout sickNames2Layout = new VRLayout();
  private VRLayout sickNames3Layout = new VRLayout();
  private ACComboBox sickMedicineDosageUnit4 = new ACComboBox();
  private ACComboBox sickMedicineDosageUnit3 = new ACComboBox();
  private ACComboBox sickMedicineDosageUnit6 = new ACComboBox();
  private ACLabelContainer sickMedicines1 = new ACLabelContainer();
  private IkenshoEraDateTextField sickDate3 = new IkenshoEraDateTextField();
  private VRLabel sickMedicineUsageHead6 = new VRLabel();
  private ACComboBox sickMedicineName1 = new ACComboBox();
  private ACComboBox sickMedicineDosageUnit2 = new ACComboBox();
  private ACTextField sickMedicineDosage6 = new ACTextField();
  private VRLabel sickMedicineDosageHead3 = new VRLabel();
  private ACComboBox sickMedicineName6 = new ACComboBox();
  private ACLabelContainer sickMedicines5 = new ACLabelContainer();
  private VRLabel sickMedicineDosageHead1 = new VRLabel();
  private ACLabelContainer sickProgresss;
  private ACGroupBox sickStableGroup;
  private ACComboBox sickMedicineName4 = new ACComboBox();
  private ACLabelContainer sickMedicines2 = new ACLabelContainer();
  private ACComboBox sickMedicineDosageUnit1 = new ACComboBox();
  private VRLabel sickMedicineUsageHead4 = new VRLabel();
  private ACTextField sickMedicineDosage2 = new ACTextField();
  private ACComboBox sickName1 = new ACComboBox();
  private VRLabel sickMedicineUsageHead2 = new VRLabel();
  private ACTextArea sickProgress = new ACTextArea();
  private VRLayout sickLayout = new VRLayout();
  private ACComboBox sickMedicineName5 = new ACComboBox();
  private VRLabel sickMedicineUsageHead3 = new VRLabel();
  private ACParentHesesPanelContainer sickDates2;
  private ACGroupBox sickOutlookGroup;
  private ACComboBox sickMedicineUsage2 = new ACComboBox();
  private ACTextField sickMedicineDosage4 = new ACTextField();
  private IkenshoSpecialSickButton sickSpecial3;
  private ACComboBox sickMedicineUsage1 = new ACComboBox();
  private ACComboBox sickMedicineName3 = new ACComboBox();
  private ACParentHesesPanelContainer sickDates3;
  private ACComboBox sickMedicineUsage3 = new ACComboBox();
  private IkenshoEraDateTextField sickDate2 = new IkenshoEraDateTextField();
  private VRLabel sickMedicineDosageHead6 = new VRLabel();
  private ACLabelContainer sickMedicines6 = new ACLabelContainer();
  private IkenshoSpecialSickButton sickSpecial1;
  private ACTextField sickMedicineDosage5 = new ACTextField();
  private ACLabelContainer sickMedicines3 = new ACLabelContainer();
  private ACComboBox sickMedicineUsage5 = new ACComboBox();
  private ACParentHesesPanelContainer sickDates1;
  private ACTextField sickMedicineDosage1 = new ACTextField();
  private ACComboBox sickName3 = new ACComboBox();
  private ACClearableRadioButtonGroup sickStable;
  private ACComboBox sickMedicineName2 = new ACComboBox();
  private VRLabel sickMedicineDosageHead4 = new VRLabel();
  private ACTextField sickMedicineDosage3 = new ACTextField();
  private ACLabelContainer sickMedicines4 = new ACLabelContainer();
  private ACComboBox sickName2 = new ACComboBox();
  private ACComboBox sickMedicineUsage6 = new ACComboBox();
  private IkenshoSpecialSickButton sickSpecial2;
  private IkenshoEraDateTextField sickDate1 = new IkenshoEraDateTextField();
  private VRLabel sickMedicineUsageHead1 = new VRLabel();
  private ACComboBox sickMedicineUsage4 = new ACComboBox();
  private VRLabel sickMedicineDosageHead2 = new VRLabel();
  private ACClearableRadioButtonGroup sickOutlook = new ACClearableRadioButtonGroup();
  private VRLayout sickStableLayout = new VRLayout();
  private FlowLayout sickOutlookLayout = new FlowLayout();
  private ACLabelContainer sickStables = new ACLabelContainer();
  private ACLabelContainer sickOutlooks = new ACLabelContainer();
  private VRLayout sickProgressGroupLayout = new VRLayout();


  /**
   * ���a���Ǔ�1��Ԃ��܂��B
   * @return ���a���Ǔ�1
   */
  protected IkenshoEraDateTextField getSickDate1() {
    return sickDate1;
  }
  /**
   * ���a���Ǔ�2��Ԃ��܂��B
   * @return ���a���Ǔ�2
   */
  protected IkenshoEraDateTextField getSickDate2() {
    return sickDate2;
  }
  /**
   * ���a���Ǔ�3��Ԃ��܂��B
   * @return ���a���Ǔ�3
   */
  protected IkenshoEraDateTextField getSickDate3() {
    return sickDate3;
  }
  /**
   * ���a��1�R���e�i��Ԃ��܂��B
   * @return ���a��1�R���e�i
   */
  protected ACLabelContainer getSickNames1() {
    return sickNames1;
  }
  /**
   * ���a��2�R���e�i��Ԃ��܂��B
   * @return ���a��2�R���e�i
   */
  protected ACLabelContainer getSickNames2() {
    return sickNames2;
  }
  /**
   * ���a��3�R���e�i��Ԃ��܂��B
   * @return ���a��3�R���e�i
   */
  protected ACLabelContainer getSickNames3() {
    return sickNames3;
  }
  /**
   * ���a���O���[�v��Ԃ��܂��B
   * @return ���a���O���[�v
   */
  protected ACGroupBox getSickNameGroup() {
    return sickNameGroup;
  }
  /**
   * �\��̌o�߃O���[�v��Ԃ��܂��B
   * @return �\��̌o�߃O���[�v
   */
  protected ACGroupBox getProgressGroup() {
    return sickProgressGroup;
  }


  /**
   * �^�C�g���\�����x����Ԃ��܂��B
   * @return �^�C�g���\�����x��
   */
  protected VRLabel getTitle() {
    return sickTitle;
  }

  /**
   * ���萫�Ɨ\��̌��ʂ��p�l����Ԃ��܂��B
   * @return ���萫�Ɨ\��̌��ʂ��p�l��
   */
  protected VRPanel getStableAndOutlook() {
    if(sickStableAndOutlook==null){
      sickStableAndOutlook = new VRPanel();
    }
    return sickStableAndOutlook;
  }

  /**
   * override���ďǏ�Ƃ��Ă̈��萫����ї\��̌������̒ǉ��������`���܂��B
   */
  protected void addSickStableAndOutlook(){
    getStableAndOutlook().add(getSickStableGroup(), VRLayout.CLIENT);
    getStableAndOutlook().add(getOutlookGroup(), VRLayout.CLIENT);
  }


  /**
   * �\��̌��ʂ��O���[�v��Ԃ��܂��B
   * @return �\��̌��ʂ��O���[�v
   */
  protected ACGroupBox getOutlookGroup() {
    if(sickOutlookGroup==null){
      sickOutlookGroup = new ACGroupBox();
    }
    return sickOutlookGroup;
  }

  /**
   * �Ǐ�Ƃ��Ă̈��萫�O���[�v��Ԃ��܂��B
   * @return �Ǐ�Ƃ��Ă̈��萫�O���[�v
   */
  protected ACGroupBox getSickStableGroup() {
    if(sickStableGroup==null){
      sickStableGroup = new ACGroupBox();
    }
    return sickStableGroup;
  }

  /**
   * �\��̌��ʂ���Ԃ��܂��B
   * @return �\��̌��ʂ�
   */
  protected ACClearableRadioButtonGroup getOutlook() {
    return sickOutlook;
  }

  /**
   * override���ďǏ�Ƃ��Ă̈��萫�̒ǉ��������`���܂��B
   */
  protected void addSickStableGroup(){
    getSickStableGroup().add(sickStables, null);
  }

  /**
   * ���萫���W�I�O���[�v��Ԃ��܂��B
   * @return �Ǐ�Ƃ��Ă̈��萫���W�I�O���[�v
   */
  protected ACClearableRadioButtonGroup getSickStable() {
    if(sickStable==null){
      sickStable = new ACClearableRadioButtonGroup();
    }
    return sickStable;
  }

  /**
   * ���a�̌o�߂�Ԃ��܂��B
   * @return ���a�̌o��
   */
  protected ACLabelContainer getSickProgresss(){
    if(sickProgresss==null){
      sickProgresss = new ACLabelContainer();
    }
    return sickProgresss;
  }

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {

    applyPoolTeikeibun(sickName1, IkenshoCommon.TEIKEI_SICK_NAME);
    applyPoolTeikeibun(sickName2, IkenshoCommon.TEIKEI_SICK_NAME);
    applyPoolTeikeibun(sickName3, IkenshoCommon.TEIKEI_SICK_NAME);

    VRHashMapArrayToConstKeyArrayAdapter adapt;
    getSickSpecial1().setUnpressedModel(sickName1.getModel());
    getSickSpecial2().setUnpressedModel(sickName2.getModel());
    getSickSpecial3().setUnpressedModel(sickName3.getModel());


    adapt = IkenshoCommon.getMDisease(dbm, IkenshoCommon.DISEASE_SEPCIAL_SICK_NAME);
    getSickSpecial1().setPressedModel(IkenshoCommon.createComboAdapter(adapt));
    getSickSpecial2().setPressedModel(IkenshoCommon.createComboAdapter(adapt));
    getSickSpecial3().setPressedModel(IkenshoCommon.createComboAdapter(adapt));


    applyPoolTeikeibun(sickMedicineName1, IkenshoCommon.TEIKEI_MEDICINE_NAME);
    applyPoolTeikeibun(sickMedicineName2, IkenshoCommon.TEIKEI_MEDICINE_NAME);
    applyPoolTeikeibun(sickMedicineName3, IkenshoCommon.TEIKEI_MEDICINE_NAME);
    applyPoolTeikeibun(sickMedicineName4, IkenshoCommon.TEIKEI_MEDICINE_NAME);
    applyPoolTeikeibun(sickMedicineName5, IkenshoCommon.TEIKEI_MEDICINE_NAME);
    applyPoolTeikeibun(sickMedicineName6, IkenshoCommon.TEIKEI_MEDICINE_NAME);
    applyPoolTeikeibun(sickMedicineDosageUnit1, IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT);
    applyPoolTeikeibun(sickMedicineDosageUnit2, IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT);
    applyPoolTeikeibun(sickMedicineDosageUnit3, IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT);
    applyPoolTeikeibun(sickMedicineDosageUnit4, IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT);
    applyPoolTeikeibun(sickMedicineDosageUnit5, IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT);
    applyPoolTeikeibun(sickMedicineDosageUnit6, IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT);
    applyPoolTeikeibun(sickMedicineUsage1, IkenshoCommon.TEIKEI_MEDICINE_USAGE);
    applyPoolTeikeibun(sickMedicineUsage2, IkenshoCommon.TEIKEI_MEDICINE_USAGE);
    applyPoolTeikeibun(sickMedicineUsage3, IkenshoCommon.TEIKEI_MEDICINE_USAGE);
    applyPoolTeikeibun(sickMedicineUsage4, IkenshoCommon.TEIKEI_MEDICINE_USAGE);
    applyPoolTeikeibun(sickMedicineUsage5, IkenshoCommon.TEIKEI_MEDICINE_USAGE);
    applyPoolTeikeibun(sickMedicineUsage6, IkenshoCommon.TEIKEI_MEDICINE_USAGE);

  }
  
  public boolean noControlError() {

    //�G���[�`�F�b�N
    IkenshoEraDateTextField[] sicks = new IkenshoEraDateTextField[]{sickDate1, sickDate2, sickDate3};
    int end = sicks.length;
    for(int i=0; i<end; i++){
      switch (sicks[i].getInputStatus()) {
        case IkenshoEraDateTextField.STATE_EMPTY:
        case IkenshoEraDateTextField.STATE_VALID:
          break;
        case IkenshoEraDateTextField.STATE_FUTURE:
          ACMessageBox.showExclamation("�����̓��t�ł��B");
          sicks[i].requestChildFocus();
          return false;
        default:
          ACMessageBox.show("���t�Ɍ�肪����܂��B");
          sicks[i].requestChildFocus();
          return false;
      }
    }
    return true;
  }

  protected void bindSourceInnerBindComponent() throws Exception {
    super.bindSourceInnerBindComponent();

    checkSpecialSick(sickName1, getSickSpecial1());
    checkSpecialSick(sickName2, getSickSpecial2());
    checkSpecialSick(sickName3, getSickSpecial3());

  }
  /**
   * ���莾�a���܂ނ����`�F�b�N���܂��B
   * @param combo ���a���R���{
   * @param button ���莾���{�^��
   * @throws ParseException ������O
   */
  protected void checkSpecialSick(ACComboBox combo, IkenshoSpecialSickButton button) throws
      ParseException {
    Object obj = VRBindPathParser.get(combo.getBindPath(), getMasterSource());
    if(IkenshoCommon.isNullText(obj)){
      if(button.isPushed()){
        button.swapPushed();
      }
      return;
    }

    String text = String.valueOf(obj);
    ComboBoxModel model = button.getPressedModel();
    int end = model.getSize();
    for (int i = 0; i < end; i++) {
      Object elem = model.getElementAt(i);
      if ( (elem != null) && (text.equals(String.valueOf(elem)))) {
        if(!button.isPushed()){
          button.setSelected(true);
          button.getCombo().getEditor().setItem("");
          button.refreshCombo();
          button.setPushed(true);
          combo.setSelectedIndex(i);
        }
        return;
      }
    }
    if(button.isPushed()){
      button.setSelected(false);
      button.refreshCombo();
      button.setPushed(false);
      combo.setSelectedItem(text);
    }
  }

  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoDocumentAffairSick() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    getSickStable().setModel(new VRListModelAdapter(new
                                               VRArrayList(Arrays.asList(new
        String[] {"����", "�s����", "�s��"}))));

    sickOutlook.setModel(new VRListModelAdapter(new
                                                VRArrayList(Arrays.asList(new
        String[] {"���P", "�s��", "����"}))));

    setLayout(sickLayout);

    sickNames1.setLayout(sickNames1Layout);
    sickNames2.setLayout(sickNames2Layout);
    sickNames3.setLayout(sickNames3Layout);
    sickNames1Layout.setHgap(0);
    sickNames1Layout.setVgap(0);
    sickNames1Layout.setAutoWrap(false);
    sickNames2Layout.setHgap(0);
    sickNames2Layout.setVgap(0);
    sickNames2Layout.setAutoWrap(false);
    sickNames3Layout.setHgap(0);
    sickNames3Layout.setVgap(0);
    sickNames3Layout.setAutoWrap(false);
    sickNameGroup.setLayout(sickNameGroupLayout);

    sickMedicineDosageUnit5.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageUnit5.setMaxLength(4);
    sickMedicineDosageUnit5.setBindPath("UNIT5");
    sickMedicineDosageUnit5.setPreferredSize(new Dimension(100, 19));
    sickMedicineDosageHead5.setText("�@�@�@");
    sickMedicineUsageHead5.setText("�@�@�@");
    sickNames1.setText("�P�D");
    sickMedicineDosageUnit4.setPreferredSize(new Dimension(100, 19));
    sickMedicineDosageUnit4.setBindPath("UNIT4");
    sickMedicineDosageUnit4.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageUnit4.setMaxLength(4);
    sickMedicineDosageUnit3.setPreferredSize(new Dimension(100, 19));
    sickMedicineDosageUnit3.setBindPath("UNIT3");
    sickMedicineDosageUnit3.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageUnit3.setMaxLength(4);
    sickMedicineDosageUnit6.setPreferredSize(new Dimension(100, 19));
    sickMedicineDosageUnit6.setBindPath("UNIT6");
    sickMedicineDosageUnit6.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageUnit6.setMaxLength(4);
    sickMedicines1.setText("��ܖ��P�D");
    sickDate3.setBindPath("HASHOU_DT3");
    sickDate3.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
    sickDate3.setAgeVisible(false);
    sickDate3.setAllowedUnknown(true);
    sickMedicineUsageHead6.setText("�@�@�@");
    sickMedicineName1.setPreferredSize(new Dimension(220, 19));
    sickMedicineName1.setMaxLength(12);
    sickMedicineName1.setBindPath("MEDICINE1");
    sickMedicineName1.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageUnit2.setPreferredSize(new Dimension(100, 19));
    sickMedicineDosageUnit2.setBindPath("UNIT2");
    sickMedicineDosageUnit2.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageUnit2.setMaxLength(4);
    getStableAndOutlook().setLayout(new VRLayout());
    sickMedicineDosage6.setMaxLength(4);
    sickMedicineDosage6.setColumns(4);
    sickMedicineDosage6.setHorizontalAlignment(SwingConstants.RIGHT);
    sickMedicineDosage6.setBindPath("DOSAGE6");
    sickMedicineDosage6.setIMEMode(InputSubset.LATIN);
    sickMedicineDosageHead3.setText("�@�@�@");
    sickMedicineName6.setPreferredSize(new Dimension(220, 19));
    sickMedicineName6.setMaxLength(12);
    sickMedicineName6.setBindPath("MEDICINE6");
    sickMedicineName6.setIMEMode(InputSubset.KANJI);
    sickNames2.setText("�Q�D");
    sickMedicines5.setText("�@�@�@�T�D");
    sickMedicines5.setToolTipText("");
    sickMedicineDosageHead1.setText("�@�p��");
    getSickProgresss().setText("���a�̌o��" + IkenshoConstants.LINE_SEPARATOR + "�i250����" +
                          IkenshoConstants.LINE_SEPARATOR + "�܂���" +
                          IkenshoConstants.LINE_SEPARATOR + "5�s�ȓ��j");
    getSickStableGroup().setText("�Ǐ�Ƃ��Ă̈��萫");
    getSickStableGroup().setLayout(sickStableLayout);
    sickMedicineName4.setPreferredSize(new Dimension(220, 19));
    sickMedicineName4.setMaxLength(12);
    sickMedicineName4.setBindPath("MEDICINE4");
    sickMedicineName4.setIMEMode(InputSubset.KANJI);
    sickMedicines2.setToolTipText("");
    sickMedicines2.setText("�@�@�@�Q�D");
    sickNames3.setText("�R�D");
    sickMedicineDosageUnit1.setPreferredSize(new Dimension(100, 19));
    sickMedicineDosageUnit1.setBindPath("UNIT1");
    sickMedicineDosageUnit1.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageUnit1.setMaxLength(4);
    sickMedicineUsageHead4.setText("�@�@�@");
    sickMedicineDosage2.setMaxLength(4);
    sickMedicineDosage2.setColumns(4);
    sickMedicineDosage2.setHorizontalAlignment(SwingConstants.RIGHT);
    sickMedicineDosage2.setBindPath("DOSAGE2");
    sickMedicineDosage2.setIMEMode(InputSubset.LATIN);
    sickName1.setMaxLength(30);
    sickName1.setIMEMode(InputSubset.KANJI);
    sickName1.setBindPath("SINDAN_NM1");
    sickMedicineUsageHead2.setText("�@�@�@");
//    sickProgress.setColumns(87);
    sickProgress.setColumns(100);
//    sickProgress.setColumns(86);
    sickProgress.setLineWrap(true);
    sickProgress.setRows(5);
//    sickProgress.setRows(6);
    sickProgress.setBindPath("MT_STS");
    sickProgress.setMaxLength(250);
//    sickProgress.setMaxColumns(100);
//    sickProgress.setMaxColumns(86);
//    sickProgress.setUseMaxRows(true);
    sickProgress.setMaxRows(sickProgress.getRows());
    sickProgress.setIMEMode(InputSubset.KANJI);
    sickLayout.setFitHLast(true);
    sickLayout.setFitVLast(true);
    sickMedicineName5.setPreferredSize(new Dimension(220, 19));
    sickMedicineName5.setMaxLength(12);
    sickMedicineName5.setBindPath("MEDICINE5");
    sickMedicineName5.setIMEMode(InputSubset.KANJI);
    sickMedicineUsageHead3.setText("�@�@�@");
    getSickDates2().setBeginText("���ǔN�����i");
    getSickDates2().setEndText("�j");
    sickDate2.getDayUnit().setText("����");
    getOutlookGroup().setText("���̕K�v�̒��x�Ɋւ���\��̌��ʂ�");
    getOutlookGroup().setLayout(sickOutlookLayout);
    sickMedicineUsage2.setPreferredSize(new Dimension(180, 19));
    sickMedicineUsage2.setMaxLength(10);
    sickMedicineUsage2.setBindPath("USAGE2");
    sickMedicineUsage2.setIMEMode(InputSubset.KANJI);
    sickMedicineDosage4.setMaxLength(4);
    sickMedicineDosage4.setColumns(4);
    sickMedicineDosage4.setHorizontalAlignment(SwingConstants.RIGHT);
    sickMedicineDosage4.setBindPath("DOSAGE4");
    sickMedicineDosage4.setIMEMode(InputSubset.LATIN);
    sickTitle.setText("�P�D���a�Ɋւ���ӌ�");
    sickTitle.setForeground(IkenshoConstants.COLOR_PANEL_TITLE_FOREGROUND);
    sickTitle.setOpaque(true);
    sickTitle.setBackground(IkenshoConstants.COLOR_PANEL_TITLE_BACKGROUND);
    sickMedicineUsage1.setPreferredSize(new Dimension(180, 19));
    sickMedicineUsage1.setMaxLength(10);
    sickMedicineUsage1.setBindPath("USAGE1");
    sickMedicineUsage1.setIMEMode(InputSubset.KANJI);
    sickMedicineName3.setPreferredSize(new Dimension(220, 19));
    sickMedicineName3.setMaxLength(12);
    sickMedicineName3.setBindPath("MEDICINE3");
    sickMedicineName3.setIMEMode(InputSubset.KANJI);
    getSickDates3().setEndText("�j");
    sickDate3.getDayUnit().setText("����");
    getSickDates3().setBeginText("���ǔN�����i");
    sickMedicineUsage3.setPreferredSize(new Dimension(180, 19));
    sickMedicineUsage3.setMaxLength(10);
    sickMedicineUsage3.setBindPath("USAGE3");
    sickMedicineUsage3.setIMEMode(InputSubset.KANJI);
    sickDate2.setBindPath("HASHOU_DT2");
    sickDate2.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
    sickDate2.setAgeVisible(false);
    sickDate2.setAllowedUnknown(true);
    sickMedicineDosageHead6.setText("�@�@�@");
    sickMedicines6.setText("�@�@�@�U�D");
    sickMedicines6.setToolTipText("");
    sickMedicineDosage5.setMaxLength(4);
    sickMedicineDosage5.setColumns(4);
    sickMedicineDosage5.setHorizontalAlignment(SwingConstants.RIGHT);
    sickMedicineDosage5.setBindPath("DOSAGE5");
    sickMedicineDosage5.setIMEMode(InputSubset.LATIN);
    sickMedicines3.setText("�@�@�@�R�D");
    sickMedicines3.setToolTipText("");
    sickMedicineUsage5.setPreferredSize(new Dimension(180, 19));
    sickMedicineUsage5.setMaxLength(10);
    sickMedicineUsage5.setBindPath("USAGE5");
    sickMedicineUsage5.setIMEMode(InputSubset.KANJI);
    getSickDates1().setBeginText("���ǔN�����i");
    getSickDates1().setEndText("�j");
    sickDate1.getDayUnit().setText("����");
    sickMedicineDosage1.setColumns(4);
    sickMedicineDosage1.setHorizontalAlignment(SwingConstants.RIGHT);
    sickMedicineDosage1.setBindPath("DOSAGE1");
    sickMedicineDosage1.setMaxLength(4);
    sickMedicineDosage1.setIMEMode(InputSubset.LATIN);
    sickName3.setIMEMode(InputSubset.KANJI);
    sickName3.setMaxLength(30);
    sickName3.setBindPath("SINDAN_NM3");
    getSickStable().setBindPath("SHJ_ANT");
    sickMedicineName2.setPreferredSize(new Dimension(220, 19));
    sickMedicineName2.setMaxLength(12);
    sickMedicineName2.setBindPath("MEDICINE2");
    sickMedicineName2.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageHead4.setText("�@�@�@");
    sickMedicineDosage3.setMaxLength(4);
    sickMedicineDosage3.setColumns(4);
    sickMedicineDosage3.setHorizontalAlignment(SwingConstants.RIGHT);
    sickMedicineDosage3.setBindPath("DOSAGE3");
    sickMedicineDosage3.setIMEMode(InputSubset.LATIN);
    sickMedicines4.setText("�@�@�@�S�D");
    sickMedicines4.setToolTipText("");
    sickName2.setIMEMode(InputSubset.KANJI);
    sickName2.setMaxLength(30);
    sickName2.setBindPath("SINDAN_NM2");
    sickProgressGroupLayout.setVgap(0);
    sickProgressGroup.setLayout(sickProgressGroupLayout);
    sickProgressGroup.setText("��Q�̒��ڂ̌����ƂȂ��Ă��鏝�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e");
    sickMedicineUsage6.setPreferredSize(new Dimension(180, 19));
    sickMedicineUsage6.setMaxLength(10);
    sickMedicineUsage6.setBindPath("USAGE6");
    sickMedicineUsage6.setIMEMode(InputSubset.KANJI);
    sickNameGroup.setText("�f�f���i���莾�a�܂��͏�Q�̒��ڂ̌����ƂȂ��Ă��鏝�a���ɂ��Ă͂P�D�ɋL���j�y�є��ǔN����");
//    sickNameGroup
    sickDate1.setBindPath("HASHOU_DT1");
    sickDate1.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
    sickDate1.setAgeVisible(false);
    sickDate1.setAllowedUnknown(true);
    sickMedicineUsageHead1.setText("�@�p�@");
    sickMedicineUsage4.setPreferredSize(new Dimension(180, 19));
    sickMedicineUsage4.setMaxLength(10);
    sickMedicineUsage4.setBindPath("USAGE4");
    sickMedicineUsage4.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageHead2.setText("�@�@�@");
    sickOutlook.setBindPath("YKG_YOGO");
    getSickSpecial1().setCombo(sickName1);
    getSickSpecial2().setCombo(sickName2);
    getSickSpecial3().setCombo(sickName3);

    sickStableLayout.setAutoWrap(false);
    sickStableLayout.setAlignment(VRLayout.LEFT);
    sickStableLayout.setVgap(0);
    sickOutlookLayout.setAlignment(FlowLayout.LEFT);
    sickNameGroupLayout.setFitHLast(true);
    sickNameGroupLayout.setHgap(0);
    sickNameGroupLayout.setLabelMargin(0);
//    sickProgressSroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//    sickProgressSroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    sickOutlooks.add(sickOutlook, null);
    getOutlookGroup().add(sickOutlooks, null);
    addSickStableAndOutlook();
    addSickStableGroup();
    sickStables.add(getSickStable(), null);
    sickMedicines6.add(sickMedicineName6, null);
    sickMedicines6.add(sickMedicineDosageHead6, null);
    sickMedicines6.add(sickMedicineDosage6, null);
    sickMedicines6.add(sickMedicineDosageUnit6, null);
    sickMedicines6.add(sickMedicineUsageHead6, null);
    sickMedicines6.add(sickMedicineUsage6, null);
    sickProgressGroup.add(sickMedicines6, VRLayout.SOUTH);
    sickProgressGroup.add(sickMedicines5, VRLayout.SOUTH);
    sickMedicines5.add(sickMedicineName5, null);
    sickMedicines5.add(sickMedicineDosageHead5, null);
    sickMedicines5.add(sickMedicineDosage5, null);
    sickMedicines5.add(sickMedicineDosageUnit5, null);
    sickMedicines5.add(sickMedicineUsageHead5, null);
    sickMedicines5.add(sickMedicineUsage5, null);
    sickProgressGroup.add(sickMedicines4, VRLayout.SOUTH);
    sickMedicines4.add(sickMedicineName4, null);
    sickMedicines4.add(sickMedicineDosageHead4, null);
    sickMedicines4.add(sickMedicineDosage4, null);
    sickMedicines4.add(sickMedicineDosageUnit4, null);
    sickMedicines4.add(sickMedicineUsageHead4, null);
    sickMedicines4.add(sickMedicineUsage4, null);
    sickProgressGroup.add(sickMedicines3, VRLayout.SOUTH);
    sickMedicines3.add(sickMedicineName3, null);
    sickMedicines3.add(sickMedicineDosageHead3, null);
    sickMedicines3.add(sickMedicineDosage3, null);
    sickMedicines3.add(sickMedicineDosageUnit3, null);
    sickMedicines3.add(sickMedicineUsageHead3, null);
    sickMedicines3.add(sickMedicineUsage3, null);
    sickProgressGroup.add(sickMedicines2, VRLayout.SOUTH);
    sickMedicines2.add(sickMedicineName2, null);
    sickMedicines2.add(sickMedicineDosageHead2, null);
    sickMedicines2.add(sickMedicineDosage2, null);
    sickMedicines2.add(sickMedicineDosageUnit2, null);
    sickMedicines2.add(sickMedicineUsageHead2, null);
    sickMedicines2.add(sickMedicineUsage2, null);
    sickProgressGroup.add(sickMedicines1, VRLayout.SOUTH);
    sickMedicines1.add(sickMedicineName1, null);
    sickMedicines1.add(sickMedicineDosageHead1, null);
    sickMedicines1.add(sickMedicineDosage1, null);
    sickMedicines1.add(sickMedicineDosageUnit1, null);
    sickMedicines1.add(sickMedicineUsageHead1, null);
    sickMedicines1.add(sickMedicineUsage1, null);
    sickProgressGroup.add(getSickProgresss(), VRLayout.NORTH);
    getSickProgresss().setLayout(new VRLayout());
    getSickProgresss().add(sickProgress, VRLayout.LEFT);
    // 2006/07/24 - ��t�ӌ���
    // Replace - begin [Masahiko Higuchi]
        // sickNameGroup.add(sickNames1, VRLayout.FLOW_INSETLINE_RETURN);
        // sickNameGroup.add(sickNames2, VRLayout.FLOW_INSETLINE_RETURN);
        // sickNameGroup.add(sickNames3, VRLayout.FLOW_INSETLINE_RETURN);
    addSickGroupComponent();
    // Replace - end
    sickNames1.add(sickName1, VRLayout.CLIENT);
    sickNames1.add(getSickDates1(), VRLayout.EAST);
    sickNames1.add(getSickSpecial1(), VRLayout.EAST);
    getSickDates1().add(sickDate1, null);
    sickNames2.add(sickName2, VRLayout.CLIENT);
    sickNames2.add(getSickDates2(), VRLayout.EAST);
    sickNames2.add(getSickSpecial2(), VRLayout.EAST);
    getSickDates2().add(sickDate2, null);
    sickNames3.add(sickName3, VRLayout.CLIENT);
    sickNames3.add(getSickDates3(), VRLayout.EAST);
    sickNames3.add(getSickSpecial3(), VRLayout.EAST);
    getSickDates3().add(sickDate3, null);
    // 2006/07/24 - ��t�ӌ���
    // Replace - begin [Masahiko Higuchi
    addThisComponent();
        // this.add(sickTitle, VRLayout.NORTH);
        // this.add(sickNameGroup, VRLayout.NORTH);
        // this.add(sickStableAndOutlook, VRLayout.NORTH);
        // this.add(sickProgressGroup, VRLayout.NORTH);
    // Replace - end
  }
  
  /**
   * ���@���O���[�v�ɒǉ����鍀�ڂ��`���܂��B
   */
  protected void addSickGroupComponent(){
      // 2006/07/24 - ��t�ӌ���
      // Addition - begin [Masahiko Higuchi]
      sickNameGroup.add(sickNames1, VRLayout.FLOW_INSETLINE_RETURN);
      sickNameGroup.add(sickNames2, VRLayout.FLOW_INSETLINE_RETURN);
      sickNameGroup.add(sickNames3, VRLayout.FLOW_INSETLINE_RETURN);
      // Addition - end
  }
  
  /**
   * �^�u�ɒǉ����鏇�ԁE���ڂ��`���܂��B
   */
  protected void addThisComponent(){
      // 2006/07/24 - ��t�ӌ���
      // Addition - begin [Masahiko Higuchi]
      this.add(sickTitle, VRLayout.NORTH);
      this.add(sickNameGroup, VRLayout.NORTH);
      this.add(sickStableAndOutlook, VRLayout.NORTH);
      this.add(sickProgressGroup, VRLayout.NORTH);
      // Addition - end
  }
      
  /**
   * ���莾�a�{�^���R��Ԃ��܂��B
   * @return
   */
    protected IkenshoSpecialSickButton getSickSpecial3() {
        if(sickSpecial3 == null){
            sickSpecial3 = new IkenshoSpecialSickButton();
        }
        return sickSpecial3;
    }
    
    /**
     * ���莾�a�{�^���P��Ԃ��܂��B
     * @return
     */
    protected IkenshoSpecialSickButton getSickSpecial1() {
        if(sickSpecial1 == null){
            sickSpecial1 = new IkenshoSpecialSickButton();
        }
        return sickSpecial1;
    }
    
    /**
     * ���莾�a�{�^���Q��Ԃ��܂��B
     * @return
     */
    protected IkenshoSpecialSickButton getSickSpecial2() {
        if(sickSpecial2 == null){
            sickSpecial2 = new IkenshoSpecialSickButton();
        }
        return sickSpecial2;
    }
    /**
     * ���a��1��Ԃ��܂��B
     * @return ���a��1
     */
    protected ACComboBox getSickName1(){
        return sickName1;
    }
    /**
     * ���a��2��Ԃ��܂��B
     * @return ���a��2
     */
    protected ACComboBox getSickName2(){
        return sickName2;
    }
    /**
     * ���a��3��Ԃ��܂��B
     * @return ���a��3
     */
    protected ACComboBox getSickName3(){
        return sickName3;
    }

    /**
     * �w��ԍ��̓��򖼂�Ԃ��܂��B
     * @param index �ԍ�
     * @return ����
     */
    protected ACComboBox getSickMedicineName(int index){
        return new ACComboBox[] { sickMedicineName1, sickMedicineName2,
                  sickMedicineName3, sickMedicineName4, sickMedicineName5,
                  sickMedicineName6 }[index];
    }
    /**
     * �w��ԍ��̓���P�ʂ�Ԃ��܂��B
     * @param index �ԍ�
     * @return ����P��
     */
    protected ACComboBox getSickMedicineDosageUnit(int index){
        return new ACComboBox[] { sickMedicineDosageUnit1, sickMedicineDosageUnit2,
                  sickMedicineDosageUnit3, sickMedicineDosageUnit4, sickMedicineDosageUnit5,
                  sickMedicineDosageUnit6 }[index];
    }
    /**
     * �w��ԍ��̓���p�@��Ԃ��܂��B
     * @param index �ԍ�
     * @return ����p�@
     */
    protected ACComboBox getSickMedicineUsage(int index){
        return new ACComboBox[] { sickMedicineUsage1, sickMedicineUsage2,
                  sickMedicineUsage3, sickMedicineUsage4, sickMedicineUsage5,
                  sickMedicineUsage6 }[index];
    }
    /**
     * ���ǔN����Heses�p�l��1��Ԃ��܂��B
     * @return
     */
    protected ACParentHesesPanelContainer getSickDates1() {
        // 2006/07/31 - ��t�ӌ���
        // override�p��Getter�ɕύX
        /// Addition - begin [Masahiko Higuchi]
        if(sickDates1 == null){
            sickDates1 = new ACParentHesesPanelContainer();
        }
        return sickDates1;
    }
    
    /**
     * ���ǔN����Heses�p�l��2��Ԃ��܂��B
     * @return
     */
    protected ACParentHesesPanelContainer getSickDates2() {
        // 2006/07/31 - ��t�ӌ���
        // override�p��Getter�ɕύX
        /// Addition - begin [Masahiko Higuchi]
        if(sickDates2 == null){
            sickDates2 = new ACParentHesesPanelContainer();
        }
        return sickDates2;
    }
    
    /**
     * ���ǔN����Heses�p�l��3��Ԃ��܂��B
     * @return
     */
    protected ACParentHesesPanelContainer getSickDates3() {
        // 2006/07/31 - ��t�ӌ���
        // override�p��Getter�ɕύX
        /// Addition - begin [Masahiko Higuchi]
        if(sickDates3 == null){
            sickDates3 = new ACParentHesesPanelContainer();
        }
        return sickDates3;
    }
}

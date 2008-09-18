package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.im.InputSubset;
import java.util.Arrays;

import jp.nichicom.ac.component.ACBitCheckBoxGroup;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoSeikatsuService1 extends IkenshoTabbableChildAffairContainer {
  private VRLayout containtsLayout = new VRLayout();
  private IkenshoDocumentTabTitleLabel title = new IkenshoDocumentTabTitleLabel();
  private ACClearableRadioButtonGroup moveOutdoorWalk = new ACClearableRadioButtonGroup();
  private ACGroupBox outdoorWalkGroup = new ACGroupBox();
  private ACLabelContainer moveOutdoorWalks = new ACLabelContainer();
  private VRLayout outdoorWalkGroupLayout = new VRLayout();
  private VRLayout eatLifeGroupLayout = new VRLayout();
  private ACLabelContainer moveWalkSupports = new ACLabelContainer();
  private ACClearableRadioButtonGroup moveWheelChair = new ACClearableRadioButtonGroup();
  private ACLabelContainer moveWheelChairs = new ACLabelContainer();
  private ACGroupBox eatLifeGroup = new ACGroupBox();
  private ACLabelContainer nourishuments = new ACLabelContainer();
  private ACLabelContainer meals = new ACLabelContainer();
  private ACClearableRadioButtonGroup meal = new ACClearableRadioButtonGroup();
  private ACClearableRadioButtonGroup nourishument = new ACClearableRadioButtonGroup();
//  private NCClearableRadioButtonGroup appetite = new NCClearableRadioButtonGroup();
//  private NCLabelContainer appetites = new NCLabelContainer();
//  private NCClearableRadioButtonGroup mealIntake = new NCClearableRadioButtonGroup();
//  private NCLabelContainer mealIntakes = new NCLabelContainer();
//  private NCParentHesesPanelContainer mealIntakeMessages = new NCParentHesesPanelContainer();
//  private NCComboBox mealIntakeMessage = new NCComboBox();
//  private NCComboBox appetiteMessage = new NCComboBox();
// 2007/10/18 [Masahiko Higuchi] Replace - begin �Ɩ��J�ڃR���{�Ή� ACComboBox��IkenshoOptionComboBox
  private IkenshoOptionComboBox eatingMessage = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
//  private NCParentHesesPanelContainer appetiteMessages = new NCParentHesesPanelContainer();
  private ACLabelContainer eatingMessages = new ACLabelContainer();
//  private NCParentHesesPanelContainer nourishumentMessages = new NCParentHesesPanelContainer();
  private ACBitCheckBoxGroup moveWalkSupport = new ACBitCheckBoxGroup();

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
    super.initDBCopmponent(dbm);

//    IkenshoCommon.setTeikeibun(dbm, mealIntakeMessage,
//                               IkenshoCommon.TEIKEI_MEAL_INTAKE_NAME);
//    IkenshoCommon.setTeikeibun(dbm, appetiteMessage,
//                               IkenshoCommon.TEIKEI_APPETITE_NAME);
    IkenshoCommon.setTeikeibun(dbm, eatingMessage,
                               IkenshoCommon.TEIKEI_EATING_RYUIJIKOU_NAME);
    

        // 2007/10/18 [Masahiko Higuchi] Addition - begin �Ɩ��J�ڃR���{�Ή�
        // ACComboBox��IkenshoOptionComboBox
        getEatingMessage().setOptionComboBoxParameters("�h�{�E�H������̗��ӓ_",
                IkenshoCommon.TEIKEI_EATING_RYUIJIKOU_NAME, 30);
        // 2007/10/18 [Masahiko Higuchi] Addition - end
  }

  public boolean noControlWarning() throws Exception {
    if(!super.noControlWarning()){
      return false;
    }

//    if(mealIntake.getSelectedIndex()==2){
//      if(isNullText(mealIntakeMessage.getEditor().getItem())) {
//        if (NCMessageBox.show("�u���ӎ����i����̐H���ێ�ʁj�v�Ŗ��L��������܂��B\n���̂܂܎��s���܂����H",
//                                   NCMessageBox.BUTTON_OKCANCEL,
//                                   NCMessageBox.ICON_QUESTION) !=
//            NCMessageBox.RESULT_OK) {
//         mealIntake.requestChildFocus();
//         return false;
//       }
//     }
//    }
//    if(appetite.getSelectedIndex()==2){
//      if(isNullText(appetiteMessage.getEditor().getItem())) {
//        if (NCMessageBox.show("�u���ӎ����i�H�~�j�v�Ŗ��L��������܂��B\n���̂܂܎��s���܂����H",
//                                   NCMessageBox.BUTTON_OKCANCEL,
//                                   NCMessageBox.ICON_QUESTION) !=
//            NCMessageBox.RESULT_OK) {
//         appetite.requestChildFocus();
//         return false;
//       }
//     }
//    }
//    if(nourishument.getSelectedIndex()==2){
//      if(isNullText(nourishumentMessage.getEditor().getItem())) {
//        if (NCMessageBox.show("�u���ӎ����i���݂̉h�{��ԁj�v�Ŗ��L��������܂��B\n���̂܂܎��s���܂����H",
//                                   NCMessageBox.BUTTON_OKCANCEL,
//                                   NCMessageBox.ICON_QUESTION) !=
//            NCMessageBox.RESULT_OK) {
//         nourishument.requestChildFocus();
//         return false;
//       }
//     }
//    }

    return true;
  }

  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoIkenshoSeikatsuService1() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }


//    mealIntake.addListSelectionListener(new
//                                        NCFollowDisableSelectionListener(new
//        JComponent[] {mealIntakeMessages, mealIntakeMessage}
//        , 1));
//
//    appetite.addListSelectionListener(new
//                                        NCFollowDisableSelectionListener(new
//        JComponent[] {appetiteMessages, appetiteMessage}
//        , 1));

//    nourishument.addListSelectionListener(new
//                                        NCFollowDisableSelectionListener(new
//        JComponent[] {nourishumentMessages, nourishumentMessage}
//        , 1));

  }
  private void jbInit() throws Exception {
    containtsLayout.setFitVLast(true);
    containtsLayout.setFitHLast(true);
    setLayout(containtsLayout);
    title.setText("�S�D�����@�\�ƃT�[�r�X�Ɋւ���ӌ�");


outdoorWalkGroupLayout.setFitHLast(true);
    outdoorWalkGroupLayout.setHgrid(200);
    outdoorWalkGroupLayout.setAutoWrap(false);
    eatLifeGroupLayout.setFitHLast(true);
    eatLifeGroupLayout.setHgrid(200);
    eatLifeGroupLayout.setAutoWrap(false);

    moveWalkSupport.setModel(new VRListModelAdapter(new
                                                VRArrayList(Arrays.asList(new
        String[] {"�p���Ă��Ȃ�", "���O�Ŏg�p", "�����Ŏg�p"}))));


    moveOutdoorWalk.setBindPath("OUTDOOR");
    moveOutdoorWalk.setModel(new VRListModelAdapter(new
                                                VRArrayList(Arrays.asList(new
        String[] {"����", "�������΂��Ă���", "���Ă��Ȃ�"}))));
    outdoorWalkGroup.setLayout(outdoorWalkGroupLayout);
    moveOutdoorWalks.setText("���O���s");
    moveWalkSupports.setText("���s�⏕��E����̎g�p");
    moveWheelChair.setBindPath("WHEELCHAIR");
    moveWheelChair.setModel(new VRListModelAdapter(new
                                                VRArrayList(Arrays.asList(new
        String[] {"�p���Ă��Ȃ�", "��Ɏ����ő��삵�Ă���", "��ɑ��l�����삵�Ă���"}))));
    moveWheelChairs.setText("�Ԃ����̎g�p");
    outdoorWalkGroup.setText("�ړ�");
    eatLifeGroup.setText("�h�{�E�H����");
    eatLifeGroup.setLayout(eatLifeGroupLayout);
    nourishuments.setText("���݂̉h�{���");
    meals.setText("�H���s��");
    meal.setBindPath("MEAL");
    meal.setModel(new VRListModelAdapter(new
                                                VRArrayList(Arrays.asList(new
        String[] {"�����Ȃ������Ƃ������ŐH�ׂ���", "�S�ʉ"}))));
    nourishument.setBindPath("NOURISHMENT");
    nourishument.setModel(new VRListModelAdapter(new
                                                VRArrayList(Arrays.asList(new
        String[] {"�ǍD", "�s��"}))));
//    appetite.setBindPath("APPETITE");
//    appetite.setModel(new VRListModelAdapter(new
//                                                VRArrayList(Arrays.asList(new
//        String[] {"�ǍD", "�s��"}))));
//    appetites.setText("�H�~");
//    mealIntake.setBindPath("MEAL_INTAKE");
//    mealIntake.setModel(new VRListModelAdapter(new
//                                                VRArrayList(Arrays.asList(new
//        String[] {"�ǍD", "�s��"}))));
//    mealIntake.setAutoApplySource(false);
//    mealIntakes.setText("����̐H���ێ��");
//    mealIntakeMessages.setBeginText("�����P�Ɍ��������ӓ_�i");
//    mealIntakeMessages.setEnabled(false);
//    mealIntakeMessage.setEnabled(false);
//    mealIntakeMessage.setPreferredSize(new Dimension(250, 19));
//    mealIntakeMessage.setIMEMode(InputSubset.KANJI);
//    mealIntakeMessage.setBindPath("MEAL_INTAKE_RYUIJIKOU");
//    mealIntakeMessage.setMaxLength(25);
//    appetiteMessage.setMaxLength(25);
//    appetiteMessage.setBindPath("APPETITE_RYUIJIKOU");
//    appetiteMessage.setEnabled(false);
//    appetiteMessage.setPreferredSize(new Dimension(250, 19));
//    appetiteMessage.setIMEMode(InputSubset.KANJI);
//    appetiteMessages.setEnabled(false);
//    appetiteMessages.setBeginText("�����P�Ɍ��������ӓ_�i");
    eatingMessage.setMaxLength(30);
    /** @todo �h�{�E�H������̗��ӓ_�o�C���h�p�X */
    eatingMessage.setBindPath("EATING_RYUIJIKOU");
    eatingMessage.setPreferredSize(new Dimension(250, 19));
    eatingMessage.setIMEMode(InputSubset.KANJI);
    eatingMessages.setText("���h�{�E�H������̗��ӓ_");
//    nourishumentMessages.setBeginText("���h�{�E�H������̗��ӓ_�i");
    moveWalkSupport.setBindPath("ASSISTANCE_TOOL");
    eatingMessages.add(eatingMessage, null);
    eatLifeGroup.add(meals, VRLayout.FLOW_INSETLINE_RETURN);
    meals.add(meal, null);
    eatLifeGroup.add(nourishuments, VRLayout.FLOW_INSETLINE_RETURN);

    eatLifeGroup.add(eatingMessages, VRLayout.FLOW_RETURN);
//    eatLifeGroup.add(mealIntakes, VRLayout.FLOW_INSETLINE_RETURN);
//    mealIntakes.add(mealIntake, null);
//    mealIntakeMessages.add(mealIntakeMessage, null);
//    eatLifeGroup.add(appetites, VRLayout.FLOW_INSETLINE_RETURN);
//    appetites.add(appetite, null);
    this.add(title, VRLayout.NORTH);
    this.add(outdoorWalkGroup, VRLayout.NORTH);
    outdoorWalkGroup.add(moveOutdoorWalks, VRLayout.FLOW_INSETLINE_RETURN);
    moveOutdoorWalks.add(moveOutdoorWalk, null);
    outdoorWalkGroup.add(moveWheelChairs, VRLayout.FLOW_INSETLINE_RETURN);
    moveWheelChairs.add(moveWheelChair, null);
    outdoorWalkGroup.add(moveWalkSupports, VRLayout.FLOW_INSETLINE_RETURN);
    moveWalkSupports.add(moveWalkSupport, null);
    this.add(eatLifeGroup, VRLayout.NORTH);
    nourishuments.add(nourishument, null);
//    appetiteMessages.add(appetiteMessage, null);
//    mealIntake.add(mealIntakeMessages, 2);
//    appetite.add(appetiteMessages, 2);


//    addInnerBindComponent(mealIntakeMessage);
//    addInnerBindComponent(appetiteMessage);
    addInnerBindComponent(eatingMessage);
  }

//2007/10/18 [Masahiko Higuchi] Addition - begin �Ɩ��J�ڃR���{�Ή� ACComboBox��IkenshoOptionComboBox
/**
 * �h�{�E�H������̗��ӓ_ ��Ԃ��܂��B
 * @return eatingMessage
 * @author Masahiko Higuchi
 * @since 3.0.5
 */
protected IkenshoOptionComboBox getEatingMessage() {
    if(eatingMessage == null){
        eatingMessage = new IkenshoOptionComboBox();
    }
    return eatingMessage;
}
//2007/10/18 [Masahiko Higuchi] Addition - end

}

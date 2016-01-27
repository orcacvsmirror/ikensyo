package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.im.InputSubset;

import javax.swing.JComponent;

import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.event.ACFollowDisableSelectionListener;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;


/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoSickH18 extends IkenshoIkenshoInfoSick {
  private ACLabelContainer notStableStateContainer;
//2007/10/18 [Masahiko Higuchi] Replace - begin �Ɩ��J�ڃR���{�ɍ����ւ�
  private IkenshoOptionComboBox noteStableState;
//2007/10/18 [Masahiko Higuchi] Replace - end

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        applyPoolTeikeibun(noteStableState,
                IkenshoCommon.TEIKEI_INSECURE_CONDITION_NAME);

        noteStableState.setOptionComboBoxParameters("�u�s����v�Ƃ����ꍇ�̋�̓I��",
                IkenshoCommon.TEIKEI_INSECURE_CONDITION_NAME, 30);

        getSickName1().setOptionComboBoxParameters("���a��",
                IkenshoCommon.TEIKEI_SICK_NAME, 30);
        getSickName1().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName2(), getSickName3() });
        getSickName2().setOptionComboBoxParameters("���a��",
                IkenshoCommon.TEIKEI_SICK_NAME, 30);
        getSickName2().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName1(), getSickName3() });
        getSickName3().setOptionComboBoxParameters("���a��",
                IkenshoCommon.TEIKEI_SICK_NAME, 30);
        getSickName3().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName1(), getSickName2() });

        // �ݒ肷��l�������ւ���
        getSickSpecial1().setUnpressedModel(getSickName1().getOriginalModel());
        getSickSpecial2().setUnpressedModel(getSickName2().getOriginalModel());
        getSickSpecial3().setUnpressedModel(getSickName3().getOriginalModel());

        // [ID:0000509][Masahiko Higuchi] 2009/06 del begin ��ʒ����ɔ�������
//        getSickMedicineName(0).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(1).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(2).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(3).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(4).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(5).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        //2009/01/06 [Tozo Tanaka] Add - begin
//        getSickMedicineName(6).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(7).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        //2009/01/06 [Tozo Tanaka] Add - end
//
//        // �R���{�A���ݒ�
//        getSickMedicineName(0).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineName(1),
//                        getSickMedicineName(2), getSickMedicineName(3),
//                        getSickMedicineName(4), getSickMedicineName(5),
//                        getSickMedicineName(6), getSickMedicineName(7) });
//        getSickMedicineName(1).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineName(0),
//                        getSickMedicineName(2), getSickMedicineName(3),
//                        getSickMedicineName(4), getSickMedicineName(5),
//                        getSickMedicineName(6), getSickMedicineName(7) });
//        getSickMedicineName(2).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineName(0),
//                        getSickMedicineName(1), getSickMedicineName(3),
//                        getSickMedicineName(4), getSickMedicineName(5),
//                        getSickMedicineName(6), getSickMedicineName(7) });
//        getSickMedicineName(3).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineName(0),
//                        getSickMedicineName(1), getSickMedicineName(2),
//                        getSickMedicineName(4), getSickMedicineName(5),
//                        getSickMedicineName(6), getSickMedicineName(7) });
//        getSickMedicineName(4).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineName(0),
//                        getSickMedicineName(1), getSickMedicineName(2),
//                        getSickMedicineName(3), getSickMedicineName(5),
//                        getSickMedicineName(6), getSickMedicineName(7) });
//        getSickMedicineName(5).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineName(0),
//                        getSickMedicineName(1), getSickMedicineName(2),
//                        getSickMedicineName(3), getSickMedicineName(4),
//                        getSickMedicineName(6), getSickMedicineName(7) });
//        //2009/01/06 [Tozo Tanaka] Add - begin
//        getSickMedicineName(6).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineName(0),
//                        getSickMedicineName(1), getSickMedicineName(2),
//                        getSickMedicineName(3), getSickMedicineName(4),
//                        getSickMedicineName(5), getSickMedicineName(7) });
//        getSickMedicineName(7).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineName(0),
//                        getSickMedicineName(1), getSickMedicineName(2),
//                        getSickMedicineName(3), getSickMedicineName(4),
//                        getSickMedicineName(5), getSickMedicineName(6) });
//        //2009/01/06 [Tozo Tanaka] Add - end
//
//        getSickMedicineDosageUnit(0).setOptionComboBoxParameters("�p�ʒP��",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        getSickMedicineDosageUnit(1).setOptionComboBoxParameters("�p�ʒP��",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        getSickMedicineDosageUnit(2).setOptionComboBoxParameters("�p�ʒP��",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        getSickMedicineDosageUnit(3).setOptionComboBoxParameters("�p�ʒP��",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        getSickMedicineDosageUnit(4).setOptionComboBoxParameters("�p�ʒP��",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        getSickMedicineDosageUnit(5).setOptionComboBoxParameters("�p�ʒP��",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        //2009/01/06 [Tozo Tanaka] Add - begin
//        getSickMedicineDosageUnit(6).setOptionComboBoxParameters("�p�ʒP��",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        getSickMedicineDosageUnit(7).setOptionComboBoxParameters("�p�ʒP��",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        //2009/01/06 [Tozo Tanaka] Add - end
//
//        // �A���R���{�̓o�^
//        getSickMedicineDosageUnit(0).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(1),
//                        getSickMedicineDosageUnit(2),
//                        getSickMedicineDosageUnit(3),
//                        getSickMedicineDosageUnit(4),
//                        getSickMedicineDosageUnit(5),
//                        getSickMedicineDosageUnit(6),
//                        getSickMedicineDosageUnit(7) });
//        getSickMedicineDosageUnit(1).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
//                        getSickMedicineDosageUnit(2),
//                        getSickMedicineDosageUnit(3),
//                        getSickMedicineDosageUnit(4),
//                        getSickMedicineDosageUnit(5),
//                        getSickMedicineDosageUnit(6),
//                        getSickMedicineDosageUnit(7) });
//        getSickMedicineDosageUnit(2).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
//                        getSickMedicineDosageUnit(1),
//                        getSickMedicineDosageUnit(3),
//                        getSickMedicineDosageUnit(4),
//                        getSickMedicineDosageUnit(5),
//                        getSickMedicineDosageUnit(6),
//                        getSickMedicineDosageUnit(7) });
//        getSickMedicineDosageUnit(3).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
//                        getSickMedicineDosageUnit(1),
//                        getSickMedicineDosageUnit(2),
//                        getSickMedicineDosageUnit(4),
//                        getSickMedicineDosageUnit(5),
//                        getSickMedicineDosageUnit(6),
//                        getSickMedicineDosageUnit(7) });
//        getSickMedicineDosageUnit(4).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
//                        getSickMedicineDosageUnit(1),
//                        getSickMedicineDosageUnit(2),
//                        getSickMedicineDosageUnit(3),
//                        getSickMedicineDosageUnit(5),
//                        getSickMedicineDosageUnit(6),
//                        getSickMedicineDosageUnit(7) });
//        getSickMedicineDosageUnit(5).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
//                        getSickMedicineDosageUnit(1),
//                        getSickMedicineDosageUnit(2),
//                        getSickMedicineDosageUnit(3),
//                        getSickMedicineDosageUnit(4),
//                        getSickMedicineDosageUnit(6),
//                        getSickMedicineDosageUnit(7) });
//        //2009/01/06 [Tozo Tanaka] Add - begin
//        getSickMedicineDosageUnit(6).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
//                        getSickMedicineDosageUnit(1),
//                        getSickMedicineDosageUnit(2),
//                        getSickMedicineDosageUnit(3),
//                        getSickMedicineDosageUnit(4),
//                        getSickMedicineDosageUnit(5),
//                        getSickMedicineDosageUnit(7) });
//        getSickMedicineDosageUnit(7).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
//                        getSickMedicineDosageUnit(1),
//                        getSickMedicineDosageUnit(2),
//                        getSickMedicineDosageUnit(3),
//                        getSickMedicineDosageUnit(4),
//                        getSickMedicineDosageUnit(5),
//                        getSickMedicineDosageUnit(6) });
//        //2009/01/06 [Tozo Tanaka] Add - end
//
//        getSickMedicineUsage(0).setOptionComboBoxParameters("�p�@",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        getSickMedicineUsage(1).setOptionComboBoxParameters("�p�@",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        getSickMedicineUsage(2).setOptionComboBoxParameters("�p�@",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        getSickMedicineUsage(3).setOptionComboBoxParameters("�p�@",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        getSickMedicineUsage(4).setOptionComboBoxParameters("�p�@",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        getSickMedicineUsage(5).setOptionComboBoxParameters("�p�@",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        //2009/01/06 [Tozo Tanaka] Add - begin
//        getSickMedicineUsage(6).setOptionComboBoxParameters("�p�@",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        getSickMedicineUsage(7).setOptionComboBoxParameters("�p�@",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        //2009/01/06 [Tozo Tanaka] Add - end
//
//        // �A���R���{�̓o�^
//        getSickMedicineUsage(0).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineUsage(1),
//                        getSickMedicineUsage(2), getSickMedicineUsage(3),
//                        getSickMedicineUsage(4), getSickMedicineUsage(5),
//                        getSickMedicineUsage(6), getSickMedicineUsage(7) });
//        getSickMedicineUsage(1).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
//                        getSickMedicineUsage(2), getSickMedicineUsage(3),
//                        getSickMedicineUsage(4), getSickMedicineUsage(5),
//                        getSickMedicineUsage(6), getSickMedicineUsage(7) });
//        getSickMedicineUsage(2).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
//                        getSickMedicineUsage(1), getSickMedicineUsage(3),
//                        getSickMedicineUsage(4), getSickMedicineUsage(5),
//                        getSickMedicineUsage(6), getSickMedicineUsage(7) });
//        getSickMedicineUsage(3).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
//                        getSickMedicineUsage(1), getSickMedicineUsage(2),
//                        getSickMedicineUsage(4), getSickMedicineUsage(5),
//                        getSickMedicineUsage(6), getSickMedicineUsage(7) });
//        getSickMedicineUsage(4).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
//                        getSickMedicineUsage(1), getSickMedicineUsage(2),
//                        getSickMedicineUsage(3), getSickMedicineUsage(5),
//                        getSickMedicineUsage(6), getSickMedicineUsage(7) });
//        getSickMedicineUsage(5).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
//                        getSickMedicineUsage(1), getSickMedicineUsage(2),
//                        getSickMedicineUsage(3), getSickMedicineUsage(4),
//                        getSickMedicineUsage(6), getSickMedicineUsage(7) });
//        //2009/01/06 [Tozo Tanaka] Add - begin
//        getSickMedicineUsage(6).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
//                        getSickMedicineUsage(1), getSickMedicineUsage(2),
//                        getSickMedicineUsage(3), getSickMedicineUsage(4),
//                        getSickMedicineUsage(5), getSickMedicineUsage(7) });
//        getSickMedicineUsage(7).addInterlockComboComponents(
//                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
//                        getSickMedicineUsage(1), getSickMedicineUsage(2),
//                        getSickMedicineUsage(3), getSickMedicineUsage(4),
//                        getSickMedicineUsage(5), getSickMedicineUsage(6) });
        //2009/01/06 [Tozo Tanaka] Add - end
        // [ID:0000509][Masahiko Higuchi] 2009/06 del end

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
   * @version 2.0
   *    Masahiko Higuchi
   */
  protected IkenshoOptionComboBox getNotStableState() {
    if(noteStableState==null){
      noteStableState = new IkenshoOptionComboBox();
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
    // [ID:0000509][Masahiko Higuchi] 2009/06 del begin ��ʒ����ɔ�������
//    getSickProgresss().setText("���a�܂���" + IkenshoConstants.LINE_SEPARATOR +
//                               "���莾�a�̌o��" + IkenshoConstants.LINE_SEPARATOR +
//                               "�i250����" + IkenshoConstants.LINE_SEPARATOR +
//                               "�܂���5�s�ȓ��j");
//
//    getNotStableState().setPreferredSize(new Dimension(400,20));
    getNotStableState().setMaxLength(30);
    getNotStableState().setColumns(30);
    getNotStableState().setIMEMode(InputSubset.KANJI);
//    getSickNameGroup().setText("�f�f���i���莾�a�܂��͐����@�\�ቺ�̒��ڂ̌����ƂȂ��Ă��鏝�a���ɂ��Ă͂P�D�ɋL���j�y�є��ǔN����");
//    getProgressGroup().setText("�����@�\�ቺ�̒��ڂ̌����ƂȂ��Ă��鏝�a�܂��͓��莾�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e");
    // [ID:0000509][Masahiko Higuchi] 2009/06 del end
//    getSickDate1().setEraRange(2);
//    getSickDate2().setEraRange(2);
//    getSickDate3().setEraRange(2);
//    getSickDate1().setAllowedUnknown(false);
//    getSickDate2().setAllowedUnknown(false);
//    getSickDate3().setAllowedUnknown(false);
  }

  //2009/01/08 [Tozo Tanaka] Add - begin
  protected void setSickProgressContaierText(int maxLength){
      getSickProgresss().setText(
                "���a�܂���" + IkenshoConstants.LINE_SEPARATOR + "���莾�a�̌o��"
                        + IkenshoConstants.LINE_SEPARATOR + "�i" + maxLength
                        + "����" + IkenshoConstants.LINE_SEPARATOR + "�܂���5�s�ȓ��j");
  }
  //2009/01/08 [Tozo Tanaka] Add - end
  
  // [ID:0000509][Masahiko Higuchi] 2009/06 add begin ��ʒ����ɔ�������
  protected void addThisComponent(){
      this.add(getTitle(), VRLayout.NORTH);
      this.add(getSickNameGroup(), VRLayout.NORTH);
      this.add(getStableAndOutlook(), VRLayout.NORTH);
  }
  // [ID:0000509][Masahiko Higuchi] 2009/06 add end

}

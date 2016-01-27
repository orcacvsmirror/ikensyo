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
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボに差し替え
  private IkenshoOptionComboBox noteStableState;
//2007/10/18 [Masahiko Higuchi] Replace - end

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        applyPoolTeikeibun(noteStableState,
                IkenshoCommon.TEIKEI_INSECURE_CONDITION_NAME);

        noteStableState.setOptionComboBoxParameters("「不安定」とした場合の具体的状況",
                IkenshoCommon.TEIKEI_INSECURE_CONDITION_NAME, 30);

        getSickName1().setOptionComboBoxParameters("疾病名",
                IkenshoCommon.TEIKEI_SICK_NAME, 30);
        getSickName1().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName2(), getSickName3() });
        getSickName2().setOptionComboBoxParameters("疾病名",
                IkenshoCommon.TEIKEI_SICK_NAME, 30);
        getSickName2().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName1(), getSickName3() });
        getSickName3().setOptionComboBoxParameters("疾病名",
                IkenshoCommon.TEIKEI_SICK_NAME, 30);
        getSickName3().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName1(), getSickName2() });

        // 設定する値を差し替える
        getSickSpecial1().setUnpressedModel(getSickName1().getOriginalModel());
        getSickSpecial2().setUnpressedModel(getSickName2().getOriginalModel());
        getSickSpecial3().setUnpressedModel(getSickName3().getOriginalModel());

        // [ID:0000509][Masahiko Higuchi] 2009/06 del begin 画面調整に伴い調整
//        getSickMedicineName(0).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(1).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(2).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(3).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(4).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(5).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        //2009/01/06 [Tozo Tanaka] Add - begin
//        getSickMedicineName(6).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(7).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        //2009/01/06 [Tozo Tanaka] Add - end
//
//        // コンボ連動設定
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
//        getSickMedicineDosageUnit(0).setOptionComboBoxParameters("用量単位",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        getSickMedicineDosageUnit(1).setOptionComboBoxParameters("用量単位",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        getSickMedicineDosageUnit(2).setOptionComboBoxParameters("用量単位",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        getSickMedicineDosageUnit(3).setOptionComboBoxParameters("用量単位",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        getSickMedicineDosageUnit(4).setOptionComboBoxParameters("用量単位",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        getSickMedicineDosageUnit(5).setOptionComboBoxParameters("用量単位",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        //2009/01/06 [Tozo Tanaka] Add - begin
//        getSickMedicineDosageUnit(6).setOptionComboBoxParameters("用量単位",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        getSickMedicineDosageUnit(7).setOptionComboBoxParameters("用量単位",
//                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
//        //2009/01/06 [Tozo Tanaka] Add - end
//
//        // 連動コンボの登録
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
//        getSickMedicineUsage(0).setOptionComboBoxParameters("用法",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        getSickMedicineUsage(1).setOptionComboBoxParameters("用法",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        getSickMedicineUsage(2).setOptionComboBoxParameters("用法",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        getSickMedicineUsage(3).setOptionComboBoxParameters("用法",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        getSickMedicineUsage(4).setOptionComboBoxParameters("用法",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        getSickMedicineUsage(5).setOptionComboBoxParameters("用法",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        //2009/01/06 [Tozo Tanaka] Add - begin
//        getSickMedicineUsage(6).setOptionComboBoxParameters("用法",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        getSickMedicineUsage(7).setOptionComboBoxParameters("用法",
//                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
//        //2009/01/06 [Tozo Tanaka] Add - end
//
//        // 連動コンボの登録
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
   * コンストラクタです。
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
   * 不安定時の具体的な状況コンテナを返します。
   * @return 不安定時の具体的な状況コンテナ
   */
  protected ACLabelContainer getNotStableStateContainer() {
    if(notStableStateContainer==null){
      notStableStateContainer = new ACLabelContainer();
    }
    return notStableStateContainer;
  }

  /**
   * 不安定時の具体的な状況を返します。
   * @return 不安定時の具体的な状況
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
    getNotStableStateContainer().setText("具体的な状況");
    //不安定における具体的な状況のバインドパス
    getNotStableState().setBindPath("INSECURE_CONDITION");
    // [ID:0000509][Masahiko Higuchi] 2009/06 del begin 画面調整に伴い調整
//    getSickProgresss().setText("傷病または" + IkenshoConstants.LINE_SEPARATOR +
//                               "特定疾病の経過" + IkenshoConstants.LINE_SEPARATOR +
//                               "（250文字" + IkenshoConstants.LINE_SEPARATOR +
//                               "または5行以内）");
//
//    getNotStableState().setPreferredSize(new Dimension(400,20));
    getNotStableState().setMaxLength(30);
    getNotStableState().setColumns(30);
    getNotStableState().setIMEMode(InputSubset.KANJI);
//    getSickNameGroup().setText("診断名（特定疾病または生活機能低下の直接の原因となっている傷病名については１．に記入）及び発症年月日");
//    getProgressGroup().setText("生活機能低下の直接の原因となっている傷病または特定疾病の経過及び投薬内容を含む治療内容");
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
                "傷病または" + IkenshoConstants.LINE_SEPARATOR + "特定疾病の経過"
                        + IkenshoConstants.LINE_SEPARATOR + "（" + maxLength
                        + "文字" + IkenshoConstants.LINE_SEPARATOR + "または5行以内）");
  }
  //2009/01/08 [Tozo Tanaka] Add - end
  
  // [ID:0000509][Masahiko Higuchi] 2009/06 add begin 画面調整に伴い調整
  protected void addThisComponent(){
      this.add(getTitle(), VRLayout.NORTH);
      this.add(getSickNameGroup(), VRLayout.NORTH);
      this.add(getStableAndOutlook(), VRLayout.NORTH);
  }
  // [ID:0000509][Masahiko Higuchi] 2009/06 add end

}

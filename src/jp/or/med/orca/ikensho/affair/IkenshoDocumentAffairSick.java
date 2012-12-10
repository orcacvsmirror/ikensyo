package jp.or.med.orca.ikensho.affair;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.im.InputSubset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.text.ACTextAreaDocument;
import jp.nichicom.ac.text.ACTextFieldDocument;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.AbstractVRTextArea;
import jp.nichicom.vr.component.AbstractVRTextField;
import jp.nichicom.vr.component.VRButton;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoACRowMaximumableTextArea;
import jp.or.med.orca.ikensho.component.IkenshoACTextArea;
import jp.or.med.orca.ikensho.component.IkenshoACTextField;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
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
  //private ACComboBox sickMedicineDosageUnit5 = new ACComboBox();
  private IkenshoOptionComboBox sickMedicineDosageUnit5 = new IkenshoOptionComboBox();
//  private JScrollPane sickProgressSroll = new JScrollPane();
  private VRLabel sickMedicineUsageHead5 = new VRLabel();
  private VRLayout sickNames1Layout = new VRLayout();
  private VRLayout sickNames2Layout = new VRLayout();
  private VRLayout sickNames3Layout = new VRLayout();
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickMedicineDosageUnit4 = new IkenshoOptionComboBox();
  private IkenshoOptionComboBox sickMedicineDosageUnit3 = new IkenshoOptionComboBox();
  private IkenshoOptionComboBox sickMedicineDosageUnit6 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private ACLabelContainer sickMedicines1 = new ACLabelContainer();
  private IkenshoEraDateTextField sickDate3 = new IkenshoEraDateTextField();
  private VRLabel sickMedicineUsageHead6 = new VRLabel();
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickMedicineName1 = new IkenshoOptionComboBox();
  private IkenshoOptionComboBox sickMedicineDosageUnit2 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private ACTextField sickMedicineDosage6 = new IkenshoACTextField();
  private VRLabel sickMedicineDosageHead3 = new VRLabel();
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickMedicineName6 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private ACLabelContainer sickMedicines5 = new ACLabelContainer();
  private VRLabel sickMedicineDosageHead1 = new VRLabel();
  private ACLabelContainer sickProgresss;
  private ACGroupBox sickStableGroup;
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickMedicineName4 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private ACLabelContainer sickMedicines2 = new ACLabelContainer();
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickMedicineDosageUnit1 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private VRLabel sickMedicineUsageHead4 = new VRLabel();
  private ACTextField sickMedicineDosage2 = new IkenshoACTextField();
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickName1 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private VRLabel sickMedicineUsageHead2 = new VRLabel();
  
  //2009/01/22 [Tozo Tanaka] Replace - begin
//  private ACTextArea sickProgress = new ACTextArea();
  private IkenshoACTextArea sickProgress = new IkenshoACTextArea();
  //2009/01/22 [Tozo Tanaka] Replace - end
  
  private VRLayout sickLayout = new VRLayout();
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickMedicineName5 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private VRLabel sickMedicineUsageHead3 = new VRLabel();
  private ACParentHesesPanelContainer sickDates2;
  private ACGroupBox sickOutlookGroup;
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickMedicineUsage2 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private ACTextField sickMedicineDosage4 = new IkenshoACTextField();
  private IkenshoSpecialSickButton sickSpecial3;
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickMedicineUsage1 = new IkenshoOptionComboBox();
  private IkenshoOptionComboBox sickMedicineName3 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private ACParentHesesPanelContainer sickDates3;
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickMedicineUsage3 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private IkenshoEraDateTextField sickDate2 = new IkenshoEraDateTextField();
  private VRLabel sickMedicineDosageHead6 = new VRLabel();
  private ACLabelContainer sickMedicines6 = new ACLabelContainer();
  private IkenshoSpecialSickButton sickSpecial1;
  private ACTextField sickMedicineDosage5 = new IkenshoACTextField();
  private ACLabelContainer sickMedicines3 = new ACLabelContainer();
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickMedicineUsage5 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private ACParentHesesPanelContainer sickDates1;
  private ACTextField sickMedicineDosage1 = new IkenshoACTextField();
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickName3 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private ACClearableRadioButtonGroup sickStable;
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickMedicineName2 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private VRLabel sickMedicineDosageHead4 = new VRLabel();
  private ACTextField sickMedicineDosage3 = new IkenshoACTextField();
  private ACLabelContainer sickMedicines4 = new ACLabelContainer();
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickName2 = new IkenshoOptionComboBox();
  private IkenshoOptionComboBox sickMedicineUsage6 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private IkenshoSpecialSickButton sickSpecial2;
  private IkenshoEraDateTextField sickDate1 = new IkenshoEraDateTextField();
  private VRLabel sickMedicineUsageHead1 = new VRLabel();
//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  private IkenshoOptionComboBox sickMedicineUsage4 = new IkenshoOptionComboBox();
//2007/10/18 [Masahiko Higuchi] Replace - end
  private VRLabel sickMedicineDosageHead2 = new VRLabel();
  private ACClearableRadioButtonGroup sickOutlook = new ACClearableRadioButtonGroup();
  private VRLayout sickStableLayout = new VRLayout();
  private FlowLayout sickOutlookLayout = new FlowLayout();
  private ACLabelContainer sickStables = new ACLabelContainer();
  private ACLabelContainer sickOutlooks = new ACLabelContainer();
  private VRLayout sickProgressGroupLayout = new VRLayout();
  
  //2009/01/06 [Tozo Tanaka] Add - begin
  //薬剤7
  private ACLabelContainer sickMedicines7 = new ACLabelContainer();
  private IkenshoOptionComboBox sickMedicineName7 = new IkenshoOptionComboBox();
  private VRLabel sickMedicineDosageHead7 = new VRLabel();
  private ACTextField sickMedicineDosage7 = new IkenshoACTextField();
  private IkenshoOptionComboBox sickMedicineDosageUnit7 = new IkenshoOptionComboBox();
  private VRLabel sickMedicineUsageHead7 = new VRLabel();
  private IkenshoOptionComboBox sickMedicineUsage7 = new IkenshoOptionComboBox();
  //薬剤8
  private ACLabelContainer sickMedicines8 = new ACLabelContainer();
  private IkenshoOptionComboBox sickMedicineName8 = new IkenshoOptionComboBox();
  private VRLabel sickMedicineDosageHead8 = new VRLabel();
  private ACTextField sickMedicineDosage8 = new IkenshoACTextField();
  private IkenshoOptionComboBox sickMedicineDosageUnit8 = new IkenshoOptionComboBox();
  private VRLabel sickMedicineUsageHead8 = new VRLabel();
  private IkenshoOptionComboBox sickMedicineUsage8 = new IkenshoOptionComboBox();
  //隠れた入力済み薬剤名の警告
  // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴いサイズ指定を可能とする。
  private ACLabel sickMedicineValueWarning = new ACLabel();
  // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
  //2009/01/06 [Tozo Tanaka] Add - end
  // [ID:0000509][Masahiko Higuchi] 2009/06 add begin 画面調整に伴いサイズ指定を可能とする。
  // 薬剤名用ラベルコンテナのレイアウト
  private VRLayout sickMedicines1Layout;
  private VRLayout sickMedicines2Layout;
  private VRLayout sickMedicines3Layout;
  private VRLayout sickMedicines4Layout;
  private VRLayout sickMedicines5Layout;
  private VRLayout sickMedicines6Layout;
  private VRLayout sickMedicines7Layout;
  private VRLayout sickMedicines8Layout;
  // [ID:0000509][Masahiko Higuchi] 2009/06 add end


  /**
   * 傷病発症日1を返します。
   * @return 傷病発症日1
   */
  protected IkenshoEraDateTextField getSickDate1() {
    return sickDate1;
  }
  /**
   * 傷病発症日2を返します。
   * @return 傷病発症日2
   */
  protected IkenshoEraDateTextField getSickDate2() {
    return sickDate2;
  }
  /**
   * 傷病発症日3を返します。
   * @return 傷病発症日3
   */
  protected IkenshoEraDateTextField getSickDate3() {
    return sickDate3;
  }
  /**
   * 傷病名1コンテナを返します。
   * @return 傷病名1コンテナ
   */
  protected ACLabelContainer getSickNames1() {
    return sickNames1;
  }
  /**
   * 傷病名2コンテナを返します。
   * @return 傷病名2コンテナ
   */
  protected ACLabelContainer getSickNames2() {
    return sickNames2;
  }
  /**
   * 傷病名3コンテナを返します。
   * @return 傷病名3コンテナ
   */
  protected ACLabelContainer getSickNames3() {
    return sickNames3;
  }
  /**
   * 傷病名グループを返します。
   * @return 傷病名グループ
   */
  protected ACGroupBox getSickNameGroup() {
    return sickNameGroup;
  }
  /**
   * 予後の経過グループを返します。
   * @return 予後の経過グループ
   */
  protected ACGroupBox getProgressGroup() {
    return sickProgressGroup;
  }


  /**
   * タイトル表示ラベルを返します。
   * @return タイトル表示ラベル
   */
  protected VRLabel getTitle() {
    return sickTitle;
  }

  /**
   * 安定性と予後の見通しパネルを返します。
   * @return 安定性と予後の見通しパネル
   */
  protected VRPanel getStableAndOutlook() {
    if(sickStableAndOutlook==null){
      sickStableAndOutlook = new VRPanel();
    }
    return sickStableAndOutlook;
  }

  /**
   * overrideして症状としての安定性および予後の見直しの追加処理を定義します。
   */
  protected void addSickStableAndOutlook(){
    getStableAndOutlook().add(getSickStableGroup(), VRLayout.CLIENT);
    getStableAndOutlook().add(getOutlookGroup(), VRLayout.CLIENT);
  }


  /**
   * 予後の見通しグループを返します。
   * @return 予後の見通しグループ
   */
  protected ACGroupBox getOutlookGroup() {
    if(sickOutlookGroup==null){
      sickOutlookGroup = new ACGroupBox();
    }
    return sickOutlookGroup;
  }

  /**
   * 症状としての安定性グループを返します。
   * @return 症状としての安定性グループ
   */
  protected ACGroupBox getSickStableGroup() {
    if(sickStableGroup==null){
      sickStableGroup = new ACGroupBox();
    }
    return sickStableGroup;
  }

  /**
   * 予後の見通しを返します。
   * @return 予後の見通し
   */
  protected ACClearableRadioButtonGroup getOutlook() {
    return sickOutlook;
  }

  /**
   * overrideして症状としての安定性の追加処理を定義します。
   */
  protected void addSickStableGroup(){
    getSickStableGroup().add(sickStables, null);
  }

  /**
   * 安定性ラジオグループを返します。
   * @return 症状としての安定性ラジオグループ
   */
  protected ACClearableRadioButtonGroup getSickStable() {
    if(sickStable==null){
      sickStable = new ACClearableRadioButtonGroup();
    }
    return sickStable;
  }

  /**
   * 傷病の経過を返します。
   * @return 傷病の経過
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
      
      //2009/01/06 [Tozo Tanaka] Add - begin
      //薬剤7
      applyPoolTeikeibun(sickMedicineName7, IkenshoCommon.TEIKEI_MEDICINE_NAME);
      applyPoolTeikeibun(sickMedicineDosageUnit7, IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT);
      applyPoolTeikeibun(sickMedicineUsage7, IkenshoCommon.TEIKEI_MEDICINE_USAGE);
      //薬剤8
      applyPoolTeikeibun(sickMedicineName8, IkenshoCommon.TEIKEI_MEDICINE_NAME);
      applyPoolTeikeibun(sickMedicineDosageUnit8, IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT);
      applyPoolTeikeibun(sickMedicineUsage8, IkenshoCommon.TEIKEI_MEDICINE_USAGE);

      //2009/01/06 [Tozo Tanaka] Add - end

      
      // [ID:0000438][Tozo TANAKA] 2009/06/09 add begin 【主治医医見書・医師医見書】薬剤名テキストの追加
      initSickMedicineDocument();
      // [ID:0000438][Tozo TANAKA] 2009/06/09 add end 【主治医医見書・医師医見書】薬剤名テキストの追加
    }
  
  public boolean noControlError() {

    //エラーチェック
    IkenshoEraDateTextField[] sicks = new IkenshoEraDateTextField[]{sickDate1, sickDate2, sickDate3};
    int end = sicks.length;
    for(int i=0; i<end; i++){
      switch (sicks[i].getInputStatus()) {
        case IkenshoEraDateTextField.STATE_EMPTY:
        case IkenshoEraDateTextField.STATE_VALID:
          break;
        case IkenshoEraDateTextField.STATE_FUTURE:
          ACMessageBox.showExclamation("未来の日付です。");
          sicks[i].requestChildFocus();
          return false;
        default:
          ACMessageBox.show("日付に誤りがあります。");
          sicks[i].requestChildFocus();
          return false;
      }
    }
    
    // [ID:0000438][Tozo TANAKA] 2009/06/02 delete begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//    //2009/01/08 [Tozo Tanaka] Add - begin
//    if(!noControlErrorOfSickProgress()){
//        return false;
//    }
//    //2009/01/08 [Tozo Tanaka] Add - end
    // [ID:0000438][Tozo TANAKA] 2009/06/02 delete end 【主治医医見書・医師医見書】薬剤名テキストの追加
    
    return true;
  }
  
  //2009/01/08 [Tozo Tanaka] Add - begin
  // [ID:0000438][Tozo TANAKA] 2009/06/02 delete begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//  protected boolean noControlErrorOfSickProgress(){
//      int maxLen = getSickProgressMaxLengthWhenMedicineOverLength();
//      if(maxLen < 0){
//          //薬剤7または薬剤8が入力され、かつ傷病の経過が最大入力文字数を超過している場合
//          ACMessageBox.show(getSickProgressName() + "はトータルで"
//                      + Math.abs(maxLen) + "文字までしか入力できません。");
//          sickProgress.requestFocus();
//          return false;
////      }else if(maxLen < getSickProgress().getMaxLength()){
////          //薬剤7または薬剤8が入力されている場合
////          if(getMedicineViewCount()==6){
////              //薬剤名の表示個数は6個に設定されている
////              ACMessageBox
////                          .show("薬剤名の表示個数が6個の場合、7あるいは8個目の薬剤名等を入力した文書の保存・印刷はできません。"
////                                  + ACConstants.LINE_SEPARATOR
////                                  + "薬剤名の表示個数は、[メインメニュー]-[設定(S)]-[その他の設定(O)]より変更可能です。");
////              sickProgress.requestFocus();
////              return false;
////          }
//      }
//      return true;
//  }
  // [ID:0000438][Tozo TANAKA] 2009/06/02 delete end 【主治医医見書・医師医見書】薬剤名テキストの追加
  
  // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴いサイズ指定を可能とする。
  /**
     * 隠れた入力済み薬剤名の警告を返します。
     * 
     * @return 隠れた入力済み薬剤名の警告
     */
    protected ACLabel getSickMedicineValueWarning() {
        return sickMedicineValueWarning;
    }
  // [ID:0000509][Masahiko Higuchi] 2009/06 edit end

//  protected ACTextArea getSickProgress(){
  protected IkenshoACTextArea getSickProgress(){
      return sickProgress;
  }

  protected int getSickProgressMaxLengthCutDownLength(){
      int subChars = 0;
      //薬剤7～8の入力チェック
      for(int i=6; i<8; i++){
          if(!(
                  isNullText(getSickMedicineName(i).getEditor().getItem())&&
                  isNullText(getSickMedicineDosage(i).getText())&&
                  isNullText(getSickMedicineDosageUnit(i).getEditor().getItem())&&
                  isNullText(getSickMedicineUsage(i).getEditor().getItem())
                  )){
              subChars += 30;
          }
      }
      return subChars;
  }  
  protected int getSickProgressMaxLengthWhenMedicineOverLength(){
      // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//      int subChars = getSickProgressMaxLengthCutDownLength();
//      if(subChars > 0){
//          //薬剤7または薬剤8が入力されている場合
//          int maxLen = sickProgress.getMaxLength() - subChars;
//          if(maxLen < sickProgress.getText().replaceAll("[\r\n]","").length()){
//              //入力可能文字数を超えていたら負の値で返す
//              return -maxLen;
//          }else{
//              //入力可能文字数は超えていないが制限がかかっている場合は正の値で返す
//              return maxLen;
//          }
//      }
//      return sickProgress.getMaxLength();
      
      int defaultMaxLength = getSickProgress().getMaxLength();
      int totalChars = getSickProgress().getText().replaceAll("[\r\n]","").length();
      //薬剤1～8の入力チェック
      for(int i=0; i<8; i++){
          Object obj;
          obj = getSickMedicineName(i).getEditor().getItem();
          if(!isNullText(obj)){
              totalChars += ACCastUtilities.toString(obj,"").length();
          }
          obj = getSickMedicineDosage(i).getText();
          if(!isNullText(obj)){
              totalChars += ACCastUtilities.toString(obj,"").length();
          }
          obj = getSickMedicineDosageUnit(i).getEditor().getItem();
          if(!isNullText(obj)){
              totalChars += ACCastUtilities.toString(obj,"").length();
          }
          obj = getSickMedicineUsage(i).getEditor().getItem();
          if(!isNullText(obj)){
              totalChars += ACCastUtilities.toString(obj,"").length();
          }
          if(defaultMaxLength < totalChars){
              //入力可能文字数を超えていたら負の値で返す
              return defaultMaxLength - totalChars;
          }
      }
      //入力可能文字数は超えていないが制限がかかっている場合は正の値で返す
      return defaultMaxLength - totalChars;
      // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end 【主治医医見書・医師医見書】薬剤名テキストの追加
  }
  
  protected String getSickProgressName(){
      return "傷病または特定疾病の経過";
  }
  
  protected void setSickProgressContaierText(int maxLength){
      getSickProgresss().setText(
              getSickProgressName() + IkenshoConstants.LINE_SEPARATOR + "（" + maxLength
                        + "文字" + IkenshoConstants.LINE_SEPARATOR + "または"
                        + IkenshoConstants.LINE_SEPARATOR + "5行以内）");
  }
//  ComboBoxEditor edit = getEditor();
//  if (edit != null) {
//      Component comp = edit.getEditorComponent();
//      if (comp instanceof AbstractVRTextField) {
//          return (AbstractVRTextField) comp;
//      }
//  }
//  return null;

//// [ID:0000438][Tozo TANAKA] 2009/06/09 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//  protected class IkenshoSickMedicineLengthCheckFocusAdapter extends FocusAdapter{
//      private List exclusionAlertCompnents;
//    public List getExclusionAlertCompnents() {
//        return exclusionAlertCompnents;
//    }
//    public void setExclusionAlertComponents(List setExclusionAlertCompnents) {
//        this.exclusionAlertCompnents = setExclusionAlertCompnents;
//    }
//    public void focusLost(FocusEvent e) {
//          super.focusLost(e);
//          
//          boolean overLength = false;
//          
//          ACFrameEventProcesser processer = ACFrame.getInstance()
//                      .getFrameEventProcesser();
//          int maxLen = getSickProgressMaxLengthWhenMedicineOverLength();
//          if (maxLen < 0) {
//              // エラー色
//              getSickProgresss().setLabelFilled(true);
//              if (processer != null) {
//                  // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
////                  getSickProgresss().setForeground(
////                          processer.getContainerErrorForeground());
////                  getSickProgresss().setBackground(
////                          processer.getContainerErrorBackground());
//                  getSickProgresss().setForeground(
//                          processer.getContainerWarningForeground());
//                  getSickProgresss().setBackground(
//                          processer.getContainerWarningBackground());
//                  // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end 【主治医医見書・医師医見書】薬剤名テキストの追加
//              }
//              setSickProgressContaierText(maxLen);
//              
//              overLength = true;
//          } else {
//              // デフォルト色
//              getSickProgresss().setLabelFilled(false);
//                  getSickProgresss().setForeground(
//                          processer.getContainerDefaultForeground());
//                  getSickProgresss().setBackground(
//                          processer.getContainerDefaultBackground());
//                  setSickProgressContaierText(250);
//          }
//          //文字数表記更新
//          setSickProgressContaierText(Math.abs(maxLen));
//          
//          if(overLength){
//              Component lostedComp= e.getComponent();
//              Component gainedComp= e.getOppositeComponent();
//              if(gainedComp!=null && lostedComp!=null){
//                  boolean showAlert = true;
//                  List comps = getExclusionAlertCompnents();
//                  if(comps!=null){
//                      Iterator it1 = comps.iterator();
//                      while(it1.hasNext()){
//                          Iterator it2=((List)it1.next()).iterator();
//                          boolean gained = false;
//                          boolean losted = false;
//                          while(it2.hasNext()){
//                              Object obj=it2.next();
//                              if(lostedComp==obj){
//                                  losted=true;
//                              }else if(gainedComp==obj){
//                                  gained=true;
//                              }
//                              if(losted&&gained){
//                                  //同列のコンポーネント間のフォーカス遷移
//                                  showAlert = false;
//                                  break;
//                              }
//                          }
//                          if(!showAlert){
//                              break;
//                          }
//                      }
//                  }
//                  if(showAlert){
//                      //警告表示
//                      //薬剤7または薬剤8が入力され、かつ傷病の経過が最大入力文字数を超過している場合
//                      // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
////                      ACMessageBox.show(getSickProgressName() + "はトータルで"
////                                  + Math.abs(maxLen) + "文字までしか入力できません。");
//                      showAlertOnSickProgressLengthOver();
//                      // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end 【主治医医見書・医師医見書】薬剤名テキストの追加
//                  }
//              }
//          }
//          
//      }
//  }
//  //2009/01/08 [Tozo Tanaka] Add - end
  
//  protected void showAlertOnSickProgressLengthOver(){
//      ACMessageBox.show(getSickProgressName() + "と薬剤名のトータルが560文字を超えています。"
//              + IkenshoConstants.LINE_SEPARATOR + "印刷時に560文字を超える文字は印字されません。");
//  }
  protected static final int SICK_MEDICINE_LINE_COUNT_LIMIT = 11; 
  protected class IkenshoSickMedicineLengthCheckFieldDocument extends ACTextFieldDocument{
      public IkenshoSickMedicineLengthCheckFieldDocument(AbstractVRTextField textField) {
          super(textField);
      }
      public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
          if (isInsertProcessDisabled(offset, str, attr)) {
              return;
          }
          if(str != null){
              int maxLen = getSickProgressMaxLengthWhenMedicineOverLength();
              if (maxLen < str.length()) {
                  //文字数超過なら削る
                  if(maxLen <= 0){
                      //すでに最大文字数
                      return;
                  }
                  str  = str.substring(0, maxLen);
              }
          }
          
          //1文字ずつ減らしながら、行あふれしないギリギリの文字数を探す。
          boolean nowIsAbsoluteEditable = isAbsoluteEditable(); 
          setAbsoluteEditable(true);
          for(int i=str.length(); i>0; i--){
              super.insertString(offset, str.substring(0, i), attr);
              int totalLineCount = getSickMedicineTotalLineCount();
              if(totalLineCount <= SICK_MEDICINE_LINE_COUNT_LIMIT){
                  if (getTextComponent() instanceof IkenshoACTextField) {
                      ((IkenshoACTextField) getTextComponent())
                              .setModelForce(getTextComponent().getText());
                  }
                  updateSickMedicineValueWarningText(totalLineCount);
                  break;
              }
              super.remove(offset, i);
          }
          setAbsoluteEditable(nowIsAbsoluteEditable);
          
      }     
      public void remove(int offset, int length) throws BadLocationException {
          super.remove(offset, length);
          int totalLineCount = getSickMedicineTotalLineCount();
          updateSickMedicineValueWarningText(totalLineCount);
      }
    }
    protected class IkenshoSickMedicineLengthCheckAreaDocument extends ACTextAreaDocument{
        public IkenshoSickMedicineLengthCheckAreaDocument(AbstractVRTextArea textArea) {
            super(textArea);
        }
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (isInsertProcessDisabled(offset, str, attr)) {
                return;
            }
            if(str != null){
                int maxLen = getSickProgressMaxLengthWhenMedicineOverLength();
                if (maxLen < str.length()) {
                    //文字数超過なら削る
                    if(maxLen <= 0){
                        //すでに最大文字数
                        return;
                    }
                    str  = str.substring(0, maxLen);
                }
            }
            //1文字ずつ減らしながら、行あふれしないギリギリの文字数を探す。
            boolean nowIsAbsoluteEditable = isAbsoluteEditable(); 
            setAbsoluteEditable(true);
            for(int i=str.length(); i>0; i--){
                super.insertString(offset, str.substring(0, i), attr);
                int totalLineCount = getSickMedicineTotalLineCount();
                if(totalLineCount <= SICK_MEDICINE_LINE_COUNT_LIMIT){
                    if (getTextComponent() instanceof IkenshoACRowMaximumableTextArea) {
                        ((IkenshoACRowMaximumableTextArea) getTextComponent())
                                .setModelForce(getTextComponent().getText());
                    }
                    updateSickMedicineValueWarningText(totalLineCount);
                    break;
                }
                
                super.remove(offset, i);
            }
            
            setAbsoluteEditable(nowIsAbsoluteEditable);
            
        }
        public void remove(int offset, int length) throws BadLocationException {
            super.remove(offset, length);
            int totalLineCount = getSickMedicineTotalLineCount();
            updateSickMedicineValueWarningText(totalLineCount);
        }
    }
    protected int getSickMedicineTotalLineCount(){
        StringBuffer sbSickProgress = new StringBuffer();

        // 傷病治療状態
        String text = getSickProgress().getText();
        String[] sickProgressLines = ACTextUtilities.separateLineWrapOnByte(
                text, 100);
        int totalLineCount = sickProgressLines.length;
        sbSickProgress
                .append(ACTextUtilities.concatLineWrap(sickProgressLines));

        text = text.replaceAll("[\r\n]", "");
        int totalCharCount = text.length();
        int totalByteCount = text.getBytes().length;

        // 薬剤
        int lastMedicineLineIndex = 0;
        for (int i = 0; i < 4; i++) {
            boolean hasData = false;
            for (int j = 0; j < 2; j++) {
                int index = i * 2 + j;
                text = getSickMedicineName(index).getText();
                if (!IkenshoCommon.isNullText(text)) {
                    hasData = true;
                    break;
                }
                text = getSickMedicineDosage(index).getText();
                if (!IkenshoCommon.isNullText(text)) {
                    hasData = true;
                    break;
                }
                text = getSickMedicineDosageUnit(index).getText();
                if (!IkenshoCommon.isNullText(text)) {
                    hasData = true;
                    break;
                }
                text = getSickMedicineUsage(index).getText();
                if (!IkenshoCommon.isNullText(text)) {
                    hasData = true;
                    break;
                }
            }
            if(hasData){
                lastMedicineLineIndex = i + 1;
            }
        }

        if (totalByteCount > 500 || totalLineCount > 5 || lastMedicineLineIndex > 3) {
            // 傷病の経過が251文字以上または6行以上または薬剤名が6個以上の場合
            int lastLineByteCount = 0;
            StringBuffer sbMedicine = new StringBuffer();
            for (int index = 0; index <= 7; index++) {
                StringBuffer sb = new StringBuffer();
                int medicineCharLen = 0;
                int dosageCharLen = 0;
                int unitCharLen = 0;
                int usageCharLen = 0;
                // 薬剤セットを結合
                text = getSickMedicineName(index).getText();
                if (!IkenshoCommon.isNullText(text)) {
                    sb.append(text);
                    sb.append(" ");
                    medicineCharLen = text.length();
                }
                text = getSickMedicineDosage(index).getText();
                if (!IkenshoCommon.isNullText(text)) {
                    sb.append(text);
                    dosageCharLen = text.length();
                }
                text = getSickMedicineDosageUnit(index).getText();
                if (!IkenshoCommon.isNullText(text)) {
                    sb.append(text);
                    unitCharLen = text.length();
                }
                text = getSickMedicineUsage(index).getText();
                if (!IkenshoCommon.isNullText(text)) {
                    sb.append(" ");
                    sb.append(text);
                    usageCharLen = text.length();
                }

                // 連結して表示するか改行するかを判定
                int lineCharLen = medicineCharLen + dosageCharLen + unitCharLen
                        + usageCharLen;
                int lineByetLen = sb.toString().getBytes().length;
                if (lastLineByteCount + lineByetLen
                        + ((lastLineByteCount > 0) ? 2 : 0) > 100) {
                    sbMedicine.append(IkenshoConstants.LINE_SEPARATOR);
                    lastLineByteCount = lineByetLen;
                } else {
                    if (lastLineByteCount > 0) {
                        sbMedicine.append("  ");
                        lastLineByteCount += 2;
                    }
                    lastLineByteCount += lineByetLen;
                }
                // 最大文字数制限を判定
                if (totalCharCount + lineCharLen > 560) {
                    int useCharCount = 560 - totalCharCount;
                    int lastPos = 0;
                    // 薬剤名
                    if (useCharCount > medicineCharLen) {
                        lastPos += medicineCharLen + 1;
                        useCharCount -= medicineCharLen;
                        // 用量
                        if (useCharCount > dosageCharLen) {
                            lastPos += dosageCharLen;
                            useCharCount -= dosageCharLen;
                            // 用量単位
                            if (useCharCount > unitCharLen) {
                                lastPos += unitCharLen;
                                useCharCount -= unitCharLen;
                                // 用法
                                if (useCharCount <= usageCharLen) {
                                    useCharCount++;
                                }
                            }
                        }
                    }
                    lastPos += useCharCount;

                    sbMedicine.append(sb.substring(0, lastPos));
                    break;
                } else {
                    sbMedicine.append(sb.toString());
                }
                totalCharCount += lineCharLen;
            }
            // 傷病の経過と薬剤名の区切りとして改行を使用(計算上は空行をカウントしない)
            if (sbSickProgress.length() > 0 && sbMedicine.length() > 0) {
                // 傷病の経過と薬剤名の間には1行分の空行を挟んで区切りとする。
                sbSickProgress.append(IkenshoConstants.LINE_SEPARATOR);
            }
            // 連結
            sbSickProgress.append(sbMedicine.toString());
            
            return ACTextUtilities.separateLineWrap(sbSickProgress.toString()).length;
        }else{
            return totalLineCount + lastMedicineLineIndex;
        }
    }
    
    
    protected void updateSickMedicineValueWarningText(int totalLineCount){
        int defaultMaxLength = getSickProgress().getMaxLength();
        int maxLen = getSickProgressMaxLengthWhenMedicineOverLength();
        int inputedLen = Math.max(0,defaultMaxLength-maxLen);
        getSickMedicineValueWarning().setText(getSickMedicineValueWarningText(inputedLen,totalLineCount));
    }
    protected String getSickMedicineValueWarningText(int inputedCharCount, int inputedLineCount){
        return getSickProgressName() + " と 薬剤名 はトータルで560文字かつ11行しか入力できません。(現在 "+inputedCharCount+"文字 "+inputedLineCount+"行)";
    }

    protected void initSickMedicineDocument(){
        //横一列の薬剤関連コンポーネントを登録
        Component sickProgressContants = getSickProgress().getViewport().getComponent(0);
        if(sickProgressContants!=null){
            if(sickProgressContants instanceof AbstractVRTextArea){
                    ((AbstractVRTextArea) sickProgressContants)
                            .setDocument(new IkenshoSickMedicineLengthCheckAreaDocument(
                                    (AbstractVRTextArea) sickProgressContants));
            }
        }
        for(int i=0; i<8; i++){
            Component comp;
          
            comp = getSickMedicineName(i).getEditor().getEditorComponent();
            ((AbstractVRTextField) comp).setDocument(new IkenshoSickMedicineLengthCheckFieldDocument(
                    (AbstractVRTextField) comp));
              
            comp = getSickMedicineDosage(i);
            ((AbstractVRTextField) comp).setDocument(new IkenshoSickMedicineLengthCheckFieldDocument(
                    (AbstractVRTextField) comp));
              
            comp = getSickMedicineDosageUnit(i).getEditor().getEditorComponent();
            ((AbstractVRTextField) comp).setDocument(new IkenshoSickMedicineLengthCheckFieldDocument(
                    (AbstractVRTextField) comp));
              
            comp = getSickMedicineUsage(i).getEditor().getEditorComponent();
            ((AbstractVRTextField) comp).setDocument(new IkenshoSickMedicineLengthCheckFieldDocument(
                    (AbstractVRTextField) comp));
        }
    }
    
    // [ID:0000438][Tozo TANAKA] 2009/06/09 replace end 【主治医医見書・医師医見書】薬剤名テキストの追加

  protected void bindSourceInnerBindComponent() throws Exception {
    super.bindSourceInnerBindComponent();

    checkSpecialSick(sickName1, getSickSpecial1());
    checkSpecialSick(sickName2, getSickSpecial2());
    checkSpecialSick(sickName3, getSickSpecial3());
    // [ID:0000438][Masahiko Higuchi] 2009/06/02 add begin 【主治医医見書・医師医見書】薬剤名テキストの追加
    // 完全新規の場合に文字数を表示する
    updateSickMedicineValueWarningText(getSickMedicineTotalLineCount());
    // [ID:0000438][Masahiko Higuchi] 2009/06/02 add end
    //2009/01/06 [Tozo Tanaka] Add - begin
    // [ID:0000438][Masahiko Higuchi] 2009/06/02 del begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//    if(getMasterAffair().getMedicineViewCount()!=8){
//        if(getSickProgressMaxLengthCutDownLength() > 0){
//            //6個表示だが薬剤7または薬剤8に入力あり
//            getSickMedicineValueWarning().setVisible(true);
//        }
//        sickMedicines7.setVisible(false);
//        sickMedicines8.setVisible(false);
//    }else{
//        sickMedicines7.setVisible(true);
//        sickMedicines8.setVisible(true);
//    }
    // [ID:0000438][Masahiko Higuchi] 2009/06/02 del end
    
    // [ID:0000438][Tozo TANAKA] 2009/06/09 delete begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//    //フォーカスロストを発火させ、文字数表示を更新させる
//    getSickProgress().focusLost(new FocusEvent(getSickProgress(), FocusEvent.FOCUS_LOST));
//
//
//    //横一列の薬剤関連コンポーネントを登録
//    IkenshoSickMedicineLengthCheckFocusAdapter sickMedicineLengthCheckFocusAdapter = new IkenshoSickMedicineLengthCheckFocusAdapter();
//    Component sickProgressContants = getSickProgress().getViewport().getComponent(0);
//    List medicineLinesComponents = new ArrayList();
//    if(sickProgressContants!=null){
//        sickProgressContants.addFocusListener(sickMedicineLengthCheckFocusAdapter);
//
//        medicineLinesComponents.add(Arrays.asList(new Object[]{
//                sickProgressContants,
//                getMasterAffair().getUpdate(),
//                getMasterAffair().getPrint(),
//        }));
//    }
//    for(int i=0; i<8; i++){
//        getSickMedicineName(i).getEditor().getEditorComponent().addFocusListener(sickMedicineLengthCheckFocusAdapter);
//        getSickMedicineDosage(i).addFocusListener(sickMedicineLengthCheckFocusAdapter);
//        getSickMedicineDosageUnit(i).getEditor().getEditorComponent().addFocusListener(sickMedicineLengthCheckFocusAdapter);
//        getSickMedicineUsage(i).getEditor().getEditorComponent().addFocusListener(sickMedicineLengthCheckFocusAdapter);
//        
////        medicineLinesComponents.add(Arrays.asList(new Object[]{
////                getSickMedicineName(i).getEditor().getEditorComponent(),
////                getSickMedicineDosage(i),
////                getSickMedicineDosageUnit(i).getEditor().getEditorComponent(),
////                getSickMedicineUsage(i).getEditor().getEditorComponent(),
////                getMasterAffair().getUpdate(),
////                getMasterAffair().getPrint(),
////        }));
//        
//      medicineLinesComponents.add(Arrays.asList(new Object[] {
//                    getSickMedicineName(i).getEditor().getEditorComponent(),
//                    getMasterAffair().getUpdate(),
//                    getMasterAffair().getPrint(), }));
//            medicineLinesComponents.add(Arrays.asList(new Object[] {
//                    getSickMedicineDosage(i), getMasterAffair().getUpdate(),
//                    getMasterAffair().getPrint(), }));
//            medicineLinesComponents.add(Arrays.asList(new Object[] {
//                    getSickMedicineDosageUnit(i).getEditor()
//                            .getEditorComponent(),
//                    getMasterAffair().getUpdate(),
//                    getMasterAffair().getPrint(), }));
//            medicineLinesComponents.add(Arrays.asList(new Object[] {
//                    getSickMedicineUsage(i).getEditor().getEditorComponent(),
//                    getMasterAffair().getUpdate(),
//                    getMasterAffair().getPrint(), }));
//    }
//    sickMedicineLengthCheckFocusAdapter.setExclusionAlertComponents(medicineLinesComponents);
//    //2009/01/06 [Tozo Tanaka] Add - end
//
    // [ID:0000438][Tozo TANAKA] 2009/06/09 delete end 【主治医医見書・医師医見書】薬剤名テキストの追加
  }
  /**
   * 特定疾病を含むかをチェックします。
   * @param combo 疾病名コンボ
   * @param button 特定疾患ボタン
   * @throws ParseException 処理例外
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
   * コンストラクタです。
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
        String[] {"安定", "不安定", "不明"}))));

    sickOutlook.setModel(new VRListModelAdapter(new
                                                VRArrayList(Arrays.asList(new
        String[] {"改善", "不変", "悪化"}))));

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
    sickMedicineDosageHead5.setText("　　　");
    sickMedicineUsageHead5.setText("　　　");
    sickNames1.setText("１．");
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
    sickMedicines1.setText("薬剤名１．");
    sickDate3.setBindPath("HASHOU_DT3");
    sickDate3.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
    sickDate3.setAgeVisible(false);
    sickDate3.setAllowedUnknown(true);
    sickMedicineUsageHead6.setText("　　　");
    // [ID:0000752][Shin Fujihara] 2012/11 edit begin 2012年度対応 薬剤名項目の入力文字数拡張
    //sickMedicineName1.setPreferredSize(new Dimension(220, 19));
    //sickMedicineName1.setMaxLength(12);
    sickMedicineNameSetting(sickMedicineName1);
    // [ID:0000752][Shin Fujihara] 2012/11 edit end 2012年度対応 薬剤名項目の入力文字数拡張
    sickMedicineName1.setBindPath("MEDICINE1");
    sickMedicineName1.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageUnit2.setPreferredSize(new Dimension(100, 19));
    sickMedicineDosageUnit2.setBindPath("UNIT2");
    sickMedicineDosageUnit2.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageUnit2.setMaxLength(4);
    getStableAndOutlook().setLayout(new VRLayout());
    sickMedicineDosage6.setMaxLength(4);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴い調整
    sickMedicineDosage6.setColumns(5);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    sickMedicineDosage6.setHorizontalAlignment(SwingConstants.RIGHT);
    sickMedicineDosage6.setBindPath("DOSAGE6");
    sickMedicineDosage6.setIMEMode(InputSubset.LATIN);
    sickMedicineDosageHead3.setText("　　　");
    // [ID:0000752][Shin Fujihara] 2012/11 edit begin 2012年度対応 薬剤名項目の入力文字数拡張
    //sickMedicineName6.setPreferredSize(new Dimension(220, 19));
    //sickMedicineName6.setMaxLength(12);
    sickMedicineNameSetting(sickMedicineName6);
    // [ID:0000752][Shin Fujihara] 2012/11 edit end 2012年度対応 薬剤名項目の入力文字数拡張
    sickMedicineName6.setBindPath("MEDICINE6");
    sickMedicineName6.setIMEMode(InputSubset.KANJI);
    sickNames2.setText("２．");
    sickMedicines5.setText("　　　５．");
    sickMedicines5.setToolTipText("");
    sickMedicineDosageHead1.setText("　用量");
    getSickProgresss().setText("傷病の経過" + IkenshoConstants.LINE_SEPARATOR + "（250文字" +
                          IkenshoConstants.LINE_SEPARATOR + "または" +
                          IkenshoConstants.LINE_SEPARATOR + "5行以内）");
    getSickStableGroup().setText("症状としての安定性");
    getSickStableGroup().setLayout(sickStableLayout);
    // [ID:0000752][Shin Fujihara] 2012/11 edit begin 2012年度対応 薬剤名項目の入力文字数拡張
    //sickMedicineName4.setPreferredSize(new Dimension(220, 19));
    //sickMedicineName4.setMaxLength(12);
    sickMedicineNameSetting(sickMedicineName4);
    // [ID:0000752][Shin Fujihara] 2012/11 edit end 2012年度対応 薬剤名項目の入力文字数拡張
    sickMedicineName4.setBindPath("MEDICINE4");
    sickMedicineName4.setIMEMode(InputSubset.KANJI);
    sickMedicines2.setToolTipText("");
    sickMedicines2.setText("　　　２．");
    sickNames3.setText("３．");
    sickMedicineDosageUnit1.setPreferredSize(new Dimension(100, 19));
    sickMedicineDosageUnit1.setBindPath("UNIT1");
    sickMedicineDosageUnit1.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageUnit1.setMaxLength(4);
    sickMedicineUsageHead4.setText("　　　");
    sickMedicineDosage2.setMaxLength(4);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴い調整
    sickMedicineDosage2.setColumns(5);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    sickMedicineDosage2.setHorizontalAlignment(SwingConstants.RIGHT);
    sickMedicineDosage2.setBindPath("DOSAGE2");
    sickMedicineDosage2.setIMEMode(InputSubset.LATIN);
    sickName1.setMaxLength(30);
    sickName1.setIMEMode(InputSubset.KANJI);
    sickName1.setBindPath("SINDAN_NM1");
    sickMedicineUsageHead2.setText("　　　");
//    sickProgress.setColumns(87);
    sickProgress.setColumns(100);
//    sickProgress.setColumns(86);
    sickProgress.setLineWrap(true);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴い調整
    sickProgress.setRows(12);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    sickProgress.setBindPath("MT_STS");
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴い調整
    sickProgress.setMaxLength(560);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
//    sickProgress.setMaxColumns(100);
//    sickProgress.setMaxColumns(86);
//    sickProgress.setUseMaxRows(true);
    sickProgress.setMaxRows(sickProgress.getRows());
    sickProgress.setIMEMode(InputSubset.KANJI);
    sickLayout.setFitHLast(true);
    sickLayout.setFitVLast(true);
    // [ID:0000752][Shin Fujihara] 2012/11 edit begin 2012年度対応 薬剤名項目の入力文字数拡張
    //sickMedicineName5.setPreferredSize(new Dimension(220, 19));
    //sickMedicineName5.setMaxLength(12);
    sickMedicineNameSetting(sickMedicineName5);
    // [ID:0000752][Shin Fujihara] 2012/11 edit end 2012年度対応 薬剤名項目の入力文字数拡張
    sickMedicineName5.setBindPath("MEDICINE5");
    sickMedicineName5.setIMEMode(InputSubset.KANJI);
    sickMedicineUsageHead3.setText("　　　");
    getSickDates2().setBeginText("発症年月日（");
    getSickDates2().setEndText("）");
    sickDate2.getDayUnit().setText("日頃");
    getOutlookGroup().setText("介護の必要の程度に関する予後の見通し");
    getOutlookGroup().setLayout(sickOutlookLayout);
    sickMedicineUsage2.setPreferredSize(new Dimension(180, 19));
    sickMedicineUsage2.setMaxLength(10);
    sickMedicineUsage2.setBindPath("USAGE2");
    sickMedicineUsage2.setIMEMode(InputSubset.KANJI);
    sickMedicineDosage4.setMaxLength(4);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴い調整
    sickMedicineDosage4.setColumns(5);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    sickMedicineDosage4.setHorizontalAlignment(SwingConstants.RIGHT);
    sickMedicineDosage4.setBindPath("DOSAGE4");
    sickMedicineDosage4.setIMEMode(InputSubset.LATIN);
    sickTitle.setText("１．傷病に関する意見");
    sickTitle.setForeground(IkenshoConstants.COLOR_PANEL_TITLE_FOREGROUND);
    sickTitle.setOpaque(true);
    sickTitle.setBackground(IkenshoConstants.COLOR_PANEL_TITLE_BACKGROUND);
    sickMedicineUsage1.setPreferredSize(new Dimension(180, 19));
    sickMedicineUsage1.setMaxLength(10);
    sickMedicineUsage1.setBindPath("USAGE1");
    sickMedicineUsage1.setIMEMode(InputSubset.KANJI);
    // [ID:0000752][Shin Fujihara] 2012/11 edit begin 2012年度対応 薬剤名項目の入力文字数拡張
    //sickMedicineName3.setPreferredSize(new Dimension(220, 19));
    //sickMedicineName3.setMaxLength(12);
    sickMedicineNameSetting(sickMedicineName3);
    // [ID:0000752][Shin Fujihara] 2012/11 edit end 2012年度対応 薬剤名項目の入力文字数拡張
    sickMedicineName3.setBindPath("MEDICINE3");
    sickMedicineName3.setIMEMode(InputSubset.KANJI);
    getSickDates3().setEndText("）");
    sickDate3.getDayUnit().setText("日頃");
    getSickDates3().setBeginText("発症年月日（");
    sickMedicineUsage3.setPreferredSize(new Dimension(180, 19));
    sickMedicineUsage3.setMaxLength(10);
    sickMedicineUsage3.setBindPath("USAGE3");
    sickMedicineUsage3.setIMEMode(InputSubset.KANJI);
    sickDate2.setBindPath("HASHOU_DT2");
    sickDate2.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
    sickDate2.setAgeVisible(false);
    sickDate2.setAllowedUnknown(true);
    sickMedicineDosageHead6.setText("　　　");
    sickMedicines6.setText("　　　６．");
    sickMedicines6.setToolTipText("");
    sickMedicineDosage5.setMaxLength(4);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴い調整
    sickMedicineDosage5.setColumns(5);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    sickMedicineDosage5.setHorizontalAlignment(SwingConstants.RIGHT);
    sickMedicineDosage5.setBindPath("DOSAGE5");
    sickMedicineDosage5.setIMEMode(InputSubset.LATIN);
    sickMedicines3.setText("　　　３．");
    sickMedicines3.setToolTipText("");
    sickMedicineUsage5.setPreferredSize(new Dimension(180, 19));
    sickMedicineUsage5.setMaxLength(10);
    sickMedicineUsage5.setBindPath("USAGE5");
    sickMedicineUsage5.setIMEMode(InputSubset.KANJI);
    getSickDates1().setBeginText("発症年月日（");
    getSickDates1().setEndText("）");
    sickDate1.getDayUnit().setText("日頃");
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴い調整
    sickMedicineDosage1.setColumns(5);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    sickMedicineDosage1.setHorizontalAlignment(SwingConstants.RIGHT);
    sickMedicineDosage1.setBindPath("DOSAGE1");
    sickMedicineDosage1.setMaxLength(4);
    sickMedicineDosage1.setIMEMode(InputSubset.LATIN);
    sickName3.setIMEMode(InputSubset.KANJI);
    sickName3.setMaxLength(30);
    sickName3.setBindPath("SINDAN_NM3");
    getSickStable().setBindPath("SHJ_ANT");
    // [ID:0000752][Shin Fujihara] 2012/11 edit begin 2012年度対応 薬剤名項目の入力文字数拡張
    //sickMedicineName2.setPreferredSize(new Dimension(220, 19));
    //sickMedicineName2.setMaxLength(12);
    sickMedicineNameSetting(sickMedicineName2);
    // [ID:0000752][Shin Fujihara] 2012/11 edit end 2012年度対応 薬剤名項目の入力文字数拡張
    sickMedicineName2.setBindPath("MEDICINE2");
    sickMedicineName2.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageHead4.setText("　　　");
    sickMedicineDosage3.setMaxLength(4);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴い調整
    sickMedicineDosage3.setColumns(5);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    sickMedicineDosage3.setHorizontalAlignment(SwingConstants.RIGHT);
    sickMedicineDosage3.setBindPath("DOSAGE3");
    sickMedicineDosage3.setIMEMode(InputSubset.LATIN);
    sickMedicines4.setText("　　　４．");
    sickMedicines4.setToolTipText("");
    sickName2.setIMEMode(InputSubset.KANJI);
    sickName2.setMaxLength(30);
    sickName2.setBindPath("SINDAN_NM2");
    sickProgressGroupLayout.setVgap(0);
    sickProgressGroup.setLayout(sickProgressGroupLayout);
    sickProgressGroup.setText("障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容");
    sickMedicineUsage6.setPreferredSize(new Dimension(180, 19));
    sickMedicineUsage6.setMaxLength(10);
    sickMedicineUsage6.setBindPath("USAGE6");
    sickMedicineUsage6.setIMEMode(InputSubset.KANJI);
    sickNameGroup.setText("診断名（特定疾病または障害の直接の原因となっている傷病名については１．に記入）及び発症年月日");
//    sickNameGroup
    sickDate1.setBindPath("HASHOU_DT1");
    sickDate1.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
    sickDate1.setAgeVisible(false);
    sickDate1.setAllowedUnknown(true);
    sickMedicineUsageHead1.setText("　用法");
    sickMedicineUsage4.setPreferredSize(new Dimension(180, 19));
    sickMedicineUsage4.setMaxLength(10);
    sickMedicineUsage4.setBindPath("USAGE4");
    sickMedicineUsage4.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageHead2.setText("　　　");
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
    

    //2009/01/06 [Tozo Tanaka] Add - begin
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴い調整
    // 印刷に関する警告
    getSickMedicineValueWarning().setText(getSickProgressName() + " と 薬剤名 はトータルで560文字しか印刷されません。");
    getSickMedicineValueWarning().setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);
    sickProgressGroup.add(getSickMedicineValueWarning(), VRLayout.SOUTH);  
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    
    //薬剤8
    sickMedicines8.setText("　　　８．");
    sickMedicines8.setToolTipText("");
    // [ID:0000752][Shin Fujihara] 2012/11 edit begin 2012年度対応 薬剤名項目の入力文字数拡張
    //sickMedicineName8.setPreferredSize(new Dimension(220, 19));
    //sickMedicineName8.setMaxLength(12);
    sickMedicineNameSetting(sickMedicineName8);
    // [ID:0000752][Shin Fujihara] 2012/11 edit end 2012年度対応 薬剤名項目の入力文字数拡張
    sickMedicineName8.setBindPath("MEDICINE8");
    sickMedicineName8.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageHead8.setText("　　　");
    sickMedicineDosage8.setMaxLength(4);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴い調整
    sickMedicineDosage8.setColumns(5);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    sickMedicineDosage8.setHorizontalAlignment(SwingConstants.RIGHT);
    sickMedicineDosage8.setBindPath("DOSAGE8");
    sickMedicineDosage8.setIMEMode(InputSubset.LATIN);
    sickMedicineDosageUnit8.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageUnit8.setMaxLength(4);
    sickMedicineDosageUnit8.setBindPath("UNIT8");
    sickMedicineDosageUnit8.setPreferredSize(new Dimension(100, 19));
    sickMedicineUsageHead8.setText("　　　");
    sickMedicineUsage8.setPreferredSize(new Dimension(180, 19));
    sickMedicineUsage8.setMaxLength(10);
    sickMedicineUsage8.setBindPath("USAGE8");
    sickMedicineUsage8.setIMEMode(InputSubset.KANJI);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴いサイズ指定を可能とする。
    // レイアウト設定
    sickMedicines8.setLayout(getSickMedicines8Layout());
    sickMedicines8.add(sickMedicineName8, VRLayout.FLOW);
    sickMedicines8.add(sickMedicineDosageHead8, VRLayout.FLOW);
    sickMedicines8.add(sickMedicineDosage8, VRLayout.FLOW);
    sickMedicines8.add(sickMedicineDosageUnit8, VRLayout.FLOW);
    sickMedicines8.add(sickMedicineUsageHead8, VRLayout.FLOW);
    sickMedicines8.add(sickMedicineUsage8, VRLayout.FLOW);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    sickProgressGroup.add(sickMedicines8, VRLayout.SOUTH);  
    //薬剤7
    sickMedicines7.setText("　　　７．");
    sickMedicines7.setToolTipText("");
    // [ID:0000752][Shin Fujihara] 2012/11 edit begin 2012年度対応 薬剤名項目の入力文字数拡張
    //sickMedicineName7.setPreferredSize(new Dimension(220, 19));
    //sickMedicineName7.setMaxLength(12);
    sickMedicineNameSetting(sickMedicineName7);
    // [ID:0000752][Shin Fujihara] 2012/11 edit end 2012年度対応 薬剤名項目の入力文字数拡張
    sickMedicineName7.setBindPath("MEDICINE7");
    sickMedicineName7.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageHead7.setText("　　　");
    sickMedicineDosage7.setMaxLength(4);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴い調整
    sickMedicineDosage7.setColumns(5);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    sickMedicineDosage7.setHorizontalAlignment(SwingConstants.RIGHT);
    sickMedicineDosage7.setBindPath("DOSAGE7");
    sickMedicineDosage7.setIMEMode(InputSubset.LATIN);
    sickMedicineDosageUnit7.setIMEMode(InputSubset.KANJI);
    sickMedicineDosageUnit7.setMaxLength(4);
    sickMedicineDosageUnit7.setBindPath("UNIT7");
    sickMedicineDosageUnit7.setPreferredSize(new Dimension(100, 19));
    sickMedicineUsageHead7.setText("　　　");
    sickMedicineUsage7.setPreferredSize(new Dimension(180, 19));
    sickMedicineUsage7.setMaxLength(10);
    sickMedicineUsage7.setBindPath("USAGE7");
    sickMedicineUsage7.setIMEMode(InputSubset.KANJI);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴いサイズ指定を可能とする。
    // レイアウト設定
    sickMedicines7.setLayout(getSickMedicines7Layout());
    sickMedicines7.add(sickMedicineName7, VRLayout.FLOW);
    sickMedicines7.add(sickMedicineDosageHead7, VRLayout.FLOW);
    sickMedicines7.add(sickMedicineDosage7, VRLayout.FLOW);
    sickMedicines7.add(sickMedicineDosageUnit7, VRLayout.FLOW);
    sickMedicines7.add(sickMedicineUsageHead7, VRLayout.FLOW);
    sickMedicines7.add(sickMedicineUsage7, VRLayout.FLOW);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    sickProgressGroup.add(sickMedicines7, VRLayout.SOUTH);
    
    //2009/01/06 [Tozo Tanaka] Add - end    
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴いサイズ指定を可能とする。
    // レイアウト設定
    sickMedicines6.setLayout(getSickMedicines6Layout());
    sickMedicines6.add(sickMedicineName6, VRLayout.FLOW);
    sickMedicines6.add(sickMedicineDosageHead6, VRLayout.FLOW);
    sickMedicines6.add(sickMedicineDosage6, VRLayout.FLOW);
    sickMedicines6.add(sickMedicineDosageUnit6, VRLayout.FLOW);
    sickMedicines6.add(sickMedicineUsageHead6, VRLayout.FLOW);
    sickMedicines6.add(sickMedicineUsage6, VRLayout.FLOW);
    sickProgressGroup.add(sickMedicines6, VRLayout.SOUTH);
    
    sickMedicines5.setLayout(getSickMedicines5Layout());
    sickProgressGroup.add(sickMedicines5, VRLayout.SOUTH);
    sickMedicines5.add(sickMedicineName5, VRLayout.FLOW);
    sickMedicines5.add(sickMedicineDosageHead5, VRLayout.FLOW);
    sickMedicines5.add(sickMedicineDosage5, VRLayout.FLOW);
    sickMedicines5.add(sickMedicineDosageUnit5, VRLayout.FLOW);
    sickMedicines5.add(sickMedicineUsageHead5, VRLayout.FLOW);
    sickMedicines5.add(sickMedicineUsage5, VRLayout.FLOW);
    
    sickMedicines4.setLayout(getSickMedicines4Layout());
    sickProgressGroup.add(sickMedicines4, VRLayout.SOUTH);
    sickMedicines4.add(sickMedicineName4, VRLayout.FLOW);
    sickMedicines4.add(sickMedicineDosageHead4, VRLayout.FLOW);
    sickMedicines4.add(sickMedicineDosage4, VRLayout.FLOW);
    sickMedicines4.add(sickMedicineDosageUnit4, VRLayout.FLOW);
    sickMedicines4.add(sickMedicineUsageHead4, VRLayout.FLOW);
    sickMedicines4.add(sickMedicineUsage4, VRLayout.FLOW);
    
    sickMedicines3.setLayout(getSickMedicines3Layout());
    sickProgressGroup.add(sickMedicines3, VRLayout.SOUTH);
    sickMedicines3.add(sickMedicineName3, VRLayout.FLOW);
    sickMedicines3.add(sickMedicineDosageHead3, VRLayout.FLOW);
    sickMedicines3.add(sickMedicineDosage3, VRLayout.FLOW);
    sickMedicines3.add(sickMedicineDosageUnit3, VRLayout.FLOW);
    sickMedicines3.add(sickMedicineUsageHead3, VRLayout.FLOW);
    sickMedicines3.add(sickMedicineUsage3, VRLayout.FLOW);
    
    sickMedicines2.setLayout(getSickMedicines2Layout());
    sickProgressGroup.add(sickMedicines2, VRLayout.SOUTH);
    sickMedicines2.add(sickMedicineName2, VRLayout.FLOW);
    sickMedicines2.add(sickMedicineDosageHead2, VRLayout.FLOW);
    sickMedicines2.add(sickMedicineDosage2, VRLayout.FLOW);
    sickMedicines2.add(sickMedicineDosageUnit2, VRLayout.FLOW);
    sickMedicines2.add(sickMedicineUsageHead2, VRLayout.FLOW);
    sickMedicines2.add(sickMedicineUsage2, VRLayout.FLOW);
    
    sickMedicines1.setLayout(getSickMedicines1Layout());
    sickProgressGroup.add(sickMedicines1, VRLayout.SOUTH);
    sickMedicines1.add(sickMedicineName1, VRLayout.FLOW);
    sickMedicines1.add(sickMedicineDosageHead1, VRLayout.FLOW);
    sickMedicines1.add(sickMedicineDosage1, VRLayout.FLOW);
    sickMedicines1.add(sickMedicineDosageUnit1, VRLayout.FLOW);
    sickMedicines1.add(sickMedicineUsageHead1, VRLayout.FLOW);
    sickMedicines1.add(sickMedicineUsage1, VRLayout.FLOW);
    // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    
    //[ID:0000753][Shin Fujihara] 2012/09 add begin 2012年度対応 薬剤情報の行単位でのクリア機能
    sickMedicines1.add(createMedicinesClearButton(), VRLayout.FLOW);
    sickMedicines2.add(createMedicinesClearButton(), VRLayout.FLOW);
    sickMedicines3.add(createMedicinesClearButton(), VRLayout.FLOW);
    sickMedicines4.add(createMedicinesClearButton(), VRLayout.FLOW);
    sickMedicines5.add(createMedicinesClearButton(), VRLayout.FLOW);
    sickMedicines6.add(createMedicinesClearButton(), VRLayout.FLOW);
    sickMedicines7.add(createMedicinesClearButton(), VRLayout.FLOW);
    sickMedicines8.add(createMedicinesClearButton(), VRLayout.FLOW);
    //[ID:0000753][Shin Fujihara] 2012/09 add end 2012年度対応 薬剤情報の行単位でのクリア機能
    
    sickProgressGroup.add(getSickProgresss(), VRLayout.NORTH);
    getSickProgresss().setLayout(new VRLayout());
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin 【2011年度対応：訪問看護指示書】帳票印字文字数の拡大
    //getSickProgresss().add(sickProgress, VRLayout.LEFT);
    getSickProgresss().add(getSickProgress(), VRLayout.LEFT);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin 【2011年度対応：訪問看護指示書】帳票印字文字数の拡大
    // 2006/07/24 - 医師意見書
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
    // [ID:0000509][Masahiko Higuchi] 2009/06 add begin 画面調整に伴い調整
    setSickProgressContaierText(0);
    // [ID:0000509][Masahiko Higuchi] 2009/06 add end
    // 2006/07/24 - 医師意見書
    // Replace - begin [Masahiko Higuchi
    addThisComponent();
        // this.add(sickTitle, VRLayout.NORTH);
        // this.add(sickNameGroup, VRLayout.NORTH);
        // this.add(sickStableAndOutlook, VRLayout.NORTH);
        // this.add(sickProgressGroup, VRLayout.NORTH);
    // Replace - end

    
  }
  
  /**
   * 入院歴グループに追加する項目を定義します。
   */
  protected void addSickGroupComponent(){
      // 2006/07/24 - 医師意見書
      // Addition - begin [Masahiko Higuchi]
      sickNameGroup.add(sickNames1, VRLayout.FLOW_INSETLINE_RETURN);
      sickNameGroup.add(sickNames2, VRLayout.FLOW_INSETLINE_RETURN);
      sickNameGroup.add(sickNames3, VRLayout.FLOW_INSETLINE_RETURN);
      // Addition - end
  }
  
  /**
   * タブに追加する順番・項目を定義します。
   */
  protected void addThisComponent(){
      // 2006/07/24 - 医師意見書
      // Addition - begin [Masahiko Higuchi]
      this.add(sickTitle, VRLayout.NORTH);
      this.add(sickNameGroup, VRLayout.NORTH);
      this.add(sickStableAndOutlook, VRLayout.NORTH);
      this.add(sickProgressGroup, VRLayout.NORTH);
      // Addition - end
  }
      
  /**
   * 特定疾病ボタン３を返します。
   * @return
   */
    protected IkenshoSpecialSickButton getSickSpecial3() {
        if(sickSpecial3 == null){
            sickSpecial3 = new IkenshoSpecialSickButton();
        }
        return sickSpecial3;
    }
    
    /**
     * 特定疾病ボタン１を返します。
     * @return
     */
    protected IkenshoSpecialSickButton getSickSpecial1() {
        if(sickSpecial1 == null){
            sickSpecial1 = new IkenshoSpecialSickButton();
        }
        return sickSpecial1;
    }
    
    /**
     * 特定疾病ボタン２を返します。
     * @return
     */
    protected IkenshoSpecialSickButton getSickSpecial2() {
        if(sickSpecial2 == null){
            sickSpecial2 = new IkenshoSpecialSickButton();
        }
        return sickSpecial2;
    }
    /**
     * 傷病名1を返します。
     * @return 傷病名1
     * @since 3.0.5
     * @author Masahiko Higuchi
     */
    protected IkenshoOptionComboBox getSickName1(){
        return sickName1;
    }
    /**
     * 傷病名2を返します。
     * @return 傷病名2
     * @since 3.0.5
     * @author Masahiko Higuchi
     */
    protected IkenshoOptionComboBox getSickName2(){
        return sickName2;
    }
    /**
     * 傷病名3を返します。
     * @return 傷病名3
     * @since 3.0.5
     * @author Masahiko Higuchi
     */
    protected IkenshoOptionComboBox getSickName3(){
        return sickName3;
    }
    /**
     * 指定番号の投薬名を返します。
     * @param index 番号
     * @return 投薬名
     * @since 3.0.5
     * @author Masahiko Higuchi
     */
    protected IkenshoOptionComboBox getSickMedicineName(int index){
        return new IkenshoOptionComboBox[] { sickMedicineName1, sickMedicineName2,
                  sickMedicineName3, sickMedicineName4, sickMedicineName5,
                  sickMedicineName6, sickMedicineName7, sickMedicineName8 }[index];
    }
    //2009/01/08 [Tozo Tanaka] Add - begin
    /**
     * 指定番号の投薬用量を返します。
     * @param index 番号
     * @return 投薬用量
     * @since 3.0.8
     * @author Tozo Tanaka
     */
    protected ACTextField getSickMedicineDosage(int index){
        return new ACTextField[] { sickMedicineDosage1, sickMedicineDosage2,
                  sickMedicineDosage3, sickMedicineDosage4, sickMedicineDosage5,
                  sickMedicineDosage6, sickMedicineDosage7, sickMedicineDosage8 }[index];
    }
    //2009/01/08 [Tozo Tanaka] Add - end
    /**
     * 指定番号の投薬単位を返します。
     * @param index 番号
     * @return 投薬単位
     * @since 3.0.5
     * @author Masahiko Higuchi
     */
    protected IkenshoOptionComboBox getSickMedicineDosageUnit(int index){
        return new IkenshoOptionComboBox[] { sickMedicineDosageUnit1, sickMedicineDosageUnit2,
                  sickMedicineDosageUnit3, sickMedicineDosageUnit4, sickMedicineDosageUnit5,
                  sickMedicineDosageUnit6, sickMedicineDosageUnit7, sickMedicineDosageUnit8 }[index];
    }
    /**
     * 指定番号の投薬用法を返します。
     * @param index 番号
     * @return 投薬用法
     * @since 3.0.5
     * @author Masahiko Higuchi
     */
    protected IkenshoOptionComboBox getSickMedicineUsage(int index){
        return new IkenshoOptionComboBox[] { sickMedicineUsage1, sickMedicineUsage2,
                  sickMedicineUsage3, sickMedicineUsage4, sickMedicineUsage5,
                  sickMedicineUsage6, sickMedicineUsage7, sickMedicineUsage8 }[index];
    }
    /**
     * 発症年月日Hesesパネル1を返します。
     * @return
     */
    protected ACParentHesesPanelContainer getSickDates1() {
        // 2006/07/31 - 医師意見書
        // override用にGetterに変更
        /// Addition - begin [Masahiko Higuchi]
        if(sickDates1 == null){
            sickDates1 = new ACParentHesesPanelContainer();
        }
        return sickDates1;
    }
    
    /**
     * 発症年月日Hesesパネル2を返します。
     * @return
     */
    protected ACParentHesesPanelContainer getSickDates2() {
        // 2006/07/31 - 医師意見書
        // override用にGetterに変更
        /// Addition - begin [Masahiko Higuchi]
        if(sickDates2 == null){
            sickDates2 = new ACParentHesesPanelContainer();
        }
        return sickDates2;
    }
    
    /**
     * 発症年月日Hesesパネル3を返します。
     * @return
     */
    protected ACParentHesesPanelContainer getSickDates3() {
        // 2006/07/31 - 医師意見書
        // override用にGetterに変更
        /// Addition - begin [Masahiko Higuchi]
        if(sickDates3 == null){
            sickDates3 = new ACParentHesesPanelContainer();
        }
        return sickDates3;
    }
    
    // [ID:0000509][Masahiko Higuchi] 2009/06 add begin 画面調整に伴いサイズ指定を可能とする。
    /**
     * 薬剤名コンテナ１のレイアウトを返却します。
     * @return 薬剤名コンテナ１レイアウト
     */
    public VRLayout getSickMedicines1Layout() {
        if(sickMedicines1Layout == null) {
            sickMedicines1Layout = new VRLayout();
            sickMedicines1Layout.setHgap(0);
            sickMedicines1Layout.setVgap(0);
        }
        return sickMedicines1Layout;
    }
    
    /**
     * 薬剤名コンテナ１のレイアウトを設定します。
     * @param レイアウト
     */
    public void setSickMedicines1Layout(VRLayout sickMedicines1Layout) {
        this.sickMedicines1Layout = sickMedicines1Layout;
    }
    
    /**
     * 薬剤名コンテナ２のレイアウトを返却します。
     * @return 薬剤名コンテナ２レイアウト
     */
    public VRLayout getSickMedicines2Layout() {
        if(sickMedicines2Layout == null) {
            sickMedicines2Layout = new VRLayout();
            sickMedicines2Layout.setHgap(0);
            sickMedicines2Layout.setVgap(0);
        }
        return sickMedicines2Layout;
    }
    
    /**
     * 薬剤名コンテナ２のレイアウトを設定します。
     * @param レイアウト
     */
    public void setSickMedicines2Layout(VRLayout sickMedicines2Layout) {
        this.sickMedicines2Layout = sickMedicines2Layout;
    }
    
    /**
     * 薬剤名コンテナ３のレイアウトを返却します。
     * @return 薬剤名コンテナ３レイアウト
     */
    public VRLayout getSickMedicines3Layout() {
        if(sickMedicines3Layout == null) {
            sickMedicines3Layout = new VRLayout();
            sickMedicines3Layout.setHgap(0);
            sickMedicines3Layout.setVgap(0);
        }
        return sickMedicines3Layout;
    }
    
    /**
     * 薬剤名コンテナ３のレイアウトを設定します。
     * @param レイアウト
     */
    public void setSickMedicines3Layout(VRLayout sickMedicines3Layout) {
        this.sickMedicines3Layout = sickMedicines3Layout;
    }
    
    /**
     * 薬剤名コンテナ４のレイアウトを返却します。
     * @return 薬剤名コンテナ４レイアウト
     */
    public VRLayout getSickMedicines4Layout() {
        if(sickMedicines4Layout == null) {
            sickMedicines4Layout = new VRLayout();
            sickMedicines4Layout.setHgap(0);
            sickMedicines4Layout.setVgap(0);
        }
        return sickMedicines4Layout;
    }
    
    /**
     * 薬剤名コンテナ４のレイアウトを設定します。
     * @param レイアウト
     */
    public void setSickMedicines4Layout(VRLayout sickMedicines4Layout) {
        this.sickMedicines4Layout = sickMedicines4Layout;
    }
    
    /**
     * 薬剤名コンテナ５のレイアウトを返却します。
     * @return 薬剤名コンテナ５レイアウト
     */
    public VRLayout getSickMedicines5Layout() {
        if(sickMedicines5Layout == null) {
            sickMedicines5Layout = new VRLayout();
            sickMedicines5Layout.setHgap(0);
            sickMedicines5Layout.setVgap(0);
        }
        return sickMedicines5Layout;
    }
    
    /**
     * 薬剤名コンテナ５のレイアウトを設定します。
     * @param レイアウト
     */
    public void setSickMedicines5Layout(VRLayout sickMedicines5Layout) {
        this.sickMedicines5Layout = sickMedicines5Layout;
    }
    
    /**
     * 薬剤名コンテナ６のレイアウトを返却します。
     * @return 薬剤名コンテナ６レイアウト
     */
    public VRLayout getSickMedicines6Layout() {
        if(sickMedicines6Layout == null) {
            sickMedicines6Layout = new VRLayout();
            sickMedicines6Layout.setHgap(0);
            sickMedicines6Layout.setVgap(0);
        }
        return sickMedicines6Layout;
    }
    
    /**
     * 薬剤名コンテナ６のレイアウトを設定します。
     * @param レイアウト
     */
    public void setSickMedicines6Layout(VRLayout sickMedicines6Layout) {
        this.sickMedicines6Layout = sickMedicines6Layout;
    }
    
    /**
     * 薬剤名コンテナ７のレイアウトを返却します。
     * @return 薬剤名コンテナ７レイアウト
     */
    public VRLayout getSickMedicines7Layout() {
        if(sickMedicines7Layout == null) {
            sickMedicines7Layout = new VRLayout();
            sickMedicines7Layout.setHgap(0);
            sickMedicines7Layout.setVgap(0);
        }
        return sickMedicines7Layout;
    }
    
    /**
     * 薬剤名コンテナ７のレイアウトを設定します。
     * @param レイアウト
     */
    public void setSickMedicines7Layout(VRLayout sickMedicines7Layout) {
        this.sickMedicines7Layout = sickMedicines7Layout;
    }
    
    /**
     * 薬剤名コンテナ８のレイアウトを返却します。
     * @return 薬剤名コンテナ８レイアウト
     */
    public VRLayout getSickMedicines8Layout() {
        if(sickMedicines8Layout == null) {
            sickMedicines8Layout = new VRLayout();
            sickMedicines8Layout.setHgap(0);
            sickMedicines8Layout.setVgap(0);
        }
        return sickMedicines8Layout;
    }
    
    /**
     * 薬剤名コンテナ８のレイアウトを設定します。
     * @param レイアウト
     */
    public void setSickMedicines8Layout(VRLayout sickMedicines8Layout) {
        this.sickMedicines8Layout = sickMedicines8Layout;
    }
    // [ID:0000509][Masahiko Higuchi] 2009/06 add end
    
    //[ID:0000753][Shin Fujihara] 2012/09 add begin 2012年度対応 薬剤情報の行単位でのクリア機能
    public VRButton createMedicinesClearButton() {
        VRButton btn = new VRButton("一行クリア");
        Font bfont = btn.getFont();
        Font font = new Font(bfont.getName(), bfont.getStyle(), bfont.getSize() - 2);
        btn.setFont(font);
        btn.setMargin(new Insets(0, 0, 0, 0));
        
        //クリア実行
        btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ACMessageBox.show("この行の薬剤名をクリアしますか？",
						ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
						ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
					return;
				}
				
				VRButton me = (VRButton)e.getSource();
				Container p = me.getParent();
				Component comp = null;
		        
				for (int i = 0; i < p.getComponentCount(); i++) {
					comp = p.getComponent(i);
					
					if (comp instanceof IkenshoOptionComboBox) {
						((IkenshoOptionComboBox)comp).setText("");
						continue;
					}
					
					if (comp instanceof ACTextField) {
						((ACTextField)comp).setText("");
						continue;
					}
				}
			}
		});
        
        return btn;
    }
    //[ID:0000753][Shin Fujihara] 2012/09 add end 2012年度対応 薬剤情報の行単位でのクリア機能
    
    // [ID:0000752][Shin Fujihara] 2012/11 add begin 2012年度対応 薬剤名項目の入力文字数拡張
    private void sickMedicineNameSetting(IkenshoOptionComboBox combo) {
    	combo.setMaxLength(IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        combo.setPreferredSize(new Dimension(170, 19));
        combo.setOptionSize(170);
    }
    // [ID:0000752][Shin Fujihara] 2012/11 add end 2012年度対応 薬剤名項目の入力文字数拡張
    
}

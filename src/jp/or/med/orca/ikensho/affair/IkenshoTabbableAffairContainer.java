package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTabbedPane;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoAffairButtonBar;
import jp.or.med.orca.ikensho.component.IkenshoUnderlineFormatableLabel;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;


/** TODO <HEAD_IKENSYO> */
public class IkenshoTabbableAffairContainer extends IkenshoAffairContainer
    implements ACAffairable{
  protected String nowMode = IkenshoConstants.AFFAIR_MODE_INSERT;
  protected String patientNo;
  protected String edaNo;
  protected VRMap originalData;
  protected VRMap patientData;
  protected VRArrayList originalArray;
  protected boolean hasPatient;
  protected boolean hasOriginalDocument;
  protected ArrayList tabArray = new ArrayList();
  protected Component firstFocusComponent;
  protected HashMap teikeibunMap;
  //2009/01/16 [Tozo Tanaka] Add - begin
  private boolean sickMedicineCountAdjusted = false;
  //2009/01/16 [Tozo Tanaka] Add - end

  //GUIコンポーネント
  // 2009/01/13 [Mizuki Tsutsumi] : add begin / メインメニューへ戻るボタン
  //protected ACAffairButtonBar buttons = new ACAffairButtonBar();
  protected IkenshoAffairButtonBar buttons = new IkenshoAffairButtonBar();
  // 2009/01/13 [Mizuki Tsutsumi] : add end
  protected ACAffairButton update = new ACAffairButton();
  protected ACAffairButton print = new ACAffairButton();
  protected VRPanel editorInfo = new VRPanel();
  protected VRLabel editorDateCaption = new VRLabel();
  protected VRLabel editorNameCaption = new VRLabel();
  protected IkenshoUnderlineFormatableLabel editorDates = new IkenshoUnderlineFormatableLabel();
  protected IkenshoUnderlineFormatableLabel editorName = new IkenshoUnderlineFormatableLabel();
  protected VRLayout editorInfoLayout = new VRLayout();
  protected VRPanel tabPanel = new VRPanel();
  protected JTabbedPane tabs = new JTabbedPane();
  
  //Passiveキー
  protected static final ACPassiveKey PASSIVE_CHECK_KEY_PATIENT = new
      ACPassiveKey("PATIENT", new String[] {"PATIENT_NO"}
                        , new Format[] {null}
                        , "PATIENT_LAST_TIME", "LAST_TIME");
  protected static final ACPassiveKey PASSIVE_CHECK_KEY_COMMON = new
      ACPassiveKey("COMMON_IKN_SIS", new String[] {"PATIENT_NO", "EDA_NO",
                        "DOC_KBN"}
                        , new Format[] {null, null, null}
                        , "COMMON_LAST_TIME", "LAST_TIME");
  

  // 2006/06/22
  // スナップショット
  // Addition - begin [Masahiko Higuchi]
  protected IkenshoIkenshoSimpleSnapshot simpleSnap = new IkenshoIkenshoSimpleSnapshot();
  // Addition - end

  // [ID:0000438][Tozo TANAKA] 2009/06/08 add begin 【主治医医見書・医師医見書】薬剤名テキストの追加
  public static final int CAN_UPDATE_CHECK_STATUS_UNKNOWN = 0;
  public static final int CAN_UPDATE_CHECK_STATUS_PRINT = 1;
  public static final int CAN_UPDATE_CHECK_STATUS_UPDATE = 2;
  public static final int CAN_UPDATE_CHECK_STATUS_INSERT = 3;
  private int canUpdateCheckStatus = CAN_UPDATE_CHECK_STATUS_UNKNOWN;
  public int getCanUpdateCheckStatus(){
      return canUpdateCheckStatus;
  }
  protected void setCanUpdateCheckStatus(int canUpdateCheckStatus){
      this.canUpdateCheckStatus = canUpdateCheckStatus;
  }
  // [ID:0000438][Tozo TANAKA] 2009/06/08 add end 【主治医医見書・医師医見書】薬剤名テキストの追加

  /**
   * overrideして固有のパッシブチェック予約を定義します。
   * @throws ParseException 処理例外
   */
  protected void doReservedPassiveCustom() throws ParseException {
    throw new RuntimeException(
        "明示的なOver rideが必要なdoReservedPassiveCustumメソッドが呼ばれました");
  }

  /**
   * overrideして固有文書の検索処理を定義します。
   * @param dbm DBManager
   * @throws Exception 処理例外
   */
  protected void doSelectCustomDocument(IkenshoFirebirdDBManager dbm) throws Exception {
    throw new RuntimeException(
        "明示的なOver rideが必要なdoSelectOriginalDocumentメソッドが呼ばれました");
  }

  /**
   * overrideして固有文書の初期設定処理を定義します。
   * @param dbm DBManager
   */
  protected void doSelectDefaultCustomDocument(IkenshoFirebirdDBManager dbm){
    throw new RuntimeException(
      "明示的なOver rideが必要なdoDefaultSelectCustumDocumentメソッドが呼ばれました");
  }

  /**
   * overrideして固有のパッシブチェック用に再度LAST_TIMEを取り直す処理を定義します。
   * @param dbm DBManager
   * @param data 追加先マップ
   * @throws Exception 処理例外
   */
  protected void reselectForPassiveCustom(IkenshoFirebirdDBManager dbm, VRMap data) throws Exception {
    throw new RuntimeException(
        "明示的なOver rideが必要なreselectForPassiveCustumメソッドが呼ばれました");
  }

  /**
   * overrideして印刷ダイアログ表示処理を定義します。
   * @throws Exception 処理例外
   * @return 印刷を実行したか
   */
  protected boolean showPrintDialogCustom()throws Exception{
    throw new RuntimeException(
        "明示的なOver rideが必要なshowPrintDialogCustumメソッドが呼ばれました");
  }

  /**
   * overrideして固有文書テーブル名を返す処理を定義します。
   * @return 固有文書テーブル名
   */
  protected String getCustomDocumentTableName(){
    throw new RuntimeException(
      "明示的なOver rideが必要なgetCustumDocumentTableNameメソッドが呼ばれました");
  }

  /**
   * overrideして対比される固有文書テーブル名を返す処理を定義します。
   * @return 対比される固有文書テーブル名
   */
  protected String getAnotherDocumentTableName(){
    throw new RuntimeException(
      "明示的なOver rideが必要なgetAnotherDocumentTableNameメソッドが呼ばれました");
  }

  /**
   * overrideして固有文書情報を挿入する処理を定義します。
   * @param dbm DBManager
   * @throws Exception 処理例外
   */
  protected void doInsertCustomDocument(IkenshoFirebirdDBManager dbm) throws Exception {
    throw new RuntimeException(
      "明示的なOver rideが必要なdoInsertOriginalDocumentメソッドが呼ばれました");
  }

  /**
   * overrideして固有文書情報を更新する処理を定義します。
   * @param dbm DBManager
   * @throws Exception 処理例外
   */
  protected void doUpdateCustomDocument(IkenshoFirebirdDBManager dbm) throws Exception {
    throw new RuntimeException(
      "明示的なOver rideが必要なdoUpdateOriginalDocumentメソッドが呼ばれました");
  }

  /**
   * overrideして固有文書情報の直前値を設定する処理を定義します。
   * @param dbm DBManager
   * @throws Exception 処理例外
   */
  protected void doSelectBeforeCustomDocument(IkenshoFirebirdDBManager dbm) throws Exception {
    throw new RuntimeException(
      "明示的なOver rideが必要なdoSelectBeforeOriginalDocumentメソッドが呼ばれました");
  }

  /**
   * overrideして固有文書情報を挿入するためのパッシブタスクを追加する処理を定義します。
   */
  protected void addPassiveInsertTaskCustom(){
    throw new RuntimeException(
      "明示的なOver rideが必要なaddPassiveInsertTaskCustumメソッドが呼ばれました");
  }

  /**
   * overrideして固有文書情報を更新するためのパッシブタスクを追加する処理を定義します。
   */
  protected void addPassiveUpdateTaskCustom(){
    throw new RuntimeException(
      "明示的なOver rideが必要なaddPassiveUpdateTaskCustumメソッドが呼ばれました");
  }

  /**
   * overrideして固有文書区分を返す処理を定義します。
   * @return 固有文書区分
   */
  protected String getCustomDocumentType(){
    throw new RuntimeException(
      "明示的なOver rideが必要なgetCustumDocumentTypeメソッドが呼ばれました");
  }

  /**
   * overrideして対比される固有文書区分を返す処理を定義します。
   * @return 対比される固有文書区分
   */
  protected String getAnotherDocumentType(){
    throw new RuntimeException(
      "明示的なOver rideが必要なgetAnotherDocumentTypeメソッドが呼ばれました");
  }

  /**
   * 必要であればoverrideしてスナップショット対象から除外するコンポーネント集合返します。
   * @return スナップショット対象から除外するコンポーネント集合
   */
  protected ArrayList getCustomSnapshotExclusions(){
    return null;
  }

  public Component getFirstFocusComponent() {
    return firstFocusComponent;
  }

  public ACAffairButtonBar getButtonBar() {
    return buttons;
  }

  /**
   * 現在の処理モードを返します。
   * @return 処理モード
   */
  public String getNowMode() {
    return nowMode;
  }

  /**
   * 現在の処理モードを設定します。
   * @param nowMode 処理モード
   */
  protected void setNowMode(String nowMode) {
    this.nowMode = nowMode;

    if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(nowMode)) {
      removeUpdateTrigger(update);
      addInsertTrigger(update);
      update.setText("登録(S)");
      update.setToolTipText("現在の内容を登録します。");
    }
    else if (IkenshoConstants.AFFAIR_MODE_UPDATE.equals(nowMode)) {
      removeInsertTrigger(update);
      addUpdateTrigger(update);
      update.setText("更新(S)");
      update.setToolTipText("現在の内容を更新します。");

    }
  }

  /**
   * 患者番号を返します。
   * @return 患者番号
   */
  public String getPatientNo() {
    return patientNo;
  }

  /**
   * 患者番号を設定します。
   * @param patientNo 患者番号
   */
  protected void setPatientNo(String patientNo) {
    this.patientNo = patientNo;
  }

  /**
   * 固有文書枝番号を返します。
   * @return 固有文書枝番号
   */
  public String getEdaNo() {
    return edaNo;
  }

  /**
   * 固有文書枝番号を設定します。
   * @param edaNo 固有文書枝番号
   */
  protected void setEdaNo(String edaNo) {
    this.edaNo = edaNo;
  }


  /**
   * 定型文を一括して取得します。
   * @param dbm DBManager
   * @throws SQLException SQL例外
   * @throws ParseException 解析例外
   */
  protected void poolTeikeibun(IkenshoFirebirdDBManager dbm) throws SQLException,
      ParseException {
    teikeibunMap = new HashMap();
    IkenshoCommon.getMasterTable(dbm, teikeibunMap, "TEIKEIBUN", "TEIKEIBUN", "TKB_KBN", "TKB_CD");
  }

  /**
   * プールした定型文をコンボに設定します。
   * @param combo コンボ
   * @param code 定型文コード
   */
  protected void applyPoolTeikeibun(JComboBox combo, int code){
    if(teikeibunMap!=null){
      Object obj = teikeibunMap.get(new Integer(code));
      if (obj instanceof VRHashMapArrayToConstKeyArrayAdapter) {
        IkenshoCommon.applyComboModel(combo,
                                      (VRHashMapArrayToConstKeyArrayAdapter)
                                      obj);
      }else{
          combo.setModel(new ACComboBoxModelAdapter());
      }
    }
  }

  public void initAffair(ACAffairInfo affair) throws Exception {
    IkenshoSnapshot.getInstance().setRootContainer(tabPanel);
    originalData = fullCreateSource();

    addPrintTrigger(print);

    //次画面からの戻りであるか
    VRMap previewData = null;
    VRMap params = affair.getParameters();
    if (VRBindPathParser.has("PREV_DATA", params)) {
      //画面遷移キャッシュデータがある場合は、パラメタを置き換える
      params = (VRMap) VRBindPathParser.get("PREV_DATA", params);
      previewData = params;
    }

    setNowMode(String.valueOf(VRBindPathParser.get("AFFAIR_MODE", params)));

    hasPatient = VRBindPathParser.has("PATIENT_NO", params);

    IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
    //患者番号
    if (hasPatient) {
      setPatientNo(String.valueOf(VRBindPathParser.get("PATIENT_NO",
          params)));
      //意見書枝番
      if (VRBindPathParser.has("EDA_NO", params)) {
        setEdaNo(String.valueOf(VRBindPathParser.get("EDA_NO", params)));
      }
    }
    if(params instanceof Map){
      originalData.putAll(params);
    }

    poolTeikeibun(dbm);

    //DBへのアクセスを必要とする初期化
    Iterator it = tabArray.iterator();
    while (it.hasNext()) {
      IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer) it.next();
      tab.setMasterAffair(this);
      tab.setSource(originalData);
      tab.initDBCopmponent(dbm);
    }
    dbm.finalize();

    doSelect();

    if (previewData != null) {
      originalData.putAll(previewData);
      fullBindSource();
    }

    if (VRBindPathParser.has("TAB", params)) {
      //タブ指定がある→タブ切り替え
      tabs.setSelectedIndex( ( (Integer) VRBindPathParser.get("TAB", params)).
                            intValue());
      IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer) tabs.
          getSelectedComponent();
      Component cmp = tab.getPreviewFocusComponent();
      if (cmp != null) {
        firstFocusComponent = cmp;
      }
    }

    if(firstFocusComponent==null){
      //フォーカス先コンポーネントが指定されていなければ、最初のタブのフォーカス先コンポーネントを採用する
      firstFocusComponent = ((IkenshoTabbableChildAffairContainer) tabs.
          getSelectedComponent()).getFirstFocusComponent();
    }
    
    //2009/01/16 [Tozo Tanaka] Add - begin
    if((previewData == null) && sickMedicineCountAdjusted){
        //新規作成モードでかつ既存文書が存在する場合で、次画面からの戻りではなく、
        //直前の文書に薬剤7または薬剤8が入力されており、かつ薬剤名の表示個数が6個設定の場合は削られる旨を警告
        showSickMedicineCountAdjustedMessage();
    }
    //2009/01/16 [Tozo Tanaka] Add - end

  }

  //2009/01/16 [Tozo Tanaka] Add - begin
  protected void checkSickMedicineCountAdjusted(VRMap source) throws Exception{
      //直前の文書に薬剤7または薬剤8が入力されており、かつ薬剤名の表示個数が6個設定の場合は削られる旨を警告
      if (!(
              IkenshoCommon.isNullText(VRBindPathParser.get("MEDICINE7", source)) && 
              IkenshoCommon.isNullText(VRBindPathParser.get("MEDICINE8", source)) &&
              IkenshoCommon.isNullText(VRBindPathParser.get("DOSAGE7", source)) && 
              IkenshoCommon.isNullText(VRBindPathParser.get("DOSAGE8", source)) &&
              IkenshoCommon.isNullText(VRBindPathParser.get("UNIT7", source)) && 
              IkenshoCommon.isNullText(VRBindPathParser.get("UNIT8", source)) &&
              IkenshoCommon.isNullText(VRBindPathParser.get("USAGE7", source)) && 
              IkenshoCommon.isNullText(VRBindPathParser.get("USAGE8", source)) 
              )) {
          //薬剤7か薬剤8が入力されている場合
          if (getMedicineViewCount() == 6) {
              //薬剤名の表示個数が6個設定の場合
              VRBindPathParser.set("MEDICINE7", source, "");
              VRBindPathParser.set("MEDICINE8", source, "");
              VRBindPathParser.set("DOSAGE7", source, "");
              VRBindPathParser.set("DOSAGE8", source, "");
              VRBindPathParser.set("UNIT7", source, "");
              VRBindPathParser.set("UNIT8", source, "");
              VRBindPathParser.set("USAGE7", source, "");
              VRBindPathParser.set("USAGE8", source, "");
              sickMedicineCountAdjusted = true;
          }
      }
  }
  
  protected int getMedicineViewCount() {
      // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//      try {
//          if (ACFrame.getInstance().hasProperty(
//                  "DocumentSetting/MedicineViewCount")
//                  && ACCastUtilities.toInt(ACFrame.getInstance().getProperty(
//                          "DocumentSetting/MedicineViewCount"), 6) == 8) {
//              return 8;
//          }
//      } catch (Exception e) {
//      }      
//    return 6;
    return 8;
    // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end 【主治医医見書・医師医見書】薬剤名テキストの追加
}
  
  protected void showSickMedicineCountAdjustedMessage(){
      ACMessageBox
      .show(
//              "最新の文書に入力された薬剤7および薬剤8の情報は転記されません。"
              "最新の文書に薬剤名が7個以上入力されています。"
              + ACConstants.LINE_SEPARATOR
              + "7個以上の薬剤を編集する場合は、"
              + ACConstants.LINE_SEPARATOR
              + "[メインメニュー]-[設定(S)]-[その他の設定(O)]より、薬剤名の表示個数を8個に変更してください。");
  }
  //2009/01/16 [Tozo Tanaka] Add - end

  /**
   * パッシブチェック用に再度LAST_TIMEを取り直します。
   * @param dbm DBManager
   * @throws Exception 処理例外
   */
  protected void reselectForPassive(IkenshoFirebirdDBManager dbm) throws Exception {
    StringBuffer sb;
    VRArrayList array;
    VRMap data = new VRHashMap();

    //患者情報
    sb = new StringBuffer();
    sb.append("SELECT");
    sb.append(" LAST_TIME AS PATIENT_LAST_TIME");
    sb.append(" FROM");
    sb.append(" PATIENT");
    sb.append(" WHERE");
    sb.append(" (PATIENT.PATIENT_NO=");
    sb.append(getPatientNo());
    sb.append(")");
    array = (VRArrayList) dbm.executeQuery(sb.toString());
    if (array.getDataSize() > 0) {
      data.putAll( (VRMap) array.getData());
    }

    //共通文書
    sb = new StringBuffer();
    sb.append("SELECT");
    sb.append(" DOC_KBN,");
    sb.append(" LAST_TIME AS COMMON_LAST_TIME");
    sb.append(" FROM");
    sb.append(" COMMON_IKN_SIS");
    sb.append(" WHERE");
    sb.append(" (COMMON_IKN_SIS.PATIENT_NO=");
    sb.append(getPatientNo());
    sb.append(")");
    sb.append("AND(COMMON_IKN_SIS.EDA_NO=");
    sb.append(getEdaNo());
    sb.append(")");
    sb.append("AND(COMMON_IKN_SIS.DOC_KBN=");
    sb.append(getCustomDocumentType());
    sb.append(")");
    array = (VRArrayList) dbm.executeQuery(sb.toString());
    if (array.getDataSize() > 0) {
      data.putAll( (VRMap) array.getData());
    }

    //固有部分の再取得
    reselectForPassiveCustom(dbm, data);
    originalData.putAll(data);


    //パッシブチェック予約
    doReservedPassive();

  }

  /**
   * 直前の共通文書の検索処理を行います。
   * @param dbm DBManager
   * @throws Exception 処理例外
   */
  protected void doSelectBeforeCommonDocument(IkenshoFirebirdDBManager dbm) throws Exception {
    StringBuffer sb = new StringBuffer();
    VRArrayList array;

    Date custumDate = null;
    Date anotherDate = null;
    
    sb.append("SELECT");
    sb.append(" MAX(CREATE_DT) AS CREATE_DT");
    sb.append(" FROM ");
    sb.append(getCustomDocumentTableName());
    sb.append(" WHERE");
    sb.append(" (PATIENT_NO=");
    sb.append(getPatientNo());
    sb.append(")");
    array = (VRArrayList) dbm.executeQuery(sb.toString());
    if (array.getDataSize() > 0) {
      Object obj = VRBindPathParser.get("CREATE_DT", (VRMap)array.getData());
      if(obj instanceof Date){
        custumDate = (Date)obj;
      }
    }
    sb = new StringBuffer();
    sb.append("SELECT");
    sb.append(" MAX(CREATE_DT) AS CREATE_DT");
    sb.append(" FROM ");
    sb.append(getAnotherDocumentTableName());
    sb.append(" WHERE");
    sb.append(" (PATIENT_NO=");
    sb.append(getPatientNo());
    sb.append(")");
    array = (VRArrayList) dbm.executeQuery(sb.toString());
    if (array.getDataSize() > 0) {
      Object obj = VRBindPathParser.get("CREATE_DT", (VRMap)array.getData());
      if(obj instanceof Date){
        anotherDate = (Date)obj;
      }
    }

    String docType;
    if(custumDate != null){
      if(anotherDate!=null){
        if(custumDate.compareTo(anotherDate)<0){
          docType = getAnotherDocumentType();
        }else{
          docType = getCustomDocumentType();
        }
      }else{
        docType = getCustomDocumentType();
      }
    }else if(anotherDate!=null){
      docType = getAnotherDocumentType();
    }else{
      docType = getCustomDocumentType();
    }


    sb = new StringBuffer();
    sb.append("SELECT");
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能
//    sb.append(" *");
    sb.append(" PATIENT_NM");
    sb.append(",PATIENT_KN");
    sb.append(",SEX");
    sb.append(",BIRTHDAY");
    sb.append(",AGE");
    sb.append(",POST_CD");
    sb.append(",ADDRESS");
    sb.append(",TEL1");
    sb.append(",TEL2");
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能
    sb.append(" FROM");
    sb.append(" COMMON_IKN_SIS");
    sb.append(" WHERE");
    sb.append(" (COMMON_IKN_SIS.PATIENT_NO=");
    sb.append(getPatientNo());
    sb.append(")");
    sb.append("AND(COMMON_IKN_SIS.DOC_KBN=");
    sb.append(docType);
    sb.append(")");
    sb.append(" ORDER BY");
    sb.append(" EDA_NO DESC");
    array = (VRArrayList) dbm.executeQuery(sb.toString());
    if (array.getDataSize() > 0) {

        VRMap data = (VRMap) array.getData();
      data.setData("COMMON_LAST_TIME",
                   VRBindPathParser.get("LAST_TIME", data));

//    [ID:0000514][Tozo TANAKA] 2009/09/09 remove begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能
//      String[] keeps = new String[] {
//          "DR_NM", "MI_NM", "MI_POST_CD", "MI_ADDRESS", "MI_TEL1", "MI_TEL2",
//          "MI_FAX1", "MI_FAX2", "MI_CEL_TEL1", "MI_CEL_TEL2", };
//      int end = keeps.length;
//      for(int i=0; i<end; i++){
//        data.setData(keeps[i], originalData.getData(keeps[i]));
//      }
//    [ID:0000514][Tozo TANAKA] 2009/09/09 remove end 【2009年度対応：訪問看護指示書】特別指示書の管理機能

      originalData.putAll(data);
    }
    

//  [ID:0000514][Tozo TANAKA] 2009/09/09 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能
    //特別訪問看護指示書以外の最新文書から取得
    custumDate = null;
    anotherDate = null;

    sb = new StringBuffer();
    sb.append("SELECT");
    sb.append(" MAX(CREATE_DT) AS CREATE_DT");
    sb.append(" FROM ");
    sb.append(getCustomDocumentTableName());
    sb.append(" WHERE");
    sb.append(" (PATIENT_NO=");
    sb.append(getPatientNo());
    sb.append(")");
    if("SIS_ORIGIN".equals(getCustomDocumentTableName())){
        //指示書の場合は特別訪問看護指示書(FORMAT_KBN=1)を除外する
        sb.append("AND(FORMAT_KBN != 1)");
    }
    array = (VRArrayList) dbm.executeQuery(sb.toString());
    if (array.getDataSize() > 0) {
      Object obj = VRBindPathParser.get("CREATE_DT", (VRMap)array.getData());
      if(obj instanceof Date){
        custumDate = (Date)obj;
      }
    }
    sb = new StringBuffer();
    sb.append("SELECT");
    sb.append(" MAX(CREATE_DT) AS CREATE_DT");
    sb.append(" FROM ");
    sb.append(getAnotherDocumentTableName());
    sb.append(" WHERE");
    sb.append(" (PATIENT_NO=");
    sb.append(getPatientNo());
    sb.append(")");
    if("SIS_ORIGIN".equals(getAnotherDocumentTableName())){
        //指示書の場合は特別訪問看護指示書(FORMAT_KBN=1)を除外する
        sb.append("AND(FORMAT_KBN != 1)");
    }
    array = (VRArrayList) dbm.executeQuery(sb.toString());
    if (array.getDataSize() > 0) {
      Object obj = VRBindPathParser.get("CREATE_DT", (VRMap)array.getData());
      if(obj instanceof Date){
        anotherDate = (Date)obj;
      }
    }

    if(custumDate != null){
      if(anotherDate!=null){
        if(custumDate.compareTo(anotherDate)<0){
          docType = getAnotherDocumentType();
        }else{
          docType = getCustomDocumentType();
        }
      }else{
        docType = getCustomDocumentType();
      }
    }else if(anotherDate!=null){
      docType = getAnotherDocumentType();
    }else{
      docType = getCustomDocumentType();
    }        
    
    String joinTable = getCustomDocumentTableName();
    if(docType.equals(getAnotherDocumentType())){
        joinTable = getAnotherDocumentTableName();
    }
    
    sb = new StringBuffer();
    sb.append("SELECT");
    sb.append(" COMMON_IKN_SIS.*");
    sb.append(" FROM");
    sb.append(" COMMON_IKN_SIS");
    sb.append(" JOIN ");
    sb.append(joinTable);
    sb.append(" ON");
    sb.append(" (COMMON_IKN_SIS.PATIENT_NO = ");
    sb.append(joinTable);
    sb.append(".PATIENT_NO)");
    sb.append("AND(COMMON_IKN_SIS.EDA_NO = ");
    sb.append(joinTable);
    sb.append(".EDA_NO)");
    
    sb.append("AND(");
    sb.append("(COMMON_IKN_SIS.DOC_KBN != 2)");
    sb.append("OR");
    sb.append("(");
    sb.append("(COMMON_IKN_SIS.DOC_KBN = 2)");
    sb.append("AND(");
    sb.append(joinTable);
    sb.append(".FORMAT_KBN != 1)");
    sb.append(")");
    sb.append(")");
    
    sb.append(" WHERE");
    sb.append(" (COMMON_IKN_SIS.PATIENT_NO=");
    sb.append(getPatientNo());
    sb.append(")");
    sb.append("AND(COMMON_IKN_SIS.DOC_KBN=");
    sb.append(docType);
    sb.append(")");
    sb.append(" ORDER BY");
    sb.append(" COMMON_IKN_SIS.EDA_NO DESC");
    array = (VRArrayList) dbm.executeQuery(sb.toString());
    if (array.getDataSize() > 0) {
        VRMap data = (VRMap) array.getData();

        String[] keeps = new String[] { "DR_NM", "MI_NM", "MI_POST_CD",
                    "MI_ADDRESS", "MI_TEL1", "MI_TEL2", "MI_FAX1", "MI_FAX2",
                    "MI_CEL_TEL1", "MI_CEL_TEL2", " PATIENT_NM", "PATIENT_KN",
                    "SEX", "BIRTHDAY", "AGE", "POST_CD", "ADDRESS", "TEL1",
                    "TEL2", };
        int end = keeps.length;
        for (int i = 0; i < end; i++) {
            data.setData(keeps[i], originalData.getData(keeps[i]));
        }

        originalData.putAll(data);
    }
    
//  [ID:0000514][Tozo TANAKA] 2009/09/09 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能
        
        
  }

  /**
   * 共通文書の検索処理を行います。
   * @param dbm DBManager
   * @throws Exception 処理例外
   */
  protected void doSelectCommonDocument(IkenshoFirebirdDBManager dbm) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT");
    sb.append(" *");
    sb.append(" FROM");
    sb.append(" COMMON_IKN_SIS");
    sb.append(" WHERE");
    sb.append(" (COMMON_IKN_SIS.PATIENT_NO=");
    sb.append(getPatientNo());
    sb.append(")");
    sb.append("AND(COMMON_IKN_SIS.EDA_NO=");
    sb.append(getEdaNo());
    sb.append(")");
    sb.append("AND(COMMON_IKN_SIS.DOC_KBN=");
    sb.append(getCustomDocumentType());
    sb.append(")");
    VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
    if (array.getDataSize() > 0) {
        VRMap data = (VRMap) array.getData();
      data.setData("COMMON_LAST_TIME",
                   VRBindPathParser.get("LAST_TIME", data));
      originalData.putAll(data);
    }
  }

  /**
   * 患者情報の検索処理を行います。
   * @param dbm DBManager
   * @throws Exception 処理例外
   */
  protected void doSelectPatient(IkenshoFirebirdDBManager dbm) throws Exception {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT");
    sb.append(" *");
    sb.append(" FROM");
    sb.append(" PATIENT");
    sb.append(" WHERE");
    sb.append(" (PATIENT.PATIENT_NO=");
    sb.append(getPatientNo());
    sb.append(")");
    VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
    if (array.getDataSize() > 0) {
      patientData = (VRMap) array.getData();
      patientData.setData("PATIENT_LAST_TIME",
                   VRBindPathParser.get("LAST_TIME", patientData));
      originalData.putAll(patientData);
    }else{
      patientData = null;
    }
  }

  // 2006/09/08[Tozo Tanaka] : add begin
    private boolean nowSelecting = false;

    /**
     * 検索処理中であるかを返します。
     * 
     * @return 検索処理中であるか
     */
    public boolean isNowSelecting() {
        return nowSelecting;
    }

    /**
     * 検索処理中であるかを設定します。
     * 
     * @param nowSelecting 検索処理中であるか
     */
    public void setNowSelecting(boolean nowSelecting) {
        this.nowSelecting = nowSelecting;
    }

    /**
     * 更新モードで検索処理中であるかを返します。
     * 
     * @return 更新モードで検索処理中であるか
     */
    public boolean isNowSelectingByUpdate() {
        return isNowSelecting()
                && IkenshoConstants.AFFAIR_MODE_UPDATE.equals(getNowMode());
    }
    //2006/09/08[Tozo Tanaka] : add end

  /**
   * 検索処理を行います。
   * @throws Exception 処理例外
   */
  protected void doSelect() throws Exception {
      // 2006/09/08[Tozo Tanaka] : add begin
        setNowSelecting(true);
        try {
            // 2006/09/08[Tozo Tanaka] : add end

    IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
    hasOriginalDocument = false;

    //初期値を収集して初期化
    if (IkenshoConstants.AFFAIR_MODE_UPDATE.equals(getNowMode())) {
      //更新モードの場合、患者番号と枝番をキーにデータを集める
        
      doSelectPatient(dbm);

      //固有文書
      doSelectCustomDocument(dbm);

      //意見書・指示書共通
      doSelectCommonDocument(dbm);
      
    }
    else if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(getNowMode())) {
      //登録モードの場合、デフォルト値を収集する

      //パッシブチェック用に文書区分を作成
      originalData.setData("DOC_KBN", new Integer(getCustomDocumentType()));

      //患者情報
      if (hasPatient) {
        doSelectPatient(dbm);

        //直前の固有文書
        doSelectBeforeCustomDocument(dbm);

        //直前の意見書・指示書共通
        doSelectBeforeCommonDocument(dbm);

        
        if(patientData!=null){
          originalData.putAll(patientData);
        }

        if(VRBindPathParser.has("OVERRIDE_PATIENT", originalData)){
          Object obj = VRBindPathParser.get("OVERRIDE_PATIENT", originalData);
          if(obj instanceof Map){
            originalData.putAll( (Map)obj);
          }
        }
      
        // 2009/01/16[Tozo Tanaka] : add begin
        checkSickMedicineCountAdjusted(originalData);
        // 2009/01/16[Tozo Tanaka] : add end
      }else{
        //初期設定
        doSelectDefaultCustomDocument(dbm);
      }

    }

    doBeforeBindOnSelected();

    //適用
    originalArray = new VRArrayList();
    if (originalData.size() > 0) {
      fullSetSource(originalData);
      fullBindSource();

      originalArray.addData(originalData);
    }

    //パッシブチェック予約
    doReservedPassive();
    
    // 2006/09/08[Tozo Tanaka] : add begin
        } finally {
            setNowSelecting(false);
        }
        // 2006/09/08[Tozo Tanaka] : add end

  }

  /**
   * パッシブチェック予約を行います。
   * @throws ParseException 処理例外
   */
  protected void doReservedPassive() throws ParseException {
    clearReservedPassive();

    reservedPassive(PASSIVE_CHECK_KEY_PATIENT, originalArray);
    reservedPassive(PASSIVE_CHECK_KEY_COMMON, originalArray);

    doReservedPassiveCustom();

    IkenshoSnapshot.getInstance().setExclusions(getCustomSnapshotExclusions());
    IkenshoSnapshot.getInstance().snapshot();
    
    // 2006/06/22
    // スナップショット
    // Addition - begin [Masahiko Higuchi]
    try{
        simpleSnap.simpleSnapshot();
//    NCSnapshot.getInstance().snapshot(getCustumSnapshotExclusions());
    }catch(Exception e){ 
        
    }
    // Addition - end
  }

  public boolean canBack(VRMap parameters) throws Exception {
    boolean result = true;
    // 2006/06/22
    // スナップショット
    // Replace - begin [Masahiko Higuchi]
    if (IkenshoSnapshot.getInstance().isModified()||simpleSnap.simpleIsModefield()) {
    // Replace - end        
      if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(getNowMode())) {
        //追加モードの場合
        switch (ACMessageBox.showYesNoCancel("登録内容を保存しますか？", "登録して戻る(S)",
                                                  'S',
                                                  "破棄して戻る(R)", 'R')) {
          case ACMessageBox.RESULT_YES:

            //登録して戻る
            result = doInsert();
            break;
          case ACMessageBox.RESULT_CANCEL:
            result = false;
            break;
        }
      }
      else if (IkenshoConstants.AFFAIR_MODE_UPDATE.equals(getNowMode())) {
        //更新モードの場合
        switch (ACMessageBox.showYesNoCancel("変更されています。保存しますか？",
                                                  "更新して戻る(S)", 'S',
                                                  "破棄して戻る(R)", 'R')) {
          case ACMessageBox.RESULT_YES:

            //更新して戻る
            result = doUpdate();
            break;
          case ACMessageBox.RESULT_CANCEL:
            result = false;
            break;
        }
      }

    }

    if (hasPatient) {
      parameters.setData("PATIENT_NO", new Integer(getPatientNo()));
      parameters.setData("AFFAIR_MODE",
                         IkenshoConstants.AFFAIR_MODE_UPDATE);
    }
    else {
      parameters.setData("AFFAIR_MODE",
                         IkenshoConstants.AFFAIR_MODE_INSERT);
    }

    return result;
  }

  protected void insertActionPerformed(ActionEvent e) throws Exception {
    if (doInsert()) {
      setNowMode(IkenshoConstants.AFFAIR_MODE_UPDATE);
    }
  }

  protected void updateActionPerformed(ActionEvent e) throws Exception {
    doUpdate();
  }

  /**
   * Apply前の段階で更新処理可能かを返します。
   * @throws Exception 処理例外
   * @return 更新処理可能か
   */
  protected boolean canControlUpdate() throws Exception {
    //エラーチェック
    Iterator it = tabArray.iterator();
    while (it.hasNext()) {
      IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer) it.next();
      if (!tab.noControlError()) {
        tabs.setSelectedComponent(tab);
        return false;
      }
    }
    //警告チェック
    it = tabArray.iterator();
    while (it.hasNext()) {
      IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer) it.next();
      if (!tab.noControlWarning()) {
        tabs.setSelectedComponent(tab);
        return false;
      }
    }

    return true;
  }

  /**
   * Apply後の段階で更新処理可能かを返します。
   * @throws Exception 処理例外
   * @return 更新処理可能か
   */
  protected boolean canDataUpdate() throws Exception {
    //エラーチェック
    if (!hasPatient) {
      //同姓同名チェック
      if (IkenshoCommon.hasSameName(originalData)) {
        if (ACMessageBox.show("同一と思われる患者が既に登録されています。\n登録しますか？",
                                   ACMessageBox.BUTTON_OKCANCEL,
                                   ACMessageBox.ICON_QUESTION,
                                   ACMessageBox.FOCUS_CANCEL) !=
            ACMessageBox.RESULT_OK) {
          return false;
        }
      }
    }

    Iterator it = tabArray.iterator();
    while (it.hasNext()) {
      IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer) it.next();
      if (!tab.canDataUpdate()) {
        tabs.setSelectedComponent(tab);
        return false;
      }
    }
    return true;
  }


  public boolean canClose() throws Exception {
    if (IkenshoSnapshot.getInstance().isModified()) {
      return ACMessageBox.show("更新された内容は破棄されます。\n終了してもよろしいですか？",
                                    ACMessageBox.BUTTON_OKCANCEL,
                                    ACMessageBox.ICON_QUESTION,
                                    ACMessageBox.FOCUS_CANCEL) ==
          ACMessageBox.RESULT_OK;
    }
    return true;
  }

  protected void printActionPerformed(ActionEvent e) throws Exception {
    //エラーチェック
    // [ID:0000438][Tozo TANAKA] 2009/06/08 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//    if (!canControlUpdate()) {
//      return;
//    }
    setCanUpdateCheckStatus(CAN_UPDATE_CHECK_STATUS_PRINT);
    try{
        if (!canControlUpdate()) {
          return;
        }
    }finally{
        setCanUpdateCheckStatus(CAN_UPDATE_CHECK_STATUS_UNKNOWN);
    }
    // [ID:0000438][Tozo TANAKA] 2009/06/08 replace end 【主治医医見書・医師医見書】薬剤名テキストの追加

    fullApplySource();

    //エラーチェック
    if (!canDataUpdate()) {
      return;
    }

    //印刷設定ダイアログ
    showPrintDialogCustom();

  }

  /**
   * 次の枝番を取得します。
   * @param dbm DBManager
   * @throws ParseException 解析例外
   * @throws SQLException SQL例外
   * @return 次の枝番
   */
  protected int getNextEdaNo(IkenshoFirebirdDBManager dbm) throws ParseException,
      SQLException {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT");
    sb.append(" MAX(");
    sb.append(getCustomDocumentTableName());
    sb.append(".EDA_NO) AS EDA_NO");
    sb.append(" FROM ");
    sb.append(getCustomDocumentTableName());
    sb.append(" WHERE");
    sb.append(" (");
    sb.append(getCustomDocumentTableName());
    sb.append(".PATIENT_NO=");
    sb.append(getPatientNo());
    sb.append(")");

    VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
    if (array.getDataSize() == 0) {
      return 1;
    }
    Object obj = VRBindPathParser.get("EDA_NO", (VRMap) array.getData());
    if (obj instanceof Integer) {
      return ( (Integer) obj).intValue() + 1;
    }
    else {
      return 1;
    }
  }


  /**
   * 追加処理を行います。
   * @throws Exception 処理例外
   * @return boolean 追加処理に成功したか
   */
  protected boolean doInsert() throws Exception {

    //エラーチェック
      // [ID:0000438][Tozo TANAKA] 2009/06/08 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//      if (!canControlUpdate()) {
//          return false;
//        }
//        if(!canUpdateCustom()){
//          return false;
//        }
    setCanUpdateCheckStatus(CAN_UPDATE_CHECK_STATUS_INSERT);
    try{
        if (!canControlUpdate()) {
            return false;
        }
        if(!canUpdateCustom()){
            return false;
        }
    }finally{
        setCanUpdateCheckStatus(CAN_UPDATE_CHECK_STATUS_UNKNOWN);
    }
    // [ID:0000438][Tozo TANAKA] 2009/06/08 replace end 【主治医医見書・医師医見書】薬剤名テキストの追加

    IkenshoFirebirdDBManager dbm = null;
    try {
      fullApplySource();

      //エラーチェック
      if (!canDataUpdate()) {
        return false;
      }

      //パッシブチェック開始
      clearPassiveTask();
      if (hasPatient) {
        addPassiveUpdateTask(PASSIVE_CHECK_KEY_PATIENT, 0);
      }
      if (hasOriginalDocument) {
        addPassiveUpdateTask(PASSIVE_CHECK_KEY_COMMON, 0);
      }

      dbm = getPassiveCheckedDBManager();
      if (dbm == null) {
        ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
        return false;
      }
      //パッシブチェック終了

      if (hasPatient) {
        //最新患者情報も更新
        doUpdatePatient(dbm);
      }
      else {
        doInsertPatient(dbm);
        //パッシブチェック用に患者番号を作成
        originalData.setData("PATIENT_NO", getPatientNo());
      }

      if (hasOriginalDocument) {
        doUpdateCustomDocument(dbm);
        doUpdateCommonDocument(dbm);
      }
      else {
        setEdaNo(String.valueOf(getNextEdaNo(dbm)));
        //パッシブチェック用に枝番号を作成
        originalData.setData("EDA_NO", getEdaNo());

        doInsertCustomDocument(dbm);
        doInsertCommonDocument(dbm);
      }

      dbm.commitTransaction();

      //再度パッシブ
      reselectForPassive(dbm);

      dbm.finalize();

      ACMessageBox.show("登録されました。");
    }
    catch (Exception ex) {
      if (dbm != null) {
        dbm.rollbackTransaction();
        dbm.finalize();
      }
      throw ex;
    }
    return true;
  }

  /**
   * overrideして更新可能かを返す処理を実装します。
   * @throws Exception 処理例外
   * @return boolean 更新可能か
   */
  protected boolean canUpdateCustom() throws Exception {
    return true;
  }

  /**
   * 指定の作成日時が最新の文書であるかを返します。
   * @param dbm FirebirdDBManager
   * @param originalDate Date
   * @throws SQLException SQL例外
   * @throws ParseException 解析例外
   * @return boolean
   */
  protected boolean isMostNewDocument(IkenshoFirebirdDBManager dbm, Date originalDate) throws
      SQLException, ParseException {
    StringBuffer sb = new StringBuffer();
    sb.append("SELECT");
    sb.append(" CREATE_DT");
    sb.append(" FROM ");
    sb.append(getAnotherDocumentTableName());
    sb.append(" WHERE");
    sb.append(" (PATIENT_NO = ");
    sb.append(getPatientNo());
    sb.append(" )");
    sb.append(" ORDER BY");
    sb.append(" EDA_NO DESC");

    VRArrayList array = (VRArrayList)dbm.executeQuery(sb.toString());
    if (array.size() > 0) {
      Date createDate = (Date) VRBindPathParser.get("CREATE_DT",
          (VRMap) array.getData());
      if (createDate != null) {
        if (originalDate.compareTo(createDate) < 0) {
          //より新しい文書があった
          return false;
        }
      }
    }
    return true;
  }

  /**
   * 更新処理を行います。
   * @throws Exception 処理例外
   * @return boolean 更新処理に成功したか
   */
  protected boolean doUpdate() throws Exception {
    //エラーチェック
      // [ID:0000438][Tozo TANAKA] 2009/06/08 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//      if (!canControlUpdate()) {
//          return false;
//        }
//        if(!canUpdateCustom()){
//          return false;
//        }
    setCanUpdateCheckStatus(CAN_UPDATE_CHECK_STATUS_UPDATE);
    try{
        if (!canControlUpdate()) {
            return false;
          }
          if(!canUpdateCustom()){
            return false;
          }
    }finally{
        setCanUpdateCheckStatus(CAN_UPDATE_CHECK_STATUS_UNKNOWN);
    }
    // [ID:0000438][Tozo TANAKA] 2009/06/08 replace end 【主治医医見書・医師医見書】薬剤名テキストの追加

    IkenshoFirebirdDBManager dbm = null;
    try {
      fullApplySource();

      //エラーチェック
      if (!canDataUpdate()) {
        return false;
      }

      //パッシブチェック開始
      clearPassiveTask();

      addPassiveUpdateTask(PASSIVE_CHECK_KEY_PATIENT, 0);
      addPassiveUpdateTask(PASSIVE_CHECK_KEY_COMMON, 0);
      addPassiveUpdateTaskCustom();

      dbm = getPassiveCheckedDBManager();
      if (dbm == null) {
        ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
        return false;
      }
      //パッシブチェック終了

      if (hasPatient) {
        if ( (getNextEdaNo(dbm) - 1) <= Integer.parseInt(getEdaNo())) {
          //対象文書以外の文書と作成日時を比較
          Date originalDate = (Date) VRBindPathParser.get("CREATE_DT",
              originalData);
          if (isMostNewDocument(dbm, originalDate)) {
            //現在更新対象の文書の方が、そうでない文書より作成日が新しい（最新文書）のであれば患者情報も更新する
            doUpdatePatient(dbm);
          }
        }
      }
      else {
        doInsertPatient(dbm);
      }

      if (hasOriginalDocument) {
        doUpdateCustomDocument(dbm);
        doUpdateCommonDocument(dbm);
      }
      else {
        doInsertCustomDocument(dbm);
        doInsertCommonDocument(dbm);
      }

      dbm.commitTransaction();

      reselectForPassive(dbm);

      dbm.finalize();

      ACMessageBox.show("更新されました。");
    }
    catch (Exception ex) {
      if (dbm != null) {
        dbm.rollbackTransaction();
        dbm.finalize();
      }
      throw ex;
    }
    return true;
  }

  protected void doInsertPatient(IkenshoFirebirdDBManager dbm) throws ParseException,
      SQLException {

    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO");
    sb.append(" PATIENT");
    sb.append(" (");
    sb.append(" PATIENT_NM");
    sb.append(",PATIENT_KN");
    sb.append(",SEX");
    sb.append(",BIRTHDAY");
    sb.append(",POST_CD");
    sb.append(",ADDRESS");
    sb.append(",TEL1");
    sb.append(",TEL2");
    sb.append(",CHART_NO");
    sb.append(",KOUSIN_DT");
    sb.append(",LAST_TIME");
    sb.append(" )");
    sb.append(" VALUES");
    sb.append(" (");
    sb.append(getDBSafeString("PATIENT_NM", originalData));
    sb.append(",");
    sb.append(getDBSafeString("PATIENT_KN", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("SEX", originalData));
    sb.append(",");
    sb.append(getDBSafeDate("BIRTHDAY", originalData));
    sb.append(",");
    sb.append(getDBSafeString("POST_CD", originalData));
    sb.append(",");
    sb.append(getDBSafeString("ADDRESS", originalData));
    sb.append(",");
    sb.append(getDBSafeString("TEL1", originalData));
    sb.append(",");
    sb.append(getDBSafeString("TEL2", originalData));
    sb.append(",");
    sb.append(getDBSafeString("CHART_NO", originalData));
    sb.append(",CURRENT_TIMESTAMP");
    sb.append(",CURRENT_TIMESTAMP");
    sb.append(")");

    dbm.executeUpdate(sb.toString());

    //追加された患者番号を取得
    sb = new StringBuffer();
    sb.append("SELECT");
    sb.append(" GEN_ID(GEN_PATIENT,0)");
    sb.append(" FROM");
    sb.append(" RDB$DATABASE");
    VRArrayList patientNoArray = (VRArrayList) dbm.executeQuery(sb.toString());

    setPatientNo(String.valueOf(VRBindPathParser.get("GEN_ID",
        (VRMap) patientNoArray.getData())));

    hasPatient = true;
  }

  protected void doUpdatePatient(IkenshoFirebirdDBManager dbm) throws ParseException,
      SQLException {
    //患者情報
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE");
    sb.append(" PATIENT");
    sb.append(" SET");
    sb.append(" PATIENT_NM = ");
    sb.append(getDBSafeString("PATIENT_NM", originalData));
    sb.append(",PATIENT_KN = ");
    sb.append(getDBSafeString("PATIENT_KN", originalData));
    sb.append(",SEX = ");
    sb.append(getDBSafeNumber("SEX", originalData));
    sb.append(",BIRTHDAY = ");
    sb.append(getDBSafeDate("BIRTHDAY", originalData));
    sb.append(",POST_CD = ");
    sb.append(getDBSafeString("POST_CD", originalData));
    sb.append(",ADDRESS = ");
    sb.append(getDBSafeString("ADDRESS", originalData));
    sb.append(",TEL1 = ");
    sb.append(getDBSafeString("TEL1", originalData));
    sb.append(",TEL2 = ");
    sb.append(getDBSafeString("TEL2", originalData));
    sb.append(",AGE = ");
    sb.append(getDBSafeString("AGE", originalData));
    sb.append(",KOUSIN_DT = CURRENT_TIMESTAMP");
    sb.append(",LAST_TIME = CURRENT_TIMESTAMP");
    sb.append(" WHERE");
    sb.append(" (PATIENT.PATIENT_NO = ");
    sb.append(getPatientNo());
    sb.append(")");

    dbm.executeUpdate(sb.toString());

  }

  /**
   * 意見書指示書共通情報を追加します。
   * @param dbm DBManager
   * @throws Exception 処理例外
   */
  protected void doInsertCommonDocument(IkenshoFirebirdDBManager dbm) throws Exception {
    //意見書指示書共通
    StringBuffer sb = new StringBuffer();
    sb.append("INSERT INTO");
    sb.append(" COMMON_IKN_SIS");
    sb.append(" (");
    sb.append(" PATIENT_NO");
    sb.append(",EDA_NO");
    sb.append(",DOC_KBN");
    sb.append(",PATIENT_NM");
    sb.append(",PATIENT_KN");
    sb.append(",SEX");
    sb.append(",BIRTHDAY");
    sb.append(",AGE");
    sb.append(",POST_CD");
    sb.append(",ADDRESS");
    sb.append(",TEL1");
    sb.append(",TEL2");
    sb.append(",SINDAN_NM1");
    sb.append(",SINDAN_NM2");
    sb.append(",SINDAN_NM3");
    sb.append(",HASHOU_DT1");
    sb.append(",HASHOU_DT2");
    sb.append(",HASHOU_DT3");
    sb.append(",MT_STS");
    sb.append(",MT_STS_OTHER");
    sb.append(",MEDICINE1");
    sb.append(",DOSAGE1");
    sb.append(",UNIT1");
    sb.append(",USAGE1");
    sb.append(",MEDICINE2");
    sb.append(",DOSAGE2");
    sb.append(",UNIT2");
    sb.append(",USAGE2");
    sb.append(",MEDICINE3");
    sb.append(",DOSAGE3");
    sb.append(",UNIT3");
    sb.append(",USAGE3");
    sb.append(",MEDICINE4");
    sb.append(",DOSAGE4");
    sb.append(",UNIT4");
    sb.append(",USAGE4");
    sb.append(",MEDICINE5");
    sb.append(",DOSAGE5");
    sb.append(",UNIT5");
    sb.append(",USAGE5");
    sb.append(",MEDICINE6");
    sb.append(",DOSAGE6");
    sb.append(",UNIT6");
    sb.append(",USAGE6");
    // [ID:0000438][Tozo TANAKA] 2009/06/02 add begin 【主治医医見書・医師医見書】薬剤名テキストの追加
    sb.append(",MEDICINE7");
    sb.append(",DOSAGE7");
    sb.append(",UNIT7");
    sb.append(",USAGE7");
    sb.append(",MEDICINE8");
    sb.append(",DOSAGE8");
    sb.append(",UNIT8");
    sb.append(",USAGE8");
    // [ID:0000438][Tozo TANAKA] 2009/06/02 add end 【主治医医見書・医師医見書】薬剤名テキストの追加
    sb.append(",NETAKIRI");
    sb.append(",CHH_STS");
    sb.append(",SHJ_ANT");
//    sb.append(",YKG_YOGO");
    sb.append(",TNT_KNR");
    sb.append(",YUEKI_PUMP");
    sb.append(",CHU_JOU_EIYOU");
    sb.append(",TOUSEKI");
    sb.append(",JD_FUKU");
    sb.append(",TOU_KYOUKYU");
    sb.append(",JINKOU_KOUMON");
    sb.append(",JINKOU_BOUKOU");
    sb.append(",OX_RYO");
    sb.append(",OX_RYO_RYO");
    sb.append(",JINKOU_KOKYU");
    sb.append(",JINKOU_KKY_HOUSIKI");
    sb.append(",JINKOU_KKY_SET");
    sb.append(",CANNULA");
    sb.append(",CANNULA_SIZE");
    sb.append(",KYUINKI");
    sb.append(",KKN_SEK_SHOCHI");
    sb.append(",KEKN_EIYOU");
    sb.append(",KEKN_EIYOU_METHOD");
    sb.append(",KEKN_EIYOU_SIZE");
    sb.append(",KEKN_EIYOU_CHG");
    sb.append(",CATHETER");
    sb.append(",RYU_CATHETER");
    sb.append(",RYU_CAT_SIZE");
    sb.append(",RYU_CAT_CHG");
    sb.append(",DOREN");
    sb.append(",DOREN_BUI");
    sb.append(",MONITOR");
    sb.append(",TOUTU");
    sb.append(",JOKUSOU_SHOCHI");
    sb.append(",SOUCHAKU_OTHER");
    sb.append(",DR_NM");
    sb.append(",MI_NM");
    sb.append(",MI_POST_CD");
    sb.append(",MI_ADDRESS");
    sb.append(",MI_TEL1");
    sb.append(",MI_TEL2");
    sb.append(",MI_FAX1");
    sb.append(",MI_FAX2");
    sb.append(",MI_CEL_TEL1");
    sb.append(",MI_CEL_TEL2");
    sb.append(",LAST_TIME");
    appendInsertCommonDocumentKeys(sb);

    sb.append(" )");
    sb.append(" VALUES");
    sb.append(" (");
    sb.append(getPatientNo());
    sb.append(",");
    sb.append(getEdaNo());
    sb.append(",");
    sb.append(getCustomDocumentType());
    sb.append(",");
    sb.append(getDBSafeString("PATIENT_NM", originalData));
    sb.append(",");
    sb.append(getDBSafeString("PATIENT_KN", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("SEX", originalData));
    sb.append(",");
    sb.append(getDBSafeDate("BIRTHDAY", originalData));
    sb.append(",");
    sb.append(getDBSafeString("AGE", originalData));
    sb.append(",");
    sb.append(getDBSafeString("POST_CD", originalData));
    sb.append(",");
    sb.append(getDBSafeString("ADDRESS", originalData));
    sb.append(",");
    sb.append(getDBSafeString("TEL1", originalData));
    sb.append(",");
    sb.append(getDBSafeString("TEL2", originalData));
    sb.append(",");
    sb.append(getDBSafeString("SINDAN_NM1", originalData));
    sb.append(",");
    sb.append(getDBSafeString("SINDAN_NM2", originalData));
    sb.append(",");
    sb.append(getDBSafeString("SINDAN_NM3", originalData));
    sb.append(",");
    sb.append(getDBSafeString("HASHOU_DT1", originalData));
    sb.append(",");
    sb.append(getDBSafeString("HASHOU_DT2", originalData));
    sb.append(",");
    sb.append(getDBSafeString("HASHOU_DT3", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MT_STS", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MT_STS_OTHER", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MEDICINE1", originalData));
    sb.append(",");
    sb.append(getDBSafeString("DOSAGE1", originalData));
    sb.append(",");
    sb.append(getDBSafeString("UNIT1", originalData));
    sb.append(",");
    sb.append(getDBSafeString("USAGE1", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MEDICINE2", originalData));
    sb.append(",");
    sb.append(getDBSafeString("DOSAGE2", originalData));
    sb.append(",");
    sb.append(getDBSafeString("UNIT2", originalData));
    sb.append(",");
    sb.append(getDBSafeString("USAGE2", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MEDICINE3", originalData));
    sb.append(",");
    sb.append(getDBSafeString("DOSAGE3", originalData));
    sb.append(",");
    sb.append(getDBSafeString("UNIT3", originalData));
    sb.append(",");
    sb.append(getDBSafeString("USAGE3", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MEDICINE4", originalData));
    sb.append(",");
    sb.append(getDBSafeString("DOSAGE4", originalData));
    sb.append(",");
    sb.append(getDBSafeString("UNIT4", originalData));
    sb.append(",");
    sb.append(getDBSafeString("USAGE4", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MEDICINE5", originalData));
    sb.append(",");
    sb.append(getDBSafeString("DOSAGE5", originalData));
    sb.append(",");
    sb.append(getDBSafeString("UNIT5", originalData));
    sb.append(",");
    sb.append(getDBSafeString("USAGE5", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MEDICINE6", originalData));
    sb.append(",");
    sb.append(getDBSafeString("DOSAGE6", originalData));
    sb.append(",");
    sb.append(getDBSafeString("UNIT6", originalData));
    sb.append(",");
    sb.append(getDBSafeString("USAGE6", originalData));
    // [ID:0000438][Tozo TANAKA] 2009/06/02 add begin 【主治医医見書・医師医見書】薬剤名テキストの追加
    sb.append(",");
    sb.append(getDBSafeString("MEDICINE7", originalData));
    sb.append(",");
    sb.append(getDBSafeString("DOSAGE7", originalData));
    sb.append(",");
    sb.append(getDBSafeString("UNIT7", originalData));
    sb.append(",");
    sb.append(getDBSafeString("USAGE7", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MEDICINE8", originalData));
    sb.append(",");
    sb.append(getDBSafeString("DOSAGE8", originalData));
    sb.append(",");
    sb.append(getDBSafeString("UNIT8", originalData));
    sb.append(",");
    sb.append(getDBSafeString("USAGE8", originalData));
    // [ID:0000438][Tozo TANAKA] 2009/06/02 add end 【主治医医見書・医師医見書】薬剤名テキストの追加
    sb.append(",");
    sb.append(getDBSafeNumber("NETAKIRI", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("CHH_STS", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("SHJ_ANT", originalData));
//    sb.append(",");
//    sb.append(getDBSafeNumber("YKG_YOGO", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("TNT_KNR", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("YUEKI_PUMP", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("CHU_JOU_EIYOU", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("TOUSEKI", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("JD_FUKU", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("TOU_KYOUKYU", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("JINKOU_KOUMON", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("JINKOU_BOUKOU", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("OX_RYO", originalData));
    sb.append(",");
    sb.append(getDBSafeString("OX_RYO_RYO", originalData));
    sb.append(",");
    sb.append(getDBSafeString("JINKOU_KOKYU", originalData));
    sb.append(",");
    sb.append(getDBSafeString("JINKOU_KKY_HOUSIKI", originalData));
    sb.append(",");
    sb.append(getDBSafeString("JINKOU_KKY_SET", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("CANNULA", originalData));
    sb.append(",");
    sb.append(getDBSafeString("CANNULA_SIZE", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("KYUINKI", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("KKN_SEK_SHOCHI", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("KEKN_EIYOU", originalData));
    sb.append(",");
    sb.append(getDBSafeString("KEKN_EIYOU_METHOD", originalData));
    sb.append(",");
    sb.append(getDBSafeString("KEKN_EIYOU_SIZE", originalData));
    sb.append(",");
    sb.append(getDBSafeString("KEKN_EIYOU_CHG", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("CATHETER", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("RYU_CATHETER", originalData));
    sb.append(",");
    sb.append(getDBSafeString("RYU_CAT_SIZE", originalData));
    sb.append(",");
    sb.append(getDBSafeString("RYU_CAT_CHG", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("DOREN", originalData));
    sb.append(",");
    sb.append(getDBSafeString("DOREN_BUI", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("MONITOR", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("TOUTU", originalData));
    sb.append(",");
    sb.append(getDBSafeNumber("JOKUSOU_SHOCHI", originalData));
    sb.append(",");
    sb.append(getDBSafeString("SOUCHAKU_OTHER", originalData));
    sb.append(",");
    sb.append(getDBSafeString("DR_NM", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MI_NM", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MI_POST_CD", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MI_ADDRESS", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MI_TEL1", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MI_TEL2", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MI_FAX1", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MI_FAX2", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MI_CEL_TEL1", originalData));
    sb.append(",");
    sb.append(getDBSafeString("MI_CEL_TEL2", originalData));

    sb.append(",CURRENT_TIMESTAMP");

    appendInsertCommonDocumentValues(sb);

    sb.append(" )");

    dbm.executeUpdate(sb.toString());

  }
  /**
   * overrideして共通文書挿入時のSQLキー句を追加します。
   * @param sb 追加先
   */
  protected void appendInsertCommonDocumentKeys(StringBuffer sb){

  }
  /**
   * overrideして共通文書挿入時のSQLバリュー句を追加します。
   * @throws ParseException 解析例外
   * @param sb 追加先
   */
  protected void appendInsertCommonDocumentValues(StringBuffer sb)throws
      ParseException{

  }
  /**
   * overrideして共通文書更新時のSQLキー句を追加します。
   * @param sb 追加先
   * @throws ParseException 解析例外
   */
  protected void appendUpdateCommonDocumentStetement(StringBuffer sb)throws
      ParseException{

  }


  protected void doUpdateCommonDocument(IkenshoFirebirdDBManager dbm) throws ParseException,
      SQLException {

    //意見書指示書共通
    StringBuffer sb = new StringBuffer();
    sb.append("UPDATE");
    sb.append(" COMMON_IKN_SIS");
    sb.append(" SET");
    sb.append(" PATIENT_NM = ");
    sb.append(getDBSafeString("PATIENT_NM", originalData));
    sb.append(",PATIENT_KN = ");
    sb.append(getDBSafeString("PATIENT_KN", originalData));
    sb.append(",SEX = ");
    sb.append(getDBSafeNumber("SEX", originalData));
    sb.append(",BIRTHDAY = ");
    sb.append(getDBSafeDate("BIRTHDAY", originalData));
    sb.append(",AGE = ");
    sb.append(getDBSafeString("AGE", originalData));
    sb.append(",POST_CD = ");
    sb.append(getDBSafeString("POST_CD", originalData));
    sb.append(",ADDRESS = ");
    sb.append(getDBSafeString("ADDRESS", originalData));
    sb.append(",TEL1 = ");
    sb.append(getDBSafeString("TEL1", originalData));
    sb.append(",TEL2 = ");
    sb.append(getDBSafeString("TEL2", originalData));
    sb.append(",SINDAN_NM1 = ");
    sb.append(getDBSafeString("SINDAN_NM1", originalData));
    sb.append(",SINDAN_NM2 = ");
    sb.append(getDBSafeString("SINDAN_NM2", originalData));
    sb.append(",SINDAN_NM3 = ");
    sb.append(getDBSafeString("SINDAN_NM3", originalData));
    sb.append(",HASHOU_DT1 = ");
    sb.append(getDBSafeString("HASHOU_DT1", originalData));
    sb.append(",HASHOU_DT2 = ");
    sb.append(getDBSafeString("HASHOU_DT2", originalData));
    sb.append(",HASHOU_DT3 = ");
    sb.append(getDBSafeString("HASHOU_DT3", originalData));
    sb.append(",MT_STS = ");
    sb.append(getDBSafeString("MT_STS", originalData));
    sb.append(",MT_STS_OTHER = ");
    sb.append(getDBSafeString("MT_STS_OTHER", originalData));
    sb.append(",MEDICINE1 = ");
    sb.append(getDBSafeString("MEDICINE1", originalData));
    sb.append(",DOSAGE1 = ");
    sb.append(getDBSafeString("DOSAGE1", originalData));
    sb.append(",UNIT1 = ");
    sb.append(getDBSafeString("UNIT1", originalData));
    sb.append(",USAGE1 = ");
    sb.append(getDBSafeString("USAGE1", originalData));
    sb.append(",MEDICINE2 = ");
    sb.append(getDBSafeString("MEDICINE2", originalData));
    sb.append(",DOSAGE2 = ");
    sb.append(getDBSafeString("DOSAGE2", originalData));
    sb.append(",UNIT2 = ");
    sb.append(getDBSafeString("UNIT2", originalData));
    sb.append(",USAGE2 = ");
    sb.append(getDBSafeString("USAGE2", originalData));
    sb.append(",MEDICINE3 = ");
    sb.append(getDBSafeString("MEDICINE3", originalData));
    sb.append(",DOSAGE3 = ");
    sb.append(getDBSafeString("DOSAGE3", originalData));
    sb.append(",UNIT3 = ");
    sb.append(getDBSafeString("UNIT3", originalData));
    sb.append(",USAGE3 = ");
    sb.append(getDBSafeString("USAGE3", originalData));
    sb.append(",MEDICINE4 = ");
    sb.append(getDBSafeString("MEDICINE4", originalData));
    sb.append(",DOSAGE4 = ");
    sb.append(getDBSafeString("DOSAGE4", originalData));
    sb.append(",UNIT4 = ");
    sb.append(getDBSafeString("UNIT4", originalData));
    sb.append(",USAGE4 = ");
    sb.append(getDBSafeString("USAGE4", originalData));
    sb.append(",MEDICINE5 = ");
    sb.append(getDBSafeString("MEDICINE5", originalData));
    sb.append(",DOSAGE5 = ");
    sb.append(getDBSafeString("DOSAGE5", originalData));
    sb.append(",UNIT5 = ");
    sb.append(getDBSafeString("UNIT5", originalData));
    sb.append(",USAGE5 = ");
    sb.append(getDBSafeString("USAGE5", originalData));
    sb.append(",MEDICINE6 = ");
    sb.append(getDBSafeString("MEDICINE6", originalData));
    sb.append(",DOSAGE6 = ");
    sb.append(getDBSafeString("DOSAGE6", originalData));
    sb.append(",UNIT6 = ");
    sb.append(getDBSafeString("UNIT6", originalData));
    sb.append(",USAGE6 = ");
    sb.append(getDBSafeString("USAGE6", originalData));
    // [ID:0000438][Tozo TANAKA] 2009/06/02 add begin 【主治医医見書・医師医見書】薬剤名テキストの追加
    sb.append(",MEDICINE7 = ");
    sb.append(getDBSafeString("MEDICINE7", originalData));
    sb.append(",DOSAGE7 = ");
    sb.append(getDBSafeString("DOSAGE7", originalData));
    sb.append(",UNIT7 = ");
    sb.append(getDBSafeString("UNIT7", originalData));
    sb.append(",USAGE7 = ");
    sb.append(getDBSafeString("USAGE7", originalData));
    sb.append(",MEDICINE8 = ");
    sb.append(getDBSafeString("MEDICINE8", originalData));
    sb.append(",DOSAGE8 = ");
    sb.append(getDBSafeString("DOSAGE8", originalData));
    sb.append(",UNIT8 = ");
    sb.append(getDBSafeString("UNIT8", originalData));
    sb.append(",USAGE8 = ");
    sb.append(getDBSafeString("USAGE8", originalData));
    // [ID:0000438][Tozo TANAKA] 2009/06/02 add end 【主治医医見書・医師医見書】薬剤名テキストの追加
    
    sb.append(",NETAKIRI = ");
    sb.append(getDBSafeNumber("NETAKIRI", originalData));
    sb.append(",CHH_STS = ");
    sb.append(getDBSafeNumber("CHH_STS", originalData));
    sb.append(",SHJ_ANT = ");
    sb.append(getDBSafeNumber("SHJ_ANT", originalData));
//    sb.append(",YKG_YOGO = ");
//    sb.append(getDBSafeNumber("YKG_YOGO", originalData));
    sb.append(",TNT_KNR = ");
    sb.append(getDBSafeNumber("TNT_KNR", originalData));
    sb.append(",YUEKI_PUMP = ");
    sb.append(getDBSafeNumber("YUEKI_PUMP", originalData));
    sb.append(",CHU_JOU_EIYOU = ");
    sb.append(getDBSafeNumber("CHU_JOU_EIYOU", originalData));
    sb.append(",TOUSEKI = ");
    sb.append(getDBSafeNumber("TOUSEKI", originalData));
    sb.append(",JD_FUKU = ");
    sb.append(getDBSafeNumber("JD_FUKU", originalData));
    sb.append(",TOU_KYOUKYU = ");
    sb.append(getDBSafeNumber("TOU_KYOUKYU", originalData));
    sb.append(",JINKOU_KOUMON = ");
    sb.append(getDBSafeNumber("JINKOU_KOUMON", originalData));
    sb.append(",JINKOU_BOUKOU = ");
    sb.append(getDBSafeNumber("JINKOU_BOUKOU", originalData));
//    sb.append(",OX_RYO = ");
//    sb.append(getDBSafeNumber("OX_RYO", originalData));
//    sb.append(",OX_RYO_RYO = ");
//    sb.append(getDBSafeString("OX_RYO_RYO", originalData));
    IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "OX_RYO",
                                     new String[] {"OX_RYO_RYO"}, true);
//    sb.append(",JINKOU_KOKYU = ");
//    sb.append(getDBSafeNumber("JINKOU_KOKYU", originalData));
//    sb.append(",JINKOU_KKY_HOUSIKI = ");
//    sb.append(getDBSafeString("JINKOU_KKY_HOUSIKI", originalData));
//    sb.append(",JINKOU_KKY_SET = ");
//    sb.append(getDBSafeString("JINKOU_KKY_SET", originalData));
    IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "JINKOU_KOKYU",
                                           new String[] {"JINKOU_KKY_HOUSIKI",
                                           "JINKOU_KKY_SET"}, true);
//    sb.append(",CANNULA = ");
//    sb.append(getDBSafeNumber("CANNULA", originalData));
//    sb.append(",CANNULA_SIZE = ");
//    sb.append(getDBSafeString("CANNULA_SIZE", originalData));
    IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "CANNULA",
                                     new String[] {"CANNULA_SIZE"}, true);
    sb.append(",KYUINKI = ");
    sb.append(getDBSafeNumber("KYUINKI", originalData));
    sb.append(",KKN_SEK_SHOCHI = ");
    sb.append(getDBSafeNumber("KKN_SEK_SHOCHI", originalData));
//    sb.append(",KEKN_EIYOU = ");
//    sb.append(getDBSafeNumber("KEKN_EIYOU", originalData));
//    sb.append(",KEKN_EIYOU_METHOD = ");
//    sb.append(getDBSafeString("KEKN_EIYOU_METHOD", originalData));
//    sb.append(",KEKN_EIYOU_SIZE = ");
//    sb.append(getDBSafeString("KEKN_EIYOU_SIZE", originalData));
//    sb.append(",KEKN_EIYOU_CHG = ");
//    sb.append(getDBSafeString("KEKN_EIYOU_CHG", originalData));
    IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "KEKN_EIYOU",
                                           new String[] {"KEKN_EIYOU_METHOD",
                                           "KEKN_EIYOU_SIZE", "KEKN_EIYOU_CHG"}, true);
    sb.append(",CATHETER = ");
    sb.append(getDBSafeNumber("CATHETER", originalData));
//    sb.append(",RYU_CATHETER = ");
//    sb.append(getDBSafeNumber("RYU_CATHETER", originalData));
//    sb.append(",RYU_CAT_SIZE = ");
//    sb.append(getDBSafeString("RYU_CAT_SIZE", originalData));
//    sb.append(",RYU_CAT_CHG = ");
//    sb.append(getDBSafeString("RYU_CAT_CHG", originalData));
    IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "RYU_CATHETER",
                                           new String[] {"RYU_CAT_SIZE",
                                           "RYU_CAT_CHG"}, true);
//    sb.append(",DOREN = ");
//    sb.append(getDBSafeNumber("DOREN", originalData));
//    sb.append(",DOREN_BUI = ");
//    sb.append(getDBSafeString("DOREN_BUI", originalData));
    IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "DOREN",
                                     new String[] {"DOREN_BUI"}, true);
    sb.append(",MONITOR = ");
    sb.append(getDBSafeNumber("MONITOR", originalData));
    sb.append(",TOUTU = ");
    sb.append(getDBSafeNumber("TOUTU", originalData));
    sb.append(",JOKUSOU_SHOCHI = ");
    sb.append(getDBSafeNumber("JOKUSOU_SHOCHI", originalData));
//    sb.append(",SOUCHAKU_OTHER = ");
//    sb.append(getDBSafeString("SOUCHAKU_OTHER", originalData));
    IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "SOUCHAKU_OTHER_FLAG",
                                     new String[] {"SOUCHAKU_OTHER"}, false);
    sb.append(",DR_NM = ");
    sb.append(getDBSafeString("DR_NM", originalData));
    sb.append(",MI_NM = ");
    sb.append(getDBSafeString("MI_NM", originalData));
    sb.append(",MI_POST_CD = ");
    sb.append(getDBSafeString("MI_POST_CD", originalData));
    sb.append(",MI_ADDRESS = ");
    sb.append(getDBSafeString("MI_ADDRESS", originalData));
    sb.append(",MI_TEL1 = ");
    sb.append(getDBSafeString("MI_TEL1", originalData));
    sb.append(",MI_TEL2 = ");
    sb.append(getDBSafeString("MI_TEL2", originalData));
    sb.append(",MI_FAX1 = ");
    sb.append(getDBSafeString("MI_FAX1", originalData));
    sb.append(",MI_FAX2 = ");
    sb.append(getDBSafeString("MI_FAX2", originalData));
    sb.append(",MI_CEL_TEL1 = ");
    sb.append(getDBSafeString("MI_CEL_TEL1", originalData));
    sb.append(",MI_CEL_TEL2 = ");
    sb.append(getDBSafeString("MI_CEL_TEL2", originalData));
    sb.append(",LAST_TIME = CURRENT_TIMESTAMP");

    appendUpdateCommonDocumentStetement(sb);

    sb.append(" WHERE");
    sb.append(" (COMMON_IKN_SIS.PATIENT_NO = ");
    sb.append(getPatientNo());
    sb.append(")");
    sb.append("AND(COMMON_IKN_SIS.EDA_NO = ");
    sb.append(getEdaNo());
    sb.append(")");
    sb.append("AND(COMMON_IKN_SIS.DOC_KBN=");
    sb.append(getCustomDocumentType());
    sb.append(")");

    dbm.executeUpdate(sb.toString());

  }


  /**
   * コンストラクタです。
   */
  public IkenshoTabbableAffairContainer() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void jbInit() throws Exception {
    //Layout
    editorInfoLayout.setAutoWrap(false);
    editorInfo.setLayout(editorInfoLayout);
    tabPanel.setLayout(new BorderLayout());

    //Properity
    update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);
    update.setMnemonic('S');
    update.setText("登録(S)");
    update.setToolTipText("現在の内容を登録します。");
    print.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_PRINT);
    print.setMnemonic('P');
    print.setText("印刷(P)");
    print.setToolTipText("現在の内容を印刷します。");
    editorDateCaption.setText("記入日");
    editorNameCaption.setText("氏　名");
    editorDates.setOpaque(false);
    editorDates.setBindPath("KINYU_DT");
    editorDates.setText("平成17年03月17日");
    editorInfo.setOpaque(false);
    editorName.setOpaque(false);
    editorName.setBindPath("PATIENT_NM");
    editorName.setText("藤原　マイト");
    editorDates.setFormat(IkenshoConstants.FORMAT_ERA_YMD);

    String osName = System.getProperty("os.name");
    if ( (osName == null) || (osName.indexOf("Mac") < 0)) {
      //Mac以外は着色
      editorNameCaption.setForeground(IkenshoConstants.COLOR_TITLE_FOREGROUND);
      editorDateCaption.setForeground(IkenshoConstants.COLOR_TITLE_FOREGROUND);
      editorDates.setForeground(IkenshoConstants.COLOR_TITLE_FOREGROUND);
      editorName.setForeground(IkenshoConstants.COLOR_TITLE_FOREGROUND);
    }else{
      //Macは無着色

    }

    //Add
    buttons.add(editorInfo, VRLayout.WEST);
    buttons.add(print, VRLayout.EAST);
    buttons.add(update, VRLayout.EAST);
    editorInfo.add(editorDateCaption, VRLayout.FLOW);
    editorInfo.add(editorDates, VRLayout.FLOW_RETURN);
    editorInfo.add(editorNameCaption, VRLayout.FLOW);
    editorInfo.add(editorName, VRLayout.FLOW);
    tabPanel.add(tabs, BorderLayout.CENTER);
    this.add(buttons, VRLayout.NORTH);
    this.add(tabPanel, VRLayout.CLIENT);
  }

  /**
   * 走査対象のコンポーネントに参照先ソースを設定します。
   *
   * @param source 参照先ソース
   */
  protected void fullSetSource(VRMap source) {
    tabPanel.setSource(source);
    editorInfo.setSource(source);
    Iterator it = tabArray.iterator();
    while (it.hasNext()) {
      IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer) it.next();
      tab.setMasterSource(source);
      tab.setSourceInnerBindComponent(source);
    }
  }

  /**
   * 走査対象のコンポーネントのデフォルトデータを格納したソースインスタンスを生成します。
   *
   * @return 子要素インスタンス
   */
  protected VRMap fullCreateSource() {
      VRMap map = (VRMap) tabPanel.createSource();
    Iterator it = tabArray.iterator();
    while (it.hasNext()) {
      map.putAll( ( (IkenshoTabbableChildAffairContainer) it.next()).
                 createSourceInnerBindComponent());
    }
    return map;
  }

  /**
   * 走査対象のコンポーネントに参照先ソースの値を流し込みます。
   * @throws Exception 解析例外
   */
  protected void fullBindSource() throws Exception {
    tabPanel.bindSource();
    editorInfo.bindSource();
    reBindSource();
  }

  /**
   * 基底を除く走査対象のコンポーネントに参照先ソースの値を流し込みます。
   * @throws Exception 解析例外
   */
  protected void reBindSource() throws Exception {
    Iterator it = tabArray.iterator();
    while (it.hasNext()) {
      ( (IkenshoTabbableChildAffairContainer) it.next()).bindSourceInnerBindComponent();
    }
  }

  /**
   * 走査対象のコンポーネントの値を参照先ソースに適用します。
   * @throws Exception 解析例外
   */
  protected void fullApplySource() throws Exception {
    //reBindSource();
    tabPanel.applySource();
    Iterator it = tabArray.iterator();
    while (it.hasNext()) {
      ( (IkenshoTabbableChildAffairContainer) it.next()).applySourceInnerBindComponent();
    }
  }

  /**
   * 選択中のタブ番号を返します。
   * @return 選択中のタブ番号
   */
  public int getSelctedTabIndex() {
    return tabs.getSelectedIndex();
  }
  //2006/02/12[Tozo Tanaka] : add begin
  /**
   * タブペインを返します。
   * @return タブペイン
   */
  public JTabbedPane getTabs(){return tabs;}
  //2006/02/12[Tozo Tanaka] : add end

  /**
   * 選択中のタブを設定します。
   * @param tab 選択するタブ
   */
  public void setSelctedTab(IkenshoTabbableChildAffairContainer tab) {
    tabs.setSelectedComponent(tab);
  }

  /**
   * 検索後、適用を行なう直前にイベントを処理します。
   * @throws Exception 処理例外
   */
  protected void doBeforeBindOnSelected() throws Exception {
    Iterator it = tabArray.iterator();
    while (it.hasNext()) {
      ( (IkenshoTabbableChildAffairContainer) it.next()).doBeforeBindOnSelected();
    }
  }
  

  /**
   * 簡易スナップショットクラスを返します。
   */
  public IkenshoIkenshoSimpleSnapshot getSimpleSnap() {
      // 2006/06/22
      // スナップショット
      // Addition - begin [Masahiko Higuchi]
      return simpleSnap;
      // Addition - end
  }

  //2009/01/22 [Tozo Tanaka] Add - begin    
  public ACAffairButton getUpdate(){
      return update;
  }
  public ACAffairButton getPrint(){
      return print;
  }
  //2009/01/22 [Tozo Tanaka] Add - end    

}

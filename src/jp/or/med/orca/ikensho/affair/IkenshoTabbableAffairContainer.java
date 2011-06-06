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

  //GUI�R���|�[�l���g
  // 2009/01/13 [Mizuki Tsutsumi] : add begin / ���C�����j���[�֖߂�{�^��
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
  
  //Passive�L�[
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
  // �X�i�b�v�V���b�g
  // Addition - begin [Masahiko Higuchi]
  protected IkenshoIkenshoSimpleSnapshot simpleSnap = new IkenshoIkenshoSimpleSnapshot();
  // Addition - end

  // [ID:0000438][Tozo TANAKA] 2009/06/08 add begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
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
  // [ID:0000438][Tozo TANAKA] 2009/06/08 add end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�

  /**
   * override���ČŗL�̃p�b�V�u�`�F�b�N�\����`���܂��B
   * @throws ParseException ������O
   */
  protected void doReservedPassiveCustom() throws ParseException {
    throw new RuntimeException(
        "�����I��Over ride���K�v��doReservedPassiveCustum���\�b�h���Ă΂�܂���");
  }

  /**
   * override���ČŗL�����̌����������`���܂��B
   * @param dbm DBManager
   * @throws Exception ������O
   */
  protected void doSelectCustomDocument(IkenshoFirebirdDBManager dbm) throws Exception {
    throw new RuntimeException(
        "�����I��Over ride���K�v��doSelectOriginalDocument���\�b�h���Ă΂�܂���");
  }

  /**
   * override���ČŗL�����̏����ݒ菈�����`���܂��B
   * @param dbm DBManager
   */
  protected void doSelectDefaultCustomDocument(IkenshoFirebirdDBManager dbm){
    throw new RuntimeException(
      "�����I��Over ride���K�v��doDefaultSelectCustumDocument���\�b�h���Ă΂�܂���");
  }

  /**
   * override���ČŗL�̃p�b�V�u�`�F�b�N�p�ɍēxLAST_TIME����蒼���������`���܂��B
   * @param dbm DBManager
   * @param data �ǉ���}�b�v
   * @throws Exception ������O
   */
  protected void reselectForPassiveCustom(IkenshoFirebirdDBManager dbm, VRMap data) throws Exception {
    throw new RuntimeException(
        "�����I��Over ride���K�v��reselectForPassiveCustum���\�b�h���Ă΂�܂���");
  }

  /**
   * override���Ĉ���_�C�A���O�\���������`���܂��B
   * @throws Exception ������O
   * @return ��������s������
   */
  protected boolean showPrintDialogCustom()throws Exception{
    throw new RuntimeException(
        "�����I��Over ride���K�v��showPrintDialogCustum���\�b�h���Ă΂�܂���");
  }

  /**
   * override���ČŗL�����e�[�u������Ԃ��������`���܂��B
   * @return �ŗL�����e�[�u����
   */
  protected String getCustomDocumentTableName(){
    throw new RuntimeException(
      "�����I��Over ride���K�v��getCustumDocumentTableName���\�b�h���Ă΂�܂���");
  }

  /**
   * override���đΔ䂳���ŗL�����e�[�u������Ԃ��������`���܂��B
   * @return �Δ䂳���ŗL�����e�[�u����
   */
  protected String getAnotherDocumentTableName(){
    throw new RuntimeException(
      "�����I��Over ride���K�v��getAnotherDocumentTableName���\�b�h���Ă΂�܂���");
  }

  /**
   * override���ČŗL��������}�����鏈�����`���܂��B
   * @param dbm DBManager
   * @throws Exception ������O
   */
  protected void doInsertCustomDocument(IkenshoFirebirdDBManager dbm) throws Exception {
    throw new RuntimeException(
      "�����I��Over ride���K�v��doInsertOriginalDocument���\�b�h���Ă΂�܂���");
  }

  /**
   * override���ČŗL���������X�V���鏈�����`���܂��B
   * @param dbm DBManager
   * @throws Exception ������O
   */
  protected void doUpdateCustomDocument(IkenshoFirebirdDBManager dbm) throws Exception {
    throw new RuntimeException(
      "�����I��Over ride���K�v��doUpdateOriginalDocument���\�b�h���Ă΂�܂���");
  }

  /**
   * override���ČŗL�������̒��O�l��ݒ肷�鏈�����`���܂��B
   * @param dbm DBManager
   * @throws Exception ������O
   */
  protected void doSelectBeforeCustomDocument(IkenshoFirebirdDBManager dbm) throws Exception {
    throw new RuntimeException(
      "�����I��Over ride���K�v��doSelectBeforeOriginalDocument���\�b�h���Ă΂�܂���");
  }

  /**
   * override���ČŗL��������}�����邽�߂̃p�b�V�u�^�X�N��ǉ����鏈�����`���܂��B
   */
  protected void addPassiveInsertTaskCustom(){
    throw new RuntimeException(
      "�����I��Over ride���K�v��addPassiveInsertTaskCustum���\�b�h���Ă΂�܂���");
  }

  /**
   * override���ČŗL���������X�V���邽�߂̃p�b�V�u�^�X�N��ǉ����鏈�����`���܂��B
   */
  protected void addPassiveUpdateTaskCustom(){
    throw new RuntimeException(
      "�����I��Over ride���K�v��addPassiveUpdateTaskCustum���\�b�h���Ă΂�܂���");
  }

  /**
   * override���ČŗL�����敪��Ԃ��������`���܂��B
   * @return �ŗL�����敪
   */
  protected String getCustomDocumentType(){
    throw new RuntimeException(
      "�����I��Over ride���K�v��getCustumDocumentType���\�b�h���Ă΂�܂���");
  }

  /**
   * override���đΔ䂳���ŗL�����敪��Ԃ��������`���܂��B
   * @return �Δ䂳���ŗL�����敪
   */
  protected String getAnotherDocumentType(){
    throw new RuntimeException(
      "�����I��Over ride���K�v��getAnotherDocumentType���\�b�h���Ă΂�܂���");
  }

  /**
   * �K�v�ł����override���ăX�i�b�v�V���b�g�Ώۂ��珜�O����R���|�[�l���g�W���Ԃ��܂��B
   * @return �X�i�b�v�V���b�g�Ώۂ��珜�O����R���|�[�l���g�W��
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
   * ���݂̏������[�h��Ԃ��܂��B
   * @return �������[�h
   */
  public String getNowMode() {
    return nowMode;
  }

  /**
   * ���݂̏������[�h��ݒ肵�܂��B
   * @param nowMode �������[�h
   */
  protected void setNowMode(String nowMode) {
    this.nowMode = nowMode;

    if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(nowMode)) {
      removeUpdateTrigger(update);
      addInsertTrigger(update);
      update.setText("�o�^(S)");
      update.setToolTipText("���݂̓��e��o�^���܂��B");
    }
    else if (IkenshoConstants.AFFAIR_MODE_UPDATE.equals(nowMode)) {
      removeInsertTrigger(update);
      addUpdateTrigger(update);
      update.setText("�X�V(S)");
      update.setToolTipText("���݂̓��e���X�V���܂��B");

    }
  }

  /**
   * ���Ҕԍ���Ԃ��܂��B
   * @return ���Ҕԍ�
   */
  public String getPatientNo() {
    return patientNo;
  }

  /**
   * ���Ҕԍ���ݒ肵�܂��B
   * @param patientNo ���Ҕԍ�
   */
  protected void setPatientNo(String patientNo) {
    this.patientNo = patientNo;
  }

  /**
   * �ŗL�����}�ԍ���Ԃ��܂��B
   * @return �ŗL�����}�ԍ�
   */
  public String getEdaNo() {
    return edaNo;
  }

  /**
   * �ŗL�����}�ԍ���ݒ肵�܂��B
   * @param edaNo �ŗL�����}�ԍ�
   */
  protected void setEdaNo(String edaNo) {
    this.edaNo = edaNo;
  }


  /**
   * ��^�����ꊇ���Ď擾���܂��B
   * @param dbm DBManager
   * @throws SQLException SQL��O
   * @throws ParseException ��͗�O
   */
  protected void poolTeikeibun(IkenshoFirebirdDBManager dbm) throws SQLException,
      ParseException {
    teikeibunMap = new HashMap();
    IkenshoCommon.getMasterTable(dbm, teikeibunMap, "TEIKEIBUN", "TEIKEIBUN", "TKB_KBN", "TKB_CD");
  }

  /**
   * �v�[��������^�����R���{�ɐݒ肵�܂��B
   * @param combo �R���{
   * @param code ��^���R�[�h
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

    //����ʂ���̖߂�ł��邩
    VRMap previewData = null;
    VRMap params = affair.getParameters();
    if (VRBindPathParser.has("PREV_DATA", params)) {
      //��ʑJ�ڃL���b�V���f�[�^������ꍇ�́A�p�����^��u��������
      params = (VRMap) VRBindPathParser.get("PREV_DATA", params);
      previewData = params;
    }

    setNowMode(String.valueOf(VRBindPathParser.get("AFFAIR_MODE", params)));

    hasPatient = VRBindPathParser.has("PATIENT_NO", params);

    IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
    //���Ҕԍ�
    if (hasPatient) {
      setPatientNo(String.valueOf(VRBindPathParser.get("PATIENT_NO",
          params)));
      //�ӌ����}��
      if (VRBindPathParser.has("EDA_NO", params)) {
        setEdaNo(String.valueOf(VRBindPathParser.get("EDA_NO", params)));
      }
    }
    if(params instanceof Map){
      originalData.putAll(params);
    }

    poolTeikeibun(dbm);

    //DB�ւ̃A�N�Z�X��K�v�Ƃ��鏉����
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
      //�^�u�w�肪���遨�^�u�؂�ւ�
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
      //�t�H�[�J�X��R���|�[�l���g���w�肳��Ă��Ȃ���΁A�ŏ��̃^�u�̃t�H�[�J�X��R���|�[�l���g���̗p����
      firstFocusComponent = ((IkenshoTabbableChildAffairContainer) tabs.
          getSelectedComponent()).getFirstFocusComponent();
    }
    
    //2009/01/16 [Tozo Tanaka] Add - begin
    if((previewData == null) && sickMedicineCountAdjusted){
        //�V�K�쐬���[�h�ł��������������݂���ꍇ�ŁA����ʂ���̖߂�ł͂Ȃ��A
        //���O�̕����ɖ��7�܂��͖��8�����͂���Ă���A����ܖ��̕\������6�ݒ�̏ꍇ�͍����|���x��
        showSickMedicineCountAdjustedMessage();
    }
    //2009/01/16 [Tozo Tanaka] Add - end

  }

  //2009/01/16 [Tozo Tanaka] Add - begin
  protected void checkSickMedicineCountAdjusted(VRMap source) throws Exception{
      //���O�̕����ɖ��7�܂��͖��8�����͂���Ă���A����ܖ��̕\������6�ݒ�̏ꍇ�͍����|���x��
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
          //���7�����8�����͂���Ă���ꍇ
          if (getMedicineViewCount() == 6) {
              //��ܖ��̕\������6�ݒ�̏ꍇ
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
      // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
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
    // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
}
  
  protected void showSickMedicineCountAdjustedMessage(){
      ACMessageBox
      .show(
//              "�ŐV�̕����ɓ��͂��ꂽ���7����і��8�̏��͓]�L����܂���B"
              "�ŐV�̕����ɖ�ܖ���7�ȏ���͂���Ă��܂��B"
              + ACConstants.LINE_SEPARATOR
              + "7�ȏ�̖�܂�ҏW����ꍇ�́A"
              + ACConstants.LINE_SEPARATOR
              + "[���C�����j���[]-[�ݒ�(S)]-[���̑��̐ݒ�(O)]���A��ܖ��̕\������8�ɕύX���Ă��������B");
  }
  //2009/01/16 [Tozo Tanaka] Add - end

  /**
   * �p�b�V�u�`�F�b�N�p�ɍēxLAST_TIME����蒼���܂��B
   * @param dbm DBManager
   * @throws Exception ������O
   */
  protected void reselectForPassive(IkenshoFirebirdDBManager dbm) throws Exception {
    StringBuffer sb;
    VRArrayList array;
    VRMap data = new VRHashMap();

    //���ҏ��
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

    //���ʕ���
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

    //�ŗL�����̍Ď擾
    reselectForPassiveCustom(dbm, data);
    originalData.putAll(data);


    //�p�b�V�u�`�F�b�N�\��
    doReservedPassive();

  }

  /**
   * ���O�̋��ʕ����̌����������s���܂��B
   * @param dbm DBManager
   * @throws Exception ������O
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
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
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
//  [ID:0000514][Tozo TANAKA] 2009/09/09 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
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

//    [ID:0000514][Tozo TANAKA] 2009/09/09 remove begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
//      String[] keeps = new String[] {
//          "DR_NM", "MI_NM", "MI_POST_CD", "MI_ADDRESS", "MI_TEL1", "MI_TEL2",
//          "MI_FAX1", "MI_FAX2", "MI_CEL_TEL1", "MI_CEL_TEL2", };
//      int end = keeps.length;
//      for(int i=0; i<end; i++){
//        data.setData(keeps[i], originalData.getData(keeps[i]));
//      }
//    [ID:0000514][Tozo TANAKA] 2009/09/09 remove end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\

      originalData.putAll(data);
    }
    

//  [ID:0000514][Tozo TANAKA] 2009/09/09 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
    //���ʖK��Ō�w�����ȊO�̍ŐV��������擾
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
        //�w�����̏ꍇ�͓��ʖK��Ō�w����(FORMAT_KBN=1)�����O����
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
        //�w�����̏ꍇ�͓��ʖK��Ō�w����(FORMAT_KBN=1)�����O����
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
    
//  [ID:0000514][Tozo TANAKA] 2009/09/09 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
        
        
  }

  /**
   * ���ʕ����̌����������s���܂��B
   * @param dbm DBManager
   * @throws Exception ������O
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
   * ���ҏ��̌����������s���܂��B
   * @param dbm DBManager
   * @throws Exception ������O
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
     * �����������ł��邩��Ԃ��܂��B
     * 
     * @return �����������ł��邩
     */
    public boolean isNowSelecting() {
        return nowSelecting;
    }

    /**
     * �����������ł��邩��ݒ肵�܂��B
     * 
     * @param nowSelecting �����������ł��邩
     */
    public void setNowSelecting(boolean nowSelecting) {
        this.nowSelecting = nowSelecting;
    }

    /**
     * �X�V���[�h�Ō����������ł��邩��Ԃ��܂��B
     * 
     * @return �X�V���[�h�Ō����������ł��邩
     */
    public boolean isNowSelectingByUpdate() {
        return isNowSelecting()
                && IkenshoConstants.AFFAIR_MODE_UPDATE.equals(getNowMode());
    }
    //2006/09/08[Tozo Tanaka] : add end

  /**
   * �����������s���܂��B
   * @throws Exception ������O
   */
  protected void doSelect() throws Exception {
      // 2006/09/08[Tozo Tanaka] : add begin
        setNowSelecting(true);
        try {
            // 2006/09/08[Tozo Tanaka] : add end

    IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
    hasOriginalDocument = false;

    //�����l�����W���ď�����
    if (IkenshoConstants.AFFAIR_MODE_UPDATE.equals(getNowMode())) {
      //�X�V���[�h�̏ꍇ�A���Ҕԍ��Ǝ}�Ԃ��L�[�Ƀf�[�^���W�߂�
        
      doSelectPatient(dbm);

      //�ŗL����
      doSelectCustomDocument(dbm);

      //�ӌ����E�w��������
      doSelectCommonDocument(dbm);
      
    }
    else if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(getNowMode())) {
      //�o�^���[�h�̏ꍇ�A�f�t�H���g�l�����W����

      //�p�b�V�u�`�F�b�N�p�ɕ����敪���쐬
      originalData.setData("DOC_KBN", new Integer(getCustomDocumentType()));

      //���ҏ��
      if (hasPatient) {
        doSelectPatient(dbm);

        //���O�̌ŗL����
        doSelectBeforeCustomDocument(dbm);

        //���O�̈ӌ����E�w��������
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
        //�����ݒ�
        doSelectDefaultCustomDocument(dbm);
      }

    }

    doBeforeBindOnSelected();

    //�K�p
    originalArray = new VRArrayList();
    if (originalData.size() > 0) {
      fullSetSource(originalData);
      fullBindSource();

      originalArray.addData(originalData);
    }

    //�p�b�V�u�`�F�b�N�\��
    doReservedPassive();
    
    // 2006/09/08[Tozo Tanaka] : add begin
        } finally {
            setNowSelecting(false);
        }
        // 2006/09/08[Tozo Tanaka] : add end

  }

  /**
   * �p�b�V�u�`�F�b�N�\����s���܂��B
   * @throws ParseException ������O
   */
  protected void doReservedPassive() throws ParseException {
    clearReservedPassive();

    reservedPassive(PASSIVE_CHECK_KEY_PATIENT, originalArray);
    reservedPassive(PASSIVE_CHECK_KEY_COMMON, originalArray);

    doReservedPassiveCustom();

    IkenshoSnapshot.getInstance().setExclusions(getCustomSnapshotExclusions());
    IkenshoSnapshot.getInstance().snapshot();
    
    // 2006/06/22
    // �X�i�b�v�V���b�g
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
    // �X�i�b�v�V���b�g
    // Replace - begin [Masahiko Higuchi]
    if (IkenshoSnapshot.getInstance().isModified()||simpleSnap.simpleIsModefield()) {
    // Replace - end        
      if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(getNowMode())) {
        //�ǉ����[�h�̏ꍇ
        switch (ACMessageBox.showYesNoCancel("�o�^���e��ۑ����܂����H", "�o�^���Ė߂�(S)",
                                                  'S',
                                                  "�j�����Ė߂�(R)", 'R')) {
          case ACMessageBox.RESULT_YES:

            //�o�^���Ė߂�
            result = doInsert();
            break;
          case ACMessageBox.RESULT_CANCEL:
            result = false;
            break;
        }
      }
      else if (IkenshoConstants.AFFAIR_MODE_UPDATE.equals(getNowMode())) {
        //�X�V���[�h�̏ꍇ
        switch (ACMessageBox.showYesNoCancel("�ύX����Ă��܂��B�ۑ����܂����H",
                                                  "�X�V���Ė߂�(S)", 'S',
                                                  "�j�����Ė߂�(R)", 'R')) {
          case ACMessageBox.RESULT_YES:

            //�X�V���Ė߂�
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
   * Apply�O�̒i�K�ōX�V�����\����Ԃ��܂��B
   * @throws Exception ������O
   * @return �X�V�����\��
   */
  protected boolean canControlUpdate() throws Exception {
    //�G���[�`�F�b�N
    Iterator it = tabArray.iterator();
    while (it.hasNext()) {
      IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer) it.next();
      if (!tab.noControlError()) {
        tabs.setSelectedComponent(tab);
        return false;
      }
    }
    //�x���`�F�b�N
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
   * Apply��̒i�K�ōX�V�����\����Ԃ��܂��B
   * @throws Exception ������O
   * @return �X�V�����\��
   */
  protected boolean canDataUpdate() throws Exception {
    //�G���[�`�F�b�N
    if (!hasPatient) {
      //���������`�F�b�N
      if (IkenshoCommon.hasSameName(originalData)) {
        if (ACMessageBox.show("����Ǝv���銳�҂����ɓo�^����Ă��܂��B\n�o�^���܂����H",
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
      return ACMessageBox.show("�X�V���ꂽ���e�͔j������܂��B\n�I�����Ă���낵���ł����H",
                                    ACMessageBox.BUTTON_OKCANCEL,
                                    ACMessageBox.ICON_QUESTION,
                                    ACMessageBox.FOCUS_CANCEL) ==
          ACMessageBox.RESULT_OK;
    }
    return true;
  }

  protected void printActionPerformed(ActionEvent e) throws Exception {
    //�G���[�`�F�b�N
    // [ID:0000438][Tozo TANAKA] 2009/06/08 replace begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
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
    // [ID:0000438][Tozo TANAKA] 2009/06/08 replace end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�

    fullApplySource();

    //�G���[�`�F�b�N
    if (!canDataUpdate()) {
      return;
    }

    //����ݒ�_�C�A���O
    showPrintDialogCustom();

  }

  /**
   * ���̎}�Ԃ��擾���܂��B
   * @param dbm DBManager
   * @throws ParseException ��͗�O
   * @throws SQLException SQL��O
   * @return ���̎}��
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
   * �ǉ��������s���܂��B
   * @throws Exception ������O
   * @return boolean �ǉ������ɐ���������
   */
  protected boolean doInsert() throws Exception {

    //�G���[�`�F�b�N
      // [ID:0000438][Tozo TANAKA] 2009/06/08 replace begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
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
    // [ID:0000438][Tozo TANAKA] 2009/06/08 replace end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�

    IkenshoFirebirdDBManager dbm = null;
    try {
      fullApplySource();

      //�G���[�`�F�b�N
      if (!canDataUpdate()) {
        return false;
      }

      //�p�b�V�u�`�F�b�N�J�n
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
      //�p�b�V�u�`�F�b�N�I��

      if (hasPatient) {
        //�ŐV���ҏ����X�V
        doUpdatePatient(dbm);
      }
      else {
        doInsertPatient(dbm);
        //�p�b�V�u�`�F�b�N�p�Ɋ��Ҕԍ����쐬
        originalData.setData("PATIENT_NO", getPatientNo());
      }

      if (hasOriginalDocument) {
        doUpdateCustomDocument(dbm);
        doUpdateCommonDocument(dbm);
      }
      else {
        setEdaNo(String.valueOf(getNextEdaNo(dbm)));
        //�p�b�V�u�`�F�b�N�p�Ɏ}�ԍ����쐬
        originalData.setData("EDA_NO", getEdaNo());

        doInsertCustomDocument(dbm);
        doInsertCommonDocument(dbm);
      }

      dbm.commitTransaction();

      //�ēx�p�b�V�u
      reselectForPassive(dbm);

      dbm.finalize();

      ACMessageBox.show("�o�^����܂����B");
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
   * override���čX�V�\����Ԃ��������������܂��B
   * @throws Exception ������O
   * @return boolean �X�V�\��
   */
  protected boolean canUpdateCustom() throws Exception {
    return true;
  }

  /**
   * �w��̍쐬�������ŐV�̕����ł��邩��Ԃ��܂��B
   * @param dbm FirebirdDBManager
   * @param originalDate Date
   * @throws SQLException SQL��O
   * @throws ParseException ��͗�O
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
          //���V����������������
          return false;
        }
      }
    }
    return true;
  }

  /**
   * �X�V�������s���܂��B
   * @throws Exception ������O
   * @return boolean �X�V�����ɐ���������
   */
  protected boolean doUpdate() throws Exception {
    //�G���[�`�F�b�N
      // [ID:0000438][Tozo TANAKA] 2009/06/08 replace begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
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
    // [ID:0000438][Tozo TANAKA] 2009/06/08 replace end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�

    IkenshoFirebirdDBManager dbm = null;
    try {
      fullApplySource();

      //�G���[�`�F�b�N
      if (!canDataUpdate()) {
        return false;
      }

      //�p�b�V�u�`�F�b�N�J�n
      clearPassiveTask();

      addPassiveUpdateTask(PASSIVE_CHECK_KEY_PATIENT, 0);
      addPassiveUpdateTask(PASSIVE_CHECK_KEY_COMMON, 0);
      addPassiveUpdateTaskCustom();

      dbm = getPassiveCheckedDBManager();
      if (dbm == null) {
        ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
        return false;
      }
      //�p�b�V�u�`�F�b�N�I��

      if (hasPatient) {
        if ( (getNextEdaNo(dbm) - 1) <= Integer.parseInt(getEdaNo())) {
          //�Ώە����ȊO�̕����ƍ쐬�������r
          Date originalDate = (Date) VRBindPathParser.get("CREATE_DT",
              originalData);
          if (isMostNewDocument(dbm, originalDate)) {
            //���ݍX�V�Ώۂ̕����̕����A�����łȂ��������쐬�����V�����i�ŐV�����j�̂ł���Ί��ҏ����X�V����
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

      ACMessageBox.show("�X�V����܂����B");
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

    //�ǉ����ꂽ���Ҕԍ����擾
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
    //���ҏ��
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
   * �ӌ����w�������ʏ���ǉ����܂��B
   * @param dbm DBManager
   * @throws Exception ������O
   */
  protected void doInsertCommonDocument(IkenshoFirebirdDBManager dbm) throws Exception {
    //�ӌ����w��������
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
    // [ID:0000438][Tozo TANAKA] 2009/06/02 add begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
    sb.append(",MEDICINE7");
    sb.append(",DOSAGE7");
    sb.append(",UNIT7");
    sb.append(",USAGE7");
    sb.append(",MEDICINE8");
    sb.append(",DOSAGE8");
    sb.append(",UNIT8");
    sb.append(",USAGE8");
    // [ID:0000438][Tozo TANAKA] 2009/06/02 add end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
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
    // [ID:0000438][Tozo TANAKA] 2009/06/02 add begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
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
    // [ID:0000438][Tozo TANAKA] 2009/06/02 add end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
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
   * override���ċ��ʕ����}������SQL�L�[���ǉ����܂��B
   * @param sb �ǉ���
   */
  protected void appendInsertCommonDocumentKeys(StringBuffer sb){

  }
  /**
   * override���ċ��ʕ����}������SQL�o�����[���ǉ����܂��B
   * @throws ParseException ��͗�O
   * @param sb �ǉ���
   */
  protected void appendInsertCommonDocumentValues(StringBuffer sb)throws
      ParseException{

  }
  /**
   * override���ċ��ʕ����X�V����SQL�L�[���ǉ����܂��B
   * @param sb �ǉ���
   * @throws ParseException ��͗�O
   */
  protected void appendUpdateCommonDocumentStetement(StringBuffer sb)throws
      ParseException{

  }


  protected void doUpdateCommonDocument(IkenshoFirebirdDBManager dbm) throws ParseException,
      SQLException {

    //�ӌ����w��������
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
    // [ID:0000438][Tozo TANAKA] 2009/06/02 add begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
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
    // [ID:0000438][Tozo TANAKA] 2009/06/02 add end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
    
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
   * �R���X�g���N�^�ł��B
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
    update.setText("�o�^(S)");
    update.setToolTipText("���݂̓��e��o�^���܂��B");
    print.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_PRINT);
    print.setMnemonic('P');
    print.setText("���(P)");
    print.setToolTipText("���݂̓��e��������܂��B");
    editorDateCaption.setText("�L����");
    editorNameCaption.setText("���@��");
    editorDates.setOpaque(false);
    editorDates.setBindPath("KINYU_DT");
    editorDates.setText("����17�N03��17��");
    editorInfo.setOpaque(false);
    editorName.setOpaque(false);
    editorName.setBindPath("PATIENT_NM");
    editorName.setText("�����@�}�C�g");
    editorDates.setFormat(IkenshoConstants.FORMAT_ERA_YMD);

    String osName = System.getProperty("os.name");
    if ( (osName == null) || (osName.indexOf("Mac") < 0)) {
      //Mac�ȊO�͒��F
      editorNameCaption.setForeground(IkenshoConstants.COLOR_TITLE_FOREGROUND);
      editorDateCaption.setForeground(IkenshoConstants.COLOR_TITLE_FOREGROUND);
      editorDates.setForeground(IkenshoConstants.COLOR_TITLE_FOREGROUND);
      editorName.setForeground(IkenshoConstants.COLOR_TITLE_FOREGROUND);
    }else{
      //Mac�͖����F

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
   * �����Ώۂ̃R���|�[�l���g�ɎQ�Ɛ�\�[�X��ݒ肵�܂��B
   *
   * @param source �Q�Ɛ�\�[�X
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
   * �����Ώۂ̃R���|�[�l���g�̃f�t�H���g�f�[�^���i�[�����\�[�X�C���X�^���X�𐶐����܂��B
   *
   * @return �q�v�f�C���X�^���X
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
   * �����Ώۂ̃R���|�[�l���g�ɎQ�Ɛ�\�[�X�̒l�𗬂����݂܂��B
   * @throws Exception ��͗�O
   */
  protected void fullBindSource() throws Exception {
    tabPanel.bindSource();
    editorInfo.bindSource();
    reBindSource();
  }

  /**
   * �������������Ώۂ̃R���|�[�l���g�ɎQ�Ɛ�\�[�X�̒l�𗬂����݂܂��B
   * @throws Exception ��͗�O
   */
  protected void reBindSource() throws Exception {
    Iterator it = tabArray.iterator();
    while (it.hasNext()) {
      ( (IkenshoTabbableChildAffairContainer) it.next()).bindSourceInnerBindComponent();
    }
  }

  /**
   * �����Ώۂ̃R���|�[�l���g�̒l���Q�Ɛ�\�[�X�ɓK�p���܂��B
   * @throws Exception ��͗�O
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
   * �I�𒆂̃^�u�ԍ���Ԃ��܂��B
   * @return �I�𒆂̃^�u�ԍ�
   */
  public int getSelctedTabIndex() {
    return tabs.getSelectedIndex();
  }
  //2006/02/12[Tozo Tanaka] : add begin
  /**
   * �^�u�y�C����Ԃ��܂��B
   * @return �^�u�y�C��
   */
  public JTabbedPane getTabs(){return tabs;}
  //2006/02/12[Tozo Tanaka] : add end

  /**
   * �I�𒆂̃^�u��ݒ肵�܂��B
   * @param tab �I������^�u
   */
  public void setSelctedTab(IkenshoTabbableChildAffairContainer tab) {
    tabs.setSelectedComponent(tab);
  }

  /**
   * ������A�K�p���s�Ȃ����O�ɃC�x���g���������܂��B
   * @throws Exception ������O
   */
  protected void doBeforeBindOnSelected() throws Exception {
    Iterator it = tabArray.iterator();
    while (it.hasNext()) {
      ( (IkenshoTabbableChildAffairContainer) it.next()).doBeforeBindOnSelected();
    }
  }
  

  /**
   * �ȈՃX�i�b�v�V���b�g�N���X��Ԃ��܂��B
   */
  public IkenshoIkenshoSimpleSnapshot getSimpleSnap() {
      // 2006/06/22
      // �X�i�b�v�V���b�g
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

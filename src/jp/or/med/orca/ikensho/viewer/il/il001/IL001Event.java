
/*
 * Project code name "ORCA"
 * 給付管理台帳ソフト QKANCHO（JMA care benefit management software）
 * Copyright(C) 2002 JMA (Japan Medical Association)
 *
 * This program is part of "QKANCHO (JMA care benefit management software)".
 *
 * This program is distributed in the hope that it will be useful
 * for further advancement in medical care, according to JMA Open
 * Source License, but WITHOUT ANY WARRANTY.
 * Everyone is granted permission to use, copy, modify and
 * redistribute this program, but only under the conditions described
 * in the JMA Open Source License. You should have received a copy of
 * this license along with this program. If not, stop using this
 * program and contact JMA, 2-28-16 Honkomagome, Bunkyo-ku, Tokyo,
 * 113-8621, Japan.
 *****************************************************************
 * アプリ: QKANCHO
 * 開発者: 藤原　伸
 * 作成日: 2012/09/20  日本コンピューター株式会社 藤原　伸 新規作成
 * 更新日: ----/--/--
 * システム 主治医意見書 (I)
 * サブシステム ログ (L)
 * プロセス ログビューア (001)
 * プログラム ログビューア (IL001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.viewer.il.il001;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * ログビューアイベント定義(IL001) 
 */
public abstract class IL001Event extends IL001SQL {
	private static final long serialVersionUID = 1L;
  /**
   * コンストラクタです。
   */
  public IL001Event(){
    addEvents();
  }
  /**
   * イベント発生条件を定義します。
   */
  protected void addEvents() {
    getPrevButton().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                prevButtonActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getNextButton().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                nextButtonActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });

  }
  //コンポーネントイベント

  /**
   * 「ひとつ前のログファイルを開く」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void prevButtonActionPerformed(ActionEvent e) throws Exception;

  /**
   * 「次のログファイルを開く」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void nextButtonActionPerformed(ActionEvent e) throws Exception;

  //変数定義

  private VRMap headerMap = new VRHashMap();
  private VRMap patientMap = new VRHashMap();
  private VRMap affairMap = new VRHashMap();
  private VRList logFileList = new VRArrayList();
  private VRList logFileContentList = new VRArrayList();
  private boolean canDBConnect;
  private int readLogFileNumber;
  public static final String FILE_COUNT = "FILE_COUNT";
  public static final String VIEW_FILE_NUMBER = "VIEW_FILE_NUMBER";
  public static final String LOG_DATETIME = "LOG_DATETIME";
  public static final String USER_NAME = "USER_NAME";
  public static final String AFFAIR_ID = "AFFAIR_ID";
  public static final String AFFAIR_NAME = "AFFAIR_NAME";
  public static final String PATIENT_NAME = "PATIENT_NAME";
  public static final String PATIENT_NAME_KANA = "PATIENT_NAME_KANA";
  public static final String MESSAGE_PROGRAM_TITLE = "医見書 ログファイルビューア";
  public static final String MESSAGE_PROGRAM_EXIT = "医見書 ログファイルビューアを終了してもよろしいですか？";
  public static final String MESSAGE_LOG_NOT_FOUND = "表示するログファイルがありません。";
  private IkenshoFirebirdDBManager dbm;
  //getter/setter

  /**
   * headerMapを返します。
   * @return headerMap
   */
  protected VRMap getHeaderMap(){
    return headerMap;
  }
  /**
   * headerMapを設定します。
   * @param headerMap headerMap
   */
  protected void setHeaderMap(VRMap headerMap){
    this.headerMap = headerMap;
  }

  /**
   * patientMapを返します。
   * @return patientMap
   */
  protected VRMap getPatientMap(){
    return patientMap;
  }
  /**
   * patientMapを設定します。
   * @param patientMap patientMap
   */
  protected void setPatientMap(VRMap patientMap){
    this.patientMap = patientMap;
  }

  /**
   * affairMapを返します。
   * @return affairMap
   */
  protected VRMap getAffairMap(){
    return affairMap;
  }
  /**
   * affairMapを設定します。
   * @param affairMap affairMap
   */
  protected void setAffairMap(VRMap affairMap){
    this.affairMap = affairMap;
  }

  /**
   * logFileListを返します。
   * @return logFileList
   */
  protected VRList getLogFileList(){
    return logFileList;
  }
  /**
   * logFileListを設定します。
   * @param logFileList logFileList
   */
  protected void setLogFileList(VRList logFileList){
    this.logFileList = logFileList;
  }

  /**
   * logFileContentListを返します。
   * @return logFileContentList
   */
  protected VRList getLogFileContentList(){
    return logFileContentList;
  }
  /**
   * logFileContentListを設定します。
   * @param logFileContentList logFileContentList
   */
  protected void setLogFileContentList(VRList logFileContentList){
    this.logFileContentList = logFileContentList;
  }

  /**
   * canDBConnectを返します。
   * @return canDBConnect
   */
  protected boolean getCanDBConnect(){
    return canDBConnect;
  }
  /**
   * canDBConnectを設定します。
   * @param canDBConnect canDBConnect
   */
  protected void setCanDBConnect(boolean canDBConnect){
    this.canDBConnect = canDBConnect;
  }

  /**
   * readLogFileNumberを返します。
   * @return readLogFileNumber
   */
  protected int getReadLogFileNumber(){
    return readLogFileNumber;
  }
  /**
   * readLogFileNumberを設定します。
   * @param readLogFileNumber readLogFileNumber
   */
  protected void setReadLogFileNumber(int readLogFileNumber){
    this.readLogFileNumber = readLogFileNumber;
  }

  /**
   * IkenshoFirebirdDBManagerを返します
   * @return IkenshoFirebirdDBManager
   */
  protected IkenshoFirebirdDBManager getDBManager() {
	  return dbm;
  }
  
  /**
   * IkenshoFirebirdDBManagerを設定します。
   * @param IkenshoFirebirdDBManager dbm
   */
  protected void setDBManager(IkenshoFirebirdDBManager dbm ) {
	  this.dbm = dbm;
  }


  //内部関数

  /**
   * 「ログデータ取得」に関する処理を行ないます。
   *
   * @throws Exception 処理例外
   *
   */
  public abstract void readLogFile() throws Exception;

  /**
   * 「業務名称翻訳初期化」に関する処理を行ないます。
   *
   * @throws Exception 処理例外
   *
   */
  public abstract void initAffairNameMap() throws Exception;

  /**
   * 「ログファイルの名称取得」に関する処理を行ないます。
   *
   * @throws Exception 処理例外
   *
   */
  public abstract void initLogFileNameList() throws Exception;

  /**
   * 「ユーザー情報の設定」に関する処理を行ないます。
   *
   * @param row VRMap
   * @param patient_id String
   * @throws Exception 処理例外
   *
   */
  public abstract void setUserInfo(VRMap row, String patient_id) throws Exception;


}

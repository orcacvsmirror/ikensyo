
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
 * 開発者: 樋口　雅彦
 * 作成日: 2009/07/09  日本コンピューター株式会社 樋口　雅彦 新規作成
 * 更新日: ----/--/--
 * システム 給付管理台帳 (Q)
 * サブシステム 保険者管理 (O)
 * プロセス 保険者登録 (002)
 * プログラム 保険者一覧 (IkenshoInsurerSelect)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.affair;
import java.awt.*;
import java.awt.event.*;
import java.awt.im.*;
import java.io.*;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import jp.nichicom.ac.*;
import jp.nichicom.ac.bind.*;
import jp.nichicom.ac.component.*;
import jp.nichicom.ac.component.dnd.*;
import jp.nichicom.ac.component.dnd.event.*;
import jp.nichicom.ac.component.event.*;
import jp.nichicom.ac.component.mainmenu.*;
import jp.nichicom.ac.component.table.*;
import jp.nichicom.ac.component.table.event.*;
import jp.nichicom.ac.container.*;
import jp.nichicom.ac.core.*;
import jp.nichicom.ac.filechooser.*;
import jp.nichicom.ac.io.*;
import jp.nichicom.ac.lang.*;
import jp.nichicom.ac.pdf.*;
import jp.nichicom.ac.sql.*;
import jp.nichicom.ac.text.*;
import jp.nichicom.ac.util.*;
import jp.nichicom.ac.util.adapter.*;
import jp.nichicom.vr.*;
import jp.nichicom.vr.bind.*;
import jp.nichicom.vr.bind.event.*;
import jp.nichicom.vr.border.*;
import jp.nichicom.vr.component.*;
import jp.nichicom.vr.component.event.*;
import jp.nichicom.vr.component.table.*;
import jp.nichicom.vr.container.*;
import jp.nichicom.vr.focus.*;
import jp.nichicom.vr.image.*;
import jp.nichicom.vr.io.*;
import jp.nichicom.vr.layout.*;
import jp.nichicom.vr.text.*;
import jp.nichicom.vr.text.parsers.*;
import jp.nichicom.vr.util.*;
import jp.nichicom.vr.util.adapter.*;
import jp.nichicom.vr.util.logging.*;

/**
 *****************************************************************
 * アプリ: Ikensho
 * 開発者: 樋口　雅彦
 * 作成日: 2009  日本コンピューター株式会社 樋口 雅彦 新規作成
 * @since V3.0.9
 *
 *****************************************************************
 */
public abstract class IkenshoInsurerSelectEvent extends IkenshoInsurerSelectSQL {
  /**
   * コンストラクタです。
   */
  public IkenshoInsurerSelectEvent(){
    addEvents();
  }
  /**
   * イベント発生条件を定義します。
   */
  protected void addEvents() {
    getClose().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                closeActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getInsurerText().getDocument().addDocumentListener(new DocumentListener(){
        private boolean lockFlag = false;
        public void changedUpdate(DocumentEvent e) {
          textChanged(e);
        }
        public void insertUpdate(DocumentEvent e) {
          textChanged(e);
        }
        public void removeUpdate(DocumentEvent e) {
          textChanged(e);
        }
        public void textChanged(DocumentEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                insurerTextTextChanged(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getApply().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                applyActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getDetailsTable().addListSelectionListener(new ListSelectionListener(){
        private boolean lockFlag = false;
        public void valueChanged(ListSelectionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                detailsTableSelectionChanged(e);
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
   * 「閉じる」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void closeActionPerformed(ActionEvent e) throws Exception;

  /**
   * 「テキスト変更に伴う検索」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void insurerTextTextChanged(DocumentEvent e) throws Exception;

  /**
   * 「反映」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void applyActionPerformed(ActionEvent e) throws Exception;

  /**
   * 「テーブル選択時」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void detailsTableSelectionChanged(ListSelectionEvent e) throws Exception;

  //変数定義

  private VRList masterInsurerList = new VRArrayList();
  private VRMap selectInsurerData = new VRHashMap();
  private ACDBManager insurerDBManager;
  private ACTableModelAdapter insurerTableModel;
  private String beforeFindKey;
  //getter/setter

  /**
   * masterInsurerListを返します。
   * @return masterInsurerList
   */
  protected VRList getMasterInsurerList(){
    return masterInsurerList;
  }
  /**
   * masterInsurerListを設定します。
   * @param masterInsurerList masterInsurerList
   */
  protected void setMasterInsurerList(VRList masterInsurerList){
    this.masterInsurerList = masterInsurerList;
  }

  /**
   * selectInsurerDataを返します。
   * @return selectInsurerData
   */
  protected VRMap getSelectInsurerData(){
    return selectInsurerData;
  }
  /**
   * selectInsurerDataを設定します。
   * @param selectInsurerData selectInsurerData
   */
  protected void setSelectInsurerData(VRMap selectInsurerData){
    this.selectInsurerData = selectInsurerData;
  }

  /**
   * insurerDBManagerを返します。
   * @return insurerDBManager
   */
  protected ACDBManager getInsurerDBManager(){
    return insurerDBManager;
  }
  /**
   * insurerDBManagerを設定します。
   * @param insurerDBManager insurerDBManager
   */
  protected void setInsurerDBManager(ACDBManager insurerDBManager){
    this.insurerDBManager = insurerDBManager;
  }

  /**
   * insurerTableModelを返します。
   * @return insurerTableModel
   */
  protected ACTableModelAdapter getInsurerTableModel(){
    return insurerTableModel;
  }
  /**
   * insurerTableModelを設定します。
   * @param insurerTableModel insurerTableModel
   */
  protected void setInsurerTableModel(ACTableModelAdapter insurerTableModel){
    this.insurerTableModel = insurerTableModel;
  }

  /**
   * beforeFindKeyを返します。
   * @return beforeFindKey
   */
  protected String getBeforeFindKey(){
    return beforeFindKey;
  }
  /**
   * beforeFindKeyを設定します。
   * @param beforeFindKey beforeFindKey
   */
  protected void setBeforeFindKey(String beforeFindKey){
    this.beforeFindKey = beforeFindKey;
  }

  //内部関数

  /**
   * 「初期設定」に関する処理を行ないます。
   *
   * @param dbm ACDBManager
   * @throws Exception 処理例外
   * @return VRMap
   */
  public abstract VRMap showModal(ACDBManager dbm) throws Exception;

  /**
   * 「データ検索」に関する処理を行ないます。
   *
   * @throws Exception 処理例外
   *
   */
  public abstract void findData() throws Exception;

}


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
 * 開発者: 田中　統蔵
 * 作成日: 2006/05/01  日本コンピューター株式会社 田中　統蔵 新規作成
 * 更新日: ----/--/--
 * システム 主治医意見書 (I)
 * サブシステム 帳票 (P)
 * プロセス 簡易帳票カスタマイズツール (001)
 * プログラム 簡易帳票カスタマイズツール (IP001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.pdf.ip.ip001;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;

import org.w3c.dom.Node;

/**
 * 簡易帳票カスタマイズツールイベント定義(IP001) 
 */
public abstract class IP001Event extends IP001State {
  /**
   * コンストラクタです。
   */
  public IP001Event(){
    addEvents();
  }
  /**
   * イベント発生条件を定義します。
   */
  protected void addEvents() {
    getOpen().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                openActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getPrint().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                printActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getSave().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                saveActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
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
    getFormatX().addCellEditorListener(new CellEditorListener(){
        private boolean lockFlag = false;
        public void editingStopped(ChangeEvent e) {
          cellEditing(e);
        }
        public void editingCanceled(ChangeEvent e) {
          cellEditing(e);
        }
        public void cellEditing(ChangeEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                formatXCellEditing(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getFormatY().addCellEditorListener(new CellEditorListener(){
        private boolean lockFlag = false;
        public void editingStopped(ChangeEvent e) {
          cellEditing(e);
        }
        public void editingCanceled(ChangeEvent e) {
          cellEditing(e);
        }
        public void cellEditing(ChangeEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                formatYCellEditing(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getFormatWidth().addCellEditorListener(new CellEditorListener(){
        private boolean lockFlag = false;
        public void editingStopped(ChangeEvent e) {
          cellEditing(e);
        }
        public void editingCanceled(ChangeEvent e) {
          cellEditing(e);
        }
        public void cellEditing(ChangeEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                formatWidthCellEditing(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getFormatHeight().addCellEditorListener(new CellEditorListener(){
        private boolean lockFlag = false;
        public void editingStopped(ChangeEvent e) {
          cellEditing(e);
        }
        public void editingCanceled(ChangeEvent e) {
          cellEditing(e);
        }
        public void cellEditing(ChangeEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                formatHeightCellEditing(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getFormatSize().addCellEditorListener(new CellEditorListener(){
        private boolean lockFlag = false;
        public void editingStopped(ChangeEvent e) {
          cellEditing(e);
        }
        public void editingCanceled(ChangeEvent e) {
          cellEditing(e);
        }
        public void cellEditing(ChangeEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                formatSizeCellEditing(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getFormatBorderWidth().addCellEditorListener(new CellEditorListener(){
        private boolean lockFlag = false;
        public void editingStopped(ChangeEvent e) {
          cellEditing(e);
        }
        public void editingCanceled(ChangeEvent e) {
          cellEditing(e);
        }
        public void cellEditing(ChangeEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                formatBorderWidthCellEditing(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getFormatText().addCellEditorListener(new CellEditorListener(){
        private boolean lockFlag = false;
        public void editingStopped(ChangeEvent e) {
          cellEditing(e);
        }
        public void editingCanceled(ChangeEvent e) {
          cellEditing(e);
        }
        public void cellEditing(ChangeEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                formatTextCellEditing(e);
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
   * 「帳票を開く」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void openActionPerformed(ActionEvent e) throws Exception;

  /**
   * 「印刷プレビュー」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void printActionPerformed(ActionEvent e) throws Exception;

  /**
   * 「帳票を保存」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void saveActionPerformed(ActionEvent e) throws Exception;

  /**
   * 「終了」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void closeActionPerformed(ActionEvent e) throws Exception;

  /**
   * 「セル変更」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void formatXCellEditing(ChangeEvent e) throws Exception;

  /**
   * 「セル変更」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void formatYCellEditing(ChangeEvent e) throws Exception;

  /**
   * 「セル変更」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void formatWidthCellEditing(ChangeEvent e) throws Exception;

  /**
   * 「セル変更」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void formatHeightCellEditing(ChangeEvent e) throws Exception;

  /**
   * 「セル変更」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void formatSizeCellEditing(ChangeEvent e) throws Exception;

  /**
   * 「セル変更」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void formatBorderWidthCellEditing(ChangeEvent e) throws Exception;

  /**
   * 「セル変更」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void formatTextCellEditing(ChangeEvent e) throws Exception;

  //変数定義

  private VRMap idReplaceMap = new VRHashMap();
  private VRMap formatNodes = new VRHashMap();
  private org.w3c.dom.Document opendDocument;
  private File opendFile;
  private boolean modified;
  private VRList formatElements = new VRArrayList();
  //getter/setter

  /**
   * idReplaceMapを返します。
   * @return idReplaceMap
   */
  protected VRMap getIdReplaceMap(){
    return idReplaceMap;
  }
  /**
   * idReplaceMapを設定します。
   * @param idReplaceMap idReplaceMap
   */
  protected void setIdReplaceMap(VRMap idReplaceMap){
    this.idReplaceMap = idReplaceMap;
  }

  /**
   * formatNodesを返します。
   * @return formatNodes
   */
  protected VRMap getFormatNodes(){
    return formatNodes;
  }
  /**
   * formatNodesを設定します。
   * @param formatNodes formatNodes
   */
  protected void setFormatNodes(VRMap formatNodes){
    this.formatNodes = formatNodes;
  }

  /**
   * opendDocumentを返します。
   * @return opendDocument
   */
  protected org.w3c.dom.Document getOpendDocument(){
    return opendDocument;
  }
  /**
   * opendDocumentを設定します。
   * @param opendDocument opendDocument
   */
  protected void setOpendDocument(org.w3c.dom.Document opendDocument){
    this.opendDocument = opendDocument;
  }

  /**
   * opendFileを返します。
   * @return opendFile
   */
  protected File getOpendFile(){
    return opendFile;
  }
  /**
   * opendFileを設定します。
   * @param opendFile opendFile
   */
  protected void setOpendFile(File opendFile){
    this.opendFile = opendFile;
  }

  /**
   * modifiedを返します。
   * @return modified
   */
  protected boolean getModified(){
    return modified;
  }
  /**
   * modifiedを設定します。
   * @param modified modified
   */
  protected void setModified(boolean modified){
    this.modified = modified;
  }

  /**
   * formatElementsを返します。
   * @return formatElements
   */
  protected VRList getFormatElements(){
    return formatElements;
  }
  /**
   * formatElementsを設定します。
   * @param formatElements formatElements
   */
  protected void setFormatElements(VRList formatElements){
    this.formatElements = formatElements;
  }

  //内部関数

  /**
   * 「帳票定義体読み込み」に関する処理を行ないます。
   *
   * @throws Exception 処理例外
   *
   */
  public abstract void readFoamrt() throws Exception;

  /**
   * 「子ノード取得」に関する処理を行ないます。
   *
   * @param node Node
   * @param childName String
   * @throws Exception 処理例外
   * @return Node
   */
  public abstract Node getChildNode(Node node, String childName) throws Exception;

  /**
   * 「子ノードのテキスト要素取得」に関する処理を行ないます。
   *
   * @param node Node
   * @param childName String
   * @throws Exception 処理例外
   * @return String
   */
  public abstract String getChildNodeText(Node node, String childName) throws Exception;

  /**
   * 「ノードのテキスト要素取得」に関する処理を行ないます。
   *
   * @param node Node
   * @throws Exception 処理例外
   * @return String
   */
  public abstract String getNodeText(Node node) throws Exception;

  /**
   * 「フォントサイズノード取得」に関する処理を行ないます。
   *
   * @param item Node
   * @throws Exception 処理例外
   * @return String
   */
  public abstract String getNodeFontSize(Node item) throws Exception;

  /**
   * 「フォーマット保存」に関する処理を行ないます。
   *
   * @param outputFile File
   * @throws Exception 処理例外
   *
   */
  public abstract void saveFormat(File outputFile) throws Exception;

  /**
   * 「セル変更」に関する処理を行ないます。
   *
   * @throws Exception 処理例外
   *
   */
  public abstract void cellChanged() throws Exception;

}

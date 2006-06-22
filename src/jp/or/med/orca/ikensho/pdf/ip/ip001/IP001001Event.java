
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
 * 作成日: 2006/04/25  日本コンピューター株式会社 田中　統蔵 新規作成
 * 更新日: ----/--/--
 * システム 主治医意見書 (I)
 * サブシステム 帳票 (P)
 * プロセス 簡易帳票カスタマイズツール (001)
 * プログラム 帳票選択 (IP001001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.pdf.ip.ip001;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import jp.nichicom.ac.ACCommon;

/**
 * 帳票選択イベント定義(IP001001) 
 */
public abstract class IP001001Event extends IP001001Design {
  /**
   * コンストラクタです。
   */
  public IP001001Event(){
    addEvents();
  }
  /**
   * イベント発生条件を定義します。
   */
  protected void addEvents() {
    getOk().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                okActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getCancel().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                cancelActionPerformed(e);
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
   * 「OK」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void okActionPerformed(ActionEvent e) throws Exception;

  /**
   * 「キャンセル」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected abstract void cancelActionPerformed(ActionEvent e) throws Exception;

  //変数定義

  private File selectedFile;
  //getter/setter

  /**
   * selectedFileを返します。
   * @return selectedFile
   */
  protected File getSelectedFile(){
    return selectedFile;
  }
  /**
   * selectedFileを設定します。
   * @param selectedFile selectedFile
   */
  protected void setSelectedFile(File selectedFile){
    this.selectedFile = selectedFile;
  }

  //内部関数

  /**
   * 「帳票選択処理」に関する処理を行ないます。
   *
   * @param openedFile File
   * @throws Exception 処理例外
   * @return File
   */
  public abstract File showModal(File openedFile) throws Exception;

}

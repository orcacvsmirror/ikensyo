
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
import java.io.File;

import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;
import jp.nichicom.ac.core.ACPDFCreatable;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;

/**
 * 帳票選択(IP001001) 
 */
public class IP001001 extends IP001001Event {
  /**
   * コンストラクタです。
   */
  public IP001001(){
  }

  //コンポーネントイベント

  /**
   * 「OK」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected void okActionPerformed(ActionEvent e) throws Exception{
    // 編集する帳票を開く。
    // コンボの選択内容から選択したファイルオブジェクト(selectedFile)を設定する。
        String fileName = "";
        switch (getFormats().getSelectedIndex()) {
        case 0:
            fileName = "NewIkensho1.xml";
            break;
        case 1:
            fileName = "NewIkensho2.xml";
            break;
        case 2:
            fileName = "Shijisho.xml";
            break;
        case 3:
            fileName = "ShijishoB.xml";
            break;
        case 4:
            fileName = "SeikyuIchiran.xml";
            break;
        case 5:
            fileName = "SeikyuIchiranTotal.xml";
            break;
        case 6:
            fileName = "Soukatusho.xml";
            break;
        case 7:
            fileName = "IkenshoMeisai.xml";
            break;
        case 8:
            fileName = "PatientList.xml";
            break;
        case 9:
            fileName = "SeikyuIkenshoIchiran.xml";
            break;
        case 10:
            fileName = "CSVFileOutputPatientList.xml";
            break;
        case 11:
            fileName = "IkenshoShien1.xml";
            break;
        case 12:
            fileName = "IkenshoShien2.xml";
            break;
        // [ID:0000514][Masahiko Higuchi] 2009/09/16 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能
        case 13:
            fileName = "SpecialShijisho.xml";
            break;
        // [ID:0000514][Masahiko Higuchi] 2009/09/16 add end
        }

        ACFrameEventProcesser processer = ACFrame.getInstance()
                .getFrameEventProcesser();
        if (processer instanceof ACPDFCreatable) {
            ACPDFCreatable pdfCreatable = (ACPDFCreatable) processer;
            fileName = pdfCreatable.getPrintFormatDirectory() + fileName;
        }
        setSelectedFile(new File(fileName));

        // 画面を破棄する。
        dispose();
  }

  /**
   * 「キャンセル」イベントです。
   * @param e イベント情報
   * @throws Exception 処理例外
   */
  protected void cancelActionPerformed(ActionEvent e) throws Exception{
    // 画面を破棄する。
      dispose();
  }

  public static void main(String[] args) {
    //デフォルトデバッグ起動
    VRMap param = new VRHashMap();
    //paramに渡りパラメタを詰めて実行することで、簡易デバッグが可能です。
    ACFrame.debugStart(new ACAffairInfo(IP001001.class.getName(), param));
  }

  //内部関数

  /**
   * 「帳票選択処理」に関する処理を行ないます。
   * @throws Exception 処理例外
   */
  public File showModal(File openedFile) throws Exception{
    // 帳票選択処理を実行する。
    // 　引数のファイルオブジェクトから、コンボの選択内容を変更する。
      int formatIndex = 0;
        if (openedFile != null) {
            String fileName = openedFile.getName().toLowerCase();
            if (fileName.endsWith("newikensho1.xml")) {
                formatIndex = 0;
            } else if (fileName.endsWith("newikensho2.xml")) {
                formatIndex = 1;
                // [ID:0000514][Masahiko Higuchi] 2009/09/16 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能
            } else if ("specialshijisho.xml".equals(fileName)) {
                formatIndex = 13;
            // [ID:0000514][Masahiko Higuchi] 2009/09/16 add end
            } else if (fileName.endsWith("shijisho.xml")) {
                formatIndex = 2;
            } else if (fileName.endsWith("shijishob.xml")) {
                formatIndex = 3;
            } else if (fileName.endsWith("seikyuichiran.xml")) {
                formatIndex = 4;
            } else if (fileName.endsWith("seikyuichirantotal.xml")) {
                formatIndex = 5;
            } else if (fileName.endsWith("soukatusho.xml")) {
                formatIndex = 6;
            } else if (fileName.endsWith("ikenshomeisai.xml")) {
                formatIndex = 7;
            } else if (fileName.endsWith("csvfileoutputpatientlist.xml")) {
                formatIndex = 10;
            } else if (fileName.endsWith("patientlist.xml")) {
                formatIndex = 8;
            } else if (fileName.endsWith("seikyuikenshoichiran.xml")) {
                formatIndex = 9;
            } else if (fileName.endsWith("ikenshoshien1.xml")) {
                formatIndex = 11;
            } else if (fileName.endsWith("ikenshoshien2.xml")) {
                formatIndex = 12;
            }
        }
        getFormats().setSelectedIndex(formatIndex);
        
        setTitle("帳票選択");
    // 　画面を表示する。
      setVisible(true);
    // 　選択したファイルオブジェクトを返す。
return getSelectedFile();
  }

}
